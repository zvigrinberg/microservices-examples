package org.example.microservicesdemo.domain;

import lombok.*;

@Data
@NoArgsConstructor
public class ProductInstanceDto extends ProductInstance{
    private String orderNumber;
    private OrderStatus orderStatus;
    private String productDescription;
}
