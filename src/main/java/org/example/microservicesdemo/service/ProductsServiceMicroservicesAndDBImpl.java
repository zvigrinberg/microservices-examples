package org.example.microservicesdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.microservicesdemo.domain.Product;
import org.example.microservicesdemo.domain.ProductInstanceDto;
import org.example.microservicesdemo.utils.PropertiesHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductsServiceMicroservicesAndDBImpl implements ProductsService {

    private final RestTemplate restTemplate;
    private final PropertiesHelper propertiesHelper;


    @Override
    public ProductInstanceDto getProductByOrder(String orderNumber) {
        ProductInstanceDto productInstanceDto = new ProductInstanceDto();
         Map response = invokeOrdersMSForGet(orderNumber);
         return productInstanceDto;
    }


    @Override
    public ProductInstanceDto processOrder(Product product, String customerId, String customerName) {
        return null;
    }


    private Map invokeOrdersMSForGet(String orderNumber) {
        ResponseEntity<Map> responseBody = restTemplate.getForEntity(propertiesHelper.returnUrlOrders() + "/" + orderNumber, Map.class);
        Map body = responseBody.getBody();
        return body;
    }
}
