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
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
 
 
 <script type="text/javascript">
 function validateProjectSequeneNumber() {
	 var ProjectSequeneNumber = document.getElementById("ProjectSequeneNumber").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
	 if(ProjectSequeneNumber != null && ProjectSequeneNumber != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="+ProjectSequeneNumber, 
		        success: function(data) {
		        	if(data == true){
						  $("input#ProjectSequeneNumber").after("<p class='jquery_form_error_message'>"+ProjectSequeneNumber+" is Already Existed</span>");
						  document.getElementById("ProjectSequeneNumber").setAttribute('record-exist','yes');
						  document.getElementById("ProjectSequeneNumber").setAttribute('record-exist-errorMsg',ProjectSequeneNumber+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#ProjectSequeneNumber').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#ProjectIdDiv').find('p.jquery_form_error_message').remove(); 
		        	} 
		        },
		    });
	 }
 }
 </script> 


  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Create Project</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Form</div>
    </section>
    <div class="content-area clearfix">
      <section class="action-btns clearfix">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
      <form:form id="project_form"  name="project_form" method="post">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            
            <div class="form-group" id="ProjectIdDiv">
                  <label class="col-lg-3">Project Id</label>
                  <div class="col-lg-9">
                    <input type="text" value="" id="ProjectSequeneNumber" name="ProjectSequeneNumber" class="form-control" onblur="validateProjectSequeneNumber();">
                  </div>
             </div>
                
            <div class="form-group">
              <label class="col-md-3"> Project Name <span class="text-danger">*</span> </label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="ProjectName" field-name="Project Name" data-validation="required" value="">
                <span class="hidden">Project Name</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3"> Project Description </label>
              <div class="col-md-9">
                <textarea class="form-control" rows="3" name="ProjectDescription"></textarea>
                <span class="hidden">Project Description</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3"> Date </label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-lg-6">
                    <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                      <input type="text" class="form-control" value="" id="StartDate" name="StartDate" field-name="Start Date" data-validation="required">
                    </div>
                  </div>
                  <div class="col-lg-6">
                    <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                      <input type="text" class="form-control" value="" id="EndDate" name="EndDate">
                    </div>
                  </div>
                  <span class="hidden">Enter Project Id</span> </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Tasks</label>
              <div class="col-md-9">
                <div class="input-group">
                  <div class="input-group-btn">
                    <button class="btn btn-default" type="button"> <span class="badge" id="addTaskCount">0</button>
                    <button class="btn btn-primary" id ="david" type="button" onclick="addSourceleadTasks();" data-toggle="modal" data-target="#addTask"> <i class="fa fa-plus" title="Add Tasks"></i> &nbsp;&nbsp;View / Add Tasks </button>
                  </div>
                </div>
                <!-- Modal -->
                <div class="modal fade" id="addTask" role="dialog">
                  <div class="modal-dialog modal-lg"> 
                    
                    <!-- Modal content-->
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add Tasks</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="modal-body-custom custom-table">
                          <table class="table table-bordered table-striped  clearfix" id="projectTaskTbl" cols="0">
                            <tr id="divTsRow0">
                              <th><input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1"/>Task Name</th>
                              <th>Description</th>
                              <th>Action</th>
                            </tr>
                            
                           <!--  <tr>
                              <td>Task Name</td>
                              <td>The home page has a section in the middle of the page that needs to be to the right.</td>
                              <td>
                              <span><a title="Edit" href="javasript:void(0)"><i class="fa fa-edit"></i></a></span>
                              <span><a title="Remove" href="javasript:void(0)"><i class="fa fa-close"></i></a></span>
                              </td>
                            </tr> -->
                           
                          </table>
                        </div>
                        <div class="form-horizontal row custom-form">
                          <div class="col-xs-12 col-lg-7">
                          <div class="form-group">
                            <label class="col-md-3">Task Name</label>
                            <div class="col-md-9"> 
                              <input type="text" value="" id="taskName" name="taskName" placeholder="Enter Task Name" class="form-control">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-3">Description</label>
                            <div class="col-md-9">
                              <textarea class="form-control" placeholder="Enter Task Description" id="taskDescription" name="taskDescription" rows="2"></textarea>
                            </div>
                          </div>
                          </div>
                          <div class="col-xs-12 col-lg-5">
                          <a href="javascript:void(0)" class="btn btn-primary" id="save_task" onclick="addTasks();" data-target="addTask" > <i class="fa fa-plus"></i><span id="addTaskText"> Add Task</span></a>
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
             <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9">
                <div class="checkbox">
                  <label>
                    <input type="checkbox" value="">
                    Mail to Project Manager</label>
                </div>
                <span class="hidden">Select Check Box</span> </div>
            </div>
          </div>
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <div class="form-group">
              <label class="col-md-3" for="Project Manager">Manager <span class="text-danger">*</span></label>
              <div class="col-md-9">
              <div class="form-icon-min">
                <select class="form-control select-bg" name="level1Manager" field-name="Select Manager" data-validation="required">
                  <option  value="">-- Select Manager --</option>
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
                <select class="form-control select-bg" name="level2Manager">
              		<option value='' >-- Select Manager --</option>
	                  <c:forEach var="option" items="${user_List}">
	                     <option value="${option.id}">${option.firstName}</option>
	                  </c:forEach>
                </select>
                </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Client</label>
              <div class="col-md-9">
                <div class="form-icon">
                  <select class="form-control select-bg" name="selectClient" id="addClientId">
                  <option value=''>-- Select Client --</option>
                  <c:forEach var="option" items="${add_clientList}">
  						<option value="${option.id}">${option.orgName}</option>
				  </c:forEach>
                  </select>
                  <i class="fa fa-plus btn btn-default formic1" data-toggle="modal" onclick="flushProject();" data-target="#addClient"></i> </div>
                <span class="hidden">Enter Project Id</span>
                </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Assign Team</label>
              
              <div id="assignTeamSavingDiv" style="display:none;">
              </div>
              <input type="hidden" name="team" value=""/>
              
              <div class="col-md-9">
                <div class="form-icon"> <span class="icon-inside"> <i class="fa fa-search-plus"></i>
                  <input type="text" value="Select Team" class="form-control" data-target="#assignTeam" data-toggle="modal" onclick="getTeamMembers();flushEmployee();">
                  </span> <span data-target="#addTeam" data-toggle="modal"> <i class="fa fa-plus btn btn-default formic1" onclick="flushEmployee();" ></i> </span> </div>
                <span class="hidden">Select Team</span> 
                <!-- Modal content Assign Team-->
                <div role="dialog" id="assignTeam" class="modal fade">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Assign Employees</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal row custom-form">
                          <div class="col-xs-12 col-md-12 col-lg-12">
                            <div class="input-group">
                              <input type="search" class="form-control" value="">
                              <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>
                          </div>
                        </div>
                        <div class="modal-body-custom mt clearfix" id="employeesList"></div>
                      </div>
                      <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-primary" type="button" onclick="assignEmployee();">Assign</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Assign Team modal popup ends -->
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3" for="Project Id">Status</label>
              <div class="col-md-9">
                <select class="form-control select-bg" name="Satatus">
                  <option>Started</option>
                  <option>In-Progress</option>
                  <option>Completed</option>
                  <option>On-Hold</option>
                </select>
                <span class="hidden">Select Status</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9">
                <div class="checkbox">
                  <label>
                    <input type="checkbox" value="">
                    Mail to Team Members</label>
                </div>
                <span class="hidden">Select Check Box</span> </div>
            </div>
          </div>
        </div>
        <div class="form-footer clearfix row">
          <div class="col-lg-12">
            <div class="pull-right">
              <input type="button" value="Save" id="save_project" onclick="saveProject();"  class="btn btn-primary">
              <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/projectDashboard'/>'"  class="btn btn-default ml">
              
            </div>
          </div>
        </div>
        </form:form>
      </div>
    </div>
  </section>
  <!-- Content Area Ends-->  
  
  
  
  
  <!-- Modal content Add Team-->
             <div role="dialog" id="addTeam" class="modal fade">
               <div class="modal-dialog">
               <form:form id="addEmployee_form"  name="addEmployee_form" method="post">
                 <div class="modal-content" id="newEmployee">
                   <div class="modal-header">
                     <button data-dismiss="modal" class="close" type="button">×</button>
                     <h4 class="modal-title">Add Employees</h4>
                   </div>
                   <div class="modal-body clearfix">
                     <div class="form-horizontal custom-form clearfix">
                       <div class="col-xs-12 col-md-12 col-lg-12">
                         <div class="form-group">
                         <label class="col-lg-3">Frist Name<span class="text-danger">*</span></label>
                         <div class="col-lg-9">
                           <input type="text" class="form-control" value="" name="employeeFirstName" id="employeeFirstName" field-name="Frist Name" data-validation="required">
                           </div>
                         </div>
                         <div class="form-group">
                         <label class="col-lg-3">Last Name</label>
                         <div class="col-lg-9">
                           <input type="text" class="form-control" value="" name="employeeLastName" id="employeeLastName">
                           </div>
                         </div>
                         <div class="form-group" id="EmailIdDiv">
                         <label class="col-lg-3">Email <span class="text-danger">*</span></label>
                         <div class="col-lg-9">
                           <input type="text" class="form-control" value="" name="EmailId" id="EmailId" field-name="Email" data-validation="required validate_email" onblur="emailVerification();">
                           </div>
                         </div>
                         <div class="form-group">
                         <label class="col-lg-3">Invoice Frequency<span class="text-danger">*</span></label>
                         <div class="col-lg-9">
                           <select class="form-control" name="invoiceFrequency" field-name="Invoice Frequency" data-validation="required">
                           <option value="">--Select-Invoice--</option>
                           	<option value="1">Invoice-1</option>
                             <option value="2">Invoice-2</option>
                             <option value="3">Invoice-3</option>
                           </select>
                           </div>
                         </div>
                         <div class="form-group">
                         <label class="col-lg-3"></label>
                         <div class="col-lg-9">
                           <div class="checkbox">
                           <label>
                             <input type="checkbox" value="" checked>
                             Send Invitation</label>
            					 </div>
                           </div>
                         </div>
                       </div>
                     </div>
                   </div>
                   <div class="modal-footer">
                   	<button type="button" id="add_employee" class="btn btn-primary" onclick="addEmployee();">Save</button>
                     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                   </div>
                 </div>
                 </form:form>
               </div>
             </div>
 <!-- Add Team modal popup ends --> 
  
  <!-- Add Client Model DialogBox Start-->
           <div class="modal fade" id="addClient" role="dialog">
				  <div class="modal-dialog">
				  <form:form id="addClient_form"  name="addClient_form" method="post">
				    <div class="modal-content" id="newClient">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal">×</button>
				        <h4 class="modal-title">Add Client</h4>
				      </div>
				      <div class="modal-body clearfix">
				        <div class="form-horizontal custom-form clearfix">
				          <div class="col-xs-12 col-md-12 col-lg-12">
				            <div class="form-group">
				              <label class="col-lg-3">Client Name <span class="text-danger">*</span></label>
				              <div class="col-lg-9">
				                 <input type="text" id="orgName" name="orgName" value="" class="form-control" field-name="Client Name" data-validation="required">
				              </div>
				            </div>
				            <div class="form-group" id="companyWebsiteDiv">
				              <label class="col-lg-3">Client Website</label>
				              <div class="col-lg-9">
				                <input type="text"   class="form-control"  id="companyWebsite" name="companyWebsite" value="" class="form-control" onblur="validateWebsite();"  field-name="Client Website" data-validation="required">
				              </div>
				            </div>
				          </div>
				        </div>
				      </div>
				      <div class="modal-footer">
				        <button type="button" id="save_client" class="btn btn-primary" onclick = "addclient();">Save</button>
				        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				      </div>
				    </div>
				    </form:form>
				  </div>
		  </div>
				
  <!-- Add Client Model DialogBox ends-->
  
<jsp:include page="../masterFooter.jsp" />
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

<!-- =============================== AddTask Divs  ============================ -->
       <div id="divTaskTd1" style="display:none; float:left;">
            <input type="hidden" id="REPLACEROWIDtaskName" name="REPLACEROWIDtaskName" value="TASKNAMEVALUE"/><span id="REPLACEROWIDtaskNameValue">TASKNAMEVALUE</span> 
      </div>
       <div id="divTaskTd2" style="display:none; float:left;">
        <input type="hidden" id="REPLACEROWIDtaskDescription" name="REPLACEROWIDtaskDescription" value="TASKDESCRIPTIONVALUE"/><span id="REPLACEROWIDtaskDescriptionValue">TASKDESCRIPTIONVALUE</span> 
      </div>
       <div id="divTaskTd3" style="display:none; float:left;">
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
	
		var taskName = $('#taskName').val();
	    var taskDescription = $('#taskDescription').val();
			
		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd1Str = divTsTd1Str.replace(/TASKNAMEVALUE/g, (taskName));
		cell0.innerHTML = divTsTd1Str;
		
		
		var cell1 = row.insertCell(1);
		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONVALUE/g, (taskDescription));
		cell1.innerHTML = divTsTd2Str;
		
		
		var cell2 = row.insertCell(2);
		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell2.innerHTML = divTsTd3Str;
		
		$("#taskName").val('');
		$("#taskDescription").val('');
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
	 var updateTaskName = $("#"+rowId+"taskName").val(); 
	 var updateTaskDescription = $("#"+rowId+"taskDescription").val(); 
	 $("#taskName").val('');
	 $("#taskDescription").val('');
	 $("#addTaskText").html('Update Task');
	 $("#taskName").val(updateTaskName);
	 $("#taskDescription").val(updateTaskDescription);
	 $("#taskId").val(rowId);
	 $("#save_task").removeAttr('onclick');
	 $("#save_task").attr('onclick','updateTask('+rowId+');');
  }
 
</script>

<script type="text/javascript">
 function updateTask(rowId) {
	 $("#"+rowId+"taskName").val($("#taskName").val()); 
	 $("#"+rowId+"taskDescription").val($("#taskDescription").val());
	 $("#"+rowId+"taskNameValue").html($("#taskName").val()); 
	 $("#"+rowId+"taskDescriptionValue").html($("#taskDescription").val());
	 $("#taskName").val('');
	 $("#taskDescription").val('');
	 $("#addTaskText").html('Add Task');
	 $("#save_task").removeAttr('onclick');
	 $("#save_task").attr('onclick','addTasks();');
	 
 }
 </script>
<script> 
$(document).ready(function(){
	 $("#maxTsRowNum").val('6');
	 addSourceleadTasks();
	document.getElementById("project_form").reset();
});
</script>

<script type="text/javascript">
function addSourceleadTasks(){
	
  var checkMaxTsRowNum =	 $("#maxTsRowNum").val();
	var tbl = document.getElementById("projectTaskTbl");
	var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
	$("#addTaskCount").html(maxRowNum);
	if(checkMaxTsRowNum == 6){
	
	 $.ajax({
	    	type: "GET",
	        url: "http://localhost:8080/sourcelead/sourceLeadTasks", 
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
	        		
	        			$('david[onclick]').removeAttr('onclick');
		        		var opt = json[i];
		        		var row = tbl.insertRow(opt.Id);
		        		row.setAttribute("id", "divTsRow" + opt.Id);
		        		var taskName =   opt.taskName;
		        	    var taskDescription = opt.taskDescription; 
		        		var cell0 = row.insertCell(0);
		        		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		        		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (opt.Id));
		        		divTsTd1Str = divTsTd1Str.replace(/TASKNAMEVALUE/g, (opt.taskName));
		        		cell0.innerHTML = divTsTd1Str;
		        		
		        		
		        		var cell1 = row.insertCell(1);
		        		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		        		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (opt.Id));
		        		divTsTd2Str = divTsTd2Str.replace(/TASKDESCRIPTIONVALUE/g, (opt.description));
		        		cell1.innerHTML = divTsTd2Str;
		        		
		        		
		        		var cell2 = row.insertCell(2);
		        		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		        		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (opt.Id));
		        		cell2.innerHTML = divTsTd3Str;
				
				}
	        	
	 		}
	  });
	}
	 
}
</script>


<!-- Add Task Script Ends -->

<script type="text/javascript">
 function addclient() {
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	   }; 
			 if ($('#addClient_form').validate(false, validationSettings)){
			     var frm = $('#addClient_form').serializeFormJSON();
			        var con = JSON.stringify(frm);
			        var sourceleadUrl ='${sourceleadUrl}';
			        $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/saveOrgRelations?json="+con, 
				        success: function(data) {
				        	var json = JSON.parse(data);
				        	var output = "";
				        	output = output + "<option value=''>-- Select Clinet --</option>"; 
				        	for (var i=0; i<json.length; i++)
							{
					        		var opt = json[i];
					        		output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
							}
				        	$("#addClientId").empty();
					        $("#addClientId").append(output);
				        },
				        error: function(e){
				            
				        }
				    });
			        
			        $('#save_client').attr('data-dismiss','modal');
					 $('#orgName').attr('');
					 $('#webSite').attr('');
			        return true;
			 	}else{
					return false;
			 }
	    }
    $('body').on('blur', '#addClient_form', function() {
		$("#addClient_form").showHelpOnFocus();
		$("#addClient_form").validateOnBlur(false, validationSettings);  
	});
 
</script>


<script type="text/javascript">
 function saveProject() {
 var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
   }; 
 if ($('#project_form').validate(false, validationSettings)){
	 var frm = $('#project_form').serializeFormJSON();
     var con = JSON.stringify(frm);
     var sourceleadUrl ='${sourceleadUrl}';
     $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/saveProject?${_csrf.parameterName}=${_csrf.token}&json="+con, 
	        success: function(result) {
	        	if(result == 'projectDashboard'){
	        		location.replace("http://localhost:8080/sourcelead/projectDashboard");  
	        	}
		        
	        },
	     });
          return true;
		 }else{
				return false;
		 }
    }
    $('body').on('blur', '#project_form', function() {
		$("#project_form").showHelpOnFocus();
		$("#project_form").validateOnBlur(false, validationSettings);  
	});
</script>


<script type="text/javascript">
function addEmployee() {
		 var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		   }; 
		 if ($('#addEmployee_form').validate(false, validationSettings)){
			var frm = $('#addEmployee_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			var sourceleadUrl ='${sourceleadUrl}';
				$.ajax({
				type: "POST",
				url: sourceleadUrl+"/addNewEmployee?${_csrf.parameterName}=${_csrf.token}&json="+con,
				success: function(data) {
			    	},
				});
				 return true;
				 }else{
				  return false;
				 }
			}
$('body').on('blur', '#addEmployee_form', function() {
	$("#addEmployee_form").showHelpOnFocus();;
	$("#addEmployee_form").validateOnBlur(false, validationSettings);  
});
</script>

<script type="text/javascript">
function getTeamMembers() {
	var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getEmployeesList?${_csrf.parameterName}=${_csrf.token}", 
        success: function(data) {
        	var json = JSON.parse(data);
        	var output = "";
        	
        	output = output+' <table cols="0" class="table table-bordered table-striped  clearfix">';
            output = output+' <tbody>';
            output = output+' <tr>';
            output = output+' <th>&nbsp;&nbsp;</th>';
            output = output+' <th>Name</th>';
            output = output+' <th>Email</th>';
            output = output+' <th>Role</th>';
            output = output+' <th>Start Date</th>';
            output = output+' <th>End Date</th>';
            output = output+' </tr>';
        	for (var i=0; i<json.length; i++)
			{
	        		var opt = json[i];
	        		output = output + "<tr>"+
                    "<td><input class='myCheckbox' id="+opt.id+" type='checkbox' name='team"+i+"' value="+opt.id+"></td>"+
                    "<td>"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"</td>"+
                    "<td>"+opt.user.email+"</td>"+
                    "<td>Senior Recruiter</td>"+
                    "<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>"+
                    "<input id='dummystartDate"+opt.id+"'   type='text' name='Date' value='' class='form-control'>"+
                    "</div></td>"+
                    "<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>"+
                    "<input id='dummyendDate"+opt.id+"' type='text' name='Date' value='' class='form-control'>"+
                    "</div></td>"+
                    "</tr>";
			}
        	output = output+' </tbody>';
	        output = output+' </table>';
	        
        	$("#employeesList").empty();
	        $("#employeesList").append(output);
        },
        error: function(e){
        }
    });
    $('#add_employee').attr('data-dismiss','modal');
	$('#employeeFirstName').val('');
	$('#employeeLastName').val('');
	$('#employeeEmail').val('');
}
</script>


<script>
function assignEmployee(){
	    var result = "";
	    var count = 1;
	    $(".myCheckbox").each(function(){
	        var localId =this.id;
	        if($(this).is(':checked')){
		        var startDate = $("#dummystartDate"+localId).val();
		        var endDate = $("#dummyendDate"+localId).val();
				result = result +"<input type='hidden'  name='employeeId"+count+"' id='employeeId"+count+"' value='"+localId+"' />";
				result = result +"<input type='hidden'  name='employeestartDate"+count+"' id='employeestartDate"+count+"' value='"+startDate+"' />";
				result = result +"<input type='hidden'  name='employeeendDate"+count+"' id='employeeendDate"+count+"' value='"+endDate+"' />";
				count++;
	        }
	    });
	    result = result +"<input type='hidden' name='employeeMaxCount' id='employeeMaxCount' value='"+count+"'/>";
	    $("#assignTeamSavingDiv").empty();
	    $("#assignTeamSavingDiv").append(result);
}
</script>


<script type="text/javascript">
 function emailVerification() {
	 var EmailId = document.getElementById("EmailId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getEmailVerification?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        	}
		        },
		    });
	 }
 }
 </script>   
 
 <script type="text/javascript">
function validateWebsite(){
     var website = document.getElementById("companyWebsite").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
	 if(website != null && website != ''){
		
		 $("#save_client").attr("disabled", "disabled");
		 $( "#save_client" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/ws/validateClientWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
		        		if(result == "success"){
		        			//existed website
		        			 $("input#companyWebsite").after("<p class='jquery_form_error_message'>"+website+" is Already Existed</span>");
						     document.getElementById("companyWebsite").setAttribute('record-exist','yes');
						     document.getElementById("companyWebsite").setAttribute('record-exist-errorMsg',website+' is Already Existed');
			        	}
			        	else{
			        		//New WebSite
			        		$("#save_client").removeAttr("disabled");
				    		$('input#companyWebsite').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$( "#save_client" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
	}
}
</script>
<script type="text/javascript">
function flushProject(){
	$('#orgName').val('');
	 $('#companyWebsite').val('');
	 $(".error").removeClass("error");
	 $('#newClient').find('p.jquery_form_error_message').remove();
}
</script>

<script type="text/javascript">
function flushEmployee(){
     $('#employeeFirstName').val('');
	 $('#EmailId').val('');
	 $(".error").removeClass("error");
	 $('#newEmployee').find('p.jquery_form_error_message').remove();
}
</script>

<script>
  $( function() {
	    
	  $('#StartDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#EndDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#StartDate").datepicker("option", "maxDate", dt);
	        }
	  });
	  
  } );
</script>
