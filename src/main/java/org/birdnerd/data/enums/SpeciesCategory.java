package org.birdnerd.data.enums;

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

  public String getDanishName() {
    return danishName;
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
