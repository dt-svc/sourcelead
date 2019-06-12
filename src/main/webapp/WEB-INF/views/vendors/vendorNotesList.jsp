<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="deleteDocpopUp"></div>

<script>
$(".addUser-org").css({"overflow-x": "auto"});
function vendorNotesList(orgRelationId,vendorLocationId){
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> '; 
	result  = result + '<div class="success_pop" id="vendorDocSelector" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="vendorDocMsgDiv"></span>';
    result  = result + '</div>';
	    result = result + '<ul class="padding_right">';
	    
	    result = result  +'    	<a href="javascript:void(0)" onClick="addNotes('+orgRelationId+','+vendorLocationId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Note</a>                                                            ';
		 
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4 editDetails"> ';
		result = result  +'     </div>  ';
		
		result = result + '</ul>';
		result = result  +'  <div class="clearfix"></div> ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table vendor_wrap_inn_table_last_child">';
		result  = result + '   <div id="vendorNotesListDiv" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';
	
    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-6").empty();  
    $("#tab-7").empty();  
    $("#tab-8").empty(); 
    $("#tab-8").append(result);  
    vendorNotes(orgRelationId);
    
}
</script>

 <script>
function vendorNotes(orgRelationId){
	var urlForm = "";
	
    	urlForm = "/getNotes?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId;
	
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
	var output = "";
    if(data != null && data != 'undefined' && data != "" && data != '[]'){
    var json = JSON.parse(data);
//     '${SourceLeadWeb_user.id}' 

	 output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
	  output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table vendor_wrap_inn_table_last_child">';
     for(var i=0;i<json.length;i++){
       	var report = json[i];
       	if(report.checkBox == 'Y' || (report.checkBox == 'N' && '${SourceLeadWeb_user.id}' == report.userIdStr)){
   	  	output = output+'<div class="comments-div comments-inner commentsNotes clearfix">';
   	    output = output+'    <div class="commentsimg">';
   	    output = output+'    <i class="fa fa-comments"></i>';
   	    output = output+'        <div class="commentsimg-inner">';
   	    if(report.commentAuthorImage != null && report.commentAuthorImage !='' && report.commentAuthorImage != 'undefined'){
   	    output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorImage+'"/>';
   	    }else{
   	   	output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/userprofileImg.jpg"/>';
   	    }
   	    output = output+'        </div>';
   	    output = output+'    </div>';
   	    output = output+'     <div class="comments-content">';
   	    output = output+'         <div class="commentsNotes_inner">';
   	    output = output+'         <p class="comment-by">';
   	    output = output+'            By <span class="text-danger" title = "'+report.userName+'">'+report.userName+'</span>';
   	 if(report.requirementTitle !=null && report.requirementTitle != 'undefined'){
   		 output = output+'          Job: <span class="text-danger" title = "'+report.requirementTitle+'"> '+report.requirementTitle+'</span>';
   	    }
   	    
   	    else if(report.contactName !=null && report.contactName != 'undefined'){
   		 output = output+'          Contact Name: <span class="text-danger" title = "'+report.contactName+'">'+report.contactName+'</span>';
   	    }
   	 else if(report.contract !=null && report.contract != 'undefined'){
   		 output = output+'         Contract:  <span class="text-danger" title = "'+report.contract+'">'+report.contract+'</span>';
   	    }
   	    
   	 else if(report.invoiceId !=null && report.invoiceId != 'undefined'){
   		 output = output+'         Invoice Id:  <span class="text-danger" title = "'+report.invoiceId+'">'+report.invoiceId+'</span>';
   	    }
   	    output = output+'            <span class="small pull-right">'+report.createdDateString+'</span></p>';
   	    output = output+'         <div class="clearfix"></div>';
   	    output = output+'         <p class="comment_text pull-left">'+report.comment+'</p>';
   	    output = output+'         </div>';
   	    output = output+'     </div>';
   	    output = output+'</div>';
    	}
     }
}else{
     var output ="";
    output = output+"   <div class='dataTables_empty'>"; 
	 output = output+"  </div> ";
}
output  = output + '              </div>';
output  = output + '        </div>';
	$("#vendorNotesListDiv").empty(); 
    $("#vendorNotesListDiv").append(output);
 },
         
      });
}
</script>

<script>
function documentpagination(){  
    $('#clientDocumentsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
<script>
function getRejectedCategory(){
	var rejectedCategory = document.getElementById("categoryType").value;
	if(rejectedCategory == 'Other'){
		 document.getElementById("rejectOtherCategoryDiv").style.display="block";
	    $("#rejectedCategoryOther").removeAttr("data-validation-optional");
		$('#rejectedCategoryOther').attr("data-validation","required validate_Space validate_length length2-100 "); 
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}else{
		$("#rejectedCategoryOther").attr("data-validation-optional","true");
		$("#rejectedCategoryOther").removeAttr("data-validation");
		$("#rejectedCategoryOther").val('');
		document.getElementById("rejectOtherCategoryDiv").style.display="none";
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}
	
}
</script>




<script>
function addNotes(orgRelationId,vendorLocationId) {	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="vendorNotes_form" name="vendorNotes_form" method="post" > ';
	result = result  +'          <input type="hidden" id="noteType" name="noteType" value="Vendor"> ';
	result = result  +'          <input type="hidden" id="orgRelationId" name="orgRelationId" value="'+orgRelationId+'"> ';
	result = result  +'          <input type="hidden" id="vendorLocationId" name="vendorLocationId" value="'+vendorLocationId+'"> ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result+'	                  <div class="clearfix">';
	
	
	 result = result +" <div class='col-md-12 inputOther mt10 inputOther'>";
     result = result +" <div class='dropdown-select mb10' id='clientcategoryTypeListDiv'>";
     result = result +"<select id='categoryType' name='categoryType' class='chosen-select form-control' onchange=' selectClientCategory(this);' field-name='Category'>";
     result = result +"<option selected value=''>--Select Category--</option>";
     result = result +"<option value='Jobs'>Jobs</option>";
     result = result +"<option value='Contacts'>Contacts</option>";
     result = result +"<option value='Contracts'>Contracts</option>";
     result = result +"<option value='Billing'>Billing</option>";
     result = result +"<option value='Other'>Other</option>";
     result = result +"</select>";
     result = result +"    </div>";
	
	
     result = result +"    <div class='mt10'>";
     result = result +"    <div class='dropdown-select mb10' id='clientCategoryContactTypeListDivDiv'>";
     result = result +"    </div>";
 	result = result  +'                <div class="row clearfix"> ';
	result = result+'	                  <div class="col-md-12 clearfix">';
	result = result+'	                      <textarea rows="2" placeholder="Note" value="" id="noteDescription" name="noteDescription" data-validation="required validate_length length1-5000"  field-name="Note" class="form-control"></textarea>';
	result = result+'	                  </div>';
	result = result  +'                  </div>';
	result = result+' <div class="row clearfix"> ';
	result = result+' <div class="col-md-9 checkBox"> ';
	result = result+'   <input type="checkbox" class="pull-left" value= "Y" id="allowAll" name = "allowAll" checked="checked">';
	result = result + '	                    <p class="pull-left mt5">Allow all users</p>';
	result = result+'	                  </div>';
	
	result = result  +'                  <div class="col-md-3" id="docAddButton"> ';
	result = result  +'                  <div class="pull-right mt5"> ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary" onClick ="storeVendorNotes('+orgRelationId+');" />'; 
  /*   result = result +'                     <input type="button" value="Cancel" name="btnAddPr" id="save_note" class="btn btn-sm btn-default" onClick ="storeClientNotes('+orgRelationId+');" />'; */ 
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	
	result = result  +'                </div>';
	
	result = result  +'                </form>';
	
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	function educationStartDate(){
	}
	
	$('#startDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	           // var dt = new Date(selected);
	           var dt = toDate("#startDate");
	           dt.setDate(dt.getDate() + 1);
	           $("#endDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	           // var dt = new Date(selected);
	            var dt = toDate("#endDate");
	            dt.setDate(dt.getDate() - 1);
	            $("#startDate").datepicker("option", "maxDate", dt);
	        }
	  });
	
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details, .ui-datepicker-prev, .ui-datepicker-next").click(function(e){
		 e.stopPropagation();
	 });
	 /* $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   }); */	
	 
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
				})
	}
</script>
<script>
   function vendorNotesCommentRemoveSpaces() {
	    var output = document.getElementById("noteDescription").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#noteDescription").removeAttr("data-validation");
	    }
	}
</script>
<script>
function closeDocPopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
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
	function storeVendorNotes(orgRelationId){
		if ($('#vendorNotes_form').validate(false, validationSettings)){ 
				
				var form = $('#vendorNotes_form')[0];
	      		var formData = new FormData(form); 
	      		var userId = '${SourceLeadWeb_user.id}';
	      		formData.append("userId",userId);
	      	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	      	    formData.append("locationCode",locationCode);
	      	    
				var sourceleadUrl = '${sourceleadUrl}';
				$("#save_note").attr("disabled", "disabled");
         		$("#save_note" ).addClass('button').val('Processing..');
				$.ajax({
						type : "POST",
		        	    data: formData,
		    	        processData: false,
		    	        contentType:false,
						url : sourceleadUrl+"/storeNotes?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&locationCode="+locationCode,
						success : function(result) {
							
							if (result && result.response === "INVALID_DATA") {
							    showError(result.responseMessage);
							} else if (result.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								closeDocPopup();
								vendorNotes(orgRelationId);
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
							
						},
					});
					$('.add-comment').toggle(200);
					return true;
			 
		}else {
			return false;	
		}
		}
		$('body').on('blur', '#vendorNotes_form', function() {
		$("#vendorNotes_form").showHelpOnFocus();
		$("#vendorNotes_form").validateOnBlur(false, validationSettings);
		
	});
</script>



<script>
function selectClientCategory(){
	$('#clientCategoryContactTypeListDivDiv').val('').trigger("chosen:updated");
	var sourceleadUrl ='${sourceleadUrl}';
	var orgRelationId = $('#orgRelationId').val(); 
	var clientLocationId = $('#clientLocationId').val(); 
	var categoryType = $('#categoryType').val(); 
	var vendorLocationId = $('#vendorLocationId').val();
	if(categoryType != ''){
		if(categoryType == "Jobs"){
			var status = "";
			$.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/getVendorJobs?"+tokenParamName+"="+tokenValue+"&orgRelationID="+orgRelationId+"&status="+status,  
		        success: function(json) {
		        	 var result ="";
		        	 result = result +"<select id='vendorcontact' name='vendorcontact' class='chosen-select form-control'  data-validation='required' field-name='Category'>";
		        	 for(var i=0;i<json.data.length;i++){
		        		 var opt = json.data[i];
		        		 result = result +"<option value="+opt.id+">"+opt.jobTitle+"</option>";
		             }
		        	 result = result +"</select>";
		        	 $("#clientCategoryContactTypeListDivDiv").empty();
				     $("#clientCategoryContactTypeListDivDiv").append(result);
				     $('#clientCategoryContactTypeListDivDiv').trigger("chosen:updated");
				     $('#vendorcontact').trigger("chosen:updated");
				     $('#vendorcontact').chosen();
		        }
			});
		}
		
		if(categoryType == "Contacts"){
			$.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&type=Client", 
		        success: function(json) {
		        	// var json = JSON.parse(data);
		        	 var result ="";
		        	 result = result +"<select id='vendorcontact' name='vendorcontact' class='chosen-select form-control'  data-validation='required' field-name='Category'>";
		        	 for(var i=0;i<json.data.length;i++){
		        		 var opt = json.data[i];
		        		 result = result +"<option value="+opt.id+">"+opt.spocName+"</option>";
		             }
		        	 result = result +"</select>";
		        	 $("#clientCategoryContactTypeListDivDiv").empty();
				     $("#clientCategoryContactTypeListDivDiv").append(result);
				     $('#clientCategoryContactTypeListDivDiv').trigger("chosen:updated");
				     $('#vendorcontact').trigger("chosen:updated");
				     $('#vendorcontact').chosen();
		        }
			});
		}
	
		
		if(categoryType == "Billing"){
			var dayWisetype = "month";
			var endDate = "";
			var startDate = "";
			var invoice = "";
			var payment ="";
				$.ajax({
			    	type: "POST",
			    	url : sourceleadUrl+"/getInvoiceListBasedOnOrgrelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&clientLocationId="+vendorLocationId+"&dayWisetype="+dayWisetype+"&startDate="+startDate+"&endDate="+endDate+"&invoiceStatus="+invoice+"&paymentStatus="+payment+"&invoiceType=payables", 
			        success: function(json) {
// 		        	  var json = JSON.parse(data);
		        	  var result ="";
		        	  result = result +"<select id='vendorcontact' name='vendorcontact' class='chosen-select form-control'  data-validation='required' field-name='Category'>";
              		    for (var i = 0; i < json.data.length; i++) {
              		    	var opt= json.data[i];
              		    	 result = result +"<option value="+opt.id+">"+opt.invoiceId+"</option>";
              		    }
              		  result = result +"</select>";
 		        	 $("#clientCategoryContactTypeListDivDiv").empty();
 				     $("#clientCategoryContactTypeListDivDiv").append(result);
 				     $('#clientCategoryContactTypeListDivDiv').trigger("chosen:updated");
 				    $('#vendorcontact').trigger("chosen:updated");
				     $('#vendorcontact').chosen();
			 }
		});
	 }
	
		if(categoryType == "Contracts"){
			var contractStatus = "";
			 $.ajax({  
		          type : "POST",
		          url: sourceleadUrl+"/getClientOrVendorContracts?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&responderType=Vendor&contractStatus="+contractStatus,        
		          success :function(data) {
		        	  var json = JSON.parse(data);
		        	  var result ="";
		        	  result = result +"<select id='vendorcontact' name='vendorcontact' class='chosen-select form-control'  data-validation='required' field-name='Category'>";
              		    for (var i = 0; i < json.length; i++) {
              		    	var opt= json[i];
              		    	 result = result +"<option value="+opt.id+">"+opt.contractTitle+"</option>";
              		    }
              		  result = result +"</select>";
 		        	 $("#clientCategoryContactTypeListDivDiv").empty();
 				     $("#clientCategoryContactTypeListDivDiv").append(result);
 				     $('#clientCategoryContactTypeListDivDiv').trigger("chosen:updated");
 				     $('#vendorcontact').trigger("chosen:updated");
				     $('#vendorcontact').chosen();
			 }
		});
	 }
		
		else{
			$("#clientCategoryContactTypeListDivDiv").empty();
			$('#clientCategoryContactTypeListDivDiv').trigger("chosen:updated");
		}	
		
}else{
	$("#clientCategoryContactTypeListDivDiv").empty();
	$('#clientCategoryContactTypeListDivDiv').trigger("chosen:updated");
}
	
}
</script>
