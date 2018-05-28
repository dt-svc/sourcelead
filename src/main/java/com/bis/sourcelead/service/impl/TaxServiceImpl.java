package com.bis.sourcelead.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.ProfessionalTaxDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxSectionDTO;
import com.bis.sourcelead.dto.SourceleadTaxSectionItemDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxForm;
import com.bis.sourcelead.entity.SourceleadTaxSection;
import com.bis.sourcelead.entity.SourceleadTaxSectionItem;
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
				st.setActive(true);
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

	@Override
	public List<SourceleadTaxFormDTO> getTaxFormTypeList(boolean includeAll) {
		List<SourceleadTaxFormDTO> sourceleadTaxFormList = new LinkedList<>();
		try {
			List<SourceleadTaxForm> stList = taxDao.getTaxFormTypeList(true);
			for (SourceleadTaxForm stForm : stList) {
				sourceleadTaxFormList.add(getTaxFormType(stForm, includeAll));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceleadTaxFormList;
	}

	@Override
	public long addITDeclarationSection(SourceleadTaxSectionDTO sectionDTO) {
		try {
			SourceleadTaxSection section = null;
			if (sectionDTO.getId() > 0) {
				section = taxDao.getTaxSection(sectionDTO.getId());
			} else {
				section = new SourceleadTaxSection();
			}
			if (section != null) {
				section.setName(sectionDTO.getName());
				section.setDescription(sectionDTO.getDescription());
				section.setActive(true);
				section.setSourceleadTaxForm(taxDao.getTaxFormType(sectionDTO.getTaxFormId()));
				return taxDao.addITDeclarationSection(section);
			} else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public SourceleadTaxFormDTO getTaxFormType(long taxFormId) {
		try {
			SourceleadTaxForm sourceleadTaxForm = taxDao.getTaxFormType(taxFormId);
			if (sourceleadTaxForm != null) {
				return getTaxFormType(sourceleadTaxForm, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SourceleadTaxFormDTO getTaxFormType(SourceleadTaxForm sourceleadTaxForm, boolean includeAll) {
		SourceleadTaxFormDTO sourceleadTaxFormDTO = new SourceleadTaxFormDTO();
		try {
			if (sourceleadTaxForm != null) {
				sourceleadTaxFormDTO.setId(sourceleadTaxForm.getId());
				sourceleadTaxFormDTO.setName(sourceleadTaxForm.getName());
				sourceleadTaxFormDTO.setActive(sourceleadTaxForm.isActive());

				if (includeAll) {
					// Iterating over all TaxSections for the current TaxForm
					List<SourceleadTaxSectionDTO> taxSectionDTOList = new LinkedList<>();
					List<SourceleadTaxSection> taxSectionList = sourceleadTaxForm.getSourceleadTaxSectionList();
					if (taxSectionList != null && !taxSectionList.isEmpty()) {
						for (SourceleadTaxSection taxSection : taxSectionList) {
							SourceleadTaxSectionDTO taxSectionDTO = new SourceleadTaxSectionDTO();
							taxSectionDTO.setId(taxSection.getId());
							taxSectionDTO.setName(taxSection.getName());
							taxSectionDTO.setDescription(taxSection.getDescription());
							taxSectionDTO.setActive(taxSection.isActive());
							if (taxSection.getSourceleadTaxForm() != null) {
								taxSectionDTO.setTaxFormId(taxSection.getSourceleadTaxForm().getId());
								taxSectionDTO.setTaxFormName(taxSection.getSourceleadTaxForm().getName());
							}
							// Iterating over all TaxSectionItems for the current TaxSection
							List<SourceleadTaxSectionItemDTO> taxSectionItemDTOList = new LinkedList<>();
							List<SourceleadTaxSectionItem> taxSectionItemList = taxSection.getSourceleadTaxSectionItemsList();
							if (taxSectionItemList != null && !taxSectionItemList.isEmpty()) {
								for (SourceleadTaxSectionItem taxSectionItem : taxSectionItemList) {
									SourceleadTaxSectionItemDTO taxSectionItemDTO = new SourceleadTaxSectionItemDTO();
									taxSectionItemDTO.setId(taxSectionItem.getId());
									taxSectionItemDTO.setName(taxSectionItem.getName());
									taxSectionItemDTO.setDescription(taxSectionItem.getDescription());
									taxSectionItemDTO.setMaxLimit(CommonUtil.toFixed2(taxSectionItem.getMaxLimit()));
									taxSectionItemDTO.setActive(taxSectionItem.isActive());
									if (taxSectionItem.getSourceleadTaxSection() != null) {
										taxSectionItemDTO.setTaxSectionId(taxSectionItem.getSourceleadTaxSection().getId());
										taxSectionItemDTO.setTaxSectionName(taxSectionItem.getSourceleadTaxSection().getName());
									}
									taxSectionItemDTOList.add(taxSectionItemDTO);
								}
							}
							taxSectionDTO.setSourceleadTaxSectionItemList(taxSectionItemDTOList);
							taxSectionDTOList.add(taxSectionDTO);
						}
					}
					sourceleadTaxFormDTO.setSourceleadTaxSectionList(taxSectionDTOList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceleadTaxFormDTO;
	}

	@Override
	public long addITDeclarationSectionItem(SourceleadTaxSectionItemDTO sectionItemDTO) {
		try {
			SourceleadTaxSectionItem sectionItem = null;
			if (sectionItemDTO.getId() > 0) {
				sectionItem = taxDao.getTaxSectionItem(sectionItemDTO.getId());
			} else {
				sectionItem = new SourceleadTaxSectionItem();
			}
			if (sectionItem != null) {
				sectionItem.setName(sectionItemDTO.getName());
				sectionItem.setDescription(sectionItemDTO.getDescription());
				sectionItem.setMaxLimit(Double.parseDouble(sectionItemDTO.getMaxLimit()));
				sectionItem.setActive(true);
				sectionItem.setSourceleadTaxSection(taxDao.getTaxSection(sectionItemDTO.getTaxSectionId()));
				return taxDao.addITDeclarationSectionItem(sectionItem);
			} else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Response<String> changeTaxFormStatus(long id) {
		Response<String> response = new Response<>();
		try {
			long result = taxDao.changeTaxFormStatus(id);
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

	@Override
	public Response<String> changeITDeclarationSectionStatus(long id) {
		Response<String> response = new Response<>();
		try {
			long result = taxDao.changeITDeclarationSectionStatus(id);
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

	@Override
	public Response<String> changeITDeclarationSectionItemStatus(long id) {
		Response<String> response = new Response<>();
		try {
			long result = taxDao.changeITDeclarationSectionItemStatus(id);
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
