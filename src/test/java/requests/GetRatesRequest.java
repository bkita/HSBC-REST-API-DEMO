package requests;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetRatesRequest extends Request {

    public static Response sendGetRequest(String endpoint) {
        return given()
                .when()
                .get(BASE_URL + endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response sendGetWithQueryParamsRequest(String endpoint, Map<String, String> queryParams) {
        return given()
                .queryParams(queryParams)
                .when()
                .get(BASE_URL + endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
