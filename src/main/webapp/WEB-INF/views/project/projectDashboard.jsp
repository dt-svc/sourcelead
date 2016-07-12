<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
<body>
      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Projects</h2>
             <a href="<c:url value='/project'/>"><span><i class="fa fa-bar-chart"></i></span>New Projects</a>
          </div>
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        
		<!-- Project -->
<div class="timesheet_out">

<div class="project_wrap">
	<div class="project_wrap_head"><input type="text" class="project_field" /><i class="glyphicon glyphicon-search"></i></div>
    <div class="project_wrap_table">
    	<table>
        	<tbody>
			<thead>
            	<tr>
                	<th>Id</th>
                    <th>Name</th>
                    <th>Manager</th>
                    <th>Date</th>
                    <th>Client</th>
                    <th>Team</th>
                    <th>Status</th>
                </tr>
			</thead>	
              
			<c:forEach var="report" items="${projectsList}" varStatus="raId">	
                <tr>
                	<td data-title="Id"><div>${report.id}</div></td>
                    <td data-title="Name"><div class="proj_show" onclick="projectSideBar('${report.id}');">${report.projectName}<i class="fa fa-binoculars"></i></div></td>
                    <td data-title="Manager"><div>${report.superiorUser.firstName} &nbsp;&nbsp;${report.superiorUser.lastName}</div></td>
                    <td data-title="Date"><div>form  &nbsp;<fmt:formatDate type="date"  value="${report.startDate}" />   &nbsp;to  &nbsp;<fmt:formatDate type="date" value="${report.endDate}" /></div></td>
                    <td data-title="Client"><div>Client 1</div></td>
                    <td data-title="Team"><div>Team a</div></td>
                    <td data-title="Status"><div><b class="proj_active">${report.projectStatus}</b></div></td>
                </tr>
            </c:forEach>          
           </tbody>
        </table>
	</div>
</div>

<div class="project_side_wrap">
	<div class="project_side_wrap_inn">
    <h1>Project name here</h1> <div class="proj_close"><i class="glyphicon glyphicon-share-alt"></i></div>
     <div id="projectSideDiv">
     </div>   
  <!-- <div class="project_side_wrap_inn_tabs">
    <ul class="project-tabs-menu">
        <li class="current"><a href="#tab-1">Employees</a></li>
        <li><a href="#tab-2">Tasks</a></li>
        <li><a href="#tab-3">Details</a></li>
        <div class="clearfix"></div>
    </ul>
    
    <div class="project_tabs">
        <div id="tab-1" class="project_tabs_inn">
        	<div class="project_tabs_inn_head">
			<a href="javascript:void(0)" data-target="#assignTeam" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i>Assign Emp</a>
			<a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i>Add Emp</a>
			</div>
            <div class="project_tabs_inn_table">
		<table>
        	<tbody>
            	<tr>
                	<th>Emp name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Approved hrs</th>
                    <th>Rejected hrs</th>
                    <th>Open hrs</th>
                    <th>Wait for approval</th>
                    <th>Edit</th>
                </tr>
                <tr>
                	<td><div><input type="text" value="Name" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Name" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Name" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Name" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Name" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Name" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                                                                                             
            </tbody>
        </table>            
            </div>
        </div>
        <div id="tab-2" class="project_tabs_inn">
        	<div class="project_tabs_inn_head">
			<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask"><i class="glyphicon glyphicon-plus"></i>Add Task</a></div>
            <div class="project_tabs_inn_table">
		<table>
        	<tbody>
            	<tr>
                	<th>Task name</th>
                    <th>Task description</th>
                    <th>Edit</th>
                </tr>
                <tr>
                	<td><div><input type="text" value="Task name here" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="Task description here" class="project_inline_field"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Task name here" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="Task description here" class="project_inline_field"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Task name here" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="Task description here" class="project_inline_field"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Task name here" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="Task description here" class="project_inline_field"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Task name here" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="Task description here" class="project_inline_field"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><input type="text" value="Task name here" class="project_inline_field"></div></td>
                    <td><div><input type="text" value="Task description here" class="project_inline_field"></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                                                                                             
            </tbody>
        </table>            
            </div>
        </div>
        <div id="tab-3" class="project_tabs_inn">
            <div class="project_tabs_inn_table">
				<div class="project_details">
					<div class="project_details_cta">
						<strong>Status:</strong><span><b>Active</b><i id="ac-drop" class="fa fa-edit">
							<ul>
								<li>Option 1</li>
								<li>Option 2</li>
								<li>Option 3</li>
								<li>Option 4</li>
							</ul>
						</i></span>
					</div></span><div class="clearfix"></div>
					<div class="project_details_cta">
						<strong>Project name:</strong>
						<span><input type="text" value="BIS India" placeholder="Project name" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Project manager:</strong>
						<span><input type="text" value="Srinivas" placeholder="Project manager" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Client:</strong>
						<span><input type="text" value="Cognizant" placeholder="Client" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Team members:</strong><span>25</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Start date:</strong>
						<span><input type="text" value="DD/MM/YY" placeholder="DD/MM/YY" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>End date:</strong>
						<span><input type="text" placeholder="DD/MM/YY" value="DD/MM/YY" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
					<p>Description<i class="fa fa-edit"></i> :</p>
					<textarea placeholder="Description">
Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
Why do we use it? It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.
a reader will be distracted by the readable content of a page when looking at its layout a reader will be distracted by the readable content of a page when looking at its layout  
					</textarea>
					<div class="clearfix"></div>
					<div class="project_details_cta">
						<strong>Created By:</strong><span>Srinivas</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Created date:</strong><span>10.07.2014 23:36:57</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Last Updated:</strong><span>10.07.2014 23:36:57</span><div class="clearfix"></div>
					</div>					
					<div class="clearfix"></div>
				</div>
            </div>
        </div>        
    </div>
</div>       -->  
      

	<div class="project_inn_mask"></div>
	<div class="project_error">
		<p>User delete message here ....</p>
		<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
	</div>	
    </div>
</div> 		
		<!-- Project -->
		
      </section>
      <!-- Content Area Ends--> 
</body>
	
<!--   Popup's   -->

<!-- Modal content Assign Team-->
                <div role="dialog" id="assignTeam" class="modal fade">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">X</button>
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
                        <div class="modal-body-custom mt clearfix" id="no-more-tables">
                          <table cols="0" class="table table-bordered table-striped  clearfix table-responsive">
                            <tbody>
                            <thead>
                              <tr>
                                <th>&nbsp;&nbsp;</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                              </tr>
                              </thead>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td data-title="Name">Murali Krishna</td>
                                <td data-title="Email">murali@businessintelli.com</td>
                                <td data-title="Role">Senior Recruiter</td>
                                <td data-title="Start Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td data-title="End Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>

 <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td data-title="Name">Murali Krishna</td>
                                <td data-title="Email">murali@businessintelli.com</td>
                                <td data-title="Role">Senior Recruiter</td>
                                <td data-title="Start Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td data-title="End Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>

 <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td data-title="Name">Murali Krishna</td>
                                <td data-title="Email">murali@businessintelli.com</td>
                                <td data-title="Role">Senior Recruiter</td>
                                <td data-title="Start Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td data-title="End Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>

 <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td data-title="Name">Murali Krishna</td>
                                <td data-title="Email">murali@businessintelli.com</td>
                                <td data-title="Role">Senior Recruiter</td>
                                <td data-title="Start Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td data-title="End Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>

 <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td data-title="Name">Murali Krishna</td>
                                <td data-title="Email">murali@businessintelli.com</td>
                                <td data-title="Role">Senior Recruiter</td>
                                <td data-title="Start Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td data-title="End Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>

 <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td data-title="Name">Murali Krishna</td>
                                <td data-title="Email">murali@businessintelli.com</td>
                                <td data-title="Role">Senior Recruiter</td>
                                <td data-title="Start Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td data-title="End Date"><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
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
                        <button data-dismiss="modal" class="close" type="button">X</button>
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
                        <div class="modal-body-custom custom-table custom-table-new">
                          <table class="table table-bordered table-striped  clearfix" cols="0">
                            <tr>
                              <th>Project Id</th>
                              <th>Project Name</th>
							  <th>Project Description</th>
							  <th>Manager</th>
							  <th>Date</th>
                              <th>Action</th>
                            </tr>
                            <tr>
                              <td>001</td>
							  <td>UI Development</td>
                              <td>The home page has a section in the middle of the page that needs to be to the right.</td>
							  <td>James Smith</td>
							  <td>08/07/2016</td>
							  <td>
							  <span><a title="Edit" href="javasript:void(0)"><i class="fa fa-edit"></i></a></span>
                              <span><a title="Remove" href="javasript:void(0)"><i class="fa fa-trash-o"></i></a></span>
							  </td>
                            </tr>
                          </table>
                        </div>
                        <div class="form-horizontal row custom-form">
                          <div class="col-xs-12 col-lg-7">
                          <div class="form-group">
                            <label class="col-md-3">Task Name</label>
                            <div class="col-md-9">
                              <input type="text" value="" class="form-control">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-3">Description</label>
                            <div class="col-md-9">
                              <textarea class="form-control" rows="2"></textarea>
                            </div>
                          </div>
                          </div>
                          <div class="col-xs-12 col-lg-5">
                          <a href="javascript:void(0)" class="btn btn-primary"> <i class="fa fa-plus"></i> Add Task</a>
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

<!--   Popup's   -->	
<jsp:include page="../masterFooter.jsp" />	
	
<script>


//project side bar
$(document).ready(function() {
 /* $(".proj_show").click(function(){
		$(".project_side_wrap").addClass("project_side_wrap_active");
	}); */
 $(".proj_close").click(function(){
		$(".project_side_wrap").removeClass("project_side_wrap_active");
	});	
});

//project side bar tabs
$(document).ready(function() {
	$("#tab-1").show();
 $(".project-tabs-menu a").click(function(event) {
     event.preventDefault();
     $(this).parent().addClass("current");
     $(this).parent().siblings().removeClass("current");
     var tab = $(this).attr("href");
     $(".project_tabs_inn").not(tab).css("display", "none");
     $(tab).fadeIn();
 });
});

//project active dropdown
$(document).ready(function() {
 $("#ac-drop").click(function(){
		$(".project_details_cta ul").slideToggle(200);
	});
 $("html").click(function() {
     $(".project_details_cta ul").slideUp(200);
 });
 $(".project_details_cta ul, #ac-drop").click(function(e) {
     e.stopPropagation();
 });
});

//project error message
$(document).ready(function() {
 $(".project_tabs_inn_table i").click(function(){
		$(".project_error").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	});
 $(".project_error a").click(function() {
     $(".project_error").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
 });
});


</script> 
 
 <script>
 function projectSideBar(projectId){
	  	    var result = "";
	  	    result = result + '<div class="project_side_wrap_inn_tabs">';
		  	result = result + '<ul class="project-tabs-menu">';
		  	result = result + '<li class="current"><a href="#tab-1" onClick="projectEmployee('+projectId+');" >Employees</a></li>';
		  	result = result + '<li><a href="#tab-2" onClick="projectTask('+projectId+');">Tasks</a></li>';
		  	result = result + ' <li><a href="#tab-3" onClick="pojectDetails('+projectId+');" >Details</a></li>';
		  	result = result + ' <div class="clearfix"></div>';
		  	result = result + ' </ul>';
		  	 
		  	result = result + '<div class="project_tabs">';
		  	result = result + ' <div id="tab-1" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-2" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-3" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '</div>'; 
		  	$("#projectSideDiv").empty(); 
		    $("#projectSideDiv").append(result);  	
		    $(".project_side_wrap").addClass("project_side_wrap_active");
			
		    projectEmployee(projectId);
 }
 </script> 
<script>
  function projectEmployee(projectId){
	    var result = "";
	    result = result + '    	<div class="project_tabs_inn_head">';
		result = result + '		<a href="javascript:void(0)" data-target="#assignTeam" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i>Assign Emp</a>';
		result = result + '		<a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i>Add Emp</a>';
		result = result + '		</div>';
	    result = result + '        <div class="project_tabs_inn_table">';
		result = result + '	<table>';
		result = result + '	<tbody>';
		result = result + '		<tr>';
        result = result + '	 	<th>Emp name</th>';
        result = result + '	     <th>Start Date</th>';
        result = result + '	     <th>End Date</th>';
        result = result + '	     <th>Approved hrs</th>';
        result = result + '	     <th>Rejected hrs</th>';
        result = result + '	     <th>Open hrs</th>';
        result = result + '	     <th>Wait for approval</th>';
        result = result + '	     <th>Edit</th>';
        result = result + '	 </tr>';
        result = result + '	 <tr>';
        result = result + '	 	<td><div><input type="text" value="Name" class="project_inline_field"></div></td>';
        result = result + '	     <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>';
        result = result + '	     <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>';
        result = result + '	     <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>';
        result = result + '	     <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>';
        result = result + '	     <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>';
        result = result + '	     <td><div><input type="text" value="HH:MM" class="project_inline_field project_inline_field_time"></div></td>';
        result = result + '	     <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
        result = result + '	 </tr>';
	    result = result + '	     </tbody>';
	    result = result + '	 </table>   ';         
	    result = result + '	     </div>';
	    $("#tab-1").empty();  
	    $("#tab-1").append(result);  
	    
	    $("#tab-1").show();
		$(".project-tabs-menu a").click(function(event) {
		     event.preventDefault();
		     $(this).parent().addClass("current");
		     $(this).parent().siblings().removeClass("current");
		     var tab = $(this).attr("href");
		     $(".project_tabs_inn").not(tab).css("display", "none");
		     $(tab).fadeIn();
		 });
  }
  
</script>

<script>
  function projectTask(projectId){
	var result = "";
	result = result + '	<div class="project_tabs_inn_head">';
	result = result + '		<a href="javascript:void(0)" data-toggle="modal" data-target="#addTask"><i class="glyphicon glyphicon-plus"></i>Add Task</a></div>';
	result = result + '    <div class="project_tabs_inn_table">';
	result = result + '	<table>';
	result = result + '	<tbody>';
	result = result + '    	<tr>';
	result = result + '        	<th>Task name</th>';
	result = result + '            <th>Task description</th>';
	result = result + '            <th>Edit</th>';
	result = result + '        </tr>';
	result = result + '        <tr>';
	result = result + '        	<td><div><input type="text" value="Task name here" class="project_inline_field"></div></td>';
	result = result + '            <td><div><input type="text" value="Task description here" class="project_inline_field"></div></td>';
	result = result + '            <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
	result = result + '        </tr>';
	result = result + '        <tr>';
	result = result + '        	<td><div><input type="text" value="Task name here" class="project_inline_field"></div></td>';
	result = result + '            <td><div><input type="text" value="Task description here" class="project_inline_field"></div></td>';
	result = result + '            <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
	result = result + '        </tr>';
	result = result + '    </tbody>';
	result = result + '</table>   ';         
	result = result + '    </div>';
	$("#tab-2").empty();  
	$("#tab-2").append(result);
  }
</script> 

<script>
  function pojectDetails(projectId){  
	  
    $.ajax({
   	type: "POST",
    url: "http://localhost:8080/sourcelead/getProjectById?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId, 	   
    		   success: function(data) {
	           alert("json"+data.id);
	var result = ""; 
    result = result + '      <div class="project_tabs_inn_table">';
	result = result + '			<div class="project_details">';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Status:</strong><span><b>'+data.status+'</b><i id="ac-drop" class="fa fa-edit">';
	result = result + '						<ul>';
	result = result + '							<li>Option 1</li>';
	result = result + '							<li>Option 2</li>';
	result = result + '							<li>Option 3</li>';
	result = result + '							<li>Option 4</li>';
	result = result + '						</ul>';
	result = result + '					</i></span>';
	result = result + '				</div></span><div class="clearfix"></div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Project name:</strong>';
	result = result + '					<span><input type="text" value="'+data.projectName+'" placeholder="Project name" class="project_detfield"></span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Project manager:</strong>';
	result = result + '					<span><input type="text" value="Srinivas" placeholder="Project manager" class="project_detfield"></span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Client:</strong>';
	result = result + '					<span><input type="text" value="Cognizant" placeholder="Client" class="project_detfield"></span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Team members:</strong><span>25</span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Start date:</strong>';
	result = result + '					<span><input type="text" value="'+data.startDate+'" placeholder="YYYY/MM/DD" class="project_detfield"></span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>End date:</strong>';
	result = result + '					<span><input type="text" placeholder="DD/MM/YY" value="'+data.endDate+'" class="project_detfield"></span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="clearfix"></div>';
	result = result + '				<p>Description<i class="fa fa-edit"></i> :</p>';
	result = result + '				<textarea placeholder="Description">';
	result = result + data.description;
	result = result + '				</textarea>';
	result = result + '				<div class="clearfix"></div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Created By:</strong><span>Srinivas</span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Created date:</strong><span>10.07.2014 23:36:57</span><div class="clearfix"></div>';
	result = result + '				</div>';
	result = result + '				<div class="project_details_cta">';
	result = result + '					<strong>Last Updated:</strong><span>10.07.2014 23:36:57</span><div class="clearfix"></div>';
	result = result + '				</div>';					
	result = result + '				<div class="clearfix"></div>';
	result = result + '			</div>';
	result = result + '      </div>';
	result = result + '  </div>   ';     
	result = result + '</div>'; 
		
	$("#tab-3").empty();  
	$("#tab-3").append(result);
	
  }
   });
  }
</script>  

  
  
 
</html>