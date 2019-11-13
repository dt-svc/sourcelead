package com.bis.sourcelead.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.enums.PaymentAccountType;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PaymentAccountService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 *
 * @author Raja Gopal Reddy P
 */
@Controller
@PropertySource(value = { "${propertyLocation:app.properties}" })
public class PaymentAccountController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgLocationService orgLocationService;
    
    @Autowired
    PaymentAccountService paymentAccountService;

    
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType PLAIN = MediaType.parse("application/x-www-form-urlencoded");
    
    @Value("${wepay.clientid}")
    private long wepayClientID;
    
    @Value("${wepay.accountid}")
    private String wepayAccountID;
    
    @Value("${wepay.secret}")
    private String wepaySecret;
    
    @Value("${wepay.url}")
    private String wepayURL;
    
    @Value("${sourceleadUrl}")
    private String sourceleadURL;
    
    private final String wepaySuccessLink = "/ws/account/access/success";
    
    @Value("${wepay.accesstoken}")
    private String wepayAccessToken;
    

    @RequestMapping(value = "/app/org-location-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseList<OrgLocations> orgLocationList(HttpServletRequest request) {
        ResponseList<OrgLocations> response = new ResponseList<>();
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        response.setResponse(Result.INVALID_DATA.name());
        response.setResponseMessage(Result.INVALID_DATA.getValue());
        if (org != null) {
            try {
                response.setData(orgLocationService.getAllOrgLocationsByOrgId(org.getId()));
            } catch (Exception ex) {
                Logger.getLogger(PaymentAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.setResponse(Result.SUCCESS.name());
            response.setResponseMessage(Result.SUCCESS.getValue());
        } else {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        }
        return response;
    }

    @RequestMapping(value = "/app/payment-account-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseList<PaymentAccountDTO> paymentAccountList(HttpServletRequest request) {
        ResponseList<PaymentAccountDTO> response = new ResponseList<>();
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        response.setResponse(Result.INVALID_DATA.name());
        response.setResponseMessage(Result.INVALID_DATA.getValue());
        if (org != null) {
            response.setData(orgService.getPaymentAccountList(org.getId(), 0, false));
            response.setResponse(Result.SUCCESS.name());
            response.setResponseMessage(Result.SUCCESS.getValue());
        } else {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        }
        return response;
    }

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/app/save-payment-account", method = RequestMethod.POST)
    @ResponseBody
    public com.bis.sourcelead.dto.Response saveAccount(HttpServletRequest request, PaymentAccountDTO account) {
        com.bis.sourcelead.dto.Response response = new com.bis.sourcelead.dto.Response();
        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        response.setResponse(Result.INVALID_DATA.name());
        response.setResponseMessage(Result.INVALID_DATA.getValue());
        if (orgLocation != null && org != null) {
            if (Validator.validate(account)) {
            	account.setOrgId(org.getId());
            	if(account.getEmail().isEmpty()) {
            		account.setEmail(null);
            	}
                long id = orgService.savePaymentAccount(account);
                if (id > 0) {
                    if (PaymentAccountType.valueOf(account.getAccountType()) == PaymentAccountType.WEPAY) {
                        account.setId(id);
                        boolean flag = initWePayAccount(request.getRemoteHost(), request.getHeader("User-Agent"), account, org.getId(), true);
                        if (!flag) {
                            response.setResponse(Result.UNABLE_SET_PAYMENT_ACCOUNT.name());
                            response.setResponseMessage(Result.UNABLE_SET_PAYMENT_ACCOUNT.getValue());
                            return response;
                        }
                        id = orgService.savePaymentAccount(account);
                    }
                    if (id > 0) {
                        response.setResponse(Result.SUCCESS.name());
                        response.setResponseMessage(Result.SUCCESS.getValue());
                    }
                    if (id < 0) {
                        response.setResponse(Result.DUPLICATE_EMAIL.name());
                        response.setResponseMessage(Result.DUPLICATE_EMAIL.getValue());
                    }
                } else if (id == 0) {
                    response.setResponse(Result.FAILED.name());
                    response.setResponseMessage(Result.FAILED.getValue());
                }
            }
        } else {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        }
        return response;
    }
    
    
    
    @RequestMapping(value = "/app/resend-wepay-mail", method = RequestMethod.POST)
    @ResponseBody
    public com.bis.sourcelead.dto.Response resendWePayMail(HttpServletRequest request, long id) {
        com.bis.sourcelead.dto.Response response = new com.bis.sourcelead.dto.Response();
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        response.setResponse(Result.INVALID_DATA.name());
        response.setResponseMessage(Result.INVALID_DATA.getValue());
        if (org != null) {
            PaymentAccountDTO account = orgService.getPaymentAccount(id, org.getId());
            if (account != null && PaymentAccountType.valueOf(account.getAccountType()) == PaymentAccountType.WEPAY) {
                account.setId(id);
                if (StringUtils.isNotBlank(account.getAccessToken())) {
                    account.setAccess(sendMail(account.getAccessToken()));
                } else {
                    boolean flag = initWePayAccount(request.getRemoteHost(), request.getHeader("User-Agent"), account, org.getId(), true);
                    if (!flag) {
                        response.setResponse(Result.UNABLE_SET_PAYMENT_ACCOUNT.name());
                        response.setResponseMessage(Result.UNABLE_SET_PAYMENT_ACCOUNT.getValue());
                        return response;
                    }
                }
                id = orgService.savePaymentAccount(account);
                if (id > 0) {
                    response.setResponse(Result.SUCCESS.name());
                    response.setResponseMessage(Result.SUCCESS.getValue());
                } else if (id < 0) {
                    response.setResponse(Result.DUPLICATE_EMAIL.name());
                    response.setResponseMessage(Result.DUPLICATE_EMAIL.getValue());
                } else if (id == 0) {
                    response.setResponse(Result.FAILED.name());
                    response.setResponseMessage(Result.FAILED.getValue());
                }
            }
        } else {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        }
        return response;
    }

    @RequestMapping(value = "/ws/account/access/success", method = RequestMethod.GET)
    @ResponseBody
    public com.bis.sourcelead.dto.Response accountAccess(HttpServletRequest request, long id, long orgId) {
        com.bis.sourcelead.dto.Response response = new com.bis.sourcelead.dto.Response();
        long flag = orgService.changePaymentAccountToAccess(id, orgId);
        if (flag > 0) {
            response.setResponse(Result.SUCCESS.name());
            response.setResponseMessage(Result.SUCCESS.getValue());
        } else if (flag == -1) {
            response.setResponse(Result.INVALID_DATA.name());
            response.setResponseMessage(Result.INVALID_DATA.getValue());
        } else {
            response.setResponse(Result.FAILED.name());
            response.setResponseMessage(Result.FAILED.getValue());
        }
        return response;
    }

    @RequestMapping(value = "/app/change-default-payment-account", method = RequestMethod.POST)
    @ResponseBody
    public com.bis.sourcelead.dto.Response changeDefaultAccount(HttpServletRequest request, 
            long id, boolean defaultAccount) {
        com.bis.sourcelead.dto.Response response = new com.bis.sourcelead.dto.Response();
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        response.setResponse(Result.INVALID_DATA.name());
        response.setResponseMessage(Result.INVALID_DATA.getValue());
        if (orgLocations != null) {
            int flag = orgService.changeDefaultPaymentAccount(id, orgLocations.getId(), defaultAccount);
            if (flag > 0) {
                response.setResponse(Result.SUCCESS.name());
                response.setResponseMessage(Result.SUCCESS.getValue());
            } else if (flag < 0) {
                response.setResponse(Result.DUPLICATE_EMAIL.name());
                response.setResponseMessage(Result.DUPLICATE_EMAIL.getValue());
            } else if (flag == 0) {
                response.setResponse(Result.FAILED.name());
                response.setResponseMessage(Result.FAILED.getValue());
            }
        } else {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        }
        return response;
    }

    public boolean initWePayAccount(String ip, String device, PaymentAccountDTO account, long orgId, boolean sendMail) {
        try {
            
            OkHttpClient client = new OkHttpClient();
            JSONObject json = new JSONObject();
            json.put("client_id", wepayClientID);
            json.put("client_secret", wepaySecret);
            json.put("email", account.getEmail());
            json.put("first_name", account.getFirstName());
            json.put("last_name", account.getLastName());
            json.put("scope", "manage_accounts,collect_payments,view_user,preapprove_payments,send_money");
            json.put("original_ip", ip);
            json.put("original_device", device);
            json.put("tos_acceptance_time", 1209600);
            json.put("redirect_uri", sourceleadURL.replace("/app", "") + wepaySuccessLink + "?id=" + account.getId() + "&orgId=" + orgId);
            RequestBody body = RequestBody.create(JSON, json.toString());
            Request req = new Request.Builder().url(wepayURL + "/v2/user/register")
                    .addHeader("Authorization", "Bearer " + wepayAccessToken)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            Response res = client.newCall(req).execute();
            JSONObject resp = new JSONObject(res.body().string());
            System.out.println(resp);
            String accessToken = resp.getString("access_token");
            int userId = resp.getInt("user_id");

            json = new JSONObject();
            json.put("name", account.getFirstName() + " " + account.getLastName());
            json.put("description", "SourceLead Invoicing Account");
            body = RequestBody.create(JSON, json.toString());
            req = new Request.Builder().url(wepayURL + "/v2/account/create")
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            res = client.newCall(req).execute();
            resp = new JSONObject(res.body().string());
            System.out.println(resp);
            long accountId = resp.getLong("account_id");

            account.setAccessToken(accessToken);
            account.setUserId(userId + "");
            account.setAccountId(accountId + "");
            if (sendMail) {
                account.setAccess(sendMail(accessToken));
            }

            return true;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean sendMail(String accessToken) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder().url(wepayURL + "/v2/user/send_confirmation")
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response res = client.newCall(req).execute();
            JSONObject resp = new JSONObject(res.body().string());
            System.out.println(resp);
            if (!resp.isNull("error_description") && resp.getString("error_description").equals("This access_token is already approved.")) {
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(PaymentAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PaymentAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @RequestMapping(value = "/app/change-active-payment-account", method = RequestMethod.POST)
    @ResponseBody
    public com.bis.sourcelead.dto.Response changeActiveAccount(HttpServletRequest request,  long id) {
        com.bis.sourcelead.dto.Response response = new com.bis.sourcelead.dto.Response();
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        response.setResponse(Result.INVALID_DATA.name());
        response.setResponseMessage(Result.INVALID_DATA.getValue());
        if (orgLocations != null) {
            int flag = orgService.changePaymentAccountStatus(id, orgLocations.getId());
            if (flag > 0) {
                response.setResponse(Result.SUCCESS.name());
                response.setResponseMessage(Result.SUCCESS.getValue());
            } else if (flag < 0) {
                response.setResponse(Result.DUPLICATE_EMAIL.name());
                response.setResponseMessage(Result.DUPLICATE_EMAIL.getValue());
            } else if (flag == 0) {
                response.setResponse(Result.FAILED.name());
                response.setResponseMessage(Result.FAILED.getValue());
            }
        } else {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        }
        return response;
    }
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/app/change-associate-default-payment-account", method = RequestMethod.POST)
    @ResponseBody
    public com.bis.sourcelead.dto.Response changeassociateDefaultAccount(HttpServletRequest request, 
            long id,long candidateId, boolean defaultAccount) {
        com.bis.sourcelead.dto.Response response = new com.bis.sourcelead.dto.Response();
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        response.setResponse(Result.INVALID_DATA.name());
        response.setResponseMessage(Result.INVALID_DATA.getValue());
        if (orgLocations != null) {
            int flag = orgService.changeAssociateDefaultPaymentAccount(id,candidateId, defaultAccount);
            if (flag > 0) {
                response.setResponse(Result.SUCCESS.name());
                response.setResponseMessage(Result.SUCCESS.getValue());
            } else if (flag < 0) {
                response.setResponse(Result.DUPLICATE_EMAIL.name());
                response.setResponseMessage(Result.DUPLICATE_EMAIL.getValue());
            } else if (flag == 0) {
                response.setResponse(Result.FAILED.name());
                response.setResponseMessage(Result.FAILED.getValue());
            }
        } else {
            response.setResponse(Result.SESSION_TIMEDOUT.name());
            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
        }
        return response;
    }
}
