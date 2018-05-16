package com.bis.sourcelead.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.ProfessionalTaxDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
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

	@Override
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
	public SourceleadTaxType getTaxTypeById(long id) {
		try {
			return taxDao.getTaxTypeById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
