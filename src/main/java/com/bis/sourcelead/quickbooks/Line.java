package com.bis.sourcelead.quickbooks;

public class Line {

	private String Amount;

    private String Description;

    private String DetailType;

    private SalesItemLineDetail SalesItemLineDetail;

    private String Id;

    private String LineNum;

    public String getAmount ()
    {
        return Amount;
    }

    public void setAmount (String Amount)
    {
        this.Amount = Amount;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getDetailType ()
    {
        return DetailType;
    }

    public void setDetailType (String DetailType)
    {
        this.DetailType = DetailType;
    }

    public SalesItemLineDetail getSalesItemLineDetail ()
    {
        return SalesItemLineDetail;
    }

    public void setSalesItemLineDetail (SalesItemLineDetail SalesItemLineDetail)
    {
        this.SalesItemLineDetail = SalesItemLineDetail;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getLineNum ()
    {
        return LineNum;
    }

    public void setLineNum (String LineNum)
    {
        this.LineNum = LineNum;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Amount = "+Amount+", Description = "+Description+", DetailType = "+DetailType+", SalesItemLineDetail = "+SalesItemLineDetail+", Id = "+Id+", LineNum = "+LineNum+"]";
    }
}
