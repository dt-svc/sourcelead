<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/jobsPerformanceReport'/>">Jobs Report</a> &raquo; Applicants List</div>
    </section>
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
      <div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="advanceSearchDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetApplicantFilter();"></a>
      </span>
      
      <ul class="pull-right list-unstyled list-inline">
      
      	  <li>
      	 <%--  <jsp:include page="JobCountFilter.jsp"></jsp:include> --%>
      	  </li>
				
		  <li class=""><input type="text" class="repSearchBar" placeholder="Search" id="dataTableSearch"><span class="repSearchIcon" onclick="filter();"></span></li>
        </ul>
      </div>
      
      <div class="clearfix"></div>
      <div class="timesheet_head">
       </div>
        <div class="clients_wrap_table datatable_filter positionRelative">
        
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
   		   var tab = '${tab}';
    	   var frequency = $('input[name=customDates]:checked').val(); 
    	    var fromDate = $("#custFromDate").val();
    	    var toDate = $("#custToDate").val();
    	    var requirementId = '${requirementId}';
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		searchValue = searchValue ? searchValue : "";
    	      
    	       var url = sourceleadUrl+"/applicantListForJob?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&requirementId="+requirementId+"&submissionStatus=All+&tabType="+tab;
    		    prepareApplicantList(url);
		
}
</script> 
        
<script>
function prepareApplicantList(url){
        var result = "";
        result = result +'<table id="applicantpagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Applicant name</th>';
		result = result + '<th>Phone Number</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Job</th>';
		result = result + '<th>Location(s)</th>';
		result = result + '<th>Source</th>';
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
            pages: 1
        }),
        "columns": [
            { "data": "applicantName" },
            { "data": "phoneNumber"},
            { "data": "job"},
            { "data": "client"},
            { "data": "locations" },
            { "data": "source"},
            { "data": "status"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "applicantName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
							result = result + ' <td>';
							result = result + ' <div title="'+opt.candidateName+'" class="clearfix mt5 proj_show"><span><a href="'+sourceleadUrl+'/requirementSummary?type=applicants&requirementUUID='+opt.requirementUUID+'&candidateUUID='+opt.candidateUUID+'&submissionId='+opt.submissionId+'" >'+opt.candidateName+'<i class="fa fa-binoculars"></i></a></span></div>';
							result = result + ' <div title="'+opt.email+'" class="clearfix mt5 mb5">'+opt.email+'</div>';
							result = result + ' </td>';
						}else{
							result = result + ' <td><div>--</div></td>';
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
          },{
	    	  "targets": colIndex++,
              "data": "job",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.requirementUUID !=null && opt.requirementUUID !='' && opt.requirementUUID !='undefined'){
            		  if(opt.jobGeneratedId != null && opt.jobGeneratedId != ''  && opt.jobGeneratedId !="undefined"){
                		  result = result + '<td><div class="proj_show"><span title="'+opt.jobGeneratedId+'-'+opt.jobTitle+'"><a href="'+sourceleadUrl+'/requirementSummary?requirementUUID='+opt.requirementUUID+'">'+opt.jobGeneratedId+' - '+opt.jobTitle+'</a></span></div></td>';
            		  }else{
                		  result = result + '<td><div class="proj_show"><span title="'+opt.jobGeneratedId+'-'+opt.jobTitle+'"><a href="'+sourceleadUrl+'/requirementSummary?requirementUUID='+opt.requirementUUID+'">'+opt.jobTitle+'</a></span></div></td>';
            		  }
					}else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              }
	     } ,{
	    	  	"targets": colIndex++,
	    	  	"data": "locations",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
            		
            	    result = result +'<td>';
   		        	result = result +'<div class="positionRelative">';
	   		         for(var k=0; k<1; k++){
	   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.locationsList[k]+'</p>';
	   		         }
   		    	
	   		        result = result +'<div class="show_Empdetails showjoblist">';
   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';

   		        	result = result +'<div class="emailvalues emailvaluesNew">';
   		        	
						for(var k=0; k<opt.locationsList.length; k++){
							result = result +'<p><i class="fa fa-map-marker"></i>'+opt.locationsList[k]+'</p>';
		   		        	
   		    	 	}
   		        	result = result +'</div>';
   		        	result = result +'</div>';
   

		        	result = result +'</div>';
		        	result = result +'</td>';
        		
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