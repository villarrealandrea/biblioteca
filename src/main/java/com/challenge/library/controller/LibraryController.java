package com.challenge.library.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.library.entities.Author;
import com.challenge.library.entities.Book;
import com.challenge.library.entities.Category;
import com.challenge.library.repository.AuthorRepository;
import com.challenge.library.repository.BookRepository;
import com.challenge.library.repository.CategoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Operation(summary = "Permite crear un nuevo autor")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "201", description = "Autor creado correctamente", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = Author.class)) }),
	  @ApiResponse(responseCode = "500", description = "Error interno", 
	    content = @Content) })
	@PostMapping("/author")
	public ResponseEntity<Author> createAuthor(@RequestBody Author author){
		try {
			Author newAuthor = authorRepository.save(author);
			
			return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Permite crear una nueva categoría")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "201", description = "Categoría creada correctamente", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = Category.class)) }),
	  @ApiResponse(responseCode = "500", description = "Error interno", 
	    content = @Content) })
	@PostMapping("/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category){
		try {
			Category newCategory = categoryRepository.save(category);
			
			return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Permite crear un nuevo libro")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "201", description = "Libro creado correctamente", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = Book.class)) }),
	  @ApiResponse(responseCode = "500", description = "Error interno", 
	    content = @Content) })
	@PostMapping("/book")
	public ResponseEntity<Book> createBook(@RequestBody Book book){
		try {
			Book newBook = bookRepository.save(book);
			
			return new ResponseEntity<>(newBook, HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Retorna una lista de todos los libros disponibles en la biblioteca")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Lista con libros disponibles", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = Book.class)) }),
	  @ApiResponse(responseCode = "500", description = "Invalid id supplied", 
	    content = @Content)})
	@GetMapping("/books/enabled")
	public ResponseEntity<List<Book>> getAllEnabledBooks() {
		try {
			
			List<Book> enabledBooks = new ArrayList<>();
			
			bookRepository.findByEnabledTrue().forEach(enabledBooks::add);
			
			return enabledBooks.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT): new ResponseEntity<>(enabledBooks, HttpStatus.OK) ;
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Permite reservar un libro")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Libro reservado Correctamente", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = Book.class)) }),
	  @ApiResponse(responseCode = "500", description = "Invalid id supplied", 
	    content = @Content)})
	@PutMapping("/reserva/{id}")
	public ResponseEntity<String> reservingBook (Long id){
		try {
			Optional<Book> book = bookRepository.findById(id);
			if (book.isPresent()) {
				Book bookReserved = book.get();
				bookReserved.setEnabled(false);
				
				bookRepository.save(bookReserved);
				
				return new ResponseEntity<String>("El libro se reservó correctamente", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("El libro no existe", HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findByField")
	public ResponseEntity<List<Book>> findByFiels(@RequestParam(name = "title", required = false) String title, 
			@RequestParam(name = "author", required = false) String author, @RequestParam(name = "category", required = false) String category){
		try {
			
			List<Book> books = new ArrayList<>();
			
			bookRepository.findAll().forEach(books::add);;
			
			if (Objects.nonNull(author)) {
				books = books.stream().filter(book -> book.getAuthor().getLastName().equalsIgnoreCase(author)).toList();
			}
			if(Objects.nonNull(title)) {
				books = books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).toList();
			}
			if(Objects.nonNull(category)) {
				books = books.stream().filter(book -> book.getCategory().getDescription().equalsIgnoreCase(category)).toList();
			}
			
			return books.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT): new ResponseEntity<>(books, HttpStatus.OK) ;
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
