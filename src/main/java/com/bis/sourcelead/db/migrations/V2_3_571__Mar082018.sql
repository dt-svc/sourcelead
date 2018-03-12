CALL sourcelead.AlterTableAddColumn ('sourcelead','EMAIL_SCENARIO','TAB_ID','BIGINT NULL AFTER `USE_LOGO`');
ALTER TABLE `sourcelead`.`EMAIL_SCENARIO` ADD CONSTRAINT `FK1_TAB_ID`  FOREIGN KEY  (`TAB_ID`)  REFERENCES  `sourcelead`.`TABS` (`ID`);


DROP TABLE IF EXISTS `sourcelead`.`USER_MAIL_UNSUBSCRIBE`;
CREATE TABLE `sourcelead`.`USER_MAIL_UNSUBSCRIBE` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  USER_ID BIGINT DEFAULT NULL,
  ORG_LOCATION_ID BIGINT DEFAULT NULL,
  TAB_IDS TEXT DEFAULT NULL,
  STATUS INT DEFAULT NULL,
  CREATED_DATE DATETIME,
  UPDATED_DATE DATETIME,
  UPDATED_BY BIGINT DEFAULT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY(USER_ID) REFERENCES `sourcelead`.`USER`(ID),
  FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `sourcelead`.`ORG_LOCATIONS`(ID),
  FOREIGN KEY(UPDATED_BY) REFERENCES `sourcelead`.`USER`(ID)
) ;


SET SQL_SAFE_UPDATES = 0;

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='Invite User';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Manager Assigned To Project';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Team Assigned To Project';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Time Sheet Submission';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Timesheet Approved';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='TimesheetRejected';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Timesheet Rejected';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Mail To Team Members';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Mail To Project Manager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Mail To Manager At Timesheet Submission';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Timesheet Approved By Approver';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Timesheet Rejected By Approver';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='9' WHERE UNIQUE_SCENARIO_NAME ='Invite Vendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Invite Client';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='9' WHERE UNIQUE_SCENARIO_NAME ='Accept Vendor Invitation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Accept Client Invitation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Week Time Sheet Submission';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Release Mechanism';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Requirement Posting';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='AssignToTeamMembers';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Approval Mechanism';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Approval Mechanism Sequential';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Approved Requirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Rejected Mail About The Requirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Applicant Interview';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Interviewer Invitation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Submit To Client';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Vendors Interview';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ShortList Applicants';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Edit SpocContact';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Forward the Mail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Client to Vendor Acceptence';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Vendor Offer Reject';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorAcceptedOffer';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantReceiveMailAboutRequirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptRequirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantRejectanceRequirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantNegotiateRequirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Interviewer Template';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='UserRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ClientRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='UserAcceptRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptanceByManagerOfRequirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='UserRejectRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ClientAcceptRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorAcceptRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ClientRejectRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorRejectRateNegotiation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='Task Mechanism';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='9' WHERE UNIQUE_SCENARIO_NAME ='Accept Client';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Accept Vendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='9' WHERE UNIQUE_SCENARIO_NAME ='Reject Client';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Reject Vendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptRate';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='RejectMailFromClient_To_Vendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='ReopenTimeSheet';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='AcceptReopenTimeSheet';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='RejectReopenTimeSheet';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ShortlistMailFromClient_To_Vendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Editprojecttomanager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Editprojecttoteammember';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ModifiedRequirementStatusMailtoVendorContact';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ModifiedRequirementStatusNotificationtoVendorContact';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Interviewer InvitationRedifined';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptMailToInterviewCreator';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantRejectMailToInterviewCreator';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantTentativeMailToInterviewCreator';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorApplicantAcceptMailToInterviewCreator';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorApplicantRejectMailToInterviewCreator';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorApplicantTentativeMailToInterviewCreator';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='updatedRequirementNotificationToVendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Offer Accepted';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Offer Accepted Notification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Offer Rejected';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Offer Rejected Notification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Notification To Client';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Notification To Manager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='updatedFeedbackNotificationToRequirementOwner';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='updatedRequirementEmailToManager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='updatedRequirementNotificationToManager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='RecruiterWillReceiveMailAboutTheAcceptanceOfRequirementByManager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='interviewRescheduleMailToApplicant';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='notifyApplicantMailAboutRequirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='Employee Termination';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='Employee Termination Mail To Manager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Employee Reassign';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='clientOwnerUpdateMail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='approverUpdatedContract';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='UserRoleChanged';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='Approve Contract';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='Reject Contract';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='Contract Mail To Owner';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceClientAcceptEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='UserNewActionAssignedAsaApprover';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='UserNewActionAssignedAsaTeamOwnerUser';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='UserNewActionAssignedAsaReportingManager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='UserNewActionAssignedAsaProjectApproverUser';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceClientPayableEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceAcceptMailtoClient';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceAcceptMailtoVendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceRejectMailtoVendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceRejectMailtoClient';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='10' WHERE UNIQUE_SCENARIO_NAME ='CandidateInvitation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='contractExpiryRemainder';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='contractDocumentsReceived';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='JobAlerts';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ProposeNewTime';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorProposeNewTime';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesEmailToManager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesApprovalManagerEmailToEmployee';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesApprovalRejectedEmailToEmployee';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesReopenEmailToSuperAdmin';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ReopenApprovedExpenseEmailToEmployee';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ReopenApprovedExpenseEmailToApprovers';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAuthorizationManagerAcceptance';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='19' WHERE UNIQUE_SCENARIO_NAME ='OffBoardingProcessApplicantTermination';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='10' WHERE UNIQUE_SCENARIO_NAME ='RequestForDocumentsToCandidates';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='10' WHERE UNIQUE_SCENARIO_NAME ='UploadedRequestForDocumentsToCandidates';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Vendors Interview Reschedule';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='ContractSigned';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceClientPayableReminderEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceClientReceivableReminderEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='MSAFinalizeEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='SOWFinalizeEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='NDAOrNSAFinalizeEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='ModifyContractEndDate';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='ModifySowContractEndDate';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ReopenRejectExpenseEmailToApprovers';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='ResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='New Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='NewResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Accept Single New Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='AcceptSingleNewResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Reject Single New Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Accept Bulk New Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='AcceptBulkNewResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Reject Bulk New Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='RejectBulkNewResourceRequesttNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='OnHold Bulk New Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='OnHoldBulkNewResourceRequesttNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Accept Bulk Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='AcceptBulkResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Reject Bulk Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='RejectBulkResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='OnHold Bulk Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='OnHoldBulkResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Accept Single Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='AcceptSingleResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Reject Single Resource Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='RejectSingleResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='entity_type_rescheduled_email';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='Rejected Leave';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='RejectedLeaveNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='Approver Leave';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='ApproverLeaveNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='Withdraw Leave';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='WithdrawLeaveNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='LeaveEmailToApprovers';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='LeaveNotificationToApprovers';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='LeaveCancelEmailToUser';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='LeaveCancelNotificationToUser';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Budget Request';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='BudgetRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Budget Request Accepted';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='BudgetRequestAcceptedNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Budget Request Rejected';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='BudgetRequestRejectedNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Budget Request OnHold';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='BudgetRequestOnHoldNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='TimesheetApprovedEmailToVendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='TimesheetRejectEmailToVendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesApproveEmailToVendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesRejectEmailToVendor';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='19' WHERE UNIQUE_SCENARIO_NAME ='OffBoardingProcessMailToReportingManager';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='19' WHERE UNIQUE_SCENARIO_NAME ='OffBoardingApplicantDealayProcess';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='UserTerminatedEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='UserTerminatedNotificationEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptOffer';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantRejectOffer';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='SendProjectDocuments';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='SendContract';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='SendSowContract';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='RejectSingleNewResourceRequestNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='connectionInvitation';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Share Recomended Applicant';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Guest Applied To Requirement';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='OnBehafLeaveEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='OnBehafLeaveNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='onBehalfEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='orgRequestEmail';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='MailtoAssignEmployeeinProjecttask';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='ModifyContractEndDateNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='MsaFinalizedNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='SowFinalizedNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='onBehalfNotification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Share Recommended Applicant Notification';

UPDATE `sourcelead`.`email_scenario` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='HolidayRemainderNotification';

SET SQL_SAFE_UPDATES = 1;






