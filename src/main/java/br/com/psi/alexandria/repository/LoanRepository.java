package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.Loan;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Loan entity.
 */
public interface LoanRepository extends JpaRepository<Loan,Long> {

}
