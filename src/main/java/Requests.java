import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Requests extends BaseSpec{
    public static String api = "/api/";

    @Step
    public ValidatableResponse requestCreateUser(User user){
        return given().
                spec(getBaseSpec()).
                body(user).
                when().
                log().
                all().
                post(api + "auth/register/").
                then().
                log().
                all();
    }
    @Step
    public ValidatableResponse requestLogin(User user){
            return given().
                    spec(getBaseSpec()).
                    body(user).
                    when().
                    log().
                    all().
                    post(api + "auth/login/").
                    then().
                    log().
                    all();
    }
    @Step
    public ValidatableResponse requestChangeUser(ChangeUserFields changeUserFields,String token) {
        return given().
                spec(getBaseSpec()).
                auth().oauth2(token).
                body(changeUserFields).
                when().
                log().
                all().
                patch(api + "auth/user/").
                then().
                log().
                all();
    }
    @Step
    public ValidatableResponse requestGetIngredient(){
        return given().
                spec(getBaseSpec()).
                when().log().all().
                get("/ingredients").
                then().
                log().
                all();
    }
    @Step
    public ValidatableResponse requestCreateOrderWithoutAuthorisation(Ingredients ingredients){
        return given().
                spec(getBaseSpec()).
                body(ingredients).
                when().
                log().
                all().
                post(api + "orders").
                then().
                log().
                all();
    }
    @Step
    public ValidatableResponse requestCreateOrderWithAuthorisation(Ingredients ingredients, String token){
        return given().
                spec(getBaseSpec()).
                auth().oauth2(token).
                body(ingredients).
                when().
                log().
                all().
                post(api + "orders").
                then().
                log().
                all();
    }
    @Step
    public ValidatableResponse requestDelete(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .log()
                .all()
                .delete(api+"auth/user")
                .then()
                .log()
                .all()
                .statusCode(202);
    }
    @Step
    public ValidatableResponse requestGetOrderWithAuthorisation(String token){
        return given().
                spec(getBaseSpec()).
                auth().oauth2(token).
                when().
                log().all().
                get(api + "orders").
                then().
                log().all();
    }
    @Step
    public ValidatableResponse requestGetOrderWithoutAuthorisation(){
        return given().
                spec(getBaseSpec()).
                when().
                log().all().
                get(api + "orders").
                then().
                log().all();
    }
}
