package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.AccessGuardV5App;

import io.github.jhipster.application.domain.OrganisationEndpoint;
import io.github.jhipster.application.repository.OrganisationEndpointRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrganisationEndpointResource REST controller.
 *
 * @see OrganisationEndpointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessGuardV5App.class)
public class OrganisationEndpointResourceIntTest {

    private static final String DEFAULT_ORGANISATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECTION_URI = "AAAAAAAAAA";
    private static final String UPDATED_CONNECTION_URI = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private OrganisationEndpointRepository organisationEndpointRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrganisationEndpointMockMvc;

    private OrganisationEndpoint organisationEndpoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganisationEndpointResource organisationEndpointResource = new OrganisationEndpointResource(organisationEndpointRepository);
        this.restOrganisationEndpointMockMvc = MockMvcBuilders.standaloneSetup(organisationEndpointResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationEndpoint createEntity(EntityManager em) {
        OrganisationEndpoint organisationEndpoint = new OrganisationEndpoint()
            .organisation(DEFAULT_ORGANISATION)
            .connectionUri(DEFAULT_CONNECTION_URI)
            .active(DEFAULT_ACTIVE);
        return organisationEndpoint;
    }

    @Before
    public void initTest() {
        organisationEndpoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisationEndpoint() throws Exception {
        int databaseSizeBeforeCreate = organisationEndpointRepository.findAll().size();

        // Create the OrganisationEndpoint
        restOrganisationEndpointMockMvc.perform(post("/api/organisation-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationEndpoint)))
            .andExpect(status().isCreated());

        // Validate the OrganisationEndpoint in the database
        List<OrganisationEndpoint> organisationEndpointList = organisationEndpointRepository.findAll();
        assertThat(organisationEndpointList).hasSize(databaseSizeBeforeCreate + 1);
        OrganisationEndpoint testOrganisationEndpoint = organisationEndpointList.get(organisationEndpointList.size() - 1);
        assertThat(testOrganisationEndpoint.getOrganisation()).isEqualTo(DEFAULT_ORGANISATION);
        assertThat(testOrganisationEndpoint.getConnectionUri()).isEqualTo(DEFAULT_CONNECTION_URI);
        assertThat(testOrganisationEndpoint.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createOrganisationEndpointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisationEndpointRepository.findAll().size();

        // Create the OrganisationEndpoint with an existing ID
        organisationEndpoint.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationEndpointMockMvc.perform(post("/api/organisation-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationEndpoint)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisationEndpoint in the database
        List<OrganisationEndpoint> organisationEndpointList = organisationEndpointRepository.findAll();
        assertThat(organisationEndpointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrganisationIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationEndpointRepository.findAll().size();
        // set the field null
        organisationEndpoint.setOrganisation(null);

        // Create the OrganisationEndpoint, which fails.

        restOrganisationEndpointMockMvc.perform(post("/api/organisation-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationEndpoint)))
            .andExpect(status().isBadRequest());

        List<OrganisationEndpoint> organisationEndpointList = organisationEndpointRepository.findAll();
        assertThat(organisationEndpointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConnectionUriIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationEndpointRepository.findAll().size();
        // set the field null
        organisationEndpoint.setConnectionUri(null);

        // Create the OrganisationEndpoint, which fails.

        restOrganisationEndpointMockMvc.perform(post("/api/organisation-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationEndpoint)))
            .andExpect(status().isBadRequest());

        List<OrganisationEndpoint> organisationEndpointList = organisationEndpointRepository.findAll();
        assertThat(organisationEndpointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationEndpointRepository.findAll().size();
        // set the field null
        organisationEndpoint.setActive(null);

        // Create the OrganisationEndpoint, which fails.

        restOrganisationEndpointMockMvc.perform(post("/api/organisation-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationEndpoint)))
            .andExpect(status().isBadRequest());

        List<OrganisationEndpoint> organisationEndpointList = organisationEndpointRepository.findAll();
        assertThat(organisationEndpointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrganisationEndpoints() throws Exception {
        // Initialize the database
        organisationEndpointRepository.saveAndFlush(organisationEndpoint);

        // Get all the organisationEndpointList
        restOrganisationEndpointMockMvc.perform(get("/api/organisation-endpoints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisationEndpoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].organisation").value(hasItem(DEFAULT_ORGANISATION.toString())))
            .andExpect(jsonPath("$.[*].connectionUri").value(hasItem(DEFAULT_CONNECTION_URI.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOrganisationEndpoint() throws Exception {
        // Initialize the database
        organisationEndpointRepository.saveAndFlush(organisationEndpoint);

        // Get the organisationEndpoint
        restOrganisationEndpointMockMvc.perform(get("/api/organisation-endpoints/{id}", organisationEndpoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organisationEndpoint.getId().intValue()))
            .andExpect(jsonPath("$.organisation").value(DEFAULT_ORGANISATION.toString()))
            .andExpect(jsonPath("$.connectionUri").value(DEFAULT_CONNECTION_URI.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganisationEndpoint() throws Exception {
        // Get the organisationEndpoint
        restOrganisationEndpointMockMvc.perform(get("/api/organisation-endpoints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisationEndpoint() throws Exception {
        // Initialize the database
        organisationEndpointRepository.saveAndFlush(organisationEndpoint);

        int databaseSizeBeforeUpdate = organisationEndpointRepository.findAll().size();

        // Update the organisationEndpoint
        OrganisationEndpoint updatedOrganisationEndpoint = organisationEndpointRepository.findById(organisationEndpoint.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisationEndpoint are not directly saved in db
        em.detach(updatedOrganisationEndpoint);
        updatedOrganisationEndpoint
            .organisation(UPDATED_ORGANISATION)
            .connectionUri(UPDATED_CONNECTION_URI)
            .active(UPDATED_ACTIVE);

        restOrganisationEndpointMockMvc.perform(put("/api/organisation-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganisationEndpoint)))
            .andExpect(status().isOk());

        // Validate the OrganisationEndpoint in the database
        List<OrganisationEndpoint> organisationEndpointList = organisationEndpointRepository.findAll();
        assertThat(organisationEndpointList).hasSize(databaseSizeBeforeUpdate);
        OrganisationEndpoint testOrganisationEndpoint = organisationEndpointList.get(organisationEndpointList.size() - 1);
        assertThat(testOrganisationEndpoint.getOrganisation()).isEqualTo(UPDATED_ORGANISATION);
        assertThat(testOrganisationEndpoint.getConnectionUri()).isEqualTo(UPDATED_CONNECTION_URI);
        assertThat(testOrganisationEndpoint.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisationEndpoint() throws Exception {
        int databaseSizeBeforeUpdate = organisationEndpointRepository.findAll().size();

        // Create the OrganisationEndpoint

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationEndpointMockMvc.perform(put("/api/organisation-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationEndpoint)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisationEndpoint in the database
        List<OrganisationEndpoint> organisationEndpointList = organisationEndpointRepository.findAll();
        assertThat(organisationEndpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganisationEndpoint() throws Exception {
        // Initialize the database
        organisationEndpointRepository.saveAndFlush(organisationEndpoint);

        int databaseSizeBeforeDelete = organisationEndpointRepository.findAll().size();

        // Delete the organisationEndpoint
        restOrganisationEndpointMockMvc.perform(delete("/api/organisation-endpoints/{id}", organisationEndpoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrganisationEndpoint> organisationEndpointList = organisationEndpointRepository.findAll();
        assertThat(organisationEndpointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationEndpoint.class);
        OrganisationEndpoint organisationEndpoint1 = new OrganisationEndpoint();
        organisationEndpoint1.setId(1L);
        OrganisationEndpoint organisationEndpoint2 = new OrganisationEndpoint();
        organisationEndpoint2.setId(organisationEndpoint1.getId());
        assertThat(organisationEndpoint1).isEqualTo(organisationEndpoint2);
        organisationEndpoint2.setId(2L);
        assertThat(organisationEndpoint1).isNotEqualTo(organisationEndpoint2);
        organisationEndpoint1.setId(null);
        assertThat(organisationEndpoint1).isNotEqualTo(organisationEndpoint2);
    }
}
