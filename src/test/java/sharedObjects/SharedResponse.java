package sharedObjects;

import io.restassured.response.Response;
import lombok.Data;

@Data
public class SharedResponse {
    private Response response;
}