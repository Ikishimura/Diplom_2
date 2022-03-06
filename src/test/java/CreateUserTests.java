import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateUserTests {

    public User user;
    public Requests requests;

    @Before
    public void setUp(){
        requests=new Requests();
    }

    //Пользователей не чищу т.к не нашел апи для их удаления :(

    @DisplayName("Create new user")
    @Test
    public void createNewUser(){
        int statusCode = requests.requestCreateUser(User.getRandomUser()).extract().statusCode();
        boolean success = requests.requestCreateUser(User.getRandomUser()).extract().path("success");

        assertEquals(200, statusCode);
        assertTrue(success);
    }
    @DisplayName("Create an existing user")
    @Test
    public void createAnExistingUser(){
        user = User.getRandomUser();

        int crateUser = requests.requestCreateUser(user).extract().statusCode();
        int statusCode = requests.requestCreateUser(user).extract().statusCode();
        boolean success = requests.requestCreateUser(user).extract().path("success");

        assertEquals(403, statusCode);
        assertFalse(success);
    }
    @DisplayName("Create user without field")
    @Test
    public void createUserWithoutField(){
        user = User.getUserWithoutEmail();

        int statusCode = requests.requestCreateUser(user).extract().statusCode();
        boolean success = requests.requestCreateUser(user).extract().path("success");

        assertFalse(success);
        assertEquals(403,statusCode);
    }

}
