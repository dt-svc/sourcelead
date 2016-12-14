<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="resources/js/formvalidator.js"></script>



<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<%-- 
     @author : David Raju.
     this script is to get filter popup
     1.usage     : onclick on requirementList() ; 
     
--%>

<script>
$(document).ready(function() {
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
}); 

</script>



<script type="text/javascript">
function requirementListIndividualStatusCounts(){
	
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getRequirementListCountBasedOnStatus",
		        success: function(data) {
		        	
		        	var json = JSON.parse(data);
		        	
		        	var  newRequirementCount = json.newRequirementCount;
		        	var  draftRequirementCount =json.draftRequirementCount;
		        	var  submittedRequirementCount =json.submittedRequirementCount;
		        	var  openRequirementCount = json.openRequirementCount; 
		        	var  onHoldRequirementCount =json.onHoldRequirementCount;
		        	var  cancelRequirementCount = json.cancelRequirementCount;
		        	
		        	
		        	if(newRequirementCount>0){
	                      $("#newRequirementCount").text(("("+newRequirementCount+")"));
	                  }else{
	                      $("#newRequirementCount").html("(0)");
	                  }
		        	if(draftRequirementCount>0){
	                      $("#draftRequirementCount").text("("+draftRequirementCount+")");
	                  }else{
	                      $("#draftRequirementCount").html("(0)");
	                  }
		        	if(submittedRequirementCount>0){
	                      $("#submittedRequirementCount").text("("+submittedRequirementCount+")");
	                  }else{
	                      $("#submittedRequirementCount").html("(0)");
	                  }
		        	if(openRequirementCount>0){
	                      $("#openRequirementCount").text("("+openRequirementCount+")");
	                  }else{
	                      $("#openRequirementCount").html("(0)");
	                  }
		        	if(onHoldRequirementCount>0){
	                      $("#onHoldRequirementCount").text("("+onHoldRequirementCount+")");
	                  }else{
	                      $("#onHoldRequirementCount").html("(0)");
	                  }
		        	if(cancelRequirementCount>0){
	                      $("#cancelRequirementCount").text("("+cancelRequirementCount+")");
	                  }else{
	                      $("#cancelRequirementCount").html("(0)");
	                  }
		        },
		        error: function(e){
		            
		        }
		    });
} 
</script>



<style>
/*.tbldata-dotdot span{
max-width:220px;
} */
 .clsDatePicker {
   z-index: 100000 !important;
}
.side-bar .side-nav ul li span{
  display: inline-block;
  float: none;
}
.project_wrap_table span{
display: inline;
}
.require_wrap_table {
overflow: visible;
}
.timesheet_head ul {
padding: 1px 280px 2px 0;
}
 </style>
 
 

<!-- my scripts -->

<script>
 window.onload = function() { 
 	 requirementList();
 	// requirementListIndividualStatusCounts();
};
</script>

<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>
<input type="hidden" name="noReq" id="noReq"/>
<input type="hidden" id="requirementEmptyListMessage" name="requirementEmptyListMessage"/>
<div id="popUp"></div>
<script>
function requirementList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
	 var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() 
	    {
			clients.push($(this).val());
	    });
	var orgATS = '${SourceLeadWeb_ats}';
	var orgVMS = '${SourceLeadWeb_vms}';
	
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getRequirementListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clients="+clients,    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Title</th>"; 
	                     result = result +"    <th>Client</th>"; 
	                     result = result +"    <th>Position type</th>"; 
	                     result = result +"    <th>Location</th>"; 
	                     result = result +"    <th>No.of<br> Positions</th>"; 
	                     result = result +"    <th>Owner</th>"; 
	                     result = result +"    <th>Status</th>"; 
	                     result = result +"    <th>Release</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>"; /*  onclick='sideBar("+opt.id+");' */
	                    	 if(opt.jobId != '' && opt.jobId != null){
	                    	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
		                     	result = result +"<td><a href='<c:url value='/requirementSummary?requirementId="+opt.id+"'/>'><div class='proj_show tbldata-dotdot'><span class='pull-left' title='"+opt.jobTitle+"-"+opt.jobId+"'>"+opt.jobTitle+"-"+opt.jobId+"</span><i class='fa fa-binoculars'></i></div></a></td>";
		                     '</sec:authorize>';
		                     '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
		                     	result = result +"<td><div class='proj_show tbldata-dotdot'><span class='pull-left' title='"+opt.jobTitle+"-"+opt.jobId+"'>"+opt.jobTitle+"-"+opt.jobId+"</span></div></td>";
		                     '</sec:authorize>';
	                    	 }else{
	                    		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
			                     	result = result +"<td><a href='<c:url value='/requirementSummary?requirementId="+opt.id+"'/>'><div class='proj_show tbldata-dotdot'><span class='pull-left' title='"+opt.jobTitle+"-"+opt.jobId+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
			                     '</sec:authorize>';
			                     '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
			                     	result = result +"<td><div class='proj_show tbldata-dotdot'><span class='pull-left' title='"+opt.jobTitle+"-"+opt.jobId+"'>"+opt.jobTitle+"</span></div></td>";
			                     '</sec:authorize>';
	                    	 }
		                     if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
		                    	 result = result +"<td><div>"+opt.clientName+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     if(opt.jobType!=null && opt.jobType !='' && opt.jobType !='undefined'){
		                    	 result = result +"<td><div>"+opt.jobType+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                    
		                     if(opt.state!=null && opt.state !='' && opt.state !='undefined' &&  opt.city !='undefined' && opt.city !='' &&  opt.city!=null){
			                     result = result +"<td><div><span><span>"+opt.country+" ,"+opt.state+" ,"+opt.city+" </span></span></div></td>";
		                     }else{
			                     result = result +"<td><div><span><span>"+opt.country+"</span></span></div></td>";
		                     }
		                     if(opt.positions!=null && opt.positions !='' && opt.positions !='undefined'){
		                    	 result = result +"<td><div>"+opt.positions+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>----</div></td>";
		                     }
		                     if(opt.manager!=null && opt.manager!='' && opt.manager!='undefined'){
		                    	 result = result +"<td><div>"+opt.manager+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>----</div></td>";
		                     }
                           result = result +"<td><select name='changeStatusId"+opt.id+"' value= '' onchange='changeStatus("+opt.id+");' id='changeStatusId"+opt.id+"'>";
		                     
		                        if(opt.requirementSubmissionStatus == "DRAFT"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
									'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
										result = result +"<option value='ONHOLD'>ONHOLD</option>";
									'</sec:authorize>';
									'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
										result = result +"<option value='CANCEL'>CANCEL</option>";
									'</sec:authorize>';
								}
								else if(opt.requirementSubmissionStatus == "CANCEL"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";

								}else if(opt.requirementSubmissionStatus == "OPEN"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
									'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
										result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
									'</sec:authorize>';
									'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
										result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
									'</sec:authorize>';
									'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
										result = result +"<option value='ONHOLD'>ONHOLD</option>";
									'</sec:authorize>';
									'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
										result = result +"<option value='CANCEL'>CANCEL</option>";
									'</sec:authorize>';
								}
								else if(opt.requirementSubmissionStatus == "NEW"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
									result = result +"<option value='OPEN'>OPEN</option>";
									'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
										result = result +"<option value='ONHOLD'>ONHOLD</option>";
									'</sec:authorize>';
									'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
										result = result +"<option value='CANCEL'>CANCEL</option>";
									'</sec:authorize>';
								}else if(opt.requirementSubmissionStatus == "APPROVED"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
									
								}
								else if(opt.requirementSubmissionStatus == "REJECTED"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
									
								}
								
								else if(opt.requirementSubmissionStatus == "SUBMITTED"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
									'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
										result = result +"<option value='ONHOLD'>ONHOLD</option>";
									'</sec:authorize>';
									'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
										result = result +"<option value='CANCEL'>CANCEL</option>";
									'</sec:authorize>';
									
								}else if(opt.requirementSubmissionStatus == "ONHOLD"){
									
									if(opt.requirementSubmissionPreviousStatus == "OPEN" || opt.requirementSubmissionPreviousStatus == null || opt.requirementSubmissionPreviousStatus == '' || opt.requirementSubmissionPreviousStatus == 'undefined' ){
										result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
										result = result +"<option value='OPEN'>OPEN</option>";
										'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
											result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
										'</sec:authorize>';
										'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
											result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
										'</sec:authorize>';
										'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
											result = result +"<option value='CANCEL'>CANCEL</option>";
										'</sec:authorize>';
									}
									if(opt.requirementSubmissionPreviousStatus == "DRAFT"){
										result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
										result = result +"<option value='DRAFT'>DRAFT</option>";
										'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
											result = result +"<option value='CANCEL'>CANCEL</option>";
										'</sec:authorize>';
									}
									if(opt.requirementSubmissionPreviousStatus == "SUBMITTED"){
										result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
										result = result +"<option value='SUBMITTED'>SUBMITTED</option>";
										'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
											result = result +"<option value='CANCEL'>CANCEL</option>";
										'</sec:authorize>';
									}
									 
								}else if(opt.requirementSubmissionStatus == "UNFILLEDCLOSED"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
									result = result +"<option value='OPEN'>OPEN</option>";
									
								}else if(opt.requirementSubmissionStatus == "FILLEDCLOSED"){
									result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
									
								}

								result = result +" </select>";	
			                    result = result +" </td>";
		                     
		                     
		                     if(((orgATS == 'Y' && orgVMS == 'Y') || orgATS != 'Y') && (opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN')){
		                    	 '<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
		                    	 	result = result +"<td><div><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementId="+opt.id+"'/>'><i class='fa fa-bullhorn'></i></a></div></td>";
		                    	 '</sec:authorize>';
		                    	 '<sec:authorize access="!hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
		                    	 	result = result +"<td><div><i class='fa fa-bullhorn'></i></div></td>";
		                    	 '</sec:authorize>';
		                     }else{
			                     result = result +"<td><div>--</div></td>";
		                     }
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noReq").val('');
	                     $("#requirementList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
                	 $("#requirementList").empty();
                	 $("#requirementList").removeAttr("style");
                	 $("#requirementEmptyListMessage").val("noRequirement");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Requirement Found</p>";
					 result = result+"			 </div>";
					/*  noRequirements();
					$("#noReq").val("noreq"); */
					$("#requirementList").empty();
				    $("#requirementList").append(result); 
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
    $('#requirementPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
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
      <h2>Requirement List</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Requirement List</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head">
        <ul>
        <sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_EDIT_REQUIREMENT')">
		<li><i class="glyphicon glyphicon-export"></i><span>Status</span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>
		            <div id="filter2ApprovedPop" class="approved_pop">
		              <ul>
                		    <li><input id="c6" type="checkbox" name="requirementStatus" value ="NEW"  onclick="requirementList();"><label for="c6">New<span id="newRequirementCount"></span></label></li>
							<li><input id="c7" name="requirementStatus" type="checkbox" value ="Draft"  onclick="requirementList();"><label for="c7">Draft<span id="draftRequirementCount"></span></label></li>
							<li><input id="c8" type="checkbox"  name="requirementStatus"  type="checkbox" value ="submitted"  onclick="requirementList();"><label for="c8">Submitted <span id="submittedRequirementCount"></span></label></li>
               				<li><input id="c9" type="checkbox"  name="requirementStatus"  type="checkbox" value ="open"  onclick="requirementList();"><label for="c8">Open <span id="openRequirementCount"></span></label></li>
               				<li><input id="c10" type="checkbox"  name="requirementStatus"  type="checkbox" value ="onhold"  onclick="requirementList();"><label for="c8">On Hold <span id="onHoldRequirementCount"></span></label></li>
               				<li><input id="c11" type="checkbox"  name="requirementStatus"  type="checkbox" value ="CANCEL"  onclick="requirementList();"><label for="c8">cancel <span id="cancelRequirementCount"></span></label></li>
		              </ul>
		            </div>
          </li>
          </sec:authorize>
          <sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
          	<li> <a href="<c:url value='/requirement'/>"><i class="fa fa-plus"></i>Add Requirement</a></li>
          </sec:authorize>
          <sec:authorize access="hasAnyRole('ROLE_ADD_TEAM')">
          	<li> <a href="<c:url value='/team'/>"><i class="fa fa-plus"></i>Team</a></li>
          </sec:authorize>
          <sec:authorize access="hasAnyRole('ROLE_EXPORT_REQUIREMENTS')">
           <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop" onClick="mypop();"></i>
            <div class="export_pop">
              <ul>
                <li onclick="exportToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp;PDF</li>
                <li onclick="exportToExcel();"><i class="fa fa-file-excel-o"></i>&nbsp;Excel</li>
              </ul>
            </div>
          </li>
          </sec:authorize>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
      </div>
      
      <!-- list requirement -->
      <div class="require_wrap_table">
          <div id="requirementList"></div>
      </div>
    </div>
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
      				<div id="requirementSideDiv">
				     </div>  
      </div>
    </div>
    <!-- Project --> 
  </div>
</section>

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script> 
<script type="text/javascript">
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

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// Bar chat pop
$(document).ready(function() {
    $(".graf_icon").click(function(){
		$(".pipeline_graf").slideToggle(200);
	});
$( "ul#canName-ch li").click(function(){
   var m = $(this).text();
  $("#canName").text(m);
});
});
</script> 
<script>
$( "#pri-ch li").click(function(){
   var m = $(this).text();
    var mid = $(this).attr('id');
    $("#pri-val").text(m);
     if('h' === $(this).attr("id")){
          $("#pri-val").removeClass("text-warning text-primary").addClass("text-danger");
     }else if('m' === $(this).attr("id")){
        $("#pri-val").removeClass("text-danger text-warning").addClass("text-primary");
     }else{
      $("#pri-val").removeClass("text-danger text-primary").addClass("text-warning");
     }   
});
</script> 
<script>
$(document).ready(function(){
	// Levels adding script
$(".add-offer").click(function(){
	 $(".addOffer-Req").toggleClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-interview").click(function(){
	 $(".addInterview-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-applicant").click(function(){
	 $(".addApplicant-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
 });
$(".reminder").click(function(){
	 $(".reminder-div").toggleClass("reminder-div-active");
 });

 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".div-close").click(function(){
	 $(this).parent().toggleClass("showDiv");
	 });

 $(".reminder").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $("").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   });
</script>


<script type="text/javascript">
function changeStatus(requirementId){
	var status = document.getElementById('changeStatusId' + requirementId).value;
	/* $("#changeStatusId option[value=status]").remove(); */
	 var sourceleadUrl ='${sourceleadUrl}';
	   $.ajax({
         type : "POST",
          url: sourceleadUrl+"/saveChangedStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status,        
          success :function(data) { 
        	  $("#changeStatusId" +requirementId).val('status');
        	  location.replace(sourceleadUrl+"/requirementList");
          }
}); 
}
</script>


<script type="text/javascript">
            $(function () {
                $('#datetimepicker5').datetimepicker({
					locale: 'ru'
                });
            });
        </script>
<div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
  <ul class="list-unstyled">
    <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
    <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
    <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
  </ul>
</div>
</body>
</html>


<script>
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
</script>

<script>
function exportToPdf(){
	var req = $("#noReq").val();
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementEmptyMessage = $("#requirementEmptyListMessage").val();
	  var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
	 var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() 
	    {
			clients.push($(this).val());
	    });
		if(requirementEmptyMessage != 'noRequirement'){
		window.location.href = sourceleadUrl+"/getRequirementExportListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clients="+clients+"&exportType=pdf";
		}else{
			 var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>Requirement List is Empty</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#empty_requirement_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
		}
}
</script>

<script>
function exportToExcel(){
	var req = $("#noReq").val();
	var requirementEmptyMessage = $("#requirementEmptyListMessage").val();
	var sourceleadUrl ='${sourceleadUrl}';
	  var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
	 var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() 
	    {
			clients.push($(this).val());
	    });
		if(requirementEmptyMessage != 'noRequirement'){
			window.location.href = sourceleadUrl+"/getRequirementExportListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clients="+clients+"&exportType=xls";	
		}else{
			 var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>Requirement List is Empty</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#empty_requirement_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
		}
		
}

</script>
  </script> 
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<jsp:include page="../masterFooter.jsp" />