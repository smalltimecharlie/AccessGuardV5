package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.AccessGuardV5App;

import io.github.jhipster.application.domain.ProductsUsers;
import io.github.jhipster.application.repository.ProductsUsersRepository;
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
 * Test class for the ProductsUsersResource REST controller.
 *
 * @see ProductsUsersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccessGuardV5App.class)
public class ProductsUsersResourceIntTest {

    private static final String DEFAULT_EMAIL = "6@*F.NE";
    private static final String UPDATED_EMAIL = "]@Ur.N3";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ProductsUsersRepository productsUsersRepository;

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

    private MockMvc restProductsUsersMockMvc;

    private ProductsUsers productsUsers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductsUsersResource productsUsersResource = new ProductsUsersResource(productsUsersRepository);
        this.restProductsUsersMockMvc = MockMvcBuilders.standaloneSetup(productsUsersResource)
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
    public static ProductsUsers createEntity(EntityManager em) {
        ProductsUsers productsUsers = new ProductsUsers()
            .email(DEFAULT_EMAIL)
            .active(DEFAULT_ACTIVE);
        return productsUsers;
    }

    @Before
    public void initTest() {
        productsUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductsUsers() throws Exception {
        int databaseSizeBeforeCreate = productsUsersRepository.findAll().size();

        // Create the ProductsUsers
        restProductsUsersMockMvc.perform(post("/api/products-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsUsers)))
            .andExpect(status().isCreated());

        // Validate the ProductsUsers in the database
        List<ProductsUsers> productsUsersList = productsUsersRepository.findAll();
        assertThat(productsUsersList).hasSize(databaseSizeBeforeCreate + 1);
        ProductsUsers testProductsUsers = productsUsersList.get(productsUsersList.size() - 1);
        assertThat(testProductsUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProductsUsers.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createProductsUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productsUsersRepository.findAll().size();

        // Create the ProductsUsers with an existing ID
        productsUsers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsUsersMockMvc.perform(post("/api/products-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsUsers)))
            .andExpect(status().isBadRequest());

        // Validate the ProductsUsers in the database
        List<ProductsUsers> productsUsersList = productsUsersRepository.findAll();
        assertThat(productsUsersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsUsersRepository.findAll().size();
        // set the field null
        productsUsers.setEmail(null);

        // Create the ProductsUsers, which fails.

        restProductsUsersMockMvc.perform(post("/api/products-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsUsers)))
            .andExpect(status().isBadRequest());

        List<ProductsUsers> productsUsersList = productsUsersRepository.findAll();
        assertThat(productsUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductsUsers() throws Exception {
        // Initialize the database
        productsUsersRepository.saveAndFlush(productsUsers);

        // Get all the productsUsersList
        restProductsUsersMockMvc.perform(get("/api/products-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProductsUsers() throws Exception {
        // Initialize the database
        productsUsersRepository.saveAndFlush(productsUsers);

        // Get the productsUsers
        restProductsUsersMockMvc.perform(get("/api/products-users/{id}", productsUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productsUsers.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductsUsers() throws Exception {
        // Get the productsUsers
        restProductsUsersMockMvc.perform(get("/api/products-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductsUsers() throws Exception {
        // Initialize the database
        productsUsersRepository.saveAndFlush(productsUsers);

        int databaseSizeBeforeUpdate = productsUsersRepository.findAll().size();

        // Update the productsUsers
        ProductsUsers updatedProductsUsers = productsUsersRepository.findById(productsUsers.getId()).get();
        // Disconnect from session so that the updates on updatedProductsUsers are not directly saved in db
        em.detach(updatedProductsUsers);
        updatedProductsUsers
            .email(UPDATED_EMAIL)
            .active(UPDATED_ACTIVE);

        restProductsUsersMockMvc.perform(put("/api/products-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductsUsers)))
            .andExpect(status().isOk());

        // Validate the ProductsUsers in the database
        List<ProductsUsers> productsUsersList = productsUsersRepository.findAll();
        assertThat(productsUsersList).hasSize(databaseSizeBeforeUpdate);
        ProductsUsers testProductsUsers = productsUsersList.get(productsUsersList.size() - 1);
        assertThat(testProductsUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProductsUsers.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductsUsers() throws Exception {
        int databaseSizeBeforeUpdate = productsUsersRepository.findAll().size();

        // Create the ProductsUsers

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsUsersMockMvc.perform(put("/api/products-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsUsers)))
            .andExpect(status().isBadRequest());

        // Validate the ProductsUsers in the database
        List<ProductsUsers> productsUsersList = productsUsersRepository.findAll();
        assertThat(productsUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductsUsers() throws Exception {
        // Initialize the database
        productsUsersRepository.saveAndFlush(productsUsers);

        int databaseSizeBeforeDelete = productsUsersRepository.findAll().size();

        // Delete the productsUsers
        restProductsUsersMockMvc.perform(delete("/api/products-users/{id}", productsUsers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductsUsers> productsUsersList = productsUsersRepository.findAll();
        assertThat(productsUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsUsers.class);
        ProductsUsers productsUsers1 = new ProductsUsers();
        productsUsers1.setId(1L);
        ProductsUsers productsUsers2 = new ProductsUsers();
        productsUsers2.setId(productsUsers1.getId());
        assertThat(productsUsers1).isEqualTo(productsUsers2);
        productsUsers2.setId(2L);
        assertThat(productsUsers1).isNotEqualTo(productsUsers2);
        productsUsers1.setId(null);
        assertThat(productsUsers1).isNotEqualTo(productsUsers2);
    }
}
