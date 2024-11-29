package org.birdnerd.data.models;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DofSpecies {

  private String euring;
  private int sortering;
  private String artnavn;
  private String latin;
  private String english;
  private String status;
  private String type;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DofSpecies that = (DofSpecies) o;
    return sortering == that.sortering
        && Objects.equals(euring, that.euring)
        && Objects.equals(artnavn, that.artnavn)
        && Objects.equals(latin, that.latin)
        && Objects.equals(english, that.english)
        && Objects.equals(status, that.status)
        && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(euring, sortering, artnavn, latin, english, status, type);
  }

  @Override
  public String toString() {
    return "DofSpecies{"
        + "euring='"
        + euring
        + '\''
        + ", sortering="
        + sortering
        + ", artnavn='"
        + artnavn
        + '\''
        + ", latin='"
        + latin
        + '\''
        + ", english='"
        + english
        + '\''
        + ", status='"
        + status
        + '\''
        + ", type='"
        + type
        + '\''
        + '}';
  }
}
