set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{InvoiceClientAcceptEmail.clientName}},\nWe are contacting you in regard to a new invoice {{InvoiceClientAcceptEmail.invoiceID}} that has been created on your account. \nYou may find the invoice attached. Please pay the invoice of {{InvoiceClientAcceptEmail.grandTotal}} by {{InvoiceClientAcceptEmail.dueDate}} We appreciate your prompt payment. \n  {{InvoiceClientAcceptEmail.acceptLink}} \n {{InvoiceClientAcceptEmail.rejectLink}}' WHERE `UNIQUE_SCENARIO_NAME`='InvoiceClientAcceptEmail' and `ENTITY_TYPE`='Email';


