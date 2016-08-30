CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EMPLOYERS','STARTMONTH','VARCHAR(10) NULL AFTER `ADDRESS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EMPLOYERS','ENDMONTH',' VARCHAR(10) NULL AFTER `STARTDATE`');


CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EMPLOYERS','STARTYEAR','VARCHAR(10) NULL AFTER `STARTMONTH`')
CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_EMPLOYERS','ENDYEAR',' VARCHAR(10) NULL AFTER `ENDMONTH`')
