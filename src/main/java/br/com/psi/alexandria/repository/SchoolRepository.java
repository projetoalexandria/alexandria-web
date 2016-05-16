package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.School;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the School entity.
 */
public interface SchoolRepository extends JpaRepository<School,Long> {

    @Query("select school from School school where school.responsible.login = ?#{principal.username}")
    List<School> findByResponsibleIsCurrentUser();

}
