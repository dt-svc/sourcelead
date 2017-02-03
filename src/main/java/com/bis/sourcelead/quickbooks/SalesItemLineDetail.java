package com.bis.sourcelead.quickbooks;

public class SalesItemLineDetail {

	private String Qty;

    private String UnitPrice;
    
    private ItemRef ItemRef;

    public String getQty ()
    {
        return Qty;
    }

    public void setQty (String Qty)
    {
        this.Qty = Qty;
    }

    public String getUnitPrice ()
    {
        return UnitPrice;
    }

    public void setUnitPrice (String UnitPrice)
    {
        this.UnitPrice = UnitPrice;
    }

	public ItemRef getItemRef() {
		return ItemRef;
	}

	public void setItemRef(ItemRef itemRef) {
		ItemRef = itemRef;
	}

	
}
