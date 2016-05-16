package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.AgeBracket;
import br.com.psi.alexandria.repository.AgeBracketRepository;

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
 * Test class for the AgeBracketResource REST controller.
 *
 * @see AgeBracketResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class AgeBracketResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_MIN_AGE = 1;
    private static final Integer UPDATED_MIN_AGE = 2;

    private static final Integer DEFAULT_MAX_AGE = 1;
    private static final Integer UPDATED_MAX_AGE = 2;

    @Inject
    private AgeBracketRepository ageBracketRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAgeBracketMockMvc;

    private AgeBracket ageBracket;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AgeBracketResource ageBracketResource = new AgeBracketResource();
        ReflectionTestUtils.setField(ageBracketResource, "ageBracketRepository", ageBracketRepository);
        this.restAgeBracketMockMvc = MockMvcBuilders.standaloneSetup(ageBracketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ageBracket = new AgeBracket();
        ageBracket.setDescription(DEFAULT_DESCRIPTION);
        ageBracket.setMinAge(DEFAULT_MIN_AGE);
        ageBracket.setMaxAge(DEFAULT_MAX_AGE);
    }

    @Test
    @Transactional
    public void createAgeBracket() throws Exception {
        int databaseSizeBeforeCreate = ageBracketRepository.findAll().size();

        // Create the AgeBracket

        restAgeBracketMockMvc.perform(post("/api/age-brackets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ageBracket)))
                .andExpect(status().isCreated());

        // Validate the AgeBracket in the database
        List<AgeBracket> ageBrackets = ageBracketRepository.findAll();
        assertThat(ageBrackets).hasSize(databaseSizeBeforeCreate + 1);
        AgeBracket testAgeBracket = ageBrackets.get(ageBrackets.size() - 1);
        assertThat(testAgeBracket.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAgeBracket.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testAgeBracket.getMaxAge()).isEqualTo(DEFAULT_MAX_AGE);
    }

    @Test
    @Transactional
    public void checkMinAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ageBracketRepository.findAll().size();
        // set the field null
        ageBracket.setMinAge(null);

        // Create the AgeBracket, which fails.

        restAgeBracketMockMvc.perform(post("/api/age-brackets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ageBracket)))
                .andExpect(status().isBadRequest());

        List<AgeBracket> ageBrackets = ageBracketRepository.findAll();
        assertThat(ageBrackets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgeBrackets() throws Exception {
        // Initialize the database
        ageBracketRepository.saveAndFlush(ageBracket);

        // Get all the ageBrackets
        restAgeBracketMockMvc.perform(get("/api/age-brackets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ageBracket.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
                .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE)));
    }

    @Test
    @Transactional
    public void getAgeBracket() throws Exception {
        // Initialize the database
        ageBracketRepository.saveAndFlush(ageBracket);

        // Get the ageBracket
        restAgeBracketMockMvc.perform(get("/api/age-brackets/{id}", ageBracket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ageBracket.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.minAge").value(DEFAULT_MIN_AGE))
            .andExpect(jsonPath("$.maxAge").value(DEFAULT_MAX_AGE));
    }

    @Test
    @Transactional
    public void getNonExistingAgeBracket() throws Exception {
        // Get the ageBracket
        restAgeBracketMockMvc.perform(get("/api/age-brackets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgeBracket() throws Exception {
        // Initialize the database
        ageBracketRepository.saveAndFlush(ageBracket);
        int databaseSizeBeforeUpdate = ageBracketRepository.findAll().size();

        // Update the ageBracket
        AgeBracket updatedAgeBracket = new AgeBracket();
        updatedAgeBracket.setId(ageBracket.getId());
        updatedAgeBracket.setDescription(UPDATED_DESCRIPTION);
        updatedAgeBracket.setMinAge(UPDATED_MIN_AGE);
        updatedAgeBracket.setMaxAge(UPDATED_MAX_AGE);

        restAgeBracketMockMvc.perform(put("/api/age-brackets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAgeBracket)))
                .andExpect(status().isOk());

        // Validate the AgeBracket in the database
        List<AgeBracket> ageBrackets = ageBracketRepository.findAll();
        assertThat(ageBrackets).hasSize(databaseSizeBeforeUpdate);
        AgeBracket testAgeBracket = ageBrackets.get(ageBrackets.size() - 1);
        assertThat(testAgeBracket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAgeBracket.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testAgeBracket.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
    }

    @Test
    @Transactional
    public void deleteAgeBracket() throws Exception {
        // Initialize the database
        ageBracketRepository.saveAndFlush(ageBracket);
        int databaseSizeBeforeDelete = ageBracketRepository.findAll().size();

        // Get the ageBracket
        restAgeBracketMockMvc.perform(delete("/api/age-brackets/{id}", ageBracket.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AgeBracket> ageBrackets = ageBracketRepository.findAll();
        assertThat(ageBrackets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
