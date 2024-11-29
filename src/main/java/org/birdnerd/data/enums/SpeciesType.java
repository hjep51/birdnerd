package org.birdnerd.data.enums;

import lombok.Getter;

@Getter
public enum SpeciesType {
  SPECIES("Art", "art"),
  SUBSPECIES("Underart", "underart"),
  HYBRID("Hybrid", "hybrid"),
  UNIDENTIFIED("Ubestemt", "ubestemt"),
  OTHER("Andet", "andre_dyr"),
  UNKNOWN("Ukendt", "ukendt");

  private final String danishName;
  private final String dofName;

  SpeciesType(String danishName, String dofName) {
    this.danishName = danishName;
    this.dofName = dofName;
  }

  public static SpeciesType getByDofName(String dofName) {
    for (SpeciesType speciesType : values()) {
      if (speciesType.getDofName().equals(dofName)) {
        return speciesType;
      }
    }
    return SpeciesType.UNKNOWN;
  }
}
