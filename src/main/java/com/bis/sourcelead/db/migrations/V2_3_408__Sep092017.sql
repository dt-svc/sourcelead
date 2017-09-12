#Rescheduled Mail


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RescheduledMail','Email');


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerFirstName', '{{RescheduledMail.interviewerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerLastName', '{{RescheduledMail.interviewerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'jobTitle', '{{RescheduledMail.jobTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{RescheduledMail.applicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewDate', '{{RescheduledMail.interviewDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewTime', '{{RescheduledMail.interviewTime}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewType', '{{RescheduledMail.interviewType}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{RescheduledMail.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginorg', '{{RescheduledMail.loginorg}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'comment', '{{RescheduledMail.comment}}');


       
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                               ('Rescheduled Interview', 'Email', 'entity_type_rescheduled_email', 'The mail should forward to interviewer when interview got rescheduled', 'Interview got rescheduled for the {{RescheduledMail.jobTitle}} Position','sourcelead', 'Best Regards \n{{RescheduledMail.loginUser}}\n{{RescheduledMail.loginorg}}','Dear {{RescheduledMail.interviewerFirstName}},\nThis is to notify interview got rescheduled for the {{RescheduledMail.applicantName}} for {{RescheduledMail.jobTitle}} Position\nDate: {{RescheduledMail.interviewDate}}\nTime: {{RescheduledMail.interviewTime}}\nType: {{RescheduledMail.interviewType}}' ,'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

       
#Updated MSA Finalize email,#SOW Finalize email,#NDA/NSA Finalize email
 SET SQL_SAFE_UPDATES = 0;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='{{MSAFinalizeEmail.contractTitle}} Contract got finalized.', `BODY`= 'Dear {{MSAFinalizeEmail.receiverParty}},\nWe are happy to inform you {{MSAFinalizeEmail.contractTitle}} Contract got finalized. The brief details of the contract are following\nDuration : {{MSAFinalizeEmail.duration}}\nBilling Amount : {{MSAFinalizeEmail.billingAmount}}\nBilling Cycle : {{MSAFinalizeEmail.billingCycle}}\nPayment Frequency : {{MSAFinalizeEmail.paymentFrequency}}.' WHERE `UNIQUE_SCENARIO_NAME`='MSAFinalizeEmail' and `ENTITY_TYPE`='Email';
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='{{SOWFinalizeEmail.contractTitle}} Contract got finalized.', `BODY`=  'Dear {{SOWFinalizeEmail.receiverParty}},\nWe are happy to inform you {{SOWFinalizeEmail.contractTitle}} Contract got finalized. The brief details of the contract are following\nDuration : {{SOWFinalizeEmail.duration}}\nJob : {{SOWFinalizeEmail.Job}}\nAssociate : {{SOWFinalizeEmail.Associate}}\nBilling Amount : {{SOWFinalizeEmail.billingAmount}}\nBilling Cycle : {{SOWFinalizeEmail.billingCycle}}\nPayment Frequency : {{SOWFinalizeEmail.paymentFrequency}}.' WHERE `UNIQUE_SCENARIO_NAME`='SOWFinalizeEmail' and `ENTITY_TYPE`='Email';
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`= '{{NDAOrNSAFinalizeEmail.contractTitle}} Contract got finalized.', `BODY`= 'Dear {{NDAOrNSAFinalizeEmail.receiverParty}},\nWe are happy to inform you {{NDAOrNSAFinalizeEmail.contractTitle}} Contract got finalized. The brief details of the contract are following\nDuration : {{NDAOrNSAFinalizeEmail.duration}}.' WHERE `UNIQUE_SCENARIO_NAME`='NDAOrNSAFinalizeEmail' and `ENTITY_TYPE`='Email';

	
		
#InterviewerInvitationRedifined	

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrg', '{{InterviewerInvitationRedifined.loginOrg}}');
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='{{InterviewerInvitationRedifined.LoginUser}} invited you to Interview/Screen, the following candidate for {{InterviewerInvitationRedifined.RequirementTitle}} position with {{InterviewerInvitationRedifined.loginOrg}}\n {{InterviewerInvitationRedifined.interviewTableDetails}}\n\nFor more details Please Login.\n{{InterviewerInvitationRedifined.LoginLink}}',`SIGNATURE`='Best Regards,\n{{InterviewerInvitationRedifined.LoginUser}}\n{{InterviewerInvitationRedifined.loginOrg}}' WHERE `UNIQUE_SCENARIO_NAME`='Interviewer InvitationRedifined' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES = 1;
                               
       
       
       
