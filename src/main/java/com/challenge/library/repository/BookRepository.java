package com.challenge.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.library.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	List<Book> findByEnabledTrue();
	
}