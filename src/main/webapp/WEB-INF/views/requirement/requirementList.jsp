
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>



<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<%-- 
     @author : David Raju.
     this script is to get filter popup
     1.usage     : onclick on requirementList() ; 
     
--%>

<script>
$(document).ready(function() {
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
}); 

</script>


<style>
 
 .clsDatePicker {
   z-index: 100000 !important;
}
.side-bar .side-nav ul li span{
  display: inline-block;
  float: none;
}
.project_wrap_table span{
display: inline;
}
.require_wrap_table {
overflow: visible;
}
.timesheet_head ul {
padding: 1px 280px 2px 0;
}
 </style>
 
 

<!-- my scripts -->

<script>
 window.onload = function() { 
 	 requirementList();
};
</script>

<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script>
function requirementList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
	 var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() 
	    {
			clients.push($(this).val());
	    });
	
	
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getRequirementListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clients="+clients,    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Title</th>"; 
	                     result = result +"    <th>Client</th>"; 
	                     result = result +"    <th>Position type</th>"; 
	                     result = result +"    <th>Location</th>"; 
	                     result = result +"    <th>No.of<br> Positions</th>"; 
	                     result = result +"    <th>Owner</th>"; 
	                     result = result +"    <th>Status</th>"; 
	                     result = result +"    <th>Release</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>"; /*  onclick='sideBar("+opt.id+");' */
		                     result = result +"<td><a href='<c:url value='/requirementSummary?requirementId="+opt.id+"'/>'><div class='proj_show'><span>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
		                     if(opt.clientName != null && opt.clientName !='' && opt.clientName !='undefined'){
		                    	 result = result +"<td><div><span>"+opt.clientName+"</span></div></td>";
		                     }else{
		                    	 result = result +"<td><div><span>---</span></div></td>";
		                     }
		                     
		                     
		                     if(opt.jobType!=null && opt.jobType !='' && opt.jobType !='undefined'){
		                    	 result = result +"<td><div>"+opt.jobType+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                    
		                     if(opt.state!=null && opt.state !='' && opt.state !='undefined' &&  opt.city !='undefined' && opt.city !='' &&  opt.city!=null){
			                     result = result +"<td><div><span><span>"+opt.country+" ,"+opt.state+" ,"+opt.city+" </span></span></div></td>";
		                     }else{
			                     result = result +"<td><div><span><span>"+opt.country+"</span></span></div></td>";
		                     }
		                     if(opt.positions!=null && opt.positions !='' && opt.positions !='undefined'){
		                    	 result = result +"<td><div>"+opt.positions+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>----</div></td>";
		                     }
		                     
		                     if(opt.manager!=null && opt.manager !='' && opt.manager !='undefined'){
		                    	 result = result +"<td><div>"+opt.manager+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }

		                     /* f(opt.managerUserId.firstName != null && opt.managerUserId.firstName !='' && opt.managerUserId.firstName !='undefined'){
		                    	 result = result +"<td><div>"+opt.managerUserId.firstName+" "+opt.managerUserId.lastName+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     } */
		                     
		                     
		                     result = result +"<td><div><b class='proj_process'>"+opt.requirementSubmissionStatus+"</b></div></td>";
		                     if(opt.requirementSubmissionStatus == 'APPROVED' ||  opt.requirementSubmissionStatus == 'OPEN'){
			                     result = result +"<td><div><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementId="+opt.id+"'/>'><i class='fa fa-bullhorn'></i></a></div></td>";

		                     }else{
			                     result = result +"<td><div>--</div></td>";
 
		                     }
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#requirementList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
                	 $("#requirementList").removeAttr("style");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Requirement Found</p>";
					 result = result+"			 </div>";
					
					$("#requirementList").empty();
				    $("#requirementList").append(result); 
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
    $('#requirementPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": ""
       }
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
function sideBar(requirementId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
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
              			 
              			 var result = '';
				        result = result+'    <div class="profile_details requirement_details clearfix">';
				        result = result+'    <div class="col-md-9">';
				        result = result+'      <div class="row">';
				        result = result+'        <div class="col-md-3">';
				        result = result+'          <div class="company-logo">';
				        result = result+'            <div class="imageParent"> <img onerror="predefineOrgImage(this);" src="${sourceleadUrl}/displayOrgImage/'+json.orgLocationId.org.orgCode+'" alt="Company" title="Company Logo" class="img-responsive"> </div>';
				        result = result+'            <span class="hidden"> TATA Consulting Private Limited, Hyderebad. </span> </div>';
				        result = result+'        </div>';
				        result = result+'        <div class="col-md-9">';
				        result = result+'          <div class="canDetails clearfix">';
				        result = result+'            <h4>SL 201 - Business Analyst - Full Time</h4>';
				        result = result+'            <div class="row">';
				        result = result+'              <div class="col-md-6">';
				        result = result+'                <ul class="list-unstyled can-perdt">';
				        if(json.maxExp != null && json.maxExp != '' && json.maxExp !='undefined'&& json.minExp != null && json.minExp != '')
				        	{
				       	result = result+'                  <li><i class="fa fa-black-tie"></i><span>Experience : '+json.maxExp+' years</span></li>';
				        	}
					    else if(json.minExp != null && json.minExp != '' && json.minExp !='undefined'){
						result = result+'                  <li><i class="fa fa-black-tie"></i><span>Experience : '+json.minExp+' years</span></li>';
	
					        }
				        else{
					    result = result+'                  <li><i class="fa fa-black-tie"></i><span>Experience : --</span></li>';
				        }
				        
				        if(json.currencyCode == 'INR' && json.currencyCode  !='' && json.currencyCode  !='undefined')
				    	   {
				        result = result+'                  <li><i class="fa fa-rupee"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
				    	   }
				       else if(json.currencyCode  == 'USD' && json.currencyCode  == 'AUD'  && json.currencyCode  == 'SGD' && json.currencyCode  !='' && json.currencyCode  !='undefined'){
					   result = result+'                  <li><i class="fa fa-dollar"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
   
				       }
				       else if(json.currencyCode  == 'EUR' && json.currencyCode  !='' && json.currencyCode  !='undefined'){
					   result = result+'                  <li><i class="fa fa-eur"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
   
				       }
				       else if(json.currencyCode  == 'GPB' && json.currencyCode  !='' && json.currencyCode  !='undefined'){
					   result = result+'                  <li><i class="fa fa-gbp"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
  
				       }
				       
				        if(json.state !='' && json.state != 'undefined' && json.city !='' && json.city != 'undefined')
				        	{
				        result = result+'                  <li><i class="fa fa-map-marker"></i><span>'+json.country+', '+json.state+', '+json.city+'</span></li>';
				        	}
				        else if(json.state !='' && json.state != 'undefined')
				        	{
					    result = result+'                  <li><i class="fa fa-map-marker"></i><span>'+json.country+', '+json.state+'</span></li>';

				        	}
				        else{
					     result = result+'                  <li><i class="fa fa-map-marker"></i><span>'+json.country+'</span></li>';

				        }
				        result = result+'                </ul>';
				        result = result+'              </div>';
				        result = result+'              <div class="col-md-6">';
				        result = result+'                <ul class="list-unstyled can-perdt">';
				        result = result+'                  <li> <i class="fa fa-user"></i><span id="canName">'+json.manager+'</span></li>';
				        result = result+'                  <li><i class="fa fa-clock-o"></i><span>'+json.requirementCreatedDateToString+'</span></li>';
				        result = result+'                  <li><i class="fa fa-flag"></i><span>Select Priority</span>';
				         result = result+'                     </li>';
				        result = result+'                </ul>';
				        result = result+'              </div>';
				        result = result+'            </div>';
				        result = result+'          </div>';
				        result = result+'          <div class="pro-detials">';
				        result = result+'            <div class="professinal-details">';
				        result = result+'              <ul class="list-inline list-unstyled">';
				        var skills = [];
			           if(skills != null && skills != '' && skills != 'undefined'){
			        	   skills = json.skiills.split(",");
					        result = result+'                <li>'+skills[0]+'</li>';
					        result = result+'                <li>'+skills[1]+'</li>';
					        result = result+'                <li>'+skills[2]+'</li>';
					        result = result+'                <li>'+skills[3]+'</li>';
					        result = result+'                <li>'+skills[4]+'</li>';
					        result = result+'              </ul>';
			           }
				        result = result+'            </div>';
				        result = result+'          </div>';
				        result = result+'        </div>';
				        result = result+'      </div>';
				        result = result+'    </div>';
				        result = result+'    <div class="col-md-3">';
				        result = result+'      <select class="selectpicker pull-right">';
				        result = result+'        <option>Open</option>';
				        result = result+'        <option>Draft</option>';
				        result = result+'        <option>Filled Closed</option>';
				        result = result+'        <option>Unfilled</option>';
				        result = result+'        <option>On Hold</option>';
				        result = result+'        <option>Cancel</option>';
				        result = result+'      </select>';
				        result = result+'    </div>';
				        result = result+'  </div>';
				        
					    result = result+'  <div class="proj_close"></div>';
					    result = result+'  <div class="project_side_wrap_inn_tabs">';
					    result = result+'    <ul class="project-tabs-menu">';
					    result = result+'      <li class="current"><a href="#tab-1" onclick="requirementDetails('+requirementId+');">Details</a></li>';
					    result = result+'      <li><a href="#tab-2" onclick="requirementPipeline('+requirementId+');">Pipeline</a></li>';
					    result = result+'      <div class="clearfix"></div>';
					    result = result+'    </ul>';
					    result = result+'    <div class="project_tabs">';
						    result = result+' <div id="tab-1" class="project_tabs_inn">';
						    result = result+'</div>';
						    result = result+' <div id="tab-2" class="project_tabs_inn">';
						    result = result+'</div>';
					    result = result+'</div>';
					    result = result+'</div>';
					    
			             $("#requirementSideDiv").empty();  
			 		     $("#requirementSideDiv").append(result);  
				 		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
						 $(".side_mask").fadeIn(200);
						 $("body").css('overflow-y', 'hidden');
						 requirementDetails(requirementId);
						 $(".proj_close, .side_mask").click(function(){
								$(".clients_side_wrap").removeClass("clients_side_wrap_active");
								$(".side_mask").fadeOut(200);
								$("body").css('overflow-y', 'auto');
						 });	
			 			 
			    	} 
                }else{
               	 $("#requirementSideDiv").removeAttr("style");
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
					$("#requirementSideDiv").empty();
				    $("#requirementSideDiv").append(result); 
                }
         },
           
      }); 
				
}
</script>

<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','resources/images/office-image.jpg');
	}
</script>


<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function requirementDetails(requirementId){ 
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
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
							var result ="";
				            result = result +' <div class="require_wrap_details">';
				            result = result +' <a href="<c:url value="/requirement?requirementId='+requirementId+'"/>"><button  class="btn btn-primary pull-right" type="button">Edit</button></a>';
				            result = result +'   <h1>Description:</h1>';
				            if(json.description != null && json.description != '' && json.description != 'undefined'){
					            result = result +'   <textarea placeholder="Enter description">'+json.description+'</textarea>';
					            }
					            else{
					            result = result +'   <textarea placeholder="Enter description"> NO DATA</textarea>';
					            }
				            result = result +'   <h2>Other details:</h2>';
				            result = result +'   <div class="require_one">';
				            if(json.positions != null && json.positions != '' && json.positions != 'undefined'){
				            result = result +'     <div class="require_wrap_details_cta"> <b>No. of positions:</b> <span>'+json.positions+'</span>';
				            }
				            else{
				            result = result +'<div class="require_wrap_details_cta"> <b>No. of positions:</b> <span> -- </span>';	
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.education != null && json.education != '' && json.education != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Education:</b> <span>'+json.education+'</span>';
					            }else{
					           	result = result +'<div class="require_wrap_details_cta"> <b>Education:</b> <span> -- </span>';
					            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Attachment:</b> <span>'+json.fileName+'<i class="glyphicon glyphicon-save"></i></span>';
					            }
					            else{
					            result = result +'<div class="require_wrap_details_cta"> <b>Attachment:</b> <span> -- </span>';	
					            }				            
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +'   <h2>Client details:</h2>';
				            result = result +'   <div class="require_one">';
				            if(json.spocName != '' && json.spocName !=null && json.spocName != 'undefined'){
				            	
				            result = result +'     <div class="require_wrap_details_cta"> <b>Client contact:</b> <span class="pull-left"> '+json.spocName+' </span>';
				            }
				            else{
					        result = result +'     <div class="require_wrap_details_cta"> <b>Client contact:</b> <span class="pull-left"> -- </span>';

				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>'; 
				            if(json.spocEmail != '' && json.spocEmail !=null && json.spocEmail != 'undefined'){
						    result = result +'     <div class="require_wrap_details_cta"> <b>Email:</b> <span>'+json.spocEmail+'</span>';
				            }
				            else{
							result = result +'     <div class="require_wrap_details_cta" style="display:none"> <b>Email:</b> <span></span>';
	
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>'; 
				            if(json.spocMobilePhone != '' && json.spocMobilePhone !=null && json.spocMobilePhone != 'undefined'){
					        result = result +'     <div class="require_wrap_details_cta"> <b>Phone No:</b> <span>'+json.spocMobilePhone+'</span>';
				            }
				            else{
						    result = result +'     <div class="require_wrap_details_cta" style="display:none"> <b>Phone No:</b> <span></span>';

				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i>300</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Submission Form:</b> <span>Submission Form</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +'   <h2>Internal details:</h2>';
				            result = result +'   <div class="require_one">';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Manager:</b> <span>Manager</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Application form:</b> <span>Business Analyst </span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +' </div>';
							$("#tab-1").empty();
							$("#tab-2").empty(); 
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
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
               }
               },
                 
            }); 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function requirementPipeline(requirementId){
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
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
							var result ="";
							
				            result = result +'<div class="graf_icon"><i class="fa fa-area-chart"></i>Report</div>';
				            result = result +'<div class="pipeline_graf"><img src="images/bargraph.jpg" alt=""/></div>';
				            result = result +'<div class="timesheet_head candidateHead recruitmentHead">';
				            result = result +'  <ul>';
				            result = result +'    <li class="li-vendor">';
				            result = result +'      <input type="text" class="vendorfild" placeholder="Search">';
				            result = result +'      <i class="fa fa-search"></i></li>';
				            result = result +'    <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>';
				            result = result +'      <div class="export_pop">';
				            result = result +'        <ul>';
				            result = result +'          <li>PDF<i class="fa fa-file-pdf-o"></i></li>';
				            result = result +'          <li>Excel<i class="fa fa-file-excel-o"></i></li>';
				            result = result +'          <li>Word<i class="fa fa-file-word-o"></i></li>';
				            result = result +'        </ul>';
				            result = result +'      </div>';
				            result = result +'    </li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-envelope"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-mail-forward"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-upload"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-download"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title="" class="add-applicant"><i class="fa fa-plus"></i>&nbsp;Add Applicant</a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title="" class="add-interview"><i class="fa fa-plus"></i>&nbsp;Add Interview</a></li>';
				            result = result +'    <li> <a href="javascript:void(0)" title="" class="add-offer"><i class="fa fa-plus"></i>&nbsp;Add Offer</a> </li>';
				            result = result +'    <div class="clearfix"></div>';
				            result = result +'    <div class="collapseDiv_add collapseDiv_styles col-xs-4 addOffer-Req"> <i class="fa fa-close close-div"></i>';
				            result = result +'     <div class="form-horizontal clearfix">';
				            result = result +'       <div class="row">';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Candiate</label>';
				            result = result +'           <select class="form-control selectpicker">';
				            result = result +'             <option>Rameshkumar Korrapati</option>';
				            result = result +'             <option>Prasanth Korrapati</option>';
				            result = result +'             <option>Muralimohan Korrapati</option>';
				            result = result +'             <option>Sarathkumar Korrapati</option>';
				            result = result +'           </select>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Date of Joining</label>';
				            result = result +'           <div class="positionRelative">';
				            result = result +'             <input type="text" value="" class="form-control input-sm">';
				            result = result +'             <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Rate / Salary</label>';
				            result = result +'           <div class="row div-padding">';
				            result = result +'             <div class="col-md-4">';
				            result = result +'               <select class="form-control selectpicker">';
				            result = result +'                 <option>Rupee</option>';
				            result = result +'                 <option>Dollar</option>';
				            result = result +'                 <option>Rupee</option>';
				            result = result +'               </select>';
				            result = result +'             </div>';
				            result = result +'             <div class="col-md-4">';
				            result = result +'               <input type="text" value="" class="form-control">';
				            result = result +'             </div>';
				            result = result +'             <div class="col-md-4">';
				            result = result +'               <select class="form-control selectpicker">';
				            result = result +'                 <option>Yearly</option>';
				            result = result +'                 <option>Monthly</option>';
				            result = result +'               </select>';
				            result = result +'             </div>';
				            result = result +'           </div>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Position Type</label>';
				            result = result +'           <select class="form-control selectpicker">';
				            result = result +'             <option>Full Type</option>';
				            result = result +'             <option>C2C</option>';
				            result = result +'             <option>Parttime</option>';
				            result = result +'           </select>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10">';
				            result = result +'           <label>Attachment</label>';
				            result = result +'           <div class="input-group">';
				            result = result +'             <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				            result = result +'               <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">';
				            result = result +'               </span> </label>';
				            result = result +'             <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">';
				            result = result +'           </div>';
				            result = result +'         </div>';
				            result = result +'         <div class="col-md-12 mb10 hidden">';
				            result = result +'           <button type="button" class="btn btn-danger"><i class="fa fa-pulse"></i>Add</button>';
				            result = result +'         </div>';
				            result = result +'       </div>';
				            result = result +'       <div class="clearfix mt10">';
				            result = result +'         <div class="pull-right">';
				            result = result +'           <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>';
				            result = result +'           <button type="submit" class="btn btn-sm btn-default">Cancel</button>';
				            result = result +'         </div>';
				            result = result +'       </div>';
				            result = result +'     </div>';
				            result = result +'   </div>';
				            result = result +' <div class="collapseDiv_add col-xs-4 collapseDiv_styles addInterview-Re"> <i class="fa fa-close close-div"></i>';
				            result = result +'   <div class="form-horizontal clearfix">';
				            result = result +'     <div class="row">';
				            result = result +'       <div class="col-md-12 mb10">';
				            result = result +'         <label>Candiate</label>';
				            result = result +'         <select class="form-control selectpicker">';
				            result = result +'           <option>Rameshkumar Korrapati</option>';
				            result = result +'           <option>Prasanth Korrapati</option>';
				            result = result +'           <option>Muralimohan Korrapati</option>';
				            result = result +'           <option>Sarathkumar Korrapati</option>';
				            result = result +'         </select>';
				            result = result +'       </div>';
				            result = result +'       <div class="col-md-12 mb10">';
				            result = result +'         <div class="positionRelative date" id="datetimepicker3">';
				            result = result +'           <input type="text" value="" class="form-control input-sm" placeholder="Date of Joining">';
				            result = result +'           <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>';
				            result = result +'       </div>';
				            result = result +'       <div class="col-md-12 mb10">';
				            result = result +'         <div class="row div-padding">';
				            result = result +'           <div class="col-md-4">';
				            result = result +'             <label>Start Time</label>';
				            result = result +'             <div class="positionRelative">';
				            result = result +'               <input type="text" value="" class="form-control input-sm">';
				            result = result +'               <span class="input-group-addon input-groupAddon"><i class="fa fa-clock-o"></i></span> </div>';
				            result = result +'           </div>';
				            result = result +'           <div class="col-md-4">';
				            result = result +'             <label>End Time</label>';
				            result = result +'             <div class="positionRelative">';
				            result = result +'               <input type="text" value="" class="form-control input-sm">';
				            result = result +'               <span class="input-group-addon input-groupAddon"><i class="fa fa-clock-o"></i></span> </div>';
				            result = result +'           </div>';
				            result = result +'           <div class="col-md-4">';
				            result = result +'             <label>Inteview Type</label>';
				            result = result +'             <select class="form-control selectpicker">';
				            result = result +'               <option>F2F</option>';
				            result = result +'               <option>Telephonic</option>';
				            result = result +'               <option>Skype Call</option>';
				            result = result +'             </select>';
				            result = result +'           </div>';
				            result = result +'         </div>';
				            result = result +'       </div>';
				            result = result +'       <div class="col-md-12 mb10">';
				            result = result +'         <input type="text" value="" class="form-control input-sm" placeholder="Location">';
				            result = result +'       </div>';
				           result = result + '             <div class="col-md-12 mb10">';
				           result = result + '               <label>Interviewer</label>';
				           result = result + '               <select class="form-control selectpicker">';
				           result = result + '                 <option>Hyderabad</option>';
				           result = result + '                 <option>Visakhapatnam</option>';
				           result = result + '                 <option>Pune</option>';
				           result = result + '               </select>';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
				           result = result + '               <label>';
				           result = result + '               Set Reminder &nbsp;<i class="fa fa-gear reminder" title="Click here"></i>';
				           result = result + '               <div class="reminder-div col-sm-7">';
				           result = result + '                 <div class="row mb10">';
				           result = result + '                   <div class="col-md-12">';
				           result = result + '                     <input type="text" class="form-control input-sm" value="" placeholder="Remind before">';
				           result = result + '                   </div>';
				           result = result + '                 </div>';
				           result = result + '                 <div class="row mb10">';
				           result = result + '                   <div class="col-md-12">';
				           result = result + '                     <input type="text" class="form-control input-sm" value="" placeholder="Frequency">';
				           result = result + '                   </div>';
				           result = result + '                 </div>';
				           result = result + '                 <div class="row mb10">';
				           result = result + '                   <div class="col-md-12">';
				           result = result + '                     <input type="text" class="form-control input-sm" value="" placeholder="Notify to">';
				           result = result + '                   </div>';
				           result = result + '                 </div>';
				           result = result + '                 <div class="row">';
				           result = result + '                   <div class="pull-right mr">';
				           result = result + '                     <button type="button" class="btn btn-sm btn-success">Set</button>';
				           result = result + '                   </div>';
				           result = result + '                 </div>';
				           result = result + '               </div>';
				           result = result + '               </label>';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10 hidden">';
				           result = result + '               <button type="button" class="btn btn-danger"><i class="fa fa-pulse"></i>Add</button>';
				           result = result + '             </div>';
				           result = result + '           </div>';
				           result = result + '           <div class="clearfix mt10">';
				           result = result + '             <div class="pull-right">';
				           result = result + '               <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>';
				           result = result + '               <button type="submit" class="btn btn-sm btn-default">Cancel</button>';
				           result = result + '             </div>';
				           result = result + '           </div>';
				           result = result + '         </div>';
				           result = result + '       </div>';
				           result = result + '       <div class="collapseDiv_add col-xs-4 collapseDiv_styles addApplicant-Re"> <i class="fa fa-close close-div"></i>';
				           result = result + '         <div class="form-horizontal clearfix">';
				           result = result + '           <div class="row">';
				           result = result + '             <div class="col-md-12 mb10">';
				           result = result + '               <label>Select Candiate</label>';
				           result = result + '               <select class="form-control selectpicker">';
				           result = result + '                 <option>Rameshkumar Korrapati</option>';
				           result = result + '                 <option>Prasanth Korrapati</option>';
				           result = result + '                 <option>Muralimohan Korrapati</option>';
				           result = result + '                 <option>Sarathkumar Korrapati</option>';
				           result = result + '               </select>';
				           result = result + '             </div>';
				           result = result + '             <div class="clearfix text-center text-info mb10">';
				           result = result + '           -------------------------------- OR --------------------------------';
				           result = result + '              </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="First Name *">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="Last Name">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="Email*">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="Phone">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12 mb10">';
						   result = result + '		 <input type="text" value="" class="form-control input-sm" placeholder="Experience">';
				           result = result + '             </div>';
				           result = result + '             <div class="col-md-12">';
				           result = result + '             <div class="panel-border">';
				           result = result + '             <div class="col-md-12">';
				           result = result + '               <div class="input-group">';
				           result = result + '                 <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				           result = result + '                   <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">';
				           result = result + '                   </span> </label>';
				           result = result + '                 <input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume *">';
				           result = result + '               </div>';
				           result = result + '             </div>';
				           result = result + '       <div class="col-md-12 mt10 mb10 clearfix text-center text-info">';
				           result = result + '           -------------------------------- OR --------------------------------';
				           result = result + '       </div>';
				           result = result + '       <div class="col-md-12">';
				           result = result + '               <select class="form-control selectpicker">';
				           result = result + '                 <option>Select</option>';
				           result = result + '                 <option>Prasanth Korrapati</option>';
				           result = result + '                 <option>Muralimohan Korrapati</option>';
				           result = result + '                 <option>Sarathkumar Korrapati</option>';
				           result = result + '               </select>';
				           result = result + '             </div>';
				           result = result + '             </div></div>';
				           result = result + '             <div class="col-md-12 mb10">';
				           result = result + '               <label>Expected Rate / Salary <span class="text-danger">*</span></label>';
				           result = result + '               <div class="row div-padding">';
				           result = result + '                 <div class="col-md-4">';
				           result = result + '                   <select class="form-control selectpicker">';
				           result = result + '                     <option>Rupee</option>';
				           result = result + '                     <option>Dollar</option>';
				           result = result + '                     <option>Rupee</option>';
				           result = result + '                   </select>';
				           result = result + '                 </div>';
				           result = result + '                 <div class="col-md-4">';
				           result = result + '                   <input type="text" value="" class="form-control input-sm">';
				           result = result + '                 </div>';
				           result = result + '                 <div class="col-md-4">';
				           result = result + '                   <select class="form-control selectpicker">';
				           result = result + '                     <option>Yearly</option>';
				           result = result + '                     <option>Monthly</option>';
				           result = result + '                   </select>';
				           result = result + '                 </div>';
				           result = result + '               </div>';
				           result = result + '             </div>';
				           result = result + '           </div>';
				           result = result + '           <div class="clearfix mt10">';
				           result = result + '             <div class="pull-right">';
				           result = result + '               <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>';
				           result = result + '               <button type="submit" class="btn btn-sm btn-default">Cancel</button>';
				           result = result + '             </div>';
				           result = result + '           </div>';
				           result = result + '         </div>';
				           result = result + '       </div>';
				           result = result + '     </ul>';
				           result = result + '     <div class="clearfix"></div>';
				           result = result + '   </div>';
				           result = result + '   <div class="require_wrap_table">';
				           result = result + '     <table>';
				           result = result + '       <tbody>';
				           result = result + '       <thead>';
				           result = result + '         <tr>';
				           result = result + '           <th><input type="checkbox" id="checkbox1"/></th>';
				           result = result + '           <th>Name</th>';
				           result = result + '           <th>Email</th>';
				           result = result + '           <th>Ph. No</th>';
				           result = result + '           <th>Resume</th>';
				           result = result + '           <th>Applied on</th>';
				           result = result + '           <th>Rate</th>';
				           result = result + '           <th>Source</th>';
				           result = result + '           <th>Experince</th>';
				           result = result + '           <th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tr>';
				           result = result + '         <td><input type="checkbox" id="checkbox1"/></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div><span>9000 000 000</span></div></td>';
				           result = result + '         <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>';
				           result = result + '         <td><div><span>08/08/2016</span></div></td>';
				           result = result + '         <td><div><span>12345678</span></div></td>';
				           result = result + '         <td><div><span>Careers</span></div></td>';
				           result = result + '         <td><div>3.4 Years</div></td>';
				           result = result + '         <td><div><span>';
				           result = result + '             <select>';
				           result = result + '               <option>Interview</option>';
				           result = result + '               <option>Submit</option>';
				           result = result + '               <option>Joined</option>';
				           result = result + '               <option>Screening</option>';
				           result = result + '               <option>Offer</option>';
				           result = result + '             </select>';
				           result = result + '             </span></div></td>';
				           result = result + '       </tr>';
				           result = result + '       <tr>';
				           result = result + '         <td><input type="checkbox" id="checkbox1"/></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div>9000 000 000</div></td>';
				           result = result + '         <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>';
				           result = result + '         <td><div><span>08/08/2016</span></div></td>';
				           result = result + '         <td><div><span>12345678</span></div></td>';
				           result = result + '         <td><div>Careers</div></td>';
				           result = result + '         <td><div>3.4 Years</div></td>';
				           result = result + '         <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>';
				           result = result + '       </tr>';
				           result = result + '       <tr>';
				           result = result + '         <td><input type="checkbox" id="checkbox1"/></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div><span>Vannam vannam vannam vannam</span></div></td>';
				           result = result + '         <td><div>9000 000 000</div></td>';
				           result = result + '         <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>';
				           result = result + '         <td><div><span>08/08/2016</span></div></td>';
				           result = result + '         <td><div><span>12345678</span></div></td>';
				           result = result + '         <td><div>Careers</div></td>';
				           result = result + '         <td><div>3.4 Years</div></td>';
				           result = result + '         <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>';
				           result = result + '       </tr>';
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   <nav>';
				           result = result + '     <ul id="paging" class="pagination">';
				           result = result + '       <li><a href="#">Previous</a></li>';
				           result = result + '       <li><a href="#">2</a></li>';
				           result = result + '       <li><a href="#">3</a></li>';
				           result = result + '       <li class="disabled"><a href="#">6</a></li>';
				           result = result + '       <li data-page="last"><a href="#">Next</a></li>';
				           result = result + '     </ul>';
				           result = result + '     <div class="clearfix"></div>';
				           result = result + '   </nav>';
				            
				            
							$("#tab-1").empty();
							$("#tab-2").empty(); 
							$("#tab-2").append(result);  
							    
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
            		 }
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
}
</script>



<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement List</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Requirement List</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head">
        <ul>
		<li><i class="glyphicon glyphicon-export"></i><span>Status</span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>|
		            <div id="filter2ApprovedPop" class="approved_pop">
		              <ul>
                		    <li><input id="c6" type="checkbox" name="requirementStatus" value ="New"  onclick="requirementList();"><label for="c6">New</label></li>
							<li><input id="c7" name="requirementStatus" type="checkbox" value ="Draft"  onclick="requirementList();"><label for="c7">Draft</label></li>
							<li><input id="c8" type="checkbox"  name="requirementStatus"  type="checkbox" value ="submitted"  onclick="requirementList();"><label for="c8">Waiting for approval</label></li>
               				<li><input id="c9" type="checkbox"  name="requirementStatus"  type="checkbox" value ="open"  onclick="requirementList();"><label for="c8">Open</label></li>
               				<li><input id="c10" type="checkbox"  name="requirementStatus"  type="checkbox" value ="onhold"  onclick="requirementList();"><label for="c8">On Hold</label></li>
               				<li><input id="c11" type="checkbox"  name="requirementStatus"  type="checkbox" value ="Cancelled"  onclick="requirementList();"><label for="c8">Cancelled</label></li>
		                 	<li><input id="c12" type="checkbox"  name="requirementStatus"  type="checkbox" value ="filledClosed"  onclick="requirementList();"><label for="c8">Cancelled</label></li>
		              		<li><input id="c13" type="checkbox"  name="requirementStatus"  type="checkbox" value ="unFilledClosed"  onclick="requirementList();"><label for="c8">Cancelled</label></li>
		              </ul>
		            </div>
          </li>
         
          <li> <a href="<c:url value='/requirement'/>"><i class="fa fa-plus"></i>Add Requirement</a></li>
          <li> <a href="<c:url value='/team'/>"><i class="fa fa-plus"></i>Team</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
      </div>
      
      <!-- list requirement -->
      <div class="require_wrap_table">
          <div id="requirementList"></div>
      </div>
    </div>
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
      				<div id="requirementSideDiv">
				     </div>  
      </div>
    </div>
    <!-- Project --> 
  </div>
</section>

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script> 
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});

});
 		</script> 
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
	
// project side bar
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
});

// Bar chat pop
$(document).ready(function() {
    $(".graf_icon").click(function(){
		$(".pipeline_graf").slideToggle(200);
	});
$( "ul#canName-ch li").click(function(){
   var m = $(this).text();
  $("#canName").text(m);
});
});
</script> 
<script>
$( "#pri-ch li").click(function(){
   var m = $(this).text();
    var mid = $(this).attr('id');
    $("#pri-val").text(m);
     if('h' === $(this).attr("id")){
          $("#pri-val").removeClass("text-warning text-primary").addClass("text-danger");
     }else if('m' === $(this).attr("id")){
        $("#pri-val").removeClass("text-danger text-warning").addClass("text-primary");
     }else{
      $("#pri-val").removeClass("text-danger text-primary").addClass("text-warning");
     }   
});
</script> 
<script>
$(document).ready(function(){
	// Levels adding script
$(".add-offer").click(function(){
	 $(".addOffer-Req").toggleClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-interview").click(function(){
	 $(".addInterview-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-applicant").click(function(){
	 $(".addApplicant-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
 });
$(".reminder").click(function(){
	 $(".reminder-div").toggleClass("reminder-div-active");
 });

 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".div-close").click(function(){
	 $(this).parent().toggleClass("showDiv");
	 });

 $(".reminder").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $("").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   });
</script>
<script type="text/javascript">
            $(function () {
                $('#datetimepicker5').datetimepicker({
					locale: 'ru'
                });
            });
        </script>
<div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
  <ul class="list-unstyled">
    <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
    <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
    <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
  </ul>
</div>
</body>
</html>





  
<jsp:include page="../masterFooter.jsp" />