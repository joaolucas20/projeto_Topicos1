package br.unitins.tp1.resource;
 
 import br.unitins.tp1.dto.ClienteRequestDTO;
 import io.quarkus.test.common.http.TestHTTPEndpoint;
 import io.quarkus.test.junit.QuarkusTest;
 import io.restassured.http.ContentType;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 
 import static io.restassured.RestAssured.given;
 import static io.restassured.RestAssured.when;
 import static org.hamcrest.CoreMatchers.is;
 
 @QuarkusTest
 @TestHTTPEndpoint(ClienteResource.class)
 public class ClienteResourceTest {
 
     private Long clienteId;
     private ClienteRequestDTO clienteValidoDTO;
     private ClienteRequestDTO clienteInvalidoDTO;
 
     @BeforeEach
     public void setup() {
         clienteValidoDTO = new ClienteRequestDTO();
         clienteValidoDTO.setNome("Novo Cliente");
         clienteValidoDTO.setEmail("novo@cliente.com");
 
         clienteInvalidoDTO = new ClienteRequestDTO();
         clienteInvalidoDTO.setNome("");
         clienteInvalidoDTO.setEmail("emailinvalido");
 
          // Create a Cliente for other tests to use (if needed)
         clienteId = given()
                 .contentType(ContentType.JSON)
                 .body(clienteValidoDTO)
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
     public void testGetById_ExistingClient() {
         given()
                 .pathParam("id", clienteId)
                 .when().get("/{id}")
                 .then()
                 .statusCode(200)
                 .body("nome", is("Novo Cliente"))
                 .body("email", is("novo@cliente.com"));
     }
 
     @Test
     public void testCreate_ValidData() {
         given()
                 .contentType(ContentType.JSON)
                 .body(clienteValidoDTO)
                 .when().post()
                 .then()
                 .statusCode(201)
                 .body("nome", is("Novo Cliente"))
                 .body("email", is("novo@cliente.com"));
     }
 
     @Test
     public void testCreate_InvalidData() {
         given()
                 .contentType(ContentType.JSON)
                 .body(clienteInvalidoDTO)
                 .when().post()
                 .then()
                 .statusCode(400);
     }
 
     @Test
     public void testUpdate_ExistingClient() {
         ClienteRequestDTO updateDTO = new ClienteRequestDTO();
         updateDTO.setNome("Nome Atualizado");
         updateDTO.setEmail("atualizado@example.com");
 
         given()
                 .contentType(ContentType.JSON)
                 .pathParam("id", clienteId)
                 .body(updateDTO)
                 .when().put("/{id}")
                 .then()
                 .statusCode(200)
                 .body("nome", is("Nome Atualizado"))
                 .body("email", is("atualizado@example.com"));
     }
 
     @Test
     public void testUpdateNonExistentId() {
         ClienteRequestDTO updateDTO = new ClienteRequestDTO();
         updateDTO.setNome("Nome Atualizado");
         updateDTO.setEmail("atualizado@example.com");
 
         given()
                 .contentType(ContentType.JSON)
                 .pathParam("id", 9999)
                 .body(updateDTO)
                 .when().put("/{id}")
                 .then()
                 .statusCode(404);
     }
 
     @Test
     public void testDelete_ExistingClient() {
         given()
                 .pathParam("id", clienteId)
                 .when().delete("/{id}")
                 .then()
                 .statusCode(204);
     }
 
     @Test
     public void testDeleteNonExistentId() {
         given()
                 .pathParam("id", 9999)
                 .when().delete("/{id}")
                 .then()
                 .statusCode(404);
     }
 }