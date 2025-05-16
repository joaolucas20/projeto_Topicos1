package br.unitins.tp1.resource;
 
 import br.unitins.tp1.model.TipoBetoneiraEnum;
 import br.unitins.tp1.dto.BetoneiraRequestDTO;
 import br.unitins.tp1.dto.BetoneiraResponseDTO;
 import br.unitins.tp1.service.BetoneiraService;
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
 
 @Path("/betoneiras")
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
 public class BetoneiraResource {
 
     @Inject
     BetoneiraService service;
 
     @Inject
     Validator validator;
 
     private static final Logger LOGGER = Logger.getLogger(BetoneiraResource.class.getName());
 
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
     public List<BetoneiraResponseDTO> getAll() {
         LOGGER.info("Received GET request for all betoneiras");
         return service.getAll();
     }
 
     @GET
     @Path("/{id}")
     public BetoneiraResponseDTO findById(@PathParam("id") Long id) {
         LOGGER.info("Received GET request for betoneira with id: " + id);
         return service.findById(id);
     }
 
     @GET
     @Path("/modelo/{modelo}")
     public List<BetoneiraResponseDTO> findByModelo(@PathParam("modelo") String modelo) {
         LOGGER.info("Received GET request for betoneiras with modelo: " + modelo);
         return service.findByModelo(modelo);
     }
 
     @POST
     public Response create(BetoneiraRequestDTO dto) {
         LOGGER.info("Received POST request to create betoneira: " + dto);
         Response validationResponse = validate(dto);
         if (validationResponse != null) {
             return validationResponse;
         }
 
         BetoneiraResponseDTO created = service.create(dto);
         LOGGER.info("Betoneira created successfully");
         return Response.status(Response.Status.CREATED).entity(created).build();
     }
 
     @PUT
     @Path("/{id}")
     public BetoneiraResponseDTO update(@PathParam("id") Long id, BetoneiraRequestDTO dto) {
         LOGGER.info("Received PUT request to update betoneira with id: " + id + ", data: " + dto);
         Response validationResponse = validate(dto);
         if (validationResponse != null) {
             throw new WebApplicationException(validationResponse);
         }
         BetoneiraResponseDTO updated = service.update(id, dto);
         LOGGER.info("Betoneira updated successfully");
         return updated;
     }
 
     @DELETE
     @Path("/{id}")
     public Response delete(@PathParam("id") Long id) {
         LOGGER.info("Received DELETE request to delete betoneira with id: " + id);
         service.delete(id);
         return Response.noContent().build();
     }
 
     // Endpoint de teste para inserir uma betoneira
     @POST
     @Path("/teste/inserir")
     public Response testeInserirBetoneira() {
         BetoneiraRequestDTO dto = new BetoneiraRequestDTO();
         dto.setMarca("Marca Teste");
         dto.setModelo("Modelo Teste");
         dto.setCapacidade(10.5);
         dto.setTipo(TipoBetoneiraEnum.HORIZONTAL);
         dto.setFabricanteId(1L); // Supondo que exista um fabricante com ID 1
 
         Response validationResponse = validate(dto);
         if (validationResponse != null) {
             return validationResponse;
         }
 
         BetoneiraResponseDTO created = service.create(dto);
         return Response.status(Response.Status.CREATED).entity(created).build();
     }
 
     // Endpoint de teste para buscar todas as betoneiras
     @GET
     @Path("/teste/buscar-todos")
     public List<BetoneiraResponseDTO> testeBuscarTodasBetoneiras() {
         return service.getAll();
     }
 }