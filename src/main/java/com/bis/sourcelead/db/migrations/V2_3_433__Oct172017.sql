CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','EXTENSION_NUMBER','VARCHAR(10) NULL  AFTER `MOBILE_PHONE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','PHONE_NUMBER','VARCHAR(20) NULL  AFTER `NEWLY_LOGIN_USER`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','EXTENSION_NUMBER','VARCHAR(10) NULL  AFTER `PHONE_NUMBER`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','BULK_CLIENT_DETAILS','EXTENSION_NUMBER','VARCHAR(10) NULL  AFTER `PHONE`');




