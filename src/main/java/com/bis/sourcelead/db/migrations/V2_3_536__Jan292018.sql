CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','FILE_NAME','VARCHAR(250) NULL AFTER `MODULES_ID`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','FILE_URL','VARCHAR(2000) NULL AFTER `FILE_NAME`');
