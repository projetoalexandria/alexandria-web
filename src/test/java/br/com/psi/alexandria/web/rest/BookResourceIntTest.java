package br.com.psi.alexandria.web.rest;

import br.com.psi.alexandria.AlexandriaApp;
import br.com.psi.alexandria.domain.Book;
import br.com.psi.alexandria.repository.BookRepository;
import br.com.psi.alexandria.service.BookService;

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
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlexandriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class BookResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_SUBTITLE = "AAAAA";
    private static final String UPDATED_SUBTITLE = "BBBBB";
    private static final String DEFAULT_IBSN = "AAAAA";
    private static final String UPDATED_IBSN = "BBBBB";

    private static final Integer DEFAULT_EDITION_NUMBER = 1;
    private static final Integer UPDATED_EDITION_NUMBER = 2;
    private static final String DEFAULT_KEYWORDS = "AAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBB";

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private BookRepository bookRepository;

    @Inject
    private BookService bookService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBookMockMvc;

    private Book book;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookResource bookResource = new BookResource();
        ReflectionTestUtils.setField(bookResource, "bookService", bookService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        book = new Book();
        book.setTitle(DEFAULT_TITLE);
        book.setSubtitle(DEFAULT_SUBTITLE);
        book.setIbsn(DEFAULT_IBSN);
        book.setEditionNumber(DEFAULT_EDITION_NUMBER);
        book.setKeywords(DEFAULT_KEYWORDS);
        book.setPublicationDate(DEFAULT_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book

        restBookMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = books.get(books.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getSubtitle()).isEqualTo(DEFAULT_SUBTITLE);
        assertThat(testBook.getIbsn()).isEqualTo(DEFAULT_IBSN);
        assertThat(testBook.getEditionNumber()).isEqualTo(DEFAULT_EDITION_NUMBER);
        assertThat(testBook.getKeywords()).isEqualTo(DEFAULT_KEYWORDS);
        assertThat(testBook.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setTitle(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isBadRequest());

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIbsnIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setIbsn(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isBadRequest());

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEditionNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setEditionNumber(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isBadRequest());

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setPublicationDate(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isBadRequest());

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the books
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].subtitle").value(hasItem(DEFAULT_SUBTITLE.toString())))
                .andExpect(jsonPath("$.[*].ibsn").value(hasItem(DEFAULT_IBSN.toString())))
                .andExpect(jsonPath("$.[*].editionNumber").value(hasItem(DEFAULT_EDITION_NUMBER)))
                .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS.toString())))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.subtitle").value(DEFAULT_SUBTITLE.toString()))
            .andExpect(jsonPath("$.ibsn").value(DEFAULT_IBSN.toString()))
            .andExpect(jsonPath("$.editionNumber").value(DEFAULT_EDITION_NUMBER))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = new Book();
        updatedBook.setId(book.getId());
        updatedBook.setTitle(UPDATED_TITLE);
        updatedBook.setSubtitle(UPDATED_SUBTITLE);
        updatedBook.setIbsn(UPDATED_IBSN);
        updatedBook.setEditionNumber(UPDATED_EDITION_NUMBER);
        updatedBook.setKeywords(UPDATED_KEYWORDS);
        updatedBook.setPublicationDate(UPDATED_PUBLICATION_DATE);

        restBookMockMvc.perform(put("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBook)))
                .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeUpdate);
        Book testBook = books.get(books.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getSubtitle()).isEqualTo(UPDATED_SUBTITLE);
        assertThat(testBook.getIbsn()).isEqualTo(UPDATED_IBSN);
        assertThat(testBook.getEditionNumber()).isEqualTo(UPDATED_EDITION_NUMBER);
        assertThat(testBook.getKeywords()).isEqualTo(UPDATED_KEYWORDS);
        assertThat(testBook.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Get the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeDelete - 1);
    }
}
