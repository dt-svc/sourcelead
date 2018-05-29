package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociateGrades;

public interface AssociateGradesDao {
	
	AssociateGrades saveAssociateGrades(AssociateGrades associateGrades) throws Exception;
	
	AssociateGrades getAssociateGradesByUUID(String uuid) throws Exception;
	
	List<AssociateGrades> getAssociateGradesListByOrgLocationId(Long orgLocationId) throws Exception;

}
