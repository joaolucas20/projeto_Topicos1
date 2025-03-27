// betoneira-api/src/main/java/br/unitins/tp1/resource/BetoneiraResource.java
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;
import br.unitins.tp1.service.BetoneiraService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/betoneiras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BetoneiraResource {

    @Inject
    BetoneiraService service;

    @GET
    public List<BetoneiraResponseDTO> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public BetoneiraResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/modelo/{modelo}")
    public List<BetoneiraResponseDTO> findByModelo(@PathParam("modelo") String modelo) {
        return service.findByModelo(modelo);
    }

    @POST
    public Response create(BetoneiraRequestDTO dto) {
        BetoneiraResponseDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public BetoneiraResponseDTO update(@PathParam("id") Long id, BetoneiraRequestDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}