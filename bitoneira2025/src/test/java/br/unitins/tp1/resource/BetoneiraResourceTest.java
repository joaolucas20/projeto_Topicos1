package br.unitins.tp1.resource;
 
 import br.unitins.tp1.dto.BetoneiraRequestDTO;
 import br.unitins.tp1.model.TipoBetoneiraEnum;
 import io.quarkus.test.common.http.TestHTTPEndpoint;
 import io.quarkus.test.junit.QuarkusTest;
 import io.restassured.http.ContentType;
 import io.restassured.response.Response;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 
 import static io.restassured.RestAssured.given;
 import static org.hamcrest.CoreMatchers.*;
 
 @QuarkusTest
 @TestHTTPEndpoint(BetoneiraResource.class)
 public class BetoneiraResourceTest {
 
     private BetoneiraRequestDTO betoneiraValidaDTO;
     private BetoneiraRequestDTO betoneiraInvalidaDTO;
     private Long fabricanteId = 1L; // Assuming a fabricante with ID 1 exists
 
     @BeforeEach
     public void setup() {
         betoneiraValidaDTO = new BetoneiraRequestDTO();
         betoneiraValidaDTO.setMarca("Marca Teste");
         betoneiraValidaDTO.setModelo("Modelo Teste");
         betoneiraValidaDTO.setCapacidade(10.0);
         betoneiraValidaDTO.setTipo(TipoBetoneiraEnum.HORIZONTAL);
         betoneiraValidaDTO.setFabricanteId(fabricanteId);
 
         betoneiraInvalidaDTO = new BetoneiraRequestDTO();
         betoneiraInvalidaDTO.setMarca("");
         betoneiraInvalidaDTO.setModelo("");
         betoneiraInvalidaDTO.setCapacidade(-10.0);
         betoneiraInvalidaDTO.setTipo(null);
         betoneiraInvalidaDTO.setFabricanteId(null);
     }
 
     @Test
     void testGetAll() {
         given()
                 .when().get()
                 .then()
                 .statusCode(200);
     }
 
     @Test
     void testFindById_ExistingBetoneira() {
         // First, create a Betoneira
         Long betoneiraId = createBetoneira(betoneiraValidaDTO);
 
         given()
                 .pathParam("id", betoneiraId)
                 .when().get("/{id}")
                 .then()
                 .statusCode(200)
                 .body("marca", is("Marca Teste"))
                 .body("modelo", is("Modelo Teste"));
     }
 
     @Test
     void testFindById_NonExistentBetoneira() {
         given()
                 .pathParam("id", 9999)
                 .when().get("/{id}")
                 .then()
                 .statusCode(404);
     }
 
     @Test
     void testCreate_ValidData() {
         given()
                 .contentType(ContentType.JSON)
                 .body(betoneiraValidaDTO)
                 .when().post()
                 .then()
                 .statusCode(201)
                 .body("marca", is("Marca Teste"))
                 .body("modelo", is("Modelo Teste"));
     }
 
     @Test
     void testCreate_InvalidData() {
         given()
                 .contentType(ContentType.JSON)
                 .body(betoneiraInvalidaDTO)
                 .when().post()
                 .then()
                 .statusCode(400);
     }
 
     @Test
     void testUpdate_ExistingBetoneira() {
         // First, create a Betoneira
         Long betoneiraId = createBetoneira(betoneiraValidaDTO);
 
         BetoneiraRequestDTO updateDTO = new BetoneiraRequestDTO();
         updateDTO.setMarca("Marca Atualizada");
         updateDTO.setModelo("Modelo Atualizado");
         updateDTO.setCapacidade(15.0);
         updateDTO.setTipo(TipoBetoneiraEnum.VERTICAL);
         updateDTO.setFabricanteId(fabricanteId);
 
         given()
                 .contentType(ContentType.JSON)
                 .pathParam("id", betoneiraId)
                 .body(updateDTO)
                 .when().put("/{id}")
                 .then()
                 .statusCode(200)
                 .body("marca", is("Marca Atualizada"))
                 .body("modelo", is("Modelo Atualizado"));
     }
 
     @Test
     void testUpdate_NonExistentBetoneira() {
         given()
                 .contentType(ContentType.JSON)
                 .pathParam("id", 9999)
                 .body(betoneiraValidaDTO)
                 .when().put("/{id}")
                 .then()
                 .statusCode(404);
     }
 
     @Test
     void testDelete_ExistingBetoneira() {
         // First, create a Betoneira
         Long betoneiraId = createBetoneira(betoneiraValidaDTO);
 
         given()
                 .pathParam("id", betoneiraId)
                 .when().delete("/{id}")
                 .then()
                 .statusCode(204);
 
         // Verify deletion (optional, but good practice)
         given()
                 .pathParam("id", betoneiraId)
                 .when().get("/{id}")
                 .then()
                 .statusCode(404);
     }
 
     @Test
     void testDelete_NonExistentBetoneira() {
         given()
                 .pathParam("id", 9999)
                 .when().delete("/{id}")
                 .then()
                 .statusCode(404);
     }
 
     // Helper method to create a Betoneira
     private Long createBetoneira(BetoneiraRequestDTO dto) {
         Response response = given()
                 .contentType(ContentType.JSON)
                 .body(dto)
                 .when().post()
                 .then()
                 .statusCode(201)
                 .extract().response();
 
         return response.jsonPath().getLong("id");
     }
 }