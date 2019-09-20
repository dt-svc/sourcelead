<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- these files for autocomplete with tagit -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- <jsp:include page="candidatesQuickView.jsp"></jsp:include>
 --%>
<style>
.addUser-org { overflow-x: auto; }
</style>

<%-- 
     @author : sarath kumar.
     this script is for display candidate education tab data.
     1.usage     : used at clientSideBar().
--%>


<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
function candidateVisa(candidateId){
	 var candidateType = '${candidateType}';
	var result = "";
	if(window.candidateStatusGlobal == '0'){
		result = result  +'    <div class="project_tabs_inn_head addUser-org  mt25">  ';
	}else{
		result = result  +'    <div class="project_tabs_inn_head addUser-org  mt10">  ';
	}
	
	result  = result + '<div class="success_pop" id="selector4" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="eduMsgDiv"></span>';
    result  = result + '</div>';
	if(window.candidateStatusGlobal == '0'){
		if(candidateType == "Candidates"){
			'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 	
			result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
			'</sec:authorize>';
		}else{
			'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\')">'; 	
			result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
			'</sec:authorize>';
		}
		
	}else{
		if(candidateType == "Candidates"){
			'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			result = result  +'<span class="pad-sea padding_right"><a href="javascript:void(0)"  id="addVisa" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Visa</a></span>';
			'</sec:authorize>';
		}else{
			'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\')">';
			result = result  +'<span class="pad-sea padding_right"><a href="javascript:void(0)"  id="addVisa" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Visa</a></span>';
			'</sec:authorize>';
		}
		
	}
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		
		
		result  = result + '   <div id="candidateVisaList" class="datatable_filter"></div>';
		
		result  = result + '   </div>';
	result = result+'	    </div>';

	$("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-7").empty();
    $("#tab-8").empty();
    $("#tab-9").empty();
    $("#tab-4").empty();
    /* $("#tab-12").empty();
    $("#tab-12").append(result);   */
    $("#tab-12").show();
    $("#immigrationCandidate").empty();
    $("#immigrationCandidate").append(result);
    candidateVisaList(candidateId);
    $("#addVisa").click(function(){
		$(".popMask").fadeIn(200);
		$("#popUpVisa").fadeIn(100);
	});	
  //Tab script
	//$(".tabcontent").hide();
	//immigrationTabHandler();
}
</script>

<!-- Show success message after form submission script -->
<script>
	function eduMsgFadeinFunction() {
		$("#selector4").fadeIn(200);
        $('#selector4').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->


<%-- 
     @author : sarath kumar.
     this script is for get candidateVisaList.
     1.usage     : used in candidateEducation().
     2.parameter : csrf token,candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function candidateVisaList(candidateId){
	var candidateType = '${candidateType}';
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  canVisaDatePattern = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  canVisaDatePattern = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  canVisaDatePattern = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  canVisaDatePattern = 'YYYY-DD-MM'; 
	  }
	 $.ajax({  
          type : "GET",
          url: atsUrl+"/getAssociateVisaDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#candidateVisaList").empty();
               	 var result = "";
               	 result = result +"<table id='candidatePassportPagination' class='display' cellspacing='0' width='100%' >"; 
                     result = result +"<thead>"; 
                     result = result +"  <tr>"; 
                     result  = result + '           <th>Visa Type</th>';
                     result  = result + '           <th>Visa Id</th>'; 
                	 result  = result + '           <th>Country</th>';
                	 result  = result + '           <th>Valid From <br>(<small class="custom-small">'+canVisaDatePattern+'</small>)</th>';
                	 result  = result + '           <th>Valid To <br>(<small class="custom-small">'+canVisaDatePattern+'</small>)</th>';
                	 result  = result + '           <th>Status</th>';
                	 result  = result + '           <th>Action</th>';
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                    
                    for (var i = 0; i < response.data.length; i++) { 
                   	 var opt = response.data[i];  
                   	console.log(opt)
                   	 result  = result + '         <tr>';
                    if(opt.visaType != null && opt.visaType != '' && opt.visaType != 'undefined'){
                      	 result  = result + '<td><div><span title = "'+opt.visaType+'">'+opt.visaType+'</span></div></td>';
                      	    }else{
                      	    	 result  = result + ' <td><div><span>----</span></div></td>';
                      	}
                   	 if(opt.visaNumber != null && opt.visaNumber != '' && opt.visaNumber != 'undefined'){
                   	 result  = result + '<td><div><span title = "'+opt.visaNumber+'">'+opt.visaNumber+'</span></div></td>';
                   	    }else{
                   	    	 result  = result + ' <td><div><span>----</span></div></td>';
                   	    }
                   	 if(opt.country != null && opt.country != '' && opt.country != 'undefined'){
                   	 result  = result + ' <td><div><span title="'+opt.country+'">'+opt.country+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.validFrom != null && opt.validFrom != '' && opt.validFrom != 'undefined'){
                   	 result  = result + ' <td><div><span>'+opt.validFrom+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.validTo != null && opt.validTo != '' && opt.validTo != 'undefined'){ 
                   	 result  = result + '<td><div><span>'+opt.validTo+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.currentStatus != null && opt.currentStatus != '' && opt.currentStatus  != 'undefined'){
                   	 result  = result + '<td><div><span>'+opt.currentStatus+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>'; 
                   	 }
                   	 if(opt.candidateVisaId != null && opt.candidateVisaId != '' && opt.candidateVisaId != 'undefined'){
                   		 if(window.candidateStatusGlobal == '0'){
                   			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
                   		 }
                   		 else{
                   			 if(candidateType == "Candidates"){
                   				 result = result+' <td><div class="tblicons tblminwidth-75"><sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="EditEducation('+ candidateId+ ','+ opt.candidateVisaId+ ');" title="Edit"></a></sec:authorize>';
                   			 }else{
                   				 result = result+' <td><div class="tblicons tblminwidth-75"><sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="EditEducation('+ candidateId+ ','+ opt.candidateVisaId+ ');" title="Edit"></a></sec:authorize>';
                   			 }
                   				 
                   		 }
                   		 if(window.candidateStatusGlobal == '0'){
								result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
                   		 }else{
                   			 if(candidateType == "Candidates"){
                   				 result = result+'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> <i class="glyphicon glyphicon-trash" onclick="removeEducation('+opt.candidateVisaId+','+candidateId+');" title="Remove"></i></sec:authorize></div></td>';	 
                   			 }else{
                   				 result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_ASSOCIATE_EDUCATION_DETAILS\')"> <i class="glyphicon glyphicon-trash" onclick="removeEducation('+opt.candidateVisaId+','+candidateId+');" title="Remove"></i></sec:authorize></div></td>';
                   			 }
                   			  
                   		 }
                   	}
                   	 result  = result + '</tr>';
                     } 
                    result = result +" </tbody>";
                    result = result +"</table>";
                    $("#candidateVisaList").append(result); 
                   // educationPagination();
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
        },
     }); 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination.
     1.usage     : used in candidateVisaList().
--%>

function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for shown popUp.
     1.usage   : used in candidateEducation() function.
--%>

<%-- 
     @author : sarath kumar.
     this script is for store candidateEducationstore .
     1.usage   : used in candidateEducation() function.
--%>
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


<div class="clearfix">
<!--popcode start  -->
		<form id="CandidateVisaform" name="CandidateVisaform" method="post"> 
		<div class="popUpOut" id="popUpVisa">
		<input type = "hidden" id="candidateVisaId"  name="candidateVisaId">
		 <input type = "hidden" id="visaHistoryId"  name="visaHistoryId">
		<div class="popupHead">
			<h2 class="mt pull-left">Add Visa</h2><a href="javascript:void(0)" class="fa fa-close popCan mt mr" onClick="popFun();"></a>
		</div>
		
	<div class="clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Type<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name = "visaType" id = "visaType" field-name = "Visa Type"  data-validation = "required" data-live-search="true">
					  <option value="" selected>Select Visa Type</option>
					  <c:forEach var="visaType" items="${canVisaList}">
						<option value="${visaType}">${visaType}</option>
					</c:forEach>
					</select>
				</div>
			</div>
         
          <div class="col-sm-12 noPad mt clearfix" id="idNumberVisaDiv">
				<label class="col-md-4 padding_left">Visa Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name ="visaNumber" id ="visaNumber" placeholder="Visa Id" field-name = "Visa Id" class="form-control"   data-validation = "required validate_length length8-20 validate_AtLeastOneAlphaNumber"/>
				</div>
			</div>				          
          	<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name="country" id= "visaCountry"  field-name="Country" data-validation="required" data-live-search="true">
					<option value="" selected>--Select Country--</option>
					  <c:forEach var="country" items="${countryList}">
						<option value="${country.name}">${country.name}</option>
					</c:forEach>
					</select>
				</div>
			</div>
		  <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid From<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text" class="form-control calendar"  placeholder="Valid From" value="" name = "validFrom" id = "visaValidFrom" field-name = "Valid From" data-validation="required"/>
				    <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('visaValidFrom');"></i></span>
				</div></div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text" class="form-control calendar" value="" name = "validTo" placeholder="Valid To" id = "visaValidTo" field-name = "Valid To" data-validation="required"/>
				 <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer"  onBlur="isCurrentValidate();"onClick="clearDateValue('visaValidTo')"></i></span>
				</div>
				
			</div>	
			
			</div>	
			
			 <div class="clearfix"></div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="visa_save_id" onclick="immigration.validateCandidateVisaAndSave()">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div><!--popcode end  -->	
</div>
<script>
		 // Add addVisa Div  
			function popFun() {
				$(".popMask").fadeOut(200);
				$("#popUpVisa").fadeOut(200);
				$("#CandidateVisaform")[0].reset();
				$('#visaType').prop("disabled",false);
				$("#visaType").selectpicker("refresh");
				$('#visaCountry').prop("disabled",false);
				$("#visaCountry").selectpicker("refresh");
				$('#visaNumber').removeAttr("disabled");
				$("#candidateVisaId").val('');
				$('#popUpVisa').find('p.jquery_form_error_message').remove();
				$("#visa_save_id").removeAttr("disabled");
			}
		   	
		</script>