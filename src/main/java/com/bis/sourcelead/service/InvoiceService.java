package com.bis.sourcelead.service;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.beans.BillingReportDTO;
import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.dto.CreditDebitNoteDTO;
import com.bis.sourcelead.dto.CreditDebitNoteListDTO;
import com.bis.sourcelead.dto.CustomInvoiceTemplateDTO;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.InvoiceTimelineDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.CreditDebitLineItems;
import com.bis.sourcelead.entity.CreditDebitNote;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceAmount;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;

public interface InvoiceService {
	

	ResponseList<Invoice> invoiceListByOrgLocationId(OrgLocations orgLocations,Date startDateUTC, Date endDateUTC,InvoiceDTO invoiceDTO) throws Exception;

	ResponseList<CreditDebitNoteListDTO> creditDebtListByOrgLocationId(OrgLocations orgLocations,Date startDateUTC, Date endDateUTC,CreditDebitNoteDTO creditDebitNoteDTO) throws Exception;

	
	ResponseList<Invoice> invoiceListByOrgLocationIdAndRelationId(InvoiceDTO invoiceDTO,OrgLocations orgLocations,Long orgRelationId) throws Exception;

	Invoice getInvoicetById(Long invoiceId) throws Exception;
	
	public Boolean getInvoiceByInvoiceId(String invoiceId, Long orgLocationsId, String invoiceType) throws Exception;
	
	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType)throws Exception;

	Invoice saveInvoice(User user, OrgLocations orgLocation, String saveType,String invoiceType, Org org, String currencyCode, InvoiceDTO invoiceDTO, String defaultFont)throws Exception;
	
	CreditDebitNote saveCreditDebitNote(User user, OrgLocations orgLocation, String saveType,String noteType, CreditDebitNoteDTO creditDebitNoteDTO,String currencyCode)throws Exception;
	
	CreditDebitNote saveAcceptedCreditDebitNote(User user, OrgLocations orgLocation,String noteType, CreditDebitNote creditDebitNote)throws Exception;

	CreditDebitNoteListDTO editCreditDebitNote(Long noteId,OrgLocations orgLocation,String noteType)throws Exception;
	
	Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception;
	
	Invoice updateInvoice(Invoice invoice) throws Exception;

	Invoice getInvoicetByParentInvoiceId(Long parentInvoiceId) throws Exception;

	List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId,Long orgLocationId)throws Exception;
	
	CreditDebitNote getCreditDebitNoteByUUID(String noteUUID)throws Exception;
	
	Response<CreditDebitNote> getCreditDebitNoteByIdAndOrglocationId(Long id,Long orgLocationId) throws Exception;

	/*void getPdfFileForInvoice(Invoice invoice, OrgLocations orgLocation, User user, Org org) throws Exception;*/

//	Invoice saveProjectInvoice(JSONObject jsonObj, User user, OrgLocations orgLocation, String saveType, List<MultipartFile> attachedfileList, String invoiceType)throws Exception;


	void updateSubmissionApproverHiearachy(Invoice invoice,String invoiceType)throws Exception;

	Map<String, String> sendInvoiceMail(User user, OrgLocations orglocations, Invoice invoice,String invoiceType,List<TimesheetSubmissionsDocuments> docusList) throws Exception;

	CurrencyConversionResponse getExchangeCurrency(String loginOrgCurrencyCode, OrgLocations orgLocation, Long orgRelationId, String grandTotal)throws Exception;

	File getExportBillingFile(ResponseList<Invoice> invoiceList, String orgName, String exportType,InvoiceAmount totalStagesAmount,String exportStartDate,String exportEndDate, String invoiceType,OrgLocations orgLocations) throws Exception;

	Integer getAutomaticInvoice(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType, User user, String contractType,String globalUser)throws Exception;

	Integer getAutomaticInvoiceForJobClosure(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType, User user,String contractType,String globalUser)throws Exception;

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
	
	boolean deleteCreditDebitNote(String noteUUID,String noteType,User user,OrgLocations orgLocations)throws Exception;
	
	boolean reactiveCreditDebitNote(String noteUUID,String noteType,User user,OrgLocations orgLocations)throws Exception;

	Invoice saveExpenseInvoice(InvoiceDTO invoiceDTO, User user, OrgLocations orgLocation, String saveType,String invoiceType, Org org, String currencyCode, String invoiceCategory, String defaultFont)throws Exception;

	void updateExpensesForinvoice(Invoice invoice, String invoiceType)throws Exception;

	List<Invoice> getAllBillsAndInvoicesBYProjectIdOrgLocationIdInvoiceStatusStartDateAndEndDate(Long projectId,Long orgLocationId,String invoiceStatus,Date startDate,Date endDate)throws Exception;



	void saveInvoiceTimelineHistory(Invoice invoice, User user, Invoice invoiceObj, String savedInvoiceId, Long invoicePaymentId,  Map<String, String> returnMap);

	List<InvoiceTimelineDTO> getInvoiceTimelineHistoryList(Long id, User user, String searchVal, String orderVal, Date[] datesRange, String[] checkedStatus, String[] namesStatus) throws ParseException, Exception;
	
	public String getInvoiceOrBillGeneratedId(OrgLocations loginOrgLoc,OrgLocations orgLoc,String invoiceType);
	
	CreditDebitLineItems getCreditDebitLineItemById(Long id);
	
	void deleteCreditDebitLineItem(CreditDebitLineItems creditDebitLineItems);
	
	Integer generateInvoiceJobClosureFromDashboard(OrgLocations orgLocation, String loginOrgCurrencyCode, String invoiceType, User user,String contractType,Long orgRlelationId,Long submissionId,Long candidateId)throws Exception;


	Response<String> saveCustomInvoiceTemplate(CustomInvoiceTemplateDTO customInvoiceTemplateDTO, OrgRelations orgRelations, OrgLocations orgLocations, User loggedInUser, OrgLocations loggedInOrgLocations);

	public ResponseList<CustomInvoiceTemplateDTO> getCustomInvoiceTemplateListByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId, SearchDTO searchDTO, OrgLocations loggedInOrgLocations);
	
	public CustomInvoiceTemplateDTO getCustomInvoiceTemplateListByUUIDAndOrgRelationIdAndOrgLocationId(String uuid, Long orgRelationId, Long orgLocationId, OrgLocations loggedInOrgLocations);

	public boolean changeCustomInvoiceTemplateStatus(Long templateId, int status, User user, OrgLocations orgLocations);
	
	public boolean deleteCustomInvoiceTemplate(Long templateId);
	
	Map<String, String> sendInvoiceMailToOthers(User user, OrgLocations orglocations, Invoice invoice,String invoiceType,List<TimesheetSubmissionsDocuments> docusList,String otherEmails) throws Exception;

	void sendInvoiceReminderMailToNotifyOthers(User user, OrgLocations orgLocation, Invoice invoice, String invoiceType, List<TimesheetSubmissionsDocuments> docusList,String otherEmails) throws Exception;
}
