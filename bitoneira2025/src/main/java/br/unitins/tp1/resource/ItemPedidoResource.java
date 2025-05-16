package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.dto.ItemPedidoResponseDTO;
import br.unitins.tp1.service.ItemPedidoService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Path("/itens-pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemPedidoResource {

    @Inject
    ItemPedidoService service;

    @Inject
    Validator validator;

    private static final Logger LOGGER = Logger.getLogger(ItemPedidoResource.class.getName());

    private Response validate(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            LOGGER.warning("Validation errors: " + errors);
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        return null;
    }

    @GET
    public List<ItemPedidoResponseDTO> getAll() {
        LOGGER.info("Received GET request for all itens-pedido");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public ItemPedidoResponseDTO findById(@PathParam("id") Long id) {
        LOGGER.info("Received GET request for item-pedido with id: " + id);
        return service.findById(id);
    }

    @POST
    public Response create(ItemPedidoRequestDTO dto) {
        LOGGER.info("Received POST request to create item-pedido: " + dto);
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            return validationResponse;
        }

        ItemPedidoResponseDTO created = service.create(dto);
        LOGGER.info("Item-pedido created successfully");
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public ItemPedidoResponseDTO update(@PathParam("id") Long id, ItemPedidoRequestDTO dto) {
        LOGGER.info("Received PUT request to update item-pedido with id: " + id + ", data: " + dto);
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            throw new WebApplicationException(validationResponse);
        }
        ItemPedidoResponseDTO updated = service.update(id, dto);
        LOGGER.info("Item-pedido updated successfully");
        return updated;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.info("Received DELETE request to delete item-pedido with id: " + id);
        service.delete(id);
        LOGGER.info("Item-pedido deleted successfully");
        return Response.noContent().build();
    }
}