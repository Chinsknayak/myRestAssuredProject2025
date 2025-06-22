package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.AuthPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuthEndPoints {

	//for authorization
	
	public static String generateToken() {
        AuthPayload credentials = new AuthPayload("admin", "password123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("https://restful-booker.herokuapp.com/auth");

        response.then().log().all();

        return response.jsonPath().getString("token");
    }
}
