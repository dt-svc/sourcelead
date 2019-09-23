<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="../masterHeader.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<title>Welcome to Source Lead :: Account List</title>
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script> 
</head>

 <script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
 <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script>
		$(document).ready(function() { 
			var type  = '${type}';
			if(type == 'accept'){     
				$("#accept").removeAttr("style");
			}else if(type == 'rejecting'){
				$("#reject").removeAttr("style");
			}
		});
</script>

<script>
  var sourceleadUrl = '${sourceleadUrl}';
</script>

<script type="text/javascript">
		window.onload = function () {
			//getExpensesList();
		 };
</script>

<style>
/* body {
background-color:#e1e6fa;
}
.project_wrap_table table {
	border: 1px solid #fff;
	border-bottom: 1px solid #909090;
	margin-bottom: 10px;
}
.project_wrap_table table tr td:last-child {
width: auto;
} */
</style>
<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/logo-sourceld.png" alt="Source Lead"> </i>
    <h1>Source Lead</h1>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <section class="container">
		<div class="row">
		
		<c:choose>
		    <c:when test="${isSubmittedForApproval eq 'submitted for approval' || isSubmittedForApproval eq 'rejected'}">
		        <div id="accept" style="display:none">
				          <div class="col-md-8 col-md-offset-2 inner-widget success-user">
					          <i class="fa fa-check-circle fa-3x"></i>
							  <p class="reason-msg">Already Performed Action </p>
							  <p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
						  </div>
		  		</div>
		    </c:when>    
		    <c:otherwise>
		        <div id="accept" style="display:none">
				          <div class="col-md-8 col-md-offset-2 inner-widget success-user">
					          <i class="fa fa-check-circle fa-3x"></i>
							  <p class="reason-msg">Successfully Employee Expense can be edited</p>
							  <p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
						  </div>
		  		</div>
		    </c:otherwise>
		</c:choose>
		
		<c:choose>
		    <c:when test="${isSubmittedForApproval eq 'submitted for approval' || isSubmittedForApproval eq 'rejected'}">
		        <div id="reject" style="display:none">
		  			<form id="reject_form" name="reject_form" method="post" >
					          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
								  <div class="col-md-6">
									  <i class="fa fa-ban fa-3x"></i>
									  <p class="reason-msg">Your decision is already submitted once </p>
							  		  <p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
								  </div>
							  </div>
					</form>							  
		  		</div>
		    </c:when>    
		    <c:otherwise>
		        <div id="reject" style="display:none">
		  			<form id="reject_form" name="reject_form" method="post" >
					          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
								  <div class="col-md-6">
									  <i class="fa fa-ban fa-3x"></i>
									  <p class="reason-msg">Drop a reason for rejecting request of employee Expense editing</p>
								  </div>
								  <div class="col-md-6" id="rejectIdDiv">
									  <div class="col-md-12" id="reasonErrorDiv">
										  <label>Reason for Reject</label>
										  <textarea class="form-control" rows="2" placeholder="Reason" id="rejectId" data-validation="required" field-name="Reason"></textarea>	
									  </div>
									  <div class="col-md-12 mt10"><button type="button" class="btn btn-sm btn-danger pull-right" onclick="saveRejectedReason();">Submit</button></div>
								  	
								  </div>
							  </div>
					</form>							  
		  		</div>
		    </c:otherwise>
		</c:choose>
		
		
		  		
		 </div>
  </section>
  <!-- Footer Area -->
  <footer class="footer-publicpages clearfix">
  <div class="container">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div></div>
  </footer>
  <!-- Footer Area --> 
</div>
</body>
</html>


<script type="text/javascript">
	function saveRejectedReason() {
		if ($('#reject_form').validate(false, validationSettings)){
			var expenseId = '${expenseId}';
			var reOpenedUserId = '${reOpenedUserId}';
			var type = 'reject';
			var reason = '${reason}';
			var orgLocation = '${orgLocId}';
			var frm = $('#reject_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			var rejectReason = $('#rejectId').val();
			con = con.replace(/[{}]/g, "");
			
			$.ajax({
						type : "GET",
						url : sourceleadUrl+"/ws/acceptOrRejectReopenExpense?"+tokenParamName+"="+tokenValue+"&type="+type+"&expenseId="+expenseId+"&reOpenedUserId="+reOpenedUserId+"&reason="+reason+"&orgLocId="+orgLocation+"&rejectReason="+rejectReason,
						success : function(data) {
							if("success"){
								$('#rejectIdDiv').empty();
								$('#rejectIdDiv').append("<span>Your Rejected Reason is submitted successfully !!</span>");	
							}
							
						},
					});
		}else{
			$('#reasonErrorDiv').find('p.jquery_form_error_message').css("color", "yellow");
		}
	} 
</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

