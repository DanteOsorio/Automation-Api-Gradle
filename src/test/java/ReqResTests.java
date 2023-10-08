import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReqResTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void loginTests() {

        given()
                .body(String.format("{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"cityslicka\" }"))
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue()); // 200;


    }

    @Test
    public void getSingleUserTest() {
        given()
                .get("users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2)); // 200;

    }

    @Test
    public void deleteUserTest() {
        given()
                .delete("/users/2")
                .then()
                .statusCode(204);
                 // 200;

    }

    @Test
    public void patchUserTest() {
        given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .patch(" /users/2" )
                .then()
                .statusCode(200)
                .body("name", equalTo("morpheus"));


    }

    @Test
    public void putUserTest() {
        given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .put(" /users/2" )
                .then()
                .statusCode(200)
                .body("job", equalTo("zion resident"));


    }


}
