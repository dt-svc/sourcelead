package com.bis.sourcelead.dto;

import java.io.Serializable;

/**
 *
 * @author Raja Gopal Reddy P
 */
public class JobBoardDTO implements Serializable {
    
    private int id;
    private String name;
    private String logoUrl;
    private String postUrl;
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
