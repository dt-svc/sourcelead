<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Submission List</div>
    </section>
    
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
        <jsp:include page="JobCountFilter.jsp"></jsp:include>
        <div class="clients_wrap_table datatable_filter">
        
        <div id="previousSubmissionList"></div>
        
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  
 <script>
 $(document).ready(function() { 
	 requirementSubmissionList();
 });
 
 </script>
 
 
 </script>
<script type="text/javascript">
       function requirementSubmissionList(){
		var sourceleadUrl ='${sourceleadUrl}';
		var tab = "previous";
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/applicantList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tab="+tab+"&submissionStatus=SUBMIT_TO_CLIENT";
		prepareSubmissionList(url);
}
</script> 


<script>
function prepareSubmissionList(url){
        var result = "";
 	    result = result +' <div class="modal-header">';
 	    result = result +' <div class="remove_search">';
 	    result = result +' <input type="text" placeholder="Search" id="dataTableSearch"/>';
 	    result = result +'</div>';
 	    result = result +'</div>';
        result = result +'<table id="submissionpagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Applicant name</th>';
		result = result + '<th>Job</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Applied On</th>';
		result = result + '<th>Submitted On</th>';
		result = result + '<th>Resume</th>';
		result = result + '<th>Exp</th>';
		result = result + '<th>Sub rate</th>';
		result = result + '<th>Source</th>';
		result = result + '<th>Score</th>';
		result = result + '<th>Submitted by</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>'; 
        result = result +' </tbody>';
        result = result +'</table>';
        $("#previousSubmissionList").empty();
        $("#previousSubmissionList").append(result);
         submissionpagination(url);
}
</script>


<script>
function submissionpagination(url){
	var colIndex = 0;
    $('#submissionpagination').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "applicantName" },
            { "data": "job"},
            { "data": "client"},
            { "data": "Applied On" },
            { "data": "Submitted On" },
            { "data": "resume" },
            { "data": "Exp" },
            { "data": "Sub rate" },
            { "data": "source"},
            { "data": "score"},
            { "data": "Submitted by"},
            { "data": "status"}  
            
        ],
       "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "applicantName",
    	  	"render": function(data, type, opt, meta) {
    	  		  var result = "";
    	  		 if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
						result = result + ' <td><div><span>'+opt.candidateName+'</span></div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
	             return result;
    	  	}
      },{
    	  "targets": colIndex++,
          "data": "job",
          "render": function ( data, type, opt, meta ) {
        	  var result = "";
        	  if(opt.requirementUUID !=null && opt.requirementUUID !='' && opt.requirementUUID !='undefined'){
        		  result = result + '<td><div class="proj_show"><span><a href="'+sourceleadUrl+'/requirementSummary?type=applicants&requirementUUID='+opt.requirementUUID+'&candidateUUID='+opt.candidateUUID+'&submissionId='+opt.submissionId+'" >'+opt.jobTitle+'<i class="fa fa-binoculars"></i></a></span></div></td>';
				}else{
					result = result + ' <td><div>--</div></td>';
				}
        	  return result;
          		
          }
      },{
        "targets": colIndex++,
        "data": "client",
        "render": function ( data, type, opt, meta ) {
        	var result = "";
        	
				if(opt.orgLocationName != null && opt.orgLocationName != '' && opt.orgLocationName !='undefined'){
					result = result + '<td><div><span>'+opt.orgLocationName+' </span></div></td>';
        	 }else{
        		 result = result + '<td><div><span>---</span></div></td>';
        	 }
				return result;
        }
      },
       {
          "targets": colIndex++,
          "data": "Applied On",
          "render": function ( data, type, opt, meta ) {
        	  var result = "";
        	  if(opt.appliedOn != null && opt.appliedOn != '' && opt.appliedOn !='undefined'){
 					result = result + '<td><div><span>'+opt.appliedOn+'</span></div></td>';
          	 }else{
          		 result = result + '<td><div><span>---</span></div></td>';
          	 }
        	  return result;
          }
        },{
            "targets": colIndex++,
            "data": "Submitted On",
            "render": function ( data, type, opt, meta ) {
          	  var result = "";
          	  if(opt.submittedOn != null && opt.submittedOn != '' && opt.submittedOn !='undefined'){
   					result = result + '<td><div><span>'+opt.submittedOn+'</span></div></td>';
            	 }else{
            		 result = result + '<td><div><span>---</span></div></td>';
            	 }
          	  return result;
            }
          },
        {
    	  	"targets": colIndex++,
    	  	"data": "resume",
    	  	"render": function(data, type, opt, meta) {
    	  		var result = "";
    	  		if(opt.resumeUUID !=null && opt.resumeUUID !='' && opt.resumeUUID !='undefined'){
		              result = result + ' <td><div><span>';   
		              result = result + ' <a target="_blank" href="'+opt.fileUrl+'"><i class="fa fa-download"></i></a>';
		              result = result + ' <a onclick="viewFile(\''+opt.fileUrl+'\',\''+opt.fileName+'\',\''+opt.contentType+'\');" class="fa fa-eye ml10 paddingRight"></a>';
//			              result = result + ' <a href="<c:url value="/downloadResumeCandidate/'+opt.resumeUUID+'"/>"></a>';
		              result = result + '</span></div></td>';  
				}else{
					result = result + ' <td><div>--</div></td>';
				}
	              return result;
    	  	}
    	  },{
    	  	"targets": colIndex++,
    	  	"data": "Exp",
    	  	"render": function(data, type, opt, meta) {
    	  		var result = "";
    	  		if(opt.experienceYears != null && opt.experienceYears !='' && opt.experienceYears != 'undefined'){
    	  			result = result + ' <td><div><span>'+opt.experienceYears+'</span></div></td>';
    	  		}else{
    	  			result = result + ' <td><div><span>---</span></div></td>';
    	  		}
    	  		return result;
    	  	}
    	  },{
    	  	"targets": colIndex++,
    	  	"data": "Sub Rate",
    	  	"render": function(data, type, opt, meta) {
    	  		var result = "";
    	  		if(opt.finalizedRate != null && opt.finalizedRate !='' && opt.finalizedRate != 'undefined'){
    	  			result = result + ' <td><div><span>'+opt.finalizedRate+'</span></div></td>';
    	  		}else{
    	  			result = result + ' <td><div><span>---</span></div></td>';
    	  		}
    	  		return result;
    	  	}
    	  },{
              "targets": colIndex++,
              "data": "source",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	    	  		   if(opt.createdUser != null && opt.createdUser !='' && opt.createdUser != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.createdUser+'</span></div></td>';
	    	  		   }else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		   }
	    	  		return result;
              }
            }, {
                "targets": colIndex++,
                "data": "score",
                "render": function ( data, type, opt, meta ) {
                	var result = "";
	    	  		   if(opt.candidateScore != null && opt.candidateScore !='' && opt.candidateScore != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.candidateScore+'</span></div></td>';
	    	  		   }else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		   }
	    	  		return result;
                }
              },{
                  "targets": colIndex++,
                  "data": "Submitted by",
                  "render": function ( data, type, opt, meta ) {
                  	var result = "";
  	    	  		   if(opt.submittedBy != null && opt.submittedBy !='' && opt.submittedBy != 'undefined'){
  	    	  			result = result + ' <td><div><span>'+opt.submittedBy+'</span></div></td>';
  	    	  		   }else{
  	    	  			result = result + ' <td><div><span>---</span></div></td>';
  	    	  		   }
  	    	  		return result;
                  }
                },{
                  "targets": colIndex++,
                  "data": "status",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
  	    	  		   if(opt.toStage != null && opt.toStage !='' && opt.toStage != 'undefined'){
  	    	  			result = result + ' <td><div><span>'+opt.toStage+'</span></div></td>';
  	    	  		   }else{
  	    	  			result = result + ' <td><div><span>---</span></div></td>';
  	    	  		   }
  	    	  		return result;
                  }
                } ],  
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
//	    	"aaSorting": [[3,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": "", 
     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
     }
} );
$("#submissionpagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
$("input[type=search]").hide();
$("#dataTableSearch").keyup(function(e) {
	   if(e.which === 13) {
		   requirementSubmissionList();
	   }
	});
}

</script>
        
        
                <%-- <table id="appTable">
            <thead>
              <tr>
                <th>Applicant name</th>
                <th>Job</th>
                <th>Client</th>
                <th>Applied On</th>
                <th>Submitted On</th>
                <th>Resume</th>
                <th>Exp</th>
                <th>Sub rate</th>
                <th>Source</th>
                <th>Score</th>
                <th>Submitted by</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="reqSub" items="${toDayReqSubmissionsList}">
              <tr>
			 <td><div><span>${reqSub.submissionId.candidate.firstName}</span></div></td>
             <sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')"> 
			 	<td><div class="proj_show"><span><a href='<c:url value='${appPath}/requirementSummary?requirementUUID=${reqSub.submissionId.requirement.requirementUUID}'/>'>${reqSub.submissionId.requirement.jobTitle}<i class='fa fa-binoculars'></i></a></span></div></td>
            </sec:authorize>
            <sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">
            	<td><div><span>${reqSub.submissionId.requirement.jobTitle}</span></div></td>
            </sec:authorize>
              <td><div><span>
              <c:if test="${not empty reqSub.submissionId.requirement.parentRequirement.orgLocationId.org.orgName}">
              	${reqSub.submissionId.requirement.parentRequirement.orgLocationId.org.orgName}
              </c:if>
              <c:if test="${empty reqSub.submissionId.requirement.parentRequirement.orgLocationId.org.orgName}">
              	--
              </c:if>
              </span></div></td>
              <td><div><span>${reqSub.submissionId.createdDateStr}</span></div></td>
              <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.submittedOn}">
	             	 ${reqSub.submissionId.submittedOn}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.submittedOn}">
	             	 --
	              </c:if>
              </span></div>
              </td>
             
               <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.candidateResumes.resumeUUID}">
	              
	               <a target="_blank" href="${reqSub.submissionId.candidateResumes.fileURL}"/><i class='fa fa-download'></i></a>
	               <a href="javascript:void(0)" onclick="viewFile('${reqSub.submissionId.candidateResumes.fileURL}','${reqSub.submissionId.candidateResumes.fileName}','${reqSub.submissionId.candidateResumes.fileContentType}');" class="fa fa-eye ml10 paddingRight"></a>
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidateResumes.resumeUUID}">
	             	 --
	              </c:if>
              </span></div>
              </td>
              
               <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.candidate.experienceYears && not empty reqSub.submissionId.candidate.experienceMonths}">
	               ${reqSub.submissionId.candidate.experienceYears} Year/s ${reqSub.submissionId.candidate.experienceMonths}  Month/s
	              </c:if>
	                <c:if test="${not empty reqSub.submissionId.candidate.experienceYears && empty reqSub.submissionId.candidate.experienceMonths}">
	             	  ${reqSub.submissionId.candidate.experienceYears} Year/s
	              </c:if>
	               <c:if test="${empty reqSub.submissionId.candidate.experienceYears && not empty reqSub.submissionId.candidate.experienceMonths}">
	               		${reqSub.submissionId.candidate.experienceMonths} Year/s
	               </c:if>
	                 <c:if test="${empty reqSub.submissionId.candidate.experienceYears && empty reqSub.submissionId.candidate.experienceMonths}">
	               		--
	               </c:if>
              </span></div>
              </td>
              
              
              <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.finalizedRate}">
	            	  ${reqSub.submissionId.finalizedRate}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.finalizedRate}">
	             	 --
	              </c:if>
              </span></div></td>
         
              
               <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.createdUser}">
	            	  ${reqSub.submissionId.createdUser}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.createdUser}">
	             	 --
	              </c:if>
              </span></div></td>
              
               <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.candidateScore}">
	            	  ${reqSub.submissionId.candidateScore}%
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidateScore}">
	             	 --
	              </c:if>
              </span></div></td> 
            
             <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.submittedBy}">
	            	  ${reqSub.submissionId.submittedBy}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.submittedBy}">
	             	 --
	              </c:if>
              </span></div></td> 
              
               <td><div><span>
	               <c:if test="${not empty reqSub.toStage}">
	            	  ${reqSub.toStage}
	              </c:if>
	              <c:if test="${empty reqSub.toStage}">
	             	 --
	              </c:if>
              </span></div></td> 
            
               </tr>
			  </c:forEach>
            </tbody>
            
          </table> --%>
        
  
  
    
 <script>
 function submissionDocumentListDocumentView(resumeUUID,title){
	 var sourceleadUrl ='${sourceleadUrl}';
		if(resumeUUID != null && resumeUUID != ""){
			window.open(sourceleadUrl+'/viewInterviewListResumes/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
		}
 }
</script>
 
<jsp:include page="../masterFooter.jsp" />
