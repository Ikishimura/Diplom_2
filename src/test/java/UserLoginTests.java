import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserLoginTests {
    public Requests requests;
    public User user;

    @Before
    public void setUp(){
        requests=new Requests();
    }

    @After
    public void tearDown(){
        requests.requestDelete(Tokens.getAccessToken());
    }
    @DisplayName("Test login user")
    @Test
    public void testLoginUser(){
        user=User.getRandomUser();
        String crateUser = requests.requestCreateUser(user).extract().path("accessToken");

        StringBuilder formatToken = new StringBuilder(crateUser);
        Tokens.setAccessToken(formatToken.delete(0, 7).toString());

        int statusCodeLoginUser = requests.requestLogin(user).extract().statusCode();
        boolean success = requests.requestLogin(user).extract().path("success");

        assertEquals(200,statusCodeLoginUser);
        assertTrue(success);
    }
    @DisplayName("Test login with wrong user name")
    @Test
    public void testLoginWithWrongUserName(){
        user= User.getRandomUser();
        User incorrectLogin= new User(user.email, user.password, "123");

        String crateUser = requests.requestCreateUser(user).extract().path("accessToken");

        StringBuilder formatToken = new StringBuilder(crateUser);
        Tokens.setAccessToken(formatToken.delete(0, 7).toString());

        int statusCodeLoginUser = requests.requestLogin(incorrectLogin).extract().statusCode();
        boolean success = requests.requestLogin(incorrectLogin).extract().path("success");

        assertEquals(200,statusCodeLoginUser); //по документации 401 код только при неверном поле email/password
        assertTrue(success);
    }
    @DisplayName("Test login with wrong password")
    @Test
    public void testLoginWithWrongPassword(){
        user= User.getRandomUser();
        User incorrectLogin= new User(user.email, "123", user.name);

        String crateUser = requests.requestCreateUser(user).extract().path("accessToken");

        StringBuilder formatToken = new StringBuilder(crateUser);
        Tokens.setAccessToken(formatToken.delete(0, 7).toString());

        int statusCodeLoginUser = requests.requestLogin(incorrectLogin).extract().statusCode();
        boolean success = requests.requestLogin(incorrectLogin).extract().path("success");

        assertEquals(401,statusCodeLoginUser);
        assertFalse(success);
    }
}