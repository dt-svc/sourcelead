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

<div class="popMask"></div>

<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
var candidateId = '${candidateId}';
function candidatePassport(candidateId){
	 var candidateType = '${candidateType}';
	var result = "";
	if(window.candidateStatusGlobal == '0'){
		result = result  +'    <div class="project_tabs_inn_head addUser-org">  ';
	}else{
		result = result  +'    <div class="project_tabs_inn_head addUser-org">  ';
	}
	
	
	
	
	result  = result + '<div class="success_pop" id="selector4" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="msgDiv"></span>';
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
			result = result  +'<span class="pad-sea padding_right"><a href="javascript:void(0)"  id="addPass" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Passport</a></span>';
			'</sec:authorize>';
		}else{
			'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS\')">';
			result = result  +'<span class="pad-sea padding_right"><a href="javascript:void(0)" id="addPass" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Passport</a></span>';
			'</sec:authorize>';
		}
		
	}
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		
		
		result  = result + '   <div id="candidatePassportList" class="datatable_filter"></div>';
		
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
    /* $("#tab-12").empty(); */
    $("#tab-12").show();
    $("#passportli").addClass("current");
    $("#visali").removeClass("current");
    $("#lcali").removeClass("current");
    $("#immigrationCandidate").empty();
    $("#immigrationCandidate").append(result);
    
    
    candidatePassportList(candidateId);
    $("#addPass").click(function(){
    	$("#candidateID").val(candidateId);
		$(".popMask").fadeIn(200);
		$("#popPassView").fadeIn(100);
		$("#CandidatePassportform")[0].reset();
		$("#countryName").selectpicker("refresh");
		$("#candidatePassportId").val('');
		$('#popPassView').find('p.jquery_form_error_message').remove();
		$("#passport_save_id").removeClass("disabled");
	});	
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
    var finalDateFormatPassSave = getJSDateFormat(locationDate);
   $('#validFrom').datepicker({
        dateFormat: finalDateFormatPassSave,
        changeMonth: true,
    	  changeYear: true, 
    	  maxDate:0,
        onSelect: function (selected) {
             // var dt = new Date(selected);
             var dt = $(this).datepicker('getDate', '+1d'); 
             dt.setDate(dt.getDate() + 1);
              $("#validTo").datepicker("option", "minDate", dt);
          }
    });
    
    $('#validTo').datepicker({
       dateFormat: finalDateFormatPassSave,
       changeMonth: true,
    	 changeYear: true, 
       onSelect: function (selected) {
          //var dt = new Date(selected);
          var dt = 	  $(this).datepicker('getDate', '-1d');
          dt.setDate(dt.getDate() - 1);
          $("#validFrom").datepicker("option", "maxDate", dt);
      }
    });

}
</script>
<script>
// Add addPass Div  
function passportHandler() {
	$(".popMask").fadeOut(200);
	$("#popPassView").fadeOut(200);
	$("#CandidatePassportform")[0].reset();
	$("#countryName").selectpicker("refresh");
	$("#candidatePassportId").val('');
	$('#popPassView').find('p.jquery_form_error_message').remove();
	$("#passport_save_id").removeClass("disabled");
}
</script>
<!-- Show success message after form submission script -->
<!-- End -->


<%-- 
     @author : sarath kumar.
     this script is for get candidatePassportList.
     1.usage     : used in candidateEducation().
     2.parameter : csrf token,candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function candidatePassportList(candidateId){
	var candidateType = '${candidateType}';
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  canPassportDatePattern = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  canPassportDatePattern = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  canPassportDatePattern = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  canPassportDatePattern = 'YYYY-DD-MM'; 
	  }
	 $.ajax({  
          type : "GET",
          url: atsUrl+"/getAssociatePassportDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#candidatePassportList").empty();
               	 var result = "";
               	 result = result +"<table id='candidatePassportPagination' class='display' cellspacing='0' width='100%' >"; 
                    result = result +"<thead>"; 
                    result = result +"  <tr>"; 
                    result  = result + '           <th>Passport Id</th>';
                	 result  = result + '           <th>Country</th>';
                	 result  = result + '           <th>Valid From <br>(<small class="custom-small">'+canPassportDatePattern+'</small>)</th>';
                	 result  = result + '           <th>Valid To <br>(<small class="custom-small">'+canPassportDatePattern+'</small>)</th>';
                	 result  = result + '           <th>Status</th>';
                	 result  = result + '           <th>Action</th>';
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                    
                    for (var i = 0; i < response.data.length; i++) { 
                   	 var opt = response.data[i];  
                   	 result  = result + '         <tr>';
                   	 if(opt.passportNumber != null && opt.passportNumber != '' && opt.passportNumber != 'undefined'){
                   	 result  = result + '<td><div><span title = "'+opt.passportNumber+'">'+opt.passportNumber+'</span></div></td>';
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
                   	 if(opt.candidatePassportId != null && opt.candidatePassportId != '' && opt.candidatePassportId != 'undefined'){
                   		 if(window.candidateStatusGlobal == '0'){
                   			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
                   		 }
                   		 else{
                   			 if(candidateType == "Candidates"){
                   				 result = result+' <td><div class="tblicons tblminwidth-75"><sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="editPassportDetailsByRowId('+ opt.candidatePassportId+ ');" title="Edit"></a></sec:authorize>';
                   			 }else{
                   				 result = result+' <td><div class="tblicons tblminwidth-75"><sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="editPassportDetailsByRowId('+ opt.candidatePassportId+ ');" title="Edit"></a></sec:authorize>';
                   			 }
                   				 
                   		 }
                   		 if(window.candidateStatusGlobal == '0'){
								result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
                   		 }else{
                   			 if(candidateType == "Candidates"){
                   				 result = result+'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> <i class="glyphicon glyphicon-trash" onclick="deleteImmigrationRecord(deletePassportDetails,'+opt.candidatePassportId+');" title="Remove"></i></sec:authorize></div></td>';	 
                   			 }else{
                   				 result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_ASSOCIATE_EDUCATION_DETAILS\')"> <i class="glyphicon glyphicon-trash" onclick="deleteImmigrationRecord(deletePassportDetails,'+opt.candidatePassportId+');" title="Remove"></i></sec:authorize></div></td>';
                   			 }
                   			  
                   		 }
                   	}
                   	 result  = result + '</tr>';
                     } 
                    result = result +" </tbody>";
                    result = result +"</table>";
                    $("#candidatePassportList").append(result); 
                    passportPagination();
                    passportHandler();
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
        },
     }); 
}
</script>
<script>
function passportPagination(){  
    $('#candidatePassportPagination').DataTable( { 
    	"searching": false,
   	 	"paging":   false,
        "ordering": false,
        "info":     false,
      	"oLanguage": {
     	    "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
<script>
function deletePassportDetails(id){
	console.log(candidateId);
	$.ajax({
        url: vmsUrl + immiModulePath + "/deleteCandidatePassportDetails?id="+id+"&candidateId="+candidateId,
        type: "GET",
        success: function (result) {
        	console.log(result);
        	if(result == "success"){
        			showSuccessMsg("Successfully Record Deleted.");
        			deleteImmigrationPopupDiv();
        			setTimeout(function(){ candidatePassport(candidateId)}, 1000);
        	}
        	
        }
    });
}
function editPassportDetailsByRowId(id){
	$("passNum").val(id);
	$.ajax({
        url: vmsUrl + immiModulePath + "/getCandidatePassportDetailsById?id="+id,
        type: "GET",
        success : function(data) {
        	if(data != null){
        		formObj = $('#CandidatePassportform')[0]; // load the child form
    			var childPopNames=["passportNumber","country", "validFrom","validTo"];
    			$("#addPass").trigger('click');
    			immigration.udpatePopupFormdata(formObj,childPopNames,data);
    			$('#validFrom').val(data.validFromDateStr);
    			$('#passNum').val(data.id);
    			$('#validTo').val(data.validToDateStr);
    			
    			$('#countryName').val(data.country);
    			$('#countryName').selectpicker('refresh');
    			$('#candidatePassportId').val(data.id);	
        		
        	}
        }
    });
}
function savePassportVisaDetails() {
	
	if ($("#CandidatePassportform").validate(false, validationSettings)) {
		//$("#passport_save_id").attr("disabled",true);
		$("#passport_save_id").addClass("disabled");
			var formData = getFormData("CandidatePassportform");
			 var candidateId = '${candidateId}';
			 //console.log( "candidateId "+candidateId);
            $.ajax({
            url: vmsUrl + immiModulePath + "/savePassportDetailsPage?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId,
            type : "POST",
			data : formData,
			success : function(result) {
				
							if (result && result.response === "INVALID_DATA") {
		                    	 showError(result.responseMessage);
		                    }else if (result.response === "AWKWARD") {
		                        location.href = "/errorPage";
		                    }else if(result.response === 'SUCCESS') {
		                    	
									$('#popPassView').hide();
									$('.popMask').fadeOut();
									if($('#candidatePassportId').val().trim() != ''){
										showSuccessMsg("Successfully Updated Passport Details.");
			                    	}else{
			                    		showSuccessMsg("Successfully New Passport Details Added.");
			                    	}
									//candidatePassport(candidateId);
									setTimeout(function(){ candidatePassport(candidateId)}, 1000);
								
		                    }else{
		                    	 showError("Operation failed, please try again");
		                    }
							$("#passport_save_id").removeClass("disabled");
			}
		});
		return true;
	} else {
		return false;
	}
}
</script>
<script>

</script>
<%-- 
     @author : sarath kumar.
     this script is for prepare pagination.
     1.usage     : used in candidatePassportList().
--%>




<%-- 
     @author : sarath kumar.
     this script is for remove popUp.
     1.usage   : used in addEducation() function.
--%>


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

<%-- 
     @author : david.
     this script is for remove candidateEducation.
--%>
<div class="clearfix">

<form id="CandidatePassportform" name="CandidatePassportform" method="post"> 
		<input type="hidden" id="Passport" name="Passport" value="Passport" >
		<div class="popUpOut" id="popPassView" style="z-index:999999">
		<input type = "hidden" id="candidatePassportId"  name="candidatePassportId">
		<div class="popupHead">
			<h2 class="pull-left mt">Add Passport</h2><a href="javascript:void(0)" class="fa fa-close popCan mt mr" onClick="passportHandler();"></a>
		</div>
		
		<div class="popupMid clearfix overflow_inherint">
        
          <div class="col-sm-12 noPad mt clearfix" id="idNumberPassportDiv">
				<label class="col-md-4 padding_left">Passport Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right" >
              		<input type="text" name ="passportNumber" id ="passportNumber" placeholder="Passport Id" field-name = "Passport Id" class="form-control" data-validation ="required validate_length length8-20 validate_AtLeastOneAlphaNumber" onchange="immigration.validateCandidatePassport();" onkeyup = "immigration.validateCandidatePassport();"/>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name="country" id="countryName" field-name="Country" data-validation="required" data-live-search="true">
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
              	<input type="text" class="form-control calendar" value=""  placeholder="Valid From" name = "validFrom" id = "validFrom" field-name = "Valid From" data-validation="required"/>
				   <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('validFrom');"></i></span>
				</div>
              		
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              	<input type="text" class="form-control calendar" value="" name = "validTo" id = "validTo" placeholder="Valid To" field-name = "Valid To" data-validation="required"/>
				<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('validTo');"></i></span>
				</div>
				</div>
			</div>
			</div>		
			<div class="clearfix"></div>							
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="passport_save_id" onclick="savePassportVisaDetails()">
            <input value="Cancel" class="popCan" type="button" onClick="passportHandler();">
          </div>		
	
	</div>
	
	<!--popcode end  -->
		</form>
</div>
