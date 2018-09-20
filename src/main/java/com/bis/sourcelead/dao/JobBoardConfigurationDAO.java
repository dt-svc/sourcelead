package com.bis.sourcelead.dao;

import com.bis.sourcelead.dto.OrgJobBoardAccountDTO;
import com.bis.sourcelead.dto.OrgJobBoardAccountUserDTO;
import com.bis.sourcelead.dto.OrgJobBoardDTO;
import com.bis.sourcelead.entity.JobBoardData;
import java.util.List;

/**
 *
 * @author Raja Gopal Reddy P
 */
public interface JobBoardConfigurationDAO {
    
    public List<OrgJobBoardDTO> getOrgJobBoardList(long orgId, boolean onlyEnabled);
    
    public long saveOrgJobBoard(OrgJobBoardDTO orgJobBoardDTO);
    
    public int changeJobBoardStatus(long orgJobBoardId);
    
    public List<OrgJobBoardAccountDTO> getOrgJobBoardAccountList(long orgId, long jobBoardId, String search, long userId, long orgLocationId);
    
    public int saveOrgJobBoardAccount(OrgJobBoardAccountDTO orgJobBoardAccountDTO);
    
    public List<OrgJobBoardAccountUserDTO> getOrgJobBoardAccountUserList(long jobBoardAccountId);
    
    public int updateJobBoardAccountUserInfo(long jobBoardAccountId, boolean allFlag, List<Long> userList, List<Long> teamList);
   
    public int changeJobBoardAccountStatus(long jobBoardAccountId);
    
    public JobBoardData getJobBoardDataList(int jobBoardId, String jobBoardField, String fieldLabel, String fieldValue, String fieldAlias);
}
