package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.PublishingHouse;
import br.com.psi.alexandria.repository.PublishingHouseRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PublishingHouseResource REST controller.
 *
 * @see PublishingHouseResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class PublishingHouseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_NATIONALITY = "AAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBB";

    private static final Integer DEFAULT_CNPJ = 1;
    private static final Integer UPDATED_CNPJ = 2;
    private static final String DEFAULT_SITE = "AAAAA";
    private static final String UPDATED_SITE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Integer DEFAULT_ADDRESS_NUMBER = 1;
    private static final Integer UPDATED_ADDRESS_NUMBER = 2;

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    @Inject
    private PublishingHouseRepository publishingHouseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPublishingHouseMockMvc;

    private PublishingHouse publishingHouse;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PublishingHouseResource publishingHouseResource = new PublishingHouseResource();
        ReflectionTestUtils.setField(publishingHouseResource, "publishingHouseRepository", publishingHouseRepository);
        this.restPublishingHouseMockMvc = MockMvcBuilders.standaloneSetup(publishingHouseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        publishingHouse = new PublishingHouse();
        publishingHouse.setName(DEFAULT_NAME);
        publishingHouse.setNationality(DEFAULT_NATIONALITY);
        publishingHouse.setCnpj(DEFAULT_CNPJ);
        publishingHouse.setSite(DEFAULT_SITE);
        publishingHouse.setEmail(DEFAULT_EMAIL);
        publishingHouse.setAdditionalInformation(DEFAULT_ADDITIONAL_INFORMATION);
        publishingHouse.setActive(DEFAULT_ACTIVE);
        publishingHouse.setAddressNumber(DEFAULT_ADDRESS_NUMBER);
        publishingHouse.setPhone(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createPublishingHouse() throws Exception {
        int databaseSizeBeforeCreate = publishingHouseRepository.findAll().size();

        // Create the PublishingHouse

        restPublishingHouseMockMvc.perform(post("/api/publishing-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publishingHouse)))
                .andExpect(status().isCreated());

        // Validate the PublishingHouse in the database
        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeCreate + 1);
        PublishingHouse testPublishingHouse = publishingHouses.get(publishingHouses.size() - 1);
        assertThat(testPublishingHouse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPublishingHouse.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPublishingHouse.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testPublishingHouse.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testPublishingHouse.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPublishingHouse.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
        assertThat(testPublishingHouse.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testPublishingHouse.getAddressNumber()).isEqualTo(DEFAULT_ADDRESS_NUMBER);
        assertThat(testPublishingHouse.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishingHouseRepository.findAll().size();
        // set the field null
        publishingHouse.setName(null);

        // Create the PublishingHouse, which fails.

        restPublishingHouseMockMvc.perform(post("/api/publishing-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publishingHouse)))
                .andExpect(status().isBadRequest());

        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishingHouseRepository.findAll().size();
        // set the field null
        publishingHouse.setCnpj(null);

        // Create the PublishingHouse, which fails.

        restPublishingHouseMockMvc.perform(post("/api/publishing-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publishingHouse)))
                .andExpect(status().isBadRequest());

        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishingHouseRepository.findAll().size();
        // set the field null
        publishingHouse.setEmail(null);

        // Create the PublishingHouse, which fails.

        restPublishingHouseMockMvc.perform(post("/api/publishing-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publishingHouse)))
                .andExpect(status().isBadRequest());

        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishingHouseRepository.findAll().size();
        // set the field null
        publishingHouse.setActive(null);

        // Create the PublishingHouse, which fails.

        restPublishingHouseMockMvc.perform(post("/api/publishing-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publishingHouse)))
                .andExpect(status().isBadRequest());

        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishingHouseRepository.findAll().size();
        // set the field null
        publishingHouse.setAddressNumber(null);

        // Create the PublishingHouse, which fails.

        restPublishingHouseMockMvc.perform(post("/api/publishing-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publishingHouse)))
                .andExpect(status().isBadRequest());

        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = publishingHouseRepository.findAll().size();
        // set the field null
        publishingHouse.setPhone(null);

        // Create the PublishingHouse, which fails.

        restPublishingHouseMockMvc.perform(post("/api/publishing-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publishingHouse)))
                .andExpect(status().isBadRequest());

        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublishingHouses() throws Exception {
        // Initialize the database
        publishingHouseRepository.saveAndFlush(publishingHouse);

        // Get all the publishingHouses
        restPublishingHouseMockMvc.perform(get("/api/publishing-houses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(publishingHouse.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
                .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())))
                .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].addressNumber").value(hasItem(DEFAULT_ADDRESS_NUMBER)))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    public void getPublishingHouse() throws Exception {
        // Initialize the database
        publishingHouseRepository.saveAndFlush(publishingHouse);

        // Get the publishingHouse
        restPublishingHouseMockMvc.perform(get("/api/publishing-houses/{id}", publishingHouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(publishingHouse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.addressNumber").value(DEFAULT_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingPublishingHouse() throws Exception {
        // Get the publishingHouse
        restPublishingHouseMockMvc.perform(get("/api/publishing-houses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublishingHouse() throws Exception {
        // Initialize the database
        publishingHouseRepository.saveAndFlush(publishingHouse);
        int databaseSizeBeforeUpdate = publishingHouseRepository.findAll().size();

        // Update the publishingHouse
        PublishingHouse updatedPublishingHouse = new PublishingHouse();
        updatedPublishingHouse.setId(publishingHouse.getId());
        updatedPublishingHouse.setName(UPDATED_NAME);
        updatedPublishingHouse.setNationality(UPDATED_NATIONALITY);
        updatedPublishingHouse.setCnpj(UPDATED_CNPJ);
        updatedPublishingHouse.setSite(UPDATED_SITE);
        updatedPublishingHouse.setEmail(UPDATED_EMAIL);
        updatedPublishingHouse.setAdditionalInformation(UPDATED_ADDITIONAL_INFORMATION);
        updatedPublishingHouse.setActive(UPDATED_ACTIVE);
        updatedPublishingHouse.setAddressNumber(UPDATED_ADDRESS_NUMBER);
        updatedPublishingHouse.setPhone(UPDATED_PHONE);

        restPublishingHouseMockMvc.perform(put("/api/publishing-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPublishingHouse)))
                .andExpect(status().isOk());

        // Validate the PublishingHouse in the database
        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeUpdate);
        PublishingHouse testPublishingHouse = publishingHouses.get(publishingHouses.size() - 1);
        assertThat(testPublishingHouse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublishingHouse.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPublishingHouse.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testPublishingHouse.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testPublishingHouse.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPublishingHouse.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
        assertThat(testPublishingHouse.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPublishingHouse.getAddressNumber()).isEqualTo(UPDATED_ADDRESS_NUMBER);
        assertThat(testPublishingHouse.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void deletePublishingHouse() throws Exception {
        // Initialize the database
        publishingHouseRepository.saveAndFlush(publishingHouse);
        int databaseSizeBeforeDelete = publishingHouseRepository.findAll().size();

        // Get the publishingHouse
        restPublishingHouseMockMvc.perform(delete("/api/publishing-houses/{id}", publishingHouse.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PublishingHouse> publishingHouses = publishingHouseRepository.findAll();
        assertThat(publishingHouses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
