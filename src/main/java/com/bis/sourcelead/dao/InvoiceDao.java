package com.bis.sourcelead.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.InvoiceTimelineHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;

public interface InvoiceDao{

	Invoice saveInvoce(Invoice invoice)throws Exception;
	
	ResponseList<Invoice> invoiceListByOrgLocationId(OrgLocations orgLocations,Date startDateUTC,Date endDateUTC,InvoiceDTO invoiceDTO) throws Exception;
	
	ResponseList<Invoice> invoiceListByOrgLocationIdAndRelationId(InvoiceDTO invoiceDTO,OrgLocations orgLocations, Long orgRelationId) throws Exception;
	
	Invoice getInvoicetById(Long invoiceId) throws Exception;
	
	List<Invoice> getInvoiceListByIdsAndLocationId(List<Long> invoiceIds,Long locationId)throws Exception;
	
	public Boolean getInvoiceByInvoiceId(String invoiceId, Long orgLocationsId,  String invoiceType) throws Exception;

	Invoice getInvoicetByParentInvoiceId(Long parentInvoiceId) throws Exception;
	
	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType)throws Exception;
	
	Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception;
	
	Invoice updateInvoice(Invoice invoice) throws Exception;

	List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception;

	Invoice getInvoicetBySelfJoinInvoiceId(Long parentInvoiceId) throws Exception;

	List<Invoice> getInvoiceByOrgLocationId(Long orgLocationId, Date startDateUTC, Date endDateUTC, String invoiceTYpe)throws Exception;

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId,Long orgLocationId, String invoiceType);
	
	List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId, Long clientLocationId,Date startDateUTC, Date endDateUTC,String invoiceStatus, String paymentStatus, String invoiceType);

	List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long orgLocationId, Date startDateUTC, Date endDateUTC)throws Exception;

	List<Invoice> getDueDateCompletedInvoicesList() throws Exception;

	List<InvoicePayment> getPaymentHistroyByInvoiceId(Long invoiceId)throws Exception;

	Invoice getInvoicetByParentId(Long invoiceId)throws Exception;

	Invoice getParentInvoiceByInvoiceId(Long invoiceId)throws Exception;

	List<Invoice> getAllBillsAndInvoicesBYProjectIdOrgLocationIdInvoiceStatusStartDateAndEndDate(Long projectId, Long orgLocationId, String invoiceStatus, Date startDate, Date endDate);

	void saveInvoiceTimelineHistory(InvoiceTimelineHistory timelineHistory);

	List<InvoiceTimelineHistory> getInvoiceTimelineHistoryList(Long id, String searchVal, String orderVal, Date[] datesRange, String[] checkedStatus) throws ParseException;

	public Long checkInvoiceOrBillIdNumberInDatebase(Long orgLocationId,Integer generatedIdcount,String invoiceType);

}
