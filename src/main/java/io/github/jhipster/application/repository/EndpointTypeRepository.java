package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.EndpointType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EndpointType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndpointTypeRepository extends JpaRepository<EndpointType, Long> {

}
