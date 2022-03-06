import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ChangeUserFields {
    public final String email;
    public final String name;

    public ChangeUserFields(String email, String name) {
        this.email = email;
        this.name = name;
    }
    public static ChangeUserFields getRandomChangeFields() {
        final String email = RandomStringUtils.randomAlphabetic(10)+"@mail.ru";
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new ChangeUserFields(email, name);

    }
}
