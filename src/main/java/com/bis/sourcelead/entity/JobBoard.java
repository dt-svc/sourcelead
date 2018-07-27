package com.bis.sourcelead.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Raja Gopal Reddy P
 */
@Entity
@Table(name = "JOB_BOARD")
public class JobBoard extends Record implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LOGO_URL")
    private String logoUrl;
    @Column(name = "POST_URL")
    private String postUrl;
    @Column(name = "CONTENT_FILE_URL")
    private String contentFileUrl;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * @return the postUrl
     */
    public String getPostUrl() {
        return postUrl;
    }

    /**
     * @param postUrl the postUrl to set
     */
    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    /**
     * @return the contentFileUrl
     */
    public String getContentFileUrl() {
        return contentFileUrl;
    }

    /**
     * @param contentFileUrl the contentFileUrl to set
     */
    public void setContentFileUrl(String contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
    }
    
}
