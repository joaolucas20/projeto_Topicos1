package br.unitins.tp1.resource;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(FabricanteResource.class)
public class FabricanteResourceTest {

    private Long fabricanteId;
    private FabricanteRequestDTO fabricanteValidoDTO;
    private FabricanteRequestDTO fabricanteInvalidoDTO;

    @BeforeEach
    public void setup() {
        fabricanteValidoDTO = new FabricanteRequestDTO("Novo Fabricante", "98765432109876");
        fabricanteInvalidoDTO = new FabricanteRequestDTO("", "123");

        // Create a Fabricante for other tests to use
        fabricanteId = given()
                .contentType(ContentType.JSON)
                .body(fabricanteValidoDTO)
                .when().post()
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");
    }

    @Test
    public void testGetAll() {
        when().get()
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetById_ExistingFabricante() {
        given()
                .pathParam("id", fabricanteId)
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .body("nome", is("Novo Fabricante"))
                .body("cnpj", is("98765432109876"));
    }

    @Test
    public void testGetById_NonExistentFabricante() {
        given()
                .pathParam("id", 9999)
                .when().get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreate_ValidData() {
        given()
                .contentType(ContentType.JSON)
                .body(fabricanteValidoDTO)
                .when().post()
                .then()
                .statusCode(201)
                .body("nome", is("Novo Fabricante"))
                .body("cnpj", is("98765432109876"));
    }

    @Test
    public void testCreate_InvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body(fabricanteInvalidoDTO)
                .when().post()
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdate_ExistingFabricante() {
        FabricanteRequestDTO updateDTO = new FabricanteRequestDTO("Updated Fabricante", "11223344556677");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", fabricanteId)
                .body(updateDTO)
                .when().put("/{id}")
                .then()
                .statusCode(200)
                .body("nome", is("Updated Fabricante"))
                .body("cnpj", is("11223344556677"));
    }

    @Test
    public void testUpdate_NonExistentFabricante() {
        given()
                .pathParam("id", 9999)
                .body(fabricanteValidoDTO)
                .when().put("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDelete_ExistingFabricante() {
        given()
                .pathParam("id", fabricanteId)
                .when().delete("/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDelete_NonExistentFabricante() {
        given()
                .pathParam("id", 9999)
                .when().delete("/{id}")
                .then()
                .statusCode(404);
    }
}