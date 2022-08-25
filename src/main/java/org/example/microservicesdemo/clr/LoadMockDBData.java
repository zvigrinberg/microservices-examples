package org.example.microservicesdemo.clr;

import org.example.microservicesdemo.domain.ProductInstance;
import org.example.microservicesdemo.repository.ProductInstancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

//@Component
//@ConditionalOnProperty()
public class LoadMockDBData implements CommandLineRunner {
//    @Autowired
    private ProductInstancesRepository productInstancesRepository;
    @Override
    public void run(String... args) throws Exception {
        ProductInstance productInstance =    ProductInstance
                                            .builder()
                                            .productInstanceId("5")
                                            .productCode("ab105")
                                            .customerId("CUST01842")
                                            .category("Musical Instruments")
                                            .customerName("John Doe")
                                            .grantedPrice(45000d)
                                            .marketPrice(5500d)
                                            .productName("Piano")
                                            .productSN("fdskjf-34hfg4-hs811lj-pklowranb")
                                            .build();

        productInstancesRepository.save(productInstance);
    }
}
