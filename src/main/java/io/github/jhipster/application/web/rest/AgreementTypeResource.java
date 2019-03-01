package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.AgreementType;
import io.github.jhipster.application.repository.AgreementTypeRepository;
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
 * REST controller for managing AgreementType.
 */
@RestController
@RequestMapping("/api")
public class AgreementTypeResource {

    private final Logger log = LoggerFactory.getLogger(AgreementTypeResource.class);

    private static final String ENTITY_NAME = "agreementType";

    private final AgreementTypeRepository agreementTypeRepository;

    public AgreementTypeResource(AgreementTypeRepository agreementTypeRepository) {
        this.agreementTypeRepository = agreementTypeRepository;
    }

    /**
     * POST  /agreement-types : Create a new agreementType.
     *
     * @param agreementType the agreementType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agreementType, or with status 400 (Bad Request) if the agreementType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agreement-types")
    public ResponseEntity<AgreementType> createAgreementType(@RequestBody AgreementType agreementType) throws URISyntaxException {
        log.debug("REST request to save AgreementType : {}", agreementType);
        if (agreementType.getId() != null) {
            throw new BadRequestAlertException("A new agreementType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgreementType result = agreementTypeRepository.save(agreementType);
        return ResponseEntity.created(new URI("/api/agreement-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agreement-types : Updates an existing agreementType.
     *
     * @param agreementType the agreementType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agreementType,
     * or with status 400 (Bad Request) if the agreementType is not valid,
     * or with status 500 (Internal Server Error) if the agreementType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agreement-types")
    public ResponseEntity<AgreementType> updateAgreementType(@RequestBody AgreementType agreementType) throws URISyntaxException {
        log.debug("REST request to update AgreementType : {}", agreementType);
        if (agreementType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgreementType result = agreementTypeRepository.save(agreementType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agreementType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agreement-types : get all the agreementTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agreementTypes in body
     */
    @GetMapping("/agreement-types")
    public List<AgreementType> getAllAgreementTypes() {
        log.debug("REST request to get all AgreementTypes");
        return agreementTypeRepository.findAll();
    }

    /**
     * GET  /agreement-types/:id : get the "id" agreementType.
     *
     * @param id the id of the agreementType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agreementType, or with status 404 (Not Found)
     */
    @GetMapping("/agreement-types/{id}")
    public ResponseEntity<AgreementType> getAgreementType(@PathVariable Long id) {
        log.debug("REST request to get AgreementType : {}", id);
        Optional<AgreementType> agreementType = agreementTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agreementType);
    }

    /**
     * DELETE  /agreement-types/:id : delete the "id" agreementType.
     *
     * @param id the id of the agreementType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agreement-types/{id}")
    public ResponseEntity<Void> deleteAgreementType(@PathVariable Long id) {
        log.debug("REST request to delete AgreementType : {}", id);
        agreementTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
