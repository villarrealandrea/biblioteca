package com.desafio.biblioteca;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.challenge.library.controller.LibraryController;
import com.challenge.library.entities.Author;
import com.challenge.library.entities.Book;
import com.challenge.library.entities.Category;
import com.challenge.library.repository.AuthorRepository;
import com.challenge.library.repository.BookRepository;
import com.challenge.library.repository.CategoryRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = LibraryController.class)
public class LibraryApplicationTests {
	
	@Autowired
	private LibraryController controller;
	
	@MockBean
	static BookRepository bookRepository;
	@MockBean
	static CategoryRepository categoryRepository;
	@MockBean
	static AuthorRepository authorRepository;
		
	private Category category;
	private Author author;
	private Book book;
	
	@BeforeEach
	public void init() {
		category = new Category(1L, "Terror", "Terror");
		author = new Author(1L, "Steven", "King");
		book = new Book(1L, "Prueba", author, category, true);
		
		MockitoAnnotations.openMocks(this);
		
	}

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(bookRepository).isNotNull();
		assertThat(categoryRepository).isNotNull();
		assertThat(authorRepository).isNotNull();
	}
	
	@Test
	public void createAuthorTest_Created() {
		
		Mockito.when(authorRepository.save(ArgumentMatchers.any(Author.class))).thenReturn(author);
		
		ResponseEntity<Author> response = controller.createAuthor(author);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void createCategoryTest_Created() {
		
		Mockito.when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(category);
		
		ResponseEntity<Category> response = controller.createCategory(category);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void createBookTest_Created() {
		
		Mockito.when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(book);
		
		ResponseEntity<Book> response = controller.createBook(book);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void findAllBooksEnabled() {
		
		List<Book> books = Lists.list(book);
		
		Mockito.when(bookRepository.findByEnabledTrue()).thenReturn(books);
		
		ResponseEntity<List<Book>> response = controller.getAllEnabledBooks();
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}

}
