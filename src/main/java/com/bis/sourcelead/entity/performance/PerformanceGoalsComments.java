package com.bis.sourcelead.entity.performance;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "PERFORMANCE_GOALS_COMMENTS")
public class PerformanceGoalsComments implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GOAL_ID" )        
    private PerformanceGoals goalId;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_USER_ID", nullable = false)
   	private User userId;
    
	@Column(name = "CREATE_USER_NAME")
   	private String createdUserName;
	
	@Column(name = "COMMENT")
	@Type(type="text")
	private String comment;
	 
    @Column(name = "CREATED_DATE")
    private Date createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PerformanceGoals getGoalId() {
		return goalId;
	}

	public void setGoalId(PerformanceGoals goalId) {
		this.goalId = goalId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
    
    
    

}
