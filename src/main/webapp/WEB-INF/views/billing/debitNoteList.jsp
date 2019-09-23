<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
$(document).ready(function(){
	getDebitList();
});
</script>

<input type="hidden" id="emptyInvoiceId" name="emptyInvoiceId"/>
 <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
  </div>
  
<section class="content-wraper">
    <section class="content-header clearfix">
    <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	     
      <div class="pull-left">
        <h2>Debit Notes</h2>
      </div>
      
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Debit Note List</div>
    </section>
    
    
    <div class="project_wrap clearfix">
    
    <div class="project_side_wrap_inn_tabs clearfix">
	    <ul class="project-tabs-menu">
		    <li class=""><a href="${appPath}/payablesList">Bill List</a></li>
		    <li class="current"><a href="${appPath}/debitNoteList">Debit Note List</a></li>
		    
	    </ul>
    </div>    
    
      <div class="billing_dashboard noPad">
          <div class="tab-content"> 
            <div id="invoiceLoading"></div>
            <!-- Employee Details -->
            <div class="tab-pane active" id="unbilled">
              <div class="timesheet_out">
                <div class="clearfix">
                

<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="advanceSearchDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetInvoiceFilter();"></a>
      </span>
      
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" href="<c:url value='${appPath}/addDebitNote'/>">+ Add</a>
			</li>
			
				<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openCreditNoteFilterPop();"></a></li>
				
				<!-- <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
						 	<a href="javascript:void(0)" onclick="exportToReceivablePdf();" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportToReceivableExcel();" title="Excel">Export Excel</a>
						</span>
					</div>
				</li> -->
		    
		  <li class=""><input type="text" class="repSearchBar" placeholder='Id/Reference Number/Company Name/Total' title= "Id/Reference Number/Company Name/Total" id='creditDebitDataTableSearch'/><span class="repSearchIcon" onClick="getDebitList();"></span></li>
        </ul>
      </div>     
                
                  <div class="candidate_wrap_table datatable_filter">
	                  <div id="creditDebitListId"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
  </section>
		  <div class="pop_mask"></div>
		  <script>
		    //addcandidate-popup script
		    function createBill(){
		        $('.addcandidate-popup').fadeIn(200);
		        $('.pop_mask').fadeIn(200);
		    };
		       function closeCandidate(){
		        $('.addcandidate-popup').fadeOut(200);
		        $('.pop_mask').fadeOut(200);
		       }
		    </script> 
  
<script>

function getfileUrl(){
	fileUpload("paymentAttachment", "incoiceDocuments", "receivePayment-form");
}

</script> 
<script> 
function remove(){
	$('#paymentAttachment').val("");
}
</script>
<script>
function removeValidate(){
	$('#errPaymentDate').find('p.jquery_form_error_message').remove(); 
}
function removeErr(){
	$('#errPaymethod').find('p.jquery_form_error_message').remove(); 
}
</script>  
  <!-- modal pop -->
<div class="modal-backdrop" style="display: none"></div>
<div id="recvPymntpop" class="modal" style="display: none;">
   <div id="receivePaymentId" ></div>
</div>
<!-- modal pop --> 

  <!-- modal pop -->
<div class="side_mask" style="display: none"></div>
<div class="modal modal_invoice datatable_filter" style="display: none;">
   <div id="linkPopup" ></div>
</div>

<script>
function getDebitList(){
	var creditDebitNoteStatus = [];
	var orglocationId = '${SourceLeadWeb_orgLocationId}';
	var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';
	// for orglocation date format
	var locationdateForList = getOrgDateFormat(locationList);
	var startDate = null;
	var endDate = null;
	var dayWiseType = null;
	var returnStat = true;
	$('input:radio[name=invoiceCustomDays]:checked').each(function() {
		dayWisetype = $(this).val();
		if(dayWisetype == 'custom'){
			startDate = $("#customStartDate").val();
			endDate = $("#customEndDate").val();
			$("#errStartDate").find('p.jquery_form_error_message').remove();
			$("#errEndDate").find('p.jquery_form_error_message').remove();
			if(startDate == null || startDate == '' || startDate == 'undefined'){
				$("input#customStartDate") .after( "<p class='jquery_form_error_message'>Start Date is Required</span>");
				returnStat =  false;
			}
			if(endDate == null || endDate == '' || endDate == 'undefined'){
				$("input#customEndDate") .after( "<p class='jquery_form_error_message'>End Date is Required</span>");
				returnStat =  false;
			}
		}
	});
	
	$('input:checkbox[name=creditNoteFilterStatus]:checked').each(function() 
    {
		creditDebitNoteStatus.push($(this).val());
    });
	
	var status = null;
	
	$('input:radio[name=invoiceStatusDelete]:checked').each(function() {
		status = $(this).val();
	});
	
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
     {
		tags.push($(this).val());
     });
	
	
	var searchValue = $("#creditDebitDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	if(returnStat){
		var url = sourceleadUrl+"/getCreDitNotesList?&creditDebitNoteStatus="+creditDebitNoteStatus+"&dayWiseType="+dayWisetype+"&startDate="+startDate+"&endDate="+endDate+"&noteType=Debit&searchValue="+searchValue+"&orglocationId="+orglocationId+"&tags="+tags+"&status="+status;
		prepareDebitNoteList(url,locationdateForList);
		closeInvoiceFilterPop();
	}
	return returnStat;
}

</script>

<script>
function prepareDebitNoteList(url,locationdateForList){
 	var result = "";
 	
 	result = result + '<table id="creditListTable" class="display" cellspacing="0" width="100%" >';
	result = result + '<thead>';
	result = result +'       <tr>                             ';
	result = result +'         <th>Action</th>                ';
	result = result +'         <th>Date</th>                ';
	result = result +'         <th>ID</th>                    ';
	result = result +'         <th>Reference Number</th>           ';
	result = result +'         <th>Vendor Name</th>               ';
	result = result +'         <th>Bill ID</th>   ';
	result = result +'         <th>Total</th>                 ';
	result = result +'         <th>Balance</th>                   ';
	result = result +'         <th>Status</th>                ';
	result = result +'       </tr>                            ';
	result = result + '</thead>	';
    result = result +"</table>";
    $("#creditDebitListId").empty();  
    $("#creditDebitListId").append(result);
    creditNotePagination(url); 
//     closePopup();
	
}

</script>

<script>
function creditNotePagination(url){
	var colIndex = 0;
    $('#creditListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
        	{ "data": "action" },
            { "data": "date" },
            { "data": "id"},
            { "data": "referenceNumber"},
            { "data": "clientName" },
            { "data": "invoiceId" },
            { "data": "total"},
            { "data": "balance"},
            { "data": "status"}
        ],
        "columnDefs": [{
    	  	"targets": colIndex++,
    	  	"data": "action",
    	  	"sortable": false,
    	  	"render": function(data, type, report, meta) {
    	  		var result = "";
    	  		result = result +'         <td>   ';
    	  		if((report.approverCheckBox == 'Y') && (report.approverUserSplitId.length >0)){
    	  			result = result +'<div class="tbldata-dotdot-vsm">';
    	  			for(var i=0; i<report.approverUserSplitId.length; i++) {
    	  				if((report.approverUserSplitId[i] == '${SourceLeadWeb_user.id}') && (report.creditDebitNoteStatus != "Send") && (report.creditDebitNoteStatus != "Rejected") && (report.status !="0")){
						result = result +'           <span class="clearfix" ><a onclick="acceptCreditDebitNote(\''+report.creditDebitUUID+'\');" title="Accept"><i class="fa fa-check text-success paddingRight"></i></a><a onclick="rejectCreditDebitNote(\''+report.creditDebitUUID+'\');" title="Reject"><i class="paddingLeft fa fa-times text-danger"></i></a></span>';
    	  				}
    	  				}
    	  			if(((report.creditDebitNoteStatus != "Send" ) || (report.creditDebitNoteStatus == "Rejected")) && (report.status !="0")){
    	  			result = result +' <span class="clearfix"><a href="<c:url value="/app/addDebitNote?creditDebitUUID='+report.creditDebitUUID+'&noteType=Debit"/>" <i class="fa fa-edit paddingRight paddingLeft" title="Edit"></i></a></span>';
    				result = result +' <span class="clearfix"><a onClick="deleteCreditDebitNote(\''+report.creditDebitUUID+'\');"><i class="fa fa-trash paddingRight paddingLeft" title="Remove"></i></a></span>';
    				}else if(report.status =="0") {
	 	    			result = result +' <span class="clearfix"><a onClick="reactiveCreditDebitNote(\''+report.creditDebitUUID+'\');"><i class="fa fa-rotate-left ml10" title="Reactive"></i></a></span>';
    	  			}else{
        				result = result +' <span class="clearfix"><a onClick="deleteCreditDebitNote(\''+report.creditDebitUUID+'\');"><i class="fa fa-trash paddingRight paddingLeft" title="Remove"></i></a></span>';
    	  			}
    				
			    	result = result +' </div>';	
			    }else{
    	  			result = result +'<div class="tbldata-dotdot-vsm">';
    	  			if((report.creditDebitNoteStatus == "Draft") && (report.status !="0"))
   				 {
					result = result +' <span class="clearfix"><a href="<c:url value="/app/addDebitNote?creditDebitUUID='+report.creditDebitUUID+'&noteType=Debit"/>" <i class="fa fa-edit paddingRight paddingLeft" title="Edit"></i></a></span>';
   				 	result = result +' <span class="clearfix"><a onClick="deleteCreditDebitNote(\''+report.creditDebitUUID+'\');"><i class="fa fa-trash paddingRight paddingLeft" title="Remove"></i></a></span>';

   				 }else if(report.status !="0"){
    				result = result +' <span class="clearfix"><a onClick="deleteCreditDebitNote(\''+report.creditDebitUUID+'\');"><i class="fa fa-trash paddingRight paddingLeft" title="Remove"></i></a></span>';
 
   				 }else{
	 	    		result = result +' <span class="clearfix"><a onClick="reactiveCreditDebitNote(\''+report.creditDebitUUID+'\');"><i class="fa fa-rotate-left ml10" title="Reactive"></i></a></span>';
 
   				 }
    				 result = result +'</div>  ';	
    	  		}
			      result = result +'         </td> ';
	             return result;
    	  	}
      		},{
	    	  	"targets": colIndex++,
	    	  	"data": "date",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  		result = result +'         <td>   ';
	    	  		if(report.creditDebitNotedate != null && report.creditDebitNotedate !='' && report.creditDebitNotedate !='undefined'){
				      result = result +'<div class="tbldata-dotdot-vsm" title="'+report.creditDebitNotedate+'">'+report.creditDebitNotedate+'</div>';
	    	  		}else{
	    	  			result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';	
	    	  		}
				      result = result +'         </td> ';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "id",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  result = result +'         <td>   ';
            	 if(report.generatedId != null && report.generatedId != '' && report.generatedId != 'undefined'){
            		 
				    //result = result +'<div class="tbldata-dotdot-vsm" title="'+report.referenceNumber+'">'+report.referenceNumber+'</div>';
					result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor1"><a href="<c:url value="/app/viewCreditDebitNote?creditDebitUUID='+report.creditDebitUUID+'&noteType=Debit"/>" title="'+report.generatedId+'">'+report.generatedId+'</a></div>';				

  				     
            	 }else{
            		 result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';
            	 }
  				result = result +'         </td> ';
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "referenceNumber",
            "render": function ( data, type, report, meta ) {
            	var result = "";
            	result = result +'         <td>';
				if(report.referenceNumber !='' && report.referenceNumber != null && report.referenceNumber != 'undefined'){
					result = result +'           <div class="tbldata-dotdot-vsm" title="'+report.referenceNumber+'">'+report.referenceNumber+'</div>  ';	
				}else{
					result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';
				}
				result = result +'         </td>';
            	 return result;
            }
          }, {
              "targets": colIndex++,
              "data": "clientName",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  result = result +'         <td>   ';
            	 if(report.clientName != null && report.clientName != '' && report.clientName != 'undefined'){
            		 
  				      //result = result +'<div class="tbldata-dotdot-vsm" title="'+report.clientName+'"><a href="'+sourceleadUrl+'/clientDashboard?orgRelationUUID='+report.orgRelationUUID+'" title="'+report.clientName+'">'+report.clientName+'</a></div>';
  				     
				      result = result +'<div class="tbldata-dotdot-vsm" title="'+report.clientName+'">'+report.clientName+'</div>';
  				     
            	 }else{
            		 result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';
            	 }
  				result = result +'         </td> ';
	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "invoiceId",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  		result = result +'         <td> ';
	    	  		if(report.invoceId !=null && report.invoceId !='' && report.invoceId !='undefined'){
					result = result +'           <div class="tbldata-dotdot_80" title="'+report.invoceId+'">'+report.invoceId+'</div>';
	    	  		}else{
	    	  		 result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';
	    	  		}
					result = result +'         </td>';
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "total",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  		result = result +'         <td> ';
	    	  		if(report.grandTotal !=null && report.grandTotal !='' && report.grandTotal !='undefined'){
					result = result +'           <div class="tbldata-dotdot_80" title="'+report.grandTotal+'">'+report.grandTotal+'</div>';
	    	  		}else{
	    	  		result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';
	    	  		}
					result = result +'         </td>';
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "balance",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  		result = result +'         <td> ';
	    	  		if(report.grandTotal !=null && report.grandTotal !='' && report.grandTotal !='undefined'){
					result = result +'           <div class="tbldata-dotdot_80" title="'+report.grandTotal+'">'+report.grandTotal+'</div>';
	    	  		}else{
	    	  		result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';	
	    	  		}
					result = result +'         </td>';
	    	  		 return result;
	    	  	}
	    	  },{
                    "targets": colIndex++,
                    "data": "status",
                    "sortable": false,
                    "render": function ( data, type, report, meta ) {
                    	var result = "";
                    
               			result = result +'         <td> ';
                    	result = result +'           <div class="tbldata-dotdot-100" title="'+report.creditDebitNoteStatus+'">'+report.creditDebitNoteStatus+'</div>';
                    	if(report.creditDebitNoteStatus == "Rejected"){
            			 	result = result + '    <i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectReason+'</p></div></i>';
                        	}
                    	result = result +'         </td> ';
                    	 return result;
                    }
                  }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[2,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#creditListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#creditDebitDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getDebitList();
    	   }
    	});
}

</script>


<script>
$('#mypop').click(function(){
		$("#exportPop").toggle();
		$("#filter2ApprovedPop, #filter1ApprovedPop, .action-pop, .approved_pop").slideUp();
 });
 $("html").click(function(){
	$("#exportPop, .ExpPop").hide(200);
 });
 $(".billing_dashboard, .nav-tabs").click(function(){
	$("#exportPop, .ExpPop").hide(200);
 });
 
 $("#exportPop, #mypop, .nav-tabs, .billing_dashboard").click(function(e) {  
     e.stopPropagation();
 });
</script>

<div id="popUp"></div>

<script>
function cancelTermination(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>

<script>
function removePopup(){
	if( document.getElementById("invoicePopupId") != null &&  document.getElementById("invoicePopupId") != 'undefined'){
		var invoicePopupId = document.getElementById("invoicePopupId").value;
		  $("#popupDiv"+invoicePopupId+"").empty(); 
	}
	
}
</script>
<script>
$(".menupop, .pull-right, .positionRelative, .menuWidget, .fa-ellipsis-v, #unbilled").click(function(e) {
	e.stopPropagation();
});
$('html').click(function(){
	$(".menupop").hide();
});
</script>


<script>
function acceptCreditDebitNote(creditDebitUUID){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"	<div class='modal-body'><p>Are you sure you want to Accept ?</p></div>";
	result = result + ' <div id="reasonForInvoiceDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" class="bg-green" value="Accept" onclick="acceptCreditDebitNoteSave(\''+creditDebitUUID+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function acceptCreditDebitNoteSave(creditDebitUUID){
	 $.ajax({
   	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/acceptCreditDebitNote?"+tokenParamName+"="+tokenValue+"&creditDebitUUID="+creditDebitUUID, 
            success: function(result) {
           	 if (result && result.response === "INVALID_DATA") {
	          		    showError(result.responseMessage);
	          		} else if (result.response === "AWKWARD") {
	          		    location.href = "/errorPage";
	          		} else if (result.response === "SUCCESS") {
	          			location.replace(sourceleadUrl+"/debitNoteList");
	          		} else if (result.indexOf('Sign In') !== -1) {
	          			location.replace(sourceleadUrl);
	          		} else {
	          		    showError("Operation failed, please try again");
	          		}
           	 
       },
   });  
}

</script>

<script>
function rejectCreditDebitNote(creditDebitUUID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Do you want to delete this vendor contact?</p>";
	result = result +"		<textarea id='reasonForReject' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Reject Reason' rows='2'></textarea>";
	result = result + ' <div id="reasonForRejectDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Reject' onclick='saveReasonCreditDebitNote(\""+creditDebitUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}


function saveReasonCreditDebitNote(creditDebitUUID){
	var reason = document.getElementById("reasonForReject").value;
	if(reason == null || reason == '' || reason == 'undefined'){
	      
		  $("#reasonForRejectDiv").find('p.jquery_form_error_message').remove();
	  	  $("#reasonForRejectDiv").append("<p class='jquery_form_error_message'>Reason Is Required</p>");
	  	  
		}else{
		  $("#reasonForRejectDiv").find('p.jquery_form_error_message').remove();
		$.ajax({
		   	type: "POST",
		   	url: sourceleadUrl+"/rejectCreditDebitNote?"+tokenParamName+"="+tokenValue+"&creditDebitUUID="+creditDebitUUID+"&reason="+reason,	    	   
		    success: function(result) {
		    	if (result && result.response === "INVALID_DATA") {
          		    showError(result.responseMessage);
          		} else if (result.response === "AWKWARD") {
          		    location.href = "/errorPage";
          		} else if (result.response === "SUCCESS") {
          			$("#popUp").empty();
   	 			    $(".project_inn_mask").fadeOut(200);
   		    	    location.replace(sourceleadUrl+"/debitNoteList");
          		} else if (result.indexOf('Sign In') !== -1) {
          			location.replace(sourceleadUrl);
          		} else {
          		    showError("Operation failed, please try again");
          		}
		    	
		    }
	   });
	}
	  
}
</script>


<script>
function cancelbtn(){
	  $("#recvPymntpop").fadeOut(200);
      $('.modal-backdrop').fadeOut(200).removeClass('in');
}
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
    // Script for tabs next pre button
    var $tabs = $('.tabbable li');

    $('#prevtab').on('click', function() {
        $tabs.filter('.active').prev('li').find('a[data-toggle="tab"]').tab('show');
    });

    $('#nexttab').on('click', function() {
        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
    });
</script> 
<script>
    function showMenu() {
        $('.menupop').toggle();
        
    };
    
    function showDatesDiv(){
    	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
    	var finalDateFormat = getJSDateFormat(locationDate);

    	$('#customStartDate').datepicker({
    	    dateFormat: finalDateFormat,
    	    changeMonth: true,
    		  changeYear: true, 
    	    onSelect: function (selected) {
    	          //var dt = new Date(selected);
    	          var dt = $(this).datepicker('getDate', '+1d');
    	          dt.setDate(dt.getDate() + 1);
    	          $("#customEndDate").datepicker("option", "minDate", dt);
    	      }
    	});

    	$('#customEndDate').datepicker({
    	    dateFormat: finalDateFormat,
    	    changeMonth: true,
    		  changeYear: true, 
    	      onSelect: function (selected) {
    	          //var dt = new Date(selected);
    	          var dt = $(this).datepicker('getDate', '-1d');
    	          dt.setDate(dt.getDate() - 1);
    	          $("#customStartDate").datepicker("option", "maxDate", dt);
    	      }
    	});
    	}
</script>
<script>
function removeMask(){
	 $("#customeAmount").removeAttr("readonly");
	/*  $(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
	        $(this).val($(this).val().replace(/[^0-9\.]/g,''));
	            if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
	                event.preventDefault();
	            }
	        }); */
	        
	 $("#customeAmount").keydown(function (e) {
	        // Allow: backspace, delete, tab, escape, enter and .
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
	             // Allow: Ctrl+A, Command+A
	            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	             // Allow: home, end, left, right, down, up
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });
	 
	 
}
function validMask(){
	  $('#customeAmount').attr("readonly","readonly");  
	  
}

$(document).ready(function() {
    $("#invoiceID1").click(function(){
		$("#invoiceTabOne").slideToggle(0);
		$("#paymentTabOne, #exportPop").slideUp(200);
	});
    $("html").click(function() {
        $("#invoiceTabOne, #exportPop").slideUp(0);
    });
    $("#invoiceTabOne, #invoiceID1, .tab-pane, .billing_dashboard, .nav-tabs").click(function(e) {
        e.stopPropagation();
    });	
    $('.tab-pane, .billing_dashboard, .nav-tabs').click(function(){
    	$("#invoiceTabOne").slideUp(200);
    });
});


$(document).ready(function() {
    $("#paymentID1").click(function(){
		$("#paymentTabOne").slideToggle(0);
		$("#invoiceTabOne, #exportPop").slideUp(200);
	});
    $("html").click(function() {
        //$("#paymentTabOne").slideUp(0);
        $("#paymentTabOne, #exportPop").slideUp(200);
    });
    $("#paymentTabOne, #paymentID1, .tab-pane, .billing_dashboard, .nav-tabs").click(function(e) {
        e.stopPropagation();
    });	
    $('.tab-pane, .billing_dashboard, .nav-tabs').click(function(){
    	$("#paymentTabOne").slideUp(200);
    });
});
</script>

<script>
    //search script
    
    
        function customInvoiceList() {
            var selectVal = $('input[name=invoiceCustomDays]:checked').val();
            if (selectVal == "custom") {
            	var result = '';
            	result  = result + '<div class="col-md-6 paddingLeft" id="errStartDate">';                                                                       
            	result = result +'     <input type="text" class="form-control input-sm calendar" onclick="showDatesDiv();" id="customStartDate" placeholder="Start Date"> ';
            	result = result +'   </div> ';
            	result = result +'   <div class="col-md-6 noPad" id="errEndDate">';
            	result = result +'     <input type="text" class="form-control input-sm calendar" onclick="showDatesDiv();" id="customEndDate" placeholder="End Date">';
            	result = result +'   </div>';
                $(".custom_search").empty().append(result);
                $(".custom_search").show();
            }else {
                $(".custom_search").empty().hide();
            }
        }

        
</script>

<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<script>
function closePopup() {
         $(".custom_search").toggle();
}
</script>

 <!-- Script for Linked Invoice pop -->
    <script>
        $(".sendReminder").click(function(){
        $('.modal.modal_invoice').fadeIn(200);
        $('.side_mask').fadeIn(200);
        });
        
        $(".close, .cancel").click(function(){
        $('.modal.modal_invoice').fadeOut(200);
        $('.side_mask').fadeOut(200);
        });
        function closeLinkInvoice(){
            $('.modal.modal_invoice').fadeOut(200);
            $('.side_mask').fadeOut(200);
        }
    </script>
    <!-- Script for Linked Invoice pop -->	


<script>
	function msgFadein() {
		$("#selector1").fadeIn(200);
        $('#selector1').delay(2000).fadeOut('slow');
	}
</script> 


<script>
function deleteCreditDebitNote(creditDebitUUID){ 
	var result = "";
	$("#popUp").empty();
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";

	result = result +"	<div class='modal-body'><p>Are you sure you want to delete ?</p></div>";
	result = result + ' <div id="deleteCreditDebitNoteDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteCreditDebitNoteMethod(\''+creditDebitUUID+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function deleteCreditDebitNoteMethod(creditDebitUUID){
			 $.ajax({
				   	type: "POST",
				    url: sourceleadUrl+"/deleteCreditDebitNote?"+tokenParamName+"="+tokenValue+"&noteType=Debit&creditDebitUUID="+creditDebitUUID,
		   		    success: function(data) {
		   		    	$("#popUp").empty();
		   			 	$(".project_inn_mask").fadeOut(200);
		   			 	location.replace(sourceleadUrl+"/debitNoteList");	
		   		    }
			     });
}

function reactiveCreditDebitNote(creditDebitUUID){ 
	var result = "";
	$("#popUp").empty();
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";

	result = result +"<div class='modal-body'><p>Are you sure you want to Re-Active ?</p></div>";
	result = result + ' <div id="reactiveCreditDebitNoteDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Reactive" onclick="reactiveCreditDebitMethod(\''+creditDebitUUID+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function reactiveCreditDebitMethod(creditDebitUUID){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/reactiveCreditDebitNote?"+tokenParamName+"="+tokenValue+"&noteType=Debit&creditDebitUUID="+creditDebitUUID,
  		    success: function(data) {
  		    	$("#popUp").empty();
  			 	$(".project_inn_mask").fadeOut(200);
  			 	location.replace(sourceleadUrl+"/debitNoteList");	
  		    }
	     });
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	location.replace(sourceleadUrl+"/creditNoteList");	
}
</script>

<script type="text/javascript">
function cancelExport(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
  $("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
	 
</script> 
<!-- ========================== Filter popup Written By Laxman ===============================  -->
<script>
function closeInvoiceFilterPop(){
	$("#invoiceFilterId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openCreditNoteFilterPop(){
	 $("#invoiceFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function resetInvoiceFilter(){
	$("ul#paymnetFilter > li input:checkbox").prop("checked",false);
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	$("ul#myInvoiceUL > li input:checkbox").prop("checked",false);
	document.getElementById("invoiceDateRadio").checked = true;
	$(".custom_search").empty().hide();
	$("#creditDebitDataTableSearch").val('');
	getDebitList();
	
}
function resetingInvoiceFilter(){
	$("ul#paymnetFilter > li input:checkbox").prop("checked",false);
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	$("ul#myInvoiceUL > li input:checkbox").prop("checked",false);
	document.getElementById("invoiceDateRadio").checked = true;
	$(".custom_search").empty().hide();
	$("#creditDebitDataTableSearch").val('');
}
</script>


     <div class="advancedsearch-mask"></div>
          <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small" id="invoiceFilterId">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeInvoiceFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Active Status</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="statusFilter">
                                    <li class="checkBox positionRelative" ><input type="radio" checked="checked" value="ACTIVE" name="invoiceStatusDelete"><label for="onboard2">Active</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" value="DELETED" name="invoiceStatusDelete"><label for="onboard2">Deleted</label></li>
			                        </ul>
							      </div>
						     </div> 
						     
						     
						  </div>
						</div>
						
						<div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Status</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="statusFilter">
									<li class="checkBox positionRelative" ><input type="checkbox" value="Draft" name="creditNoteFilterStatus"><label for="onboard1">Draft</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" value="Send" name="creditNoteFilterStatus"><label for="onboard2">Send</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" value="Received" name="creditNoteFilterStatus"><label for="onboard1">Received</label></li>
	                               	<li class="checkBox positionRelative" ><input type="checkbox" value="Rejected" name="creditNoteFilterStatus"><label>Rejected</label></li>
	                               <!--  <li class="checkBox positionRelative" ><input type="checkbox"  value="ACCEPTED" name="creditNoteFilterStatus"><label for="onboard2">Accepted</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="REJECTED" name="creditNoteFilterStatus"><label for="onboard2">Rejected</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="LINKED" name="creditNoteFilterStatus"><label for="onboard2">Linked</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="VOID" name="creditNoteFilterStatus"><label for="onboard2">Void</label></li> -->
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
						
						
						<div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Updated Status</strong></label>
								 <div class="clearfix positionRelative">
									<ul class="list-unstyled" id="statusFilter">
                                    <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" checked="checked" id="invoiceDateRadio" value="month" name="invoiceCustomDays"><label for="onboard2">Last 30 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="twomonths" name="invoiceCustomDays"><label for="onboard2">Last 60 Days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="threemonths" name="invoiceCustomDays"><label for="onboard2">Last 90 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="sixmonths" name="invoiceCustomDays"><label for="onboard2">Last 180 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="oneyear" name="invoiceCustomDays"><label for="onboard2">Last 365 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="custom" name="invoiceCustomDays"><label for="onboard2">Custom</label></li>
			                        </ul>
						              <div class="custom_search popCusDate">
						            </div>
							      </div>
						     </div> 
						  </div>
						</div>
						
						<c:if test="${!empty invoiceTagsList}">
						<div class="col-md-4 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myInvoiceInput" onkeyup="myInvoiceFunction()" placeholder="Tags" title="Type in a tag">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myInvoiceUL">
									<c:forEach var="tag" items="${invoiceTagsList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="tags" value ="${tag}" ><label>${tag}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						</c:if>
					  </div>
					  
					  
					  
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="getDebitList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetingInvoiceFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
<script>

function myInvoiceFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myInvoiceInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myInvoicetUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

$(document).keyup(function(e) {
	  if (e.keyCode === 27) {
		  closePop();
	  }
	});
	
	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	


function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	


</script>
	
 <jsp:include page="../masterFooter.jsp" />	