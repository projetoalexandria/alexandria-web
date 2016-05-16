package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.Cep;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cep entity.
 */
public interface CepRepository extends JpaRepository<Cep,Long> {

}
