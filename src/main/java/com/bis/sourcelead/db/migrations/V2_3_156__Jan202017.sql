 UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{AssignToTeamMembers.Ownername}},\n\n {{AssignToTeamMembers.RequirementTitle}} new requirement assigned to you. The brief requirement details are below  \n\n  Requirement Title :{{AssignToTeamMembers.RequirementTitle}} \n\n  Requirement Location :{{AssignToTeamMembers.RequirementLocation}} \n\n  Requirement Rate :{{AssignToTeamMembers.RequirementRate}} \n\n  Experience :{{AssignToTeamMembers.Experience}} \n\n  For more details login into the system Login \n  {{AssignToTeamMembers.link}}' WHERE `ID`='44';
 
  
#Client accept rate negotiation of the Requirement.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ClientAcceptRateNegotiation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ClientAcceptRateNegotiation.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ClientAcceptRateNegotiation.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptedRate', '{{ClientAcceptRateNegotiation.acceptedRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{ClientAcceptRateNegotiation.clientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorName', '{{ClientAcceptRateNegotiation.vendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrg', '{{ClientAcceptRateNegotiation.clientOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiationTable', '{{ClientAcceptRateNegotiation.negotiationTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ClientAcceptRateNegotiation','Email', 'ClientAcceptRateNegotiation', 'Used mail when Client Accepts rate', 'Rate accepted by the {{ClientAcceptRateNegotiation.clientName}}','org', 'Best Regards,\n{{ClientAcceptRateNegotiation.clientName}}\n{{ClientAcceptRateNegotiation.clientOrg}}.', 'Dear {{ClientAcceptRateNegotiation.vendorName}},\n We are happy about the rate for {{ClientAcceptRateNegotiation.applicantName}} for the {{ClientAcceptRateNegotiation.requirementName}}.\nConfirmed rate : {{ClientAcceptRateNegotiation.acceptedRate}}.\nFor brief details of the rate go through the following  link..\n\n {{ClientAcceptRateNegotiation.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											
#Vendor accept rate negotiation of the Requirement.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('VendorAcceptRateNegotiation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{VendorAcceptRateNegotiation.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{VendorAcceptRateNegotiation.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptedRate', '{{VendorAcceptRateNegotiation.acceptedRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{VendorAcceptRateNegotiation.clientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorName', '{{VendorAcceptRateNegotiation.vendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{VendorAcceptRateNegotiation.loginOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiationTable', '{{VendorAcceptRateNegotiation.negotiationTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('VendorAcceptRateNegotiation','Email', 'VendorAcceptRateNegotiation', 'Used mail when Vendor Accepts rate', 'Rate accepted by the {{VendorAcceptRateNegotiation.vendorName}}','org', 'Best Regards,\n{{VendorAcceptRateNegotiation.vendorName}}\n{{VendorAcceptRateNegotiation.loginOrg}}.', 'Dear {{VendorAcceptRateNegotiation.clientName}},\n We are happy about the rate for {{VendorAcceptRateNegotiation.applicantName}} for the {{VendorAcceptRateNegotiation.requirementName}}.\nConfirmed rate : {{VendorAcceptRateNegotiation.acceptedRate}}.\nFor brief details of the rate go through the following  link..\n\n {{VendorAcceptRateNegotiation.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
	
#Client reject rate negotiation of the Requirement.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ClientRejectRateNegotiation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ClientRejectRateNegotiation.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ClientRejectRateNegotiation.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptedRate', '{{ClientRejectRateNegotiation.acceptedRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{ClientRejectRateNegotiation.clientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorName', '{{ClientRejectRateNegotiation.vendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrg', '{{ClientRejectRateNegotiation.clientOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiationTable', '{{ClientRejectRateNegotiation.negotiationTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ClientRejectRateNegotiation','Email', 'ClientRejectRateNegotiation', 'Used mail when Client Rejects rate', 'Rate rejected by the {{ClientRejectRateNegotiation.clientName}}','org', 'Best Regards,\n{{ClientRejectRateNegotiation.clientName}}\n{{ClientRejectRateNegotiation.clientOrg}}.', 'Dear {{ClientRejectRateNegotiation.vendorName}},\n We are not happy about the rate for {{ClientRejectRateNegotiation.applicantName}} for the {{ClientRejectRateNegotiation.requirementName}}.\nConfirmed rate : {{ClientRejectRateNegotiation.acceptedRate}}.\nFor brief details of the rate go through the following  link..\n\n {{ClientRejectRateNegotiation.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											
#Vendor reject rate negotiation of the Requirement.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('VendorRejectRateNegotiation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{VendorRejectRateNegotiation.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{VendorRejectRateNegotiation.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptedRate', '{{VendorRejectRateNegotiation.acceptedRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{VendorRejectRateNegotiation.clientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorName', '{{VendorRejectRateNegotiation.vendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{VendorRejectRateNegotiation.loginOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'negotiationTable', '{{VendorRejectRateNegotiation.negotiationTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('VendorRejectRateNegotiation','Email', 'VendorRejectRateNegotiation', 'Used mail when Vendor Reject rate', 'Rate rejected by the {{VendorRejectRateNegotiation.vendorName}}','org', 'Best Regards,\n{{VendorRejectRateNegotiation.vendorName}}\n{{VendorRejectRateNegotiation.loginOrg}}.', 'Dear {{VendorRejectRateNegotiation.clientName}},\n We are not happy about the rate for {{VendorRejectRateNegotiation.applicantName}} for the {{VendorRejectRateNegotiation.requirementName}}.\nConfirmed rate : {{VendorRejectRateNegotiation.acceptedRate}}.\nFor brief details of the rate go through the following  link..\n\n {{VendorRejectRateNegotiation.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
