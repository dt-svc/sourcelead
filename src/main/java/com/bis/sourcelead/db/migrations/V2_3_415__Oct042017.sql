UPDATE `sourcelead`.`email_scenario` SET `SUBJECT`='Invitation from {{CandidateInvitation.loginOrg}}' WHERE `ID`='197';
UPDATE `sourcelead`.`email_scenario` SET `SUBJECT`='Invitation from {{inviteCompany.LoginUser}}' WHERE `ID`='222';


CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_MAIL_BOX','FILE_URL','TEXT NULL  AFTER `FILE_NAME`');