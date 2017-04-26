

CALL sourcelead.AlterTableAddColumn ('sourcelead','EMPLOYEE','CURRENT_CURRENCY','VARCHAR(45) NULL  AFTER `UPDATED_BY`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','EMPLOYEE','EMPLOYMENT_TYPE','VARCHAR(45) NULL  AFTER `DAILY_HOURS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','EMPLOYEE','DESIGNATION_OTHER_TYPE','VARCHAR(45) NULL  AFTER `EMPLOYMENT_TYPE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','EMPLOYEE','ORG_RELATION_ID','BIGINT(20) NULL  AFTER `DESIGNATION_OTHER_TYPE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','EMPLOYEE','SPOC_ID','BIGINT(20) NULL  AFTER `ORG_RELATION_ID`');
