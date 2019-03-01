package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.AccessGuardV5App;

import io.github.jhipster.application.domain.AgreementType;
import io.github.jhipster.application.repository.AgreementTypeRepository;
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
 * Test class for the AgreementTypeResource REST controller.
 *
 * @see AgreementTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessGuardV5App.class)
public class AgreementTypeResourceIntTest {

    private static final String DEFAULT_AGREEMENT_URL = "AAAAAAAAAA";
    private static final String UPDATED_AGREEMENT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_AGREEMENT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AGREEMENT_TYPE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_AGREEMENT_TYPE_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AGREEMENT_TYPE_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_AGREEMENT_TYPE_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AGREEMENT_TYPE_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AgreementTypeRepository agreementTypeRepository;

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

    private MockMvc restAgreementTypeMockMvc;

    private AgreementType agreementType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgreementTypeResource agreementTypeResource = new AgreementTypeResource(agreementTypeRepository);
        this.restAgreementTypeMockMvc = MockMvcBuilders.standaloneSetup(agreementTypeResource)
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
    public static AgreementType createEntity(EntityManager em) {
        AgreementType agreementType = new AgreementType()
            .agreementUrl(DEFAULT_AGREEMENT_URL)
            .agreementTypeName(DEFAULT_AGREEMENT_TYPE_NAME)
            .agreementTypeStartDate(DEFAULT_AGREEMENT_TYPE_START_DATE)
            .agreementTypeEndDate(DEFAULT_AGREEMENT_TYPE_END_DATE);
        return agreementType;
    }

    @Before
    public void initTest() {
        agreementType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgreementType() throws Exception {
        int databaseSizeBeforeCreate = agreementTypeRepository.findAll().size();

        // Create the AgreementType
        restAgreementTypeMockMvc.perform(post("/api/agreement-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreementType)))
            .andExpect(status().isCreated());

        // Validate the AgreementType in the database
        List<AgreementType> agreementTypeList = agreementTypeRepository.findAll();
        assertThat(agreementTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AgreementType testAgreementType = agreementTypeList.get(agreementTypeList.size() - 1);
        assertThat(testAgreementType.getAgreementUrl()).isEqualTo(DEFAULT_AGREEMENT_URL);
        assertThat(testAgreementType.getAgreementTypeName()).isEqualTo(DEFAULT_AGREEMENT_TYPE_NAME);
        assertThat(testAgreementType.getAgreementTypeStartDate()).isEqualTo(DEFAULT_AGREEMENT_TYPE_START_DATE);
        assertThat(testAgreementType.getAgreementTypeEndDate()).isEqualTo(DEFAULT_AGREEMENT_TYPE_END_DATE);
    }

    @Test
    @Transactional
    public void createAgreementTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agreementTypeRepository.findAll().size();

        // Create the AgreementType with an existing ID
        agreementType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgreementTypeMockMvc.perform(post("/api/agreement-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreementType)))
            .andExpect(status().isBadRequest());

        // Validate the AgreementType in the database
        List<AgreementType> agreementTypeList = agreementTypeRepository.findAll();
        assertThat(agreementTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAgreementTypes() throws Exception {
        // Initialize the database
        agreementTypeRepository.saveAndFlush(agreementType);

        // Get all the agreementTypeList
        restAgreementTypeMockMvc.perform(get("/api/agreement-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agreementType.getId().intValue())))
            .andExpect(jsonPath("$.[*].agreementUrl").value(hasItem(DEFAULT_AGREEMENT_URL.toString())))
            .andExpect(jsonPath("$.[*].agreementTypeName").value(hasItem(DEFAULT_AGREEMENT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].agreementTypeStartDate").value(hasItem(DEFAULT_AGREEMENT_TYPE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].agreementTypeEndDate").value(hasItem(DEFAULT_AGREEMENT_TYPE_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAgreementType() throws Exception {
        // Initialize the database
        agreementTypeRepository.saveAndFlush(agreementType);

        // Get the agreementType
        restAgreementTypeMockMvc.perform(get("/api/agreement-types/{id}", agreementType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agreementType.getId().intValue()))
            .andExpect(jsonPath("$.agreementUrl").value(DEFAULT_AGREEMENT_URL.toString()))
            .andExpect(jsonPath("$.agreementTypeName").value(DEFAULT_AGREEMENT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.agreementTypeStartDate").value(DEFAULT_AGREEMENT_TYPE_START_DATE.toString()))
            .andExpect(jsonPath("$.agreementTypeEndDate").value(DEFAULT_AGREEMENT_TYPE_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgreementType() throws Exception {
        // Get the agreementType
        restAgreementTypeMockMvc.perform(get("/api/agreement-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgreementType() throws Exception {
        // Initialize the database
        agreementTypeRepository.saveAndFlush(agreementType);

        int databaseSizeBeforeUpdate = agreementTypeRepository.findAll().size();

        // Update the agreementType
        AgreementType updatedAgreementType = agreementTypeRepository.findById(agreementType.getId()).get();
        // Disconnect from session so that the updates on updatedAgreementType are not directly saved in db
        em.detach(updatedAgreementType);
        updatedAgreementType
            .agreementUrl(UPDATED_AGREEMENT_URL)
            .agreementTypeName(UPDATED_AGREEMENT_TYPE_NAME)
            .agreementTypeStartDate(UPDATED_AGREEMENT_TYPE_START_DATE)
            .agreementTypeEndDate(UPDATED_AGREEMENT_TYPE_END_DATE);

        restAgreementTypeMockMvc.perform(put("/api/agreement-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgreementType)))
            .andExpect(status().isOk());

        // Validate the AgreementType in the database
        List<AgreementType> agreementTypeList = agreementTypeRepository.findAll();
        assertThat(agreementTypeList).hasSize(databaseSizeBeforeUpdate);
        AgreementType testAgreementType = agreementTypeList.get(agreementTypeList.size() - 1);
        assertThat(testAgreementType.getAgreementUrl()).isEqualTo(UPDATED_AGREEMENT_URL);
        assertThat(testAgreementType.getAgreementTypeName()).isEqualTo(UPDATED_AGREEMENT_TYPE_NAME);
        assertThat(testAgreementType.getAgreementTypeStartDate()).isEqualTo(UPDATED_AGREEMENT_TYPE_START_DATE);
        assertThat(testAgreementType.getAgreementTypeEndDate()).isEqualTo(UPDATED_AGREEMENT_TYPE_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAgreementType() throws Exception {
        int databaseSizeBeforeUpdate = agreementTypeRepository.findAll().size();

        // Create the AgreementType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgreementTypeMockMvc.perform(put("/api/agreement-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreementType)))
            .andExpect(status().isBadRequest());

        // Validate the AgreementType in the database
        List<AgreementType> agreementTypeList = agreementTypeRepository.findAll();
        assertThat(agreementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgreementType() throws Exception {
        // Initialize the database
        agreementTypeRepository.saveAndFlush(agreementType);

        int databaseSizeBeforeDelete = agreementTypeRepository.findAll().size();

        // Delete the agreementType
        restAgreementTypeMockMvc.perform(delete("/api/agreement-types/{id}", agreementType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AgreementType> agreementTypeList = agreementTypeRepository.findAll();
        assertThat(agreementTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgreementType.class);
        AgreementType agreementType1 = new AgreementType();
        agreementType1.setId(1L);
        AgreementType agreementType2 = new AgreementType();
        agreementType2.setId(agreementType1.getId());
        assertThat(agreementType1).isEqualTo(agreementType2);
        agreementType2.setId(2L);
        assertThat(agreementType1).isNotEqualTo(agreementType2);
        agreementType1.setId(null);
        assertThat(agreementType1).isNotEqualTo(agreementType2);
    }
}
