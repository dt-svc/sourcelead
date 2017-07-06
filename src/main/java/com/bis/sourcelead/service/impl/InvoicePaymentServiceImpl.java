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
import com.bis.sourcelead.util.ConversionUtil;
import com.ibm.icu.math.BigDecimal;

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

	@Override
	public InvoicePayment saveInvoicePayment(JSONObject jsonObj, User user, OrgLocations orgLocation,
			MultipartFile multiFile, Long invoiceId) throws Exception {

		Invoice invoice = invoiceDao.getInvoicetById(invoiceId);

		InvoicePayment invoicePayment = new InvoicePayment();
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);

		if (jsonObj.has("paymentDate") && !"".equalsIgnoreCase(jsonObj.getString("paymentDate"))) {
			Date paymentDate = format.parse(jsonObj.getString("paymentDate"));
			invoicePayment.setPaymentDate(paymentDate);
		}
		if (jsonObj.has("paymentMethod") && !"".equalsIgnoreCase(jsonObj.getString("paymentMethod"))) {
			invoicePayment.setPaymentMethod(jsonObj.getString("paymentMethod"));
		}
		if (jsonObj.has("referenceNumber") && !"".equalsIgnoreCase(jsonObj.getString("referenceNumber"))) {
			invoicePayment.setPaymentReferenceNumber(jsonObj.getString("referenceNumber"));

		}

		if (jsonObj.has("paymentAmount") && !"".equalsIgnoreCase(jsonObj.getString("paymentAmount"))) {
			if (jsonObj.getString("paymentAmount").equalsIgnoreCase("total")) {
				if (jsonObj.has("totalAmount") && !"".equalsIgnoreCase(jsonObj.getString("totalAmount"))) {
					invoicePayment.setPaymentAmount(jsonObj.getString("totalAmount"));
					invoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
					invoice.setDueAmount("00.00");
				}
			}
			if (jsonObj.getString("paymentAmount").equalsIgnoreCase("custom")) {
				if (jsonObj.has("customeAmount") && !"".equalsIgnoreCase(jsonObj.getString("customeAmount"))) {
					invoicePayment.setPaymentAmount(jsonObj.getString("customeAmount"));
					BigDecimal totalAmount = new BigDecimal(Double.parseDouble(jsonObj.getString("totalAmount")));
					BigDecimal customAmount = new BigDecimal(Double.parseDouble(jsonObj.getString("customeAmount")));
					BigDecimal receivedAmount = totalAmount.subtract(customAmount);
					int res = customAmount.compareTo(receivedAmount);
					if (totalAmount.equals(customAmount) || res == 1) {
						invoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
					} else {
						invoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);
					}
					invoice.setDueAmount(String.valueOf(receivedAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN)));

				}
			}
			if (Constants.INVOICE_SENT.equalsIgnoreCase(invoice.getInvoiceStatus())) {
				invoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
			}
			Invoice childInvoice = invoiceDao.saveInvoce(invoice);

			Invoice parentInvoice = null;
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
					if (jsonObj.getString("paymentAmount").equalsIgnoreCase("total")) {
						if (jsonObj.has("totalAmount") && !"".equalsIgnoreCase(jsonObj.getString("totalAmount"))) {
							invoicePayment.setPaymentAmount(jsonObj.getString("totalAmount"));
							parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
							parentInvoice.setDueAmount("00.00");
						}
					}
					if (jsonObj.getString("paymentAmount").equalsIgnoreCase("custom")) {
						if (jsonObj.has("customeAmount") && !"".equalsIgnoreCase(jsonObj.getString("customeAmount"))) {
							invoicePayment.setPaymentAmount(jsonObj.getString("customeAmount"));
							if (!paidCurrencyCode.equalsIgnoreCase(receiverCurrencyCode)) {
								float conAmount = conversionUtil.convert(paidCurrencyCode, receiverCurrencyCode,
										Float.parseFloat(paidAmount));
								BigDecimal conversionAmount = new BigDecimal(conAmount);
								BigDecimal receiverTotalAmount = new BigDecimal(
										Float.parseFloat(parentInvoice.getTotal()));
								receivedAmount = receiverTotalAmount.subtract(conversionAmount);
								BigDecimal totalAmount = new BigDecimal(
										Double.parseDouble(jsonObj.getString("totalAmount")));
								BigDecimal customAmount = new BigDecimal(
										Double.parseDouble(jsonObj.getString("customeAmount")));
								int res = customAmount.compareTo(totalAmount);
								if ((conversionAmount.equals(receiverTotalAmount)) || res == -1) {
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
								} else {
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);
								}
							} else {
								BigDecimal totalAmount = new BigDecimal(
										Double.parseDouble(jsonObj.getString("totalAmount")));
								BigDecimal customAmount = new BigDecimal(
										Double.parseDouble(jsonObj.getString("customeAmount")));
								receivedAmount = totalAmount.subtract(customAmount);
								int res = customAmount.compareTo(totalAmount);
								if ((totalAmount.equals(customAmount)) || res == 1) {
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PAID);
								} else {
									parentInvoice.setPaymentStatus(Constants.INVOICE_STATUS_PARTIAL_PAID);
								}
							}

							if (receivedAmount != null) {
								parentInvoice.setDueAmount(
										String.valueOf(receivedAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN)));
							} else {
								parentInvoice.setDueAmount("0.0");
							}
						}
					}
					if (Constants.INVOICE_RECEIVED.equalsIgnoreCase(parentInvoice.getInvoiceStatus())) {
						parentInvoice.setInvoiceStatus(Constants.INVOICE_ACCEPETED);
					}
					invoiceDao.saveInvoce(parentInvoice);
				}

			}
		}

		invoicePayment.setCurrencyCode(invoice.getCurrencyCode());
		if (jsonObj.has("paymentMemo") && !"".equalsIgnoreCase(jsonObj.getString("paymentMemo"))) {
			invoicePayment.setPaymentComment(jsonObj.getString("paymentMemo"));
		}
		if (null != multiFile) {
			invoicePayment.setFileContentType(multiFile.getContentType());
			invoicePayment.setFileName(multiFile.getOriginalFilename());
			invoicePayment.setDocFile(multiFile.getBytes());

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
