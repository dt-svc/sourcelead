CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EMPLOYERS','STARTMONTH','VARCHAR(10) NULL AFTER `ADDRESS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EMPLOYERS','ENDMONTH',' VARCHAR(10) NULL AFTER `STARTDATE`');
