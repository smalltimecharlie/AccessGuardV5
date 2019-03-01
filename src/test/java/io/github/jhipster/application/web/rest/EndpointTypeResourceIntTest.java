package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.AccessGuardV5App;

import io.github.jhipster.application.domain.EndpointType;
import io.github.jhipster.application.repository.EndpointTypeRepository;
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
 * Test class for the EndpointTypeResource REST controller.
 *
 * @see EndpointTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessGuardV5App.class)
public class EndpointTypeResourceIntTest {

    private static final String DEFAULT_ENDPOINT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT_TYPE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private EndpointTypeRepository endpointTypeRepository;

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

    private MockMvc restEndpointTypeMockMvc;

    private EndpointType endpointType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EndpointTypeResource endpointTypeResource = new EndpointTypeResource(endpointTypeRepository);
        this.restEndpointTypeMockMvc = MockMvcBuilders.standaloneSetup(endpointTypeResource)
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
    public static EndpointType createEntity(EntityManager em) {
        EndpointType endpointType = new EndpointType()
            .endpointTypeName(DEFAULT_ENDPOINT_TYPE_NAME)
            .active(DEFAULT_ACTIVE);
        return endpointType;
    }

    @Before
    public void initTest() {
        endpointType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndpointType() throws Exception {
        int databaseSizeBeforeCreate = endpointTypeRepository.findAll().size();

        // Create the EndpointType
        restEndpointTypeMockMvc.perform(post("/api/endpoint-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endpointType)))
            .andExpect(status().isCreated());

        // Validate the EndpointType in the database
        List<EndpointType> endpointTypeList = endpointTypeRepository.findAll();
        assertThat(endpointTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EndpointType testEndpointType = endpointTypeList.get(endpointTypeList.size() - 1);
        assertThat(testEndpointType.getEndpointTypeName()).isEqualTo(DEFAULT_ENDPOINT_TYPE_NAME);
        assertThat(testEndpointType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createEndpointTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = endpointTypeRepository.findAll().size();

        // Create the EndpointType with an existing ID
        endpointType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndpointTypeMockMvc.perform(post("/api/endpoint-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endpointType)))
            .andExpect(status().isBadRequest());

        // Validate the EndpointType in the database
        List<EndpointType> endpointTypeList = endpointTypeRepository.findAll();
        assertThat(endpointTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEndpointTypes() throws Exception {
        // Initialize the database
        endpointTypeRepository.saveAndFlush(endpointType);

        // Get all the endpointTypeList
        restEndpointTypeMockMvc.perform(get("/api/endpoint-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endpointType.getId().intValue())))
            .andExpect(jsonPath("$.[*].endpointTypeName").value(hasItem(DEFAULT_ENDPOINT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEndpointType() throws Exception {
        // Initialize the database
        endpointTypeRepository.saveAndFlush(endpointType);

        // Get the endpointType
        restEndpointTypeMockMvc.perform(get("/api/endpoint-types/{id}", endpointType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(endpointType.getId().intValue()))
            .andExpect(jsonPath("$.endpointTypeName").value(DEFAULT_ENDPOINT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEndpointType() throws Exception {
        // Get the endpointType
        restEndpointTypeMockMvc.perform(get("/api/endpoint-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndpointType() throws Exception {
        // Initialize the database
        endpointTypeRepository.saveAndFlush(endpointType);

        int databaseSizeBeforeUpdate = endpointTypeRepository.findAll().size();

        // Update the endpointType
        EndpointType updatedEndpointType = endpointTypeRepository.findById(endpointType.getId()).get();
        // Disconnect from session so that the updates on updatedEndpointType are not directly saved in db
        em.detach(updatedEndpointType);
        updatedEndpointType
            .endpointTypeName(UPDATED_ENDPOINT_TYPE_NAME)
            .active(UPDATED_ACTIVE);

        restEndpointTypeMockMvc.perform(put("/api/endpoint-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEndpointType)))
            .andExpect(status().isOk());

        // Validate the EndpointType in the database
        List<EndpointType> endpointTypeList = endpointTypeRepository.findAll();
        assertThat(endpointTypeList).hasSize(databaseSizeBeforeUpdate);
        EndpointType testEndpointType = endpointTypeList.get(endpointTypeList.size() - 1);
        assertThat(testEndpointType.getEndpointTypeName()).isEqualTo(UPDATED_ENDPOINT_TYPE_NAME);
        assertThat(testEndpointType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingEndpointType() throws Exception {
        int databaseSizeBeforeUpdate = endpointTypeRepository.findAll().size();

        // Create the EndpointType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndpointTypeMockMvc.perform(put("/api/endpoint-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endpointType)))
            .andExpect(status().isBadRequest());

        // Validate the EndpointType in the database
        List<EndpointType> endpointTypeList = endpointTypeRepository.findAll();
        assertThat(endpointTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEndpointType() throws Exception {
        // Initialize the database
        endpointTypeRepository.saveAndFlush(endpointType);

        int databaseSizeBeforeDelete = endpointTypeRepository.findAll().size();

        // Delete the endpointType
        restEndpointTypeMockMvc.perform(delete("/api/endpoint-types/{id}", endpointType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EndpointType> endpointTypeList = endpointTypeRepository.findAll();
        assertThat(endpointTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndpointType.class);
        EndpointType endpointType1 = new EndpointType();
        endpointType1.setId(1L);
        EndpointType endpointType2 = new EndpointType();
        endpointType2.setId(endpointType1.getId());
        assertThat(endpointType1).isEqualTo(endpointType2);
        endpointType2.setId(2L);
        assertThat(endpointType1).isNotEqualTo(endpointType2);
        endpointType1.setId(null);
        assertThat(endpointType1).isNotEqualTo(endpointType2);
    }
}
