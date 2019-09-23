package com.bis.sourcelead.couchbase;

import java.io.Serializable;
import java.util.HashMap;

public class BulkImportFailureCacheDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	HashMap<String, String> errorMsg;
	
	String documentId;
	

	public HashMap<String, String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(HashMap<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

		
}
