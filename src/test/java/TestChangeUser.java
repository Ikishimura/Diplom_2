import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestChangeUser {
    public User user;
    public Requests requests;

    @Before
    public void setUp(){
        user = User.getRandomUser();
        requests = new Requests();
    }
    @DisplayName("Test change user with authorisation")
    @Test
    public void testChangeUserWithAuthorisation(){
        ChangeUserFields changeUserFields = ChangeUserFields.getRandomChangeFields();

        String crateUser=requests.requestCreateUser(user).extract().path("accessToken");
        StringBuilder formatToken = new StringBuilder(crateUser);
        formatToken.delete(0, 7);

        String token=formatToken.toString();

        int statusCode=requests.requestChangeUser(changeUserFields,token).extract().statusCode();
        assertEquals(200,statusCode);
    }
    @DisplayName("Test change user without authorisation")
    @Test
    public void testChangeUserWithoutAuthorisation(){
        ChangeUserFields changeUserFields = ChangeUserFields.getRandomChangeFields();

        String crateUser=requests.requestCreateUser(user).extract().path("accessToken");

        int statusCode=requests.requestChangeUser(changeUserFields,"").extract().statusCode();
        boolean success = requests.requestChangeUser(changeUserFields,"").extract().path("success");
        assertEquals(401,statusCode);
        assertFalse(success);
    }
}
