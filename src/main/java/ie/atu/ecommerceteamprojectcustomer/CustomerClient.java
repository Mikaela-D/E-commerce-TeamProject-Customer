package ie.atu.ecommerceteamprojectcustomer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name = "products", url = "http://localhost:8080/products")
public interface CustomerClient {
    @GetMapping("/received")
    String connectionConfirmation();
}