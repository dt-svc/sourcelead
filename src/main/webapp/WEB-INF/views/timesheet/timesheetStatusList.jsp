<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
 <style>
 
 .clsDatePicker {
   z-index: 100000 !important;
}
 </style>

<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet Status</h2>
          </div>
          <div class="pull-right brud-crum"><a href="/sourcelead/dashboard">Home</a> &raquo;
          <c:if test="${status =='NEW'}">Saved</c:if>
          <c:if test="${status =='SUBMITTEDFORAPPROVAL'}">Waiting</c:if>
          <c:if test="${status =='APPROVED'}">Approved</c:if>
          <c:if test="${status =='REJECTED'}">Rejected</c:if>
          </div>
        </section>
        
		<!-- Project -->
<div class="timesheet_out">
	
	
		 <c:if test="${empty Timesheet_Submissions}">
			<div class="row">
				<div class="col-lg-12">
					<div class="content-area clearfix">
						<div class="blank-pages">
						        <p><i class="fa fa-5x fa-bar-chart"></i></p>
						        <p>No Submissions </p>
						 </div>
					</div>
				</div>
			</div> 
		</c:if>
		<c:if test="${!empty Timesheet_Submissions}">
		
		<div class="timesheet_status_wrap">
		<div class="timesheet_head">
        	<ul>								
                <li><i class="fa fa-filter"></i><span>Approved<b>30</b></span><i class="glyphicon glyphicon-play" id="appro-pop"></i>
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c13"><label for="c13">All</label><b>10</b></li>
							<li><input type="checkbox" id="c14"><label for="c14">Approved</label><b>10</b></li>
							<li><input type="checkbox" id="c15"><label for="c15">Draft</label><b>10</b></li>
							<li><input type="checkbox" id="c16"><label for="c16">Rejected</label><b>10</b></li>
                        </ul>
                    </div>                	
                </li>				               
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
		<div class="timesheet_appr_table">
    	<table>
        	<tbody>
			<thead>
            	<tr>
                	<th>Project name</th>
                    <th>Frequency</th>
                    <th>Total hours</th>
                    <th>Approver</th>
                    <th>Status</th>
                </tr>
			</thead>
				<c:forEach var="report" items="${Timesheet_Submissions}" varStatus="raId">   	
                <tr>
                	<td><div>${report.submissionId.projectMembers.projects.projectName}</div></td>
                	<td><div><fmt:formatDate type="date"  value="${report.submissionId.startDate}" pattern="dd-MM-yyyy"/>   &nbsp; <c:if test="${!empty report.submissionId.endDate}">  - &nbsp;<fmt:formatDate type="date" value="${report.submissionId.endDate}" pattern="dd-MM-yyyy"/></c:if></div></td>
                    <td><div>${report.submissionId.totalHours}</div></td>
                    <td><div>
                    	<c:if test="${!empty report.managerLevel1}">
                    	${report.managerLevel1}
                    	</c:if>
                    	<c:if test="${!empty report.managerLevel2}">
                    	, ${report.managerLevel2}
                    	</c:if>
                    </div></td>
                    <td><div><h3>${report.submissionApprovalStatus}</h3></div>
					</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
	</div>
	
	<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li><a href="#">4</a></li>
	<li><a href="#">5</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>
	</c:if>
		
		
		
	</div>
	
</div>

		
		<!-- Project -->
		
      </section>
      <!-- Content Area Ends--> 


<jsp:include page="../masterFooter.jsp" />

