package com.igorbunova.jaxrs;

import javax.inject.Inject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.igorbunova.filter.JsonRespFilter;
import com.igorbunova.guice.AppModule;
import com.igorbunova.handler.AnswerMixIn;
import com.igorbunova.handler.ExceptionMapper;
import com.igorbunova.handler.Hello;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.jvnet.hk2.guice.bridge.api.HK2IntoGuiceBridge;

/**
 * JaxRsApp.
 */
public class JaxRsApp extends ResourceConfig {
    @Inject
    public JaxRsApp(ServiceLocator locator) {

        Module hk2 = new HK2IntoGuiceBridge(locator);
        Injector injector = Guice.createInjector(
            hk2,
            new AppModule()
        );

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(locator);
        GuiceIntoHK2Bridge bridge = locator.getService(GuiceIntoHK2Bridge.class);
        bridge.bridgeGuiceInjector(injector);

        ObjectMapper om = new ObjectMapper();
        om.addMixIn(Hello.Answer.class, AnswerMixIn.class);
        JacksonJsonProvider json = new JacksonJsonProvider(om);
        registerInstances(json, new JsonRespFilter(), new ExceptionMapper());

        packages("com.igorbunova.handler");

    }

}
