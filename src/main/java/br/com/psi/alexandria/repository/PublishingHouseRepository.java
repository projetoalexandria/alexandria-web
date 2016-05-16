package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.PublishingHouse;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PublishingHouse entity.
 */
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse,Long> {

}
