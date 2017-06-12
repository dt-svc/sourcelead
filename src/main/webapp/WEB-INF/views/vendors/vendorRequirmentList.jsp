<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="resources/js/formvalidator.js"></script>
<style type="text/css">
.collapse-div-adduser select{
width: 100% !important;
    padding-right: 10px !important;
    padding-left: 10px !important;
    border:1px solid #ccc !important
}
</style>
<div id="loading"></div>


<div id="popUp"></div>	

<script>
function vendorRequirementListInclude(orgRelationID){
	var result = "";
	result = result  +'<div class="timesheet_head">';
// 	result  = result + '<div id="vendorConactMsgDiv" ></div>';
// 	result  = result + '<div class="success_pop" id="vendorConactTerminateMsgDiv" style="display: none;">';
//     result  = result + '<i class="fa fa-check-circle"></i><span id="vendorConactTerminateMsg"></span>';
    	result  = result + '</div>';
		result = result  +'    <div class="timesheet_head addUser-org"> ';  
	    result = result + '<ul>';
	    
	    
	    result = result + '<li class="dropdown mt10"><div class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></div>';
        result = result + '<div class="dropdown-menu bootstrap_dropdownMenu dropdown-menu_width">                                                                                                                                       ';
        result = result + '<ul>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="open" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">OPEN</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="new" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">NEW</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="filledClose" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">FILLED CLOSED</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="unfilledClose" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">UNFILLED CLOSED</label>';
        result = result + '</li>';
        result = result + '<li>';
        result = result + '<input type="checkbox" value="onhold" name="requirementStatus" id="requirementStatus" onclick="vendorReqListFunction('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">ON HOLD</label>';
        result = result + '</li>';
       /*  result = result + '<li>';
        result = result + '<input type="checkbox" value="unfilledClose" name="requirementStatus" id="requirementStatus" onclick="test('+orgRelationID+');" >';
        result = result + '<label class="paddingLeft">On Hold</label>';
        result = result + '</li>'; */
        result = result + '</ul>';
        result = result + '</li>';
	   
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="vendorRequirmentListDiv" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty(); 
    $("#tab-5").empty(); 
    $("#tab-6").append(result);  
	
    vendorReqListFunction(orgRelationID);
    
}
</script>

<script type="text/javascript">
	function vendorReqListFunction(orgRelationID) {
		// var orgRelationId =$("#orgRelationId").val();
	        var sourceleadUrl = '${sourceleadUrl}';
	        var status = [];
	        $('input:checkbox[name=requirementStatus]:checked').each(function()
	        {
	            status.push($(this).val());
	        });
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	   url: sourceleadUrl+"/getVendorJobs?${_csrf.parameterName}=${_csrf.token}&orgRelationID="+orgRelationID+"&status="+status, 	
	    	success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        
	        	$("#vendorRequirmentListDiv").empty();
	    		
	    			var result = "";
	    			result = result +"<table id='vendorRequirmentPagination' class='display' cellspacing='0' width='100%' >"; 
		    	    result = result +'<thead>';
		    		result = result + '		<tr>';
		    		   result = result + '	 	<th>Title</th>';
			            result = result + '	     <th>Type</th>';
			            result = result + '	     <th>Location</th>';
			            result = result + '	     <th>Posted Date</th>';
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
		            
			          for (var i=0; i<json.length; i++)
					  { 
			        	  var opt = json[i];
			        	  result = result + "<tr>";
			            	result = result +"<td><div title='"+opt.jobTitle+"' class='tbldata-dotdot-100'>"+opt.jobTitle+"</div></td>";
			               //	result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.parentRequirement.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";

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
			               	if(opt.state!=null && opt.state !='' && opt.state !='undefined' &&  opt.city !='undefined' && opt.city !='' &&  opt.city!=null){
			                    result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.city+', '+opt.state+', '+opt.country+"'>"+opt.city+', '+opt.state+', '+opt.country+"</span></div></td>";
			                }else if(opt.state!=null && opt.state !='' && opt.state !='undefined'){
			                    result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.state+', '+opt.country+"'>"+opt.state+', '+opt.country+"</span></div></td>";

			                }else{
			                    result = result +"<td><div><span title='"+opt.country+"'>"+opt.country+"</span></div></td>";
			                }
			               	
			               	
			                if(opt.postedDateStr!=null && opt.postedDateStr !='' && opt.postedDateStr !='undefined'){
			                 	 result = result +"<td><div class='tblminwidth-75'>"+opt.postedDateStr+"</div></td>";
			                  }else{
			                 	 result = result +"<td><div class='tblminwidth-75'>----</div></td>";
			                  }
			                
			                
			                if(opt.manager!=null && opt.manager!='' && opt.manager!='undefined'){
			                  	 result = result +"<td><div class='tbldata-dotdot-100' title='"+opt.manager+"'>"+opt.manager+"</div></td>";
			                   }else{
			                  	 result = result +"<td><div>----</div></td>";
			                   }
			                
			            	//result = result +"<td><div>"+opt.totalApplicants+"</div></td>";
 
			            	result = result +"<td><div>"+opt.totalApplicants+"</div></td>";
			            	result = result +"<td><div>"+opt.vendorInterviewsCount+"</div></td>";
			            	result = result +"<td><div>"+opt.vendorOfferedCount+"</div></td>";
			            	result = result +"<td><div>"+opt.vendorBackoutCount+"</div></td>";
			            	result = result +"<td><div>"+opt.requirementSubmissionStatus+"</div></td>";
			            	result = result +"</div></td>";
			            	result = result +"</tr>";
					  }
			        result = result + '</tbody>';
			        result = result +"</table>";
			        $("#vendorRequirmentListDiv").empty();
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
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
		
    } );
}
</script>

 <!-- This Script is For Popup Div Starts-->  
