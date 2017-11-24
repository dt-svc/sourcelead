CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_MAIL_BOX','MAIL_TYPE','INT NULL  AFTER `CAMPAIGN_TABLE_DATA`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_MAIL_BOX','REQUIREMENT_ID','TEXT NULL  AFTER `CANDIDATE_ID`');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('JobListCampaign','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'salutation', '{{JobListCampaign.salutation}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'body', '{{JobListCampaign.body}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'noOfJobs', '{{JobListCampaign.noOfJobs}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'signature', '{{JobListCampaign.signature}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'joblistTable', '{{JobListCampaign.joblistTable}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('JobListCampaign','Email', 'JobListCampaign', 'used for Joblist campaign', 'Job Campaign.','org', '{{JobListCampaign.signature}}', '{{JobListCampaign.salutation}},\n{{JobListCampaign.body}}\nNo.of Jobs : {{JobListCampaign.noOfJobs}}\n\n {{JobListCampaign.joblistTable}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

											
DROP TABLE IF EXISTS `sourcelead`.`CONTACTS_GROUP`;
CREATE TABLE `sourcelead`.`CONTACTS_GROUP` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  GROUP_NAME TEXT DEFAULT NULL,
  CREATED_USER BIGINT,
  STATUS INT,
  CREATED_DATE DATE,
  UPDATED_DATE DATE,
  PRIMARY KEY (`ID`),
  FOREIGN KEY(CREATED_USER) REFERENCES `sourcelead`.`USER`(ID)
) ;

CALL sourcelead.AlterTableAddColumn ('sourcelead','REFER_CONTACTS','GROUP_ID','TEXT NULL  AFTER `CONTACT_USER_UUID`');

DROP TABLE IF EXISTS `sourcelead`.`REFER_CONTACTS_GROUP`;
CREATE TABLE `sourcelead`.`REFER_CONTACTS_GROUP` (
  CONTACT_ID BIGINT,
  CONTACT_GROUP_ID BIGINT,
  FOREIGN KEY(CONTACT_ID) REFERENCES `sourcelead`.`REFER_CONTACTS`(ID),
  FOREIGN KEY(CONTACT_GROUP_ID) REFERENCES `sourcelead`.`CONTACTS_GROUP`(ID)
) ;
