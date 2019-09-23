var failedCandidates = new SubmissionFailedCandidatesList();

function SubmissionFailedCandidatesList() {
	
	 this.getFailuredCandidates = function(json){
		 
		 var result = '';
		   	
		   	result = result +'<div class="modal paymentaccount-modal" id="paymentAccountPopup" role="dialog">';
		    result = result +'<div class="modal-dialog modal-lg" style="width: 950px;">';
		    result = result +'<div class="modal-content"  style="height: 550px; overflow: auto;">';
		    result = result +'<div class="modal-header">';
		    result = result +'<button type="button" class="close" onclick="failedCandidates.closeCandiatesSubmitedList();" data-dismiss="modal">&times;</button>';
		    result = result +'<h4 class="modal-title">Failed Candidates List</h4>';
		    result = result +'<span class="text-danger">Failed candidates list who have less profile percentage.</span>';
		    result = result +'</div>';
		        
		    result = result +'<div>';
		    result = result +'<div class="project_wrap_table datatable_filter mt40 overflow_inherint">';
		    result = result +"<table id='failedCandidatesPagination'  width='100%' >"; 
		   	result = result +'<thead>';
		   	result = result +'<tr>';
		    result = result +'<th>Candidate Name</th>';
		    result = result +'<th>Email</th>';   
		    result = result +'<th>Profile Percentage</th>';         
		    result = result +'<th>Status</th>'; 
		   	result = result +'    </tr>';
		   	result = result +'</thead>	';
				
		    result = result +'<tbody>';
		    for(var i=0;i<json.length;i++){
		     	 var opt = json[i];
		     	 result = result +'<tr>';
		         result = result +'<td><div class="tbldata-dotdot_100" title="'+opt.candidateName+'">'+opt.candidateName+'</div></td>';
		         result = result +'<td><div class="tbldata-dotdot-big text-center" title="'+opt.email+'">'+opt.email+'</div></td>';
		         result = result +'<td><div class="tbldata-dotdot_100" title="'+opt.profilePercentage+'">'+opt.profilePercentage+'</div></td>';
		         result = result +'<td><div class="tblminwidth-150">Failed</div></td>';
		         result = result +'</tr>';
		    }
		   
		    result = result +'</tbody>';
		    result = result +'</table>';
			
		    result = result +'</div>';
		    result = result +'</div>';
		    result = result +'</div>';
		    result = result +'</div>';
		    result = result +'</div>';
		    
		    $("#candidateRejectedDiv").empty();  
		    $("#candidateRejectedDiv").append(result);
		    $(".modal.paymentaccount-modal").fadeIn();
		    $(".comMask").fadeIn();
		    
		    
		    
		    $('#failedCandidatesPagination').DataTable({ 
		    	"scrollCollapse": false,
		    	"bPaginate": true,
		    	"bLengthChange": true,
		    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
		    	"bDestroy": true,
		    	"ordering": false,
		    	"aoColumnDefs": [{
		               'bSortable': false,
		               'aTargets': 0
		    		}],
		    	"oLanguage": {
		     	"sSearch": "",
		     	 "sZeroRecords": "", "sEmptyTable": ""
		     	}
		    	
		    });
	 }
	 
	 
	 this.closeCandiatesSubmitedList = function (){
		    $("#candidateRejectedDiv").empty();  
		    $(".modal.paymentaccount-modal").fadeOut();
		    $(".comMask").fadeOut();
	 }
}