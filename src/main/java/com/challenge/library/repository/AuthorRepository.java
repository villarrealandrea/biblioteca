package com.challenge.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.library.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
