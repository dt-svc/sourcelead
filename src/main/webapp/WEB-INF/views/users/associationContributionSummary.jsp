  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateContributionSummary(userId){  
		let searchParams = new URLSearchParams(window.location.search);
		let param = searchParams.get('userCode')
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAllContributionSummaryByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
             var data = json.data;
           
             var result = "";	
            result = result + '<div class="clearfix">';
            result = result + '<div class="table-header clearfix">';
            result = result + '<h4 class="pull-left margin-top0">Contribution</h4>';
            result = result + '<ul class="list-unstyled list-inline pull-right">';
            result = result + '<li><a href="<c:url value="${appPath}/addAssociateContribution?userCode='+param+'&saveType=edit"/>" class="fa fa-plus paddingRight" title="Add">Add</a></li>';
            result = result + '<div class="clearfix"></div>';
            result = result + '</ul>';
            result = result + '</div>';

            result = result + '<div class="styleTables">';
            result = result + "<table id='associateContributionListTable' class='display' cellspacing='0' width='100%' >";
            result = result + '<thead>';
            result = result + '<tr>';
            result = result + '<th>Type</th>';
            result = result + '<th>Description</th>';
            result = result + '<th>Method</th>';
            result = result + '<th>Tax Type</th>';
            result = result + '<th>Schedule Type</th>';
            result = result + '<th>Amount</th>';
            result = result + '<th>Contribution<br>Amount</th>';
            result = result + '<th>Action</th>';
            result = result + '</tr>';
            result = result + '</thead>	';
            result = result + '<tbody>';
            
            for (var i=0; i<data.length; i++)
			{ 
            	 var opt = data[i];
            	   result = result + '<tr>';
            	  
            	   result = result + '<td><div><span>'+opt.contributionType+'</span></div></td>';
                  if(opt.description !=null && opt.description !='' && opt.description !='undefined'){
                	  result = result + '<td><div class="tbldata-dotdot-verybig">'+opt.description+'</div></td>';
                	
                  }else{
                	  result = result + '<td><div class="tbldata-dotdot-verybig">--</div></td>';
                  }
            	  
                   result = result + '<td><div  class="tblminwidth-100">'+opt.contributionMethod+'</div></td>';
                   
                   
                   if(opt.taxType != null && opt.taxType != '' && opt.taxType != 'undefined'){
                    	 result = result + '<td><div  class="tblminwidth-100">'+opt.taxType+'</div></td>';
                     }else{
                    	 result = result + '<td><div  class="tblminwidth-100">--</div></td>';
                     }
                   
                   if(opt.contributionSchedule != null && opt.contributionSchedule != '' && opt.contributionSchedule != 'undefined'){
                  	 result = result + '<td><div  class="tblminwidth-100">'+opt.contributionSchedule+'</div></td>';
                   }else{
                  	 result = result + '<td><div  class="tblminwidth-100">--</div></td>';
                   }
                   
                   if(opt.contributionAmount !=null && opt.contributionAmount !='' && opt.contributionAmount !='undefined'){
                	   if(opt.contributionMethod == "Fixed"){
                		   result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><i class="fa fa-dollar"></i> '+opt.contributionAmount.toFixed(2)+'<small class="fa fa-pencil ml10 pull-right cursorPointer" onClick="showEdit('+opt.id+','+opt.contributionAmount+')"></small></div>';
   	        		       result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
   						   result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5 cursorPointer"  onClick="editConAmount('+opt.id+','+userId+')"></small></div></td>';
                	   }else{
                		   result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'">'+opt.contributionAmount+' <i class="fa fa-percent"></i><small class="fa fa-pencil ml10 pull-right cursorPointer" onClick="showEdit('+opt.id+','+opt.contributionAmount+')"></small></div>';
   	        		       result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
   						   result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5 cursorPointer"  onClick="editConAmount('+opt.id+','+userId+')"></small></div></td>';
                	   }
						
      		    }
      		    else{
      		    	    result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>0<small class="fa fa-pencil ml10 pull-right cursorPointer" onClick="showEdit('+opt.id+',0)"></small></div>';
	        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
						result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5 cursorPointer"  onClick="editConAmount('+opt.id+','+userId+')"></small></div></td>';
					}
                   
                 /*   result = result + '<td><div  class="tblminwidth-100">$ '+opt.contributionAmount+'</div></td>'; */
                   result = result + '<td><div  class="tblminwidth-100">--</div></td>';
                   result = result + '<td><div class="tbldata-dotdot_50">';
                   result = result + '<a onclick="deleteContribution('+opt.id+','+userId+');"><i class="glyphicon glyphicon-trash"></i></a>';
                   result = result + '</div>';
                   result = result + '</td>';
                   result = result + '</tr>';
			}		
            result = result + '</tbody>';
            result = result + '</table>';
            result = result + '</div>';
            result = result + '</div>';
            	
            	
                $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").empty();
                $("#tab-21").empty(); 
                $("#tab-22").empty(); 
                $("#tab-23").empty(); 
                $("#tab-24").empty();
                $("#tab-25").empty();
                $("#tab-24").append(result); 
                associateContributionListPagination();
          },
            
       }); 
  } 
</script>

<script>
function associateContributionListPagination(){  
    $('#associateContributionListTable').DataTable( { 
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
<script>
 function showEdit(id,dedVal){
	 document.getElementById("preDedDiv"+id).style.display="none";
	 document.getElementById("editDedDiv"+id).style.display="block";
	 document.getElementById("preDed"+id).value = dedVal;
}
 </script>
 
 
  <script>
 function editConAmount(id,userId){
	 $("#preDedError"+id).empty();
		var amount = document.getElementById("preDed"+id).value;
		var flag ="valid";
		if(amount==""){
			flag="empty";
		}
		else if(/^[a-zA-Z0-9\.\_]*$/.test(amount) == false || amount.match(/[a-z]/i)) {
			flag = "invalid";
		}
		if(flag == "valid"){
			$("#preDedError").empty();
			 var sourceleadUrl ='${sourceleadUrl}';
			  $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/updateAssociateContributionAmount?${_csrf.parameterName}=${_csrf.token}&associateContributionId="+id+"&contriAmount="+amount,
			        success: function(data) {
			        	document.getElementById("preDedDiv"+id).style.display="block";
			   		 	document.getElementById("editDedDiv"+id).style.display="none";
			   		    associateContributionSummary(userId);
			        },
			    });
		}
		else{
			if(flag == "empty"){
				$("#preDedError"+id).empty().append("please Enter value") ; 
			}
			else{
				$("#preDedError"+id).empty().append("Invalid Number.") ; 
			}
		}
	}
	</script>
 
<script>
function deleteContribution(contrubutionId,userId){
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/deleteAssociateContribution?"+tokenParamName+"="+tokenValue+"&assContriId="+contrubutionId,
		success : function(data) {
			if(data != null)
				associateContributionSummary(userId);
		},
	}); 
}
</script>