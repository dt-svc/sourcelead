package com.bis.sourcelead.payroll.symmetry;

import com.bis.sourcelead.entity.User;
import com.symmetry.ste.*;

import java.util.Date;

public class SymmetryPaypal {
//	STEPayrollCalculator STE;
	
	public SymmetryPaypal() {
	}
	
	public void hello(String locationCode)
	{
		String STEhome = "/home/rajasekhar/Desktop/Symmetry/ste-Linux64-1.14.5.0-2018-R4/ste-root";
		String jniHome = "/home/rajasekhar/Desktop/Symmetry/ste-Linux64-1.14.5.0-2018-R4/ste-interface-files/java/";
		String arch = System.getProperty("os.name", "");
		if (arch.toLowerCase().contains("win")) {
			STEhome = "c:/STE/ste-root/";
			jniHome = "c:/STE/ste-interface-files/java/";
		}
		String FEDERAL_LOCATION_CODE = "00-000-0000";
		try {
			STEPayrollCalculator STE = new STEPayrollCalculator(jniHome, STEhome);
			System.out.println(STE.toString());
			getLocationCode(STE);
		/*	Date today = new Date();
			STE.setPayrollRunParameters(today, 52, 13);
			STE.clearPayrollCalculations();
			STE.setCalculationMethod(FEDERAL_LOCATION_CODE, CalcMethod.ANNUALIZED, CalcMethod.NONE);
			Money zero = Money.zero();
			STE.setWages(FEDERAL_LOCATION_CODE, WageType.REGULAR, new Hours(40.00), new Money(5000.00), zero, zero, zero);
			STE.setFederalParameters(false, FederalFilingStatus.MARRIED, 2, false, zero, zero, zero);
			STE.setFICAParameters(false, false, zero, zero, true);
			STE.setMedicareParameters(false, zero, zero);
			STE.setEICParameters(EarnedIncomeCredit.SINGLE, zero, false);
			// The code for Indianapolis, Indiana (Marion County) is
			// "18-097-452890"
			// The state GNIS code is 18
			// The county GNIS code is 097
			// The City GNIS code is 452890
			STE.setStateParameters(locationCode, true, false, StateRounding.DEFAULTROUNDING, zero, zero, zero, false);
			STE.setCalculationMethod(locationCode, CalcMethod.ANNUALIZED, CalcMethod.NONE);
			STE.setWages(locationCode, WageType.REGULAR, new Hours(35.00), new Money(437.50), zero, zero, zero);
			STE.setCountyParameters(locationCode, false, true);
			// The miscellaneous parameters vary by state
			// Indiana requires the following two parameters to withhold state
			// tax
			STE.setStateMiscellaneousParameters("18-097-452890", "PERSONALEXEMPTIONS", "1");
			STE.setStateMiscellaneousParameters("18-097-452890", "DEPENDENTEXEMPTIONS", "2");
			STE.calculateTaxes();
			
			
			String version = STE.getSTEVersion();
			System.out.println("STE Version: " + version);
			System.out.println("FICA Withholding:" + STE.getFICACalc());
			System.out.println("Medicare Withholding: " + STE.getMedicareCalc());
			System.out.println("Federal Withholding: " + STE.getFederalCalc());
			System.out.println("Earned Inc. Credit: " + STE.getEICCalc());
			System.out.println("State Withholding: " + STE.getStateCalc(locationCode));
			System.out.println("County Withholding:" + STE.getCountyCalc(locationCode));
			System.out.println("");

			
			
			
			// Calcualting Location Code
			System.out.println("--------- Calcualting Location Code ------------");
			
			STE.setWebService (WebServiceType.STE_LOC_CODE, "http://lcs2.symmetry.com/", null, "sourcelead", "s290sdq", null);
			WebServiceLocationCodeV2  w = STE.webServiceLocationCodeV2("Arizona Mills, Tempe, AZ, USA", "Arizona Mills, Tempe, AZ, USA", "Tempe", "AR", "85282", null, null);
			System.out.println("Country code     : "+w.getCountyCode());
			System.out.println("City code        : "+w.getCityCode());
			System.out.println("Location Code    : "+w.getLocationCode());
			System.out.println("MunicipalityCode : "+w.getMunicipalityCode());
			System.out.println("SchoolCode       : "+w.getSchoolCode());
			
			System.out.println("");*/
			// End Of Location Code
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	private WebServiceLocationCodeV2 getLocationCode(STEPayrollCalculator STE)
	{
		// Calcualting Location Code
		WebServiceLocationCodeV2 webServiceLocationCodeV2 = null;
		try {
//			401 Massachusetts Avenue, Cambridge, MA, USA
//			8741 Universal Boulevard, Orlando, FL, USA
//			webServiceLocationCodeV2 = STE.webServiceLocationCodeV2("Santa Fe 4017", "", "Rosario","21","",null,null);
//			webServiceLocationCodeV2 = STE.webServiceLocationCodeV2("896A Massachusetts Avenue", "", "Cambridge","MA","",null,null);
//			WebServiceLocationCodeV2 webServiceLocationCodeV2(string streetAddress, string streetAddressSecondLine, string city, string state, string zip, string latitudeIN, string longi-tudeIN)
			STE.setWebService(WebServiceType.STE_LOC_CODE, "http://lcs2.symmetry.com/", null, "sourcelead", "s290sdq", null);
			webServiceLocationCodeV2 = STE.webServiceLocationCodeV2("8741 Universal Boulevard", "", "Orlando", "FL", "32819", null, null);
			System.out.println("Country code     : " + webServiceLocationCodeV2.getCountyCode());
			System.out.println("City code**********        : " + webServiceLocationCodeV2.getCityCode());
			System.out.println("Location Code *******   : " + webServiceLocationCodeV2.getLocationCode());
			System.out.println("MunicipalityCode : "+ webServiceLocationCodeV2.getMunicipalityCode());
			System.out.println("SchoolCode       : " + webServiceLocationCodeV2);
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webServiceLocationCodeV2;
	}
	
	public static void main(String[] args)
	{
		SymmetryPaypal client = new SymmetryPaypal();
//		String locationCode = "18-097-452890";
//		String locationCode = "04-013-12233";       // Arizona Location Code 
		client.hello("");
	}
}