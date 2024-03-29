package org.example.microservicesdemo.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.microservicesdemo.domain.OrderStatus;
import org.example.microservicesdemo.domain.Product;
import org.example.microservicesdemo.domain.ProductInstance;
import org.example.microservicesdemo.domain.ProductInstanceDto;
import org.example.microservicesdemo.repository.ProductInstancesRepository;
import org.example.microservicesdemo.repository.ProductsRepository;
import org.example.microservicesdemo.utils.PropertiesHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsServiceMicroservicesAndDBImpl implements ProductsService {

    private final RestTemplate restTemplate;
    private final PropertiesHelper propertiesHelper;
    private final ProductsRepository productsRepository;
    private final ProductInstancesRepository productInstancesRepository;
    private ObjectMapper om ;


    @Override
    public ProductInstanceDto getProductByOrder(String orderNumber) {
        ProductInstanceDto productInstanceDto=null;
        ProductInstance productInstance=null;
         log.info("before invoking Orders MS");
         Map response = invokeOrdersMSForGet(orderNumber);
        log.info("After invoking Orders MS, response received: " + response.toString() );
        String orderStatus = (String)response.get("orderStatus");
        String productCode = (String)response.get("productCode");
        String productSN = (String)response.get("productSN");
        String key = productCode + ":" + productSN;
        Optional<ProductInstance> optional = productInstancesRepository.findById(key);
        if(optional.isPresent())
        {
            productInstance = optional.get();
            log.info("After fetching from DB, response received: " + response.toString() );

        }
        else
        {
            log.info("Nothing fetched from DB, aborting...." );
        }
        try {

            String parentClass = om.writeValueAsString(productInstance);
            productInstanceDto = om.readValue(parentClass, ProductInstanceDto.class);
            productInstanceDto.setOrderNumber(orderNumber);
            productInstanceDto.setOrderStatus(OrderStatus.valueOf(orderStatus));
            ResponseEntity<Map> resp = restTemplate.getForEntity(propertiesHelper.returnUrlCaching() + "/" + productInstanceDto.getProductCode(), Map.class);
            String productDescription = (String)resp.getBody().get("productDescription");
            productInstanceDto.setProductDescription(productDescription);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return productInstanceDto;
    }



    @Transactional
    @Override
    public ProductInstanceDto processOrder(Product product, String customerId, String customerName) {
        productsRepository.save(product);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        httpHeaders.add("Accept","application/json");
        RequestEntity requestEntity = new RequestEntity(product,httpHeaders, HttpMethod.POST, URI.create(propertiesHelper.returnUrlOrders() + "/"));
        Map<String, String> customerDetails = Map.of("customerId", customerId, "customerName", customerName);
        ResponseEntity<Map> responseBody = restTemplate.postForEntity(propertiesHelper.returnUrlOrders() ,requestEntity,Map.class,customerDetails);
        Map respBody = responseBody.getBody();
        ProductInstanceDto productInstanceDto = new ProductInstanceDto();
        productInstanceDto.setCustomerId(customerId);
        productInstanceDto.setCustomerName(customerName);
        productInstanceDto.setProductInstanceId(product.getProductCode() + ":" + respBody.get("productSN"));
        productInstanceDto.setProductName(product.getProductName());
        productInstanceDto.setProductCode(product.getProductCode());
        productInstanceDto.setOrderStatus(OrderStatus.valueOf((String)respBody.get("orderStatus")));
        productInstanceDto.setOrderNumber((String)respBody.get("orderNumber"));
        productInstanceDto.setGrantedPrice((Double.parseDouble((String)respBody.get("grantedPrice"))));
        productInstanceDto.setCategory(product.getCategory());
        productInstanceDto.setMarketPrice(product.getMarketPrice());
        productInstanceDto.setProductSN((String)respBody.get("productSN"));
        responseBody = restTemplate.getForEntity(propertiesHelper.returnUrlCaching() + "/" + product.getProductCode() , Map.class);
        Map body = responseBody.getBody();
        productInstanceDto.setProductDescription((String)body.get("productDescription"));

        try {
            String dto = om.writeValueAsString(productInstanceDto);
            ProductInstance productInstance = om.readValue(dto, ProductInstance.class);
            this.productInstancesRepository.save(productInstance);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
         catch (Exception e) {
            log.error("General Error occurred",e );
            e.printStackTrace();
        }
        return productInstanceDto;
    }


    private Map invokeOrdersMSForGet(String orderNumber) {
        ResponseEntity<Map> responseBody = restTemplate.getForEntity(propertiesHelper.returnUrlOrders() + "/" + orderNumber, Map.class);
        Map body = responseBody.getBody();
        return body;
    }

    @PostConstruct
    public void initializeObjectMapper()
    {
        om = new ObjectMapper();
        om.configure(JsonParser.Feature.IGNORE_UNDEFINED,true);
        om.configure(JsonGenerator.Feature.IGNORE_UNKNOWN,true);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    }
}
