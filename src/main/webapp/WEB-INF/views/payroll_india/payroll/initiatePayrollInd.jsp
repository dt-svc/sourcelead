<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Run Payroll</h2>
</div>
   <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;Run Payroll</div>
  </section>
		<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
<div class="project_wrap">
<div class="clearfix project-tabs-menu">
	<ul class="nav nav-tabs newTabs">
		<li class="current"><a onclick="reviewPayrollDiv();">Initiate Payroll</a></li>
		<li class=""><a onclick="#">Payroll</a></li>
		<li class=""><a onclick="#">Groups</a></li>
		<li class=""><a href="<c:url value='${appPath}/forms/list'/>" title="Forms">Forms</a></li>
	</ul>
   </div>
   </div>

<div class="clearfix"></div>
<div class="timesheet_out" id="reviewPayrollDiv">
<div class="tablelist-wrap">
<div class="timesheet_head minHeight_225">
<form:form id="generate_payroll_form" name="generate_payroll_form" method="post">
<div class="col-md-10 mt">
	<div class="row">
		<div class="col-md-3">
			<div class="dropdown-select">
			<select id="groupId" name="groupId" onchange="getGroupFrequency();" data-placeholder="Select User Group" class="chosen-select" data-validation="required" field-name="User Group">
                    <option value=""></option>
                    <c:forEach var="ug" items="${userGroupList}">
					<option value="${ug.groupUUID},${ug.id}">${ug.groupName}</option>
				   </c:forEach>
			</select>
			</div>
		</div>
		<div class="col-md-3 paddingLeft paddingRight">
			<select id="payPeriod" name="payPeriod" data-validation="required" data-placeholder="Select Pay Period" field-name="Pay Period" class="chosen-select">
			</select>
		</div>
		<div class="col-md-2 paddingLeft paddingRight" id="payTypeIdDiv">
			<select id="payType" name="payType" data-validation="required" data-placeholder="Select Pay type" field-name="Pay Type" class="chosen-select" onchange="errPayType();">
				 <option value=""></option>
				 <option value="All">All</option>
				 <option value="HourlyRate">Hourly Rate</option>
                 <option value = "Salary">Salary</option>
			</select>
		</div>
		
		<div class="col-md-2 paddingRight">
			<input name="" class="form-control calendar" placeholder="Check Date" id="payrollPayDate" name="payrollPayDate" type="text" data-validation="required" field-name="Pay Date">
		</div>
		<div class="col-md-2">
			<a  class="btn btn-success btnSuccess btn-sm anchorWhite" id="generateUSPayroll" onclick="reviewIndPayroll();">Initiate Payroll</a>
		</div>
	</div>						
</div>
<div class="clearfix"></div>
</form:form>
</div>
</div>
</div>
</section>
<script>
function getGroupFrequency(){
	var groupUUID = $("#groupId").val();
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getGroupPayPeriod?"+tokenParamName+"="+tokenValue+"&groupUUID="+groupUUID                  , 
	        success: function(json) {
	        var output = "";
			for (var i=0; i<json.length; i++)
			{
				var date = json[i].replace(",", " to ");
				output = output + "<option value='" +json[i]+ "'>"+date+"</option>";
			}
			$("#payPeriod").empty();
			$("#payPeriod").append(output);
			$('#payPeriod').trigger("chosen:updated"); 
	        },
	    });
}
</script>
<script>
	$(function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormat = getJSDateFormat(locationDate);
		  $('#payrollPayDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		  });
	});
</script>
<script>
function reviewIndPayroll(){
	location.replace(sourceleadUrl+"/reviewPayrollInd");
}
</script>
<jsp:include page="../../masterFooter.jsp" />