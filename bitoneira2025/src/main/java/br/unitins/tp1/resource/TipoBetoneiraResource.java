package br.unitins.tp1.resource;
 
 import br.unitins.tp1.model.TipoBetoneiraEnum;
 import br.unitins.tp1.model.TipoBetoneiraEntity;
 import br.unitins.tp1.service.TipoBetoneiraService;
 import jakarta.inject.Inject;
 import jakarta.transaction.Transactional;
 import jakarta.ws.rs.*;
 import jakarta.ws.rs.core.MediaType;
 import jakarta.ws.rs.core.Response;
 import java.util.List;
 import java.util.logging.Logger;
 
 @Path("/tipos-betoneira")
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
 public class TipoBetoneiraResource {
 
     @Inject
     TipoBetoneiraService service;
 
     private static final Logger LOGGER = Logger.getLogger(TipoBetoneiraResource.class.getName());
 
     @GET
     public List<TipoBetoneiraEntity> getAll() {
         LOGGER.info("Received GET request for all tipos-betoneira");
         return service.getAll();
     }
 
     @GET
     @Path("/{id}")
     public TipoBetoneiraEntity findById(@PathParam("id") Long id) {
         LOGGER.info("Received GET request for tipo-betoneira with id: " + id);
         return service.findById(id);
     }
 
     @POST
     @Transactional
     public Response create(TipoBetoneiraEntity tipoBetoneira) {
         LOGGER.info("Received POST request to create tipo-betoneira: " + tipoBetoneira);
         TipoBetoneiraEntity created = service.create(tipoBetoneira);
         LOGGER.info("Tipo-betoneira created successfully");
         return Response.status(Response.Status.CREATED).entity(created).build();
     }
 
     @PUT
     @Path("/{id}")
     @Transactional
     public TipoBetoneiraEntity update(@PathParam("id") Long id, TipoBetoneiraEntity tipoBetoneira) {
         LOGGER.info("Received PUT request to update tipo-betoneira with id: " + id + ", data: " + tipoBetoneira);
         TipoBetoneiraEntity updated = service.update(id, tipoBetoneira);
         LOGGER.info("Tipo-betoneira updated successfully");
         return updated;
     }
 
     @DELETE
     @Path("/{id}")
     @Transactional
     public Response delete(@PathParam("id") Long id) {
         LOGGER.info("Received DELETE request to delete tipo-betoneira with id: " + id);
         service.delete(id);
         LOGGER.info("Tipo-betoneira deleted successfully");
         return Response.noContent().build();
     }
 
     // Endpoint de teste para inserir um tipo de betoneira
     @POST
     @Path("/teste/inserir")
     public Response testeInserirTipoBetoneira() {
         LOGGER.info("Received POST request for teste/inserir");
         TipoBetoneiraEntity entity = new TipoBetoneiraEntity();
         entity.setTipo(TipoBetoneiraEnum.HORIZONTAL);
 
         TipoBetoneiraEntity created = service.create(entity);
         LOGGER.info("Tipo-betoneira created successfully (teste/inserir)");
         return Response.status(Response.Status.CREATED).entity(created).build();
     }
 
     // Endpoint de teste para buscar todos os tipos de betoneira
     @GET
     @Path("/teste/buscar-todos")
     public List<TipoBetoneiraEntity> testeBuscarTodosTiposBetoneira() {
         LOGGER.info("Received GET request for teste/buscar-todos");
         List<TipoBetoneiraEntity> result = service.getAll();
         LOGGER.info("Tipos-betoneira retrieved successfully (teste/buscar-todos)");
         return result;
     }
 }