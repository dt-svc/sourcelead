package com.bis.sourcelead.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Raja Gopal Reddy P
 */
@Entity
@Table(name = "REQUIREMENT_JOB_BOARD_INFO")
public class RequirementJobBoardInfo extends Record implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @JoinColumn(name = "JOB_BOARD_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private JobBoard jobBoard;
    
    @JoinColumn(name = "ORG_JOB_BOARD_ACCOUNT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private OrgJobBoardAccount orgJobBoardAccount;
    
    @JoinColumn(name = "REQUIREMENT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Requirement requirement;
    
    @Column(name = "JOB_ID")
    private String jobId;
    
    @Column(name = "JOB_BOARD_URL")
    private String jobUrl;

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
     * @return the jobBoard
     */
    public JobBoard getJobBoard() {
        return jobBoard;
    }

    /**
     * @param jobBoard the jobBoard to set
     */
    public void setJobBoard(JobBoard jobBoard) {
        this.jobBoard = jobBoard;
    }

    /**
     * @return the orgJobBoardAccount
     */
    public OrgJobBoardAccount getOrgJobBoardAccount() {
        return orgJobBoardAccount;
    }

    /**
     * @param orgJobBoardAccount the orgJobBoardAccount to set
     */
    public void setOrgJobBoardAccount(OrgJobBoardAccount orgJobBoardAccount) {
        this.orgJobBoardAccount = orgJobBoardAccount;
    }

    /**
     * @return the requirement
     */
    public Requirement getRequirement() {
        return requirement;
    }

    /**
     * @param requirement the requirement to set
     */
    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    /**
     * @return the jobId
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the jobUrl
     */
    public String getJobUrl() {
        return jobUrl;
    }

    /**
     * @param jobUrl the jobUrl to set
     */
    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    
}
