package br.com.psi.alexandria.service.impl;

import br.com.psi.alexandria.service.LoanService;
import br.com.psi.alexandria.domain.Loan;
import br.com.psi.alexandria.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Loan.
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService{

    private final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);
    
    @Inject
    private LoanRepository loanRepository;
    
    /**
     * Save a loan.
     * 
     * @param loan the entity to save
     * @return the persisted entity
     */
    public Loan save(Loan loan) {
        log.debug("Request to save Loan : {}", loan);
        Loan result = loanRepository.save(loan);
        return result;
    }

    /**
     *  Get all the loans.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Loan> findAll() {
        log.debug("Request to get all Loans");
        List<Loan> result = loanRepository.findAll();
        return result;
    }

    /**
     *  Get one loan by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Loan findOne(Long id) {
        log.debug("Request to get Loan : {}", id);
        Loan loan = loanRepository.findOne(id);
        return loan;
    }

    /**
     *  Delete the  loan by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Loan : {}", id);
        loanRepository.delete(id);
    }
}
