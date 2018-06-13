<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/resources/css/style.css" />
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<script src="/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<script>
function submitCandidatesToJob(){
	
	
	 var candidateIds = new Array();
	 for(var e in shareCandidates) {
		 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
			   continue;
		 candidateIds.push(shareCandidates[e]);
	 } 
	 
	 if(candidateIds.length > 0){ 
		$('.action-pop').slideUp();
		$(".new_side_wrap").addClass("new_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  createdDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  createdDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  createdDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  createdDate = 'YYYY-DD-MM'; 
		  }
		  
		var result = "";
		result = result + '<h3>Requirement List</h3>';
		result = result +" <div class='mr pull-left'>";
		result = result +" <button type='button' onclick='jobAdvSearch()' class='btn btn-primary btn-sm'>Advanced Search&nbsp;&nbsp;<i class='fa fa-search'></i></button>";
		result = result +"</div>";
		result = result +" <div class='pull-right'>";
		result = result +" <div class='mr pull-left'>";
		result = result +" <input type='button' value='Submit To Job' onclick='submitCandidtesToJob()' class='btn btn-primary btn-sm'>";
		result = result +"</div>";
		result = result +" <div class='dynamic_search pull-left'>";
	 	result = result +" <input type='text' placeholder='Search' id='reqDataTableSearch'/>";
	 	result = result +" <span class='cursorPointer faSearch' title='Search'><i class='fa fa-search' onClick='getRequirementTableList();'></i></span>";
	 	result = result +"</div>";
	 	result = result +"</div>";
	 	result = result +"<div class='clearfix'></div>";
	 	result = result + '<table id="reqListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'  <th>&nbsp;</th>';
		result = result +'	<th>Generated Id</th>';
		result = result +'	<th>Title</th>';
		result = result +'  <th>Posted On<br>(<small class="custom-small">'+createdDate+'</small>)</th>';
	  	result = result +'  <th>Location</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
		$("#addApplicantDiv").empty();
		$("#addApplicantDiv").append(result);
		getRequirementTableList();
	 }else{
		    var result = "";
			result = result +"	<div class='project_inn_mask'></div>"; 
			result = result +"	   <div id='shareCandidate' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Please select at least one Candidate</p>";
			result = result + ' <div id="reasonForInvoiceDiv"/>';
			result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelShareCandidate();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelShareCandidate();"></b>';
			result = result +"	</div>";
			$("#shareEmptypopUp").empty();
			$("#shareEmptypopUp").append(result);
			$("#shareCandidate").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	 }
}

</script>

<script>
function getRequirementTableList(){
	 $("#addApplicantDivLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#reqDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getRequirementListForCandidateBulkSubmission?searchValue="+searchValue;
	 requirementListPagination(url,sourceleadUrl);
	 $("#addApplicantDivLoading").removeAttr("style");
}
</script>

<script>
function requirementListPagination(url,sourceleadUrl){
	
	var colIndex = 0;
    $('#reqListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "id" },
            { "data": "generatedId"},
            { "data": "jobTitle"},
            { "data": "createdDate" },
            { "data": "location"}
        ],
        "columnDefs": [ {
        	"targets": colIndex++,
    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		   result = result +'  <td><input type="radio" value="'+opt.id+'" name="jobRadio" /></td>';
		               return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "generatedId",
              "sortable": true,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
 		  		 result = result +"<td>";
 		  	  	 if(opt.generatedId != '' && opt.generatedId != null){
 		  	  		
 		  		   	 result = result +"<div class='tbldata-dotdot_50 tblminwidth-75' title='"+opt.generatedId+"'>"+opt.generatedId+"</span></div>";
 		  	    	}else{
 		  	  		 result = result +"<div>-----</div>";
 		  	  	 }
 		  		 result = result +"</td>";
 	             return  result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "jobTitle",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "createdDate",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td><div class='tblminwidth-50'>"+opt.reqCreatedDate+"</div></td>";
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "location",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td><div class='tbldata-dotdot-me' title='"+opt.location+"'>"+opt.location+"</div></td>";
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"aaSorting": [[3,'desc']],
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#reqListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#reqDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#reqDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#reqDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		getRequirementTableList();
   	    	}else{
   	    		$("#reqDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#reqDataTableSearch').val(searchData);
    	   }
    	});
}
</script>


<script>
function submitCandidtesToJob(){
	var candidateIds = new Array();
	 for(var e in shareCandidates) {
		 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
			   continue;
		 candidateIds.push(shareCandidates[e]);
	 } 
	 
	 var jobTypeRadio = $('input[name=jobRadio]');
	 var jobId = jobTypeRadio.filter(':checked').val();
	 
	 if(jobId){
		 
	 }else{
		 alert("hiiii");
	 }
	 
}
</script>