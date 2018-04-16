package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLicensePaymentTransactionHistory;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionHistory;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLicensePaymentTransactionHistoryService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionHistoryService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionTypesService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.util.Calendar;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.RelatedResources;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@RequestMapping("/app")
@Controller
public class PaymentController {
	
	@Autowired
	OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	
	@Autowired
	OrgLicenseSubscriptionTypesService orgLicenseSubscriptionTypesService;
	
	@Autowired
	OrgLicenseSubscriptionHistoryService orgLicenseSubscriptionHistoryService;
	
	@Autowired
	OrgLicensePaymentTransactionHistoryService orgLicensePaymentTransactionHistoryService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;

    private final String clientId = "Ac30ZMUM8NcfYN6bFs4RAq3R83_0x8l604YTO2cnFTCpxBlyhCxAB28RZX8sm6kRVooNcWkrlmKFO3jJ";
    private final String clientSecret = "EI1lSaIvZ8t0AjBVh5wK-9CPNK4d8WK0Imy7Tn0PjKua-iNnq_9o5lhxGFPrF0u6gPupv2OTmaD2W3cI";
    private final String paymentMode = "sandbox";

    @RequestMapping("/license")
    public String license(HttpServletRequest request, ModelMap map) {
        return "payment/license";
    }

    @RequestMapping("/init-payment")
    @ResponseBody
    public String initPayment(HttpServletRequest request, String type, @RequestParam(defaultValue = "") String subscriptionUUID, @RequestParam(defaultValue = "0") int model) {

    	OrgLicenseSubscription orgLicenseSubscription = null;
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);

        RedirectUrls redirectUrls = new RedirectUrls();
        String guid = UUID.randomUUID().toString().replaceAll("-", "");
        redirectUrls.setCancelUrl(sourceleadUrl + "/payment-failed.html");
        redirectUrls.setReturnUrl(sourceleadUrl + "/processPayment.html?guid=" + guid);
        if(subscriptionUUID != null && !subscriptionUUID.isEmpty()) {
        	try {
        		orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByUUID(subscriptionUUID);
        		 payment.setTransactions(getTransactions(guid,type,orgLicenseSubscription.getInvoiceAmount()));
        	}catch (Exception e) {
        		e.printStackTrace();
			}
        	
        }else {
        	 payment.setTransactions(getTransactions(guid,type,model == 1 ? 10 : model == 2 ? 20 : 30));
        }
       
        payment.setRedirectUrls(redirectUrls);

        APIContext apiContext = new APIContext(clientId, clientSecret, paymentMode);
        request.getSession().setAttribute("apiContext", apiContext);
        try {
            Payment createdPayment = payment.create(apiContext);
            request.getSession().setAttribute("model", model);
            request.getSession().setAttribute(guid, createdPayment.getId());
            request.getSession().setAttribute("type", type);
            if(orgLicenseSubscription != null) {
            	 request.getSession().setAttribute("subscriptionUUID", subscriptionUUID);
            	 request.getSession().setAttribute("subscriptionId", orgLicenseSubscription.getId());
            }
            
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
            String type = (String) req.getSession().getAttribute("type");
            payment.setTransactions(getTransactions(guid,type,model));

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(req.getParameter("PayerID"));
            APIContext apiContext = (APIContext) req.getSession().getAttribute("apiContext");
            try {
                req.getSession().setAttribute("payId", payment.getId());
                Payment createdPayment = payment.execute(apiContext, paymentExecution);
                req.getSession().setAttribute("createdPayment", createdPayment);
                OrgLicensePaymentTransactionHistory paymentHistory = getTransactionPaymentHistory(createdPayment,req);
                try {
					orgLicensePaymentTransactionHistoryService.saveOrgLicensePaymentTransactionHistory(paymentHistory);
				} catch (Exception e) {
					e.printStackTrace();
				}
                
                
                req.getSession().setAttribute("orderId", paymentHistory.getOrderId());
                req.getSession().setAttribute("transactionId", paymentHistory.getTransactionId());
                req.getSession().setAttribute("amount", paymentHistory.getInvoiceAmount());
             	
                
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
    	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        boolean success = request.getRequestURI().contains("success");
        Payment payment = (Payment) request.getSession().getAttribute("createdPayment");
        if (payment != null) {
        	
        	 String type = (String) request.getSession().getAttribute("type");
        	 if(type.equalsIgnoreCase("renewal")) {
        		 String subscriptionUUID = (String) request.getSession().getAttribute("subscriptionUUID");
        		 if(subscriptionUUID != null && !subscriptionUUID.isEmpty()) {
        			 try {
						OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByUUID(subscriptionUUID);
						OrgLicenseSubscriptionHistory orgLicenseSubscriptionHistory =  commonUtil.getOrgLicenseSubscriptionHistory(orgLicenseSubscription,loginUser);
						
						Date subscriptionDate = null;
						Date subscribedToDate = orgLicenseSubscription.getToDate();
						if(subscribedToDate.after(new Date()) || subscribedToDate.equals(new Date())) {
							String toDateStr = commonUtil.dateStrFromOrgDateFormatter("dd-MM-yyyy",orgLicenseSubscription.getToDate());
							subscriptionDate = commonUtil.orgLocationDateFormat("dd-MM-yyyy",toDateStr);
							subscriptionDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscription.getTimezone().getTimeZoneId(), subscriptionDate, "dd-MM-yyyy");
							//orgLicenseSubscription.setFromDate(subscriptionDate);
						}else{
							subscriptionDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscription.getTimezone().getTimeZoneId(), new Date(), "dd-MM-yyyy");
							//orgLicenseSubscription.setFromDate(subscriptionDate);
						}
						
						Calendar toDate = Calendar.getInstance();
						toDate.setTime(subscriptionDate); 
						
						OrgLicenseSubscriptionTypes orgLicenseSubscriptionTypes = orgLicenseSubscriptionTypesService.getOrgLicenseSubscriptionTypesById(orgLicenseSubscription.getSubscriptionType().getId());
						
					    if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("Free Trail") || orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("1 Month")) {
					    	toDate.add(Calendar.MONTH, +1); 
					    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("6 Months")) {
					    	toDate.add(Calendar.MONTH, +6); 
					    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("1 Year")) {
					    	toDate.add(Calendar.YEAR, +1); 
					    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("2 Years")) {
					    	toDate.add(Calendar.YEAR, +2); 
					    }
					    
					    Date subscriptionToDate = commonUtil.getOrgLocationCurrentDate("dd-MM-yyyy",toDate.getTime());
					    subscriptionToDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscription.getTimezone().getTimeZoneId(), subscriptionToDate, "dd-MM-yyyy");
						orgLicenseSubscription.setToDate(subscriptionToDate);
						orgLicenseSubscription.setIsInvoiceAmountPaid(Constants.YES);
						orgLicenseSubscriptionHistoryService.saveOrgLicenseSubscriptionHistory(orgLicenseSubscriptionHistory);
						orgLicenseSubscriptionService.saveOrgLicenseSubscription(orgLicenseSubscription);
						
					} catch (Exception e) {
						e.printStackTrace();
					} 
        		 }
        	 }
        }
        return success ? "payment/success" : "payment/failed";
    }

    private List<Transaction> getTransactions(String guid,String type,double amt) {
        Transaction transaction = new Transaction();
        transaction.setDescription("This is the payment transaction description.");

        List<Item> items = new ArrayList<>();
        ItemList itemList = new ItemList();

        DecimalFormat f = new DecimalFormat("##.00");
        Item item = new Item();
	       
        if(type.equalsIgnoreCase("license")) {
	    	   item.setName("Licence Upgrade");
	       }else if(type.equalsIgnoreCase("renewal")) {
	    	   item.setName("Licence Renewal");
         }
        
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
    
    
    public OrgLicensePaymentTransactionHistory getTransactionPaymentHistory(Payment createdPayment,HttpServletRequest req) {
    	 OrgLicensePaymentTransactionHistory paymentHistory = new OrgLicensePaymentTransactionHistory();
    	 String type = (String) req.getSession().getAttribute("type");
    	 Long subscriptionId = (Long) req.getSession().getAttribute("subscriptionId");
    	 if(subscriptionId != null) {
    		 OrgLicenseSubscription subscription = new OrgLicenseSubscription();
    		 subscription.setId(subscriptionId);
    		 paymentHistory.setOrgLicenseSubscriptionId(subscription);
    	 }
    	 paymentHistory.setPaymentId(createdPayment.getId());
    	 paymentHistory.setLinenseType(type);
    	 StringBuilder transactionUser = new StringBuilder();
    	 String paymentUser = transactionUser.append(createdPayment.getPayer().getPayerInfo().getFirstName()).append(" ").append(createdPayment.getPayer().getPayerInfo().getLastName()).toString();
    	 paymentHistory.setTransactionUser(paymentUser);
    	 paymentHistory.setTransactionUserEmail(createdPayment.getPayer().getPayerInfo().getEmail());
    	 
    	 Transaction transactions = createdPayment.getTransactions().iterator().next();
     	 RelatedResources resources = transactions.getRelatedResources().iterator().next();
     	 paymentHistory.setOrderId(SourceLeadRandomCodeHelper.generateRandomTransactionOrderId());
     	 paymentHistory.setTransactionId(resources.getSale().getId());
     	 paymentHistory.setCurrency(resources.getSale().getAmount().getCurrency());
     	 paymentHistory.setInvoiceAmount(Double.valueOf(resources.getSale().getAmount().getTotal()));
     	 paymentHistory.setReceiptId(resources.getSale().getReceiptId());
     	 paymentHistory.setTransactionPayerId(createdPayment.getPayer().getPayerInfo().getPayerId());
     	 paymentHistory.setInvoiceNumber(transactions.getInvoiceNumber());
     	 paymentHistory.setTransactionDate(new Date());
     	 return paymentHistory;
    }
}
