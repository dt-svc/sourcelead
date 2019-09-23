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
@Table(name = "ORG_JOB_BOARD")
public class OrgJobBoard extends Record implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @JoinColumn(name = "JOB_BOARD_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private JobBoard jobBoard;
    
    @JoinColumn(name = "ORG_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Org org;
    
    @Column(name = "ACTIVE")
    private boolean active;

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
}
