package com.bis.sourcelead.quickbooks;

public class BillEmail {
	
	private String Address;

    public String getAddress ()
    {
        return Address;
    }

    public void setAddress (String Address)
    {
        this.Address = Address;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Address = "+Address+"]";
    }
}
