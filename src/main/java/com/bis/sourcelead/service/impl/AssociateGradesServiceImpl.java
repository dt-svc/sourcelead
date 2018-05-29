package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssociateGradesDao;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.service.AssociateGradesService;

@Service
public class AssociateGradesServiceImpl implements AssociateGradesService{
	
	@Autowired
	AssociateGradesDao associateGradesDao;

	@Override
	public AssociateGrades saveAssociateGrades(AssociateGrades associateGrades) throws Exception {
		return associateGradesDao.saveAssociateGrades(associateGrades);
	}

	@Override
	public AssociateGrades getAssociateGradesByUUID(String uuid) throws Exception {
		return associateGradesDao.getAssociateGradesByUUID(uuid);
	}

	@Override
	public List<AssociateGrades> getAssociateGradesListByOrgLocationId(Long orgLocationId) throws Exception {
		return associateGradesDao.getAssociateGradesListByOrgLocationId(orgLocationId);
	}

}
