package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.ProfessionalTaxDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionSectionDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.dto.TdsDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.CentralTax;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxForm;
import com.bis.sourcelead.entity.SourceleadTaxITDeclaration;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationItem;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationSubmission;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationSubmissionItem;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class TaxServiceImpl implements TaxService {

	@Autowired
	private TaxDao taxDao;

	@Autowired
	private StatesService statesService;

	@Autowired
	private UserService userService;

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
	public List<CentralTaxDTO> getCentralTaxList(String yearFrequency) {
		List<CentralTaxDTO> centralTaxList = new LinkedList<>();
		try {
			List<CentralTax> ctList = taxDao.getCentralTaxList(yearFrequency, 0);
			if (ctList != null && !ctList.isEmpty()) {
				for (CentralTax ct : ctList) {
					centralTaxList.add(getCentralTaxDTO(ct, false));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return centralTaxList;
	}

	public StateTaxDTO getStateTax(String statetaxuuid) {
		StateTaxDTO stateTaxDTO = new StateTaxDTO();
		try {
			StateTax stateTax = taxDao.getStateTax(statetaxuuid);
			if (stateTax != null) {
				stateTaxDTO = getStateTaxDTO(stateTax, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stateTaxDTO;
	}

	@Override
	public CentralTaxDTO getCentralTax(String centraltaxuuid) {
		CentralTaxDTO centralTaxDTO = new CentralTaxDTO();
		try {
			CentralTax centralTax = taxDao.getCentralTax(centraltaxuuid);
			if (centralTax != null) {
				centralTaxDTO = getCentralTaxDTO(centralTax, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return centralTaxDTO;
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
			stateTaxDTO.setStatetaxuuid(stateTax.getStatetaxuuid());
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

	public CentralTaxDTO getCentralTaxDTO(CentralTax centralTax, boolean includeAll) {
		try {
			CentralTaxDTO centralTaxDTO = new CentralTaxDTO();
			centralTaxDTO.setId(centralTax.getId());
			centralTaxDTO.setCeilvalueEsi(centralTax.getCeilvalueesi());
			centralTaxDTO.setCeilvaluepf(centralTax.getCeilvaluepf());
			centralTaxDTO.setEmployeeEsi(centralTax.getEmployeeesi());
			centralTaxDTO.setEmployerEsi(centralTax.getEmployeresi());
			centralTaxDTO.setEmployeepf(centralTax.getEmployeepf());
			centralTaxDTO.setEmployerpf(centralTax.getEmployerpf());
			centralTaxDTO.setNoofemployees(centralTax.getNoofemployees());
			centralTaxDTO.setSourceleadTaxId(centralTax.getSourceleadTaxType().getId());
			centralTaxDTO.setSourceleadTaxType(centralTax.getSourceleadTaxType().getDescription());
			centralTaxDTO.setCtfinancialYear(centralTax.getCtfinancialYear());
			centralTaxDTO.setCentraltaxuuid(centralTax.getCentraltaxuuid());
			centralTaxDTO.setActive(centralTax.isActive());
			if (includeAll) {
				List<TdsDTO> tdsTaxList = new LinkedList<>();
				for (Tds tds : centralTax.getTdsList()) {
					TdsDTO tdsDTO = new TdsDTO();
					tdsDTO.setAge(tds.getAge());
					tdsDTO.setFromamount(tds.getFromamount());
					tdsDTO.setToamount(tds.getToamount());
					tdsDTO.setPercentage(tds.getPercentage());
					tdsDTO.setEducationceiss(tds.getEducationceiss());
					tdsDTO.setSurcharge(tds.getSurcharge());
					tdsDTO.setId(tds.getId());
					tdsTaxList.add(tdsDTO);
				}
				centralTaxDTO.setTdsTaxList(tdsTaxList);
			}
			return centralTaxDTO;
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
					st = taxDao.getStateTax(stateTaxDTO.getStatetaxuuid());
				} else {
					st = new StateTax();
					String sttaxuuid = SourceLeadRandomCodeHelper.generateRandomTaxId();
					st.setStatetaxuuid(sttaxuuid);
				}

				st.setId(stateTaxDTO.getId());
				st.setFinancialYear(stateTaxDTO.getFinancialYear());

				st.setActive(true);
				st.setSourceleadTaxType(getTaxTypeById(stateTaxDTO.getTaxTypeId()));
				st.setStates(statesService.getStateById(stateTaxDTO.getStateId()));

				// While editing, if an existing ProfessionalTax row is deleted,
				// then it should
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
	public long saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations) {
		try {
			List<CentralTax> ctList = taxDao.getCentralTaxList(centralTaxDTO.getCtfinancialYear(), centralTaxDTO.getSourceleadTaxId());
			if (centralTaxDTO.getId() == 0 && ctList != null && ctList.size() > 0) {
				return -1;
			} else {
				CentralTax ct = null;
				if (centralTaxDTO.getId() > 0) {
					ct = taxDao.getCentralTax(centralTaxDTO.getCentraltaxuuid());
				} else {
					ct = new CentralTax();
					String cttaxuuid = SourceLeadRandomCodeHelper.generateRandomTaxId();
					ct.setCentraltaxuuid(cttaxuuid);
				}
				ct.setCeilvaluepf(centralTaxDTO.getCeilvaluepf());
				ct.setEmployeepf(centralTaxDTO.getEmployeepf());
				ct.setEmployerpf(centralTaxDTO.getEmployerpf());
				ct.setNoofemployees(centralTaxDTO.getNoofemployees());
				ct.setCeilvalueesi(centralTaxDTO.getCeilvalueEsi());
				ct.setCeilvaluepf(centralTaxDTO.getCeilvaluepf());
				ct.setEmployeeesi(centralTaxDTO.getEmployeeEsi());
				ct.setEmployeresi(centralTaxDTO.getEmployerEsi());
				ct.setCtfinancialYear(centralTaxDTO.getCtfinancialYear());
				ct.setActive(true);
				ct.setSourceleadTaxType(getTaxTypeById(centralTaxDTO.getSourceleadTaxId()));

				if (centralTaxDTO.getRemoveIds() != null && !centralTaxDTO.getRemoveIds().isEmpty()) {
					for (String id : centralTaxDTO.getRemoveIds().split(",")) {
						taxDao.removeTDSTax(taxDao.getTDSById(Long.valueOf(id)));
					}
				}

				HashSet<Tds> tdsList = new LinkedHashSet<>();
				for (TdsDTO tdsDTO : centralTaxDTO.getTdsTaxList()) {
					Tds tds = null;
					if (tdsDTO.getId() > 0) {
						tds = getTDSById(tdsDTO.getId());
					} else {
						tds = new Tds();
					}
					tds.setAge(tdsDTO.getAge());
					tds.setFromamount(tdsDTO.getFromamount());
					tds.setToamount(tdsDTO.getToamount());
					tds.setPercentage(tdsDTO.getPercentage());
					tds.setEducationceiss(tdsDTO.getEducationceiss());
					tds.setSurcharge(tdsDTO.getSurcharge());
					tds.setCentraltaxid(ct);
					tdsList.add(tds);
				}
				ct.setTdsList(tdsList);
				return taxDao.storeCentralTax(ct);
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
	public Response<String> changeCentralTaxStatus(long centralTaxId) {
		Response<String> response = new Response<>();
		try {
			long result = taxDao.changeCentralTaxStatus(centralTaxId);
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
	public Tds getTDSById(long id) {
		try {
			return taxDao.getTDSById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SourceleadTaxFormDTO> getTaxFormTypeList(boolean includeAll) {
		List<SourceleadTaxFormDTO> sourceleadTaxFormList = new LinkedList<>();
		try {
			List<SourceleadTaxForm> stList = taxDao.getTaxFormTypeList(true);
			for (SourceleadTaxForm stForm : stList) {
				sourceleadTaxFormList.add(getTaxFormType(stForm, includeAll, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceleadTaxFormList;
	}

	@Override
	public List<SourceleadTaxFormDTO> getTaxFormTypeListForSubmission(int year) {
		List<SourceleadTaxFormDTO> sourceleadTaxFormList = new LinkedList<>();
		try {
			List<SourceleadTaxForm> stList = taxDao.getTaxFormTypeList(false);
			for (SourceleadTaxForm sourceleadTaxForm : stList) {
				if (sourceleadTaxForm.isActive()) {
					SourceleadTaxFormDTO sourceleadTaxFormDTO = new SourceleadTaxFormDTO();
					sourceleadTaxFormDTO.setId(sourceleadTaxForm.getId());
					sourceleadTaxFormDTO.setName(sourceleadTaxForm.getName());
					sourceleadTaxFormDTO.setUuid(sourceleadTaxForm.getUuid());
					sourceleadTaxFormDTO.setActive(sourceleadTaxForm.isActive());
					sourceleadTaxFormDTO.setFinancialYear(year + "");
					int count = taxDao.getITDeclarationFormsCountByYear(year, sourceleadTaxForm.getName().replaceAll(" ", ""));
					sourceleadTaxFormDTO.setTotalFormsCount(count);
					if (count > 0)
						sourceleadTaxFormList.add(sourceleadTaxFormDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceleadTaxFormList;
	}

	@Override
	public long addITDeclarationSection(SourceleadTaxITDeclarationDTO sectionDTO) {
		try {
			SourceleadTaxITDeclaration section = null;
			if (sectionDTO.getId() > 0) {
				section = taxDao.getTaxSection(sectionDTO.getId());
			} else {
				section = new SourceleadTaxITDeclaration();
				section.setUuid(SourceLeadRandomCodeHelper.generateRandomTaxId());
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
	public SourceleadTaxFormDTO getTaxFormType(long taxFormId, boolean includeAll, boolean isAdminFlag) {
		try {
			SourceleadTaxForm sourceleadTaxForm = taxDao.getTaxFormType(taxFormId);
			if (sourceleadTaxForm != null) {
				return getTaxFormType(sourceleadTaxForm, includeAll, isAdminFlag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SourceleadTaxFormDTO getTaxFormType(String uuid, boolean includeAll, boolean includeInactive) {
		try {
			SourceleadTaxForm sourceleadTaxForm = taxDao.getTaxFormType(uuid);
			if (sourceleadTaxForm != null) {
				return getTaxFormType(sourceleadTaxForm, includeAll, includeInactive);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// @Override
	public SourceleadTaxFormDTO getTaxFormType(SourceleadTaxForm sourceleadTaxForm, boolean includeAll, boolean includeInactive) {
		SourceleadTaxFormDTO sourceleadTaxFormDTO = new SourceleadTaxFormDTO();
		try {
			if (sourceleadTaxForm != null) {
				sourceleadTaxFormDTO.setId(sourceleadTaxForm.getId());
				sourceleadTaxFormDTO.setName(sourceleadTaxForm.getName());
				sourceleadTaxFormDTO.setUuid(sourceleadTaxForm.getUuid());
				sourceleadTaxFormDTO.setActive(sourceleadTaxForm.isActive());

				if (includeAll) {
					// Iterating over all TaxSections for the current TaxForm
					List<SourceleadTaxITDeclarationDTO> taxSectionDTOList = new LinkedList<>();
					List<SourceleadTaxITDeclaration> taxSectionList = sourceleadTaxForm.getSourceleadTaxSectionList();
					if (taxSectionList != null && !taxSectionList.isEmpty()) {
						for (SourceleadTaxITDeclaration taxSection : taxSectionList) {
							if (includeInactive || (!includeInactive && taxSection.isActive())) {
								SourceleadTaxITDeclarationDTO taxSectionDTO = new SourceleadTaxITDeclarationDTO();
								taxSectionDTO.setId(taxSection.getId());
								taxSectionDTO.setName(taxSection.getName());
								taxSectionDTO.setDescription(taxSection.getDescription());
								taxSectionDTO.setActive(taxSection.isActive());
								if (taxSection.getSourceleadTaxForm() != null) {
									taxSectionDTO.setTaxFormId(taxSection.getSourceleadTaxForm().getId());
									taxSectionDTO.setTaxFormName(taxSection.getSourceleadTaxForm().getName());
								}
								// Iterating over all TaxSectionItems for the current TaxSection
								List<SourceleadTaxITDeclarationItemDTO> taxSectionItemDTOList = new LinkedList<>();
								List<SourceleadTaxITDeclarationItem> taxSectionItemList = taxSection.getSourceleadTaxITDeclarationList();
								if (taxSectionItemList != null && !taxSectionItemList.isEmpty()) {
									for (SourceleadTaxITDeclarationItem taxSectionItem : taxSectionItemList) {
										if (includeInactive || (!includeInactive && taxSectionItem.isActive())) {
											SourceleadTaxITDeclarationItemDTO taxSectionItemDTO = new SourceleadTaxITDeclarationItemDTO();
											taxSectionItemDTO.setId(taxSectionItem.getId());
											taxSectionItemDTO.setName(taxSectionItem.getName());
											taxSectionItemDTO.setDescription(taxSectionItem.getDescription());
											taxSectionItemDTO.setMaxLimit(CommonUtil.toFixed2(taxSectionItem.getMaxLimit()));
											taxSectionItemDTO.setActive(taxSectionItem.isActive());
											if (taxSectionItem.getSourceleadTaxITDeclaration() != null) {
												taxSectionItemDTO.setTaxSectionId(taxSectionItem.getSourceleadTaxITDeclaration().getId());
												taxSectionItemDTO
														.setTaxSectionName(taxSectionItem.getSourceleadTaxITDeclaration().getName());
												if (taxSectionItem.getSourceleadTaxITDeclaration().getSourceleadTaxForm() != null) {
													taxSectionItemDTO.setTaxFormId(
															taxSectionItem.getSourceleadTaxITDeclaration().getSourceleadTaxForm().getId());
													taxSectionItemDTO.setTaxFormName(taxSectionItem.getSourceleadTaxITDeclaration()
															.getSourceleadTaxForm().getName());
												}
											}
											taxSectionItemDTOList.add(taxSectionItemDTO);
										}
									}
								}
								taxSectionDTO.setSectionItemList(taxSectionItemDTOList);
								taxSectionDTOList.add(taxSectionDTO);
							}
						}
					}
					sourceleadTaxFormDTO.setItDeclarationSectionList(taxSectionDTOList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceleadTaxFormDTO;
	}

	@Override
	public long addITDeclarationSectionItem(SourceleadTaxITDeclarationItemDTO sectionItemDTO) {
		try {
			SourceleadTaxITDeclarationItem sectionItem = null;
			if (sectionItemDTO.getId() > 0) {
				sectionItem = taxDao.getTaxSectionItem(sectionItemDTO.getId());
			} else {
				sectionItem = new SourceleadTaxITDeclarationItem();
			}
			if (sectionItem != null) {
				sectionItem.setName(sectionItemDTO.getName());
				sectionItem.setDescription(sectionItemDTO.getDescription());
				sectionItem.setMaxLimit(Double.parseDouble(sectionItemDTO.getMaxLimit()));
				sectionItem.setActive(true);
				sectionItem.setSourceleadTaxITDeclaration(taxDao.getTaxSection(sectionItemDTO.getTaxSectionId()));
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

	@Override
	public Response<String> submitITDeclaration(SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO, User loggedInUser) {
		Response<String> response = new Response<>();
		try {
			SourceleadTaxITDeclarationSubmission itDeclarationSubmission = null;
			if (itDeclarationSubmissionDTO.getId() > 0) {
				itDeclarationSubmission = taxDao.getITDeclarationSubmission(itDeclarationSubmissionDTO.getId());
				itDeclarationSubmission.setUpdatedBy(loggedInUser);
				itDeclarationSubmission.setUpdatedDate(new Date());
			} else {
				itDeclarationSubmission = new SourceleadTaxITDeclarationSubmission();
				itDeclarationSubmission.setCreatedBy(loggedInUser);
				itDeclarationSubmission.setCreatedDate(new Date());
				itDeclarationSubmission.setUuid(SourceLeadRandomCodeHelper.generateRandomTaxId());
			}
			itDeclarationSubmission.setFinancialYear(itDeclarationSubmissionDTO.getFinancialYear());
			itDeclarationSubmission.setUser(userService.getUserById(itDeclarationSubmissionDTO.getUserId()));
			itDeclarationSubmission.setStatus(itDeclarationSubmissionDTO.getStatus());
			itDeclarationSubmission.setSourceleadTaxForm(taxDao.getTaxFormType(itDeclarationSubmissionDTO.getTaxFormId()));
			List<SourceleadTaxITDeclarationSubmissionItem> submissionItemList = new LinkedList<>();
			for (SourceleadTaxITDeclarationSubmissionItemDTO submissionItemDTO : itDeclarationSubmissionDTO.getSubmissionItemList()) {
				SourceleadTaxITDeclarationSubmissionItem submissionItem;
				if (submissionItemDTO.getId() > 0)
					submissionItem = taxDao.getITDeclarationSubmissionItem(submissionItemDTO.getId());
				else
					submissionItem = new SourceleadTaxITDeclarationSubmissionItem();
				submissionItem.setName(submissionItemDTO.getName());
				submissionItem.setMaxLimit(submissionItemDTO.getMaxLimit());
				submissionItem.setAmount(submissionItemDTO.getAmount());
				submissionItem.setDescription(submissionItemDTO.getDescription());
				submissionItem.setSourceleadTaxITDeclarationSubmission(itDeclarationSubmission);
				submissionItem.setSourceleadTaxITDeclaration(taxDao.getTaxSection(submissionItemDTO.getTaxSectionId()));
				submissionItemList.add(submissionItem);
			}
			itDeclarationSubmission.setSubmissionItemList(submissionItemList);
			long result = taxDao.submitITDeclaration(itDeclarationSubmission);
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
	public List<SourceleadTaxITDeclarationSubmissionDTO> getITDeclarationSubmissionList(String uuid, int year) {
		List<SourceleadTaxITDeclarationSubmissionDTO> itDeclarationSubmissionList = new LinkedList<>();
		try {
			List<SourceleadTaxITDeclarationSubmission> itList = taxDao.getITDeclarationSubmissionList(uuid, year);
			if (itList != null && !itList.isEmpty()) {
				for (SourceleadTaxITDeclarationSubmission itSubmission : itList) {
					SourceleadTaxITDeclarationSubmissionDTO itSubmissionDTO = new SourceleadTaxITDeclarationSubmissionDTO();
					itSubmissionDTO.setId(itSubmission.getId());
					itSubmissionDTO.setUuid(itSubmission.getUuid());
					itSubmissionDTO.setFinancialYear(itSubmission.getFinancialYear());
					itSubmissionDTO.setStatus(itSubmission.getStatus());
					itSubmissionDTO.setUserId(itSubmission.getUser().getId());
					itSubmissionDTO.setUserName(itSubmission.getUser().getFullName());
					itSubmissionDTO.setTaxFormId(itSubmission.getSourceleadTaxForm().getId());

					itDeclarationSubmissionList.add(itSubmissionDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itDeclarationSubmissionList;
	}

	@Override
	public SourceleadTaxITDeclarationSubmissionDTO getITDeclarationSubmission(String taxFormUUID, String formUUID) {
		try {
			SourceleadTaxITDeclarationSubmission itDeclarationSubmission = taxDao.getITDeclarationSubmission(taxFormUUID, formUUID);
			if (itDeclarationSubmission != null) {
				SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO = new SourceleadTaxITDeclarationSubmissionDTO();
				itDeclarationSubmissionDTO.setId(itDeclarationSubmission.getId());
				itDeclarationSubmissionDTO.setUuid(itDeclarationSubmission.getUuid());
				itDeclarationSubmissionDTO.setTaxFormId(itDeclarationSubmission.getSourceleadTaxForm().getId());
				itDeclarationSubmissionDTO.setStatus(itDeclarationSubmission.getStatus());
				itDeclarationSubmissionDTO.setFinancialYear(itDeclarationSubmission.getFinancialYear());
				if (itDeclarationSubmission.getUser() != null) {
					itDeclarationSubmissionDTO.setUserId(itDeclarationSubmission.getUser().getId());
					itDeclarationSubmissionDTO.setUserName(itDeclarationSubmission.getUser().getFullName());
				}

				Set<SourceleadTaxITDeclaration> sectionList = new LinkedHashSet<>();
				for (SourceleadTaxITDeclarationSubmissionItem submissionItem : itDeclarationSubmission.getSubmissionItemList()) {
					sectionList.add(submissionItem.getSourceleadTaxITDeclaration());
				}

				List<SourceleadTaxITDeclarationSubmissionSectionDTO> submittedSectionList = new LinkedList<>();

				for (SourceleadTaxITDeclaration section : sectionList) {
					SourceleadTaxITDeclarationSubmissionSectionDTO submittedSectionDTO = new SourceleadTaxITDeclarationSubmissionSectionDTO();
					submittedSectionDTO.setId(section.getId());
					submittedSectionDTO.setName(section.getName());
					submittedSectionDTO.setDescription(section.getDescription());
					submittedSectionDTO.setActive(section.isActive());
					List<SourceleadTaxITDeclarationSubmissionItemDTO> submissionItemList = new LinkedList<>();

					for (SourceleadTaxITDeclarationSubmissionItem submissionItem : itDeclarationSubmission.getSubmissionItemList()) {
						if (submissionItem.getSourceleadTaxITDeclaration().getId() == section.getId()) {
							SourceleadTaxITDeclarationSubmissionItemDTO submissionItemDTO = new SourceleadTaxITDeclarationSubmissionItemDTO();
							submissionItemDTO.setId(submissionItem.getId());
							submissionItemDTO.setName(submissionItem.getName());
							submissionItemDTO.setAmount(submissionItem.getAmount());
							submissionItemDTO.setMaxLimit(submissionItem.getMaxLimit());
							submissionItemDTO.setDescription(submissionItem.getDescription());
							submissionItemDTO.setTaxSectionId(submissionItem.getSourceleadTaxITDeclaration().getId());
							submissionItemDTO.setSourceleadTaxITDeclarationSubmissionId(
									submissionItem.getSourceleadTaxITDeclarationSubmission().getId());
							submissionItemList.add(submissionItemDTO);
						}
					}
					submittedSectionDTO.setSubmissionItemList(submissionItemList);
					submittedSectionList.add(submittedSectionDTO);
				}
				itDeclarationSubmissionDTO.setSubmittedSectionList(submittedSectionList);

				return itDeclarationSubmissionDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
