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
        <h2>Import Users</h2>
      </div>
      <div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo; <a href="usersList">Users List</a> &raquo; Users Import </div>
    </section>
    <form:form id="upload_form" name="upload_form" method="post" enctype="multipart/form-data" modelAttribute="uploadedFile" action="fileUpload?${_csrf.parameterName}=${_csrf.token}">
    <div class="content-area clearfix">
    <div id="loading"></div> 
      <section class="action-btns clearfix">
        <ul class="pull-right list-unstyled list-inline">
          <li><a class="faclose" href="#"><i class="fa fa-close" aria-hidden="true"></i></a></li>
          <li><a class="collpase-div" href="#"><i class="fa fa-angle-down" aria-hidden="true"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
        <div class="row minHeight">
          <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
		  <!-- Download File button -->
		  <div class="clearfix mb">
			 <a href="<c:url value='/downloadCreateUserTemplate'/>"> <button class="btn btn-info btn-block" type="button">
					<i class="fa fa-download"></i>&nbsp;&nbsp;Download Template
			</button></a>
			</div>
			<!-- Attach File button -->
			
			<div class="dropzone">
			  <div class="clearfix">
			  <div class="btn-group pull-right">
			  <button class="btn btn-sm btn-primary" id="upload_button" type="submit" onclick = "check();uploadSave();checkFile()" ">
				<i aria-hidden="true" class="fa fa-upload"></i>&nbsp;&nbsp;&nbsp;Import
			  </button>
			  <button class="btn btn-sm btn-danger" type="reset" onClick="javascript:location.href = '<c:url value='/usersList'/>'">
				<i aria-hidden="true" class="fa fa-times"></i>&nbsp;&nbsp;&nbsp;Cancel
			  </button>
			  </div>
			  </div>
			<div class="file-upload clearfix mt text-center" id="fileDiv" onclick = "check();">
		<div class="col-md-9" id="clientNameDiv">
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
			
			<!-- =================== -->
			 <div class="bgInfo bg-info clearfix">
			 <span class="pull-left"><i class="fa fa-question" aria-hidden="true"></i></span>
			  <span class="info-msg pull-left">
			  <p>
			  According this template, the Users need to <span class="text-danger"><strong>Import Required Fields</strong></span></p>
			  </p>
			  <p>
			  <label><i class="fa fa-user"></i>&nbsp;&nbsp;First Name</label>
			  <label><i class="fa fa-envelope"></i>&nbsp;&nbsp;Email</label>
			  <label><i class="fa fa-check"></i>&nbsp;&nbsp;Role</label>
			  </p>
			  </span>
            </div>
            <div class="panel-body checkbox div-checkbox">
				  <label><input type="checkbox" checked="checked" value="checked" name="email"></label><span>Send Emails</span>
		    </div>
          </div>
		  <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
		  <div class="errInfo clearfix">
		  <div id="errorFileDiv"  style="display: none;">
		  <p class="text-danger"><strong>Error File Dowload</strong></p>
		      <c:if test="${!empty errorMessage}">
				   <div class="file-upload"> 
				  <a href="<c:url value='/download/${fileName}'/>" onclick="errorMsgAppend();"> 
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
	var errormsg = '${errorMessage}';
	$("#fileAttached").hide();
	if(errormsg){
		$("#errorFileDiv").show();
	}
});
</script>

<script type="text/javascript">

document.getElementById("uploadBtn").onchange = function () {
    document.getElementById("uploadFile").value = this.value;
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
     var fsize = $('#uploadBtn')[0].files[0].size;
     $('#clientNameDiv').find('p.jquery_form_error_message').remove();
     /* $("#upload_button").removeAttr("disabled"); */
     if (fsize>50000)
     {
    	 $("input#uploadBtn").after("<p class='jquery_form_error_message'>");
			document.getElementById("uploadBtn").setAttribute('record-exist', 'yes');
			document.getElementById("uploadBtn").setAttribute('record-exist-errorMsg',"  File size should be below 500 KB");
         return false;
     }
     $	//New WebSite class="form-control"
		//$("#save_client").removeAttr("disabled");
		$('input#uploadBtn').removeAttr("class record-exist record-exist-errormsg");
		$('input#uploadBtn').attr('class','valid form-control');
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
		$("#upload_form").click(function() {
			if ($(this).validate(false, validationSettings)) {
			}
		});
	   
    }
</script> 

  <!-- Footer Area -->
  <footer class="footer-area clearfix">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div>
  </footer>
  <!-- Footer Area -->

   