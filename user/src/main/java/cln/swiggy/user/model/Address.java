package cln.swiggy.user.model;

import cln.swiggy.user.model.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    @Enumerated(value = EnumType.STRING)
    private AddressType addressType;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    private String state;
    @Column(nullable = false)
    private String pincode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}