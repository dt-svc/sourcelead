
INSERT INTO sourcelead.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('licensePaymentAcknowledgement', 'Email');

INSERT INTO sourcelead.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'customerName', '{{licensePaymentAcknowledgement.customerName}}');
INSERT INTO sourcelead.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'paymentDetails', '{{licensePaymentAcknowledgement.paymentDetails}}');


INSERT INTO sourcelead.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Lincense Payment Acknowledgement','Email', 'licensePaymentAcknowledgement', 'SOURCELEAD  Payment Acknowledgement', 'SOURCELEAD Payment Acknowledgement','org', '', 'Dear {{licensePaymentAcknowledgement.customerName}},\nHere is an outline of your payment acknowledgement.\nPayment Details : \n {{licensePaymentAcknowledgement.paymentDetails}}\n\nNote: No signature required as this document is generated electronically.','N', 'SLADMIN', 'slead', 'sleadIN');
