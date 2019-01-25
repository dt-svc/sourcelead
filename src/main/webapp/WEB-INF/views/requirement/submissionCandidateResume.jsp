<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 

<script>
function getCandidateResume(candidateId){
	var result ="";
	result = result+'	    <div class="project_tabs_inn_head addUser-org">';
		result = result  +'     <div id="popUpDivResume" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="resumesList" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	
	$("#tabb-1").empty();
	$("#tabb-2").empty(); 
	$("#tabb-3").empty();
	$("#tabb-3").append(result);
	
	candidateResumeById(candidateId);
	
    
}
</script>

<script>
function candidateResumeById(candidateId){
	  var candidateId = candidateId;
	  var result = "";
	  var resumesCount = 0;
	  $.ajax({
			type : "POST",
			url : sourceleadUrl+"/candidateResumesListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
			success : function(json) {
// 				var json = JSON.parse(data);
// 				if( data.indexOf('Sign In') >= 0){
// 		    		location.replace(sourceleadUrl);
// 		    	 }
//        		 else if (data.indexOf('exception') >= 0) {
//        			 location.replace(sourceleadUrl+"/errorPage");
//        		 }
//        		 else{
					if(json.data.length<=0 || (json == null && json == 'undefined' && json == "")){
							result = result+"   <div class='row'>"; 
						 	result = result+"	<div class='col-lg-12'>"; 
						 	result = result+"		<div class='content-area clearfix'>"; 
						 	result = result+"			<div class='blank-pages'>";
						 	result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
						 	result = result+"			        <p>No Resumes Found</p>";
						 	result = result+"			 </div>";
						 	result = result+"		</div>"; 
						 	result = result+"	</div>"; 
						 	result = result+"  </div> "; 
						}else{
							result = result+'	      <table>';
							result = result+'	        <tbody>';
							result = result+'	          <tr>';
							result = result+'	            <th>Title</th>';
							result = result+'	            <th>Attachment</th>';
							result = result+'	            <th>Description</th>';
							result = result+'	            <th>Created By</th>';
							result = result+'	            <th>Action</th>';
							result = result+'	          </tr>';
							
							
				             for (var i = 0; i < json.data.length; i++) {
				            	 	resumesCount = json.data.length;
					            	var opt = json.data[i];
					            	var title = "";
					            	var description = "";
					            	if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
					            		title = opt.title;
					            	}else{
					            		title = opt.fileName;
					            	}
					            	if(opt.description != null && opt.description != 'undefined' && opt.description != ""){
					            		description = opt.description;
					            	}else{
					            		description = '----';
					            	}
					            	if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
					            		createdBy = opt.createdBy;
					            	}else{
					            		createdBy = '----';
					            	}
					            	result = result+'	          <tr>';
					           		result = result+'	            <td><div><span title="'+title+'">'+title+'</span></div></td>';
					           		result = result+'	            <td><div><span title="'+opt.fileName+'">'+opt.fileName+'</span></div></td>';
					           		result = result+'	            <td><div><span title="'+description+'">'+description+'</span></div></td>';
					           		result = result+'	            <td><div><span title="'+createdBy+'">'+createdBy+'</span></div></td>';
					           		result = result+'	            <td><div class="tblicons"><a download class="glyphicon glyphicon-download-alt" title="Download" target="_blank" href=\"'+opt.fileURL+'\"></a>';
					           		result = result+'	            </div></td>';
					           		result = result+'	          </tr>';
				             }
				            result = result+'	          </tbody>';
				       		result = result+'	      </table>';
						}
					$("#resumesList").empty();
					$("#resumesList").append(result);
					
//        		}
				},
		});
}
</script>

