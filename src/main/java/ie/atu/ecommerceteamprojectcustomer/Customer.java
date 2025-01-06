package ie.atu.ecommerceteamprojectcustomer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Valid
    @Id
    private String id;

    private String paymentId;

    //@NotBlank(message = "Name is required")
    //@Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    //@Pattern(regexp = "^[A-Za-z ]+$", message = "Name can only have letters and spaces")
    private String name;

    //@NotBlank(message = "Email is required")
    //@Email(message = "Email should be valid")
    private String email;

    //@NotBlank(message = "Address is required")
    //@Size(max = 80, message = "Address must be less than 80 characters")
    //@Pattern(regexp = "^[A-Za-z0-9,\\s]+$", message = "Address can only have letters, numbers, commas, and spaces")
    private String address;
}
