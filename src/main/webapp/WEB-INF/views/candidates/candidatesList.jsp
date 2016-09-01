<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

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

<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>

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
	                    	 var mobilePhone = "";
	                    	 var designation ="";
	                    	 var address="";
	                    	 if(opt.mobilePhone != null && opt.mobilePhone != 'undefined' && opt.mobilePhone != ""){
	                    		 mobilePhone = opt.mobilePhone;
				            	}else{
				            		mobilePhone = "NO DATA";
				            	}
				            	if(opt.designation != null && opt.designation != 'undefined' && opt.designation != ""){
				            		designation = opt.designation;
				            	}else{
				            		designation = "NO DATA";
				            	}
				            	if(opt.address != null && opt.address != 'undefined' && opt.address != ""){
				            		address = opt.address;
				            	}else{
				            		address = "NO DATA";
				            	}
				            	
		                     result = result +"<tr>";
			                 result = result +"  <td><input type='checkbox' id='checkbox12'/></td>";
			                 result = result +"  <td><div class='proj_show' onclick='clientSideBar("+opt.id+");'><span>"+opt.firstName+" "+opt.lastName+"</span><i class='fa fa-binoculars'></i></div></td>";
			                 result = result +"  <td><div><span>"+designation+"</span></div></td>";
			                 result = result +"  <td><div>"+opt.designationType+"</div></td>";
			                 result = result +"  <td><div ><span>"+opt.email+"</span></div></td>";
			                 result = result +"  <td><div>"+mobilePhone+"</div></td>";
			                 result = result +"  <td><div><span title='"+address+"'>"+address+"</span></div></td>";
			                 result = result +"  <td><div>"+opt.candidateUpdateDateToString+"</div></td>";
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

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to candidateList.
     1.usage     : used in candidateList() function.
--%>

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

<%-- 
     @author : sarath kumar.
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at candidateList() function.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

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
	                	 
	                	 result = result+'<div id="popUp" ></div>  ';
	                	
	                	 
	                	 result = result+'<form id="candidate_form" name="candidate_form" method="post" >';
			             result = result+'  <div class="profile_details clearfix" style="margin-bottom:20px;">';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                   <div class="media">';
			             
			             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
			             result = result+'<div id="candidateImageId">';
			             result = result+'<div class="well text-center clearfix">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 result = result+'   <img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+candidateId+'"/>" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 result = result+'  <div class="btn-group uploadfile">';
						 result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary">';
					     result = result+'    Upload / Change<input type="file" name="file" id="uploadedImage"  onchange="loadFile(event,\''+candidateId+'\')" style="display: none;">';
					     result = result+'  </label>';
					     /*  if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
						     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
							 result = result+'  <i class="fa fa-trash"></i></button>';
					     } */
						 result = result+'  </div>';
						 result = result+'  </div>';
						 /*  result = result+'  <div class="pull-right" style="position:relative; top:10px; right:-10px;">';
						 result = result+'  <a href="#" title="Save"><i class="fa fa-check-circle" onClick="saveCandidateImage('+candidateId+');" ></i></a>';
						 result = result+'  <a href="#" title="Close"><i class="fa fa-close text-danger"></i></a>';
						 result = result+'  </div>'; */
						 result = result+'  </div>';
						 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not allowed</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>';
						 result = result+' </div>';
						 result = result+' </div>';
						 result = result+'</form>';
						  
						  
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
						 candidateProfile(candidateId);
			 			 
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

<%-- 
     @author : sarath kumar.
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at saveOrCancel() function. line number 595.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function saveCandidateImage(candidateId){
	            		
          		var frm = $('#candidate_form').serializeFormJSON();
              	var con = JSON.stringify(frm);
  			    con = con.replace(/[{}]/g, ""); 
  			    
  		        var jsonData = encodeURIComponent(con);
          		
          		var sourceleadUrl ='${sourceleadUrl}';
          		var form = $('#candidate_form')[0];
           		var formData = new FormData(form);
	            		
	            $.ajax({
	                	      type: "POST",
	                	      data: formData,
	                	      contentType : 'application/json; charset=utf-8',
	                	      enctype: 'multipart/form-data',
	            	          processData: false,
	            	          contentType:false,
	                	      dataType : 'json',
	                	      
	                      	  url: sourceleadUrl+"/saveCandidateProfile?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,
	                          success: function(result) {
	    			        	  cancel();
	    			          },  
	            });
}
</script> 


<%-- 
     @author : sarath kumar.
     this script is for hide side bar when you click on the cross button on popup.
--%>

<script> 
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
}); 
</script> 

<%-- 
     @author : sarath kumar.
     this script is for load predefined image for candidate.
     1.usage     : called at clientSideBar() function. line number 166.
--%>

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<%-- 
     @author : sarath kumar.
     this script is for delete image for candidate.
     1.usage     : not used..future enhancements
--%>
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

<%-- 
     @author : sarath kumar.
     this script is for delete image for candidate.
     1.usage     : not used..future enhancements
--%>
<script type="text/javascript">
function deleteProfileImage(candidateId){   
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteCandidateImage&candidateId="+candidateId,
	        success: function(data) {
	        	 var result = data;
	       },
	        
     }); 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for validate image for candidate.
     1.usage     : used in clientSideBar() function line number :171.
--%>
<script type="text/javascript">
function loadFile(event,candidateId){
	    var output = document.getElementById('userImage');
	    var uploadedImage = document.getElementById('uploadedImage').value;
	    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
	    document.getElementById("errorImage").style.display="none"; 
	    
	    if(uploadedImage.substring(uploadedImage.lastIndexOf(".")+1) != 'jpg'){
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	
	    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
	 		var msg = "."+extensionName+" extension not allowed";
	 		alertMessage(msg);
	    }else if(sizeinbytes > 200000){
	    	output.src = URL.createObjectURL(event.target.files[0]);
	 		var msg = "Image size should be less than 200 KB";
	 		alertMessage(msg);
	    }else{
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	var msg = "Do you want to save the picture.";
	    	saveOrCancel(msg,candidateId);
	   } 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for show  alert message for candidate image format.
     1.usage     : used in loadFile() function line number :422.
--%>

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

<%-- 
     @author : sarath kumar.
     this script is for conformation message to save file / not .
     1.usage     : used in loadFile() function line number :439.
--%>
<script type="text/javascript">
function saveOrCancel(message,candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='saveCandidateImage("+candidateId+");' ><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<%-- 
     @author : sarath kumar.
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage function line number : 435 / 439.
--%>
<script type="text/javascript">
function cancel(){ 
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<!--HTML CONTENT -->

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
      </div>
      <div class="candidate_wrap_table">
        <div id="candidateList"></div>
      </div>
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
    </div>
    <!-- Compnay details Ends --> 
  </div>
</section>

<!--   Error Message   -->
  <div class="vendor_wrap_mask"></div>
  <div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
<!--   Error Message   --> 
 
<!-- BELOW SCRIPTS ARE USED TO SHOW / HIDE POPUP'S --> 
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
 
<jsp:include page="../masterFooter.jsp" />