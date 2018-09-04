<!-- Content Area -->
<!-- <section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>My Payrolls</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo;My Payrolls</div>
</section>
 
   <div class="tabsMenu">
   	<ul class="list-unstyled">
	   <li><a href="myPays-us.html" class="active">Pays</a></li>
	   <li><a href="myForm-us.html">Forms</a></li>
	   <li class="current"><a href="myPayrolls-us.html" class="active">Loans &amp; Advances</a></li>
	 </ul>
   </div> --> 
 
 <div class="clearfix"></div>
  <div class="timesheet_out" id="loansAdvancesDiv">

<div class="tablelist-wrap">

<div class="col-md-12">
<ul class="list-unstyled mt row">
<li class="col-md-3" title="Outstanding" onClick="outstandingFun(this)">
	<div class="cardModel-widget text-center cursorPointer currentActive" id="outstandingWidget">
		<h5 class="margin-top0">Outstanding</h5>
		<p class="text-muted mb0"><i class="fa fa-rupee paddingRight"></i><span>1000</span></p>
	</div>
</li>
<li class="col-md-3"  title="Completed" onClick="completedFun(this)">
	<div class="cardModel-widget text-center cursorPointer" id="completedWidget">
				<h5 class="margin-top0">Completed</h5>
		<p class="text-muted mb0"><i class="fa fa-rupee paddingRight"></i><span>1000</span></p>

	</div>
</li>
<li class="col-md-3" title="Request" onClick="requestFun(this)">
	<div class="cardModel-widget text-center cursorPointer" id="requestWidget">
				<h5 class="margin-top0">Request</h5>
		<p class="text-muted mb0"><i class="fa fa-rupee paddingRight"></i><span>1000</span></p>

	</div>
</li>
<li class="col-md-3">
	<a href="javascript:void(0)" class="pull-right" onClick="requestPop();">Request</a>
</li>
</ul>
</div>
<div class="clearfix"></div>
   
<div class="styleTables clearfix mt" id="outStadingTable">
<div id="outStadingTableDiv"></div>
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Total Amount</th>
                  <th>Paid Amount</th>
                  <th>Due Amount</th>
                  <th>No. of Schedule</th>
                  <th>Next Schedule</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tbldata-dotdot-vsm">Car</div></td>
                  <td><div class="tblminwidth-50">Loan</div></td>
                  <td><div class="tblminwidth-50 clearfix">5,000,000</div></td>
                  <td><div class="tblminwidth-50 clearfix">3,000,000</div></td>
                  <td><div class="tblminwidth-50 clearfix">2,000,000</div></td>
                  <td><div class="tblminwidth-50 clearfix">10</div></td>
                  <td><div class="tblminwidth-50 clearfix">11/08/2018</div></td>
                  <td>
					  <div class="tblminwidth-30 text-center">
					  	<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a>
					  </div>
                  </td>
                </tr>
              </tbody>
            </table>
</div>
<div class="styleTables clearfix mt" id="completedTable" style="display: none">
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Total Amount</th>
                  <th>No. of Schedule</th>
                  <th>Last Paid On</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tbldata-dotdot-vsm">Personal</div></td>
                  <td><div class="tblminwidth-50">Loan</div></td>
                  <td><div class="tblminwidth-50 clearfix">5,000,000</div></td>
                  <td><div class="tblminwidth-50 clearfix">5</div></td>
                  <td><div class="tblminwidth-50 clearfix">11/08/2018</div></td>
                  <td>
					  <div class="tblminwidth-30 text-center">
					  	<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a>
					  </div>
                  </td>
                </tr>
              </tbody>
            </table>
</div>
<div class="styleTables clearfix mt" id="requestTable" style="display: none">
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Total Amount</th>
                  <th>Requested To</th>
                  <th>Requested On</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tbldata-dotdot-vsm">Gold</div></td>
                  <td><div class="tblminwidth-50">Loan</div></td>
                  <td><div class="tblminwidth-50 clearfix">5,000,000</div></td>
                  <td><div class="tblminwidth-50 clearfix">Users</div></td>
                  <td><div class="tblminwidth-50 clearfix">11/08/2018</div></td>
                  <td>
					  <div class="tblminwidth-30 text-center text-danger">
					  	Pending
					  </div>
                  </td>
                </tr>
              </tbody>
            </table>
</div>
    
    
</div>
</div>

 <!--  </section> -->
  
  <script type="text/javascript">
	var config = {
	  '.chosen-select'           : {},
	  '.chosen-select-deselect'  : {allow_single_deselect:true},
	  '.chosen-select-no-single' : {disable_search_threshold:10},
	  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	  '.chosen-select-width'     : {width:"95%"}
	}
	for (var selector in config) {
	  $(selector).chosen(config[selector]);
	}
	$('.chosen-select').each(function() {
		var $this = $(this);
		$this.next().css({'width': '100%'});

});
</script> 
<script>	
function outstandingFun(event){
	$("#outstandingWidget").addClass("currentActive");
	$("#completedWidget, #requestWidget").removeClass("currentActive");
	document.getElementById("outStadingTable").style.display="block";
	document.getElementById("completedTable").style.display="none";
	document.getElementById("requestTable").style.display="none";
}
function completedFun(event){
	$("#completedWidget").addClass("currentActive");
	$("#outstandingWidget, #requestWidget").removeClass("currentActive");

	document.getElementById("outStadingTable").style.display="none";
	document.getElementById("completedTable").style.display="block";
	document.getElementById("requestTable").style.display="none";
}
function requestFun(event){
	$("#requestWidget").addClass("currentActive");
	$("#outstandingWidget, #completedWidget").removeClass("currentActive");

	document.getElementById("outStadingTable").style.display="none";
	document.getElementById("completedTable").style.display="none";
	document.getElementById("requestTable").style.display="block";
}
//Request Pop script
function requestPop() {
	$(".requestPop, .sideMask").fadeIn();
}
function clsoerequestPop() {
	$(".requestPop, .sideMask").fadeOut();
}
</script>