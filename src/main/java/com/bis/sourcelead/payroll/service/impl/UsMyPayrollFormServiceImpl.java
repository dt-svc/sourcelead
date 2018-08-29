package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AccountDeductionsDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UsMyPayrollFormsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AccountDeductionsDao;
import com.bis.sourcelead.payroll.dao.UsMyPayrollFormDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.PayrollBalance;
import com.bis.sourcelead.payroll.entity.PayrollHelper;
import com.bis.sourcelead.payroll.entity.UsMyPayrollForms;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.service.AccountDeductionsService;
import com.bis.sourcelead.payroll.service.BisUserMappingService;
import com.bis.sourcelead.payroll.service.InvoiceDataService;
import com.bis.sourcelead.payroll.service.UsMyPayrollFormService;
import com.bis.sourcelead.payroll.service.UserPayrollService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class UsMyPayrollFormServiceImpl implements UsMyPayrollFormService{

	private static final Logger logger = LoggerFactory.getLogger(UsMyPayrollFormServiceImpl.class);
	
	@Autowired
	private UsMyPayrollFormDao usMyPayrollFormDao;
	
	@Autowired
	CandidateService candidateService;

	@Override
	public UsMyPayrollForms saveUsMyPayrollForm(UsMyPayrollFormsDTO usMyPayrollFormsDTO,OrgLocations orgLocation,User user)
	{
		UsMyPayrollForms myPayrollForm = null;
		try {
			myPayrollForm = new UsMyPayrollForms();
			myPayrollForm.setFormName(usMyPayrollFormsDTO.getFormName());
			myPayrollForm.setOrgLocationId(orgLocation);
			Candidate can = candidateService.getCandidateByCandidateId(Long.parseLong(usMyPayrollFormsDTO.getCandidate()));
			myPayrollForm.setCandidate(can);
			myPayrollForm.setStatus(Constants.RECORD_ACTIVE);
			myPayrollForm.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			myPayrollForm.setCreatedDate(new Date());
			myPayrollForm.setUpdatedDate(new Date());
			myPayrollForm.setCreatedBy(user.getUserCode());
			myPayrollForm.setUpdatedBy(user.getUserCode());
			for(FileInfo file : usMyPayrollFormsDTO.getFileInfo()) {
				myPayrollForm.setFileContentType(file.getContentType());
				myPayrollForm.setFileUrl(file.getFileURL());
				myPayrollForm.setFileName(file.getFileName());
				FileUploadUtil.moveFile(usMyPayrollFormsDTO.getStaticPath(), file, "usMyPayrollForms");
			}
			myPayrollForm = usMyPayrollFormDao.saveUsMyPayrollForm(myPayrollForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myPayrollForm;
	}

	@Override
	public ResponseList<UsMyPayrollForms> getUsMyPayrollFormByLocationIdAndCandidateId(Long locationId, Long candidateId)
	{
		ResponseList<UsMyPayrollForms> res = new ResponseList<>();
		List<UsMyPayrollForms> list = usMyPayrollFormDao.getUsMyPayrollFormByLocationIdAndCandidateId(locationId,candidateId);
		if(list != null) {
			res.setData(list);
			res.setResponse(Result.SUCCESS.name());
			res.setResponseMessage(Result.SUCCESS.getValue());
		} else {
			res.setResponse(Result.FAILED.name());
			res.setResponseMessage(Result.FAILED.getValue());
		}
		return res;
	}

	@Override
	public UsMyPayrollForms getUsMyPayrollFormByUUID(String uuid)
	{
		return usMyPayrollFormDao.getUsMyPayrollFormByUUID(uuid);
	}
	
	@Override
	public Response<String> deleteUsMyPayrollFormByUUID(UsMyPayrollFormsDTO usMyPayrollFormsDTO)
	{
		Response<String> res = new Response<>();
		try {
			UsMyPayrollForms form = usMyPayrollFormDao.getUsMyPayrollFormByUUID(usMyPayrollFormsDTO.getUuid());
			form.setStatus(Constants.RECORD_INACTIVE);
			form.setUpdatedBy(usMyPayrollFormsDTO.getUpdatedBy());
			form.setUpdatedDate(new Date());
			UsMyPayrollForms updatedForm = usMyPayrollFormDao.saveUsMyPayrollForm(form);
			if(updatedForm != null) {
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				res.setResponse(Result.FAILED.name());
				res.setResponseMessage(Result.FAILED.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
