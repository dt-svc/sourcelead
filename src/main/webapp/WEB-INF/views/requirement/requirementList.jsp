<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 


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
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getRequirementListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}",       
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
	                    	 
	                    	 result = result +"<tr>";
		                     result = result +"<td><div class='proj_show' onclick='sideBar("+opt.id+");'><span>Vannam vannam vannam vannam</span><i class='fa fa-binoculars'></i></div></td>";
		                     result = result +"<td><div><span>Vannam vannam vannam vannam</span></div></td>";
		                     result = result +"<td><div>Full Time</div></td>";
		                     result = result +"<td><div><span><span>Vannam vannam vannam vannam</span></span></div></td>";
		                     result = result +"<td><div>2</div></td>";
		                     result = result +"<td><div>Name here</div></td>";
		                     result = result +"<td><div><b class='proj_process'>Process</b></div></td>";
		                     result = result +"<td><div><a href='javascript:void(0)'><i class='fa fa-bullhorn'></i></a></div></td>";
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
				        result = result+'            <div class="imageParent"> <img src="./images/dell-logo.png" alt="Company" title="Company Logo" class="img-responsive"> </div>';
				        result = result+'            <span class="hidden"> TATA Consulting Private Limited, Hyderebad. </span> </div>';
				        result = result+'        </div>';
				        result = result+'        <div class="col-md-9">';
				        result = result+'          <div class="canDetails clearfix">';
				        result = result+'            <h4>SL 201 - Business Analyst - Full Time</h4>';
				        result = result+'            <div class="row">';
				        result = result+'              <div class="col-md-6">';
				        result = result+'                <ul class="list-unstyled can-perdt">';
				        result = result+'                  <li><i class="fa fa-black-tie"></i><span>Experience - 4 years</span></li>';
				        result = result+'                  <li><i class="fa fa-rupee"></i><span>200 Per Hour</span></li>';
				        result = result+'                  <li><i class="fa fa-map-marker"></i><span>Hyderabad, TS, India</span></li>';
				        result = result+'                </ul>';
				        result = result+'              </div>';
				        result = result+'              <div class="col-md-6">';
				        result = result+'                <ul class="list-unstyled can-perdt">';
				        result = result+'                  <li> <i class="fa fa-user"></i><span id="canName">SampathKumar Reddy Nakkapalli</span> <b class="dropdown"> <span class="caret dropdown-toggle caret-top" data-toggle="dropdown"></span>';
				        result = result+'                    <ul class="dropdown-menu dropdown-menu-right" id="canName-ch">';
				        result = result+'                      <li><a href="#">Muralimanohar Reddy</a></li>';
				        result = result+'                      <li><a href="#">Krishnamanohar Surampalli</a></li>';
				        result = result+'                      <li><a href="#">Prasadraj Mamidipayy</a></li>';
				        result = result+'                    </ul>';
				        result = result+'                    </b> </li>';
				        result = result+'                  <li><i class="fa fa-clock-o"></i><span>30/08/2005</span></li>';
				        result = result+'                  <li><i class="fa fa-flag"></i><span id="pri-val">Select Priority</span> <b class="dropdown"> <span class="caret dropdown-toggle caret-top" data-toggle="dropdown"></span>';
				        result = result+'                    <ul class="dropdown-menu dropdown-menu-right" id="pri-ch">';
				        result = result+'                      <li id="h"><a href="#">High</a></li>';
				        result = result+'                      <li id="m"><a href="#">Medium</a></li>';
				        result = result+'                      <li id="l"><a href="#">Low</a></li>';
				        result = result+'                    </ul>';
				        result = result+'                    </b> </li>';
				        result = result+'                </ul>';
				        result = result+'              </div>';
				        result = result+'            </div>';
				        result = result+'          </div>';
				        result = result+'          <div class="pro-detials">';
				        result = result+'            <div class="professinal-details">';
				        result = result+'              <ul class="list-inline list-unstyled">';
				        result = result+'                <li>BA</li>';
				        result = result+'                <li>BRS</li>';
				        result = result+'                <li>SRS</li>';
				        result = result+'                <li>Agile</li>';
				        result = result+'                <li>SDLC</li>';
				        result = result+'              </ul>';
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
				            result = result +'   <h1>Description:</h1>';
				            result = result +'   <textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea>';
				            result = result +'   <h2>Other details:</h2>';
				            result = result +'   <div class="require_one">';
				            result = result +'     <div class="require_wrap_details_cta"> <b>No. of positions:</b> <span>10</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Education:</b> <span>MBA</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Attachment:</b> <span>file name<i class="glyphicon glyphicon-save"></i></span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +'   <h2>Client details:</h2>';
				            result = result +'   <div class="require_one">';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Client contact:</b> <span class="pull-left"> Sudhakar Reddy </span>';
				            result = result +'       <div class="dropdown pull-left"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>';
				            result = result +'         <ul class="dropdown-menu dropdown-menu-right">';
				            result = result +'           <li><a href="#">Client Contact-1</a></li>';
				            result = result +'           <li><a href="#">Client Contact-2</a></li>';
				            result = result +'           <li><a href="#">Client Contact-3</a></li>';
				            result = result +'         </ul>';
				            result = result +'       </div>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Email:</b> <span>sudhakar@tcs.com</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Phone No:</b> <span>+91 9000 000 000</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i>300</span>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Submission Form:</b> <span>Genral</span>';
				            result = result +'       <div class="dropdown pull-left"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>';
				            result = result +'         <ul class="dropdown-menu dropdown-menu-right">';
				            result = result +'           <li><a href="#">General Form-1</a></li>';
				            result = result +'           <li><a href="#">General Form-2</a></li>';
				            result = result +'           <li><a href="#">General Form-3</a></li>';
				            result = result +'         </ul>';
				            result = result +'       </div>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +'   <h2>Internal details:</h2>';
				            result = result +'   <div class="require_one">';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Manager:</b> <span>Sujith S</span>';
				            result = result +'       <div class="dropdown pull-left dropup"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>';
				            result = result +'         <ul class="drop-up dropdown-menu dropdown-menu-right">';
				            result = result +'           <li><a href="#">Manager-1</a></li>';
				            result = result +'           <li><a href="#">Manager-2</a></li>';
				            result = result +'           <li><a href="#">Manager-3</a></li>';
				            result = result +'         </ul>';
				            result = result +'       </div>';
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="require_wrap_details_cta"> <b>Application form:</b> <span>Business Analyst <i class="fa fa-edit"></i></span>';
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



<!-- <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Source Lead :: Recruitment-Add Vendor</title>
Bootstrap
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap-select.min.css">
<link rel="stylesheet" href="datetimepicker.css">
<link rel="stylesheet" href="css/chosen.min.css">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
<link href="css/sourcelead-main.css" rel="stylesheet">
<link href="css/timesheet.css" rel="stylesheet">
</head>
<body> -->
<!-- <div class="wraper"> -->

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement Details</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Client Contact Details</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head">
        <ul>
          <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>
          <li> <a href="<c:url value='/requirement'/>"><i class="fa fa-plus"></i>Add Requirement</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
        <div class="remove_search">
          <input type="text" placeholder="search" />
        </div>
      </div>
      
      <!-- list requirement -->
      <div class="require_wrap_table">
          <div id="requirementList"></div>
      </div>
     <!--  <nav>
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
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
      				<div id="requirementSideDiv">
				     </div>  
			         <!--  <div class="proj_close"></div>
			        <div class="project_side_wrap_inn_tabs">
				          <ul class="project-tabs-menu">
				            <li class="current"><a href="#tab-1">Details</a></li>
				            <li><a href="#tab-2">Pipeline</a></li>
				            <div class="clearfix"></div>
				          </ul>
			             <div class="project_tabs">
							            <div id="tab-1" class="project_tabs_inn">
							              <div class="require_wrap_details">
							                <h1>Description:</h1>
							                <textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea>
							                <h2>Other details:</h2>
							                <div class="require_one">
							                  <div class="require_wrap_details_cta"> <b>No. of positions:</b> <span>10</span>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="require_wrap_details_cta"> <b>Education:</b> <span>MBA</span>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="require_wrap_details_cta"> <b>Attachment:</b> <span>file name<i class="glyphicon glyphicon-save"></i></span>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="clearfix"></div>
							                </div>
							                <h2>Client details:</h2>
							                <div class="require_one">
							                  <div class="require_wrap_details_cta"> <b>Client contact:</b> <span class="pull-left"> Sudhakar Reddy </span>
							                    <div class="dropdown pull-left"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>
							                      <ul class="dropdown-menu dropdown-menu-right">
							                        <li><a href="#">Client Contact-1</a></li>
							                        <li><a href="#">Client Contact-2</a></li>
							                        <li><a href="#">Client Contact-3</a></li>
							                      </ul>
							                    </div>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="require_wrap_details_cta"> <b>Email:</b> <span>sudhakar@tcs.com</span>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="require_wrap_details_cta"> <b>Phone No:</b> <span>+91 9000 000 000</span>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i>300</span>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="require_wrap_details_cta"> <b>Submission Form:</b> <span>Genral</span>
							                    <div class="dropdown pull-left"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>
							                      <ul class="dropdown-menu dropdown-menu-right">
							                        <li><a href="#">General Form-1</a></li>
							                        <li><a href="#">General Form-2</a></li>
							                        <li><a href="#">General Form-3</a></li>
							                      </ul>
							                    </div>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="clearfix"></div>
							                </div>
							                <h2>Internal details:</h2>
							                <div class="require_one">
							                  <div class="require_wrap_details_cta"> <b>Manager:</b> <span>Sujith S</span>
							                    <div class="dropdown pull-left dropup"> <i class="fa fa-edit dropdown-toggle" data-toggle="dropdown"></i>
							                      <ul class="drop-up dropdown-menu dropdown-menu-right">
							                        <li><a href="#">Manager-1</a></li>
							                        <li><a href="#">Manager-2</a></li>
							                        <li><a href="#">Manager-3</a></li>
							                      </ul>
							                    </div>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="require_wrap_details_cta"> <b>Application form:</b> <span>Business Analyst <i class="fa fa-edit"></i></span>
							                    <div class="clearfix"></div>
							                  </div>
							                  <div class="clearfix"></div>
							                </div>
							              </div>
							            </div>
						            <div id="tab-2" class="project_tabs_inn">
						              <div class="graf_icon"><i class="fa fa-area-chart"></i>Report</div>
						              <div class="pipeline_graf"><img src="images/bargraph.jpg" alt=""/></div>
						              <div class="timesheet_head candidateHead recruitmentHead">
						                <ul>
						                  <li class="li-vendor">
						                    <input type="text" class="vendorfild" placeholder="Search">
						                    <i class="fa fa-search"></i></li>
						                  <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
						                    <div class="export_pop">
						                      <ul>
						                        <li>PDF<i class="fa fa-file-pdf-o"></i></li>
						                        <li>Excel<i class="fa fa-file-excel-o"></i></li>
						                        <li>Word<i class="fa fa-file-word-o"></i></li>
						                      </ul>
						                    </div>
						                  </li>
						                  <li><a href="javascript:void(0)" title=""><i class="fa fa-envelope"></i></a></li>
						                  <li><a href="javascript:void(0)" title=""><i class="fa fa-mail-forward"></i></a></li>
						                  <li><a href="javascript:void(0)" title=""><i class="fa fa-upload"></i></a></li>
						                  <li><a href="javascript:void(0)" title=""><i class="fa fa-download"></i></a></li>
						                  <li><a href="javascript:void(0)" title="" class="add-applicant"><i class="fa fa-plus"></i>&nbsp;Add Applicant</a></li>
						                  <li><a href="javascript:void(0)" title="" class="add-interview"><i class="fa fa-plus"></i>&nbsp;Add Interview</a></li>
						                  <li> <a href="javascript:void(0)" title="" class="add-offer"><i class="fa fa-plus"></i>&nbsp;Add Offer</a> </li>
						                  <div class="clearfix"></div>
						                  Popup Div -- Add Offer
						                  <div class="collapseDiv_add collapseDiv_styles col-xs-4 addOffer-Req"> <i class="fa fa-close close-div"></i>
						                    <div class="form-horizontal clearfix">
						                      <div class="row">
						                        <div class="col-md-12 mb10">
						                          <label>Candiate</label>
						                          <select class="form-control selectpicker">
						                            <option>Rameshkumar Korrapati</option>
						                            <option>Prasanth Korrapati</option>
						                            <option>Muralimohan Korrapati</option>
						                            <option>Sarathkumar Korrapati</option>
						                          </select>
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <label>Date of Joining</label>
						                          <div class="positionRelative">
						                            <input type="text" value="" class="form-control input-sm">
						                            <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <label>Rate / Salary</label>
						                          <div class="row div-padding">
						                            <div class="col-md-4">
						                              <select class="form-control selectpicker">
						                                <option>Rupee</option>
						                                <option>Dollar</option>
						                                <option>Rupee</option>
						                              </select>
						                            </div>
						                            <div class="col-md-4">
						                              <input type="text" value="" class="form-control">
						                            </div>
						                            <div class="col-md-4">
						                              <select class="form-control selectpicker">
						                                <option>Yearly</option>
						                                <option>Monthly</option>
						                              </select>
						                            </div>
						                          </div>
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <label>Position Type</label>
						                          <select class="form-control selectpicker">
						                            <option>Full Type</option>
						                            <option>C2C</option>
						                            <option>Parttime</option>
						                          </select>
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <label>Attachment</label>
						                          <div class="input-group">
						                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
						                              <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
						                              </span> </label>
						                            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">
						                          </div>
						                        </div>
						                        <div class="col-md-12 mb10 hidden">
						                          <button type="button" class="btn btn-danger"><i class="fa fa-pulse"></i>Add</button>
						                        </div>
						                      </div>
						                      <div class="clearfix mt10">
						                        <div class="pull-right">
						                          <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>
						                          <button type="submit" class="btn btn-sm btn-default">Cancel</button>
						                        </div>
						                      </div>
						                    </div>
						                  </div>
						                  Popup Div -- Add Offer Ends 
						                  Popup Div -- Add Interview
						                  <div class="collapseDiv_add col-xs-4 collapseDiv_styles addInterview-Re"> <i class="fa fa-close close-div"></i>
						                    <div class="form-horizontal clearfix">
						                      <div class="row">
						                        <div class="col-md-12 mb10">
						                          <label>Candiate</label>
						                          <select class="form-control selectpicker">
						                            <option>Rameshkumar Korrapati</option>
						                            <option>Prasanth Korrapati</option>
						                            <option>Muralimohan Korrapati</option>
						                            <option>Sarathkumar Korrapati</option>
						                          </select>
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <div class="positionRelative date" id="datetimepicker3">
						                            <input type="text" value="" class="form-control input-sm" placeholder="Date of Joining">
						                            <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span> </div>
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <div class="row div-padding">
						                            <div class="col-md-4">
						                              <label>Start Time</label>
						                              <div class="positionRelative">
						                                <input type="text" value="" class="form-control input-sm">
						                                <span class="input-group-addon input-groupAddon"><i class="fa fa-clock-o"></i></span> </div>
						                            </div>
						                            <div class="col-md-4">
						                              <label>End Time</label>
						                              <div class="positionRelative">
						                                <input type="text" value="" class="form-control input-sm">
						                                <span class="input-group-addon input-groupAddon"><i class="fa fa-clock-o"></i></span> </div>
						                            </div>
						                            <div class="col-md-4">
						                              <label>Inteview Type</label>
						                              <select class="form-control selectpicker">
						                                <option>F2F</option>
						                                <option>Telephonic</option>
						                                <option>Skype Call</option>
						                              </select>
						                            </div>
						                          </div>
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <input type="text" value="" class="form-control input-sm" placeholder="Location">
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <label>Interviewer</label>
						                          <select class="form-control selectpicker">
						                            <option>Hyderabad</option>
						                            <option>Visakhapatnam</option>
						                            <option>Pune</option>
						                          </select>
						                        </div>
						                        <div class="col-md-12 mb10">
						                          <label>
						                          Set Reminder &nbsp;<i class="fa fa-gear reminder" title="Click here"></i>
						                          <div class="reminder-div col-sm-7">
						                          
						                            <div class="row mb10">
						                              <div class="col-md-12">
						                                <input type="text" class="form-control input-sm" value="" placeholder="Remind before">
						                              </div>
						                            </div>
						                            <div class="row mb10">
						                              <div class="col-md-12">
						                                <input type="text" class="form-control input-sm" value="" placeholder="Frequency">
						                              </div>
						                            </div>
						                            <div class="row mb10">
						                              <div class="col-md-12">
						                                <input type="text" class="form-control input-sm" value="" placeholder="Notify to">
						                              </div>
						                            </div>
						                            <div class="row">
						                              <div class="pull-right mr">
						                                <button type="button" class="btn btn-sm btn-success">Set</button>
						                              </div>
						                            </div>
						                          </div>
						                          </label>
						                        </div>
						                        <div class="col-md-12 mb10 hidden">
						                          <button type="button" class="btn btn-danger"><i class="fa fa-pulse"></i>Add</button>
						                        </div>
						                      </div>
						                      <div class="clearfix mt10">
						                        <div class="pull-right">
						                          <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>
						                          <button type="submit" class="btn btn-sm btn-default">Cancel</button>
						                        </div>
						                      </div>
						                    </div>
						                  </div>
						                  Popup Div -- Add Interview
						                  Popup Div -- add Applicant
						                  <div class="collapseDiv_add col-xs-4 collapseDiv_styles addApplicant-Re"> <i class="fa fa-close close-div"></i>
						                    <div class="form-horizontal clearfix">
						                      <div class="row">
						                        <div class="col-md-12 mb10">
						                          <label>Select Candiate</label>
						                          <select class="form-control selectpicker">
						                            <option>Rameshkumar Korrapati</option>
						                            <option>Prasanth Korrapati</option>
						                            <option>Muralimohan Korrapati</option>
						                            <option>Sarathkumar Korrapati</option>
						                          </select>
						                        </div>
						                        <div class="clearfix text-center text-info mb10">
						                      -------------------------------- OR --------------------------------
						                         </div>
						                        <div class="col-md-12 mb10">
												 <input type="text" value="" class="form-control input-sm" placeholder="First Name *">
						                        </div>
						                        <div class="col-md-12 mb10">
												 <input type="text" value="" class="form-control input-sm" placeholder="Last Name">
						                        </div>
						                        <div class="col-md-12 mb10">
												 <input type="text" value="" class="form-control input-sm" placeholder="Email*">
						                        </div>
						                        <div class="col-md-12 mb10">
												 <input type="text" value="" class="form-control input-sm" placeholder="Phone">
						                        </div>
						                        <div class="col-md-12 mb10">
												 <input type="text" value="" class="form-control input-sm" placeholder="Experience">
						                        </div>
						                        <div class="col-md-12">
						                        <div class="panel-border">
						                        <div class="col-md-12">
						                          <div class="input-group">
						                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
						                              <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
						                              </span> </label>
						                            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume *">
						                          </div>
						                        </div>
						                    
						                  <div class="col-md-12 mt10 mb10 clearfix text-center text-info">
						                      -------------------------------- OR --------------------------------
						                  </div>
						                  <div class="col-md-12">
						                          <select class="form-control selectpicker">
						                            <option>Select</option>
						                            <option>Prasanth Korrapati</option>
						                            <option>Muralimohan Korrapati</option>
						                            <option>Sarathkumar Korrapati</option>
						                          </select>
						                        </div>
						                        </div></div>
						                        <div class="col-md-12 mb10">
						                          <label>Expected Rate / Salary <span class="text-danger">*</span></label>
						                          <div class="row div-padding">
						                            <div class="col-md-4">
						                              <select class="form-control selectpicker">
						                                <option>Rupee</option>
						                                <option>Dollar</option>
						                                <option>Rupee</option>
						                              </select>
						                            </div>
						                            <div class="col-md-4">
						                              <input type="text" value="" class="form-control input-sm">
						                            </div>
						                            <div class="col-md-4">
						                              <select class="form-control selectpicker">
						                                <option>Yearly</option>
						                                <option>Monthly</option>
						                              </select>
						                            </div>
						                          </div>
						                        </div>
						                        
						                        
						                      </div>
						                      <div class="clearfix mt10">
						                        <div class="pull-right">
						                          <button type="submit" class="btn btn-sm btn-primary mr">Submit</button>
						                          <button type="submit" class="btn btn-sm btn-default">Cancel</button>
						                        </div>
						                      </div>
						                    </div>
						                  </div>
						                  Popup Div -- add Applicant
						                  
						                </ul>
						                <div class="clearfix"></div>
						              </div>
						              <div class="require_wrap_table">
						                <table>
						                  <tbody>
						                  <thead>
						                    <tr>
						                      <th><input type="checkbox" id="checkbox1"/></th>
						                      <th>Name</th>
						                      <th>Email</th>
						                      <th>Ph. No</th>
						                      <th>Resume</th>
						                      <th>Applied on</th>
						                      <th>Rate</th>
						                      <th>Source</th>
						                      <th>Experince</th>
						                      <th>Status</th>
						                    </tr>
						                  </thead>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>9000 000 000</span></div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div><span>Careers</span></div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>
						                        <select>
						                          <option>Interview</option>
						                          <option>Submit</option>
						                          <option>Joined</option>
						                          <option>Screening</option>
						                          <option>Offer</option>
						                        </select>
						                        </span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b>Offer</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b>Offer</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                    <td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>08/08/2016</span></div></td>
						                    <td><div><span>12345678</span></div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
						                  </tr>
						                  <tr>
						                	<td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>Full Time</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div>08/08/2016 07:30 AM</div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
						                </tr>
						                <tr>
						                	<td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>Full Time</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div>08/08/2016 07:30 AM</div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span><b class="proj_active">Joined</b><i class=" fa fa-edit"></i></span></div></td>
						                </tr>
						                <tr>
						                	<td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>Full Time</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div>08/08/2016 07:30 AM</div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span><b class="proj_active">Offer</b><i class=" fa fa-edit"></i></span></div></td>
						                </tr>
						                <tr>
						                	<td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Full Time</span></div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>9000 000 000</span></div></td>
						                    <td><div><span>08/08/2016 07:30 AM</span></div></td>
						                    <td><div><span>Careers</span></div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span><b>Screening</b><i class=" fa fa-edit"></i></span></div></td>
						                </tr>
						                <tr>
						                	<td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>Full Time</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div>08/08/2016 07:30 AM</div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span><b class="proj_process">Interview</b><i class=" fa fa-edit"></i></span></div></td>
						                </tr>
						                <tr>
						                	<td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>Full Time</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div>08/08/2016 07:30 AM</div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span><b class="proj_closed">Submit</b><i class=" fa fa-edit"></i></span></div></td>
						                </tr>
						                <tr>
						                	<td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>Full Time</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div>08/08/2016 07:30 AM</div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span><b class="proj_active">Joined</b><i class=" fa fa-edit"></i></span></div></td>
						                </tr>
						                <tr>
						                	<td><input type="checkbox" id="checkbox1"/></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>Full Time</div></td>
						                    <td><div><a href="javascript:void(0)"><i class="glyphicon glyphicon-list-alt"></i></a></div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>                    
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div>9000 000 000</div></td>
						                    <td><div>08/08/2016 07:30 AM</div></td>
						                    <td><div>Careers</div></td>
						                    <td><div>3.4 Years</div></td>
						                    <td><div><span>Vannam vannam vannam vannam</span></div></td>
						                    <td><div><span><b class="proj_active">Offer</b><i class=" fa fa-edit"></i></span></div></td>
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
			        </div> -->
        
        
        
      </div>
    </div>
    <!-- Project --> 
  </div>
</section>
<!-- Footer Area -->
<!-- <footer class="footer-area clearfix">
  <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
  <div class="powered-by"> Powered by: businessintelli.com </div>
</footer> -->
<!-- Footer Area --> 

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script>
<script src="js/bootstrap-datetimepicker.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 

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