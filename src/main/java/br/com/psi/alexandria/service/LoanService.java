package br.com.psi.alexandria.service;

import br.com.psi.alexandria.domain.Loan;

import java.util.List;

/**
 * Service Interface for managing Loan.
 */
public interface LoanService {

    /**
     * Save a loan.
     * 
     * @param loan the entity to save
     * @return the persisted entity
     */
    Loan save(Loan loan);

    /**
     *  Get all the loans.
     *  
     *  @return the list of entities
     */
    List<Loan> findAll();

    /**
     *  Get the "id" loan.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Loan findOne(Long id);

    /**
     *  Delete the "id" loan.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
