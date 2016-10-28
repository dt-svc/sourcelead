CALL sourcelead.AlterTableRemoveColumn ('sourcelead','REQUIREMENT_INTERVIEW','START_TIME');
CALL sourcelead.AlterTableRemoveColumn ('sourcelead','REQUIREMENT_INTERVIEW','END_TIME');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_INTERVIEW','START_TIME','DATETIME NULL AFTER `DATE_OF_INTERVIEW`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_INTERVIEW','END_TIME','DATETIME NULL AFTER `START_TIME`');