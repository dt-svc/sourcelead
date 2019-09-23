<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<script type="text/javascript">
	var activeTaxTab = "${activeTaxTab}";
	formId = "${formId}";
	var isEditable = ${isEditable};
</script>
<section class="content-wraper">
	<jsp:include page="../payroll_india/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">
		<div class="tablelist-wrap">
			<div class="col-md-10">
				<div class="clearfix row">
					<div class="col-md-4 dropdown-select mt10 mb10">
						<select class="selectpicker width100percnt" title="Select Form" ${!isEditable ? "disabled='disabled'" : ""}
							data-live-search="true" id="formType">
							<c:forEach items="${formTypes}" var="form">
								<option value="${form.id}" ${formId == form.id ? "selected='selected'" : ""}>${form.name}</option>
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