package com.bis.sourcelead.dto;

import java.io.Serializable;

/**
 *
 * @author Raja Gopal Reddy P
 */
public class OrgJobBoardAccountUserDTO implements Serializable {
    
    private long id;
    private long orgJobBoardAccountId;
    private long userId;
    private long teamId;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the orgJobBoardAccountId
     */
    public long getOrgJobBoardAccountId() {
        return orgJobBoardAccountId;
    }

    /**
     * @param orgJobBoardAccountId the orgJobBoardAccountId to set
     */
    public void setOrgJobBoardAccountId(long orgJobBoardAccountId) {
        this.orgJobBoardAccountId = orgJobBoardAccountId;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the teamId
     */
    public long getTeamId() {
        return teamId;
    }

    /**
     * @param teamId the teamId to set
     */
    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }


}
