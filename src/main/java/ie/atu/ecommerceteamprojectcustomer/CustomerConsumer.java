package ie.atu.ecommerceteamprojectcustomer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "consumer.enabled", havingValue = "true")
public class CustomerConsumer {
    @RabbitListener(queues = "customerQueue")
    public void processProductMessage(Customer customer) {
        System.out.println("Received message: " + customer.toString());
    }
}
