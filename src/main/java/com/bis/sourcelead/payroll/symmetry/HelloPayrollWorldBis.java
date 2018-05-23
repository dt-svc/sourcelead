package com.bis.sourcelead.payroll.symmetry;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.symmetry.ste.CalcMethod;
import com.symmetry.ste.EarnedIncomeCredit;
import com.symmetry.ste.FederalFilingStatus;
import com.symmetry.ste.Hours;
import com.symmetry.ste.Money;
import com.symmetry.ste.STEPayrollCalculator;
import com.symmetry.ste.StateRounding;
import com.symmetry.ste.WageType;
import com.symmetry.ste.WebServiceLocationCodeV2;
import com.symmetry.ste.WebServiceType;

public class HelloPayrollWorldBis {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloPayrollWorldBis.class);
	
	STEPayrollCalculator STE;
	
//	public HelloPayrollWorld() {
//	}
	
	public static void main(String[] args)
	{
		System.out.println("Hi");
	}
	
	public void runUSPayroll(User user, float totalHours, double amount, int payPeriod, AssociateTaxDTO associateTaxDTO, String STEhome, String jniHome)
//	public void hello(String locationCode)
	{
//		String STEhome = "/home/rajashekar/Desktop/Tools/ste/ste-root";
//		String jniHome = "/home/rajashekar/Desktop/Tools/ste/ste-interface-files/java/";
		String arch = System.getProperty("os.name", "");
		if (arch.toLowerCase().contains("win")) {
			STEhome = "c:/STE/ste-root/";
			jniHome = "c:/STE/ste-interface-files/java/";
		}
		String FEDERAL_LOCATION_CODE = "00-000-0000";
		try {
			STEPayrollCalculator STE = new STEPayrollCalculator(jniHome, STEhome);
			WebServiceLocationCodeV2 w = getLocationCode(STE,user);
			String locationCode = w.getLocationCode();
			Date today = new Date();
			STE.setPayrollRunParameters(today, 52, 0);
			STE.clearPayrollCalculations();
			STE.setCalculationMethod(FEDERAL_LOCATION_CODE, CalcMethod.ANNUALIZED, CalcMethod.NONE);
			Money zero = Money.zero();
			STE.setWages(FEDERAL_LOCATION_CODE, WageType.REGULAR, new Hours(40.00), new Money(5000.00), zero, zero, zero);
			FederalFilingStatus federalFilingStatus = FederalFilingStatus.SINGLE;
			if(associateTaxDTO != null && "MARRIED".equalsIgnoreCase(associateTaxDTO.getFederaltaxFillingStatus())){
				federalFilingStatus = FederalFilingStatus.MARRIED;
			}else if(associateTaxDTO != null && "MARRIED_USE_SINGLE_RATE".equalsIgnoreCase(associateTaxDTO.getFederaltaxFillingStatus())){
				federalFilingStatus = FederalFilingStatus.MARRIED_USE_SINGLE_RATE;
			}else if(associateTaxDTO != null && "NONRESIDENT_ALIEN".equalsIgnoreCase(associateTaxDTO.getFederaltaxFillingStatus())){
				federalFilingStatus = FederalFilingStatus.NONRESIDENT_ALIEN;
			}
			int numberOfAllowances = 0;
			if(associateTaxDTO != null &&  StringUtils.isNotBlank(associateTaxDTO.getFederaltaxAllowances())){
				numberOfAllowances = Integer.parseInt(associateTaxDTO.getFederaltaxAllowances());
			}
			boolean federalExemt = false;
			if(associateTaxDTO != null &&  "YES".equalsIgnoreCase(associateTaxDTO.getFederalExempt())){
				federalExemt = true;
			}
			STE.setFederalParameters(federalExemt, federalFilingStatus, numberOfAllowances, false, zero, zero, zero);
			STE.setFICAParameters(false, false, zero, zero, true);
			STE.setMedicareParameters(false, zero, zero);
			STE.setEICParameters(EarnedIncomeCredit.SINGLE, zero, false);
			// The code for Indianapolis, Indiana (Marion County) is   MARRIED,SINGLE
			// "18-097-452890"
			// The state GNIS code is 18
			// The county GNIS code is 097
			// The City GNIS code is 452890
			STE.setStateParameters(locationCode, true, false, StateRounding.DEFAULTROUNDING, zero, zero, zero, false);
			STE.setCalculationMethod(locationCode, CalcMethod.ANNUALIZED, CalcMethod.NONE);
			STE.setWages(locationCode, WageType.REGULAR, new Hours(totalHours), new Money(amount), zero, zero, zero);
			STE.setCountyParameters(locationCode, false, true);
			// The miscellaneous parameters vary by state
			// Indiana requires the following two parameters to withhold state
			// tax
			STE.setStateMiscellaneousParameters("18-097-452890", "PERSONALEXEMPTIONS", "1");
			STE.setStateMiscellaneousParameters("18-097-452890", "DEPENDENTEXEMPTIONS", "2");
			STE.calculateTaxes();
			
			
			String version = STE.getSTEVersion();
			System.out.println("User Name : " +user.getFirstName() + " Id is "+user.getId());
			System.out.println("STE Version: " + version);
			System.out.println("FICA Withholding:" + STE.getFICACalc());
			System.out.println("Medicare Withholding: " + STE.getMedicareCalc());
			System.out.println("Federal Withholding: " + STE.getFederalCalc());
			System.out.println("Earned Inc. Credit: " + STE.getEICCalc());
			System.out.println("State Withholding: " + STE.getStateCalc(locationCode));
			System.out.println("County Withholding:" + STE.getCountyCalc(locationCode));
			System.out.println("");
			System.out.println("************************************************");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.exit(0);
	}

	private WebServiceLocationCodeV2 getLocationCode(STEPayrollCalculator STE, User user)
	{
		// Calcualting Location Code
					System.out.println("--------- Calcualting Location Code ------------");
			WebServiceLocationCodeV2  webServiceLocationCodeV2 = null;
				try{
					STE.setWebService (WebServiceType.STE_LOC_CODE, "http://lcs2.symmetry.com/", null, "sourcelead", "s290sdq", null);
					webServiceLocationCodeV2 = STE.webServiceLocationCodeV2(user.getResidenceAddress(), user.getResidenceStreetAddress(), user.getResidenceCity(), user.getResidenceState(), user.getResidenceZipCode(), null, null);
					System.out.println("Country code     : "+webServiceLocationCodeV2.getCountyCode());
					System.out.println("City code        : "+webServiceLocationCodeV2.getCityCode());
					System.out.println("Location Code    : "+webServiceLocationCodeV2.getLocationCode());
					System.out.println("MunicipalityCode : "+webServiceLocationCodeV2.getMunicipalityCode());
					System.out.println("SchoolCode       : "+webServiceLocationCodeV2.getSchoolCode());
					
					System.out.println("");
				}catch(Exception e){
					logger.error(e.getMessage());
				}
				return webServiceLocationCodeV2;
					
	// End Of Location Code
		
	}
}