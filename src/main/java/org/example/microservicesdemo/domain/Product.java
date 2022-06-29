package org.example.microservicesdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    private String productCode;
    private String productName;
    private String category;
    private double marketPrice;

}
