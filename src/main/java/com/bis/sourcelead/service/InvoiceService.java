package com.bis.sourcelead.service;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.beans.BillingReportDTO;
import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.InvoiceTimelineDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceAmount;
import com.bis.sourcelead.entity.InvoiceTimelineHistory;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;

public interface InvoiceService {
	

	ResponseList<Invoice> invoiceListByOrgLocationId(OrgLocations orgLocations,Date startDateUTC, Date endDateUTC,InvoiceDTO invoiceDTO) throws Exception;

	ResponseList<Invoice> invoiceListByOrgLocationIdAndRelationId(InvoiceDTO invoiceDTO,OrgLocations orgLocations,Long orgRelationId) throws Exception;

	Invoice getInvoicetById(Long invoiceId) throws Exception;
	
	public Boolean getInvoiceByInvoiceId(String invoiceId, Long orgLocationsId, String invoiceType) throws Exception;
	
	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType)throws Exception;

	Invoice saveInvoice(User user, OrgLocations orgLocation, String saveType,String invoiceType, Org org, String currencyCode, InvoiceDTO invoiceDTO)throws Exception;
	
	Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception;
	
	Invoice updateInvoice(Invoice invoice) throws Exception;

	Invoice getInvoicetByParentInvoiceId(Long parentInvoiceId) throws Exception;

	List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId,Long orgLocationId)throws Exception;

	/*void getPdfFileForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org) throws Exception;*/

//	Invoice saveProjectInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation, String saveType, List<MultipartFile> attachedfileList, String invoiceType)throws Exception;


	void updateSubmissionApproverHiearachy(Invoice invoice,String invoiceType)throws Exception;

	Map<String, String> sendInvoiceMail(User user, OrgLocations orglocations, Invoice invoice,String invoiceType,List<TimesheetSubmissionsDocuments> docusList) throws Exception;

	CurrencyConversionResponse getExchangeCurrency(String loginOrgCurrencyCode, OrgLocations orgLocation, Long orgRelationId, String grandTotal)throws Exception;

	File getExportBillingFile(ResponseList<Invoice> invoiceList, String orgName, String exportType,InvoiceAmount totalStagesAmount,String exportStartDate,String exportEndDate, String invoiceType,OrgLocations orgLocations) throws Exception;

	Integer getAutomaticInvoice(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType, User user, String contractType,String globalUser)throws Exception;

	List<Invoice> getInvoiceByOrgLocationId(Long idorgLocationId, Date startDateUTC, Date endDateUTC, String invoiceTYpe)throws Exception;

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocationId, String invoiceType);

	Invoice linkInvoice(Invoice invoice)throws Exception;

	List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId, Long clientLocationId, String invoiceStatus, String paymentStatus, String invoiceType, OrgLocations orgLocations, String dayWisetype, String customStartDate, String customEndDate) throws Exception;

	List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long id, Date startDateUTC, Date endDateUTC)throws Exception;

	List<Invoice> getDueDateCompletedInvoicesList() throws Exception;

	void saveInvocePojo(Invoice invoice)throws Exception;

	BillingReportDTO getBillingReports(String billingReportType, String invoiceType, String startDate, String endDate, OrgLocations orgLocation, User user, Long loginUserRoleId, String loginOrgCurrencyCode)throws Exception;

	void sendInvoiceReminderMail(User user, OrgLocations orgLocation, Invoice invoice, String invoiceType, List<TimesheetSubmissionsDocuments> docusList) throws Exception;

	Invoice getInvoicetByParentId(Long invoiceId)throws Exception;

	boolean deleteInvoice(String invoiceUUID, String reason, String invoiceType, User user)throws Exception;

	Invoice saveExpenseInvoice(InvoiceDTO invoiceDTO, User user, OrgLocations orgLocation, String saveType,String invoiceType, Org org, String currencyCode, String invoiceCategory)throws Exception;

	void updateExpensesForinvoice(Invoice invoice, String invoiceType)throws Exception;

	List<Invoice> getAllBillsAndInvoicesBYProjectIdOrgLocationIdInvoiceStatusStartDateAndEndDate(Long projectId,Long orgLocationId,String invoiceStatus,Date startDate,Date endDate)throws Exception;



	void saveInvoiceTimelineHistory(Invoice invoice, User user, Invoice invoiceObj, String savedInvoiceId, Long invoicePaymentId,  Map<String, String> returnMap);

	List<InvoiceTimelineDTO> getInvoiceTimelineHistoryList(Long id, User user, String searchVal, String orderVal, Date[] datesRange, String[] checkedStatus, String[] namesStatus) throws ParseException, Exception;
	
	public String getInvoiceOrBillGeneratedId(OrgLocations loginOrgLoc,OrgLocations orgLoc,String invoiceType);

}
