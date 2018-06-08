package com.bis.sourcelead.dao.impl;

import com.bis.sourcelead.dao.JobBoardConfigurationDAO;
import com.bis.sourcelead.dto.OrgJobBoardAccountDTO;
import com.bis.sourcelead.dto.OrgJobBoardAccountUserDTO;
import com.bis.sourcelead.dto.OrgJobBoardDTO;
import com.bis.sourcelead.entity.JobBoard;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgJobBoard;
import com.bis.sourcelead.entity.OrgJobBoardAccount;
import com.bis.sourcelead.entity.OrgJobBoardAccountUser;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.PasswordSecurity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Raja Gopal Reddy P
 */
@Repository
public class JobBoardConfigurationDAOImpl implements JobBoardConfigurationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<OrgJobBoardDTO> getOrgJobBoardList(long orgId, boolean onlyEnabled) {
        List<OrgJobBoardDTO> resultList = new ArrayList<>();
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String query = "from OrgJobBoard j where j.org.id = :orgId";
            if (onlyEnabled) {
                query += " and j.active = true";
            }
            query += " order by j.jobBoard.name";
            Query queryObj = session.createQuery(query);
            queryObj.setLong("orgId", orgId);
            List<OrgJobBoard> list = queryObj.list();
            if (!list.isEmpty()) {
                for (OrgJobBoard oj : list) {
                    OrgJobBoardDTO oj1 = new OrgJobBoardDTO();
                    oj1.setId(oj.getId());
                    JobBoard j = oj.getJobBoard();
                    oj1.setJobBoardId(j.getId());
                    oj1.setName(j.getName());
                    oj1.setLogoUrl(j.getLogoUrl());
                    oj1.setActive(oj.isActive());
                    oj1.setOrgId(orgId);
                    resultList.add(oj1);
                }
            }
            if (!onlyEnabled) {
                String query1 = "from JobBoard j order by j.name";
                Query queryObj1 = session.createQuery(query1);
                List<JobBoard> list1 = queryObj1.list();
                outer:
                for (JobBoard j : list1) {
                    for (OrgJobBoardDTO oj : resultList) {
                        if (j.getId() == oj.getJobBoardId()) {
                            continue outer;
                        }
                    }
                    OrgJobBoardDTO oj1 = new OrgJobBoardDTO();
                    oj1.setId(0);
                    oj1.setJobBoardId(j.getId());
                    oj1.setName(j.getName());
                    oj1.setLogoUrl(j.getLogoUrl());
                    oj1.setActive(false);
                    oj1.setOrgId(orgId);
                    resultList.add(oj1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public long saveOrgJobBoard(OrgJobBoardDTO orgJobBoardDTO) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            OrgJobBoard jobBoard = new OrgJobBoard();
            boolean isNew = true;
            if (orgJobBoardDTO.getId() != 0) {
                jobBoard = session.get(OrgJobBoard.class, orgJobBoardDTO.getId());
                isNew = false;
            } else {
                List<OrgJobBoardDTO> list = getOrgJobBoardList(orgJobBoardDTO.getOrgId(), false);
                for (OrgJobBoardDTO oj : list) {
                    if (oj.getName().equals(orgJobBoardDTO.getName()) && oj.getId() > 0) {
                        jobBoard = session.get(OrgJobBoard.class, oj.getId());
                        isNew = false;
                        break;
                    }
                }
            }
            if (isNew) {
                //created...
            } else {
                //updated...
            }
            jobBoard.setJobBoard(session.get(JobBoard.class, orgJobBoardDTO.getJobBoardId()));
            jobBoard.setOrg(session.get(Org.class, orgJobBoardDTO.getOrgId()));
            jobBoard.setActive(orgJobBoardDTO.isActive());
            session.saveOrUpdate(jobBoard);
            return jobBoard.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int changeJobBoardStatus(long orgJobBoardId) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            OrgJobBoard jobBoard = session.get(OrgJobBoard.class, orgJobBoardId);
            jobBoard.setActive(!jobBoard.isActive());
            //updated...        
            session.saveOrUpdate(jobBoard);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional
    public List<OrgJobBoardAccountDTO> getOrgJobBoardAccountList(long orgId, long jobBoardId, String search) {
        List<OrgJobBoardAccountDTO> resultList = new ArrayList<>();
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String query = "from OrgJobBoardAccount p where p.org.id = :orgId";
            if (jobBoardId > 0) {
                query += " and p.orgJobBoard.jobBoard.id = :jobBoardId";
            }
            if (StringUtils.isNotEmpty(search)) {
                query += " and p.name like :search";
            }
            query += " order by p.name";
            Query queryObj = session.createQuery(query);
            queryObj.setLong("orgId", orgId);
            if (jobBoardId > 0) {
                queryObj.setLong("jobBoardId", jobBoardId);
            }
            if (StringUtils.isNotEmpty(search)) {
                queryObj.setString("search", "%" + search + "%");
            }
            List<OrgJobBoardAccount> list = queryObj.list();
            for (OrgJobBoardAccount oa : list) {
                OrgJobBoardAccountDTO oj = new OrgJobBoardAccountDTO();
                oj.setOrgId(oa.getOrg().getId());
                oj.setId(oa.getId());
                oj.setOrgJobBoardId(oa.getOrgJobBoard().getId());
                oj.setJobBoardId(oa.getOrgJobBoard().getJobBoard().getId());
                oj.setActive(oa.isActive());
                oj.setAccessToAll(oa.isAccessToAll());
                oj.setName(oa.getName());
                oj.setUsername(oa.getUsername());
//                oj.setPassword(new PasswordSecurity().encrypt(oa.getPassword()));
                oj.setApiKey(oa.getApiKey());
                oj.setAccessToken(oa.getAccessToken());
                oj.setAccountId(oa.getAccountId());
                oj.setJobBoardName(oa.getOrgJobBoard().getJobBoard().getName());
                if(!oa.isAccessToAll()) {
                    int userCount = 0;
                    int teamCount = 0;
                    List<OrgJobBoardAccountUser> list1 = oa.getOrgJobBoardAccountUserList();
                    for (OrgJobBoardAccountUser oa1 : list1) {
                        if(oa1.getUser() != null) {
                            userCount ++;
                        } else if(oa1.getTeam() != null) {
                            teamCount ++;
                        }
                    }
                    oj.setAssignCount(userCount+" / "+teamCount);
                }
                resultList.add(oj);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int saveOrgJobBoardAccount(OrgJobBoardAccountDTO orgJobBoardAccountDTO) {
        try {
            if (orgJobBoardAccountDTO.getOrgJobBoardId() == 0) {
                OrgJobBoardDTO orgJobBoardDTO = new OrgJobBoardDTO();
                orgJobBoardDTO.setOrgId(orgJobBoardAccountDTO.getOrgId());
                orgJobBoardDTO.setActive(false);
                orgJobBoardDTO.setJobBoardId(orgJobBoardAccountDTO.getJobBoardId());
                orgJobBoardAccountDTO.setOrgJobBoardId(saveOrgJobBoard(orgJobBoardDTO));
            }
            Session session = this.sessionFactory.getCurrentSession();
            OrgJobBoardAccount orgJobBoardAccount = new OrgJobBoardAccount();
            boolean isNew = true;
            if (orgJobBoardAccountDTO.getId() != 0) {
                orgJobBoardAccount = session.get(OrgJobBoardAccount.class, orgJobBoardAccountDTO.getId());
                isNew = false;
                //created...
            } else {
                //updated...
            }
            orgJobBoardAccount.setOrgJobBoard(session.get(OrgJobBoard.class, orgJobBoardAccountDTO.getOrgJobBoardId()));
            orgJobBoardAccount.setOrg(session.get(Org.class, orgJobBoardAccountDTO.getOrgId()));
            orgJobBoardAccount.setActive(isNew ? true : orgJobBoardAccountDTO.isActive());
            orgJobBoardAccount.setAccessToAll(orgJobBoardAccountDTO.isAccessToAll());
            orgJobBoardAccount.setName(orgJobBoardAccountDTO.getName());
            orgJobBoardAccount.setUsername(orgJobBoardAccountDTO.getUsername());
            orgJobBoardAccount.setPassword(new PasswordSecurity().encrypt(orgJobBoardAccountDTO.getPassword()));
            orgJobBoardAccount.setApiKey(orgJobBoardAccountDTO.getApiKey());
            orgJobBoardAccount.setAccessToken(orgJobBoardAccountDTO.getAccessToken());
            orgJobBoardAccount.setAccountId(orgJobBoardAccountDTO.getAccountId());
            session.saveOrUpdate(orgJobBoardAccount);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional
    public List<OrgJobBoardAccountUserDTO> getOrgJobBoardAccountUserList(long jobBoardAccountId) {
        List<OrgJobBoardAccountUserDTO> result = new ArrayList<>();
        try {
            Session session = this.sessionFactory.getCurrentSession();
            OrgJobBoardAccount jobBoard = session.get(OrgJobBoardAccount.class, jobBoardAccountId);
            List<OrgJobBoardAccountUser> list = jobBoard.getOrgJobBoardAccountUserList();
            for (OrgJobBoardAccountUser oa : list) {
                OrgJobBoardAccountUserDTO ou = new OrgJobBoardAccountUserDTO();
                ou.setId(oa.getId());
                ou.setOrgJobBoardAccountId(oa.getOrgJobBoardAccount().getId());
                ou.setTeamId(oa.getTeam() == null ? 0 : oa.getTeam().getId());
                ou.setUserId(oa.getUser() == null ? 0 : oa.getUser().getId());
                result.add(ou);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int updateJobBoardAccountUserInfo(long jobBoardAccountId, boolean allFlag, List<Long> userList, List<Long> teamList) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            OrgJobBoardAccount jobBoard = session.get(OrgJobBoardAccount.class, jobBoardAccountId);
            List<OrgJobBoardAccountUser> list = jobBoard.getOrgJobBoardAccountUserList();
            if (list != null) {
                for (OrgJobBoardAccountUser oa : list) {
                    session.delete(oa);
                }
            }
            if (allFlag) {
                jobBoard.setAccessToAll(true);
                jobBoard.setOrgJobBoardAccountUserList(null);
            } else if ((userList == null || userList.isEmpty()) && (teamList == null || teamList.isEmpty())) {
                jobBoard.setAccessToAll(false);
                jobBoard.setOrgJobBoardAccountUserList(null);
            } else if ((userList != null && !userList.isEmpty()) || (teamList != null && !teamList.isEmpty())) {
                List<OrgJobBoardAccountUser> list1 = new ArrayList<>();
                if(userList != null && !userList.isEmpty()) {
                    for (Long l : userList) {
                        OrgJobBoardAccountUser ou = new OrgJobBoardAccountUser();
                        ou.setOrgJobBoardAccount(jobBoard);
                        ou.setUser((User) session.get(User.class, l));
                        session.save(ou);
                    }
                }
                if(teamList != null && !teamList.isEmpty()) {
                    for (Long l : teamList) {
                        OrgJobBoardAccountUser ou = new OrgJobBoardAccountUser();
                        ou.setOrgJobBoardAccount(jobBoard);
                        ou.setTeam((Team) session.get(Team.class, l));
                        session.save(ou);
                    }
                }
                jobBoard.setAccessToAll(false);
                jobBoard.setOrgJobBoardAccountUserList(list1);
            }
//            jobBoard.setActive(!jobBoard.isActive());
            //updated...        
            session.saveOrUpdate(jobBoard);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int changeJobBoardAccountStatus(long orgJobBoardAccountId) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            OrgJobBoardAccount jobBoard = session.get(OrgJobBoardAccount.class, orgJobBoardAccountId);
            jobBoard.setActive(!jobBoard.isActive());
            //updated...        
            session.saveOrUpdate(jobBoard);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
