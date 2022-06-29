package org.example.microservicesdemo.controller;

import org.example.microservicesdemo.domain.Product;
import org.example.microservicesdemo.domain.ProductInstanceDto;
import org.example.microservicesdemo.service.ProductsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class ProductsController {

    private ProductsService productsService;


    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/{orderNumber}")
    public ProductInstanceDto getProductByOrder(@PathVariable String orderNumber)
    {
           return productsService.getProductByOrder(orderNumber);
    }

    @GetMapping
    public List<ProductInstanceDto> getAllProducts()
    {
        return null;
    }

    @PostMapping
    public ProductInstanceDto orderProduct(@Valid @RequestBody Product product, @RequestParam String customerId, @RequestParam String customerName)
    {
        return productsService.processOrder(product,customerId,customerName);
    }


}
