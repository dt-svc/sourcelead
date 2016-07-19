CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','TERMINATE_REASON','TEXT NULL AFTER `IS_MAIL_SENT`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','EMAIL','VARCHAR(100) NULL  AFTER `PHONE`');
