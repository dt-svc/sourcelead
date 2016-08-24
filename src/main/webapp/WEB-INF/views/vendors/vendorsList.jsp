<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>

    <!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Vendors list</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; Vendors list </div>
        </section>
        
		<!-- Project -->
<div class="timesheet_out">

<div class="vendor_wrap">
	<div class="timesheet_head">
        	<ul>
				<li class="li-vendor"><input type="text" class="vendorfild"><i class="fa fa-search"></i>|</li>
				<li><i class="fa fa-filter"></i><span>Approved<b>30</b></span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c1"><label for="c1">All</label><b>10</b></li>
							<li><input type="checkbox" id="c2"><label for="c2">Approved</label><b>10</b></li>
							<li><input type="checkbox" id="c3"><label for="c3">Draft</label><b>10</b></li>
							<li><input type="checkbox" id="c4"><label for="c4">Rejected</label><b>10</b></li>
                        </ul>
                    </div>                 	
                </li>
				<li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
                	<div class="export_pop">
                    	<ul>
                        	<li>PDF<i class="fa fa-file-pdf-o"></i></li>
                            <li>Excel<i class="fa fa-file-excel-o"></i></li>
                            <li>Word<i class="fa fa-file-word-o"></i></li>
                        </ul>
                    </div>
                </li>                
				<li><a href="add-vendor-contact.html"><i class="fa fa-plus"></i>Add contact</a></li>	
                <li><a href="add-vendor.html"><i class="fa fa-plus"></i>Add Vendor</a></li>		
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
    <div class="clients_wrap_table">
    	<table>
        	<tbody>
			<thead>
            	<tr>
                    <th>Name</th>
                    <th>Website</th>
                    <th>Location</th>
                    <th>No.of Contacts</th>
                    <th>Released jobs</th>
                    <th>Level</th>
                    <th>Actions</th>
                </tr>
			</thead>	
                <tr>
                    <td><div class="proj_show"><span title="Broadridge Broadridge Broadridge" class="td_overflow">Broadridge Broadridge Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span title="clientdomain.comclientdomain.comclientdomain.com" class="td_overflow">clientdomain.comclientdomain.comclientdomain.com</span></div></td>
                    <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr> 
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                    <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span class="td_overflow">clientdomain.com</span></div></td>
                    <td><div><span class="td_overflow">Hyderabad</span></div></td>
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>level 1</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>                                                                                                                                                			
            </tbody>
        </table>
	
</div>	
<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li><a href="#">4</a></li>
	<li><a href="#">5</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>		
		
	
	
	
</div>

<div class="side_mask"><div class="proj_close"></div></div>
<div class="clients_side_wrap">
	<div class="clients_side_wrap_inn">
    	<h1>Vendor name here</h1> 
        
<div class="project_side_wrap_inn_tabs">
    <ul class="project-tabs-menu">
        <li class="current"><a href="#tab-1">Details</a></li>
        <li><a href="#tab-2">Contacts</a></li>
        <li><a href="#tab-3">Documents</a></li>
        <li><a href="#tab-4">Activity</a></li>
        <div class="clearfix"></div>
    </ul>
    
    <div class="project_tabs">
    
        <div id="tab-1" class="project_tabs_inn">
            
<div class="require_wrap_details">
 <h1>Vendor details:</h1>
                <div class="require_one">
                	<div class="require_wrap_details_cta">
                    	<b>Name:</b>
                        <span>Sudhakar Reddy</span>
                         <div class="clearfix"></div>
                    </div>
                	<div class="require_wrap_details_cta">
                    	<b>Wedsite:</b>
                        <span>wedsitewedsite.com</span>
                         <div class="clearfix"></div>
                    </div>
                	<div class="require_wrap_details_cta">
                    	<b>Location:</b>
                        <span>Hyderabad, Telangana, India</span>
                         <div class="clearfix"></div>
                    </div>
                	<div class="require_wrap_details_cta">
                    	<b>Email:</b>
                        <span>wedsitewedsite@domain.com</span>
                         <div class="clearfix"></div>
                    </div> 
                	<div class="require_wrap_details_cta">
                    	<b>Level:</b>
                        <span>Level text</span>
                         <div class="clearfix"></div>
                    </div> 
                	<div class="require_wrap_details_cta">
                    	<b>Created by:</b>
                        <span>Vannam Mahesh</span>
                         <div class="clearfix"></div>
                    </div>                                                                                                                     
                    <div class="clearfix"></div>
                </div>              
            </div>            
            
        </div> 
        
            
        <div id="tab-2" class="project_tabs_inn">
<div class="project_tabs_inn_head">
              <input class="vendorfild" type="text"><i class="fa fa-search"></i> | <a href="javascript:void(0)" class="addcon-pop">
              <i class="glyphicon glyphicon-plus"></i>Add Contact</a>
              
				<!-- Popup Div -->
                    <div class="collapse-div-adduser col-md-4"> <i class="fa fa-close close-div"></i>
                          <div class="form-horizontal clearfix">
                                                    
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="First name">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Last name">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Email Id *">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Work phone">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Owner">
                              </div>
                            </div>                                                                                                                
                            <div class="clearfix mb10">
                              <div class="col-md-12 checkbox">
                                <label>
                                <input type="checkbox" name="Position" id="endDate">Send Invitation</label>
                              </div>
                            </div>
                            <div class="clearfix mr">
                              <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                            </div>
                          </div>
                        </div>
				<!-- Popup Div -->              
              
              </div>
            <div class="project_tabs_inn_table vendor_wrap_inn_table">
		<table>
        	<tbody>
            	<tr>
                	<th>Name</th>
                    <th>Email</th>
                    <th>Ph. No</th>
                    <th>Owner</th>
                    <th>Status</th>
                    <th>Invitation status</th>
                    <th>Action</th>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><select>
						<option>Active</option>
						<option>Closed</option>
						<option>Process</option>
						<option>Option</option>
					</select></div></td>
                    <td><div><b class="proj_closed">Rejected</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><b class="proj_active">Active</b><i class=" fa fa-edit"></i></div></td>
                    <td><div><b class="proj_active">Accepted</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam@domain.comVannam@domain.comVannam@domain.com</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><b class="proj_closed">Closed</b><i class=" fa fa-edit"></i></div></td>
                    <td><div><b class="proj_closed">Rejected</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><b class="proj_process">Process</b><i class=" fa fa-edit"></i></div></td>
                    <td><div><b class="proj_closed">Rejected</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><b class="proj_active">Active</b><i class=" fa fa-edit"></i></div></td>
                    <td><div><b class="proj_active">Accepted</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><b>Option</b><i class=" fa fa-edit"></i></div></td>
                    <td><div><b class="proj_closed">Rejected</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><b class="proj_active">Active</b></div></td>
                    <td><div><b class="proj_active">Accepted</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><b class="proj_active">Active</b></div></td>
                    <td><div><b class="proj_closed">Rejected</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><b class="proj_active">Active</b></div></td>
                    <td><div><b class="proj_active">Accepted</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam</span></div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><span>Vannam</span></div></td>
                    <td><div><b class="proj_active">Active</b></div></td>
                    <td><div><b class="proj_closed">Rejected</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>			
				
            </tbody>
        </table>  

	</div>
<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>		
		
           
        </div>
        
<div id="tab-3" class="project_tabs_inn">
<div class="project_tabs_inn_head">
              <input class="vendorfild" type="text"><i class="fa fa-search"></i> | <a href="javascript:void(0)" class="addcon-pop">
              <i class="glyphicon glyphicon-plus"></i>Add Document</a> 
              
				<!-- Popup Div -->
                          <div class="collapse-div-adduser col-md-4 show-div"> <i class="fa fa-close close-div"></i>
                          <div class="form-horizontal clearfix">
                          
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Name *">
                              </div>
                            </div>
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <select class="chosen-select">
                                    <option value="Select Month">Select Type</option>
                                    <option value="January">Type 1</option>
                                    <option value="February">Type 2</option>
                                    <option value="March">Type 3</option>
                                    <option value="April">Type 4</option>
                                </select>
                                </div>
                                <div class="col-md-12 mt10">
                                  <div class="input-group">
                                    <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                                      <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                                      </span> </label>
                                    <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Files are not selected...">
                                  </div>
                                </div>
                              </div>
                              
                            <div class="clearfix mb10">
                              <div class="col-md-6">
                                <select class="chosen-select">
                                    <option value="Select Month">Client Contact</option>
                                    <option value="January">Contact 1</option>
                                    <option value="February">Contact 2</option>
                                    <option value="March">Contact 3</option>
                                    <option value="April">Contact 4</option>
                                </select>
                                </div>
                              <div class="col-md-6">
                                <select class="chosen-select">
                                    <option value="Select Month">Owner</option>
                                    <option value="January">Owner 1</option>
                                    <option value="February">Owner 2</option>
                                    <option value="March">Owner 3</option>
                                    <option value="April">Owner 4</option>
                                </select>
                                </div>
                              </div>   
                              
							<div class="clearfix mb10">
                              <div class="col-md-12">
                                <textarea rows="2" placeholder="Description" class="form-control"></textarea>
                              </div>
                            </div>                                                         

                            <div class="clearfix mr">
                              <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                            </div>
                          </div>
                        </div>
				<!-- Popup Div -->               
              
              </div>
            <div class="project_tabs_inn_table vendor_wrap_inn_table">
		<table>
        	<tbody>
            	<tr>
                	<th>Name</th>
                    <th>Document Type</th>
                    <th>Contact Person</th>
                    <th>Attachment</th>
                    <th>Description</th>
                    <th>Created By</th>
                    <th>Action</th>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>Vannam</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div>File Name</div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><span>Vannam Mahesh Vannam Mahesh</span></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>			
				
            </tbody>
        </table>  

	</div>
<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>		
		
           
        </div>
        
<div id="tab-4" class="project_tabs_inn">
              <div class="project_tabs_inn_head">
              <input class="vendorfild" type="text"><i class="fa fa-search"></i> </div>	
            <div class="project_tabs_inn_table vendor_wrap_inn_table">
		<table>
        	<tbody>
            	<tr>
                	<th>By</th>
                    <th>To</th>
                    <th>Date & Time</th>
                    <th>Details</th>
                    <th>Action</th>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>12/8/2016  07:30 AM</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div><span>Vannam Vannam Vannam Vannam</span></div></td>
                    <td><div>9000 000 000</div></td>
                    <td><div><a href="javascript:void(0)">Vannam</a></div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>		
				
            </tbody>
        </table>  

	</div>
<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>		
		
           
        </div>                
               
    </div>
</div>        
      	
    </div>
</div> 		
		<!-- Project -->
	</div>	
      </section>
      
      </div>
      </div>
  </section>

<jsp:include page="../masterFooter.jsp" />