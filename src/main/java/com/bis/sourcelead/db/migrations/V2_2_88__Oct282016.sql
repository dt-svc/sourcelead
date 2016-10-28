
CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_OFFERS','Notify_To_Vendor','VARCHAR(10) NULL AFTER `STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_OFFERS','Notify_To_Applicant','VARCHAR(10) NULL AFTER `Notify_To_Vendor`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_OFFERS','Rejected_Reason','TEXT NULL AFTER `Notify_To_Applicant`');


