package com.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByTitle(String title);

}
