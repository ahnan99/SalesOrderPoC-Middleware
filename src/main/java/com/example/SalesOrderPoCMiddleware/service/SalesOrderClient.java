package com.example.SalesOrderPoCMiddleware.service;

import com.example.SalesOrderPoCMiddleware.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;


public class SalesOrderClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(SalesOrderClient.class);

    @Value("${client.defaultUri}")
    private String defaultUri;

    public Object getSalesOrderByID(String id, String dateTimeLower, String dateTimeUpper) {
        SalesOrderByElementsQueryMessageSync messageSync = new SalesOrderByElementsQueryMessageSync();
        SalesOrderByElementsQuerySelectionByElements salesOrderByElementsQuerySelectionByElements = new SalesOrderByElementsQuerySelectionByElements();
        if(id!=null){
            //Selection by ID
            SalesOrderByElementsQuerySelectionByID selectionByID = new SalesOrderByElementsQuerySelectionByID();
            selectionByID.setInclusionExclusionCode("I");
            selectionByID.setIntervalBoundaryTypeCode("1");
            BusinessTransactionDocumentID lowerBoundary = new BusinessTransactionDocumentID();
            lowerBoundary.setValue(id);
            selectionByID.setLowerBoundaryID(lowerBoundary);
            selectionByID.setUpperBoundaryID(null);
            log.info("Requesting location for " + id);
            salesOrderByElementsQuerySelectionByElements.getSelectionByID().add(selectionByID);
        }

        if(dateTimeLower != null && dateTimeUpper != null){
            SalesOrderByElementsQuerySelectionByDateTime selectionByDateTime = new SalesOrderByElementsQuerySelectionByDateTime();
            selectionByDateTime.setInclusionExclusionCode("I");
            selectionByDateTime.setIntervalBoundaryTypeCode("3");
            try{
                selectionByDateTime.setUpperBoundaryDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeUpper));
                selectionByDateTime.setLowerBoundaryDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeLower));
                logger.info(DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeUpper));
            }catch (Exception e){
                logger.error(e.getMessage());
            }
            salesOrderByElementsQuerySelectionByElements.getSelectionByPostingDate().add(selectionByDateTime);
        }
        //Selection by Date


        messageSync.setSalesOrderSelectionByElements(salesOrderByElementsQuerySelectionByElements);
        return  getWebServiceTemplate()
                .marshalSendAndReceive(defaultUri,
                        new ObjectFactory().createSalesOrderByElementsQuerySync(messageSync));
    }


}
