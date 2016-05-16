package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.Loan;
import br.com.psi.alexandria.repository.LoanRepository;
import br.com.psi.alexandria.service.LoanService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LoanResource REST controller.
 *
 * @see LoanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class LoanResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_LOAN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOAN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DEVOLUTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEVOLUTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MAX_DEVOLUTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MAX_DEVOLUTION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private LoanRepository loanRepository;

    @Inject
    private LoanService loanService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLoanMockMvc;

    private Loan loan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LoanResource loanResource = new LoanResource();
        ReflectionTestUtils.setField(loanResource, "loanService", loanService);
        this.restLoanMockMvc = MockMvcBuilders.standaloneSetup(loanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        loan = new Loan();
        loan.setDescription(DEFAULT_DESCRIPTION);
        loan.setLoanDate(DEFAULT_LOAN_DATE);
        loan.setDevolutionDate(DEFAULT_DEVOLUTION_DATE);
        loan.setMaxDevolutionDate(DEFAULT_MAX_DEVOLUTION_DATE);
    }

    @Test
    @Transactional
    public void createLoan() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan

        restLoanMockMvc.perform(post("/api/loans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loan)))
                .andExpect(status().isCreated());

        // Validate the Loan in the database
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans).hasSize(databaseSizeBeforeCreate + 1);
        Loan testLoan = loans.get(loans.size() - 1);
        assertThat(testLoan.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLoan.getLoanDate()).isEqualTo(DEFAULT_LOAN_DATE);
        assertThat(testLoan.getDevolutionDate()).isEqualTo(DEFAULT_DEVOLUTION_DATE);
        assertThat(testLoan.getMaxDevolutionDate()).isEqualTo(DEFAULT_MAX_DEVOLUTION_DATE);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanRepository.findAll().size();
        // set the field null
        loan.setDescription(null);

        // Create the Loan, which fails.

        restLoanMockMvc.perform(post("/api/loans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loan)))
                .andExpect(status().isBadRequest());

        List<Loan> loans = loanRepository.findAll();
        assertThat(loans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoanDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanRepository.findAll().size();
        // set the field null
        loan.setLoanDate(null);

        // Create the Loan, which fails.

        restLoanMockMvc.perform(post("/api/loans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loan)))
                .andExpect(status().isBadRequest());

        List<Loan> loans = loanRepository.findAll();
        assertThat(loans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDevolutionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanRepository.findAll().size();
        // set the field null
        loan.setDevolutionDate(null);

        // Create the Loan, which fails.

        restLoanMockMvc.perform(post("/api/loans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loan)))
                .andExpect(status().isBadRequest());

        List<Loan> loans = loanRepository.findAll();
        assertThat(loans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxDevolutionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanRepository.findAll().size();
        // set the field null
        loan.setMaxDevolutionDate(null);

        // Create the Loan, which fails.

        restLoanMockMvc.perform(post("/api/loans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loan)))
                .andExpect(status().isBadRequest());

        List<Loan> loans = loanRepository.findAll();
        assertThat(loans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoans() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loans
        restLoanMockMvc.perform(get("/api/loans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(loan.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].loanDate").value(hasItem(DEFAULT_LOAN_DATE.toString())))
                .andExpect(jsonPath("$.[*].devolutionDate").value(hasItem(DEFAULT_DEVOLUTION_DATE.toString())))
                .andExpect(jsonPath("$.[*].maxDevolutionDate").value(hasItem(DEFAULT_MAX_DEVOLUTION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", loan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(loan.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.loanDate").value(DEFAULT_LOAN_DATE.toString()))
            .andExpect(jsonPath("$.devolutionDate").value(DEFAULT_DEVOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.maxDevolutionDate").value(DEFAULT_MAX_DEVOLUTION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoan() throws Exception {
        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoan() throws Exception {
        // Initialize the database
        loanService.save(loan);

        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Update the loan
        Loan updatedLoan = new Loan();
        updatedLoan.setId(loan.getId());
        updatedLoan.setDescription(UPDATED_DESCRIPTION);
        updatedLoan.setLoanDate(UPDATED_LOAN_DATE);
        updatedLoan.setDevolutionDate(UPDATED_DEVOLUTION_DATE);
        updatedLoan.setMaxDevolutionDate(UPDATED_MAX_DEVOLUTION_DATE);

        restLoanMockMvc.perform(put("/api/loans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLoan)))
                .andExpect(status().isOk());

        // Validate the Loan in the database
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans).hasSize(databaseSizeBeforeUpdate);
        Loan testLoan = loans.get(loans.size() - 1);
        assertThat(testLoan.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLoan.getLoanDate()).isEqualTo(UPDATED_LOAN_DATE);
        assertThat(testLoan.getDevolutionDate()).isEqualTo(UPDATED_DEVOLUTION_DATE);
        assertThat(testLoan.getMaxDevolutionDate()).isEqualTo(UPDATED_MAX_DEVOLUTION_DATE);
    }

    @Test
    @Transactional
    public void deleteLoan() throws Exception {
        // Initialize the database
        loanService.save(loan);

        int databaseSizeBeforeDelete = loanRepository.findAll().size();

        // Get the loan
        restLoanMockMvc.perform(delete("/api/loans/{id}", loan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
