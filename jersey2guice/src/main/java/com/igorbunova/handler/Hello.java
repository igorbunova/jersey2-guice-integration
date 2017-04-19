package com.igorbunova.handler;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import com.google.inject.Inject;
import com.igorbunova.service.HelloService;

/**
 * Hello.
 */
@Path("/hello")
public class Hello {

    private final HelloService service;

    @Inject
    public Hello(HelloService service) {
        this.service = service;
    }


    public static class Answer {
        private final boolean ok;

        private Answer(boolean ok) {
            this.ok = ok;
        }

        public boolean isOk() {
            return ok;
        }
    }

    @GET
    @Path("/world")
    public Answer method() {
        service.doSomething();
        return new Answer(true);
    }

    @POST
    @Path("/post")
    public Answer post(Answer body) {
        service.doSomething();
        return body;
    }
}
