package org.birdnerd.data.enums;

import lombok.Getter;

@Getter
public enum SpeciesCategory {
  BIRD("Fugl"),
  MAMMAL("Pattedyr"),
  INSECT("Insekt"),
  REPTILE("Reptil"),
  AMPHIBIAN("Amfibie"),
  FISH("Fisk"),
  INVERTEBRATE("Hvirvelløse dyr"),
  BUTTERFLY("Sommerfugl"),
  SNAIL("Snegl"),
  SNAKE("Slange"),
  UNKNOWN("Ukendt");

  private final String danishName;

  SpeciesCategory(String danishName) {
    this.danishName = danishName;
  }

  public static SpeciesCategory getByDanishName(String danishName) {
    for (SpeciesCategory speciesCategory : values()) {
      if (speciesCategory.getDanishName().equals(danishName)) {
        return speciesCategory;
      }
    }
    return SpeciesCategory.UNKNOWN;
  }
}
