package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import sharedObjects.SharedEndpoint;
import sharedObjects.SharedResponse;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static requests.GetRatesRequest.sendGetRequest;
import static requests.GetRatesRequest.sendGetWithQueryParamsRequest;
import static utils.DateUtil.*;
import static utils.DateParser.*;

public class FxStepsDef {
    private SharedEndpoint sharedEndpoint;
    private SharedResponse sharedResponse;

    public FxStepsDef(SharedResponse sharedResponse, SharedEndpoint sharedEndpoint) {
        this.sharedEndpoint = sharedEndpoint;
        this.sharedResponse = sharedResponse;
    }

    @Given("User specifies an endpoint {string}")
    public void user_specifies_an_endpoint(String endpoint) {
        sharedEndpoint.setEndpoint(endpoint);
    }

    @Given("User sends a GET request with query params")
    public void user_sends_a_get_request_with_query_params(DataTable requestData) {
        String endpoint = sharedEndpoint.getEndpoint();
        List<Map<String, String>> paramsList = requestData.asMaps(String.class, String.class);

        for (int i = 0; i < paramsList.size(); i++) {
            Map<String, String> paramsFromUser = new HashMap<>();
            paramsFromUser.put("base", paramsList.get(0).get("base"));
            paramsFromUser.put("symbols", paramsList.get(0).get("symbols"));
            sharedResponse.setResponse(sendGetWithQueryParamsRequest(endpoint, paramsFromUser));
        }
    }

    @Given("User specifies a date {string}")
    public void user_specifies_a_date(String date) {
        LocalDate dateFromCucumber = parseDateFromCucumber(date);
        user_specifies_an_endpoint(dateFromCucumber.toString());
    }

    @When("User sends a GET request")
    public void user_sends_a_get_request() {
        sharedResponse.setResponse(sendGetRequest(sharedEndpoint.getEndpoint()));
    }

    @Then("User verifies status code is {int}")
    public void user_verifies_status_code_is(Integer statusCode) {
        assertThat(getStatusCode()).isEqualTo(statusCode);
    }

    @Then("User verifies response contains {string}")
    public void user_verifies_response_contains(String date) {
        LocalDate dateFromCucumber = parseDateFromCucumber(date);
        assertThat(getResponse().getString("date")).isIn(getCurrentDateRange(dateFromCucumber));
    }

    @And("User verifies response contains base {string}")
    public void user_verifies_response_contains_base(String base) {
        assertThat(getResponse().getString("base")).isEqualTo(base);
    }

    @And("User verifies response contains positive rates")
    public void user_verifies_response_contains_positive_rates() {
        Map<String, Float> rates = getResponse().getMap("rates");
        assertThat(rates.values()).allMatch(rate -> rate >= 0);
    }

    @And("User verifies response contains {int} rates")
    public void user_verifies_response_contains_given_number_of_rates(Integer numberOfRates) {
        Map<String, Float> rates = getResponse().getMap("rates");
        assertThat(rates.size()).isEqualTo(numberOfRates);
    }

    private JsonPath getResponse() {
        return sharedResponse.getResponse().jsonPath();
    }

    private Integer getStatusCode() {
        return sharedResponse.getResponse().statusCode();
    }
}
