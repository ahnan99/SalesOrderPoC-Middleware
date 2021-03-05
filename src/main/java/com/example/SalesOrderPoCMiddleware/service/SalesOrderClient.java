package com.example.SalesOrderPoCMiddleware.service;

import com.example.SalesOrderPoCMiddleware.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;


public class SalesOrderClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(SalesOrderClient.class);

    @Value("${client.defaultUri}")
    private String defaultUri;

    public Object getSalesOrderByID(String id) {
        SalesOrderByElementsQuerySelectionByID selection = new SalesOrderByElementsQuerySelectionByID();
        selection.setInclusionExclusionCode("I");
        selection.setIntervalBoundaryTypeCode("1");
        BusinessTransactionDocumentID lowerBoundary = new BusinessTransactionDocumentID();
        lowerBoundary.setValue(id);
        selection.setLowerBoundaryID(lowerBoundary);
        selection.setUpperBoundaryID(null);
        log.info("Requesting location for " + id);
        SalesOrderByElementsQuerySelectionByElements salesOrderByElementsQuerySelectionByElements = new SalesOrderByElementsQuerySelectionByElements();
        SalesOrderByElementsQueryMessageSync messageSync = new SalesOrderByElementsQueryMessageSync();
        salesOrderByElementsQuerySelectionByElements.getSelectionByID().add(selection);
        messageSync.setSalesOrderSelectionByElements(salesOrderByElementsQuerySelectionByElements);
        return  getWebServiceTemplate()
                .marshalSendAndReceive(defaultUri,
                        new ObjectFactory().createSalesOrderByElementsQuerySync(messageSync));
    }
}
