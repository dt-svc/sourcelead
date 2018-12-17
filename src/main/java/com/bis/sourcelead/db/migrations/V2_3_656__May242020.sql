SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `TEMPLATE_TYPE`='org' WHERE UNIQUE_SCENARIO_NAME IN ('Forgot Password','Update Password','InvoiceClientAcceptEmail','UploadedRequestForDocumentsToCandidates','ActivateUserEmail');
SET SQL_SAFE_UPDATES=1;
