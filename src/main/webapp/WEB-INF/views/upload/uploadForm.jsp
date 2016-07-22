<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
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
  <!-- Content Area -->
  <%-- <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>File Upload</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Users Dashboard</div>
    </section>
   
		<!--  <h2>Being Java Guys | Hello World</h2>
		<h3>Please select a file to upload !</h3> -->
		<span><a href="<c:url value='/download'/>">${error}</a></span> 
		<span>${msg}</span> 
		<br />
		<form:form method="post" enctype="multipart/form-data" modelAttribute="uploadedFile" action="fileUpload.htm?${_csrf.parameterName}=${_csrf.token}">
			<table>
				<tr>
					<td>Upload File:&nbsp;</td>
					<td><input type="file" name="file" />
					</td>
					<td style="color: red; font-style: italic;">
					<form:errors path="file" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="Upload" />
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</form:form>

  </section>
      <!-- Content Area Ends--> 

      <!-- Footer Area -->
      <footer class="footer-area clearfix">
        <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
        <div class="powered-by"> Powered by: businessintelli.com </div>
      </footer>
      <!-- Footer Area -->  --%>
  
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Import Users</h2>
      </div>
      <div class="pull-right brud-crum">Home » Users » Bulk Import</div>
    </section>
    <form:form method="post" enctype="multipart/form-data" modelAttribute="uploadedFile" action="fileUpload?${_csrf.parameterName}=${_csrf.token}">
    <div class="content-area clearfix">
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
			  <button class="btn btn-sm btn-primary" type="submit" onclick = "check();">
				<i aria-hidden="true" class="fa fa-upload"></i>&nbsp;&nbsp;&nbsp;Import
			  </button>
			  <button class="btn btn-sm btn-danger" type="reset" onClick="javascript:location.href = '<c:url value='/usersList'/>'">
				<i aria-hidden="true" class="fa fa-times"></i>&nbsp;&nbsp;&nbsp;Cancel
			  </button>
			  </div>
			  </div>
			<div class="file-upload clearfix mt text-center" onclick = "check();">
		
			<label class="bg-default">
			<i aria-hidden="true" class="fa fa-paperclip"></i> <br/>
                <input type="file" name="file" style="display: none;" id="uploadBtn">
            </label>
        	
        	
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
            <div class="panel-body checkbox">
				  <label><input type="checkbox" value="checked" name="email"></label>Send Emails
				  
		    </div>
          </div>
		  <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
		  <div class="errInfo clearfix">
		  <div id="errorFileDiv"  style="display: none;">
		  <p class="text-danger"><strong>Error File Dowload</strong></p>
		   <div class="file-upload"> 
		  <a href="<c:url value='/download'/>" onclick="errorMsgAppend();"> 
			<label class="btn btn-block">
			<i class="fa fa-download" aria-hidden="true"></i> <br>
               ${errorMessage}
            </label>
            </a>
			</div>
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

  <!-- Footer Area -->
  <footer class="footer-area clearfix">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div>
  </footer>
  <!-- Footer Area -->

   