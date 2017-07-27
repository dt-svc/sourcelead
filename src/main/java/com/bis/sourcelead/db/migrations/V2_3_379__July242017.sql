CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','ACCOUNT_DETAILS','TEXT NULL  AFTER `BILLING_ADDRESS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE_LINE_ITEMS','INLINE_DESCRIPTION','TEXT NULL  AFTER `CANDIDATE_ID`');
