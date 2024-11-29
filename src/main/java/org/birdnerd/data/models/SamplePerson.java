package org.birdnerd.data.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SamplePerson extends AbstractEntity {

  private String firstName;
  private String lastName;
  @Email private String email;
  private String phone;
  private LocalDate dateOfBirth;
  private String occupation;
  private String role;
  private boolean important;
}
