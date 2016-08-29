<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>

<style>
.clsDatePicker {
	z-index: 100000 !important;
}
</style>

	<div id="tab-5" class="project_tabs_inn">
              <div class="project_tabs_inn_head">
              	<a href="#" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Certification</a>
                <!-- Popup Div -->
                    <div class="collapse-div-adduser col-md-4"> <i class="fa fa-close"></i>
                    <form:form id="Candidate_certification_form" name="Candidate_certification_form" method="post">
                          <div class="form-horizontal clearfix">
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="candidatename">
                                <input type="text" class="form-control input-sm" value="" name="name"  placeholder="Name *" name="Name"
                                 data-validation="required validate_Space validate_length length2-100" onblur="validatename();">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" name="provider" placeholder="Provider *"
                                 data-validation="required validate_Space validate_length length2-100">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" name="score" placeholder="Score" 
                                data-validation="required validate_Space validate_length length2-100">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <textarea class="form-control" rows="1" name ="description"  placeholder="Description"
                                data-validation="required validate_Space validate_length length2-100"></textarea>
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-6 positionRelative">
                                <input type="text" class="form-control input-sm" value="" name ="validFrom" placeholder="Valid From" id="validFrom"
                                data-validation="required" readonly="readonly">
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
                              </div>
                              <div class="col-md-6 positionRelative">
                                <input type="text" class="form-control input-sm" value=""  name="validTo" placeholder="Validate To" id="validTo"
                                data-validation="required" readonly="readonly">
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
                              </div>
                            </div>
                            <div class="clearfix mr">
                              <button type="submit" class="btn btn-sm btn-primary pull-right"  onclick="storeCertificate();" id="storeCandidateCertificate"
                              value="storeCandidateCertifications" >Add</button>
                            </div>
                          </div>
                         </form:form>
                        </div>
					<!-- Popup Div -->
              </div>

              <div class="project_tabs_inn_table vendor_wrap_inn_table">
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
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>TCS Pvt Ltd</span></div></td>
                      <td><div><span>Web developer</span></div></td>
                      <td><div><span>300</span></div></td>
                      <td><div><span>Description Description</span></div></td>
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <nav>
                <ul id="pagingg" class="pagination">
                  <li><a href="#">Previous</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li class="disabled"><a href="#">6</a></li>add<li data-page="last"><a href="#">Next</a></li>
                </ul>
                <div class="clearfix"></div>
              </nav>
            </div>  
            
            
            
<!-- this script is  to store candidate certificate functionality Start-->
<script type="text/javascript">
	function storeCertificate() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#Candidate_certification_form').validate(false, validationSettings)) {
			var frm = $('#Candidate_certification_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			 //It will escape all the special characters
		     var jsonData = encodeURIComponent(con);
			  
			var sourceleadUrl = '${sourceleadUrl}';
			$("#storeCandidateCertificate").attr("disabled", "disabled");
		}
	}
	
	
</script>
<script type="text/javascript">
 function validatename(){
	 var name = document.getElementById(candidatename);
	 $('#candidatename').after("hihdvcsjvc");
	 alert("my name is shiva");
 }


</script>
















