package br.unitins.tp1.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TipoBetoneiraResourceTest {

    private String tipoValidoJson;
    private String tipoInvalidoJson;

    @BeforeEach
    public void setup() {
        tipoValidoJson = "{\"tipo\":\"HORIZONTAL\"}";
        tipoInvalidoJson = "{\"tipo\":\"INVALID\"}"; // Assuming \"INVALID\" is not a valid enum value
    }

    @Test
    void testGetAll() {
        given()
                .when().get("/tipos-betoneira")
                .then()
                .statusCode(200);
    }

    @Test
    void testFindById_ExistingTipoBetoneira() {
        // First, create a TipoBetoneira
        Long tipoBetoneiraId = createTipoBetoneira(tipoValidoJson);

        given()
                .pathParam("id", tipoBetoneiraId)
                .when().get("/tipos-betoneira/{id}")
                .then()
                .statusCode(200)
                .body("tipo", is("HORIZONTAL"));
    }

    @Test
    void testFindById_NonExistentTipoBetoneira() {
        given()
                .pathParam("id", 9999)
                .when().get("/tipos-betoneira/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testCreate_ValidData() {
        given()
                .contentType(ContentType.JSON)
                .body(tipoValidoJson)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(201)
                .body("tipo", is("HORIZONTAL"));
    }

    @Test
    void testCreate_InvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body(tipoInvalidoJson)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(500); // Or appropriate error code for invalid enum
    }

    @Test
    void testUpdate_ExistingTipoBetoneira() {
        // First, create a TipoBetoneira
        Long tipoBetoneiraId = createTipoBetoneira(tipoValidoJson);

        String updateJson = "{\"tipo\":\"VERTICAL\"}";

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", tipoBetoneiraId)
                .body(updateJson)
                .when().put("/tipos-betoneira/{id}")
                .then()
                .statusCode(200)
                .body("tipo", is("VERTICAL"));
    }

    @Test
    void testUpdate_NonExistentTipoBetoneira() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 9999)
                .body(tipoValidoJson)
                .when().put("/tipos-betoneira/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testDelete_ExistingTipoBetoneira() {
        // First, create a TipoBetoneira
        Long tipoBetoneiraId = createTipoBetoneira(tipoValidoJson);

        given()
                .pathParam("id", tipoBetoneiraId)
                .when().delete("/tipos-betoneira/{id}")
                .then()
                .statusCode(204);

        // Verify deletion (optional, but good practice)
        given()
                .pathParam("id", tipoBetoneiraId)
                .when().get("/tipos-betoneira/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testDelete_NonExistentTipoBetoneira() {
        given()
                .pathParam("id", 9999)
                .when().delete("/tipos-betoneira/{id}")
                .then()
                .statusCode(404);
    }

    // Helper method to create a TipoBetoneira
    private Long createTipoBetoneira(String jsonBody) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(201)
                .extract().response();

        return response.jsonPath().getLong("id");
    }

    @Test
    void testTesteInserirTipoBetoneira() {
        given()
                .when().post("/tipos-betoneira/teste/inserir")
                .then()
                .statusCode(201);
    }

    @Test
    void testTesteBuscarTodosTiposBetoneira() {
        given()
                .when().get("/tipos-betoneira/teste/buscar-todos")
                .then()
                .statusCode(200);
    }
}