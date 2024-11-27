package org.birdnerd.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class HashTag extends AbstractEntity {

  private String name;
  private int weight;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  private HashTagGroup hashTagGroup;

  public String getAsHashTag() {
    return "#" + name.toLowerCase().replace(" ", "");
  }
}
