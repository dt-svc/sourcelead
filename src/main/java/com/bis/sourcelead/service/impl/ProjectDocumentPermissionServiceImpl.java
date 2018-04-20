package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectDocumentPermissionDao;
import com.bis.sourcelead.dto.ProjectsDocumentFolderDTO;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocumentPermission;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.ProjectDocumentPermissionService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ProjectDocumentPermissionServiceImpl implements ProjectDocumentPermissionService{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ProjectDocumentPermissionDao projectDocumentPermissionDao;
	
	 
	@Override
	public ProjectDocumentPermission saveProjectDocumentPermission(ProjectDocumentPermission projectDocumentPermission) throws Exception {
		return projectDocumentPermissionDao.saveProjectDocumentPermission(projectDocumentPermission);
	}


	@Override
	public String saveProjectDocumentPermissionWithDTO(ProjectsDocumentFolderDTO projectsDocumentFolderDTO) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long documentId ;
		if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
			documentId  = Long.valueOf(projectsDocumentFolderDTO.getDocumentId());
		}else {
			documentId = null;
		}
		
		//readRoleId functionality
		List<Long> dbReadRoleIds = projectDocumentPermissionDao.projectDocumentPermissionRoleIdListByFolderIdAndAccessStatus(Long.valueOf(projectsDocumentFolderDTO.getFolderId()),documentId, Constants.DOCUMENT_READ_ACCESS);
		List<Long> freshReadRoleIds = new ArrayList<>();
		if(projectsDocumentFolderDTO.getReadRoleIds() != null) {
			String[] readRoleId  = projectsDocumentFolderDTO.getReadRoleIds();
			for (String rr:readRoleId){ 
				freshReadRoleIds.add(Long.valueOf(rr));
				if(!dbReadRoleIds.contains(Long.valueOf(rr))) {
					ProjectDocumentPermission pdp = null;
					if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
						 pdp = projectDocumentPermissionDao.getDocumentPermissionByDocumentId(Long.valueOf(projectsDocumentFolderDTO.getDocumentId()),Long.valueOf(rr));
						 if(pdp == null) {
							 pdp = new ProjectDocumentPermission();
						 }
					}else {
						 pdp = new ProjectDocumentPermission();
					}
					Roles role = new Roles();
					role.setId(Long.valueOf(rr));
					pdp.setUserRoleId(role);
					pdp.setAccess("0");
					ProjectDocumentFolder pdf = new ProjectDocumentFolder();
					pdf.setId(Long.valueOf(projectsDocumentFolderDTO.getFolderId()));
					pdp.setProjectDocumentFolderId(pdf);
					
					if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
						ProjectDocuments pd = new ProjectDocuments();
						pd.setId(Long.valueOf(projectsDocumentFolderDTO.getDocumentId()));
						pdp.setProjectDocumentId(pd);	
					}
					pdp.setCreatedBy(user);
					pdp.setCreatedDate(new Date());
					projectDocumentPermissionDao.saveProjectDocumentPermission(pdp);
				}
				
		    }
		}
		dbReadRoleIds.removeAll(freshReadRoleIds);
		if(dbReadRoleIds != null && !dbReadRoleIds.isEmpty()) {
				projectDocumentPermissionDao.deleteAllProjectDocumentPermissionByFoderIdAndRoleIdAndAccess(Long.valueOf(projectsDocumentFolderDTO.getFolderId()), dbReadRoleIds, Constants.DOCUMENT_READ_ACCESS);
		}
		
		//writeRoleId functionality
		List<Long> dbWriteRoleIds = projectDocumentPermissionDao.projectDocumentPermissionRoleIdListByFolderIdAndAccessStatus(Long.valueOf(projectsDocumentFolderDTO.getFolderId()),documentId, Constants.DOCUMENT_WRITE_ACCESS);
		List<Long> freshWriteRoleIds = new ArrayList<>();
		if(projectsDocumentFolderDTO.getWriteRoleIds() != null) {
					String[] writeRoleId  = projectsDocumentFolderDTO.getWriteRoleIds();
					for (String wr:writeRoleId){
						freshWriteRoleIds.add(Long.valueOf(wr));
						if(!dbWriteRoleIds.contains(Long.valueOf(wr))) {
							ProjectDocumentPermission pdp = null;
							if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
								 pdp = projectDocumentPermissionDao.getDocumentPermissionByDocumentId(Long.valueOf(projectsDocumentFolderDTO.getDocumentId()),Long.valueOf(wr));
								 if(pdp == null) {
									 pdp = new ProjectDocumentPermission();
								 }
							}else {
								 pdp = new ProjectDocumentPermission();
							}
							Roles role = new Roles();
							role.setId(Long.valueOf(wr));
							pdp.setUserRoleId(role);
							pdp.setAccess("1");
							ProjectDocumentFolder pdf = new ProjectDocumentFolder();
							pdf.setId(Long.valueOf(projectsDocumentFolderDTO.getFolderId()));
							pdp.setProjectDocumentFolderId(pdf);
							
							if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
								ProjectDocuments pd = new ProjectDocuments();
								pd.setId(Long.valueOf(projectsDocumentFolderDTO.getDocumentId()));
								pdp.setProjectDocumentId(pd);	
							}
							
							pdp.setCreatedBy(user);
							pdp.setCreatedDate(new Date());
							projectDocumentPermissionDao.saveProjectDocumentPermission(pdp);
						}
				    }
				}
		dbWriteRoleIds.removeAll(freshWriteRoleIds);
		if(dbWriteRoleIds != null && !dbWriteRoleIds.isEmpty()) {
				projectDocumentPermissionDao.deleteAllProjectDocumentPermissionByFoderIdAndRoleIdAndAccess(Long.valueOf(projectsDocumentFolderDTO.getFolderId()), dbWriteRoleIds, Constants.DOCUMENT_WRITE_ACCESS);
		}
		
		//readOtheMemberIds functionality
		List<Long> dbReadMemberIds = projectDocumentPermissionDao.projectDocumentPermissionMemberIdListByFolderIdAndAccessStatus(Long.valueOf(projectsDocumentFolderDTO.getFolderId()),documentId, Constants.DOCUMENT_READ_ACCESS);
		List<Long> freshReadMemberIds = new ArrayList<>();
		if(projectsDocumentFolderDTO.getReadOtherMemberIds() != null) {
			String[] otherMemberIds  = projectsDocumentFolderDTO.getReadOtherMemberIds();
			for (String rom:otherMemberIds){
				freshReadMemberIds.add(Long.valueOf(rom));
				if(!dbReadMemberIds.contains(Long.valueOf(rom))) {
					ProjectDocumentPermission pdp = null;
					if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
						 pdp = projectDocumentPermissionDao.getDocumentPermissionByDocumentId(Long.valueOf(projectsDocumentFolderDTO.getDocumentId()),Long.valueOf(rom));
						 if(pdp == null) {
							 pdp = new ProjectDocumentPermission();
						 }
					}else {
						 pdp = new ProjectDocumentPermission();
					}
					ProjectMembers pm = new ProjectMembers();
					pm.setId(Long.valueOf(rom));
					pdp.setProjectMemberId(pm);
					pdp.setAccess("0");
					ProjectDocumentFolder pdf = new ProjectDocumentFolder();
					pdf.setId(Long.valueOf(projectsDocumentFolderDTO.getFolderId()));
					pdp.setProjectDocumentFolderId(pdf);
					
					if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
						ProjectDocuments pd = new ProjectDocuments();
						pd.setId(Long.valueOf(projectsDocumentFolderDTO.getDocumentId()));
						pdp.setProjectDocumentId(pd);	
					}
					pdp.setCreatedBy(user);
					pdp.setCreatedDate(new Date());
					projectDocumentPermissionDao.saveProjectDocumentPermission(pdp);
				}
		    }
		}
		dbReadMemberIds.removeAll(freshReadMemberIds);
		if(dbReadMemberIds != null && !dbReadMemberIds.isEmpty()) {
				projectDocumentPermissionDao.deleteAllProjectDocumentPermissionByFoderIdAndMemberAndAccess(Long.valueOf(projectsDocumentFolderDTO.getFolderId()), dbReadMemberIds, Constants.DOCUMENT_READ_ACCESS);
		}	
		
		 //readOtheMemberIds functionality
		List<Long> dbWriteMemberIds = projectDocumentPermissionDao.projectDocumentPermissionMemberIdListByFolderIdAndAccessStatus(Long.valueOf(projectsDocumentFolderDTO.getFolderId()),documentId, Constants.DOCUMENT_WRITE_ACCESS);
		List<Long> freshWriteMemberIds = new ArrayList<>();
			if(projectsDocumentFolderDTO.getWriteOtherMemberIds() != null) {
				String[] writeMemberIds  = projectsDocumentFolderDTO.getWriteOtherMemberIds();
				for (String wm:writeMemberIds){
					freshWriteMemberIds.add(Long.valueOf(wm));
					if(!dbWriteMemberIds.contains(Long.valueOf(wm))) {
						ProjectDocumentPermission pdp = null;
						if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
							 pdp = projectDocumentPermissionDao.getDocumentPermissionByDocumentId(Long.valueOf(projectsDocumentFolderDTO.getDocumentId()),Long.valueOf(wm));
							 if(pdp == null) {
								 pdp = new ProjectDocumentPermission();
							 }
						}else {
							 pdp = new ProjectDocumentPermission();
						}
						ProjectMembers pm = new ProjectMembers();
						pm.setId(Long.valueOf(wm));
						pdp.setProjectMemberId(pm);
						pdp.setAccess("1");
						ProjectDocumentFolder pdf = new ProjectDocumentFolder();
						pdf.setId(Long.valueOf(projectsDocumentFolderDTO.getFolderId()));
						pdp.setProjectDocumentFolderId(pdf);
						
						if(projectsDocumentFolderDTO.getDocumentId() != null && !projectsDocumentFolderDTO.getDocumentId().equalsIgnoreCase("undefined")) {
							ProjectDocuments pd = new ProjectDocuments();
							pd.setId(Long.valueOf(projectsDocumentFolderDTO.getDocumentId()));
							pdp.setProjectDocumentId(pd);	
						}
						
						pdp.setCreatedBy(user);
						pdp.setCreatedDate(new Date());
						projectDocumentPermissionDao.saveProjectDocumentPermission(pdp);
					}
			    }
			}
			dbWriteMemberIds.removeAll(freshWriteMemberIds);
			if(dbWriteMemberIds != null && !dbWriteMemberIds.isEmpty()) {
					projectDocumentPermissionDao.deleteAllProjectDocumentPermissionByFoderIdAndMemberAndAccess(Long.valueOf(projectsDocumentFolderDTO.getFolderId()), dbWriteMemberIds, Constants.DOCUMENT_WRITE_ACCESS);
			}
			
		return null;
	}


	@Override
	public List<ProjectDocumentPermission> projectDocumentPermissionListByFolderIdAndAccessStatus(Long folderId,Long documentId,String accessStatus) {
		return projectDocumentPermissionDao.projectDocumentPermissionListByFolderIdAndAccessStatus(folderId,documentId,accessStatus);
	}


	@Override
	public List<Long> projectDocumentPermissionRoleIdListByFolderIdAndAccessStatus(Long folderId,Long documentId, String accessStatus) throws Exception {
		return projectDocumentPermissionDao.projectDocumentPermissionRoleIdListByFolderIdAndAccessStatus(folderId,documentId, accessStatus);
	}


	@Override
	public List<Long> projectDocumentPermissionMemberIdListByFolderIdAndAccessStatus(Long folderId,Long documentId, String accessStatus) throws Exception {
		return projectDocumentPermissionDao.projectDocumentPermissionRoleIdListByFolderIdAndAccessStatus(folderId,documentId, accessStatus);

	}
}
