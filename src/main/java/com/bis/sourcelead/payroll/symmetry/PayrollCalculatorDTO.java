package com.bis.sourcelead.payroll.symmetry;

import com.symmetry.ste.Money;
import com.symmetry.ste.RUIACalculation;
import com.symmetry.ste.STEPayrollCalculator;
import com.symmetry.ste.WebServiceLocationCodeV2;

public class PayrollCalculatorDTO {
	
	private STEPayrollCalculator sTEPayrollCalculator;
	
	private Money employerFUTA;
	
	private Money employerSUTA;
	
	private RUIACalculation employerRUIACalculation;
	
	private Money employerOregonTransitTAX;
	
	private Money employerMassachusettsUHI;
	
	private Money employerMassachusettsEMAC;
	
	private Money employerMassachusettsEMACSupplemental;
	
	private Money employerMetropolitanMCTMT;
	
	private Money employerWyomingWC;
	
	private Money employerOregonWC;
	
	private Money employerSUTASurcharge;
	
	private Money employerSDI;
	
	private WebServiceLocationCodeV2 webServiceLocationCodeV2;
	
	private String FEDERAL_LOCATION_CODE;

	private Money SUI;
	
	private Money SDI;
	
	private Money FLI; 
	
	private Money HCSF;
	
	private Money WC;
	

	public STEPayrollCalculator getsTEPayrollCalculator()
	{
		return sTEPayrollCalculator;
	}

	public Money getEmployerFUTA()
	{
		return employerFUTA;
	}

	public Money getEmployerSUTA()
	{
		return employerSUTA;
	}

	public RUIACalculation getEmployerRUIACalculation()
	{
		return employerRUIACalculation;
	}

	public Money getEmployerOregonTransitTAX()
	{
		return employerOregonTransitTAX;
	}

	public Money getEmployerMassachusettsUHI()
	{
		return employerMassachusettsUHI;
	}

	public Money getEmployerMassachusettsEMAC()
	{
		return employerMassachusettsEMAC;
	}

	public Money getEmployerMassachusettsEMACSupplemental()
	{
		return employerMassachusettsEMACSupplemental;
	}

	public Money getEmployerMetropolitanMCTMT()
	{
		return employerMetropolitanMCTMT;
	}

	public Money getEmployerWyomingWC()
	{
		return employerWyomingWC;
	}

	public Money getEmployerOregonWC()
	{
		return employerOregonWC;
	}

	public Money getEmployerSUTASurcharge()
	{
		return employerSUTASurcharge;
	}

	public Money getEmployerSDI()
	{
		return employerSDI;
	}

	public void setsTEPayrollCalculator(STEPayrollCalculator sTEPayrollCalculator)
	{
		this.sTEPayrollCalculator = sTEPayrollCalculator;
	}

	public void setEmployerFUTA(Money employerFUTA)
	{
		this.employerFUTA = employerFUTA;
	}

	public void setEmployerSUTA(Money employerSUTA)
	{
		this.employerSUTA = employerSUTA;
	}

	public void setEmployerRUIACalculation(RUIACalculation employerRUIACalculation)
	{
		this.employerRUIACalculation = employerRUIACalculation;
	}

	public void setEmployerOregonTransitTAX(Money employerOregonTransitTAX)
	{
		this.employerOregonTransitTAX = employerOregonTransitTAX;
	}

	public void setEmployerMassachusettsUHI(Money employerMassachusettsUHI)
	{
		this.employerMassachusettsUHI = employerMassachusettsUHI;
	}

	public void setEmployerMassachusettsEMAC(Money employerMassachusettsEMAC)
	{
		this.employerMassachusettsEMAC = employerMassachusettsEMAC;
	}

	public void setEmployerMassachusettsEMACSupplemental(Money employerMassachusettsEMACSupplemental)
	{
		this.employerMassachusettsEMACSupplemental = employerMassachusettsEMACSupplemental;
	}

	public void setEmployerMetropolitanMCTMT(Money employerMetropolitanMCTMT)
	{
		this.employerMetropolitanMCTMT = employerMetropolitanMCTMT;
	}

	public void setEmployerWyomingWC(Money employerWyomingWC)
	{
		this.employerWyomingWC = employerWyomingWC;
	}

	public void setEmployerOregonWC(Money employerOregonWC)
	{
		this.employerOregonWC = employerOregonWC;
	}

	public void setEmployerSUTASurcharge(Money employerSUTASurcharge)
	{
		this.employerSUTASurcharge = employerSUTASurcharge;
	}

	public void setEmployerSDI(Money employerSDI)
	{
		this.employerSDI = employerSDI;
	}

	public WebServiceLocationCodeV2 getWebServiceLocationCodeV2()
	{
		return webServiceLocationCodeV2;
	}

	public void setWebServiceLocationCodeV2(WebServiceLocationCodeV2 webServiceLocationCodeV2)
	{
		this.webServiceLocationCodeV2 = webServiceLocationCodeV2;
	}

	public String getFEDERAL_LOCATION_CODE()
	{
		return FEDERAL_LOCATION_CODE;
	}

	public void setFEDERAL_LOCATION_CODE(String fEDERAL_LOCATION_CODE)
	{
		FEDERAL_LOCATION_CODE = fEDERAL_LOCATION_CODE;
	}

	public Money getSUI()
	{
		if(SUI != null) {
			return SUI;		
		}else {
			return Money.zero();
		}
		
	}

	public Money getSDI()
	{
		if(SDI != null) {
			return SDI;		
		}else {
			return Money.zero();
		}
	}

	public Money getFLI()
	{
		if(FLI != null) {
			return FLI;		
		}else {
			return Money.zero();
		}
	}

	public Money getHCSF()
	{
		if(HCSF != null) {
			return HCSF;		
		}else {
			return Money.zero();
		}
	}

	public Money getWC()
	{
		if(WC != null) {
			return WC;		
		}else {
			return Money.zero();
		}
	}

	public void setSUI(Money sUI)
	{
		SUI = sUI;
	}

	public void setSDI(Money sDI)
	{
		SDI = sDI;
	}

	public void setFLI(Money fLI)
	{
		FLI = fLI;
	}

	public void setHCSF(Money hCSF)
	{
		HCSF = hCSF;
	}

	public void setWC(Money wC)
	{
		WC = wC;
	}

	

	
	
	
}
