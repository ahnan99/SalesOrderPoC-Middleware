package com.example.SalesOrderPoCMiddleware.Controller;

import com.example.SalesOrderPoCMiddleware.service.SalesOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SalesOrderController {

    @Autowired
    SalesOrderClient salesOrderClient;

    @GetMapping(value = "/SalesOrderByID")
    @ResponseBody
    public Object getSalesOrderByID(@RequestParam(required = false) String id,
                                    @RequestParam(required = false) String upperId,
                                    @RequestParam(required = false) String dateTimeLower,
                                    @RequestParam(required = false) String dateTimeUpper,
                                    @RequestParam(required = false) int maxHit) {
        return salesOrderClient.getSalesOrderByID(id, upperId, dateTimeLower, dateTimeUpper, maxHit);
    }
}
