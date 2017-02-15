<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
window.onload = function () {
	requirementsList();
	document.getElementById('requirementType').value = 'OpenJObs';
 };
</script>

<section class="content-wraper openJobsWrap">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Requirements<span class="select-fild">
          <select id="requirementType" onchange="requirementsList();">
           	<option value="OpenJObs">Open</option>
            <option value="CrowdSourcingJobs">Crowdsourcing</option>
          </select>
          </span>
			<div class="timeNewSe timeNewSe_h">
                <select class="form-control chosen-select">
                  <option>All open jobs</option>
                  <option>Referred to me</option>
                  <option>Referred by me</option>
                </select>
            </div> 
		  <div class="clearfix"></div>            
          </h2>        
      </div>
    </section>
    
    <!-- Project -->
 
	  <section class="openJobsHead">
	  
		  <span>
			  <i class="fa fa-search"></i><input type="text" placeholder="Search" class="searchJob">
		  </span>
		  <span>
			  <i class="fa fa-map-marker"></i><input type="text" placeholder="Location" class="searchJob searchJobLo">
		  </span>
		  <span>
			  <input type="button" value="Search" class="jobBtn">
		  </span>		  		  	  
	  
			<div class="timeNewSe" style="float:right;">
                <select class="form-control chosen-select">
                  <option>1 day ago</option>
                  <option>3 day ago</option>
                  <option>1 week ago</option>
                  <option>3 week ago</option>
                  <option>1 month ago</option>
                </select>
            </div>    
            
		  <div class="clearfix"></div>
	  </section>
	   <br/>
	  <div id="requirementsList"></div> 
	  
	  
	  
	  
	  
	  </section>

<jsp:include page="../masterFooter.jsp" />




<script>
function requirementsList(jobType){
	var sourceleadUrl = '${sourceleadUrl}';
	var requirementType = document.getElementById('requirementType').value;
	
	
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getCrowdSourcingRequirements?${_csrf.parameterName}=${_csrf.token}&requirementType="+requirementType,
	   			
	    success: function(data) {
       	 var json = JSON.parse(data);
       	 var result = "";
	     if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 	
	   	  if(json.length > 0)	
	   		{
	 			for(var i=0; i<json.length; i++){
	 				
			 				var requirements = json[i];
			 				
			 				
			 				var jobType = '';
			 				var experience = '';
			 				var rate = '';
			 				var location = '';
			 				
			 				if(requirements.jobType != null && requirements.jobType != '' && requirements.jobType != 'undefined'){
			 					jobType = requirements.jobType;
			 				}else{
			 					jobType = 'No Data';
			 				}
			 				
			 				
			 				if(requirements.minExperience != 'undefined' && requirements.maxExperience == 'undefined'){
			 					experience = requirements.minExperience+" Years";
			 				}
			 				else if(requirements.minExperience == 'undefined' && requirements.maxExperience != 'undefined'){
			 					experience = requirements.maxExperience+" Years";
			 				}
			 				else if(requirements.minExperience != null && requirements.maxExperience != null){
			 					experience = requirements.minExperience+" to "+requirements.maxExperience+" Years";
			 				}else{
			 					experience = 'No Data';
			 				}
			 				
			 				if(requirements.state != null && requirements.state != '' && requirements.state != 'undefined'){
			 					location = requirements.state+", "+requirements.country;
			 				}else{
			 					location = requirements.country;
			 				}
			 				
			 				if(requirements.rate != null && requirements.rate != '' && requirements.rate != 'undefined'){
			 					rate = requirements.rate+" / "+requirements.rateType;
			 				}else{
			 					rate = 'No Data';
			 				}
			 				
			 				result = '<section class="openCta">';
			 				result = result +'<div class="OpenJob">';
			 				result = result +'<a href="open-jobs-details.html"><h3>'+requirements.jobTitle+'</h3></a>';
			 				result = result +'<h4>'+requirements.postedOrg+'</h4>';
			 				result = result +'<span>';
			 				result = result +'<b><i class="fa fa-clock-o"></i>'+jobType+'</b>';
			 				result = result +'<b><i class="fa fa-briefcase"></i>'+experience+'</b>';
			 				result = result +'<b><i class="fa fa-map-marker"></i>'+location+'</b>';
			 				result = result +'<b><i class="fa fa-money"></i>'+rate+'</b>';
			 				result = result +'</span>';
			 				result = result +'<h5>';
			 				result = result +'<strong>Skills :</strong>';
			 				result = result +'<ul>';
			 				
			 				if(requirements.skillsList != null){
			 					for(var j=0; j<requirements.skillsList.length; j++){
				 					result = result +'<li>'+requirements.skillsList[j]+'</li>';
				 				}
			 				}else{
			 					result = result +'<li>No Data</li>';
			 				}
			 				
			 				
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</ul>';
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</h5>';
			 		  
			 				result = result +'</div>';
			 				result = result +'<div class="openCtaRight">';
			 				/* result = result +'<p>Share Requirement</p>';
			 				result = result +'<span>';
			 				result = result +'<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>';
			 				result = result +'<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>';				  
			 				result = result +'<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>';
			 				result = result +'<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>';
			 				result = result +'</span>'; */
			 				result = result +'<div class="openPostBtns">';
			 				result = result +'<a href="javascript:void(0)" class="jobApply">Apply</a>';
			 				
			 				if(requirementType == "CrowdSourcingJobs"){
			 					result = result +'<a class="refer" href="javascript:void(0)">Refer</a>';
			 				}
			 				
			 				result = result +'</div>';
			 				result = result +'<div class="openPostDate">';
			 				result = result +'Posted :';
			 				result = result +'<b><i class="fa fa-clock-o"></i> '+requirements.calDays+'</b>';
			 				result = result +'</div>';			  
			 				result = result +'</div>';
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</section>';
			 				
			 				$("#requirementsList").empty();
			       	    	$('#requirementsList').append(result);
		            		
				   }
	   		    }
          } else{
	   		     var result ="";
	    	     result = result+"   <div class='row'>"; 
	    	     result = result +'<div class="clearfix"></div>';
				 result = result+"	<div class='col-lg-12'>"; 
				 result = result+"		<div class='content-area clearfix'>"; 
				 result = result+"			<div class='blank-pages'>";
				 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
				 result = result+"			        <p>No Requirements Found</p>";
				 result = result+"			 </div>";
				 result = result+"		</div>"; 
				 result = result+"	</div>"; 
				 result = result+"  </div> "; 
				$("#requirementsList").empty();
			    $("#requirementsList").append(result); 
   		    }
   	
		 },
       error: function(e){
       }
	 }); 
}
</script>
