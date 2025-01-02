package ie.atu.ecommerceteamprojectcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ECommerceTeamProjectCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceTeamProjectCustomerApplication.class, args);
    }

}