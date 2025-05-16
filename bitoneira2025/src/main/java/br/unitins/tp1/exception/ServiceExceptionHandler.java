package br.unitins.tp1.exception;
 
 import br.unitins.tp1.dto.ErrorResponseDTO;  // Add this line
 import jakarta.ws.rs.core.Response;
 import jakarta.ws.rs.ext.ExceptionMapper;
 import jakarta.ws.rs.ext.Provider;
 
 import java.util.logging.Logger;
 
 @Provider
 public class ServiceExceptionHandler implements ExceptionMapper<ServiceException> {
 
     private static final Logger LOGGER = Logger.getLogger(ServiceExceptionHandler.class.getName());
 
     @Override
     public Response toResponse(ServiceException exception) {
         LOGGER.severe(exception.getMessage());
         return Response.status(exception.getStatus())
                 .entity(new ErrorResponseDTO(exception.getMessage()))
                 .build();
     }
 }