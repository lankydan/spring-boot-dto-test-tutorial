package lankydan.tutorial.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonRestController.class)
public class PersonRestControllerJsonTest {

  @Autowired private MockMvc mockMvc;

  private static final String PERSON_DTO_JSON =
      "{"
          + "\"firstName\":\"First name\","
          + "\"secondName\":\"Second name\","
          + "\"dateOfBirth\":\"01/12/2020\","
          + "\"profession\":\"Professional time waster\","
          + "\"salary\":0"
          + "}";

  private static final String PERSON_DTO_REQUEST_PARAMETER = "personDTO=" + PERSON_DTO_JSON;

  private static final String PERSON_DTO_2_JSON =
      "{"
          + "\"firstName\":\"Second Person First name\","
          + "\"secondName\":\"Second Person Second name\","
          + "\"dateOfBirth\":\"11/01/2017\","
          + "\"profession\":\"Useless Person\","
          + "\"salary\":0"
          + "}";

  private static final String PERSON_DTO_2_REQUEST_PARAMETER = "personDTO2=" + PERSON_DTO_2_JSON;

  private static final String GET_PERSON_DTO_LIST_JSON_TO_RETURN =
      "[ " + PERSON_DTO_JSON + "," + PERSON_DTO_2_JSON + "]";

  private static final String GET_PEOPLE_DTO_JSON_TO_RETURN =
      "{ people:[" + PERSON_DTO_JSON + "," + PERSON_DTO_2_JSON + "]}";

  @Test
  public void getPersonDTOReturnsCorrectJson() throws Exception {
    mockMvc
        .perform(get("/getPersonDTO?" + PERSON_DTO_REQUEST_PARAMETER))
        .andExpect(status().isOk())
        .andExpect(content().json(PERSON_DTO_JSON));
  }

  @Test
  public void getPersonDTOListReturnsCorrectJson() throws Exception {
    mockMvc
        .perform(
            get(
                "/getPersonDTOList?"
                    + PERSON_DTO_REQUEST_PARAMETER
                    + "&"
                    + PERSON_DTO_2_REQUEST_PARAMETER))
        .andExpect(status().isOk())
        .andExpect(content().json(GET_PERSON_DTO_LIST_JSON_TO_RETURN));
  }

  @Test
  public void getPeopleDTOReturnsCorrectJson() throws Exception {
    mockMvc
        .perform(
            get(
                "/getPeopleDTO?"
                    + PERSON_DTO_REQUEST_PARAMETER
                    + "&"
                    + PERSON_DTO_2_REQUEST_PARAMETER))
        .andExpect(status().isOk())
        .andExpect(content().json(GET_PEOPLE_DTO_JSON_TO_RETURN));
  }
}
