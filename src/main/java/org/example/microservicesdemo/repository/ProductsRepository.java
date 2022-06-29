package org.example.microservicesdemo.repository;

import org.example.microservicesdemo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product,String> {
}
