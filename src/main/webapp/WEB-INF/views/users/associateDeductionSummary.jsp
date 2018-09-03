  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 <script>
function associateDeductionSummary(userId){
	let searchParams = new URLSearchParams(window.location.search);
	let param = searchParams.get('userCode')
	$.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAllDeductionSummaryByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
             var data = json.data;
             var result = "";	
             result = result + '<div id="associateDeductionSummaryDiv">';
            result = result + '<div class="clearfix">';
            result = result + '<div class="table-header clearfix">';
            result = result + '<h4 class="pull-left margin-top0">Deductions</h4>';
            result = result + '<ul class="list-unstyled list-inline pull-right">';
            result = result + '<li><a href="<c:url value="${appPath}/addAssociateDeduction?userCode='+param+'&saveType=edit"/>" class="fa fa-plus paddingRight" title="Add">Add</a></li>';
            result = result + '<div class="clearfix"></div>';
            result = result + '</ul>';
            result = result + '</div>';

            result = result + '<div class="styleTables">';
            result = result + "<table id='associateDeductonListTable' class='display' cellspacing='0' width='100%' >";
            result = result + '<thead>';
            result = result + '<tr>';
            result = result + '<th>Type</th>';
            result = result + '<th>Description</th>';
            result = result + '<th>Method</th>';
            result = result + '<th>Tax Type</th>';
            result = result + '<th>Schedule Type</th>';
            result = result + '<th>Amount</th>';
            result = result + '<th>Deducted<br>Amount</th>';
            result = result + '<th>Action</th>';
            result = result + '</tr>';
            result = result + '</thead>	';
            result = result + '<tbody>';
            
            for (var i=0; i<data.length; i++)
			{ 
            	 var opt = data[i];
            	   result = result + '<tr>';
            	   result = result + '<td><div><span>'+opt.deductionType+'</span></div></td>';
                   if(opt.description != null && opt.description != '' && opt.description != 'undefined'){
                	   result = result + '<td><div class="tbldata-dotdot-verybig">'+opt.description+'</div></td>';
                   }else{
                	   result = result + '<td><div class="tbldata-dotdot-verybig">--</div></td>';
                   }
                   result = result + '<td><div  class="tblminwidth-100">'+opt.method+'</div></td>';
                   
                   if(opt.dedTaxType != null && opt.dedTaxType != '' && opt.dedTaxType != 'undefined'){
                  	 result = result + '<td><div  class="tblminwidth-100">'+opt.dedTaxType+'</div></td>';
                   }else{
                  	 result = result + '<td><div  class="tblminwidth-100">--</div></td>';
                   }
                   
                   if(opt.dedSchedule != null && opt.dedSchedule != '' && opt.dedSchedule != 'undefined'){
                    	 result = result + '<td><div  class="tblminwidth-100">'+opt.dedSchedule+'</div></td>';
                     }else{
                    	 result = result + '<td><div  class="tblminwidth-100">--</div></td>';
                     }
                   
                   if(opt.amount !=null && opt.amount !='' && opt.amount !='undefined'){
                	   if(opt.method == "Fixed"){
						result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><i class="fa fa-dollar"></i> '+opt.amount.toFixed(2)+'<small class="fa fa-pencil ml10 pull-right cursorPointer" onClick="showEdit('+opt.id+','+opt.amount+')"></small></div>';
	        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
						result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5 cursorPointer"  onClick="editDedAmount('+opt.id+','+userId+')" minlength="1" maxlength="10"></small></div></td>';
                	   }else{
                		result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'">'+opt.amount+' <i class="fa fa-percent"></i><small class="fa fa-pencil ml10 pull-right cursorPointer" onClick="showEdit('+opt.id+','+opt.amount+')"></small></div>';
   	        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
   						result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5 cursorPointer"  onClick="editDedAmount('+opt.id+','+userId+')"></small></div></td>';  
                	   }
                }
       		    else{
       		    	result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>0<small class="fa fa-pencil ml10 pull-right cursorPointer" onClick="showEdit('+opt.id+',0)"></small></div>';
	        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
						result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.dedTaxType+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"><small class="fa fa-floppy-o ml10 pull-right mt5 cursorPointer"  onClick="editDedAmount('+opt.id+','+userId+')"></small></div></td>';
					}
                 /*   result = result + '<td><div  class="tblminwidth-100">$ '+opt.amount+'</div></td>'; */
                 
                   result = result + '<td><div  class="tblminwidth-100">--</div></td>';
                   result = result + '<td><div class="tbldata-dotdot_50">';
                   result = result + '<a><i class="glyphicon glyphicon-trash" onclick="deleteDed('+opt.id+','+userId+');"></i></a>';
                   result = result + '</div>';
                   result = result + '</td>';
                   result = result + '</tr>';
			}		
            result = result + '</tbody>';
            result = result + '</table>';
            result = result + '</div>';
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
                $("#tab-25").empty();
                $("#tab-23").append(result); 
                associateDeductonListPagination();
                
          },
            
       }); 
  } 
</script>
 
  <script>
function associateDeductonListPagination(){  
    $('#associateDeductonListTable').DataTable( { 
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
 function editDedAmount(id,userId){
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
			        url: sourceleadUrl+"/updateAssociateDeductionAmount?${_csrf.parameterName}=${_csrf.token}&associateDeductionId="+id+"&dedAmount="+amount,
			        success: function(data) {
			        	document.getElementById("preDedDiv"+id).style.display="block";
			   		 	document.getElementById("editDedDiv"+id).style.display="none";
			   			associateDeductionSummary(userId);
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
function deleteDed(assDedId,userId){
 	$.ajax({
				type : "POST",
				url : sourceleadUrl+"/deleteAssociateDeduction?"+tokenParamName+"="+tokenValue+"&assDedId="+assDedId,
				success : function(data) {
					if(data != null && data != null)
					associateDeductionSummary(userId);
				},
			}); 
         
	 
}
</script>

<!--  <div id="tab-2" class="tabsMenu_tabs_inn clearfix"> -->
		
		<!--   </div> -->