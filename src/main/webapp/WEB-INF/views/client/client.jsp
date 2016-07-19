<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
 
  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Create Client</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Client</div>
    </section>
    <div class="content-area clearfix">
      <section class="action-btns clearfix">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
      <form:form id="client_form"  name="client_form" method="post">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <div class="form-group">
              <label class="col-md-3" for="Project Id">Client Name<span class="text-danger">*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="orgName" field-name="Client Name" data-validation="required" value="">
                <span class="hidden">Enter Client Name</span> 
                </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Client Country</label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="country" name="country" field-name="Client Country" value="">
                <span class="hidden">Client Country</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Client State</label>
              <div class="col-md-9">
                <input type="text" id="state" name="state" field-name="Client State" class="form-control"  value="">
                <span class="hidden">Client State</span> </div>
            </div>
            
            
             <div class="form-group">
              <label class="col-md-3">ClientCity</label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="city" name="city" field-name="Client City" value="">
                <span class="hidden">Client City</span> </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Projects</label>
              <div class="col-md-9">
                <div class="input-group">
                  <div class="input-group-btn">
                    <button class="btn btn-default" type="button"> <span class="badge" id="addTaskCount">0</button>
                    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addTask"> <i class="fa fa-plus" title="Add Tasks"></i> &nbsp;&nbsp;View / Add Projects </button>
                  </div>
                </div>
                <!-- Modal -->
                
                <div class="modal fade" id="addTask" role="dialog">
                  <div class="modal-dialog modal-lg"> 
                    <!-- Modal content-->
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add Project</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="modal-body-custom custom-table">
                          <table class="table table-bordered table-striped  clearfix" id="projectTaskTbl" cols="0">
                            <tr id="divTsRow0">
                              <th><input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1"/>Project Id</th>
                              <th>Project Name</th>
                              <th>Manager</th>
                              <th>Start Date</th>
                              <th>Action</th>
                            </tr>
                          </table>
                        </div>
                        <div class="form-horizontal row custom-form">
                          <div class="col-xs-12 col-lg-7">
                          <div class="form-group" id="ProjectIdDiv">
                            <label class="col-md-3">Project Id</label>
                            <div class="col-md-9"> 
                              <input type="text" value="" id="projectId" name="projectId" placeholder="Enter Project Id" class="form-control" onblur="validateProjectId();">
                            </div>
                          </div>
                          
                          <div class="form-group">
                            <label class="col-md-3">Project Name</label>
                            <div class="col-md-9"> 
                              <input type="text" value="" id="projectName" name="projectName" placeholder="Enter Project Name" class="form-control">
                            </div>
                          </div>
                          <div class="form-group">
              					<label class="col-md-3" for="Project Manager">Manager</label>
              						<div class="col-md-9">
             						 <div class="form-icon-min">
             						 <!-- <input id="projectManager" name="projectManager"/>  -->
               					 <select class="form-control select-bg" name="projectManager" id="projectManager">
                					  <option value="">-Select Manager-</option>
                                          <c:forEach var="option" items="${user_List}">
	                                      <option value="${option.id}">${option.firstName}</option>
	                              </c:forEach>
              					  </select>
                 		   <span class="btn btn-default formic1 addLevel">Add Level-II</span>
                  		  </div>
                  	  <span class="hidden">Select Project Manager</span>
              		  </div>
            		</div>
            		<div class="form-group level-second">
              			<label class="col-md-3" for="Level-II">Level-II</label>
              			<div class="col-md-9">
               				 <select class="form-control select-bg" name="level2Manager" >
              							<option value='' >-- Select Manager --</option>
	                			 	 <c:forEach var="option" items="${user_List}">
	                    				 <option value="${option.id}">${option.firstName}</option>
	                  				</c:forEach>
              				  </select>
               			 </div>
           			 </div>
                         <!--  <div class="form-group">
                            <label class="col-md-3">Manager</label>
                            <div class="col-md-9">
                              <input type="text" value="" id="projectManager" name="projectManager"  placeholder="Enter Manager Name" class="form-control">
                            </div>
                          </div> -->
                          
                          <div class="form-group">
                            <label class="col-md-3">Start Date</label>
                             <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                      <input type="text" class="form-control" value="" id="projectStartDate" name="projectStartDate" field-name="Start Date" class="form-control" >
		                    </div>
                           <!--  <div class="col-md-9">
                              <input type="text" value="" id="projectStartDate" name="projectStartDate"  placeholder="Enter Project Start Date" class="form-control">
                            </div> -->
                          </div>
                          
                         <!--  <div class="form-group">
                            <label class="col-md-3">End Date</label>
                             <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                      <input type="text" class="form-control" value="" id="projectEndDate" name="projectEndDate" field-name="Start Date" class="form-control" >
		                    </div>
                            <div class="col-md-9">
                              <input type="text" value="" id="projectEndDate" name="projectEndDate"  placeholder="Enter Project End Date" class="form-control">
                            </div>
                          </div>
                           -->
                          </div>
                          <div class="col-xs-12 col-lg-5">
                          <a href="javascript:void(0)" class="btn btn-primary" id="save_task" onclick="addTasks();" data-target="addTask" > <i class="fa fa-plus"></i><span id="addTaskText"> Add Project</span></a>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Save</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Modal contente Ends--> 
                
              </div>
            </div>

            
          </div>
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
           <div class="form-group "  id="companyWebsiteDiv">
              <label class="col-md-3">Website</label>
              <div class="col-md-9">
                 <input type="text" class="form-control" id="webSite" name="webSite" field-name="Website"  data-validation="required" value="">
                <span class="hidden">Website</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3" for="Project Manager">Email</label>
              <div class="col-md-9">
				<input type="text" id="email" name="email" field-name="Website" value="" class="form-control">
                    <span class="hidden">Enter Email</span>
                </div>
            </div>
            
			   <div class="form-group">
              <label class="col-md-3">Mobile Number</label>
              <div class="col-md-9">
			  <input type="text" class="form-control" id="phone" name="phone" ield-name="Mobile Number" value="">
                <span class="hidden">Mobile Number</span> </div>
              </div>
			  
			  
			    </div>
        </div>
        
        <div class="form-footer clearfix row">
          <div class="col-lg-12">
            <div class="pull-right">
               <input type="button" value="Save" id="save_client" onclick="saveClient();"  class="btn btn-primary">
               <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/clientDashboard'/>'"  class="btn btn-default ml">
            </div>
          </div>
        </div>
        </form:form>
      </div>
    
    </div>
  </section>
  <!-- Content Area Ends--> 
  
  <!-- Footer Area -->
 <jsp:include page="../masterFooter.jsp" />
  <!-- Footer Area --> 


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
<script type="text/javascript">
	$(document).ready(function(e) {
		$('.side-menu').click(function(){
			$('.side-bar').toggleClass("side-bar-active");
			$('.user-panel').toggleClass("user-panel-active");
			$('.content-wraper').toggleClass("content-wraper-active");
			});
		$('.srch-btn-xs').click(function(){
		  $(".search-area").toggleClass("search-area-active");
	    });
		$('html').click(function(){
		  $(".search-area").removeClass("search-area-active");
	    });
		$('.srch-btn-xs, .search-area-txt').click(function(e){
		  e.stopPropagation();
	    });
		$('.login-user').click(function(){
		 $('.user-details').toggleClass('user-details-active');
	    });
		
		$("#notification").click(function(){
			$('.note-area').toggleClass('note-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.alert-area').removeClass('alert-area-active');
		});
		$("#alert").click(function(){
			$('.alert-area').toggleClass('alert-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.note-area').removeClass('note-area-active');
			$('.qlinks-area').removeClass('qlinks-area-active');
		});
		$("#qlinks").click(function(){
			$('.qlinks-area').toggleClass('qlinks-area-active');
			$('.alert-area').removeClass('alert-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.note-area').removeClass('note-area-active');
		});
		$('html').click(function(){
		  $('.user-details').removeClass("user-details-active");
		  $('.setting-area').removeClass('setting-area-active');
		  $('.note-area').removeClass('note-area-active');
		  $('.alert-area').removeClass('alert-area-active');
		  $('.qlinks-area').removeClass('qlinks-area-active');
	    });
		$('.header-nav .navbar, .custom-navbar, .user-details').click(function(e){
		  e.stopPropagation();
	    });
		$('.settings').click(function(){
		$('.setting-area').toggleClass('setting-area-active');
		$('.note-area').removeClass('note-area-active');
		$('.user-details').removeClass('user-details-active');
		$('.alert-area').removeClass('alert-area-active');
	    });
		$(".collpase-div").click(function(){
			$(".toggle-div").slideToggle(800);
		});
		$(".faclose").click(function(){
			$(".remove-div").addClass('hidden');
		});
		$(".bell").click(function(){
				$(this).toggleClass('bell-active');			
			});
       $(".addLevel").click(function(){
		   $(".level-second").toggleClass("level-second-active");
		});
    });   
   </script>
   


<script type="text/javascript">
 function saveClient() {
 var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
   }; 
 if ($('#client_form').validate(false, validationSettings)){
	 var frm = $('#client_form').serializeFormJSON();
     var con = JSON.stringify(frm);
     var sourceleadUrl ='${sourceleadUrl}';
     $.ajax({
	    	type: "POST",
	    	url: "http://localhost:8080/sourcelead/saveClient?${_csrf.parameterName}=${_csrf.token}&json="+con, 
	        success: function(result) {
	        	if(result == 'clientDashboard'){
	        		location.replace("http://localhost:8080/sourcelead/clientDashboard");  
	        	}
		        
	        },
	     });
          return true;
		 }else{
				return false;
		 }
    }
    $('body').on('blur', '#client_form', function() {
		$("#client_form").showHelpOnFocus();
		$("#client_form").validateOnBlur(false, validationSettings);  
	});
</script>




<!-- =============================== AddTask Divs  ============================ -->

       <div id="divTaskTd1" style="display:none; float:left;">
            <input type="hidden" id="REPLACEROWIDprojectId" name="REPLACEROWIDprojectId" value="PROJECTIDVALUE"/><span id="REPLACEROWIDprojectIdValue">PROJECTIDVALUE</span> 
      </div>
      
       <div id="divTaskTd2" style="display:none; float:left;">
            <input type="hidden" id="REPLACEROWIDprojectName" name="REPLACEROWIDprojectName" value="TASKNAMEVALUE"/><span id="REPLACEROWIDprojectNameValue">TASKNAMEVALUE</span> 
      </div>
       <div id="divTaskTd3" style="display:none; float:left;">
        <input type="hidden" id="REPLACEROWIDprojectManager" name="REPLACEROWIDprojectManager" value="TASKDESCRIPTIONVALUE"/><span id="REPLACEROWIDprojectManagerValue">TASKDESCRIPTIONVALUE</span> 
      </div>
      
      <div id="divTaskTd4" style="display:none; float:left;">
        <input type="hidden" id="REPLACEROWIDprojectStartDate" name="REPLACEROWIDprojectStartDate" value="PROJECTSTARTDATEVALUE"/><span id="REPLACEROWIDprojectStartDateValue">PROJECTSTARTDATEVALUE</span> 
      </div>
      
      <!-- <div id="divTaskTd5" style="display:none; float:left;">
        <input type="hidden" id="REPLACEROWIDprojectEndDate" name="REPLACEROWIDprojectEndDate" value="PROJECTENDDATEVALUE"/><span id="REPLACEROWIDprojectEndDateValue">PROJECTENDDATEVALUE</span> 
      </div> -->
     
       <div id="divTaskTd5" style="display:none; float:left;">
            <span><a title="Edit" href="javascript:void(0)" onclick="editTask(REPLACEROWID)"><i class="fa fa-edit"></i></a></span>&nbsp;&nbsp;&nbsp;
            <span><a title="Remove" href="javascript:void(0)" onclick="removeRow(REPLACEROWID)"><i class="fa fa-close"></i></a></span>
      </div>
  <!-- =============================== AddTask Divs  ============================ -->     
<!-- Add Task Script start -->
<script type="text/javascript">
function addTasks(){
    	var tbl = document.getElementById("projectTaskTbl");
    	var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
    	$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
    	$("#addTaskCount").html(maxRowNum);
    	var row = tbl.insertRow(maxRowNum);
    	row.setAttribute("id", "divTsRow" + maxRowNum);
	
    	var projectId = $('#projectId').val();
		var projectName = $('#projectName').val();
	    var projectManager = $('#projectManager').val();  
	    var projectStartDate = $('#projectStartDate').val();
	  /*   var projectEndDate = $('#projectEndDate').val(); */
			
		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd1Str = divTsTd1Str.replace(/PROJECTIDVALUE/g, (projectId));
		cell0.innerHTML = divTsTd1Str;
		
		
		var cell1 = row.insertCell(1);
		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/TASKNAMEVALUE/g, (projectName));
		cell1.innerHTML = divTsTd2Str;
		
		
		
		var cell1 = row.insertCell(2);
		var divTsTd2Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONVALUE/g, (projectManager));
		cell1.innerHTML = divTsTd2Str;
		
		var cell1 = row.insertCell(3);
		var divTsTd2Str = document.getElementById("divTaskTd4").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/PROJECTSTARTDATEVALUE/g, (projectStartDate));
		cell1.innerHTML = divTsTd2Str;
		
	/* 	var cell1 = row.insertCell(4);
		var divTsTd2Str = document.getElementById("divTaskTd5").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/PROJECTENDDATEVALUE/g, (projectEndDate));
		cell1.innerHTML = divTsTd2Str; */
		
		
		 var cell2 = row.insertCell(4);
		var divTsTd3Str = document.getElementById("divTaskTd5").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell2.innerHTML = divTsTd3Str; 
		
		$("#projectId").val('');
		$("#projectName").val('');
		$("#projectManager").val('');
		$("#projectStartDate").val('');
		/* $("#projectEndDate").val(''); */
}
</script>
<script type="text/javascript">
 function removeRow(rowId) {
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		  var r = document.getElementById('projectTaskTbl').rows[rowId];
             var count = 2*(r.cells.length);
			    for(i=0;i<=count;i++){
			        r.deleteCell(0);
			    }
	}
 
</script>
<script type="text/javascript">
 function editTask(rowId) {
	 var updateProjectId = $("#"+rowId+"projectId").val(); 
	 var updateTaskName = $("#"+rowId+"projectName").val(); 
	 var updateTaskDescription = $("#"+rowId+"projectManager").val(); 
	 var updateProjectStartDate = $("#"+rowId+"projectStartDate").val(); 
	/*  var updateProjectEndDate = $("#"+rowId+"projectEndDate").val();  */
	 
	 $("#projectId").val('');
	 $("#projectName").val('');
	 $("#projectManager").val('');
	 $("#projectStartDate").val('');
	 $("#projectEndDate").val('');
	 $("#addTaskText").html('Update Task');
	 $("#projectId").val(updateProjectId);
	 $("#projectName").val(updateTaskName);
	 $("#projectManager").val(updateTaskDescription);
	 $("#projectStartDate").val(updateProjectStartDate);
	/*  $("#projectEndDate").val(updateProjectEndDate); */
	 $("#taskId").val(rowId);
	 $("#save_task").removeAttr('onclick');
	 $("#save_task").attr('onclick','updateTask('+rowId+');');
  }
 
</script>

<script type="text/javascript">
 function updateTask(rowId) {
	 /* $("#"+rowId+"projectManager").val($("#projectManager").val()); */
	 $("#"+rowId+"projectIdValue").html($("#projectId").val());
	 
	 $("#"+rowId+"projectNameValue").html($("#projectName").val()); 
	 
	 $("#"+rowId+"projectManagerValue").html($("#projectManager").val());
	 
	 $("#"+rowId+"projectStartDateValue").html($("#projectStartDate").val()); 
	 
	 $("#"+rowId+"projectEndDateValue").html($("#projectEndDate").val());
	 $("#projectId").val('');
	 $("#projectName").val('');
	 $("#projectManager").val('');
	 $("#projectStartDate").val('');
	 $("#projectEndDate").val('');
	 $("#addTaskText").html('Add Task');
	 $("#save_task").removeAttr('onclick');
	 $("#save_task").attr('onclick','addTasks();');
	 
 }
 </script>
<script> 
$(document).ready(function(){
	 $("#maxTsRowNum").val('1');
	document.getElementById("project_form").reset();
});
</script>



<script type="text/javascript">
 function validateProjectId() {
	  var ProjectSequeneNumber = document.getElementById("projectId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
	 if(ProjectSequeneNumber != null && ProjectSequeneNumber != ""){
		 $.ajax({
		    	type: "POST",
		        url: "http://localhost:8080/sourcelead/validateProjectSequeneNumberId?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="+ProjectSequeneNumber, 
		        success: function(data) {
		        	if(data == true){
						  $("input#projectId").after("<p class='jquery_form_error_message'>"+ProjectSequeneNumber+" is Already Existed</span>");
						  document.getElementById("projectId").setAttribute('record-exist','yes');
						  document.getElementById("projectId").setAttribute('record-exist-errorMsg',projectId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#projectId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#ProjectIdDiv').find('p.jquery_form_error_message').remove(); 
		        	} 
		        },
		    });
	 } 
 }
 </script> 

<!-- Add Task Script Ends -->

