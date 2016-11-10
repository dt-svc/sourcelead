
 UPDATE `sourcelead`.`CLASS_FIELD` SET `FIELD_NAME`='role' WHERE `DELIM_STR`= '{{ApprovalMechanism.role}}' and ENTITY_TYPE_ID = (select  id  from `ENTITY_TYPE` where  `ENTITY_NAME`='ApprovalMechanism' and `ENTITY_TYPE`='Email');
 UPDATE `sourcelead`.`CLASS_FIELD` SET `FIELD_NAME`='client' WHERE `DELIM_STR`= '{{ApprovalMechanism.client}}' and ENTITY_TYPE_ID = (select  id  from `ENTITY_TYPE` where  `ENTITY_NAME`='ApprovalMechanism' and `ENTITY_TYPE`='Email');

 
 #Forward Mail

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ForwardOfferMail','Email');


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'organizationName', '{{ForwardOfferMail.organizationName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{ForwardOfferMail.applicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'designation', '{{ForwardOfferMail.designation}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'dateofJoin', '{{ForwardOfferMail.dateofJoin}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'acceptLink', '{{ForwardOfferMail.acceptLink}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectLink', '{{ForwardOfferMail.rejectLink}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'attachment', '{{ForwardOfferMail.attachment}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{ForwardOfferMail.loginUser}}');




INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
       ('Forward Mail', 'Email', 'Forward the Mail', 'Used to notify the applicant about the offer', 'Congratulations !! Offer from{{ForwardOfferMail.organizationName}}', 'sourcelead', 'Best Regards\n {{ForwardOfferMail.loginUser}}.', 'Dear {{ForwardOfferMail.applicantName}},\n\n We are pleased to offer you a job as a {{ForwardOfferMail.designation}} at {{ForwardOfferMail.organizationName}}.\n We trust that your experience and skills will be a valuable asset to our Organization.\n\n Designation :{{ForwardOfferMail.designation}}\n\n Date of Join:{{ForwardOfferMail.dateofJoin}}\n\n
       Confirm the offer by clicking on the following link \n {{ForwardOfferMail.acceptLink}} {{ForwardOfferMail.rejectLink}}\n\n {{ForwardOfferMail.attachment}}', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

#Notification

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ForwardOfferMail', 'Notification');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                               ('Forward Mail','Notification','Forward the Mail', 'Used to notify the applicant about the offer', 'Congratulations!! Offer from {{ForwardOfferMail.organizationName}', 'sourcelead', '', '','N','SLADMIN', 'sourcelead', 'sourceleadIN');


#Client to Vendor Acceptence Mail Email

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ClienttoVendorAcceptence','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'OrgnanizationName', '{{ClienttoVendorAcceptence.OrgnanizationName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'link', '{{ClienttoVendorAcceptence.link}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementName', '{{ClienttoVendorAcceptence.requirementName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorContact', '{{ClienttoVendorAcceptence.vendorContact}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{ClienttoVendorAcceptence.loginUser}}');


#Email
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
						('Client to Vendor Acceptence','Email', 'Client to Vendor Acceptence', 'Used mail for client to  vendor ', 'Congratulations!! Offer from {{ClienttoVendorAcceptence.OrgnanizationName}}','org', 'Best Regards \n{{ClienttoVendorAcceptence.loginUser}}', 'Dear {{ClienttoVendorAcceptence.vendorContact}},\n\n We are pleased to inform your applicants got selected for the {{ClienttoVendorAcceptence.requirementName}}. \n\n For brief details login into the system \n\n {{ClienttoVendorAcceptence.link}}\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
