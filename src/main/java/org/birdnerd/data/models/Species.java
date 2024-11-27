package org.birdnerd.data.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.birdnerd.data.enums.SpeciesCategory;
import org.birdnerd.data.enums.SpeciesStatus;
import org.birdnerd.data.enums.SpeciesType;

@Entity
@Getter
@Setter
public class Species extends AbstractEntity {

  private String danishName;
  private String latinName;
  private String englishName;
  private String euringCode;

  @Enumerated(EnumType.STRING)
  private SpeciesCategory category;

  @Enumerated(EnumType.STRING)
  private SpeciesType type;

  @Enumerated(EnumType.STRING)
  private SpeciesStatus status;

  private LocalDate firstObservation;
  private String imageFileName;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<HashTagGroup> hashTagGroups;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<HashTag> hashTags;
}
