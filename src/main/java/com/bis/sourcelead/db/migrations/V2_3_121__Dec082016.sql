

ALTER TABLE `sourcelead`.`REQUIREMENT_INTERVIEW` 
DROP COLUMN `NOTIFICATION_TO_SPOC`,
DROP COLUMN `NOTIFICATION_TO_APPLICANT`;

DROP TABLE IF EXISTS `sourcelead`.`REQUIREMENT_INTERVIEW_HISTORY`;
CREATE TABLE `sourcelead`.`REQUIREMENT_INTERVIEW_HISTORY` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  INTERVIEW_ID BIGINT DEFAULT NULL,
  SUBMISSION_ID BIGINT DEFAULT NULL,
  ORG_LOCATION_ID BIGINT DEFAULT NULL,
  INTERVIEW_SOURCE_TYPE VARCHAR(250),
  NOTIFICATION_TO_APPLICANT VARCHAR(3),
  NOTIFICATION_TO_SPOC VARCHAR(2),
  STATUS INT(10) DEFAULT NULL,
  CREATED_DATE DATETIME,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATETIME,
  UPDATED_BY VARCHAR(10),
  
  PRIMARY KEY (`ID`), 
  FOREIGN KEY(INTERVIEW_ID) REFERENCES `sourcelead`.`REQUIREMENT_INTERVIEW`(ID),
  FOREIGN KEY(SUBMISSION_ID) REFERENCES `sourcelead`.`REQUIREMENT_SUBMISSION`(ID),
  FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `sourcelead`.`ORG_LOCATIONS`(ID)
); 


UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='You has invited as vendor from {{InviteVendor.clientOrgName}}' WHERE `ID`='37';
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='You has invited as Client from {{inviteClient.vendorOrgName}}' WHERE `ID`='38';

#Vendor Offer rejectance Email

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('VendorOfferRejectMail','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{VendorOfferRejectMail.applicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientContactName', '{{VendorOfferRejectMail.clientContactName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementName', '{{VendorOfferRejectMail.requirementName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'joinDate', '{{VendorOfferRejectMail.joinDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rate', '{{VendorOfferRejectMail.rate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorContact', '{{VendorOfferRejectMail.vendorContact}}');


#Email
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
						('Vendor Offer Reject','Email', 'Vendor Offer Reject', 'Used mail for vendor rejectance  ', '{{VendorOfferRejectMail.applicantName}} rejected  the offer for {{VendorOfferRejectMail.requirementName}}','org', 'Best Regards \n{{VendorOfferRejectMail.vendorContact}}', 'Dear {{VendorOfferRejectMail.clientContactName}},\n\n {{VendorOfferRejectMail.applicantName}} rejected the offer for {{VendorOfferRejectMail.requirementName}}. \n\n Join Date : {{VendorOfferRejectMail.rate}} \n\n Rate :{{VendorOfferRejectMail.rate}}\n\n Requirement Rate  : {{ApprovalMechanism.requirementRate}}\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

						
						
#VendorAcceptedOffer_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VendorAcceptedOffer', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'ApplicantName', '{{VendorAcceptedOffer.ApplicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'RequirementName', '{{VendorAcceptedOffer.RequirementName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'ClientContactName', '{{VendorAcceptedOffer.ClientContactName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'JoinDate', '{{VendorAcceptedOffer.JoinDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'Rate', '{{VendorAcceptedOffer.Rate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'VendorContact', '{{VendorAcceptedOffer.VendorContact}}');


#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`)
VALUES ('Vendor Accepted Offer', 'Email', 'VendorAcceptedOffer', 'Used to notify vendor requirement offer', '{{VendorAcceptedOffer.ApplicantName}} accepted the offer for {{VendorAcceptedOffer.RequirementName}}', 'sourcelead', 'Best Regards \n {{VendorAcceptedOffer.VendorContact}}','Dear {{VendorAcceptedOffer.ClientContactName}},\n\n {{VendorAcceptedOffer.ApplicantName}} accepted the offer for {{VendorAcceptedOffer.RequirementName}}. \n\n Join Date :{{VendorAcceptedOffer.JoinDate}} \n\n Rate :{{VendorAcceptedOffer.Rate}}\n\n', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');						


