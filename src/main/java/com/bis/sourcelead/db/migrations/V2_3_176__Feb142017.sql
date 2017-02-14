
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('80', 'negotiationTable', '{{UserAcceptRateNegotiation.negotiationTable}}');
											
UPDATE `sourcelead`.`email_scenario` SET `BODY`='Dear {{UserAcceptRateNegotiation.applicantName}},\n\n We are the finalizing rate for the {{UserAcceptRateNegotiation.requirementName}}  after your acknowledgment.\n\n Finalized Rate  : {{UserAcceptRateNegotiation.finalRate}}\n {{UserAcceptRateNegotiation.negotiationTable}}' WHERE `ID`='83';
