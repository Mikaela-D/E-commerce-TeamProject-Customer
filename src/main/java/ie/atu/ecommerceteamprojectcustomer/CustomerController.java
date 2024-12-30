package ie.atu.ecommerceteamprojectcustomer;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")

public class CustomerController {

    private final CustomerService customerService;
    private final CustomerClient customerClient;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerClient customerClient) {
        this.customerService = customerService;
        this.customerClient = customerClient;
    }

    //to check connection with Product service
    @GetMapping("/check-connection")
    public String checkConnection() {
        return customerClient.connectionConfirmation();
    }


    //get all customers list
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    //fetch by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    //Add new customer into the db
    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @Valid @RequestBody Customer customer) {
        /*Optional<Customer> customer = customerService.updateCustomer(id, updatedCustomer);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());*/
        try{
            Customer updatedCustomer = customerService.updateCustomer(id, customer);
            return ResponseEntity.ok(updatedCustomer);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        boolean deleted = customerService.deleteCustomer(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    //To establish connection with payment service (will be called by PaymentClient to display this message and ensure it is connected)
    @GetMapping("/confirm-paymentService")
    public String confirmPaymentService() {
        return "Connected with Customer Service";
    }
}