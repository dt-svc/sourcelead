<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 

<style type="text/css">
.center-div{
width:500px;
height:30px;
margin:0 auto;
clear:both;
display: inline-block;
text-align: center;
line-height: 30px;
}
.center-div input {
	border:none;
	background:transparent
}
</style>
  
  
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Import Transactions</h2>
      </div>
    </section>
    <form:form id="upload_form" name="upload_form" method="post" enctype="multipart/form-data" modelAttribute="amexUploadFile" action="saveImports?${_csrf.parameterName}=${_csrf.token}">
    <div class="content-area clearfix">
    <div id="loading"></div> 
      <div class="clearfix form-area form-horizontal mt">
        <div class="minHeight row">

						  <div class="col-md-12 clearfix"><div class="row">
						  <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
						  <div class="row">
						  <div class="col-md-5">
							  <select class="chosen-select" id="financialInstituteName" name="financialInstituteName" field-name="Transaction Type" data-validation="required">
							  	<option value="">-- Select Transaction type --</option>
							  	<option value="American Exchange Credit Card">American Exchange Credit Card</option>
							  	<option value="Comerica Bank">Comerica Bank</option>
							  	<option value="Chase Bank">Chase Bank</option>
							  </select>
						  </div>
						  <div class="col-md-6">
						  <!-- Start date End date -->
							<div class="row">
							<div class="col-md-6">
							<input type="text" class="form-control input-sm calendar-img" data-validation="required" field-name="From Date" name="startDate" id="startDate" placeholder="From Date">
							</div>
							<div class="col-md-6">
							<input type="text" class="form-control input-sm calendar-img" data-validation="required" field-name="To Date" name="endDate" id="endDate" placeholder="To Date">
							</div>
							  </div>
							<!-- Start date End date -->
						  </div>
						  </div>
						  
						  

          <!-- <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8"> -->
          
			
			<div class="dropzone import-zone mt">
			  <div class="clearfix">
			  <div class="btn-group pull-right">
			  <button class="btn btn-sm btn-primary" id="upload_button" type="submit" onclick = "check();uploadSave();checkFile();">
				<i aria-hidden="true" class="fa fa-upload"></i>&nbsp;&nbsp;&nbsp;Import
			  </button>
			  <button class="btn btn-sm btn-danger" type="reset" onClick="javascript:location.href = '<c:url value='/usersList'/>'">
				<i aria-hidden="true" class="fa fa-times"></i>&nbsp;&nbsp;&nbsp;Cancel
			  </button>
			  </div>
			  </div>
			<div class="file-upload clearfix mt text-center" id="fileDiv" onclick = "check();">
		<div class="col-md-12" id="clientNameDiv">
			<label class="bg-default">
			<i aria-hidden="true" class="fa fa-paperclip"></i> <br/>
                <input type="file" name="file" field-name="File Upload" style="display: none;" id="uploadBtn" data-validation="required validate_importFile">
            </label>
        	</div>
        	
        	<div class="center-div">
        	<input id="uploadFile" placeholder="" disabled="disabled"/>
        	</div>
        	
        	
			</div>
			<!-- <p class="text-danger text-center mt hidden">Please Select the file &hellip;</p> -->
			<p class="text-danger text-center mt" id="fileExist"><form:errors path="file" /></p>
			<p class="text-danger text-center mt" id="success">${msg}</p> 
		   	 
		   	 <!--  Downloaded File show here -->
		   	   <div class="col-xs-3 alert alert-success fade in hidden" style="position:absolute; top:10px;" id="fileAttached">
				    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				    <div>File Holder</div>
				</div>
		   	 <!--  Downloaded File show here -->
		   	 
			</div>
		 </div>
		  <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
		  <div class="errInfo erro-file clearfix">
		   <p class="text-danger"><strong>Download Failed Transactions</strong></p>
		  <div id="errorFileDiv"  style="display: none;">
		      <c:if test="${!empty errorMessage}">
				   <div class="file-upload"> 
				  <a href="<c:url value='/downloadFailedTransactions/${fileName}'/>" onclick="errorMsgAppend();"> 
					<label class="btn btn-block">
					<i class="fa fa-download" aria-hidden="true"></i> <br>
		               ${errorMessage}
		            </label>
		            </a>
					</div>
		     </c:if>			
          </div>
         <!--  <div id="sucessMsg"><p class="text-success" ><strong>File downloaded successfully</strong></p></div> -->
          </div>
		  </div>
		  </div></div>
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
 
  <script>
$(document).ready(function(){
	document.getElementById("uploadFile").value = '';
	var errormsg = '${errorMessage}';
	$("#fileAttached").hide();
	if(errormsg){
		$("#errorFileDiv").show();
	}
});
</script> 

<script type="text/javascript">

document.getElementById("uploadBtn").onchange = function () {
    document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();;
};
</script>

<script>
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
        	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
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

<script language="javascript" type="text/javascript">
 function checkFile()
    {
     var fsize = $('#uploadBtn')[0].files[0].size;
     $('#clientNameDiv').find('p.jquery_form_error_message').remove();
     var res_field = document.upload_form.elements["uploadFile"].value;   
     var extension = res_field.substr(res_field.lastIndexOf('.') + 1).toLowerCase();
     var allowedExtensions = ['xls', 'xlsx', 'csv'];
     if (res_field.length > 0)
        {
             if (allowedExtensions.indexOf(extension) === -1) 
                {
                   $("input#uploadBtn").after("<p class='jquery_form_error_message'>");
               	   document.getElementById("uploadBtn").setAttribute('record-exist', 'yes');
               	   document.getElementById("uploadBtn").setAttribute('record-exist-errorMsg',"Invalid file Format. Only " + allowedExtensions.join(', ') + " are allowed.");
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
</script> 

<script>
function checkDates(){
	
	var fromDate = document.getElementById("startDate").value
	var toDate = document.getElementById("endDate").value
	var financialInstituteName = document.getElementById("financialInstituteName").value
	var sourceleadUrl = '${sourceleadUrl}';
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/validateTransDates?${_csrf.parameterName}=${_csrf.token}&fromDate="+fromDate+"&toDate="+toDate+"&financialInstituteName="+financialInstituteName, 	   
		    success: function(data) {
		    	
		    	if(data == 'Invalid'){
		    		$("input#uploadBtn").after("<p class='jquery_form_error_message'>");
	               	   document.getElementById("uploadBtn").setAttribute('record-exist', 'yes');
	               	   document.getElementById("uploadBtn").setAttribute('record-exist-errorMsg',"Duplicate Transaction Dates.");
		               	$("#upload_form").click(function() {
		        			if ($(this).validate(false, validationSettings)) {
		        			}
		        		});
	                   return false;
		    	}
		    }
	 });
	
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

<script>
  $( function() {
	  
	  $( "#startDate, #endDate" ).datepicker({
		  dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	      changeYear: true
	    });
	  
  } );
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 



<script type="text/javascript">
			// Chosen script
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
</script> 

   