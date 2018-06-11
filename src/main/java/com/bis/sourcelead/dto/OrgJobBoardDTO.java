package com.bis.sourcelead.dto;

import java.io.Serializable;

/**
 *
 * @author Raja Gopal Reddy P
 */
public class OrgJobBoardDTO implements Serializable {
    
    private long id;
    private int jobBoardId;
    private long orgId;
    private boolean active;
    private String name;
    private String logoUrl;

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
     * @return the jobBoardId
     */
    public int getJobBoardId() {
        return jobBoardId;
    }

    /**
     * @param jobBoardId the jobBoardId to set
     */
    public void setJobBoardId(int jobBoardId) {
        this.jobBoardId = jobBoardId;
    }

    /**
     * @return the orgId
     */
    public long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the logoUrl
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @param logoUrl the logoUrl to set
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
