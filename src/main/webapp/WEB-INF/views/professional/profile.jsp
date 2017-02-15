<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <link href="resources/css/ crowdsourcingstyles.css" rel="stylesheet">
<!-- <script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> -->


<script> 
$(document).ready(function(){
	getProfessionalInfo();
	//getVendorContactLists();
});
</script>
<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<section class="content-wraper">
        
     <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Profile</h2>
      </div>
      <div class="pull-right brud-crum">Home » Profile</div>
    </section>
    
  <!-- Profile Content Start-->
    <div id="professionalInfoDiv"></div>
     <!-- Profile Content End -->
    
         <!-- <section class="clearfix mt25"> -->
           <!-- <div class="project_side_wrap_inn_tabs">
            <ul class="project-tabs-menu">
              <li class="current"><a href="#tab-1" onclick="professionalProfile();">Profile</a></li>
              <li><a href="#tab-2">Resume</a></li>
              <li><a href="#tab-3">Employer's</a></li>
              <li><a href="#tab-4">Education</a></li>
              <li><a href="#tab-5">Certifications</a></li>
              <div class="clearfix"></div>
            </ul> -->
            <!-- <div class="project_tabs">
			<div id="tab-1" class="project_tabs_inn" style="display: block;">
				<div class="require_wrap_details content_padding crwd-textarea">
					  <h1>Summary:</h1>
					  <textarea placeholder="Enter description" readonly="">Enter description</textarea>
					  <div class="clearfix dashboard-widget candidate-profile">
					    <ul class="nav nav-tabs">
					      <li class="active"><a data-toggle="tab" href="#skills">General View</a></li>
					    </ul>
					    <div class="tab-content">
					      <div id="skills" class="tab-pane fade in active">
					        <div class="skills-summary">
					          <div class="design-view"> Select Skills
					            <h2 class="education">Skills:</h2>
					            <div class="select-skills">
					              <div class="divskill col-md-12"> Add skills div
					                <div class="addSkills"> <a href="#" class="btn-addskills" title="Add Skills" onclick="removeSkillPopUp(4547);"> <i class="fa fa-code"></i>Add Skills </a>
					                  <div class="col-md-7 add-skills"> <i class="fa fa-close close-div" onclick="removeSkillPopUp(4547);"></i>
					                    <div class="form-horizontal clearfix">
					                      <div class="clearfix div-padding">
					                        <div class="col-md-10">
					                          <select multiple="" class="chosen-select form-control" name="skillName" id="skillName" data-validation="validate_Space" data-validation-optional="true" style="display: none;">
					                            <option value="Net">Net</option>
					                            <option value="3D Max">3D Max</option>
					                            <option value="ABAP">ABAP</option>
					                          </select><div class="chosen-container chosen-container-multi" style="width: 100%;" title="" id="skillName_chosen"><ul class="chosen-choices"><li class="search-field"><input type="text" value="Select Some Options" class="default" autocomplete="off" style="width: 0px;"></li></ul><div class="chosen-drop"><ul class="chosen-results"></ul></div></div>
					                        </div>
					                        <div class="col-md-2">
					                          <button class="btn btn-sm btn-primary pull-right" type="submit" onclick="saveSkils(4547);">Add</button>
					                        </div>
					                      </div>
					                    </div>
					                  </div>
					                </div>
					                <div id="candidateSkillList" class="heightScroll clearfix">
					                  <table class="table table-responsive table-hover clearfix">
					                    <thead>
					                      <tr>
					                        <th>Skills</th>
					                        <th>Level</th>
					                        <th>Experience</th>
					                        <th>Action</th>
					                      </tr>
					                    </thead>
					                    <tbody>
					                      <tr>
					                        <td>
					                          <p class="form-control-static">C</p>
					                        </td>
					                        <td>
					                          <select id="scale" class="tbldata-dotdot-me width100percnt" onchange="fieldUpdateCandidate(78757,this);">
					                            <option selected="" value="Familiar" disabled="">Select</option>
					                            <option>Beginner</option>
					                            <option selected="">Familiar</option>
					                            <option>proficient</option>
					                            <option>Expert</option>
					                            <option>Master</option>
					                          </select>
					                        </td>
					                        <td>
					                          <select id="totalExperience" class="tbldata-dotdot-me width100percnt" onchange="fieldUpdateCandidate(78757,this);">
					                            <option selected="" disabled="">Select</option>
					                            <option>0</option>
					                            <option>1</option>
					                            <option>2</option>
					                            <option>3</option>
					                            <option>4</option>
					                            <option>5</option>
					                            <option>6</option>
					                          </select>
					                        </td>
					                        <td>
					                          <div class="text-center"><a onclick="removeSkillById(4547, 78757);" href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div>
					                        </td>
					                      </tr>
					                    </tbody>
					                  </table>
					                </div>
					                Add skills div ends </div>
					            </div>
					            
					            Select Skills </div>
					          <div id="employeeSubmissionProfile">
					            <div class="clearfix design-view">
					              <h2 class="education"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Employers</h2>
					              <div class="row">
					                <div class="col-md-12">
					                  <ul class="list-unstyled personal-info">
					                    <li>
					                      <div class="clearfix">
					                        <h3>ASD</h3>
					                        <p>QA</p>
					                        <p> January 2017 TO   January 2017 </p>
					                      </div>
					                    </li>
					                  </ul>
					                </div>
					              </div>
					            </div>
					          </div>
					          <div id="educationSubmissionProfile">
					            <div class="clearfix design-view">
					              <h2 class="education"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;Education</h2>
					              <div class="row">
					                <div class="col-md-12">
					                  <ul class="list-unstyled personal-info">
					                    <li>
					                      <div class="clearfix">
					                        <h3>Doctor of Pharmacy (Pharm.D.) <span class="small text-muted">&nbsp; </span></h3>
					                        <p> </p>
					                        <p> </p>
					                      </div>
					                    </li>
					                  </ul>
					                </div>
					              </div>
					            </div>
					          </div>
					          <div id="certificationSubmissionProfile">
					            <div class="clearfix design-view">
					              <h2 class="education"><i class="fa fa-certificate"></i>&nbsp;&nbsp;Certification</h2>
					              <div class="row">
					                <div class="col-md-12">
					                  <ul class="list-unstyled personal-info">
					                    <li>
					                      <div class="clearfix">
					                        <h3>QA <span class="small text-muted">&nbsp; </span></h3>
					                        <p>QA </p>
					                        <p> TO </p>
					                      </div>
					                    </li>
					                  </ul>
					                </div>
					              </div>
					            </div>
					          </div>
					        </div>
					      </div>
					      <div id="milestone" class="tab-pane fade">
					        <div class="skills-graph">
					          <h2 class="education">Skills Level</h2>
					          <textarea placeholder="Enter description">Typesetting industry.</textarea>
					        </div>
					      </div>
					    </div>
					  </div>
					</div>
            </div>
              <div id="tab-2" class="project_tabs_inn" style="display: none;">
                <div class="project_tabs_inn_head">
                    <a href="#" class="addUser-org add_mrgnRignt"><i class="glyphicon glyphicon-plus"></i>Add Resume</a> 
                  Popup Div
                  <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Title">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <textarea rows="2" placeholder="Description" class="form-control"></textarea>
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <div class="input-group">
                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                              <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                              </span> </label>
                            <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Files are not selected...">
                          </div>
                        </div>
                      </div>
                      <div class="clearfix mr">
                        <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                      </div>
                    </div>
                  </div>
                  Popup Div 
                <div class="remove_search">
                  <input placeholder="search" type="text">
                </div>
                  </div>
                <div class="content_padding project_tabs_inn_table vendor_wrap_inn_table">
                  <table>
                    <tbody>
                      <tr>
                        <th>Title</th>
                        <th>Attachment</th>
                        <th>Description</th>
                        <th>Created By</th>
                        <th>Action</th>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>javaresume</span></div>
                        </td>
                        <td>
                          <div><span>javaresume.doc</span></div>
                        </td>
                        <td>
                          <div><span>javaresume description here</span></div>
                        </td>
                        <td>
                          <div><span>Sujith S</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <nav>
                  <ul id="pagingg" class="pagination">
                    <li><a href="#">Previous</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li class="disabled"><a href="#">6</a></li>
                    <li data-page="last"><a href="#">Next</a></li>
                  </ul>
                  <div class="clearfix"></div>
                </nav>
              </div>
              <div id="tab-3" class="project_tabs_inn" style="display: none;">
                <div class="project_tabs_inn_head add_mrgnRignt"> <a href="#" class="addUser-org"> <i class="glyphicon glyphicon-plus"></i>Add Employer's </a> 
                  Popup Div
                  <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Employer *">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12 checkbox">
                          <label>
                            <input type="checkbox" name="Position" id="endDate">
                            Is Current Position</label>
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Designation">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Address">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <p class="ml">Start Date</p>
                        <div class="col-md-6">
                          <select class="chosen-select" style="display: none;">
                            <option value="Select Month">Select Month</option>
                            <option value="January">January</option>
                            <option value="February">February</option>
                            <option value="March">March</option>
                            <option value="April">April</option>
                            <option value="April">May</option>
                            <option value="April">June</option>
                            <option value="April">Jully</option>
                            <option value="April">August</option>
                            <option value="April">September</option>
                            <option value="April">October</option>
                            <option value="April">November</option>
                            <option value="April">December</option>
                          </select><div class="chosen-container chosen-container-single" style="width: 100%;" title=""><a class="chosen-single"><span>Select Month</span><div><b></b></div></a><div class="chosen-drop"><div class="chosen-search"><input type="text" autocomplete="off"></div><ul class="chosen-results"></ul></div></div>
                        </div>
                        <div class="col-md-6">
                          <input type="text" max="" class="form-control" value="" placeholder="Enter Year">
                        </div>
                      </div>
                      <div class="clearfix mb10 endDate">
                        <p class="ml">End Date</p>
                        <div class="col-md-6">
                          <select class="chosen-select" style="display: none;">
                            <option value="Select Month">Select Month</option>
                            <option value="January">January</option>
                            <option value="February">February</option>
                            <option value="March">March</option>
                            <option value="April">April</option>
                            <option value="April">May</option>
                            <option value="April">June</option>
                            <option value="April">Jully</option>
                            <option value="April">August</option>
                            <option value="April">September</option>
                            <option value="April">October</option>
                            <option value="April">November</option>
                            <option value="April">December</option>
                          </select><div class="chosen-container chosen-container-single" style="width: 100%;" title=""><a class="chosen-single"><span>Select Month</span><div><b></b></div></a><div class="chosen-drop"><div class="chosen-search"><input type="text" autocomplete="off"></div><ul class="chosen-results"></ul></div></div>
                        </div>
                        <div class="col-md-6">
                          <input type="text" max="" class="form-control" value="" placeholder="Enter Year">
                        </div>
                      </div>
                      <div class="clearfix mb10 hidden">
                        <div class="col-md-12">
                          <label>End Date</label>
                          <select class="chosen-select" style="display: none;">
                            <option value="Select Month">Select Month</option>
                            <option value="January">January</option>
                            <option value="February">February</option>
                            <option value="March">March</option>
                            <option value="April">April</option>
                            <option value="April">May</option>
                            <option value="April">June</option>
                            <option value="April">Jully</option>
                            <option value="April">August</option>
                            <option value="April">September</option>
                            <option value="April">October</option>
                            <option value="April">November</option>
                            <option value="April">December</option>
                          </select><div class="chosen-container chosen-container-single" style="width: 100%;" title=""><a class="chosen-single"><span>Select Month</span><div><b></b></div></a><div class="chosen-drop"><div class="chosen-search"><input type="text" autocomplete="off"></div><ul class="chosen-results"></ul></div></div>
                          <input type="text" class="form-control input-sm mt10" value="" placeholder="Enter Year">
                          <span class="input-group-addon input-groupAddon hidden"><i class="fa fa-calendar"></i></span> </div>
                      </div>
                      <div class="clearfix mr">
                        <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                      </div>
                    </div>
                  </div>
                  Popup Div 
                 <div class="remove_search">
                  <input placeholder="search" type="text">
                </div>
                  </div>
                <div class="content_padding project_tabs_inn_table vendor_wrap_inn_table">
                  <table>
                    <tbody>
                      <tr>
                        <th>Employer's</th>
                        <th>Designation</th>
                        <th>Address</th>
                        <th>Start date</th>
                        <th>End date</th>
                        <th>Action</th>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <nav>
                  <ul id="pagingdg" class="pagination">
                    <li><a href="#">Previous</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li class="disabled"><a href="#">6</a></li>
                    <li data-page="last"><a href="#">Next</a></li>
                  </ul>
                  <div class="clearfix"></div>
                </nav>
              </div>
              <div id="tab-4" class="project_tabs_inn" style="display: none;">
                <div class="project_tabs_inn_head addUser-org add_mrgnRignt"> <a href="javascript:void(0)"><i class="glyphicon glyphicon-plus"></i>Add Education</a> 
                  Popup Div
                  <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Education *" required="">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Specialization">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-6 positionRelative">
                          <input type="text" class="form-control input-sm" value="" placeholder="Start Date" id="edu-startDate">
                          <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                        <div class="col-md-6 positionRelative">
                          <input type="text" class="form-control input-sm" value="" placeholder="End Date" id="edu-endDate">
                          <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-8">
                          <input type="text" class="form-control input-sm" value="" placeholder="Grade">
                        </div>
                        <div class="col-md-4">
                          <select>
                            <option>Grade</option>
                            <option>%</option>
                            <option>CGPA</option>
                          </select>
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Institute">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Address">
                        </div>
                      </div>
                      <div class="clearfix mr">
                        <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                      </div>
                    </div>
                  </div>
                  Popup Div 
                 <div class="remove_search">
                  <input placeholder="search" type="text">
                </div>
                  </div>
                <div class="content_padding project_tabs_inn_table vendor_wrap_inn_table">
                  <table>
                    <tbody>
                      <tr>
                        <th>Education</th>
                        <th>Specilization</th>
                        <th>Start date</th>
                        <th>End date</th>
                        <th>Grade %</th>
                        <th>Institute/University</th>
                        <th>Address</th>
                        <th>Action</th>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>MBA MBA MBA MBA MBA</span></div>
                        </td>
                        <td>
                          <div><span>Finance</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><span>65%</span></div>
                        </td>
                        <td>
                          <div><span>JNTU JNTU JNTU JNTU</span></div>
                        </td>
                        <td>
                          <div><span>Hyderabad Hyderabad</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <nav>
                  <ul id="paginggd" class="pagination">
                    <li><a href="#">Previous</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li class="disabled"><a href="#">6</a></li>
                    <li data-page="last"><a href="#">Next</a></li>
                  </ul>
                  <div class="clearfix"></div>
                </nav>
              </div>
              <div id="tab-5" class="project_tabs_inn" style="display: none;">
                <div class="project_tabs_inn_head add_mrgnRignt"> <a href="#" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a> 
                  Popup Div
                  <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Name *">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control input-sm" value="" placeholder="Provider *">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-8">
                          <input type="text" class="form-control input-sm" value="" placeholder="Score">
                        </div>
                        <div class="col-md-4">
                          <select>
                            <option>Grade</option>
                            <option>%</option>
                            <option>CGPA</option>
                          </select>
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <textarea class="form-control" rows="1" placeholder="Description"></textarea>
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-6 positionRelative">
                          <input type="text" class="form-control input-sm" value="" placeholder="Valid From" id="validFrom">
                          <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                        <div class="col-md-6 positionRelative">
                          <input type="text" class="form-control input-sm" value="" placeholder="Validate To" id="validTo">
                          <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
                      </div>
                      <div class="clearfix mr">
                        <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                      </div>
                    </div>
                  </div>
                  Popup Div 
                <div class="remove_search">
                  <input placeholder="search" type="text">
                </div>
                  </div>
                <div class="content_padding project_tabs_inn_table vendor_wrap_inn_table">
                  <table>
                    <tbody>
                      <tr>
                        <th>Name</th>
                        <th>Provider</th>
                        <th>Score</th>
                        <th>Description</th>
                        <th>Valid from</th>
                        <th>Valid to</th>
                        <th>Action</th>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <nav>
                  <ul id="pagingg" class="pagination">
                    <li><a href="#">Previous</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li class="disabled"><a href="#">6</a></li>
                    <li data-page="last"><a href="#">Next</a></li>
                  </ul>
                  <div class="clearfix"></div>
                </nav>
              </div>
              <div id="tab-6" class="project_tabs_inn" style="display: none;">
                <div class="project_tabs_inn_head">
                  <input class="vendorfild" type="text">
                  <i class="fa fa-search"></i> </div>
                <div class="project_tabs_inn_table vendor_wrap_inn_table">
                  <table>
                    <tbody>
                      <tr>
                        <th>By</th>
                        <th>To</th>
                        <th>Date &amp; Time</th>
                        <th>Action</th>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span">Vannam (vannam@bis.com)</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016 7:30 AM</span></div>
                        </td>
                        <td>
                          <div><a href="javascript:void(0)">View</a></div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <nav>
                  <ul id="pagingg" class="pagination">
                    <li><a href="#">Previous</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li class="disabled"><a href="#">6</a></li>
                    <li data-page="last"><a href="#">Next</a></li>
                  </ul>
                  <div class="clearfix"></div>
                </nav>
              </div>
              <div id="tab-7" class="project_tabs_inn" style="display: none;">
                <div class="project_tabs_inn_head">
                  <input class="vendorfild" type="text">
                  <i class="fa fa-search"></i> </div>
                <div class="project_tabs_inn_table">
                  <table>
                    <tbody>
                      <tr>
                        <th>Title</th>
                        <th>Emp Type</th>
                        <th>Location</th>
                        <th>Rate</th>
                        <th>Owner</th>
                        <th>Client</th>
                        <th>Stage</th>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span class="mid-span ReqClick">Requirement Title</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Designer</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Hyderabad</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">300</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Vannam</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Srinivas</span></div>
                        </td>
                        <td>
                          <div><span class="mid-span">Final</span></div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <nav>
                  <ul id="pagingg" class="pagination">
                    <li><a href="#">Previous</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li class="disabled"><a href="#">6</a></li>
                    <li data-page="last"><a href="#">Next</a></li>
                  </ul>
                  <div class="clearfix"></div>
                </nav>
                
                popup
                <div class="reqPop">
                  <div class="reqPopInn">
                    <div class="reqClose"> <span><i class="fa fa-angle-double-left"></i>Go Back</span><b class="fa fa-close"></b> </div>
                    <h3>Requirement Title</h3>
                    <ul>
                      <li> <img src="images/pratap-bis.jpg" alt=""><span>Text here test here</span><b>12 DEC 2016 03:50 PM</b> <i class="fa fa-paperclip"></i>
                        <div class="clearfix"></div>
                      </li>
                      <li> <img src="images/pratap-bis.jpg" alt=""><span>Text here test here</span><b>12 DEC 2016 03:50 PM</b> <i class="fa fa-paperclip"></i>
                        <div class="clearfix"></div>
                      </li>
                      <li> <img src="images/pratap-bis.jpg" alt=""><span>Text here test here</span><b>12 DEC 2016 03:50 PM</b> <i class="fa fa-paperclip"></i>
                        <div class="clearfix"></div>
                      </li>
                      <li> <img src="images/pratap-bis.jpg" alt=""><span>Text here test here</span><b>12 DEC 2016 03:50 PM</b> <i class="fa fa-paperclip"></i>
                        <div class="clearfix"></div>
                      </li>
                      <li> <img src="images/pratap-bis.jpg" alt=""><span>Text here test here</span><b>12 DEC 2016 03:50 PM</b> <i class="fa fa-paperclip"></i>
                        <div class="clearfix"></div>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div> -->
          <!-- </div>
          
      </section> -->
</section>

<script>
function getProfessionalInfo(){ 
	var sourceleadUrl ='${sourceleadUrl}';
	var candidateType= "Professional";
	$.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/getProfessionalInfo?candidateStatus="+candidateType, 	   
		    success: function(data) {
		    	 var json = JSON.parse(data);
		    	 if(json.firstName != null && json.firstName != '' && json.firstName !='undefined'){
		    		 firstName = json.firstName;
		    	 }else{
		    		 firstName = "";
		    	 }
		    	 if(json.lastName != null && json.lastName != '' && json.lastName !='undefined'){
		    		 lastName = json.lastName;
		    	 }else{
		    		 lastName = "";
		    	 }
		    	 
		    	 if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod !='undefined'){
		    		 noticePeriod = json.noticePeriod;
		    	 }else{
		    		 noticePeriod = "";
		    	 }
		    	 
		    	 if(json.designation != null && json.designation != '' && json.designation !='undefined'){
		    		 designation = json.designation;
		    	 }else{
		    		 designation = "";
		    	 }
		    	 if(json.designationType != null && json.designationType != '' && json.designationType !='undefined'){
		    		 designationType = json.designationType;
		    	 }else{
		    		 designationType = "";
		    	 }
		    	 if(json.email != null && json.email != '' && json.email !='undefined'){
		    		 email = json.email;
		    	 }else{
		    		 email = "";
		    	 }
		    	 if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone !='undefined'){
		    		 mobilePhone = json.mobilePhone;
		    	 }else{
		    		 mobilePhone = "";
		    	 }
		    	 if(json.country != null && json.country != '' && json.country !='undefined'){
		    		 country = json.country;
		    	 }else{
		    		 country = "";
		    	 }
		    	 
		    	 if(json.skillsName != null  && json.skillsName != '' && json.skillsName != 'undefiend'){
		    		 skillsName = json.skillsName;
		    	 }else{
		    		 skillsName = "NO DATA";
		    	 }
		    	 
		    	 
		    	 var result = "";
				 result = result +'	<div class="content-area clearfix panelBody"> ';
				 result = result +'	<section class="row pro_info">';
				 result = result +'	    <div class="col-md-5">';
				 result = result +'	        <h5>Professional Info</h5>';
				 result = result +'	        <div class="chip">';
				 result = result +'	        <img onerror="predefineProfileImage(this);"  src="<c:url value="/displayCandidateImage/'+json.id+'"/>" alt="Profile Image" width="96" height="96">';
				 
				 result = result +'	        <span>';
				 result = result +'	        <strong>'+firstName+' '+lastName+'</strong> <br>';
				 result = result +'	        UI / Frontend Developer';
				 result = result +'	        </span>';
				 result = result +'	        </div>';
				 result = result +'	        <ul class="list-unstyled professional-details">';
				 result = result +'	            <li><i class="fa fa-magnet" title="Designation Type"></i><strong>'+designation+' <span class="text-muted">'+designationType+'</span></strong></li>';
				 result = result +'	            <li><i class="fa fa-history" title="Experience"></i>6 Years</li>';
				 result = result +'	            <li><i class="fa fa-calendar" title="Notice Period"></i>'+noticePeriod+'</li>';
				 result = result +'	            <li>';
				 result = result +'	                <i class="fa fa-money" title="Current and Expected Rate"></i>';
				 result = result +'	                <span>Current: <strong>EUR</strong></span> -||- <span>Expected: <strong>EUR</strong></span>';
				 result = result +'	            </li>';
				 result = result +'	        </ul>';
				 result = result +'	    </div>';
				 result = result +'	    <div class="col-md-3">';
				 result = result +'	        <h5>Can be Reach</h5>';
				 result = result +'	        <ul class="list-inline list-unstyled reachout-links mt25">';
				 result = result +'	            <li><a href="javascript:void(0)" title="FaceBook" class="btn-fb"><i class="fa fa-facebook-square fa-2x"></i></a></li>';
				 result = result +'	            <li><a href="javascript:void(0)" title="LinkedIn" class="btn-li"><i class="fa fa-linkedin-square fa-2x"></i></a></li>';
				 result = result +'	            <li><a href="javascript:void(0)" title="Twitter" class="btn-tw"><i class="fa fa-twitter-square fa-2x"></i></a></li>';
				 result = result +'	            <li><a href="javascript:void(0)" title="Google" class="btn-go"><i class="fa fa-google-plus-square fa-2x"></i></a></li>';
				 result = result +'	            <li><a href="javascript:void(0)" title="Globe" class="btn-ww"><i class="fa fa-globe fa-2x"></i></a></li>';
				 result = result +'	        </ul>';
				 result = result +'	        <div class="reachout">';
				 result = result +'	        <p><i class="fa fa-at" title="Mail me"></i>'+email+'</p>';
				 result = result +'	        <p><i class="fa fa-phone" title="Call us"></i>'+mobilePhone+'</p>';
				 result = result +'	        <p><i class="fa fa-map-marker" title="Location"></i>'+country+'</p>';
				 result = result +'	        </div>';
				 result = result +'	    </div>';
				 result = result +'	    <div class="col-md-4">';
				 result = result +'	        <h5>Skills</h5>';
				 result = result +'	        <div class="skills text-center mt25">';
				 result = result +'	            <p><strong>';
				 result = result +'	        '+skillsName+'</strong>';
				 result = result +'	        </p>';
				 result = result +'	            </div>';
				 result = result +'	    </div>';
				 result = result +'	</section>';
				 result = result +'	</div>';
				 
				 
				 result = result +' <section class="clearfix mt25">';
				 result = result +'<div class="project_side_wrap_inn_tabs">';
			     result = result +'   <ul class="project-tabs-menu">';
			     result = result +'     <li class="current"><a href="#tab-1" onclick="professionalProfile('+json.id+');">Profile</a></li>';
			     result = result +'     <li><a href="#tab-2" onclick="candidateResumesList('+json.id+'); candidateResume('+json.id+');">Resume</a></li>';
			     result = result +'     <li><a href="#tab-3" onclick="professionalCandidateEmployers('+json.id+');">Employers</a></li>';
			     result = result +'     <li><a href="#tab-4" onclick="professionalCandidateEducation('+json.id+');">Education</a></li>';
			     result = result +'     <li><a href="#tab-5" onclick="professionalCandidateCertificate('+json.id+');">Certifications</a></li>';
			     result = result +'     <div class="clearfix"></div>';
			     result = result +'   </ul>';
			     
			     result = result+'     <div class="project_tabs">';
		         result = result + ' <div id="tab-1" class="project_tabs_inn">';
				 result = result + '</div>';

				 result = result + '<div id="tab-2" class="project_tabs_inn">';
				 result = result + '</div>';
				 
				 result = result + '<div id="tab-3" class="project_tabs_inn">';
				 result = result + '</div>';
				 
				 result = result + ' <div id="tab-4" class="project_tabs_inn">';
				 result = result + '</div>';
				 
				 result = result + '<div id="tab-5" class="project_tabs_inn">';
				 result = result + '</div>';
				 
			     result = result +'	</div>';
			     result = result +' </section>';
				 
				 $("#professionalInfoDiv").empty();
				 $("#professionalInfoDiv").append(result); 
				 professionalProfile(json.id);
		    },
	});
            
}

</script>


<jsp:include page="professionalCandidatesProfile.jsp"></jsp:include>
<jsp:include page="professionalCandidateResumes.jsp"></jsp:include>
<jsp:include page="professionalCandidateEmployers.jsp"></jsp:include>
<jsp:include page="professionalCandidateEducation.jsp"></jsp:include>
<jsp:include page="professionalCandidateCertificates.jsp"></jsp:include>
<jsp:include page="../masterFooter.jsp" />