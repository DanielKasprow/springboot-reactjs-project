package com.example.demo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository {

    @Query("From Product where name like %:searchText% ORDER BY name asc")
    Page<Product> findAll(Pageable page, @Param("searchText") String searchText);


}
