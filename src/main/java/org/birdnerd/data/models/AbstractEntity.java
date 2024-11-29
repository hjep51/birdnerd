package org.birdnerd.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
  // The initial value is to account for data.sql demo data ids
  @SequenceGenerator(name = "idgenerator", initialValue = 1000)
  private Long id;

  @Version private int version;

  @Override
  public int hashCode() {
    if (getId() != null) {
      return getId().hashCode();
    }
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AbstractEntity that)) {
      return false; // null or not an AbstractEntity class
    }
    if (getId() != null) {
      return getId().equals(that.getId());
    }
    return super.equals(that);
  }
}
