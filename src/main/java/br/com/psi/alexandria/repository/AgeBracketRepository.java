package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.AgeBracket;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AgeBracket entity.
 */
public interface AgeBracketRepository extends JpaRepository<AgeBracket,Long> {

}
