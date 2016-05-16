package br.com.psi.alexandria.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.psi.alexandria.domain.Cep;
import br.com.psi.alexandria.repository.CepRepository;
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
 * REST controller for managing Cep.
 */
@RestController
@RequestMapping("/api")
public class CepResource {

    private final Logger log = LoggerFactory.getLogger(CepResource.class);
        
    @Inject
    private CepRepository cepRepository;
    
    /**
     * POST  /ceps : Create a new cep.
     *
     * @param cep the cep to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cep, or with status 400 (Bad Request) if the cep has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ceps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cep> createCep(@Valid @RequestBody Cep cep) throws URISyntaxException {
        log.debug("REST request to save Cep : {}", cep);
        if (cep.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cep", "idexists", "A new cep cannot already have an ID")).body(null);
        }
        Cep result = cepRepository.save(cep);
        return ResponseEntity.created(new URI("/api/ceps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cep", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ceps : Updates an existing cep.
     *
     * @param cep the cep to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cep,
     * or with status 400 (Bad Request) if the cep is not valid,
     * or with status 500 (Internal Server Error) if the cep couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ceps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cep> updateCep(@Valid @RequestBody Cep cep) throws URISyntaxException {
        log.debug("REST request to update Cep : {}", cep);
        if (cep.getId() == null) {
            return createCep(cep);
        }
        Cep result = cepRepository.save(cep);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cep", cep.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ceps : get all the ceps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ceps in body
     */
    @RequestMapping(value = "/ceps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cep> getAllCeps() {
        log.debug("REST request to get all Ceps");
        List<Cep> ceps = cepRepository.findAll();
        return ceps;
    }

    /**
     * GET  /ceps/:id : get the "id" cep.
     *
     * @param id the id of the cep to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cep, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/ceps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cep> getCep(@PathVariable Long id) {
        log.debug("REST request to get Cep : {}", id);
        Cep cep = cepRepository.findOne(id);
        return Optional.ofNullable(cep)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ceps/:id : delete the "id" cep.
     *
     * @param id the id of the cep to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/ceps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCep(@PathVariable Long id) {
        log.debug("REST request to delete Cep : {}", id);
        cepRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cep", id.toString())).build();
    }

}
