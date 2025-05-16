package br.unitins.tp1.resource;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.service.PedidoService;
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

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService service;

    @Inject
    Validator validator;

    private static final Logger LOGGER = Logger.getLogger(PedidoResource.class.getName());

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
    public List<PedidoResponseDTO> getAll() {
        LOGGER.info("Received GET request for all pedidos");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public PedidoResponseDTO findById(@PathParam("id") Long id) {
        LOGGER.info("Received GET request for pedido with id: " + id);
        return service.findById(id);
    }

    @POST
    public Response create(PedidoRequestDTO dto) {
        LOGGER.info("Received POST request to create pedido: " + dto);
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            return validationResponse;
        }

        PedidoResponseDTO created = service.create(dto);
        LOGGER.info("Pedido created successfully");
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public PedidoResponseDTO update(@PathParam("id") Long id, PedidoRequestDTO dto) {
        LOGGER.info("Received PUT request to update pedido with id: " + id + ", data: " + dto);
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            throw new WebApplicationException(validationResponse);
        }
        PedidoResponseDTO updated = service.update(id, dto);
        LOGGER.info("Pedido updated successfully");
        return updated;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.info("Received DELETE request to delete pedido with id: " + id);
        service.delete(id);
        LOGGER.info("Pedido deleted successfully");
        return Response.noContent().build();
    }
}