package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.DecimalFormat;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@RequestMapping("/app")
@Controller
public class PaymentController {

    private final String clientId = "Ac30ZMUM8NcfYN6bFs4RAq3R83_0x8l604YTO2cnFTCpxBlyhCxAB28RZX8sm6kRVooNcWkrlmKFO3jJ";
    private final String clientSecret = "EI1lSaIvZ8t0AjBVh5wK-9CPNK4d8WK0Imy7Tn0PjKua-iNnq_9o5lhxGFPrF0u6gPupv2OTmaD2W3cI";
    private final String paymentMode = "sandbox";
    private final String redirectPath = "http://localhost:9000";

    @RequestMapping("/license")
    public String license(HttpServletRequest request, ModelMap map) {
        return "payment/license";
    }

    @RequestMapping("/init-payment")
    @ResponseBody
    public String initPayment(HttpServletRequest request, String type, @RequestParam(defaultValue = "0") int model) {

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);

        RedirectUrls redirectUrls = new RedirectUrls();
        String guid = UUID.randomUUID().toString().replaceAll("-", "");
        redirectUrls.setCancelUrl(redirectPath + "/app/payment-failed.html");
        redirectUrls.setReturnUrl(redirectPath + "/app/processPayment.html?guid=" + guid);
        payment.setTransactions(getTransactions(guid, model == 1 ? 10 : model == 2 ? 20 : 30));
        payment.setRedirectUrls(redirectUrls);

        APIContext apiContext = new APIContext(clientId, clientSecret, paymentMode);
        request.getSession().setAttribute("apiContext", apiContext);
        try {
            Payment createdPayment = payment.create(apiContext);
            request.getSession().setAttribute("model", model);
            request.getSession().setAttribute(guid, createdPayment.getId());
            System.out.println("Created payment with id = " + createdPayment.getId() + " and status = " + createdPayment.getState());
            Iterator<Links> links = createdPayment.getLinks().iterator();
            while (links.hasNext()) {
                Links link = links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            return "redirect:/app/payment-failed.html";
        }
        return null;
    }

    @RequestMapping(value = "/processPayment.html")
    public String processPayment(HttpServletRequest req, HttpServletResponse resp, String guid) {
        if (req.getParameter("PayerID") != null) {
            Payment payment = new Payment();
            if (req.getParameter("guid") != null) {
                payment.setId(req.getSession().getAttribute(req.getParameter("guid")).toString());
            }
            int model = (Integer) req.getSession().getAttribute("model");
            payment.setTransactions(getTransactions(guid, model));

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(req.getParameter("PayerID"));
            APIContext apiContext = (APIContext) req.getSession().getAttribute("apiContext");
            try {
                req.getSession().setAttribute("payId", payment.getId());
                Payment createdPayment = payment.execute(apiContext, paymentExecution);
                req.getSession().setAttribute("createdPayment", createdPayment);
                if (createdPayment.getState().equalsIgnoreCase("approved")) {
                    return "redirect:/app/payment-success.html";
                } else {
                    return "redirect:/app/payment-failed.html";
                }
            } catch (PayPalRESTException e) {
                return "redirect:/payment-failed.html";
            }
        }
        return null;
    }

    @RequestMapping(value = {"/payment-success.html", "/payment-failed.html"})
    public String paymentResponse(HttpServletRequest request, ModelMap map) {
        boolean success = request.getRequestURI().contains("success");
        Payment payment = (Payment) request.getSession().getAttribute("createdPayment");
        if (payment != null) {
            //process payment
        }
        return success ? "payment/success" : "payment/failed";
    }

    private List<Transaction> getTransactions(String guid, double amt) {
        Transaction transaction = new Transaction();
        transaction.setDescription("This is the payment transaction description.");

        List<Item> items = new ArrayList<>();
        ItemList itemList = new ItemList();

        DecimalFormat f = new DecimalFormat("##.00");
        Item item = new Item();
        item.setName("Licence upgrade");
        item.setQuantity("1").setCurrency("USD").setPrice(f.format(amt));
        items.add(item);
        itemList.setItems(items);

        transaction.setItemList(itemList);

        Details details = new Details();
        details.setShipping("0");
        details.setSubtotal(f.format(amt));
        details.setTax("0");

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(f.format(amt));
        amount.setDetails(details);
        transaction.setAmount(amount);
        transaction.setInvoiceNumber(guid);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        return transactions;
    }
}
