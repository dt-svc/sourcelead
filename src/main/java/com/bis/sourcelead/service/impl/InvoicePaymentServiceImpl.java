package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dao.InvoicePaymentDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.InvoicePaymentDTO;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.InvoicePaymentService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class InvoicePaymentServiceImpl implements InvoicePaymentService {

	private static final Logger logger = LoggerFactory.getLogger(InvoicePaymentServiceImpl.class);

	@Autowired
	private InvoiceDao invoiceDao;

	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	UserService userService;

	@Autowired
	CandidateService candidateService;

	@Autowired
	ContractsTemplateService contractsTemplateService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	InvoicePaymentDao invoicePaymentDao;

	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	InvoiceService invoiceService;

	@Override
	public InvoicePayment saveInvoicePayment(InvoicePaymentDTO invoicePaymentDTO, User user, OrgLocations orgLocation,
			MultipartFile multiFile, Long invoiceId) throws Exception {

		
		Invoice invoice = invoiceDao.getInvoicetById(invoiceId);
		Invoice parentInvoice = null;
		InvoicePayment invoicePayment = new InvoicePayment();
		/*String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);*/

		if (StringUtils.isNotBlank(invoicePaymentDTO.getPaymentDate())) {
			Date paymentDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),invoicePaymentDTO.getPaymentDate());
			invoicePayment.setPaymentDate(paymentDate);
		}
		if (StringUtils.isNotBlank(invoicePaymentDTO.getPaymentMethod())) {
			invoicePayment.setPaymentMethod(invoicePaymentDTO.getPaymentMethod());
		}
		if (StringUtils.isNotBlank(invoicePaymentDTO.getReferenceNumber())) {
			invoicePayment.setPaymentReferenceNumber(invoicePaymentDTO.getReferenceNumber());

		}

		if (StringUtils.isNotBlank(invoicePaymentDTO.getPaymentAmount())) {
			if (invoicePaymentDTO.getPaymentAmount().equalsIgnoreCase("total")) {
				if (StringUtils.isNotBlank(invoicePaymentDTO.getTotalAmount())) {
					invoicePayment.setPaymentAmount(invoicePaymentDTO.getTotalAmount());
					invoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
					invoice.setDueAmount("00.000");
				}
			}
			if (invoicePaymentDTO.getPaymentAmount().equalsIgnoreCase("custom")) {
				if (StringUtils.isNotBlank(invoicePaymentDTO.getCustomeAmount())) {
					invoicePayment.setPaymentAmount(invoicePaymentDTO.getCustomeAmount());
					BigDecimal totalAmount = new BigDecimal(Double.parseDouble(invoicePaymentDTO.getTotalAmount()));
					BigDecimal customAmount = new BigDecimal(Double.parseDouble(invoicePaymentDTO.getCustomeAmount()));
					BigDecimal dueAmount = totalAmount.subtract(customAmount);
					// -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
					int res = customAmount.compareTo(totalAmount);
					if (totalAmount.equals(customAmount) || res == 1) {
						invoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
					} else if(commonUtil.currentDate().after(invoice.getDueDate())) {
						invoice.setPaymentStatus(Constants.OVER_DUE);
					}else{
						invoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);
					}
					invoice.setDueAmount(String.valueOf(dueAmount.setScale(3, BigDecimal.ROUND_HALF_EVEN)));

				}
			}
			if (Constants.INVOICE_SENT.equalsIgnoreCase(invoice.getInvoiceStatus())) {
				invoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
			}
			invoice.setUpdatedBy(user.getUserCode());
			invoice.setUpdatedDate(commonUtil.currentDate());
			Invoice childInvoice = invoiceDao.saveInvoce(invoice);
			if (null != childInvoice) {
				if (null != childInvoice.getId()) {
					parentInvoice = invoiceDao.getInvoicetBySelfJoinInvoiceId(childInvoice.getId());
					if (parentInvoice == null) {
						parentInvoice = invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
					}
					String paidCurrencyCode = invoice.getCurrencyCode();
					String receiverCurrencyCode = parentInvoice.getCurrencyCode();
					String paidAmount = invoicePayment.getPaymentAmount();
					// float totalAmount = 0;
					// float customAmount = 0;
					BigDecimal receivedAmount = null;
					if (invoicePaymentDTO.getPaymentAmount().equalsIgnoreCase("total")) {
						if (StringUtils.isNotBlank(invoicePaymentDTO.getTotalAmount())) {
							invoicePayment.setPaymentAmount(invoicePaymentDTO.getTotalAmount());
							parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
							parentInvoice.setDueAmount("00.000");
							parentInvoice.setUpdatedBy(user.getUserCode());
							parentInvoice.setUpdatedDate(commonUtil.currentDate());
						}
					}
					if (invoicePaymentDTO.getPaymentAmount().equalsIgnoreCase("custom")) {
						if (StringUtils.isNotBlank(invoicePaymentDTO.getCustomeAmount())) {
							invoicePayment.setPaymentAmount(invoicePaymentDTO.getCustomeAmount());
							if (!paidCurrencyCode.equalsIgnoreCase(receiverCurrencyCode)) {
								float conAmount = conversionUtil.convert(paidCurrencyCode, receiverCurrencyCode,Float.parseFloat(paidAmount));
								BigDecimal paidConversionAmount = new BigDecimal(conAmount);
								BigDecimal receiverTotalAmount = new BigDecimal(Float.parseFloat(parentInvoice.getDueAmount()));
								receivedAmount = receiverTotalAmount.subtract(paidConversionAmount);
//								BigDecimal totalAmount = new BigDecimal(
//										Double.parseDouble(invoicePaymentDTO.getTotalAmount()));
//								BigDecimal customAmount = new BigDecimal(
//										Double.parseDouble(invoicePaymentDTO.getCustomeAmount()));
								int res = paidConversionAmount.compareTo(receiverTotalAmount);
								if ((receiverTotalAmount.equals(paidConversionAmount)) || res == 1) {
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
								}else if(commonUtil.currentDate().after(invoice.getDueDate())) {
									invoice.setPaymentStatus(Constants.OVER_DUE);
								}else{
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);
								}
							} else {
								BigDecimal totalAmount = new BigDecimal(
										Double.parseDouble(invoicePaymentDTO.getTotalAmount()));
								BigDecimal customAmount = new BigDecimal(
										Double.parseDouble(invoicePaymentDTO.getCustomeAmount()));
								receivedAmount = totalAmount.subtract(customAmount);
								int res = customAmount.compareTo(totalAmount);
								if ((totalAmount.equals(customAmount)) || res == 1) {
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
								}else if(commonUtil.currentDate().after(invoice.getDueDate())) {
									invoice.setPaymentStatus(Constants.OVER_DUE);
								}else {
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);
								}
								
							}

							if (receivedAmount != null) {
								parentInvoice.setDueAmount(
										String.valueOf(receivedAmount.setScale(3, BigDecimal.ROUND_HALF_EVEN)));
							} else {
								parentInvoice.setDueAmount("00.000");
							}
						}
					}
					if (Constants.INVOICE_RECEIVED.equalsIgnoreCase(parentInvoice.getInvoiceStatus())) {
						parentInvoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
					}
					parentInvoice.setUpdatedBy(user.getUserCode());
					parentInvoice.setUpdatedDate(commonUtil.currentDate());
					invoiceDao.saveInvoce(parentInvoice);
					
				}

			}
		}

		invoicePayment.setCurrencyCode(invoice.getCurrencyCode());
		if (StringUtils.isNotBlank(invoicePaymentDTO.getPaymentMemo())) {
			invoicePayment.setPaymentComment(invoicePaymentDTO.getPaymentMemo());
		}
		
		if(invoicePaymentDTO.getFileInfo() != null && invoicePaymentDTO.getFileInfo().size() > 0){
			for(FileInfo file : invoicePaymentDTO.getFileInfo()){
				invoicePayment.setFileContentType(file.getContentType());
				invoicePayment.setFileName(file.getFileName());
				invoicePayment.setFileURL(file.getFileURL());
				FileUploadUtil.moveFile(invoicePaymentDTO.getStaticPath(), file, "incoiceDocuments");
			}
		}
		
		/*if (null != multiFile) {
			invoicePayment.setFileContentType(multiFile.getContentType());
			invoicePayment.setFileName(multiFile.getOriginalFilename());
			invoicePayment.setDocFile(multiFile.getBytes());

		}*/
		invoicePayment.setOrgLocation(orgLocation);
		if (invoice.getParentInvoiceId() != null) {
			invoicePayment.setInvoiceId(invoice.getParentInvoiceId());
		} else {
			invoicePayment.setInvoiceId(invoice);
		}

		invoicePayment.setStatus(Constants.RECORD_ACTIVE.toString());
		invoicePayment.setCreatedBy(user.getUserCode());
		invoicePayment.setCreatedDate(commonUtil.currentDate());
		invoicePayment.setUpdatedBy(user.getUserCode());
		invoicePayment.setUpdatedDate(commonUtil.currentDate());
		InvoicePayment invoicePaymentObject =invoicePaymentDao.saveInvoicePayment(invoicePayment); 
		invoiceService.saveInvoiceTimelineHistory(parentInvoice, user, null, null, invoicePaymentObject.getId(), null);
		invoiceService.saveInvoiceTimelineHistory(invoice, user, null, null, invoicePaymentObject.getId(), null);
		return invoicePaymentObject;
	}
	
	
	@Override
	public InvoicePayment saveInvoiceBulkPayment(InvoicePaymentDTO invoicePaymentDTO, User user, OrgLocations orgLocation,Map<Long,String> invoiceIdsMap) throws Exception {

		 List<Invoice> list = invoiceDao.getInvoiceListByIdsAndLocationId(new ArrayList(invoiceIdsMap.keySet()),orgLocation.getId());
		 InvoicePayment invoicePayment = null;
		 for(Invoice invoice : list){
			invoicePayment = new InvoicePayment();
			Invoice parentInvoice = null;
		/*String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);*/
		if (StringUtils.isNotBlank(invoicePaymentDTO.getPaymentDate())) {
			Date paymentDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),invoicePaymentDTO.getPaymentDate());
			invoicePayment.setPaymentDate(paymentDate);
		}
		if (StringUtils.isNotBlank(invoicePaymentDTO.getPaymentMethod())) {
			invoicePayment.setPaymentMethod(invoicePaymentDTO.getPaymentMethod());
		}
		if (StringUtils.isNotBlank(invoicePaymentDTO.getReferenceNumber())) {
			invoicePayment.setPaymentReferenceNumber(invoicePaymentDTO.getReferenceNumber());

		}
		BigDecimal remainAmount =  null;
		if (StringUtils.isNotBlank(invoicePaymentDTO.getPaymentAmount())) {
				if (StringUtils.isNotBlank(invoicePaymentDTO.getTotalAmount())) {
					if(invoice.getDueAmount().equals(invoiceIdsMap.get(invoice.getId()))){
					invoicePayment.setPaymentAmount(invoiceIdsMap.get(invoice.getId()));
					invoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
					invoice.setDueAmount("00.000");
					}else{
						String due = invoice.getDueAmount();
						BigDecimal dueDecimal = (new BigDecimal(due));
						String received = invoiceIdsMap.get(invoice.getId());
						BigDecimal receivedDecimal = (new BigDecimal(received));
						remainAmount = dueDecimal.subtract(receivedDecimal);
						if(commonUtil.currentDate().after(invoice.getDueDate())) {
							invoice.setPaymentStatus(Constants.OVER_DUE);
						}else{
							invoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);	
						}
						remainAmount = remainAmount.setScale(3, BigDecimal.ROUND_UP);
						invoicePayment.setPaymentAmount(invoiceIdsMap.get(invoice.getId()));
						invoice.setDueAmount(remainAmount.toString());
					}
				}
		
			if (Constants.INVOICE_SENT.equalsIgnoreCase(invoice.getInvoiceStatus())) {
				invoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
			}
			invoice.setUpdatedBy(user.getUserCode());
			invoice.setUpdatedDate(commonUtil.currentDate());
			Invoice childInvoice = invoiceDao.saveInvoce(invoice);

			
			if (null != childInvoice) {
				if (null != childInvoice.getId()) {
					parentInvoice = invoiceDao.getInvoicetBySelfJoinInvoiceId(childInvoice.getId());
					if (parentInvoice == null) {
						parentInvoice = invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());
					}
					String childCurrencyCode = invoice.getCurrencyCode();
					String parentCurrencyCode = parentInvoice.getCurrencyCode();
					// float totalAmount = 0;
					// float customAmount = 0;
					if(childCurrencyCode.equalsIgnoreCase(parentCurrencyCode)){
						if (StringUtils.isNotBlank(invoicePaymentDTO.getTotalAmount())) {
							if(parentInvoice.getDueAmount().equals(invoiceIdsMap.get(invoice.getId()))){
								// total amount paid
							invoicePayment.setPaymentAmount(invoiceIdsMap.get(invoice.getId()));
							parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
							parentInvoice.setDueAmount("00.000");
							}else{
								// partial amount paid
								String due = parentInvoice.getDueAmount();
								BigDecimal dueDecimal = (new BigDecimal(due));
								String received = invoiceIdsMap.get(invoice.getId());
								BigDecimal receivedDecimal = (new BigDecimal(received));
								remainAmount = dueDecimal.subtract(receivedDecimal);
								remainAmount = remainAmount.setScale(3, BigDecimal.ROUND_UP);
								if(commonUtil.currentDate().after(parentInvoice.getDueDate())) {
									parentInvoice.setPaymentStatus(Constants.OVER_DUE);
								}else{
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);	
								}
								invoicePayment.setPaymentAmount(invoiceIdsMap.get(invoice.getId()));
								parentInvoice.setDueAmount(remainAmount.toString());
							}
						}
					}else{
						// currency conversion code start
						float conAmount = conversionUtil.convert(childCurrencyCode, parentCurrencyCode,1);
						float convertedAmount1 = Float.parseFloat((new DecimalFormat(".###").format(((conAmount * 100.0) / 100.0))));
						// currency conversion code ends
						float amountPaid = convertedAmount1 *Float.valueOf((invoiceIdsMap.get(invoice.getId())));
						if(parentInvoice.getDueAmount().equals(String.valueOf(amountPaid))){
							// total amount paid
							invoicePayment.setPaymentAmount(String.valueOf(amountPaid));
							parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
							parentInvoice.setDueAmount("00.000");
						}else{
							// partial amount paid
							String due = parentInvoice.getDueAmount();
							BigDecimal dueDecimal = (new BigDecimal(due));
							String received = invoiceIdsMap.get(invoice.getId());
							float amountreceived = convertedAmount1 *Float.valueOf(received);
							BigDecimal receivedDecimal = (new BigDecimal(amountreceived));
							remainAmount = dueDecimal.subtract(receivedDecimal);
							remainAmount = remainAmount.setScale(3, BigDecimal.ROUND_UP);
							parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);
							invoicePayment.setPaymentAmount(String.valueOf(amountreceived));
							parentInvoice.setDueAmount(remainAmount.toString());
						}
					}
					if (Constants.INVOICE_RECEIVED.equalsIgnoreCase(parentInvoice.getInvoiceStatus())) {
						parentInvoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
					}
					parentInvoice.setUpdatedBy(user.getUserCode());
					parentInvoice.setUpdatedDate(commonUtil.currentDate());
					invoiceDao.saveInvoce(parentInvoice);
				}

			}
		}

		invoicePayment.setCurrencyCode(invoice.getCurrencyCode());
		if (StringUtils.isNotBlank(invoicePaymentDTO.getPaymentMemo())) {
			invoicePayment.setPaymentComment(invoicePaymentDTO.getPaymentMemo());
		}
		
		if(invoicePaymentDTO.getFileInfo() != null && invoicePaymentDTO.getFileInfo().size() > 0){
			for(FileInfo file : invoicePaymentDTO.getFileInfo()){
				invoicePayment.setFileContentType(file.getContentType());
				invoicePayment.setFileName(file.getFileName());
				invoicePayment.setFileURL(file.getFileURL());
			}
		}
		
		invoicePayment.setOrgLocation(orgLocation);
		if (invoice.getParentInvoiceId() != null) {
			invoicePayment.setInvoiceId(invoice.getParentInvoiceId());
		} else {
			invoicePayment.setInvoiceId(invoice);
		}

		invoicePayment.setStatus(Constants.RECORD_ACTIVE.toString());
		invoicePayment.setCreatedBy(user.getUserCode());
		invoicePayment.setCreatedDate(commonUtil.currentDate());
		invoicePayment.setUpdatedBy(user.getUserCode());
		invoicePayment.setUpdatedDate(commonUtil.currentDate());
		InvoicePayment invoicePaymentObject = invoicePaymentDao.saveInvoicePayment(invoicePayment);
		invoiceService.saveInvoiceTimelineHistory(parentInvoice, user, null, null, invoicePaymentObject.getId(), null);
		invoiceService.saveInvoiceTimelineHistory(invoice, user, null, null, invoicePaymentObject.getId(), null);
		 }
		 // this for moving file to folder after setting values (same information set to selected invoices)
		 if(invoicePaymentDTO.getFileInfo() != null && invoicePaymentDTO.getFileInfo().size() > 0){
			 for(FileInfo file : invoicePaymentDTO.getFileInfo()){
				 FileUploadUtil.moveFile(invoicePaymentDTO.getStaticPath(), file, "incoiceDocuments");	 
			 }
		 }
		
		return invoicePayment;
	}

	@Override
	public List<InvoicePayment> getInvoicePaymentList(Long invoiceId) throws Exception {
		return invoicePaymentDao.getInvoicePaymentList(invoiceId);
	}

	@Override
	public InvoicePayment invoicePaymentById(Long id) throws Exception {
		return invoicePaymentDao.invoicePaymentById(id);
	}

	@Override
	public List<InvoicePayment> getInvoicePaymentListByOrgloactionIdAndDates(Long orgLocationId, Date startDate, Date endDate) throws Exception {
		return invoicePaymentDao.getInvoicePaymentListByOrgloactionIdAndDates(orgLocationId, startDate, endDate);
	}

}
