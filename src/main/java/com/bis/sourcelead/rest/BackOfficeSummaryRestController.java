package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.entity.BoComericaTrans;
import com.bis.sourcelead.entity.BoJmpcTrans;
import com.bis.sourcelead.entity.BoPaymentTypes;
import com.bis.sourcelead.service.BoAmexTransService;
import com.bis.sourcelead.service.BoComericaTransService;
import com.bis.sourcelead.service.BoJmpcTransService;
import com.bis.sourcelead.service.BoPaymentTypesService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class BackOfficeSummaryRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	BoAmexTransService boAmexTransService;
	
	@Autowired
	BoComericaTransService bBoComericaTransService;
	
	@Autowired
	BoJmpcTransService boJmpcTransService;
	
	
	@RequestMapping(value = "/getFinancialTransactionsList")
	@ResponseBody
	public String getFinancialTransactionsList(HttpServletRequest request,@RequestParam(value="json", required=false) String json,@RequestParam(value="financialInstitute", required=false) String financialInstitute) throws Exception {
		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
		String resultJson = null;
		List<Object> finalcialInstituesList = new ArrayList<Object>();
		
		if(null != financialInstitute && "amex".equals(financialInstitute)){
			List<BoAmexTrans> boAmexTransList = boAmexTransService.getBoAmexTransByFilters(jsonObj);
			if(boAmexTransList != null && !boAmexTransList.isEmpty()){
				for(BoAmexTrans amexTrans : boAmexTransList){
					amexTrans.setTransDateStr(Constants.DF_YMD.format(amexTrans.getTransDate()));
					if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("CREDIT")){
						amexTrans.setCreditAmount(amexTrans.getAmount());
					}
					
					if( !isNullOrEmpty(amexTrans.getTransType()) && amexTrans.getTransType().equalsIgnoreCase("DEBIT")){
						amexTrans.setDebitAmount(amexTrans.getAmount());
					}
				}
			}
			finalcialInstituesList.addAll(boAmexTransList);
		}else if(null != financialInstitute && "comerica".equals(financialInstitute)){
			List<BoComericaTrans> boComericaTransList = bBoComericaTransService.getBoComericaTransByFilters(jsonObj);
			if(boComericaTransList != null && !boComericaTransList.isEmpty()){
				for(BoComericaTrans boComericaTrans : boComericaTransList){
					boComericaTrans.setTransDateStr(Constants.DF_YMD.format(boComericaTrans.getTransDate()));
					if( !isNullOrEmpty(boComericaTrans.getTransType()) && boComericaTrans.getTransType().equalsIgnoreCase("CREDIT")){
						boComericaTrans.setCreditAmount(boComericaTrans.getAmount());
					}
					
					if( !isNullOrEmpty(boComericaTrans.getTransType()) && boComericaTrans.getTransType().equalsIgnoreCase("DEBIT")){
						boComericaTrans.setDebitAmount(boComericaTrans.getAmount());
					}
				}
			}
			finalcialInstituesList.addAll(boComericaTransList);
		}else if(null != financialInstitute && "jmpc".equals(financialInstitute)){
        	List<BoJmpcTrans> boJmpcTransList = boJmpcTransService.getBoJmpcTransByFilters(jsonObj);
			if(boJmpcTransList != null && !boJmpcTransList.isEmpty()){
				for(BoJmpcTrans boJmpcTrans : boJmpcTransList){
					boJmpcTrans.setTransDateStr(Constants.DF_YMD.format(boJmpcTrans.getTransDate()));
					if( !isNullOrEmpty(boJmpcTrans.getTransType()) && boJmpcTrans.getTransType().equalsIgnoreCase("CREDIT")){
						boJmpcTrans.setCreditAmount(boJmpcTrans.getAmount());
					}
					
					if( !isNullOrEmpty(boJmpcTrans.getTransType()) && boJmpcTrans.getTransType().equalsIgnoreCase("DEBIT")){
						boJmpcTrans.setDebitAmount(boJmpcTrans.getAmount());
					}
				}
			}
			finalcialInstituesList.addAll(boJmpcTransList);
		}
		
		resultJson = toJSON(finalcialInstituesList);
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
