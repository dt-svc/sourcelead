<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.center-div{
width:300px;
height:30px;
margin:10px 220px 0;
}
.center-div input {
	border:none;
	background:transparent
}
</style>
  
  
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Import Vendor & Contacts</h2>
      </div>
      <div class="remove_pop" id="selector"  style="display: none;">
	         <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
		</div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="vendorsList">Vendor & Contact List</a> &raquo; Import Vendor & Contacts </div>
    </section>
     <form:form id="upload_form" name="upload_form" enctype="multipart/form-data">
    <%-- <form:form id="upload_form" name="upload_form" method="post" enctype="multipart/form-data" modelAttribute="uploadedFile" action="clientFileUpload?${_csrf.parameterName}=${_csrf.token}&type=Client"> --%>
    <div class="content-area clearfix">
    <div id="loadinggif"></div> 
      <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
        <div class="row minHyewtriuwe rwrghek	fghjqwehjgfwej tkjreight">
          <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
		  <!-- Download File button -->
		  <div class="clearfix mb row">
		 <%--  <sec:authorize access="hasAnyRole('ROLE_CLIENT_BULK_IMPORTS')"> --%>
		<%-- 	<div class="col-xs-12 col-md-6 hidden">
			 <a href="<c:url value='/downloadClientTemplate?name=client'/>">
			 <button class="btn btn-success btn-block" type="button">
					<i class="fa fa-download"></i>&nbsp;&nbsp;Download Client Template
			</button>
			</a>
			</div> --%>
			<%-- </sec:authorize> --%>
			<%-- <sec:authorize access="hasAnyRole('ROLE_CLIENT_CONTACT_BULK_IMPORTS')"> --%>
			<div class="col-xs-8 col-md-8">
			 <a href="<c:url value='/downloadVendorTemplate?name=Contact'/>">
			 <button class="btn btn-success btn-block" type="button">
					<i class="fa fa-download"></i>&nbsp;&nbsp;Download Vendor Template
			</button>
			</a>
			</div>
			<div class="col-xs-4 col-md-4">
			<div class="btn-group btn-group-justified">
			  <div class="btn-group">
			  <input type="button" class="btn btn-primary" id="upload_button" name="upload_button"  onclick = "saveBulkImport();" value="Import">
				</div>
				<div class="btn-group">
			  <input type="reset" class="btn btn-danger" value="Reset" onClick="javascript:window.location.href = '<c:url value='/vendorsList'/>'">
				</div>
			  </div>
			</div>
			<%-- </sec:authorize> --%>
			</div>
			<!-- Attach File button -->
			
			<div class="dropzone">
			  <div class="clearfix">
			<%--   <div class="btn-group pull-right hidden">
			  <button class="btn btn-sm btn-primary" id="upload_button1" type="submit" onclick = "saveBulkImport(); check();uploadSave();checkFile()">
				<i aria-hidden="true" class="fa fa-upload"></i>&nbsp;&nbsp;&nbsp;Import
			  </button>
			  <button class="btn btn-sm btn-danger" type="reset" onClick="javascript:window.location.href = '<c:url value='/clientDashboard'/>'">
				<i aria-hidden="true" class="fa fa-times"></i>&nbsp;&nbsp;&nbsp;Cancel
			  </button>
			  </div> --%>
			  </div>
			<div class="file-upload clearfix text-center" id="fileDiv" onclick = "check();">
		<div class="col-md-12" id="clientNameDiv">
			<label class="bg-default">
			<i aria-hidden="true" class="fa fa-paperclip" title="Attach file"></i> <br/>
                <input type="file" name="file" field-name="File Upload" style="display: none;" id="uploadBtn" data-validation="required validate_importFile">
            </label>
        	</div>
        	
        	<div class="center-div">
        	<input id="uploadFile" placeholder="" disabled="disabled" style="width: 100%;"/>
        	</div>
        	
        	
			</div>
			<!-- <p class="text-danger text-center mt hidden">Please Select the file &hellip;</p> -->
			<%-- <p class="text-danger text-center mt" id="fileExist"><form:errors path="file" /></p> --%>
			<%-- <p class="text-danger text-center mt" id="success">${msg}</p> 
			<div><span class="text-danger"><strong>${fileNameWrong}</strong></span></div> --%>
		   	 
		   	 <!--  Downloaded File show here -->
		   	   <div class="col-xs-3 alert alert-success fade in hidden" style="position:absolute; top:10px;" id="fileAttached">
				    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				    <div>File Holder</div>
				</div>
		   	 <!--  Downloaded File show here -->
		   	 
			</div>
			
			<!-- =================== -->
			 <div class="bgInfo bg-info clearfix hidden">
			 <span class="pull-left"><i class="fa fa-question" aria-hidden="true"></i></span>
			  <span class="info-msg pull-left">
			  <p>
			  According to this template, the Users need to <span class="text-danger"><strong>Import Required Fields</strong></span></p>
			  </p>
			  <p>
			  <label><i class="fa fa-user"></i>&nbsp;&nbsp;Vendor Name</label>
<!-- 			  <label><i class="fa fa-user"></i>&nbsp;&nbsp;First Name</label>
			  <label><i class="fa fa-envelope"></i>&nbsp;&nbsp;Email</label>
 -->			  </p>
			  </span>
            </div>
            <div class="panel-body checkbox div-checkbox hidden">
				  <label><input type="checkbox" checked="checked" value="Y" name="email"></label><span>Send Emails</span>
		    </div>
          </div>
		  <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
		  			 <div class="bgInfo bg-info clearfix text-center">
			 <span><i class="fa fa-question" aria-hidden="true"></i></span>
			  <span class="info-msg clearfix mt">
			  <p class="clearfix mt">
			  According to this template, the Users need to <span class="text-danger"><strong>Import Required Fields</strong></span></p>
			  </p>
			  <p class="text-center">
			  <label class="paddingRight"><i class="fa fa-user"></i>&nbsp;&nbsp;Vendor Name</label>
			  <label class="paddingRight"><i class="fa fa-user"></i>&nbsp;&nbsp;First Name</label>
			  <label><i class="fa fa-envelope"></i>&nbsp;&nbsp;Email</label>
			  </p>
			  </span>
            </div>
		  </div>
        </div>
        <div class="require_wrap_table filter-position mt25">
        
        	<div id="excelimportData"></div>
        	
        	
        	<div class="clearfix ml">
        	<ul class="list-inline">
	        	<li class="text-success" title="Merge"><b class="fa fa-object-group paddingRight"></b>To merge with the existing Vendor</li>
	        	<li class="text-primary" title="Add"><b class="fa fa-plus paddingRight"></b>To add the new Vendor and contact</li>
	        	<li class="text-danger" title="Remove"><b class="fa fa-trash paddingRight"></b>To remove the record</li>
        	</ul>
        </div>
        </div>
        
        <div class="form-footer clearfix row hidden">
          <div class="col-lg-12">
            <div class="pull-right">
              <input type="button" class="btn btn-primary" value="Import">
              <input type="button" class="btn btn-default ml" value="Cancel">
            </div>
          </div>
        </div>
      </div>
    </div>
    </form:form>
  </section>  
  
  <div class="side_mask"></div>   
  <section class="mergePopup">
  <div class="mergeAA"><span onclick="mergeclose();"><i class="fa fa-times"></i></span></div>
	<span><select class="chosen-select form-control mergePopupSel" id="vendorListIds" onchange="flush();">
    	       
	</select></span>  
	<input type="button" onclick="mergeWithRecord();" value="Merge" class="btn btn-primary mergePopupbtn" />
	<div class="clearfix"></div>
	<p id="clientError"></p>	
	<p><i class="fa fa-info-circle"></i>The user will be created in the selected Vendor</p>	
  </section>
  
 <!-- <script>
$(document).ready(function(){
	document.getElementById("uploadFile").value = '';
	var errormsg = '${errorMessage}';
	$("#fileAttached").hide();
	if(errormsg){
		$("#errorFileDiv").show();
	}
});
</script> -->

<script type="text/javascript">
function saveBulkImport(){
	 var validationSettings = {
	            errorMessagePosition : 'element',
	            borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	            };
	if ($('#upload_form').validate(false, validationSettings)){
	var frm = $('#upload_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, "");
	
	//It will escape all the special characters
	var jsonData = encodeURIComponent(con);
	var form = $('#upload_form')[0];
	var formData = new FormData(form); 
	var sourceleadUrl = '${sourceleadUrl}';
	
	$("#loadinggif").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		   	type: "POST",
		   	data: formData,
    	    contentType : 'application/json; charset=utf-8',
    	    enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
    	    dataType : 'json',
		    url : sourceleadUrl+"/clientExcelFileUpload?${_csrf.parameterName}=${_csrf.token}&responderType=Vendor", 	   
 		    success : function(data) {
 		    	$("#loadinggif").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 		    	if(data == "failure"){
 		    		$("#selector").removeAttr("style");
 	    			$("#selector").css({ display: "block" });
 		    		$("#msgDiv").html("Mandatory fields are required");
 		    		msgFadein();
 		    	}else{
 		    		prepareExcelDataDiv(data);
 		    	}
 		    },
 		   error: function(e){
 			  alert(e);
	        }
	 });
	 
}
}
</script>

<script>
	function msgFadein() {
		$("#selector").fadeIn(200);
        $('#selector').delay(2000).fadeOut('slow');
	}
</script> 

<script>
function prepareExcelDataDiv(data){
	var result = '';
	result = result +  '<table id="excelimportDataTable" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '     <th>Vendor Name</th>';
	result = result + '    <th>Website</th>';
	result = result + '    <th>Location</th>';
	result = result + '    <th>Contact Name</th>';
	result = result + '    <th>Email</th>';
	result = result + '    <th>Status</th>';
	result = result + '    <th>Owner(Email)</th>';
	result = result + '    <th>Reason</th>';
	result = result + '    <th>Action</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr id="row'+report.id+'">';
        result = result + '<td>';
        if(report.orgName != null && report.orgName != '' && report.orgName !='undefined'){
           	result = result + '<div><span>'+report.orgName+'</span></div>';
     		} else{
    		result = result + '<div><span>---</span></div>';
    		    }
        result = result + '</td>';
    	result = result + '<td>';
        if(report.website != null && report.website != '' && report.website !='undefined'){
       	result = result + '<div><span>'+report.website+'</span></div>';
 		} else{
		result = result + '<div><span>---</span></div>';
		    }
        result = result + '</td>';
        result = result + '<td>';
        if(report.country != null && report.country != '' && report.country !='undefined'){
         result = result + '<div><span>'+report.country+'</span></div>';
        }
        else{
		 result = result + '<div><span>---</span></div>';
		    }
         result = result + '</td>';
        
        result = result + '<td>';
        if(report.firstName != null && report.firstName != '' && report.firstName !='undefined'){
        		result = result + "<div><span>"+report.firstName+"</span></div> ";
        }
        else{
		    	result = result + "<div><span>---</span></div>";
		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.email != null && report.email != '' && report.email !='undefined'){
       		 result = result + "<div><span>"+report.email+"</span></div>";
        }
        else{
		    	result = result + "<div><span>---</span></div>";
		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.status != null && report.status != '' && report.status !='undefined'){
       		 result = result + "<div><span>"+report.status+"</span></div>";
        }
        else{
		    	result = result + "<div><span>---</span></div>";
		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.ownerEmail != null && report.ownerEmail != '' && report.ownerEmail !='undefined'){
       		 result = result + "<div><span>"+report.ownerEmail+"</span></div>";
        }
        else{
		    	result = result + "<div><span>---</span></div>";
		    }
        result = result + '</td>';
        
        result = result + '<td>';
        if(report.reason != null && report.reason != '' && report.reason !='undefined'){
       		 result = result + "<div><a class='text-danger' title='"+report.reason+"'>"+report.reason+"</a></div>";
        }
        else{
		    	result = result + "<div><span>---</span></div>";
		    }
        
        result = result + '</td>';
        
         result = result + '<td>';
         if(report.reason == 'Spoc existed' || report.reason == 'Current org user' || report.reason ==  'Vendor name is mandatory' || report.reason ==  'Vendor name should contain above 2 char' || report.reason ==  'Invalid email' || report.reason ==  'Invalid moblie number' || report.reason ==  'First Name is mandatory' || report.reason ==  'Email is Mandatory'){
        	 result = result + '<div class="proj_active" id="removeStatus'+report.id+'"><a class="text-danger fa fa-trash" title="Remove" onclick="removeRecord('+report.id+');"></a></div>';  
         }else{
        	 result = result + '<div class="tbldata-dotdot-100" id="recordStatus'+report.id+'"><a class="text-success fa fa-object-group paddingRight aa" title="Merge" onclick="mergeRecord('+report.id+');"></a> <small>|</small> <a class="text-primary fa fa-plus paddingRight" title="Add" onclick="addRecord('+report.id+');"></a> <small>|</small> <a class="text-danger fa fa-trash" title="Remove" onclick="terminateRecord('+report.id+');"></a></div>';
         }
       
        result = result + '</td>';
        result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	  $("#excelimportData").empty();
    $("#excelimportData").append(result);
    excelimportDataPagination();
}
</script>

<script>
function removeRecord(recordId){
	var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/deleteRecordFromBulk?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&responderType=Vendor",       
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<b class='proj_closed'>Removed</b>");
       },
   }); 
}

</script>


<script>
function terminateRecord(recordId){
	var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/deleteRecordFromBulk?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&responderType=Vendor",       
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#recordStatus"+recordId).html("<b class='proj_closed'>Removed</b>");
       },
   }); 
}

</script>
<script>
function addRecord(recordId){
	var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/addRecordFromBulk?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&responderType=Vendor",       
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#recordStatus"+recordId).html("<b class='proj_active'>Added</b>");
       },
   }); 
}

</script>

<script>
function flush(){
	$("#clientError").empty();
}
</script>
<script>
function mergeWithRecord(){
	var recordId = document.getElementById("vendorListIds").value;
	if(recordId!=null && recordId !=''){
	$("#clientError").empty();
	var recordIdVal = recordId.split(',');
	var rowHtmlContent =  $("#row"+recordIdVal[1]).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	   type : "POST",
	    url: sourceleadUrl+"/mergeRecordFromBulk?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&responderType=Vendor",       
	    success :function(data) {
	    	$("#row"+recordIdVal[1]).empty();
	    	$("#row"+recordIdVal[1]).html(rowHtmlContent);
	        $("#recordStatus"+recordIdVal[1]).html("<b class='proj_process'>Merged</b>");
	        $(".side_mask").hide();
	   		$(".mergePopup").hide();
	   },
	}); 
	 $(".side_mask").fadeIn(200);
	 $(".mergePopup").fadeIn(100);
	}
	else{
		$("#clientError").empty().append("Select the Vendor") ; 
	}
}
</script>


<script>
function mergeRecord(recordId){
	flush();
	 var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	$('#vendorListIds').empty();
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/getMatchedClientList?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&responderType=Vendor",       
        success :function(data) {
        	var json = JSON.parse(data);
        	var result = "";
        	
        	$('#vendorListIds').append('<option value="">---select Vendor---</option>');
        	for (var i=0; i<json.matchedDomainClientList.length; i++)
			{ 
        		var report = json.matchedDomainClientList[i];
        		$('#vendorListIds').append('<option value='+report.id+','+recordId+'>'+report.orgName+'</option>');
			}
        	for (var i=0; i<json.matchedClientNameList.length; i++)
			{ 
        		var report = json.matchedClientNameList[i];
        		$('#vendorListIds').append('<option value='+report.id+','+recordId+'>'+report.orgName+'</option>');
			}
        	
        	$('#vendorListIds').trigger("chosen:updated");
        	
        	
        	/* $("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#recordStatus"+recordId).html("<b class='proj_active'>Merged</b>"); */
       },
   }); 
	 $(".side_mask").fadeIn(200);
   $(".mergePopup").fadeIn(100);
}
function mergeclose(){
	$(".side_mask").fadeOut(200);
	$(".mergePopup").fadeOut(100);
}
</script>

<script>
function excelimportDataPagination(){
	$('#excelimportDataTable').DataTable({ 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", 
     		 "sEmptyTable": ""
     	}
    } );
};
</script>




<script type="text/javascript">
//script for upload file
document.getElementById("uploadBtn").onchange = function () {
	document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();
}
</script>

<!-- <script>
function check(){
	var errormsg = '${errorMessage}';
		$("#errorFileDiv").hide();
		$("#fileExist").empty();
		$("#success").empty();
		$('#fileDiv').find('p.jquery_form_error_message').remove();
}

</script>

<script>
function errorMsgAppend(){
	var errormsg = '${errorMessage}';
	if(errormsg){
	    $("#errorFileDiv").hide();
	 	$("#fileExist").empty();
		$("#success").empty();
	}
	
}

</script>

<script type="text/javascript">
function uploadSave() {
	$('#upload_form').submit(function() {
          if (($(this).validate(false, validationSettings))){
        	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            	return true;  
          }
          else{
        	  return false;	
          }
          	
          })
}
	$('body').on('blur', '#upload_form', function() {
		$("#upload_form").showHelpOnFocus();
		$("#upload_form").validateOnBlur(false, validationSettings);
		
	});
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
 

<script language="javascript" type="text/javascript">
 function checkFile()
    {
//      var fsize = $('#uploadBtn')[0].files[0].size;
     $('#clientNameDiv').find('p.jquery_form_error_message').remove();
     var res_field = document.upload_form.elements["uploadFile"].value;  
     var extension = res_field.substr(res_field.lastIndexOf('.') + 1).toLowerCase();
     var allowedExtensions = ['xls', 'xlsx'];
     if (res_field.length > 0)
        {
             if (allowedExtensions.indexOf(extension) === -1) 
                {
                  //alert('Invalid file Format. Only ' + allowedExtensions.join(', ') + ' are allowed.');
                   $("input#uploadBtn").after("<p class='jquery_form_error_message'>");
               	   document.getElementById("uploadBtn").setAttribute('record-exist', 'yes');
               	   document.getElementById("uploadBtn").setAttribute('record-exist-errorMsg',"Invalid file Format. Only " + allowedExtensions.join(', ') + " are allowed.");
                    return false;
                  return false;
                }
                	if (fsize < 4000 || fsize > 5120000)
                    {
                   	   $("input#uploadBtn").after("<p class='jquery_form_error_message'>");
               			document.getElementById("uploadBtn").setAttribute('record-exist', 'yes');
               			document.getElementById("uploadBtn").setAttribute('record-exist-errorMsg',"File size should between above 50kb & below 500 KB");
                        return false;
                    }  
                
       }
		$('input#uploadBtn').removeAttr("class record-exist record-exist-errormsg");
		$('input#uploadBtn').attr('class','valid form-control');
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
		$("#upload_form").click(function() {
			if ($(this).validate(false, validationSettings)) {
			}
		});
	   
    }
</script>  -->

  <!-- Footer Area -->
  <footer class="footer-area clearfix">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div>
  </footer>
  <!-- Footer Area -->

   