package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.Cep;
import br.com.psi.alexandria.repository.CepRepository;

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
 * Test class for the CepResource REST controller.
 *
 * @see CepResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class CepResourceIntTest {


    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    @Inject
    private CepRepository cepRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCepMockMvc;

    private Cep cep;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CepResource cepResource = new CepResource();
        ReflectionTestUtils.setField(cepResource, "cepRepository", cepRepository);
        this.restCepMockMvc = MockMvcBuilders.standaloneSetup(cepResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cep = new Cep();
        cep.setNumber(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createCep() throws Exception {
        int databaseSizeBeforeCreate = cepRepository.findAll().size();

        // Create the Cep

        restCepMockMvc.perform(post("/api/ceps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cep)))
                .andExpect(status().isCreated());

        // Validate the Cep in the database
        List<Cep> ceps = cepRepository.findAll();
        assertThat(ceps).hasSize(databaseSizeBeforeCreate + 1);
        Cep testCep = ceps.get(ceps.size() - 1);
        assertThat(testCep.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = cepRepository.findAll().size();
        // set the field null
        cep.setNumber(null);

        // Create the Cep, which fails.

        restCepMockMvc.perform(post("/api/ceps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cep)))
                .andExpect(status().isBadRequest());

        List<Cep> ceps = cepRepository.findAll();
        assertThat(ceps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCeps() throws Exception {
        // Initialize the database
        cepRepository.saveAndFlush(cep);

        // Get all the ceps
        restCepMockMvc.perform(get("/api/ceps?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cep.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    public void getCep() throws Exception {
        // Initialize the database
        cepRepository.saveAndFlush(cep);

        // Get the cep
        restCepMockMvc.perform(get("/api/ceps/{id}", cep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cep.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingCep() throws Exception {
        // Get the cep
        restCepMockMvc.perform(get("/api/ceps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCep() throws Exception {
        // Initialize the database
        cepRepository.saveAndFlush(cep);
        int databaseSizeBeforeUpdate = cepRepository.findAll().size();

        // Update the cep
        Cep updatedCep = new Cep();
        updatedCep.setId(cep.getId());
        updatedCep.setNumber(UPDATED_NUMBER);

        restCepMockMvc.perform(put("/api/ceps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCep)))
                .andExpect(status().isOk());

        // Validate the Cep in the database
        List<Cep> ceps = cepRepository.findAll();
        assertThat(ceps).hasSize(databaseSizeBeforeUpdate);
        Cep testCep = ceps.get(ceps.size() - 1);
        assertThat(testCep.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void deleteCep() throws Exception {
        // Initialize the database
        cepRepository.saveAndFlush(cep);
        int databaseSizeBeforeDelete = cepRepository.findAll().size();

        // Get the cep
        restCepMockMvc.perform(delete("/api/ceps/{id}", cep.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cep> ceps = cepRepository.findAll();
        assertThat(ceps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
