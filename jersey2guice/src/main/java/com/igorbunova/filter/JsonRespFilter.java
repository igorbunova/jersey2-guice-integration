package com.igorbunova.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ContainerResponse;

/**
 * JsonRespFilter.
 */
public class JsonRespFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (responseContext != null && responseContext instanceof ContainerResponse) {
            ContainerResponse response = (ContainerResponse) responseContext;
            response.setMediaType(MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"));
        }
    }
}
