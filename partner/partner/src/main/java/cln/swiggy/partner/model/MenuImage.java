package cln.swiggy.partner.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MenuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private String image;

}
