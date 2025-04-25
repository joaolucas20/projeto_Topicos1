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

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService service;

    @Inject
    Validator validator;

    private Response validate(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        return null;
    }

    @GET
    public List<PedidoResponseDTO> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public PedidoResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(PedidoRequestDTO dto) {
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            return validationResponse;
        }

        PedidoResponseDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public PedidoResponseDTO update(@PathParam("id") Long id, PedidoRequestDTO dto) {
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            throw new WebApplicationException(validationResponse);
        }
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}