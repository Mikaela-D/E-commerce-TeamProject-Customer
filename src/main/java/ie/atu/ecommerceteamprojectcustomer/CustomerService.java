package ie.atu.ecommerceteamprojectcustomer;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public CustomerService(CustomerRepository customerRepository, RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        rabbitTemplate.convertAndSend("customerQueue", customer);
        System.out.println("Saved customer details: " + customer);
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_ROUTING_KEY, customer);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, Customer customer) {
    /*    return customerRepository.findById(id).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setAddress(updatedCustomer.getAddress());
            return customerRepository.save(customer);
        });*/
        Optional<Customer> queryCustomer = customerRepository.findById(id);
        if(!queryCustomer.isPresent()){
            throw new RuntimeException("This customer ID does not exist");
        }
        Customer updatedCustomer = queryCustomer.get();

        updatedCustomer.setPaymentId(customer.getPaymentId());
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setAddress(customer.getAddress());
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_ROUTING_KEY, customer);
        return customerRepository.save(updatedCustomer);
    }

    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}