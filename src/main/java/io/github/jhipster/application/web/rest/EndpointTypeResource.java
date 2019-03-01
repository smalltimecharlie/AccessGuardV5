package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.EndpointType;
import io.github.jhipster.application.repository.EndpointTypeRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EndpointType.
 */
@RestController
@RequestMapping("/api")
public class EndpointTypeResource {

    private final Logger log = LoggerFactory.getLogger(EndpointTypeResource.class);

    private static final String ENTITY_NAME = "endpointType";

    private final EndpointTypeRepository endpointTypeRepository;

    public EndpointTypeResource(EndpointTypeRepository endpointTypeRepository) {
        this.endpointTypeRepository = endpointTypeRepository;
    }

    /**
     * POST  /endpoint-types : Create a new endpointType.
     *
     * @param endpointType the endpointType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endpointType, or with status 400 (Bad Request) if the endpointType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/endpoint-types")
    public ResponseEntity<EndpointType> createEndpointType(@RequestBody EndpointType endpointType) throws URISyntaxException {
        log.debug("REST request to save EndpointType : {}", endpointType);
        if (endpointType.getId() != null) {
            throw new BadRequestAlertException("A new endpointType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EndpointType result = endpointTypeRepository.save(endpointType);
        return ResponseEntity.created(new URI("/api/endpoint-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /endpoint-types : Updates an existing endpointType.
     *
     * @param endpointType the endpointType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endpointType,
     * or with status 400 (Bad Request) if the endpointType is not valid,
     * or with status 500 (Internal Server Error) if the endpointType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/endpoint-types")
    public ResponseEntity<EndpointType> updateEndpointType(@RequestBody EndpointType endpointType) throws URISyntaxException {
        log.debug("REST request to update EndpointType : {}", endpointType);
        if (endpointType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EndpointType result = endpointTypeRepository.save(endpointType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, endpointType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /endpoint-types : get all the endpointTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of endpointTypes in body
     */
    @GetMapping("/endpoint-types")
    public List<EndpointType> getAllEndpointTypes() {
        log.debug("REST request to get all EndpointTypes");
        return endpointTypeRepository.findAll();
    }

    /**
     * GET  /endpoint-types/:id : get the "id" endpointType.
     *
     * @param id the id of the endpointType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endpointType, or with status 404 (Not Found)
     */
    @GetMapping("/endpoint-types/{id}")
    public ResponseEntity<EndpointType> getEndpointType(@PathVariable Long id) {
        log.debug("REST request to get EndpointType : {}", id);
        Optional<EndpointType> endpointType = endpointTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(endpointType);
    }

    /**
     * DELETE  /endpoint-types/:id : delete the "id" endpointType.
     *
     * @param id the id of the endpointType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/endpoint-types/{id}")
    public ResponseEntity<Void> deleteEndpointType(@PathVariable Long id) {
        log.debug("REST request to delete EndpointType : {}", id);
        endpointTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
