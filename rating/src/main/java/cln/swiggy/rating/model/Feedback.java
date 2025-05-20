package cln.swiggy.rating.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;
    @NotBlank(message = "Feedback is Required")
    private String feedback;
}
