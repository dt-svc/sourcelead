CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','TYPE','VARCHAR(50) NULL  AFTER `APPROVER_USER_ID`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','FILE_NAME','TEXT NULL  AFTER `TYPE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','DOC_FILE','MEDIUMBLOB NULL  AFTER `FILE_NAME`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','FILE_TYPE','VARCHAR(250) NULL  AFTER `DOC_FILE`');


