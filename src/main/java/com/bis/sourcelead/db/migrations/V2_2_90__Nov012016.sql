CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','REPORTING_MANAGER_ID','BIGINT NULL AFTER `USER_ID`');
ALTER TABLE `sourcelead`.`USER_ROLE` ADD FOREIGN KEY(REPORTING_MANAGER_ID) REFERENCES USER(ID);

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_INTERVIEW','TIME_ZONE','TEXT NULL AFTER `END_TIME`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_OFFERS','Offer_Status','VARCHAR(50) NULL AFTER `STATUS`');
