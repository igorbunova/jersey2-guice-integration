package com.igorbunova.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.igorbunova.service.HelloService;

/**
 * AppModule.
 */
public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloService.class).in(Singleton.class);
    }
}
