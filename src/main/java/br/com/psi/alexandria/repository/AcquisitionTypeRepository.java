package br.com.psi.alexandria.repository;

import br.com.psi.alexandria.domain.AcquisitionType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AcquisitionType entity.
 */
public interface AcquisitionTypeRepository extends JpaRepository<AcquisitionType,Long> {

}
