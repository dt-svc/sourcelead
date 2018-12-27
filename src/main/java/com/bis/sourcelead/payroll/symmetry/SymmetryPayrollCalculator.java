package com.bis.sourcelead.payroll.symmetry;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;
import com.symmetry.ste.CalcMethod;
import com.symmetry.ste.CountyTax;
import com.symmetry.ste.EarnedIncomeCredit;
import com.symmetry.ste.FederalFilingStatus;
import com.symmetry.ste.Hours;
import com.symmetry.ste.Money;
import com.symmetry.ste.PercentOfPayrollTax;
import com.symmetry.ste.RUIACalculation;
import com.symmetry.ste.Rate;
import com.symmetry.ste.SDITaxType;
import com.symmetry.ste.STEObjectPool;
import com.symmetry.ste.STEPayrollCalculator;
import com.symmetry.ste.StateRounding;
import com.symmetry.ste.SurchargeTaxType;
import com.symmetry.ste.TransitTaxType;
import com.symmetry.ste.UnemploymentCalculation;
import com.symmetry.ste.WageType;
import com.symmetry.ste.WebServiceLocationCodeV2;
import com.symmetry.ste.WebServiceType;

@Component
public class SymmetryPayrollCalculator {
	
	private static final Logger logger = LoggerFactory.getLogger(SymmetryPayrollCalculator.class);
	
	@Value("${symmetry.STEhome}")
	private String STEhome;
	
	@Value("${symmetry.jniHome}")
	private String jniHome;
	
	String FEDERAL_LOCATION_CODE = "00-000-0000";
	
	STEObjectPool eObjectPool;
	
	
	public PayrollCalculatorDTO runUSPayroll(Candidate candidate, double totalHours, BigDecimal grossAamount, int invoiceFrequency, AssociateTaxDedContriView associateTaxView,Date checkDate, YTDPayrollDTO ytdTaxes)
	{
		PayrollCalculatorDTO payrollCalculatorDTO = new PayrollCalculatorDTO();
		
		 Money SUI = Money.zero();
		 Money SDI = Money.zero();
		 Money FLI = Money.zero();
		 Money HCSF = Money.zero();
		 Money WC = Money.zero();
		 
//		String STEhome = "/home/rajashekar/Desktop/Tools/ste/ste-root";
//		String jniHome = "/home/rajashekar/Desktop/Tools/ste/ste-interface-files/java/";
		String arch = System.getProperty("os.name", "");
		if (arch.toLowerCase().contains("win")) {
			STEhome = "c:/STE/ste-root/";
			jniHome = "c:/STE/ste-interface-files/java/";
		}
		try {
//			eObjectPool = new STEObjectPool(jniHome, STEhome, 10, 300, 600, 10);
			STEPayrollCalculator STE = new STEPayrollCalculator(jniHome, STEhome);
//			STEPayrollCalculator STE = eObjectPool.checkOut();
			WebServiceLocationCodeV2 w = getLocationCode(STE,candidate);
			if(w != null && w.getLocationCode() != null) {
				String locationCode = w.getLocationCode();
//				Date today = new Date();
				STE.setPayrollRunParameters(checkDate, invoiceFrequency, 0);
				STE.clearPayrollCalculations();
				STE.setCalculationMethod(FEDERAL_LOCATION_CODE, CalcMethod.ANNUALIZED, CalcMethod.NONE);
				Money zero = Money.zero();
				STE.setWages(FEDERAL_LOCATION_CODE, WageType.REGULAR, new Hours(totalHours), new Money(grossAamount), zero, zero, zero);
				FederalFilingStatus federalFilingStatus = FederalFilingStatus.SINGLE;
				if(associateTaxView != null && "MARRIED".equalsIgnoreCase(associateTaxView.getFederalTaxFilliingStatus())){
					federalFilingStatus = FederalFilingStatus.MARRIED;
				}else if(associateTaxView != null && "MARRIED_USE_SINGLE_RATE".equalsIgnoreCase(associateTaxView.getFederalTaxFilliingStatus())){
					federalFilingStatus = FederalFilingStatus.MARRIED_USE_SINGLE_RATE;
				}else if(associateTaxView != null && "NONRESIDENT_ALIEN".equalsIgnoreCase(associateTaxView.getFederalTaxFilliingStatus())){
					federalFilingStatus = FederalFilingStatus.NONRESIDENT_ALIEN;
				}
				int numberOfAllowances = 0;
				if(associateTaxView != null &&  StringUtils.isNotBlank(associateTaxView.getFederalTaxAllowances())){
					numberOfAllowances = Integer.parseInt(associateTaxView.getFederalTaxAllowances());
				}
				boolean federalExempt = false;
				boolean medicareExempt = false;
				boolean socsecExemt = false;
				boolean isResident = PayrollUtil.getEmployeeResident(candidate);
				if(associateTaxView.getFederalExempt() != null) {
					federalExempt = associateTaxView.getFederalExempt();
				}
				if(associateTaxView.getMediacareExempt() !=null) {
					medicareExempt = associateTaxView.getMediacareExempt();
				}
				if(associateTaxView.getSocialSecurityExempt() !=null) {
					socsecExemt = associateTaxView.getSocialSecurityExempt();
				}
				STE.setFederalParameters(federalExempt, federalFilingStatus, numberOfAllowances, false, zero, zero, zero);
				STE.setFICAParameters(socsecExemt, false, zero, zero, true);
				STE.setMedicareParameters(medicareExempt, zero, zero);
				STE.setEICParameters(EarnedIncomeCredit.SINGLE, zero, false);
				// The code for Indianapolis, Indiana (Marion County) is   MARRIED,SINGLE
				// "18-097-452890"
				// The state GNIS code is 18
				// The county GNIS code is 097
				// The City GNIS code is 452890
				STE.setStateParameters(locationCode, true, false, StateRounding.DEFAULTROUNDING, zero, zero, zero, false);
				STE.setCalculationMethod(locationCode, CalcMethod.ANNUALIZED, CalcMethod.NONE);
				STE.setWages(locationCode, WageType.REGULAR, new Hours(totalHours), new Money(grossAamount), zero, zero, zero);
				
				
				if(PayrollConstant.KENTUCKY.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					STE.setKYCountyParameters(locationCode, CountyTax.KY_MENTAL_HEALTH, federalExempt, isResident,zero);
				}
				String residanceTaxAllowances = "0";
				if(associateTaxView.getResidenceTaxAllowances() != null) {
					residanceTaxAllowances = associateTaxView.getResidenceTaxAllowances();
				}
				if(PayrollConstant.OREGON.equalsIgnoreCase(candidate.getResidenceState())) {
					STE.setStateMiscellaneousParameters(locationCode, "FILINGSTATUS", "M");
					STE.setStateMiscellaneousParameters(locationCode, "TOTALALLOWANCES", residanceTaxAllowances);
				}
				
				// State Unemployment Insurance (SUI) 
				
				if(PayrollConstant.NEW_JERSEY.equalsIgnoreCase(candidate.getResidenceState().trim())
				  || PayrollConstant.PENNSYLVANIA.equalsIgnoreCase(candidate.getResidenceState().trim())
				   || PayrollConstant.ALASKA.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(grossAamount),zero, zero, zero);
					STE.setSUI(locationCode, zero, null, null, false);
					
				}
				
				// State Disability Insurance (SDI)
				if(PayrollConstant.CALIFORNIA.equalsIgnoreCase(candidate.getResidenceState().trim())
				   || PayrollConstant.NEW_JERSEY.equalsIgnoreCase(candidate.getResidenceState().trim())
				   || PayrollConstant.PUERTO_RICO.equalsIgnoreCase(candidate.getResidenceState().trim())
				   || PayrollConstant.RHODE_ISLAND.equalsIgnoreCase(candidate.getResidenceState().trim())
				   || PayrollConstant.HAWAII.equalsIgnoreCase(candidate.getResidenceState().trim())
				   || PayrollConstant.NEW_YORK.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					
//					STE.setSDI(locationCode, zero, zero, zero, zero, false);
					STE.setSDI(locationCode, zero, zero, zero, zero, zero, false);
				}
				
				// Family Leave Insurance (FLI)
				// New Jersey
				if(PayrollConstant.NEW_JERSEY.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					//HCSF
					STE.setHealthCareSubsidyFund(locationCode, null, null, null, true);
					STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(grossAamount),zero, zero, zero);
					STE.setFLI(locationCode, new Money(grossAamount), null, null, false);			
				}
				//New York Family Leave Insurance tax
				if(PayrollConstant.NEW_YORK.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					STE.setFLI(locationCode, new Money(0.00), null, null, false);
				}
				
				//Workers Compensation (WC)
				if(PayrollConstant.NEW_MEXICO.equalsIgnoreCase(candidate.getResidenceState().trim()) || PayrollConstant.OREGON.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					STE.setWorkersComp(locationCode, zero,null, null, null,false);
				}
				
				//TODO Need to explore
				if(PayrollConstant.WASHINGTON.equalsIgnoreCase(candidate.getResidenceState())) {
					STE.setSDI(locationCode, new Money(0D), null, new Rate(0.0), null);
				}
				
				// Calculating Employer FUTA and SUTA 
				STE = calculateEmployerFUTASUTA(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE,ytdTaxes);
				
			
				//Railroad Unemployment Insurance Authority (RUIA)
				STE = calculateEmployerRUIA(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE,ytdTaxes);
				
				
				//Employer Colorado Political Subdivisions Not required
//				STE = calcEmployerColoradoPoliticalSubdivisions(STE,locationCode,totalHours,amount,FEDERAL_LOCATION_CODE,ytdTaxes);
				
				
				//Oregon Employer Transit Taxes
				STE.setTransitTax(locationCode, TransitTaxType.WILSONVILLE_TRANSIT, false);
				
				//Employer Massachusetts Unemployment Health Insurance Tax (UHI) - Discontinued
				STE = calcuUHITax(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE,ytdTaxes);
				
				
				//Massachusetts Employer Medical Assistance Contributions (EMAC)
				STE = calculateEMAC(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE,ytdTaxes);
				
				//Massachusetts Employer Medical Assistance Contributions Supplemental (EMACS)
				STE = calculateEMACSupplemental(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE,ytdTaxes);
				
				//New York Metropolitan Commuter Transportation Mobility Tax (MCTMT)
				if(PayrollConstant.NEW_YORK.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					STE = calculateMCTMT(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE);
					STE.setStateMiscellaneousParameters(locationCode, "FILINGSTATUS", "M");
					STE.setStateMiscellaneousParameters(locationCode, "TOTALALLOWANCESNYC",residanceTaxAllowances);
				}
				
				STE.setCityParameters(locationCode, federalExempt, isResident, 0, zero);
				//Employer Wyoming Workers Compensation Tax (WC)
				STE.setWyomingWorkersComp(new Rate(0.014), true);
				
				//Employer SUTA Surcharge Taxes
				STE = calculateEmployerSUTASurchargeTaxes(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE,ytdTaxes);
				
				//Employer SDI
				if("Puerto Rico".equalsIgnoreCase(candidate.getResidenceState().trim().trim()) || "New Jersey".equalsIgnoreCase(candidate.getResidenceState().trim().trim())) {
					STE = calculateEmployerSDI(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE,candidate.getResidenceState().trim());	
				}
			
				//Employer Head Taxes (EHT)
				calculateEmployerHeadTaxes(STE,locationCode,totalHours,grossAamount,FEDERAL_LOCATION_CODE);
				STE.setCountyParameters(locationCode, federalExempt, isResident);
				
				STE.calculateTaxes();
				
				
				
//				BigDecimal taxes = STE.getFICACalc().add(STE.getMedicareCalc()).add(STE.getFederalCalc()).add(STE.getEICCalc()).asBigDecimal();
//				BigDecimal netPay = BigDecimal.valueOf(amount).subtract(taxes);
				
				
				
			
				String version = STE.getSTEVersion();
				logger.info("candidate Name : " +candidate.getFirstName() + " Id is "+candidate.getId());
				logger.info("STE Version: " + version);
				logger.info("FICA Withholding:" + STE.getFICACalc());
				logger.info("Medicare Withholding: " + STE.getMedicareCalc());
				logger.info("Federal Withholding: " + STE.getFederalCalc());
				logger.info("Earned Inc. Credit: " + STE.getEICCalc());
				logger.info("State Withholding: " + STE.getStateCalc(locationCode));
				logger.info("County Withholding:" + STE.getCountyCalc(locationCode));
				logger.info("Advanced Earned Income Credit :"+STE.getEICCalc());
				
				logger.info("");
				if(PayrollConstant.KENTUCKY.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					Money kentonCountyMentalHealth = STE.getKYCountyCalc (locationCode,CountyTax.KY_MENTAL_HEALTH);
					Money florenceWithholding = STE.getCityCalc(locationCode);
					logger.info("Kentucky County: " + kentonCountyMentalHealth);
					logger.info("Kentucky City: " + florenceWithholding);
				}
				if(PayrollConstant.WASHINGTON.equalsIgnoreCase(candidate.getResidenceState())) {
					Money industrialInsuranceWithholding = STE.getSDICalc(locationCode);
					logger.info("Washington Industrial Insurance: " + industrialInsuranceWithholding);
				}
				logger.info("albama,colorado,Delaware ,Missouri , Ohio , West Virginia and Michigan City Tax: " + STE.getCityCalc(locationCode)); // this for albama,colorado,Delaware
//				logger.info("************************************************");

				// Employer Taxes
				logger.info("Employer FUTA/SUTA TAXES :");
				UnemploymentCalculation federalUC = STE.getUnemployment(FEDERAL_LOCATION_CODE);
				UnemploymentCalculation stateUC = STE.getUnemployment(locationCode);
				logger.info("Employer FUTA: " + federalUC.getCalculatedUITax());
				logger.info("Employer SUTA: " + stateUC.getCalculatedUITax());
				logger.info("");
				
				logger.info("Employer RUIA TAXES :");
				RUIACalculation ruia = STE.getRailroadUnemployment();
				logger.info("Employer RUIA Withholding: " + ruia.getCalculatedUITax());
				logger.info("");
				
				logger.info("Employer Oregon Transit TAXES :");
				Money transitCalc = STE.getTransitTaxCalc(locationCode, TransitTaxType.WILSONVILLE_TRANSIT);
				logger.info("Employer Oregon Transit Tax: " + transitCalc);
				logger.info("");
				
				logger.info("UHI TAXES :");
				UnemploymentCalculation uc = STE.getMassachusettsUHI();
				logger.info("Massachusetts UHI: " +uc.getCalculatedUITax());
				logger.info("");
				
				logger.info("Massachusetts EMAC TAXES :");
				UnemploymentCalculation emac = STE.getMassachusettsEMAC();
				logger.info("Massachusetts EMAC: " +emac.getCalculatedUITax());
				logger.info("");
				
				logger.info("Massachusetts EMAC TAXES :");
				UnemploymentCalculation emacs = STE.getEMACS(locationCode);
				logger.info("Massachusetts EMAC Supplemental: " +emacs.getCalculatedUITax());
				logger.info("");
				
				if("New York".equalsIgnoreCase(candidate.getResidenceState().trim())) {
					logger.info("New York Metropolitan Commuter Transportation Mobility Tax (MCTMT) TAXES--------------------------");
					Money mctmtWithholding = STE.getPercentTotalPayrollTax(locationCode, PercentOfPayrollTax.NEW_YORK_MCTMT);
					payrollCalculatorDTO.setEmployerMetropolitanMCTMT(mctmtWithholding);
					logger.info("New York Metro Commuter Transportation: " + mctmtWithholding);
					logger.info("");
					
					
//                .add(payrollCalculator.getSDI().asBigDecimal()).add(payrollCalculator.getFLI().asBigDecimal())
//                .add(payrollCalculator.getHCSF().asBigDecimal()).add(payrollCalculator.getWC().asBigDecimal());
				
				}
				if(PayrollConstant.NEW_JERSEY.equalsIgnoreCase(candidate.getResidenceState().trim())
						  || PayrollConstant.PENNSYLVANIA.equalsIgnoreCase(candidate.getResidenceState().trim())
						   || PayrollConstant.ALASKA.equalsIgnoreCase(candidate.getResidenceState().trim())) {
							STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(grossAamount),zero, zero, zero);
							STE.setSUI(locationCode, zero, null, null, false);
							
							// State Unemployment Insurance (SUI)
							SUI = STE.getSUICalc(locationCode);
							payrollCalculatorDTO.setSUI(SUI);
							logger.info("Unemployment Rate: (SUI)" + SUI);
							
						}
				// SDI
				SDI = STE.getSDICalc(locationCode);
				payrollCalculatorDTO.setSDI(SDI);
				logger.info("SDI Calculation : " +SDI);
				
				// FLI
				// New Jersy
				if(PayrollConstant.NEW_JERSEY.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					FLI = STE.getFLICalc(locationCode);
					HCSF = STE.getHealthCareSubsidyFundCalc(locationCode);
					logger.info("NJ FLI Calculation: " + FLI);	
					logger.info("NJ Health Care Subsidy Fund (HCSF): " + HCSF);	
					payrollCalculatorDTO.setFLI(FLI);
					payrollCalculatorDTO.setHCSF(HCSF);
				}
				//New York Family Leave Insurance tax
				if(PayrollConstant.NEW_YORK.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					FLI = STE.getFLICalc(locationCode);
					logger.info("NY FLI Calculation: " + FLI);
					payrollCalculatorDTO.setFLI(FLI);
				}
				
				//Workers Compensation (WC)
				if(PayrollConstant.NEW_MEXICO.equalsIgnoreCase(candidate.getResidenceState().trim()) || PayrollConstant.OREGON.equalsIgnoreCase(candidate.getResidenceState().trim())) {
					WC = STE.getWorkersCompCalc(locationCode);
					logger.info("New Mexico Worker’s Comp Calculation (WC): " +WC);
					payrollCalculatorDTO.setWC(WC);
				}
				
				
				logger.info("Employer Wyoming Workers Compensation Tax (WC) :");
				Money wcCalc = STE.getWyomingWorkersCompCalc();
				logger.info("Wyoming Workers Comp: " +wcCalc);
				logger.info("###############################");
				
				logger.info("Employer Oregon Workers Compensation Tax WC:");
				//Employer Oregon Workers Compensation Tax WC
				Money oregonWC = STE.getOregonWorkersCompCalc();
				logger.info("Employer Oregon Workers Compensation Tax: " + oregonWC);
				logger.info("###############################");
				
				logger.info("Employer SUTA Surcharge Taxes :");
				UnemploymentCalculation surChargeTaxes = STE.getUnemploymentSurcharge(locationCode, SurchargeTaxType.IDAHO_ADMINISTRATIVE_RESERVE);
				logger.info("EMPLOYER SUTA Surcharge: " + surChargeTaxes.getCalculatedUITax());
				
				logger.info("Employer SDI Taxes :");
				if("New Jersey".equalsIgnoreCase(candidate.getResidenceState().trim())) {
					Money empNJSDICalc = STE.getSDIEmployerCalc(locationCode, SDITaxType.NEW_JERSEY_SDI);
					logger.info("New Jersey Employer SDI: " + empNJSDICalc);
					payrollCalculatorDTO.setEmployerSDI(empNJSDICalc);
				}
				if("Puerto Rico".equalsIgnoreCase(candidate.getResidenceState().trim())) {
					Money ricoSDICalc = STE.getSDIEmployerCalc(locationCode, SDITaxType.PUERTO_RICO_SDI);
					logger.info("Puerto Rico Employer SDI: " + ricoSDICalc);
					payrollCalculatorDTO.setEmployerSDI(ricoSDICalc);
				}
				
				logger.info("Employer FICA calc :");
				// Calculating Employer FICA
				Money empficaCalc = STE.getEmployerFICACalc();
				logger.info("Employer FICA calc: " +empficaCalc);
							
				// Calculating Employer Medicare
				Money empMediCalc = STE.getEmployerMedicareCalc();
				logger.info("Employer Medicare calc: " +empMediCalc);
				
				
				payrollCalculatorDTO.setsTEPayrollCalculator(STE);
				payrollCalculatorDTO.setEmployerFUTA(federalUC.getCalculatedUITax());
				payrollCalculatorDTO.setEmployerSUTA(stateUC.getCalculatedUITax());
				payrollCalculatorDTO.setEmployerRUIACalculation( STE.getRailroadUnemployment());
				payrollCalculatorDTO.setEmployerOregonTransitTAX(transitCalc);
				payrollCalculatorDTO.setEmployerMassachusettsUHI(uc.getCalculatedUITax());
				payrollCalculatorDTO.setEmployerMassachusettsEMAC(emac.getCalculatedUITax());
				payrollCalculatorDTO.setEmployerMassachusettsEMACSupplemental(emacs.getCalculatedUITax());
				payrollCalculatorDTO.setEmployerWyomingWC(wcCalc);
				payrollCalculatorDTO.setEmployerOregonWC(oregonWC);
				payrollCalculatorDTO.setEmployerSUTASurcharge(surChargeTaxes.getCalculatedUITax());
				payrollCalculatorDTO.setWebServiceLocationCodeV2(w);
				payrollCalculatorDTO.setFEDERAL_LOCATION_CODE(FEDERAL_LOCATION_CODE);
//				eObjectPool.checkIn( STE );
				return payrollCalculatorDTO;
			}
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
	private STEPayrollCalculator calculateEmployerSDI(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String fEDERAL_LOCATION_CODE, String empState)
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
	 * @param ytdTaxes 
	 * @return
	 */
	private STEPayrollCalculator calculateEmployerSUTASurchargeTaxes(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String fEDERAL_LOCATION_CODE, YTDPayrollDTO ytdTaxes)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, new Money(ytdTaxes.getYtdGross()) /* need to pass YTD Wage*/);
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
	private void calculateEmployerHeadTaxes(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String fEDERAL_LOCATION_CODE)
	{
		try {
			STE.setHeadTax(locationCode, 23, false);
			STE.calculateEmployerTaxes();
			Money headTax = STE.getHeadTax(locationCode);
			logger.info("Head Tax: " + headTax);
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
	private STEPayrollCalculator calculateMCTMT(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String fEDERAL_LOCATION_CODE)
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
	 * @param ytdTaxes 
	 * @return STEPayrollCalculator
	 */
	private STEPayrollCalculator calculateEMACSupplemental(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String fEDERAL_LOCATION_CODE, YTDPayrollDTO ytdTaxes)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, new Money(ytdTaxes.getYtdGross()));
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
	 * @param ytdTaxes 
	 * @return STEPayrollCalculator
	 */
	private STEPayrollCalculator calculateEMAC(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String fEDERAL_LOCATION_CODE, YTDPayrollDTO ytdTaxes)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, new Money(ytdTaxes.getYtdGross()));
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
	 * @param ytdTaxes 
	 * @param netPay
	 * @return STEPayrollCalculator
	 */
	private STEPayrollCalculator calcuUHITax(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String fEDERAL_LOCATION_CODE, YTDPayrollDTO ytdTaxes)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, new Money(ytdTaxes.getYtdGross()));
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
	private STEPayrollCalculator calcEmployerColoradoPoliticalSubdivisions(STEPayrollCalculator STE, String locationCode, float totalHours, BigDecimal amount, String fEDERAL_LOCATION_CODE,YTDPayrollDTO ytdTaxes)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(0.00), new Money(amount), zero, zero, new Money(ytdTaxes.getYtdGross()));
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
	 * @param ytdTaxes 
	 * @return
	 */
	private STEPayrollCalculator calculateEmployerRUIA(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String FEDERAL_LOCATION_CODE, YTDPayrollDTO ytdTaxes)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(FEDERAL_LOCATION_CODE, WageType.RUIA_COMPENSATION, new Hours(totalHours), new Money(amount), zero, zero, new Money(ytdTaxes.getYtdGross()));
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
	 * @param ytdTaxes 
	 */
	private STEPayrollCalculator calculateEmployerFUTASUTA(STEPayrollCalculator STE, String locationCode, double totalHours, BigDecimal amount, String FEDERAL_LOCATION_CODE, YTDPayrollDTO ytdTaxes)
	{
		try {
			Money zero = Money.zero();
			STE.setWages(FEDERAL_LOCATION_CODE, WageType.FUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, zero);
			STE.setWages(locationCode, WageType.SUTA_GROSS_WAGES, new Hours(totalHours), new Money(amount), zero, zero, new Money(ytdTaxes.getYtdGross()));
			// STE.setWages(locationCode, WageType.PREV_STATE_WAGES, new Hours(totalHours), new Money(amount), zero, zero, new Money(amount));
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
	 * @param candidate
	 * @return WebServiceLocationCodeV2
	 */
	private WebServiceLocationCodeV2 getLocationCode(STEPayrollCalculator STE, Candidate candidate)
	{
		// Calcualting Location Code
		WebServiceLocationCodeV2 webServiceLocationCodeV2 = null;
		String residentAddress = candidate.getResidenceAddress();
		String address = residentAddress.substring( 0, residentAddress.indexOf(","));
		String zipCode = "";
		if(StringUtils.isNotBlank(candidate.getResidenceZipCode())) {
			zipCode = candidate.getResidenceZipCode();
		}
		try {
			logger.info("candidate Residence State :"+candidate.getResidenceState());
			STE.setWebService(WebServiceType.STE_LOC_CODE, "http://lcs2.symmetry.com/", null, "sourcelead", "s290sdq", null);
			webServiceLocationCodeV2 = STE.webServiceLocationCodeV2(address, "", candidate.getResidenceCity().trim(), candidate.getResidenceStateCode().trim(),zipCode,null,null);
			logger.info("Country code     : " + webServiceLocationCodeV2.getCountyCode());
			logger.info("City code        : " + webServiceLocationCodeV2.getCityCode());
			logger.info("Location Code    : " + webServiceLocationCodeV2.getLocationCode());
			logger.info("MunicipalityCode : "+ webServiceLocationCodeV2.getMunicipalityCode());
			logger.info("SchoolCode       : " + webServiceLocationCodeV2);
			logger.info("");
		} catch (Exception e) {
			logger.error("for location "+address+" "+e.getMessage());
		}
		return webServiceLocationCodeV2;
	}
}
