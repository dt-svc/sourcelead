<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script>

<script type="text/javascript">
$(document).ready(function(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	getContractTagsList();
// 	if("" != localStorage.getItem("stage") && null != localStorage.getItem("stage")){
		displyContract(localStorage.getItem("stage"));
// 	}
});
</script>

<!-- Content Area -->
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View Contract</h2>
      </div>
     <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_vmsUrl}/contractListbyStatus">Contracts</a> &raquo; View Contract</div>
    </section>
    

		   <div class="success_pop tagStyles" id="addContractTagAlert">
				<i class="fa fa-check-circle"></i>
		   		<span></span>
		   </div>
		   <div class="remove_pop tagStyles" id="deleteContractTagAlert">
				<i class="fa fa-times"></i>
		   		<span></span>
		   </div>
		   
		   
    
    <div class="project_wrap clearfix">
    
     <div class="quickviewTabs viewontracts">
		<ul class="nav nav-tabs mb10">
		   <li class="active" id="candQuickView"><a data-toggle="tab" href="#contractDetails">Contract Details</a></li>
		   <li id="candDetailView" onclick="addContractNotes();"><a data-toggle="tab" href="#notes" >Notes</a></li>
		 </ul>
		
		 <div class="tab-content backgroundWhite">
		   <div id="contractDetails" class="tab-pane fade in active">
		   
		        <div id="loading"></div>
      <div class="clearfix form-horizontal">
        <div class="row newContrWrap">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           	<h2>Contract Details</h2>
			<div id="viewPopupDiv"></div>
        	</div>
      	</div>
       </div> 
		   
		  </div>
		  <div id="notes" class="tab-pane fade">
		   <div class="clients_side_wrap_inn">
				<div class="timesheet_head project_tabs_inn_head">
<ul class="padding_right">
	<a href="javascript:void(0)" onclick="addClientNotes();" class="addUser-org"><i class="glyphicon glyphicon-plus paddingRight"></i>Add Note</a>
	<div id="popUpDiv" class="edit-details col-md-4">
		<form role="form" id="notes_Contracts" name="notes_Contracts" method="post">
	<i class="fa fa-close close-div" onclick="closeDocPopup();"></i>
	<input type="hidden" name="noteType" id="noteType" value = "Contract">
		<div class="col-md-12 inputOther mt10 inputOther">
		<div class="form-horizontal clearfix"><div class="clearfix">
		<textarea rows="2" placeholder="Note" value="" id="noteDescription" name="noteDescription" data-validation="required validate_length length1-5000" field-name="Note" class="form-control" ></textarea></div>
		
		<div class="row clearfix mt5">
		<div class="checkBox col-md-9">
		<input type="checkbox" value="Y" id="allowAll" name="allowAll" checked="checked">
		<span>Allow all users</span>
		</div>
		<div class="col-md-3" id="docAddButton">
		<div class="pull-right">
			<input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary" onclick="storeContractNotes();"></div>
		</div>
		</div>
		
		</div>
		</div>
		</form>
		</div>
		
	</ul>
	</div>
<div class="clearfix"></div>

				</div>
				<div class="clearfix">
					<div id="viewNotesDiv"></div>	
				</div>
				
		   </div>
		    </div>
		</div>			     
		  </div>
    
    
      <div class="cearfix">
		  <div class="newTagsWrap">
			    <ul class="clearfix tagsWrap tagsWrapBor">
				<select multiple class="chosen-select form-control" name="tagName" id="contractTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
				</select>
				</ul>
				<div class="clearfix"></div>
		  </div>
		 </div>    
    
     <!-- Content Area Ends--> 
</section>




<!--  ============================= Contract tags related scripts written by Laxman Start ================================ -->
<script>
var select, chosen;
select = $("#contractTagName");
select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
chosen = select.data('chosen');
chosen.search_field.on('keyup', function(e)
{
	var anyKeyword = this.value.replace(",","");
    if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
    {
    	
    	var par = this.parent;
        var option = $("<option>").val(anyKeyword).text(anyKeyword);
        select.append(option);
        select.find(option).prop('selected', true);
        $("#contractTagName option").each(function(){
              $(this).siblings("[value='"+ this.value+"']").remove();
        });
		$("#contractTagName").trigger("chosen:updated");
		saveContractTags("add");
    }
});

$('.chosen-select').chosen().on('change', function(evt, params) {
	if (params.selected && params.selected != undefined) {
		saveContractTags("add");
	}
	if (params.deselected && params.deselected != undefined) {
		saveContractTags("delete");
	}
	
});
$("#contractTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag")
</script>
<script>
function getContractTagsList(){
	var contractUUID ='${contractUUID}';
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({  
        type : "POST",
        url: vmsUrl+"/getContractTags?"+tokenParamName+"="+tokenValue+"&contractUUID="+contractUUID,        
        success :function(json) {
        	var result = "";
			$.each( json.tagsList, function( i, tags ) {
			  var existed = 0;
                if(json.contractTags != null && json.contractTags != 'undefined' && json.contractTags != '') {
	                var res = [];
	                res = json.contractTags.split(",");
	                res.forEach(function(item) {
	                	if(tags == item){
	                		existed = 1;
	                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
	                	}
	                });
                }
                if(existed == 0){
                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
                }
			});
            $("#contractTagName").empty();
            $("#contractTagName").append(result);
            $("#contractTagName").chosen().trigger("chosen:updated")

        },
	});
}
</script>
<script>
function saveContractTags(type){
	 var contractUUID ='${contractUUID}';
	 var tagName = $("#contractTagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = vmsUrl+"/saveOrUpdateTagsByContractUUID?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&contractUUID="+contractUUID+"&tagName="+jsonData
	 } else {
		 Urlcheck = vmsUrl+"/saveOrUpdateTagsByContractUUID?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&contractUUID="+contractUUID+"&tagName="+""
	 }
	
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
        	$("#editRoleLoading").removeAttr('style');
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		if(type == "add"){
        			showSuccessStatusPopup("Tags added successfully");
        		}else if(type == "delete"){
        			showDeleteStatusPopup("Tags deleted successfully");
        		}
        		
        		getContractTagsList(contractUUID);
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(vmsUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
       },
    }); 
}
function showSuccessStatusPopup(status) {
	$("#addContractTagAlert").css("display","block");
	$("#addContractTagAlert span").html(status);
    $('#addContractTagAlert').delay(2000).fadeOut('slow');
}
function showDeleteStatusPopup(status) {
	$("#deleteContractTagAlert").css("display","block");
	$("#deleteContractTagAlert span").html(status);
    $('#deleteContractTagAlert').delay(2000).fadeOut('slow');
}
</script>
<!--  ============================= Contract tags related scripts written by Laxman Ends ================================ -->







<script>
   function viewContractCommentRemoveSpaces() {
	    var output = document.getElementById("noteDescription").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#noteDescription").removeAttr("data-validation");
	    }
	}
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
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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
function addContractNotes(){
	var urlForm = "";
	var contractUUID = getParameterByName('contractUUID');
	    urlForm = "/getContractNotes?${_csrf.parameterName}=${_csrf.token}&contractUUID="+contractUUID;
		
    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
	var output = "";
    if(data != null && data != 'undefined' && data != "" && data != '[]'){
    var json = JSON.parse(data);
	 output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
	  output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
     for(var i=0;i<json.length;i++){
       	var report = json[i];
    	if(report.checkBox == 'Y' || (report.checkBox == 'N' && '${SourceLeadWeb_user.id}' == report.userIdStr)){
   	  	output = output+'<div class="comments-div comments-inner commentsNotes clearfix">';
   	    output = output+'    <div class="commentsimg">';
   	    output = output+'    <i class="fa fa-comments"></i>';
   	    output = output+'        <div class="commentsimg-inner">';
   	    if(report.commentAuthorImage != null && report.commentAuthorImage != '' && report.commentAuthorImage != 'undefined'){
   	    output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorImage+'"/>';
   	    }else{
   	   	output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="/resources/images/userprofileImg.jpg"/>';
   	    }
   	    output = output+'        </div>';
   	    output = output+'    </div>';
   	    output = output+'     <div class="comments-content comments-notes">';
   	    output = output+'         <div class="commentsNotes_inner">';
   	    output = output+'         <p class="comment-by">';
   		if(report.userName != null && report.userName != '' && report.userName != 'undefined')
   	    output = output+'            By <span class="text-danger" title = "'+report.userName+'">'+report.userName+'</span>';
   	    if(report.createdDateString != null && report.createdDateString != '' && report.createdDateString != 'undefined')
   	    output = output+'            <span class="small pull-right">'+report.createdDateString+'</span></p>';
   	    if(report.comment != null && report.comment != '' && report.comment != 'undefined')
   	    output = output+'         <p class="comment_text pull-left">'+report.comment+'</p>';
   	    output = output+'         </div>';
   	    output = output+'     </div>';
   	    output = output+'</div>';
    	}
     }
     output  = output + '              </div>';
     output  = output + '        </div>';
}else{
     var output ="";
    output = output+"   <div class='dataTables_empty'>"; 
	 output = output+"  </div> ";
}

	$("#viewNotesDiv").empty(); 
    $("#viewNotesDiv").append(output);
 },
         
      });
}
</script>
<script type="text/javascript">
	function storeContractNotes(){
		var contractUUID = getParameterByName('contractUUID');
		if ($('#notes_Contracts').validate(false, validationSettings)){
				//var frm = $('#notes_Contracts').serializeFormJSON();
				var form = $('#notes_Contracts')[0];
	      		var formData = new FormData(form);
	      		formData.append("contractUUID",contractUUID);
	      		var userId = '${SourceLeadWeb_user.id}';
	      		formData.append("userId",userId);
	      	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	      	    formData.append("locationCode",locationCode);
	      	    
	      	  var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
				$.ajax({
						type : "POST",
		        	    data: formData,
		    	        processData: false,
		    	        contentType:false,
						url : sourceleadUrl+"/storeNotes?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"locationCode="+locationCode,
						success : function(result) {
							
							if (result && result.response === "INVALID_DATA") {
							    showError(result.responseMessage);
							} else if (result.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								closeDocPopup();
								addContractNotes();
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(vmsUrl);
							} else {
							    showError("Operation failed, please try again");
							}
							
						},
					});
					
					return true;
		}else {
			return false;	
		}
		}
		$('body').on('blur', '#notes_Contracts', function() {
		$("#notes_Contracts").showHelpOnFocus();
		$("#notes_Contracts").validateOnBlur(false, validationSettings);
		
	});
</script>

<script>
function displyContract(stage){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
// 	var contractUUID = getParameterByName('contractUUID');
	var contractUUID ='${contractUUID}';
	 $.ajax({
      type : "GET",
       url: vmsUrl+"/getContractByContractUUID?contractUUID="+contractUUID,       
       success :function(data) {
    	   
    	    var result = '';
    	    var json = JSON.parse(data);
			result = result +	'<div class="contPopupHead clearfix">';
			if(json.autoGenId != null && '' != json.autoGenId && 'undefined' != json.autoGenId){
				result = result +	'<h4>'+json.autoGenId+' - '+json.contractTitle+'</h4>';
			}else{
				result = result +	'<h4>'+json.contractTitle+'</h4>';
			}
			
			result = result +	'<span>';
			
			if(json.templateSubmissionStatus == 'DRAFT' || json.templateSubmissionStatus == 'REJECTED' || json.templateSubmissionStatus == 'WAITING_FOR_APPROVER'){
				if('New' == json.typeOfContract){
					result = result +	'	<a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
				}else if('Existing' == json.typeOfContract){
					result = result +	'';
				}else if('Custom' == json.typeOfContract){
					result = result +	'	<a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
				}
			}
			
			result = result +	'</span>';
			result = result +	'</div>';
		
			result = result +	'<div class="modal-body clearfix">';
			result = result +	'<div class="col-xs-12 col-md-12 col-lg-12 noPad clearfix mt10">';
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Contract Title</div>';
			result = result +	'		<div class="col-md-8 col-sm-8" title="'+json.contractTitle+'">: '+json.contractTitle+'</div>';
			result = result +	'	</div>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Contract Type</div>';
			result = result +	'		<div class="col-md-8 col-sm-8" title="'+json.contractType+'">: '+json.contractType+'</div>';
			result = result +	'	</div>';
			
			if(json.templateName != null && 'Custom' != json.typeOfContract && 'Existing' != json.typeOfContract){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Template Title</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.templateName+' <a href="#" title="Preview"><i  title="View Template" class="fa fa-eye" onclick="previewTemplatePDF(\''+json.tempUUID+'\', \''+json.templateName+'\')"></i></a></div>';
				result = result +	'	</div>';
			}
			if(null != json.startDateStr && "" != json.startDateStr && 'undefined' != json.startDateStr){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Start Date</div>';
				result = result +	'		<div class="col-md-8 col-sm-8" title="'+json.startDateStr+'">: '+json.startDateStr+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.joinedDateStr && "" != json.joinedDateStr && 'undefined' != json.joinedDateStr){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Join Date</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.joinedDateStr+'</div>';
				result = result +	'	</div>';
			}
			if(null != json.workLocation && "" != json.workLocation && 'undefined' != json.workLocation){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Work Location</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.workLocation+'</div>';
				result = result +	'	</div>';
			}
			if(json.contractType!="Full time offer"){
					if(null != json.endDateStr && "" != json.endDateStr && 'undefined' != json.endDateStr){
						result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
						result = result +	'		<div class="col-md-4 col-sm-4">End Date </div>';
						if(json.templateSubmissionStatus!="SIGN" &&  json.templateSubmissionStatus!="VERIFY" && json.templateSubmissionStatus!="REJECTED"){
							result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: '+json.endDateStr+' <i class="fa fa-pencil popEdCo"  onclick="editOne(\''+json.startDateStr+'\',\''+json.endDateStr+'\');"></i>';
							result = result +	'<div class="editNewDat"><input type="text" class="form-control calendar" value="'+json.endDateStr+'" id="endDate" name="endDate" placeholder="End date" readonly="readonly"><i class="fa fa-floppy-o" onclick="editClo('+json.id+',\''+json.startDateStr+'\');"></i></div>';
						}
						else{
							result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: '+json.endDateStr+'</div>';	
						}
						result = result +	'	</div>';
						result = result +	'	</div>';
					}
					else{
						result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
						result = result +	'		<div class="col-md-4 col-sm-4">End Date </div>';
						if(json.templateSubmissionStatus!="SIGN" &&  json.templateSubmissionStatus!="VERIFY" && json.templateSubmissionStatus!="REJECTED"){
							result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: --  <i class="fa fa-pencil popEdCo"  onclick="editOne(\''+json.startDateStr+'\',\''+json.endDateStr+'\');"></i>';
							result = result +	'<div class="editNewDat"><input type="text" class="form-control calendar" value="" id="endDate" name="endDate" placeholder="End date" readonly="readonly"><i class="fa fa-floppy-o" onclick="editClo('+json.id+',\''+json.startDateStr+'\');"></i></div>';
						}
						else{
							result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: -- </div>';	
						}
						result = result +	'	</div>';
						result = result +	'	</div>';
					}
				
			}
			if(json.contractType =="Full time offer"){
				if(null != json.billingAmount && "" != json.billingAmount && 'undefined' != json.billingAmount){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Offer Rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.billingRateType+' '+json.billingAmount+' / '+json.billingRatePer+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.ownerName && "" != json.ownerName && 'undefined' != json.ownerName){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Signing Authority</div>';
				
				if(json.templateSubmissionStatus == "WAITING_TO_BE_SIGNED" && json.isParentContract == "N"){
					result = result +	'<div class="col-md-8 col-sm-8 popRela" id="contactUserDiv"><span id="contactUserSpan">: '+json.ownerName+'</span> <i class="fa fa-pencil popEdCo"  onclick="editContact();"></i>';
					result = result +	'<div class="editSignature"><div class="col-md-10 padding_left"><select class="chosen-select form-control sel" id="editSigner" name="clientContact"><option>Hi</option></select></div><div class="col-md-2 paddingLeft paddingRight"><i class="fa fa-floppy-o" onclick="saveContractSigner();"></i></div></div>';
				}
				else{
					result = result +	'<div class="col-md-8 col-sm-8 popRela" id="contactUserDiv">: '+json.ownerName+'</div>';	
				}
				result = result +	'	</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.approverUserName && "" != json.approverUserName && 'undefined' != json.approverUserName){
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Approver</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.approverUserName+'</div>';
			result = result +	'	</div>';
			}
			
			if(null != json.typeOfContract && (json.contractType.includes("SOW") || json.contractType =="Full time offer")){
				if(null != json.requirementName && "" != json.requirementName && 'undefined' != json.requirementName){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Job Title</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.requirementName+'</div>';
				result = result +	'	</div>';
			}
		}
			
			if(null != json.typeOfContract && (json.contractType.includes("SOW") || json.contractType =="Full time offer")){
				if(null != json.applicantName && "" != json.applicantName && 'undefined' != json.applicantName){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Applicant Name</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.applicantName+'</div>';
				result = result +	'	</div>';
			}
		}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.offerValue && "" != json.offerValue && 'undefined' != json.offerValue){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Offer Rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.offerValue+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.duration && "" != json.duration && 'undefined' != json.duration){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Duration</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: </div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.initiatorType && "Client" == json.initiatorType && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Client</div>';
				'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_VENDOR\',\'ROLE_VIEW_EDIT_VENDOR\')">';
				result = result +	'		<div class="col-md-8 col-sm-8">: <a href="'+vmsUrl+'/vendorsList?orgRelationUUID='+json.orgRelationUUID+'" title="'+json.contractWith+'">'+json.contractWith+'</a></div>';
				'</sec:authorize>';
				
				'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_VENDOR\',\'ROLE_VIEW_EDIT_VENDOR\')">';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractWith+'</div>';
				'</sec:authorize>';
				
				result = result +	'	</div>';
			}
			
			if(null != json.initiatorType && "Vendor" == json.initiatorType && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Vendor</div>';
				'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
				result = result +	'		<div class="col-md-8 col-sm-8">: <a href="'+vmsUrl+'/clientDashboard?orgRelationUUID='+json.orgRelationUUID+'" title="'+json.contractWith+'"> '+json.contractWith+'</a></div>';
				'</sec:authorize>';
				 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	  	            result = result + '<div class="col-md-8 col-sm-8">: '+json.contractWith+'</div>';
	  	         '</sec:authorize>';
				result = result +	'	</div>';
			}
			if(json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Contact</div>';
				result = result +	'		<div class="col-md-8 col-sm-8 popRela">: '+json.contactUserName+'</div>';	
				result = result +	'	</div>';
			}
			
			if(null != json.stateJurisdiction && "" != json.stateJurisdiction && 'undefined' != json.stateJurisdiction){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">State of Jurisdiction</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.stateJurisdiction+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.description && "" != json.description && 'undefined' != json.description && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Description</div>';
				result = result +	'		<div class="col-md-8 col-sm-8"><i style="display: inline-block; font-style:normal; float:left;">:</i><span style=" padding: 0px 0px 0px 3px; display: inline-block; width:90%; float:left;">'+json.description+'</span></div>';
				result = result +	'	</div>';
			}
			
			if(null != json.rejectedReason && "" != json.rejectedReason && 'undefined' != json.rejectedReason && json.templateSubmissionStatus =="REJECTED"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Rejected Reason</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.rejectedReason+'</div>';
				result = result +	'	</div>';
			}
			
			result = result +	'<div class="clearfix"/>';
			if(json.contractRequestDocumentsList.length>0){
			
				result = result +	'<h4>Requested Documents</h4>';
				
				result = result + '	<div class="clients_wrap_table">';
				result = result +  '<table id="contractListTableREJ2" class="display" cellspacing="0" width="100%" >';
				result = result + '	<thead>';
				result = result + '   <tr>';
				result = result + '    <th>Doc Name</th>';
				result = result + '    <th>Doc Type</th>';
				result = result + '    <th>Description</th>';
				result = result + '    <th>Action</th>';
				result = result + '   </tr>';
				result = result + '	</thead>';
				result = result + '	<tbody>';
				
				  for (var i = 0; i < json.contractRequestDocumentsList.length; i++) {
		    	      var opt=json.contractRequestDocumentsList[i];
						result = result + '   <tr>';
						result = result + '    <td>'+opt.fileName+'</td>';
						if(opt.docType != null && opt.docType != 'undefined' && opt.docType != ""){
							result = result + '    <td>'+opt.docType+'</td>';
						}
						else{
							result = result + '    <td>--</td>';
						}
						if(opt.description != null && opt.description != 'undefined' && opt.description != ""){
							result = result + '    <td>'+opt.description+'</td>';
						}
						else{
							result = result + '    <td>--</td>';
						}
						 if(opt.fileURL != null && opt.fileURL !=""){
							result = result+'<td><div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt" href=\"'+opt.fileURL+'\"title="Download"></a>';
		           			result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+opt.fileURL+'\', \''+opt.docFileName+'\', \''+opt.fileType+'\');"title="preview"></a>';
						 }else{
							 result = result + '    <td>--</td>';
						 }
		           			result = result + '   </tr>';
				  }
				result = result + '	</tbody>';
				result = result + '</table>';
				result = result + '	</div>';
				result = result +	'<div class="clearfix"/>';
			}
					
			
		if(json.contractType != "Vendor NDA" && json.contractType != "Client MSA" && json.contractType != "Vendor MSA" && json.contractType != "Vendor NSA" && json.contractType!="Full time offer"){
			result = result +	'<h4>Billing Details</h4>';
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.hoursPerWeek && "" != json.hoursPerWeek && 'undefined' != json.hoursPerWeek){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Hours Per Week</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.hoursPerWeek+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.overTimeHours && "" != json.overTimeHours && 'undefined' != json.overTimeHours){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Overtime Hours</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.overTimeHours+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.overtimeRate && "" != json.overtimeRate && 'undefined' != json.overtimeRate){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Overtime rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.overtimeRate+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.totalHours && "" != json.totalHours && 'undefined' != json.totalHours){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Total Estimated Hours</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.totalHours+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.billingAmount && "" != json.billingAmount && 'undefined' != json.billingAmount){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Billing Amount</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.billingRateType+' '+json.billingAmount+' / '+json.billingRatePer+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.invoiceFrequency && "" != json.invoiceFrequency && 'undefined' != json.invoiceFrequency){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Billing Cycle</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.invoiceFrequency+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.paymentTerm && "" != json.paymentTerm && 'undefined' != json.paymentTerm){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Payment Frequency</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.paymentTerm+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.tax && "" != json.tax && 'undefined' != json.tax){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Tax</div>';
				var abc = json.taxType;
				if(abc == "Percentage"){
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.tax+' %</div>';
				}else{
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.tax+' '+json.taxType+'</div>';
				}
				
				result = result +	'	</div>';
			}
			
			result = result +	'<div class="clearfix"/>';
		}
			result = result +	'<h4>History</h4>';
			
			if(null != json.createdDateStr && 'undefined' != json.createdDateStr && json.createdDateStr != ''){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Created On</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.createdDateStr+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.createdByName && 'undefined' != json.createdByName && json.createdByName != ''){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Created By</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.createdByName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.updatedDateStr && 'undefined' != json.updatedDateStr && json.updatedDateStr != json.createdDateStr){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Updated On</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.updatedDateStr+'</div>';
				result = result +	'	</div>';
				
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Updated By</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.updatedByName+'</div>';
				result = result +	'	</div>';
			}
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Added From</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.typeOfContract+'</div>';
			result = result +	'	</div>';
			
			result = result +	'</div>';
			result = result +	'</div>';
			
			if(json.newContractCustomFieldlabelNameAndValue != null && json.newContractCustomFieldlabelNameAndValue != "" && json.newContractCustomFieldlabelNameAndValue != 'undefined'){
				 $.each(json.newContractCustomFieldlabelNameAndValue, function(key, value) {
					  var labelNameAndlabelData = value.toString().split("$");
	            	  var labelName = labelNameAndlabelData[0].toString().split(",");
					  result = result + '	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
	            	  result = result + '	<div class="col-md-4 col-sm-4">'+labelName[1]+'</div>';
	            	  result = result + '   <div class="col-md-8 col-sm-8">:'+labelNameAndlabelData[1]+'</div>';
	            	  result = result + '	</div>'; 
	              }); 
            }
			
			$("#viewPopupDiv").empty();
			$("#viewPopupDiv").append(result);
			
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
			});
       },
	  }); 
}


function previewTemplatePDF(uuid, templateName){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(uuid != null && uuid != ""){
		window.open(vmsUrl+'/viewContractTemplatePdf/'+uuid+'/'+templateName+'','_newtab');
	}
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
</script>
<script type="text/javascript">
function previewContractDoc(docId,fileName){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(docId != null && docId != ""){
		window.open(vmsUrl+'/viewReqContractDocPdf/'+docId+'/'+fileName+'',fileName,"height=600,width=600");
	}  
}
</script>
<script>
function editOne(startDate,endDate){
	 var contractEffectiveEndDate = startDate.split("-");
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatEditOneSave = '';
	  var d2 = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatEditOneSave = 'yy-mm-dd';
		   d2 = new Date(contractEffectiveEndDate[0], parseInt(contractEffectiveEndDate[1])-parseInt(1), parseInt(contractEffectiveEndDate[2])+parseInt(1));
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatEditOneSave = 'dd-mm-yy'; 
		   d2 = new Date(contractEffectiveEndDate[2], parseInt(contractEffectiveEndDate[1])-parseInt(1), parseInt(contractEffectiveEndDate[0])+parseInt(1));
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatEditOneSave = 'mm-dd-yy'; 
		   d2 = new Date(contractEffectiveEndDate[2], parseInt(contractEffectiveEndDate[0])-parseInt(1), parseInt(contractEffectiveEndDate[1])+parseInt(1));
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatEditOneSave = 'yy-dd-mm';
		   d2 = new Date(contractEffectiveEndDate[0], parseInt(contractEffectiveEndDate[2])-parseInt(1), parseInt(contractEffectiveEndDate[1])+parseInt(1));
	  }
	$('.editNewDat').show();
	$('#endDate').datepicker({
        dateFormat: finalDateFormatEditOneSave,
        changeMonth: true,
          changeYear: true,
          minDate: d2,
          onSelect: function (selected) {
//               var dt = toDate("#endDate");
//               dt.setDate(dt.getDate() - 1);

        	    var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
	            $("#startDate").datepicker("option", "maxDate", dt);
          }
    });
}


function editClo(contractId,startDate){
	$('.editNewDat').hide();
	var endDate = $("#endDate").val();
	if(endDate != ""){
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		 $.ajax({
	    type : "POST",
	     url: vmsUrl+"/updateContractEndDate?${_csrf.parameterName}=${_csrf.token}&contractId="+contractId+"&endDate="+endDate,       
	     success :function(data) {
	    	 $("#endVal").empty();
			 $("#endVal").append(": "+endDate+' <i class="fa fa-pencil popEdCo"  onclick="editOne(\''+startDate+'\',\''+endDate+'\');"></i><div class="editNewDat"><input type="text" class="form-control calendar" value="'+endDate+'" id="endDate" name="endDate" placeholder="End date" readonly="readonly"><i class="fa fa-floppy-o" onclick="editClo('+contractId+',\''+startDate+'\');"></i></div>');
	    },
	}); 
	}
}

function editContact(){
	$(".editSignature").show();
	var result = "";
	<c:forEach var="user" items="${userList}">
		var name = "";
		if("${user.lastName}" != null && "${user.lastName}" != '' && "${user.lastName}" != 'undefined'){
			name += "${user.firstName}" +" "+ "${user.lastName}";
		}else{
			name += "${user.firstName}";
		}
		result += '<option value="${user.id}">'+name+'</option>';
	</c:forEach>
	$("#editSigner").empty();
	$("#editSigner").append(result);
	$('#editSigner').trigger("chosen:updated");
}

function saveContractSigner(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var contractUUID ='${contractUUID}';
	var clientContact = $("#editSigner").val();
	 $.ajax({
		    type : "POST",
		     url: vmsUrl+"/updateContractSigner?contractUUID="+contractUUID+"&clientContact="+clientContact,       
		     success :function(data) {
		    	 if (data.response === "SUCCESS") {
		    		 var opt = data.object;
		    		 $("#contactUserSpan").html(" : "+opt.ownerName);
		    		 $(".editSignature").hide();
		    	 }else if (response.indexOf('Sign In') !== -1) {
		 			location.replace(vmsUrl);
		 	    } else if (response.response === "AWKWARD") {
		 		    location.href = "/errorPage";
		 	    }
		    },
		}); 
}
</script>

<script>
$(".addUser-org").click(function(){
$(".edit-details").toggleClass("edit-details-active");
});
$(".close-div").click(function(){ 
	 $(this).parent("div.edit-details").removeClass("edit-details-active");
});
$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details, .ui-datepicker-prev, .ui-datepicker-next").click(function(e){
	 e.stopPropagation();
});
function closeDocPopup(){
$("#popUpDiv").removeClass("edit-details-active");
$("#notes_Contracts")[0].reset();
}
</script>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->