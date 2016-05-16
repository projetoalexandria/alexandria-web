package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.AcquisitionType;
import br.com.psi.alexandria.repository.AcquisitionTypeRepository;

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
 * Test class for the AcquisitionTypeResource REST controller.
 *
 * @see AcquisitionTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class AcquisitionTypeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private AcquisitionTypeRepository acquisitionTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAcquisitionTypeMockMvc;

    private AcquisitionType acquisitionType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AcquisitionTypeResource acquisitionTypeResource = new AcquisitionTypeResource();
        ReflectionTestUtils.setField(acquisitionTypeResource, "acquisitionTypeRepository", acquisitionTypeRepository);
        this.restAcquisitionTypeMockMvc = MockMvcBuilders.standaloneSetup(acquisitionTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        acquisitionType = new AcquisitionType();
        acquisitionType.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAcquisitionType() throws Exception {
        int databaseSizeBeforeCreate = acquisitionTypeRepository.findAll().size();

        // Create the AcquisitionType

        restAcquisitionTypeMockMvc.perform(post("/api/acquisition-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(acquisitionType)))
                .andExpect(status().isCreated());

        // Validate the AcquisitionType in the database
        List<AcquisitionType> acquisitionTypes = acquisitionTypeRepository.findAll();
        assertThat(acquisitionTypes).hasSize(databaseSizeBeforeCreate + 1);
        AcquisitionType testAcquisitionType = acquisitionTypes.get(acquisitionTypes.size() - 1);
        assertThat(testAcquisitionType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = acquisitionTypeRepository.findAll().size();
        // set the field null
        acquisitionType.setDescription(null);

        // Create the AcquisitionType, which fails.

        restAcquisitionTypeMockMvc.perform(post("/api/acquisition-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(acquisitionType)))
                .andExpect(status().isBadRequest());

        List<AcquisitionType> acquisitionTypes = acquisitionTypeRepository.findAll();
        assertThat(acquisitionTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcquisitionTypes() throws Exception {
        // Initialize the database
        acquisitionTypeRepository.saveAndFlush(acquisitionType);

        // Get all the acquisitionTypes
        restAcquisitionTypeMockMvc.perform(get("/api/acquisition-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(acquisitionType.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAcquisitionType() throws Exception {
        // Initialize the database
        acquisitionTypeRepository.saveAndFlush(acquisitionType);

        // Get the acquisitionType
        restAcquisitionTypeMockMvc.perform(get("/api/acquisition-types/{id}", acquisitionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(acquisitionType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAcquisitionType() throws Exception {
        // Get the acquisitionType
        restAcquisitionTypeMockMvc.perform(get("/api/acquisition-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcquisitionType() throws Exception {
        // Initialize the database
        acquisitionTypeRepository.saveAndFlush(acquisitionType);
        int databaseSizeBeforeUpdate = acquisitionTypeRepository.findAll().size();

        // Update the acquisitionType
        AcquisitionType updatedAcquisitionType = new AcquisitionType();
        updatedAcquisitionType.setId(acquisitionType.getId());
        updatedAcquisitionType.setDescription(UPDATED_DESCRIPTION);

        restAcquisitionTypeMockMvc.perform(put("/api/acquisition-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAcquisitionType)))
                .andExpect(status().isOk());

        // Validate the AcquisitionType in the database
        List<AcquisitionType> acquisitionTypes = acquisitionTypeRepository.findAll();
        assertThat(acquisitionTypes).hasSize(databaseSizeBeforeUpdate);
        AcquisitionType testAcquisitionType = acquisitionTypes.get(acquisitionTypes.size() - 1);
        assertThat(testAcquisitionType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteAcquisitionType() throws Exception {
        // Initialize the database
        acquisitionTypeRepository.saveAndFlush(acquisitionType);
        int databaseSizeBeforeDelete = acquisitionTypeRepository.findAll().size();

        // Get the acquisitionType
        restAcquisitionTypeMockMvc.perform(delete("/api/acquisition-types/{id}", acquisitionType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AcquisitionType> acquisitionTypes = acquisitionTypeRepository.findAll();
        assertThat(acquisitionTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
