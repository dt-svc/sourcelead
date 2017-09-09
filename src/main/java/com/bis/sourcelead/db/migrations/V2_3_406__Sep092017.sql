
CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','FILE_URL','VARCHAR(2000) NULL AFTER `DOC_FILENAME`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','EXPENSES_ENTRIES_DOCUMENTS','FILEURL','VARCHAR(2000) NULL AFTER `UPDATED_BY`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_RESUMES','FILE_URL','VARCHAR(4000) NULL AFTER `RESUME_UUID`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_RESUMES_HISTORY','FILE_URL','VARCHAR(4000) NULL AFTER `RESUME_UUID`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','FILE_URL','VARCHAR(4000) NULL AFTER `IS_GENERATE_AUTOMATIC_INVOICE`');