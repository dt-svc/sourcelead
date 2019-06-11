<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.competencePaddingRight{
padding: 0px 240px 4px 0px;
}
</style>


<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Performance Rating Scale</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
      <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
      <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo; Performance Rating Scale <span id = "fname"></a> </div>
</section>

<div class="clearfix">
           <ul class="project-tabs-menu" id="tab_bar">
            
	            <li class="current" id="scaleList">
	            	<a href="#"  onclick="getRatingScaleList();" >
	              		Rating Scale List
	              	</a> 
	            </li>
	            
	             <li id="newRatingScale">
	            	<a href="#"  onclick="getRatingScaleLevelsList();">
	              		Rating Levels List
	              	</a> 
	            </li>
          </ul>
 </div>
 
 
<div class="content-area content-new clearfix backWhite">
 
 <div id="ratingScaleId" class="positionRelative reportHead competencePaddingRight clearfix">
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
				          <a onclick="performanceManagement.addNewRatingScale();">Add Rating Scale</a> 
				          <a onclick="performanceManagement.addRatingScaleLevels();" >Add Rating Level</a>
					</span>
				</div>
			</li>
        </ul>
      </div>
          
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table datatable_filter positionRelative" style="position: relative;top: -8px;">
	          <div id="ratingListLoading"></div>
	         
		     <div class="project_wrap_table datatable_filter dataSeaTop mt10" style="position: relative;top: -8px;" >
		    	<div id="ratingScaleList" class="datatable_filter"></div>
			</div>	  
			
		   <div class="project_wrap_table datatable_filter dataSeaTop mt10" style="position: relative;top: -8px;">
	    	<table id="ratingLevelsList" class="display" cellspacing="0" width="100%"></table>
		   </div>
		
          </div>
        
      </div>
      
     <jsp:include page="../performance/addNewRatingScaleLevels.jsp" />    
     
     
     
     
     
<script>
function getRatingScaleLevelsList(){
	 $("#scaleList").removeClass("current");
	 $("#newRatingScale").addClass("current");
	$("#ratingListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: sourceleadUrl+"/getRatingLevelsList?",
		     success :function(response) {
		    	 
		    	 	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    	    		    showError(response.responseMessage);
    	    		    $("#ratingListLoading").removeAttr('style');
    	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		
		        	  var dateFormat = '${SourceLeadWeb_dateFormat}';
		       		  if(dateFormat.match("yyyy-MM-dd")){
			   			  uploadedDateStr = 'YYYY-MM-DD';
			   		  }if(dateFormat.match("dd-MM-yyyy")){
			   			uploadedDateStr = 'DD-MM-YYYY'; 
			   		  }if(dateFormat.match("MM-dd-yyyy")){
			   			uploadedDateStr = 'MM-DD-YYYY'; 
			   		  }if(dateFormat.match("yyyy-dd-MM")){
			   			uploadedDateStr = 'YYYY-DD-MM'; 
			   		  }
		        		
		        	   var result = "";
 		        	   result = result + '   <div class="clearfix"></div>';
 			           result = result + '   </div>';
 			           result = result + '   <div class="">';
 			           result = result + '     <table id="ratingListPegination" class="display" class="display mt10" cellspacing="0" width="100%">';
 			           result = result + '       <thead>';
 			           result = result + '         <tr>';
 			           result = result + '           <th>Level Name</th>';
 			           result = result + '           <th>Description</th>';
 			           result = result + '           <th>Date<br>(<small class="custom-small">'+uploadedDateStr+'</small>)</th>';
 			           result = result + '           <th>Action</th>';
 			           result = result + '         </tr>';
 			           result = result + '       </thead>';
 			           result = result + '       <tbody>';
 			           
		        		if(response.data.length > 0){
		        			for (var i=0; i<response.data.length; i++) {
		    	        		var report = response.data[i];
		    	        		
		    			        		    
		    			        		    result = result + '<tr>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me" title="'+report.name+'">'+report.name+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.description+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.date+' / '+report.time+'</div></td>';
		    			        		    result = result+'<td>';
		    			        		    result = result+'<div class="tblminwidth-50 pull-left ml">'; 
		    			        		    result = result	+'<div class="pull-left"><a><i class="fa fa-pencil text-success" onclick="performanceManagement.editPerformanceRatingLevel(\''+report.id+'\');"></i></a></div>';
		    			        		    result = result	+'<div class="pull-left mt2 ml5"><a><i class="fa fa-trash text-danger" onclick="performanceManagement.deletePerformanceRatingLevel(\''+report.id+'\');"></i></a></div>';
		    			        		    result = result+'</div></td>';
		    			        		    result = result + '</tr>';
		    					          
		    		  				
		    				}
		        			  
		        		}else{
		        			 result = result + '<tr class="odd"><td valign="top" colspan="4" class="dataTables_empty"></td></tr>';
			    		}
		        		
		        		   result = result + '</tbody>';
				           result = result + '</table>';
				           result = result + '</div>';
				           result = result + '</div>';
				           result = result + '<div id="choicepopUp"></div>';
				            $("#ratingListLoading").removeAttr('style');
				            $("#ratingScaleList").empty();
				            $("#ratingLevelsList").empty();
			    			$("#ratingLevelsList").append(result);
			    			ratingLevelsListPegination();
		        		  
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	} 
		    	  
				},
			});
}
</script>
 
 
   <script>
function ratingLevelsListPegination(){ 
	$('#ratingListPegination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script> 
 
      <script>
function getRatingScaleList(){
	 $("#newRatingScale").removeClass("current");
	 $("#scaleList").addClass("current");
	$("#ratingListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: sourceleadUrl+"/getRatingScaleList?",
		     success :function(response) {
		    	 
		    	 	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    	    		    showError(response.responseMessage);
    	    		    $("#ratingListLoading").removeAttr('style');
    	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		
		        	  var dateFormat = '${SourceLeadWeb_dateFormat}';
		       		  if(dateFormat.match("yyyy-MM-dd")){
			   			  uploadedDateStr = 'YYYY-MM-DD';
			   		  }if(dateFormat.match("dd-MM-yyyy")){
			   			uploadedDateStr = 'DD-MM-YYYY'; 
			   		  }if(dateFormat.match("MM-dd-yyyy")){
			   			uploadedDateStr = 'MM-DD-YYYY'; 
			   		  }if(dateFormat.match("yyyy-dd-MM")){
			   			uploadedDateStr = 'YYYY-DD-MM'; 
			   		  }
		        		
		        	   var result = "";
 		        	   result = result + '   <div class="clearfix"></div>';
 			           result = result + '   </div>';
 			           result = result + '   <div class="">';
 			           result = result + '     <table id="scaleListPegination" class="display" class="display mt10" cellspacing="0" width="100%">';
 			           result = result + '       <thead>';
 			           result = result + '         <tr>';
 			           result = result + '           <th>Scale Name</th>';
 			           result = result + '           <th>Short Code</th>';
 			           result = result + '           <th>Level Name</th>';
 			           result = result + '           <th>Scale Value</th>';
 			           result = result + '           <th>Date<br>(<small class="custom-small">'+uploadedDateStr+'</small>)</th>';
 			           result = result + '           <th>Action</th>';
 			           result = result + '         </tr>';
 			           result = result + '       </thead>';
 			           result = result + '       <tbody>';
 			           
		        		if(response.data.length > 0){
		        			for (var i=0; i<response.data.length; i++) {
		    	        		var report = response.data[i];
		    	        		
		    			        		    
		    			        		    result = result + '<tr>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me" title="'+report.ratingName+'">'+report.ratingName+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me" title="'+report.shortCode+'">'+report.shortCode+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me" title="'+report.levelName+'">'+report.levelName+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me" title="'+report.ratingScaleValue+'">'+report.ratingScaleValue+'</div></td>';
		    			        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.date+' / '+report.time+'</div></td>';
		    			        		    result = result+'<td>';
		    			        		    result = result+'<div class="tblminwidth-50 pull-left ml">'; 
		    			        		    result = result	+'<div class="pull-left"><a><i class="fa fa-pencil text-success" onclick="performanceManagement.addNewRatingScale(\''+report.id+'\')"></i></a></div>';
		    			        		    result = result	+'<div class="pull-left mt2 ml5"><a><i class="fa fa-trash text-danger" onclick="performanceManagement.deletePerformanceRatingScale(\''+report.id+'\');"></i></a></div>';
		    			        		    result = result+'</div></td>';
		    			        		    result = result + '</tr>';
		    					          
		    		  				
		    				}
		        			  
		        		}else{
		        			 result = result + '<tr class="odd"><td valign="top" colspan="4" class="dataTables_empty"></td></tr>';
			    		}
		        		
		        		   result = result + '</tbody>';
				           result = result + '</table>';
				           result = result + '</div>';
				           result = result + '</div>';
				           result = result + '<div id="choicepopUp"></div>';
				            $("#ratingListLoading").removeAttr('style');
				            $("#ratingLevelsList").empty();
				            $("#ratingScaleList").empty();
			    			$("#ratingScaleList").append(result);
			    			scalesListPegination();
		        		  
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	} 
		    	  
				},
			});
}
</script>
      
      
<script>
function scalesListPegination(){ 
	$('#scaleListPegination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script> 
      
    <script>
	function getActions(){
		$(".action-pop").slideToggle(200);
		$(".newActPop").slideUp(200);
	}	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
	});
</script>
 