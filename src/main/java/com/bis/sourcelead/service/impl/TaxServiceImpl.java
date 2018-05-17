package com.bis.sourcelead.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.ProfessionalTaxDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.dto.TdsDTO;
import com.bis.sourcelead.entity.CentralTax;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TaxService;

@Service
public class TaxServiceImpl implements TaxService {

	@Autowired
	private TaxDao taxDao;

	@Autowired
	private StatesService statesService;

	@Override
	public List<SourceleadTaxType> getAllTaxesByType(long type) {
		return taxDao.getAllTaxesByType(type);
	}

	@Override
	public List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId) {
		List<StateTaxDTO> stateTaxList = new LinkedList<>();
		try {
			List<StateTax> stList = taxDao.getStateTaxList(yearFrequency, stateId);
			if (stList != null && !stList.isEmpty()) {
				for (StateTax st : stList) {
					stateTaxList.add(getStateTaxDTO(st, false));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stateTaxList;
	}

	
	public StateTaxDTO getStateTax(long stateTaxId) {
		StateTaxDTO stateTaxDTO = new StateTaxDTO();
		try {
			StateTax stateTax = taxDao.getStateTax(stateTaxId);
			if (stateTax != null) {
				stateTaxDTO = getStateTaxDTO(stateTax, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stateTaxDTO;
	}

	public StateTaxDTO getStateTaxDTO(StateTax stateTax, boolean includeAll) {
		try {
			StateTaxDTO stateTaxDTO = new StateTaxDTO();
			stateTaxDTO.setId(stateTax.getId());
			stateTaxDTO.setFinancialYear(stateTax.getFinancialYear());
			stateTaxDTO.setStateId(stateTax.getStates().getId());
			stateTaxDTO.setStateName(stateTax.getStates().getStateName());
			stateTaxDTO.setTaxTypeId(stateTax.getSourceleadTaxType().getId());
			stateTaxDTO.setTaxType(stateTax.getSourceleadTaxType().getDescription());
			if (includeAll) {
				List<ProfessionalTaxDTO> professionalTaxList = new LinkedList<>();
				for (ProfessionalTax pt : stateTax.getProfessionalTaxList()) {
					ProfessionalTaxDTO professionalTaxDTO = new ProfessionalTaxDTO();
					professionalTaxDTO.setId(pt.getId());
					professionalTaxDTO.setStateTaxId(pt.getStateTax().getId());
					professionalTaxDTO.setTaxType(stateTax.getSourceleadTaxType().getDescription());
					professionalTaxDTO.setAmount(pt.getAmount());
					professionalTaxDTO.setAmountFrom(pt.getAmountFrom());
					professionalTaxDTO.setAmountTo(pt.getAmountTo());
					professionalTaxDTO.setMonthApplicable(pt.getMonthApplicable());
					professionalTaxList.add(professionalTaxDTO);
				}
				stateTaxDTO.setProfessionalTaxList(professionalTaxList);
			}
			return stateTaxDTO;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long addStateTax(StateTaxDTO stateTaxDTO) {
		try {
			StateTax st = new StateTax();
			st.setId(stateTaxDTO.getId());
			st.setFinancialYear(stateTaxDTO.getFinancialYear());
			st.setSourceleadTaxType(getTaxTypeById(stateTaxDTO.getTaxTypeId()));
			st.setStates(statesService.getStateById(stateTaxDTO.getStateId()));
			List<ProfessionalTax> ptList = new LinkedList<>();
			for (ProfessionalTaxDTO ptDTO : stateTaxDTO.getProfessionalTaxList()) {
				ProfessionalTax pt = null;
				if (ptDTO.getId() > 0) {
					pt = new ProfessionalTax();
				} else {
					// pt = getPT
				}
				pt.setAmountFrom(ptDTO.getAmountFrom());
				pt.setAmountTo(ptDTO.getAmountTo());
				pt.setAmount(ptDTO.getAmount());
				pt.setMonthApplicable(ptDTO.getMonthApplicable());
				pt.setStateTax(st);
				ptList.add(pt);
			}
			st.setProfessionalTaxList(ptList);
			return taxDao.addStateTax(st);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	
	@Override
	public long saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations) {
		try {
			CentralTax ct = new CentralTax();
			ct.setCeilvaluepf(centralTaxDTO.getCeilvaluepf());
			ct.setEmployeepf(centralTaxDTO.getEmployeepf());
			ct.setEmployerpf(centralTaxDTO.getEmployerpf());
			ct.setNoofemployees(centralTaxDTO.getNoofemployees());
			ct.setCeilvalueesi(centralTaxDTO.getCeilvalueEsi());
			ct.setCeilvaluepf(centralTaxDTO.getCeilvaluepf());
			ct.setEmployeeesi(centralTaxDTO.getEmployeeEsi());
			ct.setEmployeresi(centralTaxDTO.getEmployerEsi());
			ct.setSourceleadTaxType(getTaxTypeById(centralTaxDTO.getSourceleadTaxId()));
			
			List<Tds> tdsList = new LinkedList<>();
			for (TdsDTO tdsDTO : centralTaxDTO.getTdsTaxList()) {
				Tds tds = null;
				if (tdsDTO.getId() > 0) {
//					tds = new Tds();
				} else {
					tds = new Tds();
				}
				
				tds.setAge(tdsDTO.getAge());
				tds.setFromamount(tdsDTO.getFromamount());
				tds.setToamount(tdsDTO.getToamount());
				tds.setPercentage(tdsDTO.getPercentage());
				tds.setCentraltaxid(ct);;
				tdsList.add(tds);
			}
			ct.settdsTaxList(tdsList);
			return taxDao.storeCentralTax(ct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	/*@Override
	public void saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations) {
		CentralTax centralTax = null;
		if (centralTaxDTO.getId() != null) {
			// service
		} else {
			centralTax = new CentralTax();
		}
		centralTax.setCeilvalueesi(centralTaxDTO.getCeilvalueEsi());
		centralTax.setCeilvaluepf(centralTaxDTO.getCeilvaluepf());
		centralTax.setEmployeeesi(centralTaxDTO.getEmployeeEsi());
		centralTax.setEmployeepf(centralTaxDTO.getEmployeepf());
		centralTax.setEmployeresi(centralTaxDTO.getEmployerEsi());
		centralTax.setEmployerpf(centralTaxDTO.getEmployerpf());
		centralTax.setNoofemployees(centralTaxDTO.getNoofemployees());
		centralTax.setSourceleadTaxType(getTaxTypeById(centralTaxDTO.getSourceleadTaxId()));
		centralTax = taxDao.storeCentralTax(centralTax);
		Tds tds = new Tds();
		tds.setAge(centralTaxDTO.getAge());
		tds.setFromamount(centralTaxDTO.getFromamount());
		tds.setToamount(centralTaxDTO.getToamount());
		tds.setPercentage(centralTaxDTO.getPercentage());
		tds.setCentraltaxid(centralTax);
		tds = taxDao.storeTds(tds);
	}
*/
	
	
	
	
	@Override
	public SourceleadTaxType getTaxTypeById(long id) {
		try {
			return taxDao.getTaxTypeById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
