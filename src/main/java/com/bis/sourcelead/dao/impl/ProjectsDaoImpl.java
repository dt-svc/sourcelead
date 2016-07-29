package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.entity.Projects;

@Repository
public class ProjectsDaoImpl implements ProjectsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	

    @Autowired
	private ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDaoImpl.class);

	/**
	 * This method for save project
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects saveProject(Projects projects) throws Exception {
		logger.debug("In Projects Save method");
		Session session = this.sessionFactory.getCurrentSession();
		session.save(projects);
		return projects;
	}

	/**
	 * This method for update project
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects updateProject(Projects projects) throws Exception {
		logger.debug("In Projects Update method");
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(projects);
		logger.info("Record updated successfully..!");
		return projects;
	}

	/**
	 * This method for get project by id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getProjectById(Long id) throws Exception {
		logger.debug("In Projects getProject method");
		Session session = this.sessionFactory.getCurrentSession();
		return (Projects) session.get(Projects.class, id);
	}
	
	/**
	 * This method for get project by orgLocationsId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project where project.orgLocationsId = :orgLocationsId ";
		List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		
		return projectsList;
	}
	
	/**
	 * This method for get primary project by orgLocationsId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("=========================================In ProjectsDaoImpl getPrimaryProjectByOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.isPrimary = '1' ";
		Collection<Projects> projectsList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();

		Projects projects = null;
		if (projectsList != null && projectsList.size() > 0) {
			projects = (Projects) projectsList.iterator().next();
		}
		return projects;
	}

	/**
	 * This method for delete project by id.
	 * Here we are doing soft deletion only, record not deleted from DB just we have to change the status
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean deleteProjectById(Long id) throws Exception {
		 logger.debug("In Projects deleteProject method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean isDeleted = false;
		 if(id != null){
			 Projects projects= (Projects)session.load(Projects.class,id);
			 projects.setStatus("0");
			 session.saveOrUpdate(projects);
			 logger.info("Successfully deleted !!!");
			 isDeleted = true;
		 }
		return isDeleted;
	}
	
	
	/**
	 * This method for validate project by projectSequeneNumber. It returns boolean value
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception {
		 logger.debug("In ProjectsDaoImpl validateProjectSequeneNumber method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(projectSequeneNumber != null && orgLocationsId != null){
				String query = "from Projects project where project.orgLocationsId = :orgLocationsId and project.projectSequeneNumber = :projectSequeneNumber ";
				Collection<Projects> projectsList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setString("projectSequeneNumber", projectSequeneNumber).list();

				if (projectsList != null && projectsList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

	/**
	 * This method for get all projects
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<Projects> getAllProjects() throws Exception {
		logger.debug("In Projects getAllProjects method");
        Session session = this.sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Projects.class);
        List<Projects> projectsList = crit.list();
        return projectsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception {
		logger.info("In Projects getAllProjects method");
        Session session = this.sessionFactory.getCurrentSession();
        
        String query = "from  Projects projects  left join fetch projects.orgRelationId  orgrelation left join fetch orgrelation.clientLocationId orgLocation "	

        		+ "left join fetch orgLocation.org  where projects.id IN (:projectIds) and projects.orgLocationsId = :orgLocationId";
        List<Projects> projectsList  =   session.createQuery(query.toString()).setParameterList("projectIds", projectIds).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
       
        return projectsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer projectIdValidation(Long projectId) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query ="select count(*)from Projects projects where projects.projectSequeneNumber = :projectId";
		
		List<Long> deptCount =   session.createQuery(query.toString()).setLong("projectId", projectId)
        										.list();
			Integer count = 0;
			if(deptCount != null && !deptCount.isEmpty()){
					count = deptCount.get(0).intValue();
			}
			return count;
		
	}

     public class FilterNameValueMap<key,value> extends HashMap<key,value>{

	private static final long serialVersionUID = -2650993424650202629L;

     }
    /**
     * This method for get projects based on OrgRelationId (particular client)
     */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId, Long locationId) throws Exception {
		logger.debug("In ProjectsDaoImpl class getAllProjectsByOrgRelationId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects projects  left join fetch projects.orgRelationId orgre  where  orgre.clientLocationId  IN(select orgrel.clientLocationId from OrgRelations  orgrel where orgrel.clientLocationId = :orgRelationId and orgrel.locationId = :locationId) and projects.orgLocationsId = :locationId";
		List<Projects> clientProjectsList =  session.createQuery(query.toString()).setLong("orgRelationId", orgRelationId).setLong("locationId", locationId).list();
		
		return clientProjectsList;
	}

	@Override

	@Transactional(rollbackFor = { Exception.class })
	public Integer updateStartDateOfProjectId(Date startDate ,Long projectId) {
		Session session =  this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects pj set pj.startDate =:startDate where pj.id = :projectId");
		query.setLong("projectId", projectId);
		query.setDate("startDate", startDate);
		int result = query.executeUpdate();
		return result;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer updateEndDateOfProjectId(Date endDate, Long projectId) {
		Session session =  this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects pj set pj.endDate =:endDate where pj.id = :projectId");
		query.setLong("projectId", projectId);
		query.setDate("endDate", endDate);
		int result = query.executeUpdate();
		return result;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Long> getApprovarAssignedProjects(Long userId, Long orgLocationId) throws Exception{
		logger.debug("In ProjectsDaoImpl class getApprovarAssignedProjects method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select projects.id from Projects projects where projects.orgLocationsId = :orgLocationId and "
				+ "projects.id IN (select pah.projectId from ProjectApproverHierarchy pah  where pah.approverUserId = :userId)";
		List<Long> projectsIdList =  session.createQuery(query.toString())
									.setLong("userId", userId)
									.setLong("orgLocationId", orgLocationId)
									.setCacheable(true).list();
		
		return projectsIdList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Projects p where p.projectSequeneNumber = :projectSequeneNumber";
		List<Projects> projectsList =  session.createQuery(query.toString())
				.setString("projectSequeneNumber", projectSequeneNumber)
				.setCacheable(true).list();
		if(projectsList != null){
			return projectsList.get(0);
		}
		return null;
	}
	
	
	
	  /**
	   * This method for get projects based on clients and Status (Any number of Client)
	   */
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public Collection<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId, String clients,
				String status) throws Exception {

			
			    Collection<Projects> plist = null;
		        Session session = this.sessionFactory.getCurrentSession();
				String[] tokens = status.split(",");
				ArrayList<Projects> projectssList = new ArrayList<Projects>();
				String[] client = clients.split(",");
				if(client[0] != null && client[0].length() > 0){
						for(String clnt :client){
							String subQuery2 = "";
								if(Arrays.asList(tokens).contains("All")){
									subQuery2 = subQuery2+ "ps.projectStatus = 'STARTED' or ps.projectStatus = 'INPROGRESS' or ps.projectStatus = 'COMPLETED' or ps.projectStatus = 'ONHOLD' or ";
								}
								  
								if(Arrays.asList(tokens).contains("STARTED")){
									subQuery2 = subQuery2+ "ps.projectStatus = 'STARTED' or ";
								}
								
								if(Arrays.asList(tokens).contains("INPROGRESS")){
									subQuery2 = subQuery2 + "ps.projectStatus = 'INPROGRESS' or ";
								}
								if(Arrays.asList(tokens).contains("COMPLETED")){
									subQuery2 = subQuery2 + "ps.projectStatus = 'COMPLETED' or ";
								}
								if(Arrays.asList(tokens).contains("ONHOLD")){
									subQuery2 = subQuery2 + "ps.projectStatus = 'ONHOLD' or ";
								}
								
								if (tokens[0] != null && tokens[0].length() > 0){
									subQuery2 = "and (" + subQuery2.substring(0,subQuery2.length() - 4)+")";
								}
							 String subquery1 ="and ps.orgRelationId ="+clnt;
							 String subquery3 ="(ps.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId)";
							 String query = "from  Projects ps   left join fetch ps.orgLocationsId  where "+subquery3+"  "+subQuery2+" "+subquery1+")" ;
							 plist = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list(); 
							 
							 projectssList.addAll(plist);
					
						}
				}else{
					String subQuery2 = "";
					if(Arrays.asList(tokens).contains("All")){
						subQuery2 = subQuery2+ "ps.projectStatus = 'STARTED' or ps.projectStatus = 'INPROGRESS' or ps.projectStatus = 'COMPLETED' or ps.projectStatus = 'ONHOLD' or ";
					}
					  
					if(Arrays.asList(tokens).contains("STARTED")){
						subQuery2 = subQuery2+ "ps.projectStatus = 'STARTED' or ";
					}
					
					if(Arrays.asList(tokens).contains("INPROGRESS")){
						subQuery2 = subQuery2 + "ps.projectStatus = 'INPROGRESS' or ";
					}
					if(Arrays.asList(tokens).contains("COMPLETED")){
						subQuery2 = subQuery2 + "ps.projectStatus = 'COMPLETED' or ";
					}
					if(Arrays.asList(tokens).contains("ONHOLD")){
						subQuery2 = subQuery2 + "ps.projectStatus = 'ONHOLD' or ";
					}
					
					if (tokens[0] != null && tokens[0].length() > 0){
						subQuery2 = "and (" + subQuery2.substring(0,subQuery2.length() - 4)+")";
					}
					 
					 String subquery3 ="(ps.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId)";
					 String query = "from  Projects ps   left join fetch ps.orgLocationsId  where "+subquery3+"  "+subQuery2+")" ;
					 plist = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list(); 
					 projectssList.addAll(plist);
				}
				Collection<Projects> userCollection = new HashSet<Projects>(projectssList);
				return userCollection;

		}
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Projects> getProjectByOrgLocationsIdAndUserId(Long orgLocationsId, Long userId) {
			logger.debug("In ProjectsDaoImpl class getProjectByOrgLocationsId method");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Projects pj where pj.orgLocationsId = :orgLocationsId and pj.id NOT IN(select pm.projects.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId ) ) ";
			List<Projects> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setLong("userId", userId).setCacheable(true).list();
			return projectsList;
		}
}
