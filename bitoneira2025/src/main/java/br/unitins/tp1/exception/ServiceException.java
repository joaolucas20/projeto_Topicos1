package br.unitins.tp1.exception;

import jakarta.ws.rs.core.Response;

public class ServiceException extends RuntimeException {

    private Response.Status status;

    public ServiceException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}