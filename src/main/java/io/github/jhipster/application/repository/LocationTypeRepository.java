package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.LocationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, Long> {

}
