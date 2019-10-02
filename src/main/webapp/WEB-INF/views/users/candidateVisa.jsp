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
    $("#passportli").removeClass("current");
    $("#visali ").addClass("current");
    $("#lcali").removeClass("current");
    $("#immigrationCandidate").empty();
    $("#immigrationCandidate").append(result);
    candidateVisaList(candidateId);
    $("#visa_save_id").removeClass("disabled");
    $("#addVisa").click(function(){
		$(".popMask").fadeIn(200);
		$("#popUpVisa").fadeIn(100);
	});	
  //Tab script
	//$(".tabcontent").hide();
	//immigrationTabHandler();
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatVisaSave = getJSDateFormat(locationDate);
	 $('#visaValidFrom').datepicker({
	      dateFormat: finalDateFormatVisaSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	           // var dt = new Date(selected);
	           var dt = 	  $(this).datepicker('getDate', '+1d'); 
	           dt.setDate(dt.getDate() + 1);
	            $("#visaValidTo").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#visaValidTo').datepicker({
	      dateFormat: finalDateFormatVisaSave,
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = 	  $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
	            $("#visaValidFrom").datepicker("option", "maxDate", dt);
	        }
	  });
	  
		 

}
</script>
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
				$("#visa_save_id").removeClass("disabled");
			}

		</script>
<!-- Show success message after form submission script -->
<script>
function validateCandidateVisaAndSave() {
	if ($("#CandidateVisaform").validate(false, validationSettings)) {
	 var  country =   $('#visaCountry').val();
	 if(country == '') return;
	 var candidateId = $("#candidateID").val();
	 
	  var idNumberVal = null;
	  var id = null;

	  
	id = $("#candidateVisaId").val();
    visaNumber =$('#CandidateVisaform input[name="visaNumber"]').val().trim();  
	  
	  $('#idNumberDiv').find('p.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "GET",
		    	 url:vmsUrl + immiModulePath + "/validateCandidateVisa?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&visaNumber="+visaNumber+"&country="+country+"&id="+id, 
		        success: function(data) {
		        	if(data == true){
		        		   $("input#visaNumber").after("<p class='jquery_form_error_message'>"+visaNumber+" is Already Existed With Active Details.</span>");
						   $("#visa_save_id").addClass("disabled");
		        	}
		        	else{
		        		  $('input#visaNumber').removeAttr( "record-exist record-exist-errormsg");
		        		  $('#idNumberVisaDiv').find('p.jquery_form_error_message').remove(); 
                          $("#visa_save_id").removeClass("disabled");
                           saveVisaDetails(); // saving the visa details once validation is 
		        	}
		        
		        },
		    });
		
	} else {
		return false;
	}
}
function saveVisaDetails() {
	
	if ($("#CandidateVisaform").validate(false, validationSettings)) {
		$("#visa_save_id").addClass("disabled");
			var formData = getFormData("CandidateVisaform");
		 	var candidateId = $('#candidateID').val();
           
             var visaHistoryId = $('#visaHistoryId').val();
            $.ajax({
            url: vmsUrl + immiModulePath + "/saveVisaDetailsPage?candidateId="+candidateId+"&immigrationId=&visaHistoryId="+visaHistoryId,
            type : "POST",
			data : formData,
			success : function(result) {
							if (result && result.response === "INVALID_DATA") {
		                    	 showError(result.responseMessage);
		                    }else if (result.response === "AWKWARD") {
		                        location.href = "/errorPage";
		                    }else if(result.response === 'SUCCESS') {
		                    	
									$("#popUpVisa").hide();
									$(".popMask").fadeOut(200); // close the popup code															// popup code
									if($('#candidateVisaId').val().trim() != ''){
										showSuccessMsg("Successfully Updated Visa Details.");
			                    	}else{
			                    		showSuccessMsg("Successfully New Visa Details Added.");
			                    	}
									setTimeout(function(){candidateVisa(candidateId)}, 1000);
								
		                    }else{
		                    	 showError("Operation failed, please try again");
		                    }
							
							
			}
		});
		return true;
	} else {
		return false;
	}
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
	  
	 function enableVisaExtensiondDate(expiredVisaIdsLst){
			 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			  var finalDateFormatVisaSave = getJSDateFormat(locationDate);
			for( var i = 0 ; i<expiredVisaIdsLst.length; i++){
				 var visaId=expiredVisaIdsLst[i];
				// console.log("Expired visa id is "+visaId);
			    $('#visaExtendDate'+visaId).datepicker({
			      dateFormat: finalDateFormatVisaSave,
			      changeMonth: true,
			  	  changeYear: true, 
			  	  minDate:1,
			      onSelect: function (selected) {
			    	  var extensionDate = $(this).val();
			    	  addvisaExtensionDate($(this).attr("visaId"),extensionDate);
			         }
			    });
			}
	  };
    addvisaExtensionDate = function(visaId, extensionDate){
			 var candidateId = $("#candidateID").val();
			$.ajax({
		    	type: "GET",
		    	 url:vmsUrl + immiModulePath + "/addVisaExtensionDate?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId
		    	                       +"&immigrationId=&extensionDate="+extensionDate+"&visaId="+visaId, 
		        success: function(visaDetails) {
		        	showSuccessMsg("Successfully Visa Date updated.");
		        	setTimeout(function(){candidateVisa(candidateId)}, 1000);
		        }
			});
			
		}
    
   
	 $.ajax({  
          type : "GET",
          url: atsUrl+"/getAssociateVisaDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        		//console.log(response.data[0].expiredVisaLst);
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#candidateVisaList").empty();
               	 var result = "";
               	 result = result +"<table id='candidateVisaPagination' class='display' cellspacing='0' width='100%' >"; 
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
                   	 if(opt.id != null && opt.id != '' && opt.id != 'undefined'){
                   		 if(window.candidateStatusGlobal == '0'){
                   			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
                   		 }
                   		 else{
                   			 if(candidateType == "Candidates"){
                   				result = result+' <td><div class="width50px">';
                   				result = result+'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
                   				if(opt.currentStatus == 'Expired' && opt.status == 1){
                   				result = result+'<input type="text" class="calendar visaCalendarInput" visaId ='+opt.candidateVisaId+' id=visaExtendDate'+opt.candidateVisaId+' readonly="readonly">';
                   				}
                   				else if(opt.currentStatus == 'Active' && opt.status == 1){
                   					result = result+'<i id="edit" onclick=immigration.editVisaDetailsByRowId('+opt.id+')';
                   					result = result+'	class="glyphicon glyphicon-pencil cursorPointer"></i>';
                   				}
                   				result = result+'   <i id="delete"  onclick=deleteImmigrationRecord(deleteVisaDetails,'+opt.id+')' ;
               					result = result+'   class="glyphicon glyphicon-trash pull-right cursorPointer"></i></div></td>';
                   				result = result+'</sec:authorize>';
                   			 }else{
                   				result = result+' <td><div class="width50px">';
                   				result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS\')">';
                   				if(opt.currentStatus == 'Expired' && opt.status == 1){
                   				result = result+'<input type="text" class="calendar visaCalendarInput"  visaId ='+opt.candidateVisaId+'  id=visaExtendDate'+opt.candidateVisaId+' readonly="readonly">';
                   				}
                   				else if(opt.currentStatus == 'Active' && opt.status == 1){
                   					result = result+'<i id="edit" onclick=immigration.editVisaDetailsByRowId('+opt.id+')';
                   					result = result+'  class="glyphicon glyphicon-pencil cursorPointer"></i>';
                   				}
                   				result = result+'  <i id="delete"  onclick=deleteImmigrationRecord(deleteVisaDetails,'+opt.id+')';
               					result = result+'  class="glyphicon glyphicon-trash pull-right cursorPointer"></i></div></td>';
                   				result = result+'</sec:authorize>'; 
                   			 }
                   				 
                   		 }
                   		/*  if(window.candidateStatusGlobal == '0'){
								result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
                   		 }else{
                   			 if(candidateType == "Candidates"){
                   				 result = result+'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_EDUCATION\',\'ROLE_CROWDSOURCING_CANDIDATES\')"> <i class="glyphicon glyphicon-trash" onclick="deleteImmigrationRecord(deleteVisaDetails,'+opt.id+');" " title="Remove"></i></sec:authorize></div></td>';	 
                   			 }else{
                   				 result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_ASSOCIATE_EDUCATION_DETAILS\')"> <i class="glyphicon glyphicon-trash" onclick="deleteImmigrationRecord(deleteVisaDetails,'+opt.id+');" " title="Remove"></i></sec:authorize></div></td>';
                   			 }
                   			  
                   		 } */
                   	}
                   	 result  = result + '</tr>';
                     } 
                    result = result +" </tbody>";
                    result = result +"</table>";
                    $("#candidateVisaList").append(result); 
                    popFun();
                    if(typeof response.data[0] != 'undefined' 
        				&& response.data[0].expiredVisaLst != '' && response.data[0].expiredVisaLst != null){
        			enableVisaExtensiondDate(response.data[0].expiredVisaLst);
        		   } 
                    visaPagination();
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
function deleteVisaDetails(visaHistoryId){
	$.ajax({
        url: vmsUrl + immiModulePath + "/deleteCandidateVisaDetails?visaHistoryId="+visaHistoryId+"&candidateId="+candidateId,
        type: "GET",
        success: function (result) {
        	if(result=="success"){
        			showSuccessMsg("Successfully Record Deleted.");
        			setTimeout(function(){candidateVisa(candidateId)}, 1000);
            	    deleteImmigrationPopupDiv(); // removes the existing added delete popup div in html code.
        	}
        	
        }
    });
}
</script>
<script>
function visaPagination(){  
    $('#candidateVisaPagination').DataTable( { 
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
<%-- 
     @author : sarath kumar.
     this script is for prepare pagination.
     1.usage     : used in candidateVisaList().
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


<div class="clearfix">
<!--popcode start  -->
		<form id="CandidateVisaform" name="CandidateVisaform" method="post"> 
		<div class="popUpOut" id="popUpVisa" style="z-index:999999">
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
              		<input type="text" name ="visaNumber" id ="visaNumber" placeholder="Visa Id" field-name = "Visa Id" class="form-control"  
              			 onblur="immigration.validateCandidateVisaDetails();" 
              		 data-validation = "required validate_length length8-20 validate_AtLeastOneAlphaNumber"/>
				</div>
			</div>				          
          	<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name="country" id= "visaCountry"   onchange="immigration.validateCandidateVisaDetails();" field-name="Country" data-validation="required" data-live-search="true">
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
            <input value="Save" class="" type="button" id="visa_save_id" onclick="validateCandidateVisaAndSave()">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div><!--popcode end  -->	
   </div>
</form>
