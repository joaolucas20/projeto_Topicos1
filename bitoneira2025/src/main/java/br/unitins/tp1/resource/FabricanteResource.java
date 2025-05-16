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
import java.util.logging.Logger;

@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService service;

    @Inject
    Validator validator;

    private static final Logger LOGGER = Logger.getLogger(FabricanteResource.class.getName());

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
    public List<FabricanteResponseDTO> getAll() {
        LOGGER.info("Received GET request for all fabricantes");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public FabricanteResponseDTO findById(@PathParam("id") Long id) {
        LOGGER.info("Received GET request for fabricante with id: " + id);
        return service.findById(id);
    }

    @POST
    public Response create(FabricanteRequestDTO dto) {
        LOGGER.info("Received POST request to create fabricante: " + dto);
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            return validationResponse;
        }

        FabricanteResponseDTO created = service.create(dto);
        LOGGER.info("Fabricante created successfully");
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public FabricanteResponseDTO update(@PathParam("id") Long id, FabricanteRequestDTO dto) {
        LOGGER.info("Received PUT request to update fabricante with id: " + id + ", data: " + dto);
        Response validationResponse = validate(dto);
        if (validationResponse != null) {
            throw new WebApplicationException(validationResponse);
        }
        FabricanteResponseDTO updated = service.update(id, dto);
        LOGGER.info("Fabricante updated successfully");
        return updated;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.info("Received DELETE request to delete fabricante with id: " + id);
        service.delete(id);
        LOGGER.info("Fabricante deleted successfully");
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