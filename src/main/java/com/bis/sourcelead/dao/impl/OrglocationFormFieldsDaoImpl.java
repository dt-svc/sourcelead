package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrglocationFormFieldsDao;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

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
		String query = "from OrglocationFormFields orgForm left join fetch orgForm.sourceleadFormSections slSection left join fetch orgForm.orgLocations orgLoc  where slSection.id =:sectionId and orgLoc.id =:orgLocation and orgForm.status = '1'";
		List<OrglocationFormFields> orglocaFormField = session.createQuery(query).setLong("sectionId", sectionId).setLong("orgLocation", orgLocation).setCacheable(true).list();
		return orglocaFormField;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String deleteCustomFieldById(Long customID) {
		String returnResult = "";
		Session session = this.sessionFactory.getCurrentSession();
		String query = "update OrglocationFormSubFields subFields set subFields.status ='0' where subFields.formField in(:customID)";
		 Query result = session.createQuery(query).setLong("customID", customID).setCacheable(true);
		 int count = result.executeUpdate();
		 if(count > 0) {
			 String formFieldQuery = "update OrglocationFormFields formField set formField.status ='0' where  formField.id =:customID";
			 Query resultformField = session.createQuery(formFieldQuery).setLong("customID", customID).setCacheable(true);
			  int formFieldResult = resultformField.executeUpdate();
			  if(formFieldResult > 0) {
				  returnResult =  "success"; 
			  }
		 }else {
			 String formFieldQuery = "update OrglocationFormFields formField set formField.status ='0' where  formField.id =:customID";
			 Query resultformField = session.createQuery(formFieldQuery).setLong("customID", customID).setCacheable(true);
			 int formFieldResult = resultformField.executeUpdate();
			  if(formFieldResult > 0) {
				  returnResult ="success"; 
			  }else {
				  returnResult ="failure";  
			  }
		 }
		 return returnResult;
	}


}
