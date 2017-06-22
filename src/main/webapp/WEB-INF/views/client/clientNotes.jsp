<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
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
function clientNotes(orgRelationId){
	
	var result = "";
	result = result  +'<div class="timesheet_head top_filters">';
	result = result  +'<input type="hidden" id="orgRelationId" value="'+orgRelationId+'">';
	result = result  +'    <div> ';  
	    result = result + '<ul>';
		result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addEducation();" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Education</a></span>';

	    result = result + '	<li><a class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></a>';
	    result = result + '   	<div class="filterLi dropdown-menu bootstrap_dropdownMenu">';

		result = result + '       	<ul>';
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
		result  = result + '   <div id="clientNotesDivtList" ></div>';
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
    $("#tab-8").empty(); 
    $("#tab-8").append(result); 
    
    clientNotesList();
    
}
</script>
	
	
	
	<script>
function addEducation() {	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="education_form" name="education_form" method="post" > ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	result = result  +'                  <div class="clearfix mb10 col-md-12" id="nameIdDiv"> ';
	
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="specialization" field-name="specialization" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Specialization">                                          ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
	result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
	result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	
	result  = result + '                 <div class="clearfix mb10">';
	result = result  +'	                     <div class="col-md-5" positionRelative>';
	result = result  +'	                     <select class="chosen-select"  name="gradeType" id="scoreType" onchange="selectScoreType();clearSearchKey();">';
	result = result  +' 					   <option value="" selected="selected">Score Type</option>';
	result = result  +'	                       <option value="Percentage">Percentage</option>';
	result = result  +'	                       <option value="CGPA">CGPA</option>';
	result = result  +'	                       <option value="GRADE">GRADE</option>';
	result = result  +'	                      </select>';
	result = result  +'	                   </div>';
	result  = result + '                   <div class="col-md-7" positionRelative id="candidateScoreDiv">';
	result  = result + '                     <input type="text" class="form-control" value="" name="grade" placeholder="Score" id="candidateScore" onkeyup="selectScoreType();"';
	result  = result + '                     data-validation-optional="true" data-validation="required validate_length length1-5" field-name="score">';
	result  = result + '                 	 </div>';
	result = result  +'	                   </div>';
	
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="institute" field-name="institute" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Institute">                                               ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" field-name="Address" data-validation="required validate_Space validate_length length2-250" data-validation-optional="true" name="address" class="form-control input-sm" value="" placeholder="Address">                                                 ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mr">                                                                                                            ';
    result = result +'<input type="button" value="Add" name="btnAddPr" id="save_button" class="btn btn-sm btn-primary pull-right" onClick ="candidateEducationStore('+candidateId+');" />'; 
	/* result = result  +'                  <input onClick ="candidateEmployersStore('+candidateId+');" type="submit" value="Add" class="btn btn-sm btn-primary pull-right"/>                                                       '; */
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                </div>                                                                                                                                 ';
	result = result  +'                </form>                                                                                                                                ';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	
	}
</script>
				

<!-- client details View starts-->
<script type="text/javascript">
	function clientNotesList(orgRelationId) {
		
		var orgRelationId =$("#orgRelationId").val();
		var sourceleadUrl = '${sourceleadUrl}';
		var status = [];
		$('input:checkbox[name=requirementStatusFilter]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
		
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getClientReleasedRequirementList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&status="+status, 
	        success: function(data) {
	        	
	        var json = JSON.parse(data);
    			$("#clientNotesDivtList").empty();
    			
    			
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
				result = result +'<table id="clientNotesForPagination" class="display" cellspacing="0" width="100%" >'; 
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result + '<th>Title</th>';
	            result = result + '<th>Location</th>';
	            result = result + '<th>Type</th>';
	            result = result + '<th>Posted Date</th>';
	            result = result + '<th>Owner</th>';
	            result = result + '<th>Submissions</th>';
	            result = result + '<th>Offer</th>';
	            //result = result + '<th>Back-out</th>';
	            result = result + '<th>Interview</th>';
	            result = result + '<th>Status</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	for (var i=0; i<json.length; i++)
				  {	        		
	        		var opt = json[i];
	        		
	        		
	        		result = result + '<tr>';
               		result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
	                
	                if(opt.state!=null && opt.state !='' && opt.state !='undefined' &&  opt.city !='undefined' && opt.city !='' &&  opt.city!=null){
	                    result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.city+', '+opt.state+', '+opt.country+"'>"+opt.city+', '+opt.state+', '+opt.country+"</span></div></td>";
	                }else if(opt.state!=null && opt.state !='' && opt.state !='undefined'){
	                    result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.state+', '+opt.country+"'>"+opt.state+', '+opt.country+"</span></div></td>";

	                }else{
	                    result = result +"<td><div><span title='"+opt.country+"'>"+opt.country+"</span></div></td>";
	                }
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
	                result = result +"<td><div class='tblminwidth-75'>"+opt.postedDateStr+"</div></td>";
	                result = result + '<td><div class="tbldata-dotdot-vsm"><span title="'+opt.manager+'">'+opt.manager+'</span</div></td>';
	               	result = result +"<td><div>"+opt.noOfSubmittedApplicants+"</div></td>";
	                result = result + '<td><div>'+opt.noOfOfferedApplicants+'</div></td>';
	               // result = result + '<td><div>'+opt.noOfBackOutApplicants+'</div></td>';
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
 	                result = result + '<td><div class="tblminwidth-75"><span title="'+opt.requirementSubmissionStatus+'">'+opt.requirementSubmissionStatus+'</span</div></td>';
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
	        	
    	    $("#clientNotesDivtList").append(result); 
        		clientNotespagination(); 
    	  
	        },
	    });
	}
</script>


 <script>
function clientNotespagination(){  
	$('#clientNotesForPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
	$('#clientNotesForPagination').wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
}
</script>
