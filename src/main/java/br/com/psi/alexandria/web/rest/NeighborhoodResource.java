package br.com.psi.alexandria.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.psi.alexandria.domain.Neighborhood;
import br.com.psi.alexandria.repository.NeighborhoodRepository;
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
 * REST controller for managing Neighborhood.
 */
@RestController
@RequestMapping("/api")
public class NeighborhoodResource {

    private final Logger log = LoggerFactory.getLogger(NeighborhoodResource.class);
        
    @Inject
    private NeighborhoodRepository neighborhoodRepository;
    
    /**
     * POST  /neighborhoods : Create a new neighborhood.
     *
     * @param neighborhood the neighborhood to create
     * @return the ResponseEntity with status 201 (Created) and with body the new neighborhood, or with status 400 (Bad Request) if the neighborhood has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/neighborhoods",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Neighborhood> createNeighborhood(@Valid @RequestBody Neighborhood neighborhood) throws URISyntaxException {
        log.debug("REST request to save Neighborhood : {}", neighborhood);
        if (neighborhood.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("neighborhood", "idexists", "A new neighborhood cannot already have an ID")).body(null);
        }
        Neighborhood result = neighborhoodRepository.save(neighborhood);
        return ResponseEntity.created(new URI("/api/neighborhoods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("neighborhood", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /neighborhoods : Updates an existing neighborhood.
     *
     * @param neighborhood the neighborhood to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated neighborhood,
     * or with status 400 (Bad Request) if the neighborhood is not valid,
     * or with status 500 (Internal Server Error) if the neighborhood couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/neighborhoods",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Neighborhood> updateNeighborhood(@Valid @RequestBody Neighborhood neighborhood) throws URISyntaxException {
        log.debug("REST request to update Neighborhood : {}", neighborhood);
        if (neighborhood.getId() == null) {
            return createNeighborhood(neighborhood);
        }
        Neighborhood result = neighborhoodRepository.save(neighborhood);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("neighborhood", neighborhood.getId().toString()))
            .body(result);
    }

    /**
     * GET  /neighborhoods : get all the neighborhoods.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of neighborhoods in body
     */
    @RequestMapping(value = "/neighborhoods",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Neighborhood> getAllNeighborhoods() {
        log.debug("REST request to get all Neighborhoods");
        List<Neighborhood> neighborhoods = neighborhoodRepository.findAll();
        return neighborhoods;
    }

    /**
     * GET  /neighborhoods/:id : get the "id" neighborhood.
     *
     * @param id the id of the neighborhood to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the neighborhood, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/neighborhoods/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Neighborhood> getNeighborhood(@PathVariable Long id) {
        log.debug("REST request to get Neighborhood : {}", id);
        Neighborhood neighborhood = neighborhoodRepository.findOne(id);
        return Optional.ofNullable(neighborhood)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /neighborhoods/:id : delete the "id" neighborhood.
     *
     * @param id the id of the neighborhood to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/neighborhoods/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNeighborhood(@PathVariable Long id) {
        log.debug("REST request to delete Neighborhood : {}", id);
        neighborhoodRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("neighborhood", id.toString())).build();
    }

}
