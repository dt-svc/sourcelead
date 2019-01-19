<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 
<div class="tablelist-wrap">
			<div class="col-md-12">
				<ul class="list-unstyled mt row">
					<li class="col-md-3" title="Outstanding" onclick="getMyLoansAdvList('Outstanding');">
						<div class="cardModel-widget text-center cursorPointer currentActive" id="outstandingWidget">
							<h5 class="margin-top0">Outstanding</h5>
							<p class="text-muted mb0">
							</p>
						</div>
					</li>
					<li class="col-md-3" title="Completed" onclick="getMyLoansAdvList('Completed');">
						<div class="cardModel-widget text-center cursorPointer" id="completedWidget">
							<h5 class="margin-top0">Completed</h5>
							<p class="text-muted mb0">
							</p>
						</div>
					</li>
					<li class="col-md-3" title="Request" onclick="getMyLoansAdvList('Requested');">
						<div class="cardModel-widget text-center cursorPointer " id="requestWidget">
							<h5 class="margin-top0">Requested</h5>
							<p class="text-muted mb0">
							</p>
						</div>
					</li>
					<li class="col-md-3"><a href="javascript:void(0)"
						class="pull-right" onClick="requestLoanPop();">Request</a></li>
				</ul>
			</div>
			<div class="clearfix"></div>
			<div class="clearfix project_tabs_inn_table overflow_Auto dataFilterNew" id="myLoansAdvListDivId">
			</div>
		</div>
		
		
		
<div class="sideMask"></div>
<div class="modal requestPop">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onClick="closeRequestLoanPop();">×</button>
				<h4 class="modal-title">Request</h4>
			</div>
			<div class="modal-body">
				<div class="form-group clearfix">
					<label class="col-md-3">Type</label>
					<div class="clearfix dropdown-select col-md-9">
						<select>
							<option selected="">Type</option>
							<option>Type1</option>
							<option>Type2</option>
							<option>Type3</option>
							<option>Type4</option>
						</select>
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Name</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Amount</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">No. of Scheduels</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Requested To</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Emi6</label>
					<div class="clearfix dropdown-select col-md-9">
						<input type="text" class="form-control" placeholder="Enter">
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-md-3">Comment</label>
					<div class="clearfix dropdown-select col-md-9">
						<textarea cols="2" class="form-control" placeholder="Enter"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary">Request</button>
				<button type="button" class="btn btn-default"
					onClick="closeRequestLoanPop();">Close</button>
			</div>
		</div>
	</div>
</div>
		
<script>
function getMyLoansAdvList(loanStatus){
	if(loanStatus === "Completed"){
		$("#completedWidget").addClass("currentActive");
		$("#outstandingWidget").removeClass("currentActive");
		$("#requestWidget").removeClass("currentActive");
	}else if(loanStatus ===  "Outstanding"){
		$("#completedWidget").removeClass("currentActive");
		$("#outstandingWidget").addClass("currentActive");
		$("#requestWidget").removeClass("currentActive");
	}else if(loanStatus ===  "Requested"){
		$("#completedWidget").removeClass("currentActive");
		$("#outstandingWidget").removeClass("currentActive");
		$("#requestWidget").addClass("currentActive");
	} 
	
	var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/getMyLoansAdvList?${_csrf.parameterName}=${_csrf.token}&loanStatus="+loanStatus,
		success : function(json) {
			var result = "";
			result = result +'<table width="100%" id="myLoansAdvPagination">';
			result = result +'<thead>';
			result = result +'<tr>';
			result = result +'<th>Name</th>';
			result = result +'<th>Type</th> '; 
			result = result +'<th>Total Amount</th>';
			if(loanStatus === "Outstanding"){
			    result = result +'<th>Paid Amount</th>';
			    result = result +'<th>Due Amount</th>';
		    }	         
		    if(loanStatus === "Requested"){
			    result = result +'<th>Requested To</th>';
			    result = result +'<th>Requested On</th>';
		    } 
		    if(loanStatus === "Outstanding" || loanStatus === "Completed"){
				result = result +'<th>No. of Schedules</th>';
		    } 
		     if(loanStatus === "Outstanding"){
				result = result +'<th>Next Schedule</th>';
		    } 
		    if(loanStatus === "Completed"){
				result = result +'<th>Last Paid On</th>';
		    } 
		     if(loanStatus === "Outstanding" || loanStatus === "Completed"){
				result = result +'<th>Action</th>';
		    } 
		     if(loanStatus === "Requested"){
				result = result +'<th>Status</th>';
		    }        
			result = result +'</tr>';
			result = result +'</thead>';
			result = result +'<tbody>';
			for (var i=0; i<json.length; i++){	
				var report = json[i];
					result = result +'	<tr>';
					result = result +'	   <td><div class="tblminwidth-50" title="'+report.name+'">'+report.name+'</div></td>';
					result = result +'	   <td><div class="tblminwidth-50" title="'+report.type+'">'+report.type+'</div></td>';
					result = result +'	   <td><div class="tblminwidth-50" title="'+report.totalAmount+'"><span class="paddingLeft">&#8377; '+report.totalAmount+'</span></div></td>';
					if(loanStatus === "Outstanding"){ 
						result = result +'		   <td><div class="tblminwidth-50" title="">'+report.paidAmount+'</div></td>';
						result = result +'		   <td><div class="tblminwidth-30" title="">'+report.dueAmount+'</div></td>';
					}
					if(loanStatus === "Requested"){
						result = result +'   		<td><div class="tblminwidth-50" title="">'+report.requestedOn+'</div></td>';
						result = result +'   		<td><div class="tblminwidth-50" title="">'+report.requestedTo+'</div></td>';
					}
					if(loanStatus === "Outstanding" || loanStatus === "Completed"){
						result = result +'   		<td><div class="tblminwidth-50" >'+report.noOfShedules+'</div></td>';
					}
					if(loanStatus === "Outstanding"){ 
						result = result +'   		<td><div class="tblminwidth-50" title="">--</div></td>';
					}
					if(loanStatus === "Completed"){ 
						result = result +'   		<td><div class="tblminwidth-50" title="">--</div></td>';
					}
					if(loanStatus === "Outstanding" || loanStatus === "Completed"){ 
						result = result +'   		<td><div class="tblminwidth-50"><a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a></div></td>';
					}
					if(loanStatus === "Requested"){ 
						result = result +'   		<td><div class="tblminwidth-30 text-center text-danger">'+report.loanStatus+'</div></td>';
					}
					result = result +'	 </tr>';
			}		
			result = result +'</tbody>';
			result = result +'</table>'; 
			$("#myLoansAdvListDivId").empty();
			$("#myLoansAdvListDivId").append(result);
			myLoansAdvPagenation();
		}
	});
}
 
</script>

 <script>
function myLoansAdvPagenation(){  
    $('#myLoansAdvPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>