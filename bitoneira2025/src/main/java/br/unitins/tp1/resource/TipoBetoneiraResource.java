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

@Path("/tipos-betoneira")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoBetoneiraResource {

    @Inject
    TipoBetoneiraService service;

    @GET
    public List<TipoBetoneiraEntity> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public TipoBetoneiraEntity findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @Transactional
    public Response create(TipoBetoneiraEntity tipoBetoneira) {
        TipoBetoneiraEntity created = service.create(tipoBetoneira);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public TipoBetoneiraEntity update(@PathParam("id") Long id, TipoBetoneiraEntity tipoBetoneira) {
        return service.update(id, tipoBetoneira);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    // Endpoint de teste para inserir um tipo de betoneira
    @POST
    @Path("/teste/inserir")
    public Response testeInserirTipoBetoneira() {
        TipoBetoneiraEntity entity = new TipoBetoneiraEntity();
        entity.setTipo(TipoBetoneiraEnum.HORIZONTAL);

        TipoBetoneiraEntity created = service.create(entity);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // Endpoint de teste para buscar todos os tipos de betoneira
    @GET
    @Path("/teste/buscar-todos")
    public List<TipoBetoneiraEntity> testeBuscarTodosTiposBetoneira() {
        return service.getAll();
    }
}