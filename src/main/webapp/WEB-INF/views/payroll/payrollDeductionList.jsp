<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script>
$(document).ready(function(){
   getAllDeductionType();
});
</script>



<!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Payroll Deduction</h2>
  </div>
  
  <div class="success_pop" id="selector2" style="display: none;">
   <i class="fa fa-check-circle"></i><span id="deductionDeleteMsgDiv"></span>
 </div>
 
         <div class="pull-right brud-crum">
                <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo;Payroll Deduction
				</div>
</section>
 

 
  <div class="tablelist-wrap clearfix">
   
    <div class="table-header clearfix">
      <ul class="list-unstyled list-inline">
        <li><a href="<c:url value='${appPath}/addDeduction'/>" class="addcon-pop"><i class="fa fa-plus paddingRight"></i>Add</a></li>
        <li>
		 <div class="dynamic_search">
			<input type="text" placeholder="Search" id='dataTableSearch'/>
			<i class="fa fa-search" onclick="getAllDeductionType();"></i>
		 </div>
	   </li>
        <div class="clearfix"></div>
      </ul>
    </div>
    
<div class="project_wrap_table datatable_filter">
	<div id="deductionTable"></div>
  </div>


  </section>
  
<script>
  function getAllDeductionType(){
	  var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/findAllPayrollDeduction?searchValue="+searchValue;
	    prepareAllDeductionTypeList(url);
  }
</script>

<script>
function prepareAllDeductionTypeList(url){
	$("#deductionTable").empty();
 	    var result = "";
 	    /* result = result +" <div class='modal-header'>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='deductionTypePaginagtion' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
		result = result + '<th>Type</th>';
		result = result + '<th>Description</th>';
		result = result + '<th>Tax Type</th>';
		result = result + '<th>Schedule Type</th>';
		result = result + '<th>Method</th>';
		result = result + '<th>Amount</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#deductionTable").append(result);
        deductionTypepagination(url); 
}

</script>

<script>
function deductionTypepagination(url){
	var colIndex = 0;
    $('#deductionTypePaginagtion').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "payrollDeductionType" },
            { "data": "description"},
            { "data": "taxType"},
            { "data": "scheduleType"},
            { "data": "payrollDeductionMethod"},
            { "data": "deductionAmount" },
            { "data": "Action"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "payrollDeductionType",
	    	  	"sortable": true,
	    	  	"render": function(data, type, opt, meta) {
	    	  		        var result = "";
							result = result + ' <td><div class="tblminwidth-75"><span>'+opt.payrollDeductionType+'</span></div></td>';
		                     return result;
	    	  	}
	      },{
			    	  "targets": colIndex++,
		              "data": "description",
		              "sortable": false,
		              "render": function ( data, type, opt, meta ) {
		            	  var result = "";
		            	  if(opt.description !=null && opt.description !='' && opt.description !='undefined'){
								result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.description+'</div></td>';
							}else{
								result = result + ' <td><div>--</div></td>';
							}
			              return result;
		              		
		              }
			          },{
				    	  "targets": colIndex++,
			              "data": "taxType",
			              "sortable": false,
			              "render": function ( data, type, opt, meta ) {
			            	  var result = "";
			            	  if(opt.taxType !=null && opt.taxType !='' && opt.taxType !='undefined'){
									result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.taxType+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
				              return result;
			              		
			              }
				          },{
					    	  "targets": colIndex++,
				              "data": "scheduleType",
				              "sortable": false,
				              "render": function ( data, type, opt, meta ) {
				            	  var result = "";
				            	  if(opt.payrollScheduled !=null && opt.payrollScheduled !='' && opt.payrollScheduled !='undefined'){
										result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.payrollScheduled+'</div></td>';
									}else{
										result = result + ' <td><div>--</div></td>';
									}
					              return result;
				              		
				              }
					          },{
            "targets": colIndex++,
            "data": "payrollDeductionMethod",
            "sortable": false,
            "render": function ( data, type, opt, meta ) { 
          			  var result = "";
      	 			   if(opt.payrollDeductionMethod !=null && opt.payrollDeductionMethod !='' && opt.payrollDeductionMethod !='undefined'){ 
					 result = result + ' <td><div  class="tblminwidth-100">'+opt.payrollDeductionMethod+'</div></td>';
				   }  else{ 
					 result = result + ' <td><div>--</div></td>';
				   } 
            return result;
            }
          }, {
              "targets": colIndex++,
              "data": "deductionAmount",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.payrollDeductionMethod == "Fixed"){
            		  result = result + ' <td><div  class="tblminwidth-100">$ '+opt.deductionAmount.toFixed(2)+'</div></td>';
            	  }else{
            		  result = result + ' <td><div  class="tblminwidth-100">'+opt.deductionAmount+' %</div></td>';
            	  }
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "Action",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		result = result + '<td><div class="tbldata-dotdot_50">';
                		result = result + '<a href="'+sourceleadUrl+'/editDeductionType?deductionUUID='+opt.deductionUUID+'" class="text-primary"><i class="fa fa-pencil-square-o mr5"></i></a>';
                		if(opt.status == 1){
                			result = result + '<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash" onclick="deleteDeductionType('+opt.id+');"></i></a>';
                		}else{
                			result = result + '<span title="Reactive" class="paddingLeft"><i class="fa fa-repeat text-primary cursorPointer" onclick = "reactiveDeduction('+opt.id+');"></i></span>';
                		}
                		result = result + '</div>';
                		result = result + '</td>';
    	              return result;
                }
          } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#deductionTypePaginagtion").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getAllDeductionType();
    	   }
    	});
}
</script>

<div id="deleteDeductionPopUpDiv"></div> 

<script type="text/javascript">
function deleteDeductionType(deductionId){
	$("#deleteDeductionPopUpDiv").empty();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete.!</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedDeductionType("+deductionId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteDeductionPopUp();'></b>";
	result = result +"	</div>";
	$("#deleteDeductionPopUpDiv").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
  
<script type="text/javascript">
function deleteDeductionPopUp(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>



<script>
function deleteSelectedDeductionType(id){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/deleteDeductionType?${_csrf.parameterName}=${_csrf.token}&id="+id+"&changeStatus=DELETE", 	   
		    success: function(data) {
		    	if(data == true){
		    		getAllDeductionType();
		    		deleteDeductionPopUp();
		    		 $("#selector2").removeAttr("style");
	     			 $("#selector2").css({ display: "block" });
	     			 $("#deductionDeleteMsgDiv").html("Deleted Successfully");
	     			deductionDeleteMsgFadein();
		    	}
		    },
     });
}
</script>

<script>
	function deductionDeleteMsgFadein() {
		$("#selector2").fadeIn(200);
        $('#selector2').delay(2000).fadeOut('slow');
	}
</script>

 <script>
function reactiveDeduction(id){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/deleteDeductionType?${_csrf.parameterName}=${_csrf.token}&id="+id+"&changeStatus=REACTIVE", 	   
		    success: function(data) {
		    	if(data == true){
		    		getAllDeductionType();
		    	}
		    },
     });
}
</script> 
<jsp:include page="../masterFooter.jsp" />