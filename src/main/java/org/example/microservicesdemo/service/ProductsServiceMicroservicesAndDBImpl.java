package org.example.microservicesdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.microservicesdemo.domain.Product;
import org.example.microservicesdemo.domain.ProductInstanceDto;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductsServiceMicroservicesAndDBImpl implements ProductsService {

    private final RestTemplate restTemplate;
    @ConfigurationProperties

    @Override
    public ProductInstanceDto getProductByOrder(String orderNumber) {
         String productInstanceId = invokeOrdersMSForGet(orderNumber);


    }


    @Override
    public ProductInstanceDto processOrder(Product product, String customerId, String customerName) {
        return null;
    }


    private String invokeOrdersMSForGet(String orderNumber) {
        restTemplate.getForEntity()
    }
}
