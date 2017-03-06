<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
$(document).ready(function() {
	refercontactList();
});	
</script>
<style type="text/css">
.emailWidth {
	width:400px;
}
</style>

<section class="content-wraper">

<section class="content-header clearfix">
    <div class="pull-left">
      <h2>Contacts List</h2>
    </div>
    <div class="pull-right brud-crum">Contacts List</div>
  </section>
  <div class="timesheet_out">
      <div class="vendor_wrap">
      <div class="timesheet_head candidateHead">
          <ul>
            <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
              <!-- <div class="export_pop">
                <ul>
                  <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                  <li>Excel<i class="fa fa-file-excel-o"></i></li>
                  <li>Word<i class="fa fa-file-word-o"></i></li>
                </ul>
              </div> -->
            </li>
            
            <li><a id="addPop" href="javascript:void(0)"><i class="fa fa-plus"></i>Add Contact</a>
            
				<!-- <div class="referWrap">
				  <div class="referWrapInn">
					<div class="form-group col-md-6">
					  <label class="col-md-12" for="Project Id">First Name</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="" value="">
						<span class="hidden">Enter Project Id</span> </div>
					</div> 
					<div class="form-group col-md-6">
					  <label class="col-md-12" for="Project Id">Last Name</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="" value="">
						<span class="hidden">Enter Project Id</span> </div>
					</div> 
					<div class="form-group col-md-6">
					  <label class="col-md-12" for="Project Id">Email Id *</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="" value="">
						<span class="hidden">Enter Project Id</span> </div>
					</div> 
					<div class="form-group col-md-6">
					  <label class="col-md-12" for="Project Id">Phone *</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="" value="">
						<span class="hidden">Enter Project Id</span> </div>
					</div> 
					<div class="col-md-12 mt5 clearfix">            
					<div class="pull-right">
					  <input type="button" value="Add Contact" class="btn btn-primary">
					  <input type="button" value="Cancel" class="btn btn-default ml10 canBtn">
					</div>    
					</div> 

				  <div class="clearfix"></div>
				  </div>
			  </div> -->

            </li>
          </ul>
          <div class="clearfix"></div>
        </div>
      <div id="refercontactListDiv"></div>
      </div>
	</div>
  
</section>

<script>
function refercontactList(){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getRefercontactList?${_csrf.parameterName}=${_csrf.token}", 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
		    	var result = "";
			   	 result = result +'	<div class="project_tabs_inn_table vendor_wrap_inn_table">';
			   	 result = result +'    <table id="refercontactpagenationid" class="display" cellspacing="0" width="100%">';
			   	 result = result + '	<thead>';
			   	 result = result +'        <tr>';
			   	 result = result +'          <th>Name</th>';
			   	 result = result +'          <th class="emailWidth">Email Id</th>';
			   	 result = result +'          <th>Phone No</th>';
			   	 result = result +'          <th>Action</th>';
			   	 result = result +'        </tr>';
			   	result = result + '	     </thead>';
			   	 result = result +'      <tbody>';
			   for(var i=0; i<json.length; i++){
				   var report = json[i];
				   if(report.contactUserPhone != null && report.contactUserPhone != '' && report.contactUserPhone != 'undefiend'){
					   contactUserPhone = report.contactUserPhone;
				   }else{
					   contactUserPhone = "--";
				   }
				   result = result +'        <tr>';
				   	 result = result +'          <td><div><span class="mid-span">'+report.firstName+'</span></div></td>';
				   	 result = result +'          <td><div><span class="mid-span">'+report.contactUserEmail+'</span></div></td>';
				   	 result = result +'          <td><div><span class="mid-span">'+contactUserPhone+'</span></div></td>';
				   	 result = result +'          <td><div><i class="glyphicon glyphicon-edit"></i><i class="glyphicon glyphicon-trash"></i></div></td>';
				   	 result = result +'        </tr>';
			   }
			   	 result = result +'      </tbody>';
			   	 result = result +'    </table>';
			   	 result = result +'  </div>';
			   	 $("#refercontactListDiv").empty();
			   	 $("#refercontactListDiv").append(result); 
			   	 refercontactpagenation();
		    }
	});
	
}

</script>

<script>
function refercontactpagenation(){
    $('#refercontactpagenationid').DataTable( { 
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
<jsp:include page="../masterFooter.jsp" />