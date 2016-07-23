CALL sourcelead.AlterTableAddColumn ('sourcelead','TIMESHEET_ENTRIES','DOC_FILE','MEDIUMBLOB NULL  AFTER `STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','TIMESHEET_ENTRIES','FILE_CONTENT_TYPE','VARCHAR(50) NULL  AFTER `DOC_FILE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','TIMESHEET_ENTRIES','FILE_NAME','VARCHAR(50) NULL  AFTER `FILE_CONTENT_TYPE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','TIMESHEET_ENTRIES','WEEK_COMMENTS','TEXT NULL  AFTER `FILE_NAME`');
