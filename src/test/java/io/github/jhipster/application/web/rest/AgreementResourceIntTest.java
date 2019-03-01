package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.AccessGuardV5App;

import io.github.jhipster.application.domain.Agreement;
import io.github.jhipster.application.repository.AgreementRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgreementResource REST controller.
 *
 * @see AgreementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessGuardV5App.class)
public class AgreementResourceIntTest {

    private static final String DEFAULT_AGREEMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AGREEMENT_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_AGREEMENT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AGREEMENT_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_AGREEMENT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AGREEMENT_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SOURCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    @Autowired
    private AgreementRepository agreementRepository;

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

    private MockMvc restAgreementMockMvc;

    private Agreement agreement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgreementResource agreementResource = new AgreementResource(agreementRepository);
        this.restAgreementMockMvc = MockMvcBuilders.standaloneSetup(agreementResource)
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
    public static Agreement createEntity(EntityManager em) {
        Agreement agreement = new Agreement()
            .agreementName(DEFAULT_AGREEMENT_NAME)
            .agreementStartDate(DEFAULT_AGREEMENT_START_DATE)
            .agreementEndDate(DEFAULT_AGREEMENT_END_DATE)
            .sourceId(DEFAULT_SOURCE_ID)
            .source(DEFAULT_SOURCE);
        return agreement;
    }

    @Before
    public void initTest() {
        agreement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgreement() throws Exception {
        int databaseSizeBeforeCreate = agreementRepository.findAll().size();

        // Create the Agreement
        restAgreementMockMvc.perform(post("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isCreated());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeCreate + 1);
        Agreement testAgreement = agreementList.get(agreementList.size() - 1);
        assertThat(testAgreement.getAgreementName()).isEqualTo(DEFAULT_AGREEMENT_NAME);
        assertThat(testAgreement.getAgreementStartDate()).isEqualTo(DEFAULT_AGREEMENT_START_DATE);
        assertThat(testAgreement.getAgreementEndDate()).isEqualTo(DEFAULT_AGREEMENT_END_DATE);
        assertThat(testAgreement.getSourceId()).isEqualTo(DEFAULT_SOURCE_ID);
        assertThat(testAgreement.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    @Transactional
    public void createAgreementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agreementRepository.findAll().size();

        // Create the Agreement with an existing ID
        agreement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgreementMockMvc.perform(post("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isBadRequest());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAgreements() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        // Get all the agreementList
        restAgreementMockMvc.perform(get("/api/agreements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].agreementName").value(hasItem(DEFAULT_AGREEMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].agreementStartDate").value(hasItem(DEFAULT_AGREEMENT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].agreementEndDate").value(hasItem(DEFAULT_AGREEMENT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].sourceId").value(hasItem(DEFAULT_SOURCE_ID.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())));
    }
    
    @Test
    @Transactional
    public void getAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        // Get the agreement
        restAgreementMockMvc.perform(get("/api/agreements/{id}", agreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agreement.getId().intValue()))
            .andExpect(jsonPath("$.agreementName").value(DEFAULT_AGREEMENT_NAME.toString()))
            .andExpect(jsonPath("$.agreementStartDate").value(DEFAULT_AGREEMENT_START_DATE.toString()))
            .andExpect(jsonPath("$.agreementEndDate").value(DEFAULT_AGREEMENT_END_DATE.toString()))
            .andExpect(jsonPath("$.sourceId").value(DEFAULT_SOURCE_ID.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgreement() throws Exception {
        // Get the agreement
        restAgreementMockMvc.perform(get("/api/agreements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        int databaseSizeBeforeUpdate = agreementRepository.findAll().size();

        // Update the agreement
        Agreement updatedAgreement = agreementRepository.findById(agreement.getId()).get();
        // Disconnect from session so that the updates on updatedAgreement are not directly saved in db
        em.detach(updatedAgreement);
        updatedAgreement
            .agreementName(UPDATED_AGREEMENT_NAME)
            .agreementStartDate(UPDATED_AGREEMENT_START_DATE)
            .agreementEndDate(UPDATED_AGREEMENT_END_DATE)
            .sourceId(UPDATED_SOURCE_ID)
            .source(UPDATED_SOURCE);

        restAgreementMockMvc.perform(put("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgreement)))
            .andExpect(status().isOk());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeUpdate);
        Agreement testAgreement = agreementList.get(agreementList.size() - 1);
        assertThat(testAgreement.getAgreementName()).isEqualTo(UPDATED_AGREEMENT_NAME);
        assertThat(testAgreement.getAgreementStartDate()).isEqualTo(UPDATED_AGREEMENT_START_DATE);
        assertThat(testAgreement.getAgreementEndDate()).isEqualTo(UPDATED_AGREEMENT_END_DATE);
        assertThat(testAgreement.getSourceId()).isEqualTo(UPDATED_SOURCE_ID);
        assertThat(testAgreement.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingAgreement() throws Exception {
        int databaseSizeBeforeUpdate = agreementRepository.findAll().size();

        // Create the Agreement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgreementMockMvc.perform(put("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isBadRequest());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        int databaseSizeBeforeDelete = agreementRepository.findAll().size();

        // Delete the agreement
        restAgreementMockMvc.perform(delete("/api/agreements/{id}", agreement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agreement.class);
        Agreement agreement1 = new Agreement();
        agreement1.setId(1L);
        Agreement agreement2 = new Agreement();
        agreement2.setId(agreement1.getId());
        assertThat(agreement1).isEqualTo(agreement2);
        agreement2.setId(2L);
        assertThat(agreement1).isNotEqualTo(agreement2);
        agreement1.setId(null);
        assertThat(agreement1).isNotEqualTo(agreement2);
    }
}
