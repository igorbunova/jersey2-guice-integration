package com.igorbunova.handler;

import javax.ws.rs.core.Response;

/**
 * ExceptionMapper.
 */
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable> {

    private static class Failure {
        private final String message;

        private Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @Override
    public Response toResponse(Throwable exception) {
        System.out.println(exception.getMessage());
        exception.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new Failure(exception.getMessage())).build();
    }
}
