package org.example.microservicesdemo.service;

import org.example.microservicesdemo.domain.Product;
import org.example.microservicesdemo.domain.ProductInstanceDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductsService {
    ProductInstanceDto getProductByOrder (String orderNumber);
    ProductInstanceDto processOrder(Product product, String customerId, String customerName);


}
