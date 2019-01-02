package com.bis.sourcelead.web;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.InvoicePaymentDTO;
import com.bis.sourcelead.dto.OrgLicenseSubscriptionDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateMarketCart;

import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlacePaymentTransactionHistory;
import com.bis.sourcelead.entity.CandidateMarketWallet;
import com.bis.sourcelead.entity.EmailAttachment;

import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicensePaymentTransactionHistory;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionHistory;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.InvoicePaymentService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgLicensePaymentTransactionHistoryService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionHistoryService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionTypesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
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
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;


@RequestMapping("/app")
@Controller
public class PaymentController {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    OrgLicenseSubscriptionService orgLicenseSubscriptionService;

    @Autowired
    OrgLicenseSubscriptionTypesService orgLicenseSubscriptionTypesService;

    @Autowired
    OrgLicenseSubscriptionHistoryService orgLicenseSubscriptionHistoryService;

    @Autowired
    OrgLicensePaymentTransactionHistoryService orgLicensePaymentTransactionHistoryService;

    @Autowired
    private InvoicePaymentService invoicePaymentService;
    
    @Autowired
    private UserService userService;

    @Autowired
	private CandidateService candidateService;
    
    @Autowired
	EmailScenarioService emailScenarioService;
    
    @Autowired
	EmailScenerioUtil emailScenerioUtil;
    
    @Value("${sourceleadUrl}")
    private String sourceleadUrl;

    @Autowired
    private CommonUtil commonUtil;

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${wepay.accountid}")
    private String wepayAccountId;

    @Value("${wepay.accesstoken}")
    private String wepayAccessToken;

    @Value("${wepay.url}")
    private String wepayURL;

    @Value("${paypal.clientSecret}")
    private String clientSecret;

    @Value("${paypal.paymentMode}")
    private String paymentMode;
    
    @Value("${paymentSuccessReturnEmail}")
	private String paymentSuccessReturnEmail;

    @Value("${env}")
    private String environment;
    
    @Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;	

    @RequestMapping("/license")
    public String license(HttpServletRequest request, ModelMap map) {
        return "payment/license";
    }

    @RequestMapping("/init-payment")
    @ResponseBody
    public String initPayment(HttpServletRequest request, String type, @RequestParam(defaultValue = "") String subscriptionUUID,
            @RequestParam(defaultValue = "0") int model, @RequestParam(defaultValue = "") String registeredModules, OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO) {

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
        if (subscriptionUUID != null && !subscriptionUUID.isEmpty()) {
            try {
                orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByUUID(subscriptionUUID);
                if (orgLicenseSubscriptionDTO.getInvoiceAmount() != null) {
                    payment.setTransactions(getTransactions(guid, type, orgLicenseSubscriptionDTO.getInvoiceAmount()));
                } else {
                    payment.setTransactions(getTransactions(guid, type, orgLicenseSubscription.getInvoiceAmount()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            payment.setTransactions(getTransactions(guid, type, model == 1 ? 10 : model == 2 ? 20 : 30));
        }

        payment.setRedirectUrls(redirectUrls);

        APIContext apiContext = new APIContext(clientId, clientSecret, paymentMode);
        request.getSession().setAttribute("apiContext", apiContext);
        try {
            Payment createdPayment = payment.create(apiContext);
            request.getSession().setAttribute("model", model);
            request.getSession().setAttribute(guid, createdPayment.getId());
            request.getSession().setAttribute("type", type);
            request.getSession().setAttribute("orgLicenseSubscriptionDTO", orgLicenseSubscriptionDTO);
            if (orgLicenseSubscription != null) {
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
            payment.setTransactions(getTransactions(guid, type, model));

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(req.getParameter("PayerID"));
            APIContext apiContext = (APIContext) req.getSession().getAttribute("apiContext");
            try {
                req.getSession().setAttribute("payId", payment.getId());
                Payment createdPayment = payment.execute(apiContext, paymentExecution);
                if (createdPayment != null && createdPayment.getState().equalsIgnoreCase("approved")) {

                    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(req, "user");
                    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(req, "org");
                    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(req, "orgLocations");

                    req.getSession().setAttribute("createdPayment", createdPayment);
                    OrgLicensePaymentTransactionHistory paymentHistory = getTransactionPaymentHistory(createdPayment, req);
                    try {
                        orgLicensePaymentTransactionHistoryService.saveOrgLicensePaymentTransactionHistory(paymentHistory);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    req.getSession().setAttribute("orderId", paymentHistory.getOrderId());
                    req.getSession().setAttribute("transactionId", paymentHistory.getTransactionId());
                    req.getSession().setAttribute("amount", paymentHistory.getInvoiceAmount());
                    Transaction transactions = createdPayment.getTransactions().iterator().next();
                    Item item = transactions.getItemList().getItems().iterator().next();
                    req.getSession().setAttribute("subscriptionType", item.getName());
                    paymentHistory.setSubscriptionType(item.getName());

                    try {
                        commonUtil.paymentAcknowledgementMail(paymentHistory, loginUser, org, orgLocations);
                        if(orgLocations.getUser() != null) {
                      		  User locationPrimaryUser = userService.getUserById(orgLocations.getUser().getId());
                      		  paymentHistory.setTransactionUser(locationPrimaryUser.getFullName());
                      		  paymentHistory.setTransactionUserEmail(locationPrimaryUser.getEmail());
                        	  commonUtil.paymentAcknowledgementMail(paymentHistory, loginUser, org, orgLocations);
                        }
                        
                        if(org.getUser() != null) {
                        	  User orgPrimaryUser = userService.getUserById(org.getUser().getId());
                     		  paymentHistory.setTransactionUser(orgPrimaryUser.getFullName());
                     		  paymentHistory.setTransactionUserEmail(orgPrimaryUser.getEmail());
                       	      commonUtil.paymentAcknowledgementMail(paymentHistory, loginUser, org, orgLocations);
                        }

                        if (environment.equalsIgnoreCase("prod")) {
                            commonUtil.paymentAcknowledgementDetailsMail(paymentHistory, loginUser, org, orgLocations);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
        if (payment != null && success == true && payment.getState().equalsIgnoreCase("approved")) {
            User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
            Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
            String type = (String) request.getSession().getAttribute("type");
            OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO = (OrgLicenseSubscriptionDTO) request.getSession().getAttribute("orgLicenseSubscriptionDTO");

            String subscriptionUUID = (String) request.getSession().getAttribute("subscriptionUUID");
            if (subscriptionUUID != null && !subscriptionUUID.isEmpty()) {
                try {

                    if (type.equalsIgnoreCase("renewal")) {
                        OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByUUID(subscriptionUUID);
                        OrgLicenseSubscriptionHistory orgLicenseSubscriptionHistory = commonUtil.getOrgLicenseSubscriptionHistory(orgLicenseSubscription, loginUser);
                        Date subscriptionDate = null;
                        Date subscribedToDate = orgLicenseSubscription.getToDate();
                        if (subscribedToDate.after(new Date()) || subscribedToDate.equals(new Date())) {
                            String toDateStr = commonUtil.dateStrFromOrgDateFormatter("dd-MM-yyyy", orgLicenseSubscription.getToDate());
                            subscriptionDate = commonUtil.orgLocationDateFormat("dd-MM-yyyy", toDateStr);
                            subscriptionDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscription.getTimezone().getTimeZoneId(), subscriptionDate, "dd-MM-yyyy");
                            //orgLicenseSubscription.setFromDate(subscriptionDate);
                        } else {
                            subscriptionDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscription.getTimezone().getTimeZoneId(), new Date(), "dd-MM-yyyy");
                            //orgLicenseSubscription.setFromDate(subscriptionDate);
                        }

                        Calendar toDate = Calendar.getInstance();
                        toDate.setTime(subscriptionDate);

                        OrgLicenseSubscriptionTypes orgLicenseSubscriptionTypes = orgLicenseSubscriptionTypesService.getOrgLicenseSubscriptionTypesById(orgLicenseSubscription.getSubscriptionType().getId());

                        if (orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("Free Trail") || orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("1 Month")) {
                            toDate.add(Calendar.MONTH, +1);
                        } else if (orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("6 Months")) {
                            toDate.add(Calendar.MONTH, +6);
                        } else if (orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("1 Year")) {
                            toDate.add(Calendar.YEAR, +1);
                        } else if (orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("2 Years")) {
                            toDate.add(Calendar.YEAR, +2);
                        }

                        Date subscriptionToDate = commonUtil.getOrgLocationCurrentDate("dd-MM-yyyy", toDate.getTime());
                        subscriptionToDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscription.getTimezone().getTimeZoneId(), subscriptionToDate, "dd-MM-yyyy");
                        orgLicenseSubscription.setToDate(subscriptionToDate);
                        orgLicenseSubscription.setInvoiceAmount(0d);
                        orgLicenseSubscription.setIsInvoiceAmountPaid(Constants.YES);
                        orgLicenseSubscriptionHistoryService.saveOrgLicenseSubscriptionHistory(orgLicenseSubscriptionHistory);
                        orgLicenseSubscriptionService.saveOrgLicenseSubscription(orgLicenseSubscription);
                    } else if (type.equalsIgnoreCase("pay")) {
                        OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByUUID(subscriptionUUID);
                        OrgLicenseSubscriptionHistory orgLicenseSubscriptionHistory = commonUtil.getOrgLicenseSubscriptionHistory(orgLicenseSubscription, loginUser);
                        orgLicenseSubscription.setInvoiceAmount(0d);
                        orgLicenseSubscription.setIsInvoiceAmountPaid(Constants.YES);
                        orgLicenseSubscriptionHistoryService.saveOrgLicenseSubscriptionHistory(orgLicenseSubscriptionHistory);
                        orgLicenseSubscriptionService.saveOrgLicenseSubscription(orgLicenseSubscription);
                    } else if (type.equalsIgnoreCase("upgrade")) {
                        orgLicenseSubscriptionService.upgradeLicenseSubscription(orgLicenseSubscriptionDTO, loginUser, org.getId(), subscriptionUUID);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return success ? "payment/subscriptionPaymentSuccess" : "payment/failed";
    }

    private List<Transaction> getTransactions(String guid, String type, double amt) {
        Transaction transaction = new Transaction();
        transaction.setDescription("This is the payment transaction description.");

        List<Item> items = new ArrayList<>();
        ItemList itemList = new ItemList();

        DecimalFormat f = new DecimalFormat("##.00");
        Item item = new Item();

        if (type.equalsIgnoreCase("license")) {
            item.setName("Licence Upgrade");
        } else if (type.equalsIgnoreCase("renewal")) {
            item.setName("Licence Renewal");
        } else if (type.equalsIgnoreCase("pay")) {
            item.setName("Licence Payment");
        } else if (type.equalsIgnoreCase("upgrade")) {
            item.setName("Licence Upgrade");
        }
        else if (type.equalsIgnoreCase("markeplace")) {
            item.setName("markeplace");
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

    public OrgLicensePaymentTransactionHistory getTransactionPaymentHistory(Payment createdPayment, HttpServletRequest req) {
        OrgLicensePaymentTransactionHistory paymentHistory = new OrgLicensePaymentTransactionHistory();
        String type = (String) req.getSession().getAttribute("type");
        Long subscriptionId = (Long) req.getSession().getAttribute("subscriptionId");
        if (subscriptionId != null) {
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

    @RequestMapping(value = "/wepay-iframe-payment", method = RequestMethod.POST)
    @ResponseBody
    public String wePayIFamePayment(HttpServletRequest request, String invoiceUUID, double amount, String notes, int paymentType) {
        try {
            Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
            if (invoice.getPaymentAccount() == null) {
                invoice = invoiceService.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
            }
            OkHttpClient client = new OkHttpClient();
            JSONObject json = new JSONObject();
            json.put("account_id", invoice.getPaymentAccount().getAccountId());
            json.put("type", "donation");
            json.put("short_description", "Invoice Payment");
            json.put("currency", "USD");
            json.put("amount", amount);

            JSONObject hostedCheckout = new JSONObject();
            hostedCheckout.put("redirect_uri", sourceleadUrl + "/wepay-success.html?invoiceId=" + invoice.getId());
            json.put("hosted_checkout", hostedCheckout);

            JSONObject fee = new JSONObject();
            fee.put("fee_payer", "payee_from_app");
//            fee.put("app_fee", 0);
            json.put("fee", fee);

            RequestBody body = RequestBody.create(JSON, json.toString());
            Request req = new Request.Builder().url(wepayURL + "/v2/checkout/create")
                    .addHeader("Authorization", "Bearer " + invoice.getPaymentAccount().getAccessToken())
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            Response res = client.newCall(req).execute();
            JSONObject resp = new JSONObject(res.body().string());
            if (resp.has("state") && (resp.getString("state").equalsIgnoreCase("new"))) {
                OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
                InvoicePaymentDTO payment = new InvoicePaymentDTO();
                payment.setInvoiceId(invoice.getInvoiceId());
                payment.setPaymentAmount(paymentType == 1 ? "total" : "custom");
                payment.setTotalAmount(invoice.getDueAmount());
                payment.setCustomeAmount("" + (paymentType == 1 ? 0 : amount));
                payment.setPaymentMemo(notes);
                payment.setPaymentMethod("Online (WePay)");
                payment.setReferenceNumber(resp.get("checkout_id").toString());
                payment.setPaymentDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date()));
                request.getSession().setAttribute("invoice", invoice);
                request.getSession().setAttribute("payment", payment);
                return resp.getJSONObject("hosted_checkout").getString("checkout_uri");
            }
            request.getSession().removeAttribute("invoice");
            request.getSession().removeAttribute("payment");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "FAILED";
    }

    @RequestMapping(value = {"/wepay-success.html"})
    public String wepaySuccess(HttpServletRequest request, ModelMap map, Long invoiceId) {
        try {
            Invoice invoice = (Invoice) request.getSession().getAttribute("invoice");
            InvoicePaymentDTO payment = (InvoicePaymentDTO) request.getSession().getAttribute("payment");
            if (invoice != null && Objects.equals(invoice.getId(), invoiceId)) {
                List<InvoicePayment> list = invoicePaymentService.getInvoicePaymentList(invoiceId);
                boolean flag = true;
                if (list != null) {
                    for (InvoicePayment ip : list) {
                        if (ip.getPaymentReferenceNumber().equals(payment.getReferenceNumber())) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    json.put("checkout_id", payment.getReferenceNumber());

                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request req = new Request.Builder().url(wepayURL + "/v2/checkout")
                            .addHeader("Authorization", "Bearer " + invoice.getPaymentAccount().getAccessToken())
                            .addHeader("Content-Type", "application/json")
                            .post(body)
                            .build();

                    Response res = client.newCall(req).execute();
                    JSONObject resp = new JSONObject(res.body().string());
                    if (resp.has("state") && (resp.getString("state").equalsIgnoreCase("authorized")
                            || resp.getString("state").equalsIgnoreCase("captured")
                            || resp.getString("state").equalsIgnoreCase("released"))) {
                        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
                        User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
                        InvoicePayment invoicePayment = invoicePaymentService.saveInvoicePayment(payment, user, orgLocation, null, invoice.getId());
                        request.getSession().removeAttribute("invoice");
                        request.getSession().removeAttribute("payment");
                        if (null != invoicePayment) {
                            map.addAttribute("invoice", invoice);
                            map.addAttribute("payment", payment);
                            return "payment/success";
                        }

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getSession().removeAttribute("invoice");
        request.getSession().removeAttribute("payment");
        return "payment/failed";
    }
   //===================== Marketplace Payment Process Starts Here=========================
    @PreAuthorize("hasRole('ROLE_PURCHASE_PROFILE')")
    @RequestMapping("/init-marketplace-payment")
    @ResponseBody
    public String initMarketplacePayment(HttpServletRequest request, @RequestParam(defaultValue = "") String cartId) {
    	Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);

        RedirectUrls redirectUrls = new RedirectUrls();
        String guid = UUID.randomUUID().toString().replaceAll("-", "");
        redirectUrls.setCancelUrl(sourceleadUrl + "/marketplace-payment-failed.html");
        redirectUrls.setReturnUrl(sourceleadUrl + "/marketplace-process-payment.html?guid=" + guid);
        payment.setRedirectUrls(redirectUrls);

        APIContext apiContext = new APIContext(clientId, clientSecret, paymentMode);
        request.getSession().setAttribute("apiContext", apiContext);
       
        System.out.println("Code is updated :::::::::::::::>");
        
        try{
        User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 if(user != null){
			if(StringUtils.isNotBlank(userRoleName)) {
				Object[] countAndSum = candidateService.updateMarketplaceCartCountAndPrice(orgLocation.getId(), userRoleName, user);
		        Double totalprice = Double.parseDouble(countAndSum[1].toString());
		        payment.setTransactions(getTransactions(guid, "markeplace", totalprice));
			}}
			}catch(Exception e){
        	e.printStackTrace();}
		
        try {
            Payment createdPayment = payment.create(apiContext);
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
            return "redirect:/app/marketplace-payment-failed.html";
        }
        return null;
    }
    @RequestMapping(value = "/marketplace-process-payment.html")
    public String processMarketplacePayment(HttpServletRequest req, HttpServletResponse resp, String guid) {
        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(req, "user");
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(req, "org");
        String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(req, "loginUserRole");
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(req, "orgLocations");

    	if (req.getParameter("PayerID") != null) {
            Payment payment = new Payment();
            if (req.getParameter("guid") != null) {
                payment.setId(req.getSession().getAttribute(req.getParameter("guid")).toString());
            }
            	// need to update the actual amount for the transaction for the marketplace.
            try{
            if(loginUser != null){
				if(StringUtils.isNotBlank(userRoleName)) {
					Object[] countAndSum = candidateService.updateMarketplaceCartCountAndPrice(orgLocations.getId(), userRoleName, loginUser);
			        Double totalprice = Double.parseDouble(countAndSum[1].toString());
			        payment.setTransactions(getTransactions(guid, "marketplace", totalprice));
				}}}catch(Exception e){
            	e.printStackTrace();
            }
            
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(req.getParameter("PayerID"));
            APIContext apiContext = (APIContext) req.getSession().getAttribute("apiContext");
            try {
                req.getSession().setAttribute("payId", payment.getId());
                Payment createdPayment = payment.execute(apiContext, paymentExecution);
                if (createdPayment != null && createdPayment.getState().equalsIgnoreCase("approved")) {


                    req.getSession().setAttribute("createdPayment", createdPayment);
                    OrgLicensePaymentTransactionHistory paymentHistory = getTransactionPaymentHistory(createdPayment, req);
                    try {
                        orgLicensePaymentTransactionHistoryService.saveOrgLicensePaymentTransactionHistory(paymentHistory);
                        
                        List<CandidateMarketCart> marketCartList = candidateService.getMarketCartIdsFromMarketcart(orgLocations.getId());
                        List<Long> marketCartIds = new ArrayList<Long>();
                    	for(CandidateMarketCart cartItem : marketCartList){
                    		marketCartIds.add(cartItem.getId());
                    	}
                    	if(marketCartIds != null && marketCartIds.size() > 0){
                    		CandidateMarketPlacePaymentTransactionHistory tranHistory = new CandidateMarketPlacePaymentTransactionHistory();
                    		CandidateMarketWallet wallet = new CandidateMarketWallet();
                			tranHistory.setId(paymentHistory.getId());
                			tranHistory.setOrgLocationId(orgLocations);
                			if(marketCartIds != null && marketCartIds.size() > 0){
                				String ids = StringUtils.join(marketCartIds, ",");
                				tranHistory.setMarketPlaceCartIds(ids);
                			}
                			tranHistory.setCurrency(paymentHistory.getCurrency());
                			tranHistory.setInvoiceAmount(paymentHistory.getInvoiceAmount());
                			tranHistory.setPaymentId(paymentHistory.getPaymentId());
                			tranHistory.setOrderId(paymentHistory.getOrderId());
                			tranHistory.setInvoiceNumber(paymentHistory.getInvoiceNumber());
                			tranHistory.setTransactionId(paymentHistory.getTransactionId());
                			tranHistory.setTransactionPayerId(paymentHistory.getTransactionPayerId());
                			tranHistory.setTransactionUser(paymentHistory.getTransactionUser());
                			tranHistory.setTransactionUserEmail(paymentHistory.getTransactionUserEmail());
                			tranHistory.setTransactionDate(paymentHistory.getTransactionDate());
                    		candidateService.saveCandidateMarketplacePaymentTranscationHistory(tranHistory);
                    		
                    		/*Save CandidateMarketPlaceWallet table*/
                    		
                    		wallet.setWalletUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
                    		wallet.setAmount(paymentHistory.getInvoiceAmount());
                    		wallet.setAmountType(paymentHistory.getCurrency());
                    		wallet.setMarketPlacePaymentTransactionHistory(tranHistory);
                    		wallet.setOrgLocationId(orgLocations);
                    		wallet.setPaymentStatus(Constants.CREDIT_NOTE);
                    		wallet.setTransactionId(paymentHistory.getTransactionId());
                    		Date formattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), paymentHistory.getTransactionDate());
                    		wallet.setTransactionDate(formattedDate);
                    		wallet.setDescription(null);
                    		Double balanceAmount = candidateService.getBalanceAmountFromWallet(orgLocations.getId());
                    		Double balanceAmountValue = paymentHistory.getInvoiceAmount();
                    		if(balanceAmount != null){
                    			balanceAmountValue = balanceAmount + balanceAmountValue;
                    		}
                    		wallet.setTotalBalanceAmount(balanceAmountValue);
                    		candidateService.saveCandidateMarketPlaceWallet(wallet);
                    		candidateService.updateMarketWalletAmount(balanceAmountValue, orgLocations.getId());
                    	}
                    	} catch (Exception e) {
                        e.printStackTrace();
                    }

                    req.getSession().setAttribute("orderId", paymentHistory.getOrderId());
                    req.getSession().setAttribute("transactionId", paymentHistory.getTransactionId());
                    req.getSession().setAttribute("amount", paymentHistory.getInvoiceAmount());
                    Transaction transactions = createdPayment.getTransactions().iterator().next();
                    Item item = transactions.getItemList().getItems().iterator().next();

                    try {
                        commonUtil.paymentAcknowledgementMail(paymentHistory, loginUser, org, orgLocations);
                        if(orgLocations.getUser() != null) {
                      		  User locationPrimaryUser = userService.getUserById(orgLocations.getUser().getId());
                      		  paymentHistory.setTransactionUser(locationPrimaryUser.getFullName());
                      		  paymentHistory.setTransactionUserEmail(locationPrimaryUser.getEmail());
                        	  commonUtil.paymentAcknowledgementMail(paymentHistory, loginUser, org, orgLocations);
                        }
                        
                        if(org.getUser() != null) {
                        	  User orgPrimaryUser = userService.getUserById(org.getUser().getId());
                     		  paymentHistory.setTransactionUser(orgPrimaryUser.getFullName());
                     		  paymentHistory.setTransactionUserEmail(orgPrimaryUser.getEmail());
                       	      commonUtil.paymentAcknowledgementMail(paymentHistory, loginUser, org, orgLocations);
                        }

                        if (environment.equalsIgnoreCase("prod")) {
                            commonUtil.paymentAcknowledgementDetailsMail(paymentHistory, loginUser, org, orgLocations);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return "redirect:/app/marketplace-payment-success.html";
                } else {
                    return "redirect:/app/marketplace-payment-failed.html";
                }
            } catch (PayPalRESTException e) {
                return "redirect:/marketplace-payment-failed.html";
            }
        }
        return null;
    }
    @RequestMapping(value = {"/marketplace-payment-success.html", "/marketplace-payment-failed.html"})
    public String paymentMarketPlaceResponse(HttpServletRequest request, ModelMap map) {

        boolean success = request.getRequestURI().contains("success");
        Payment payment = (Payment) request.getSession().getAttribute("createdPayment");
        if (payment != null && success == true && payment.getState().equalsIgnoreCase("approved")) {
            User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
            Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			EmailMessage emailMessage = new EmailMessage();
          //  String subscriptionUUID = (String) request.getSession().getAttribute("subscriptionUUID");
            if (org != null) {
                try {
                    // need to copy th bve candidate to buyer/target organization location.
                	
                	List<CandidateMarketCart>  marketCartList = candidateService.getMarketCartIdsFromMarketcart(orgLocations.getId());
                	List<Long> candidateIds = new ArrayList<Long>();
                	List<Long> cartIds = new ArrayList<Long>();
                	Map<Long , List<Long>> orgAndCandidatesMap = new HashMap<Long , List<Long>>();
                	for(CandidateMarketCart cartItem : marketCartList){
                	cartIds.add(cartItem.getId());
                	candidateIds.add(cartItem.getCandidateId().getId());
                	if(orgAndCandidatesMap.get(cartItem.getSourceOrgLocationId().getId()) == null){
                	List<Long> mailCandidateIds = new ArrayList<Long>();
                	mailCandidateIds.add(cartItem.getCandidateId().getId());
                	orgAndCandidatesMap.put(cartItem.getSourceOrgLocationId().getId(), mailCandidateIds);
                	}else {
                	orgAndCandidatesMap.get(cartItem.getSourceOrgLocationId().getId()).add((cartItem.getCandidateId().getId()));
                	}
                	}
                	List<CandidateDTO> candidateDto = null;
                	if(candidateIds != null && candidateIds.size() > 0){
                	candidateDto = candidateService.getCandidatesListByIds(candidateIds); 
                	}
                	Boolean flag = candidateService.updateMarketCartStatus(cartIds, orgLocations.getId());
                	
                	if(flag == true){
                		for(int i = 0; i < candidateDto.size(); i++){
                    		candidateService.saveTransactionCompletedCandidates(candidateDto.get(i), orgLocations, loginUser);
                    	}
                	}
                	
                	System.out.println("org value is "+org.getId());

                  /*=====================Email Scenario for the profile contributor starts here=====================*/
                	
                	EmailMessage emailMessage1 = new EmailMessage();
                    // Getting the payment transaction details from the table. It will always be single record.
                	CandidateMarketPlacePaymentTransactionHistory transactionDetails = candidateService.getMarketplaceTransactionDetails(orgLocations.getId());
                	// Getting the purchased organization name for email.
                	String orgName= transactionDetails.getOrgLocationId().getOrg().getOrgName();
					Set<Long> keySet =  orgAndCandidatesMap.keySet();
					List<Long> aList = keySet.stream().collect(Collectors.toList());
					String numberOfResumes = null;
					User userDetails = null;
					for(Long sourceOrgLocationId : aList){
				    // Getting primary contact user details by passing the source org location from the above list. It will always be single record.
					userDetails = candidateService.getUserDetailsBySourceOrgLocationId(sourceOrgLocationId);
					StringBuilder sb1 = new StringBuilder(); 
                	StringBuilder cb = new StringBuilder();
                	Map<String, String> globalVarsMap1 = new HashMap<String, String>(); 
                	emailMessage1.setEmailType(Constants.CUSTOM_USER_EMAIL);
                	emailMessage1.setFirstName(userDetails.getFirstName());
                		if(null != userDetails.getLastName() && !"".equalsIgnoreCase(userDetails.getLastName())){ 
                			sb1.delete(0, sb1.length());
                			sb1.append(userDetails.getFirstName()).append(" ").append(userDetails.getLastName()).toString();
                			globalVarsMap1.put("contributorName", sb1.toString()); 
                		}else{ 
                			sb1.delete(0, sb1.length());
            				sb1.append(userDetails.getFirstName()).toString();
            				globalVarsMap1.put("contributorName", sb1.toString()); 
            			}
                		emailMessage1.setToEmail(userDetails.getEmail()); 
                		emailMessage1.setFromEmail(paymentSuccessReturnEmail);
                		globalVarsMap1.put("toEmail", userDetails.getEmail());
                		List<Long> candidateIdList = orgAndCandidatesMap.get(sourceOrgLocationId);
                		numberOfResumes = String.valueOf(candidateIdList.size());
                		Date date = transactionDetails.getTransactionDate();  
                		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
                		String transactionDate = dateFormat.format(date);
                		int amount=0;
                		for(Long candidateId : candidateIdList){
                		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
                		if(null != candidate.getLastName() && !"".equalsIgnoreCase(candidate.getLastName())){ 
                			cb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString();
                			
                		}else{ 
            				cb.append(candidate.getFirstName()).toString();
                		}
                		//Getting the candidate details from the candidate market place table. It will be a single records.
                		CandidateMarketPlace candidateDetails = candidateService.getCandidateDetailsByCandidateId(candidateId);
                		           cb.append("-"+candidateDetails.getMarketPrice()+"<br>");
                		           amount += Double.parseDouble(candidateDetails.getMarketPrice());
                		}
                		
                		globalVarsMap1.put("numberOfResumes", numberOfResumes);
                		globalVarsMap1.put("transactionId", transactionDetails.getTransactionId());
                		globalVarsMap1.put("orderedCompanyName", orgName);
                		globalVarsMap1.put("totalAmount", String.valueOf(amount) );
                		globalVarsMap1.put("date", transactionDate);
                		globalVarsMap1.put("candidateDetails", cb.toString());
                		if(emailMessage1 !=null && globalVarsMap1 != null){ 
                			commonUtil.sendTemplateEmail(emailMessage1, globalVarsMap1, orgLocations.getOrg(), orgLocations, Constants.PURCHASED_CANDIDATE_DETAILS_TO_CONTRIBUTOR, new ArrayList<EmailAttachment>());	
                			} 
                			cb.delete(0, cb.length());
                	 }
					
					/*=====================Email Scenario for the profile contributor ends here=====================*/
                	
                	System.out.println("org value is "+org.getId());
                	
                	CandidateMarketPlacePaymentTransactionHistory transactionHistory = candidateService.getOrderNo(orgLocations.getId());
                	String purchasedOrgName = transactionHistory.getOrgLocationId().getOrg().getOrgName();
                	String orderNo = transactionHistory.getOrderId();
                	String paymentId = transactionHistory.getPaymentId();
                	candidateService.saveToMarketplacePurchaseOrder(marketCartList,orgLocations,orderNo,purchasedOrgName,paymentId);
                	candidateService.saveToMarketplaceSalesOrder(marketCartList,orderNo,paymentId);
                	//candidateService.saveMarketPlaceWallet();

                	//sending Email.
                	StringBuilder currentUserName = new StringBuilder();
                	StringBuilder bodyBuilder = new StringBuilder();
                	StringBuilder candidateName = new StringBuilder();
                	CandidateMarketPlace marketPlace = null;
                	String marketPrice = null;
                	Map<String, String> globalVarsMap = new HashMap<String, String>();
                	for(Long sourceOrgLocationId : aList){
    					userDetails = candidateService.getUserDetailsBySourceOrgLocationId(sourceOrgLocationId);
    					emailMessage.setToEmail(userDetails.getEmail());
    					globalVarsMap.put("otherUser", userDetails.getFirstName()+" "+userDetails.getLastName());
    				if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
          				 currentUserName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString();
          				 globalVarsMap.put("currentUser", currentUserName.toString());
          			 	}else{
          				 globalVarsMap.put("currentUser", loginUser.getFirstName());
          			 	}
                	if(candidateDto !=null && candidateDto.size() > 0){
                			emailMessage.setFromEmail(defaultFromEmailAddressForProd);
                			emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
                			emailMessage.setCreatedBy(loginUser.getUserCode());
                			emailMessage.setOrgLocationId(orgLocations.getId());
                			emailMessage.setFirstName("Source Lead.");
                			globalVarsMap.put("dateAndTime", new Date().toString());
                			
                			for(int i=0; i< candidateDto.size(); i++){
                				candidateName.delete(0, candidateName.length());
                				candidateName.append(candidateDto.get(i).getFirstName()+" "+candidateDto.get(i).getLastName()).toString();
                				marketPlace = candidateService.getCandidateDetailsByCandidateId(candidateDto.get(i).getId());
                				marketPrice = "<strong style='color: #f60;'>&#36; "+(marketPlace.getMarketPrice() == null ? "---" : marketPlace.getMarketPrice())+"</strong>";
                			
                			 bodyBuilder.append("<table width='100%' style='border-collapse:collapse; border: 1px solid #ddd; font-family: 'Helvetica Neue', Helvetica, Arial;'>");
                			 bodyBuilder.append("<tr>");
                			 bodyBuilder.append("<td style='width: 20%; vertical-align: top; border-right:1px solid #f5f5f5;'>");
                			 bodyBuilder.append("<div style='width: 75px; height: 75px; margin: 10px auto 5px; padding: 0; text-align: center;'>");
                			 bodyBuilder.append("<img src='"+sourceleadUrl.split("/app")[0]+(candidateDto.get(i).getFileUrl() == null ? "/resources/images/userprofileImg.jpg" : candidateDto.get(i).getFileUrl())+"' style='max-height: 100%; max-width: 100%' alt='img'>");
                			 bodyBuilder.append("</div>");
                			 bodyBuilder.append("<div style='font-size: 14px; clear: both; display: inline-block; width: 100%; text-align: center; margin-bottom: 10px;'>");
                			 bodyBuilder.append("<span>Price: </span>");
                			 bodyBuilder.append(marketPrice);
                			 bodyBuilder.append("</div>");
                			 bodyBuilder.append("<div style='clear: both; display: inline-block; width: 100%; text-align: center; margin-bottom: 10px;'>");
                			 bodyBuilder.append("<button type='button' style='background-color: #fff; border: 1px solid #0277BD; color: #0277BD; padding:3px 10px; border-radius: 3px; cursor: pointer;'>&#43; Add</button>");
                			 bodyBuilder.append("</div>");
                			 bodyBuilder.append("</td>");
                			 bodyBuilder.append("<td style='width:80%; vertical-align: top;'>");
                			 bodyBuilder.append("<div style='display: inline-block; clear: both; width: 100%; ertical-align: top; padding: 10px;'>");
                			 bodyBuilder.append("<h4 style='margin: 0 0 5px 0; padding: 0; color:#368ee0; font-size: 18px'>"+(candidateName.toString() == null ? "--" : candidateName.toString())+"</h4>");
                			 bodyBuilder.append("<p style='margin:0 0 10px 0; padding: 0; color: #777; font-size: 14px;'>"+(candidateDto.get(i).getDesignation() == null ? "--" : candidateDto.get(i).getDesignation())+"</p>");
                			 bodyBuilder.append("<ul style='list-style-position: inside; margin: 0; padding: 0; font-size: 14px;'>");
                			 bodyBuilder.append("<li style='float: left; padding-right: 20px;'>");
                			 bodyBuilder.append("<strong style='font-size: 13px; padding-right: 10px; color: #777;'>Current:</strong>"+(candidateDto.get(i).getCurrentRate() == null ? "--" : candidateDto.get(i).getCurrentRate())+" / hr");
                			 bodyBuilder.append("</li>");
                			 bodyBuilder.append("<li style='float: left; padding-right: 20px;'>");
                			 bodyBuilder.append("<strong style='font-size: 13px; padding-right: 10px; color: #777;'>Expected:</strong>"+(candidateDto.get(i).getExpectedRate() == null ? "--" : candidateDto.get(i).getExpectedRate())+" + / earned");
                			 bodyBuilder.append("</li>");
                			 bodyBuilder.append("<li style='float: left; padding-right: 20px;'>");
                			 bodyBuilder.append("<strong style='font-size: 13px; padding-right: 10px; color: #777;'>Location:</strong>"+(candidateDto.get(i).getLocation() == null ? "--" : candidateDto.get(i).getLocation())+"");
                			 bodyBuilder.append("</li>");
                			 bodyBuilder.append("</ul>");
                			 bodyBuilder.append("<div style='display: inline-block; clear: both; width: 100%; margin-top: 15px;'>");
                			 bodyBuilder.append("<ul style='color: #333; list-style-type: none; margin: 0; padding: 0; font-size: 14px;'>");
                			 if(candidateDto.get(i).getSkillsName() != null){
                				 String candidateSkills[] = candidateDto.get(i).getSkillsName().split(",");
                			 for(int j = 0; j < candidateSkills.length;){
                				 bodyBuilder.append("<li style='float: left; background-color: #ededed; border: 1px solid #ededed; border-radius: 2px; padding: 2px 5px; margin-right: 10px; margin-bottom: 10px;'>");
                				 bodyBuilder.append(""+candidateSkills[j]+"");
                				 bodyBuilder.append("</li>");
                				 if(j == 5){
                					 bodyBuilder.append("<li style='float: left; background-color: #ededed; border: 1px solid #ededed; border-radius: 2px; padding: 2px 5px; margin-right: 10px; margin-bottom: 10px;'>");
                    				 bodyBuilder.append("<span class='text-success' title='"+candidateDto.get(i).getSkillsName()+"'>more</span>");
                    				 bodyBuilder.append("</li>");
                    				 break;
                				 }
                				 j++;
                			 }
                			 }else{
                				 bodyBuilder.append("<li style='float: left; background-color: #ededed; border: 1px solid #ededed; border-radius: 2px; padding: 2px 5px; margin-right: 10px; margin-bottom: 10px;'>");
                				 bodyBuilder.append("--");
                				 bodyBuilder.append("</li>");
                			 }
                			 bodyBuilder.append("</ul>");
                			 bodyBuilder.append("</div>");
                			 bodyBuilder.append("</div>");
                			 bodyBuilder.append("</td>");
                			 bodyBuilder.append("</tr>");
                			 bodyBuilder.append("</table><br>");
                			 globalVarsMap.put("htmlBody", bodyBuilder.toString());
                			 }
                		 if(emailMessage !=null && globalVarsMap != null){
                			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.PROFILES_ADDED_TO_CART_MAIL, new ArrayList<EmailAttachment>());	
                			 }


                		}

                		if(Integer.parseInt(numberOfResumes) > 1){
                			globalVarsMap.put("resumeCount", "resumes");
                		}else{
                			globalVarsMap.put("resumeCount", "resume");
                		}
                		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.PROFILES_ADDED_TO_CART_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
                		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
                		notificationScenario.setBody(notificationBody);
                		emailScenerioUtil.sendNotification(null, loginUser, userDetails, orgLocations, notificationScenario, new HashMap<String,String>());	
                	}
                	
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return success ? "payment/marketplacePaymentSuccess" : "payment/failed";
        
    }
  //===================== Marketplace Payment Process ENDS Here=========================

}