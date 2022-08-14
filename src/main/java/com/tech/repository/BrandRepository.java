package com.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

	Brand findByTitle(String title);

}
