CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','JOIN_DATE','DATETIME NULL  AFTER `END_DATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','WORK_LOCATION','VARCHAR(500) NULL  AFTER `JOIN_DATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACT_REQUEST_DOCUMENTS','DOC_TYPE','VARCHAR(200) NULL  AFTER `FILE_TYPE`');


