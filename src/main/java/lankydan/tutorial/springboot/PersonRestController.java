package lankydan.tutorial.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lankydan.tutorial.springboot.dto.PeopleDTO;
import lankydan.tutorial.springboot.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@RestController
public class PersonRestController {

  @Autowired private ObjectMapper objectMapper;

  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

  @RequestMapping("/getPersonDTO")
  public PersonDTO getPersonDTO(@RequestParam(value = "personDTO") String jsonPersonDTO)
      throws IOException {
    return getPersonDTOFromJson(jsonPersonDTO);
  }

  private PersonDTO getPersonDTOFromJson(final String jsonPersonDTO) throws IOException {
    return objectMapper.setDateFormat(simpleDateFormat).readValue(jsonPersonDTO, PersonDTO.class);
  }

  @RequestMapping("/getPersonDTOList")
  public List<PersonDTO> getPersonDTOList(
      @RequestParam(value = "personDTO") String jsonPersonDTO,
      @RequestParam(value = "personDTO2") String jsonPersonDTO2)
      throws IOException {
    final PersonDTO personDTO = getPersonDTOFromJson(jsonPersonDTO);
    final PersonDTO personDTO2 = getPersonDTOFromJson(jsonPersonDTO2);
    return Arrays.asList(personDTO, personDTO2);
  }

  @RequestMapping("/getPeopleDTO")
  public PeopleDTO getPeopleDTO(
      @RequestParam(value = "personDTO") String jsonPersonDTO,
      @RequestParam(value = "personDTO2") String jsonPersonDTO2)
      throws IOException {
    final PersonDTO personDTO = getPersonDTOFromJson(jsonPersonDTO);
    final PersonDTO personDTO2 = getPersonDTOFromJson(jsonPersonDTO2);
    return new PeopleDTO(Arrays.asList(personDTO, personDTO2));
  }
}
