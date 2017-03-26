package lankydan.tutorial.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lankydan.tutorial.springboot.dto.PeopleDTO;
import lankydan.tutorial.springboot.dto.PersonDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonRestController.class)
public class PersonRestControllerJsonTestVersion2 {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private JacksonTester<PersonDTO> personDTOJsonTester;
  private JacksonTester<List> listJsonTester;
  private JacksonTester<PeopleDTO> peopleDTOJsonTester;

  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("dd/MM/yyyy"));

  private static final String PERSON_FIRST_NAME = "First name";
  private static final String PERSON_SECOND_NAME = "Second name";
  private static final String PERSON_DATE_OF_BIRTH_STRING = "01/12/2020";
  private static final Date PERSON_DATE_OF_BIRTH = parseDate(PERSON_DATE_OF_BIRTH_STRING);
  private static final String PERSON_PROFESSION = "Professional time waster";
  private static final BigDecimal PERSON_SALARY = BigDecimal.ZERO;

  private static final String PERSON_2_FIRST_NAME = "Second Person First name";
  private static final String PERSON_2_SECOND_NAME = "Second Person Second name";
  private static final String PERSON_2_DATE_OF_BIRTH_STRING = "11/01/2017";
  private static final Date PERSON_2_DATE_OF_BIRTH = parseDate(PERSON_2_DATE_OF_BIRTH_STRING);
  private static final String PERSON_2_PROFESSION = "Useless Person";
  private static final BigDecimal PERSON_2_SALARY = BigDecimal.ZERO;

  private static Date parseDate(final String dateString) {
    try {
      return simpleDateFormat.parse(dateString);
    } catch (final ParseException e) {
      return new Date();
    }
  }

  private PersonDTO personDTO;
  private PersonDTO personDTO2;

  @Before
  public void setup() {
    JacksonTester.initFields(this, objectMapper);
    personDTO =
        new PersonDTO(
            PERSON_FIRST_NAME,
            PERSON_SECOND_NAME,
            PERSON_DATE_OF_BIRTH,
            PERSON_PROFESSION,
            PERSON_SALARY);
    personDTO2 =
        new PersonDTO(
            PERSON_2_FIRST_NAME,
            PERSON_2_SECOND_NAME,
            PERSON_2_DATE_OF_BIRTH,
            PERSON_2_PROFESSION,
            PERSON_2_SALARY);
  }

  @Test
  public void getPersonDTOReturnsCorrectJson() throws Exception {
    final String personDTOJson = personDTOJsonTester.write(personDTO).getJson();
    final String personDTORequestParameter = "personDTO=" + personDTOJson;
    final String outputJson = personDTOJson;
    mockMvc
        .perform(get("/getPersonDTO?" + personDTORequestParameter))
        .andExpect(status().isOk())
        .andExpect(content().json(outputJson));
  }

  @Test
  public void getPersonDTOListReturnsCorrectJson() throws Exception {
    final String personDTORequestParameter =
        "personDTO=" + personDTOJsonTester.write(personDTO).getJson();
    final String personDTO2RequestParameter =
        "personDTO2=" + personDTOJsonTester.write(personDTO2).getJson();
    final String outputJson = listJsonTester.write(Arrays.asList(personDTO, personDTO2)).getJson();
    mockMvc
        .perform(
            get(
                "/getPersonDTOList?"
                    + personDTORequestParameter
                    + "&"
                    + personDTO2RequestParameter))
        .andExpect(status().isOk())
        .andExpect(content().json(outputJson));
  }

  @Test
  public void getPeopleDTOReturnsCorrectJson() throws Exception {
    final String personDTORequestParameter =
        "personDTO=" + personDTOJsonTester.write(personDTO).getJson();
    final String personDTO2RequestParameter =
        "personDTO2=" + personDTOJsonTester.write(personDTO2).getJson();
    final String outputJson =
        peopleDTOJsonTester.write(new PeopleDTO(Arrays.asList(personDTO, personDTO2))).getJson();
    mockMvc
        .perform(
            get("/getPeopleDTO?" + personDTORequestParameter + "&" + personDTO2RequestParameter))
        .andExpect(status().isOk())
        .andExpect(content().json(outputJson));
  }
}
