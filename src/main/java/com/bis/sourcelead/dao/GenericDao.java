package com.bis.sourcelead.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.model.ProjectDocumentBO;

public interface GenericDao<T extends Serializable> {
	
	public void setEntityClass(Class<T> entityClass);
	
	public T findById(final long id);
	
	public List<T> findAll();
	
	public void save(T entity);
	
	public T update(final T entity);
	
	void delete(final T entity);
	
	void deleteById(final long id);

	public List<T> findByCriteria(String property, Long value);
	
	List<ProjectDocuments> getProjectDocumentsListByProjectIdId(Long projectId) throws Exception;
	
	ProjectDocuments getDocumentById(Long porjectDocumentId)throws Exception;
	
	ProjectDocuments updateProjectDocuments(ProjectDocuments projectDocuments) throws Exception;
	
	List<ProjectDocuments> getEachProjectDocumentsListByProjectIdIdAndFolderId(Long projectId,Long userRoleId,String userRoleType,Long folderId,Long candidateId) throws Exception;
	
	Boolean validateProjectDocumentFolderNameByProjectIdIdAndFolderName(Long projectId,String folderName) throws Exception;
	
	void delteEachDocumentById(Long porjectDocumentId)throws Exception;
	
	Boolean validateProjectDocumentNameByProjectIdIdAndDocumentName(Long projectId,String documentName) throws Exception;


}
