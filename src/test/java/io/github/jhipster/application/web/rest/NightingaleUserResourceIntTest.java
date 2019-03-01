package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.AccessGuardV5App;

import io.github.jhipster.application.domain.NightingaleUser;
import io.github.jhipster.application.repository.NightingaleUserRepository;
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
import org.springframework.util.Base64Utils;
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
 * Test class for the NightingaleUserResource REST controller.
 *
 * @see NightingaleUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessGuardV5App.class)
public class NightingaleUserResourceIntTest {

    private static final String DEFAULT_EMAIL = ":@LS.b";
    private static final String UPDATED_EMAIL = "A@k.!";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_COGNITO_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_COGNITO_USERNAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COGNITO_EVENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COGNITO_EVENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COGNITO_EVENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COGNITO_EVENT_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_COGNITO_SIGNUP_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COGNITO_SIGNUP_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NightingaleUserRepository nightingaleUserRepository;

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

    private MockMvc restNightingaleUserMockMvc;

    private NightingaleUser nightingaleUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NightingaleUserResource nightingaleUserResource = new NightingaleUserResource(nightingaleUserRepository);
        this.restNightingaleUserMockMvc = MockMvcBuilders.standaloneSetup(nightingaleUserResource)
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
    public static NightingaleUser createEntity(EntityManager em) {
        NightingaleUser nightingaleUser = new NightingaleUser()
            .email(DEFAULT_EMAIL)
            .active(DEFAULT_ACTIVE)
            .cognitoUsername(DEFAULT_COGNITO_USERNAME)
            .cognitoEvent(DEFAULT_COGNITO_EVENT)
            .cognitoEventContentType(DEFAULT_COGNITO_EVENT_CONTENT_TYPE)
            .cognitoSignupDate(DEFAULT_COGNITO_SIGNUP_DATE);
        return nightingaleUser;
    }

    @Before
    public void initTest() {
        nightingaleUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createNightingaleUser() throws Exception {
        int databaseSizeBeforeCreate = nightingaleUserRepository.findAll().size();

        // Create the NightingaleUser
        restNightingaleUserMockMvc.perform(post("/api/nightingale-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nightingaleUser)))
            .andExpect(status().isCreated());

        // Validate the NightingaleUser in the database
        List<NightingaleUser> nightingaleUserList = nightingaleUserRepository.findAll();
        assertThat(nightingaleUserList).hasSize(databaseSizeBeforeCreate + 1);
        NightingaleUser testNightingaleUser = nightingaleUserList.get(nightingaleUserList.size() - 1);
        assertThat(testNightingaleUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNightingaleUser.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testNightingaleUser.getCognitoUsername()).isEqualTo(DEFAULT_COGNITO_USERNAME);
        assertThat(testNightingaleUser.getCognitoEvent()).isEqualTo(DEFAULT_COGNITO_EVENT);
        assertThat(testNightingaleUser.getCognitoEventContentType()).isEqualTo(DEFAULT_COGNITO_EVENT_CONTENT_TYPE);
        assertThat(testNightingaleUser.getCognitoSignupDate()).isEqualTo(DEFAULT_COGNITO_SIGNUP_DATE);
    }

    @Test
    @Transactional
    public void createNightingaleUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nightingaleUserRepository.findAll().size();

        // Create the NightingaleUser with an existing ID
        nightingaleUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNightingaleUserMockMvc.perform(post("/api/nightingale-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nightingaleUser)))
            .andExpect(status().isBadRequest());

        // Validate the NightingaleUser in the database
        List<NightingaleUser> nightingaleUserList = nightingaleUserRepository.findAll();
        assertThat(nightingaleUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = nightingaleUserRepository.findAll().size();
        // set the field null
        nightingaleUser.setEmail(null);

        // Create the NightingaleUser, which fails.

        restNightingaleUserMockMvc.perform(post("/api/nightingale-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nightingaleUser)))
            .andExpect(status().isBadRequest());

        List<NightingaleUser> nightingaleUserList = nightingaleUserRepository.findAll();
        assertThat(nightingaleUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = nightingaleUserRepository.findAll().size();
        // set the field null
        nightingaleUser.setActive(null);

        // Create the NightingaleUser, which fails.

        restNightingaleUserMockMvc.perform(post("/api/nightingale-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nightingaleUser)))
            .andExpect(status().isBadRequest());

        List<NightingaleUser> nightingaleUserList = nightingaleUserRepository.findAll();
        assertThat(nightingaleUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNightingaleUsers() throws Exception {
        // Initialize the database
        nightingaleUserRepository.saveAndFlush(nightingaleUser);

        // Get all the nightingaleUserList
        restNightingaleUserMockMvc.perform(get("/api/nightingale-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nightingaleUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].cognitoUsername").value(hasItem(DEFAULT_COGNITO_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].cognitoEventContentType").value(hasItem(DEFAULT_COGNITO_EVENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cognitoEvent").value(hasItem(Base64Utils.encodeToString(DEFAULT_COGNITO_EVENT))))
            .andExpect(jsonPath("$.[*].cognitoSignupDate").value(hasItem(DEFAULT_COGNITO_SIGNUP_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getNightingaleUser() throws Exception {
        // Initialize the database
        nightingaleUserRepository.saveAndFlush(nightingaleUser);

        // Get the nightingaleUser
        restNightingaleUserMockMvc.perform(get("/api/nightingale-users/{id}", nightingaleUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nightingaleUser.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.cognitoUsername").value(DEFAULT_COGNITO_USERNAME.toString()))
            .andExpect(jsonPath("$.cognitoEventContentType").value(DEFAULT_COGNITO_EVENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.cognitoEvent").value(Base64Utils.encodeToString(DEFAULT_COGNITO_EVENT)))
            .andExpect(jsonPath("$.cognitoSignupDate").value(DEFAULT_COGNITO_SIGNUP_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNightingaleUser() throws Exception {
        // Get the nightingaleUser
        restNightingaleUserMockMvc.perform(get("/api/nightingale-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNightingaleUser() throws Exception {
        // Initialize the database
        nightingaleUserRepository.saveAndFlush(nightingaleUser);

        int databaseSizeBeforeUpdate = nightingaleUserRepository.findAll().size();

        // Update the nightingaleUser
        NightingaleUser updatedNightingaleUser = nightingaleUserRepository.findById(nightingaleUser.getId()).get();
        // Disconnect from session so that the updates on updatedNightingaleUser are not directly saved in db
        em.detach(updatedNightingaleUser);
        updatedNightingaleUser
            .email(UPDATED_EMAIL)
            .active(UPDATED_ACTIVE)
            .cognitoUsername(UPDATED_COGNITO_USERNAME)
            .cognitoEvent(UPDATED_COGNITO_EVENT)
            .cognitoEventContentType(UPDATED_COGNITO_EVENT_CONTENT_TYPE)
            .cognitoSignupDate(UPDATED_COGNITO_SIGNUP_DATE);

        restNightingaleUserMockMvc.perform(put("/api/nightingale-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNightingaleUser)))
            .andExpect(status().isOk());

        // Validate the NightingaleUser in the database
        List<NightingaleUser> nightingaleUserList = nightingaleUserRepository.findAll();
        assertThat(nightingaleUserList).hasSize(databaseSizeBeforeUpdate);
        NightingaleUser testNightingaleUser = nightingaleUserList.get(nightingaleUserList.size() - 1);
        assertThat(testNightingaleUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNightingaleUser.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testNightingaleUser.getCognitoUsername()).isEqualTo(UPDATED_COGNITO_USERNAME);
        assertThat(testNightingaleUser.getCognitoEvent()).isEqualTo(UPDATED_COGNITO_EVENT);
        assertThat(testNightingaleUser.getCognitoEventContentType()).isEqualTo(UPDATED_COGNITO_EVENT_CONTENT_TYPE);
        assertThat(testNightingaleUser.getCognitoSignupDate()).isEqualTo(UPDATED_COGNITO_SIGNUP_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingNightingaleUser() throws Exception {
        int databaseSizeBeforeUpdate = nightingaleUserRepository.findAll().size();

        // Create the NightingaleUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNightingaleUserMockMvc.perform(put("/api/nightingale-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nightingaleUser)))
            .andExpect(status().isBadRequest());

        // Validate the NightingaleUser in the database
        List<NightingaleUser> nightingaleUserList = nightingaleUserRepository.findAll();
        assertThat(nightingaleUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNightingaleUser() throws Exception {
        // Initialize the database
        nightingaleUserRepository.saveAndFlush(nightingaleUser);

        int databaseSizeBeforeDelete = nightingaleUserRepository.findAll().size();

        // Delete the nightingaleUser
        restNightingaleUserMockMvc.perform(delete("/api/nightingale-users/{id}", nightingaleUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NightingaleUser> nightingaleUserList = nightingaleUserRepository.findAll();
        assertThat(nightingaleUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NightingaleUser.class);
        NightingaleUser nightingaleUser1 = new NightingaleUser();
        nightingaleUser1.setId(1L);
        NightingaleUser nightingaleUser2 = new NightingaleUser();
        nightingaleUser2.setId(nightingaleUser1.getId());
        assertThat(nightingaleUser1).isEqualTo(nightingaleUser2);
        nightingaleUser2.setId(2L);
        assertThat(nightingaleUser1).isNotEqualTo(nightingaleUser2);
        nightingaleUser1.setId(null);
        assertThat(nightingaleUser1).isNotEqualTo(nightingaleUser2);
    }
}
