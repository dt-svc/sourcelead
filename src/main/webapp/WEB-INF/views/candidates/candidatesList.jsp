<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="candidatesProfile.jsp"></jsp:include>
<jsp:include page="candidatesResumes.jsp"></jsp:include>
<jsp:include page="candidatesExperience.jsp"></jsp:include>
<jsp:include page="candidatesEducation.jsp"></jsp:include>
<jsp:include page="candidatesCertificates.jsp"></jsp:include>
<jsp:include page="candidatesActivities.jsp"></jsp:include>


<script>
 window.onload = function() {
 	 candidateList();
};
</script>

<!-- candidateList script -->
<script>
  function candidateList(){ 	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#candidateList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getCandidatesListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}",        
            success :function(data) {
                 if(data != null && data != 'undefined' && data != ""){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#candidateList").removeAttr("style");
	                	 $("#candidateList").empty();
	                	 var result = "";
	                	 result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th><input type='checkbox' id='checkbox1'/></th>"; 
	                     result = result +"    <th>Name</th>"; 
	                     result = result +"    <th>Designation</th>"; 
	                     result = result +"    <th>Position type</th>"; 
	                     result = result +"    <th>Email</th>"; 
	                     result = result +"    <th>Mobile</th>"; 
	                     result = result +"    <th>Address</th>"; 
	                     result = result +"    <th>Last Update</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
		                     result = result +"<tr>";
			                 result = result +"  <td><input type='checkbox' id='checkbox12'/></td>";
			                 result = result +"  <td><div class='proj_show' onclick='clientSideBar("+opt.id+");'><span>"+opt.firstName+" "+opt.lastName+"</span><i class='fa fa-binoculars'></i></div></td>";
			                 result = result +"  <td><div><span>"+opt.designation+"</span></div></td>";
			                 result = result +"  <td><div>"+opt.designation+"</div></td>";
			                 result = result +"  <td><div ><span>"+opt.email+"</span></div></td>";
			                 result = result +"  <td><div>"+opt.mobilePhone+"</div></td>";
			                 result = result +"  <td><div><span title='"+opt.address+"'>"+opt.address+"</span></div></td>";
			                 result = result +"  <td><div>"+opt.updatedDate+"</div></td>";
			                 result = result +"</tr>"; 
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
                	 $("#candidateList").removeAttr("style");
  	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Candidates Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#candidateList").empty();
				    $("#candidateList").append(result); 
                 }
          },
            
       }); 
  } 
</script>

<script>
function pagination(){  
    $('#candidatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    } );
}
</script>

<script>
function clientSideBar(candidateId){ 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getCandidateById?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
           success :function(data) {  
                if(data != null && data != 'undefined' && data != ""){  
                	 var json = JSON.parse(data);
              		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
              		 else if (data.indexOf('exception') >= 0) {
              			 location.replace(sourceleadUrl+"/errorPage");
              		 }
              		 else{
	                	 $("#candidateSideDiv").empty();
	                	 var skillsList = json.skillsList;
			             var skills = [];
			             skills = skillsList.split(",");
			             
	                	 var result = "";
			             result = result+'  <div class="profile_details clearfix" style="margin-bottom:20px;">';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                   <div class="media">';
			             
			             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
			             result = result+'<div class="well text-center clearfix">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 result = result+'   <img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/${SourceLeadWeb_userId}"/>" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 result = result+'  <div class="btn-group uploadfile">';
						 result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary">';
					     result = result+'    Upload / Change<input type="file" name="file" id="uploadedImage"  onchange="loadFile(event)" style="display: none;">';
					     result = result+'  </label>';
					     if(json.fileName != null && fileName != '' && fileName != 'undefined'){
						     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
							 result = result+'  <i class="fa fa-trash"></i></button>';
					     }
						 result = result+'  </div>';
						 result = result+'  </div>';
						 result = result+'  </div>';
						 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not allowed</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>';
						 result = result+' </div>';
						 result = result+'<div id="popUp" ></div>  ';
						 
						  
						  
			            /*  result = result+'                     <div class="media-left image-parent">';
			             result = result+'                     <div class="imageParent">'; */
			             /* result = result+'                     	<img src="./images/24.jpg" alt="candidate" title="Candidate">'; */
			             /* result = result+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/${SourceLeadWeb_userId}"/>"  alt="Profile Image" class="img-circle img-responsive">';
			             result = result+'                     </div>';
			             result = result+'                     </div>'; */
			             result = result+'                     <div class="media-body candidate-details">';
			             result = result+'                       <h4 class="media-heading">Jaden Smith</h4>';
			             result = result+'                       <ul class="list-unstyled">';
			             result = result+'                         <li><strong>'+json.firstName+' '+json.firstName+'</strong></li>';
			             result = result+'                         <li>Experience - '+json.experienceYears+' years '+json.experienceMonths+' months </li>';
			             result = result+'                       </ul>';
			             
			             
			             result = result+'                 <div class="clearfix">';
			             result = result+'                 <div class="professinal-details">';
			             
			            
			             result = result+'<ul class="list-inline list-unstyled">';
			             for (i=0;i < skills.length;i++)
			             {
			            	 result = result+'<li>'+skills[i]+'</li>';
			             }
			             result = result+'</ul>';
			             
			             result = result+'                 </div>';
			             result = result+'                 <div class="socialnetwork-details">';
			             result = result+'                 <ul class="list-inline list-unstyled">';
			             result = result+'                                 <li class="bg-facebook"><a href="javascript:void(0)"> <i class="fa fa-facebook-square"></i></a></li>';
			             result = result+'                                 <li class="bg-twitter"><a href="javascript:void(0)"> <i class="fa fa-twitter-square"></i></a></li>';
			             result = result+'                                 <li class="bg-google "><a href="javascript:void(0)"> <i class="fa fa-google-plus-square"></i></a></li>';
			             result = result+'                                 <li class="bg-linked"><a href="javascript:void(0)"> <i class="fa fa-linkedin-square"></i></a></li>';
			             result = result+'                                 <li class="bg-web"><a href="javascript:void(0)"> <i class="fa fa-globe"></i></a></li>';
			             result = result+'                               </ul>';
			             result = result+'                 </div>';
			             result = result+'                 </div>';
			             result = result+'               </div>';
			             result = result+'                   </div>';
			             result = result+'                 </div>';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                 <ul class="list-unstyled can-perdt">';
			             result = result+'                   <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">'+json.email+'</a></li>';
			             result = result+'                   <li><i class="fa fa-phone"></i>+'+json.mobilePhone+'</li>';
			             result = result+'                   <li><i class="fa fa-location-arrow"></i>'+json.address+'</li>';
			             result = result+'                   <li><i class="fa fa-rupee"></i>'+json.currentRate+'/Per Hour - Current Rate / Salary</li>';
			             result = result+'                   <li><i class="fa fa-rupee"></i>'+json.expectedRate+'/Per Hour - Expected Rate / Salary</li>';
			             result = result+'                 </ul>';
			             result = result+'               </div>';
			             result = result+'			</div>';
			             result = result+'   <div class="proj_close"></div>';
			             result = result+'   <div class="project_side_wrap_inn_tabs">';
			             result = result+'     <ul class="project-tabs-menu">';
			             result = result+'       <li class="current"><a href="#tab-1" onclick="candidateProfile('+candidateId+');">Profile</a></li>';
			             result = result+'       <li><a href="#tab-2" onclick="candidateResumesList('+candidateId+'); candidateResume('+candidateId+');" >Resume</a></li>';
			             result = result+'       <li><a href="#tab-3" onclick="candidateExperience('+candidateId+');">Experience</a></li>';
			             result = result+'       <li><a href="#tab-4" onclick="candidateEducation('+candidateId+');">Education</a></li>';
			             result = result+'       <li><a href="#tab-5" onclick="candidateCertificate('+candidateId+');">Certificate</a></li>';
			             result = result+'       <li><a href="#tab-6" onclick="candidateActivities('+candidateId+');">Activities</a></li>';
			             result = result+'       <div class="clearfix"></div>';
			             result = result+'     </ul>';
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
						 
						 result = result + '<div id="tab-6" class="project_tabs_inn">';
						 result = result + '</div>';
			             result = result+'     </div>';
			             result = result+'   </div>';
			             
			             $("#candidateSideDiv").empty();  
			 		     $("#candidateSideDiv").append(result);  
				 		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
						 $(".side_mask").fadeIn(200);
						 $("body").css('overflow-y', 'hidden');
						 candidateProfile();
			 			 
			    	} 
                }else{
               	 $("#candidateSideDiv").removeAttr("style");
 	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Candidates Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#candidateSideDiv").empty();
				    $("#candidateSideDiv").append(result); 
                }
         },
           
      }); 
				
}
</script>
<!-- 
$(document).ready(function() {
    $(".proj_show").click(function(){  
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
}); -->


<!-- Content Area --> 
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Candidate list</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Client Contact Details</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head candidateHead">
        <ul>
          <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>
         <!--  <li><a href="javascript:void(0)" title=""><i class="fa fa-envelope"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-mail-forward"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-upload"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-download"></i></a></li> -->
         <li><a href="<c:url value='/candidate'/>" title=""><i class="fa fa-plus"></i>Add Candidate</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
        
           <!--  <div class="remove_search">
            	<input type="text" placeholder="search" />
            </div>     -->      
        
      </div>
      <div class="candidate_wrap_table">
        <div id="candidateList"></div>
      </div>
      <!--   <nav>
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
      </nav> -->
    </div>
    
    <!-- Compnay details -->
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
	    <div class="clients_side_wrap_inn">
		             <div id="candidateSideDiv">
				     </div>  
	    </div>			     
      <%-- <div class="clients_side_wrap_inn">
       <div class="profile_details clearfix" style="margin-bottom:20px;">
                    <div class="col-md-6">
                        <div class="media">
                          <div class="media-left image-parent">
                          <div class="imageParent">
                          	<img src="./images/24.jpg" alt="candidate" title="Candidate">
                          </div>
                          </div>
                          <div class="media-body candidate-details">
                            <h4 class="media-heading">Jaden Smith</h4>
                            <ul class="list-unstyled">
                              <li><strong>Business Analyst - Full Time</strong></li>
                              <li>Experience - 4 years</li>
                            </ul>
                        </div>
                      </div>
                      <div class="other-detials">
                      <div class="professinal-details">
                      	<ul class="list-inline list-unstyled"><li>BA</li><li>BRS</li><li>SRS</li><li>Agile</li><li>SDLC</li></ul>
                      </div>
                      <div class="socialnetwork-details">
                      <ul class="list-inline list-unstyled">
                                      <li class="bg-facebook"><a href="javascript:void(0)"> <i class="fa fa-facebook-square"></i></a></li>
                                      <li class="bg-twitter"><a href="javascript:void(0)"> <i class="fa fa-twitter-square"></i></a></li>
                                      <li class="bg-google "><a href="javascript:void(0)"> <i class="fa fa-google-plus-square"></i></a></li>
                                      <li class="bg-linked"><a href="javascript:void(0)"> <i class="fa fa-linkedin-square"></i></a></li>
                                      <li class="bg-web"><a href="javascript:void(0)"> <i class="fa fa-globe"></i></a></li>
                                    </ul>
                      </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <ul class="list-unstyled can-perdt">
                        <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">jadensmith@hotmail.com</a></li>
                        <li><i class="fa fa-phone"></i>+91 9900223355</li>
                        <li><i class="fa fa-location-arrow"></i>Hyderabad, TS, India</li>
                        <li><i class="fa fa-rupee"></i>200/Per Hour - Current Rate / Salary</li>
                        <li><i class="fa fa-rupee"></i>300/Per Hour - Expected Rate / Salary</li>
                      </ul>
                    </div>
				</div>
        <div class="proj_close"></div>
        <div class="project_side_wrap_inn_tabs">
          <ul class="project-tabs-menu">
            <li class="current"><a href="#tab-1" onclick="candidateProfile();">Profile</a></li>
            <li><a href="#tab-2">Resume</a></li>
            <li><a href="#tab-3">Experience</a></li>
            <li><a href="#tab-4">Education</a></li>
            <li><a href="#tab-5">Certificate</a></li>
            <li><a href="#tab-6">Activities</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="project_tabs">
                <jsp:include page="candidatesProfile.jsp"></jsp:include>
                <jsp:include page="candidatesResumes.jsp"></jsp:include>
                <jsp:include page="candidatesExperience.jsp"></jsp:include>
                <jsp:include page="candidatesEducation.jsp"></jsp:include>
                <jsp:include page="candidatesCertificates.jsp"></jsp:include>
                <jsp:include page="candidatesActivities.jsp"></jsp:include>
          </div>
        </div>
      </div> --%>
    </div>
    <!-- Compnay details Ends --> 
    <!-- Project --> 
  </div>
</section>


<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 
 
<script>

// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });

	// Levels adding script
	 $(".addUser-org").click(function(event){  
		 $(".collapse-div-adduser").toggleClass("showMydiv");
		 $(".collapse-div").removeClass("showMydiv");
		 event.preventDefault();
	 });
	 $(".close-div").click(function(){
		 $(this).parent().toggleClass("showMydiv");
		 });
	 $(".assign-org").click(function(){
		 $(".collapse-div").toggleClass("show-div");
		 $(".collapse-div-adduser").removeClass("show-div");
	 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div").click(function(e){
		 e.stopPropagation();
	 });
	 $('html').click(function(){
	    $(".collapse-div-adduser").removeClass("show-div");
		$(".collapse-div").removeClass("show-div");
	   });	
	   $("#endDate").click(function(){
		   $(".endDate").toggle(200);
	   });
});
</script>	


<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script type="text/javascript">
function deleteImage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>


<script type="text/javascript">
function deleteProfileImage(candidateId){  alert("hai delete");
	var sourceleadUrl ='${sourceleadUrl}';
	
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteCandidateImage&candidateId="+candidateId,
	        success: function(data) {
	        	 var result = data;
	        	 alert("result"+result);
	       },
	        
     }); 
}
</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>



<script type="text/javascript">
  var loadFile = function(event) {
    var output = document.getElementById('userImage');
    
    var uploadedImage = document.getElementById('uploadedImage').value;
    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
    document.getElementById("errorImage").style.display="none"; 
    
    if(uploadedImage.substring(uploadedImage.lastIndexOf(".")+1) != 'jpg'){
    	output.src = URL.createObjectURL(event.target.files[0]);
    	
    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
 		var msg = "."+extensionName+" extension not allowed";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#profile_submit").attr("disabled", "disabled");
    	
    }else if(sizeinbytes > 200000){
    	output.src = URL.createObjectURL(event.target.files[0]);
 		var msg = "Image size should be less than 200 KB";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#profile_submit").attr("disabled", "disabled");
    	
    }else{
    	output.src = URL.createObjectURL(event.target.files[0]);
    	$("#profile_submit").removeAttr("disabled")
    }
    
  };
</script>

<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


 
<jsp:include page="../masterFooter.jsp" />