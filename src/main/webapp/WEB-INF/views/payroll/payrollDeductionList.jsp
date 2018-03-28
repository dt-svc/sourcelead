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
    <h2>Payroll Configuration</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Payroll Configuration</div>
</section>
 
   <div class="tabsMenu clearfix">
   <ul class="list-unstyled">
	   <li class="current"><a href="<c:url value='${appPath}/payrollConfigure'/>" >Deduction</a></li>
	   <li><a href="<c:url value='${appPath}/payrollContribution'/>" class="active">Contribution</a></li>
	   </ul>
   </div>  
 
  <div class="tablelist-wrap clearfix">
   
    <div class="table-header clearfix">
      <ul class="list-unstyled list-inline">
        <li><a href="<c:url value='${appPath}/addDeduction'/>" class="addcon-pop"><i class="fa fa-plus paddingRight"></i>Add</a></li>
        <li>
		 <div class="dynamic_search">
			<input type="text" placeholder="Search" id='dataTableSearch'/>
			<i class="fa fa-search"></i>
		 </div>
	   </li>
        <div class="clearfix"></div>
      </ul>
    </div>
    
<div class="styleTables datatable_filter">
	<div id="deductionTable"></div>
    	<!-- <table>
		<thead>
            	<tr>
                	<th>Type</th>
                   	<th>Description</th>
                    <th>Method</th>
                    <th>Amount</th>
                    <th>Action</th>
                </tr>
			</thead>	
			<tbody>
			   	<tr>
                    <td><div class="proj_show tblminwidth-75"><span>401</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div class="tbldata-dotdot-verybig">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</div></td>
                    <td><div  class="tblminwidth-100">Fixed</div></td>
                    <td><div  class="tblminwidth-100">50000$</div></td>
                    <td><div class="tbldata-dotdot_50">
                   		 	<a href="javascript:void(0)" class="text-primary"><i class="fa fa-pencil-square-o"></i></a>
                    		<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i></a>
						</div>
               		</td>
                </tr>
			   	<tr>
                    <td><div class="proj_show tblminwidth-75"><span>Post Tax Deduction</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div class="tbldata-dotdot-verybig">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</div></td>
                    <td><div  class="tblminwidth-100">Fixed</div></td>
                    <td><div  class="tblminwidth-100">50000$</div></td>
                    <td><div class="tbldata-dotdot_50">
                   		 	<a href="javascript:void(0)" class="text-primary"><i class="fa fa-pencil-square-o"></i></a>
                    		<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i></a>
						</div>
               		</td>
                </tr>      			
            </tbody>
        </table> -->
	</div>
  <!--   
    <nav>
      <ul id="paging" class="pagination pull-right mr">
        <li><a href="#">Previous</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li class="disabled"><a href="#">6</a></li>
        <li data-page="last"><a href="#">Next</a></li>
      </ul>
      <div class="clearfix"></div>
    </nav> -->
    
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
            "data": "payrollDeductionMethod",
            "render": function ( data, type, opt, meta ) { 
          			  var result = "";
      	 			  if(opt.payrollDeductionMethod !=null && opt.payrollDeductionMethod !='' && opt.payrollDeductionMethod !='undefined'){
					 result = result + ' <td><div  class="tblminwidth-100">'+opt.payrollDeductionMethod+'</div></td>';
				  }else{
					 result = result + ' <td><div>--</div></td>';
				}
            return result;
            }
          }, {
              "targets": colIndex++,
              "data": "deductionAmount",
              "render": function ( data, type, opt, meta ) {
            	  return ' <td><div  class="tblminwidth-100">$ '+opt.deductionAmount+'</div></td>';
              }
            }, {
                "targets": colIndex++,
                "data": "Action",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		result = result + '<td><div class="tbldata-dotdot_50">';
                		result = result + '<a href="javascript:void(0)" class="text-primary"><i class="fa fa-pencil-square-o mr5"></i></a>';
                		result = result + '<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i></a>';
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


<jsp:include page="../masterFooter.jsp" />