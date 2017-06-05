CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','NEWLY_LOGIN_USER','VARCHAR(10) NULL');

SET SQL_SAFE_UPDATES = 0;
update sourcelead.ACTION_ROLES set ORGLOCATION_ID = 1 where ORG_ID = 1;
SET SQL_SAFE_UPDATES = 1;