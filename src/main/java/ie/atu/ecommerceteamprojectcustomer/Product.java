package ie.atu.ecommerceteamprojectcustomer;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Valid
    @Id
    private String id;

    private String customerName;

    @NotNull(message = "This field can not be left blank")
    private String name;

    @NotNull(message = "Description should be detailed and meaningful")
    private String description;

    @NotNull(message = "You cannot leave the price field blank!")
    @Positive(message = "Price must be a positive value!")
    private double price;

    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;

    @NotNull(message = "You should mark if the item is available or not. If available type 'true', otherwise 'false'")
    @Pattern(regexp = "true|false", message = "Available must be either 'true' or 'false' in lowercase")
    private String available;

    private int quantity;

}
