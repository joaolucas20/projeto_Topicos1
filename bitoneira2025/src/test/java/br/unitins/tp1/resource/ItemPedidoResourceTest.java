package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ItemPedidoResource.class)
public class ItemPedidoResourceTest {

    private ItemPedidoRequestDTO itemPedidoValidoDTO;
    private ItemPedidoRequestDTO itemPedidoInvalidoDTO;
    private Long pedidoId = 1L; // Assuming pedidoId 1 exists
    private Long betoneiraId = 1L; // Assuming betoneiraId 1 exists

    @BeforeEach
    public void setup() {
        itemPedidoValidoDTO = new ItemPedidoRequestDTO();
        itemPedidoValidoDTO.setQuantidade(2);
        itemPedidoValidoDTO.setPreco(150.0);
        itemPedidoValidoDTO.setPedidoId(pedidoId);
        itemPedidoValidoDTO.setBetoneiraId(betoneiraId);

        itemPedidoInvalidoDTO = new ItemPedidoRequestDTO();
        itemPedidoInvalidoDTO.setQuantidade(0);
        itemPedidoInvalidoDTO.setPreco(-10.0);
        itemPedidoInvalidoDTO.setPedidoId(null);
        itemPedidoInvalidoDTO.setBetoneiraId(null);
    }

    @Test
    public void testGetAll() {
        given()
                .when().get()
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetById_ExistingItemPedido() {
        // First, create an ItemPedido
        Long itemPedidoId = createItemPedido(itemPedidoValidoDTO);

        given()
                .pathParam("id", itemPedidoId)
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .body("quantidade", is(2))
                .body("preco", is(150.0f))
                .body("pedidoId", is(pedidoId.intValue()))
                .body("betoneiraId", is(betoneiraId.intValue()));
    }

    @Test
    public void testGetById_NonExistentItemPedido() {
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
                .body(itemPedidoValidoDTO)
                .when().post()
                .then()
                .statusCode(201)
                .body("quantidade", is(2))
                .body("preco", is(150.0f))
                .body("pedidoId", is(pedidoId.intValue()))
                .body("betoneiraId", is(betoneiraId.intValue()));
    }

    @Test
    public void testCreate_InvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body(itemPedidoInvalidoDTO)
                .when().post()
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdate_ExistingItemPedido() {
        // First, create an ItemPedido
        Long itemPedidoId = createItemPedido(itemPedidoValidoDTO);

        ItemPedidoRequestDTO updateDTO = new ItemPedidoRequestDTO();
        updateDTO.setQuantidade(3);
        updateDTO.setPreco(300.0);
        updateDTO.setPedidoId(pedidoId);
        updateDTO.setBetoneiraId(2L); // Assuming betoneiraId 2 exists

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", itemPedidoId)
                .body(updateDTO)
                .when().put("/{id}")
                .then()
                .statusCode(200)
                .body("quantidade", is(3))
                .body("preco", is(300.0f))
                .body("betoneiraId", is(2));
    }

    @Test
    public void testUpdate_NonExistentItemPedido() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 9999)
                .body(itemPedidoValidoDTO)
                .when().put("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDelete_ExistingItemPedido() {
        // First, create an ItemPedido
        Long itemPedidoId = createItemPedido(itemPedidoValidoDTO);

        given()
                .pathParam("id", itemPedidoId)
                .when().delete("/{id}")
                .then()
                .statusCode(204);

        // Verify deletion (optional, but good practice)
        given()
                .pathParam("id", itemPedidoId)
                .when().get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDelete_NonExistentItemPedido() {
        given()
                .pathParam("id", 9999)
                .when().delete("/{id}")
                .then()
                .statusCode(404);
    }

    // Helper method to create an ItemPedido
    private Long createItemPedido(ItemPedidoRequestDTO dto) {
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