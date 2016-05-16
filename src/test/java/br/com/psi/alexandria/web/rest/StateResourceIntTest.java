package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.State;
import br.com.psi.alexandria.repository.StateRepository;

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
 * Test class for the StateResource REST controller.
 *
 * @see StateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class StateResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ACRONYM = "AA";
    private static final String UPDATED_ACRONYM = "BB";

    @Inject
    private StateRepository stateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStateMockMvc;

    private State state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StateResource stateResource = new StateResource();
        ReflectionTestUtils.setField(stateResource, "stateRepository", stateRepository);
        this.restStateMockMvc = MockMvcBuilders.standaloneSetup(stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        state = new State();
        state.setDescription(DEFAULT_DESCRIPTION);
        state.setAcronym(DEFAULT_ACRONYM);
    }

    @Test
    @Transactional
    public void createState() throws Exception {
        int databaseSizeBeforeCreate = stateRepository.findAll().size();

        // Create the State

        restStateMockMvc.perform(post("/api/states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(state)))
                .andExpect(status().isCreated());

        // Validate the State in the database
        List<State> states = stateRepository.findAll();
        assertThat(states).hasSize(databaseSizeBeforeCreate + 1);
        State testState = states.get(states.size() - 1);
        assertThat(testState.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testState.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateRepository.findAll().size();
        // set the field null
        state.setDescription(null);

        // Create the State, which fails.

        restStateMockMvc.perform(post("/api/states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(state)))
                .andExpect(status().isBadRequest());

        List<State> states = stateRepository.findAll();
        assertThat(states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStates() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the states
        restStateMockMvc.perform(get("/api/states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(state.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM.toString())));
    }

    @Test
    @Transactional
    public void getState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get the state
        restStateMockMvc.perform(get("/api/states/{id}", state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(state.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingState() throws Exception {
        // Get the state
        restStateMockMvc.perform(get("/api/states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // Update the state
        State updatedState = new State();
        updatedState.setId(state.getId());
        updatedState.setDescription(UPDATED_DESCRIPTION);
        updatedState.setAcronym(UPDATED_ACRONYM);

        restStateMockMvc.perform(put("/api/states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedState)))
                .andExpect(status().isOk());

        // Validate the State in the database
        List<State> states = stateRepository.findAll();
        assertThat(states).hasSize(databaseSizeBeforeUpdate);
        State testState = states.get(states.size() - 1);
        assertThat(testState.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testState.getAcronym()).isEqualTo(UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    public void deleteState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);
        int databaseSizeBeforeDelete = stateRepository.findAll().size();

        // Get the state
        restStateMockMvc.perform(delete("/api/states/{id}", state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<State> states = stateRepository.findAll();
        assertThat(states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
