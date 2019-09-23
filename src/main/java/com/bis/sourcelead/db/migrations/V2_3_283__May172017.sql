ALTER TABLE `CONTRACTS_TEMPLATE` 
CHANGE COLUMN `TEMPLATE_NAME` `TEMPLATE_NAME` VARCHAR(250) NULL DEFAULT NULL ;


ALTER TABLE `CONTRACTS` 
CHANGE COLUMN `CONTRACT_TITLE` `CONTRACT_TITLE` VARCHAR(250) NULL DEFAULT NULL ;


DROP TABLE IF EXISTS `CONTRACTS_HISTORY`;
CREATE TABLE `CONTRACTS_HISTORY` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  CONTRACT_ID BIGINT,
  FROM_STAGE VARCHAR(50) DEFAULT NULL,
  TO_STAGE VARCHAR(50) DEFAULT NULL,
  CREATED_DATE DATE,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATE,
  UPDATED_BY VARCHAR(10),
  PRIMARY KEY (`ID`),
  FOREIGN KEY(CONTRACT_ID) REFERENCES `CONTRACTS`(ID)
) ;


set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{notifyApplicantMailAboutRequirement.applicantName}},\n \n We have opening for the {{notifyApplicantMailAboutRequirement.requirementName}} position,\n Brief details for the position are below\n \n Exeperience : {{notifyApplicantMailAboutRequirement.experience}} \n \n Location : {{notifyApplicantMailAboutRequirement.location}} \n \n Skills : {{notifyApplicantMailAboutRequirement.skills}} \n \n Job Description : {{notifyApplicantMailAboutRequirement.jobDescription}} . \n ' WHERE `UNIQUE_SCENARIO_NAME`='notifyApplicantMailAboutRequirement' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

CALL AlterTableAddColumn ('CONTRACTS','BILLING_RATE_TYPE','VARCHAR(20) NULL  AFTER `BILLING_AMOUNT`');
CALL AlterTableAddColumn ('CONTRACTS','BILLING_RATE_PER','VARCHAR(20) NULL  AFTER `BILLING_RATE_TYPE`');