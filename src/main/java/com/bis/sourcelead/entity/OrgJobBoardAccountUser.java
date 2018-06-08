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
@Table(name = "ORG_JOB_BOARD_ACCOUNT_USER")
public class OrgJobBoardAccountUser extends Record implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @JoinColumn(name = "ORG_JOB_BOARD_ACCOUNT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private OrgJobBoardAccount orgJobBoardAccount;
    
    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @JoinColumn(name = "TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

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
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }

}
