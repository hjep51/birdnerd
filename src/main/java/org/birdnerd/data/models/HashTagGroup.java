package org.birdnerd.data.models;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.birdnerd.data.enums.SpeciesCategory;

@Getter
@Setter
@Entity
public class HashTagGroup extends AbstractEntity {

  private String name;
  private String description;

  @Enumerated(EnumType.STRING)
  private SpeciesCategory speciesCategory;

  @OneToMany(mappedBy = "hashTagGroup", fetch = FetchType.EAGER)
  private Set<HashTag> hashTags;
}
