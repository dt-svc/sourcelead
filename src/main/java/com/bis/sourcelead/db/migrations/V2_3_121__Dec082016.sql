
UPDATE `EMAIL_SCENARIO` SET `BODY`='You has invited as vendor from {{InviteVendor.clientOrgName}}' WHERE `ID`='37';
UPDATE `EMAIL_SCENARIO` SET `BODY`='You has invited as Client from {{inviteClient.vendorOrgName}}' WHERE `ID`='38';

CALL AlterTableAddColumn ('REQUIREMENT_INTERVIEW','INTERVIEW_SUBMISSION_TYPE','VARCHAR(250) NULL  AFTER `SUBMISSION_ID`');

UPDATE `EMAIL_SCENARIO` SET `BODY`='You has invited as vendor from {{InviteVendor.clientOrgName}}' WHERE `ID`='37';
UPDATE `EMAIL_SCENARIO` SET `BODY`='You has invited as Client from {{inviteClient.vendorOrgName}}' WHERE `ID`='38';

#Vendor Offer rejectance Email

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('VendorOfferRejectMail','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{VendorOfferRejectMail.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientContactName', '{{VendorOfferRejectMail.clientContactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{VendorOfferRejectMail.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'joinDate', '{{VendorOfferRejectMail.joinDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rate', '{{VendorOfferRejectMail.rate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorContact', '{{VendorOfferRejectMail.vendorContact}}');


#Email
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
						('Vendor Offer Reject','Email', 'Vendor Offer Reject', 'Used mail for vendor rejectance  ', '{{VendorOfferRejectMail.applicantName}} rejected  the offer for {{VendorOfferRejectMail.requirementName}}','org', 'Best Regards \n{{VendorOfferRejectMail.vendorContact}}', 'Dear {{VendorOfferRejectMail.clientContactName}},\n\n {{VendorOfferRejectMail.applicantName}} rejected the offer for {{VendorOfferRejectMail.requirementName}}. \n\n Join Date : {{VendorOfferRejectMail.rate}} \n\n Rate :{{VendorOfferRejectMail.rate}}\n\n Requirement Rate  : {{ApprovalMechanism.requirementRate}}\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

						
						
#VendorAcceptedOffer_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorAcceptedOffer', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{VendorAcceptedOffer.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementName', '{{VendorAcceptedOffer.RequirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ClientContactName', '{{VendorAcceptedOffer.ClientContactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'JoinDate', '{{VendorAcceptedOffer.JoinDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Rate', '{{VendorAcceptedOffer.Rate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'VendorContact', '{{VendorAcceptedOffer.VendorContact}}');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`)
VALUES ('Vendor Accepted Offer', 'Email', 'VendorAcceptedOffer', 'Used to notify vendor requirement offer', '{{VendorAcceptedOffer.ApplicantName}} accepted the offer for {{VendorAcceptedOffer.RequirementName}}', 'sourcelead', 'Best Regards \n {{VendorAcceptedOffer.VendorContact}}','Dear {{VendorAcceptedOffer.ClientContactName}},\n\n {{VendorAcceptedOffer.ApplicantName}} accepted the offer for {{VendorAcceptedOffer.RequirementName}}. \n\n Join Date :{{VendorAcceptedOffer.JoinDate}} \n\n Rate :{{VendorAcceptedOffer.Rate}}\n\n', 'N', 'SLADMIN', 'com', 'in.com');						

