CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','CONTRACT_STATUS','VARCHAR(50) NULL  AFTER `APPROVER_USER_ID`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','ENVELOP_ID','TEXT NULL  AFTER `CONTRACT_STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','DOC_ID','TEXT NULL  AFTER `ENVELOP_ID`');