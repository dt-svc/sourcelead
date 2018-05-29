package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrglocationFormFieldsDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.OrglocationFormSubFields;

@Repository
public class OrglocationFormFieldsDaoImpl implements OrglocationFormFieldsDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrglocationFormFields saveOrglocationFormField(OrglocationFormFields orgLocFormFields) {
		if(orgLocFormFields != null) {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(orgLocFormFields);
			return orgLocFormFields;
		}else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrglocationFormFields getOrglocationFormFieldsById(Long id)throws Exception {
		if(id != null && id !=0) {
		Session session = this.sessionFactory.getCurrentSession();
		OrglocationFormFields orgField = session.get(OrglocationFormFields.class, id);
			return orgField;
		}else {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationId(Long sectionId,Long orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationFormFields orgForm left join fetch orgForm.sourceleadFormSections slSection left join fetch orgForm.orgLocations orgLoc  where slSection.id =:sectionId and orgLoc.id =:orgLocation and orgForm.status = 1";
		List<OrglocationFormFields> orglocaFormField = session.createQuery(query).setLong("sectionId", sectionId).setLong("orgLocation", orgLocation).list();
		return orglocaFormField;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationIdForCustomList(Long sectionId,Long orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationFormFields orgForm left join fetch orgForm.sourceleadFormSections slSection left join fetch orgForm.orgLocations orgLoc  where slSection.id =:sectionId and orgLoc.id =:orgLocation";
		List<OrglocationFormFields> orglocaFormField = session.createQuery(query).setLong("sectionId", sectionId).setLong("orgLocation", orgLocation).list();
		return orglocaFormField;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrglocationFormSubFields> getAllCustomFormSubFieldsBySectionIdandOrgLocationId(Long formFieldId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationFormSubFields orgSubForm left join fetch orgSubForm.formField formField where formField.id =:formFieldId ";
		List<OrglocationFormSubFields> orglocaFormSubField = session.createQuery(query).setLong("formFieldId", formFieldId).list();
		return orglocaFormSubField;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteCustomFieldById(Long customID,String status) {
		Long statusValue = null;
		if(status.equalsIgnoreCase("deactivate")) {
			 statusValue = 0L;
		}else {
			 statusValue = 1L;
		}
		
		String returnResult = "";
		Session session = this.sessionFactory.getCurrentSession();
		String query = "update OrglocationFormSubFields subFields set subFields.status ='"+statusValue+"' where subFields.formField in(:customID)";
		 Query result = session.createQuery(query).setLong("customID", customID);
		 int count = result.executeUpdate();
		 if(count > 0) {
			 String formFieldQuery = "update OrglocationFormFields formField set formField.status ='"+statusValue+"' where  formField.id =:customID";
			 Query resultformField = session.createQuery(formFieldQuery).setLong("customID", customID);
			  int formFieldResult = resultformField.executeUpdate();
			  if(formFieldResult > 0) {
				  returnResult =  "success"; 
			  }
		 }else {
			 String formFieldQuery = "update OrglocationFormFields formField set formField.status ='"+statusValue+"' where  formField.id =:customID";
			 Query resultformField = session.createQuery(formFieldQuery).setLong("customID", customID);
			 int formFieldResult = resultformField.executeUpdate();
			  if(formFieldResult > 0) {
				  returnResult ="success"; 
			  }else {
				  returnResult ="failure";  
			  }
		 }
		 return returnResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrglocationFormFields> getAllSectionByActionId(Long actionId, OrgLocations orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationFormFields orgForm left join fetch orgForm.sourceleadFormSections slSection left join fetch orgForm.orgLocations orgLoc left join fetch slSection.sourceleadActions action  where action.id =:actionId and orgLoc.id =:orgLocation and orgForm.status = '1'";
		List<OrglocationFormFields> result = session.createQuery(query).setLong("actionId", actionId).setLong("orgLocation", orgLocation.getId()).list();
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCurrentIndexBySourceleadActionIdAndOrders(Long actionId, List<Long> orders) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "Select Count(*) from OrglocationFormFields orgForm    where orgForm.sourceleadFormSections.sourceleadActions.id =:actionId and orgForm.sourceleadFormSections.order IN(:orders) and orgForm.status ='1'";
		List<Long> list = session.createQuery(query).setLong("actionId", actionId).setParameterList("orders", orders).list();
		Long result = 0l;
		if(list != null && !list.isEmpty()) {
			result = list.iterator().next();
		}
		return result;
	}


}
