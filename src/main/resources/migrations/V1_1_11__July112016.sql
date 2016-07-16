CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','DOC_FILE','MEDIUMBLOB NULL  AFTER `STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','FILE_CONTENT_TYPE','VARCHAR(50) NULL  AFTER `DOC_FILE`');


CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','JOB_TITLE','VARCHAR(100) NULL  AFTER `USER_CODE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','LOCATION','TEXT NULL  AFTER `JOB_TITLE`');