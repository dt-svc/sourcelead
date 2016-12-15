
DROP TABLE IF EXISTS `sourcelead`.`USER_SIGNATURE`;
CREATE TABLE `sourcelead`.`USER_SIGNATURE` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  USER_ID BIGINT DEFAULT NULL,
  ORG_LOCATION_ID BIGINT DEFAULT NULL,
  SIGNATURE_NAME VARCHAR(100),
  SIGNATURE TEXT DEFAULT NULL,
  STATUS INT DEFAULT NULL,
  CREATED_DATE DATE,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATE,
  UPDATED_BY VARCHAR(10),
  PRIMARY KEY (`ID`), 
  FOREIGN KEY fk1_USER_SIGNATURE_USER (USER_ID)  REFERENCES `sourcelead`.`USER`(ID),
  FOREIGN KEY fk1_USER_SIGNATURE_ORG_LOCATIONS (ORG_LOCATION_ID)  REFERENCES `sourcelead`.`ORG_LOCATIONS`(ID)
); 


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('HotListCampaign','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'salutation', '{{HotListCampaign.salutation}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'body', '{{HotListCampaign.body}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'noOfCandidates', '{{HotListCampaign.noOfCandidates}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'signature', '{{HotListCampaign.signature}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requestResume', '{{HotListCampaign.requestResume}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'hotlistTable', '{{HotListCampaign.hotlistTable}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('HotListCampaign','Email', 'HotListCampaign', 'used for Hotlist campaign', 'Hotlisted Candidates / Consultants.','org', '{{HotListCampaign.signature}}', '{{HotListCampaign.salutation}},\n\n {{HotListCampaign.body}} \n No.of Candidates : {{HotListCampaign.noOfCandidates}}\n\n {{HotListCampaign.hotlistTable}}\n\n {{HotListCampaign.requestResume}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

