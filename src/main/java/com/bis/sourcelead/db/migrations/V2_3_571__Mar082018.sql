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

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='Invite User';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Manager Assigned To Project';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Team Assigned To Project';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Time Sheet Submission';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Timesheet Approved';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='TimesheetRejected';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Timesheet Rejected';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Mail To Team Members';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Mail To Project Manager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Mail To Manager At Timesheet Submission';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Timesheet Approved By Approver';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Timesheet Rejected By Approver';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='9' WHERE UNIQUE_SCENARIO_NAME ='Invite Vendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Invite Client';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='9' WHERE UNIQUE_SCENARIO_NAME ='Accept Vendor Invitation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Accept Client Invitation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='Week Time Sheet Submission';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Release Mechanism';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Requirement Posting';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='AssignToTeamMembers';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Approval Mechanism';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Approval Mechanism Sequential';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Approved Requirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Rejected Mail About The Requirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Applicant Interview';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Interviewer Invitation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Submit To Client';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Vendors Interview';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ShortList Applicants';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Edit SpocContact';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Forward the Mail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Client to Vendor Acceptence';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Vendor Offer Reject';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorAcceptedOffer';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantReceiveMailAboutRequirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptRequirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantRejectanceRequirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantNegotiateRequirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Interviewer Template';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='UserRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ClientRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='UserAcceptRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptanceByManagerOfRequirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='UserRejectRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ClientAcceptRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorAcceptRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ClientRejectRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorRejectRateNegotiation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='Task Mechanism';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='9' WHERE UNIQUE_SCENARIO_NAME ='Accept Client';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Accept Vendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='9' WHERE UNIQUE_SCENARIO_NAME ='Reject Client';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='Reject Vendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptRate';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='RejectMailFromClient_To_Vendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='ReopenTimeSheet';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='AcceptReopenTimeSheet';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='RejectReopenTimeSheet';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ShortlistMailFromClient_To_Vendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Editprojecttomanager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Editprojecttoteammember';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ModifiedRequirementStatusMailtoVendorContact';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ModifiedRequirementStatusNotificationtoVendorContact';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Interviewer InvitationRedifined';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptMailToInterviewCreator';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantRejectMailToInterviewCreator';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantTentativeMailToInterviewCreator';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorApplicantAcceptMailToInterviewCreator';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorApplicantRejectMailToInterviewCreator';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorApplicantTentativeMailToInterviewCreator';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='updatedRequirementNotificationToVendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Offer Accepted';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Offer Accepted Notification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Offer Rejected';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Offer Rejected Notification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Notification To Client';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Notification To Manager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='updatedFeedbackNotificationToRequirementOwner';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='updatedRequirementEmailToManager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='updatedRequirementNotificationToManager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='RecruiterWillReceiveMailAboutTheAcceptanceOfRequirementByManager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='interviewRescheduleMailToApplicant';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='notifyApplicantMailAboutRequirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='Employee Termination';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='Employee Termination Mail To Manager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Employee Reassign';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='7' WHERE UNIQUE_SCENARIO_NAME ='clientOwnerUpdateMail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='approverUpdatedContract';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='UserRoleChanged';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='Approve Contract';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='Reject Contract';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='Contract Mail To Owner';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceClientAcceptEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='UserNewActionAssignedAsaApprover';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='UserNewActionAssignedAsaTeamOwnerUser';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='UserNewActionAssignedAsaReportingManager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='UserNewActionAssignedAsaProjectApproverUser';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceClientPayableEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceAcceptMailtoClient';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceAcceptMailtoVendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceRejectMailtoVendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceRejectMailtoClient';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='10' WHERE UNIQUE_SCENARIO_NAME ='CandidateInvitation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='contractExpiryRemainder';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='contractDocumentsReceived';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='JobAlerts';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ProposeNewTime';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='VendorProposeNewTime';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesEmailToManager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesApprovalManagerEmailToEmployee';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesApprovalRejectedEmailToEmployee';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesReopenEmailToSuperAdmin';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ReopenApprovedExpenseEmailToEmployee';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ReopenApprovedExpenseEmailToApprovers';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAuthorizationManagerAcceptance';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='19' WHERE UNIQUE_SCENARIO_NAME ='OffBoardingProcessApplicantTermination';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='10' WHERE UNIQUE_SCENARIO_NAME ='RequestForDocumentsToCandidates';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='10' WHERE UNIQUE_SCENARIO_NAME ='UploadedRequestForDocumentsToCandidates';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Vendors Interview Reschedule';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='ContractSigned';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceClientPayableReminderEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='20' WHERE UNIQUE_SCENARIO_NAME ='InvoiceClientReceivableReminderEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='MSAFinalizeEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='SOWFinalizeEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='NDAOrNSAFinalizeEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='ModifyContractEndDate';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='ModifySowContractEndDate';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ReopenRejectExpenseEmailToApprovers';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='ResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='New Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='NewResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Accept Single New Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='AcceptSingleNewResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Reject Single New Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Accept Bulk New Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='AcceptBulkNewResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Reject Bulk New Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='RejectBulkNewResourceRequesttNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='OnHold Bulk New Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='OnHoldBulkNewResourceRequesttNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Accept Bulk Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='AcceptBulkResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Reject Bulk Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='RejectBulkResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='OnHold Bulk Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='OnHoldBulkResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Accept Single Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='AcceptSingleResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Reject Single Resource Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='RejectSingleResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='entity_type_rescheduled_email';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='Rejected Leave';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='RejectedLeaveNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='Approver Leave';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='ApproverLeaveNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='Withdraw Leave';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='WithdrawLeaveNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='LeaveEmailToApprovers';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='LeaveNotificationToApprovers';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='LeaveCancelEmailToUser';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='LeaveCancelNotificationToUser';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Budget Request';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='BudgetRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Budget Request Accepted';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='BudgetRequestAcceptedNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Budget Request Rejected';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='BudgetRequestRejectedNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='Budget Request OnHold';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='BudgetRequestOnHoldNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='TimesheetApprovedEmailToVendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='4' WHERE UNIQUE_SCENARIO_NAME ='TimesheetRejectEmailToVendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesApproveEmailToVendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='23' WHERE UNIQUE_SCENARIO_NAME ='ExpensesRejectEmailToVendor';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='19' WHERE UNIQUE_SCENARIO_NAME ='OffBoardingProcessMailToReportingManager';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='19' WHERE UNIQUE_SCENARIO_NAME ='OffBoardingApplicantDealayProcess';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='UserTerminatedEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='2' WHERE UNIQUE_SCENARIO_NAME ='UserTerminatedNotificationEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantAcceptOffer';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='ApplicantRejectOffer';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='SendProjectDocuments';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='SendContract';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='SendSowContract';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='RejectSingleNewResourceRequestNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='connectionInvitation';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Share Recomended Applicant';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Guest Applied To Requirement';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='OnBehafLeaveEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='OnBehafLeaveNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='onBehalfEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='orgRequestEmail';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='3' WHERE UNIQUE_SCENARIO_NAME ='MailtoAssignEmployeeinProjecttask';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='ModifyContractEndDateNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='MsaFinalizedNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE UNIQUE_SCENARIO_NAME ='SowFinalizedNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='onBehalfNotification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='8' WHERE UNIQUE_SCENARIO_NAME ='Share Recommended Applicant Notification';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `TAB_ID`='1' WHERE UNIQUE_SCENARIO_NAME ='HolidayRemainderNotification';

SET SQL_SAFE_UPDATES = 1;






