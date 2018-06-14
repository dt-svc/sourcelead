<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/resources/css/style.css" />
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<script src="/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<script>
function submitCandidatesToJob(){
	
		$('.action-pop').slideUp();
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		var result = "";
		result = result + '<h3>Candidate List</h3>';
		result = result + '<div id="candidatesListLoading"></div>';
	    result = result +" <div class='mr pull-left'>";
		result = result +" <button type='button' onclick='jobAdvSearch()' class='btn btn-primary btn-sm'>Advanced Search&nbsp;&nbsp;<i class='fa fa-search'></i></button>";
		result = result +" <button type='button' onclick='clearJobAdvSearch();getCandidatesTableList()' class='btn btn-primary btn-sm' title='Refresh'><i class='fa fa-refresh'></i></button>";
		result = result +"</div>"; 
		result = result +" <div class='pull-right'>";
		result = result +" <div class='mr pull-left'>";
		result = result +" <input type='button' value='Submit To Job' onclick='submitCandidtesToJob()' class='btn btn-primary btn-sm'>";
		result = result +"</div>";
		result = result +" <div class='dynamic_search pull-left'>";
	 	result = result +" <input type='text' placeholder='Search' id='candDataTableSearch'/>";
	 	result = result +" <span class='cursorPointer faSearch' title='Search'><i class='fa fa-search' onClick='getCandidatesTableList();'></i></span>";
	 	result = result +"</div>";
	 	result = result +"</div>";
	 	result = result +"<div class='clearfix'></div>";
	 	result = result + '<table id="candListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th class="text-center"><input type="checkbox" id="sharedcheckbox" onclick="toggleShareCandidates(this);"/></th>'; 
		result = result +'	<th>Candidate Name</th>';
		result = result +'	<th>Email</th>';
		result = result +'	<th>Primary Skill</th>';
		result = result +'	<th>Employment Type</th>';
		result = result +'  <th>Created Date<br>(<small class="custom-small">YYYY-MM-DD</small>)</th>';
	  	result = result +'  <th>Location</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
		$("#addJobApplicantsDiv").empty();
		$("#addJobApplicantsDiv").append(result);
		getCandidatesTableList();
	
}

</script>

<script>
function toggleShareCandidates(source){
	  var checkboxes = document.querySelectorAll('input[class="contactscheckbox"]');
	  if($(source).is(":checked"))
		  $('input[class="contactscheckbox"]').prop('checked', 'checked');
	  else 
		  $('input[class="contactscheckbox"]').removeProp('checked');
	  
	  $('input[class="contactscheckbox"]').each(function(i) {
		  if($(this).is(":checked")) {
		      shareCandidates[$(this).data("id")] = $(this).val();
		  } else {
			  shareCandidates[$(this).data("id")] = "";
		  }
	  });
}

function checkOrUncheckParent(tableId, selectedItems, checkboxId) {
	$("#"+checkboxId).removeProp("checked");
	   var count = 0;
	   var data = $("#"+tableId).DataTable().data();
	 for(var e in selectedItems) {
	   if(!e || !selectedItems[e] || selectedItems[e] === "")
		   continue;
	   
	   for(var f in data) {
 	   if(e === data[f].id+"") {
 		count ++;
 	   }
		}
	 }
	 if(count == data.count()) {
		$("#"+checkboxId).prop("checked", "checked");
	 }
}
</script>
<script>
var shareCandidates = {};
function checkShareCandidates(elem) {
	if($(elem).is(":checked")) {
		shareCandidates[$(elem).data("id")] = $(elem).val();
	} else{
		$("#sharedcheckbox").removeProp('checked');
		shareCandidates[$(elem).data("id")] = "";
	}
}
</script>

<script>
function getCandidatesTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#candDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getCandidatesListForJobBulkSubmission?searchValue="+searchValue;
	 candidatesListPagination(url,sourceleadUrl);
}
</script>

<script>
function candidatesListPagination(url,sourceleadUrl){
	
	var colIndex = 0;
    $('#candListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 3
        }),
        "columns": [
            { "data": "action" },
            { "data": "candidateName"},
            { "data": "email"},
            { "data": "primarySkill" },
            { "data": "employementType" },
            { "data": "createdDate" },
            { "data": "location"}
        ],
        "columnDefs": [ {
        	"targets": colIndex++,
    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 var shareflag = false;
	        		 for(var e in shareCandidates) {
   	     	    	   if(!e || !shareCandidates[e] || shareCandidates[e] === "")
   	     				   continue;
   	     	    	   if(e === opt.id+"") {
   	     	    		    if(!$("#sharedcheckbox").is(":checked"))
   	     	    		 	checkOrUncheckParent("candListTable",shareCandidates, "sharedcheckbox"); 
   	     	    		    shareflag = true;
   	     	    	   	break;
   	     	    	   }
   	        		 }
	        		 if(!shareflag)
   	        			$("#sharedcheckbox").removeAttr('checked');
   	        		 
	        			result = result +'  <td><input'+(shareflag ? " checked='checked'" : "")+' class="contactscheckbox" name="sharedcheckbox"  type="checkbox" id="'+opt.id+'" data-id="'+opt.id+'" value="'+opt.id+'" onchange="checkShareCandidates(this)"/></td>';
    	  		 return result; 
	    	  	}
	      },{
		    	  "targets": colIndex++,
	              "data": "candidateName",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div class='tbldata-dotdot_140'><span class='pull-left' title='"+opt.candidateName+"'>"+opt.candidateName+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "email",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div class='tbldata-dotdot_140'><span class='pull-left' title='"+opt.email+"'>"+opt.email+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "createdDate",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.primarySkill != null && opt.primarySkill != '' && opt.primarySkill != "undefined"){
	            	    	 result = result +"<td><div class='tbldata-dotdot_140' title='"+opt.primarySkill+"'>"+opt.primarySkill+"</div></td>";
	            	    }else{
	            	    	 result = result +"<div>----</div>";
	            	    }
	            	   
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "primarySkill",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.employementType != null && opt.employementType != '' && opt.employementType != "undefined"){
	            	    	 result = result +"<td><div class='tblminwidth-50' title='"+opt.employementType+"'>"+opt.employementType+"</div></td>";
	            	    }else{
	            	    	 result = result +"<div>----</div>";
	            	    }
	            	   
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "primarySkill",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td><div class='tblminwidth-75' title='"+opt.createdDate+"'>"+opt.createdDate+"</div></td>";
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "location",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.location != null && opt.location != '' && opt.location != "undefined"){
	            	    	result = result +"<td><div class='tbldata-dotdot-me' title='"+opt.location+"'>"+opt.location+"</div></td>";
	            	    }else if(opt.country != null && opt.country != '' && opt.country != "undefined"){
	            	    	result = result +"<td><div class='tbldata-dotdot-me' title='"+opt.country+"'>"+opt.country+"</div></td>";
	            	    }else{
	            	    	result = result +"<div>----</div>";
	            	    }
	            	    
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"aaSorting": [[5,'desc']],
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#candListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#candDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#candDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#candDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		getCandidatesTableList();
   	    	}else{
   	    		$("#candDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#candDataTableSearch').val(searchData);
    	   }
    	});
}
</script>
