CALL sourcelead.AlterTableRemoveColumn('sourcelead','REQUIREMENT_INTERVIEW','COUNTRY');
CALL sourcelead.AlterTableRemoveColumn('sourcelead','REQUIREMENT_INTERVIEW','STATE');
CALL sourcelead.AlterTableRemoveColumn('sourcelead','REQUIREMENT_INTERVIEW','CITY');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_INTERVIEW','LOCATION','TEXT NULL AFTER `END_TIME`');