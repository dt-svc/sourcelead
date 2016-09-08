
CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','TERMINATE_REASON','VARCHAR(500) NULL AFTER `STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_SKILLS','STATUS','VARCHAR(20) NULL AFTER `SCALE`');