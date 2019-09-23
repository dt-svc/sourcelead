package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.SourceleadTicketsDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTicketDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTickets;
import com.bis.sourcelead.entity.SourceleadTicketsAttachments;
import com.bis.sourcelead.entity.SourceleadTicketsComments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class SourceleadTicketsDaoImpl implements SourceleadTicketsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
	@Value("${jdbc.schema}")
	private String schema;
	private static final Logger logger = LoggerFactory.getLogger(SourceleadTicketsDaoImpl.class);


	@Override
	@Transactional
	public SourceleadTickets saveSourceleadTickets(SourceleadTickets sourceleadTickets) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (sourceleadTickets != null) {
				session.saveOrUpdate(sourceleadTickets);
			}
			logger.debug("Saving SourceleadTickets");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SourceleadTickets"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return sourceleadTickets;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<SourceleadTickets> getSourceleadTicketListByOrgLocationId(SourceleadTicketDTO sourceleadTicketDTO, Long userId,OrgLocations orgLocations,Date[] searchDates) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		List<SourceleadTickets> ticketList = null;
		Integer filterCount = 0;
        ResponseList<SourceleadTickets> response = new ResponseList<>();
        
        Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
 	   Matcher matcher = pattern.matcher(sourceleadTicketDTO.getSearchValue());
 	   java.sql.Date sqlDate = null;
 	    if(matcher.matches() == true) {
 					if(sourceleadTicketDTO.getSearchValue().contains("/")||(sourceleadTicketDTO.getSearchValue().contains("-"))) {
 						String replacedValue = sourceleadTicketDTO.getSearchValue().replaceAll("/", "-");
 							SimpleDateFormat format = new SimpleDateFormat(orgLocations.getDateFormat());
 					        java.util.Date utilDate = format.parse(replacedValue);
 					        sqlDate = new java.sql.Date(utilDate.getTime());
 					}
 	    }
 	    
        StringBuilder query1 = new StringBuilder("");
		if(sourceleadTicketDTO.getSearchValue() !=null && !"".equalsIgnoreCase(sourceleadTicketDTO.getSearchValue())) {
			query1.append("and ((st.userId.firstName like '" + sourceleadTicketDTO.getSearchValue() + "%' OR st.userId.lastName like '%" + sourceleadTicketDTO.getSearchValue() + "' )")
			   .append("OR (concat(st.userId.firstName,st.userId.lastName) like '%" + sourceleadTicketDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')" )
		       .append("OR st.ticketTitle like '%" + sourceleadTicketDTO.getSearchValue() + "%' ")
		       .append("OR st.ticketType like '%" + sourceleadTicketDTO.getSearchValue() + "%' ")
		       .append("OR st.ticketGeneratedId like '%" + sourceleadTicketDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%' ")
		       .append("OR st.tabId.tabName like '%" + sourceleadTicketDTO.getSearchValue() + "%' ")
		       .append("OR st.orgLocationId.orgLocationName like '%" + sourceleadTicketDTO.getSearchValue() + "%' ")
			   .append("OR st.ticketStatus like '%" + sourceleadTicketDTO.getSearchValue() + "%' )");
		       if(sqlDate != null) {
		    	   query1.append("OR st.createdDate like '%" + sqlDate + "%' ");
			   } 
		}
		
		//usertList sorting order
		String sortingQry = "";
		if(StringUtils.isNotBlank(sourceleadTicketDTO.getSortColumn())) {
				sortingQry = " order by st."+ sourceleadTicketDTO.getSortColumn() +" "+sourceleadTicketDTO.getSortOrder();
		}
		
		if(sourceleadTicketDTO.getLoginUserRole().equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN") || orgLocations.getId() == Constants.SOURCELEAD_ORG_LOCATION_ID) {
			//org list query
			List<Long> orgLocationList = new ArrayList<>();
			String subQuery = "";
			if(sourceleadTicketDTO.getSelectCompanyIds() != null && !sourceleadTicketDTO.getSelectCompanyIds().isEmpty()) {
				String[] orgLocationIds =  sourceleadTicketDTO.getSelectCompanyIds().split(",");
						for(int i=0; i<orgLocationIds.length; i++) {
							orgLocationList.add(Long.parseLong(orgLocationIds[i]));
						}
			}
			
			if(orgLocationList.isEmpty()) {
				orgLocationList.add(0L);
			}
			List<String> ticketStatusList = new ArrayList<>();
			String statusQury = "";
			if(sourceleadTicketDTO.getTicketStatus() !=null && !sourceleadTicketDTO.getTicketStatus().isEmpty()){
				String[] ticketStatus =sourceleadTicketDTO.getTicketStatus().split(",");
					for(int i=0; i < ticketStatus.length; i++) {
						ticketStatusList.add(ticketStatus[i]);
				}
					statusQury = "and st.ticketStatus IN(:ticketStatusList)";
			 }
			
			if(searchDates != null) {
				subQuery = " and ((:varZero  IN (:orgLocationList)) OR (:varZero NOT IN (:orgLocationList) and st.orgLocationId.id IN (:orgLocationList)))";

				String query = "from SourceleadTickets st  where date(st.createdDate) BETWEEN :fromDate and :endDate "+query1+" "+statusQury+" "+subQuery+" "+sortingQry+" ";
			    if(!ticketStatusList.isEmpty()){
					ticketList = session.createQuery(query.toString()).setParameter("fromDate", searchDates[0]).setParameter("endDate", searchDates[1]).setLong("varZero", 0).setParameterList("orgLocationList", orgLocationList).setParameterList("ticketStatusList", ticketStatusList).setFirstResult(sourceleadTicketDTO.getStart()).setMaxResults(sourceleadTicketDTO.getLength()).list();
	
			    }
			    else{
				ticketList = session.createQuery(query.toString()).setParameter("fromDate", searchDates[0]).setParameter("endDate", searchDates[1]).setLong("varZero", 0).setParameterList("orgLocationList", orgLocationList).setFirstResult(sourceleadTicketDTO.getStart()).setMaxResults(sourceleadTicketDTO.getLength()).list();
			    }
			    String countQuery = "select count(*) from SourceleadTickets st where date(st.createdDate) BETWEEN :fromDate and :endDate "+query1+" "+subQuery+" "+sortingQry+"";
			    countlist = session.createQuery(countQuery.toString()).setParameter("fromDate", searchDates[0]).setParameter("endDate", searchDates[1]).setLong("varZero", 0).setParameterList("orgLocationList", orgLocationList).list();
			}else {
				subQuery = " ((:varZero  IN (:orgLocationList)) OR (:varZero NOT IN (:orgLocationList) and st.orgLocationId.id IN (:orgLocationList)))";
				
				
				String query = "from SourceleadTickets st  where   "+subQuery+" "+query1+"  "+sortingQry+"";
			    ticketList = session.createQuery(query.toString()).setLong("varZero", 0).setParameterList("orgLocationList", orgLocationList).setFirstResult(sourceleadTicketDTO.getStart()).setMaxResults(sourceleadTicketDTO.getLength()).list();
			    
			    String countQuery = "select count(*) from SourceleadTickets st where  "+subQuery+" "+query1+"  "+sortingQry+"";
			    countlist = session.createQuery(countQuery.toString()).setLong("varZero", 0).setParameterList("orgLocationList", orgLocationList).list();
			}
			
		}else {
			if(searchDates != null) {
				String query = "from SourceleadTickets st  where st.orgLocationId.id = :orgLocationId and st.userId.id = :userId and date(st.createdDate) BETWEEN :fromDate and :endDate "+query1+" "+sortingQry+"";
			    ticketList = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocations.getId()).setParameter("fromDate", searchDates[0]).setParameter("endDate", searchDates[1]).setFirstResult(sourceleadTicketDTO.getStart()).setMaxResults(sourceleadTicketDTO.getLength()).list();
			    
			    String countQuery = "select count(*) from SourceleadTickets st where st.orgLocationId.id = :orgLocationId and st.userId.id = :userId and date(st.createdDate) BETWEEN :fromDate and :endDate "+query1+" "+sortingQry+"";
			    countlist = session.createQuery(countQuery.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocations.getId()).setParameter("fromDate", searchDates[0]).setParameter("endDate", searchDates[1]).list();
			}else {
				String query = "from SourceleadTickets st  where st.orgLocationId.id = :orgLocationId and st.userId.id = :userId  "+query1+" "+sortingQry+"";
			    ticketList = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocations.getId()).setFirstResult(sourceleadTicketDTO.getStart()).setMaxResults(sourceleadTicketDTO.getLength()).list();
			    
			    String countQuery = "select count(*) from SourceleadTickets st where st.orgLocationId.id = :orgLocationId and st.userId.id = :userId "+query1+" "+sortingQry+"";
			    countlist = session.createQuery(countQuery.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocations.getId()).list();
			}
			//org list query
			
		}
		
	    int totalCount = 0;
		if (sourceleadTicketDTO != null) {
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
		
		response.setData(ticketList);
		return response;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public SourceleadTickets getSourceleadTicketsByTicketUUID(String ticketUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadTickets> tickets = session.createQuery("from SourceleadTickets st left join fetch st.userId left join fetch st.orgLocationId where st.ticketUUID =:ticketUUID" ).setString("ticketUUID", ticketUUID).list();
		if (tickets.size() > 0) {
			return tickets.get(0);
		} else {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadTicketsAttachments> getSourceleadTicketsAttachmentsListByTicketId(Long ticketId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from SourceleadTicketsAttachments sta where sta.ticketId.id =:ticketId and sta.status = '1'";
	    List<SourceleadTicketsAttachments> sourceleadTicketsAttachmentsList = session.createQuery(query.toString()).setLong("ticketId", ticketId).list();
        return sourceleadTicketsAttachmentsList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean deleteSourceleadTicketAttachmentByTicketId(Long ticketAttachementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql ="delete from SourceleadTicketsAttachments sta where sta.id = :ticketAttachementId";
		Query query = session.createQuery(hql).setLong("ticketAttachementId", ticketAttachementId);
		query.executeUpdate();
		
		return true;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SourceleadTicketsAttachments getSourceleadTicketAttachmentByTicketId(Long attachmentId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadTicketsAttachments> ticketsAttachement = session.createQuery("from SourceleadTicketsAttachments  sta where sta.id =:attachmentId" ).setLong("attachmentId", attachmentId).list();
		if (ticketsAttachement.size() > 0) {
			return ticketsAttachement.get(0);
		} else {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadTicketsComments> getSourceleadTicketsCommentsListByTicketId(Long ticketId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from SourceleadTicketsComments stc where stc.ticketId.id =:ticketId and stc.status = '1'";
	    List<SourceleadTicketsComments> sourceleadTicketsCommentsList = session.createQuery(query.toString()).setLong("ticketId", ticketId).list();
        return sourceleadTicketsCommentsList;
	}


	@Override
	@Transactional
	public SourceleadTicketsComments saveSourceleadTicketsComments(SourceleadTicketsComments sourceleadTicketsComments) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (sourceleadTicketsComments != null) {
				session.saveOrUpdate(sourceleadTicketsComments);
			}
			logger.debug("Saving SourceleadTicketsComments");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SourceleadTicketsComments"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return sourceleadTicketsComments;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getTicketGeneratedIdByGeneratedIdcount(Integer generatedIdCount) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(ID) FROM "+schema+".SOURCELEAD_TICKETS";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getSourceleadTicketRequestedOrgLocatons() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from OrgLocations locations where locations.id IN (select distinct(ticket.orgLocationId.id) from SourceleadTickets ticket where ticket.status = '1') ";
	    List<OrgLocations> orgLocationsList = session.createQuery(query.toString()).list();
        return orgLocationsList;
	}
	 
}
