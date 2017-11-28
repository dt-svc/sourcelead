<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="resources/css/bootstrap-multiselect.css?staticTime=${staticTime}" type="text/css">
<link rel="stylesheet" href="resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}" type="text/css">


<style>
.modal-backdrop{
display: none;
}

.nicEdit-main {
	overflow: auto !important;
}

.mt40{
 margin-top: 50px !important;
}
</style>


<script>
 $(document).ready(function(){
	 var UUID = '${UUID}';
	 if(null != UUID && UUID != '' && UUID != 'undefined'){
		 
		 var mailType = '${mailType}';
		 if(null != mailType && mailType != '' && mailType != 'undefined'){
			 loadHotMailBoxDetails(UUID);
		 }
	 }
	 getSenders(); 
});
</script> 

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Joblist Mail Box</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='/campaigns/hotcampaigns'/>"> Mailbox</a>
			&raquo; Joblist Campaign</div>
    </section>
   
    <div class="content-area clearfix">
    <div id="mailLoading"></div>
    <div id ="popUp"></div>
    <div id ="textBoxPopUp"></div>
      <div class="form-horizontal compose-mail col-md-10 col-md-offset-1">
       <form:form id="joblistCampaign_form" name="joblistCampaign_form"  >
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-muted small">From</label>
           <div class="col-md-10">
            <%-- <input type="text" value="${createSender.fromMail}" class="form-control input-sm" readonly> --%>
            
                        <select class="chosen-select form-control" id="senderIdentity" name="senderIdentity" data-validation="required" field-name="Sender">             
				                </select>            
            
          </div> 
         <!--   <div class="form-group">
				              <label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
				              <div class="col-md-9 select-field" id="errClient">
				                <select class="chosen-select form-control" id="senderIdentity" name="senderIdentity" data-validation="required" field-name="Sender Name">             
				                </select>
				              </div>
				              <input type="hidden" name="savedInvoiceId" id="savedInvoiceId">
				                <input type="hidden" name="clientCurrency" id="clientCurrency">
		 </div> -->
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-primary small">To</label>
          <div class="col-md-10 positionRelative disable-choosen-dropdown">

            <select class="form-control"  field-name="To List" multiple data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email" id="emailsList" data-validation="validate_email" data-validation-optional="true" name="Toemails">

            </select>
            <span class="select-multiple" data-target="#multiplemail" onclick="getRecipientContactsList();" data-toggle="modal"><i class="fa fa-list-alt" title="Multiple Mail"></i></span> 
            <!-- Modal content Multiple mail -->
            <div role="dialog" id="multiplemail" class="modal fade">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">Select Vendors</h4>
                    <!-- <label class="paddingLeft paddingRight">
               	    <input  type="radio" id="vendorCheck" name="users" checked onclick="vendorListChange()"><strong class="paddingLeft">Vendors</strong></label>
               	    <label class="paddingLeft">
               	    <input type="radio" id="contactsCheck" name="users" onclick="getContactsList();"><strong class="paddingLeft">My Contacts</strong></label> -->
                  </div>
                  
                   <div class="modal-body clearfix">
                    <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100 select-field paddingLeft mb10" id="vendorsListDiv" style="display: none">
                          <select class="form-control" id="vendorListType" onchange="vendorListChange();">
                           <option value="listbyVendor" selected>List By Vendor</option>
                           <option value="listbyContact">List By Contact</option>
                          </select>
                    </div>
                    <div class="clearfix"></div>
                    <div class="clearfix">
                     <div id="mailBoxVendorsList" class="datatable_filter"></div>
                    </div>
                    
                  </div> 
                  
                </div>
              </div>
            </div>
            <!-- AMultiple mail modal popup ends --> 
          </div>
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-warning small">Others</label>
          <div class="col-md-10 email-icon positionRelative disable-choosen-dropdown">
                <select multiple data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email" data-validation="validate_email" data-validation-optional="true" class="chosen-select form-control" name="otherEmails" id="otherEmails" >
                </select>
          </div>
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-danger small">Subject</label>
          <div class="col-md-10">
            <input type="text" name="subject" data-validation="required" field-name="Subject" id="subject" data-placeholder="&nbsp;Enter Subject" class="form-control input-sm">
          </div>
        </div>
        
        <!-- Job list papulation -->
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-success small">Jobs</label>
          <div class="col-md-10 positionRelative disable-choosen-dropdown">
            <select class="chosen-select" name="campaignjobs" data-validation="required" field-name="Jobs" multiple data-placeholder="&nbsp;Enter Jobs" id="selectedJobs">
            </select>
            
            <span class="select-multiple" data-target="#requirement" onclick="selectJobsRadio();" data-toggle="modal"><i class="fa fa-th-large" title="Select Candidates"></i></span> 
            <!-- Modal content Multiple mail -->
            <div role="dialog" id="requirement" class="modal fade custom-modal">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">Select Jobs</h4>
                  </div>
                  
                  <div class="modal-body clearfix">
                    <div class="clearfix">
                    <label class="paddingLeft paddingRight">
               	    <input  type="radio" id="myJobsRadio" name="users" value="myjobs" checked onclick="jobListChange('myjobs')"><strong class="paddingLeft">My Jobs</strong></label>
               	    <label class="paddingLeft">
               	    <input type="radio" id="allJobsRadio" name="users" value="openJobs" onclick="jobListChange('openJobs')"><strong class="paddingLeft">All Jobs</strong></label>
                    
					<!-- <span class="dropdown ml">
					  <span class="dropdown-toggle cursorPointer" type="button" data-toggle="dropdown"><strong>Status</strong>
					  <span class="caret"></span></span>
					  <ul class="dropdown-menu paddingLeft paddingRight">
					    <li class="checkBox"><input name="jobStatus" type="checkbox"><label>Priority</label></li>
					    <li class="checkBox"><input name="jobStatus" type="checkbox"><label>By Job type</label></li>
					    <li class="checkBox"><input name="jobStatus" type="checkbox"><label>By Client</label></li>
					  </ul>
					</span> -->
                    
                    </div>
                  <!-- <div id="mailBoxJobsList" class="mailFil datatable_filter"></div> -->
                    <div class="clearfix">
                     <div id="mailBoxJobsList" class="datatable_filter"></div>
                    </div>
                    
                  </div> 
                </div>
              </div>
            </div>
            <!-- AMultiple mail modal popup ends --> 
          </div>
        </div>
        
        <div class="clearfix">
          <div class="col-md-6">
            <label class="col-md-4 control-label text-info small">Salutation</label>
            <div class="col-md-8 saluation field-border">
              <select id="saluation" name="saluation">
                <option value="Hi" selected>Hi</option>
                <option value="Hello">Hello</option>
                <option value="Dear">Dear</option>
              </select>
            </div>
          </div>
          
        </div>
        
        <!-- Text Area -->
        <div class="col-md-12 clearfix mb10 mt10">
          <div class="col-md-12">
            <div class="clearfix txt-area">
              <textarea id="bodytext" name="bodytext" class="form-control" rows="5" placeholder="Message" style="height: 250px">
                    
			  </textarea>
            </div>
          </div>
        </div>
        <!-- Text Area Ends --> 
        
        <!-- Attachment button-->
        <div class="col-md-12 clearfix mb10">
          <div class="col-md-12 attachment">
            <div class="input-group">
              <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-paperclip"></i>
                <input type="file" name="attachfile" style="display: none;"  id="attachfileBtn" class="upload">
                </b> </label>
              <input type="text" class="form-control" readonly id="attachfile-mail" placeholder="Files are not selected...">
            </div>
          </div>
        </div>
        <!-- Attachment button --> 
        
        <!-- Attachment button-->
        <div class="col-md-12 clearfix mt">
            <div class="pull-right"> 
              <!--<input type="button" value="Schedule" class="btn btn-sm btn-primary">-->
              <button type="button" class="btn btn-sm btn-success" onclick="sendJobListCampaign();"><i class="fa fa-envelope-o"></i>&nbsp;Send</button>
              <button type="button" class="btn btn-sm btn-default" onClick="javascript:window.location.href = '<c:url value='/campaigns/hotcampaigns'/>'" ><i class="fa fa-times"></i>&nbsp;Cancel</button>
            </div>
        </div>
        <!-- Attachment button --> 
         </form:form>
      </div>
    </div>
    
  </section>
  
  <script src="resources/js/nicEdit.js"></script> 
  
   <script type="text/javascript">
	function sendJobListCampaign() {
		
		if ($('#joblistCampaign_form').validate(false, validationSettings)){ 
		nicEditors.findEditor('bodytext').saveContent();
  		
		 var form = $('#joblistCampaign_form')[0];
  		 var formData = new FormData(form)
  		 var toEmails =  $('#emailsList').val();
  		 var otherEmails =  $('#otherEmails').val();
  		
  	    if(toEmails == null && otherEmails == null){
  	    	alertMailerMsg();
  	    }else{
	  	    	
	  	    	$("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  			$.ajax({
	  				type : "POST",
	  	    	    data: formData,
	  	    	    enctype: 'multipart/form-data',
	  		        processData: false,
	  		        contentType:false, 
	  				url : sourceleadUrl+"/sendJobListCampaign?${_csrf.parameterName}=${_csrf.token}",
	  				success : function(result) {
	  						location.replace(sourceleadUrl+"/campaigns/hotcampaigns");
	  				},
	  			}); 
	  	    
  	    }
		 return true
		 
		}else{
			return false;
		  }
	}
	
	$('body').on('blur', '#joblistCampaign_form', function() {
		$("#joblistCampaign_form").showHelpOnFocus();
		$("#joblistCampaign_form").validateOnBlur(false, validationSettings);  
   });
</script>
  
  <script>
  function selectRadio(){
	  document.getElementById("vendorCheck").checked = true;
	  document.getElementById("contactsCheck").checked = false;
	 $("#vendorListType").val('listbyVendor');
  }
  
  
  function selectJobsRadio(){
	  document.getElementById("myJobsRadio").checked = true;
	  document.getElementById("allJobsRadio").checked = false;
	  jobListChange('myjobs');
  }
  </script>
   

<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	       
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});

$(function() {
	$("#attachfileBtn").change(function() {
		fileUpload("attachfileBtn", "Joblist Campaign Documents", "joblistCampaign_form");
	});
 });
 
</script>

<script>
function jobListChange(type){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='jobsDataTableSearch'/>";
	 	result = result +"</div>";
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
  		result = result +'<table id="jobsDataTable" class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'             <th><input type="checkbox" id="checkAllJobs" onclick="toggleJobs(this);"></th>';
 		result = result +'             <th>Job ID</th>';
 		result = result +'             <th>Job Title</th>';
 		result = result +'             <th>Job Type</th>';
 		result = result +'             <th>Location</th>';
 		result = result +'             <th>Rate</th>';
 		result = result +'             <th>Posted On</th>';
 		result = result +'             <th>Priority</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="assignbtnId" onclick="getCheckedJobs();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    $("#mailBoxJobsList").empty();
	    $("#mailBoxJobsList").append(result);
	    allJobs = {};
	    jobsDataTableList(type);
}
</script>

<script>
function jobsDataTableList(type){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#jobsDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getJobsForCampaign?searchValue="+searchValue+"&jobCampaignType="+type;
	 jobsPagination(url,type);
}
</script>

<script>
function jobsPagination(url,type){
	var colIndex = 0;
    $('#jobsDataTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "ischeck" },
            { "data": "jobId" },
            { "data": "jobTitle"},
            { "data": "jobType"},
            { "data": "rate"},
            { "data": "location"},
            { "data": "posted on"},
            { "data": "priority"},
           
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		 var flag = false;
    	  		
    	  	  for(var e in allJobs) {
    	  			 if(opt.requirementId+"" === allJobs[e].split(",")[0]) {
    	  				 flag = true;
    	  				 break;
    	  			 }
    	  		 } 
    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+opt.requirementId+'" data-id="'+opt.requirementId+'" value="'+opt.requirementId+','+opt.jobTitle+'" onchange="checkJob(this)"  type="checkbox" name="jobTitle[]"  class="myJobsCheckbox" ></td>';
    	  		
    	  		  return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "jobId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  var jobId = "";
	    	  	 	 if(opt.jobId != null && opt.jobId != 'undefined' && opt.jobId != ""){
		           		 jobId = opt.jobId+" "+opt.jobId ;
		           	 }else{
		           		 jobId = "----";
		           	 }
	    	  	 	 
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+jobId+'">'+jobId+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "jobTitle",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+opt.jobTitle+'">'+opt.jobTitle+'</div></td>';
	              return result;
              		
              }
          },{
		    	  "targets": colIndex++,
	              "data": "jobType",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var jobType = "";
	            	    if(opt.jobType != null && opt.jobType != 'undefined' && opt.jobType != ""){
	   	           		 jobType = opt.jobType ;
	   	           	     }else{
	   	           		 jobType = "----";
	   	           	     }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+jobType+'">'+jobType+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "location",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var location="";
	            	    if(opt.location != null && opt.location !='' && opt.location != 'undefined'){
	            	    	location = opt.location;
	            	    }else{
	            	    	location = "----";
	            	    }
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+location+'">'+location+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "rate",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var rate = "";
	            	    if(opt.rate != null && opt.rate != 'undefined' && opt.rate != ""){
	   	           		 rate = opt.rate ;
	   	           	     }else{
	   	           		 rate = "----";
	   	           	      }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+rate+'">'+rate+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "posted on",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.createdDate+'">'+opt.createdDate+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "priority",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var priority = "";
	            		 if(opt.priority != null && opt.priority != 'undefined' && opt.priority != ""){
	    	           		 priority = opt.priority ;
	    	           	 }else{
	    	           		 priority = "&nbsp;&nbsp;----";
	    	           	 }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+priority+'">'+priority+'</span></div></td>';
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#jobsDataTable").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>"); 
    $("input[type=search]").hide();
    $("#jobsDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   jobsDataTableList(type);
    	   }
    	});
}

</script>

<!-- for job selection script stars -->


<script>
function toggleJobs(source) {
var checkboxes = document.querySelectorAll('input[class="myJobsCheckbox"]');
if($(source).is(":checked"))
	  $('input[class="myJobsCheckbox"]').prop('checked', 'checked');
else 
	  $('input[class="myJobsCheckbox"]').removeProp('checked');

$('input[class="myJobsCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
		  allJobs[$(this).data("id")] = $(this).val();
	  } else {
		  allJobs[$(this).data("id")] = "";
	  }
});
}
</script>

<script>
function getCheckedJobs(){
	
	var internalOutput = "";
	 for(var e in allJobs) {
		 var checkbox_value = allJobs[e].split(",");
		 internalOutput = internalOutput+"<option value='" +checkbox_value[0]+ "' selected>"+checkbox_value[1]+"</option>";
	  }
	
	    $("#selectedJobs").append(internalOutput);
		$("#selectedJobs").trigger("chosen:updated");
		
		$("input[name='jobTitle[]']:checked").removeAttr('checked');
		$('#checkAllJobs').removeAttr('checked'); 
		
}
</script>

<script>
var allJobs = [];
function checkJob(ele) {
	if($(ele).is(":checked")) {
		allJobs[allJobs.length] = $(ele).val();
	}
}
</script>
<!-- for job selection script ends -->

  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			
			  //Adding Customized Emails to the list
	        var select, chosen;
		    select = $("#otherEmails");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space Or Tab To Add New Email:' });
		    chosen = select.data('chosen');
		    chosen.search_field.bind(('keydown keyup'), function(e)
		    {
		    	 var otherEmailArray = this.value.split(/[ ,]+/);
		    	 if(otherEmailArray.length > 1){
		    		 for (var i=0; i < otherEmailArray.length; i++) {
	        			 var option = $("<option>").val(otherEmailArray[i].replace(",","")).text(otherEmailArray[i].replace(",",""));
	        			 select.append(option);
	 		             select.find(option).prop('selected', true);
	 	        		 $("#otherEmails").trigger("chosen:updated");
	                 }
		    	 }else{
		    		 var newEmail = this.value.replace(","," ");
		    		   if (tagItBykeyForEmails(e) && (newEmail != null  && newEmail != '' && newEmail != 'undefined'))
				        {
		    			         preventTabkey(e); 
			        		     var par = this.parent;
				        		 var option = $("<option>").val(newEmail.replace(",","")).text(newEmail.replace(",",""));
				        		 select.append(option);
					             select.find(option).prop('selected', true);
				        		 $("#otherEmails").trigger("chosen:updated");
			        		
				        } 
		    	 }
		    	
		    });

		    var selectone, chosenone;
		    selectone = $("#emailsList");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space Or Tab To Add New Email:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.bind(('keydown keyup'), function(e)
		    {
		    	 var toEmailArray = this.value.split(/[ ,]+/);
		    	 if(toEmailArray.length > 1){
		    		 for (var i=0; i < toEmailArray.length; i++) {
	        			 var option = $("<option>").val(toEmailArray[i].replace(",","")).text(toEmailArray[i].replace(",",""));
	        			 selectone.append(option);
	        			 selectone.find(option).prop('selected', true);
	 	        		 $("#emailsList").trigger("chosen:updated");
	                 }
		    	 }else{
		    			var newEmail = this.value.replace(","," ");
		    			if (tagItBykeyForEmails(e) && (newEmail != null  && newEmail != '' && newEmail != 'undefined'))
				        {
		    				preventTabkey(e); 
				        	var par = this.parent;
				        		 var option = $("<option>").val(newEmail.replace(",","")).text(newEmail.replace(",",""));
				        		 selectone.append(option);
				        		 selectone.find(option).prop('selected', true);
					        		$("#emailsList").trigger("chosen:updated");
				        }
		    	 }
		    
		    });

			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
});
</script> 

  <script>
//upload file script -laptop-shiftA
document.getElementById("attachfileBtn").onchange = function () {
	$('#attachfile-mail').val($(this).val().replace(/C:\\fakepath\\/i, ''))
};
</script> 
  
<script type="text/javascript">
	bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
</script>
 
 <script>
//Bootstrap dropdown
$('.dropdown-menu').click(function(e) {
    e.stopPropagation();
});
</script>

<script>
	  function vendorListChange(){
		  document.getElementById("vendorsListDiv").style.display="";
		  var listType = document.getElementById("vendorListType").value;
		  if(listType == 'listbyContact'){
			  $("#mailBoxVendorsList").empty(); 
			  vendorContactList();
		  }else{
			  $("#mailBoxVendorsList").empty(); 
			  vendorsList();
		  }
		  
	  }
  </script>

<!-- For Vendor list in job campaigns -->

<script>
function toggleVendors(source) {
var checkboxes = document.querySelectorAll('input[class="myVendorCheckbox"]');
if($(source).is(":checked"))
	  $('input[class="myVendorCheckbox"]').prop('checked', 'checked');
else 
	  $('input[class="myVendorCheckbox"]').removeProp('checked');

$('input[class="myVendorCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
		  vendorList[$(this).data("id")] = $(this).val();
	  } else {
		  vendorList[$(this).data("id")] = "";
	  }
});
}
</script>


<script>
function toggleVendorContacts(source) {

if($(source).is(":checked"))
	  $('input[class="myVendorContactsCheckbox"]').prop('checked', 'checked');
else 
	  $('input[class="myVendorContactsCheckbox"]').removeProp('checked');

$('input[class="myVendorContactsCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
		  vendorcontactsList[$(this).data("id")] = $(this).val();
	  } else {
		  vendorcontactsList[$(this).data("id")] = "";
	  }
});

}
</script>

<script>
function vendorsList(){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='vendorDataTableSearch'/>";
	 	result = result +"</div>";
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
  		result = result +'<table id="vendorDataTable" class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'             <th><input type="checkbox" id="checkAllVendors" onclick="toggleVendors(this);"></th>';
 		result = result +'             <th>Vendor Name</th>';
 		result = result +'             <th>Website</th>';
 		result = result +'             <th>Location</th>';
 		result = result +'             <th>Created By</th>';
 		result = result +'             <th>No.of Contacts</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="vendorbtnId" onclick="getContactEmails();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    $("#mailBoxVendorsList").empty();
	    $("#mailBoxVendorsList").append(result);
	    vendorList = {};
	    vendorsDataTableList();
}
</script>

<script>
function vendorsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#vendorDataTableSearch").val();
	 var responderType ="JobCampaignVendor";
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/searchClientDetails?searchValue="+searchValue+"&responderType="+responderType;
	 vendorsPagination(url);
	 
}
</script>

<script>
function vendorsPagination(url){
	var colIndex = 0;
    $('#vendorDataTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "ischeck" },
            { "data": "responderOrgName" },
            { "data": "website"},
            { "data": "createdBy"},
            { "data": "responderCity"},
            { "data": "noContacts"}
        ],
         "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		   var flag = false;
    	  		   
	   	  		   for(var e in vendorList) {
	   	  			 if(opt.orgRelationId+"" === vendorList[e]) {
	   	  				 flag = true;
	   	  				 break;
	   	  			 }
	   	  		 } 
    	  		
    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+opt.orgRelationId+'" data-id="'+opt.orgRelationId+'" value="'+opt.orgRelationId+'" onchange="checkVendors(this)"   type="checkbox" name="orgRelationIds[]" class="myVendorCheckbox" ></td>';
    	  		  return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "orgName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  var orgName = "";
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+opt.orgName+'">'+opt.orgName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "website",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  var website = "";
	    	  		 if(opt.website != null && opt.website != 'undefined' && opt.website != ""){
	    	  			website = opt.website ;
		           	 }else{
		           		website = "----";
		           	 }
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+website+'">'+website+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "responderCity",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  var responderCity="";
            	  if(opt.responderCity != null && opt.responderCity != 'undefined' && opt.responderCity != ""){
            		  responderCity = opt.responderCity ;
		           	 }else{
		           		responderCity = "----";
		           	 }
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+responderCity+'">'+responderCity+'</div></td>';
	              return result;
              		
              }
          },{
		    	  "targets": colIndex++,
	              "data": "createdBy",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var createdBy = "";
	            	    if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
	            	    	createdBy = opt.createdBy ;
	   	           	     }else{
	   	           	   createdBy = "----";
	   	           	     }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+createdBy+'">'+createdBy+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "noContacts",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var noContacts = "";
	            		 if(opt.noContacts != null && opt.noContacts != 'undefined' && opt.noContacts != ""){
	            			 noContacts = opt.noContacts ;
	    	           	 }else{
	    	           		noContacts = "&nbsp;&nbsp;----";
	    	           	 }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+noContacts+'">'+noContacts+'</span></div></td>';
	            		return result;
	              }
		     }], 
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     	"sSearch": "",
	     	"sZeroRecords": "", "sEmptyTable": "", 
	     	"sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#vendorDataTable").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>"); 
    $("input[type=search]").hide();
    $("#vendorDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   vendorsDataTableList();
    	   }
    	});
}

</script>


<script>
function getContactsList(){
	
	document.getElementById("vendorsListDiv").style.display="none";
	$("#mailBoxVendorsList").empty();
	
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='contactsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
	 	
  		result = result +'<table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +' <th><input type="checkbox" id="checkAllContacts" onclick="toggleMyContacts(this);"></th>';
		result = result +'<th>Contact Name</th>';
		result = result +'<th>Email</th>';
		result = result +'<th>Source</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="assignbtnId" onclick="getAllEmailContacts();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    
     	$("#mailBoxVendorsList").append(result);
     	contactUsers = [];
	    contactsDataTableList();
}
</script>

<script>
function contactsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#contactsDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getUserContactsListForMalling?searchValue="+searchValue;
	 contactsPagination(url);
}
</script>

<script>
function contactsPagination(url){
	var colIndex = 0;
    $('#example').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "ischeck" },
            { "data": "contactName" },
            { "data": "email"},
            { "data": "source"}
           
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, opt, meta) {
    	  		var result = "";
    	  		var flag = false;
   	  		 
	   	  		   for(var e in contactUsers) {
	   	  			 if(opt.email+"" === contactUsers[e]) {
	   	  				 flag = true;
	   	  				 break;
	   	  			 }
	   	  		 } 
   	  		 
   	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+opt.id+'" data-id="'+opt.id+'" value="'+opt.email+'" onchange="checkContactUsers(this)"  type="checkbox" name="emails[]"  class="myCheckbox" ></td>';
	              return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "contactName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+opt.fullName+'">'+opt.fullName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "email",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+opt.email+'">'+opt.email+'</div></td>';
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "source",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.source+'">'+opt.source+'</span></div></td>';
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#example").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>"); 
    $("input[type=search]").hide();
    $("#contactsDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   contactsDataTableList();
    	   }
    	});
}

</script>

<script>
var contactUsers = [];
function checkContactUsers(ele) {
	if($(ele).is(":checked")) {
		contactUsers[contactUsers.length] = $(ele).val();
	}
}
</script>

<script>
var vendorList = {};
function checkVendors(ele){
	if($(ele).is(":checked")) {
		vendorList[$(ele).data("id")] = $(ele).val();
	}
}
</script>



<script>
var vendorcontactsList = {};
function checkVendorsContacts(ele){
	if($(ele).is(":checked")) {
		vendorcontactsList[$(ele).data("id")] = $(ele).val();
	}
}
</script>


 <script>
function toggleMyContacts(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
            checkContactUsers(checkboxes[i])
        
    }
$('input:checkbox:not(#checkAllContacts)').click(function() {
    if(!this.checked) {
        $('#checkAllContacts').removeAttr('checked');
    }
});
}
</script>
<!-- End -->

 <!-- Vendor contacts List Starts Here-->

<script>
function vendorContactList(){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='vendorContactsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
	 	
  		result = result +'<table id="vendorconDataTable"   class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		 result = result +' <th><input type="checkbox" id="checkAllVendorContacts" onclick="toggleVendorContacts(this);"></th>'; 
		result = result +'  <th>Vendor Name</th>';
	   	result = result +'	<th>Contact Name</th>';
	   	result = result +'  <th>Email</th>';
	   	result = result +' <th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="vendorContactbtnId" onclick="getVendorContactEmails();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    $("#mailBoxVendorsList").empty();
	    $("#mailBoxVendorsList").append(result);
	    vendorcontactsList = {}
	    vendorContactDataTableList();
	    
	   
}
</script>

<script>
function vendorContactDataTableList(){
	 var searchValue = $("#vendorContactsDataTableSearch").val();
	 var responderType ="JobCampaignVendor";
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/searchClientContactDetails?searchValue="+searchValue+"&responderType="+responderType;
	 vendorContactpagination(url);
}
</script>

<script>
	function vendorContactpagination(url){
		var colIndex = 0;
		 $('#vendorconDataTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'GET',
	            pages: 10
	        }),
	        "columns": [
	        	{ "data": "" },
	            { "data": "orgName" },
	            { "data": "spocName"},
	            { "data": "spocEmail" },
	            { "data": "spocStatus" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "ischeck",
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
		    	  		 var flag = false;
		    	  		
		    	  	   for(var e in vendorcontactsList) {
		    	  		   if(report.spocEmail+"" === vendorcontactsList[e]) {
		    	  				 flag = true;
		    	  				 break;
		    	  			 }
		    	  		 }  
		    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+report.id+'" data-id="'+report.id+'" value="'+report.spocEmail+'" onchange="checkVendorsContacts(this);"  type="checkbox" name="contactEmails[]"  class="myVendorContactsCheckbox" ></td>'; 
		    	  		
		    	  		  return result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "orgName",
	            "render": function ( data, type, report, meta ) {
	            	     var result = "";
		    	  		 result = result +'<input type="hidden" value="row'+report.id+'" id="row'+report.id+'">';
		    	  		result = result +'<td><div class="tbldata-dotdot-100" title="'+report.orgName+'">'+report.orgName+'</div></td>';
			             return  result;
	            }
	          },{
		            "targets": colIndex++,
		            "data": "spocName",
		            "render": function ( data, type, report, meta ) {
		            	     
		            	    var spocName ="";
		            	    if(report.spocName != null && report.spocName !='' && report.spocName !='undefined'){
		            	    	spocName = report.spocName;
		            	    }else{
		            	    	spocName = "----";
		            	    }
		            	     var result = "";
				   			 result = result +'<td><div class="proj_show" ><span title="'+spocName+'">'+spocName+'</span></div></td>';
			                 return result; 
		            }
		          }, {
	              "targets": colIndex++,
	              "data": "spocEmail",
	              "render": function ( data, type, report, meta ) {
	            	    var spocEmail="";
	            	     var result = "";
	            	     if(report.spocEmail != null && report.spocEmail !='' && report.spocEmail !='undefined'){
	            	    	 spocEmail=report.spocEmail;
	            	     }else{
	            	    	 spocEmail = "----";
	            	     }
	            	     result = result +'<td><div class="tbldata-dotdot-vsm" title='+spocEmail+'>'+spocEmail+'</div></td>'; 
	  	                 return result; 
	              }
	            },{
		    	  	"targets": colIndex++,
		    	  	"data": "spocStatus",
		    	  	"sortable": false,
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
		    	  		result = result + ' <td><div class="tblminwidth-30 dropdown-select paddingNone">';
		    	  		result = result + '<span title='+report.spocStatus+'>'+report.spocStatus+'</span>';
	   	        		result = result + ' </div></td>'; 
		    	  		 return result; 
		    	  	}
		    	  }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": false,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
		$("#vendorconDataTable").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>");
	    $("input[type=search]").hide();
	    $("#vendorContactsDataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   vendorContactDataTableList();
	    	   }
	    	});
}
</script>

 <!-- Vendor contacts List Ends Here Here-->

<script>
function getVendorContactEmails(){
	
	
	var internalOutput = "";
	
	 for(var e in vendorcontactsList) {
		 if(vendorcontactsList[e] == 'Email' || vendorcontactsList[e] == 'Notification' || vendorcontactsList[e] == 'on')
				continue;
		 internalOutput = internalOutput+"<option value='" +vendorcontactsList[e]+ "' selected>"+vendorcontactsList[e]+"</option>";
	  }
	
	
	    $("#emailsList").append(internalOutput);
		$("#emailsList").trigger("chosen:updated");
		
		$("input[name='contactEmails[]']:checked").removeAttr('checked');
		$('#checkAllVendorContacts').removeAttr('checked');
		
}
</script>

<script>
function getAllEmailContacts(){
	var internalOutput = "";
	
	for(var e in contactUsers) {
		if(contactUsers[e] == 'Email' || contactUsers[e] == 'Notification' || contactUsers[e] == 'on')
			continue;
		internalOutput = internalOutput+"<option value='" +contactUsers[e]+ "' selected>"+contactUsers[e]+"</option>";
	}
	
	    $("#emailsList").append(internalOutput);
		$("#emailsList").trigger("chosen:updated");
		
		$("input[name='emails[]']:checked").removeAttr('checked');
		$('#checkAllVendors').removeAttr('checked');
		
		
}
</script>

<script>
function getContactEmails(){
	var orgRelationIds = new Array();
	var internalOutput = "";
	
	 for(var e in vendorList) {
		 if(vendorList[e] == 'Email' || vendorList[e] == 'Notification' || vendorList[e] == 'on')
				continue;
		 orgRelationIds.push(vendorList[e])
	  }
	 
	 
	 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
         type : "POST",
         url: sourceleadUrl+"/getVendorContactsEmailsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationIds="+orgRelationIds,    
          success :function(data) {
       	    var str_array = data.replace(/\"/g, "").split(',');
				for(var i = 0; i < str_array.length; i++) {
				   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
				   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
				}
				
				 $("#emailsList").append(internalOutput);
				 $("#emailsList").trigger("chosen:updated");
				 
				 $("input[name='orgRelationIds[]']:checked").removeAttr('checked');
				 $('#checkAllClients').removeAttr('checked');
				
          },
          
     }); 
	 
}
</script>

<script>
function getSenders(){
		$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getSenders?"+tokenParamName+"="+tokenValue,
		success :function(json) {
		var output = "";
		output = output + "<option value=''>Select Sender</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		output = output + "<option value='" +opt.senderId+ "'>"+opt.cpnSenderFromMail+"("+opt.cpnSenderName+")</option>";
		
		}
		
		$("#senderIdentity").empty();
		$("#senderIdentity").append(output);
		$('#senderIdentity').trigger("chosen:updated");
		},
		});
}
</script>

<script>
function getRecipientContactsList(){
		$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getRecipientList?"+tokenParamName+"="+tokenValue,
		success :function(json) {
			alert(json);
		/* var output = "";
		output = output + "<option value=''>Select Sender</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		alert(opt.cpnSenderFromMail);
		output = output + "<option value='" +opt.senderId+ "'>"+opt.cpnSenderFromMail+"("+opt.cpnSenderName+")</option>";
		
		}
		
		$("#senderIdentity").empty();
		$("#senderIdentity").append(output);
		$('#senderIdentity').trigger("chosen:updated"); */
		},
		});
}
</script>
<jsp:include page="masterFooter.jsp" />