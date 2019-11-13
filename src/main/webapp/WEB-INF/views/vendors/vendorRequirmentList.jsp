<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style type="text/css">
.collapse-div-adduser select{
	width: 100% !important; padding-right: 10px !important; padding-left: 10px !important; border:1px solid #ccc !important
}
</style>
<div id="loading"></div>


<div id="popUp"></div>	

<script>
function vendorRequirementListInclude(orgRelationID){
	var result = "";
	result = result  +'<div class="timesheet_head top_filters">';
	result = result  +'    <div> ';  

	    result = result + '<ul>';
	    
	    
	    result = result + '	<li><a class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></a>';
	    result = result + '   	<div class="filterLi dropdown-menu bootstrap_dropdownMenu">';
	    result = result + '<ul  class="paddingLi">';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="open" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">Open</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="new" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">New</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="filledClose" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">Filled Closed</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="unfilledClose" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">Unfilled Closed</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="onhold" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">On Hold</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="cancel" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">Cancel</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="Rejected" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">Rejected</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="Closed" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">Closed</label>';
        result = result + '</li>';
       /*  result = result + '<li>';
        result = result + '<input type="checkbox" value="unfilledClose" name="requirementStatus" id="requirementStatus" onclick="test('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">On Hold</label>';
        result = result + '</li>'; */
        result = result + '</ul>';
		result = result + '       </div>'; 
		result = result + '   </li>';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="vendorRequirmentListDiv" class="datatable_filter"></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty(); 
    $("#tab-5").empty(); 
    $("#tab-8").empty();
    $("#tab-6").empty(); 
    $("#tab-6").append(result);  
	
    vendorReqListFunction(orgRelationID);
    
}
</script>

<script type="text/javascript">
	function vendorReqListFunction(orgRelationID) {
		// var orgRelationId =$("#orgRelationId").val();
	        var sourceleadUrl = '${sourceleadUrl}';
	        var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  	  if(locationList.match("yyyy-MM-dd")){
	  		  reqVendorDatePattern = 'YYYY-MM-DD';
	  	  }if(locationList.match("dd-MM-yyyy")){
	  		reqVendorDatePattern = 'DD-MM-YYYY'; 
	  	  }if(locationList.match("MM-dd-yyyy")){
	  		reqVendorDatePattern = 'MM-DD-YYYY'; 
	  	  }if(locationList.match("yyyy-dd-MM")){
	  		reqVendorDatePattern = 'YYYY-DD-MM'; 
	  	  } 
	        var status = [];
	        $('input:checkbox[name=requirementStatus]:checked').each(function()
	        {
	            status.push($(this).val());
	        });
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	   url: sourceleadUrl+"/getVendorJobs?"+tokenParamName+"="+tokenValue+"&orgRelationID="+orgRelationID+"&status="+status, 	
	    	success: function(json) {
	        	
	        
	        	$("#vendorRequirmentListDiv").empty();
	        	
	       	 var openCount = 0;
        	 var newCount = 0;
        	 var filledClosedCount = 0;
        	 var unfilledCount = 0;
        	 var onHoldCount = 0;
        	 var cancelCount = 0;
        	 var rejectedCount = 0;
        	 var closedCount = 0;
	        	
	    			var result = "";
	    			result = result +"<table id='vendorRequirmentPagination' class='display' cellspacing='0' width='100%' >"; 
		    	    result = result +'<thead>';
		    		result = result + '		<tr>';
		    		   result = result + '	 	<th>Title</th>';
			            result = result + '	     <th>Type</th>';
			            result = result + '	     <th>Location</th>';
			            result = result + '	     <th>Posted Date<br>(<small class="custom-small">'+reqVendorDatePattern+'</small>)</th>';
			            result = result + '	     <th>Owner</th>';
			            //result = result + '	     <th>No Of Contacts</th>';
			            result = result + '	     <th>Applicants</th>';
			            result = result + '	     <th>Interview</th>';
			            result = result + '	     <th>Offer</th>';
			            result = result + '	     <th>Back-out</th>';
			            result = result + '	     <th>Status</th>';
		            result = result + '	 </tr>';
		            result = result +'</thead>';
		            result = result + '<tbody>';
			          for (var i=0; i<json.data.length; i++)
					  { 
			        	  var opt = json.data[i];
			        	  result = result + "<tr>";
			            	// result = result +"<td><div title='"+opt.jobTitle+"' class='tbldata-dotdot-100'>"+opt.jobTitle+"</div></td>"; 
			           	result = result +"<td><div class='tbldata-dotdot'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";

			            	  if(opt.jobType != null && opt.jobType != '' && opt.jobType != "undefined")
			             	 {
			            	  if(opt.otherJobType !=null && opt.otherJobType !='' && opt.otherJobType !="undefined"){
			                   result = result +"  <td><div>"+opt.otherJobType+"</div></td>";
			             	 }else{
			             		result = result +"  <td><div class='tblminwidth-75'>"+opt.jobType+"</div></td>"; 
			             	 }
			             	 }else{
			             		 result = result +"  <td><div class='tblminwidth-75'>----</div></td>"; 
			             	 }
			            	  
			            	  result = result +'<td>';
			   		        	result = result +'<div class="positionRelative">';
				   		         for(var k=0; k<1; k++){
				   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.locationsList[k]+'</p>';
				   		         }
			   		    	
				   		        result = result +'<div class="show_Empdetails showjoblist">';
			   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';
		
			   		        	result = result +'<div class="emailvalues">';
			   		        	
	 							for(var k=0; k<opt.locationsList.length; k++){
	 								result = result +'<p><i class="fa fa-map-marker"></i>'+opt.locationsList[k]+'</p>';
	 			   		        	
			   		    	 	}
			   		        	result = result +'</div>';
			   		        	result = result +'</div>';
			   

		   		        	result = result +'</div>';
		   		        	result = result +'</td>';
			               	
			                if(opt.postedDateStr!=null && opt.postedDateStr !='' && opt.postedDateStr !='undefined'){
			                 	 result = result +"<td><div class='tblminwidth-100'>"+opt.postedDateStr+"</div></td>";
			                  }else{
			                 	 result = result +"<td><div class='tblminwidth-100'>----</div></td>";
			                  }
			                
			                
			                if(opt.manager!=null && opt.manager!='' && opt.manager!='undefined'){
			                  	 result = result +"<td><div class='tbldata-dotdot' title='"+opt.manager+"'>"+opt.manager+"</div></td>";
			                   }else{
			                  	 result = result +"<td><div class='tbldata-dotdot'>----</div></td>";
			                   }
			                
			            	//result = result +"<td><div>"+opt.totalApplicants+"</div></td>";
 							result = result +"<td><div class='tblminwidth-75'>"+opt.noOfSubmittedApplicants+"</div></td>";
			            	result = result +"<td><div class='tblminwidth-75'>"+opt.vendorInterviewsCount+"</div></td>";
			            	result = result +"<td><div class='tblminwidth-75'>"+opt.vendorOfferedCount+"</div></td>";
			            	result = result +"<td><div class='tblminwidth-75'>"+opt.vendorBackoutCount+"</div></td>";
			            	
			            	result = result +"<td><div>"+opt.requirementSubmissionStatus+"</div></td>";
			            	
			            	result = result +"</div></td>";
			            	/* if(opt.requirementSubmissionStatus == "OPEN"){
			            		openCount = openCount+1;       
       		        	 }else if(opt.requirementSubmissionStatus == "NEW"){
       		        		newCount = newCount +1;    
       		        	 }else if(opt.requirementSubmissionStatus == "FILLEDCLOSED"){
       		        		filledClosedCount = filledClosedCount +1; 
       		        	 }else if(opt.requirementSubmissionStatus == "UNFILLEDCLOSED"){
       		        		unfilledCount = unfilledCount +1;   
       		        	 }else if(opt.requirementSubmissionStatus == "ONHOLD"){
       		        		onHoldCount = onHoldCount +1;   
       		        	 }else if(opt.requirementSubmissionStatus == "CANCEL"){
       		        		cancelCount = cancelCount +1;   
       		        	 }else if(opt.requirementSubmissionStatus == "REJECTED"){
       		        		rejectedCount = rejectedCount +1;   
       		        	 }else if(opt.requirementSubmissionStatus == "CLOSED"){
       		        		closedCount = closedCount +1;     
       		        	 } */
			            	
			            	result = result +"</tr>";
					  }
			        result = result + '</tbody>';
			        result = result +"</table>";
			        $("#vendorRequirmentListDiv").empty();
			      /*   $("#openCountId").html(openCount);
            		$("#newCountId").html(newCount);
            		$("#filledClosedCountId").html(filledClosedCount);
            		$("#unfilledCountId").html(unfilledCount);
            		$("#onHoldCountId").html(onHoldCount);
            		$("#cancelCountId").html(cancelCount);
            		$("#rejectedCountId").html(rejectedCount);
            		$("#closedCountId").html(closedCount); */
				    $("#vendorRequirmentListDiv").append(result); 
				    contactRequirmentlistPagination();                     
				    
				    
			    	    
	        
	        },
	    });
	}
</script>
<!--  vendor details ends -->

<script>
function contactRequirmentlistPagination(){  
    $('#vendorRequirmentPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 9
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#vendorRequirmentPagination').wrap("<div class='overflow_Auto width100Percnt'></div>");
}
</script>

 <!-- This Script is For Popup Div Starts-->  
