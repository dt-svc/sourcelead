<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	
});
</script>

<!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Payroll</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; My Forms</div>
</section>

   <div class="project_side_wrap_inn_tabs">
   	<ul class="project-tabs-menu">
   	    
	   <li class="current"><a href="javascript:void(0)" class="active" onclick="getOverviewdata();">Overview</a></li>
	   <li><a href="javascript:void(0)" onclick="getPayPeriodData();">History</a></li>
	   <li><a href="javascript:void(0)">Employees</a></li>
	   <li><a href="javascript:void(0)">Contractors</a></li>
	   <li><a href="javascript:void(0)">Tax Forms</a></li>
	   <li><a href="javascript:void(0)">Settings</a></li>
	 </ul>
   </div> 
   
   <div class="clearfix"></div>
   <div class="col-md-2 col-md-offset-7">
         			<a href="<c:url value='${appPath}/runPayroll'/>" class="btn btn-success btnSuccess btn-sm anchorWhite pull-right mt10">Initiate Payroll</a>
    </div>
    
    <div id="overViewDiv">
    <div class="project_tabs">
      <div class="clearfix">
        <div class="minHeight40 reportHead clearfix">
          <div class="col-md-9">
          	
          </div>
          <ul class="col-md-3 mt10">
            <li class="pull-right"><input class="repSearchBar" placeholder="Search" id="dataTableSearch" type="text"><span class="repSearchIcon" onclick="getClientList();"></span></li>
          </ul>
        </div>
          <div class="project_tabs_inn_table clearfix">
            <table>
              <thead>
                <tr>
                  <th>Assessment Year</th>
                  <th>Form Type</th>
                  <th>Filling Date</th>
                  <th>Dummy Filling</th>
                  <th>Dummy Filling</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">IT Declaration</div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td><div class="tblminwidth-150 clearfix">Submitted</div></td>
                </tr>
                <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">IT Declaration</div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td><div class="tblminwidth-150 clearfix">Submitted</div></td>
                </tr>
                 
              </tbody>
            </table>
          </div>
        <div class="clearfix"></div>
      </div>
    </div>
	<div class="clearfix"></div>
	
	<div class="row mt">
		<div class="col-md-8">
		</div>
		<div class="col-md-4">
              <div class="clearfix mb">
                <div class="dropdown-select">
                  <select>
                    <option selected>Select Group</option>
                    <option>Group-1</option>
                    <option>Group-2</option>
                    <option>Group-3</option>
                  </select>
                </div>
              </div>
              <div class="row">
                 <div class="dropdown-select col-md-7">
                  <select>
                    <option selected>Select Frequency</option>
                    <option>01/02/2018</option>
                    <option>01/02/2018</option>
                    <option>09/02/2018 - 15/01/2018</option>
                  </select>
                </div>
              
               <div class="clearfix col-md-5">
                <input name="" class="form-control calendar" placeholder="Check Date" type="text" id="checkDate">
              </div>
              
              </div>
          <div class="clearfix mt">
          	<button type="button" class="btn btn-primary btn-block">Run Payroll</button>
          </div>  
         </div>
		</div>
	</div>
		
	<div id="historyDiv">
	<div class='reportHead'>
		<ul class='pull-right'>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='historyDataTableSearch'/><span class='repSearchIcon' onClick=''></span></li>
		</ul>
   </div>
	<div class="candidate_wrap_table datatable_filter backgroundWhite">
                <div id="payrollFrequencyDiv"></div>
    </div>
    <div class="clearfix"></div>
	</div>
		
  </section>
  
<script>
$(document).ready(function() {
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
    });
});
</script>
  
  <script>
  function getOverviewdata(){
	  $("#historyDiv").hide();
	  $("#overViewDiv").show();
  }
  </script>
  
  
  <script>
  function getPayPeriodData(){
			var sourceleadUrl = '${sourceleadUrl}';
			var searchValue = $("#historyDataTableSearch").val();
			searchValue = searchValue ? searchValue : "";
			var url = sourceleadUrl+ "/getPaySheetFrequencyData?searchValue="+searchValue;
			preparePaySheetFrequency(url);
	  
  }
  
  
  function preparePaySheetFrequency(url) {
		 
		var result = "";
		result = result + '<table id="payFrequency" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Pay run id</th>';
		result = result + '<th>Group Name</th>';
		result = result + '<th>Pay Period</th>';
		result = result + '<th>Pay Date</th>';
		result = result + '<th>Type</th>';
		result = result + '<th>Total</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#payrollFrequencyDiv").empty();
		$("#payrollFrequencyDiv").append(result);
		payFrequencyPagination(url);
		
		$("#overViewDiv").hide();
		$("#historyDiv").show();

	}
  
  function payFrequencyPagination(url) {
		var sourceleadUrl = '${sourceleadUrl}';
		var colIndex = 0;
		$('#payFrequency').DataTable({
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'GET',
								pages : 10
							}),
							
							"columns" : [ {
								"data" : "Pay run id"
							}, {
								"data" : "Group Name"
							}, {
								"data" : "Pay Period"
							}, {
								"data" : "Pay Date"
							},{
								"data" : "Type"
							},{
								"data" : "total"
							}, {
								"data" : "status"
							}],
							
							
							 "columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "Pay run id",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriodNum != null && opt.payPeriodNum != '' && opt.payPeriodNum !='undefined'){
						                		  result = result +'<div class="tblminwidth-100"><a href="'+sourceleadUrl+'/viewPaySheetFrequencyData?approverStatus='+opt.approverStatus+'&paySheetFrequencyUUID='+opt.paySheetFrequencyuuid+'&payPeriodNum='+opt.payPeriodNum+'">'+opt.payPeriodNum+'</a></div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "Group Name",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.groupName != null && opt.groupName != '' && opt.groupName !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.groupName+'</div>';
											}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "Pay Period",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriod != null && opt.payPeriod != '' && opt.payPeriod !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.payPeriod+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "Pay Date",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payDate != null && opt.payDate != '' && opt.payDate !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.payDate+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "Type",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.payPeriod != null && opt.payPeriod != '' && opt.payPeriod !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">Regular</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "Total",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.total != null && opt.total != '' && opt.total !='undefined'){
						                		  result = result +'<div class="tbldata-dotdot-vsm">'+opt.total.toFixed(2)+'</div>';
						              		}else{
						              		  result = result +'<div class="tbldata-dotdot-vsm">--</div>';
						              		}
											return result;
										}
										
									},{
										"targets" : colIndex++,
										"data" : "Status",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.approverStatus != null && opt.approverStatus != '' && opt.approverStatus !='undefined'){
						                		  result = result +'<div class="tblminwidth-100">'+opt.approverStatus+'</div>';
						              		}else{
						              		  result = result +'<div class="tblminwidth-100">--</div>';
						              		}
											return result;
										}
									}],
							"scrollCollapse" : false,
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 3, 'desc' ] ],
							"bLengthChange" : true,
							"bDestroy" : true,
							"ordering" : true,
							"oLanguage" : {
								"sSearch" : "",
								"sZeroRecords" : "",
								"sEmptyTable" : "",
								"sProcessing" : "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
							}
						});
		$("#payFrequency").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#historyDataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				getPayPeriodData();
			}
		});
		
	}
  
  </script>
  
  
<jsp:include page="../masterFooter.jsp" />