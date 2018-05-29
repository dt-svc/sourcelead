package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.AssociateGrades;

public interface AssociateGradesService {

	AssociateGrades saveAssociateGrades(AssociateGrades associateGrades) throws Exception;
	
	AssociateGrades getAssociateGradesByUUID(String uuid) throws Exception;
	
	List<AssociateGrades> getAssociateGradesListByOrgLocationId(Long orgLocationId) throws Exception;
}
