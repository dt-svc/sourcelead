SET SQL_SAFE_UPDATES = 0;

UPDATE `EMAIL_SCENARIO` SET `BODY`='Invoice-{{InvoiceRejectMailtoVendor.invoiceID}} Rejected by {{InvoiceRejectMailtoVendor.orgName}}  '  WHERE `UNIQUE_SCENARIO_NAME`='InvoiceRejectMailtoVendor' and `ENTITY_TYPE`='Notification';


UPDATE `EMAIL_SCENARIO` SET `BODY`='Invoice -{{InvoiceAcceptMailtoVendor.invoiceID}} Accepted  by {{InvoiceAcceptMailtoVendor.orgName}}  '  WHERE `UNIQUE_SCENARIO_NAME`='InvoiceAcceptMailtoVendor' and `ENTITY_TYPE`='Notification';

SET SQL_SAFE_UPDATES = 1;