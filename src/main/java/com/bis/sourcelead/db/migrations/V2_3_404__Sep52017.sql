CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_DOCUMENTS','FILE_URL','VARCHAR(2000) NULL AFTER `FILE_NAME`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_REFERENCES','FILE_URL','VARCHAR(2000) NULL AFTER `STATUS`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EMPLOYERS','FILE_URL','VARCHAR(2000) NULL AFTER `STATUS`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_RESUMES','FILE_URL','VARCHAR(2000) NULL AFTER `RESUME_UUID`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_RESUMES_HISTORY','FILE_URL','VARCHAR(2000) NULL AFTER `RESUME_UUID`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','FILE_URL','VARCHAR(2000) NULL AFTER `STATUS`');