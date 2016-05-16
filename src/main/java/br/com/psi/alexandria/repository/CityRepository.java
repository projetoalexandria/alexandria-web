package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.City;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the City entity.
 */
public interface CityRepository extends JpaRepository<City,Long> {

}
