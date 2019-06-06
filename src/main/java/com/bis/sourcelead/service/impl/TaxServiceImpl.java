package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.PayrollTaxDao;
import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.ProfessionalTaxDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.SourceleadTaxChallanDTO;
import com.bis.sourcelead.dto.SourceleadTaxChallanItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxChallanItemSubFormFieldDTO;
import com.bis.sourcelead.dto.SourceleadTaxFormDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionDTO;
import com.bis.sourcelead.dto.SourceleadTaxITDeclarationSubmissionItemDTO;
import com.bis.sourcelead.dto.SourceleadTaxSubmissionDocumentAttachmentDTO;
import com.bis.sourcelead.dto.SourceleadTaxSubmissionDocumentDTO;
import com.bis.sourcelead.dto.SourceleadTaxSubmissionMasterDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.dto.TdsDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CentralTax;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxChallan;
import com.bis.sourcelead.entity.SourceleadTaxChallanItem;
import com.bis.sourcelead.entity.SourceleadTaxChallanItemSubFormField;
import com.bis.sourcelead.entity.SourceleadTaxForm;
import com.bis.sourcelead.entity.SourceleadTaxITDeclaration;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationItem;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationSubmission;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionDocument;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionDocumentAttachment;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionMaster;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Service
public class TaxServiceImpl implements TaxService {

	@Autowired
	private TaxDao taxDao;
	
	@Autowired
	private PayrollTaxDao payrollTaxDao;

	@Autowired
	private StatesService statesService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private EmployeesDao employeesDao;
	
	@Autowired
	private OrgLocationsDao orgLocationsDao;

	@Autowired
	private CandidateService candidateService;

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
	public StateTaxDTO getStateTaxByStateNameAndFinancialYear(String stateName, String financialYear) {
		StateTaxDTO stateTaxDTO = new StateTaxDTO();
		try {
			StateTax stateTax = taxDao.getStateTaxByStateNameAndFinancialYear(stateName, financialYear);
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

	@Override
	public CentralTaxDTO getCentralTaxByTaxType(String taxType) {
		CentralTaxDTO centralTaxDTO = new CentralTaxDTO();
		try {
			CentralTax centralTax = taxDao.getCentralTaxByTaxType(taxType);
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
			List<CentralTax> ctList = taxDao.getCentralTaxList(centralTaxDTO.getCtfinancialYear(),
					centralTaxDTO.getSourceleadTaxId());
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
	public List<SourceleadTaxFormDTO> getTaxFormTypeListForSubmission(String financialYear, long orgLocationId) {
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
					sourceleadTaxFormDTO.setFinancialYear(financialYear);
					int count = 0;
					// TODO
					// as of now, we are getting counts for IT Declaration only, need to get
					// dynamically to all taxForm types
					if (sourceleadTaxForm.getName().equalsIgnoreCase("IT Declaration")) {
						count = taxDao.getITDeclarationFormsCountByYear(financialYear,
								sourceleadTaxForm.getName().replaceAll(" ", ""), orgLocationId);
					}
					sourceleadTaxFormDTO.setTotalFormsCount(count);
					// if (count > 0)
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
				section.setMaxLimit(new BigDecimal(sectionDTO.getMaxLimit()));
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
	public SourceleadTaxFormDTO getTaxFormType(SourceleadTaxForm sourceleadTaxForm, boolean includeAll,
			boolean includeInactive) {
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
								taxSectionDTO.setMaxLimit(taxSection.getMaxLimit() + "");
								taxSectionDTO.setDescription(taxSection.getDescription());
								taxSectionDTO.setActive(taxSection.isActive());
								if (taxSection.getSourceleadTaxForm() != null) {
									taxSectionDTO.setTaxFormId(taxSection.getSourceleadTaxForm().getId());
									taxSectionDTO.setTaxFormName(taxSection.getSourceleadTaxForm().getName());
								}
								// Iterating over all TaxSectionItems for the current TaxSection
								List<SourceleadTaxITDeclarationItemDTO> taxSectionItemDTOList = new LinkedList<>();
								List<SourceleadTaxITDeclarationItem> taxSectionItemList = taxSection
										.getSourceleadTaxITDeclarationList();
								if (taxSectionItemList != null && !taxSectionItemList.isEmpty()) {
									for (SourceleadTaxITDeclarationItem taxSectionItem : taxSectionItemList) {
										if (includeInactive || (!includeInactive && taxSectionItem.isActive())) {
											SourceleadTaxITDeclarationItemDTO taxSectionItemDTO = new SourceleadTaxITDeclarationItemDTO();
											taxSectionItemDTO.setId(taxSectionItem.getId());
											taxSectionItemDTO.setName(taxSectionItem.getName());
											taxSectionItemDTO.setDescription(taxSectionItem.getDescription());
											taxSectionItemDTO.setMaxLimit(taxSectionItem.getMaxLimit() + "");
											taxSectionItemDTO.setActive(taxSectionItem.isActive());
											if (taxSectionItem.getSourceleadTaxITDeclaration() != null) {
												taxSectionItemDTO.setTaxSectionId(
														taxSectionItem.getSourceleadTaxITDeclaration().getId());
												taxSectionItemDTO.setTaxSectionName(
														taxSectionItem.getSourceleadTaxITDeclaration().getName());
												if (taxSectionItem.getSourceleadTaxITDeclaration()
														.getSourceleadTaxForm() != null) {
													taxSectionItemDTO
															.setTaxFormId(taxSectionItem.getSourceleadTaxITDeclaration()
																	.getSourceleadTaxForm().getId());
													taxSectionItemDTO.setTaxFormName(
															taxSectionItem.getSourceleadTaxITDeclaration()
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

					List<SourceleadTaxChallanDTO> challanSectionDTOList = new LinkedList<>();
					List<SourceleadTaxChallan> challanSectipnList = sourceleadTaxForm
							.getSourceleadTaxChallanSectionList();
					for (SourceleadTaxChallan challanSection : challanSectipnList) {
						if (includeInactive || (!includeInactive && challanSection.isActive())) {
							SourceleadTaxChallanDTO challanSectionDTO = new SourceleadTaxChallanDTO();
							challanSectionDTO.setActive(challanSection.isActive());
//							challanSectionDTO.setChallanId(challanSection.getId());
							challanSectionDTO.setDescription(challanSection.getDescription());
							challanSectionDTO.setName(challanSection.getName());
							challanSectionDTO.setId(challanSection.getId());
							challanSectionDTO.setTaxFormId(challanSection.getSourceleadTaxForm().getId());
							List<SourceleadTaxChallanItemDTO> challanSectionItemDTOList = new LinkedList<>();
							List<SourceleadTaxChallanItem> challanSectionItemList = challanSection
									.getSourceleadTaxChallanItemList();
							for (SourceleadTaxChallanItem challanSectionItem : challanSectionItemList) {
								if (includeInactive || (!includeInactive && challanSectionItem.isActive())) {
									SourceleadTaxChallanItemDTO challanSectionItemDTO = new SourceleadTaxChallanItemDTO();
									challanSectionItemDTO.setCreatedDate(challanSectionItem.getCreatedDate());
									challanSectionItemDTO
											.setDataValidationRange(challanSectionItem.getDataValidationRange());
									challanSectionItemDTO
											.setDataValidationType(challanSectionItem.getDataValidationType());
									challanSectionItemDTO.setDiscription(challanSectionItem.getDiscription());
									challanSectionItemDTO.setFieldType(challanSectionItem.getFieldType());
									challanSectionItemDTO.setId(challanSectionItem.getId());
									challanSectionItemDTO.setLabelName(challanSectionItem.getLabelName());
									challanSectionItemDTO.setMaxDate(challanSectionItem.getMaxDate());
									challanSectionItemDTO.setMinDate(challanSectionItem.getMinDate());
									challanSectionItemDTO.setMaxLength(challanSectionItem.getMaxLength());
									challanSectionItemDTO.setMinLength(challanSectionItem.getMinLength());
									challanSectionItemDTO.setMinLength(challanSectionItem.getMinLength());
									challanSectionItemDTO.setPlaceHolder(challanSectionItem.getPlaceHolder());
									challanSectionItemDTO.setUpdatedDate(challanSectionItem.getUpdatedDate());
									challanSectionItemDTO.setActive(challanSectionItem.isActive());
									challanSectionItemDTOList.add(challanSectionItemDTO);
								}
							}
							challanSectionDTO.setSourceleadTaxChallanSectionItemList(challanSectionItemDTOList);
							challanSectionDTOList.add(challanSectionDTO);
						}
					}
					sourceleadTaxFormDTO.setSourceleadTaxSectionList(taxSectionDTOList);
					sourceleadTaxFormDTO.setSourceleadTaxChallanSectionList(challanSectionDTOList);
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
				sectionItem.setMaxLimit(new BigDecimal(sectionItemDTO.getMaxLimit()));
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
	public Response<Long> submitITDeclaration(SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO,
			User loggedInUser, OrgLocations orgLocations) {
		Response<Long> response = new Response<>();
		try {
			SourceleadTaxITDeclarationSubmission existedItDeclarationSubmission = taxDao.getITDeclarationSubmission(itDeclarationSubmissionDTO.getUserId(), itDeclarationSubmissionDTO.getFinancialYear());
			if(existedItDeclarationSubmission!=null && existedItDeclarationSubmission.getId() != itDeclarationSubmissionDTO.getId()) {
				response.setResponse(Result.DUPLICATE_TAX_RECORD.name());
				response.setResponseMessage(Result.DUPLICATE_TAX_RECORD.getValue());
				return response;
			}
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
			User user = userService.getUserById(itDeclarationSubmissionDTO.getUserId());
			itDeclarationSubmission.setUser(user);
			itDeclarationSubmission.setOrgLocations(orgLocations);
//			itDeclarationSubmission.setStatus(itDeclarationSubmissionDTO.getStatus());
			itDeclarationSubmission
					.setSourceleadTaxForm(taxDao.getTaxFormType(itDeclarationSubmissionDTO.getTaxFormId()));

			Map<Long, String> taxSectionMapList = new HashMap<>();
			for (SourceleadTaxITDeclarationSubmissionItemDTO submissionItemDTO : itDeclarationSubmissionDTO
					.getSubmissionItemList()) {
				taxSectionMapList.put(submissionItemDTO.getTaxSectionId(), submissionItemDTO.getTaxSectionName());
			}

			JSONArray jsonArray = new JSONArray();
			for (Long taxSectionId : taxSectionMapList.keySet()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("taxSectionId", taxSectionId);
				jsonObject.put("taxSectionName", taxSectionMapList.get(taxSectionId));
				JSONArray itemArray = new JSONArray();
				for (SourceleadTaxITDeclarationSubmissionItemDTO submissionItemDTO : itDeclarationSubmissionDTO
						.getSubmissionItemList()) {
					if (submissionItemDTO.getTaxSectionId() == taxSectionId) {
						JSONObject itemObject = new JSONObject();
						itemObject.put("name", submissionItemDTO.getName());
						itemObject.put("maxLimit", submissionItemDTO.getMaxLimit());
						itemObject.put("amount", submissionItemDTO.getAmount());
						itemObject.put("description", submissionItemDTO.getDescription());
						itemObject.put("comment", submissionItemDTO.getComment());
						itemArray.put(itemObject);
					}
				}
				jsonObject.put("items", itemArray);
				jsonArray.put(jsonObject);
			}
			// System.out.println("JSON Array : " + jsonArray);
			itDeclarationSubmission.setSubmissionItems(jsonArray.toString());
			long result = taxDao.submitITDeclaration(itDeclarationSubmission);
			if (result > 0) {
				if (itDeclarationSubmissionDTO.getId() == 0) {
					SourceleadTaxSubmissionMaster sourceleadTaxSubmissionMaster = new SourceleadTaxSubmissionMaster();
					sourceleadTaxSubmissionMaster.setFinancialYear(itDeclarationSubmissionDTO.getFinancialYear());
					sourceleadTaxSubmissionMaster.setUser(user);
					sourceleadTaxSubmissionMaster.setSourceleadTaxITDeclarationSubmission(itDeclarationSubmission);
					taxDao.saveTaxSubmissionMaster(sourceleadTaxSubmissionMaster);
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setObject(itDeclarationSubmission.getId());
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
	public List<SourceleadTaxITDeclarationSubmissionDTO> getITDeclarationSubmissionList(long orgLocationId, String uuid,
			String financialYear, String searchValue, Paginator paginator) {
		List<SourceleadTaxITDeclarationSubmissionDTO> itDeclarationSubmissionList = new LinkedList<>();
		try {
			List<SourceleadTaxITDeclarationSubmission> itList = taxDao.getITDeclarationSubmissionList(orgLocationId, uuid,
					financialYear, searchValue, paginator);
			if (itList != null && !itList.isEmpty()) {
				for (SourceleadTaxITDeclarationSubmission itSubmission : itList) {
					SourceleadTaxITDeclarationSubmissionDTO itSubmissionDTO = new SourceleadTaxITDeclarationSubmissionDTO();
					itSubmissionDTO.setId(itSubmission.getId());
					itSubmissionDTO.setUuid(itSubmission.getUuid());
					itSubmissionDTO.setFinancialYear(itSubmission.getFinancialYear());
					itSubmissionDTO.setStatus(itSubmission.getStatus());
					itSubmissionDTO.setUserId(itSubmission.getUser().getId());
					Employees emp = employeesDao.getEmployeeByUserIdAndOrgLocationsId(itSubmission.getUser().getId(), orgLocationId);
					if (emp.getEmployeeId() != null) {
						itSubmissionDTO.setEmployeeId(emp.getEmployeeId());
					} else {
						itSubmissionDTO.setEmployeeId("--");
					}
					itSubmissionDTO.setUserName(itSubmission.getUser().getFullName());
					itSubmissionDTO.setTaxFormId(itSubmission.getSourceleadTaxForm().getId());
					double submittedAmount = getCalculatedAmountForSubmittedItDeclaration(itSubmission);
					itSubmissionDTO.setSubmittedAmount(submittedAmount + "");
					//TODO need to show calculated TDS Amount also
//					itSubmissionDTO.setTaxAmount(calculateTaxAmountForSumittedAmount(submittedAmount, 0, ""));
					itSubmissionDTO.setStatus(itSubmission.getStatus());
					itDeclarationSubmissionList.add(itSubmissionDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itDeclarationSubmissionList;
	}
	
	private double getCalculatedAmountForSubmittedItDeclaration(SourceleadTaxITDeclarationSubmission itDeclarationSubmission) {
		double amount = 0;
		try {
			JSONArray jsonArray = new JSONArray(itDeclarationSubmission.getSubmissionItems());
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					if (jsonObject != null) {
						JSONArray itemsArray = jsonObject.getJSONArray("items");
						if (itemsArray != null) {
							for (int j = 0; j < itemsArray.length(); j++) {
								JSONObject itemObject = itemsArray.getJSONObject(j);
								if (itemObject != null) {
									double itemAmount = itemObject.getDouble("amount");
									amount += itemAmount;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}

	@Override
	public SourceleadTaxITDeclarationSubmissionDTO getITDeclarationSubmission(String taxFormUUID, String formUUID, Long orgLocationId) {
		try {
			SourceleadTaxITDeclarationSubmission itDeclarationSubmission = taxDao
					.getITDeclarationSubmission(taxFormUUID, formUUID);
			if (itDeclarationSubmission != null) {
				SourceleadTaxITDeclarationSubmissionDTO itDeclarationSubmissionDTO = new SourceleadTaxITDeclarationSubmissionDTO();
				itDeclarationSubmissionDTO.setId(itDeclarationSubmission.getId());
				itDeclarationSubmissionDTO.setUuid(itDeclarationSubmission.getUuid());
				itDeclarationSubmissionDTO.setTaxFormId(itDeclarationSubmission.getSourceleadTaxForm().getId());
				itDeclarationSubmissionDTO.setStatus(itDeclarationSubmission.getStatus());
				itDeclarationSubmissionDTO.setFinancialYear(itDeclarationSubmission.getFinancialYear());
				Employees emp = employeesDao.getEmployeeByUserIdAndOrgLocationsId(itDeclarationSubmission.getUser().getId(), orgLocationId);
				if (emp.getEmployeeId() != null) {
					itDeclarationSubmissionDTO.setEmployeeId(emp.getEmployeeId());
				} else {
					itDeclarationSubmissionDTO.setEmployeeId("--");
				}
				if (itDeclarationSubmission.getUser() != null) {
					itDeclarationSubmissionDTO.setUserId(itDeclarationSubmission.getUser().getId());
					itDeclarationSubmissionDTO.setUserName(itDeclarationSubmission.getUser().getFullName());
				}
				JSONArray jsonArray = new JSONArray(itDeclarationSubmission.getSubmissionItems());
				List<JSONObject> submissionItems = new LinkedList<>();
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						submissionItems.add(jsonObject);
					}
				}
				itDeclarationSubmissionDTO.setSubmissionItems(submissionItems);
				// for (int i = 0; i < jsonArray.length(); i++) {
				// JSONObject jsonObject = jsonArray.getJSONObject(i);
				//
				// }

				// List<SourceleadTaxITDeclarationSubmissionSectionDTO> submittedSectionList =
				// new LinkedList<>();
				// List<SourceleadTaxITDeclarationSubmissionItemDTO> submissionItemList = new
				// LinkedList<>();
				// JSONArray itDeclarationSubmissionItemArray = new
				// JSONArray(itDeclarationSubmission.getSubmissionItemList());
				// if (itDeclarationSubmissionItemArray != null) {
				// for (int i = 0; i < itDeclarationSubmissionItemArray.length(); i++) {
				// JSONObject itDeclarationSubmissionItemObject =
				// itDeclarationSubmissionItemArray.getJSONObject(i);
				// SourceleadTaxITDeclarationSubmissionItemDTO submissionItemDTO = new
				// SourceleadTaxITDeclarationSubmissionItemDTO();
				// submissionItemDTO.setId(itDeclarationSubmissionItemObject.getLong("id"));
				// submissionItemDTO.setName(itDeclarationSubmissionItemObject.getString("name"));
				// submissionItemDTO.setAmount(itDeclarationSubmissionItemObject.getDouble("amount"));
				// submissionItemDTO.setMaxLimit(itDeclarationSubmissionItemObject.getDouble("maxLimit"));
				// submissionItemDTO.setDescription(itDeclarationSubmissionItemObject.getString("description"));
				// submissionItemDTO.setTaxSectionId(itDeclarationSubmissionItemObject.getLong("taxSectionId"));
				// submissionItemDTO.setSourceleadTaxITDeclarationSubmissionId(itDeclarationSubmissionItemObject.getLong("sourceleadTaxITDeclarationSubmissionId"));
				// submissionItemList.add(submissionItemDTO);
				// }
				// }
				// itDeclarationSubmissionDTO.setSubmittedSectionList(submittedSectionList);

				/*
				 * Set<SourceleadTaxITDeclaration> sectionList = new LinkedHashSet<>(); for
				 * (SourceleadTaxITDeclarationSubmissionItem submissionItem :
				 * itDeclarationSubmission.getSubmissionItemList()) {
				 * sectionList.add(submissionItem.getSourceleadTaxITDeclaration()); }
				 */

				// List<SourceleadTaxITDeclarationSubmissionSectionDTO> submittedSectionList =
				// new LinkedList<>();
				//
				// for (SourceleadTaxITDeclaration section : sectionList) {
				// SourceleadTaxITDeclarationSubmissionSectionDTO submittedSectionDTO = new
				// SourceleadTaxITDeclarationSubmissionSectionDTO();
				// submittedSectionDTO.setId(section.getId());
				// submittedSectionDTO.setName(section.getName());
				// submittedSectionDTO.setDescription(section.getDescription());
				// submittedSectionDTO.setActive(section.isActive());
				// List<SourceleadTaxITDeclarationSubmissionItemDTO> submissionItemList = new
				// LinkedList<>();
				//
				// for (SourceleadTaxITDeclarationSubmissionItem submissionItem :
				// itDeclarationSubmission.getSubmissionItemList()) {
				// if (submissionItem.getSourceleadTaxITDeclaration().getId() ==
				// section.getId()) {
				// SourceleadTaxITDeclarationSubmissionItemDTO submissionItemDTO = new
				// SourceleadTaxITDeclarationSubmissionItemDTO();
				// submissionItemDTO.setId(submissionItem.getId());
				// submissionItemDTO.setName(submissionItem.getName());
				// submissionItemDTO.setAmount(submissionItem.getAmount());
				// submissionItemDTO.setMaxLimit(submissionItem.getMaxLimit());
				// submissionItemDTO.setDescription(submissionItem.getDescription());
				// submissionItemDTO.setTaxSectionId(submissionItem.getSourceleadTaxITDeclaration().getId());
				// submissionItemDTO.setSourceleadTaxITDeclarationSubmissionId(
				// submissionItem.getSourceleadTaxITDeclarationSubmission().getId());
				// submissionItemList.add(submissionItemDTO);
				// }
				// }
				// submittedSectionDTO.setSubmissionItemList(submissionItemList);
				// submittedSectionList.add(submittedSectionDTO);
				// }
				// itDeclarationSubmissionDTO.setSubmittedSectionList(submittedSectionList);

				return itDeclarationSubmissionDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveUploadedDocuments(List<SourceleadTaxSubmissionDocumentDTO> documentList, String staticPath,
			Long orgId, Long orgLocationId, long submissionId) {
		try {
			String path = staticPath + System.getProperty("file.separator") + orgId
					+ System.getProperty("file.separator") + orgLocationId + System.getProperty("file.separator")
					+ "temp";
			for (SourceleadTaxSubmissionDocumentDTO submissionDocumentDTO : documentList) {
				if (submissionDocumentDTO.getAttachmentList() != null
						&& !submissionDocumentDTO.getAttachmentList().isEmpty()) {
					SourceleadTaxSubmissionDocument taxSubmissionDocument;
					if (submissionDocumentDTO.getId() > 0) {
						taxSubmissionDocument = taxDao.getSourceleadTaxSubmissionDocumentById(submissionDocumentDTO.getId());
						if(submissionDocumentDTO.isUpdateFile()) {
							List<SourceleadTaxSubmissionDocumentAttachment> savedAttachmentList = taxSubmissionDocument.getAttachmentList();
							if(savedAttachmentList!=null) {
								for(SourceleadTaxSubmissionDocumentAttachment attachment : savedAttachmentList) {
									taxDao.removeTaxSubmissionDocumentAttachment(attachment);
								}
							}
						}
					}else {
						taxSubmissionDocument = new SourceleadTaxSubmissionDocument();
					}
					taxSubmissionDocument.setName(submissionDocumentDTO.getName());
					taxSubmissionDocument
							.setSourceleadTaxITDeclarationSubmission(taxDao.getITDeclarationSubmission(submissionId));
	
					if (submissionDocumentDTO.isUpdateFile()) {
						List<SourceleadTaxSubmissionDocumentAttachment> attachmentList = new LinkedList<>();
						for (SourceleadTaxSubmissionDocumentAttachmentDTO attachmentDTO : submissionDocumentDTO
								.getAttachmentList()) {
							File file = new File(path, attachmentDTO.getAttachment());
							SourceleadTaxSubmissionDocumentAttachment attachment;
							if (attachmentDTO.getId() > 0)
								attachment = new SourceleadTaxSubmissionDocumentAttachment();
							else {
								attachment = new SourceleadTaxSubmissionDocumentAttachment();
								attachment.setUuid(SourceLeadRandomCodeHelper.generateRandomTaxId());
							}
							attachment.setAttachment(attachmentDTO.getAttachment());
							attachment.setFileURL(attachmentDTO.getFileURL());
							attachment.setContentType(attachmentDTO.getContentType());
							attachment.setSourceleadTaxSubmissionDocument(taxSubmissionDocument);
							attachment.setActive(true);
							attachmentList.add(attachment);
							// boolean flag = taxDao.saveDocumentAttachment(attachment);
							// if (flag)
							FileUploadUtil.moveFile(staticPath, orgId, orgLocationId, "taxDocuments", file);
						}
						taxSubmissionDocument.setAttachmentList(attachmentList);
					}
					taxDao.saveDocument(taxSubmissionDocument);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteDocumentAttachment(long attachmentId) {
		try {
			taxDao.deleteDocumentAttachment(attachmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteDocument(long documentId) {
		try {
			taxDao.deleteDocument(documentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<SourceleadTaxSubmissionDocumentDTO> getUploadedDocumentsBySubmissionId(long submissionId) {
		List<SourceleadTaxSubmissionDocumentDTO> documentList = new LinkedList<>();
		try {
			List<SourceleadTaxSubmissionDocument> dList = taxDao.getUploadedDocumentsBySubmissionId(submissionId);
			if (dList != null && !dList.isEmpty()) {
				for (SourceleadTaxSubmissionDocument document : dList) {
					SourceleadTaxSubmissionDocumentDTO documentDTO = new SourceleadTaxSubmissionDocumentDTO();
					documentDTO.setId(document.getId());
					documentDTO.setName(document.getName());
					documentDTO.setTaxITSubmissionId(document.getSourceleadTaxITDeclarationSubmission().getId());
					List<SourceleadTaxSubmissionDocumentAttachmentDTO> attachmentList = new LinkedList<>();
					for (SourceleadTaxSubmissionDocumentAttachment attachment : document.getAttachmentList()) {
						SourceleadTaxSubmissionDocumentAttachmentDTO attachmentDTO = new SourceleadTaxSubmissionDocumentAttachmentDTO();
						attachmentDTO.setId(attachment.getId());
						attachmentDTO.setUuid(attachment.getUuid());
						attachmentDTO.setAttachment(attachment.getAttachment());
						attachmentDTO.setFileURL(attachment.getFileURL());
						attachmentDTO.setContentType(attachment.getContentType());
						attachmentDTO.setTaxSubmissionDocumentId(attachment.getSourceleadTaxSubmissionDocument().getId());
						attachmentDTO.setActive(attachment.isActive());
						attachmentList.add(attachmentDTO);
					}
					documentDTO.setAttachmentList(attachmentList);
					documentList.add(documentDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentList;
	}

	public long addITChallanSectionItem(SourceleadTaxChallanItemDTO sectionItemDTO) {
		try {
			SourceleadTaxChallanItem sectionItem = null;
			if (sectionItemDTO.getId() != null && sectionItemDTO.getId() > 0) {
				sectionItem = taxDao.getITChallanSectionItem(sectionItemDTO.getId());
			} else {
				sectionItem = new SourceleadTaxChallanItem();
			}
			if (sectionItem != null) {
				sectionItem.setCreatedDate(sectionItemDTO.getCreatedDate());
				sectionItem.setDataValidationRange(sectionItemDTO.getDataValidationRange());
				sectionItem.setDataValidationType(sectionItemDTO.getDataValidationType());
				sectionItem.setDiscription(sectionItemDTO.getDiscription());
				sectionItem.setFieldType(sectionItemDTO.getFieldType());
				sectionItem.setId(sectionItemDTO.getId());
				sectionItem.setLabelName(sectionItemDTO.getLabelName());
				sectionItem.setMaxDate(sectionItemDTO.getMaxDate());
				sectionItem.setMaxNumber(sectionItemDTO.getMaxNumber());
				sectionItem.setMinDate(sectionItemDTO.getMinDate());
				sectionItem.setMinNumber(sectionItemDTO.getMinNumber());
				sectionItem.setMinLength(sectionItemDTO.getMinLength());
				sectionItem.setPlaceHolder(sectionItemDTO.getPlaceHolder());
				sectionItem.setUpdatedDate(sectionItemDTO.getUpdatedDate());
				sectionItem.setHelptext(sectionItemDTO.getHelpText());
				sectionItem.setActive(true);
				sectionItem.setSourceleadtaxchallan(taxDao.getTaxChallanSection(sectionItemDTO.getTaxChallanId()));
				return taxDao.addITChallanSectionItem(sectionItem);
			} else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long addItChallanSection(SourceleadTaxChallanDTO sourceleadTaxSectionDTO) {
		try {
			SourceleadTaxChallan section = null;
			if (sourceleadTaxSectionDTO.getId() > 0) {
				 section = taxDao.getTaxChallanSection(sourceleadTaxSectionDTO.getId());
			} else {
				section = new SourceleadTaxChallan();
				section.setUuid(SourceLeadRandomCodeHelper.generateRandomTaxId());
			}
			if (section != null) {
				section.setDescription(sourceleadTaxSectionDTO.getDescription());

				section.setName(sourceleadTaxSectionDTO.getName());
				section.setActive(true);
				section.setId(sourceleadTaxSectionDTO.getId());
				section.setSourceleadTaxForm(taxDao.getTaxFormType(sourceleadTaxSectionDTO.getTaxFormId()));
				return taxDao.addItChallanSection(section);
			} else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long addSourceleadTaxChallanItemSubFormField(SourceleadTaxChallanItemSubFormFieldDTO optionData,
			long addChallanSectionItemId) {
		SourceleadTaxChallanItemSubFormField challanSubField = new SourceleadTaxChallanItemSubFormField();
		challanSubField.setId(optionData.getId());
		challanSubField.setLabelName(optionData.getLabelName());
		challanSubField.setSourceleadTaxChallanItem(taxDao.getSourceleadTaxChallanItemById(addChallanSectionItemId));
		return taxDao.addSourceleadTaxChallanItemSubFormField(challanSubField);
	}

	@Override
	public SourceleadTaxChallanItemDTO getChallanItemDetails(Long itemRowId) {
		try {
			SourceleadTaxChallanItem challanItem = taxDao.getChallanItemDetails(itemRowId);
			if (challanItem != null) {
				SourceleadTaxChallanItemDTO challanSectionItemDTO = new SourceleadTaxChallanItemDTO();
				challanSectionItemDTO.setCreatedDate(challanItem.getCreatedDate());
				challanSectionItemDTO.setDataValidationRange(challanItem.getDataValidationRange());
				challanSectionItemDTO.setDataValidationType(challanItem.getDataValidationType());
				challanSectionItemDTO.setDiscription(challanItem.getDiscription());
				challanSectionItemDTO.setFieldRequired(challanItem.getIsFieldRequired());
				challanSectionItemDTO.setFieldType(challanItem.getFieldType());
				challanSectionItemDTO.setId(challanItem.getId());
				challanSectionItemDTO.setHelpText(challanItem.getHelptext());
				challanSectionItemDTO.setLabelName(challanItem.getLabelName());
				challanSectionItemDTO.setMaxDate(challanItem.getMaxDate());
				challanSectionItemDTO.setPlaceHolder(challanItem.getPlaceHolder());
				challanSectionItemDTO.setMaxLength(challanItem.getMaxLength());
				challanSectionItemDTO.setMaxNumber(challanItem.getMaxNumber());
				challanSectionItemDTO.setUpdatedDate(challanItem.getUpdatedDate());
				challanSectionItemDTO.setStatus(challanItem.isActive());
				challanSectionItemDTO.setTaxChallanId(challanItem.getSourceleadtaxchallan().getId());
				List<SourceleadTaxChallanItemSubFormFieldDTO> subFormFieldDTOList = new LinkedList<>();
				for (SourceleadTaxChallanItemSubFormField subFormField : challanItem
						.getSourceleadTaxChallanItemSubFormFieldList()) {
					SourceleadTaxChallanItemSubFormFieldDTO subFormFieldDTO = new SourceleadTaxChallanItemSubFormFieldDTO();
					subFormFieldDTO.setId(subFormField.getId());
					subFormFieldDTO.setLabelName(subFormField.getLabelName());
					subFormFieldDTO.setSourceleadTaxChallanItemId(subFormField.getSourceleadTaxChallanItem().getId());
					subFormFieldDTOList.add(subFormFieldDTO);
				}
				challanSectionItemDTO.setOptionData(subFormFieldDTOList);
				return challanSectionItemDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getChallanItemDetails((long) 0);
	}


	
	
	@Override
	public Response<String> changeChallanItemStatus(long itemId) {
       Response<String> response = new Response<>();
       try {
    	   long result = taxDao.changeChallanItemStatus(itemId);
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
	public Response<String> changeSectionStatus(long sectionId) {
		Response<String> response = new Response<>();
		try {
			long result = taxDao.changeSectionStatus(sectionId);
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
	public Response<String> changeITDeclarationSubmissionStatus(long id, int status) {
		Response<String> response = new Response<>();
		try {
			long result = taxDao.changeITDeclarationSubmissionStatus(id, status);
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
	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmissionDetails(long formId) {
		return taxDao.getITDeclarationSubmissionDetails(formId);
	}

	@Override
	public User getEmployeeDetails(Long id) {
		return taxDao.getEmployeeDetails(id);
	}
	
	@Override
	public File exportItDeclarationSubmissionPdf(SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmissionDetails, User loginUser, long orgLocationId,User user) {
			File file = null;
		try {
			OrgLocations orgLoc = orgLocationsDao.getOrgLocations(orgLocationId);
			if(orgLoc != null) {
				String FONTB = "/fonts/OpenSans-Bold.ttf";
				String FONT = "/fonts/OpenSans-Regular.ttf";
				BaseFont bf = null, bfb = null;
		//		StringBuilder fileSB = new StringBuilder();
					bf = BaseFont.createFont(FONT, BaseFont.WINANSI, BaseFont.EMBEDDED);
					bfb = BaseFont.createFont(FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED);
				
				
				Font font8 = new Font(bf, 8);
				Font font8_B = new Font(bfb, 8, Font.BOLD);
				Font font8_U = new Font(bf, 8, Font.UNDERLINE);
				Font font8_B_U = new Font(bf, 8, Font.BOLD | Font.UNDERLINE);
		
				float marginLeft = 28f;
				float marginRight = 28f;
				float marginLeftChild = 48f;
				float marginTop = 10f;
				float marginBottom = 10f;
				float headerCellHeight = 15f;
	        	String[] headers = Constants.IT_DECLARATION_SUBMISSION_FORM;
	        	String paragraphData = Constants.IT_DECLARATION_FORM_PARAGRAPHDATA;
	        	Document document = new Document(PageSize.A4);
	//        	String home = System.getProperty("user.home");
	//        	file = new File(home+"/Downloads/" + fileSB.append(Constants.IT_DECLARATION_FORM).toString()+".pdf");
	        	file = File.createTempFile("IT_Declaration_" + user.getFullName().replaceAll(" ", "_") + "_" + sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear() + "_", ".pdf");
	    		file.setReadable(true);
	    		PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();
	//			BaseColor myBordColor = WebColors.getRGBColor("#000000");
				PdfPTable table = new PdfPTable(5);
				table.setWidthPercentage(90);
				table.setSpacingBefore(5);
				table.setSpacingAfter(5);
				table.setWidths(new int[] { 1, 1, 1, 1, 1 });
				
				Candidate can = candidateService.getCandidateByConsultantUserId(user.getId(), orgLoc.getId());
	        	if(can != null && can.getDateOfBirth() != null) {
	        		can.setDateofBirthString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(), can.getDateOfBirth()));
	        	}
		          
				table.addCell(getCell("INVESTMENT DECLARATION FORM FOR THE YEAR OF" + " " + (sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear().toString()), font8_U,Element.ALIGN_CENTER, 5, 18f));
				table.addCell(getCell("Employee Code", font8,Element.ALIGN_LEFT, 1, 18f));
				table.addCell(getCell("", font8,Element.ALIGN_LEFT, 4, 18f));
				table.addCell(getCell("Employee Name", font8,Element.ALIGN_LEFT, 1, 18f));
				table.addCell(getCell(user.getFullName(), font8,Element.ALIGN_LEFT, 4, 18f));
				table.addCell(getCell("Employee Designation ", font8,Element.ALIGN_LEFT, 1, 18f));
				table.addCell(getCell(can != null && can.getDesignation() != null ? can.getDesignation() : "", font8,Element.ALIGN_LEFT, 4, 18f));
				table.addCell(getCell("Employee PAN ", font8,Element.ALIGN_LEFT, 1, 18f));
				table.addCell(getCell(can != null && can.getPanNumber() != null ? can.getDesignation() : "", font8,Element.ALIGN_LEFT, 4, 18f));
				table.addCell(getCell("Employee DOB ", font8,Element.ALIGN_LEFT, 1, 18f));
				table.addCell(getCell(can != null && can.getDateOfBirth() != null ? can.getDateofBirthString() : "", font8,Element.ALIGN_LEFT, 2, 18f));
				table.addCell(getCell("Gender ", font8,Element.ALIGN_LEFT, 1, 18f));
				table.addCell(getCell(can != null && can.getGender() != null ? can.getGender() : "", font8,Element.ALIGN_LEFT, 1, 18f));
				document.add(table);
		          
		        document.add(addParagraph(paragraphData+" "+(sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear().toString())+" "+"starting from", font8_B, marginLeft, marginRight,marginTop,0));
	//	        document.add(addParagraph(sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear(), font8, marginLeft, marginRight,0,marginBottom));
				
		        int startYear = Integer.parseInt(sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear().split("-")[0]);
				String fromDate = "";
				if(orgLoc.getStartMonth() != null && !orgLoc.getStartMonth().isEmpty()) {
		        	fromDate = CalendarUtil.getMonthStartDateAndEndDateWithPrefixByMonth(orgLoc.getStartMonth(), startYear, true) + " of " + orgLoc.getStartMonth();
		        }
				fromDate = (!fromDate.isEmpty() ? fromDate + " " : "") + sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear().split("-")[0];
				
				int endYear = Integer.parseInt(sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear().split("-")[1]);
				String toDate = "";
				if(orgLoc.getEndMonth() != null && !orgLoc.getEndMonth().isEmpty()) {
					toDate = CalendarUtil.getMonthStartDateAndEndDateWithPrefixByMonth(orgLoc.getEndMonth(), endYear, false) + " of " + orgLoc.getEndMonth();
				}
				toDate = (!toDate.isEmpty() ? toDate + " " : "") + sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear().split("-")[1];
				document.add(addParagraph(fromDate + " to " + toDate, font8, marginLeft, marginRight, 0, marginBottom));
		        
		        
				PdfPTable table2 = new PdfPTable(headers.length);
				PdfPCell cell1 = new PdfPCell();
				cell1.setColspan(4);
				headerCellStyle(cell1);
				table2.setWidthPercentage(90);
				table2.setSpacingBefore(5);
				table2.setSpacingAfter(5);
				table2.setWidths(new int[] { 4, 2, 1, 1 });
				table2.addCell(cell1);
				for (int i = 0; i < headers.length; i++) {
					String header = headers[i];
					PdfPCell cell = new PdfPCell();
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setGrayFill(0.9f);
					cell.setFixedHeight(headerCellHeight);
					cell.setPhrase(new Phrase(header, font8_B_U));
					BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
					cell.setBackgroundColor(myColor);
					table2.addCell(cell);
				}
				table.completeRow();
				List<String[]> itDeclarationItemList = new ArrayList<>();
				JSONArray submissionItemsArray = new JSONArray(sourceleadTaxITDeclarationSubmissionDetails.getSubmissionItems());
				if (submissionItemsArray != null) {
					for (int i = 0; i < submissionItemsArray.length(); i++) {
						JSONObject jsonObject = submissionItemsArray.getJSONObject(i);
						String taxSectionName = jsonObject.getString("taxSectionName");
						JSONArray itemsArray = jsonObject.getJSONArray("items");
						String[] sectionNameArray = new String[1];
						if (itemsArray != null) {
							sectionNameArray[0] = taxSectionName;
							itDeclarationItemList.add(sectionNameArray);
							for (int k = 0; k < itemsArray.length(); k++) {
								JSONObject itemObject = itemsArray.getJSONObject(k);
								itDeclarationItemList
										.add(prepareItemListToArray(itemObject, headers, orgLoc, loginUser, user));
							}
						}
					}
					for (String[] itemArray : itDeclarationItemList) {
						for (int i = 0; i < itemArray.length; i++) {
							String datum = itemArray[i];
							PdfPCell cell = new PdfPCell();
							if (itemArray.length == 1) {
								cell.setColspan(4);
								cell.setVerticalAlignment(Element.ALIGN_CENTER);
								cell.setPhrase(new Phrase(datum, font8_B_U));
								cell.setFixedHeight(headerCellHeight);
							} else {
								cell.setPhrase(new Phrase(datum, font8));
							}
							table2.addCell(cell);
						}
						table2.completeRow();
					}
					document.add(table2);
					document.add(addParagraph("Income from Previous Employment:-", font8_B_U, marginLeft,0,(marginTop + marginTop),marginBottom));
		          
		          // Below commented code is for preparing dynamic data
		          
		        /*  com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
			  
		            StringBuilder st1=new StringBuilder();
		            StringBuilder st2=new StringBuilder();
		            StringBuilder st3=new StringBuilder();
		            StringBuilder st4=new StringBuilder();
		            
			        orderedList.add(new ListItem(st1.append("Income after exemptions :").append(   dynamic        )));
			        orderedList.add(new ListItem(st2.append("Provident found :").append()));
		            orderedList.add(new ListItem(st3.append("Professional tax:").append()));	
		            orderedList.add(new ListItem(st4.append("tax Deducted at source:").append()));  
		            document.add(orderedList);*/
					
					Chunk space = new Chunk(new VerticalPositionMark());
					
					Paragraph p1 = addParagraph("1."+"   "+"Income after exemptions", font8, marginLeftChild,(marginRight + marginLeftChild),0,marginBottom);
					Chunk c1 = new Chunk("__________________________________________",font8);
					p1.add(space);
					p1.add(c1);
					document.add(p1);
					
					Paragraph p2 = addParagraph("2."+"   "+"Provident found(PF)", font8, marginLeftChild,(marginRight + marginLeftChild),0,marginBottom);
					Chunk c2 = new Chunk("__________________________________________",font8);
					p2.add(space);
					p2.add(c2);
					document.add(p2);
					
					Paragraph p3 = addParagraph("3."+"   "+"Professional tax(PT)", font8, marginLeftChild,(marginRight + marginLeftChild),0,marginBottom);
					Chunk c3 = new Chunk("__________________________________________",font8);
					p3.add(space);
					p3.add(c3);
					document.add(p3);
					
					Paragraph p4 = addParagraph("4."+"   "+"Tax Deducted at source(TDS)", font8, marginLeftChild,(marginRight + marginLeftChild),0,marginBottom);
					Chunk c4 = new Chunk("__________________________________________",font8);
					p4.add(space);
					p4.add(c4);
					document.add(p4);
	
					document.add(addParagraph("Income from Other Sources:-", font8_B_U, marginLeft,0,(marginTop + marginTop),marginBottom));
	
					document.add(addParagraph("1.", font8, marginLeftChild,0,0,marginBottom));
					document.add(addParagraph("2.", font8, marginLeftChild,0,0,marginBottom));
	
					document.add(addParagraph("Declaration:-", font8_B_U, marginLeft,0,(marginTop + marginTop),marginBottom));
					document.add(addParagraph("I ........ here by declare that the information that given above is correct and true in all respect. I also undertake to indemnify to company for any loss/liability may arrise in the event of the above information being incorrect.", font8, marginLeft,0,0,marginBottom));
	
					document.add(addParagraph("Date : " + commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(), new Date()), font8, marginLeft,0,(marginTop + marginTop),marginBottom));
					
					Paragraph p5 = addParagraph("Place : " + (orgLoc.getCity() != null ? orgLoc.getCity() : ""), font8, marginLeft,marginRight,0,marginBottom);
					Chunk c5 = new Chunk("Signature of the employee",font8);
					p5.add(space);
					p5.add(c5);
					document.add(p5);
					
					document.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	
	private Paragraph addParagraph(String text, Font font, float leftMargin, float rightMargin, float topMargin, float bottomMargin) {
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setIndentationLeft(leftMargin);
		paragraph.setIndentationRight(rightMargin);
		paragraph.setSpacingBefore(topMargin);
		paragraph.setSpacingAfter(bottomMargin);
		return paragraph;
	}
	
	private PdfPCell getCell(String value, Font font, int alignment, int colSpan, float cellheight) {
		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		Paragraph p = new Paragraph(value, font);
		p.setAlignment(alignment);
		cell.addElement(p);
		cell.setColspan(colSpan);
		cell.setMinimumHeight(cellheight);
		return cell;

	}

	private static void headerCellStyle(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		cell.setBackgroundColor(new BaseColor(0, 121, 182));
		cell.setBorder(0);
		cell.setBorderWidthBottom(2f);
	}
	
	private String[] prepareItemListToArray(JSONObject itemObject, String[] headers,  OrgLocations orgLoc, User loginUser,User user) throws Exception {
		String itemArray[] = new String[headers.length];
		itemArray[0] = itemObject.getString("name");
		itemArray[1] = itemObject.getString("description");
		itemArray[2] = itemObject.getDouble("maxLimit") + "";
		itemArray[3] = itemObject.getDouble("amount") + "";
		return itemArray;
	}

	@Override
	public File exportItDeclarationSubmissionExcel(SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmissionDetails, User loginUser,
			long orgLocationId, User user) throws Exception {
		File file = null;
		try {
			 OrgLocations orgLoc = orgLocationsDao.getOrgLocations(orgLocationId);
			 Candidate can = candidateService.getCandidateByConsultantUserId(user.getId(), orgLoc.getId());
			if(orgLoc != null) {
				Workbook createExcelworkbook = new XSSFWorkbook();
				Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrgLocationName() + "_" + "ItDeclarationForm");
				Row writeRow = null;
				
				//for creating header
				createExcelsheet = getItDeclarationSubmissionExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook, sourceleadTaxITDeclarationSubmissionDetails, orgLoc, user,can);
				
				
				//for writing data in body
				int row = 11;
				JSONArray submissionItemsArray = new JSONArray(sourceleadTaxITDeclarationSubmissionDetails.getSubmissionItems());
				if (submissionItemsArray != null) {
					for (int i = 0; i < submissionItemsArray.length(); i++) {
						JSONObject jsonObject = submissionItemsArray.getJSONObject(i);
						String taxSectionName = jsonObject.getString("taxSectionName");
						JSONArray itemsArray = jsonObject.getJSONArray("items");
		
						if (itemsArray != null) {
							writeRow = createExcelsheet.createRow(row++);
							Cell cell = writeRow.createCell(0);
							cell.setCellValue(taxSectionName);
							CellStyle cellStyle = createExcelworkbook.createCellStyle();
							org.apache.poi.ss.usermodel.Font font = createExcelworkbook.createFont();
							cellStyle.setFont(font);
							font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
							cell.setCellStyle(cellStyle);
							for (int k = 0; k < itemsArray.length(); k++) {
								JSONObject itemObject = itemsArray.getJSONObject(k);
								writeRow = createExcelsheet.createRow(row++);
								createExcelsheet = createXlsSheet(createExcelsheet, writeRow, itemObject, user);
							}
							writeRow.createCell(0).setCellValue(" ");
						}
					}
					
	
					//for adding footer
					int lastRow = createExcelsheet.getLastRowNum() +1 ;
					createExcelsheet = getItDeclarationSubmissionExcelSheetWithFooter(createExcelsheet, writeRow, createExcelworkbook, sourceleadTaxITDeclarationSubmissionDetails, orgLoc, user,lastRow);
					
					String home = System.getProperty("user.home");
					StringBuilder fileSB = null;
					file = File.createTempFile("ItdeclarationForm ", ".xls");
					// file = new File(home+"/Downloads/" + fileSB.append(Constants.IT_DECLARATION_FORM).toString()+".xls");
					file.setReadable(true);
					FileOutputStream fos = new FileOutputStream(file);
					createExcelworkbook.write(fos);
					fos.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, JSONObject itemObject, User user)
			throws JSONException {
		writeRow.createCell(0).setCellValue(itemObject.getString("name"));
		writeRow.createCell(1).setCellValue(itemObject.getString("description"));
		writeRow.createCell(2).setCellValue(itemObject.getDouble("maxLimit"));
		writeRow.createCell(3).setCellValue(itemObject.getDouble("amount"));
		return createExcelsheet;
	}

	//footers
	private Sheet getItDeclarationSubmissionExcelSheetWithFooter(Sheet createExcelsheet, Row writeRow,
			Workbook createExcelworkbook,
			SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmissionDetails, OrgLocations orgLoc,
			User user, int rowNum) {
		String[] headers = null;
		headers = Constants.IT_DECRALARATION_FOOTER;
		
		CellStyle style4 = createExcelworkbook.createCellStyle();
		style4.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style4.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style4.setAlignment(CellStyle.ALIGN_JUSTIFY);
	    style4.setVerticalAlignment(CellStyle.VERTICAL_JUSTIFY);
		org.apache.poi.ss.usermodel.Font font4 = createExcelworkbook.createFont();
		font4.setColor(IndexedColors.BLACK.getIndex());
		font4.setItalic(true);
		style4.setFont(font4);
		
		CellStyle style45 = createExcelworkbook.createCellStyle();
		style45.setBorderLeft(CellStyle.BORDER_THIN);
		style45.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		    
		
		CellStyle style5 = createExcelworkbook.createCellStyle();
		org.apache.poi.ss.usermodel.Font font5 = createExcelworkbook.createFont();
		font5.setColor(IndexedColors.BLACK.getIndex());
		font5.setUnderline(font5.U_SINGLE);
		font5.setBold(true);
		style5.setFont(font5);
		
		CellStyle style6 = createExcelworkbook.createCellStyle();
		org.apache.poi.ss.usermodel.Font font6 = createExcelworkbook.createFont();
		font6.setColor(IndexedColors.BLACK.getIndex());
		font6.setBold(true);
		style6.setFont(font6);
		
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 4));
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 0, 4));
		writeRow = createExcelsheet.createRow(rowNum + 1);
		createExcelsheet.setColumnWidth(0, 10000);
		Cell cell14 = writeRow.createCell(0);
		cell14.setCellValue(headers[0]);
		cell14.setCellStyle(style5);
		
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 3, rowNum + 5, 0, 4));
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 6, rowNum + 6, 0, 4));
		writeRow = createExcelsheet.createRow(rowNum + 3);
		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("1. " +headers[1]);
		cell5.setCellStyle(style4);
		Cell cell50 = writeRow.createCell(5);
		cell50.setCellStyle(style45);
		writeRow = createExcelsheet.createRow(rowNum + 4);
		Cell cell56 = writeRow.createCell(5);
		cell56.setCellStyle(style45);
		writeRow = createExcelsheet.createRow(rowNum + 5);
		Cell cell57 = writeRow.createCell(5);
		cell57.setCellStyle(style45);
		
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 7, rowNum + 8, 0, 4));
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 9, rowNum + 9, 0, 4));
		writeRow = createExcelsheet.createRow(rowNum + 7);
		createExcelsheet.setColumnWidth(0, 500);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell17 = writeRow.createCell(0);
		cell17.setCellValue("2. " +headers[2]);
		cell17.setCellStyle(style4);
		Cell cell55 = writeRow.createCell(5);
		cell55.setCellStyle(style45);
		writeRow = createExcelsheet.createRow(rowNum + 8);
		Cell cell58 = writeRow.createCell(5);
		cell58.setCellStyle(style45);
		
		
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum +10 , rowNum + 10, 0, 4));
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 11, rowNum + 11, 0, 4));
		writeRow = createExcelsheet.createRow(rowNum + 10);
		createExcelsheet.setColumnWidth(0, 500);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell19 = writeRow.createCell(0);
		cell19.setCellValue("3. " +headers[3]);
		cell19.setCellStyle(style4);
		Cell cell54 = writeRow.createCell(5);
		cell54.setCellStyle(style45);
		
		
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 12, rowNum + 12, 0, 4));
		writeRow = createExcelsheet.createRow(rowNum + 12);
		createExcelsheet.setColumnWidth(0, 10000);
		Cell cell21 = writeRow.createCell(0);
		cell21.setCellValue(headers[4]);
		cell21.setCellStyle(style6);
		
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 13, rowNum + 13, 0, 4));
		writeRow = createExcelsheet.createRow(rowNum + 13);
		createExcelsheet.setColumnWidth(0, 10000);
		Cell cell22 = writeRow.createCell(0);
		cell22.setCellValue(headers[5] +" -  " + commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(), new Date()) );
		cell22.setCellStyle(style6);
		
		
		createExcelsheet.addMergedRegion(new CellRangeAddress(rowNum + 14, rowNum + 14, 0, 1));
		writeRow = createExcelsheet.createRow(rowNum + 14);
		createExcelsheet.setColumnWidth(0, 10000);
		Cell cell23 = writeRow.createCell(0);
		cell23.setCellValue(headers[6] +" -  " +(orgLoc.getCity() != null ? orgLoc.getCity() : ""));
		cell23.setCellStyle(style6);
		Cell cell26 = writeRow.createCell(2);
		cell26.setCellValue(headers[7]);
		cell26.setCellStyle(style6);
	
				return createExcelsheet;
		
	}
	
	//headers
	private Sheet getItDeclarationSubmissionExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow,
			Workbook createExcelworkbook,
			SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmissionDetails, OrgLocations orgLoc,
			User user, Candidate can) {
		
			CellStyle style = createExcelworkbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			org.apache.poi.ss.usermodel.Font font = createExcelworkbook.createFont();
			font.setColor(IndexedColors.BLACK.getIndex());
			style.setFont(font);
			
			CellStyle style1 = createExcelworkbook.createCellStyle();
		    style1.setAlignment(CellStyle.ALIGN_CENTER);
		    style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			org.apache.poi.ss.usermodel.Font font1 = createExcelworkbook.createFont();
			font1.setColor(IndexedColors.BLACK.getIndex());
			font1.setFontHeightInPoints((short) 18);
			font1.setBold(true);
			style1.setFont(font1);
		
		    CellStyle style2 = createExcelworkbook.createCellStyle();
		    style2.setAlignment(CellStyle.ALIGN_CENTER);
		    style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			org.apache.poi.ss.usermodel.Font font2 = createExcelworkbook.createFont();
			font2.setColor(IndexedColors.BLACK.getIndex());
		    font2.setFontHeightInPoints((short) 9);
		    font2.setItalic(true);
			style2.setFont(font2);
				
			//to find start date and end date of the month
			    int startYear = Integer.parseInt(sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear().split("-")[0]);
				String startDay = "";
				if(orgLoc.getStartMonth() != null && !orgLoc.getStartMonth().isEmpty()) {
					String 	fromDate = CalendarUtil.getMonthStartDateAndEndDateWithPrefixByMonth(orgLoc.getStartMonth(), startYear, true);
		        	startDay = fromDate.split(" ")[0].substring(0, fromDate.toString().split(" ")[0].length() - 2);	
		        }else startDay ="__";
				
				int endYear = Integer.parseInt(sourceleadTaxITDeclarationSubmissionDetails.getFinancialYear().split("-")[1]);
				String endDay ="";
				if(orgLoc.getEndMonth() != null && !orgLoc.getEndMonth().isEmpty()) {
					String	toDate = CalendarUtil.getMonthStartDateAndEndDateWithPrefixByMonth(orgLoc.getEndMonth(), endYear, false);
					 endDay = toDate.toString().split(" ")[0].substring(0, toDate.toString().split(" ")[0].length() - 2);
				}else endDay ="__";
				
											
			writeRow = createExcelsheet.createRow(0);
			Cell cell1 = writeRow.createCell(0);
			cell1.setCellValue(Constants.IT_DECRALARATION_HEADER1 +startYear+"-"+endYear);
			cell1.setCellStyle(style1);
			
			
			createExcelsheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));			
			String Header2 = Constants.IT_DECRALARATION_HEADER2 +startDay+"/"+ (orgLoc.getStartMonth() != null ? orgLoc.getStartMonth().toString() : "__")+"/"+startYear+" to " +endDay +"/"+(orgLoc.getEndMonth() != null ? orgLoc.getEndMonth().toString() : "__")+"/"+endYear +" for Income Tax purpose)";
			writeRow = createExcelsheet.createRow(2);
			createExcelsheet.setColumnWidth(0, 30000);
			Cell cell2 = writeRow.createCell(0);
			cell2.setCellValue(Header2);
			cell2.setCellStyle(style2);
			createExcelsheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
			
			writeRow = createExcelsheet.createRow(3);
			createExcelsheet.setColumnWidth(0, 10000);
			createExcelsheet.setColumnWidth(1, 10000);
			Cell cell3 = writeRow.createCell(0);
			cell3.setCellValue("Employee Code :");
			cell3.setCellStyle(style);
			Cell cell4 = writeRow.createCell(1);
			cell4.setCellValue(user.getEmploymentType() != null ? user.getEmploymentType().toString() :"---");
			cell4.setCellStyle(style);
	
			writeRow = createExcelsheet.createRow(4);
			createExcelsheet.setColumnWidth(0, 10000);
			createExcelsheet.setColumnWidth(1, 10000);
			Cell cell5 = writeRow.createCell(0);
			cell5.setCellValue("Employee Name :");
			cell5.setCellStyle(style);
			Cell cell6 = writeRow.createCell(1);
			cell6.setCellValue(user.getFullName() != null ? user.getFullName().toString() : "---");
			cell6.setCellStyle(style);
	
			writeRow = createExcelsheet.createRow(5);
			createExcelsheet.setColumnWidth(0, 10000);
			createExcelsheet.setColumnWidth(1, 10000);
			Cell cell7 = writeRow.createCell(0);
			cell7.setCellValue("Employee Designation : ");
			cell7.setCellStyle(style);
			Cell cell8 = writeRow.createCell(1);
			cell8.setCellValue(can.getDesignation() != null ? can.getDesignation().toString() : "---");
			cell8.setCellStyle(style);
	
			writeRow = createExcelsheet.createRow(6);
			createExcelsheet.setColumnWidth(0, 10000);
			createExcelsheet.setColumnWidth(1, 10000);
			Cell cell9 = writeRow.createCell(0);
			cell9.setCellValue("Employee PAN : ");
			cell9.setCellStyle(style);
			Cell cell10 = writeRow.createCell(1);
			cell10.setCellValue(can.getPanNumber() != null ? can.getPanNumber().toString() : "---" );
			cell10.setCellStyle(style);
	
			writeRow = createExcelsheet.createRow(7);
			createExcelsheet.setColumnWidth(0, 10000);
			createExcelsheet.setColumnWidth(1, 10000);
			Cell cell11 = writeRow.createCell(0);
			cell11.setCellValue("Employee DOB : ");
			cell11.setCellStyle(style);
			Cell cell12 = writeRow.createCell(1);	
			if(can.getDateOfBirth() != null) {
				String a = can.getDateOfBirth().toString().split("\\s+")[0];			
				cell12.setCellValue( commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(), commonUtil.stringToDate(a)));
			}else {
				cell12.setCellValue("---");
			}
			
			cell12.setCellStyle(style);
	
			writeRow = createExcelsheet.createRow(8);
			createExcelsheet.setColumnWidth(0, 10000);
			createExcelsheet.setColumnWidth(1, 10000);
			Cell cell13 = writeRow.createCell(0);
			cell13.setCellValue("Gender : ");
			cell13.setCellStyle(style);
			Cell cell14 = writeRow.createCell(1);
			cell14.setCellValue(user.getGender() != null && !user.getGender().isEmpty() ? user.getGender() : "---");
			cell14.setCellStyle(style);
			
			String[] headers = null;
			headers = Constants.IT_DECLARATION_SUBMISSION_FORM;
			createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 10);
			
		   return createExcelsheet;
	}
	
	public List<SourceleadTaxSubmissionMasterDTO> getTaxSubmissionMaster(Long userId, String dateFormat,
			String financialYear, Paginator paginator) {
		List<SourceleadTaxSubmissionMasterDTO> taxSubmissionMasterDTOList = new LinkedList<>();
		try {
			List<SourceleadTaxSubmissionMaster> taxSubmissionMasterList = taxDao.getTaxSubmissionMasterList(userId,
					financialYear, paginator);
			if (taxSubmissionMasterList != null && !taxSubmissionMasterList.isEmpty()) {
				for (SourceleadTaxSubmissionMaster taxSubmissionMaster : taxSubmissionMasterList) {
					SourceleadTaxSubmissionMasterDTO taxSubmissionMasterDTO = new SourceleadTaxSubmissionMasterDTO();
					taxSubmissionMasterDTO.setId(taxSubmissionMaster.getId());
					taxSubmissionMasterDTO.setFinancialYear(taxSubmissionMaster.getFinancialYear());
					taxSubmissionMasterDTO.setUserId(taxSubmissionMaster.getUser().getId());
					taxSubmissionMasterDTO.setTaxFormUUID(taxSubmissionMaster.getSourceleadTaxITDeclarationSubmission().getSourceleadTaxForm().getUuid());
					taxSubmissionMasterDTO.setFormUUID(taxSubmissionMaster.getSourceleadTaxITDeclarationSubmission().getUuid());
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
					if (taxSubmissionMaster.getSourceleadTaxITDeclarationSubmission() != null) {
						taxSubmissionMasterDTO.setItDeclarationSubmissionId(
								taxSubmissionMaster.getSourceleadTaxITDeclarationSubmission().getId());
						taxSubmissionMasterDTO.setFillingDate(sdf.format(
								taxSubmissionMaster.getSourceleadTaxITDeclarationSubmission().getCreatedDate()));
					}
					taxSubmissionMasterDTO
							.setStatus(taxSubmissionMaster.getSourceleadTaxITDeclarationSubmission().getStatus());
					taxSubmissionMasterDTOList.add(taxSubmissionMasterDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taxSubmissionMasterDTOList;
	}

	@Override
	public List<Tds> getTDSListByFinancialYear(String financialYear) {
		return taxDao.getTDSListByFinancialYear(financialYear);
	}

	@Override
	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmissionByUserIdOrgLocationAndFinancialYear(Long userId, Long orgLocationId, String financialYear) {
		
		return taxDao.getITDeclarationSubmissionByUserIdOrgLocationAndFinancialYear(userId, orgLocationId, financialYear);
	}
}

