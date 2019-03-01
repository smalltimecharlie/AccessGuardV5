package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ProductsUsers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductsUsers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductsUsersRepository extends JpaRepository<ProductsUsers, Long> {

}
