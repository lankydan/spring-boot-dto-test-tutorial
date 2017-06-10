package lankydan.tutorial.springboot.dto;

import java.util.List;

public class PeopleDTO {

  private List<PersonDTO> people;

  public PeopleDTO() {}

  public PeopleDTO(List<PersonDTO> people) {
    this.people = people;
  }

  public List<PersonDTO> getPeople() {
    return people;
  }

  public void setPeople() {
    this.people = people;
  }
}
