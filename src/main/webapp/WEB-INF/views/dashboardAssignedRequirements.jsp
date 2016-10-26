<div class="content-dashboard clearfix"> 
      <!-- Assigned Requirements -->
      <div class="clearfix dashboard-widget mb col-md-12">
        <h4 class="pull-left">
        Assigned Requirements
        &nbsp;&nbsp;<span class="pull-right" title="Calendar"><i aria-hidden="true" class="fa fa-calendar" id="datepicker"></i>
        </span>
        </h4>
      <a href="#" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a>
        <div class="clearfix require_wrap_table dashboard-data">
        		 <div id="requirementList"></div>
         <!--  <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Requirement</th>
                <th>Client</th>
                <th>Rate</th>
                <th>Priority</th>
                <th>Applicants</th>
                <th>Shortlisted</th>
                <th>Submited</th>
                <th>Interviewd</th>
                <th>Offered</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
            </tbody>
          </table> -->
          <!-- Pagenation -->
          <!-- <nav class="clearfix">
            <ul id="paging" class="pagination pull-right">
              <li><a href="#">Previous</a></li>
              <li class="active"><a href="#">1</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">4</a></li>
              <li><a href="#">5</a></li>
              <li data-page="last"><a href="#">Next</a></li>
            </ul>
          </nav> -->
        </div>
      </div>
      <!-- Pagenation Ends --> 
    </div>
    <!-- Assigned Requirements Row Ends  -->
    
    
 <%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script> 
$(document).ready(function(){
	requirementList();
});
</script>

<script>
function requirementList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAssignedRequirementListByOrgLocationIDAndUserId?${_csrf.parameterName}=${_csrf.token}",    
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
		                     result = result +"<td><a href='<c:url value='/requirementSummary?requirementId="+opt.id+"'/>'><div class='proj_show'><span>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
		                     result = result +"<td><div><span>Client</span></div></td>";
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
	                    	 result = result +"<td><div>-----</div></td>";

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