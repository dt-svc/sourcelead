<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Add Form</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/runIndianPayRoll'/>">Run Payroll</a> &raquo; Add Form
		</div>
	</section>
	<div class="timesheet_out">
		<div class="tablelist-wrap">
			<div class="col-md-10">
				<div class="clearfix row">
					<div class="col-md-4 dropdown-select mt10 mb10">
						<select class="selectpicker width100percnt" title="Select Form"
							data-live-search="true" id="formType" name="uuid">
							<c:forEach items="${formList}" var="form">
								<option value="${form.uuid}"
									${formId == form.id ? "selected='selected'" : ""}>${form.name}</option>
							</c:forEach>
						</select>
					</div>

				</div>
			</div>
			<div class="clearfix"></div>
			<!-- add dynamic forms here -->

			<div id="dynamicForm">
				<div class="blankForm">
					<div class="clearfix mt40"></div>
					<i class="fa fa-file-code-o mb fa-3x text-muted"></i>
					<div class="clearfix"></div>
					<p class="text-muted mt5">Please select form type !</p>
				</div>
			</div>
			<%-- <jsp:include page="../tax/itDeclarationForm.jsp"></jsp:include> --%>
		</div>
	</div>
</section>
<script type="text/javascript">
	$("#formType").change(
			function() {
				location.href = sourceleadUrl + "/forms/getForm?uuid="
						+ $(this).val();
			});
</script>