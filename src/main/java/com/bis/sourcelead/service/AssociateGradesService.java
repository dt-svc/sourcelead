package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.AssociateGradesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateGrades;

public interface AssociateGradesService {

	AssociateGrades saveAssociateGrades(AssociateGrades associateGrades) throws Exception;
	
	AssociateGrades getAssociateGradesByUUID(String uuid) throws Exception;
	
	List<AssociateGrades> getAssociateGradesListByOrgLocationId(Long orgLocationId) throws Exception;
	
	ResponseList<AssociateGradesDTO> getAssociateGradesDTOListByOrgLocationId(AssociateGradesDTO associateGradesDTO,Long orgLocationId) throws Exception;
	
	AssociateGrades getAssociateGradesByGradeNameAndOrgLocationId(String gradeName,Long orgLocationId) throws Exception;

	AssociateGrades getAssociateGradeById(Long id);
	
	List<String> getGradesListByOrgLocationId(Long orgLocationId) throws Exception;
	
	boolean isGradeExistsByGradeNameAndOrgLocationId(String gradeName,Long orgLocationId) throws Exception;
}
