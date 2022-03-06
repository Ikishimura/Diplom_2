    import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class User {
    public final String email;
    public final String password;
    public final String name;

    public User(String email, String password,String name){
        this.email=email;
        this.name=name;
        this.password=password;
    }
    public String setEmail(String email){
        return this.email;
    }
    public String setPassword(String password){
        return this.password;
    }
    public String setName(String name){
        return this.name;
    }

    public static User getRandomUser (){
        final String email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User (email,password,name);
    }
    public static User getUserWithoutEmail(){
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User (null,password,name);
    }
    public static User getUserWithoutPassword(){
        final String email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User (email,null,name);
    }
    public static User getUserWithoutName(){
        final String email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new User (email,password,null);
    }
}
