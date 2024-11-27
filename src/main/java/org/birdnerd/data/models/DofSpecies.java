package org.birdnerd.data.models;

import java.util.Objects;

public class DofSpecies {

  private String euring;
  private int sortering;
  private String artnavn;
  private String latin;
  private String english;
  private String status;
  private String type;

  public String getEuring() {
    return euring;
  }

  public void setEuring(String euring) {
    this.euring = euring;
  }

  public int getSortering() {
    return sortering;
  }

  public void setSortering(int sortering) {
    this.sortering = sortering;
  }

  public String getArtnavn() {
    return artnavn;
  }

  public void setArtnavn(String artnavn) {
    this.artnavn = artnavn;
  }

  public String getLatin() {
    return latin;
  }

  public void setLatin(String latin) {
    this.latin = latin;
  }

  public String getEnglish() {
    return english;
  }

  public void setEnglish(String english) {
    this.english = english;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

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
