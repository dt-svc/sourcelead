package com.bis.sourcelead.quickbooks;

public class QueryResponse
{

    private Invoice[] Invoice;
    
    private Item[] Item;
    
    public Invoice[] getInvoice ()
    {
        return Invoice;
    }

    public void setInvoice (Invoice[] Invoice)
    {
        this.Invoice = Invoice;
    }

    public Item[] getItem ()
    {
        return Item;
    }

    public void setItem (Item[] Item)
    {
        this.Item = Item;
    }

}
			