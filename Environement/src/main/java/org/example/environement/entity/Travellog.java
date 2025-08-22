package org.example.environement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.entity.enums.TravelMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Travellog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double distanceKm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelMode mode;

    @Column(nullable = false)
    private double estimatedCo2Kg;

    @ManyToOne
    @JoinColumn(name = "observation_id")
    private Observation observation;


    public TravellogDtoResponse entityToDto() {
        return TravellogDtoResponse.builder()
                    .id(id)
                    .distanceKm(distanceKm)
                    .mode(mode.toString())
                    .estimatedCo2Kg(estimatedCo2Kg)
                    .build();
    }

    public void calculateCO2() {
         Double factorEmission = 0.0;

        switch (mode){
            case WALKING: factorEmission = 0.0; break;
            case BIKE: factorEmission = 0.0; break;
            case CAR: factorEmission = 0.22; break;
            case BUS: factorEmission = 0.11; break;
            case TRAIN: factorEmission = 0.03; break;
            case PLANE: factorEmission = 0.259; break;
        }

        this.estimatedCo2Kg = distanceKm * factorEmission;
    }

    }
