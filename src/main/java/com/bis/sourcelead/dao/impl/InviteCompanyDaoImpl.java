package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InviteCompanyDao;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.entity.InviteCompany;
import com.bis.sourcelead.entity.ScheduleMaintenance;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class InviteCompanyDaoImpl implements InviteCompanyDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;

	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	private static final Logger logger = LoggerFactory.getLogger(InviteCompanyDaoImpl.class);

	@Override
	@Transactional
	public InviteCompany saveInviteCompany(InviteCompany inviteCompany) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (inviteCompany != null) {
				session.saveOrUpdate(inviteCompany);
			}
			logger.debug("Saving Department");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"InviteCompany"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return inviteCompany;
	}

	@Override
	@Transactional
	public void storeScheduleMaintenance(ScheduleMaintenance maintenance) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (maintenance != null) {
				session.saveOrUpdate(maintenance);
			}
			logger.debug("Saving Schedule Maintenance");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Schedule Maintenance"));
				try {
					throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
				} catch (SourceLeadConstraintViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	@Transactional
	public ScheduleMaintenance getScheduleStatus(Date currentDate){
		Session session = this.sessionFactory.getCurrentSession();
		ScheduleMaintenance scheduleMaintenance = null;   //Time difference for 5 days in seconds 432000
		scheduleMaintenance = (ScheduleMaintenance) session.createQuery("from ScheduleMaintenance sm "+
                "WHERE ((TIMESTAMPDIFF(SECOND, :currentDate, sm.startDate)) >= :seconds1 and (TIMESTAMPDIFF(SECOND, :currentDate, sm.startDate)) <= :seconds2)) or (TIMESTAMPDIFF(SECOND, :currentDate, sm.endDate)) >= :seconds1) " +
                "ORDER BY sm.createdDate desc").setParameter("currentDate", currentDate).setParameter("seconds1", 0).setParameter("seconds2", 172800).setFirstResult(0).setMaxResults(1).uniqueResult();
		return  scheduleMaintenance;
	}

	@Override
	@Transactional
	public ResponseList<ScheduleMaintenanceDTO> getScheduleMaintenanceList(ScheduleMaintenanceDTO scheduleMaintenanceDTO, String searchValue) throws ParseException {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		List<ScheduleMaintenance> scheduleMaintenances = null;
		ResponseList<ScheduleMaintenanceDTO> response = new ResponseList<>();
		
		StringBuilder query1 = new StringBuilder("");
		if(searchValue !=null && !"".equalsIgnoreCase(searchValue)) {
		 query1.append(" where (sm.scheduleMessage like '%" + searchValue + "%' )");
		}
		//usertList sorting order
		String sortingQry = "";
		if(StringUtils.isNotBlank(scheduleMaintenanceDTO.getSortColumn())) {
				sortingQry = " order by sm."+ scheduleMaintenanceDTO.getSortColumn() +" "+scheduleMaintenanceDTO.getSortOrder();
		}else {
			sortingQry = " order by sm.createdDate desc";
		}
		String listQuery = "from ScheduleMaintenance sm";
		scheduleMaintenances = session.createQuery(listQuery+query1+sortingQry).list();
		String listQueryCount = "select count(*) from ScheduleMaintenance sm";
		countlist = session.createQuery(listQueryCount+query1).setFirstResult(scheduleMaintenanceDTO.getStart()).setMaxResults(scheduleMaintenanceDTO.getLength()).list();
		int totalCount = 0;
		if (scheduleMaintenanceDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		List<ScheduleMaintenanceDTO> maintenanceDTOs = new ArrayList<ScheduleMaintenanceDTO>();
		for(ScheduleMaintenance source : scheduleMaintenances) {
			ScheduleMaintenanceDTO target = new ScheduleMaintenanceDTO();
			BeanUtils.copyProperties(source, target);
			DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			DateFormat dateFormatResult = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
			target.setEndDate(dateFormatResult.format(dateFormat.parse(source.getEndDate().toString())));
			target.setStartDate(dateFormatResult.format(dateFormat.parse(source.getStartDate().toString())));
			maintenanceDTOs.add(target);
		}
		response.setData(maintenanceDTOs);
		return response;
	}

	@Override
	@Transactional
	public void deleteScheduleMaintenance(String id) {
		logger.debug("deleteScheduleMaintenance");
		Session session = this.sessionFactory.getCurrentSession();
		Query qry = null;
		String query = "delete from ScheduleMaintenance sm where sm.id = :id";
    	qry = session.createQuery(query).setString("id", id);
    	int i = qry.executeUpdate();
	}
}
