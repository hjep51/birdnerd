package org.birdnerd.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.birdnerd.data.enums.SpeciesCategory;
import org.birdnerd.data.enums.SpeciesStatus;
import org.birdnerd.data.enums.SpeciesType;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Species extends AbstractEntity {

    private String danishName;
    private String latinName;
    private String englishName;
    private String euringCode;
    @Enumerated(EnumType.STRING)
    private SpeciesCategory category;
    @Enumerated(EnumType.STRING)
    private SpeciesType type;
    @Enumerated(EnumType.STRING)
    private SpeciesStatus status;
    private LocalDate firstObservation;
    private String imageFileName;

}