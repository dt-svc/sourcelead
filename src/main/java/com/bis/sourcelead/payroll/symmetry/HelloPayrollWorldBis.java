package com.bis.sourcelead.payroll.symmetry;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;
import com.symmetry.ste.CalcMethod;
import com.symmetry.ste.EarnedIncomeCredit;
import com.symmetry.ste.FederalFilingStatus;
import com.symmetry.ste.Hours;
import com.symmetry.ste.Money;
import com.symmetry.ste.PercentOfPayrollTax;
import com.symmetry.ste.RUIACalculation;
import com.symmetry.ste.Rate;
import com.symmetry.ste.SDITaxType;
import com.symmetry.ste.STEPayrollCalculator;
import com.symmetry.ste.StateRounding;
import com.symmetry.ste.SurchargeTaxType;
import com.symmetry.ste.TransitTaxType;
import com.symmetry.ste.UnemploymentCalculation;
import com.symmetry.ste.WageType;
import com.symmetry.ste.WebServiceLocationCodeV2;
import com.symmetry.ste.WebServiceType;

public class HelloPayrollWorldBis {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloPayrollWorldBis.class);
	
//	STEPayrollCalculator STE;
	
//	public HelloPayrollWorld() {
//	}
	
	/*public static void main(String[] args)
	{
		System.out.println("Hi");
	}*/
	
	public STEPayrollCalculator runUSPayroll(User user, float totalHours, double amount, int payPeriod, AssociateTaxDedContriView associateTaxDTO, String STEhome, String jniHome, String empState)
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
			STE.setWages(FEDERAL_LOCATION_CODE, WageType.REGULAR, new Hours(totalHours), new Money(amount), zero, zero, zero);
			FederalFilingStatus federalFilingStatus = FederalFilingStatus.SINGLE;
			if(associateTaxDTO != null && "MARRIED".equalsIgnoreCase(associateTaxDTO.getFederalTaxFilliingStatus())){
				federalFilingStatus = FederalFilingStatus.MARRIED;
			}else if(associateTaxDTO != null && "MARRIED_USE_SINGLE_RATE".equalsIgnoreCase(associateTaxDTO.getFederalTaxFilliingStatus())){
				federalFilingStatus = FederalFilingStatus.MARRIED_USE_SINGLE_RATE;
			}else if(associateTaxDTO != null && "NONRESIDENT_ALIEN".equalsIgnoreCase(associateTaxDTO.getFederalTaxFilliingStatus())){
				federalFilingStatus = FederalFilingStatus.NONRESIDENT_ALIEN;
			}
			int numberOfAllowances = 0;
			if(associateTaxDTO != null &&  StringUtils.isNotBlank(associateTaxDTO.getFederalTaxAllowances())){
				numberOfAllowances = Integer.parseInt(associateTaxDTO.getFederalTaxAllowances());
			}
//			System.out.println("----------------------------------++++++++++++++++++++++++++"+associateTaxDTO.getFederalExempt());
			boolean federalExempt = false;
			if(associateTaxDTO.getFederalExempt() != null) {
				federalExempt = associateTaxDTO.getFederalExempt();
			}
			STE.setFederalParameters(federalExempt, federalFilingStatus, numberOfAllowances, false, zero, zero, zero);
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
			
			
			
			
			
			// Calculating Employer FUTA and SUTA 
			STE = calculateEmployerFUTASUTA(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
		
			//Railroad Unemployment Insurance Authority (RUIA)
			STE = calculateEmployerRUIA(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
			
			//Employer Colorado Political Subdivisions Not required
//			STE = calcEmployerColoradoPoliticalSubdivisions(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
			
			//Oregon Employer Transit Taxes
			STE.setTransitTax(locationCode, TransitTaxType.WILSONVILLE_TRANSIT, false);
			
			//Employer Massachusetts Unemployment Health Insurance Tax (UHI) - Discontinued
			STE = calcuUHITax(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
			
			//Massachusetts Employer Medical Assistance Contributions (EMAC)
			STE = calculateEMAC(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
			//Massachusetts Employer Medical Assistance Contributions Supplemental (EMACS)
			STE = calculateEMACSupplemental(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
			//New York Metropolitan Commuter Transportation Mobility Tax (MCTMT)
			STE = calculateMCTMT(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
			//Employer Wyoming Workers Compensation Tax (WC)
			STE.setWyomingWorkersComp(new Rate(0.014), true);
			
			//Employer SUTA Surcharge Taxes
			STE = calculateEmployerSUTASurchargeTaxes(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
			//Employer SDI
			if("Puerto Rico".equalsIgnoreCase(empState) || "New Jersey".equalsIgnoreCase(empState)) {
				STE = calculateEmployerSDI(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE,empState);	
			}
		
			//Employer Head Taxes (EHT)
			calculateEmployerHeadTaxes(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE);
			
			STE.calculateTaxes();
			
			
			
//			BigDecimal taxes = STE.getFICACalc().add(STE.getMedicareCalc()).add(STE.getFederalCalc()).add(STE.getEICCalc()).asBigDecimal();
//			BigDecimal netPay = BigDecimal.valueOf(amount).subtract(taxes);
			
			
			
			
			System.out.println("************************************************");
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
//			System.out.println("************************************************");
			
			// Employer Taxes
			System.out.println("Employer FUTA/SUTA TAXES--------------------------");
			UnemploymentCalculation federalUC = STE.getUnemployment("00-000-0000");
			UnemploymentCalculation stateUC = STE.getUnemployment(locationCode);
			System.out.println("Employer FUTA: " + federalUC.getCalculatedUITax());
			System.out.println("Employer SUTA: " + stateUC.getCalculatedUITax());
			System.out.println("");
			
			System.out.println("Employer RUIA TAXES--------------------------");
			RUIACalculation ruia = STE.getRailroadUnemployment();
			System.out.println("Employer RUIA Withholding: " + ruia.getCalculatedUITax());
			System.out.println("");
			
			System.out.println("Employer Oregon Transit TAXES--------------------------");
			Money transitCalc = STE.getTransitTaxCalc(locationCode, TransitTaxType.WILSONVILLE_TRANSIT);
			System.out.println("Employer Oregon Transit Tax: " + transitCalc);
			System.out.println("");
			
			System.out.println("UHI TAXES--------------------------");
			UnemploymentCalculation uc = STE.getMassachusettsUHI();
			System.out.println("Massachusetts UHI: " +uc.getCalculatedUITax());
			System.out.println("");
			
			System.out.println("Massachusetts EMAC TAXES--------------------------");
			UnemploymentCalculation emac = STE.getMassachusettsEMAC();
			System.out.println("Massachusetts EMAC: " +emac.getCalculatedUITax());
			System.out.println("");
			
			System.out.println("Massachusetts EMAC TAXES--------------------------");
			UnemploymentCalculation emacs = STE.getEMACS(locationCode);
			System.out.println("Massachusetts EMAC Supplemental: " +emacs.getCalculatedUITax());
			System.out.println("");
			
			System.out.println("New York Metropolitan Commuter Transportation Mobility Tax (MCTMT) TAXES--------------------------");
			Money mctmtWithholding = STE.getPercentTotalPayrollTax("36-061-971945", PercentOfPayrollTax.NEW_YORK_MCTMT);
			System.out.println("New York Metro Commuter Transportation: " + mctmtWithholding);
			System.out.println("");
			
			System.out.println("Employer Wyoming Workers Compensation Tax (WC)--------------------------");
			Money wcCalc = STE.getWyomingWorkersCompCalc();
			System.out.println("Wyoming Workers Comp: " +wcCalc);
			System.out.println();
			
			System.out.println("Employer Oregon Workers Compensation Tax WC--------------------------");
			//Employer Oregon Workers Compensation Tax WC
			Money oregonWC = STE.getOregonWorkersCompCalc();
			System.out.println("Employer Oregon Workers Compensation Tax: " + oregonWC);
			System.out.println();
			
			System.out.println("Employer SUTA Surcharge Taxes--------------------------");
			UnemploymentCalculation surChargeTaxes = STE.getUnemploymentSurcharge(locationCode, SurchargeTaxType.IDAHO_ADMINISTRATIVE_RESERVE);
			System.out.println("EMPLOYER SUTA Surcharge: " + surChargeTaxes.getCalculatedUITax());
			
			System.out.println("Employer SDI Taxes--------------------------");
			if("New Jersey".equalsIgnoreCase(empState)) {
				Money empNJSDICalc = STE.getSDIEmployerCalc("34-021-884540", SDITaxType.NEW_JERSEY_SDI);
				System.out.println("New Jersey Employer SDI: " + empNJSDICalc);
			}
			if("Puerto Rico".equalsIgnoreCase(empState)) {
				Money ricoSDICalc = STE.getSDIEmployerCalc("72-127-1612697", SDITaxType.PUERTO_RICO_SDI);
				System.out.println("Puerto Rico Employer SDI: " + ricoSDICalc);
			}
			
			System.out.println("Employer SUTA Surcharge Taxes--------------------------");
			// Calculating Employer FICA
			Money empficaCalc = STE.getEmployerFICACalc();
			System.out.println("Employer FICA calc: " +empficaCalc);
						
			// Calculating Employer Medicare
			Money empMediCalc = STE.getEmployerMedicareCalc();
			System.out.println("Employer Medicare calc: " +empMediCalc);
			
			return STE;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.exit(0);
		return null;
	}
	
	/**
	 * Calculating Employer SDI
	 * 
	 * @param sTE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param fEDERAL_LOCATION_CODE
	 * @param empState 
	 * @return
	 */
	private STEPayrollCalculator calculateEmployerSDI(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String fEDERAL_LOCATION_CODE, String empState)
	{
		Money zero = Money.zero();
		try {
			if("New Jersey".equalsIgnoreCase(empState)) {
				STE.setSDIEmployerExt(locationCode, SDITaxType.NEW_JERSEY_SDI, new Rate(0.005), zero, zero, false);
			}
			if("Puerto Rico".equalsIgnoreCase(empState)) {
				STE.setSDIEmployerExt(locationCode, SDITaxType.PUERTO_RICO_SDI, new Rate(0.00),zero, zero, false);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return STE;
	}

	/**
	 * Employer SUTA Surcharge Taxes
	 * 
	 * @param sTE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param fEDERAL_LOCATION_CODE
	 * @return
	 */
	private STEPayrollCalculator calculateEmployerSUTASurchargeTaxes(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String fEDERAL_LOCATION_CODE)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, zero /* need to pass YTD Wage*/);
			STE.setUnemploymentSurcharge(locationCode, SurchargeTaxType.IDAHO_ADMINISTRATIVE_RESERVE, new Rate(0.01), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STE;
	}

	/**
	 * Employer Head Taxes (EHT)
	 * 
	 * @param sTE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param fEDERAL_LOCATION_CODE
	 * @return STEPayrollCalculator
	 */
	private void calculateEmployerHeadTaxes(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String fEDERAL_LOCATION_CODE)
	{
		try {
			STE.setHeadTax(locationCode, 23, false);
			STE.calculateEmployerTaxes();
			Money headTax = STE.getHeadTax(locationCode);
			System.out.println("Head Tax: " + headTax);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Calculating New York Metropolitan Commuter Transportation Mobility Tax (MCTMT)
	 * @param sTE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param fEDERAL_LOCATION_CODE
	 * @return
	 */
	private STEPayrollCalculator calculateMCTMT(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String fEDERAL_LOCATION_CODE)
	{
		try {
			STE.setPercentTotalPayrollTax(locationCode, PercentOfPayrollTax.NEW_YORK_MCTMT, new Money(amount), false);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return STE;
		
	}

	/**
	 * Calculating Massachusetts Employer Medical Assistance Contributions
	 * Supplemental (EMACS)
	 * 
	 * @param STE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param fEDERAL_LOCATION_CODE
	 * @return STEPayrollCalculator
	 */
	private STEPayrollCalculator calculateEMACSupplemental(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String fEDERAL_LOCATION_CODE)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, zero);
			STE.setEMACS(locationCode, zero /* Need to Pass YTD wage */, new Rate(0.00), new Money(0.00), 8, false);
		} catch (Exception e) {
		}
		return STE;
	}

	/**
	 * Calculating Massachusetts Employer Medical Assistance Contributions
	 * (EMAC)
	 * 
	 * @param STE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param fEDERAL_LOCATION_CODE
	 * @return STEPayrollCalculator
	 */
	private STEPayrollCalculator calculateEMAC(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String fEDERAL_LOCATION_CODE)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, new Money(3000.00));
			STE.setMassachusettsEMAC(zero, null, null, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STE;
	}
	
	/**
	 * calculating Employer Massachusetts Unemployment Health Insurance Tax
	 * (UHI) - Discontinued
	 * 
	 * @param STE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param fEDERAL_LOCATION_CODE
	 * @param netPay
	 * @return STEPayrollCalculator
	 */
	private STEPayrollCalculator calcuUHITax(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String fEDERAL_LOCATION_CODE)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, zero);
			STE.setMassachusettsUHI(zero, null, null, 8, false);
			STE.calculateTaxes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STE;
	}

	/**
	 * calculating Employer Colorado Political Subdivisions
	 * 
	 * @param STE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param fEDERAL_LOCATION_CODE
	 * @return
	 */
	private STEPayrollCalculator calcEmployerColoradoPoliticalSubdivisions(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String fEDERAL_LOCATION_CODE)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(0.00), new Money(amount), zero, zero, zero);
			STE.setUnemploymentExt(locationCode, new Rate(0.05), zero, false);
			STE.setStateMiscellaneousParameters(locationCode, "POLITICALSUBDIVISION", "TRUE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STE;
	}

	/**
	 * calculating Railroad Unemployment Insurance Authority (RUIA)
	 * 
	 * @param STE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param FEDERAL_LOCATION_CODE
	 * @return
	 */
	private STEPayrollCalculator calculateEmployerRUIA(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String FEDERAL_LOCATION_CODE)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(FEDERAL_LOCATION_CODE, WageType.RUIA_COMPENSATION, new Hours(totalHours), new Money(amount), zero, zero, zero);
			STE.setRailroadUnemployment(new Rate(0.10), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STE;
	}
	
	/**
	 * Calculating Employer FUTA/SUTA
	 * 
	 * @param STE
	 * @param locationCode
	 * @param totalHours
	 * @param amount
	 * @param FEDERAL_LOCATION_CODE
	 */
	private STEPayrollCalculator calculateEmployerFUTASUTA(STEPayrollCalculator STE, String locationCode, float totalHours, double amount, String FEDERAL_LOCATION_CODE)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(FEDERAL_LOCATION_CODE, WageType.FUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, zero);
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, zero);
			// STE.setWages(locationCode, WageType.PREV_STATE_WAGES, new
			// Hours(totalHours), new Money(amount), zero, zero, new
			// Money(amount));
			STE.setUnemployment(FEDERAL_LOCATION_CODE, new Rate(0.054), false);
			STE.setUnemployment(locationCode, new Rate(0.008), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return STE;
	}

	/**
	 * Get location code
	 * @param STE
	 * @param user
	 * @return WebServiceLocationCodeV2
	 */
	private WebServiceLocationCodeV2 getLocationCode(STEPayrollCalculator STE, User user)
	{
		// Calcualting Location Code
		WebServiceLocationCodeV2 webServiceLocationCodeV2 = null;
		try {
			STE.setWebService(WebServiceType.STE_LOC_CODE, "http://lcs2.symmetry.com/", null, "sourcelead", "s290sdq", null);
			//webServiceLocationCodeV2 = STE.webServiceLocationCodeV2(user.getResidenceAddress(), user.getResidenceStreetAddress(), user.getResidenceCity(), user.getResidenceState(), user.getResidenceZipCode(), null, null);
			System.out.println("Country code     : " + webServiceLocationCodeV2.getCountyCode());
			System.out.println("City code        : " + webServiceLocationCodeV2.getCityCode());
			System.out.println("Location Code    : " + webServiceLocationCodeV2.getLocationCode());
			System.out.println("MunicipalityCode : "+ webServiceLocationCodeV2.getMunicipalityCode());
			System.out.println("SchoolCode       : " + webServiceLocationCodeV2);
			System.out.println("");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return webServiceLocationCodeV2;
	}
}