package br.com.psi.alexandria.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.psi.alexandria.domain.PublishingHouse;
import br.com.psi.alexandria.repository.PublishingHouseRepository;
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
 * REST controller for managing PublishingHouse.
 */
@RestController
@RequestMapping("/api")
public class PublishingHouseResource {

    private final Logger log = LoggerFactory.getLogger(PublishingHouseResource.class);
        
    @Inject
    private PublishingHouseRepository publishingHouseRepository;
    
    /**
     * POST  /publishing-houses : Create a new publishingHouse.
     *
     * @param publishingHouse the publishingHouse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publishingHouse, or with status 400 (Bad Request) if the publishingHouse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/publishing-houses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PublishingHouse> createPublishingHouse(@Valid @RequestBody PublishingHouse publishingHouse) throws URISyntaxException {
        log.debug("REST request to save PublishingHouse : {}", publishingHouse);
        if (publishingHouse.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("publishingHouse", "idexists", "A new publishingHouse cannot already have an ID")).body(null);
        }
        PublishingHouse result = publishingHouseRepository.save(publishingHouse);
        return ResponseEntity.created(new URI("/api/publishing-houses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("publishingHouse", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publishing-houses : Updates an existing publishingHouse.
     *
     * @param publishingHouse the publishingHouse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publishingHouse,
     * or with status 400 (Bad Request) if the publishingHouse is not valid,
     * or with status 500 (Internal Server Error) if the publishingHouse couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/publishing-houses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PublishingHouse> updatePublishingHouse(@Valid @RequestBody PublishingHouse publishingHouse) throws URISyntaxException {
        log.debug("REST request to update PublishingHouse : {}", publishingHouse);
        if (publishingHouse.getId() == null) {
            return createPublishingHouse(publishingHouse);
        }
        PublishingHouse result = publishingHouseRepository.save(publishingHouse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("publishingHouse", publishingHouse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publishing-houses : get all the publishingHouses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of publishingHouses in body
     */
    @RequestMapping(value = "/publishing-houses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PublishingHouse> getAllPublishingHouses() {
        log.debug("REST request to get all PublishingHouses");
        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        return publishingHouses;
    }

    /**
     * GET  /publishing-houses/:id : get the "id" publishingHouse.
     *
     * @param id the id of the publishingHouse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publishingHouse, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/publishing-houses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PublishingHouse> getPublishingHouse(@PathVariable Long id) {
        log.debug("REST request to get PublishingHouse : {}", id);
        PublishingHouse publishingHouse = publishingHouseRepository.findOne(id);
        return Optional.ofNullable(publishingHouse)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /publishing-houses/:id : delete the "id" publishingHouse.
     *
     * @param id the id of the publishingHouse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/publishing-houses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePublishingHouse(@PathVariable Long id) {
        log.debug("REST request to delete PublishingHouse : {}", id);
        publishingHouseRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("publishingHouse", id.toString())).build();
    }

}
