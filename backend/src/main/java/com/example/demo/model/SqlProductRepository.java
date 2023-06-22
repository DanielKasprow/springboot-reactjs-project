package com.example.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface SqlProductRepository  extends ProductRepository, JpaRepository<Product,Long> {
}
