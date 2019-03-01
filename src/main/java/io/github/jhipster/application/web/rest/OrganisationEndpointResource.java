package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.OrganisationEndpoint;
import io.github.jhipster.application.repository.OrganisationEndpointRepository;
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
 * REST controller for managing OrganisationEndpoint.
 */
@RestController
@RequestMapping("/api")
public class OrganisationEndpointResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationEndpointResource.class);

    private static final String ENTITY_NAME = "organisationEndpoint";

    private final OrganisationEndpointRepository organisationEndpointRepository;

    public OrganisationEndpointResource(OrganisationEndpointRepository organisationEndpointRepository) {
        this.organisationEndpointRepository = organisationEndpointRepository;
    }

    /**
     * POST  /organisation-endpoints : Create a new organisationEndpoint.
     *
     * @param organisationEndpoint the organisationEndpoint to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organisationEndpoint, or with status 400 (Bad Request) if the organisationEndpoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organisation-endpoints")
    public ResponseEntity<OrganisationEndpoint> createOrganisationEndpoint(@Valid @RequestBody OrganisationEndpoint organisationEndpoint) throws URISyntaxException {
        log.debug("REST request to save OrganisationEndpoint : {}", organisationEndpoint);
        if (organisationEndpoint.getId() != null) {
            throw new BadRequestAlertException("A new organisationEndpoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganisationEndpoint result = organisationEndpointRepository.save(organisationEndpoint);
        return ResponseEntity.created(new URI("/api/organisation-endpoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organisation-endpoints : Updates an existing organisationEndpoint.
     *
     * @param organisationEndpoint the organisationEndpoint to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organisationEndpoint,
     * or with status 400 (Bad Request) if the organisationEndpoint is not valid,
     * or with status 500 (Internal Server Error) if the organisationEndpoint couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organisation-endpoints")
    public ResponseEntity<OrganisationEndpoint> updateOrganisationEndpoint(@Valid @RequestBody OrganisationEndpoint organisationEndpoint) throws URISyntaxException {
        log.debug("REST request to update OrganisationEndpoint : {}", organisationEndpoint);
        if (organisationEndpoint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganisationEndpoint result = organisationEndpointRepository.save(organisationEndpoint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organisationEndpoint.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organisation-endpoints : get all the organisationEndpoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of organisationEndpoints in body
     */
    @GetMapping("/organisation-endpoints")
    public List<OrganisationEndpoint> getAllOrganisationEndpoints() {
        log.debug("REST request to get all OrganisationEndpoints");
        return organisationEndpointRepository.findAll();
    }

    /**
     * GET  /organisation-endpoints/:id : get the "id" organisationEndpoint.
     *
     * @param id the id of the organisationEndpoint to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organisationEndpoint, or with status 404 (Not Found)
     */
    @GetMapping("/organisation-endpoints/{id}")
    public ResponseEntity<OrganisationEndpoint> getOrganisationEndpoint(@PathVariable Long id) {
        log.debug("REST request to get OrganisationEndpoint : {}", id);
        Optional<OrganisationEndpoint> organisationEndpoint = organisationEndpointRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organisationEndpoint);
    }

    /**
     * DELETE  /organisation-endpoints/:id : delete the "id" organisationEndpoint.
     *
     * @param id the id of the organisationEndpoint to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organisation-endpoints/{id}")
    public ResponseEntity<Void> deleteOrganisationEndpoint(@PathVariable Long id) {
        log.debug("REST request to delete OrganisationEndpoint : {}", id);
        organisationEndpointRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
