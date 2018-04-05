<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<style>
.clsDatePicker {
	z-index: 100000 !important;
}
</style>
<script>
function closeCertificatePopup(){
	 $("#popUpDivEmp").removeClass("edit-details-active");
}
</script>
<%-- 
     @author : Ajith Matta, Shiva yogi,PrasadK.
     this script is for display candidate education tab data.
--%>

<script>
function candidateCertificate(candidateId){
	 
	var result = "";
	result  = result + '   <div class="project_tabs_inn_head addUser-org"> ';
	if(window.candidateStatusGlobal == '0'){
		 result  = result + '   	<a href="javascript:void(0)" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a> ';
	 }else{
		 result  = result + '   	<span class="pad-sea"> <a href="javascript:void(0)" id="newCandidate" onClick="addCertification('+candidateId+');flushCandiadate();" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a></span> ';
	 }
	result  = result + '<div class="success_pop" id="selector5" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="cerMsgDiv"></span>';
    result  = result + '</div>';
    result = result  +'     <div id="popUpDivEmp" class="edit-details col-md-4" > ';
	result = result  +'     </div>  ';
	result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result  = result + '   <div id="candidateCertificateList" class="datatable_filter"></div>';
	result  = result + '   </div>';
	result = result+'	    </div>';

    $("#tab-11").empty();
    $("#tab-12").empty(); 
    $("#tab-13").empty();  
    $("#tab-14").empty();  
    $("#tab-15").empty();  
    $("#tab-16").empty();  
    $("#history").empty();
    $("#tab-15").append(result);
    
    candidateCertificateList(candidateId);
    
}
</script>
<%-- 
     @author : Ajith Matta, Shiva yogi.
     this script is for validationSettings.
--%>

<script type="text/javascript">
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>

<%-- 
     @author : Ajith Matta, Shiva yogi,prasadK.
     this script is to Store  storeCertificate.
     1.usage     :  used in addCertification function
     2.parameter : candidateId,csrf.token
     3.ajax url implementation in CandidateCertificationsRestController.
--%>

<script>
function storeCertificate(candidateId){
	
			 var scoreType = document.getElementById("scoreType").value; 
			 if(scoreType != null && scoreType != ""){
				 $('#candidateScore').removeAttr('data-validation-optional');
				 $('#candidateScore').attr("data-validation","required"); 
			 }else{
				 $('#candidateScore').removeAttr('data-validation');
				 $('#candidateScore').attr("data-validation-optional","true"); 
			 }
		 
			
		if ($('#Candidate_certification_form').validate(false,validationSettings)) {
			
			var formData = getFormData('Candidate_certification_form');
			$("#Candidate_certification_form").attr("disabled", "disabled");
				$.ajax({
						type : "POST",
						data : formData,
						url : sourceleadUrl+ "/storeCandidateCertifications?"+tokenParamName+"="+tokenValue+"&candidateId=" + candidateId,
						success : function(response) {
							if (response && response.response === "INVALID_DATA") {
				    		    showError(response.responseMessage);
				    		} else if (response.response === "AWKWARD") {
				    		    location.href = "/errorPage";
				    		} else if (response.response === "SUCCESS") {
				    			closeCertificatePopup();
		    		        	$("#selector5").removeAttr("style");
		    	     			$("#selector5").css({ display: "block" });
		    	     			$("#cerMsgDiv").html("Success!! New Certification added");
		               			candidateCertificateList(candidateId);
		               			cerMsgFadein();
		               			recQwikCertificates(candidateId);
		                   		setTimeout(function(){
		    			 			candidateList();					 			
		    			 		},100);
				    		} else if (response.indexOf('Sign In') !== -1) {
				    			location.replace(sourceleadUrl);
				    		} else {
				    		    showError("Operation failed, please try again");
				    		}
						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur','#Candidate_certification_form',
			function() {
				$("#Candidate_certification_form").showHelpOnFocus();
				$("#Candidate_certification_form").validateOnBlur(false,validationSettings);
			});
</script>
<script>
function closeCertificatePopup(){
	 $("#popUpDivEmp").removeClass("edit-details-active");
}
</script>
<script>
	function cerMsgFadein() {
		$("#selector5").fadeIn(200);
        $('#selector5').delay(2000).fadeOut('slow');
	}
</script> 
<!-- this script is for serializeFormJSON -->
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


<!-- this script is for serializeFormJSON  End-->
<%-- 
     @author : Ajith Matta, Shiva yogi.
     this script is getting candidateCertificate List .
     1.usage     :  used in candidateCertificate  function
     2.parameter : candidateId ,csrf.token.
     3.ajax url implementation in CandidateCertificationsRestController.
--%>
<script>
function candidateCertificateList(candidateId){
	 
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  candCertListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  candCertListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  candCertListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  candCertListDate = 'YYYY-DD-MM'; 
	  }  
	 
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#candidateCertificateList").empty();
	               	 var result = "";
	               	 result = result +"<table id='candidateCertificatePagination' class='display' cellspacing='0' width='100%' >"; 
                    result = result +"<thead>"; 
                    result = result +"  <tr>"; 
                    result  = result +"           <th>Name</th>";
                	result  = result + "           <th>Provider</th>";
                	result  = result + "           <th>Score</th>";
                	result  = result + "           <th>Score Type</th>";
                	result  = result + "           <th>Description</th>";
                	result  = result + "           <th>Valid from <br>(<small class='custom-small'>"+candCertListDate+"</small>)</th>";
                	result  = result + "           <th>Valid to <br>(<small class='custom-small'>"+candCertListDate+"</small>)</th>";
                	result  = result + "           <th>Action</th>";
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                    for (var i = 0; i < response.data.length; i++) {
                   	 var opt = response.data[i];  
                   	 var score = "";
                   	 var scoreType ="";
                   	 var description ="";
                   	 var candidatevalidFromDateToString ="";
                   	 var candidatevalidToDateToString ="";
	            	if(opt.score != null && opt.score != 'undefined' && opt.score != ""){
	            		score = opt.score;
	            	}else{
	            		score = "----";
	            	}
	            	if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != ""){
	            		scoreType = opt.scoreType;
	            	}else{
	            		scoreType = "----";
	            	}
	            	if(opt.description != null && opt.description != 'undefined' && opt.description != ""){
	            		description = opt.description;
	            	}else{
	            		description = "----";
	            	}
	            	if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != ""){
	            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
	            	}else{
	            		candidatevalidFromDateToString = "----";
	            	}
	            	if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
	            		candidatevalidToDateToString = opt.candidatevalidToDateToString;
	            	}else{
	            		candidatevalidToDateToString = "----";
	            	}
                   	 result  = result + '         <tr>';
                   	 result  = result + '           <td><div><span>'+opt.name+'</span></div></td>';
                   	 result  = result + '           <td><div><span>'+opt.provider+'</span></div></td>';
                   	 result  = result + '           <td><div><span>'+score+'</span></div></td>';
                   	 result  = result + '           <td><div><span>'+scoreType+'</span></div></td>';
                   	 result  = result + '           <td><div><span>'+description+'</span></div></td>';
                   	 result  = result + '           <td><div><span>'+candidatevalidFromDateToString+'</span></div></td>';
                   	 result  = result + '           <td><div><span>'+candidatevalidToDateToString+'</span></div></td>';
                   	 if(window.candidateStatusGlobal == '0'){
                   		 result = result  +'	            <td><div class="tblicons"><a a href="javascript:void(0)" disabled="disabled" class="glyphicon glyphicon-pencil" id="editCandidateCertificate"></a>';
                   	}else{
                   		result = result  +'	            <td><div class="tblicons"><a href="javascript:void(0)" class="glyphicon glyphicon-pencil" id="editCandidateCertificate" href="javascript:void(0)" onClick="editCertification('+ opt.id+ ','+ candidateId+ ');"></a>';
                   	}
                   	 
                   	 if(window.candidateStatusGlobal == '0'){
                   		 result = result  +'<i class="glyphicon glyphicon-trash"></i></div></td>';
                   	}else{
                   		 result = result  +'<i class="glyphicon glyphicon-trash" style="color:#f51515 !important;" onclick="removeCertification('+opt.id+','+candidateId+');" id="removeCandidate"></i></div></td>';
                   	}
                   	 result  = result + '         </tr>';
                     } 
                    result = result +" </tbody>";
                    result = result +"</table>";
                    $("#candidateCertificateList").append(result); 
                    certificationPagination(); 
                    msgFadein();
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
       function msgFadein() {
           setTimeout(function(){
                   $('#selector1').fadeOut('slow'); }, 1000);
       }
</script>     
<script>
function certificationPagination(){  
    $('#candidateCertificatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
 }
</script>

<%-- 
     @author : Ajith Matta,Shiva yogi.
     this script is for add the  Candidate details Details .
     1.usage     :  used in candidateCertificate  function 
     2.parameter : candidateId .
--%>
<script>
function addCertification(candidateId) {	
		
	 	var result = '';
	 	result  = result + '     <!-- Popup Div -->                                                                                         ';
		result  = result + '         <form id="Candidate_certification_form" name="Candidate_certification_form" method="post">        ';
		result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
		result  = result + '               <div class="form-horizontal clearfix">';
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-12" id="candidatenameDiv">';
		result  = result + '                     <input type="text" class="form-control input-sm" value="" name="name"  placeholder="Name *" id="candidateName" ';
		result  = result + '                      data-validation="required validate_Name validate_length length2-100 " field-name="Name" >';
		result  = result + '                   </div>';
		result  = result + '                 </div>';
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-12">';
		result  = result + '                     <input type="text" class="form-control input-sm" value="" name="provider" placeholder="Provider *" id="candidateProvider"';
		result  = result + '                      data-validation="required validate_Name validate_length length2-100" field-name="Provider">';
		result  = result + '                   </div>';
		result  = result + '                 </div>';
		
		result  = result + '                 <div class="clearfix mb10">';
		result = result  +'	                     <div class="col-md-6 dropdown-select positionRelative paddingRight">';
		result = result  +'	                     <select class="newSelect"  name="scoreType" id="scoreType" onchange="selectScoreType();clearSearchKey();">';
		result = result  +' 					   <option value="" selected="selected">Score Type</option>';
		result = result  +'	                       <option value="Percentage">Percentage</option>';
		result = result  +'	                       <option value="CGPA">CGPA</option>';
		result = result  +'	                       <option value="GRADE">GRADE</option>';
		result = result  +'	                      </select>';
		result = result  +'	                   </div>';
		result  = result + '                   <div class="col-md-6 paddginLeft" positionRelative id="candidateScoreDiv">';
		result  = result + '                     <input type="text" class="form-control input-sm" value="" name="score" placeholder="Score" id="candidateScore" onkeyup="selectScoreType();scoreAndScoreType();"';
		result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-5" field-name="score">';
		result  = result + '                 	 </div>';
		result = result  +'	                   </div>';
		
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-12">';
		result  = result + '                     <textarea class="form-control" rows="1" name ="description"  placeholder="Description" id="candidateDescription"';
		result  = result + '                     data-validation-optional="true" data-validation="required validate_length length2-250"></textarea>';
		result  = result + '                   </div>';
		result  = result + '                 </div>';
		result  = result + '                 <div class="clearfix mb10">';
		result  = result + '                   <div class="col-md-6 positionRelative">';
		result  = result + '                     <input type="text" class="form-control input-sm"  value="" name ="validFrom" placeholder="Valid From"';
		result  = result + '                      data-validation-optional="true" data-validation="required" field-name="Valid From" id="StartDate" readonly>';
		result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
		result  = result + '                   </div>';
		result  = result + '                   <div class="col-md-6 positionRelative">';
		result  = result + '                     <input type="text" class="form-control input-sm" value="" name="validTo" placeholder="Validate To"';
		result  = result + '                     data-validation-optional="true" data-validation="required" field-name="Valid To" id="EndDate" readonly>';
		result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
		result  = result + '                   </div>';
		result  = result + '                 </div>';
		result  = result + '                 <div class="clearfix mr">';
		result  = result + '                   <button type="button" class="btn btn-sm btn-primary pull-right" id="addCandidate"  onclick="storeCertificate('+candidateId+');"  >Add</button>';
		result  = result + '                 </div>';
		result  = result + '               </div>';
		result  = result + '              </form>';
		$("#popUpDivEmp").empty();
		$("#popUpDivEmp").append(result);
		
		$(".edit-details").toggleClass("edit-details-active");
		
		$(".close-div").click(function(){
			 $(this).parent("div.edit-details").removeClass("edit-details-active");
			 });
		 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
			 e.stopPropagation();
		 });
	   
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatRecomondedCandCertSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatRecomondedCandCertSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatRecomondedCandCertSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatRecomondedCandCertSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatRecomondedCandCertSave = 'yy-dd-mm'; 
		  }
	   
	  $('#StartDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	           // var dt = new Date(selected);
	             var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#EndDate').datepicker({
	      dateFormat: finalDateFormatRecomondedCandCertSave,
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	          //  var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
	            $("#StartDate").datepicker("option", "maxDate", dt);
	        }
	  });
	}
</script>

<%-- 
     @author : Ajith Matta.
     this script is for edit Candidate certification .
     1.used in  candidateCertificateList function.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateCertificationsRestController.
--%>
<script>
function editCertification(candidateCertificationId, candidateId) {
	$.ajax({
		type : "POST",
		url : sourceleadUrl
				+ "/getCandidateCertificateBycandidateIdandCertificateId?"+tokenParamName+"="+tokenValue+"&candidateId="
				+ candidateId + "&candidateCertificationId=" + candidateCertificationId,
		success : function(response) {
			if (response && response.response === "INVALID_DATA") {
    		    showError(response.responseMessage);
    		} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
				var json = response.object;
				var result = '';
			 	result  = result + '     <!-- Popup Div -->                                                                                         ';
				result  = result + '         <form id="candidateCertificationEdit_form" name="candidateCertificationEdit_form" method="post">        ';
				result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
				result  = result + '               <div class="form-horizontal clearfix">';
				result  = result + '                 <div class="clearfix mb10">';
				result  = result + '                   <div class="col-md-12" id="candidatenameDiv">';
				result  = result + '                     <input type="text" class="form-control input-sm" value="'+ json.name+ '" name="name"  placeholder="Name *" id="candidateName" ';
				result  = result + '                      data-validation="required validate_Name validate_length length2-100 " field-name="Name" >';
				result  = result + '                   </div>';
				result  = result + '                 </div>';
				result  = result + '                 <div class="clearfix mb10">';
				result  = result + '                   <div class="col-md-12">';
				result  = result + '                     <input type="text" class="form-control input-sm" value="'+ json.provider+ '" name="provider" placeholder="Provider *" id="candidateProvider"';
				result  = result + '                      data-validation="required validate_Name validate_length length2-100" field-name="Provider">';
				result  = result + '                   </div>';
				result  = result + '                 </div>';
				
				result  = result + '                 <div class="clearfix mb10">';
				result = result  +'	                     <div class="col-md-4" positionRelative>';
				result = result  +'	                     <select class="newSelect"  name="scoreType"  value="'+ json.scoreType+ '"  id="scoreType" onchange="selectScoreType();clearSearchKey();">';
				result = result  +' 					   <option value="" selected="selected">Score Type</option>';
				if (json.scoreType == "Percentage") {
					result = result + '<option selected>Percentage</option>';
				} else {
					result = result	+ '<option>Percentage</option>';
				}
				if (json.scoreType == "CGPA") {
					result = result	+ '<option selected>CGPA</option>';
				} else {
					result = result	+ '<option>CGPA</option>';
				}
				if (json.scoreType == "GRADE") {
					result = result	+ '<option selected>GRADE</option>';
				} else {
					result = result + '<option>GRADE</option>';
				}
				result = result  +'	                      </select>';
				result = result  +'	                   </div>';
				result  = result + '                   <div class="col-md-8" positionRelative id="candidateScoreDiv">';
				result  = result + '                     <input type="text" class="form-control input-sm" value="'+ json.score+ '" name="score" placeholder="Score" id="candidateScore" onkeyup="selectScoreType();scoreAndScoreType();"';
				result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-5" field-name="score">';
				result  = result + '                 	 </div>';
				result = result  +'	                   </div>';
				
				result  = result + '                 <div class="clearfix mb10">';
				result  = result + '                   <div class="col-md-12">';
				result  = result + '                     <input type="text" class="form-control" rows="1" name ="description" value="'+ json.description+ '"  placeholder="Description" id="candidateDescription"';
				result  = result + '                     data-validation-optional="true" data-validation="required">';
				result  = result + '                   </div>';
				result  = result + '                 </div>';
				result  = result + '                 <div class="clearfix mb10">';
				result  = result + '                   <div class="col-md-6 positionRelative">';
				if(json.candidatevalidFromDateToString !=null && json.candidatevalidFromDateToString!='undefined'&& json.candidatevalidFromDateToString !=''){
					
					result  = result + '                     <input type="text" class="form-control input-sm"  value="'+ json.candidatevalidFromDateToString+ '" name ="validFrom" placeholder="Valid From" ';
					result  = result + '                      data-validation-optional="true" data-validation="required" field-name="Valid From" id="StartDateEdit" readonly>';
					result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
				}else{
					
					result  = result + '                     <input type="text" class="form-control input-sm"  value=" " name ="validFrom" placeholder="Valid From" ';
					result  = result + '                      data-validation-optional="true" data-validation="required" field-name="Valid From" id="StartDateEdit" readonly>';
					result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
					
				}
				
				result  = result + '                   </div>';
				result  = result + '                   <div class="col-md-6 positionRelative">';
				if(json.candidatevalidToDateToString !=null && json.candidatevalidToDateToString!='undefined'&& json.candidatevalidToDateToString !=''){
					
					result  = result + '                     <input type="text" class="form-control input-sm" value="'+ json.candidatevalidToDateToString+ '" name="validTo" placeholder="Validate To" ';
					result  = result + '                     data-validation-optional="true" data-validation="required" field-name="Valid To" id="EndDateEdit" readonly>';
					result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
				}else{
					
					result  = result + '                     <input type="text" class="form-control input-sm" value = " " name="validTo" placeholder="Validate To" ';
					result  = result + '                     data-validation-optional="true" data-validation="required" field-name="Valid To" id="EndDateEdit" readonly>';
					result  = result + '                     <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>';
				}
				
				
				result  = result + '                   </div>';
				result  = result + '                 </div>';
				result  = result + '                 <div class="clearfix mr">';
				result = result	+ '	                   <input type="hidden"  value="'+candidateCertificationId+'" id="candidateCertificationId"  name="candidatecertificateID" >';
				result = result	+ '	                   <input type="hidden"  value="'+candidateId+'" id="candidateId" >';
			
				result  = result + '                   <button type="button"  id="addCandidate"  onClick ="updateCandidateCertification('+ candidateId+ ','+ candidateCertificationId+ ');" class="btn btn-sm btn-primary pull-right"  >Update</button>';
				result  = result + '                 </div>';
				result  = result + '               </div>';
				result  = result + '              </form>';
				$("#popUpDivEmp").empty();
				$("#popUpDivEmp").append(result);
				
				$(".edit-details").toggleClass("edit-details-active");
				
				$(".close-div").click(function(){
					 $(this).parent("div.edit-details").removeClass("edit-details-active");
					 });
				 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
					 e.stopPropagation();
				 });

				 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
				  var finalDateFormatRecCandCert = '';
				  if(locationDate.match("yyyy-MM-dd")){
					  finalDateFormatRecCandCert = 'yy-mm-dd';
				  }if(locationDate.match("dd-MM-yyyy")){
					  finalDateFormatRecCandCert = 'dd-mm-yy'; 
				  }if(locationDate.match("MM-dd-yyyy")){
					  finalDateFormatRecCandCert = 'mm-dd-yy'; 
				  }if(locationDate.match("yyyy-dd-MM")){
					  finalDateFormatRecCandCert = 'yy-dd-mm'; 
				  }
			   
			  $('#StartDateEdit').datepicker({
			      dateFormat: finalDateFormatRecCandCert,
			      changeMonth: true,
			  	  changeYear: true, 
			      onSelect: function (selected) {
			            //var dt = new Date(selected);
			             var dt = $(this).datepicker('getDate', '+1d');
			            dt.setDate(dt.getDate() + 1);
			            $("#EndDateEdit").datepicker("option", "minDate", dt);
			        }
			  });
			  
			  $('#EndDateEdit').datepicker({
			      dateFormat: finalDateFormatRecCandCert,
			      changeMonth: true,
			  	  changeYear: true, 
			        onSelect: function (selected) {
			           // var dt = new Date(selected);
			            var dt = $(this).datepicker('getDate', '-1d');
			            dt.setDate(dt.getDate() - 1);
			            $("#StartDateEdit").datepicker("option", "maxDate", dt);
			        }
			  });
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
     @author : Ajith Matta.
     this script is for update Candidate certification .
     1.usage     :  used in editCertification() function 
     2.parameter : csrf token .
     3.ajax url implementation in CandidateCertificationsRestController.
--%>
      
      <script type="text/javascript">
	function updateCandidateCertification(candidateId, candidateCertificationId) {

		var scoreType = document.getElementById("scoreType").value; 
		 if(scoreType != null && scoreType != ""){
			 $('#candidateScore').removeAttr('data-validation-optional');
			 $('#candidateScore').attr("data-validation","required"); 
		 }else{
			 $('#candidateScore').removeAttr('data-validation');
			 $('#candidateScore').attr("data-validation-optional","true"); 
		 }
		
		if ($('#candidateCertificationEdit_form').validate(false,validationSettings)) {

			var formData = getFormData('candidateCertificationEdit_form');
			$("#addCandidate").attr("disabled", "disabled");
			$.ajax({
						type : "POST",
						data : formData,
						url : sourceleadUrl+ "/updateCandidateCertificationsBycandidateIdandcandidateCertificateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&candidateCertificationId"+ candidateCertificationId,
						success : function(result) {
							if (result && result.response === "INVALID_DATA") {
				    		    showError(result.responseMessage);
				    		} else if (result.response === "AWKWARD") {
				    		    location.href = "/errorPage";
				    		} else if (result.response === "SUCCESS") {
				    			closeCertificatePopup();
								$("#selector5").removeAttr("style");
				     			$("#selector5").css({ display: "block" });
				     			$("#cerMsgDiv").html("Certification details updated");
									candidateCertificateList(candidateId);
									cerMsgFadein();
									recQwikCertificates(candidateId);
									setTimeout(function(){
							 			candidateList();					 			
							 		},100);
				    		} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
						},
					});
			return true;
		} else {
			return false;
		}
		$('body').on('blur','#candidateCertificationEdit_form',
				function() {
					$("#candidateCertificationEdit_form").showHelpOnFocus();
					$("#candidateCertificationEdit_form").validateOnBlur(false,
							validationSettings);
				});
	}
</script>
  <%-- 
     @author : David Sammeta.
     this script is getting removing Certification in the  List .
     1.usage     :  used in candidateCertificateList  function
     2.parameter : candidateId ,csrf.token,candidateemployerId
     3.ajax url implementation in CandidateCertificationRestController.
--%>    
<script>
function removeCertification(candidateCertificationId, candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop_certificates' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are choosing to delete the Certification</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedCertification("+candidateCertificationId+","+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteCerPopup();'></b>";
	result = result +"	</div>";
	$("#deleteCertificationPopUp").empty();
	$("#deleteCertificationPopUp").append(result);
	$("#remove_user_pop_certificates").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script> 
<div id="deleteCertificationPopUp"></div> 
<script type="text/javascript">
function deleteCerPopup(){
	$("#remove_user_pop_certificates, .project_inn_mask").fadeOut();
}
</script>
<script>
function deleteSelectedCertification(candidateCertificationId,candidateId){
	
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';	
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/removeCandidateCertificateBycandidateIdandcandidateCertificateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&candidateCertificationId="+candidateCertificationId,        
          success :function(response) {
        	  if (response.response === "AWKWARD") {
                  location.href = "/errorPage";
              } else if (response.response === "SUCCESS") {
            	  $("#popUpDivEmp").removeClass("edit-details-active");
      			 $("#selector5").removeAttr("style");
       			 $("#selector5").css({ display: "block" });
       			 $("#cerMsgDiv").html("Certification deleted");
       			$("#remove_user_pop_certificates, .project_inn_mask").fadeOut();
       			candidateCertificateList(candidateId);
       			recQwikCertificates(candidateId);
       			cerMsgFadein();
       			setTimeout(function(){
			 			candidateList();					 			
			 	    },100);             
              } else if (response.indexOf('Sign In') !== -1) {
                  location.replace(sourceleadUrl);
              } else {
                  showError("Operation failed, please try again");
              }
        },
     }); 
}
</script> 
 <!-- this script is for removing the Certification  Ends -->  
      
<%-- 
     @author : 
     this script is to flush candidate.
--%>
<script type="text/javascript">
	function flushCandiadate() {
		$("#addCandidate").removeAttr("disabled");
		$('#candidateName').val('');
		$('#candidateProvider').val('');
		$('#candidateScore').val('');
		$('#candidateDescription').val('');
		$('#StartDate').val('');
		$('#EndDate').val('');
		$(".error").removeClass("error");
		$('#newCandidate').find('p.jquery_form_error_message').remove();
	}
</script>


<%-- 
     @author : shivayogi , Ajith.
     this script is to display candidate score validation based on scoreType.
--%>
<script type="text/javascript">
function selectScoreType(){
	var str = document.getElementById("candidateScore").value;
	if (str == 'a' || str == 'b' || str == 'c' || str == 'd' || str == 'e') {
		var strToUpper = str.toUpperCase();
		$('#candidateScore').val(strToUpper);
	}
	
	 var selectedText = document.getElementById("scoreType").value;
	/* if(selectedText != null && selectedText !='' && selectedText !='undefined'){
		 $("#candidateScore").removeAttr("readonly");
	}else{
		 $("#candidateScore").attr("readonly", "readonly");
	} */
	 var canScore = document.getElementById("candidateScore").value;
	 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
	 if(selectedText =='Percentage'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(canScore.match(floatNum)){
			  $("#addCandidate").removeAttr("disabled");
			   return true;
		  }		  
		 else{
				$("input#candidateScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid Percentage</span>");
				 $("#addCandidate").attr("disabled", "disabled");
				return false; 
		 	 }
	  }
	 else if(selectedText =='CGPA'){
		  var cgpanum = /^(([\d][\.])?([\d])|10)?$/
			  if(canScore.match(cgpanum)){
				  $("#addCandidate").removeAttr("disabled");
				   return true;
			  }		  
			 else{
				 $("input#candidateScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid CGPA value</span>");
				 $("#addCandidate").attr("disabled", "disabled");
			return false; 
			 }
	  } 
	 else if(selectedText =='GRADE'){
		 var grades = /^([A-E][-+]{0,2})?$/
			 if (canScore.match(grades)) {
				 $("#addCandidate").removeAttr("disabled");
				 return true;
			}else{
				$("input#candidateScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Grade </p>");
				 $("#addCandidate").attr("disabled", "disabled");
	    		return false;
	    		
			}
   		}
		 }
	  
</script>

<script type="text/javascript">
function scoreAndScoreType(){
	 var candidateScore = document.getElementById("candidateScore").value;
	 var selectedText = document.getElementById("scoreType").value;
	 var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
	 var cgpanum = /^(([\d][\.])?([\d])|10)?$/
	 var grades = /^([A-E][-+]{0,2})?$/
	 if(candidateScore.match(floatNum)){
		 $("#scoreType").val('Percentage'); 
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
		 $("#addCandidate").removeAttr("disabled");
	 }
	 if(candidateScore.match(grades)){
		 $("#scoreType").val('GRADE'); 
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
		 $("#addCandidate").removeAttr("disabled");
	 }
	 if(candidateScore.match(cgpanum)){
		 $("#scoreType").val('CGPA'); 
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
		 $("#addCandidate").removeAttr("disabled");
	 }
	 if((selectedText == '') && (!candidateScore.match(floatNum)) && (!candidateScore.match( cgpanum)) && (!candidateScore.match(grades))){
		 $("input#candidateScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Score </p>");
	 }
	 if(selectedText == '' && candidateScore == ''){
		 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
	 }
	 if(candidateScore == ''){
		 $("#scoreType").val('');
	 }
	 
}
</script>

<%-- 
     @author : shivayogi , Ajith.
     this script is to flush candidate value when score type is changed.
--%>
<script>
function clearSearchKey() {
	   $('#candidateScore').val('');
	   $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();

}
</script>                     