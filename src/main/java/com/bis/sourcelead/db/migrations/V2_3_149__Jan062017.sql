
  
#User accept rate negotiation of the Requirement.

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('UserRejectRateNegotiation','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{UserRejectRateNegotiation.applicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementName', '{{UserRejectRateNegotiation.requirementName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectRate', '{{UserRejectRateNegotiation.rejectRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'userName', '{{UserRejectRateNegotiation.userName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectedReason', '{{UserRejectRateNegotiation.rejectedReason}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'negotiationTable', '{{UserRejectRateNegotiation.negotiationTable}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('UserRejectRateNegotiation','Email', 'UserRejectRateNegotiation', 'Used mail when user Rejects rate', 'Rate Rejected for {{UserRejectRateNegotiation.requirementName}}','org', 'Best Regards,\n{{UserRejectRateNegotiation.userName}}', 'Dear {{UserRejectRateNegotiation.applicantName}},\n\n We are the Rejecting rate for the {{UserRejectRateNegotiation.requirementName}}.\n\n Rejected Rate  : {{UserRejectRateNegotiation.rejectRate}}\n Rejected Reason  : {{UserRejectRateNegotiation.rejectedReason}}\n\n {{UserRejectRateNegotiation.negotiationTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											
