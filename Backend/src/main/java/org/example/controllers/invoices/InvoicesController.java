package org.example.controllers.invoices;

import org.example.controllers.MainController;
import org.example.models.courses.Invoice;
import org.example.models.views.invoice.InvoiceViewElement;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @CrossOrigin
    @PostMapping("/invoices/users")
    public String getInvoicesForUser(@RequestBody Map<String, Integer> inputData) {
        Query query = session.createSQLQuery("SELECT * FROM f_invoices_for_participant(:participant_id)")
                .setParameter("participant_id", inputData.get("participant_id"));
        List<Invoice> invoices = new ArrayList<>();
        Object[] currObj;
        Invoice invoice;
        try {
            for (Object result : query.getResultList()) {
                currObj = (Object[]) result;
                invoice = new Invoice(currObj);
                invoices.add(invoice);
            }
        } catch (PersistenceException ignored) {
        }
        return gson.toJson(invoices);
    }

    @CrossOrigin
    @PostMapping("/invoices/unpaid/sum")
    public BigDecimal getSumOfUnpaidInvoices(@RequestBody Map<String, Integer> inputData) {
        Query query = session.createSQLQuery("SELECT f_amount_to_pay_for_participant(:participant_id) FROM DUAL")
                .setParameter("participant_id", inputData.get("participant_id"));
        BigDecimal finalSum = BigDecimal.ZERO;
        Object result = query.getSingleResult();
        if (result != null) {
            finalSum = BigDecimal.valueOf(Long.parseLong(result.toString()));
        }
        return finalSum;
    }
}
