package com.bis.sourcelead.quickbooks;

public class MyPojo
{
    private String time;

    private QueryResponse QueryResponse;

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public QueryResponse getQueryResponse ()
    {
        return QueryResponse;
    }

    public void setQueryResponse (QueryResponse QueryResponse)
    {
        this.QueryResponse = QueryResponse;
    }


	@Override
    public String toString()
    {
        return "ClassPojo [time = "+time+", QueryResponse = "+QueryResponse+"]";
    }
}