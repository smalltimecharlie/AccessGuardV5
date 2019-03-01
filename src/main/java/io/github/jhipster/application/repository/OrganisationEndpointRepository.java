package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.OrganisationEndpoint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrganisationEndpoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationEndpointRepository extends JpaRepository<OrganisationEndpoint, Long> {

}
