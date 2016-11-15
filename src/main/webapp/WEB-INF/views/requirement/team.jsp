<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">

<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script> 

<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>


<style type="text/css">
.teamEditDetails {
margin-bottom: 50px;
border-bottom:1px solid #ccc;
padding-bottom:5px;
}
.teamEditDetails select.form-control{
width:100% !important;
padding: 0 10px !important;
border: 1px solid #ccc;
background:url("resources/images/arrow-w.png") no-repeat 95% center; background-size:10px;
}
</style>

<!-- end files for autocomplete with tagit -->
<script>
 window.onload = function() { 
 	 teamList();
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
function teamList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#teamList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
		  type : "POST",
            url: sourceleadUrl+"/getTeamListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}",       
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#teamList").removeAttr("style");
	                	 $("#teamList").empty();
	                	 var result = "";
	                	 result = result +"<table id='teamPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Team Name</th>"; 
	                     result = result +"    <th>Team Leader</th>"; 
	                     result = result +"    <th>Members</th>"; 
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
	                    	
				             name:result = result +"<td><div><span>"+opt.name+"</span></div></td>";
				             if(opt.teamOwnerName != null && opt.teamOwnerName != '' && opt.teamOwnerName != 'undefined'){
				             teamOwnerName:result = result +"<td><div><span>"+opt.teamOwnerName+"</span></div></td>";
				             }else{
				            	 result = result +"<td>-----</td>";
				             }
		            	     result = result +"<td><div><span>"+opt.teamMembersCount+"</span></div></td>";
				             result = result +"<td><div class='tblicons'><i class='glyphicon glyphicon-pencil' onclick='sideBar("+opt.id+");'></i><i class='glyphicon glyphicon-trash' onclick='deleteTeam("+opt.id+");'></i></div></td>";
				             result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#teamList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
                	 $("#teamList").removeAttr("style");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Team Found</p>";
					 result = result+"			 </div>";
					
					$("#teamList").empty();
				    $("#teamList").append(result); 
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
    $('#teamPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
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
function sideBar(teamId){
	
	    var sourceleadUrl ='${sourceleadUrl}';
	      
	    var outPut = '';
	    outPut = outPut+'<div id="removeCandidateAlert" ></div>  ';
		outPut = outPut+ '	<div class="project_side_wrap_inn_tabs">';
		outPut = outPut+ '    <div class="project_tabs">';
		outPut = outPut+ '      <div id="tab-1" class="project_tabs_inn">';
		outPut = outPut+ '        <div  class="project_tabs_inn_head"> <a href="#" class="addUser-org" onClick="addMember('+teamId+');getTeamMembersListExceptExistedTeamMembersList('+teamId+');"> <i class="glyphicon glyphicon-plus"></i>Add Member</a> ';
		
		outPut = outPut  +'     <div id="popUpDivTeam" class="edit-details col-md-4"> ';
		outPut = outPut  +'     </div>  ';
		outPut = outPut  +'     </div>  ';
				
		outPut = outPut+ ' <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		
		outPut = outPut+ ' <div id="teamEditDiv"></div>';
		outPut = outPut+ ' <div id="projectMemberList"></div>';
		outPut = outPut+ '        </div>';
		outPut = outPut+ '      </div>';
		outPut = outPut+ '    </div>';
		
		
		outPut = outPut+ '  </div>';
		
		$("#teamSideDiv").empty();  
	    $("#teamSideDiv").append(outPut);
	     
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		$("#tab-1").show();
		projectMemberList(teamId);
		
}
</script>

<%-- 
     @author : shivayogi
     this script is for Edit team Details
     1.usage     : used in projectMemberList();
--%>
<script type="text/javascript">
function teamEditDetails(teamId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getTeamByTeamID?${_csrf.parameterName}=${_csrf.token}&teamId="+teamId,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var outPut = '';
	        	outPut = outPut+'<form role="form"  id="edit_team_form" name="team_form" method="post" > ';
	        	outPut = outPut+ '<div class="form-horizontal clearfix teamEditDetails">';
	        	outPut = outPut+ '<div class="clearfix mb10">';
	        	outPut = outPut+ '<div class="col-md-6">';
	        	outPut = outPut +'<input type="hidden" class="form-control input-sm" name="teamId"  value="'+teamId+'">';
	        	outPut = outPut+ '<label>Team Name<b class="text-danger">*</b> </label>';
	        	outPut = outPut+ '<input type="text" class="form-control"  placeholder="Team Name" name="name" data-validation="required validate_Space validate_AlphaNumaric validate_length length2-100" field-name="Team Name" value="'+json.name+'">';
	        	outPut = outPut+ '                </div>';
	        	
	        	
	        	outPut = outPut+ '                <div class="col-md-5">';
	        	outPut = outPut+ '                <label>Team Lead<b class="text-danger">*</b> </label>';
	        	outPut = outPut + '<select   class="form-control" name="editOwnerUserId" value="'+json.teamOwnerName+'" id="editOwnerUserId" placeholder="Team Lead *" data-validation="required validate_Space" field-name="Team Lead">';
	            outPut = outPut + '</select>';
	        	outPut = outPut+ '                </div>';
	        	outPut = outPut+ '                <div class="col-md-1">';
	        	outPut = outPut+ '                <label class="col-md-12">&nbsp;</label>';
	        	outPut = outPut+ '<button type="button" id="edit_team" class="btn btn-primary btn-sm btn-block" onclick="storeEditedTeam();">Add</button>';
	        	outPut = outPut+ '              </div>';
	        	outPut = outPut+ '              </div>';
	        	outPut = outPut+ '              </div> ';
	        	outPut = outPut+ '              <div class="clearfix mr">';
	        	/* outPut = outPut+ '<button type="button" id="edit_team" class="btn btn-sm btn-primary pull-left" onclick="storeEditedTeam();">Add</button>'; */
	        	outPut = outPut+ '              </div>';
	        	outPut = outPut+ '            </div>'
	        	outPut = outPut+ '</form>';
	        	
	        	outPut = outPut+ '  </hr>';
	        	outPut = outPut+ '  </hr>';
	        	outPut = outPut+ '  </hr>';
	        	$("#teamEditDiv").empty();
	        	$("#teamEditDiv").append(outPut); 
	        	getEditTeamOwners(json.ownerUserId.id,json.teamOwnerName,teamId);
	        },
	        error: function(e){
	            
	        }
	    }); 
	 
}
</script>


<%-- 
     @author : shivayogi
     this script is for get team owners
     1.usage     : used in teamEditDetails();
--%>
<script type="text/javascript">
function getEditTeamOwners(teamOwnerUserId,teamOwnerName,teamId){ 
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	/*  var editOwnerUserId = document.getElementById("editOwnerUserId").value; */
	 var existedTeamMembersIds = [];
	 $('.existedTeamMember').each(function() {
		 var localId = this.value;
		 existedTeamMembersIds.push(localId);
	 });  
      
     $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getTeamMembersListExceptExistedTeamMembersList?${_csrf.parameterName}=${_csrf.token}&existedTeamMembersIds="+existedTeamMembersIds+"&teamId="+teamId,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option disabled >-Select Team Members-</option>"; 
	        	output = output + "<option selected value="+teamOwnerUserId+" >"+teamOwnerName+"</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
				}
	        	$("#editOwnerUserId").empty();
		        $("#editOwnerUserId").append(output);
		        $("#editOwnerUserId").trigger("chosen:updated");
	        },
	        error: function(e){
	            
	        }
});
     
     
     
} 
</script>

<script>
function storeEditedTeam(){
	
	   var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };
	   if ($('#edit_team_form').validate(false, validationSettings)){ 
			var frm = $('#edit_team_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
		  
		     var jsonData = encodeURIComponent(con);
			 var sourceleadUrl = '${sourceleadUrl}';
			 $("#team_form").attr("disabled", "disabled");
			
			$.ajax({
					type : "POST",
					url : sourceleadUrl+"/storeEditedTeam?${_csrf.parameterName}=${_csrf.token}&json="+con,
					success :function(data) {  
						location.replace(sourceleadUrl+"/team");
				      }, 
		    });
			return true; 
	   }else{ 
			return false;
	        } 
		} 
 
		$('body').on('blur', '#team_form', function() {
			$("#team_form").showHelpOnFocus();
			$("#team_form").validateOnBlur(false, validationSettings);
		});


</script>   

<script>
function addTeam(){ 
	var outPut = '';
	outPut = outPut+'<form role="form"  id="team_form" name="team_form" method="post" > ';
	outPut = outPut+ '<i class="fa fa-close close-div"  onClick="closeTeamPopup();"></i>';
	outPut = outPut+ '<div class="form-horizontal clearfix">';
	outPut = outPut+ '<div class="clearfix mb10">';
	/* outPut = outPut + '<input type="hidden" class="form-control input-sm" name="teamId"  value="'+teamId+'">'; */
	outPut = outPut+ '<div class="col-md-12">';
	outPut = outPut+ '<label>Team Name<b class="text-danger">*</b> </label>';
	outPut = outPut+ '<input type="text" class="form-control"  placeholder="Team Name" name="name" data-validation="required validate_Space validate_AlphaNumaric validate_length length2-100" field-name="Team Name" value="">';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '              </div>';
	outPut = outPut+ '              <div class="clearfix mb10">';
	outPut = outPut+ '                <div class="col-md-12">';
	outPut = outPut+ '                <label>Team Lead<b class="text-danger">*</b> </label>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '                <div class="col-md-12">';
	outPut = outPut + '<select   class="form-control" name="ownerUserId" id="ownerUserId" placeholder="Team Lead *" data-validation="required validate_Space" field-name="Team Lead"  onchange="getTeamMembers();">';
				/* outPut = outPut + ' <option value="">--Select Team Lead--</option>';  */           
	           /* <c:forEach var="report" items="${usersList}">
			         outPut = outPut + ' <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>';
			   </c:forEach> */
    outPut = outPut + '</select>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '              </div> ';
	outPut = outPut+ '              <div class="clearfix mb10">';
	outPut = outPut+ '                <div class="col-md-12">';
	outPut = outPut+ '                <label>Team Member<b class="text-danger">*</b> </label>';
	outPut = outPut + '<select multiple class="chosen-select form-control" name="memberUserId" id="memberUserId" placeholder="Team Member *" data-validation="required validate_Space" field-name="Team Member">';
		/* outPut = outPut + ' <option disabled >--Select Team Member--</option>';      */   
		/* <c:forEach var="report" items="${usersList}">
	         outPut = outPut + ' <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>';
	     </c:forEach> */
    outPut = outPut + '</select>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '              </div> ';
	outPut = outPut+ '              <div class="clearfix mr">';
	outPut = outPut+ '<button type="button" id="add_team" class="btn btn-sm btn-primary pull-right" onclick="storeTeam();">Add</button>';
	outPut = outPut+ '              </div>';
	outPut = outPut+ '            </div>';
	outPut = outPut+ '</form>';
	$("#popUpDivTeamParent").empty();
	$("#popUpDivTeamParent").append(outPut); 
	
	$(".edit-details").toggleClass("edit-details-active");
	$(".close-div").click(function(){
		$(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
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
	
}
</script>


<%-- 
     @author : prasad K.
     1.usage     : called at closeTeamPopup() function.
     3.close Create.
--%>

<script>
function closeTeamPopup(){
	
	 $("#popUpDivTeamParent").removeClass("edit-details-active");
}
</script>

<script>
function closePopup(){
	 $("#popUpDivTeam").removeClass("edit-details-active");
}
</script>

<script>
function addMember(teamId){
	var outPut = '';
	outPut = outPut+'          <form role="form"  id="teamMember_form" name="teamMember_form" method="post" > ';
	outPut = outPut+ '<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	outPut = outPut+ '            <div class="form-horizontal clearfix">';
	outPut = outPut+ '              <div class="clearfix mb10">';
	outPut = outPut+ '                <div class="col-md-12">';
	outPut = outPut + '<select multiple class="chosen-select form-control" name="teamMemberUserId" id="teamMemberUserId" data-validation="required validate_Space" field-name="Team Member">';
					        				   /*<c:forEach var="report" items="${usersList}">
					    							outPut = outPut + ' <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>';
					                      		</c:forEach> */
     outPut = outPut + '</select>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '              </div>';
	outPut = outPut+ '              <div class="clearfix mr">';
	outPut = outPut+ '<button type="button" id="add_teamMember" class="btn btn-sm btn-primary pull-right" onclick="storeTeamMembers('+teamId+');">Add</button>';
	outPut = outPut+ '</div>';
	outPut = outPut+ '</div>';
	outPut = outPut+ '</form>';
		
	$("#popUpDivTeam").empty();
	$("#popUpDivTeam").append(outPut); 
	$("#popUpDivTeam").addClass("edit-details-active"); 
	
	/* $(".edit-details").toggleClass("edit-details-active"); */
	
	/* $(".close-div").click(function(){
		$(this).parent("div.edit-details").removeClass("edit-details-active");
		 }); */
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	/*  $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	 }); */	
	 
	 $( function() {
		    $( "#edu-startDate, #edu-endDate" ).datepicker();
		  });
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
	
}
</script>

<script>
function projectMemberList(teamId){
	    
	    var sourceleadUrl ='${sourceleadUrl}';
		  $("#projectMemberList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		  $.ajax({
	           type : "POST",
	            url: sourceleadUrl+"/getTeamMembersListByTeamID?${_csrf.parameterName}=${_csrf.token}&teamId="+teamId,       
	            success :function(data) {
	                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
	               		 if( data.indexOf('Sign In') >= 0){
				    		location.replace(sourceleadUrl);
				    	 }
	               		 else if (data.indexOf('exception') >= 0) {
	               			 location.replace(sourceleadUrl+"/errorPage");
	               		 }
	               		 else{
				    	     $("#projectMemberList").removeAttr("style");
		                	 $("#projectMemberList").empty();
		                	 
		                	 
		                	
		                      
		                      
		                	 
		                	 var result = "";
		                	 
		                	
		                	 result = result +"";
		                	 
		                	 result = result +"<table id='teamMemberPagination' class='display' cellspacing='0' width='100%' >"; 
		                     result = result +"<thead>"; 
		                     result = result+ '              <tr>';
		                     result = result+ '                <th>Name</th>';
		                     result = result+ '                <th>Email ID</th>';
		                     result = result+ '                <th>Role</th>';
		                     result = result+ '                <th>Requirement</th>';
		                     result = result+ '                <th>Active<br>Requirement</th>';
		                     result = result+ '                <th>Action</th>';
		                     result = result+ '              </tr>';
		                     result = result +"</thead>";
		                     result = result +"<tbody>"; 
		                     
		                     var json = JSON.parse(data);
		                     
		                     
		                     for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i];
		                    	 var  output = "";
		                    	 
		                    	 
		                    	 
		                        /* 	 output = output
				     				+ '<div><input type="text" onBlur="inlineEditForProjectsTasks('+opt.teamName+',\'taskName\');"  id="teamName'+ opt.teamName+ '" value="'+ opt.teamName+ '"  class="project_inline_field"></div>';
				     				
				                      $("#TeamName").append(output); 
				                       */
				                      
				                      
		                    	 result = result+ '<tr>';
		                    	 if(opt.memberName != null && opt.memberName != '' && opt.memberName !="undefined"){
		                    	 result = result+ '<td><div><span>'+opt.memberName+'</span></div></td>';
		                    	 }else{
		                    		 result = result+ '<td><div><span>-----</span></div></td>'; 
		                    	 }
		                    	 result = result+ '<input class="existedTeamMember" type="hidden" name="existedTeamMember" id="existedTeamMember" value="'+opt.teamMemberUserId+'"/>'; 
		                    	 if(opt.email != null && opt.email != '' && opt.email !="undefined"){
		                    	 result = result+ '<td><div><span>'+opt.email+'</span></div></td>';
		                    	 }else{
		                    		 result = result+ '<td><div><span>-----</span></div></td>'; 
		                    	 }
		                    	 if(opt.userType != null && opt.userType != '' && opt.userType !="undefined"){
		                    	 result = result+ '<td><div><span>'+opt.userType+'</span></div></td>';
		                    	 }else{
		                    		 result = result+ '<td><div><span>-----</span></div></td>'; 
		                    	 }
		                    	 result = result+ '<td><div><span>'+opt.teamMemberRequirementAssignedCount+'</span></div></td>';
		                    	 result = result+ '<td><div><span>'+opt.teamMemberRequirementAssignedOpenCount+'</span></div></td>';
		                    	 result = result+ '<td><div><i class="glyphicon glyphicon-trash" onclick="removeProjectMemberAlertMessage('+opt.id+');"></i></div></td>';
		                    	 result = result+ '</tr>';
		                     }
		                     result = result +" </tbody>";
		                     result = result +"</table>"; 
		                     $("#projectMemberList").append(result); 
		                     TeamMemberpagination(); 
		                     teamEditDetails(teamId);
				    	} 
	                 }else{
	                	 $("#projectMemberList").removeAttr("style");
	  	    	         var result ="";
			    	    
						 result = result+"			<div class='blank-pages'>";
						 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
						 result = result+"			        <p>No Team Member Found</p>";
						 result = result+"			 </div>";
						
						$("#projectMemberList").empty();
					    $("#projectMemberList").append(result); 
	                }
	          },
	            
	       }); 
}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function removeProjectMemberAlertMessage(teamId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='#'><input id='confirm-id' type='button' value='Ok' onclick='deleteTeamMember("+teamId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result +"	</div>";
	$("#removeCandidateAlert").append(result);
	$("#remove_user_pop").slideDown(200); 
	$(".project_inn_mask").fadeIn(200);
}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function cancelAlert(){ 
	$("#removeCandidateAlert").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token, candidateId
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function deleteTeamMember(teamMemberId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  var reason = document.getElementById("rejectReason").value;
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/deleteTeamMemberByCandidateId?${_csrf.parameterName}=${_csrf.token}&teamMemberId="+teamMemberId+"&reason="+reason,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != ""){
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
            			 projectMemberList(teamMemberId);
            			 $("#removeCandidateAlert").empty();
            			 $(".project_inn_mask").fadeOut(200);
            		 }
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
function TeamMemberpagination(){ 
    $('#teamMemberPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>


<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Team</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Team</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head candidateHead">
        <ul>
          <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>
          <li><a href="#" onclick="addTeam();getTeamOwners();" title=""><i class="fa fa-plus"></i>Add Team</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
        <!-- <div class="remove_search">
          <input type="text" placeholder="search" />
        </div> -->
      </div>
      <div class="project_tabs_inn_table vendor_wrap_inn_table">
       			<div id="popUpDivTeamParent" class="edit-details col-md-4"></div>  
      			<div id="teamList"></div>
      			
              </div>
        </div>      
  
    
    <!-- Compnay details -->
    <div class="side_mask"><div class="proj_close"></div></div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
      
      <div id="teamSideDiv">
	  </div>

      
      </div>
      
 <!-- Edit Details Popup -->      
   
 <!-- Edit Details Popup Ends -->      

    </div>
    <!-- Compnay details Ends --> 
    <!-- Project --> 
  </div>
</section>



<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 
 
 <script>
function storeTeam(){
	
	   var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };
	   if ($('#team_form').validate(false, validationSettings)){ 
			var frm = $('#team_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
		  
		     var jsonData = encodeURIComponent(con);
			 var sourceleadUrl = '${sourceleadUrl}';
			
			 $("#team_form").attr("disabled", "disabled");
			
			$.ajax({
					type : "POST",
					url : sourceleadUrl+"/storeTeam?${_csrf.parameterName}=${_csrf.token}&json="+con,
					success :function(data) {  
						location.replace(sourceleadUrl+"/team");
				      }, 
		    });
			return true; 
	   }else{ 
			return false;
	        } 
		} 
 
		$('body').on('blur', '#team_form', function() {
			$("#team_form").showHelpOnFocus();
			$("#team_form").validateOnBlur(false, validationSettings);
		});


</script>   


<script type="text/javascript">
function getTeamOwners(){ 
	 var sourceleadUrl ='${sourceleadUrl}';
	 var teamMemberUserId = document.getElementById("memberUserId").value;
     $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllUsersByOrgLocationCodeExceptTeamOwner?${_csrf.parameterName}=${_csrf.token}&teamOwnerUserId="+teamMemberUserId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Team Owners-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#ownerUserId").empty();
			        $("#ownerUserId").append(output);
			        $("#ownerUserId").trigger("chosen:updated");
			        
			        getTeamMembers();
		        },
		        error: function(e){
		            
		        }
		    });
} 
</script>


<script type="text/javascript">
function getTeamMembers(){  
	 var sourceleadUrl ='${sourceleadUrl}';
	 var teamOwnerUserId = document.getElementById("ownerUserId").value;
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllUsersByOrgLocationCodeExceptTeamOwner?${_csrf.parameterName}=${_csrf.token}&teamOwnerUserId="+teamOwnerUserId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option disabled >-Select Team Members-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#memberUserId").empty();
			        $("#memberUserId").append(output);
			        $("#memberUserId").trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
} 
</script>

<script>
function deleteTeam(teamId){
	var sourceleadUrl ='${sourceleadUrl}';
	var message = confirm("You are choosing to delete the team");
	if (message == true) {
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/deleteTeamById?${_csrf.parameterName}=${_csrf.token}&teamId="+teamId, 
   		    success: function(data) {
   		    	
   		    	if(data != null && data != 'undefined' && data != ""){
             		if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	}
             		else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		}else{
             			teamList();
             		}
   		    	}
   		    }
	     });
	}
}
</script>         
 
 <!-- Team Member Store -->
 
 
 <script>
function storeTeamMembers(teamId){
	
		var validationSettings = {
		        errorMessagePosition : 'element',
		        borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         };
		
	   if ($('#teamMember_form').validate(false, validationSettings)){ 
				var frm = $('#teamMember_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
		  
		     var jsonData = encodeURIComponent(con);
			 var sourceleadUrl = '${sourceleadUrl}';
			
			 $("#teamMember_form").attr("disabled", "disabled");
			
			$.ajax({
					type : "POST",
					url : sourceleadUrl+"/storeTeamMembers?${_csrf.parameterName}=${_csrf.token}&json="+con+"&teamId="+teamId,
					success :function(data) {  
						location.replace(sourceleadUrl+"/team");
				    }, 
		    });
			return true; 
	   }else{ 
			return false;
	        } 
		}  
 
$('body').on('blur', '#teamMember_form', function() {
	$("#teamMember_form").showHelpOnFocus();
	$("#teamMember_form").validateOnBlur(false, validationSettings);
});


</script>            
          

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>           

 <!-- Team Memberend -->
 
 
 
 
<script type="text/javascript">

  $( function() {
    $( "#edu-startDate, #edu-endDate" ).datepicker();
  });
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
  /*   $("html").click(function() {
        $(".approved_pop").slideUp(200);
    }); */
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
   /*  $("html").click(function() {
        $(".export_pop").slideUp(200);
    }); */
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

// Tabs script
/* $(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
	});  */

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
$(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
	 $(".collapse-div").removeClass("edit-details-active");
 });
 $(".close-div").click(function(){
	 $(this).closest(".edit-details").toggleClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
 /* $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	 */
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   event.preventDefault();
});

$(".addSkills").click(function(){
	$(".add-skills").toggleClass('add-skills-active');
	});
$(".close-div").click(function(){
	$(".add-skills").removeClass('add-skills-active');
	});
$(".add-skills").click(function(e){
	 e.stopPropagation();
 });
</script> 
<script>
  $(function() {
    $("#validFrom, #edu-endDate, #edu-startDate, #validTo").datepicker();
  } );
  </script>
  
  
  
  
 <script type="text/javascript">
function getTeamMembersListExceptExistedTeamMembersList(teamId){  
	 var sourceleadUrl ='${sourceleadUrl}';
	 var existedTeamMembersIds = [];
	 $('.existedTeamMember').each(function() {
		 var localId = this.value;
		 existedTeamMembersIds.push(localId);
	 });  
	 
	 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getTeamMembersListExceptExistedTeamMembersList?${_csrf.parameterName}=${_csrf.token}&existedTeamMembersIds="+existedTeamMembersIds+"&teamId="+teamId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option disabled >-Select Team Members-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#teamMemberUserId").empty();
			        $("#teamMemberUserId").append(output);
			        $("#teamMemberUserId").trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
	});
} 
</script>
 <jsp:include page="../masterFooter.jsp" />
