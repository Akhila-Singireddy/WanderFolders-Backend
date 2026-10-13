package com.wander.backend;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WanderFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String name;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 10000)
    private String description;

    @Column(length = 10000)
    private String itinerary;

    @Column(length = 10000)
    private String memories;

    @Column(length = 10000)
    private String mood;
    
    private Double expenses;
    
    @Column(length = 1000000)
    private String imageUrl;

    @Column(length = 1000000)
    private String cover;
}
