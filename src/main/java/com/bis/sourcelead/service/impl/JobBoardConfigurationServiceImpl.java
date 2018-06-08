package com.bis.sourcelead.service.impl;

import com.bis.sourcelead.dao.JobBoardConfigurationDAO;
import com.bis.sourcelead.dto.OrgJobBoardAccountDTO;
import com.bis.sourcelead.dto.OrgJobBoardAccountUserDTO;
import com.bis.sourcelead.dto.OrgJobBoardDTO;
import com.bis.sourcelead.service.JobBoardConfigurationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Raja Gopal Reddy P
 */
@Service
public class JobBoardConfigurationServiceImpl implements JobBoardConfigurationService {

    @Autowired
    private JobBoardConfigurationDAO jobBoardConfigurationDAO;

    @Override
    public List<OrgJobBoardDTO> getOrgJobBoardList(long orgId, boolean onlyEnabled) {
        return jobBoardConfigurationDAO.getOrgJobBoardList(orgId, onlyEnabled);
    }

    @Override
    public long saveOrgJobBoard(OrgJobBoardDTO orgJobBoardDTO) {
        return jobBoardConfigurationDAO.saveOrgJobBoard(orgJobBoardDTO);
    }

    @Override
    public int changeJobBoardStatus(long jobBoardId) {
        return jobBoardConfigurationDAO.changeJobBoardStatus(jobBoardId);
    }

    @Override
    public List<OrgJobBoardAccountDTO> getOrgJobBoardAccountList(long orgId, long jobBoardId, String search) {
        return jobBoardConfigurationDAO.getOrgJobBoardAccountList(orgId, jobBoardId, search);
    }

    @Override
    public int saveOrgJobBoardAccount(OrgJobBoardAccountDTO orgJobBoardAccountDTO) {
        return jobBoardConfigurationDAO.saveOrgJobBoardAccount(orgJobBoardAccountDTO);
    }

    @Override
    public List<OrgJobBoardAccountUserDTO> getOrgJobBoardAccountUserList(long jobBoardAccountId) {
        return jobBoardConfigurationDAO.getOrgJobBoardAccountUserList(jobBoardAccountId);
    }

    @Override
    public int updateJobBoardAccountUserInfo(long jobBoardAccountId, boolean allFlag, List<Long> userList, List<Long> teamList) {
        return jobBoardConfigurationDAO.updateJobBoardAccountUserInfo(jobBoardAccountId, allFlag, userList, teamList);
    }

    @Override
    public int changeJobBoardAccountStatus(long orgJobBoardAccountId) {
        return jobBoardConfigurationDAO.changeJobBoardAccountStatus(orgJobBoardAccountId);
    }

}
