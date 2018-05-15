<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.collapse-div-adduser select{
width: 100% !important;
    padding-right: 10px !important;
    padding-left: 10px !important;
    border:1px solid #ccc !important
}
</style>

<script type="text/javascript">
//On document ready
$(function(){
    //generateForm();
});
</script>



<script>
function clientRequirementList(orgRelationId){
	
	var result = "";
	result = result  +'<div class="timesheet_head top_filters">';
	result = result  +'<input type="hidden" id="orgRelationId" value="'+orgRelationId+'">';
	result = result  +'    <div> ';  
	    result = result + '<ul>';
	    
	    result = result + '	<li><a class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></a>';
	    result = result + '   	<div class="filterLi dropdown-menu bootstrap_dropdownMenu">';
		result = result + '       	<ul class="paddingLi">';
		result = result + '           	<li><input type="checkbox" value="OPEN"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">Open  </label></li>';
		result = result + '           	<li><input type="checkbox" value="NEW"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">New  </label></li>';
		result = result + '           	<li><input type="checkbox" value="SUBMITTED"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">Submitted </label></li>';
		result = result + '           	<li><input type="checkbox" value="FILLEDCLOSED"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">Filled Closed </label></li>';
		result = result + '           	<li><input type="checkbox" value="UNFILLEDCLOSED"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">Unfilled Closed </label></li>';
		result = result + '           	<li><input type="checkbox" value="ONHOLD"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">On Hold </label></li>';
		result = result + '           	<li><input type="checkbox" value="CLOSED"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">Closed </label></li>';
		result = result + '           	<li><input type="checkbox" value="REJECTED"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">Rejected </label></li>';
		result = result + '           	<li><input type="checkbox" value="CANCEL"  name="requirementStatusFilter" onclick="clientReqList(this)"><label class="paddingLeft">Cancel </label></li>';
		
		//result = result + '           	<li><input type="checkbox" name="rl"><label>Checkbox3</label></li>';
		result = result + '           </ul>';
		result = result + '       </div>'; 
		result = result + '   </li>';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="clientSubmissionRequirementList" class="datatable_filter"></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-6").empty(); 
    $("#tab-7").empty(); 
    $("#tab-5").empty(); 
    $("#tab-5").append(result);  
    clientReqList();
    
}
</script>
				

<!-- client details View starts-->
<script type="text/javascript">
	function clientReqList(orgRelationId) {
		
		var orgRelationId =$("#orgRelationId").val();
		var sourceleadUrl = '${sourceleadUrl}';
		 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  reqListClientDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  reqListClientDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  reqListClientDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  reqListClientDate = 'YYYY-DD-MM'; 
		  } 
		var status = [];
		$('input:checkbox[name=requirementStatusFilter]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
		
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getClientReleasedRequirementList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&status="+status, 
	        success: function(json) {
	        	
    			$("#clientSubmissionRequirementList").empty();
    			
    			
    			 var newCount = 0;
            	 var submittedCount = 0;
            	 var closedCount = 0;
            	 var filledClosedCount = 0;
            	 var unfilledClosedCount = 0;
            	 var openCount = 0;
            	 var rejectedCount = 0;
            	 var onholdCount = 0;
            	 var cancelCount = 0;
    			
				var result = "";
				result = result +'<table id="listReqPagination" class="display" cellspacing="0" width="100%" >'; 
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result + '<th>Title</th>';
	            result = result + '<th>Location</th>';
	            result = result + '<th>Type</th>';
	            result = result + '<th>Posted Date <br>(<small class="custom-small">'+reqListClientDate+'</small>)</th>';
	            result = result + '<th>Owner</th>';
	            result = result + '<th>Applicants</th>';
	            result = result + '<th>Submit to client</th>';
	            result = result + '<th>Offer</th>';
	            result = result + '<th>Interview</th>';
	            result = result + '<th>Status</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	for (var i=0; i<json.data.length; i++)
				  {	        		
	        		var opt = json.data[i];
	        		
	        		
	        		result = result + '<tr>';
               		result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
	                
               		
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
	                result = result +"<td><div class='tblminwidth-100'>"+opt.postedDateStr+"</div></td>";
	                result = result + '<td><div class="tbldata-dotdot-vsm"><span title="'+opt.manager+'">'+opt.manager+'</span</div></td>';
	               	result = result + '<td><div>'+opt.noOfApplicants+'</div></td>';
	            	result = result +"<td><div>"+opt.noOfSubmittedApplicants+"</div></td>";
	                result = result + '<td><div>'+opt.noOfOfferedApplicants+'</div></td>';
	                result = result + '<td><div>'+opt.noOfInterviewedApplicants+'</div></td>';
	               // result = result + '<td><div class="tblminwidth-75"><span title="'+opt.requirementSubmissionStatus+'">'+opt.requirementSubmissionStatus+'</span</div></td>';
	                
   		         if(opt.requirementSubmissionStatus !=null && opt.requirementSubmissionStatus != '' && opt.requirementSubmissionStatus != 'undefined'){
   		        	 /* if(opt.requirementSubmissionStatus == "OPEN"){
   		        		openCount = openCount+1;       
   		        	 }else if(opt.requirementSubmissionStatus == "NEW"){
   		        		newCount = newCount +1;    
   		        	 }else if(opt.requirementSubmissionStatus == "SUBMITTED"){
   		        		submittedCount = submittedCount +1; 
   		        	 }else if(opt.requirementSubmissionStatus == "FILLEDCLOSED"){
   		        		filledClosedCount = filledClosedCount +1;   
   		        	 }else if(opt.requirementSubmissionStatus == "UNFILLEDCLOSED"){
   		        		unfilledClosedCount = unfilledClosedCount +1;   
   		        	 }else if(opt.requirementSubmissionStatus == "ONHOLD"){
   		        		onholdCount = onholdCount +1;   
   		        	 }else if(opt.requirementSubmissionStatus == "CLOSED"){
   		        		closedCount = closedCount +1;   
   		        	 }else if(opt.requirementSubmissionStatus == "REJECTED"){
   		        		rejectedCount = rejectedCount +1;   
   		        	 }else if(opt.requirementSubmissionStatus == "CANCEL"){
   		        		cancelCount = cancelCount +1;   
   		        	 } */
 	                result = result + '<td><div class="width50px"><span title="'+opt.requirementSubmissionStatus+'">'+opt.requirementSubmissionStatus+'</span</div></td>';
   		         }
   		         else{
   		        	result = result + '<div class="tblminwidth-75 ">---</div>';
   		         }
	               
	                result = result +'</div></td>';
	                result = result + '	 </tr>';
				} 
	        	result = result +'</tbody>';
	        	result = result +"</table>";
	        	
	        	/* $("#openCount").html(openCount);
        		$("#newCount").html(newCount);
        		$("#unfilledClosedCount").html(unfilledClosedCount);
        		$("#filledClosedCount").html(filledClosedCount);
        		$("#onholdCount").html(onholdCount);
        		$("#closedCount").html(closedCount);
        		$("#rejectedCount").html(rejectedCount);
        		$("#cancelCount").html(cancelCount);
        		$("#submittedCount").html(submittedCount); */
	        	
    	    $("#clientSubmissionRequirementList").append(result); 
    	    clientRequirementListpagination(); 
    	  
	        },
	    });
	}
</script>


 <script>
function clientRequirementListpagination(){  
	$('#listReqPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
	$('#listReqPagination').wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
}
</script>
