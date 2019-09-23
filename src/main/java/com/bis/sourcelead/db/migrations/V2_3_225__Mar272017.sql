#offer accepted mail 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('OfferAccepted','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'offeredApplicantName', '{{OfferAccepted.OfferedApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'offeredRequirementName', '{{OfferAccepted.OfferedRequirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'offeredRate', '{{OfferAccepted.OfferedRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'offeredJoinDate', '{{OfferAccepted.OfferedJoinDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientContactName', '{{OfferAccepted.ClientContactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorContactName', '{{OfferAccepted.VendorContactName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Offer Accepted','Email', 'Offer Accepted', 'Offer Accepted Mail to Client.', '{{OfferAccepted.OfferedApplicantName}} accepted the offer for {{OfferAccepted.OfferedRequirementName}} ','org', 'Best Regards,\n{{OfferAccepted.VendorContactName}}.', 'Dear {{OfferAccepted.ClientContactName}},\n\n {{OfferAccepted.OfferedApplicantName}} accepted the offer for {{OfferAccepted.OfferedRequirementName}} \n Join Date :  {{OfferAccepted.OfferedJoinDate}} \n Rate : {{OfferAccepted.OfferedRate}} \n ','N', 'SLADMIN', 'slead', 'sleadIN');


#offer accepted Notification

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OfferAccepted', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                               ('Offer Accepted','Notification','Offer Accepted Notification', 'Offer Accepted Notification from vendor', '{{OfferAccepted.OfferedApplicantName}} accepted the offer for {{OfferAccepted.OfferedRequirementName}}', 'sourcelead', '', '','N','SLADMIN', 'sourcelead', 'sourceleadIN');

  
                               
                               
#offer Rejected mail 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('OfferRejected','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'offerRejectedApplicantName', '{{OfferRejected.OfferRejectedApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementName', '{{OfferRejected.RequirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Rate', '{{OfferRejected.Rate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'JoinDate', '{{OfferRejected.JoinDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientContactName', '{{OfferRejected.ClientContactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorContactName', '{{OfferRejected.VendorContactName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Offer Rejected','Email', 'Offer Rejected', 'Offer Rejected Mail to Client.', '{{OfferRejected.OfferRejectedApplicantName}} rejected the offer for {{OfferRejected.RequirementName}}','org', 'Best Regards,\n{{OfferRejected.VendorContactName}}.', 'Dear {{OfferRejected.ClientContactName}},\n\n {{OfferRejected.OfferRejectedApplicantName}} rejected the offer for {{OfferRejected.RequirementName}}. \n Join Date :  {{OfferRejected.JoinDate}} \n Rate : {{OfferRejected.Rate}} \n ','N', 'SLADMIN', 'slead', 'sleadIN');


#offer rejected Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OfferRejected', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                               ('Offer Rejected','Notification','Offer Rejected Notification', 'Offer Rejected Notification from vendor', '{{OfferRejected.OfferRejectedApplicantName}} rejected the offer for {{OfferRejected.RequirementName}}', 'sourcelead', '', '','N','SLADMIN', 'sourcelead', 'sourceleadIN');
                               

                               
#Submitted applicants notification to client

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NotificationToClient', 'Notification');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorName', '{{NotificationToClient.VendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementTitle', '{{NotificationToClient.RequirementTitle}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Notification To Client', 'Notification', 'Notification To Client', 'Notification to client about submitted applicants', 'New applicants recieved for the {{NotificationToClient.RequirementTitle}} from the {{NotificationToClient.VendorName}}', 'sourcelead', '', '','N','SLADMIN', 'com', 'in.com');


#Shortlisted applicants notification to manager

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NotificationToManager', 'Notification');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'shortListApplicantCount', '{{NotificationToManager.ShortListApplicantCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{NotificationToManager.RequirementName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Notification To Manager', 'Notification', 'Notification To Manager', 'Notification to manager about shortlisted applicants', '{{NotificationToManager.ShortListApplicantCount}} applicants got shortlisted for the {{NotificationToManager.RequirementName}}', 'sourcelead', '', '','N','SLADMIN', 'com', 'in.com');
