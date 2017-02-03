package com.bis.sourcelead.quickbooks;

public class MetaData {
	
	private String CreateTime;

    private String LastUpdatedTime;

    public String getCreateTime ()
    {
        return CreateTime;
    }

    public void setCreateTime (String CreateTime)
    {
        this.CreateTime = CreateTime;
    }

    public String getLastUpdatedTime ()
    {
        return LastUpdatedTime;
    }

    public void setLastUpdatedTime (String LastUpdatedTime)
    {
        this.LastUpdatedTime = LastUpdatedTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CreateTime = "+CreateTime+", LastUpdatedTime = "+LastUpdatedTime+"]";
    }

}
