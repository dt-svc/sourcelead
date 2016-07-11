<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
            <div class="form-group">
              <label class="col-md-3" for="Project Id"> Project Id </label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Project Id" value="">
                <span class="hidden">Enter Project Id</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3"> Project Name <span class="text-danger">*</span> </label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Project Name" value="">
                <span class="hidden">Project Name</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3"> Project Description </label>
              <div class="col-md-9">
                <textarea class="form-control" rows="3" name="Project Description"></textarea>
                <span class="hidden">Project Description</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3"> Date </label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-lg-6">
                    <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                      <input type="text" class="form-control" value="" name="Date">
                    </div>
                  </div>
                  <div class="col-lg-6">
                    <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                      <input type="text" class="form-control" value="" name="Date">
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
                    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addTask"> <i class="fa fa-plus" title="Add Tasks"></i> &nbsp;&nbsp;View / Add Tasks </button>
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
              <label class="col-md-3" for="Project Manager">Manager</label>
              <div class="col-md-9">
              <div class="form-icon-min">
                <select class="form-control select-bg">
                  <option>Level Manager</option>
                  <option>Manager-2</option>
                  <option>Manager-3</option>
                  <option>Manager-4</option>
                </select>
                    <span class="btn btn-default formic1 addLevel">Add Level-II</span>
                    </div>
                    <span class="hidden">Select Project Manager</span>
                </div>
            </div>
            <div class="form-group level-second">
              <label class="col-md-3" for="Level-II">Level-II</label>
              <div class="col-md-9">
                <select class="form-control select-bg">
                  <option>Level-II</option>
                  <option>Manager-2</option>
                  <option>Manager-3</option>
                  <option>Manager-4</option>
                </select>
                </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Client</label>
              <div class="col-md-9">
                <div class="form-icon">
                  <select class="form-control select-bg" id="addClientId">
                  <option>-- Select Client --</option>
                  <c:forEach var="option" items="${add_client}">
  						<option value="${option.id}"}>${option.orgName}</option>
				  </c:forEach>
                  </select>
                  <i class="fa fa-plus btn btn-default formic1" data-toggle="modal" data-target="#addClient"></i> </div>
                <span class="hidden">Enter Project Id</span>
				<div class="modal fade" id="addClient" role="dialog">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal">×</button>
				        <h4 class="modal-title">Add Client</h4>
				      </div>
				      <div class="modal-body clearfix">
				        <div class="form-horizontal custom-form clearfix">
				          <div class="col-xs-12 col-md-12 col-lg-12">
				            <div class="form-group">
				              <label class="col-lg-3">Client Name</label>
				              <div class="col-lg-9">
				                 <input type="text" id="orgName" name="orgName" value="" class="form-control">
				              </div>
				            </div>
				            <div class="form-group">
				              <label class="col-lg-3">Client Website</label>
				              <div class="col-lg-9">
				                <input type="text" id="webSite" name="webSite" value="" class="form-control">
				              </div>
				            </div>
				          </div>
				        </div>
				      </div>
				      <div class="modal-footer">
				        <button type="button" id="save_client" class="btn btn-primary" data-dismiss="modal" onclick = "addclient();">Save</button>
				        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				      </div>
				    </div>
				  </div>
				</div>
                </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Assign Team</label>
              <div class="col-md-9">
                <div class="form-icon"> <span class="icon-inside"> <i class="fa fa-search-plus"></i>
                  <input type="text" value="Select Team" class="form-control" data-target="#assignTeam" data-toggle="modal">
                  </span> <span data-target="#addTeam" data-toggle="modal"> <i class="fa fa-plus btn btn-default formic1"></i> </span> </div>
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
                        <div class="modal-body-custom mt clearfix">
                          <table cols="0" class="table table-bordered table-striped  clearfix">
                            <tbody>
                              <tr>
                                <th>&nbsp;&nbsp;</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-primary" type="button">Assign</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Assign Team modal popup ends -->
                <!-- Modal content Add Team-->
                <div role="dialog" id="addTeam" class="modal fade">
                  <div class="modal-dialog">
                    <div class="modal-content">
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
                              <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3">Last Name</label>
                            <div class="col-lg-9">
                              <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3">Email <span class="text-danger">*</span></label>
                            <div class="col-lg-9">
                              <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3">Invoice Frequency<span class="text-danger">*</span></label>
                            <div class="col-lg-9">
                              <select class="form-control">
                              	<option>Invoice-1</option>
                                <option>Invoice-2</option>
                                <option>Invoice-3</option>
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
                        <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Add Team modal popup ends --> 
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3" for="Project Id">Status</label>
              <div class="col-md-9">
                <select class="form-control select-bg">
                  <option>Status-1</option>
                  <option>Status-2</option>
                  <option>Status-3</option>
                  <option>Status-4</option>
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
              <input type="submit" value="Save" class="btn btn-primary">
              <input type="button" value="Cancel" class="btn btn-default ml">
            </div>
          </div>
        </div>
        </form:form>
      </div>
    </div>
  </section>
  <!-- Content Area Ends-->  
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
<script type="text/javascript">
        $('#project_form')
           .submit(function(e) {
        	    e.preventDefault(); 
        	    var frm = $('#project_form').serializeFormJSON();
        		var con = JSON.stringify(frm);
                $.ajax({
            	      type: "GET",
                  	  url: "http://localhost:8080/sourcelead/saveProject?json="+con,
                  	  data:con
                });
            
            });
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
	 $("#maxTsRowNum").val('1');
	document.getElementById("project_form").reset();
});
</script>

<script type="text/javascript">
 function addclient() {
     var frm = $('#project_form').serializeFormJSON();
        var con = JSON.stringify(frm);
        $.ajax({
	    	type: "GET",
	        url: "http://localhost:8080/sourcelead/saveOrgRelations?json="+con, 
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
    }
 
</script>