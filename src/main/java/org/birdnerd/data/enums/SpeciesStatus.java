package org.birdnerd.data.enums;

public enum SpeciesStatus {
  A("A", "A"),
  AS("AS", "AS"),
  AU("AU", "AU"),
  BU("BU", "BU"),
  C("C", "C"),
  CU("CU", "CU"),
  D("D", "D"),
  DU("DU", "DU"),
  E("E", "E"),
  EU("EU", "EU"),
  H("H", "H"),
  HS("HS", "HS"),
  HU("HU", "HU"),
  U("U", "U"),
  X("X", "X"),
  UNKNOWN("Ukendt", "ukendt");

  private final String danishName;
  private final String dofName;

  SpeciesStatus(String danishName, String dofName) {
    this.danishName = danishName;
    this.dofName = dofName;
  }

  public String getDanishName() {
    return danishName;
  }

  public String getDofName() {
    return dofName;
  }

  public static SpeciesStatus getByDofName(String dofName) {
    for (SpeciesStatus speciesStatus : values()) {
      if (speciesStatus.getDofName().equals(dofName)) {
        return speciesStatus;
      }
    }
    return SpeciesStatus.UNKNOWN;
  }

  public static SpeciesStatus getByDanishName(String danishName) {
    for (SpeciesStatus speciesStatus : values()) {
      if (speciesStatus.getDanishName().equals(danishName)) {
        return speciesStatus;
      }
    }
    return SpeciesStatus.UNKNOWN;
  }
}
