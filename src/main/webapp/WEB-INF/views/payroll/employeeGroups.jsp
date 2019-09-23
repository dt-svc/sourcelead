<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employees List</h2>
      </div>
<%--       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a>&raquo;<a href=""> Groups List </a> &raquo;  Employees List </div> --%>
      <div class="pull-right brud-crum"><a href=" ${appPath}/dashboard" title="Home">Home</a>&raquo;
       <a href="${appPath}/userGroupList" title="Groups List"> Batches List</a>&raquo;Employees List
		</div>
    </section>
     <div class='reportHead' id="">
		<ul class='pull-right'>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick='getAllEmployeesInGroups();'></span></li>
		</ul>
   </div>
     <div class="candidate_wrap_table datatable_filter">
	                  <div id="employeeGroupDiv"></div>
     </div>
</section>   


<script>
$(document).ready(function(){
	getAllEmployeesInGroups(); 
});
</script>

<script>
  function getAllEmployeesInGroups(){
	  	var groupUUID = '${groupUUID}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getAllEmployeesInGroups?groupUUID="+groupUUID+"&searchValue="+searchValue;
		getAllEmployeesInGroupsData(url); 
  }
</script>

<script>
function getAllEmployeesInGroupsData(url){
 	    var result = "";
 	   result = result +' <div class="remove_search mt10">';
	    result = result +' <input type="text" placeholder="Search" id="dataTableSearch"/>';
	    result = result +'</div>';
 	    result = result +"<table id='employeeGroupTable' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Email</th>';
		result = result + '<th>Employment Type</th>';
		result = result + '<th>Mobile Phone</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#employeeGroupDiv").empty();
        $("#employeeGroupDiv").append(result);
        employeesInGroupsPagination(url); 
}

</script>
<script>
function employeesInGroupsPagination(url){
	var colIndex = 0;
    $('#employeeGroupTable').DataTable( {
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "employeeName" },
            { "data": "email"},
            { "data": "employmentType"},
            { "data": "mobilePhone"}
        ],
        "columnDefs": [{
	    	  "targets": colIndex++,
              "data": "employeeName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
					result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.candidateName+'</div></td>';
	              return result;
              		
              }
	          },{
		    	  "targets": colIndex++,
	              "data": "email",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
						result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.email+'</div></td>';
		              return result;
	              		
	              }
		          },{
			    	  "targets": colIndex++,
		              "data": "employmentType",
		              "render": function ( data, type, opt, meta ) {
		            	  var result = "";
							result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.designationType+'</div></td>';
			              return result;
		              		
		              }
			          },{
              "targets": colIndex++,
              "data": "mobilePhone",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.mobilePhone != null && opt.mobilePhone != '' && opt.mobilePhone != 'undefined'){
            		  result = result + '  <td><div  class="tblminwidth-100">'+opt.mobilePhone+'</div></td>';
            	  }else{
            		  result = result + '  <td><div  class="tblminwidth-100">--</div></td>';
            	  }
            	  return result;
              }
          } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#employeeGroupTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getAllEmployeesInGroups();
    	   }
    	}); 
}
</script>

<jsp:include page="../masterFooter.jsp" />