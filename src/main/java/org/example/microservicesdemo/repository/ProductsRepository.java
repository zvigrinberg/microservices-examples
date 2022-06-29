package org.example.microservicesdemo.repository;

import org.example.microservicesdemo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product,String> {
}
