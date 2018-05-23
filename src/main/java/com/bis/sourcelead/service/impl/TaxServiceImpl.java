package com.bis.sourcelead.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.ProfessionalTaxDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.enums.Result;
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
	public List<SourceleadTaxType> getAllTaxesByType(long type, boolean includeInactive) {
		return taxDao.getAllTaxesByType(type, includeInactive);
	}

	@Override
	public List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId, Paginator paginator) {
		List<StateTaxDTO> stateTaxList = new LinkedList<>();
		try {
			List<StateTax> stList = taxDao.getStateTaxList(yearFrequency, stateId, 0, paginator);
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
			stateTaxDTO.setActive(stateTax.isActive());
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
					stateTaxDTO.getTaxTypeId(), null);
			if (stateTaxDTO.getId() == 0 && stList != null && stList.size() > 0) {
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

				// While editing, if an existing ProfessionalTax row is deleted, then it should
				// be manually delete as below by iterating all removeIds
				if (stateTaxDTO.getRemoveIds() != null && !stateTaxDTO.getRemoveIds().isEmpty()) {
					for (String id : stateTaxDTO.getRemoveIds().split(",")) {
						taxDao.removeProfesionalTax(taxDao.getProfessionalTaxById(Long.valueOf(id)));
					}
				}

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

	@Override
	public Response<String> changeStateTaxStatus(long stateTaxId) {
		Response<String> response = new Response<>();
		try {
			long result = taxDao.changeStateTaxStatus(stateTaxId);
			if (result > 0) {
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (result == -2) {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			} else {
				response.setResponse(Result.FAILED.name());
				response.setResponseMessage(Result.FAILED.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
