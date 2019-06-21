<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
	<div class="success_pop" >
		<i class="fa fa-check-circle"></i><span></span>
	</div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Candidate Source</h2>
      </div>
       <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo; Candidate Source List</div>
    </section>
    <div class="content-area content-new clearfix timesheet_out">
      <div class="clearfix mt mb">
      <div class="clearfix">
			<!-- <div class="project_tabs projectTabsborder"> -->
				<div id="tab-1">
		          <div class="positionRelative reportHead clearfix">
		            <ul class="pull-right list-unstyled list-inline">
		            <li><a class="newAdBtn mr5" title="Add" id="addPop">+ Add</a></li>
		              <li>
		                <input class="repSearchBar" placeholder="Search" id="candidateSourceTableSearch" type="text">
		                <span class="repSearchIcon" onclick="getCandidateSourceList();"></span>
		              </li>
			        </ul>
		          </div>
		           <div class="candidate_wrap_table datatable_filter" id="candidateSourceParentList"></div>
			</div>
		  <!-- </div> -->
        </div>
      </div>
    </div>
  </section>

<div class="mask"></div>

<div class="popupWrap" id="modalTasktype">
	<div class="popupHead">
		<span id="candidateSourceFormHead">Add Type</span><i class="closePop pull-right popClose"></i>
	</div>
	<form id="addCandidatesourceForm" name="" action="" method="post">
		<div class="popupContent">
			<input type="hidden" id="candidateSourceId" name="id" value="0"/>
			<div class="clearfix mb">
				<label class="noPad">Name <b class="valStar">*</b></label>
				<div class="clearfix">
					<input class="form-control" placeholder="Name" name="name" type="text" id="candidateSourceName" field-name="Name" data-validation="required validate_Space validate_length length1-100">
				</div>
			</div>
			<div class="clearfix mb">
				<label class="noPad">Description</label>
				<div class="clearfix">
					<textarea placeholder="Description" name="description" id="candidateSourceDesc" class="form-control" rows="2" field-name="Description" data-validation="validate_length length0-5000" data-validation-optional="true"></textarea>
				</div>
			</div>
		</div>
		<div class="popupFoot text-right">
			<button type="button" class="slBtn md colorBtn" onclick="saveCandidateSource();">Save</button>
			<button type="button" class="slBtn md popClose" onclick="cancelCandidateSource();">Cancel</button>
		</div>
	</form>
</div>

<div id='deletePopup' class='remove_user_pop remove_user_pop_a'>
	<p> Is Delete Candidate Source?</p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id="confirmDeleteId" type="button" value="Ok" onclick="deleteCandidateSource();" class="pull-left">
		<input class="u_can_color" type="button" value="Cancel" onClick="closeDeletePopup();">
	</b>
</div>

<script type="text/javascript">
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	$(document).ready(function() {
		getCandidateSourceList();
	});
	
	function candidateSourcePagination(url){
		var colIndex = 0;
	    $('#cnadidateSourceTable').DataTable( {
	    	"processing": true,
	       "serverSide": true,
	       "searching": true,
	       "searchHighlight": true,
	       "ajax": $.fn.dataTable.pipeline( {
	           url: url,
	           method: 'POST',
	           pages: 20
	       }),
	       "columns": [
	    	   {"data": "id"},
	           {"data": "name"},
	           {"data": "description"},
	           {"data": "actions" }
	       	],
		       "columnDefs": [
	    	   {
		            "targets": colIndex++,
		            "data": "id",
		            "visible": false,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.id != null && opt.id != '' && opt.id != 'undefined') {
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm" title="'+opt.id+'">'+opt.id+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			           		return result;
		             }
		        },
	            {
		            "targets": colIndex++,
		            "data": "name",
		            "sortable": true,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.name != null && opt.name != '' && opt.name != 'undefined') {
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm" title="'+opt.name+'">'+opt.name+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			           		return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "description",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.description != null && opt.description != '' && opt.description != 'undefined'){
		     	        	result = result + '<td><div  class="tbldata-dotdot-big500" title="'+opt.description+'">'+opt.description+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "actions",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            	var result = '<div class=" widthIcon widthIcon-three">';
			            	result +='	<div class="text-center">';
			            	result +='		<a href="javascript:void(0)" title="Edit Candidate Source" onclick="editCandidateSource(\'' + opt.id + '\')">';
			            	result +='		<i class="fa fa-pencil"></i>';
			            	result +='		</a>';
			            	result +='	</div>';
			            	result +='	<div class="text-center">';
			            	result +='		<a href="javascript:void(0)" title="Delete Candidate Source" onclick="showDeleteCandidateSource(' + opt.id + ')">';
			            	result +='		<i class="fa fa-trash"></i>';
			            	result +='		</a>';
			            	result +='	</div>';
		            	result +='</div>';
	           	        return result;
		             }
		        }
	           ],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"createdRow": function ( row, data, index ) {
		    		row.setAttribute('id',"candidateSource_"+data.id);
	    	    },
		    	"initComplete": function( settings, json ) {
		    		 $('#candidateSourceTableSearch').removeAttr('disabled');
		    	},
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     },
		     drawCallback: function(){
	   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
	   	    	setTimeout(function () {
	   	  		var body = $( table.table().body() );
	   	 	        body.unhighlight();
	   	 	        body.highlight($("#candidateSourceTableSearch").val());   
	   	 			
	   	 			}, 1000);
	   	      });
	   	   	}
	   });
	   $("#cnadidateSourceTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	   $("input[type=search]").hide();
	}
</script>
<script>
var atsUrl = '${SourceLeadWeb_atsUrl}';
$(document).ready(function() {
	$("#addPop").click(function() {
		$("#candidateSourceFormHead").html("Add Type");
		$(".mask, #modalTasktype").fadeIn();
	});
	$(".popClose").click(function() {
		cancelCandidateSource();
		
	});
	$("#candidateSourceTableSearch").keyup(function(e) {
		if(e.which === 13) {
			getCandidateSourceList();
		}
	});
	
});
function cancelCandidateSource() {
	$("#candidateSourceId").val(0);
	if(document.getElementById("addCandidatesourceForm") !== null)
		document.getElementById("addCandidatesourceForm").reset();
	$(".mask, #modalTasktype").fadeOut();
	$(".location-alert").fadeOut();
}

function getCandidateSourceList() {
	$("#candidateSourceParentList").empty();
	var text = "<table style='width: 100%;' id='cnadidateSourceTable'>";
	text += " <thead>";
	text += "    <tr>";
	text += "      	<th>Id</th>";
	text += "      	<th>Type</th>";
	text += "      	<th>Description</th>";
	text += "      	<th>Action</th>";
	text += "    </tr>";
	text += "  </thead>";
	text += "  <tbody id='candidateSourceList'>";
	text += "  </tbody>";
	text += "</table>";
	$("#candidateSourceParentList").append(text);
	candidateSourcePagination(atsUrl + "/getCandidateSourceList?searchValue=" + $("#candidateSourceTableSearch").val());
}
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
        };

function saveCandidateSource() {
	if ($('#addCandidatesourceForm').validate(false, validationSettings)){
		var formData = getFormData("addCandidatesourceForm");
		$.post(atsUrl + "/saveCandidateSource", formData, function(response) {
			if (response && (response.response === "INVALID_DATA" || response.response === "DUPLICATE_NAME")) {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object > 0) {
					$(".success_pop span").html("Candidate Source saved successfully...");
					$(".success_pop").show();
					cancelCandidateSource();
					setTimeout(function() {
						getCandidateSourceList();
						$(".success_pop").hide();
			 		}, 1000);
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
		return true;
		
	  }else{
		return false;
	  } 
}
$('body').on('blur', '#addCandidatesourceForm', function() {
	$("#addCandidatesourceForm").showHelpOnFocus();
	$("#addCandidatesourceForm").validateOnBlur(false, validationSettings);  
});

function editCandidateSource(id){
	$.ajax({
		type : "POST",
 		url : atsUrl+ "/getCandidateSourceById?candidateSourceId="+id,
		success : function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var data = response.object;
				$("#candidateSourceFormHead").html("Edit Type");
				$("#candidateSourceId").val(data.id);
				$("#candidateSourceName").val(data.name);
				$("#candidateSourceDesc").val(data.description);
				$(".mask, #modalTasktype").fadeIn();
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(atsUrl);
			} else {
				showError("Operation failed, please try again");
			}
		}
	});
}

function deleteCandidateSource() {
	var id = $("#confirmDeleteId").data("id");
	var status = $("#confirmDeleteId").data("status");
	$.post(atsUrl + "/deleteCandidateSourceById?candidateSourceId="+ id + "&status=" + status, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				closeDeletePopup();
				location.reload();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(atsUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

 function showDeleteCandidateSource(id, status) {
	$("#confirmDeleteId").data("id", id);
	$("#confirmDeleteId").data("status", status);
	$("#deletePopup").slideDown(200);
	$(".mask").fadeIn(200);
}  
function closeDeletePopup() {
	$("#deletePopup").slideUp(200);
	$(".mask").fadeOut(200);
}
</script>
<jsp:include page="../masterFooter.jsp" />	