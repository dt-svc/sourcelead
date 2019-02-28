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
@Table(name = "JOB_BOARD_DATA")
public class JobBoardData implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @JoinColumn(name = "JOB_BOARD_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private JobBoard jobBoard;
    
    @Column(name = "JOB_BOARD_FIELD")
    private String jobBoardField;
    
    @Column(name = "JOB_BOARD_VALUE")
    private String jobBoardValue;
    
    @Column(name = "JOB_BOARD_ALIAS")
    private String jobBoardAlias;
    
    @Column(name = "SL_FIELD_LABEL")
    private String slFieldLabel;
    
    @Column(name = "SL_FIELD_VALUE")
    private String slFieldValue;
    
    @Column(name = "SL_FIELD_ALIAS")
    private String slFieldAlias;

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
     * @return the jobBoardField
     */
    public String getJobBoardField() {
        return jobBoardField;
    }

    /**
     * @param jobBoardField the jobBoardField to set
     */
    public void setJobBoardField(String jobBoardField) {
        this.jobBoardField = jobBoardField;
    }

    /**
     * @return the jobBoardValue
     */
    public String getJobBoardValue() {
        return jobBoardValue;
    }

    /**
     * @param jobBoardValue the jobBoardValue to set
     */
    public void setJobBoardValue(String jobBoardValue) {
        this.jobBoardValue = jobBoardValue;
    }

    /**
     * @return the jobBoardAlias
     */
    public String getJobBoardAlias() {
        return jobBoardAlias;
    }

    /**
     * @param jobBoardAlias the jobBoardAlias to set
     */
    public void setJobBoardAlias(String jobBoardAlias) {
        this.jobBoardAlias = jobBoardAlias;
    }

    /**
     * @return the slFieldLabel
     */
    public String getSlFieldLabel() {
        return slFieldLabel;
    }

    /**
     * @param slFieldLabel the slFieldLabel to set
     */
    public void setSlFieldLabel(String slFieldLabel) {
        this.slFieldLabel = slFieldLabel;
    }

    /**
     * @return the slFiledValue
     */
    public String getSlFiledValue() {
        return slFieldValue;
    }

    /**
     * @param slFiledValue the slFiledValue to set
     */
    public void setSlFiledValue(String slFiledValue) {
        this.slFieldValue = slFiledValue;
    }

    /**
     * @return the slFieldAlias
     */
    public String getSlFieldAlias() {
        return slFieldAlias;
    }

    /**
     * @param slFieldAlias the slFieldAlias to set
     */
    public void setSlFieldAlias(String slFieldAlias) {
        this.slFieldAlias = slFieldAlias;
    }

}
