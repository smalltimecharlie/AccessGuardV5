package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.ProductsUsers;
import io.github.jhipster.application.repository.ProductsUsersRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProductsUsers.
 */
@RestController
@RequestMapping("/api")
public class ProductsUsersResource {

    private final Logger log = LoggerFactory.getLogger(ProductsUsersResource.class);

    private static final String ENTITY_NAME = "productsUsers";

    private final ProductsUsersRepository productsUsersRepository;

    public ProductsUsersResource(ProductsUsersRepository productsUsersRepository) {
        this.productsUsersRepository = productsUsersRepository;
    }

    /**
     * POST  /products-users : Create a new productsUsers.
     *
     * @param productsUsers the productsUsers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productsUsers, or with status 400 (Bad Request) if the productsUsers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/products-users")
    public ResponseEntity<ProductsUsers> createProductsUsers(@Valid @RequestBody ProductsUsers productsUsers) throws URISyntaxException {
        log.debug("REST request to save ProductsUsers : {}", productsUsers);
        if (productsUsers.getId() != null) {
            throw new BadRequestAlertException("A new productsUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductsUsers result = productsUsersRepository.save(productsUsers);
        return ResponseEntity.created(new URI("/api/products-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /products-users : Updates an existing productsUsers.
     *
     * @param productsUsers the productsUsers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productsUsers,
     * or with status 400 (Bad Request) if the productsUsers is not valid,
     * or with status 500 (Internal Server Error) if the productsUsers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/products-users")
    public ResponseEntity<ProductsUsers> updateProductsUsers(@Valid @RequestBody ProductsUsers productsUsers) throws URISyntaxException {
        log.debug("REST request to update ProductsUsers : {}", productsUsers);
        if (productsUsers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductsUsers result = productsUsersRepository.save(productsUsers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productsUsers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /products-users : get all the productsUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productsUsers in body
     */
    @GetMapping("/products-users")
    public List<ProductsUsers> getAllProductsUsers() {
        log.debug("REST request to get all ProductsUsers");
        return productsUsersRepository.findAll();
    }

    /**
     * GET  /products-users/:id : get the "id" productsUsers.
     *
     * @param id the id of the productsUsers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productsUsers, or with status 404 (Not Found)
     */
    @GetMapping("/products-users/{id}")
    public ResponseEntity<ProductsUsers> getProductsUsers(@PathVariable Long id) {
        log.debug("REST request to get ProductsUsers : {}", id);
        Optional<ProductsUsers> productsUsers = productsUsersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productsUsers);
    }

    /**
     * DELETE  /products-users/:id : delete the "id" productsUsers.
     *
     * @param id the id of the productsUsers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/products-users/{id}")
    public ResponseEntity<Void> deleteProductsUsers(@PathVariable Long id) {
        log.debug("REST request to delete ProductsUsers : {}", id);
        productsUsersRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
