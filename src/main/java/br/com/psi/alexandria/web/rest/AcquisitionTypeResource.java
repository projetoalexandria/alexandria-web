package br.com.psi.alexandria.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.psi.alexandria.domain.AcquisitionType;
import br.com.psi.alexandria.repository.AcquisitionTypeRepository;
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
 * REST controller for managing AcquisitionType.
 */
@RestController
@RequestMapping("/api")
public class AcquisitionTypeResource {

    private final Logger log = LoggerFactory.getLogger(AcquisitionTypeResource.class);
        
    @Inject
    private AcquisitionTypeRepository acquisitionTypeRepository;
    
    /**
     * POST  /acquisition-types : Create a new acquisitionType.
     *
     * @param acquisitionType the acquisitionType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acquisitionType, or with status 400 (Bad Request) if the acquisitionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/acquisition-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AcquisitionType> createAcquisitionType(@Valid @RequestBody AcquisitionType acquisitionType) throws URISyntaxException {
        log.debug("REST request to save AcquisitionType : {}", acquisitionType);
        if (acquisitionType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("acquisitionType", "idexists", "A new acquisitionType cannot already have an ID")).body(null);
        }
        AcquisitionType result = acquisitionTypeRepository.save(acquisitionType);
        return ResponseEntity.created(new URI("/api/acquisition-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("acquisitionType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acquisition-types : Updates an existing acquisitionType.
     *
     * @param acquisitionType the acquisitionType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acquisitionType,
     * or with status 400 (Bad Request) if the acquisitionType is not valid,
     * or with status 500 (Internal Server Error) if the acquisitionType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/acquisition-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AcquisitionType> updateAcquisitionType(@Valid @RequestBody AcquisitionType acquisitionType) throws URISyntaxException {
        log.debug("REST request to update AcquisitionType : {}", acquisitionType);
        if (acquisitionType.getId() == null) {
            return createAcquisitionType(acquisitionType);
        }
        AcquisitionType result = acquisitionTypeRepository.save(acquisitionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("acquisitionType", acquisitionType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acquisition-types : get all the acquisitionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acquisitionTypes in body
     */
    @RequestMapping(value = "/acquisition-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AcquisitionType> getAllAcquisitionTypes() {
        log.debug("REST request to get all AcquisitionTypes");
        List<AcquisitionType> acquisitionTypes = acquisitionTypeRepository.findAll();
        return acquisitionTypes;
    }

    /**
     * GET  /acquisition-types/:id : get the "id" acquisitionType.
     *
     * @param id the id of the acquisitionType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acquisitionType, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/acquisition-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AcquisitionType> getAcquisitionType(@PathVariable Long id) {
        log.debug("REST request to get AcquisitionType : {}", id);
        AcquisitionType acquisitionType = acquisitionTypeRepository.findOne(id);
        return Optional.ofNullable(acquisitionType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /acquisition-types/:id : delete the "id" acquisitionType.
     *
     * @param id the id of the acquisitionType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/acquisition-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAcquisitionType(@PathVariable Long id) {
        log.debug("REST request to delete AcquisitionType : {}", id);
        acquisitionTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("acquisitionType", id.toString())).build();
    }

}
