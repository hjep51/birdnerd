package org.birdnerd.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.birdnerd.data.enums.SpeciesCategory;

@Getter
@Setter
@Entity
public class HashTagGroup extends AbstractEntity {

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private SpeciesCategory speciesCategory;

}
