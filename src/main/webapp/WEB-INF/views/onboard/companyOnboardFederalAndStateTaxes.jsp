<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">


<link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=AIzaSyCIbxD8-aZ7Wgp6N-JeEJKVRIHGRpNMdRE" type="text/javascript"></script>


<script type="text/javascript">
window.onload = function () {
	getStateTaxes();
 };
</script>




<div class="wraper"> 
	<section class="comLogBg">
			<section class="comLogInn">
			<div class="comLogHead">
				 <div class="comLogLogoText">${SourceLeadWeb_orgName}</div>
				  <div class="welSiO onboard-logoutbtn" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out mt5"></i></div>
				       <ul>
						  <li class="actLi">Organization Details<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Locations<i class="fa fa-check-circle"></i></li>
						  <li class="hoverLi">Federal & state taxes</li>
						  <li>Banking details</li>
						  <li>Signatory</li>
						  <div class="clearfix"></div>
					  </ul>
				  <div class="clearfix"></div>
			  </div>
			  
			 <form:form class="login-form clearfix"  id="fedralAndTax_form"  name="fedralAndTax_form" method="post" >   
			  <div class="comLogContent">
      			  <h2>Federal & State Taxes</h2>
			         <div class="row clearfix"> 
			         	
		         	<div class="col-md-6">
					   		
					   		<div class="form-group clearfix">
								<label class="col-md-4 noPad">Federal EIN<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								 <input class=" form-control" type="text" name="fedralEIN" id="fedralEIN" field-name="fedralEIN"  data-validation="required validate_length length9-9" maxlength="9">
								</div>
							</div>
							
							
							<div class="form-group clearfix">
								<label class="col-md-4 noPad">Deposit Schedule<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								<select  class="chosen-select form-control" name="depositeSchedule" id="depositeSchedule" field-name="Deposit Schedule"  data-validation="required ">
											<option value="">--Select Deposit Schedule--</option>
											<option value="Monthly">Monthly</option>
											<option value="Semi Monthly">Semi Monthly</option>
					                 </select>
								</div>
							</div>
							
							<div class="form-group clearfix">
								<label class="col-md-4 noPad">Filling form<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								<select  class="chosen-select form-control" name="fillingForm" id="fillingForm" field-name="Filling Form"  data-validation="required ">
									<option value="">--Select Filling form --</option>
									<option value="Quarterly[940]">Quarterly[940]</option>
									<option value="Annuallu[944]">Annuallu[944]</option>
									<option value="House hold[H]">House hold[H]</option>
								  </select>
								</div>
							</div>
							
							<div class="form-group clearfix">
								<label class="col-md-4 noPad">Unemployment Tax?</label>
								<div class="col-md-8 padding_right">
					              <label class="col-md-5 noPad">
					                <input name="unemploymentTax" type="radio" value="Exempt">
					                <strong class="paddingLeft">Exempt</strong></label>
					              <label class="col-md-5 noPad">
					                <input name="unemploymentTax" type="radio" value="Unexempt" checked>
					                <strong class="paddingLeft">Unexempt</strong></label>
					            </div>
							</div>
			  </div>		
					
					
					<div class="col-md-10">	
					<h3>State Taxes</h3>
					<div id="stateTaxesList"></div>
		         	</div>
		       </div>
			       
			         
				<div class="welBtns welBtnsA">
					<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/companyOnboardLocations'/>'"><input type="button" value="Save & Continue" class="Brig">
					<input type="button" value="Skip this step" class="skipBtn"></a>
					<div class="clearfix"></div>
				</div>
			         
       		  </div>
       		  </form:form>
  
			</section>
	</section>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
 <script type="text/javascript" src="resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
	
  <script src="resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
 <script src="resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 

<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>


<script>
function getStateTaxes(){
	 var result = "";
	 
	 
	 result = result +'<div class="clearfix mb10 mt5 requestdocmntTable">';
		result = result +'<input type="hidden" name="docuemntMaxRowNumber"  id="docuemntMaxRowNumber" value="1" />';
		result = result +'<table class="table table-responsive table-bordered table-hover" width="100%" id="requestedDocumentsTable" border="0">';
		result = result +'<tbody>';
		result = result +'<tr id="requestedDocumentsTsRow0">';
		result = result +'<div id="0requestedDocumentsFileGroup">';
		result = result +'<td width="500px">';
		result = result +'<div class="dropdown-select">';
		
		result = result +'<select class="width100percnt form-control selectborder" data-validation="required" field-name="Document Type" onchange="getSelectedDocumentType(0);" id="0docType" name="requestDocumentList[0].docType">';
		result = result +'<option selected value="">-- Select State --</option>';
		result = result + '<option value="rererere" >ererer</option>';
		result = result + '<option value="rererere" >ererer</option>';
		result = result +'</select>';
		

		result = result +'</div>';
		result = result +'</td>';
		
		result = result +'<td width="500px">';
		result = result +'<div class="">';
		result = result +'<input type="text" class="form-control input-sm" placeholder="State Unemployment Tax " data-validation="required" field-name="Other Type" name="requestDocumentList[0].otherDocType" id="0otherDocType">';
		result = result +'</div>';
		result = result +'</td>';
		
		result = result +'<td width="500px">';
		result = result +'<div class="">';
		result = result +'<input type="text" class="form-control input-sm" placeholder="Sate Tax Account Number" data-validation="required" field-name="Other Type" name="requestDocumentList[0].otherDocType" id="0otherDocType">';
		result = result +'</div>';
		result = result +'</td>';
		
		result = result +'<td width="50px">';
		result = result +'<div class="">';
		
		result = result +'<div class="pull-right">';
		result = result +'<button type="button" class="btn btm-sm pull-right mt5" onclick="addDocumentRow();"><i class="fa fa-plus text-primary"></i></button>';
		result = result +'</div>';
		result = result +'</div>';
		result = result +'</td>';
		result = result +'</div>';
		result = result +'</tr>';
		result = result +'</tbody>';
		result = result +'</table>';
		result = result +'</div>';
		result = result +'</div>';
		result = result +'</div>';
	$("#stateTaxesList").append(result);
}
</script>





<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>