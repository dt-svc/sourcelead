CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_DOCUMENTS','FILE_URL','VARCHAR(2000) NULL AFTER `FILE_NAME`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_REFERENCES','FILE_URL','VARCHAR(2000) NULL AFTER `STATUS`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EMPLOYERS','FILE_URL','VARCHAR(2000) NULL AFTER `STATUS`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_DOCUMENTS','FILE_URL','VARCHAR(2000) NULL AFTER `UPDATED_BY`');