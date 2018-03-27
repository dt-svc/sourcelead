
ALTER TABLE `sourcelead`.`SOURCELEAD_BLOGS` 
ADD COLUMN `BLOG_STATUS` VARCHAR(50) NULL DEFAULT NULL AFTER `STATUS`;

ALTER TABLE `sourcelead`.`SOURCELEAD_BLOGS` ADD COLUMN `HREF` VARCHAR(50) NULL DEFAULT NULL AFTER `STATUS`;

ALTER TABLE `sourcelead`.`BLOG_CATEGORY` ADD COLUMN `HREF` VARCHAR(50) NULL DEFAULT NULL AFTER `STATUS`;

UPDATE `sourcelead`.`BLOG_CATEGORY` SET `CATEGORY_NAME`='General Category', `DESCRIPTION`='General Category', `HREF`='General-Category' WHERE `ID`='1';

DROP TABLE IF EXISTS `sourcelead`.`BLOG_SUBSCRIPTION`;
CREATE TABLE `sourcelead`.`BLOG_SUBSCRIPTION` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  EMAIL VARCHAR(250) DEFAULT NULL,
  STATUS VARCHAR(10) DEFAULT NULL,
  CREATED_DATE DATETIME,
  PRIMARY KEY (`ID`)
) ;

#CLASS_FIELD
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('blogSubscription', 'Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'blogTable', '{{blogSubscription.blogTable}}');
#INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'blogTitle', '{{blogSubscription.blogTitle}}');



#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('blogSubscription','Email', 'blogSubscription', 'blogSubscription mail to Subscriber', ' {{blogSubscription.blogTitle}}','org', ' ', '{{blogSubscription.blogTable}}','N', 'SLADMIN', 'slead', 'sleadIN');
