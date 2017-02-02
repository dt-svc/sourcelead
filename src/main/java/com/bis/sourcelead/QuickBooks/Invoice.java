package com.bis.sourcelead.QuickBooks;

public class Invoice
{
	
    private BillEmail BillEmail;

    private MetaData MetaData;

    private CustomerRef CustomerRef;
	
    private String Balance;

    private String DueDate;

    private String TotalAmt;
    
    private String Id;
    
    private String DocNumber;
    
    private String TxnDate;

    
    private Line[] Line;

	public BillEmail getBillEmail() {
		return BillEmail;
	}

	public void setBillEmail(BillEmail billEmail) {
		BillEmail = billEmail;
	}

	public MetaData getMetaData() {
		return MetaData;
	}

	public void setMetaData(MetaData metaData) {
		MetaData = metaData;
	}

	public CustomerRef getCustomerRef() {
		return CustomerRef;
	}

	public void setCustomerRef(CustomerRef customerRef) {
		CustomerRef = customerRef;
	}

	public String getBalance() {
		return Balance;
	}

	public void setBalance(String balance) {
		Balance = balance;
	}

	public String getDueDate() {
		return DueDate;
	}

	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}

	public String getTotalAmt() {
		return TotalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		TotalAmt = totalAmt;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDocNumber() {
		return DocNumber;
	}

	public void setDocNumber(String docNumber) {
		DocNumber = docNumber;
	}

	public Line[] getLine() {
		return Line;
	}

	public void setLine(Line[] line) {
		Line = line;
	}

	public String getTxnDate() {
		return TxnDate;
	}

	public void setTxnDate(String txnDate) {
		TxnDate = txnDate;
	}

}
			
