package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.NightingaleUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NightingaleUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NightingaleUserRepository extends JpaRepository<NightingaleUser, Long> {

}
