import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestsOrder {
    public Requests requests;
    public Ingredients ingredients;
    public User user;

    @Before
    public void setUp(){
        requests=new Requests();
        ingredients=new Ingredients(List.of("61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa72"));
        user = User.getRandomUser();
    }

    @DisplayName("Create order without authorisation")
    @Test
    public void createOrderWithoutAuthorisation(){
        boolean success = requests.requestCreateOrderWithoutAuthorisation((ingredients)).extract().path("success");
        int statusCode = requests.requestCreateOrderWithoutAuthorisation((ingredients)).extract().statusCode();
        assertTrue(success);
        assertEquals(200,statusCode);
    }
    @DisplayName("Create order with authorisation")
    @Test
    public void createOrderWithAuthorisation(){
        String crateUser =requests.requestCreateUser(user).extract().path("accessToken");

        StringBuilder formatToken = new StringBuilder(crateUser);
        Tokens.setAccessToken(formatToken.delete(0, 7).toString());

        boolean success = requests.requestCreateOrderWithAuthorisation(ingredients,Tokens.getAccessToken()).extract().path("success");
        int statusCode = requests.requestCreateOrderWithAuthorisation(ingredients,Tokens.getAccessToken()).extract().statusCode();

        assertTrue(success);
        assertEquals(200,statusCode);
        requests.requestDelete(Tokens.getAccessToken());
    }
    @DisplayName("Create order without ingredients")
    @Test
    public void createOrderWithoutIngredients(){
        Ingredients ingredientsNull= new Ingredients(null);
        boolean success = requests.requestCreateOrderWithoutAuthorisation(ingredientsNull).extract().path("success");
        int statusCode = requests.requestCreateOrderWithoutAuthorisation(ingredientsNull).extract().statusCode();
        assertFalse(success);
        assertEquals(400,statusCode);
    }
    @DisplayName("Create order with not valid hash")
    @Test
    public void createOrderWithNotValidHash(){
        Ingredients ingredientsNotValidHash = new Ingredients(List.of("123","123","123"));

        int statusCode = requests.requestCreateOrderWithoutAuthorisation(ingredientsNotValidHash).extract().statusCode();

        assertEquals(500,statusCode);
    }
}
