package unit_testing;

import com.mtucholski.reservation.app.model.Address;
import com.mtucholski.reservation.app.model.Clinic;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class AddressTest {

    private Validator validator;
    private Address address;
    Pattern pattern;
    private Set<ConstraintViolation<Address>> violations;

    @Before
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        address = new Address();
    }

    @Test
    public void cityMatchPattern(){

        String cityName = "Kraków";
        String cityNameEnglish = "Los Angeles";
        assertTrue(pattern.matcher(cityName).matches());
        assertTrue(pattern.matcher(cityNameEnglish).matches());

    }

    @Test
    public void  cityDoNotMatchPattern(){

      assertFalse(pattern.matcher("12343#$").matches());
      assertFalse(pattern.matcher("$%#").matches());
      assertFalse(pattern.matcher("Kraków123$#").matches());

    }

    @Test
    public void streetNumberMatchPattern(){

        Pattern streetPattern = Pattern.compile("[A-Z0-9]+");
        assertTrue(streetPattern.matcher("domANIEWSKIEJ").matches());
    }

    @Test
    public void builderPatternWorks(){

        address = getNewAddress();

        assertEquals(address.getCity(), "Kraków");
        assertNotEquals(address.getCity(), ("Miami"));
    }

    @Test
    public void cityNotMatchSize(){

        address = Address.builder()
                .city("Mamajami jest bardzo fajnym miastem i stolicą watykańskiego nieba, poza tym niebo zostaw lotnikom").build();

        violations = validator.validate(address);
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));

        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());
    }

    private Address getNewAddress(){

        return Address.builder()
                .city("Kraków")
                .clinic(new Clinic())
                .streetNumber("19A")
                .zip_code("31-102")
                .build();
    }
}
