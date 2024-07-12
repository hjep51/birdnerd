package org.birdnerd.data;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.birdnerd.data.enums.SpeciesCategory;
import org.birdnerd.data.enums.SpeciesStatus;
import org.birdnerd.data.enums.SpeciesType;

import java.time.LocalDate;

@Entity
public class Species extends AbstractEntity{

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

    public String getDanishName() {
        return danishName;
    }

    public void setDanishName(String danishName) {
        this.danishName = danishName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getEuringCode() {
        return euringCode;
    }

    public void setEuringCode(String euringCode) {
        this.euringCode = euringCode;
    }

    public SpeciesCategory getCategory() {
        return category;
    }

    public void setCategory(SpeciesCategory category) {
        this.category = category;
    }

    public SpeciesType getType() {
        return type;
    }

    public void setType(SpeciesType type) {
        this.type = type;
    }

    public SpeciesStatus getStatus() {
        return status;
    }

    public void setStatus(SpeciesStatus status) {
        this.status = status;
    }

    public LocalDate getFirstObservation() {
        return firstObservation;
    }

    public void setFirstObservation(LocalDate firstObservation) {
        this.firstObservation = firstObservation;
    }
}
