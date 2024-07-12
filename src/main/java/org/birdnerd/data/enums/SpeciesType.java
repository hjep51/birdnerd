package org.birdnerd.data.enums;

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

    public String getDanishName() {
        return danishName;
    }

    public String getDofName() {
        return dofName;
    }

    public static SpeciesType getByDofName(String dofName) {
        for (SpeciesType speciesType : values()) {
            if (speciesType.getDofName().equals(dofName)) {
                return speciesType;
            }
        }
        return SpeciesType.UNKNOWN;
    }

    public static SpeciesType getByDanishName(String danishName) {
        for (SpeciesType speciesType : values()) {
            if (speciesType.getDanishName().equals(danishName)) {
                return speciesType;
            }
        }
        return SpeciesType.UNKNOWN;
    }
}
