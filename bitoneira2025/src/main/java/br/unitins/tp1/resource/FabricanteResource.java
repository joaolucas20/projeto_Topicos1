package br.unitins.tp1.resource;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.service.FabricanteService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService service;

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
    public List<FabricanteResponseDTO> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public FabricanteResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(FabricanteRequestDTO dto) {
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            return validationResponse;
        }

        FabricanteResponseDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public FabricanteResponseDTO update(@PathParam("id") Long id, FabricanteRequestDTO dto) {
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

    // Endpoint de teste para inserir um fabricante
    @POST
    @Path("/teste/inserir")
    public Response testeInserirFabricante() {
        FabricanteRequestDTO dto = new FabricanteRequestDTO();
        dto.setNome("Fabricante Teste");
        dto.setCnpj("12345678901234");

        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            return validationResponse;
        }

        FabricanteResponseDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // Endpoint de teste para buscar todos os fabricantes
    @GET
    @Path("/teste/buscar-todos")
    public List<FabricanteResponseDTO> testeBuscarTodosFabricantes() {
        return service.getAll();
    }
}