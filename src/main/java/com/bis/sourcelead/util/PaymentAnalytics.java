package com.bis.sourcelead.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.BoPaymentTypes;
import com.bis.sourcelead.service.BoPaymentTypesService;

@Component
public  class PaymentAnalytics {
	
	@Autowired
	BoPaymentTypesService boPaymentTypesService;

	//find out the payment type by description
	public BoPaymentTypes  paymentAnalytics(String description) throws Exception{
			List<BoPaymentTypes> boPaymentTypesList = boPaymentTypesService.getAllBoPaymentTypes();
			BoPaymentTypes bt = new BoPaymentTypes();
			for(BoPaymentTypes boPaymentTypes : boPaymentTypesList){
				  if(boPaymentTypes.getPaymentType() != null && description != null){
						String[] s1 = boPaymentTypes.getPaymentType().split(" ");
					    String[] s2 = description.split(" ");
						for(String s : s1){
						    for(String ss : s2){
						        if(ss.equalsIgnoreCase(s)){
						        	bt.setId(boPaymentTypes.getId());
						        	break;
						        }
						    }
						}
				  }	
			}
			return bt;
	}

}