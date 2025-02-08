package projectPS.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import projectPS.dto.food.FoodDishResponseDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodDishControllerIT {

    private static String API_URL = "http://localhost:{PORT}/api/midnight-morsel-diner/foods";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    @Value("${security.invalid-jwt-token}")
    private String invalidJwtToken;

    @Value("${security.admin-jwt-token}")
    private String adminJwtToken;

    @BeforeEach
    public void setUp() {
        API_URL = API_URL.replace("{PORT}", port.toString());
    }

    @Test
    void givenInvalidJwtToken_whenGetByIdIsCalled_thenReturnAccessForbidden() {
        final var foodDishId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        final var headers = getHeadersEntity(invalidJwtToken);

        final var response = testRestTemplate.exchange(
                String.format("%s/%s", API_URL, foodDishId),
                HttpMethod.GET,
                headers,
                FoodDishResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void givenValidFoodDishId_whenGetByIdIsCalled_thenReturnFoodDishResponseDTO() {
        final var foodDishId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        final var headers = getHeadersEntity(adminJwtToken);
        final var expected = getFoodDishResponseDTO();

        final var response = testRestTemplate.exchange(
                String.format("%s/%s", API_URL, foodDishId),
                HttpMethod.GET,
                headers,
                FoodDishResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void givenInvalidFoodDishId_whenGetByIdIsCalled_thenThrowException() {
        final var foodDishID = UUID.fromString("99999999-9999-9999-9999-999999999999");
        final var headers = getHeadersEntity(adminJwtToken);

        final var response = testRestTemplate.exchange(
                String.format("%s/%s", API_URL, foodDishID),
                HttpMethod.GET,
                headers,
                FoodDishResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private HttpEntity<?> getHeadersEntity(String jwtToken) {
        final var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        return new HttpEntity<>(null, headers);
    }

    private FoodDishResponseDTO getFoodDishResponseDTO() {
        return FoodDishResponseDTO.builder()
                .id("00000000-0000-0000-0000-000000000000")
                .name("Butter chicken")
                .price(5.0d)
                .rating(4.5d)
                .build();
    }
}
