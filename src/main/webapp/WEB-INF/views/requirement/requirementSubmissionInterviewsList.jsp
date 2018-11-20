
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

	<div class="clearfix">
  	<div class="modal-content">
  	    <div class="modal-header">
        <button type="button" class="close" onclick="closeInterviewlistpop();">&times;</button>
        <h4 class="modal-title">${candidate.fullName}, <span class="ml10 small text-muted">${candidate.designationType}</span></h4>
      </div>
  		<div class="modal-body">
  			<div class="clearfix tablecustom-styles mailerpopup overflow_Auto">
  				<table class="table">
  					<thead>
  					<tr>
  						<th>Interview Type</th>
  						<th>Date &amp; Time</th>
  						<th>Round</th>
  						<th>Score %</th>
  						<th>Status</th>
  						<th>Comment</th>
  						<th>&nbsp;</th>
  					</tr>
					</thead>
					<tbody>
					
						<c:forEach items="${interviewsList}" var="opt" varStatus="loop">
					        <tr>
								<td>
									<div class="text-left tblminwidth-150">
									<i class="fa fa-plus paddingRight cursorPointer mr5 knowNext" id="${opt.id}InterviewRatingsClickDiv" onclick="getInterviewRatings(${opt.id});"></i>${opt.interviewType}
									</div>
								</td>
								<td>
									<div class="tblminwidth-75">
										${opt.dateOfInterviewStr}
									</div>
									<div class="tblminwidth-75">
										${opt.startTimeStr}
										<c:if test="${not empty opt.endTimeStr}">
											&nbsp;-&nbsp;${opt.endTimeStr}
										</c:if>
									</div>
								</td>
								<td>
									<div class="tblminwidth-75">
										${loop.count}
									</div>
								</td>
								<td>
									<div class="tbldata-dotdot_80" id="${opt.id}interviewScoreDiv">
										<input type="text" class="form-control" value="${opt.interviewScore}" id="${opt.id}interviewScore" name="${opt.id}interviewScore" onblur="enableSaveIcon(${opt.id});">
									</div>
								</td>
								<td>
									<div class="tbldata-dotdot-sm">
									<select class="chosen-select sel" id="${opt.id}interviewPhase" name="${opt.id}interviewPhase" onchange="enableSaveIcon(${opt.id});">
										<c:if test="${empty opt.interviewPhase}">
											<option value="">--Select--</option>
										</c:if>
										<c:if test="${opt.interviewPhase != 'Rejected'}">
											<option ${opt.interviewPhase == 'Selected' ? 'selected' : ''}>Selected</option>
										</c:if>
										<option ${opt.interviewPhase == 'Rejected' ? 'selected' : ''}>Rejected</option>
										<%-- <option ${opt.interviewPhase == 'On-Hold' ? 'selected' : ''}>On-Hold</option> --%>
									</select>
									</div>
								</td>
								<td>
								<div>
									<textarea class="form-control" rows="1" id="${opt.id}interviewComment" name="${opt.id}interviewComment" onblur="enableSaveIcon(${opt.id});">${opt.interviewComment}</textarea>
								</div>
								</td>
								<td>
									<c:if test="${empty opt.interviewPhase}">
										<div class="tblminwidth-30" id="${opt.id}saveInterviewDiv"><a class="fa fa-floppy-o mt10" onclick="saveInterviewStatus(${opt.id})"></a></div>
									</c:if>
									<c:if test="${not empty opt.interviewPhase}">
										<div class="tblminwidth-30" id="${opt.id}saveInterviewDiv"><a class="fa fa-check mt10" ></a></div>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="6">
									<ul class="list-unstyled clearfix mt5 mb5 row" id="${opt.id}interviewRatingDiv"> </ul>
								</td>
							</tr>
					    </c:forEach>
					</tbody>
  				</table>
  			</div>
  		</div>
  	</div>
  </div>
<script>
$(".knowNext").click(function() {
	$(this).closest("tr").next().slideToggle();
	$(this).toggleClass("fa-plus fa-minus");
});

function closeInterviewlistpop(){
	$(".comMask, .modal-POP").fadeOut();
}

function saveInterviewStatus(id){
	$("#"+id+"interviewScoreDiv").find("p.jquery_form_error_message").remove();
	var interviewScore = $("#"+id+"interviewScore").val();
	var interviewPhase = $("#"+id+"interviewPhase").val();
	var interviewComment = $("#"+id+"interviewComment").val();
	if(interviewScore != null && interviewScore != '' &&  interviewScore != 'undefined'){
		if((interviewScore < 0) || (interviewScore > 100)){
			$("#"+id+"interviewScoreDiv").after("<p class='jquery_form_error_message'>Score should be in between 0 - 100</span>");
			return false;
		}else if(interviewScore != parseInt(interviewScore, 10)){
			$("#"+id+"interviewScoreDiv").after("<p class='jquery_form_error_message'>Score should be in between 0 - 100</span>");
			return false;
		}
	}
  	$.ajax({
		type: "POST",
	    url: sourceleadUrl+"/saveInterviewStatusByInterviewId?"+tokenParamName+"="+tokenValue+"&id="+id+"&interviewScore="+interviewScore+"&interviewPhase="+interviewPhase+"&interviewComment="+interviewComment, 
	    success: function(result) {
			if (result.response === "SESSION_TIMEDOUT") {
  				location.replace(sourceleadUrl);
           } else if (result.response === "AWKWARD") {
               location.href = "/errorPage";
           } else if (result.response === "SUCCESS") {
        	   $("#"+id+"saveInterviewDiv").empty();
        	   $("#"+id+"saveInterviewDiv").append('<i class="fa fa-check mt10"></i>');
           } else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
		   } else {
               showError("Operation failed, please try again");
           }
	    },
	});
}

function enableSaveIcon(id){
	$("#"+id+"saveInterviewDiv").empty();
	$("#"+id+"saveInterviewDiv").append('<a class="fa fa-floppy-o mt10" onclick="saveInterviewStatus('+id+')"></a>');
}

function getInterviewRatings(interviewId){
	if($("#"+interviewId+"InterviewRatingsClickDiv").hasClass("fa-plus")){
		$.ajax({
			type: "POST",
		    url: sourceleadUrl+"/getInterviewerAndRatingListByInterviewId?"+tokenParamName+"="+tokenValue+"&interviewId="+interviewId, 
		    success: function(result) {
				if (result.response === "SESSION_TIMEDOUT") {
	  				location.replace(sourceleadUrl);
	           } else if (result.response === "AWKWARD") {
	               location.href = "/errorPage";
	           } else if (result.response === "SUCCESS") {
	        	   var list = result.data;
	        	   var opt = '';
	        	   for (var i=0; i<list.length; i++){
	        		   opt = opt + '<li class="col-md-6 mb">';
	        		   opt = opt + '	<p class="dotdot-class inlineBlock text-left pull-left">'+ list[i].interviewerName +'</p>';
	        		   if(list[i].interviewerRating !== null) {
		        		   opt = opt + '	<ul class="pull-left list-inline list-unstyled star-rating starating-li ml">';
			        		var details = eval(list[i].interviewerRating);
			        		   $.each(details, function( k, v ) {
			    	    		   var value = '';
			    	    		   if(v['label'].trim() === "Rating"){
			    	    			   
			    	    			   switch (v['value']) {
			    	    			    case 'Inadequate':
			    	    			    	value = 1;
			    	    			        break;
			    	    			    case 'Adequate':
			    	    			    	value = 2;
			    	    			        break;
			    	    			    case 'Good':
			    	    			    	value = 3;
			    	    			        break;
			    	    			    case 'Excellent':
			    	    			    	value = 4;
			    	    			        break;
			    	    			    case 'Outstanding':
			    	    			    	value = 5;
			    	    			        break;
			    	    			    default: value = 0;
			    	    			}
			    	    			   for (var j=0; j<5; j++){
			    	    				   var active = '';
			    	    				   if(value > j){
			    	    					   active = 'acivestar-rating';
			    	    				   }
			    	    				   opt = opt + '<li><i class="fa fa-star '+active+'"></i></li>';
			    	    			   }
			    	    		   }
			    	    	   });
		        		   opt = opt + '	</ul>';
	        		   } else {
	        			   opt = opt + '<span>(Rejected Applicant)</span>';
	        		   }
	        		   opt = opt + '</li>';
					}
	        	   $("#"+interviewId+"interviewRatingDiv").empty();
	        	   $("#"+interviewId+"interviewRatingDiv").append(opt);
	           } else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
			   } else {
	               showError("Operation failed, please try again");
	           }
		    },
		});
	}
}

</script>
