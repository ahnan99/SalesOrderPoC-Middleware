package com.example.SalesOrderPoCMiddleware.Controller;

import com.example.SalesOrderPoCMiddleware.service.SalesOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SalesOrderController {

    @Autowired
    SalesOrderClient salesOrderClient;

    @GetMapping(value="/SalesOrderByID")
    @ResponseBody
    public Object getSalesOrderByID(@RequestParam String id){
        return salesOrderClient.getSalesOrderByID(id);
    }
}
