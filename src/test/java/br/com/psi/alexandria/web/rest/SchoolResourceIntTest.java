package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.School;
import br.com.psi.alexandria.repository.SchoolRepository;

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
 * Test class for the SchoolResource REST controller.
 *
 * @see SchoolResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class SchoolResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_FANTASY_NAME = "AAAAA";
    private static final String UPDATED_FANTASY_NAME = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_ADDRESS_NUMBER = "AAAAA";
    private static final String UPDATED_ADDRESS_NUMBER = "BBBBB";

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_SITE = "AAAAA";
    private static final String UPDATED_SITE = "BBBBB";
    private static final String DEFAULT_MEC_NUMBER = "AAAAA";
    private static final String UPDATED_MEC_NUMBER = "BBBBB";

    @Inject
    private SchoolRepository schoolRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSchoolMockMvc;

    private School school;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchoolResource schoolResource = new SchoolResource();
        ReflectionTestUtils.setField(schoolResource, "schoolRepository", schoolRepository);
        this.restSchoolMockMvc = MockMvcBuilders.standaloneSetup(schoolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        school = new School();
        school.setName(DEFAULT_NAME);
        school.setFantasyName(DEFAULT_FANTASY_NAME);
        school.setCode(DEFAULT_CODE);
        school.setAddressNumber(DEFAULT_ADDRESS_NUMBER);
        school.setPhone(DEFAULT_PHONE);
        school.setEmail(DEFAULT_EMAIL);
        school.setSite(DEFAULT_SITE);
        school.setMecNumber(DEFAULT_MEC_NUMBER);
    }

    @Test
    @Transactional
    public void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School

        restSchoolMockMvc.perform(post("/api/schools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(school)))
                .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schools.get(schools.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchool.getFantasyName()).isEqualTo(DEFAULT_FANTASY_NAME);
        assertThat(testSchool.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSchool.getAddressNumber()).isEqualTo(DEFAULT_ADDRESS_NUMBER);
        assertThat(testSchool.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSchool.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSchool.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testSchool.getMecNumber()).isEqualTo(DEFAULT_MEC_NUMBER);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setName(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(school)))
                .andExpect(status().isBadRequest());

        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setPhone(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(school)))
                .andExpect(status().isBadRequest());

        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setEmail(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(school)))
                .andExpect(status().isBadRequest());

        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schools
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fantasyName").value(hasItem(DEFAULT_FANTASY_NAME.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].addressNumber").value(hasItem(DEFAULT_ADDRESS_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
                .andExpect(jsonPath("$.[*].mecNumber").value(hasItem(DEFAULT_MEC_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fantasyName").value(DEFAULT_FANTASY_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.addressNumber").value(DEFAULT_ADDRESS_NUMBER.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.mecNumber").value(DEFAULT_MEC_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = new School();
        updatedSchool.setId(school.getId());
        updatedSchool.setName(UPDATED_NAME);
        updatedSchool.setFantasyName(UPDATED_FANTASY_NAME);
        updatedSchool.setCode(UPDATED_CODE);
        updatedSchool.setAddressNumber(UPDATED_ADDRESS_NUMBER);
        updatedSchool.setPhone(UPDATED_PHONE);
        updatedSchool.setEmail(UPDATED_EMAIL);
        updatedSchool.setSite(UPDATED_SITE);
        updatedSchool.setMecNumber(UPDATED_MEC_NUMBER);

        restSchoolMockMvc.perform(put("/api/schools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSchool)))
                .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schools.get(schools.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchool.getFantasyName()).isEqualTo(UPDATED_FANTASY_NAME);
        assertThat(testSchool.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSchool.getAddressNumber()).isEqualTo(UPDATED_ADDRESS_NUMBER);
        assertThat(testSchool.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSchool.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSchool.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testSchool.getMecNumber()).isEqualTo(UPDATED_MEC_NUMBER);
    }

    @Test
    @Transactional
    public void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Get the school
        restSchoolMockMvc.perform(delete("/api/schools/{id}", school.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeDelete - 1);
    }
}
