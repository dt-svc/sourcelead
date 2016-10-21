package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ModulesDao;

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
									.setCacheable(true).list();
		
		return modulesIdList;

	
	}

}
