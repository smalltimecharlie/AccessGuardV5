package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.NightingaleUser;
import io.github.jhipster.application.repository.NightingaleUserRepository;
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
 * REST controller for managing NightingaleUser.
 */
@RestController
@RequestMapping("/api")
public class NightingaleUserResource {

    private final Logger log = LoggerFactory.getLogger(NightingaleUserResource.class);

    private static final String ENTITY_NAME = "nightingaleUser";

    private final NightingaleUserRepository nightingaleUserRepository;

    public NightingaleUserResource(NightingaleUserRepository nightingaleUserRepository) {
        this.nightingaleUserRepository = nightingaleUserRepository;
    }

    /**
     * POST  /nightingale-users : Create a new nightingaleUser.
     *
     * @param nightingaleUser the nightingaleUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nightingaleUser, or with status 400 (Bad Request) if the nightingaleUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nightingale-users")
    public ResponseEntity<NightingaleUser> createNightingaleUser(@Valid @RequestBody NightingaleUser nightingaleUser) throws URISyntaxException {
        log.debug("REST request to save NightingaleUser : {}", nightingaleUser);
        if (nightingaleUser.getId() != null) {
            throw new BadRequestAlertException("A new nightingaleUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NightingaleUser result = nightingaleUserRepository.save(nightingaleUser);
        return ResponseEntity.created(new URI("/api/nightingale-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nightingale-users : Updates an existing nightingaleUser.
     *
     * @param nightingaleUser the nightingaleUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nightingaleUser,
     * or with status 400 (Bad Request) if the nightingaleUser is not valid,
     * or with status 500 (Internal Server Error) if the nightingaleUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nightingale-users")
    public ResponseEntity<NightingaleUser> updateNightingaleUser(@Valid @RequestBody NightingaleUser nightingaleUser) throws URISyntaxException {
        log.debug("REST request to update NightingaleUser : {}", nightingaleUser);
        if (nightingaleUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NightingaleUser result = nightingaleUserRepository.save(nightingaleUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nightingaleUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nightingale-users : get all the nightingaleUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nightingaleUsers in body
     */
    @GetMapping("/nightingale-users")
    public List<NightingaleUser> getAllNightingaleUsers() {
        log.debug("REST request to get all NightingaleUsers");
        return nightingaleUserRepository.findAll();
    }

    /**
     * GET  /nightingale-users/:id : get the "id" nightingaleUser.
     *
     * @param id the id of the nightingaleUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nightingaleUser, or with status 404 (Not Found)
     */
    @GetMapping("/nightingale-users/{id}")
    public ResponseEntity<NightingaleUser> getNightingaleUser(@PathVariable Long id) {
        log.debug("REST request to get NightingaleUser : {}", id);
        Optional<NightingaleUser> nightingaleUser = nightingaleUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nightingaleUser);
    }

    /**
     * DELETE  /nightingale-users/:id : delete the "id" nightingaleUser.
     *
     * @param id the id of the nightingaleUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nightingale-users/{id}")
    public ResponseEntity<Void> deleteNightingaleUser(@PathVariable Long id) {
        log.debug("REST request to delete NightingaleUser : {}", id);
        nightingaleUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
