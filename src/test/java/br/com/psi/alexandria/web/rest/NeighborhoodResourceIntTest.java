package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.Neighborhood;
import br.com.psi.alexandria.repository.NeighborhoodRepository;

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
 * Test class for the NeighborhoodResource REST controller.
 *
 * @see NeighborhoodResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class NeighborhoodResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private NeighborhoodRepository neighborhoodRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNeighborhoodMockMvc;

    private Neighborhood neighborhood;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NeighborhoodResource neighborhoodResource = new NeighborhoodResource();
        ReflectionTestUtils.setField(neighborhoodResource, "neighborhoodRepository", neighborhoodRepository);
        this.restNeighborhoodMockMvc = MockMvcBuilders.standaloneSetup(neighborhoodResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        neighborhood = new Neighborhood();
        neighborhood.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createNeighborhood() throws Exception {
        int databaseSizeBeforeCreate = neighborhoodRepository.findAll().size();

        // Create the Neighborhood

        restNeighborhoodMockMvc.perform(post("/api/neighborhoods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(neighborhood)))
                .andExpect(status().isCreated());

        // Validate the Neighborhood in the database
        List<Neighborhood> neighborhoods = neighborhoodRepository.findAll();
        assertThat(neighborhoods).hasSize(databaseSizeBeforeCreate + 1);
        Neighborhood testNeighborhood = neighborhoods.get(neighborhoods.size() - 1);
        assertThat(testNeighborhood.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = neighborhoodRepository.findAll().size();
        // set the field null
        neighborhood.setDescription(null);

        // Create the Neighborhood, which fails.

        restNeighborhoodMockMvc.perform(post("/api/neighborhoods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(neighborhood)))
                .andExpect(status().isBadRequest());

        List<Neighborhood> neighborhoods = neighborhoodRepository.findAll();
        assertThat(neighborhoods).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNeighborhoods() throws Exception {
        // Initialize the database
        neighborhoodRepository.saveAndFlush(neighborhood);

        // Get all the neighborhoods
        restNeighborhoodMockMvc.perform(get("/api/neighborhoods?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(neighborhood.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getNeighborhood() throws Exception {
        // Initialize the database
        neighborhoodRepository.saveAndFlush(neighborhood);

        // Get the neighborhood
        restNeighborhoodMockMvc.perform(get("/api/neighborhoods/{id}", neighborhood.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(neighborhood.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNeighborhood() throws Exception {
        // Get the neighborhood
        restNeighborhoodMockMvc.perform(get("/api/neighborhoods/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNeighborhood() throws Exception {
        // Initialize the database
        neighborhoodRepository.saveAndFlush(neighborhood);
        int databaseSizeBeforeUpdate = neighborhoodRepository.findAll().size();

        // Update the neighborhood
        Neighborhood updatedNeighborhood = new Neighborhood();
        updatedNeighborhood.setId(neighborhood.getId());
        updatedNeighborhood.setDescription(UPDATED_DESCRIPTION);

        restNeighborhoodMockMvc.perform(put("/api/neighborhoods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedNeighborhood)))
                .andExpect(status().isOk());

        // Validate the Neighborhood in the database
        List<Neighborhood> neighborhoods = neighborhoodRepository.findAll();
        assertThat(neighborhoods).hasSize(databaseSizeBeforeUpdate);
        Neighborhood testNeighborhood = neighborhoods.get(neighborhoods.size() - 1);
        assertThat(testNeighborhood.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteNeighborhood() throws Exception {
        // Initialize the database
        neighborhoodRepository.saveAndFlush(neighborhood);
        int databaseSizeBeforeDelete = neighborhoodRepository.findAll().size();

        // Get the neighborhood
        restNeighborhoodMockMvc.perform(delete("/api/neighborhoods/{id}", neighborhood.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Neighborhood> neighborhoods = neighborhoodRepository.findAll();
        assertThat(neighborhoods).hasSize(databaseSizeBeforeDelete - 1);
    }
}
