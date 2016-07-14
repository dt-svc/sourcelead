CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','ORG_DESCRIPTION','TEXT NULL AFTER `FILE_CONTENT_TYPE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','PHONE','VARCHAR(30) NULL AFTER `ZIP_CODE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','FAX','VARCHAR(30) NULL AFTER `PHONE`');