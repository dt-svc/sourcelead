package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dao.InvoicePaymentDao;
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

@Service
public class InvoicePaymentServiceImpl implements InvoicePaymentService{
	
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
	
	@Override
	public InvoicePayment saveInvoicePayment(JSONObject jsonObj, User user, OrgLocations orgLocation,MultipartFile multiFile,Long invoiceId) throws Exception {
		
		Invoice invoice =  invoiceDao.getInvoicetById(invoiceId);
		
		InvoicePayment invoicePayment = new InvoicePayment();
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
		if(jsonObj.has("paymentDate") && !"".equalsIgnoreCase(jsonObj.getString("paymentDate"))){
			Date paymentDate = format.parse(jsonObj.getString("paymentDate"));
			invoicePayment.setPaymentDate(paymentDate);
		}
		if(jsonObj.has("paymentMethod") && !"".equalsIgnoreCase(jsonObj.getString("paymentMethod"))){
			invoicePayment.setPaymentMethod(jsonObj.getString("paymentMethod"));
		}
		if(jsonObj.has("referenceNumber") && !"".equalsIgnoreCase(jsonObj.getString("referenceNumber"))){
			invoicePayment.setPaymentReferenceNumber(jsonObj.getString("referenceNumber"));
			
		}
		
		if(jsonObj.has("paymentAmount") && !"".equalsIgnoreCase(jsonObj.getString("paymentAmount"))){
			if(jsonObj.getString("paymentAmount").equalsIgnoreCase("total")){
				if(jsonObj.has("totalAmount") && !"".equalsIgnoreCase(jsonObj.getString("totalAmount"))){
					invoicePayment.setPaymentAmount(jsonObj.getString("totalAmount"));
					invoice.setPaymentStatus("Paid");
					invoice.setDueAmount("00.00");
				}
			}
			if(jsonObj.getString("paymentAmount").equalsIgnoreCase("custom")){
				if(jsonObj.has("customeAmount") && !"".equalsIgnoreCase(jsonObj.getString("customeAmount"))){
					invoicePayment.setPaymentAmount(jsonObj.getString("customeAmount"));
					Float totalAmount = Float.parseFloat(jsonObj.getString("totalAmount"));
					Float customAmount = Float.parseFloat(jsonObj.getString("customeAmount"));
					Float receivedAmount  = totalAmount - customAmount;
					
					if(totalAmount.equals(customAmount) || (customAmount > totalAmount)){
						invoice.setPaymentStatus("Paid");
					}else{
						invoice.setPaymentStatus("Partial Paid");
					}
					invoice.setDueAmount(receivedAmount.toString());
					
				}
			}
			Invoice childInvoice = invoiceDao.saveInvoce(invoice);
			Invoice parentInvoice = null;
			if(null != childInvoice){
				if(null != childInvoice.getId()){
					parentInvoice =  invoiceDao.getInvoicetBySelfJoinInvoiceId(childInvoice.getId());
					if(parentInvoice == null){
						parentInvoice =  invoiceDao.getInvoicetByParentInvoiceId(invoice.getParentInvoiceId().getId());	
					}
					if(jsonObj.getString("paymentAmount").equalsIgnoreCase("total")){
						if(jsonObj.has("totalAmount") && !"".equalsIgnoreCase(jsonObj.getString("totalAmount"))){
							invoicePayment.setPaymentAmount(jsonObj.getString("totalAmount"));
							parentInvoice.setPaymentStatus("Paid");
							parentInvoice.setDueAmount("00.00");
						}
					}
					if(jsonObj.getString("paymentAmount").equalsIgnoreCase("custom")){
						if(jsonObj.has("customeAmount") && !"".equalsIgnoreCase(jsonObj.getString("customeAmount"))){
							invoicePayment.setPaymentAmount(jsonObj.getString("customeAmount"));
							Float totalAmount = Float.parseFloat(jsonObj.getString("totalAmount"));
							Float customAmount = Float.parseFloat(jsonObj.getString("customeAmount"));
							Float receivedAmount  = totalAmount - customAmount;
							
							if(totalAmount.equals(customAmount) || (customAmount > totalAmount)){
								parentInvoice.setPaymentStatus("Paid");
							}else{
								parentInvoice.setPaymentStatus("Partial Paid");
							}
							
							parentInvoice.setDueAmount(receivedAmount.toString());
							
						}
					}
				  invoiceDao.saveInvoce(parentInvoice);
				}
				
			}
		}
		
		 
		if(jsonObj.has("paymentMemo") && !"".equalsIgnoreCase(jsonObj.getString("paymentMemo"))){
			invoicePayment.setPaymentComment(jsonObj.getString("paymentMemo"));
		}
		if(null != multiFile){
			invoicePayment.setFileContentType(multiFile.getContentType());
			invoicePayment.setFileName(multiFile.getOriginalFilename());
			invoicePayment.setDocFile(multiFile.getBytes());
	
		}
		invoicePayment.setOrgLocation(orgLocation);
		if(invoice.getParentInvoiceId() != null){
			invoicePayment.setInvoiceId(invoice.getParentInvoiceId());	
		}else{
			invoicePayment.setInvoiceId(invoice);
		}
		
		invoicePayment.setStatus(Constants.RECORD_ACTIVE.toString());
		invoicePayment.setCreatedBy(user.getUserCode());
		invoicePayment.setCreatedDate(commonUtil.currentDate());
		return invoicePaymentDao.saveInvoicePayment(invoicePayment);
	}

	@Override
	public List<InvoicePayment> getInvoicePaymentList(Long invoiceId) throws Exception {
		return invoicePaymentDao.getInvoicePaymentList(invoiceId);
	}

	@Override
	public InvoicePayment invoicePaymentById(Long id) throws Exception {
		return invoicePaymentDao.invoicePaymentById(id);
	}
	
	
	
}
