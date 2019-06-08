<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src='/resources/js/slead-common.js'></script>
<script type="text/javascript">
//On document ready
$(function(){
    //generateForm();
});
</script>

<script>
function vendorAssociatesList(orgRelationId){
	var result = "";
	result = result  +'<div class="timesheet_head top_filters">';
	//result = result  +'<input type="hidden" id="orgRelationId" value="'+orgRelationId+'">';
	result = result  +'    <div> ';  
	    result = result + '<ul>';
	    
	    result = result + '	<li>';
	    
		result = result + '           </ul>';
		result = result + '       </div>'; 
		result = result + '   </li>';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table mt">';
		result  = result + '   <div id="vendorAssociatesListTab" class="datatable_filter"></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty(); 
    $("#tab-5").empty(); 
    $("#tab-6").empty();
    $("#tab-8").empty();
    $("#tab-7").empty();
    $("#tab-7").append(result);
	
    getVendorRequirementListTab(orgRelationId);
    
}
</script>

<script type="text/javascript">
	function getVendorRequirementListTab(orgRelationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	 	  if(locationList.match("yyyy-MM-dd")){
	 		  associatesVendorDatePattern = 'YYYY-MM-DD';
	 	  }if(locationList.match("dd-MM-yyyy")){
	 		 associatesVendorDatePattern = 'DD-MM-YYYY'; 
	 	  }if(locationList.match("MM-dd-yyyy")){
	 		 associatesVendorDatePattern = 'MM-DD-YYYY'; 
	 	  }if(locationList.match("yyyy-dd-MM")){
	 		 associatesVendorDatePattern = 'YYYY-DD-MM'; 
	 	  }
		$.ajax({
	    	type: "POST",
	    	   url: sourceleadUrl+"/getVendorAssociatesListForVendors?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId, 	
	    	success: function(json) {
	        	
	        ///	var json = JSON.parse(data);
	        
	        	$("#vendorAssociatesListTab").empty();
	    		
	    			var result = "";
	    			result = result +"<table id='vendorAssociatePagination' class='display' cellspacing='0' width='100%' >"; 
		    	    result = result +'<thead>';
		    		result = result + '		<tr>';
		    		   result = result + '	 	<th>Name</th>';
			            result = result + '	     <th>Email</th>';
			            result = result + '	     <th>Location</th>';
			            result = result + '	     <th>Primary Skill</th>';
			            result = result + '	     <th>Resume</th>';
			            result = result + '	     <th>Vendor Contact</th>';
			            result = result + '	     <th>Project Count</th>';
			            result = result + '	     <th>Updated On <br>(<small class="custom-small">'+associatesVendorDatePattern+'</small>)</th>';
		            result = result + '	 </tr>';
		            result = result +'</thead>';
		            result = result + '<tbody>';
		            
			          for (var i=0; i<json.data.length; i++)
					  { 
			        	  var opt = json.data[i];
			        	  result = result + "<tr>";
			            	
			        	  result = result +"<td><div title='"+opt.candidateName+"' class='tbldata-dotdot-100'>"+opt.candidateName+"</div></td>";
			            	  
			             result = result +"  <td><div title='"+opt.email+"' class='tbldata-dotdot-100'>"+opt.email+"</div></td>"; 
			             	
			             	 
			               	if(opt.state!=null && opt.state !='' && opt.state !='undefined' &&  opt.city !='undefined' && opt.city !='' &&  opt.city!=null){
			                    result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.city+', '+opt.state+', '+opt.country+"'>"+opt.city+', '+opt.state+', '+opt.country+"</span></div></td>";
			                }else if(opt.state!=null && opt.state !='' && opt.state !='undefined'){
			                    result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.state+', '+opt.country+"'>"+opt.state+', '+opt.country+"</span></div></td>";
			                }else if(opt.country != null && opt.country !='' && opt.country !='undefined'){
			                    result = result +"<td><div><span title='"+opt.country+"'>"+opt.country+"</span></div></td>";
			                }else{
			                	result = result +"<td><div><span>--</span></div></td>";
			                }
			               	
			               	if(opt.primarySkill != null && opt.primarySkill != '' && opt.primarySkill !='undefined'){
			            	result = result +"<td><div>"+opt.primarySkill+"</div></td>";
			               	}
			               	else{
				            	result = result +"<td><div> --- </div></td>";
	
			               	}
			               	if(opt.candidateResumeName != null && opt.candidateResumeName != '' && opt.candidateResumeName !='undefined'){
		               		result = result +'<td ><div title='+opt.candidateResumeName+' class="tblicons_4"><a download class="glyphicon glyphicon-download-alt" href=\"'+opt.fileUrl+'\"></a>&nbsp;&nbsp;<a class="fa fa-eye"  href="javascript:void(0)" onClick="viewFile(\''+opt.fileUrl+'\',\''+opt.candidateResumeName+'\',\''+opt.candidateResumeFileType+'\');"></a></div></td>';
			               	
			               	}
			               	else{
				            	result = result +"<td><div> --- </div></td>";
	
			               	}
			            	result = result +"<td><div>"+opt.vendorContactName+"</div></td>";
			            	if(opt.assignedProjectsCount != null && opt.assignedProjectsCount != '' && opt.assignedProjectsCount !='undefined'){
			            		result = result +"<td><div>"+opt.assignedProjectsCount+"</div></td>";	
			            	}else{
			            		result = result +"<td><div>--</div></td>";
			            	}
			            	
			            	
			            	result = result +"<td><div>"+opt.candidateUpdateDateToString+"</div></td>";
			            	
			            	result = result +"</div></td>";
			            	result = result +"</tr>";
					  }
			        result = result + '</tbody>';
			        result = result +"</table>";
			        $("#vendorAssociatesListTab").empty();
				    $("#vendorAssociatesListTab").append(result); 
				    vendorAssociatesPagination();
			    	    
	        
	        },
	    });
	}
</script>

<script>
function viewAssociateCandidateResume(candidateId,title){
	var sourceleadUrl ='${sourceleadUrl}';
	window.open(sourceleadUrl+'/viewAssociateCandidateResumeDocView/'+title+'?candidateId='+candidateId,title,"height=600,width=600");
}
</script>

<!--  vendor details ends -->

<script>
function vendorAssociatesPagination(){  
    $('#vendorAssociatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
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
