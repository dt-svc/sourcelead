<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/jquery.dataTables.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/chosen.min.css">
<link href="css/bootstrap-select.min.css" rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
<link href="css/jquery-ui.css" rel="stylesheet">
<link href="css/sourcelead-main.css" rel="stylesheet">
<link href="css/timesheet.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum">Home &raquo; Applicant List</div>
    </section>
    
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
        <div class="timesheet_head">
        <div class="pull-left ml mt10">
				<p><span class="select-fild">
				    <select id="reqList">
                  		<option value="appList" selected="selected" onclick="loadRequirementApplicants();" >Applicant List</option>
                        <option value="subList" onclick="loadRequirementSubmissions();">Submission List</option> 
                        <option value="interList" onclick="loadRequirementInterviews();">Interview List</option>
                        <option value="offerlist"  onclick="loadRequirementOffers();">Offer List</option> 
                    </select>
				</span>
				</p>
			</div>
			 <div class="pull-left ml mt10">
				<p><span class="select-fild">
				    <c:if test="${tab == 'today'}">
				    <select id="tabList" onchange="tabsList(this);" >
                  		<option value="today" selected="selected">Today</option>
                        <option value="previous">Previous</option> 
                    </select>
                </c:if>
                <c:if test="${tab == 'previous'}">
				    <select id="tabList" onchange="tabsList(this);" >
                  		<option value="today">Today</option>
                        <option value="previous" selected="selected">Previous</option> 
                    </select>
                </c:if>
				</span>
				</p>
			</div>

          <div class="clearfix"></div>
          <div class="remove_search hidden">
            <input type="text" placeholder="search" />
          </div>
        </div>
        <div class="clients_wrap_table datatable_filter">
          <table id="appTable">
            
            <thead>
              <tr>
                <th>Applicant name</th>
                <th>Job</th>
                <th>Client</th>
                <th>Email</th>
                <th>Phone Number<br>
                <th>Primary Skill</th>
                <th>Source</th>
                 <th>Score</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="reqSub" items="${toDayReqApplicantscount}">
               <tr>
			 <td><div><span>${reqSub.candidate.firstName}</span></div></td>
			  <sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')"> 
			 	<td><div class="proj_show"><span><a href='<c:url value='/requirementSummary?requirementUUID=${reqSub.requirement.requirementUUID}'/>'>${reqSub.requirement.jobTitle}<i class='fa fa-binoculars'></i></a></span></div></td>
            </sec:authorize>
            <sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">
            	<td><div><span>${reqSub.requirement.jobTitle}</span></div></td>
            </sec:authorize>
             <%--  <td><div><span>${reqSub.requirement.jobTitle}</span></div></td> --%>
              <td><div><span>${reqSub.requirement.orgLocationId.org.orgName}</span></div></td>
              <td><div><span>${reqSub.candidate.email}</span></div></td>
              <td><div><span>
	              <c:if test="${not empty reqSub.candidate.mobilePhone}">
	             	 ${reqSub.candidate.mobilePhone}
	              </c:if>
	              <c:if test="${empty reqSub.candidate.mobilePhone}">
	             	 --
	              </c:if>
              </span></div>
              </td>
              
              <td><div><span>
	               <c:if test="${not empty reqSub.candidate.primarySkill}">
	            	  ${reqSub.candidate.primarySkill}
	              </c:if>
	              <c:if test="${empty reqSub.candidate.primarySkill}">
	             	 --
	              </c:if>
              </span></div></td>
         
              
               <td><div><span>
	               <c:if test="${not empty reqSub.vendorLocationId.org.orgName}">
	            	  ${reqSub.vendorLocationId.org.orgName}
	              </c:if>
	              <c:if test="${empty reqSub.vendorLocationId.org.orgName}">
	             	 --
	              </c:if>
              </span></div></td>
              
               <td><div><span>
	               <c:if test="${not empty reqSub.score}">
	            	  ${reqSub.score}
	              </c:if>
	              <c:if test="${empty reqSub.score}">
	             	 --
	              </c:if>
              </span></div></td> 
            
               </tr>
			  </c:forEach>
            </tbody>
            
          </table>
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  <script>
function pagination1(){ 
    $('#appTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    })
}
</script>
<script type="text/javascript">
window.onload = function () {
	pagination1();
 };
</script>

	<script type="text/javascript">
	function loadRequirementApplicants(){
		var tab = document.getElementById("tabList").value;
		var sourceleadUrl = '${sourceleadUrl}';
		window.location.href = sourceleadUrl + "/requirementApplicantsList?&tab="+tab;
	}
	function loadRequirementSubmissions(){
		var tab = document.getElementById("tabList").value;
		var sourceleadUrl = '${sourceleadUrl}';
		window.location.href = sourceleadUrl + "/requirementSubmissionsList?&tab="+tab;
	}
	function loadRequirementInterviews(){
		var tab = document.getElementById("tabList").value;
		var sourceleadUrl = '${sourceleadUrl}';
		window.location.href = sourceleadUrl + "/requirementInterviewsList?&tab="+tab;
	}
	function loadRequirementOffers(){
		var tab = document.getElementById("tabList").value;
		var sourceleadUrl = '${sourceleadUrl}';
		window.location.href = sourceleadUrl + "/requirementOffersList?&tab="+tab;
	}

	function tabsList(sel){
		var tab = sel.value;
		var reqList = document.getElementById("reqList").value;
		if(reqList=="appList"){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + "/requirementApplicantsList?&tab="+tab;
		}
		if(reqList=="subList"){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + "/requirementSubmissionsList?&tab="+tab;
			 }
		if(reqList=="interList"){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + "/requirementInterviewsList?&tab="+tab;
		}
		if(reqList=="offerlist"){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + "/requirementOffersList?&tab="+tab;
		}
	}
	</script> 