package com.igorbunova;

import java.io.IOException;
import java.net.URI;
import javax.servlet.ServletException;
import javax.ws.rs.core.UriBuilder;
import com.igorbunova.jaxrs.JaxRsApp;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.glassfish.jersey.servlet.ServletContainer;

import static io.undertow.servlet.Servlets.servlet;

/**
 * Application.
 */
public class Application {
    /**
     * Starts the lightweight HTTP server serving the JAX-RS application.
     *
     * @return new instance of the lightweight HTTP server
     * @throws IOException
     */
    static void startServer() throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment();
        servletBuilder.setClassLoader(Application.class.getClassLoader())
            .setContextPath("/")
            .setDeploymentName("test")
            .addServlets(servlet("jerseyServlet", ServletContainer.class)
                .setLoadOnStartup(1)
                .addInitParam("javax.ws.rs.Application", JaxRsApp.class.getName())
                .addMapping("/*")
            );

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        Runtime.getRuntime().addShutdownHook(new Thread(manager::undeploy));

        PathHandler path = Handlers.path().addPrefixPath("/", manager.start());

        Undertow server = Undertow.builder()
            .addHttpListener(8080, "localhost")
            .setHandler(path)
            .build();
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        server.start();
    }

    public static void main(String[] args) throws ServletException {
        System.out.println("\"Hello World\" Jersey Example Application");

        startServer();

        System.out.println("Application started.\n"
            + "Try accessing " + getBaseURI() + "hello/world in the browser.\n");
    }

    private static int getPort(int defaultPort) {
        final String port = System.getProperty("jersey.config.test.container.port");
        if (null != port) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException e) {
                System.out.println("Value of jersey.config.test.container.port property"
                    + " is not a valid positive integer [" + port + "]."
                    + " Reverting to default [" + defaultPort + "].");
            }
        }
        return defaultPort;
    }

    /**
     * Gets base {@link URI}.
     *
     * @return base {@link URI}.
     */
    public static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort(8080)).build();
    }
}
