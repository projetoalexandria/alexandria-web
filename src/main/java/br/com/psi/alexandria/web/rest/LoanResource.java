package br.com.psi.alexandria.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.psi.alexandria.domain.Loan;
import br.com.psi.alexandria.service.LoanService;
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
 * REST controller for managing Loan.
 */
@RestController
@RequestMapping("/api")
public class LoanResource {

    private final Logger log = LoggerFactory.getLogger(LoanResource.class);
        
    @Inject
    private LoanService loanService;
    
    /**
     * POST  /loans : Create a new loan.
     *
     * @param loan the loan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loan, or with status 400 (Bad Request) if the loan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/loans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loan) throws URISyntaxException {
        log.debug("REST request to save Loan : {}", loan);
        if (loan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("loan", "idexists", "A new loan cannot already have an ID")).body(null);
        }
        Loan result = loanService.save(loan);
        return ResponseEntity.created(new URI("/api/loans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("loan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loans : Updates an existing loan.
     *
     * @param loan the loan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loan,
     * or with status 400 (Bad Request) if the loan is not valid,
     * or with status 500 (Internal Server Error) if the loan couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/loans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Loan> updateLoan(@Valid @RequestBody Loan loan) throws URISyntaxException {
        log.debug("REST request to update Loan : {}", loan);
        if (loan.getId() == null) {
            return createLoan(loan);
        }
        Loan result = loanService.save(loan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("loan", loan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loans : get all the loans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of loans in body
     */
    @RequestMapping(value = "/loans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Loan> getAllLoans() {
        log.debug("REST request to get all Loans");
        return loanService.findAll();
    }

    /**
     * GET  /loans/:id : get the "id" loan.
     *
     * @param id the id of the loan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loan, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/loans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Loan> getLoan(@PathVariable Long id) {
        log.debug("REST request to get Loan : {}", id);
        Loan loan = loanService.findOne(id);
        return Optional.ofNullable(loan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /loans/:id : delete the "id" loan.
     *
     * @param id the id of the loan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/loans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        log.debug("REST request to delete Loan : {}", id);
        loanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("loan", id.toString())).build();
    }

}
