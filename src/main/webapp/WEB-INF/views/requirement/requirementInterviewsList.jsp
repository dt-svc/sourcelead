<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Interview List</div>
    </section>
    
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
        <jsp:include page="JobCountFilter.jsp"></jsp:include>
        <div class="clients_wrap_table datatable_filter">
        
        <div id="previousInterviewDiv"></div>
        
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  
  
  
<script>
 $(document).ready(function() {
	 requirementInterviewList();
	 
 });
 
 </script>
 
 
 <script type="text/javascript">
       function requirementInterviewList(){
		var sourceleadUrl ='${sourceleadUrl}';
		// var tab = "previous";
		var tab = '${tab}';
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/applicantList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tab="+tab+"&submissionStatus=INTERVIEW";
	    prepareInterviewList(url);
}
</script> 


<script>
function prepareInterviewList(url){
        var result = "";
 	    result = result +' <div class="modal-header">';
 	    result = result +' <div class="remove_search">';
 	    result = result +' <input type="text" placeholder="Search" id="dataTableSearch"/>';
 	    result = result +'</div>';
 	    result = result +'</div>';
        result = result +'<table id="interviewpagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Applicant name</th>';
		result = result + '<th>Job</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Resume</th>';
		result = result + '<th>Applied On</th>';
		result = result + '<th>No Of Interviews</th>';
		result = result + '<th>Source</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>'; 
        result = result +' </tbody>';
        result = result +'</table>';
        $("#previousInterviewDiv").empty();
        $("#previousInterviewDiv").append(result);
         interviewpagination(url);
}
</script>


<script>
function interviewpagination(url){
	var colIndex = 0;
    $('#interviewpagination').DataTable( { 
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
            { "data": "resume" },
            { "data": "appliedOn"},
            { "data": "noOfInterviews" },
            { "data": "source"},
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
          }, {
              "targets": colIndex++,
              "data": "resume",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.resumeUUID !=null && opt.resumeUUID !='' && opt.resumeUUID !='undefined'){
		              result = result + ' <td><div><span>';   
		              result = result + ' <a target="_blank" href="'+opt.fileUrl+'"><i class="fa fa-download"></i></a>';
		              result = result + ' <a onclick="viewFile(\''+opt.fileUrl+'\',\''+opt.fileName+'\',\''+opt.contentType+'\');" class="fa fa-eye ml10 paddingRight"></a>';
//			              result = result + ' <a href="<c:url value="/downloadResumeCandidate/'+opt.resumeUUID+'"/>"></a>';
		              result = result + '</span></div></td>';  
				}else{
              		 result = result + '<td><div><span>---</span></div></td>';
              	 }
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "appliedOn",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.appliedOn !=null && opt.appliedOn !='' && opt.appliedOn !='undefined'){
							result = result + ' <td><div><span>'+opt.appliedOn+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "noOfInterviews",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.numOfInterview !=null && opt.numOfInterview !='' && opt.numOfInterview !='undefined'){
	    	  			result = result + ' <td><div><span>'+opt.numOfInterview+'</span></div></td>'; 
					}else{
						result = result + ' <td><div>--</div></td>';
					}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "source",
	    	  	"render": function(data, type, opt, meta) {
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
// 	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#interviewpagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   requirementInterviewList();
    	   }
    	});
}

</script>
        
        
               <%--  <table id="appTable">
            
            <thead>
              <tr>
                <th>Applicant name</th>
                <th>Job</th>
                <th>Client</th>
                <th>Resume</th>
                <th>Applied On</th>
                <th>No Of Interviews</th>
                <th>Source</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="reqSub" items="${toDayReqInterviewscount}">
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
              <td><div><span>${reqSub.submissionId.createdDateStr}</span></div></td>
              
               <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.numOfInterview}">
	               ${reqSub.submissionId.numOfInterview} 
	              </c:if>
	               <c:if test="${empty reqSub.submissionId.numOfInterview}">
	               --
	              </c:if>
              </span></div>
              </td>
                           
               <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.createdUser}">
	            	  ${reqSub.submissionId.createdUser}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.createdUser}">
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
function viewInterviewListResumes(resumeUUID,title){
	if(resumeUUID != null && resumeUUID != ""){
		window.open(sourceleadUrl+'/viewInterviewListResumes/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
	}  
}
</script>
  
 
<jsp:include page="../masterFooter.jsp" />