#ApplicantRejectMailToInterviewCreator
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Interview_Creator_Name],\n\nI regret to say that i am no longer interested in attending the following interview\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]\n\nThank you for providing me the opportunity', `SUBJECT`='I am not intrested in attending the interview for [Requirement_Name]', `SIGNATURE`='Best Regards,\n[Applicant_Name]'  WHERE `UNIQUE_SCENARIO_NAME`='Applicant RejectMailToInterviewCreator' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'MSAFinalizeEmail' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Receiver_Party],\n We are happy to inform you [Contract_Title] Contract got finalised from [Duration].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='MSAFinalizeEmail';
set SQL_SAFE_UPDATES=1;

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'SOWFinalizeEmail' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='[Contract_Title] Contract got finalized.', `BODY`=  'Dear [Receiver_Party],\nWe are happy to inform you [Contract_Title] Contract got finalized. The brief details of the contract are following\nDuration : [Duration]\nJob : [Job]\nAssociate : [Associate]\nBilling Amount :[Billing_Amount]\nBilling Cycle : [Billing_Cycle]\nPayment Frequency : [Payment_Frequency].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='SOWFinalizeEmail' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;