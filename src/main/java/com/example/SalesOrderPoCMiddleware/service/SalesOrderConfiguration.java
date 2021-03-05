package com.example.SalesOrderPoCMiddleware.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.apache.http.auth.UsernamePasswordCredentials;

@Configuration
public class SalesOrderConfiguration {
    @Value("${client.defaultUri}")
    private String defaultUri;

    @Value("${client.user.name}")
    private String userName;

    @Value("${client.user.password}")
    private String userPassword;

    @Bean
    Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.example.SalesOrderPoCMiddleware.wsdl");
        return marshaller;
    }

    @Bean
    public HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        // set the basic authorization credentials
        httpComponentsMessageSender.setCredentials(usernamePasswordCredentials());

        return httpComponentsMessageSender;
    }

    @Bean
    public UsernamePasswordCredentials usernamePasswordCredentials() {
        // pass the user name and password to be used
        return new UsernamePasswordCredentials(userName, userPassword);
    }

    @Bean
    public SalesOrderClient salesOrderClient(Jaxb2Marshaller marshaller) {
        SalesOrderClient client = new SalesOrderClient();
        client.setDefaultUri(defaultUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(httpComponentsMessageSender());
        return client;
    }
}
