<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
   getAllContributionType();
});
</script>
 
 <!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Payroll Contribution</h2>
  </div>
  
 <div class="success_pop" id="selector2" style="display: none;">
   <i class="fa fa-check-circle"></i><span id="contributeDeleteMsgDiv"></span>
 </div>
  <div class="pull-right brud-crum">
                <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo; Payroll Contribution
  </div>
</section>
 

  <div class="tablelist-wrap clearfix">
   
    <div class="table-header clearfix">
      <ul class="list-unstyled list-inline">
        <li><a href="<c:url value='${appPath}/addContribution'/>" class="addcon-pop"><i class="fa fa-plus paddingRight"></i>Add</a></li>
        <li>
		 <div class="dynamic_search">
			<input type="text" placeholder="Search" id='dataTableSearch'/>
			<i class="fa fa-search"  onclick="getAllContributionType();"></i>
		 </div>
	   </li>
        <div class="clearfix"></div>
      </ul>
    </div>
    
<div class="project_wrap_table datatable_filter">
     <div id="contributionTable"></div>
  </div>


  </section>
  
  
  <script>
  function getAllContributionType(){
	  var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/findAllPayrollContribution?searchValue="+searchValue;
	    prepareAllContributionTypeList(url);
  }
</script>

<script>
function prepareAllContributionTypeList(url){
	$("#contributionTable").empty();
 	    var result = "";
 	    /* result = result +" <div class='modal-header'>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='contributionPaginagtion' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
		result = result + '<th>Deduction Type</th>';
		result = result + '<th>Description</th>';
		result = result + '<th>taxType</th>';
		result = result + '<th>scheduleType</th>';
		result = result + '<th>Method</th>';
		result = result + '<th>Amount</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#contributionTable").append(result);
        contributionTypepagination(url); 
}

</script>

<script>
function contributionTypepagination(url){
	var colIndex = 0;
    $('#contributionPaginagtion').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "deductionType" },
            { "data": "description"},
            { "data": "taxType"},
            { "data": "scheduleType"},
            { "data": "payrollContributionMethod"},
            { "data": "contributionAmount" },
            { "data": "Action"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "deductionType",
	    	  	 "sortable": false,
	    	  	"sortable": true,
	    	  	"render": function(data, type, opt, meta) {
	    	  		        var result = "";
							result = result + ' <td><div class="tblminwidth-75"><span>'+opt.deductionType+'</span></div></td>';
		                     return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
	    	  "sortable": false,
              "data": "description",
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
		    	  "sortable": false,
	              "data": "taxType", 
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
			    	  "sortable": false,
		              "data": "scheduleType",
		              "render": function ( data, type, opt, meta ) {
		            	  var result = "";
		            	  if(opt.scheduleType !=null && opt.scheduleType !='' && opt.scheduleType !='undefined'){
								result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.scheduleType+'</div></td>';
							}else{
								result = result + ' <td><div>--</div></td>';
							}
			              return result;
		              		
		              }
			          },{
            "targets": colIndex++,
            "sortable": false,
            "data": "payrollContributionMethod",
            "render": function ( data, type, opt, meta ) { 
          			  var result = "";
      	 			  if(opt.payrollContributionMethod !=null && opt.payrollContributionMethod !='' && opt.payrollContributionMethod !='undefined'){
					 result = result + ' <td><div  class="tblminwidth-100">'+opt.payrollContributionMethod+'</div></td>';
				  }else{
					 result = result + ' <td><div>--</div></td>';
				}
            return result;
            }
          }, {
              "targets": colIndex++,
              "sortable": false,
              "data": "contributionAmount",
               "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.payrollContributionMethod == 'Fixed'){
            		  result = result + ' <td><div  class="tblminwidth-100">$ '+opt.contributionAmount.toFixed(2)+'</div></td>';  
            	  }else{
            		  result = result + ' <td><div  class="tblminwidth-100">'+opt.contributionAmount.toFixed(2)+' %</div></td>';
            	  }
            	  return result
              }
            }, {
                "targets": colIndex++,
                "data": "Action",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		result = result + '<td><div class="tbldata-dotdot_50">';
                		result = result + '<a href="'+sourceleadUrl+'/editContributionType?contributionUUID='+opt.contributionUUID+'" class="text-primary"><i class="fa fa-pencil-square-o mr5"></i></a>';
                		if(opt.status == 1){
                			result = result + '<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash" onclick="deleteContributionType('+opt.contributionId+');"></i></a>';
                		}else{
                			result = result + '<span title="Reactive" class="paddingLeft"><i class="fa fa-repeat text-primary cursorPointer" onclick = "reactiveContribution('+opt.contributionId+');"></i></span>';
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
    $("#contributionPaginagtion").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getAllContributionType();
    	   }
    	});
}
</script>

<div id="deleteContributionPopUpDiv"></div> 


<script type="text/javascript">
function deleteContributionType(contributionId){
	$("#deleteContributionPopUpDiv").empty();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete.!</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedContributionType("+contributionId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteContributionPopUp();'></b>";
	result = result +"	</div>";
	$("#deleteContributionPopUpDiv").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
  
<script type="text/javascript">
function deleteContributionPopUp(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>



<script>
function deleteSelectedContributionType(contributionId){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/deleteContributionType?${_csrf.parameterName}=${_csrf.token}&contributionId="+contributionId+"&changeStatus=DELETE", 	   
		    success: function(data) {
		    	if(data == true){
		    		getAllContributionType();
		    		deleteContributionPopUp();
		    		 $("#selector2").removeAttr("style");
	     			 $("#selector2").css({ display: "block" });
	     			 $("#contributeDeleteMsgDiv").html("Deleted Successfully");
	     			contributeDeleteMsgFadein();
		    	}
		    },
     });
}
</script>

<script>
	function contributeDeleteMsgFadein() {
		$("#selector2").fadeIn(200);
        $('#selector2').delay(2000).fadeOut('slow');
	}
</script>

<script>
function reactiveContribution(contributionId){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/deleteContributionType?${_csrf.parameterName}=${_csrf.token}&contributionId="+contributionId+"&changeStatus=REACTIVE", 	   
		    success: function(data) {
		    	if(data == true){
		    		getAllContributionType();
		    	}
		    },
     });
}
</script>
<jsp:include page="../masterFooter.jsp" />