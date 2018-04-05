SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{passwordResetMailToUser.userName}},\nThe Password of your Sourcelead  has been reset successfully.\nYou can now log in to your Sourcelead application with your new password.\nIf you do not request for new password please contact support@com\n '  WHERE `UNIQUE_SCENARIO_NAME`='passwordResetMailToUser' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES = 1;
ALTER TABLE `CANDIDATE_RESUMES` CHANGE COLUMN `TITLE` `TITLE` TEXT NULL DEFAULT NULL;