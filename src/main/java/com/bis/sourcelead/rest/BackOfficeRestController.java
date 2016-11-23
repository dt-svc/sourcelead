package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.entity.BoComericaTrans;
import com.bis.sourcelead.entity.BoJmpcTrans;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UploadedFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.BoAmexTransService;
import com.bis.sourcelead.service.BoComericaTransService;
import com.bis.sourcelead.service.BoJmpcTransService;
import com.bis.sourcelead.service.UploadFileService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.validation.FileValidator;
import com.bis.sourcelead.web.UploadController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class BackOfficeRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(BackOfficeRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	BoAmexTransService boAmexTransService;
	
	@Autowired
	BoComericaTransService bBoComericaTransService;
	
	@Autowired
	BoJmpcTransService boJmpcTransService;
	
	@RequestMapping(value = "/getAccountsList")
	@ResponseBody
	public String getAccountsList(HttpServletRequest request) throws Exception {
		
		String resultJson = null;
		
		List<BoAmexTrans> boAmexTransList = boAmexTransService.getBoAmexTrans();
		Float boamexCreditAmount = 0f; 
		Float boamexDebitAmount = 0f;
		Float boamexBalanceAmount = 0f;
		BoAmexTrans boamexTrans = new BoAmexTrans(); 
		for(BoAmexTrans amexTrans : boAmexTransList){
			if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("CREDIT")){
				boamexCreditAmount = boamexCreditAmount+amexTrans.getAmount();
			}
			
			if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("DEBIT")){
				boamexDebitAmount = boamexDebitAmount+amexTrans.getAmount();
			}
		}
		
		boamexTrans.setFinancialInstituteName("American Exchange Credit Card");
		boamexTrans.setDebitAmount(boamexDebitAmount);
		boamexTrans.setCreditAmount(boamexCreditAmount);
		boamexBalanceAmount = boamexCreditAmount - boamexDebitAmount;
		boamexTrans.setBalanceAmount(boamexBalanceAmount);
		boamexTrans.setIdentity("amex");
		
		Float commericaCreditAmount = 0f; 
		Float commericaDebitAmount = 0f;
		Float commericabalanceAmount = 0f;
		BoComericaTrans boComericaTrans = new BoComericaTrans(); 		
		List<BoComericaTrans> BboComericaTransList = bBoComericaTransService.getBoComericaTrans();
		
		for(BoComericaTrans comericaTrans : BboComericaTransList){
			if( !isNullOrEmpty(comericaTrans.getTransType()) && comericaTrans.getTransType().equalsIgnoreCase("CREDIT")){
				commericaCreditAmount = commericaCreditAmount+comericaTrans.getAmount();
			}
			
			if( !isNullOrEmpty(comericaTrans.getTransType()) && comericaTrans.getTransType().equalsIgnoreCase("DEBIT")){
				commericaDebitAmount = commericaDebitAmount+comericaTrans.getAmount();
			}
		}
		
		boComericaTrans.setFinancialInstituteName("Comerica Bank");
		boComericaTrans.setDebitAmount(commericaDebitAmount);
		boComericaTrans.setCreditAmount(commericaCreditAmount);
		commericabalanceAmount = commericaCreditAmount - commericaDebitAmount;
		boComericaTrans.setBalanceAmount(commericabalanceAmount);
		boComericaTrans.setIdentity("comerica");
		
		Float jmpcCreditAmount = 0f; 
		Float jmpcDebitAmount = 0f;
		Float jmpcBalanceAmount = 0f;
		BoJmpcTrans boJmpcTrans = new BoJmpcTrans();
		List<BoJmpcTrans> boJmpcTransList = boJmpcTransService.getBoJmpcTrans();
		
		for(BoJmpcTrans jmpcTrans : boJmpcTransList){
			if( !isNullOrEmpty(jmpcTrans.getTransType()) && jmpcTrans.getTransType().equalsIgnoreCase("CREDIT")){
				jmpcCreditAmount = jmpcCreditAmount+jmpcTrans.getAmount();
			}
			
			if( !isNullOrEmpty(jmpcTrans.getTransType()) && jmpcTrans.getTransType().equalsIgnoreCase("DEBIT")){
				jmpcDebitAmount = jmpcDebitAmount+jmpcTrans.getAmount();
			}
		}
		
		boJmpcTrans.setFinancialInstituteName("Chase Bank");
		boJmpcTrans.setDebitAmount(jmpcDebitAmount);
		boJmpcTrans.setCreditAmount(jmpcCreditAmount);
		jmpcBalanceAmount = jmpcCreditAmount - jmpcDebitAmount;
		boJmpcTrans.setBalanceAmount(jmpcBalanceAmount);
		boJmpcTrans.setIdentity("jmpc");
		
		List<Object> finalcialInstituesList = new ArrayList<Object>();
		
		finalcialInstituesList.addAll(Arrays.asList(boamexTrans));
		finalcialInstituesList.addAll(Arrays.asList(boComericaTrans));
		finalcialInstituesList.addAll(Arrays.asList(boJmpcTrans));
		
		resultJson = toJSON(finalcialInstituesList);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getAmexTransactions")
	@ResponseBody
	public String getAmexTransactions(HttpServletRequest request) throws Exception {
		
		String resultJson = null;
		
		List<BoAmexTrans> boAmexTransList = boAmexTransService.getBoAmexTrans();
		Float boamexCreditAmount = 0f; 
		Float boamexDebitAmount = 0f;
		Float boamexBalanceAmount = 0f;
		BoAmexTrans boamexTrans = new BoAmexTrans(); 
		for(BoAmexTrans amexTrans : boAmexTransList){
			if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("CREDIT")){
				boamexCreditAmount = boamexCreditAmount+amexTrans.getAmount();
			}
			
			if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("DEBIT")){
				boamexDebitAmount = boamexDebitAmount+amexTrans.getAmount();
			}
		}
		
		boamexTrans.setDebitAmount(boamexDebitAmount);
		boamexTrans.setCreditAmount(boamexCreditAmount);
		boamexBalanceAmount = boamexCreditAmount - boamexDebitAmount;
		boamexTrans.setBalanceAmount(boamexBalanceAmount);
		boamexTrans.setIdentity("amex");
		
		
		resultJson = toJSON(boamexTrans);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getComericaTransactions")
	@ResponseBody
	public String getComericaTransactions(HttpServletRequest request) throws Exception {
		
		String resultJson = null;
		
		Float commericaCreditAmount = 0f; 
		Float commericaDebitAmount = 0f;
		Float commericabalanceAmount = 0f;
		BoComericaTrans boComericaTrans = new BoComericaTrans(); 		
		List<BoComericaTrans> BboComericaTransList = bBoComericaTransService.getBoComericaTrans();
		
		for(BoComericaTrans comericaTrans : BboComericaTransList){
			if( !isNullOrEmpty(comericaTrans.getTransType()) && comericaTrans.getTransType().equalsIgnoreCase("CREDIT")){
				commericaCreditAmount = commericaCreditAmount+comericaTrans.getAmount();
			}
			
			if( !isNullOrEmpty(comericaTrans.getTransType()) && comericaTrans.getTransType().equalsIgnoreCase("DEBIT")){
				commericaDebitAmount = commericaDebitAmount+comericaTrans.getAmount();
			}
		}
		
		boComericaTrans.setDebitAmount(commericaDebitAmount);
		boComericaTrans.setCreditAmount(commericaCreditAmount);
		commericabalanceAmount = commericaCreditAmount - commericaDebitAmount;
		boComericaTrans.setBalanceAmount(commericabalanceAmount);
		boComericaTrans.setIdentity("comerica");
		
		resultJson = toJSON(boComericaTrans);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getChaseTransactions")
	@ResponseBody
	public String getChaseTransactions(HttpServletRequest request) throws Exception {
		
		String resultJson = null;
		
		Float jmpcCreditAmount = 0f; 
		Float jmpcDebitAmount = 0f;
		Float jmpcBalanceAmount = 0f;
		BoJmpcTrans boJmpcTrans = new BoJmpcTrans();
		List<BoJmpcTrans> boJmpcTransList = boJmpcTransService.getBoJmpcTrans();
		
		for(BoJmpcTrans jmpcTrans : boJmpcTransList){
			if( !isNullOrEmpty(jmpcTrans.getTransType()) && jmpcTrans.getTransType().equalsIgnoreCase("CREDIT")){
				jmpcCreditAmount = jmpcCreditAmount+jmpcTrans.getAmount();
			}
			
			if( !isNullOrEmpty(jmpcTrans.getTransType()) && jmpcTrans.getTransType().equalsIgnoreCase("DEBIT")){
				jmpcDebitAmount = jmpcDebitAmount+jmpcTrans.getAmount();
			}
		}
		
		boJmpcTrans.setDebitAmount(jmpcDebitAmount);
		boJmpcTrans.setCreditAmount(jmpcCreditAmount);
		jmpcBalanceAmount = jmpcCreditAmount - jmpcDebitAmount;
		boJmpcTrans.setBalanceAmount(jmpcBalanceAmount);
		boJmpcTrans.setIdentity("jmpc");
		
		resultJson = toJSON(boJmpcTrans);
		return resultJson;
	}
	
	
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
}
