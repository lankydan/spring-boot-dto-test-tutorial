package lankydan.tutorial.springboot;

import lankydan.tutorial.springboot.dto.PersonDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RunWith(SpringRunner.class)
public class PersonDTOJsonTest {

  @Autowired private JacksonTester<PersonDTO> json;

  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("dd/MM/yyyy"));

  private static final String FIRST_NAME = "First name";
  private static final String SECOND_NAME = "Second name";
  private static final String DATE_OF_BIRTH_STRING = "01/12/2020";
  private static final Date DATE_OF_BIRTH = parseDate(DATE_OF_BIRTH_STRING);
  private static final String PROFESSION = "Professional time waster";
  private static final BigDecimal SALARY = BigDecimal.ZERO;

  private static final String JSON_TO_DESERIALIZE =
      "{\"firstName\":\""
          + FIRST_NAME
          + "\",\"secondName\":\""
          + SECOND_NAME
          + "\",\"dateOfBirth\":\""
          + DATE_OF_BIRTH_STRING
          + "\",\"profession\":\""
          + PROFESSION
          + "\",\"salary\":"
          + SALARY
          + "}";

  private PersonDTO personDTO;

  private static Date parseDate(final String dateString) {
    try {
      return simpleDateFormat.parse(dateString);
    } catch (final ParseException e) {
      return new Date();
    }
  }

  @Before
  public void setup() throws ParseException {
    personDTO = new PersonDTO(FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH, PROFESSION, SALARY);
  }

  @Test
  public void firstNameSerializes() throws IOException {
    assertThat(this.json.write(personDTO))
        .extractingJsonPathStringValue("@.firstName")
        .isEqualTo(FIRST_NAME);
  }

  @Test
  public void secondNameSerializes() throws IOException {
    assertThat(this.json.write(personDTO))
        .extractingJsonPathStringValue("@.secondName")
        .isEqualTo(SECOND_NAME);
  }

  @Test
  public void dateOfBirthSerializes() throws IOException, ParseException {
    assertThat(this.json.write(personDTO))
        .extractingJsonPathStringValue("@.dateOfBirth")
        .isEqualTo(DATE_OF_BIRTH_STRING);
  }

  @Test
  public void professionSerializes() throws IOException {
    assertThat(this.json.write(personDTO))
        .extractingJsonPathStringValue("@.profession")
        .isEqualTo(PROFESSION);
  }

  @Test
  public void salarySerializes() throws IOException {
    assertThat(this.json.write(personDTO))
        .extractingJsonPathNumberValue("@.salary")
        .isEqualTo(SALARY.intValue());
  }

  @Test
  public void firstNameDeserializes() throws IOException {
    assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getFirstName()).isEqualTo(FIRST_NAME);
  }

  @Test
  public void secondNameDeserializes() throws IOException {
    assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getSecondName()).isEqualTo(SECOND_NAME);
  }

  @Test
  public void dateOfBirthDeserializes() throws IOException {
    assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getDateOfBirth())
        .isEqualTo(DATE_OF_BIRTH);
  }

  @Test
  public void professionDeserializes() throws IOException {
    assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getProfession()).isEqualTo(PROFESSION);
  }

  @Test
  public void salaryDeserializes() throws IOException {
    assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getSalary()).isEqualTo(SALARY);
  }
}
