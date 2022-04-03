import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
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

    @DisplayName("Create new user")
    @Test
    public void createNewUser(){
        user = User.getRandomUser();
        User user2 = User.getRandomUser();

        int statusCode =requests.requestCreateUser(user2).extract().statusCode();
        boolean success = requests.requestCreateUser(user).extract().path("success");

        String getToken = requests.requestLogin(user).extract().path("accessToken");
        String getToken2= requests.requestLogin(user2).extract().path("accessToken");
        StringBuilder formatToken = new StringBuilder(getToken);
        Tokens.setAccessToken(formatToken.delete(0, 7).toString());

        StringBuilder formatToken2 = new StringBuilder(getToken2);

        assertEquals(200,statusCode);
        assertTrue(success);
        requests.requestDelete(Tokens.getAccessToken());
        requests.requestDelete(formatToken2.delete(0, 7).toString());
    }
    @DisplayName("Create an existing user")
    @Test
    public void createAnExistingUser(){
        user = User.getRandomUser();

        String crateUser = requests.requestCreateUser(user).extract().path("accessToken");

        StringBuilder formatToken = new StringBuilder(crateUser);
        Tokens.setAccessToken(formatToken.delete(0, 7).toString());

        int statusCode = requests.requestCreateUser(user).extract().statusCode();
        boolean success = requests.requestCreateUser(user).extract().path("success");

        assertEquals(403, statusCode);
        assertFalse(success);
        requests.requestDelete(Tokens.getAccessToken());
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
