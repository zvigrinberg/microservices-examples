package org.example.microservicesdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class ProductInstance {
    @Id
    private String productInstanceId;
    private String productSN;
    private String productCode;
    private String productName;
    private String category;
    private double grantedPrice;
    private double marketPrice;
    private String customerId;
    private String customerName;

}
