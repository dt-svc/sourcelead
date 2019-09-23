package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Raja Gopal Reddy P
 */
@Entity
@Table(name = "ORG_JOB_BOARD_ACCOUNT")
public class OrgJobBoardAccount extends Record implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @JoinColumn(name = "ORG_JOB_BOARD_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private OrgJobBoard orgJobBoard;
    
    @JoinColumn(name = "ORG_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Org org;
    
    @Column(name = "ACTIVE")
    private boolean active;
    
    @Column(name = "ACCESS_TO_ALL")
    private boolean accessToAll;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "API_KEY")
    private String apiKey;
    
    @Column(name = "ACCESS_TOKEN")
    private String accessToken;
    
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    
    @OneToMany(mappedBy = "orgJobBoardAccount")
    private List<OrgJobBoardAccountUser> orgJobBoardAccountUserList;

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
     * @return the orgJobBoard
     */
    public OrgJobBoard getOrgJobBoard() {
        return orgJobBoard;
    }

    /**
     * @param orgJobBoard the orgJobBoard to set
     */
    public void setOrgJobBoard(OrgJobBoard orgJobBoard) {
        this.orgJobBoard = orgJobBoard;
    }

    /**
     * @return the org
     */
    public Org getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(Org org) {
        this.org = org;
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
     * @return the orgJobBoardAccountUserList
     */
    public List<OrgJobBoardAccountUser> getOrgJobBoardAccountUserList() {
        return orgJobBoardAccountUserList;
    }

    /**
     * @param orgJobBoardAccountUserList the orgJobBoardAccountUserList to set
     */
    public void setOrgJobBoardAccountUserList(List<OrgJobBoardAccountUser> orgJobBoardAccountUserList) {
        this.orgJobBoardAccountUserList = orgJobBoardAccountUserList;
    }
}
