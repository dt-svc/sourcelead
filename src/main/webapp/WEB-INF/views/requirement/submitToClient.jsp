<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
</c:if>

<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/style.css?staticTime=${staticTime}" />
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/summernote.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}">
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/bootstrap3-wysihtml5.all.min.js?staticTime=${staticTime}"></script> 
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/summernote.js?staticTime=${staticTime}"></script>
<style type="text/css">
ul.tagit li.tagit-choice .tagit-close .text-icon { visibility: hidden; }
ul.tagit li.tagit-choice .tagit-close:before { content:"x"; z-index: 999; position: absolute; right: 0; top: -5px; color: red;
</style>
<script>
function submitToClient(requirementId){
	$('.action-pop').slideUp();
	var result = "";
	'<sec:authorize access="hasAnyRole(\'ROLE_SUBMIT_TO_CLIENT\')">';
	result = result + '<div class="add_inter_head"> <span>Submissions</span> <span class="text-success col-md-offset-1">No Of Candidates Selected: <b id="checkedSubmitToClientCount">0</b></span> <a href="javascript:void(0)" onclick="addEmailtemplate('+requirementId+');" class="sub-btn1">Submit to client</a>';
	'</sec:authorize>';
	result = result + ' <div class="clearfix"></div>';
	result = result + ' </div>';
	
	 $.ajax({ 
	    	type: "POST",
	    	url: atsUrl+"/getShortListedCandidates?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId,
	    	success: function(data) {
	    		if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
	    		
	    		if(data.data != null && data.data != 'undefined' && data.data != "" && data.data != '[]' && data.data != 'failed'){
		    	   var json = data.data;
		           result = result + '     <div class="clearfix"></div>';
		           result = result + '   </div>';
		           result = result + '   <div class="require_wrap_table scroll_table mt40 tableData">';
		           result = result + '     <table id="SCPaginationId"  class="display" cellspacing="0" width="100%">';
		           result = result + '       <thead>';
		           result = result + '         <tr>';
		           result = result + ' <th><input type="checkbox" onclick="selectAllSubmitToClient();"  id="selectallSTC" ></th>';
		           result = result + '           <th>Name</th>';
		           result = result + '           <th>Email</th>';
		           result = result + '           <th>Ph. No</th>';
		           result = result + '           <th>Resume</th>';
		           result = result + '           <th>Applied on</th>';
		           result = result + '           <th>Rate</th>';
		           result = result + '           <th>Submission Rate</th>';
		           result = result + '           <th>Rate Per</th>';
		           result = result + '           <th>Source</th>';
		           result = result + '           <th>Experience</th>';
		           result = result + '         </tr>';
		           result = result + '       </thead>';
		           result = result + '       <tbody>';
		           
			    	if(json.length > 0)
			   		{
			    		for(var i=0; i<json.length; i++){
			    			var report = json[i];
			    			
			    			if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
		   	        			orgName = "("+report.orgName+")";
		   	        		}else{
		   	        			orgName = "";
		   	        		}
			    			
		    			    result = result + '<tr>';
		    			    result = result + '<td><input type="checkbox" onclick="getSubmitToClientCheckedCandidatesCount();"  class="shortlistedChecks" subrate  subrateper value='+report.id+' name="shortlist[]" ></td>';
		    			    
				            result = result + '<td><div><span title="'+report.candidateName+'">'+report.candidateName+'</span></div></td>';
				           
				           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
				        	   result = result + '<td><div><span title="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
					           }
					       else{
					        	result = result + '<td><div><span>--</span></div></td>';
				    	     }
				           
				           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
				        	   result = result + '<td><div><span>'+report.phoneNumber+'</span></div></td>';
					           }
					       else{
					        	result = result + '<td><div><span>--</span></div></td>';
				    	     }
				            if(report.fileURL != null && report.fileURL != '' && report.fileURL != 'undefined'){
				        	   result = result + '<td><div><span><a href="javascript:void(0)"><a download class="glyphicon glyphicon-download-alt" href=\"'+report.fileURL+'\" title="Download"></a></span></div></td>';
				           } 
				           
					       else{
					    	   result = result + '<td><div>--</div></td>';
				    	     }
				           
				           if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
				        	   result = result + '<td><div><span>'+report.createdDateStr+'</span></div></td>';
					           }
					       else{
					        	result = result + '<td><div><span>--</span></div></td>';
				    	     }
				           
				           if(report.finalizedRate != null && report.finalizedRate != '' && report.finalizedRate != 'undefined'){
				        	   result = result + '<td><div><span>'+report.finalizedRate+'</span></div></td>';
					         	}else if(report.expectedCTC != null && report.expectedCTC != '' && report.expectedCTC != 'undefined'){
				        	   result = result + '<td><div><span>'+report.expectedCTC+'</span></div></td>';
					         	}else{
					        	result = result + '<td><div><span>--</span></div></td>';
				    	     }
				           
				           if(report.submitToClientRate != null && report.submitToClientRate != '' && report.submitToClientRate != 'undefined'){
				        	   result = result + '<td><div><input type="text" class="form-control quantity" maxlength="10" onblur="validateRate(this,'+report.id+')" data-validation="validate_length length1-10" data-validation-optional="true" id="submissionRate'+report.id+'" name="submissionRate'+report.id+'" value="'+report.submitToClientRate+'"></div></td>';
					           }
					       else{
					    	   result = result + '<td><div><input type="text" class="form-control quantity" maxlength="10" onblur="validateRate(this,'+report.id+')" id="submissionRate'+report.id+'" name="submissionRate'+report.id+'" value=""></div></td>';
				    	     }
								result = result + '<td><select class="selct-picker" id="submissionRateType'+report.id+'" name="submissionRateType" onchange = "validateRate(this,'+report.id+')" field-name="Rate Type" data-validation-optional="true">';
								result = result + '<option value="">Rate Per</option>';
								result = result + '<option '+(report.submitToClientRateType == "Hourly" ? "selected" : '') +'>Hourly</option>';
								result = result + '<option  '+(report.submitToClientRateType == "Weekly" ? "selected" : '') +'>Weekly</option>';
								result = result + '<option  '+(report.submitToClientRateType == "Bi-weekly" ? "selected" : '') +'>Bi-weekly</option>';
								result = result + '<option  '+(report.submitToClientRateType == "Semi-Monthly" ? "selected" : '') +'>Semi-Monthly</option>';
								result = result + '<option  '+(report.submitToClientRateType == "Monthly" ? "selected" : '') +'>Monthly</option>';
								result = result + '<option  '+(report.submitToClientRateType == "Bi-Monthly" ? "selected" : '') +'>Bi-Monthly</option>';
								result = result + '<option  '+(report.submitToClientRateType == "Yearly" ? "selected" : '') +'>Yearly</option>';
								result = result + ' </select></td>';
				           
				           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
				        	   result = result + '<td><div><span title="'+report.createdUser+''+orgName+'">'+report.createdUser+''+orgName+'</span></div></td>';
					           }
					       else{
					        	result = result + '<td><div><span>--</span></div></td>';
				    	     }
				           
				           if(report.workExperience != null && report.workExperience != '' && report.workExperience != 'undefined'){
				        	   result = result + '<td><div><span>'+report.workExperience+'</span></div></td>';
					           }
					       else{
					        	result = result + '<td><div><span>--</span></div></td>';
				    	     }
				           
				           result = result + '       </tr>';
			    		}
				           result = result + '       </tbody>';
				           result = result + '       </table>';
				           result = result + '</div><div id="loading">';
				           result = result + '</div><div id="popUp">';
			    			
			    			$("#submitToClientDiv").empty();
			    			$("#submitToClientDiv").append(result);
			    			SCpagination();
			   			}
			   		}else if(data.error != 'failed'){
			    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-user'></i><h3>Shortlisted List is Empty</h3></div>";
			    		$('#submitToClientDiv').empty();
			    		$('#submitToClientDiv').append(result);
			    	}else if(data.error == 'failed'){
			    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-briefcase'></i><h3>Client Job was Closed/Onhold/Canceled. </h3></div>";
			    		$('#submitToClientDiv').empty();
			    		$('#submitToClientDiv').append(result);
			    	} 
				 }else if (data.indexOf('Sign In') !== -1) {
			 		location.replace(sourceleadUrl);
			 	} else {
			 		showError("Operation failed, please try again");
			 	}
	    	} 		
	 }); 
	

	$(".submitnew-wrap").addClass("submitnew-wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
}
</script>
<script>
$(document).on('keyup', '.quantity', function(event) {
	   var v = this.value;
	   if($.isNumeric(v) === false) {
	        //chop off the last char entered
	        this.value = this.value.slice(0,-1);
	   }
	   $('input.quantity').bind('cut copy paste', function (e) {
		    e.preventDefault(); //disable cut,copy,paste
		});
	});
</script>
<!-- this script is used for validate the rate, which accepts only numerics and upto 10 digits (Venkat Kesav) -->
<script>

function validateRate(elem, idval) {
	 $(elem).closest("tr").find('[type=checkbox]').attr("subrate", $("#submissionRate"+idval).val());
	 $(elem).closest("tr").find('[type=checkbox]').attr("subrateper", $("#submissionRateType"+idval).val());
	
}
</script>

<script>
function SCpagination(){  
    $('#SCPaginationId').DataTable( { 
    	"scrollCollapse": false,
    	"bPaginate": false,
    	"bLengthChange": false,
    	"bDestroy": true,
    	"aaSorting": [[1,'desc']],
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     	"sSearch": "",
     	 "sZeroRecords": "", "sEmptyTable": ""
     	}
		
    });
    $('#SCPaginationId').wrap("<div class='overflow_Auto width100Percnt'></div>")
}
</script>

<!-- For add New Applicant -->
<script>
    $(".submitToClient_close, .side_mask").click(function() {
    	$("#submitToClientDiv").empty();
    	$("#emailTemplateDiv").empty();
    	$(".submitnew-wrap").removeClass("submitnew-wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<script>
function selectAllSubmitToClient(){
	
	var checked = document.getElementById("selectallSTC").checked  
        if(checked) {
            $('.shortlistedChecks').each(function() {
                this.checked = true;   
            });
        }else{
            $('.shortlistedChecks').each(function() {
                this.checked = false;      
            });         
        } 
	
	var oTable = $('#SCPaginationId').dataTable();
	var rowcollectionsc =  oTable.$("input[name='shortlist[]']:checked", {"page": "all"});
	 rowcollectionsc.each(function(index,elem){
		 selectedShortlists[index] = $(elem).val();
		 
	 });
	 
	 $("#checkedSubmitToClientCount").html(selectedShortlists.length);
}
</script> 


<script>
var selectedShortlists = [];
function getSubmitToClientCheckedCandidatesCount(){
	 var oTable = $('#SCPaginationId').dataTable();
	 var rowcollectionsc =  oTable.$("input[name='shortlist[]']:checked", {"page": "all"});
	 rowcollectionsc.each(function(index,elem){
		 selectedShortlists[index] = $(elem).val();
		 
	 });
	 
	 $("#checkedSubmitToClientCount").html(selectedShortlists.length);
}
</script>


<script>
function clientSubmissions(requirementId){
	 var selectedShortlists = [];
	 var selectedShortlistsRate = [];
	 $("input[name='shortlist[]']:checked").each( function (i) {
		 selectedShortlists[i] = $(this).val();
		 var rateId = $(this).val();
		 var selectedRate = $("#submissionRate"+rateId).val();
		 selectedShortlistsRate[i] = selectedRate;
	 });
	 
	 if(selectedShortlists.length > 0){
		 if(selectedShortlists.length <= 50){
			 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({ 
			    	type: "POST",
			    	url: atsUrl+"/submitShortlistedToClient?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&submissionsList="+selectedShortlists+"&selectedShortlistsRate="+selectedShortlistsRate,
			        success: function(result) {
			        	$(".submitnew-wrap").removeClass("submitnew-wrap_active");
			    		$(".side_mask").fadeOut(200);
			    		$("body").css('overflow-y', 'auto');
			    		var allApplicants = 'all';
			    		requirementPipeline(requirementId,allApplicants); 
			    		pipelineList(requirementId);
			        },
			 }); 
		 }else{
			 alertMessage("<b>Select candidate for Client Submission</b>");
		 }
		
	 }else{
		 alertMessage("<b>Selected Candidates should not more than 50</b>");
	 }
	
}
</script>

<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='sideMask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelSumitToClient();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelSumitToClient();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".sideMask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelSumitToClient(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	$(".sideMask").fadeOut(200);
	$(".comMask").fadeOut(200);
}
</script>


<script>

function addEmailtemplate(requirementId){
	
	var result ="";
		 var selectedShortlists = [];
		 var selectedShortlistsRate = [];
		 var selectedShortlistsRatePer = [];
		 var isCountinue = true;
		 
		 var oTable = $('#SCPaginationId').dataTable();
		 var rowcollectionsc =  oTable.$("input[name='shortlist[]']:checked", {"page": "all"});
		 rowcollectionsc.each(function(index,elem){
			 selectedShortlists[index] = $(elem).val();
			 
			 var selectedRate = $(elem).attr("subrate");
			 selectedShortlistsRate[index] = selectedRate;
			 
			 var selectedRatePer =  $(elem).attr("subrateper");
			 selectedShortlistsRatePer[index] = selectedRatePer;
			 
			 if((selectedRate == '' || selectedRate == null || selectedRate == 'undefined') || (selectedRatePer == '' || selectedRatePer == null || selectedRatePer == 'undefined')){
				 isCountinue = false;
			 }else if((selectedRate != '' || selectedRate != null || selectedRate != 'undefined') && (selectedRatePer == '' || selectedRatePer == null || selectedRatePer == 'undefined')){
				 isCountinue = false; 
			 }else if((selectedRatePer != '' || selectedRatePer != null || selectedRatePer != 'undefined') && (selectedRate == '' || selectedRate == null || selectedRate == 'undefined')){
				 isCountinue = false; 
			 }
		 });
		 
		 if(isCountinue){
		 if(selectedShortlists.length >0){
		 $.ajax({ 
		    	type: "GET",
		    	url: atsUrl+"/addEmailtemplate?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&selectedShortlistsCount="+selectedShortlists,
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	var json = data.object;
			        	result = result +'<section class="clearfix">';
			        	result = result +'<div id="loadingDiv"></div>';
			        	result = result +'<form id="submitToClientMailTemplate" name="submitToClient" enctype="multipart/form-data">';
			        	result = result +'<section class="content-header clearfix">';	
						result = result +'  <div class="pull-left">';
					 	result = result +'    <h2>Mail Box</h2>';
						result = result +'  </div>';	
						result = result +'</section>';
						result = result +'	<div class="content-area clearfix">';
						result = result +'		<div class="form-horizontal compose-mail col-md-10 col-md-offset-1">';
						result = result +'			<div class="col-md-12 clearfix">';
						result = result +'				<label class="col-md-2 control-label text-muted small">From</label>';
						result = result +'				<div class="col-md-10">';
						result = result +'					<input type="text" value="'+json.submitToClientFromMailId+'" class="form-control input-sm" name="fromMailId" readonly>';
						result = result +'				</div>';
						result = result +'			</div>';
						result = result +'			<div class="col-md-12 clearfix">';
						result = result +'				<label class="col-md-2 control-label text-primary small">To</label>';
						result = result +'				<div class="col-md-10" id="toMailIdDiv">';
						result = result +'					<input type="text" value="'+json.submitToClientToMailId+'" class="form-control input-sm" name="toMailId" id="toMailId" readonly>';
						result = result +'				</div>';
						result = result +'			</div>';
						result = result +'			<div class="col-md-12 clearfix" id="othersMailIdDiv">';
						result = result +'				<label class="col-md-2 control-label text-warning small">Others</label>';
						result = result +'				<div class="col-md-10 mail-tagit">';
						result = result +'					<input type="hidden" value="" class="form-control input-sm" id="othersMailId" name="othersMail">';
						result = result +'					<ul id="mailsTagit"></ul>';
						result = result +'				</div>';
						result = result +'			</div>';
						result = result +'			<div class="col-md-12 clearfix">';
						result = result +'				<label class="col-md-2 control-label text-danger small">Subject</label>';
						result = result +'				<div class="col-md-10">';
						result = result +'					<input type="text" class="form-control input-sm" value="'+json.submitToClientMailSubject+'" name="mailSubject" data-validation="required" field-name="Subject">';
						result = result +'				</div>';
						result = result +'			</div>';
						result = result +'			<!-- Text Area -->';
						result = result +'			<div class="col-md-12 clearfix mb10 mt10">';
						result = result +'				<div class="col-md-12">';
						result = result +'					<div class="clearfix txt-area">';
						result = result +'						<textarea id="compose-textarea"  class="form-control" rows="2" placeholder="Message" style="height: 250px" name="mailBody" data-validation-optional="true" data-validation="validate_length length2-1000">'+json.mailBody+'</textarea>';
						result = result +'					</div>';
						result = result +'				</div>';
						result = result +'			</div>';
						result = result +'			 <div class="clearfix mb12" id="uploadResumeDiv">';
						result = result +'                 <div class="col-md-9">';
						result = result +'						<div class="timesheet_week_comm mail-attach timesheet_week_comm_div" id="uploadFileGroup">';
						result = result +'	                          <table class="timesheetAttachment" id="attachmentTable">';
						result = result +'							  <tr id="divAttachmentTsRow0">';
						result = result +'							  		<td>';
						result = result +'							  		<div id="0docFileuploadFileGroup">';
						result = result +'							  		<span>';
						result = result +'							  		<input type="file" name="0docFile" id="0docFile" class="timesheetAttachmentValidation">';
						result = result +'							  		<i class="fa fa-plus" onClick="addAttachment()"></i></span>';
						result = result +'							  		</div>';
						result = result +'							  		</td>';         
						result = result +'							  </tr>';
						result = result +'							  </table>';
						result = result +'	                          <input type="hidden" name="attcahmentMaxRowNumber"  id="attcahmentMaxRowNumber" value="1" />';
						result = result +'							  <div id="divAttachmentTd1" style="display: none; float: left;">';
						result = result +'										<div id="REPLACEROWIDdocFileuploadFileGroup">';
						result = result +'								  			<span><input type="file" name="REPLACEROWIDdocFile" id="REPLACEROWIDdocFile" class="timesheetAttachmentValidation">'; 
						result = result +'								  			<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i></span>';
						result = result +'								  		</div>';
						result = result +'							   </div>';
						result = result +'							</div>';
						result = result +'	                    </div>'; 
						result = result +'                 <div class="col-md-3">';
						result = result +'	                  <lable><input type="checkbox" name="sendTracker" checked>&nbsp;Send tracker</label>';
						result = result +'	                  </div>';
						
						result = result +'                 <div class="col-md-3">';
						result = result +'	                  <lable><input type="checkbox" name="sendResumes" checked>&nbsp;Send resumes</label>';
						result = result +'	                  </div>';
						
						
						result = result +'	                  </div>';
						result = result +'			<div class="col-md-12 clearfix mt">';
						result = result +'				<div class="col-md-6">';
						result = result +'				</div>';
						result = result +'				<div class="col-md-6">';
						result = result +'					<div class="pull-right">';
						result = result +'						<button type="button" id="sendMail"';
						result = result +'							class="btn btn-sm btn-success" onclick="submitToClientMail(\''+selectedShortlists+'\',\''+json.id+'\',\''+selectedShortlistsRate+'\',\''+selectedShortlistsRatePer+'\');">';
						result = result +'							<i class="fa fa-envelope-o"></i>&nbsp;Send</button>';
						result = result +'						<button type="button" class="btn btn-sm btn-default" onclick="cancelSubmit();">';
						result = result +'							<i class="fa fa-times" ></i>&nbsp;Cancel';
						result = result +'						</button>';
						result = result +'					</div>';
						result = result +'				</div>';
						result = result +'			</div>';
						result = result +'		</div>';
						result = result +'	</div>'; 
						result = result +'</form>';
						result = result +'</section>';
	                    $("#submitToClientDiv").empty();
	                    $("#emailTemplateDiv").empty();
	                    $("#emailTemplateDiv").append(result);
	                    $('#compose-textarea').summernote({
	  					  toolbar: [
	  					    // [groupName, [list of button]]
	  					    ['style', ['bold', 'italic', 'underline', 'clear']],
	  					    ['font', ['strikethrough', 'superscript', 'subscript']],
	  					    ['fontsize', ['fontsize']],
	  					    ['color', ['color']],
	  					    ['para', ['ul', 'ol', 'paragraph']],
	  						['table',['table']],
	  					    ['height', ['height']]
	    					]});
						$("#create-signature").wysihtml5();
	                    $('#mailsTagit').tagit({
	                        singleField: true,
	                        singleFieldNode: $('#othersMailId')
	                        ,placeholderText:"Enter emails"
	                        ,allowDuplicates: false
	                     	,afterTagAdded: function(evt, ui){
	                    	              checkEmailsForSubmitToClient();
	                    	              return false;
	                    	    }
	                        ,afterTagRemoved: function(evt, ui){
	                           checkEmailsForSubmitToClient();
	                           return false;
	                      }
	                    });
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		        },
		 }); 
}else{
	var result = "";
	result = result +"	<div class='comMask zInMask'></div>";
	result = result +"	   <div id='pipeline_user_pop' class='remove_user_pop remove_user_pop_a zInPop'>";
	result = result +"		<p>Please select atleast one candidate</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelSumitToClient();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelSumitToClient();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#pipeline_user_pop").slideDown(200);
	//$(".sideMask").fadeIn(200);
	$(".comMask").fadeIn(200);
 }
		 
 }else{
	 var selectedShortlists = [];
	 var selectedShortlistsRate = [];
	 var selectedShortlistsRatePer = [];
	 $("input[name='shortlist[]']:checked").each( function (i) {
		 selectedShortlists[i] = $(this).val();
		 var rateId = $(this).val();
		 var selectedRate = $("#submissionRate"+rateId).val();
		 selectedShortlistsRate[i] = selectedRate;
		 var rateIdper = $(this).val();
		 var selectedRatePer =  $("#submissionRateType"+rateIdper).val();
		 selectedShortlistsRatePer[i] = selectedRatePer;
	  
		 if(selectedRate != '' && selectedRatePer == ''){
		 var result = "";
		result = result +"	<div class='comMask zInMask'></div>";
		result = result +"	   <div id='pipeline_user_pop' class='remove_user_pop remove_user_pop_a zInPop'>";
		result = result +"		<p>Rate Per is Required for given Rate</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelSumitToClient();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelSumitToClient();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#pipeline_user_pop").slideDown(200);
		$(".comMask").fadeIn(200);
		
		}else if(selectedRate == '' && selectedRatePer != ''){
		 var result = "";
			result = result +"	<div class='comMask zInMask'></div>";
			result = result +"	   <div id='pipeline_user_pop' class='remove_user_pop remove_user_pop_a zInPop'>";
			result = result +"		<p>Submission Rate is Required</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelSumitToClient();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelSumitToClient();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#pipeline_user_pop").slideDown(200);
			$(".comMask").fadeIn(200);
	 }
 });	
}
}
</script>

<script language="javascript">
function validateEmail(email) {
   var regExp = /^.+@.+\..{2,3}$/
   return regExp.test(email);
}

function checkEmailsForSubmitToClient(){
	var emails = document.getElementById("othersMailId").value;
	var emailArray = emails.split(",");
	var invEmails = "";
	for(i = 0; i <= (emailArray.length - 1); i++){
		if(validateEmail(emailArray[i])){
			//Do what ever with the email.
		}else{
			invEmails += "'" +emailArray[i]+ "'"+ "\n";
		}
	}
	
	
	if(invEmails != "" && invEmails.length != 3 && invEmails != null && invEmails !="undefined" && invEmails !="\n"){
	      $("#sendMail").attr("disabled", "disabled");
		  $('#othersMailIdDiv').find('p.jquery_form_error_message').remove();
		  $("input#othersMailId").after("<p class='jquery_form_error_message'>"  +invEmails+" Invalid emails</span>");
		  document.getElementById("othersMailId").setAttribute('record-exist','yes');
		  document.getElementById("othersMailId").setAttribute('record-exist-errorMsg',invEmails+' Invalid emails'); 
	}else{
		$('input#othersMailId').removeAttr( "record-exist record-exist-errormsg");
		$('#othersMailIdDiv').find('p.jquery_form_error_message').remove(); 
        $("#sendMail").removeAttr("disabled");
	}
}
</script> 

  
<script>
  $(function () {
    //Add text editor
	  $("#compose-textarea").wysihtml5();
	  $("#create-signature").wysihtml5();
  });

</script> 
 
<script>
function addAttachment(){  
			
			var checkMaxTsRowNum = $("#attcahmentMaxRowNumber").val();
			var tbl = document.getElementById("attachmentTable");
			var maxRowNum = parseInt($("input[name = 'attcahmentMaxRowNumber']").val());
			$("input[name = 'attcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentTsRow" + maxRowNum);
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}			
</script>


<script type="text/javascript">
	function removeAttachmentRow(rowId) {
		var rowNum = $("#divAttachmentTsRow"+rowId).val();
		var maxRowNum = parseInt(rowNum);
		 document.getElementById("divAttachmentTsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentTsRow"+rowId).value= '';
		 var  rId = "divAttachmentTsRow"+rowId;
		  var r = document.getElementById("attachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
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


<script>
function submitToClientMail(selectedIds,requirementId,selectedShortlistsRate,selectedShortlistsRatePer){
	 if ($('#submitToClientMailTemplate').validate(false, validationSettings)){ 
			var frm = $('#submitToClientMailTemplate').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var jsonData = encodeURIComponent(con);
			var form = $('#submitToClientMailTemplate')[0];
    		var formData = new FormData(form); 
			$("#sendMail").addClass('button').val('Processing..');
     		$("#loadingDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				$.ajax({
					 type : "POST",
					 data: formData,
	          	     contentType : 'application/json; charset=utf-8',
	          	     enctype: 'multipart/form-data',
	      	         processData: false,
	      	         contentType:false,
	          	     dataType : 'json',
					url : atsUrl+"/submitToClient?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData+"&selectedIds="+selectedIds+"&requirementId="+requirementId+"&selectedShortlistsRate="+selectedShortlistsRate+"&selectedShortlistsRatePer="+selectedShortlistsRatePer,
					success : function(result) {
						cancelSubmit();
						var allApplicants = 'all';
			    		requirementPipeline(requirementId,allApplicants); 
			    		pipelineList(requirementId);
					},
				}); 
	 }
}
</script>


<script>
function cancelSubmit(){
    	$("#submitToClientDiv").empty();
    	$("#emailTemplateDiv").empty();
    	$(".submitnew-wrap").removeClass("submitnew-wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
}
</script>

<script>
	wysihtml5.dom.getPastedHtml = function(event) {
	var html;
	if (event.clipboardData) {
	html = event.clipboardData.getData('text/plain');
	}
	return html;
	};
</script>
