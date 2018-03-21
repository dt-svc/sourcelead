
<!DOCTYPE html>
 <jsp:include page="staticHeader.jsp" /> 
<html>
<head>
     <meta charset="utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1">
    
     <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@page session="true"%>
    
    <c:if test="${staticTime == null || staticTime == 0 }">
       <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
     </c:if>
    <title>::. Unsubscribe .::</title>
    <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	<link rel="stylesheet" href="/resources/css/font-awesome.min.css?staticTime=${staticTime}">
	<link rel="stylesheet" href="/resources/css/timesheet.css" type="text/css" >   
</head>


<script type="text/javascript">
window.onload = function () {
	var sourceleadUrl ='${sourceleadUrl}';
	var status = '${status}';
 };
</script>
<style type="text/css">
/*  switch button styels */
.slider.round:before {
    border-radius: 50%;
}
.slider.round {
    border-radius: 15px;
}
.switch {
	position: relative; display: inline-block; width: 35px; height: 22px;
}
.switch input {
	display: none;
}
.slider {
	position: absolute; cursor: pointer; top: 0; left: 0; right: -13px; bottom: 0;
	background-color: #ccc; -webkit-transition: .4s; transition: .4s;
}
.slider:before {
	position: absolute; content: ""; height: 16px; width: 15px; left: 4px; bottom: 3px;
	background-color: white; -webkit-transition: .4s; transition: .4s; -webkit-border-radius: 3px;
    -moz-border-radius: 3px; border-radius: 3px; box-shadow: 1px 1px 2px #333;
}
input:checked + .slider {
	background-color: #4CAF50;
}
input:focus + .slider {
	box-shadow: 0 0 1px #2196F3;
}
input:checked + .slider:before {
	-webkit-transform: translateX(26px); -ms-transform: translateX(26px); transform: translateX(26px);
}
.uenlables label{
font-weight: normal;
}
.uenlables .form-group {
margin-bottom: 5px;
}
p.jquery_form_error_message {
font-size: 12px !important; color:red !important; margin-top: 0 padding:0 !important;
}
.text-danger {
color:red !important;
}
.text-success {
color:#4CAF50 !important;
}
</style>
<body>
	<section class="emailBg">
		<section class="emailVeri emailverification-contnet">
			<div id="mainSectionDiv">
				<div class="tab-content panel-body">
				
				<div class="col-md-6 col-md-offset-3">
				<input type="text" class="form-control error" id="emailId" name="email" placeholder="Enter your email" field-name="Email Id"  onblur="validateUserEmail();">
				<div class="mt5 clearfix">
				<span class="fa fa-check text-success pull-right" aria-hidden="true" id="validUser" style="display: none;">&nbsp;&nbsp;<span id="validUserName" class="paddingLeft"></span></span>
				<span class="fa fa-times text-danger pull-right" aria-hidden="true" id="inValidUser" style="display: none;">&nbsp;&nbsp;<span id="inValidUserName" class="paddingLeft"></span></span>
				</div>
				</div>
				
	                  <div class="clearfix mb col-md-6 col-md-offset-3">
	                      <div class="pull-left">
	                          <h5>Unsubscribe Emails and Notifications</h5>
	                      </div>
	                  </div>
						<div class="col-md-6 col-md-offset-3 uenlables">
							<div class="form-group row">
								<label class="col-md-4">Jobs<i class="" title="Jobs"></i></label>
								<div class="col-md-8 toggle-button">
									<label class="switch">
										<c:set var="disabled" value=""/>
										<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
									      	 <c:if test="${8 == unsubscriptionTabId}">
									      	 	<c:set var="disabled" value="checked"/>
									      	 </c:if>
		   								</c:forEach>
										<input name="chkChangeSubscription" type="checkbox" id="Jobs" ${disabled} value="8">
										<div class="slider round"></div>
									</label>
								</div>
							</div>
							
							<div class="form-group row">
								<label class="col-md-4">Projects<i class="" title="Projects"></i></label>
								<div class="col-md-8 toggle-button">
									<label class="switch">
										<c:set var="disabled" value=""/>
										<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
									      	 <c:if test="${3 == unsubscriptionTabId}">
									      	 	<c:set var="disabled" value="checked"/>
									      	 </c:if>
		   								</c:forEach>
										<input name="chkChangeSubscription" type="checkbox" id="Projects" ${disabled} value="3">
										<div class="slider round"></div>
									</label>
								</div>
							</div>
							
							<div class="form-group row">
								<label class="col-md-4">Timesheets<i class="" title="Timesheets"></i></label>
								<div class="col-md-8 toggle-button">
									<label class="switch">
										<c:set var="disabled" value=""/>
										<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
									      	 <c:if test="${4 == unsubscriptionTabId}">
									      	 	<c:set var="disabled" value="checked"/>
									      	 </c:if>
		   								</c:forEach>
										<input name="chkChangeSubscription" type="checkbox" id="Timesheets" ${disabled} value="4">
										<div class="slider round"></div>
									</label>
								</div>
							</div>
							
							<div class="form-group row">
								<label class="col-md-4">Expenses<i class="" title="Expenses"></i></label>
								<div class="col-md-8 toggle-button">
									<label class="switch">
										<c:set var="disabled" value=""/>
										<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
									      	 <c:if test="${23 == unsubscriptionTabId}">
									      	 	<c:set var="disabled" value="checked"/>
									      	 </c:if>
		   								</c:forEach>
										<input name="chkChangeSubscription" type="checkbox" id="Expenses" ${disabled} value="23">
										<div class="slider round"></div>
									</label>
								</div>
							</div>
							
							<div class="form-group row">
								<label class="col-md-4">Contracts<i class="" title="Contracts"></i></label>
								<div class="col-md-8 toggle-button">
									<label class="switch">
										<c:set var="disabled" value=""/>
										<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
									      	 <c:if test="${16 == unsubscriptionTabId}">
									      	 	<c:set var="disabled" value="checked"/>
									      	 </c:if>
		   								</c:forEach>
										<input name="chkChangeSubscription" type="checkbox" id="Contracts" ${disabled} value="16">
										<div class="slider round"></div>
									</label>
								</div>
							</div>
							
							<div class="form-group row">
								<label class="col-md-4">Campaigns<i class="" title="Campaigns"></i></label>
								<div class="col-md-8 toggle-button">
									<label class="switch">
										<c:set var="disabled" value=""/>
										<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
									      	 <c:if test="${30 == unsubscriptionTabId}">
									      	 	<c:set var="disabled" value="checked"/>
									      	 </c:if>
		   								</c:forEach>
										<input name="chkChangeSubscription" type="checkbox" id="Campaigns" ${disabled} value="30">
										<div class="slider round"></div>
									</label>
								</div>
							</div>
							
							<div class="form-group row">
								<label class="col-md-4">Invoice &amp; Billing<i class="" title="Invoice & Billing"></i></label>
								<div class="col-md-8 toggle-button">
									<label class="switch">
										<c:set var="disabled" value=""/>
										<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
									      	 <c:if test="${20 == unsubscriptionTabId}">
									      	 	<c:set var="disabled" value="checked"/>
									      	 </c:if>
		   								</c:forEach>
										<input name="chkChangeSubscription" type="checkbox" id="Finance" ${disabled} value="20">
										<div class="slider round"></div>
									</label>
								</div>
							</div>
							
							<div class="form-group row">
								<label class="col-md-4">Others<i class="" title="Others"></i></label>
								<div class="col-md-8 toggle-button">
									<label class="switch">
										<c:set var="disabled" value=""/>
										<c:set var="othersId" value="0"/>
										<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
									      	 <c:if test="${unsubscriptionTabId == othersId}">
									      	 	<c:set var="disabled" value="checked"/>
									      	 </c:if>
		   								</c:forEach>
										<input name="chkChangeSubscription" type="checkbox" id="Other" ${disabled} value="0">
										<div class="slider round"></div>
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-md-offset-3">
					<input type="button" value="Unsubscribe" id="submit_request" onclick="submitRequest();" class="btn btn-primary">
					</div>
			</div>
			
			<div id="successDiv" class="successDiv" style="display: none;">
				<h4>Your request is processed successfully!!!</h4>
			</div>
			
			<div id="failureDiv" class="failureDiv" style="display: none;">
				<h4>Internal error occurred.</h4>
			</div>
			
		</section>
	</section>
</body>



<script type="text/javascript">
function submitRequest() {
		$( "#submit_request" ).addClass('button').val('Processing..');
		$( "#submit_request" ).prop('disabled', true);
		var sourceleadUrl = '${sourceleadUrl}';
		var tabIds = [];
		$('input:checkbox[name=chkChangeSubscription]:checked').each(function(){
			tabIds.push($(this).val());
		});
		/* var uniqueToken = GetURLParameter('token'); */
		var uniqueToken = $("#emailId").val();
		
		$.ajax({
			 type : "GET",
			 url : sourceleadUrl+"/storeUnsubscriptionPublic?tabIds="+tabIds+"&uniqueToken="+uniqueToken,
			 success : function(data) {
				 if(data == "Success"){
					 $("#mainSectionDiv").hide();
					 $("#failureDiv").hide();
					 $("#successDiv").show();
				 }else if(data == "Failure"){
					 $("#mainSectionDiv").hide();
					 $("#successDiv").hide();
					 $("#failureDiv").show();
				 }
			 },
		});
}
	

/* function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++)
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
        {
            return sParameterName[1];
        }
    }
} */


function validateUserEmail(){
	$( "#submit_request" ).prop('disabled', false);
	$("#Jobs").prop('checked', false);
	$("#Projects").prop('checked', false);
	$("#Timesheets").prop('checked', false);
	$("#Expenses").prop('checked', false);
	$("#Contracts").prop('checked', false);
	$("#Campaigns").prop('checked', false);
	$("#Finance").prop('checked', false);
	$("#Other").prop('checked', false);
	
	$("#validUser").hide();
	$("#validUserName").empty("");
	$("#inValidUser").hide();
	$("#inValidUserName").empty("");
	var sourceleadUrl = '${sourceleadUrl}';
	var email = $("#emailId").val();
	$.ajax({
		 type : "GET",
		 url: sourceleadUrl+"/isActiveUserByUserEmail?&EmailId="+email, 
		 success : function(data) {
			 if(data != null && data != 'undefined' && data != ''){
				 $("#validUser").show();
				 $("#validUserName").append(data.fullName);
				 if(data.unSubscribeTabIds != null && data.unSubscribeTabIds != 'undefined' && data.unSubscribeTabIds != ''){
					 var tabIds = data.unSubscribeTabIds;
					 tabIds.split(',').forEach(function (item) {
						if(item == 3){
							$("#Projects").prop('checked', true);
						}if(item == 4){
							$("#Timesheets").prop('checked', true);
						}if(item == 8){
							$("#Jobs").prop('checked', true);
						}if(item == 16){
							$("#Contracts").prop('checked', true);
						}if(item == 20){
							$("#Finance").prop('checked', true);
						}if(item == 23){
							$("#Expenses").prop('checked', true);
						}if(item == 30){
							$("#Campaigns").prop('checked', true);
						}if(item == 0){
							$("#Other").prop('checked', true);
						}
					});
				 }
			 }else{
				 $("#inValidUser").show();
				 $("#inValidUserName").append("Invalid email");
			 }
		 },
	});
}
</script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=AIzaSyCIbxD8-aZ7Wgp6N-JeEJKVRIHGRpNMdRE" type="text/javascript"></script>
<script src="/resources/js/formvalidator.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
<script src="/resources/static-web/js/jquery-1.11.3.min.js"></script>
<script src="/resources/js/bootstrap-select.min.js"></script>
<script src="/resources/static-web/js/owl.carousel.js"></script>
<script src="/resources/static-web/js/bootstrap.min.js"></script> 
<script src="/resources/static-web/js/jquery.prettyPhoto.js" type="text/javascript"></script>
<script src="/resources/static-web/js/customscripts.js" type="text/javascript"></script>
<jsp:include page="masterFooter.jsp" />
<%-- <jsp:include page="staticFooter.jsp" /> --%>
</html>
