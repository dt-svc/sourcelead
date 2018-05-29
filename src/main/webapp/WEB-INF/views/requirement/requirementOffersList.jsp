<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Offers List</div>
    </section>
    
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
        <div class="timesheet_head">
      
      <jsp:include page="JobCountFilter.jsp"></jsp:include>
      
      <div class="pull-left ml mt10 dropdown-select">
	        <div class="dropdown-select positionRelative">
	        <select class="form-control" id="customDates" onchange="showCustomFields();">
	          <option value="Last_7_days" selected>Last 7 days</option>
	          <option value="Last_15_days">Last 15 days</option>
	          <option value="1_month">1 month</option>
	          <option value="3_months">3 months</option>
	          <option value="Last_Year">Last year</option>
	          <option value="Custom">Custom</option>
	        </select>
	        <!-- Top Leave Takers Custom Date -->
		    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-selects" style="display: none; width: 346px;" >
				<div class="pull-left">
				<div class="pull-left mr5" style="width: 140px" id="custFromDateDiv">
				<input value="" name="Date" id="custFromDate" placeholder="From" class="form-control calendar input-sm margin-top0" type="text">
				</div>
				<div class="pull-left mr5" style="width: 140px" id="custToDateDiv">
				<input value="" name="Date" id="custToDate" placeholder="To" class="form-control calendar input-sm margin-top0" type="text">
				</div>
				<button type="button" class="btn btn-primary btn-sm" onclick="validateCustDatesList();"><i class="fa fa-search"></i></button>
				</div>
		    </div>
	      	</div>
      	</div>
      
       
       </div>
        <div class="clients_wrap_table datatable_filter">
        
        <div id="previousOfferDiv"></div>
        
        
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  
  <script>
 $(document).ready(function() {
	 requirementOfferist();
	 
 });
 
 </script>
 
 <script type="text/javascript">
       function requirementOfferist(){
		var sourceleadUrl ='${sourceleadUrl}';
	   // var tab = "previous";
	    var tab = '${tab}';
	    var fromDate = $("#custFromDate").val();
	    var toDate = $("#custToDate").val();
	   	var customDates = $("#customDates").val();
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/applicantList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=OFFERMADE&frequency="+customDates+"&fromDate="+fromDate+"&toDate="+toDate;
	    prepareOfferList(url);
}
</script> 


<script>
function prepareOfferList(url){
        var result = "";
 	    result = result +' <div class="modal-header">';
 	    result = result +' <div class="remove_search mt10">';
 	    result = result +' <input type="text" placeholder="Search" id="dataTableSearch"/>';
 	    result = result +'</div>';
 	    result = result +'</div>';
        result = result +'<table id="offerpagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Applicant name</th>';
		result = result + '<th>Job</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Resume</th>';
		result = result + '<th>Join Date</th>';
		result = result + '<th>Rate</th>';
		result = result + '<th>Position Type</th>';
		result = result + '<th>Attachment</th>';
		result = result + '<th>Offered On</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>'; 
        result = result +' </tbody>';
        result = result +'</table>';
        $("#previousOfferDiv").empty();
        $("#previousOfferDiv").append(result);
         offerpagination(url);
}
</script>

<script>
function offerpagination(url){
	var colIndex = 0;
    $('#offerpagination').DataTable( { 
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
            { "data": "joinDate"},
            { "data": "rate" },
            { "data": "positionType" },
            { "data": "attachment"},
            { "data": "offeredOn"},
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
                "data": "joinDate",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.offerJoinDate !=null && opt.offerJoinDate !='' && opt.offerJoinDate !='undefined'){
							result = result + ' <td><div><span>'+opt.offerJoinDate+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "rate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.offerRate !=null && opt.offerRate !='' && opt.offerRate !='undefined'){
	    	  			result = result + ' <td><div><span>'+opt.offerRate+'</span></div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "positionType",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.positionType != null && opt.positionType !='' && opt.positionType != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.positionType+'</span></div></td>';
	    	  		}else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "attachment",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
  	    	  		   if(opt.contentType != null && opt.contentType !='' && opt.contentType != 'undefined' && opt.attachment!=null){
  	    	  			result = result + ' <td><div><span></span></div></td>';
	  	    	  		result = result +'<a href="<c:url value="/app/ws/downloadOfferAttachment?offerId='+opt.offerId+'"/>"><i class="fa fa-paperclip"></i></a>';
	  	    	  		result = result + ' <a class="fa fa-eye ml10" onclick="viewOffersListAttachment(\''+opt.offerId+'\',\''+opt.attachment+'\');"></a>';
//			          
	  	    	  	    result = result + '</span></div></td>';
  	    	  		   }else{
  	    	  			result = result + ' <td><div><span>---</span></div></td>';
  	    	  		   }
  	    	  		return result;
                  }
                },{
                    "targets": colIndex++,
                    "data": "offeredOn",
                    "render": function ( data, type, opt, meta ) {
                    	var result = "";
   	    	  		   if(opt.appliedOn != null && opt.appliedOn !='' && opt.appliedOn != 'undefined'){
   	    	  			result = result + ' <td><div><span>'+opt.appliedOn+'</span></div></td>';
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
    $("#offerpagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   requirementOfferist();
    	   }
    	});
}

</script>
  
<script>
function viewInterviewListResumes(resumeUUID,title){
	if(resumeUUID != null && resumeUUID != ""){
		window.open(sourceleadUrl+'/viewInterviewListResumes/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
	}  
}
</script>

<script>
function viewOffersListAttachment(offerId,title){
	if(offerId != null && offerId != ""){
		window.open(sourceleadUrl+'/viewOffersListAttachment/'+title+'?offerId='+offerId,title,"height=600,width=600");
	}  
}
</script>

<script>
function showCustomFields(){
    var frequency = $("#customDates").val();
    if(frequency == "Custom"){
        $("#custom-selects").show();
         $('#custFromDate').attr('data-validation',"required");
         $('#custToDate').attr('data-validation',"required");
    }else{
        $("#custom-selects").hide();
        $('#custFromDate').removeAttr('data-validation');
        $('#custToDate').attr('data-validation',"required");
        $('#custFromDate').val("");
        $('#custToDate').val("");
        requirementOfferist();
    }
    
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
      var finalDateFormatEndDate = '';
      if(locationDate.match("yyyy-MM-dd")){
          finalDateFormatEndDate = 'yy-mm-dd';
      }if(locationDate.match("dd-MM-yyyy")){
          finalDateFormatEndDate = 'dd-mm-yy'; 
      }if(locationDate.match("MM-dd-yyyy")){
          finalDateFormatEndDate = 'mm-dd-yy'; 
      }if(locationDate.match("yyyy-dd-MM")){
          finalDateFormatEndDate = 'yy-dd-mm'; 
      }

      $( "#custFromDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
            maxDate: new Date() ,
            onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custToDate").datepicker("option", "minDate", dt);
             removeStartValid();
            } 
      
      });
      
      $( "#custToDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
          	maxDate: new Date() ,
      		onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custFromDate").datepicker("option", "maxDate", dt);
             removeEndValid();
            }});
}
function removeStartValid(){
    $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
}
function removeEndValid(){
    $('#custToDateDiv').find('p.jquery_form_error_message').remove();
}


function validateCustDatesList(){
// 	var userType = $("#userTypeId").val();
    var frequency = $("#customDates").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
    if(frequency == "Custom"){
        if(fromDate == ''){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $("input#custFromDate").after( "<p class='jquery_form_error_message'>FromDate is required.</span>");
        }
        if(toDate == ''){
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            $("input#custToDate").after( "<p class='jquery_form_error_message'>ToDate is required.</span>");
        }
        if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            requirementOfferist();
            $("#custom-selects").hide();
        }
    }else{
        $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
        $('#custToDateDiv').find('p.jquery_form_error_message').remove();
        requirementOfferist();
    } 
}
</script>

<jsp:include page="../masterFooter.jsp" />
