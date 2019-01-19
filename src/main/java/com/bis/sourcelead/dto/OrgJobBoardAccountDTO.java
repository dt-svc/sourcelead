package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;
import java.io.Serializable;

/**
 *
 * @author Raja Gopal Reddy P
 */
public class OrgJobBoardAccountDTO implements Serializable {
    
    private long id;
    private long orgJobBoardId;
    @Required(desc = "Job Board")
    private int jobBoardId;
    private String jobBoardName;
    private long orgId;
    private boolean active;
    private boolean accessToAll;
    @Required(desc = "Name")
    private String name;
    @Required(desc = "Username", dependsOn = "jobBoardId", parentValue = {"2"})
    private String username;
    @Required(desc = "Password", dependsOn = "jobBoardId", parentValue = {"2"})
    private String password;
    private String apiKey;
    private String accessToken;
    private String accountId;
    private String assignCount;

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
     * @return the orgJobBoardId
     */
    public long getOrgJobBoardId() {
        return orgJobBoardId;
    }

    /**
     * @param orgJobBoardId the orgJobBoardId to set
     */
    public void setOrgJobBoardId(long orgJobBoardId) {
        this.orgJobBoardId = orgJobBoardId;
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
     * @return the accessToAll
     */
    public boolean isAccessToAll() {
        return accessToAll;
    }

    /**
     * @param accessToAll the accessToAll to set
     */
    public void setAccessToAll(boolean accessToAll) {
        this.accessToAll = accessToAll;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
     * @return the jobBoardName
     */
    public String getJobBoardName() {
        return jobBoardName;
    }

    /**
     * @param jobBoardName the jobBoardName to set
     */
    public void setJobBoardName(String jobBoardName) {
        this.jobBoardName = jobBoardName;
    }

    /**
     * @return the assignCount
     */
    public String getAssignCount() {
        return assignCount;
    }

    /**
     * @param assignCount the assignCount to set
     */
    public void setAssignCount(String assignCount) {
        this.assignCount = assignCount;
    }

}
