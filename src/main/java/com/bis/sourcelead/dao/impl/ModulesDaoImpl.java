package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ModulesDao;
import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;

@Repository
public class ModulesDaoImpl implements ModulesDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(ModulesDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getModulesIdByModuleName(String modules) throws Exception {
		

		logger.debug("In ProjectsDaoImpl class getApprovarAssignedProjects method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String[] registredModules = modules.split(",");
		String subQuery = null;
		
		if(registredModules.length == 2){
			subQuery = "(modules.moduleName = 'RECRUITMENT' OR modules.moduleName = 'TIMESHEET MANAGEMENT' OR modules.moduleName = 'SOURCELEAD_DEFAULT_MODULE')";
			
		}else if(registredModules.length == 1){
			
			if(registredModules[0].equalsIgnoreCase("TimeSheet")){
				subQuery = "(modules.moduleName = 'TIMESHEET MANAGEMENT' OR modules.moduleName = 'SOURCELEAD_DEFAULT_MODULE') ";
			}else{
				subQuery = "(modules.moduleName = 'RECRUITMENT' OR modules.moduleName = 'SOURCELEAD_DEFAULT_MODULE') ";
			}
			
		}
		
		String query = "select modules.id from Modules modules where "+subQuery+" ";
		List<Long> modulesIdList =  session.createQuery(query.toString())
									.list();
		
		return modulesIdList;

	
	}

	@Override
	@Transactional
	public List<Modules> getSourceLeadModules() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from Modules modules where modules.parentModule.id IS NULL and modules.id <> '5' ";
	    List<Modules> modulesList = session.createQuery(query.toString()).list();
      
	    return modulesList;
	}

	@Override
	@Transactional
	public List<Modules> getRequirementSubModules() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from Modules modules where modules.parentModule.id = '1' ";
	    List<Modules> modulesList = session.createQuery(query.toString()).list();
      
	    return modulesList;
	}

	@Override
	@Transactional
	public List<Long> getModulesIds() throws Exception {
		
		logger.debug("In ModulesDaoImpl class getOrgRegisteredModules method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select modules.id from Modules modules";
		List<Long> modulesIdList =  session.createQuery(query.toString())
									.list();
		
		return modulesIdList;

	
	}

	@Override
	@Transactional
	public List<Modules> getRequirementSubModulesByParentId(Long moduleId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from Modules modules where modules.parentModule.id = :moduleId ";
	    List<Modules> modulesList = session.createQuery(query.toString()).setLong("moduleId", moduleId).list();
      
	    return modulesList;
	}

	@Override
	@Transactional
	public List<Long> getOrgRegisteredModulesByOrgLocationId(OrgLocations orgLocations) throws Exception
	{
		logger.debug("In ModulesDaoImpl class getOrgRegisteredModulesByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		List<Long> modulesList = new ArrayList<Long>();
		for (String s : orgLocations.getModulesId().split(",")){
			modulesList.add(Long.parseLong(s));
		 }	
			 
		String query = "select modules.id from Modules modules where modules.id IN (:modulesList) ";
		List<Long> modulesIdList =  session.createQuery(query.toString()).setParameterList("modulesList", modulesList)
									.list();
		
		return modulesIdList;

	}

}
