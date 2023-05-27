package org.example.controllers.invoices;

import org.example.controllers.MainController;
import org.example.models.views.InvoiceViewElement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InvoicesController extends MainController {
    @CrossOrigin
    @GetMapping("/invoices")
    public String getInvoices() {
        Object[] objects = session.createSQLQuery("SELECT * FROM INVOICES_VIEW").stream().toArray();
        List<InvoiceViewElement> invoiceViewElements = new ArrayList<>();
        Object[] currObj;
        InvoiceViewElement invoiceViewElement;
        for (Object result : objects) {
            currObj = (Object[]) result;
            invoiceViewElement = new InvoiceViewElement(currObj);
            invoiceViewElements.add(invoiceViewElement);
        }
        return gson.toJson(invoiceViewElements);
    }
}
