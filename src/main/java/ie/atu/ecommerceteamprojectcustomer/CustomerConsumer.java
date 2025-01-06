package ie.atu.ecommerceteamprojectcustomer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "consumer.enabled", havingValue = "true")
public class CustomerConsumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void processProductMessage(Product product) {
        System.out.println("Received message: " + product.toString());
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void processProductMessage(Payment payment) {
        System.out.println("Received message: " + payment.toString());
    }
}