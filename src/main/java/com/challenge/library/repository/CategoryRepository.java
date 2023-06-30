package com.challenge.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.library.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
