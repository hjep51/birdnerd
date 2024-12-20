package org.birdnerd.data;

import java.util.List;
import org.birdnerd.data.models.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SpeciesRepository
    extends JpaRepository<Species, Long>, JpaSpecificationExecutor<Species> {

  @Query(
      "select s from Species s where s.firstObservation is not null and s.imageFileName != '' order by s.danishName")
  List<Species> findByFirstObservationIsNotNullAndImageFileNameIsNotEmpty();
}
