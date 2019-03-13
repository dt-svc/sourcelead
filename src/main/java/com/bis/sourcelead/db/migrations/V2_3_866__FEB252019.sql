#ApplicantRejectMailToInterviewCreator
set SQL_SAFE_UPDATES=0;
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Interview_Creator_Name],\n\nI regret to say that i am no longer interested in attending the following interview\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]\n\nThank you for providing me the opportunity', `SUBJECT`='I am not intrested in attending the interview for [Requirement_Name]', `SIGNATURE`='Best Regards,\n[Applicant_Name]'  WHERE `UNIQUE_SCENARIO_NAME`='Applicant RejectMailToInterviewCreator' and `ENTITY_TYPE`='Email';

#MSAFinalizeEmail
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'MSAFinalizeEmail' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Receiver_Party],\n We are happy to inform you [Contract_Title] Contract got finalised from [Duration].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='MSAFinalizeEmail';

#SOWFinalizeEmail
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'SOWFinalizeEmail' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='[Contract_Title] Contract got finalized.', `BODY`=  'Dear [Receiver_Party],\nWe are happy to inform you [Contract_Title] Contract got finalized. The brief details of the contract are following\nDuration : [Duration]\nJob : [Job]\nAssociate : [Associate]\nBilling Amount :[Billing_Amount]\nBilling Cycle : [Billing_Cycle]\nPayment Frequency : [Payment_Frequency].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='SOWFinalizeEmail' and `ENTITY_TYPE`='Email';

#Approved Requirement
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Approved Requirement' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear [Job_Initiator_Name],\n\n[Approver_Name] Approved the Job [Requirement_Id-_Name].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Approved Requirement' and `ENTITY_TYPE`='Email';

#Vendors Interview
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Vendors Interview' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Vendor_First_Name] [Vendor_Last_Name],\n\nInterview scheduled for the following applicants,\n\nRequirement name : [Requirement_Name]\n\nInterview date : [Interview_Date]\n\nDetails : [Details]\n\nInterview Type : [Interview_Type]\n\nApplicant : [Applicant_Name].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Vendors Interview' and `ENTITY_TYPE`='Email';

#VendorApplicantAcceptMailToInterviewCreator
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'VendorApplicantAcceptMailToInterviewCreator' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Interview_Creator_Name],\n\n[Applicant_Name] is attending for interview.\nGo through the brief details.\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='VendorApplicantAcceptMailToInterviewCreator' and `ENTITY_TYPE`='Email';

#VendorApplicantRejectMailToInterviewCreator
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'VendorApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Interview_Creator_Name],\n\nWe are sorry to say [Applicant_Name] is not attending for interview.\nGo through the brief details.\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='VendorApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE`='Email';

#Client to Vendor Acceptence
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Vendor_Contact],\n\n We are pleased to inform you that the applicant sent by you have been selected for [Requirement_Name]. \n\n If you would like to release offer, Please click here \n\n[Link]' WHERE `UNIQUE_SCENARIO_NAME`='Client to Vendor Acceptence' and `ENTITY_TYPE`='Email';

#Offer Accepted
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Offer Accepted' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`= ' Dear [Client_Contact_Name],\n\n[Offered_Applicant_Name] accepted the offer for [Offered_Requirement_Name].\nJoin Date :[Offered_Join_Date]\nJob Details : [Requirement_Description]\nRate : [Offered_Rate].\n Please click here \n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Offer Accepted' and `ENTITY_TYPE`='Email';

#Offer Rejected
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Offer Rejected' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Client_Contact_Name],\n\n [Offer_Rejected_Applicant_Name] rejected the offer for [Requirement_Name]. \n Join Date :  [Join_Date] \n Rate : [Rate] \n Reject Reason: [Reject_Reason]\n Please click here \n\n[Login_URL] '  WHERE `UNIQUE_SCENARIO_NAME`='Offer Rejected' and `ENTITY_TYPE`='Email';

#ApplicantAcceptOffer
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ApplicantAcceptOffer' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Org_User],\n\nCongratulations! Your Offer has been accepted. \nPlease find the details below\nJob ID/ Title /Project Name : [Job_Title]\nJoin Date :  [Join_Date]\nYou can go ahead and click below to start your onboarding process.\n\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='ApplicantAcceptOffer' and `ENTITY_TYPE`='Email';                                         

#ApplicantRejectOffer
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ApplicantRejectOffer' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Org_User],\n\nI regret to inform you that I will not be able to accept your Offer for the following position.\nJob Title: [Job_Title].\nReason: [Reason]\nPlease find the details below.\n\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='ApplicantRejectOffer' and `ENTITY_TYPE`='Email';                          

#ApplicantAcceptRequirement
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ApplicantAcceptRequirement' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Initiator_User_Name],\n\nI am pleased to accept the rate offered by you for the following position.\nplease let me know the next steps\n\nJob ID/name : [Requirement_Name]\nExeperience : [Experience]\nLocation : [Location]\nRate :[Requirement_Rate]\nSkills  : [Skills]\nJob Description :  [Job_Description].\n\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='ApplicantAcceptRequirement' and `ENTITY_TYPE`='Email';

#ApplicantRejectanceRequirement
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ApplicantRejectanceRequirement' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Initiator_User_Name],\n\n [Applicant_Name] not interested for the position [Requirement_Name] \n\n Reason : [Reject_Reason].\n Rate :[Requirement_Rate]\n[Negotiation_Link]\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='ApplicantRejectanceRequirement' and `ENTITY_TYPE`='Email';              

#RecruiterWillReceiveMailAboutTheAcceptanceOfRequirementByManager
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'RecruiterWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Assigned_User],\n\n I have accepted the [Applicant_Name] for [Requirement_Name].\n You can go for further process.\n\n The brief details are below \n Job Location :[Requirement_Location]\n Job Rate :[Rate].\n\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='RecruiterWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE`='Email';

#RejectMailFromClient_To_Vendor
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'RejectMailFromClient_To_Vendor' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY` = 'Dear [Vendor_Contact_Name],\n\n[Applicant_Name] got rejected by [Client_Contact_Name] for [Requirement_Name] \n\n Reason: [Reason].\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='RejectMailFromClient_To_Vendor' and `ENTITY_TYPE`='Email';

#Mail To Project Manager
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Mail To Project Manager' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY` = 'Hi [Manager_First_Name] [Manager_Last_Name],\n [Login_User_First_Name] [Login_User_Last_Name] assigned you as a project manager for the [Project_Name].\nTeam Members\n[Team_Members].\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='Mail To Project Manager' and `ENTITY_TYPE`='Email';

#Submit To Client
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Submit To Client' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY` =  'Hi [Client_First_Name] [Client_Last_Name],\n\n [Login_User]( [Login_Org_Name] ) submitted the applicants for the [Requirement_Title].\n Total no.of submitted applicants : [Total_No_Of_Applicants].\n\n [Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='Submit To Client' and `ENTITY_TYPE`='Email';

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'LeaveEmailToApprovers' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
#Email_body_update -------
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_Name],\n\n We are regret to inform that your leave has not been approved for the requested period.\n Leave Type : [Leave_Type_Name]\n  Duration : [From_Date] - [To_Date].\n  Rejected Reason : [Reason]\n\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='Rejected Leave' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Approver_Name],\n\nThis is to inform you that I have withdrawn my requested leave for the period [From_Date] - [To_Date], due to \nReason : [Reason].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Withdraw Leave' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_Name],\n\n We are glad to inform you that your leave [From_Date] to [To_Date] got approved.\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Approver Leave' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY` ='Dear [Employee_User],\n\n We are regret to inform you that your leave has been cancelled for the requested period.\nLeave Type : [Leave_Type]\nDuration : [From_Date] - [To_Date]\n Cancelled Reason : [Reason].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='LeaveCancelEmailToUser' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY` ='Dear [User_Name],\n\nThis is to inform you that there will be holiday on [Date] due to [Holidayname].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='HolidayRemainder' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Leave_Taken_Employee],\n\ This is to notify that leave has been requested for you on behalf of [Login_User]. Please find the details below.\nLeave Type : [Leave_Type]\nDuration  : [Leave_Duration]\nReason  : [Leave_Reason]\n\n[Login_Link]'   WHERE `UNIQUE_SCENARIO_NAME`='onBehalfEmail' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY` ='Dear [Approver_User],\nThis email is a request for leave.\nDays : [No_Of_Days] days. \nLeave Type : [Leave_Type]. \nDuration : [From_Date] to [To_Date] \nReason : [Reason]\nI request you to grant me leave for the above mentioned dates and reason.\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='LeaveEmailToApprovers' and `ENTITY_TYPE`='Email';

#project_email_body
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Manager],\n\nThis is to inform you that the following resources have been terminated from Project\nProject Name: [Project_Name]\nEmployee: [Employee_Name]\nLast day: [Project_End_Date]\nDetails and reason: [Reason]\nPlease click here for more details\n\n[Login_Link]'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Termination Mail To Manager' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_Name],\nThis is to inform you that you are relieved of your duties from [Project_Name] with [Client_Name]\nPlease find the details below.\nProject Name : [Project_Name]\nLast Day : [Project_End_Date]\nReason : [Reason]\nPlease contact your manager for further details or login to Source Lead\n\n[Login_Link]'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Termination' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_Name],\n\nWe are happy to inform you that you are assigned with the following Project work\nThe details of the project are as follows:-\nProject Start date : [Project_Start_Date]\nProject End date : [Project_End_Date]\nManager : [Manager]\nLocation : [Location]\n\nWe are looking forward to work with you.'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Reassign' and `ENTITY_TYPE`='Email';

#Budget Request Accepted
UPDATE `EMAIL_SCENARIO` SET `BODY` ='Dear [Approver_Name],\nThe Budget request for [Project_Name] project got approved.\nPlease log into Sourcelead to go ahead with the further process.'  WHERE `UNIQUE_SCENARIO_NAME`='Budget Request Accepted' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;