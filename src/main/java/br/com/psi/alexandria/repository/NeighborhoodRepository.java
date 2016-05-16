package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.Neighborhood;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Neighborhood entity.
 */
public interface NeighborhoodRepository extends JpaRepository<Neighborhood,Long> {

}
