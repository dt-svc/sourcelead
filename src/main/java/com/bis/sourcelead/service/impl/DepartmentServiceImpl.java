package com.bis.sourcelead.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dto.DepartmentDTO;
import com.bis.sourcelead.dto.LineOfBusinessDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LineOfBusiness;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	DepartmentDao departmentDao;
	
	 @Autowired
	 private CommonUtil commonUtil;

	@Override
	public Department saveDepartment(Department department) throws Exception {
		return departmentDao.saveDepartment(department);
	}

	@Override
	public Department getDepartmentById(Long departmentId) throws Exception {
		return departmentDao.getDepartmentById(departmentId);
	}

	@Override
	public List<Department> getDepartmentByOrgLocationId(Long orgLocationId) throws Exception {
		return departmentDao.getDepartmentByOrgLocationId(orgLocationId);
	}

	@Override
	public String saveDepartmentDTO(DepartmentDTO  departmentDTO) throws Exception {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	
		Department department = new Department();
		
		if(StringUtils.isNotBlank(departmentDTO.getDepartmentName())){
			department.setDepartmentName(departmentDTO.getDepartmentName());
		}
		if(StringUtils.isNotBlank(departmentDTO.getDepartmentDesc())){
			department.setDescription(departmentDTO.getDepartmentDesc());
		}
		department.setOrgLocationId(loginOrgLocation);
		department.setStatus(Constants.RECORD_ACTIVE);
		department.setCreatedDate(commonUtil.currentDate());
		department.setCreatedBy(user.getUserCode());
		departmentDao.saveDepartment(department);
		// TODO Auto-generated method stub
		return "success";
	}

	@Override
	public Department validateDepartmentnameByDepartmentIdAndDepartmentName(Long departmentId, String departmentName) throws Exception {
		return departmentDao.validateDepartmentnameByDepartmentIdAndDepartmentName(departmentId, departmentName);
	}

	@Override
	public Boolean validateDepartmentnameByOrgLocationIdAndDepartmentName(Long orgLocationId, String departmentName) throws Exception {
		Boolean flag =departmentDao.validateDepartmentnameByOrgLocationIdAndDepartmentName(orgLocationId, departmentName);
		return flag;
	}

	@Override
	public List<Department> getDepartmentByOrgLocationIdAndStatus(Long orgLocationId, String status) throws Exception {
		return departmentDao.getDepartmentByOrgLocationIdAndStatus(orgLocationId, status);
	}

	@Override
	public List<String> getDepartmentList(Long orgLocationId) throws Exception {
		return departmentDao.getDepartmentList(orgLocationId);
	}

	@Override
	public Department getDepartmentByOrgLocationIdAndDepartmentName(Long orgLocationId, String departmentName)
			throws Exception {
		return departmentDao.getDepartmentByOrgLocationIdAndDepartmentName(orgLocationId, departmentName);
	}

	@Override
	public List<DepartmentDTO> getDepartmentsListByIds(List<Long> id) throws Exception {
		return departmentDao.getDepartmentsListByIds(id);
	}

	@Override
	public List<DepartmentDTO> getDepartmentsListByOrgLocationId(Long orgLocationId) throws Exception {
		return departmentDao.getDepartmentsListByOrgLocationId(orgLocationId);
	}

	@Override
	public String saveLineOfBusiness(LineOfBusinessDTO lineOfBusinessDTO) throws Exception {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	
	 	
		LineOfBusiness lineOfBusiness = new LineOfBusiness();
		
		if(lineOfBusinessDTO.getLobId() != null) {
			lineOfBusiness.setId(lineOfBusinessDTO.getLobId().longValue());
	 	}
		
		if(StringUtils.isNotBlank(lineOfBusinessDTO.getLobName())){
			lineOfBusiness.setLobName(lineOfBusinessDTO.getLobName());
		}
		
		if(StringUtils.isNotBlank(lineOfBusinessDTO.getDepartmentId())){
			Department department = new Department();
			department.setId(Long.valueOf(lineOfBusinessDTO.getDepartmentId()));
			lineOfBusiness.setDepartmentId(department);
		}
		
		if(StringUtils.isNotBlank(lineOfBusinessDTO.getDescription())){
			lineOfBusiness.setDescription(lineOfBusinessDTO.getDescription());
		}
		lineOfBusiness.setOrgLocationId(loginOrgLocation);
		lineOfBusiness.setStatus(Constants.RECORD_ACTIVE);
		lineOfBusiness.setCreatedDate(commonUtil.currentDate());
		lineOfBusiness.setCreatedBy(user.getUserCode());
		departmentDao.saveLineOfBusiness(lineOfBusiness);
		// TODO Auto-generated method stub
		return "success";
	}

	@Override
	public List<LineOfBusiness> getLineOfBusinessListByOrgLocationId(Long orgLocationId) throws Exception {
		return departmentDao.getLineOfBusinessListByOrgLocationId(orgLocationId);
	}

	@Override
	public LineOfBusinessDTO getLineOfBusinessDTOByLOBId(Long id) throws Exception {
		return departmentDao.getLineOfBusinessDTOByLOBId(id);
	}

	@Override
	public List<LineOfBusinessDTO> getLineOfBusinessDTOList(Long orgLocationId) throws Exception {
		return departmentDao.getLineOfBusinessDTOList(orgLocationId);
	}
	
}
