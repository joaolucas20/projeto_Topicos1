package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.service.ClienteService;
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

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @Inject
    Validator validator;

    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());

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
    public List<ClienteResponseDTO> getAll() {
        LOGGER.info("Received GET request for all clientes");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public ClienteResponseDTO findById(@PathParam("id") Long id) {
        LOGGER.info("Received GET request for cliente with id: " + id);
        return service.findById(id);
    }

    @POST
    public Response create(ClienteRequestDTO dto) {
        LOGGER.info("Received POST request to create cliente: " + dto);
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            return validationResponse;
        }

        ClienteResponseDTO created = service.create(dto);
        LOGGER.info("Cliente created successfully");
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public ClienteResponseDTO update(@PathParam("id") Long id, ClienteRequestDTO dto) {
        LOGGER.info("Received PUT request to update cliente with id: " + id + ", data: " + dto);
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            throw new WebApplicationException(validationResponse);
        }
        ClienteResponseDTO updated = service.update(id, dto);
        LOGGER.info("Cliente updated successfully");
        return updated;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.info("Received DELETE request to delete cliente with id: " + id);
        service.delete(id);
        LOGGER.info("Cliente deleted successfully");
        return Response.noContent().build();
    }
}