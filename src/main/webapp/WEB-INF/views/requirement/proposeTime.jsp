<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: Account List</title>
<!-- Bootstrap -->
<link rel="stylesheet" href="/resources/css/jquery.timepicker.css">
<link href="/resources/css/jquery-ui.css" rel="stylesheet">
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet">
<link href="/resources/css/timesheet.css" rel="stylesheet">
</head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <script src="/resources/js/jquery.dataTables.min.js"></script>
 
<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>  
<script src="/resources/js/chosen.jquery.min.js"></script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/jquery-fun.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
<script src="/resources/js/bootstrap-select.min.js"></script>
<script src="/resources/js/themeswitcher.js"></script>
<script src="/resources/js/moment.min.js"></script>
<script src="/resources/js/datetime-moment.js"></script>

<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
    <h1>Source Lead</h1>
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/logo-sourceld.png" alt="Source Lead"></i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <section class="container">
  	<div class="col-md-12">
		  		<div id="accept" style="display:none">
				          <div class="col-md-8 col-md-offset-2 inner-widget success-user">
					          <i class="fa fa-check-circle fa-3x"></i>
							  <p class="reason-msg">You Have accepted the Interview for ${requirementInterview.submissionId.requirement.jobTitle} </p>
							  <p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
						  </div>
		  		</div>
		  		<div id="tentative" style="display:none">
				          <section style='margin-top :85px;' class="clearfix">
						  <section class="content-header clearfix"></section>
						  
						  <div class="propWrap">
								  
							<div class="propWrapRight">
							  	<h2>Propose New Time</h2>	  
							  <ul>
								  <li><span>Job Title:</span><b><i class="fa fa-briefcase"></i>${requirementInterview.submissionId.requirement.jobTitle}</b><div class="clearfix"></div></li>
								  <li><span>Interview Date:</span><b><i class="fa fa-calendar"></i>${requirementInterviewDate}</b><div class="clearfix"></div></li>
								  <c:if test="${! empty interviewStartTimeStr && ! empty interviewEndTimeStr}">
								     <li><span>Interview Time:</span><b><i class="fa fa-clock-o"></i>${interviewStartTimeStr} - ${interviewEndTimeStr}</b><div class="clearfix"></div></li>
								  </c:if>
								  <c:if test="${! empty interviewStartTimeStr &&  empty interviewEndTimeStr}">
								   <li><span>Interview Time:</span><b><i class="fa fa-clock-o"></i>${interviewStartTimeStr} </b><div class="clearfix"></div></li>
								  </c:if>
								  <li><span>Interview Type:</span><b><i class="fa fa-clock-o"></i>${requirementInterview.interviewType}</b><div class="clearfix"></div></li>
							  </ul>
							  </div>
							  <div class="propWrapLeft">
							  <c:if test="${empty proposeTime}">
							  
								<form id="proposeTime_form" name="proposeTime_form" method="post">
								<input type="hidden" name="requirementInterviewId"   value="${requirementInterview.id}" />
								<input type="hidden" name="proposeTimeMaxRowNumber"  id="proposeTimeMaxRowNumber" value="1" />
								<input type="hidden" name="proposeNewTimeRowsNum${requirementInterview.id}"  id="proposeNewTimeRowsNum" value="1" />
								<table width="100%" class="table table-responsive table-bordered table-hover" id="proposeTimeTbl">
								<tbody>
									<tr id="divProposeTimeTsRow0">
										<td>
										  <div id="errMsg0">
										  		<input type="text" class="form-control calendar input-sm" id="interviewDate0" name="interviewDate0" value="" placeholder="Interview Date" readonly data-validation="required" field-name="Interview Date">
										  </div>
										</td>
										<td>
											<div>
												  <input type="text" class="form-control timeicon timepicker input-sm" id="time0" name="time0" placeholder="Select Time" data-validation='required' field-name='Time'>
											</div>										
										</td>
										<td>
											<div>
										  		<button type="button" class="btn btn-primary btn-sm" onClick="addProposeTime();"><i class="fa fa-plus"></i></button>
										  		
									  		</div>
										</td>
									</tr>
								</tbody>
								</table>
								  <div class="propBtn">
								  	<input value="Submit" class="btn btn-success" id="save_proposeTime" type="button" onclick="saveProposeTime();">
								  	<input value="Clear" class="btn btn-default" type="button" onclick="clearProposeTime();">
								  </div>	
								  </form>  	
							  </c:if>
							  <c:if test="${! empty proposeTime}">
							  <c:forEach var="report" items="${proposeTime}">
								<table width="100%" class="table table-responsive table-bordered table-hover">
								<tbody>
									<tr>
									<td> 
									  <div >
									     <input type="text" class="form-control calendar input-sm"   value="${report.proposeDateStr}" placeholder="Interview Date" readonly data-validation="required" field-name="Invoice Date">
									  </div>
									</td>
									<td>
										<div>
											  <input type="text" class="form-control timeicon timepicker input-sm" readonly  value="${report.proposeTime}" placeholder="Select Time" data-validation='required' field-name='Time'>
										</div>										
									</td>
									</tr>
								</tbody>
								</table>
								  
							  </c:forEach>
							  </c:if>
							  </div>  	  
							<div class="clearfix"></div>
						  </div>
						</section>
		  		</div>
		  		<div id="expire" style="display:none">
				          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
					          <i class="fa fa-ban fa-3x"></i>
							  <p class="reason-msg">Sorry !! Interview time is expired  </p>
							  <p class="reason-msg"><center><h3>Thank You !!!</h3></center></p>
						  </div>
		  		</div>
		  		<div id="reject" style="display:none">
					          <div class="col-md-8 col-md-offset-2 inner-widget reject-user">
									  <i class="fa fa-ban fa-3x"></i>
									  <p class="reason-msg">You are Rejecting the Interview for position <B>${requirementInterview.submissionId.requirement.jobTitle} </B></p>
							  </div>
		  		</div>
		 </div>
  </section>
  <div id="popUp"></div>

  <script>
  function addProposeTime(){
	  var requirementInterviewId  = '${requirementInterviewId}';
	  var checkMaxTsRowNum = $("#proposeTimeMaxRowNumber").val();
		var tbl = document.getElementById("proposeTimeTbl");
		var maxRowNum = parseInt($("input[name = 'proposeTimeMaxRowNumber']").val());
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divProposeTimeRow" + maxRowNum);
		  var locationDate ='${requirementInterview.submissionId.orgLocations.dateFormat}'; 
		  var finalDateFormatInterviewDate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatInterviewDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatInterviewDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatInterviewDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatInterviewDate = 'yy-dd-mm'; 
		  }
		
		var rowNum = parseInt($("input[name = 'proposeNewTimeRowsNum"+requirementInterviewId+"']").val());
		if(rowNum >= 5){
			var result = "";
     		result = result +"	<div class='project_inn_mask'></div>";
     		result = result +"	   <div id='invoice_pop' class='remove_user_pop remove_user_pop_a'>";
     		result = result +"		<p>Only you can add 5 Rows</p>";
     		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
     		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
     		result = result +"	</div>";
     		$("#popUp").append(result);
     		$("#invoice_pop").slideDown(200);
     		$(".project_inn_mask").fadeIn(200);
		}else{
			$("input[name = 'proposeTimeMaxRowNumber']").val(maxRowNum + 1);
			$("input[name = 'proposeNewTimeRowsNum"+requirementInterviewId+"']").val(rowNum + 1);
			var text = "";
			var cell0 = row.insertCell(0);
			text = text+'<div id="errMsg'+maxRowNum+'">';
			text = text+'  <input type="text" class="form-control calendar" id="interviewDate'+maxRowNum+'" name="interviewDate'+maxRowNum+'" value="" placeholder="Interview Date"  readonly data-validation="required" field-name="Invoice Date">';
			text = text+'</div>';
			cell0.innerHTML = text;
			
			$('#interviewDate'+maxRowNum+'').datepicker({
				dateFormat : finalDateFormatInterviewDate,
				changeMonth: true,
				changeYear: true, 
				minDate:0,
				onSelect : function(selected) {
						errInvoiceDate(maxRowNum);
				}
			});
			
			var text = "";
		    var cell1 = row.insertCell(1);
			text = text+'<div>';
			text = text+'  <input type="text" class="form-control timeicon timepicker input-sm ui-timepicker-input" id="time'+maxRowNum+'" name="time'+maxRowNum+'" placeholder="Select Time" data-validation="required" field-name="Time">';
			text = text+'</div>	';
			cell1.innerHTML = text;
			
			var text = "";
		    var cell2 = row.insertCell(2);
			text = text+'<div>';
			text = text+'<button type="button" class="btn btn-danger btn-sm" id="remove"  onclick="removeRow('+maxRowNum+')"><i class="fa fa-remove"></i></button>';
			text = text+'</div>';
			cell2.innerHTML = text;
		}
		
		  $("#time"+maxRowNum+"").timepicker({
				'showDuration': true,
				'disableTextInput': true
			}); 
		 
  }
  </script>
  <script>
  function removeRow(rowId){
	  document.getElementById('divProposeTimeRow' + rowId).style.display = 'none';
	  var requirementInterviewId  = '${requirementInterviewId}';
	  var rowNum = parseInt($("input[name = 'proposeNewTimeRowsNum"+requirementInterviewId+"']").val());
	  $("input[name = 'proposeNewTimeRowsNum"+requirementInterviewId+"']").val(rowNum - 1);
		
		var r = document.getElementById('proposeTimeTbl').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
  }
  
  </script>
  
   <script>
  $("#time0").timepicker({
		'showDuration': true,
		'disableTextInput': true
	});
  </script>
  <script>
  function timePic(rowId){
	  $("#time"+rowId+"").timepicker({
			'showDuration': true,
			'disableTextInput': true
		});
  }
  </script>
  <script>
  var locationDate ='${requirementInterview.submissionId.orgLocations.dateFormat}'; 
  var finalDateFormatEduSave = '';
  if(locationDate.match("yyyy-MM-dd")){
	  finalDateFormatEduSave = 'yy-mm-dd';
  }if(locationDate.match("dd-MM-yyyy")){
	  finalDateFormatEduSave = 'dd-mm-yy'; 
  }if(locationDate.match("MM-dd-yyyy")){
	  finalDateFormatEduSave = 'mm-dd-yy'; 
  }if(locationDate.match("yyyy-dd-MM")){
	  finalDateFormatEduSave = 'yy-dd-mm'; 
  }
  $('#interviewDate0').datepicker({
	  dateFormat : finalDateFormatEduSave,
		changeMonth: true,
		changeYear: true, 
		minDate:0,
		onSelect : function(selected) {
				errInvoiceDate("0");
		}
	});
  </script>
<script>
function errInvoiceDate(rowId){
	$('#errMsg'+rowId+'').find('p.jquery_form_error_message').remove(); 
}
</script>
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
 <script>
function saveProposeTime(){
	if ($('#proposeTime_form').validate(false, validationSettings)){ 
		var sourceleadUrl ='${sourceleadUrl}';
		sourceleadUrl = sourceleadUrl.split("/app")[0];
		var frm = $('#proposeTime_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, ""); 
		//It will escape all the special characters
		$("#save_proposeTime").attr("disabled", "disabled");
        $("#save_proposeTime" ).addClass('button').val('Processing..');
	    var jsonData = encodeURIComponent(con);
		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/saveProposeTime?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData,
					success : function(result) {
						location.reload();
					},
				});
		return true;
	 }else{ 
			return false;
	 } 
}	 
$('body').on('blur', '#proposeTime_form', function() {
	$("#proposeTime_form").showHelpOnFocus();
	$("#proposeTime_form").validateOnBlur(false, validationSettings);  
});
</script>
<script>
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
<script>
function clearProposeTime(){
	location.reload();
}
</script>
<script>
window.onload = function() {
	  var interviewStatus  = '${interviewStatus}';
	  var intervewStartTime = '${interviewStartTime}';
	  var intervewEndTime = '${interviewEndTime}';
	  if(intervewStartTime != "interviewStartTime" && intervewEndTime != "interviewEndTime"){
	  	$("#expire").removeAttr("style");
	  }else{
	  	if(interviewStatus == 'Attending'){     
	  		$("#accept").removeAttr("style");
	  	}else if(interviewStatus == 'Not_Attending'){
	  		$("#reject").removeAttr("style");
	  	}else if(interviewStatus == 'Maybe'){
	  		$("#tentative").removeAttr("style");
	  	}
	  }
	};
</script>
<script src="/resources/js/formvalidator.js"></script>
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