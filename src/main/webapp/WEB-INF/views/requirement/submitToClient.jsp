<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>


<!-- newly added -->

<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" href="resources/css/bootstrap3-wysihtml5.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link href="resources/css/timesheet.css" rel="stylesheet">


<script src="resources/js/jquery-1.11.3.min.js"></script>
  <script src="resources/js/bootstrap3-wysihtml5.all.min.js"></script> 
  <script src="resources/js/bootstrap.min.js"></script> 
  <script src="resources/js/masterHeaderAction.js"></script> 
  <script src="resources/js/chosen.jquery.min.js"></script> 

<!-- newly added end -->

<script>
function submitToClient(requirementId){
	var sourceleadUrl ='${sourceleadUrl}';
	var result = "";
	//var status = "SHORTLIST";
	/* result = result + '<div class="add_inter_head"> <span>Submissions</span><a href="javascript:void(0)" onclick="clientSubmissions('+requirementId+');" class="sub-btn1">Submit to client</a>'; */
	'<sec:authorize access="hasAnyRole(\'ROLE_SUBMIT_TO_CLIENT\')">';
	result = result + '<div class="add_inter_head"> <span>Submissions</span><a href="javascript:void(0)" onclick="addEmailtemplate('+requirementId+');" class="sub-btn1">Submit to client</a>';
	'</sec:authorize>';
	result = result + ' <div class="clearfix"></div>';
	result = result + ' </div>';
	
	 $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getShortListedCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,
	    			
	    	success: function(data) {
		       
	    	   var json = JSON.parse(data);
		        
	           result = result + '     <div class="clearfix"></div>';
	           result = result + '   </div>';
	           result = result + '   <div class="require_wrap_table">';
	           result = result + '     <table id="shortlistedPagination"  class="display" cellspacing="0" width="100%">';
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
	           result = result + '           <th>Source</th>';
	           result = result + '           <th>Experince</th>';
	           result = result + '         </tr>';
	           result = result + '       </thead>';
	           result = result + '       <tbody>';
		        
		    	if(json.length > 0)
		   		{
		    		for(var i=0; i<json.length; i++){
		    			var report = json[i];
		    			
	    			    result = result + '<tr>';
	    			    result = result + '<td><input type="checkbox"  class="shortlistedChecks" value='+report.id+' name="shortlist[]" ></td>';
	    			    
			            result = result + '<td><div><span>'+report.candidateName+'</span></div></td>';
			           
			           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
			        	   result = result + '<td><div><span>'+report.candidateEmail+'</span></div></td>';
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
			           
			           if(report.resumeName != null && report.resumeName != '' && report.resumeName != 'undefined'){
/* 			        	   result = result + '<td><div><span><a href="javascript:void(0)"><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeId='+report.id+'\"></a>'+report.resumeName+'</span></div></td>';*/
 			        	   result = result + '<td><div><span><a href="javascript:void(0)"><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeId='+report.candidateResumes.id+'\"></a>'+report.resumeName+'</span></div></td>';
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
			           
			           if(report.expectedCTC != null && report.expectedCTC != '' && report.expectedCTC != 'undefined'){
			        	   result = result + '<td><div><span>'+report.expectedCTC+'</span></div></td>';
				           }
				       else{
				        	result = result + '<td><div><span>--</span></div></td>';
			    	     }
			           
			           if(report.expectedCTC != null && report.expectedCTC != '' && report.expectedCTC != 'undefined'){
			        	   result = result + '<td><div><input type="text" class="form-control" id="submissionRate'+report.id+'" name="submissionRate'+report.id+'" value="'+report.expectedCTC+'"></div></td>';
				           }
				       else{
				    	   result = result + '<td><div><input type="text" class="form-control" id="submissionRate'+report.id+'" name="submissionRate'+report.id+'" value=""></div></td>';
			    	     }
			           
			           if(report.userName != null && report.userName != '' && report.userName != 'undefined'){
			        	   result = result + '<td><div><span>'+report.userName+'</span></div></td>';
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
			           
			           result = result + '       </tbody>';
			           result = result + '</div><div id="loading">';
			           result = result + '</div><div id="popUp">';
		    			
		    			$("#submitToClientDiv").empty();
		    			$("#submitToClientDiv").append(result);
		    			
		    		}
		   		}else{
		    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-user'></i><h3>Shortlisted List is Empty</h3></div>";
		    		$('#submitToClientDiv').empty();
		    		$('#submitToClientDiv').append(result);
		    	}
	    	}
	    	
	    		
	    }); 
	

	$(".submitnew-wrap").addClass("submitnew-wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
}
</script>




<script>
function pagination(){  
    $('#shortlistedPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     	"sSearch": ""
     	}
		
    } );
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
}
</script> 


<script>
function clientSubmissions(requirementId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var selectedShortlists = [];
	 var selectedShortlistsRate = [];
	 $("input[name='shortlist[]']:checked").each( function (i) {
		 selectedShortlists[i] = $(this).val();
		 var rateId = $(this).val();
		 var selectedRate = $("#submissionRate"+rateId).val();
		 selectedShortlistsRate[i] = selectedRate;
	 });
	 
	 if(selectedShortlists.length > 0){
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		 $.ajax({ 
		    	type: "POST",
		    	url: sourceleadUrl+"/submitShortlistedToClient?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionsList="+selectedShortlists+"&selectedShortlistsRate="+selectedShortlistsRate,
		        success: function(result) {
		        	$(".submitnew-wrap").removeClass("submitnew-wrap_active");
		    		$(".side_mask").fadeOut(200);
		    		$("body").css('overflow-y', 'auto');
		    		var allApplicants = 'all';
		    		requirementPipeline(requirementId,allApplicants); 
		        },
		 }); 
	 }else{
		 alertMessage("<b>Select candidate for Client Submission</b>");
	 }
	
}
</script>

<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<script>
function addEmailtemplate(requirementId){
 	var result ="";
	 var sourceleadUrl ='${sourceleadUrl}';
		 var selectedShortlists = [];
		 $("input[name='shortlist[]']:checked").each( function (i) {
			 selectedShortlists[i] = $(this).val();
		});
		 if(selectedShortlists.length >0){
		 $.ajax({ 
		    	type: "GET",
		    	url: sourceleadUrl+"/addEmailtemplate?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&selectedShortlistsCount="+selectedShortlists,
		        success: function(data) {
		        	 var json = JSON.parse(data);
		        	result = result +'<section class="clearfix">';
		        	result = result +'<div id="loading"></div>';
		        	result = result +'<form id="submitToClientMailTemplate" name="submitToClient" enctype="multipart/form-data">';
		        	result = result +'<section class="content-header clearfix">';	
					result = result +'  <div class="pull-left">';
				 	result = result +'    <h2>Mail Box</h2>';
					result = result +'  </div>';	
					result = result +'  <div class="pull-right brud-crum">Mail &raquo; Mail Box</div>';
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
					result = result +'						<textarea id="compose-textarea"  class="form-control" rows="2" placeholder="Message" style="height: 250px" name="mailBody" data-validation-optional="true" data-validation="validate_length length2-500">'+json.mailBody+'</textarea>';
					result = result +'					</div>';
					result = result +'				</div>';
					result = result +'			</div>';
					result = result +'			 <div class="clearfix mb12" id="uploadResumeDiv">';
					result = result +'                 <div class="col-md-9">';
					result = result +'						<div class="timesheet_week_comm mail-attach" id="uploadFileGroup">';
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
					result = result +'							class="btn btn-sm btn-success" onclick="submitToClientMail(\''+selectedShortlists+'\',\''+json.id+'\');">';
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
					$("#compose-textarea").wysihtml5();
					$("#create-signature").wysihtml5();
                    $('#mailsTagit').tagit({
                        singleField: true,
                        singleFieldNode: $('#othersMailId')
                        ,placeholderText:"Enter emails"
                        ,allowDuplicates: false
                     	,afterTagAdded: function(evt, ui){
                    	              checkEmails();
                    	              return false;
                    	    }
                        ,afterTagRemoved: function(evt, ui){
                           checkEmails();
                           return false;
                      }
                    });
		        },
		 }); 
}else{
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='pipeline_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Please select atleast one candidate</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#pipeline_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
 }
}
</script>

<script language="javascript">
function checkEmail(email) {
   var regExp = /^.+@.+\..{2,3}$/
   return regExp.test(email);
}

function checkEmails(){
	var emails = document.getElementById("othersMailId").value;
	var emailArray = emails.split(",");
	var invEmails = "";
	for(i = 0; i <= (emailArray.length - 1); i++){
		if(checkEmail(emailArray[i])){
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
function submitToClientMail(selectedIds,requirementId){
	 if ($('#submitToClientMailTemplate').validate(false, validationSettings)){ 
			var frm = $('#submitToClientMailTemplate').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var jsonData = encodeURIComponent(con);
			var form = $('#submitToClientMailTemplate')[0];
    		var formData = new FormData(form); 
			var sourceleadUrl = '${sourceleadUrl}';
			$("#sendMail").addClass('button').val('Processing..');
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
				$.ajax({
					 type : "POST",
					 data: formData,
	          	     contentType : 'application/json; charset=utf-8',
	          	     enctype: 'multipart/form-data',
	      	         processData: false,
	      	         contentType:false,
	          	     dataType : 'json',
					url : sourceleadUrl+"/submitToClient?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData+"&selectedIds="+selectedIds+"&requirementId="+requirementId,
					success : function(result) {
							location.replace(sourceleadUrl+"/requirementList");
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

