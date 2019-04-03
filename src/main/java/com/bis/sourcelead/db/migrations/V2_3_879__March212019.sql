DROP TABLE IF EXISTS `OFFER_LETTER_TEMPLATE`;
CREATE TABLE `OFFER_LETTER_TEMPLATE` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  TEMPLATE_NAME varchar(100),
  ORGLOCATION_ID BIGINT,
  LETTER_HEAD_ID BIGINT DEFAULT NULL,
  DESCRIPTION TEXT DEFAULT NULL,
  TEMPLATE_CONTENT LONGTEXT DEFAULT NULL,
  TEMPLATE_STATUS INT,
  TEMPLATE_UUID VARCHAR(50),
  CREATED_DATE DATE,
  CREATED_BY VARCHAR(50),
  UPDATED_DATE DATE,
  UPDATED_BY VARCHAR(50),
  PRIMARY KEY (`ID`),
  FOREIGN KEY(LETTER_HEAD_ID) REFERENCES `LETTER_HEAD`(ID),
  FOREIGN KEY(ORGLOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID)
) ;

#for Employee on Leave

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'EmployeeOnLeave' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'DateOfLeave', '[Date_Of_Leave]');

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Manager],\n\nThis is to notify that following employee will be on leave [Date_Of_Leave].\n\n[Appliants_Info]\n'  WHERE `UNIQUE_SCENARIO_NAME`='EmployeeOnLeave' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;


#for Approve Pending Leaves

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Manager],\nThis is to notify that the following leave requests are in pending to approve\n[Appliants_Info]\nFor brief details kindly login into the system. \n To approve leave please click here.\n[Approve_Leave_Link]\n' WHERE `UNIQUE_SCENARIO_NAME`='ApprovePendingLeaves' and `ENTITY_TYPE`='Email';