package br.com.psi.alexandria.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.psi.alexandria.domain.AgeBracket;
import br.com.psi.alexandria.repository.AgeBracketRepository;
import br.com.psi.alexandria.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AgeBracket.
 */
@RestController
@RequestMapping("/api")
public class AgeBracketResource {

    private final Logger log = LoggerFactory.getLogger(AgeBracketResource.class);
        
    @Inject
    private AgeBracketRepository ageBracketRepository;
    
    /**
     * POST  /age-brackets : Create a new ageBracket.
     *
     * @param ageBracket the ageBracket to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ageBracket, or with status 400 (Bad Request) if the ageBracket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/age-brackets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AgeBracket> createAgeBracket(@Valid @RequestBody AgeBracket ageBracket) throws URISyntaxException {
        log.debug("REST request to save AgeBracket : {}", ageBracket);
        if (ageBracket.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ageBracket", "idexists", "A new ageBracket cannot already have an ID")).body(null);
        }
        AgeBracket result = ageBracketRepository.save(ageBracket);
        return ResponseEntity.created(new URI("/api/age-brackets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ageBracket", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /age-brackets : Updates an existing ageBracket.
     *
     * @param ageBracket the ageBracket to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ageBracket,
     * or with status 400 (Bad Request) if the ageBracket is not valid,
     * or with status 500 (Internal Server Error) if the ageBracket couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/age-brackets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AgeBracket> updateAgeBracket(@Valid @RequestBody AgeBracket ageBracket) throws URISyntaxException {
        log.debug("REST request to update AgeBracket : {}", ageBracket);
        if (ageBracket.getId() == null) {
            return createAgeBracket(ageBracket);
        }
        AgeBracket result = ageBracketRepository.save(ageBracket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ageBracket", ageBracket.getId().toString()))
            .body(result);
    }

    /**
     * GET  /age-brackets : get all the ageBrackets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ageBrackets in body
     */
    @RequestMapping(value = "/age-brackets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AgeBracket> getAllAgeBrackets() {
        log.debug("REST request to get all AgeBrackets");
        List<AgeBracket> ageBrackets = ageBracketRepository.findAll();
        return ageBrackets;
    }

    /**
     * GET  /age-brackets/:id : get the "id" ageBracket.
     *
     * @param id the id of the ageBracket to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ageBracket, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/age-brackets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AgeBracket> getAgeBracket(@PathVariable Long id) {
        log.debug("REST request to get AgeBracket : {}", id);
        AgeBracket ageBracket = ageBracketRepository.findOne(id);
        return Optional.ofNullable(ageBracket)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /age-brackets/:id : delete the "id" ageBracket.
     *
     * @param id the id of the ageBracket to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/age-brackets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAgeBracket(@PathVariable Long id) {
        log.debug("REST request to delete AgeBracket : {}", id);
        ageBracketRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ageBracket", id.toString())).build();
    }

}
