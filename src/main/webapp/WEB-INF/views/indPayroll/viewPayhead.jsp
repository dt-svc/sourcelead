<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Area -->
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Payhead Summary</h2>
		</div>
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a> &raquo; <a href="<c:url value='${appPath}/payheadList'/>">Payhead List</a> &raquo; Payhead Summary</div>
	</section>
	<div class="clearfix"></div>
	<div class="timesheet_out">
		<div class="tablelist-wrap">
			<div class="clerfix viewlabel-b">
		      <div class="modal-body">
		          
		          <div class="viewgrid-header pull-right zIndex">
		              <a href="<c:url value='/app/payrollPayhead?uuid=${uuid}'/>" class="btn btn-default btn-sm" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>
		          </div>
		          <div class="row">
		              <div class="clearfix">
		              <div class="col-md-5 mb">
		                 <div class="row">
		                  <label class="col-md-4 control-label"><b>Name:</b></label>
		                  <div class="col-md-8">
		                      <span>${payrollPayhead.name}</span>
		                  </div>
		                  </div>
		              </div>
		              <div class="col-md-5 mb">
		                 <div class="row">
		                  <label class="col-md-4 control-label"><b>Short Code:</b></label>
		                  <div class="col-md-8">
		                      <span>${payrollPayhead.shortCode}</span>
		                  </div>
		                  </div>
		              </div>
		
		              <c:if test="${!empty payrollPayhead.effectiveDateStr}">
			              <div class="col-md-5 mb">
			                 <div class="row">
			                  <label class="col-md-4 control-label"><b>Effective Date:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.effectiveDateStr}</span>
			                  </div>
			                  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.type}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Type:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.type}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.payType}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Pay Type:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.payType.name}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.form16}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Form-16:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.form16}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.tax}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Tax:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.tax}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.component}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Component:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.component}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.applicableFor}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Applicable For:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.applicableFor}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${payrollPayhead.applicableFor == 'Experienced'}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Experience:</b></label>
			                  <div class="col-md-8">
			                      <span>
			                      <c:if test="${!empty payrollPayhead.experienceYears}">
			                      		${payrollPayhead.experienceYears} Year(s)
			                      </c:if>
			                      <c:if test="${!empty payrollPayhead.experienceYears && !empty payrollPayhead.experienceMonths}">
			                      		&
			                      </c:if>
			                      <c:if test="${!empty payrollPayhead.experienceMonths}">
			                      		${payrollPayhead.experienceMonths} Month(s)
			                      </c:if>
			                      </span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.paidComponent}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Paid Component:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.paidComponent}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.partOfGratitutiy}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>ComponentPart of Gratitutiy:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.partOfGratitutiy}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.fbpComponent}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>FBP Component:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.fbpComponent}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.leaveDependent}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Leave Dependent:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.leaveDependent}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.showInPayslip}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>Show in Payslip:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.showInPayslip}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.pfApplicable}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>PF Applicable:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.pfApplicable}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.esiApplicable}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>ESI Applicable:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.esiApplicable}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		              <c:if test="${!empty payrollPayhead.ptApplicable}">
			              <div class="col-md-5 mb">
			                  <div class="row">
			                  <label class="col-md-4 control-label"><b>PT Applicable:</b></label>
			                  <div class="col-md-8">
			                      <span>${payrollPayhead.ptApplicable}</span>
			                  </div>
							  </div>
			              </div>
		              </c:if>
		         </div>
		      </div>
			</div>
		</div>   
		<div class="clearfix"></div>
		</div>
	</div>
</section>
	<!-- Content Area Ends-->






	<jsp:include page="../masterFooter.jsp" />