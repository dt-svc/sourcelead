package com.bis.sourcelead.service.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;
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
import com.bis.sourcelead.util.CommonUtil;

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
			List<StateTax> stList = taxDao.getStateTaxList(yearFrequency, stateId, 0);
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

	
	

	@Override
	public List<CentralTaxDTO> getCentralTaxList(String yearFrequency, long sourceleadTaxId) {
			List<CentralTaxDTO> centralTaxList = new LinkedList<>();
			try {
				List<CentralTax> ctList = taxDao.getCentralTaxList(yearFrequency, sourceleadTaxId, 0);
				/*if (ctList != null && !ctList.isEmpty()) {
					for (CentralTax ct : ctList) {
						centralTaxList.add(getCentralTaxDTO(ct, false));
					}
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}
			return centralTaxList;
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
					professionalTaxDTO.setAmount(CommonUtil.toFixed2(pt.getAmount()));
					professionalTaxDTO.setAmountFrom(CommonUtil.toFixed2(pt.getAmountFrom()));
					professionalTaxDTO.setAmountTo(CommonUtil.toFixed2(pt.getAmountTo()));
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
			List<StateTax> stList = taxDao.getStateTaxList(stateTaxDTO.getFinancialYear(), stateTaxDTO.getStateId(),
					stateTaxDTO.getTaxTypeId());
			if (stList != null && stList.size() > 0) {
				return -1;
			} else {
				StateTax st = null;
				if (stateTaxDTO.getId() > 0) {
					st = taxDao.getStateTax(stateTaxDTO.getId());
				} else {
					st = new StateTax();
				}
				st.setId(stateTaxDTO.getId());
				st.setFinancialYear(stateTaxDTO.getFinancialYear());
				st.setSourceleadTaxType(getTaxTypeById(stateTaxDTO.getTaxTypeId()));
				st.setStates(statesService.getStateById(stateTaxDTO.getStateId()));
				List<ProfessionalTax> ptList = new LinkedList<>();
				for (ProfessionalTaxDTO ptDTO : stateTaxDTO.getProfessionalTaxList()) {
					ProfessionalTax pt = null;
					if (ptDTO.getId() > 0) {
						pt = getProfessionalTaxById(ptDTO.getId());
					} else {
						pt = new ProfessionalTax();
					}
					pt.setAmountFrom(Double.parseDouble(ptDTO.getAmountFrom()));
					pt.setAmountTo(Double.parseDouble(ptDTO.getAmountTo()));
					pt.setAmount(Double.parseDouble(ptDTO.getAmount()));
					pt.setMonthApplicable(ptDTO.getMonthApplicable());
					pt.setStateTax(st);
					ptList.add(pt);
				}
				st.setProfessionalTaxList(ptList);
				return taxDao.addStateTax(st);
			}
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
			
			HashSet<Tds> tdsList = new LinkedHashSet<>();
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
				tds.setCentraltaxid(ct);
				tdsList.add(tds);
			}
			ct.setTdsList(tdsList);
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

	@Override
	public ProfessionalTax getProfessionalTaxById(long id) {
		try {
			return taxDao.getProfessionalTaxById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
