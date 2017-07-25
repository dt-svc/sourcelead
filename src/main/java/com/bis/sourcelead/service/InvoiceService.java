package com.bis.sourcelead.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.beans.BillingReportDTO;
import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceAmount;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;

public interface InvoiceService {
	

	List<Invoice> invoiceListByOrgLocationId(Long orgLocationId, String invoiceStatus, String paymentStatus,Date startDateUTC, Date endDateUTC,String invoiceType) throws Exception;

	Invoice getInvoicetById(Long invoiceId) throws Exception;
	
	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType)throws Exception;

	Invoice saveInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation, String saveType, List<MultipartFile> attachedfileList,String invoiceType, Org org, String currencyCode)throws Exception;
	
	Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception;
	
	Invoice updateInvoice(Invoice invoice) throws Exception;

	Invoice getInvoicetByParentInvoiceId(Long parentInvoiceId) throws Exception;

	List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId,Long orgLocationId)throws Exception;

	/*void getPdfFileForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org) throws Exception;*/

	Invoice saveProjectInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation, String saveType, List<MultipartFile> attachedfileList, String invoiceType)throws Exception;


	void updateSubmissionApproverHiearachy(List<String> submissionApproverHierarchyIds, String invoiceFrequency,String invoiceType)throws Exception;

	void sendInvoiceMail(User user, OrgLocations orglocations, Invoice invoice,String invoiceType,List<TimesheetSubmissionsDocuments> docusList) throws Exception;

	CurrencyConversionResponse getExchangeCurrency(String loginOrgCurrencyCode, OrgLocations orgLocation, Long orgRelationId, String grandTotal)throws Exception;

	File getExportBillingFile(List<Invoice> invoiceList, String orgName, String exportType,InvoiceAmount totalStagesAmount,String exportStartDate,String exportEndDate, String invoiceType) throws Exception;

	Integer getAutomaticInvoice(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType, User user, String contractType)throws Exception;

	List<Invoice> getInvoiceByOrgLocationId(Long idorgLocationId, Date startDateUTC, Date endDateUTC, String invoiceTYpe)throws Exception;

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocationId, String invoiceType);

	Invoice linkInvoice(Invoice invoice)throws Exception;

	List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId, Long clientLocationId, String invoiceStatus, String paymentStatus, String invoiceType, OrgLocations orgLocations, String dayWisetype, String customStartDate, String customEndDate) throws Exception;

	List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long id, Date startDateUTC, Date endDateUTC)throws Exception;

	List<Invoice> getDueDateCompletedInvoicesList() throws Exception;

	void saveInvocePojo(Invoice invoice)throws Exception;

	BillingReportDTO getBillingReports(String billingReportType, String invoiceType, String startDate, String endDate, OrgLocations orgLocation, User user, Long loginUserRoleId)throws Exception;

	void sendInvoiceReminderMail(User user, OrgLocations orgLocation, Invoice invoice, String invoiceType, List<TimesheetSubmissionsDocuments> docusList) throws Exception;

	Invoice getInvoicetByParentId(Long invoiceId)throws Exception;
	

}
