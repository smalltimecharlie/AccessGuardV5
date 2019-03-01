package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Products;
import io.github.jhipster.application.repository.ProductsRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Products.
 */
@RestController
@RequestMapping("/api")
public class ProductsResource {

    private final Logger log = LoggerFactory.getLogger(ProductsResource.class);

    private static final String ENTITY_NAME = "products";

    private final ProductsRepository productsRepository;

    public ProductsResource(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    /**
     * POST  /products : Create a new products.
     *
     * @param products the products to create
     * @return the ResponseEntity with status 201 (Created) and with body the new products, or with status 400 (Bad Request) if the products has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/products")
    public ResponseEntity<Products> createProducts(@Valid @RequestBody Products products) throws URISyntaxException {
        log.debug("REST request to save Products : {}", products);
        if (products.getId() != null) {
            throw new BadRequestAlertException("A new products cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Products result = productsRepository.save(products);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /products : Updates an existing products.
     *
     * @param products the products to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated products,
     * or with status 400 (Bad Request) if the products is not valid,
     * or with status 500 (Internal Server Error) if the products couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/products")
    public ResponseEntity<Products> updateProducts(@Valid @RequestBody Products products) throws URISyntaxException {
        log.debug("REST request to update Products : {}", products);
        if (products.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Products result = productsRepository.save(products);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, products.getId().toString()))
            .body(result);
    }

    /**
     * GET  /products : get all the products.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/products")
    public List<Products> getAllProducts(@RequestParam(required = false) String filter) {
        if ("productsusers-is-null".equals(filter)) {
            log.debug("REST request to get all Productss where productsUsers is null");
            return StreamSupport
                .stream(productsRepository.findAll().spliterator(), false)
                .filter(products -> products.getProductsUsers() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Products");
        return productsRepository.findAll();
    }

    /**
     * GET  /products/:id : get the "id" products.
     *
     * @param id the id of the products to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the products, or with status 404 (Not Found)
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Products> getProducts(@PathVariable Long id) {
        log.debug("REST request to get Products : {}", id);
        Optional<Products> products = productsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(products);
    }

    /**
     * DELETE  /products/:id : delete the "id" products.
     *
     * @param id the id of the products to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long id) {
        log.debug("REST request to delete Products : {}", id);
        productsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
