
ALTER TABLE `REQUIREMENT_WORKFLOW_STAGES` 
ADD COLUMN `STAGE_SHORT_CODE` VARCHAR(50) NOT NULL AFTER `STAGE_NAME`;

ALTER TABLE `sourcelead`.`requirement_default_workflow_stages` 
ADD COLUMN `STAGE_SHORT_CODE` VARCHAR(45) NULL DEFAULT NULL AFTER `DESCRIPTION`;

UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='SUBMISSION' WHERE `ID`='1';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='SHORTLIST' WHERE `ID`='2';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='SUBMIT_TO_CLIENT' WHERE `ID`='3';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='INTERVIEW' WHERE `ID`='4';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='OFFERMADE' WHERE `ID`='5';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='REJECTED' WHERE `ID`='6';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='JOINED' WHERE `ID`='7';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='BACKOUT' WHERE `ID`='8';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='ON_HOLD' WHERE `ID`='9';
UPDATE `sourcelead`.`requirement_default_workflow_stages` SET `STAGE_SHORT_CODE`='NO_RESPONSE' WHERE `ID`='10';
