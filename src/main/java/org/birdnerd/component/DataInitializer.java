package org.birdnerd.component;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.birdnerd.data.SpeciesRepository;
import org.birdnerd.data.enums.SpeciesCategory;
import org.birdnerd.data.enums.SpeciesStatus;
import org.birdnerd.data.enums.SpeciesType;
import org.birdnerd.data.models.Species;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitializer {

  private final SpeciesRepository speciesRepository;

  private final ResourceLoader resourceLoader;

  @Value("${birdnerd.doffile}")
  private String DOF_SPECIES_CSV;

  public DataInitializer(SpeciesRepository speciesRepository, ResourceLoader resourceLoader) {
    this.speciesRepository = speciesRepository;
    this.resourceLoader = resourceLoader;
  }

  @PostConstruct
  public void init() {
    if (speciesRepository.count() > 0) {
      log.info("Species data already imported. Skipping import.");
      return; // Skip importing if data exists
    }
    Resource resource = resourceLoader.getResource("classpath:dof-species.csv");
    File csvFile = new File(DOF_SPECIES_CSV);
    try (Scanner scanner = new Scanner(csvFile)) {
      if (scanner.hasNextLine()) { // Skip the first line (header)
        scanner.nextLine();
      }
      int lineCount = 1;
      while (scanner.hasNextLine()) {
        lineCount++;
        String line = scanner.nextLine();
        line = line.replace("\"", ""); // Remove quotes if present
        String[] data = line.split(";");
        if (data.length < 7) { // Check if there are at least 3 parts
          log.warn("Warning: Line({}) does not contain enough parts: {}", lineCount, line);
          continue; // Skip this line
        }
        Species species = new Species();
        species.setEuringCode(data[0]);
        species.setDanishName(data[2]);
        species.setLatinName(data[3]);
        species.setEnglishName(data[4]);
        species.setStatus(SpeciesStatus.getByDofName(data[5]));
        species.setType(SpeciesType.getByDofName(data[6]));
        SpeciesCategory cat =
            SpeciesType.getByDofName(data[6]) == SpeciesType.OTHER
                ? SpeciesCategory.UNKNOWN
                : SpeciesCategory.BIRD;
        species.setCategory(cat);

        speciesRepository.save(species);
      }
    } catch (IOException e) {
      log.error("Error while reading species data from file", e);
    }
  }
}
