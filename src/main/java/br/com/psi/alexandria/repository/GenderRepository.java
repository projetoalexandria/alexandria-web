package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.Gender;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gender entity.
 */
public interface GenderRepository extends JpaRepository<Gender,Long> {

}
