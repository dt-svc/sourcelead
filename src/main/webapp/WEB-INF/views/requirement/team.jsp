<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				             result = result +"<td><div><span>Business Intelli</span></div></td>";
				             result = result +"<td><div><span>Vannam Mahesh</span></div></td>";
				             result = result +"<td><div><span>25</span></div></td>";
				             result = result +"<td><div><i class='fa fa-pencil proj_show' onclick='sideBar("+opt.id+");'></i><i class='glyphicon glyphicon-trash'></i></div></td>";
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
    	"lengthMenu": [[2, 15, 20, -1], [2, 15, 20, "All"]],
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
	  
	    var outPut = '';
	    outPut = outPut+'<div id="removeCandidateAlert" ></div>  ';
		outPut = outPut+ '	<div class="project_side_wrap_inn_tabs">';
		outPut = outPut+ '    <div class="project_tabs">';
		outPut = outPut+ '      <div id="tab-1" class="project_tabs_inn">';
		outPut = outPut+ '        <div  class="project_tabs_inn_head team_search"> <a href="#" class="addUser-org" onClick="addMember('+teamId+');"> <i class="glyphicon glyphicon-plus"></i>Add Member</a> ';
		
		outPut = outPut  +'     <div id="popUpDivTeam" class="edit-details col-md-4"> ';
		outPut = outPut  +'     </div>  ';
				
		outPut = outPut+ ' <div class="project_tabs_inn_table vendor_wrap_inn_table">';
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

<script>
function addMember(teamId){
	var outPut = '';
	outPut = outPut+'          <form role="form"  id="teamMember_form" name="teamMember_form" method="post" > ';
	outPut = outPut+ '<i class="fa fa-close close-div"></i>';
	outPut = outPut+ '            <div class="form-horizontal clearfix">';
	outPut = outPut+ '              <div class="clearfix mb10">';
	outPut = outPut+ '                <div class="col-md-12">';
	outPut = outPut + '<select multiple class="chosen-select form-control" name="managerUserId" id="managerUserId" data-validation="validate_Space" data-validation-optional="true">';
                       <c:forEach var="report" items="${usersList}">
    outPut = outPut + ' <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>';
                      </c:forEach>
     outPut = outPut + '</select>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '              </div>';
	outPut = outPut+ '              <div class="clearfix mr">';
	outPut = outPut+ '                <button type="button" id="add_teamMember" class="btn btn-sm btn-primary pull-right" onclick="storeTeamMembers('+teamId+');">Add</button>';
	outPut = outPut+ '              </div>';
	outPut = outPut+ '            </div>';
	outPut = outPut+ '            </form>';
		
	$("#popUpDivTeam").empty();
	$("#popUpDivTeam").append(outPut); 
	
	$(".edit-details").toggleClass("edit-details-active");
	
	$(".close-div").click(function(){
		$(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
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
		  $("#teamList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
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
				    	     $("#teamList").removeAttr("style");
		                	 $("#teamList").empty();
		                	 var result = "";
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
		                    	 result = result+ '              <tr>';
		                    	 result = result+ '                <td><div><span>Rajashekar</span></div></td>';
		                    	 result = result+ '                <td><div><span>rajashekar@bis.com</span></div></td>';
		                    	 result = result+ '                <td><div><span>Employee</span></div></td>';
		                    	 result = result+ '                <td><div><span>Requirement</span></div></td>';
		                    	 result = result+ '                <td><div><span>Requirement</span></div></td>';
		                    	 result = result+ '                <td><div><i class="glyphicon glyphicon-trash" onclick="removeProjectMemberAlertMessage('+opt.id+');"></i></div></td>';
		                    	 result = result+ '              </tr>';
		                     }
		                     result = result +" </tbody>";
		                     result = result +"</table>"; 
		                     $("#projectMemberList").append(result); 
		                     TeamMemberpagination(); 
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
		  
		/* var outPut ='';
	    outPut = outPut+ '  <table>';
		outPut = outPut+ '            <tbody>';
		outPut = outPut+ '              <tr>';
		outPut = outPut+ '                <th>Name</th>';
		outPut = outPut+ '                <th>Email ID</th>';
		outPut = outPut+ '                <th>Role</th>';
		outPut = outPut+ '                <th>Requirement</th>';
		outPut = outPut+ '                <th>Active<br>Requirement</th>';
		outPut = outPut+ '                <th>Action</th>';
		outPut = outPut+ '              </tr>';
		outPut = outPut+ '              <tr>';
		outPut = outPut+ '                <td><div><span>Rajashekar</span></div></td>';
		outPut = outPut+ '                <td><div><span>rajashekar@bis.com</span></div></td>';
		outPut = outPut+ '                <td><div><span>Employee</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
		outPut = outPut+ '              </tr>';
		outPut = outPut+ '              <tr>';
		outPut = outPut+ '                <td><div><span>Rajashekar</span></div></td>';
		outPut = outPut+ '                <td><div><span>rajashekar@bis.com</span></div></td>';
		outPut = outPut+ '                <td><div><span>Employee</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
		outPut = outPut+ '              </tr>';
		outPut = outPut+ '              <tr>';
		outPut = outPut+ '                <td><div><span>Rajashekar</span></div></td>';
		outPut = outPut+ '                <td><div><span>rajashekar@bis.com</span></div></td>';
		outPut = outPut+ '                <td><div><span>Employee</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
		outPut = outPut+ '              </tr>';
		outPut = outPut+ '              <tr>';
		outPut = outPut+ '                <td><div><span>Rajashekar</span></div></td>';
		outPut = outPut+ '                <td><div><span>rajashekar@bis.com</span></div></td>';
		outPut = outPut+ '                <td><div><span>Employee</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
		outPut = outPut+ '              </tr>';
		outPut = outPut+ '              <tr>';
		outPut = outPut+ '                <td><div><span>Rajashekar</span></div></td>';
		outPut = outPut+ '                <td><div><span>rajashekar@bis.com</span></div></td>';
		outPut = outPut+ '                <td><div><span>Employee</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
		outPut = outPut+ '              </tr>';
		outPut = outPut+ '              <tr>';
		outPut = outPut+ '                <td><div><span>Rajashekar</span></div></td>';
		outPut = outPut+ '                <td><div><span>rajashekar@bis.com</span></div></td>';
		outPut = outPut+ '                <td><div><span>Employee</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><span>Requirement</span></div></td>';
		outPut = outPut+ '                <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
		outPut = outPut+ '              </tr>';
		outPut = outPut+ '            </tbody>';
		outPut = outPut+ '          </table>'; */
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
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteTeamMember("+teamId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
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
function deleteTeamMember(teamId){
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
            			 projectMemberList(teamId);
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
    	"lengthMenu": [[2, 15, 20, -1], [2, 15, 20, "All"]],
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
          <li><a href="add-candidate.html" title=""><i class="fa fa-plus"></i>Add Team</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
        <div class="remove_search">
          <input type="text" placeholder="search" />
        </div>
      </div>
      <div class="project_tabs_inn_table vendor_wrap_inn_table">
      
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
 
 
 
 <!-- Team Member Store -->
 
 
 <script>
function storeTeamMembers(teamId){
		
				var frm = $('#teamMember_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
		  
			 alert("teamId="+teamId);
		     var jsonData = encodeURIComponent(con);
		     alert(jsonData); 
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
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
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
 $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	
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
 <jsp:include page="../masterFooter.jsp" />
