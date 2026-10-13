package com.wander.backend;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class WanderFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 3000)
    private String itinerary;

    @Column(length = 3000)
    private String memories;

    private Double expenses;
    private String imageUrl;
}