// betoneira-api/src/main/java/br/unitins/tp1/resource/FabricanteResource.java (continuação)
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.service.FabricanteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService service;

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
        FabricanteResponseDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public FabricanteResponseDTO update(@PathParam("id") Long id, FabricanteRequestDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}