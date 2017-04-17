<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
<!-- Content Area -->

<script type="text/javascript">
$(document).ready(function(){
   getTemplateList('pending');
});
</script>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>List by status</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; List by status</div>
</section>

<section class="contrHead"> 
<ul> 
<li title="Draft"><a href="#" id="draft" class="draft" onClick="mainTabEnable(this);getTemplateList('draft');">Draft</a></li>
<li title="Waiting for Approval"><a href="#" id="approve"  onClick="mainTabEnable(this);"><i class="fa fa-clock-o fa-2"></i>Approve</a></li>
<li title="Waiting to be sign"><a href="#" id="sign" onClick="mainTabEnable(this);getTemplateList('Waiting to be signed');"><i class="fa fa-clock-o fa-2"></i>Sign</a></li>
<li title="Waiting for Verify"><a href="#" id="verify" onClick="mainTabEnable(this);getTemplateList('Waiting To Be Verify');"><i class="fa fa-clock-o fa-2"></i>Verify</a></li>
<li title="Finalised"><a href="#" id="finalised"  onClick="mainTabEnable(this);">Finalised</a></li>
<li title="Expired"><a href="#" id="expired" onClick="mainTabEnable(this);">Expired</a></li>
<li title="Rejected"><a href="#" id="rejected"  onClick="mainTabEnable(this);">Rejected</a></li>

<div class="conBtn">
  <span>Add<i class="fa fa-caret-down"></i></span>
  <ul>  
	  <li><a href="<c:url value="/addNewContract" />"><i class="fa fa-caret-right"></i>New Contract</a></li>
	  <li><a href="<c:url value="/addExistingContract" />"><i class="fa fa-caret-right"></i>Existing Contract</a></li>
  </ul>
</div>
<div class="clearfix"></div>
</ul>
</section>
	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="timesheet_head">
		<div class="ContrBut">
		    <input type="hidden" name="tabType" id="tabType" value='Vendor'>
			<a href="#" id="Vendor" class="active tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Vendors</a>
			<a href="#" id="Client" class="tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Clients</a>
		</div>
		<ul>
			<li id="export-pop"><i class="glyphicon glyphicon-export"></i>
				<div class="export_pop">
					<ul>
						<li>PDF<i class="fa fa-file-pdf-o"></i></li>
						<li>Excel<i class="fa fa-file-excel-o"></i></li>
						<li>Word<i class="fa fa-file-word-o"></i></li>
					</ul>
				</div>
			</li>	
			<div class="clearfix"></div>
		</ul>
		<div class="clearfix"></div>
            
	</div>
    <div class="project_wrap_table">
    <div id="contractsList"></div>
	</div>
	
</div>
  
</div>
</section>
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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
<script>
    $("#uasrch").selectpicker('refresh');
	
// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").show();
	});
    $("html").click(function() {
        $(".export_pop").hide();
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});		
</script>

<script type="text/javascript">
function getTemplateList(contractSubmissionType){
	var tabType = $("#tabType").val();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getAllContractsByOrgLocationIdandStatus?${_csrf.parameterName}=${_csrf.token}&contractSubmissionType="+contractSubmissionType+"&tabType="+tabType,       
       success :function(data) {
      	preparedContractList(data);
      },
  }); 
}
</script>

<script>
function preparedContractList(data){
	var sourceleadUrl ='${sourceleadUrl}';
	var result = '';
	result = result +  '<table id="contractListTable" class="display" cellspacing="0" width="100%" >';
	result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '    <th>Contract Title</th>';
	result = result + '    <th>Owner</th>';
	result = result + '    <th>Start date</th>';
	result = result + '    <th>End date</th>'; 
	result = result + '    <th>Type Of the Contract</th>';
	result = result + '    <th>Vendor Contact</th>';
	result = result + '    <th>Action</th>';
	result = result + '   </tr>';
	result = result + '	</thead>';
	result = result + '	<tbody>';
	var json = JSON.parse(data);
    for (var i = 0; i < json.length; i++) {
    	var report= json[i];
    	result = result + '<tr id="row'+report.id+'">';
        result = result + '<td>';
           	result = result + '<div><span>'+report.contractTitle+'</span></div>';
         result = result + '</td>';
    	 result = result + '<td>';
       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
         result = result + '</td>';
         result = result + '<td>';
         result = result + '<div><span>'+report.startDateStr+'</span></div>';
         result = result + '</td>';
         result = result + '<td>';
         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
        	 result = result + '<div><span>'+report.endDateStr+'</span></div>';
         }else{
        	 result = result + '<div><span>--</span></div>';
         }
         result = result + '<td>';
         result = result + '<div><span>'+report.contractType+'</span></div>';
         result = result + '</td>';
         result = result + '<td>';
         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
         result = result + '</td>';
         result = result + '</td>';
         result = result + '<td>';//Waiting to be signed
     	  if(report.parentId != null && report.parentId != '' && report.parentId != 'undefined'){
         	 /* result = result + '<div onClick="verify('+report.id+'\,\''+report.orgRelations.id+'\')";><span>Verify</span></div>'; */
     		 result = result + '<div  id="removeStatus'+report.id+'"><a class="text-danger fa fa-trash" title="verify" onclick="removeRecord('+report.id+');"></a></div>';  
          }else{
         	 result = result + '<div><span>--</span></div>';
          }
     	 result = result + '</td>';
        result = result + '</tr>';
    }
	result = result + '	</tbody>';
	result = result + '</table>';
	$("#contractsList").empty();
    $("#contractsList").append(result);
    listContractsPagination();	
	
}
</script>

<script>
function removeRecord(recordId){
	var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/updateContractStatus?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId,       
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<b >Send For Verification</b>");
       },
   }); 
}

</script>


<script>
function listContractsPagination(){
	$('#contractListTable').DataTable({ 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
function tabEnable(local){
	$('.tabActivation').each(function() {
		 var localId = this.id;
		 $("#"+localId).removeClass("active");
    });
	var tabId = local.id;
	$("#tabType").val(tabId);
	$("#"+tabId).addClass("active");
}

</script>

<script>
function mainTabEnable(local){
   
	var tabId = local.id;
    if(tabId == 'draft'){
	$("#draft").addClass("draft"); 
    $("#approve").removeClass("wApp");
    $("#sign").removeClass("wSig");
    $("#verify").removeClass("wVer");
    $("#finalised").removeClass("fin");
    $("#expired").removeClass("exp");
    $("#rejected").removeClass("rej");
    }
    if(tabId == 'approve'){
    	$("#draft").removeClass("draft"); 
        $("#approve").addClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'sign'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").addClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("expi");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'verify'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").addClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'finalised'){  
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").addClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'expired'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").addClass("exp");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'rejected'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").addClass("rej");
        }
	
}

</script>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->




