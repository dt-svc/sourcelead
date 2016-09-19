<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

 <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">

<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script> 

<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
 
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script> 
<!-- end files for autocomplete with tagit -->

<jsp:include page="requirementDetails.jsp"></jsp:include>
<jsp:include page="requirementClientDetails.jsp"></jsp:include>
<jsp:include page="requirementInternalDetails.jsp"></jsp:include>


<style type="text/css">
.modal-bodyCustom {
margin-bottom: 15px;
padding-bottom: 15px;
border-bottom: 1px solid #ccc;
min-height: 150px;
}
.popupFilter #example_filter {
top:-48px !important;
}
</style>

<script type="text/javascript">
		window.onload = function () {
			getAllCountries();
			
			$(".cd").click(function(){  
		        $(".clientDetails").toggleClass('hidden', 500);
		    });
			
			$(".cd1").click(function(){  
		        $(".col-md-12 clearfix").toggleClass('hidden', 500);
		    });
			
		 };
</script>

 <script>
		$(document).ready(function() {
			document.getElementById("requirement_form").reset();
		});
	</script>
<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement Details</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Requirement &raquo; Requirement Details</div>
  </section>
  <div class="content-area content-new clearfix">
   <form:form id="requirement_form" name="requirement_form"  enctype='multipart/form-data' >
    <div class="form-horizontal req-dtls myForm row">
      <div class="clearfix">
        <div class="col-lg-12">
          <h3>Add Requirement Details</h3>
        </div>
  <div class="col-md-12 clearfix">
	  <div class="col-md-12">
	          <div style="margin:0 auto" class="input-group col-md-6">
	            <label class="input-group-btn">
	            	<span class="btn btn-primary">Select Template
	                </span>
	            </label>
                <select class="form-control" id="template"  onChange="setTemplateValues();">
                    <option>Select template</option>
                    <c:forEach var="title" items="${jobsTemplateList}">
	                    <option value="${title.id}">${title.jobTitle}</option>
                     </c:forEach>   
                </select>
	          </div>
	          <div class="text-center clearfix mt text-muted">
	          	<div class="clearfix text-center mb">
	          <span class="text-info">
	          	<strong>
	            <b class="hidden-xs">---------------------------------</b><span class="r-circle">OR</span>
	            <b class="hidden-xs">------------------------------</b>
	            </strong>
	            </span>
	          </div>
	          </div>
	 </div>
</div>

          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
           <div class="form-group">
	         <label class="col-md-3">Title<span class="text-danger">*</span></label>
	         <div class="col-md-9">
	          <div class="input-group">
	             <input type="text" id="jobTitle" name="jobTitle" value="${jobTitle}" field-name="Job Title"  data-validation="validate_Space validate_length length2-250" data-validation-optional="true"  value="" class="form-control">
	             <div class="input-group-btn selp-custom-min">
	             <select class="selectpicker" id="jobType" name="jobType">
	                 <option><a href="#">C2C</a></option>
	                 <option><a href="#">W-2</a></option>
	                 <option><a href="#">Part Time</a></option>
	                 <option><a href="#">Full Time</a></option>
	                 <option><a href="#">Other</a></option>
	             </select>
	             </div>
	           </div>
	         </div>
	       </div>
          
          
      
          
          
          <div class="form-group">
            <label class="col-md-3">Id</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="jobId" name="jobId" value="${jobId}"  field-name="JobId" data-validation="validate_Space validate_length length2-100" data-validation-optional="true"  value="">
            </div>
          </div>
          
          <div class="form-group">
            <label class="col-md-3">No. of Positions</label>
            <div class="col-md-9">
               <input type="text" class="form-control" id="positions" name="positions" value="${positions}"  field-name="Positions" data-validation="validate_Space validate_length length1-5" data-validation-optional="true"  value="">
            </div>
          </div>
          
          <div class="form-group">
              <label class="col-md-3">Education</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="education" id="education" data-validation="validate_Space" data-validation-optional="true">
                <c:forEach var="edu" items="${educationsList}">
	                     <option value="${edu.name}">${edu.name}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            
          <div class="form-group">
            <label class="col-md-3">Experince</label>
            <div class="col-md-9">
              <div class="row">
                <div class="col-md-6">
                   <input type="text" class="form-control" id="minExp" name="minExp" value="${minExp}"  field-name="MinExp" data-validation="validate_Space validate_length length1-5" data-validation-optional="true"  value="">
                </div>
                <div class="col-md-6">
                  <input type="text" class="form-control" id="maxExp" name="maxExp" value="${maxExp}"  field-name="MinExp" data-validation="validate_Space validate_length length1-5" data-validation-optional="true"  value="">
                </div>
              </div>
            </div>
          </div>
          
          <div class="form-group">
            <label class="col-md-3">Pay Rate/Salary</label>
            <div class="col-md-9">
              <div class="input-group">
                <div class="input-group-btn selp-custom-min">
                  
                  <select class="selectpicker" id="currencyCode" name="currencyCode">
	                 <option ${currencyCode  == 'INR' ? 'selected' : ''}><a href="#">INR</a></option>
	                 <option ${currencyCode  == 'AUD' ? 'selected' : ''}><a href="#">AUD</a></option>
	                 <option ${currencyCode  == 'USD' ? 'selected' : ''}><a href="#">USD</a></option>
	                 <option ${currencyCode  == 'GPB' ? 'selected' : ''}><a href="#">GPB</a></option>
	                 <option ${currencyCode  == 'SGD' ? 'selected' : ''}><a href="#">SGD</a></option>
	                 <option ${currencyCode  == 'EUR' ? 'selected' : ''}><a href="#">EUR</a></option>
                </select>
                
                </div>
                <input type="text" class="form-control" id="rate" name="rate" value="${rate}" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true"  value="">
                <div class="input-group-btn selp-custom-min">
                  <select class="selectpicker" id="rateType" name="rateType">
                    <option value="c2c">Per</option>
                    <option value="w2">1hr</option>
                    <option value="c2c">2hr</option>
                    <option value="c2c">3hr</option>
                    <option value="c2c">4hr</option>
                    <option value="c2c">Other</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          
        
          
          <div class="form-group">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				 <select class="chosen-select form-control select-bg" name="country" id="country" field-name="Country"  >
                 </select>
				</div>
		</div>
			
          
          <div class="form-group">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<select  class="chosen-select form-control select-bg" name="state" id="state" field-name="State"  >
                 </select>
				</div>
		 </div>
		 
		 <div class="form-group">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
			     <select  class="chosen-select form-control select-bg" name="city" id="city" field-name="City"  >
                 </select>
			</div>
		</div>
		
         <div class="form-group">
              <label class="col-md-3">Skills</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="skills" id="skills" data-validation="validate_Space" data-validation-optional="true">
                <c:forEach var="skill" items="${skillsList}">
	                     <option value="${skill.name}">${skill.name}</option>
                </c:forEach>
                </select>
              </div>
            </div>
         
          <div class="form-group">
            <label class="col-md-3">Attachment</label>
            <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
            <div class="col-md-9">
              <div class="input-group">
                <label class="input-group-btn">
                <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                       <input type="file" name="uploadBtn" id="uploadBtn"  class="hidden upload">
                  </span>
                </label>
                <input type="text" class="form-control" readonly id="uploadFile" name="docFile" type="file" name="" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation="validate_Space validatedoc" data-validation-optional="true">
              </div>
            </div>
            <!-- upload file html need to add bottom JavaScript otherwise it will not work --> 
          </div>
           <div class="form-group">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
             <textarea rows="2" class="form-control" id="description" name="description" value="${description}" field-name="Description" data-validation="validate_Space validate_length length2-5000" data-validation-optional="true"></textarea>
            </div>
          </div>
          
          <div class="form-group">
            
            <label class="col-md-3">Is Template</label>
            <div class="col-md-9">
            <input type="checkbox" name="isTemplate" id="isTemplate" value="" onclick="getTemplateVal();">
		    </div>
        </div>
        
      </div>
      <div class="clearfix mt">
        <div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
	      <div class="ml">
            <input type="checkbox" name="cd" class="cd">
            <label>&nbsp;&nbsp;<strong>Is Client Job</strong>
            &nbsp;&nbsp;
            <i class="fa fa-question-circle-o text-info" title="Click the Checkbox"></i></label>
		  </div>
          <div class="clientDetails hidden">
            <div class="clearfix">
              <h3>Client Details</h3>
            </div>
            <div class="clearfix">
              <div class="form-group">
                <label class="col-md-3">Client <span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control select-bg" name="selectClient" id="addClientId" onchange="getContactList(this);">
                    	<option value=''>-- Select Client --</option>
	                    <c:forEach var="report" items="${add_clientList}">
							 	<c:if test="${report.locationId.id == loginOrgLocId}">  
			                    	<option value="${report.id},${report.responderLocationId.id}">${report.responderLocationId.org.orgName}</option>
			                    </c:if>  
			                    <c:if test="${report.responderLocationId.id == loginOrgLocId}"> 
			                  	 	<option value="${report.id},${report.responderLocationId.id}">${report.locationId.org.orgName}</option>
			                    </c:if>
						</c:forEach>
                    </select>
                    <i data-target="#addClient" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i>
                     
                    </div>
                  <div role="dialog" id="addLevel" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Level</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                            <div class="col-xs-12 col-md-12 col-lg-12">
                              <div class="form-group">
                                <label class="col-lg-3">Level Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Description </label>
                                <div class="col-lg-9">
                                  <textarea class="form-control" rows="2"></textarea>
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
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Client Contact <span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control" name="selectContact" id="selectContact">
                      <option value=''>-- Select Contact --</option>
                    </select>
                    <i data-target="#addContact" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new" id="addContactPlus" style="display: none;"></i> 
                 </div>
                  
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Bill Rate / Salary</label>
                <div class="hidden">
                <div class="col-md-9">
                  <div class="input-group">
                    <div class="input-group-btn selp-custom-med">
                      <select class="selectpicker">
                        <option value="c2c">INR</option>
                        <option value="w2">200</option>
                        <option value="c2c">300</option>
                        <option value="c2c">350</option>
                        <option value="c2c">400</option>
                        <option value="c2c">Other</option>
                      </select>
                    </div>
                    <div class="input-group-btn selp-custom-med">
                      <select class="selectpicker">
                        <option value="c2c">Per</option>
                        <option value="w2">1hr</option>
                        <option value="c2c">2hr</option>
                        <option value="c2c">3hr</option>
                        <option value="c2c">4hr</option>
                        <option value="c2c">Other</option>
                      </select>
                    </div>
                    <div class="input-group-btn selp-custom-med">
                      <select class="selectpicker">
                        <option value="c2c">Per</option>
                        <option value="w2">1hr</option>
                        <option value="c2c">2hr</option>
                        <option value="c2c">3hr</option>
                        <option value="c2c">4hr</option>
                        <option value="c2c">Other</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-md-9">
              <div class="input-group">
                <div class="input-group-btn selp-custom-min">
                  <select class="selectpicker">
                    <option value="c2c">INR</option>
                    <option value="w2">200</option>
                    <option value="c2c">300</option>
                    <option value="c2c">350</option>
                    <option value="c2c">400</option>
                    <option value="c2c">Other</option>
                  </select>
                </div>
                <input type="text" class="form-control" value="">
                <div class="input-group-btn selp-custom-min">
                  <select class="selectpicker">
                    <option value="c2c">Per</option>
                    <option value="w2">1hr</option>
                    <option value="c2c">2hr</option>
                    <option value="c2c">3hr</option>
                    <option value="c2c">4hr</option>
                    <option value="c2c">Other</option>
                  </select>
                </div>
              </div>
            </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Submission Form<span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control">
                      <option>Select Submission Form</option>
                      <option>Submission Form-1</option>
                      <option>Submission Form-2</option>
                      <option>Submission Form-3</option>
                    </select>
                    <i data-target="#addSubmissonForm" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i> </div>
                  <div id="addSubmissonForm" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Submission Form</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                          <div class="row">
                            <div class="col-xs-12 col-md-12 col-lg-12">
								<div class="clearfix">
                    <div class="form-horizontal clearfix custom-fields">
                      <div class="clearfix mb">
                        <div class="col-md-8">
                          <div class="row">
                            <label class="col-md-3">Title</label>
                            <div class="col-md-8">
                              <input type="text" class="form-control input-sm" value="" placeholder="Title">
                            </div>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <input type="button" class="btn btn-primary btn-sm" value="Add Field">
                        </div>
                      </div>
                      <div class="clearfix mb">
                        <div class="col-md-8">
                          <div class="row">
                            <label class="col-md-3">
								<input type="text" placeholder="Field Name" class="form-control input-sm">
                            </label>
                            <div class="col-md-8">
                              <select class="form-control">
                                <option>Option Value-1</option>
                                <option>Option Value-2</option>
                                <option>Option Value-3</option>
                              </select>
                            </div>
                          </div>
                        </div>
                                                    <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>

                      </div>
                      <div class="form-custom">
                      <div class="form-header clearfix mb col-md-12">
                      <div class="col-sm-3">
                       <label>Field</label>
                      </div>
                      <div class="col-sm-5">
                        <label>Type</label>
                      </div>
                      <div class="col-sm-4">
                         <label>Is Mandator</label>
                      </div>
                      </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Text Field</label>
                              <div class="col-md-8">
                                <input type="text" class="form-control input-sm" value="" placeholder="Text Field">
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>

                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Number Field</label>
                              <div class="col-md-8">
                                <input type="text" class="form-control input-sm" value="" placeholder="Number Field">
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Select Field</label>
                              <div class="col-md-8">
                                <select class="form-control">
                                  <option>Select Value-1</option>
                                  <option>Select Value-2</option>
                                  <option>Select Value-3</option>
                                  <option>Select Value-4</option>
                                </select>
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Browse File</label>
                              <div class="col-md-8">
                                    <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
                                            <div class="input-group">
                                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                                            <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
                                            </span> </label>
                                            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">
                                        </div>
                                    <!-- upload file html need to add bottom JavaScript otherwise it will not work --> 
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Radio btn</label>
                              <div class="col-md-8 toggle-button">
                                <label class="switch">
                                <input type="checkbox" checked>
                                <div class="slider round"></div>
                                </label>
                                </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Checkbox</label>
                              <div class="col-md-8 toggle-button">
                                <label class="switch">
                                <input type="checkbox">
                                <div class="slider round"></div>
                                </label>
                                </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Textarea</label>
                              <div class="col-md-8">
                                <textarea rows="1" class="form-control" placeholder="Textarea"></textarea>
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                            </div>
                          </div> </div>
                        </div>
                        <div class="modal-footer">
                          <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      
      <%-- <div class="clearfix">
        <div class="col-lg-12">
          <h3>Internal Details</h3>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-12 col-lg-12  myForm">
         <div class="form-group">
            <label class="col-md-3">Manager <span class="text-danger">*</span></label>
            <div class="col-md-9">
              <select  class="chosen-select form-control select-bg" id="managerUserId" >
               <option value="php">Select Manager</option>
	                    <c:forEach var="report" items="${usersList}">
		                    <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
		                </c:forEach>
              </select>
            </div>
          </div>
         <div class="form-group">
                <label class="col-md-3">Approver<span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select multiple class="chosen-select form-control select-bg">
                      <option disabled>Select Client</option>
                      <option>Client-1</option>
                      <option>Client-2</option>
                      <option>Client-3</option>
                    </select>
                    Add Level
                    
                     
                    </div>
                  </div>
                    </div>
                  <div role="dialog" id="addLevel" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Level</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                            <div class="col-xs-12 col-md-12 col-lg-12">
                              <div class="form-group">
                                <label class="col-lg-3">Level Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Description </label>
                                <div class="col-lg-9">
                                  <textarea class="form-control" rows="2"></textarea>
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
                </div>
              </div>
          
          <div class="form-group">
            <label class="col-md-3">Assign To <span class="text-danger">*</span></label>
            <div class="col-md-9">
                <div class="form-icon"> <span class="icon-inside"> <i class="fa fa-search-plus"></i>
                  <input type="text" value="Assing Role" class="form-control" data-target="#assignTeam" data-toggle="modal">
                  </span> <span data-target="#addTeam" data-toggle="modal"> <i class="fa fa-plus btn btn-default formic1"></i> </span> </div>
                <span class="hidden">Select Team</span> 
                <!-- Modal content Assign To -->
                <div role="dialog" id="assignTeam" class="modal fade">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Assign Role</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal row custom-form">
                        <div class="form-group">
                          <div class="col-xs-12 col-md-8 col-lg-8">
                            <div class="input-group">
                              <input type="search" class="form-control" value="">
                              <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>
                          </div>
                          <div class="col-xs-12 col-md-4 col-lg-4">
                             <select class="selectpicker">
                              <option value="All">All</option>
                              <option value="Admin">Admin</option>
                              <option value="Account Manager">Account Manager</option>
                              <option value="Recruiting Manager">Recruiting Manager</option>
                              <option value="Hiring Manager">Hiring Managerr</option>
                              <option value="Teams">Teams</option>
                             </select>
                        </div>
                        </div>
                        <div class="modal-body-custom mt clearfix">
                          <table cols="0" class="table table-bordered table-striped  clearfix">
                            <tbody>
                              <tr>
                                <th><input type="checkbox"></th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td><span class="tbldata-dotdot">Murali Krishna</span></td>
                                <td><span class="tbldata-dotdot" title="muraraikumarmahessrinivas@businessintelli.com">muraraikumarmahessrinivas@businessintelli.com</span></td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control clsDatePicker" id="datepicker5">
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
                </div> </div>
                <!-- Assign To modal popup ends -->
                <!-- Modal content Demo Popup: Need to Update -->
                <div role="dialog" id="addTeam" class="modal fade">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Demo Popup: Need to Update</h4>
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
                <!-- Modal content Demo Popup: Need to Update ends --> 
              </div>
          </div>
          <div class="form-group">
            <label class="col-md-3">Priority<span class="text-danger">*</span></label>
            <div class="col-md-9">
              <select class="selectpicker form-control">
                <option>Select Priority</option>
                <option>Priority-1</option>
                <option>Priority-2</option>
                <option>Priority-3</option>
              </select>
            </div>
          </div>
          <div class="form-group hidden">
            <label class="col-md-3">Application Form<span class="text-danger">*</span></label>
            <div class="col-md-9">
              <div class="form-icon">
                <select class="chosen-select form-control">
                  <option>Select Client</option>
                  <option>Client-1</option>
                  <option>Client-2</option>
                  <option>Client-3</option>
                </select>
                <i data-target="#addApplicationForm" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i> </div>
              <div role="dialog" id="addApplicationForm" class="modal fade">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                      <h4 class="modal-title">Application Form</h4>
                    </div>
                    <div class="modal-body clearfix">
                                                <div class="form-horizontal custom-form clearfix">
                          <div class="row">
                            <div class="col-xs-12 col-md-12 col-lg-12">
								<div class="clearfix">
                    <div class="form-horizontal clearfix custom-fields">
                      <div class="clearfix mb">
                        <div class="col-md-8">
                          <div class="row">
                            <label class="col-md-3">Title</label>
                            <div class="col-md-8">
                              <input type="text" class="form-control input-sm" value="" placeholder="Title">
                            </div>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <input type="button" class="btn btn-primary btn-sm" value="Add Field">
                        </div>
                      </div>
                      <div class="clearfix mb">
                        <div class="col-md-8">
                          <div class="row">
                            <label class="col-md-3">
								<input type="text" placeholder="Field Name" class="form-control input-sm">
                            </label>
                            <div class="col-md-8">
                              <select class="form-control">
                                <option>Option Value-1</option>
                                <option>Option Value-2</option>
                                <option>Option Value-3</option>
                              </select>
                            </div>
                          </div>
                        </div>
                                                    <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>

                      </div>
                      <div class="form-custom">
                      <div class="form-header clearfix mb col-md-12">
                      <div class="col-sm-3">
                       <label>Field</label>
                      </div>
                      <div class="col-sm-5">
                        <label>Type</label>
                      </div>
                      <div class="col-sm-4">
                         <label>Is Mandator</label>
                      </div>
                      </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Text Field</label>
                              <div class="col-md-8">
                                <input type="text" class="form-control input-sm" value="" placeholder="Text Field">
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>

                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Number Field</label>
                              <div class="col-md-8">
                                <input type="text" class="form-control input-sm" value="" placeholder="Number Field">
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Select Field</label>
                              <div class="col-md-8">
                                <select class="form-control">
                                  <option>Select Value-1</option>
                                  <option>Select Value-2</option>
                                  <option>Select Value-3</option>
                                  <option>Select Value-4</option>
                                </select>
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Browse File</label>
                              <div class="col-md-8">
                                    <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
                                            <div class="input-group">
                                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                                            <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
                                            </span> </label>
                                            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">
                                        </div>
                                    <!-- upload file html need to add bottom JavaScript otherwise it will not work --> 
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Radio btn</label>
                              <div class="col-md-8 toggle-button">
                                <label class="switch">
                                <input type="checkbox" checked>
                                <div class="slider round"></div>
                                </label>
                                </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Checkbox</label>
                              <div class="col-md-8 toggle-button">
                                <label class="switch">
                                <input type="checkbox">
                                <div class="slider round"></div>
                                </label>
                                </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Textarea</label>
                              <div class="col-md-8">
                                <textarea rows="1" class="form-control" placeholder="Textarea"></textarea>
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                            </div>
                          </div> </div>
                    </div>
                    <div class="modal-footer">
                      <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                      <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div> --%>
      
      
       <!-- Internal Details html -->
      <div class="clearfix">
        <div class="col-lg-12">
          <h3>Internal Details</h3>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-12 col-lg-12  myForm">

        <!-- Approver Required Check box html -->
        <div class="clearfix mb">
                <div class="ml">
                
                <div class="pull-left">
                <input type="checkbox" name="cd" id="apprverReq" onClick="selApprover()" checked>
                <label>&nbsp;&nbsp;<strong>Approver required</strong></label>
                </div>
                
                <!-- Radio buttons for Sequential or Parallel function -->
                <div  class="ml pull-left apprverReq" style="display:none;">
                <label class="rad" id="parallel">
                <input type="radio" name="Approver" checked="checked">
                <i class="fa fa-align-center" data-placement="top" data-toggle="tooltip"  title="Parallel"></i>
                </label>
                <label class="rad" id="sequential">
                <input type="radio" name="approver" onClick="sequential();">
                <i class="fa fa-retweet" data-placement="top" data-toggle="tooltip" title="Sequential"></i>
                </label>
                
                 <i data-placement="top" data-toggle="tooltip" class="glyphicon glyphicon-info-sign text-info ml" title="info"></i>
                </div>
                <!-- Radio buttons for Sequential or Parallel function -->
               
                </div>
        </div>
       <!-- Approver Required Check box html ends -->  
         
         
         <div class="form-group parallel" style="display:none;">
                <label class="col-md-3">Approver<span class="text-danger">*</span></label>
                <div class="col-md-9">
                    <select multiple class="chosen-select form-control">
                      <option disabled>Select Approver</option>
                      <option>Approver-1</option>
                      <option>Approver-2</option>
                      <option>Approver-3</option>
                    </select>
                </div>
         </div>
         
         <div class="form-group sequential" id="addLvl" style="display:none;">
                <label class="col-md-3">Approver<span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control">
                      <option disabled>Select Client</option>
                      <option>Client-1</option>
                      <option>Client-2</option>
                      <option>Client-3</option>
                    </select>
                    <span onClick="addLvl();"><i class="fa fa-level-down btn btn-default formic1" data-toggle="tooltip" data-placement="left" title="Add Level"></i></span>
                    </div>
                </div>
              </div>
              
              <div class="clearfix lvlTow" style="display:none;">
              <div class="form-group">
              <div class="col-md-3">Approver 2</div>
              <div class="col-md-9">
                <select class="chosen-select form-control">
                      <option disabled>Select Client</option>
                      <option>Client-1</option>
                      <option>Client-2</option>
                      <option>Client-3</option>
                    </select>
              </div></div>
          </div>
          <div class="form-group">
            <label class="col-md-3">Manager<span class="text-danger">*</span></label>
            <div class="col-md-9">
              <select multiple class="chosen-select form-control">
                <option>Select Manager</option>
                <option>Manager-1</option>
                <option>Manager-2</option>
                <option>Manager-3</option>
              </select>
            </div>
          </div>
          <div class="form-group">
             
            <div id="assignedToSavingDiv"></div>
													
            <label class="col-md-3">Assign To <span class="text-danger">*</span></label>
            <div class="col-md-9">
                <div class="form-icon">
                <span class="icon-inside"> <i class="fa fa-search-plus"></i>
                  <input type="text" value="Assing Role" class="form-control" data-target="#assignTeam" data-toggle="modal" onclick="getUserList();">
                  </span>
                  <span data-target="#addTeam" data-toggle="modal"> <i class="fa fa-plus btn btn-default formic1"></i> </span>
                  </div>
                <!-- Modal content Assign To -->
                <div role="dialog" id="assignTeam" class="modal fade">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Assign To</h4>
                      </div>
                       <div id="userList"></div>
                        <!-- <div class="modal-body clearfix"> -->
                       
                        <!-- <div class="form-horizontal custom-form">
                        <div class="row">
                          <div class="col-xs-12 col-md-8 col-lg-8">
                            <div class="input-group">
                              <input type="search" class="form-control" value="">
                              <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>
                          </div>
                          <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100">
                             <select class="selectpicker">
                             <option selected disabled>Select Role</option>
                              <option value="All">All</option>
                              <option value="Admin">Admin</option>
                              <option value="Account Manager">Account Manager</option>
                              <option value="Recruiting Manager">Recruiting Manager</option>
                              <option value="Hiring Manager">Hiring Manager</option>
                              <option value="Teams">Teams</option>
                             </select>
                        </div>
                        </div>
                        </div> -->
                        <!-- <div class="modal-body-custom mt clearfix">
                          <table cols="0" class="table table-bordered table-striped  clearfix">
                            <tbody>
                              <tr>
                              <thead>
                                <th><input type="checkbox"></th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Active Jobs</th>
                                </thead>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td><span class="tbldata-dotdot">Murali Krishna</span></td>
                                <td><span class="tbldata-dotdot" title="muraraikumarmahessrinivas@businessintelli.com">muraraikumarmahessrinivas@businessintelli.com</span></td>
                                <td>Account Manager</td>
                                <td>101</td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Account Manager</td>
                                <td>101</td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Account Manager</td>
                                <td>101</td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Account Manager</td>
                                <td>101</td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Account Manager</td>
                                <td>101</td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Account Manager</td>
                                <td>101</td>
                              </tr>
                            </tbody>
                          </table>
                        </div> -->
                        <!-- <div class="clearfix">
                          <ul class="pagination pull-right pagination-sm" style="margin:0;">
                            <li><a href="#">1</a></li>
                            <li class="active"><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                          </ul>
                          </div> -->
                     <!--  </div> -->
                      <!-- <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-primary" type="button">Assign</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div> -->
                    </div>
                  </div>
                </div>
                <!-- Assign To modal popup ends -->
                <!-- Modal content Demo Popup: Need to Update -->
                <div role="dialog" id="addTeam" class="modal fade">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Add Team</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal custom-form clearfix">
                          <div class="col-xs-12 col-md-12 col-lg-12">
                            <div class="form-group">
                            <label class="col-lg-4">Team Name <span class="text-danger">*</span></label>
                            <div class="col-lg-8">
                              <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-4">Team Owner <span class="text-danger">*</span></label>
                            <div class="col-lg-8">
                              <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-4">Team Members <span class="text-danger">*</span></label>
                            <div class="col-lg-8">
                              <input type="text" class="form-control" value="">
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
                <!-- Modal content Demo Popup: Need to Update ends --> 
              </div>
          </div>
          <div class="form-group">
            <label class="col-md-3">Priority<span class="text-danger">*</span></label>
            <div class="col-md-9">
              <select class="selectpicker form-control">
                <option>Select Priority</option>
                <option>Priority-1</option>
                <option>Priority-2</option>
                <option>Priority-3</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3">Application Form<span class="text-danger">*</span></label>
            <div class="col-md-9">
                <select class="chosen-select form-control">
                  <option>Select Client</option>
                  <option>Client-1</option>
                  <option>Client-2</option>
                  <option>Client-3</option>
                </select>
          </div>
        </div>
      </div>
    </div>
    <!-- Internal Details html Ends -->
      
      
    </div>
    </div>
    </form:form>
  <div class="form-footer clearfix">
    <div class="col-lg-12">
      <div class="pull-right">
        <input type="button" value="Release" class="btn btn-danger">
        <input type="button" value="Save" id="store_requirement" onclick="storeRequirement();" class="btn btn-primary">
        <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/requirementList'/>'" class="btn btn-default ml">
      </div>
    </div>
  </div>
  
  <!-- Content Area Ends-->
  <!-- Add Client Details popup-->
  				<div id="addClient" class="modal fade" role="dialog">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Client Details</h4>
                        </div>
                        <form:form id="clientDetails_form" name="clientDetails_form" method="GET">
	                        <div class="modal-body clearfix">
	                          <div class="form-horizontal custom-form clearfix">
	                            <div class="col-xs-12 col-md-12 col-lg-12">
	                              <div class="form-group">
	                                <label class="col-lg-3">Client Name<span class="text-danger">*</span></label>
	                                <div class="col-lg-9">
	                                  <input type="text" id="orgName" name="orgName" class="form-control" value="" field-name="Client Name" data-validation="required validate_Space validate_length length2-250">
	                                  <input type="hidden" id="orgLocationId" />
	                                </div>
	                              </div>
	                              <div class="form-group">
	                                <label class="col-lg-3">Website</label>
	                                <div class="col-lg-9">
	                                  <input type="text" id="webSite" name="webSite" class="form-control" value="" field-name="Client Website" data-validation="validate_Space validate_domain validate_length length2-100" data-validation-optional="true">
	                                </div>
	                              </div>
	                              
	                              <div class="form-group">
	                                <label class="col-lg-3">First Name<span class="text-danger"> *</span></label>
	                                <div class="col-lg-9">
	                                  <input type="text" id="firstName" name="firstName" class="form-control" value="" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length2-100">
	                                </div>
	                              </div>
	                              <div class="form-group">
	                                <label class="col-lg-3">Last Name</label>
	                                <div class="col-lg-9">
	                                  <input type="text" id="lastName" name="lastName" class="form-control" value="" data-validation="validate_Space validate_AlphaNumber validate_length length2-100" data-validation-optional="true">
	                                </div>
	                              </div>
	                               <div class="form-group">
	                                <label class="col-lg-3">Email<span class="text-danger"> *</span></label>
	                                <div class="col-lg-9">
	                                  <input type="text" id="email" name="email" class="form-control" value="" field-name="Client Email" data-validation="required validate_Space validate_email">
	                                </div>
	                              </div>
	                              <div class="form-group">
	                                <label class="col-lg-3">Role<span class="text-danger"> *</span></label>
	                                <div class="col-lg-9">
	                                <select class="chosen-select form-control select-bg" id="roleType" name="roleType" field-name="Client Role" data-validation="required">
	                                  	<option value="">-Select Role-</option>
										<option value="1">Admin</option>
										<option value="3">Approver/Project Manager</option>
										<option value="5">HR Manager</option>
										<option value="8">Recruiter</option>
	                                </select>
	                                </div>
	                              </div>
	                            </div>
	                          </div>
	                        </div>
	                        <div class="modal-footer">
	                        	<input type="button" value="Save" class="btn btn-primary" id="save_client" onclick="saveClient();"> 
								<input data-dismiss="modal" type="button" value="Cancel" class="btn btn-default">
	                        </div>
                         </form:form>
                      </div>
                    </div>
               </div>
               
               
               <!-- Add Client Contact Details popup-->
               <div id="addContact" class="modal fade" role="dialog">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Client Contact</h4>
                        </div>
                        <form:form id="clientContacts_form" name="clientContacts_form" method="POST">
	                        <div class="modal-body clearfix">
	                          <div class="form-horizontal custom-form clearfix">
	                            <div class="col-xs-12 col-md-12 col-lg-12">
	                            	<input type="text" class="form-control" id="vendorName" name="vendorName" value="" data-validation-optional="true" style="display: none;">
	                              <div class="form-group">
	                                <label class="col-lg-3">First Name<span class="text-danger"> *</span></label>
	                                <div class="col-lg-9">
	                                  <input type="text" class="form-control" id="firstName" name="firstName" value="" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length2-100">
	                                </div>
	                              </div>
	                              <div class="form-group">
	                                <label class="col-lg-3">Last Name</label>
	                                <div class="col-lg-9">
	                                  <input type="text" class="form-control" value="" id="lastName" name="lastName" data-validation="validate_Space validate_AlphaNumber validate_length length2-100" data-validation-optional="true">
	                                </div>
	                              </div>
	                              <div class="form-group">
	                                <label class="col-lg-3">Email<span class="text-danger"> *</span></label>
	                                <div class="col-lg-9">
	                                  <input type="text" class="form-control" value="" id="email" name="email" field-name="Email Id" data-validation="required validate_Space validate_email">
	                                </div>
	                              </div>
	                              <div class="form-group">
						              <label class="col-md-3">Owner<span class="text-danger">&nbsp;*</span></label>
						              <div class="col-md-9">
						                <select multiple class="chosen-select form-control" name="owner" id="owner" field-name="Owner"  data-validation="required">
						                <c:forEach var="user" items="${spocOwnersList}">
							                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
						                </c:forEach>
						                </select>
						              </div>
						          </div>
	                              <div class="form-group">
	                                <label class="col-lg-3">Role<span class="text-danger"> *</span></label>
	                                <div class="col-lg-9">
	                                <select class="selectpicker form-control" id="role" name="roleType" field-name="Role" data-validation="required">
		                                <option value="">-Select Role-</option>
										<option value="1">Admin</option>
										<option value="3">Approver/Project Manager</option>
										<option value="5">HR Manager</option>
										<option value="8">Recruiter</option>
	                                </select>
	                                </div>
	                              </div>
	                              <div class="form-group">
	                                <label class="col-lg-3"></label>
	                                <div class="col-lg-9">
	                                <label class="check-box checkBox">
	                                 <input type="checkbox" value="Y" name="isMailSent" checked="checked"> Send Invitation
	                                </label>
	                                </div>
	                              </div>
	                            </div>
	                          </div>
	                        </div>
	                        <div class="modal-footer">
	                        	<input type="button" value="Save" class="btn btn-primary" id="save_clientContact" onclick="saveClientContact();"> 
								<input data-dismiss="modal" type="button" value="Cancel" class="btn btn-default">
	                        </div>
                        </form:form>
                      </div>
                    </div>
                  </div>
  </div>
</section>

<script type="text/javascript">
	function storeRequirement() {
		
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		  if ($('#requirement_form').validate(false, validationSettings)){  
			var frm = $('#requirement_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			var form = $('#requirement_form')[0];
      		var formData = new FormData(form); 
			  //It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeRequirement?${_csrf.parameterName}=${_csrf.token}&json="+con,
						success : function(result) {
								location.replace(sourceleadUrl+"/requirementList");
						},
					});
			return true;
		  }else{ 
				return false;
		 }  
	} 
</script>

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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

<!-- Country,State,City Script Starts Here -->

<script type="text/javascript">
function getAllCountries(){
	var countryName = $("#existingCountry").val();
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---select---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${loginCountryName}'){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#country').trigger("chosen:updated");
            getStates();
        },
        error: function(e){
        }
    });
} 
</script>


<script>
function getStates(){
	var countryName = $("#country").val();
	
	//to set existing state value(Auto populate)
	var stateName = $("#state").val();
	//end state Auto populate
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == stateName){
	            		$('#state').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#state').trigger("chosen:updated");
	            getCities();
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	//to set existing city value(Auto populate)
	var cityName = $("#city").val();
	//end city Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == cityName){
	            		$('#city').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>



<!-- Country,State,City Script Ends Here -->


<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			
		  $("#country").chosen();
	       $("#state").chosen();
	       $("#city").chosen();
	       
	       $("#country").on('change', function (e) {
	    	   getStates();
	       });
	       $("#state").on('change', function (e) {
	    	   getCities();
	       });
		       
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
			})
			
			/* upload file script */
			document.getElementById("uploadBtn").onchange = function () {
			document.getElementById("uploadFile").value = this.value;
			};
				
				
</script> 
<!-- Eductions and Skills Autocomplete Scripts End Here -->


<script>
function getTemplateVal(){
	
    if(document.getElementById('isTemplate').checked) {
        $("#isTemplate").val('Y');
       
    }else{
        $("#isTemplate").val('N');
    }
}

</script>


<script>
function setTemplateValues(){
	
 var sourceleadUrl ='${sourceleadUrl}';
	var requirementId = document.getElementById('template').value;
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId, 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
		    	$("#jobTitle").val(json.jobTitle);
		    	$("#jobType").val(json.jobType);
		    	$("#jobId").val(json.jobId);
		    	$("#positions").val(json.positions);
		    	$("#education").val(json.education);
		    	
		    	$("#minExp").val(json.minExp);
		    	$("#maxExp").val(json.minExp);
		    	
		    	var country = '';
				 var state = '';
				 var city = '';
				
				 if(json.country != null && json.country !='' && json.country !='undefined'){
					$("#country").val(json.country);
				}
				 
				if(json.state != null && json.state !='' && json.state !='undefined'){
					$("#state").val(json.state);
				}else{
					$("#state").val('');
				}
				
				$('#city').trigger("chosen:updated");
				if(json.city != null && json.city !='' && json.city !='undefined'){
					$("#city").val(json.city);
				}else{
					$("#city").val('');
				}
				 
				getAllCountries();
				
				$('#skills').trigger("chosen:updated");
				if(json.skills != null && json.skills !='' && json.skills !='undefined'){
					$("#skills").val(json.city);
				}else{
					$("#skills").val('');
				}
		    	
		    	
		    	$("#description").val(json.description);
		    	$("#rate").val(json.rate);
		    	$("#currencyCode").val(json.currencyCode);
		    	$("#rateType").val(json.rateType);
		    	$("#fileName").val(json.fileName);
		    	
		    	
		    }
     });
}

$(function(){
    var config = {
		  '.chosen-select'           : {},
		  '.chosen-select-deselect'  : {allow_single_deselect:true},
		  '.chosen-select-no-single' : {disable_search_threshold:10},
		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		  '.chosen-select-width'     : {width:"95%"}
		}
    $("#country").chosen();
    $("#state").chosen();
    $("#city").chosen();
    
    $("#country").on('change', function (e) {
 	   getStates();
    });
   
    $("#state").on('change', function (e) {
 	   getCities();
    });
   
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});
getAllCountries();
</script>


<script>
// script for tooltip
$('[data-toggle="tooltip"]').tooltip();
///////////////////// Back To Top 
$(document).ready(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
});

// Show hide Approver script
$("#apprverReq").removeAttr("checked");
$("#sequential input").removeAttr("checked");

function selApprover(){
  if ($("#apprverReq").prop('checked') ) {
			$(".parallel, #parallel, .apprverReq").show();
			$(".sequential").hide();
			$("#parallel i").addClass("apprverReq-active");
			$("#sequential i").removeClass("apprverReq-active");

} 
else {
	$(".parallel, #parallel, .apprverReq, .sequential").hide();
}
};

$("#sequential").click(function(){
	$(".parallel").hide();
	$(".sequential").show();
	$("#parallel i").removeClass("apprverReq-active");
	$("#sequential i").addClass("apprverReq-active");
});
$("#parallel").click(function(){
	$(".parallel").show();
	$(".sequential").hide();
	$("#parallel i").addClass("apprverReq-active");
	$("#sequential i").removeClass("apprverReq-active");
});
function addLvl(){
	$(".lvlTow").toggle();
}
</script> 



<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script>
function getUserList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#userList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getUserList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#userList").removeAttr("style");
	                	    var result = '';
	                	    result = result +'<div class="modal-body clearfix">';
	                		result = result +'<div class="form-horizontal custom-form">';
	                		result = result +'     <div class="row">';
	                		result = result +'       <div class="col-xs-12 col-md-8 col-lg-8 hidden">';
	                		result = result +'         <div class="input-group">';
	                		result = result +'           <input type="search" class="form-control" value="">';
	                		result = result +'           <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>';
	                		result = result +'       </div>';
	                		result = result +'       <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100">';
	                		result = result +'          <select class="">';
	                		result = result +'          <option selected disabled>Select Role</option>';
	                		result = result +'           <option value="All">All</option>';
	                		result = result +'           <option value="Admin">Admin</option>';
	                		result = result +'           <option value="Account Manager">Account Manager</option>';
	                		result = result +'           <option value="Recruiting Manager">Recruiting Manager</option>';
	                		result = result +'           <option value="Hiring Manager">Hiring Manager</option>';
	                		result = result +'           <option value="Teams">Teams</option>';
	                		result = result +'          </select>';
	                		result = result +'     </div>';
	                		result = result +'     </div>';
	                		result = result +'     </div>';
	                		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter">'; 
	                		result = result +'       <table id="example" class="display" cellspacing="0" width="100%" >';
	                		result = result +'           <thead>';
	                		result = result +'           <tr>';
	                		result = result +'             <th><input type="checkbox"></th>';
	                		result = result +'             <th>Name</th>';
	                		result = result +'             <th>Email</th>';
	                		result = result +'             <th>Role</th>';
	                		result = result +'             <th>Active Jobs</th>';
	                		result = result +'           </tr>';
	                		result = result +'           </thead>';
		                    result = result +"<tbody>"; 
		                    var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 result = result +'<tr>';
		                    	/*  var localCheckedStatus;
		                    	 $(".defaultCheckBox").each(function() {
		         							var localId = this.id;
		         							if(localId == opt.id){
		         								localCheckedStatus = true;
		         							} 
		                    	 });
		                    	 alert("localCheckedStatus"+localCheckedStatus); */
		                    	 result = result +'             <td><input id="'+opt.id+'" value="'+opt.id+'"  type="checkbox" name="team" class="myCheckbox" ></td>';
		                    	 result = result +'             <td>'+opt.firstName+' '+opt.lastName+'</td>';
		                    	 result = result +'             <td>'+opt.email+'</td>';
		                    	 result = result +'             <td>'+opt.id+'</td>';
		                    	 result = result +'             <td>'+opt.id+'</td>';
		                    	 result = result +'           </tr>';
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	 /*result = result +'       <ul class="pagination pull-right pagination-sm" style="margin:0;">';
	                 	 result = result +'         <li><a href="#">1</a></li>';
	                 	 result = result +'         <li class="active"><a href="#">2</a></li>';
	                 	 result = result +'         <li><a href="#">3</a></li>';
	                 	 result = result +'         <li><a href="#">4</a></li>';
	                 	 result = result +'         <li><a href="#">5</a></li>';
	                 	 result = result +'       </ul>'; */
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="assignMembers();">Assign</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>';
	                 	 result = result +'   </div>'; 
	                 	 $("#userList").empty(); 
	                     $("#userList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
                	 $("#userList").removeAttr("style");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No UserList Found</p>";
					 result = result+"			 </div>";
					
					$("#userList").empty();
				    $("#userList").append(result); 
                }
          },
            
       }); 
  } 
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to requirementList.
     1.usage     : used in candidateList() function.
--%>

<script>
function pagination(){
    $("#example").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[2, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>

<script>
function assignMembers() {
	var result = "";
	var oTable = $('#example').dataTable();
	var rowcollection =  oTable.$(".myCheckbox:checked", {"page": "all"});
	var count = 1;
	rowcollection.each(function(index,elem){
	    var checkbox_value = $(elem).val();
	    result = result+ "<input type='hidden' class='defaultCheckBox' name='assignUserId"+count+"' id='assignUserId"+count+"' value='"+checkbox_value+"' />";
	    count++;
	});
	result = result+"<input type='hidden' name='assignedToMaxCount' id='assignedToMaxCount' value='"+count+"'/>";
	$("#assignedToSavingDiv").empty();
	$("#assignedToSavingDiv").append(result); 
}
</script>



<jsp:include page="../masterFooter.jsp" />