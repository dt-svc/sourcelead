
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('licensePaymentAcknowledgement', 'Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'customerName', '{{licensePaymentAcknowledgement.customerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'paymentDetails', '{{licensePaymentAcknowledgement.paymentDetails}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Lincense Payment Acknowledgement','Email', 'licensePaymentAcknowledgement', 'SOURCELEAD  Payment Acknowledgement', 'SOURCELEAD Payment Acknowledgement','sourcelead', '', 'Dear {{licensePaymentAcknowledgement.customerName}},\nHere is an outline of your payment acknowledgement.\nPayment Details : \n {{licensePaymentAcknowledgement.paymentDetails}}\n\nNote: No signature required as this document is generated electronically.','N', 'SLADMIN', 'slead', 'sleadIN');

			
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('licensePaymentAcknowledgementDetails', 'Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyName', '{{licensePaymentAcknowledgementDetails.companyName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'paymentDetails', '{{licensePaymentAcknowledgementDetails.paymentDetails}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Lincense Payment Acknowledgement Details','Email', 'licensePaymentAcknowledgementDetails', 'SOURCELEAD  Payment Acknowledgement Details', 'SOURCELEAD Payment Acknowledgement Details','sourcelead', '', 'Here is an outline of payment acknowledgement details from Company {{licensePaymentAcknowledgementDetails.companyName}}.\nPayment Details : \n {{licensePaymentAcknowledgementDetails.paymentDetails}}','N', 'SLADMIN', 'slead', 'sleadIN');
