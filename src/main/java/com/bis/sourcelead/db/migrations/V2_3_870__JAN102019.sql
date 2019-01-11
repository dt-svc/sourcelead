#ApplicantRejectMailToInterviewCreator
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Interview_Creator_Name],\n\nI regret to say that i am no longer interested in attending the following interview\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]\n\nThank you for providing me the opportunity', `SUBJECT`='I am not intrested in attending the interview for [Requirement_Name]', `SIGNATURE`='Best Regards,\n[Applicant_Name]'  WHERE `UNIQUE_SCENARIO_NAME`='Applicant RejectMailToInterviewCreator' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

#MSAFinalizeEmail
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'MSAFinalizeEmail' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Receiver_Party],\n We are happy to inform you [Contract_Title] Contract got finalised from [Duration].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='MSAFinalizeEmail';
set SQL_SAFE_UPDATES=1;

#SOWFinalizeEmail
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'SOWFinalizeEmail' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='[Contract_Title] Contract got finalized.', `BODY`=  'Dear [Receiver_Party],\nWe are happy to inform you [Contract_Title] Contract got finalized. The brief details of the contract are following\nDuration : [Duration]\nJob : [Job]\nAssociate : [Associate]\nBilling Amount :[Billing_Amount]\nBilling Cycle : [Billing_Cycle]\nPayment Frequency : [Payment_Frequency].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='SOWFinalizeEmail' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;


#Approved Requirement
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Approved Requirement' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear [Job_Initiator_Name],\n\n[Approver_Name] Approved the Job [Requirement_Id-_Name].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Approved Requirement' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

#Vendors Interview
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Vendors Interview' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Vendor_First_Name] [Vendor_Last_Name],\n\nInterview scheduled for the following applicants,\n\nRequirement name : [Requirement_Name]\n\nInterview date : [Interview_Date]\n\nDetails : [Details]\n\nInterview Type : [Interview_Type]\n\nApplicant : [Applicant_Name].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Vendors Interview' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

#VendorApplicantAcceptMailToInterviewCreator
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'VendorApplicantAcceptMailToInterviewCreator' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Interview_Creator_Name],\n\n[Applicant_Name] is attending for interview.\nGo through the brief details.\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='VendorApplicantAcceptMailToInterviewCreator' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

#VendorApplicantRejectMailToInterviewCreator
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'VendorApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Interview_Creator_Name],\n\nWe are sorry to say [Applicant_Name] is not attending for interview.\nGo through the brief details.\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='VendorApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

#VendorApplicantAcceptMailToInterviewCreator
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'VendorApplicantAcceptMailToInterviewCreator' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{VendorApplicantAcceptMailToInterviewCreator.InterviewCreatorName}},\n\n{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} is attending for interview.\nGo through the brief details.\n\nJob Title :{{VendorApplicantAcceptMailToInterviewCreator.requirementName}}\nInterview Date :{{VendorApplicantAcceptMailToInterviewCreator.InterviewDate}}\nInterview Time :{{VendorApplicantAcceptMailToInterviewCreator.InterviewTime}}\nInterview Type :{{VendorApplicantAcceptMailToInterviewCreator.InterviewType}}\nDetails :{{VendorApplicantAcceptMailToInterviewCreator.Details}}' WHERE `UNIQUE_SCENARIO_NAME`='VendorApplicantAcceptMailToInterviewCreator' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

























