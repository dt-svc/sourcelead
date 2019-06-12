package com.bis.sourcelead.ticketManagement.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.TicketCategoryDTO;
import com.bis.sourcelead.dto.TicketPriorityDTO;
import com.bis.sourcelead.dto.TicketStatusDTO;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Ticket;
import com.bis.sourcelead.entity.TicketAssignTo;
import com.bis.sourcelead.entity.TicketAttachment;
import com.bis.sourcelead.entity.TicketPriority;
import com.bis.sourcelead.entity.TicketStatus;
import com.bis.sourcelead.entity.TicketStatusHistory;
import com.bis.sourcelead.entity.TicketType;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.TicketCategory;
import com.bis.sourcelead.entity.TicketCategoryAssignTo;
import com.bis.sourcelead.entity.TicketComment;
import com.bis.sourcelead.enums.Ticket_Priority;
import com.bis.sourcelead.enums.Ticket_Status;
import com.bis.sourcelead.enums.Ticket_Type;
import com.bis.sourcelead.ticketManagement.dao.TicketManagementDao;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Repository
@Transactional
public class TicketManagementDaoImpl implements TicketManagementDao {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TicketManagementDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OrgLocationsDao orgLocationsDao;

	@SuppressWarnings("unchecked")
	@Override
	public int dumpTicketManagementPreDefinedValuesToAllOrgLocations(long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			User adminUser = userDao.getUserByEmail("info@sourcelead.com");
			if(orgLocationId > 0) {
				OrgLocations orgLocations = orgLocationsDao.getOrgLocations(orgLocationId);
				if (adminUser != null && orgLocations != null) {
					applyTicketManagementPreDefinedData(session, orgLocations, adminUser);
					return 1;
				}
			} else {
				String query = "select distinct o from OrgLocations o order by o.id";
				List<OrgLocations> orgLocationsList = session.createQuery(query).list();
				if (adminUser != null && orgLocationsList != null && !orgLocationsList.isEmpty()) {
					for (OrgLocations orgLocations : orgLocationsList) {
						applyTicketManagementPreDefinedData(session, orgLocations, adminUser);
					}
					return orgLocationsList.size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private void applyTicketManagementPreDefinedData(Session session, OrgLocations orgLocations, User adminUser) {
		try {
			// code for adding TicketType records to OrgLocations
			List<Long> ticketTypeCount = session.createQuery("select count(*) from TicketType t where t.orgLocations.id="+orgLocations.getId()).list();
			if(ticketTypeCount == null || (!ticketTypeCount.isEmpty() && ticketTypeCount.get(0) == 0)) {
				for (Ticket_Type t : Ticket_Type.values()) {
					TicketType ticketType = new TicketType();
					ticketType.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
					ticketType.setName(t.name());
					ticketType.setDescription(t.getValue());
					ticketType.setStatus(1);
					ticketType.setOrgLocations(orgLocations);
					ticketType.setCreatedBy(adminUser.getId());
					ticketType.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
					session.saveOrUpdate(ticketType);
					System.out.println("Added TicketType '" + t.name() + "' for OrgLocation : " + orgLocations.getId());
				}
			}

			List<Long> TicketPriorityCount = session.createQuery("select count(*) from TicketPriority p where p.orgLocations.id="+orgLocations.getId()).list();
			if(TicketPriorityCount == null || (!TicketPriorityCount.isEmpty() && TicketPriorityCount.get(0) == 0)) {
				// code for adding TicketPriority records to OrgLocations
				for (Ticket_Priority p : Ticket_Priority.values()) {
					TicketPriority ticketPriority = new TicketPriority();
					ticketPriority.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
					ticketPriority.setName(p.name());
					ticketPriority.setDescription(p.getValue());
					ticketPriority.setStatus(1);
					ticketPriority.setOrgLocations(orgLocations);
					ticketPriority.setCreatedBy(adminUser.getId());
					ticketPriority.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
					session.saveOrUpdate(ticketPriority);
					System.out.println("Added TicketPriority '" + p.name() + "' for OrgLocation : " + orgLocations.getId());
				}
			}

			List<Long> TicketStatusCount = session.createQuery("select count(*) from TicketStatus s where s.orgLocations.id="+orgLocations.getId()).list();
			if(TicketStatusCount == null || (!TicketStatusCount.isEmpty() && TicketStatusCount.get(0) == 0)) {
				// code for adding TicketStatus records to OrgLocations
				for (Ticket_Status s : Ticket_Status.values()) {
					TicketStatus ticketStatus = new TicketStatus();
					ticketStatus.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
					ticketStatus.setName(s.name());
					ticketStatus.setDescription(s.getValue());
					ticketStatus.setStatus(1);
					ticketStatus.setOrgLocations(orgLocations);
					ticketStatus.setCreatedBy(adminUser.getId());
					ticketStatus.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
					session.saveOrUpdate(ticketStatus);
					System.out.println("Added TicketStatus '" + s.name() + "' for OrgLocation : " + orgLocations.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TicketType> getTicketTypeListByOrgLocationId(Long orgLocationId, int status, String searchValue,TicketTypeDTO ticketTypeDTO) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketType t where t.orgLocations.id=" + orgLocationId;
			if(status >= 0) {
				query += " and t.status=" + status;
			}
			if(searchValue != null && !searchValue.isEmpty()) {
				query += " and (t.name like '%" + searchValue + "%' or t.description like '%" + searchValue + "%')";
			}
			
			String qry = "";
			if (StringUtils.isNotBlank(ticketTypeDTO.getSortColumn())) {
				String sortColumnName = ticketTypeDTO.getSortColumn();
					qry = " order by t." + sortColumnName + " " + ticketTypeDTO.getSortOrder();
			}
			String finalQuery = query+qry;
			return session.createQuery(finalQuery).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TicketType getTicketTypeById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(TicketType.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getTicketTypeIdByNameAndOrgLocationId(String name, long orgLocationsId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query= "select t.id from TicketType t where t.name=:name and t.orgLocations.id=:orgLocationsId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("name", name);
			queryObj.setLong("orgLocationsId", orgLocationsId);
			List<Long> ticketIdlist = queryObj.list();
			if(ticketIdlist != null && !ticketIdlist.isEmpty()) 
				return ticketIdlist.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public long getTicketCategoryIdByNameAndOrgLocationId(String name, long orgLocationsId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query= "select t.id from TicketCategory t where t.name=:name and t.orgLocations.id=:orgLocationsId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("name", name);
			queryObj.setLong("orgLocationsId", orgLocationsId);
			List<Long> ticketIdlist = queryObj.list();
			if(ticketIdlist != null && !ticketIdlist.isEmpty()) 
				return ticketIdlist.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long getTicketPriorityIdByNameAndOrgLocationId(String name, long orgLocationsId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query= "select t.id from TicketPriority t where t.name=:name and t.orgLocations.id=:orgLocationsId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("name", name);
			queryObj.setLong("orgLocationsId", orgLocationsId);
			List<Long> ticketIdlist = queryObj.list();
			if(ticketIdlist != null && !ticketIdlist.isEmpty()) 
				return ticketIdlist.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long getTicketStatusIdByNameAndOrgLocationId(String name, long orgLocationsId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query= "select t.id from TicketStatus t where t.name=:name and t.orgLocations.id=:orgLocationsId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("name", name);
			queryObj.setLong("orgLocationsId", orgLocationsId);
			List<Long> ticketIdlist = queryObj.list();
			if(ticketIdlist != null && !ticketIdlist.isEmpty()) 
				return ticketIdlist.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long saveTicketType(TicketType ticketType) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(ticketType);
			return ticketType.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	@Transactional
	public boolean deleteTicketTypeDataById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketType tickettype = session.get(TicketType.class, id);
			if (tickettype != null) {
				session.delete(tickettype);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TicketCategory> getTicketCategoryListByOrgLocationId(Long orgLocationId, int status, String searchValue,TicketCategoryDTO ticketCategoryDTO) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketCategory t where t.orgLocations.id=" + orgLocationId;
			if(status >= 0) {
				query += " and t.status=" + status;
			}
			if(searchValue != null && !searchValue.isEmpty()) {
				query += " and (t.name like '%" + searchValue + "%' or t.description like '%" + searchValue + "%')";
			}
			String qry = "";
			if (StringUtils.isNotBlank(ticketCategoryDTO.getSortColumn())) {
				String sortColumnName = ticketCategoryDTO.getSortColumn();
					qry = " order by t." + sortColumnName + " " + ticketCategoryDTO.getSortOrder();
			}
			String finalQuery = query+qry;
			return session.createQuery(finalQuery).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

}

	@Override
	public TicketCategory getTicketCategoryById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(TicketCategory.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long saveTicketCategory(TicketCategory ticketCategory) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(ticketCategory);
			return ticketCategory.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
}

	@Override
	public Boolean deleteTicketCategoryDataById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketCategory ticketcategory = session.get(TicketCategory.class, id);
			if (ticketcategory != null) {
				session.delete(ticketcategory);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TicketPriority> getTicketPriorityListByOrgLocationId(Long orgLocationId,int status, String searchValue,TicketPriorityDTO ticketPriorityDTO) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketPriority t where t.orgLocations.id=" + orgLocationId;
			if(status >= 0) {
				query += " and t.status=" + status;
			}
			if(searchValue != null && !searchValue.isEmpty()) {
				query += " and (t.name like '%" + searchValue + "%' or t.description like '%" + searchValue + "%')";
			}
			String qry = "";
			if (StringUtils.isNotBlank(ticketPriorityDTO.getSortColumn())) {
				String sortColumnName = ticketPriorityDTO.getSortColumn();
					qry = " order by t." + sortColumnName + " " + ticketPriorityDTO.getSortOrder();
			}
			String finalQuery = query+qry;
			return session.createQuery(finalQuery).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TicketPriority getTicketPriorityById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(TicketPriority.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long saveTicketPriority(TicketPriority ticketpriority) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(ticketpriority);
			return ticketpriority.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Boolean deleteTicketPriorityDataById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketPriority ticketpriority = session.get(TicketPriority.class, id);
			if (ticketpriority != null) {
				session.delete(ticketpriority);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TicketStatus> getTicketStatusListByOrgLocationId(Long orgLocationId, int status, String searchValue,TicketStatusDTO ticketStatusDTO) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketStatus t where t.orgLocations.id=" + orgLocationId;
			if(status >= 0) {
				query += " and t.status=" + status;
			}
			if(searchValue != null && !searchValue.isEmpty()) {
				query += " and (t.name like '%" + searchValue + "%' or t.description like '%" + searchValue + "%')";
			}
			//query += " order by t.id desc";
			String qry = "";
			if (StringUtils.isNotBlank(ticketStatusDTO.getSortColumn())) {
				String sortColumnName = ticketStatusDTO.getSortColumn();
					qry = " order by t." + sortColumnName + " " + ticketStatusDTO.getSortOrder();
			}
			String finalQuery = query+qry;
			return session.createQuery(finalQuery).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TicketStatus getTicketStatusById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(TicketStatus.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long saveTicketStatus(TicketStatus ticketStatus) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(ticketStatus);
			return ticketStatus.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Boolean deleteTicketStatusDataById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketStatus ticketstatus = session.get(TicketStatus.class, id);
			if (ticketstatus != null) {
				session.delete(ticketstatus);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Ticket> getTicketListByOrgLocationId(Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Ticket t where t.orgLocations.id=" + orgLocationId + " order by t.id desc";
			return session.createQuery(query).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Ticket getTicketById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(Ticket.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean saveTicket(Ticket ticketManagement) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(ticketManagement);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean changeStatusTicketTypeDataById(long id,int status, OrgLocations orgLocation) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketType ticketType = session.get(TicketType.class, id);
			if (ticketType != null) {
				ticketType.setStatus(status);
				session.saveOrUpdate(ticketType);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean changeTicketCategoryStatusDataById(long id, int status, OrgLocations orgLocation) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketCategory ticketCategory = session.get(TicketCategory.class, id);
			if (ticketCategory != null) {
				ticketCategory.setStatus(status);
				session.saveOrUpdate(ticketCategory);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Boolean changeTicketPriorityStatusDataById(long id, int status, OrgLocations orgLocation) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketPriority ticketPriority = session.get(TicketPriority.class, id);
			if (ticketPriority != null) {
				ticketPriority.setStatus(status);
				session.saveOrUpdate(ticketPriority);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean changeTicketStatusDataById(long id, int status, OrgLocations orgLocation) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketStatus ticketStatus = session.get(TicketStatus.class, id);
			if (ticketStatus != null) {
				ticketStatus.setStatus(status);
				session.saveOrUpdate(ticketStatus);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Ticket> getTicketManagementListByOrgLocationId(Long orgLocationId,long userId, int status, String userRole, String searchValue) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select t from Ticket t";
			if(!Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) && !Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
				query += " join t.ticketAssignToList assignTo";
			}
			query += " where t.orgLocations.id=" + orgLocationId;
			if(!Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) && !Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
				query += " and t.createdBy.id=" + userId + " or assignTo.user.id=" + userId;
			}
			if(status >= 0) {
				query += " and t.status=" + status;
			}
			if(searchValue != null && !searchValue.isEmpty()) {
				query += " and (t.generatedId like '%" + searchValue + "%' or t.title like '%" + searchValue + "%' or t.ticketPriority.name like '%" + searchValue + "%' or t.createdBy.firstName like '%" + searchValue + "%' or t.createdBy.middelName like '%" + searchValue + "%' or t.createdBy.lastName like '%" + searchValue + "%' or t.ticketStatus.name like '%" + searchValue + "%')";
			}
			query += " order by t.id desc";
			return session.createQuery(query).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Ticket getTicketSummaryByUUID(String uuid, Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Ticket t where t.uuid=:uuid and t.orgLocations.id=:orgLocationId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("uuid", uuid);
			queryObj.setLong("orgLocationId", orgLocationId);
			List<Ticket> ticketList = queryObj.list();
			if(ticketList != null && !ticketList.isEmpty())
				return ticketList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<TicketComment> getTicketCommentListByTicketId(Long ticketId, long userId, Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketComment tc where tc.ticket.id =:ticketId and tc.status = 1 order by tc.id desc";
			return session.createQuery(query).setLong("ticketId", ticketId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TicketComment getTicketCommentByTicketId(long ticketId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketComment tc where tc.ticket.id=:ticketId";
			List<TicketComment> ticketCommentList = session.createQuery(query).setLong("ticketId", ticketId).list();
			if(ticketCommentList != null && !ticketCommentList.isEmpty())
				return ticketCommentList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public TicketComment getTicketComment(long ticketCommentId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(TicketComment.class, ticketCommentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Boolean saveTicketComment(TicketComment ticketComment) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(ticketComment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Boolean deleteTicketComment(long ticketCommentId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			TicketComment ticketComment = session.get(TicketComment.class, ticketCommentId);
			if(ticketComment != null) {
				session.delete(ticketComment);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Ticket getTicketDataById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(Ticket.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Boolean changeTicketManagementStatusDataById(long id, int status, OrgLocations orgLocation) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Ticket ticket = session.get(Ticket.class, id);
			if (ticket != null) {
				ticket.setStatus(status);
				session.saveOrUpdate(ticket);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean saveTicketStatusHistory(TicketStatusHistory ticketStatusHistory) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(ticketStatusHistory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getTicketAssignToIdList(long ticketId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select tat.user.id from TicketAssignTo tat where tat.ticket.id=:ticketId";
			return session.createQuery(query).setLong("ticketId", ticketId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TicketAssignTo getTicketAssignToByUserIdAndTicket(long userId, long ticketId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketAssignTo tat where tat.ticket.id=:ticketId and tat.user.id=:userId";
			List<TicketAssignTo> ticketAssignToList = session.createQuery(query).setLong("ticketId", ticketId).setLong("userId", userId).list();
			if(ticketAssignToList != null && !ticketAssignToList.isEmpty())
				return ticketAssignToList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteTicketAssignTo(TicketAssignTo ticketAssignTo) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(ticketAssignTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TicketCategoryAssignTo getTicketCategoryAssignToByUserIdAndTicket(long userId, long ticketCategoryId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketCategoryAssignTo tcat where tcat.ticketCategory.id=:ticketCategoryId and tcat.user.id=:userId";
			List<TicketCategoryAssignTo> ticketCategoryAssignToList = session.createQuery(query).setLong("ticketCategoryId", ticketCategoryId).setLong("userId", userId).list();
			if(ticketCategoryAssignToList != null && !ticketCategoryAssignToList.isEmpty())
				return ticketCategoryAssignToList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Ticket getTicketSnoAndTicketId(String generatedId, Long ticketId, Long orgLocationsId) {
		try {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Ticket ticket where ticket.generatedId = :generatedId and ticket.orgLocations.id = :orgLocationsId and ticket.id = :ticketId and ticket.status = 1";
		List<Ticket> ticketList =  session.createQuery(query) .setString("generatedId", generatedId).setLong("ticketId", ticketId).setLong("orgLocationsId", orgLocationsId).list();
		if(ticketList != null && !ticketList.isEmpty()) {
			return ticketList.get(0);
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean validateTicketId(String generatedId, Long orgLocationsId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Ticket ticket where ticket.orgLocations.id = :orgLocationsId and ticket.generatedId = :generatedId and ticket.status = 1 ";
		List<Ticket> ticketList = session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setString("generatedId", generatedId).list();
		if (ticketList != null && ticketList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TicketAttachment getTicketAttachmentByTicketIdAndUUID(long ticketId, String uuid) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TicketAttachment ta where ta.ticket.id=:ticketId and ta.uuid=:uuid";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("ticketId", ticketId);
			queryObj.setString("uuid", uuid);
			List<TicketAttachment> ticketAttachmentList = queryObj.list();
			if(ticketAttachmentList != null && !ticketAttachmentList.isEmpty()) {
				return ticketAttachmentList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSavedTicketAttachmentUUIDList(long ticketId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select ta.uuid from TicketAttachment ta where ta.ticket.id=:ticketId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("ticketId", ticketId);
			return queryObj.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteTicketAttachment(TicketAttachment ticketAttachment) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(ticketAttachment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean changeTicketManagementStatus(long ticketId, int status) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Ticket ticket = (Ticket) session.get(Ticket.class, ticketId);
			if(ticket != null) {
				ticket.setStatus(status);
				session.saveOrUpdate(ticket);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getCategoryAssignToListByTicketCategoryId(long ticketCategoryId, Long orgLocationId, long userId) {
		try {
			Session session =  this.sessionFactory.getCurrentSession();
			String query = "select tca.user from TicketCategoryAssignTo tca where tca.ticketCategory.id = :ticketCategoryId ";
			List<User> userList = session.createQuery(query).setLong("ticketCategoryId", ticketCategoryId).list();
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getTicketCategoryAssignToIdList(long ticketCategoryId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select tcat.user.id from TicketCategoryAssignTo tcat where tcat.ticketCategory.id=:ticketCategoryId";
			return session.createQuery(query).setLong("ticketCategoryId", ticketCategoryId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void deleteTicketCategoryAssignTo(TicketCategoryAssignTo ticketCategoryAssignTo) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(ticketCategoryAssignTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}