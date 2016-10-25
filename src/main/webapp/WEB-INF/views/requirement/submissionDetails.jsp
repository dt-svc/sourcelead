<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
 <jsp:include page="employeeSubmissionDetails.jsp" />
 <jsp:include page="educationSubmissionDetails.jsp" />
 <jsp:include page="certificationSubmissionDetails.jsp" />
 
<script>
function getSubmissionDetailsSideBar(candidateId){
	var result = "";
	       
	          result = result + ' <div class="req_hide">';
	               result = result + '<div class="profile_details clearfix">';
	               result = result + '<div class="col-md-6">';
	               result = result + ' <div class="media">';
	               result = result + '<div class="media-left image-parent">';
	               result = result + '<div class="imageParent"><img src="./images/24.jpg" alt="candidate" title="Candidate"></div>';
	               result = result + ' </div>';
	               result = result + ' <div class="media-body candidate-details">';
	               result = result + ' <h4 class="media-heading">Jaden Smith</h4>';
	               result = result + ' <ul class="list-unstyled">';
	               result = result + ' <li><strong>Business Analyst - Full Time</strong></li>';
	               result = result + ' <li>Experience - 4 years</li>';
	               result = result + ' </ul>';
	               result = result + '</div>';
	               result = result + '</div>';
	               result = result + '<div class="other-detials">';
	               result = result + '<div class="professinal-details">';
	               result = result + ' <ul class="list-inline list-unstyled">';
	               result = result + '<li>BA</li>';
	               result = result + ' <li>BRS</li>';
	               result = result + '<li>SRS</li>';
	               result = result + ' <li>Agile</li>';
	               result = result + '<li>SDLC</li>';
	               result = result + ' </ul>';
	               result = result + '</div>';
	               result = result + '<div class="socialnetwork-details">';
	               result = result + '<ul class="list-inline list-unstyled">';
	               result = result + '<li class="bg-facebook"><a href="javascript:void(0)"> <i class="fa fa-facebook-square"></i></a></li>';
	               result = result + '<li class="bg-twitter"><a href="javascript:void(0)"> <i class="fa fa-twitter-square"></i></a></li>';
	               result = result + '<li class="bg-google "><a href="javascript:void(0)"> <i class="fa fa-google-plus-square"></i></a></li>';
	               result = result + '<li class="bg-linked"><a href="javascript:void(0)"> <i class="fa fa-linkedin-square"></i></a></li>';
	               result = result + ' <li class="bg-web"><a href="javascript:void(0)"> <i class="fa fa-globe"></i></a></li>';
	               result = result + ' </ul>';
	               result = result + ' </div>';
	               result = result + ' </div>';
	               result = result + '</div>';
			       result = result + ' <div class="col-md-6">';
			       result = result + '<ul class="list-unstyled can-perdt">';
			       result = result + ' <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">jadensmith@hotmail.com</a></li>';
			       result = result + '<li><i class="fa fa-phone"></i>+91 9900223355</li>';
			       result = result + '<li><i class="fa fa-location-arrow"></i>Hyderabad, TS, India</li>';
			       result = result + '<li><i class="fa fa-rupee"></i>200/Per Hour - Current Rate / Salary</li>';
			       result = result + '<li><i class="fa fa-rupee"></i>300/Per Hour - Expected Rate / Salary</li>';
			       result = result + '</ul>';
			       result = result + '</div>';
			       result = result + '</div>';
	       		
			       result = result + ' <div class="project_side_wrap_inn_tabs">';
			       result = result + '<ul class="new-tabs">';
			       result = result + ' <li class="current"><a href="#tabb-1">Details</a></li>';
			       result = result + ' <li><a href="#tabb-2">Profile</a></li>';
			       result = result + ' <li><a href="#tabb-3">Resume</a></li>';
			       result = result + '<li><a href="#tabb-4" onclick="employeeSubmissionDetails('+candidateId+');">Employer</a></li>';
			       result = result + '<li><a href="#tabb-5" onclick="educationSubmissionDetails('+candidateId+');">Education</a></li>';
			       result = result + '<li><a href="#tabb-6" onclick="certificationSubmissionDetails('+candidateId+');">Certifications</a></li>';
			       result = result + '<li><a href="#tabb-7">Activities</a></li>';
			       result = result + '<div class="clearfix"></div>';
			       result = result + '</ul>';
			       result = result + '<div class="project_tabs">';
			       
			       result = result + '<div id="tabb-4" class="new-tabs_inn" style="display: block;">';
			       result = result + '</div>';
			       
			       result = result + '<div id="tabb-5" class="new-tabs_inn" style="display: block;">';
			       result = result + '</div>';
			       
			       result = result + '<div id="tabb-6" class="new-tabs_inn" style="display: block;">';
			       result = result + '</div>';
			       
			       result = result + '<div id="tabb-1" class="new-tabs_inn" style="display: block;">';
	
			       result = result + '<div class="require_wrap_details">';
			       result = result + '<div class="clearfix">';
			       result = result + '<div class="new-tabs_inn_head addUser-org"> <a href="javascript:void(0)" class="mr"><i class="glyphicon glyphicon-pencil"></i>Edit</a> <a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i>Remove</a> ';
			               // Popup Div: Edit Candidate
			                result = result + ' <div class="edit-details col-md-8 edit-candaite"> <i class="fa fa-close close-div"></i>';
			                result = result + '<div class="form-horizontal clearfix">';
			                result = result + '  <div class="clearfix mb10">';
			                result = result + '<div class="col-md-6">';
			                result = result + ' <input class="form-control input-sm" value="" placeholder="First Name" type="text">';
			                result = result + ' </div>';
			                result = result + ' <div class="col-md-6">';
			                result = result + '<input class="form-control input-sm" value="" placeholder="Last Name" type="text">';
			                result = result + '</div>';
			                result = result + '</div>';
			                result = result + '<div class="clearfix mb10">';
			                result = result + '<div class="col-md-6">';
			                result = result + '<input class="form-control input-sm" value="" placeholder="Email" type="text">';
			                result = result + '</div>';
			                result = result + '<div class="col-md-6">';
			                result = result + '<input class="form-control input-sm" value="" placeholder="Mobile" type="text">';
			                result = result + ' </div>';
			                result = result + ' </div>';
			                result = result + '<div class="clearfix mb10">';
			                result = result + ' <div class="col-md-6 custom-fields">';
			                result = result + '  <select class="form-control select-arrow select-bg">';
			                result = result + ' <option>India</option>';
			                result = result + '  <option>United States</option>';
			                result = result + '  <option>UK</option>';
			                result = result + ' </select>';
			                result = result + '</div>';
			                result = result + '<div class="col-md-6 custom-fields select-arrow">';
			                result = result + '<select class="form-control input-sm">';
			                result = result + '   <option>India</option>';
			                result = result + '  <option>United States</option>';
			                result = result + '  <option>UK</option>';
			                result = result + '  </select>';
			                result = result + ' </div>';
			                result = result + '</div>';
			                result = result + ' <div class="clearfix mb10">';
			                result = result + '   <div class="col-md-6">';
			                result = result + '   <input class="form-control input-sm" value="" placeholder="City" type="text">';
			                result = result + ' </div>';
			                result = result + '<div class="col-md-6">';
			                result = result + ' <input class="form-control input-sm" value="" placeholder="Skills" type="text">';
			                result = result + '</div>';
			                result = result + ' </div>';
			                result = result + '<div class="clearfix mb10">';
			                result = result + ' <div class="col-md-12">';
			                result = result + '  <textarea class="form-control input-sm" placeholder="Summary" rows="2" cols="0">    ';                          
			                result = result + '       </textarea>';
			                result = result + ' </div>';
			                result = result + ' </div>';
			                result = result + ' <div class="clearfix mr">';
			                result = result + '<div class="pull-right">';
			                result = result + '<button type="submit" class="btn btn-sm btn-primary mr">Save</button>';
			                result = result + '<button type="submit" class="btn btn-sm btn-danger">Cancel</button>';
			                result = result + ' </div>';
			                result = result + ' </div>';
			                result = result + '</div>';
			                result = result + '</div>';
			                
			               //Popup Div 
			                
			               
		   result = result + '</div> ';        
		   result = result + '<h1>Summary:</h1>';
		   result = result + '<textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea>';
		   result = result + ' <div class="row">';
		   result = result + '<div class="col-md-6">';
		   result = result + '<div id="accordion-sl" class="release-jobs">';
			                    
						   // Accordion First Tab 
						   result = result + '<div class="clearfix">';
						   result = result + ' <h2 class="active">Profile</h2>';
						   result = result + ' <div class="content clearfix" style="display: block;">';
						   result = result + '	d fadsfsdfsdfs dfsdf ';
						   result = result + ' </div>';
						   result = result + ' </div>';
						   
			               // Accordion second Tab 
			                    
		                    
		                    result = result + ' </div>';
		                    result = result + ' </div>';
		                    result = result + '<div class="col-md-6">';
		                    
		                     // Select Skills
			               
			                result = result + ' <div class="divskill"> ';
			                
			                    // Add skills div
			                    
			                    result = result + ' <div class="addSkills"> <a href="#" class="btn-addskills" title="Add Skills"> <i class="fa fa-code"></i>Add Skills </a>';
			                    result = result + ' <div class="col-md-7 add-skills"> <i class="fa fa-close close-div"></i>';
			                    result = result + '  <div class="form-horizontal clearfix">';
			                    result = result + ' <div class="clearfix div-padding">';
			                    result = result + '<div class="col-md-10">';
			                    result = result + '  <input placeholder="Skill..." value="" class="form-control input-sm" type="text">';
			                    result = result + '  </div>';
			                    result = result + '  <div class="col-md-2">';
			                    result = result + '   <button class="btn btn-sm btn-primary pull-right" type="submit">Add</button>';
			                    result = result + '  </div>';
			                    result = result + '</div>';
			                    result = result + '  </div>';
			                    result = result + '</div>';
			                    result = result + '</div>';
			                    
			                   // Add skills div ends
			                   
			                    result = result + '<table class="table table-responsive table-hover clearfix">';
			                    result = result + ' <thead>';
			                    result = result + '<tr>';
			                    result = result + ' <th>Skills</th>';
			                    result = result + '<th>Level</th>';
			                    result = result + ' <th>Experience</th>';
			                    result = result + '<th>Action</th>';
			                    result = result + '</tr>';
			                    result = result + '</thead>';
			                    result = result + '<tbody>';
			                    result = result + '<tr>';
			                    result = result + '<td><p class="form-control-static">Java</p></td>';
			                    result = result + '<td><select class="tbldata-dotdot-me width100percnt">';
			                    result = result + '<option selected="" disabled="">Select</option>';
			                    result = result + '<option>Beginner</option>';
			                    result = result + '<option>Familiar</option>';
			                    result = result + '<option>proficient</option>';
			                    result = result + '<option>Expert</option>';
			                    result = result + '<option>Master</option>';
			                    result = result + '</select></td>';
			                    result = result + '<td><select class="tbldata-dotdot-me width100percnt">';
			                    result = result + '<option selected="" disabled="">Select</option>';
			                    result = result + '<option>0</option>';
			                    result = result + '<option>1</option>';
			                    result = result + '<option>2</option>';
			                    result = result + '</select></td>';
			                    result = result + '<td><div class="text-center"> <i class="fa fa-trash"></i> </div></td>';
			                    result = result + '</tr>';
			                        
			                    result = result + '</tbody>';
			                    result = result + '</table>';
			                    result = result + '<a href="javascript:void(0)">more <i class="fa fa-angle-right"></i></a>';
			                    result = result + ' </div>';
			                    
			                //Select Skills
	
			                result = result + '</div>';
			                result = result + '</div>';
			                result = result + '</div>';
			                result = result + ' </div>';
			                result = result + '</div>';
			       
			       
			       result = result + '</div>';
			       result = result + '</div>';
	       
	          result = result + '</div>';
  
  
  $("#viewCandidateDiv").empty();  
  $("#viewCandidateDiv").append(result);
  
 $(".clients_side_wrap").addClass("clients_side_wrap_active");
 $(".side_mask").fadeIn(200);
 $("body").css('overflow-y', 'hidden');
  
  
	

}
</script>


<script>
    $(".viewCandidate_close, .side_mask").click(function() {
    	$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<script>
$(document).ready(function() {
	$("#tabb-1").show()
    $(".new-tabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".new-tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

</script>







