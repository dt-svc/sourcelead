<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Applicant List</div>
    </section>
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
       <jsp:include page="JobCountFilter.jsp"></jsp:include>
        <div class="clients_wrap_table datatable_filter">
        
        <div id="previousApplicantsDiv"></div>
        
         </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
<script>
 $(document).ready(function() {
	 requirementApplicantList();
	 
 });
 
 </script>
<script type="text/javascript">
       function requirementApplicantList(){
		var sourceleadUrl ='${sourceleadUrl}';
		// var tab = "previous";
		 var tab = '${tab}';
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/applicantList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tab="+tab+"&submissionStatus=SUBMISSION";
	    prepareApplicantList(url);
}
</script> 
        
<script>
function prepareApplicantList(url){
        var result = "";
 	    result = result +' <div class="modal-header">';
 	    result = result +' <div class="remove_search">';
 	    result = result +' <input type="text" placeholder="Search" id="dataTableSearch"/>';
 	    result = result +'</div>';
 	    result = result +'</div>';
        result = result +'<table id="applicantpagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Applicant name</th>';
		result = result + '<th>Job</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Email</th>';
		result = result + '<th>Phone Number</th>';
		result = result + '<th>Resume</th>';
		result = result + '<th>Primary Skill</th>';
		result = result + '<th>Source</th>';
		result = result + '<th>Score</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>'; 
        result = result +' </tbody>';
        result = result +'</table>';
        $("#previousApplicantsDiv").empty();
        $("#previousApplicantsDiv").append(result);
         applicantpagination(url);
}
</script>

<script>
function applicantpagination(url){
	var colIndex = 0;
    $('#applicantpagination').DataTable( { 
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
            { "data": "email" },
            { "data": "phoneNumber"},
            { "data": "resume" },
            { "data": "primarySkill" },
            { "data": "source"},
            { "data": "score"},
            { "data": "status"}
            
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "applicantName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
							result = result + ' <td><div><span title='+opt.candidateName+'>'+opt.candidateName+'</span></div></td>';
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
            		  result = result + '<td><div class="proj_show"><span title='+opt.jobTitle+'><a href="'+sourceleadUrl+'/requirementSummary?type=applicants&requirementUUID='+opt.requirementUUID+'&candidateUUID='+opt.candidateUUID+'&submissionId='+opt.submissionId+'" >'+opt.jobTitle+'<i class="fa fa-binoculars"></i></a></span></div></td>';
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
              "data": "email",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.email != null && opt.email != '' && opt.email !='undefined'){
     					result = result + '<td><div><span title='+opt.email+'>'+opt.email+'</span></div></td>';
              	 }else{
              		 result = result + '<td><div><span>---</span></div></td>';
              	 }
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "phoneNumber",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.mobilePhone !=null && opt.mobilePhone !='' && opt.mobilePhone !='undefined'){
							result = result + ' <td><div><span>'+opt.mobilePhone+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "resume",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.resumeUUID !=null && opt.resumeUUID !='' && opt.resumeUUID !='undefined'){
			              result = result + ' <td><div><span>';   
			              result = result + ' <a target="_blank" href="'+opt.fileUrl+'"><i class="fa fa-download"></i></a>';
			              result = result + ' <a onclick="viewFile(\''+opt.fileUrl+'\',\''+opt.fileName+'\',\''+opt.contentType+'\');" class="fa fa-eye ml10 paddingRight"></a>';
// 			              result = result + ' <a href="<c:url value="/downloadResumeCandidate/'+opt.resumeUUID+'"/>"></a>';
			              result = result + '</span></div></td>';  
					}else{
						result = result + ' <td><div>--</div></td>';
					}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "primarySkill",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.primarySkill != null && opt.primarySkill !='' && opt.primarySkill != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.primarySkill+'</span></div></td>';
	    	  		}else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		}
	    	  		return result;
	    	  	}
	    	  }, {
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
                },{
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
                      "data": "status",
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
      	    	  		   if(opt.submissionStatus != null && opt.submissionStatus !='' && opt.submissionStatus != 'undefined'){
      	    	  			result = result + ' <td><div><span>'+opt.submissionStatus+'</span></div></td>';
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
    $("#applicantpagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   requirementApplicantList();
    	   }
    	});
}

</script>
        
       
  
 <script>
 function applicatDocumentListDocumentView(resumeUUID,title){
	 var sourceleadUrl ='${sourceleadUrl}';
		if(resumeUUID != null && resumeUUID != ""){
			window.open(sourceleadUrl+'/viewInterviewListResumes/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
		}
 }
</script>
  
   
<jsp:include page="../masterFooter.jsp" />