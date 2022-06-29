package org.example.microservicesdemo.repository;

import org.example.microservicesdemo.domain.ProductInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInstancesRepository extends JpaRepository<ProductInstance,String> {
}
