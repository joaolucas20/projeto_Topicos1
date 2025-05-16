package br.unitins.tp1.resource;

import br.unitins.tp1.dto.*;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@TestHTTPEndpoint(PedidoResource.class)
public class PedidoResourceTest {

    private static Long clienteId;

    @BeforeAll
    public static void setup() {
        // Create a Cliente for the tests
        ClienteRequestDTO clienteDTO = new ClienteRequestDTO();
        clienteDTO.setNome("Test Cliente");
        clienteDTO.setEmail("test@cliente.com");

        clienteId = given()
                .contentType(ContentType.JSON)
                .body(clienteDTO)
                .when().post("/clientes")  // Assuming ClienteResource is at /clientes
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");
    }

    @Test
    public void testGetAll() {
        given()
                .when().get()
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetById_ExistingPedido() {
        // First, create a Pedido
        PedidoRequestDTO pedidoValidoDTO = new PedidoRequestDTO();
        pedidoValidoDTO.setDataPedido(LocalDate.now());
        pedidoValidoDTO.setClienteId(clienteId);

        Long pedidoId = createPedido(pedidoValidoDTO);

        given()
                .pathParam("id", pedidoId)
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .body("clienteId", is(clienteId.intValue()))
                .body("dataPedido", is(LocalDate.now().toString()));
    }

    @Test
    public void testGetById_NonExistentPedido() {
        given()
                .pathParam("id", 9999)
                .when().get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreate_ValidData() {
        PedidoRequestDTO pedidoValidoDTO = new PedidoRequestDTO();
        pedidoValidoDTO.setDataPedido(LocalDate.now());
        pedidoValidoDTO.setClienteId(clienteId);

        given()
                .contentType(ContentType.JSON)
                .body(pedidoValidoDTO)
                .when().post()
                .then()
                .statusCode(201)
                .body("clienteId", is(clienteId.intValue()))
                .body("dataPedido", is(LocalDate.now().toString()));
    }

    @Test
    public void testCreate_InvalidData() {
        PedidoRequestDTO pedidoInvalidoDTO = new PedidoRequestDTO();
        pedidoInvalidoDTO.setDataPedido(null);
        pedidoInvalidoDTO.setClienteId(null);

        given()
                .contentType(ContentType.JSON)
                .body(pedidoInvalidoDTO)
                .when().post()
                .then()
                .statusCode(400)
                .body(".", hasSize(2)); // Check for 2 validation errors
    }

    @Test
    public void testUpdate_ExistingPedido() {
        // First, create a Pedido
        PedidoRequestDTO pedidoValidoDTO = new PedidoRequestDTO();
        pedidoValidoDTO.setDataPedido(LocalDate.now());
        pedidoValidoDTO.setClienteId(clienteId);

        Long pedidoId = createPedido(pedidoValidoDTO);

        PedidoRequestDTO updateDTO = new PedidoRequestDTO();
        updateDTO.setDataPedido(LocalDate.now().plusDays(1));
        updateDTO.setClienteId(clienteId);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", pedidoId)
                .body(updateDTO)
                .when().put("/{id}")
                .then()
                .statusCode(200)
                .body("clienteId", is(clienteId.intValue()))
                .body("dataPedido", is(LocalDate.now().plusDays(1).toString()));
    }

    @Test
    public void testUpdate_NonExistentPedido() {
        PedidoRequestDTO updateDTO = new PedidoRequestDTO();
        updateDTO.setDataPedido(LocalDate.now().plusDays(1));
        updateDTO.setClienteId(clienteId);

        given()
                .pathParam("id", 9999)
                .body(updateDTO)
                .when().put("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDelete_ExistingPedido() {
        // First, create a Pedido
        PedidoRequestDTO pedidoValidoDTO = new PedidoRequestDTO();
        pedidoValidoDTO.setDataPedido(LocalDate.now());
        pedidoValidoDTO.setClienteId(clienteId);

        Long pedidoId = createPedido(pedidoValidoDTO);

        given()
                .pathParam("id", pedidoId)
                .when().delete("/{id}")
                .then()
                .statusCode(204);

        // Verify deletion (optional, but good practice)
        given()
                .pathParam("id", pedidoId)
                .when().get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDelete_NonExistentPedido() {
        given()
                .pathParam("id", 9999)
                .when().delete("/{id}")
                .then()
                .statusCode(404);
    }

    // Helper method to create a Pedido
    private Long createPedido(PedidoRequestDTO dto) {
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