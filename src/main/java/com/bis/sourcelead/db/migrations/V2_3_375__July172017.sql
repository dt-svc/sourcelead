CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','GENERATED_USER_ID','VARCHAR(20) NULL AFTER `STATUS`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','SYSTEM_GENERATED_ID','VARCHAR(20) NULL AFTER `GENERATED_USER_ID`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','GENERATED_ID','VARCHAR(20) NULL AFTER `STATUS`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','GENERATED_ID','VARCHAR(20) NULL AFTER `STATUS`');
