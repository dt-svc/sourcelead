<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css">

<script type="text/javascript">
$(document).ready(function(){
	
});
</script>

 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Missing Invoice Lists</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Missing Invoice Lists</div>
    </section>
    <!-- hiring Process  -->
   
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href='${appPath}/missingInvoiceGraphReport' title="List View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href='${appPath}/missingInvoiceReport' title="List View" id="LView" class="btn btn-default rAct"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4 col-md-offset-1">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Client">
		<option>Client-1</option>
		<option>Client-2</option>
		<option>Client-3</option>
		<option>Client-4</option>
		<option>Client-5</option>
		<option>Client-1</option>
		<option>Client-2</option>
		<option>Client-3</option>
		<option>Client-4</option>
		<option>Client-5</option>
		</select>
	</div>
        </div>
        <div class="col-md-2 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" id="customDates">
          <option>Select Date</option>
          <option value="invoiceweekly">Last 7 days</option>
          <option value="invoice15Days">Last 15 days</option>
          <option value="invoice1Month">1 month</option>
          <option value="invoice3Month">3 months</option>
          <option value="invoices" selected>Last year</option>
          <option>Year to Date</option>
          <option value="custom-select">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;">
		<div class="pull-left">
		<div class="pull-left mr5" style="width: 140px">
		<input value="" name="Date" id="datepicker4" placeholder="From" class="form-control calendar input-sm" type="text">
		</div>
		<div class="pull-left mr5" style="width: 140px">
		<input value="" name="Date" id="datepicker2" placeholder="To" class="form-control calendar input-sm" type="text">
		</div>
		<button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
		</div>
    </div>
<!-- Top Leave Takers Custom Date --> 
      </div>

        </div>
        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2">Customize</button>
		</div>
		<div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-download"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div>
      </div>
    </div>    
     
      <div class="content-dashboard clearfix"> 
        <!-- Assigned Requirements -->
        <div class="clearfix dashboard-widget mt col-md-12">
          <div class="clearfix">
          <p class="col-md-2 mb0 margin-top0 padding_left fontSize20">
         	 	<span class="text-muted">Total:</span><span class="ml"><strong>250</strong></span>
         	 </p>
          	<div class="remove_search pull-right">
                  <input placeholder="Search" type="text">
                </div>
          </div>
          
          
          <div class="clearfix tableStyles mt5">
            <table class="table table-bordered noMar">
              <thead>          
                <tr>
                  <th>Refer ID</th>
                  <th>Client Name</th>
                  <th>Project Name</th>
                  <th>Date</th>
                  <th>Total Amount</th>
                  <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <tr>
				  <td><div class="tbldata-dotdot-sm">0925</div></td>
                  <td><div class="tbldata-dotdot-me">XYZ</div></td>
                  <td><div class="tbldata-dotdot-sm">Project1</div></td>
                  <td><div class="tbldata-dotdot-vsm">11/12/2017</div></td>
                  <td><div class="tbldata-dotdot-vsm">20,000.00</div></td>
				<td><div class="tbldata-dotdot-vsm"><a href="javascript:void(0)" class="butLink"><i class="fa fa-plus"></i>Gan. Invoice</a></div></td>
                  </tr>
                <tr>
				  <td><div class="tbldata-dotdot-sm">0925</div></td>
                  <td><div class="tbldata-dotdot-me">XYZ</div></td>
                  <td><div class="tbldata-dotdot-sm">Project1</div></td>
                  <td><div class="tbldata-dotdot-vsm">11/12/2017</div></td>
                  <td><div class="tbldata-dotdot-vsm">20,000.00</div></td>
				<td><div class="tbldata-dotdot-vsm"><a href="javascript:void(0)" class="butLink"><i class="fa fa-plus"></i>Gan. Invoice</a></div></td>
                  </tr>
                <tr>
				  <td><div class="tbldata-dotdot-sm">0925</div></td>
                  <td><div class="tbldata-dotdot-me">XYZ</div></td>
                  <td><div class="tbldata-dotdot-sm">Project1</div></td>
                  <td><div class="tbldata-dotdot-vsm">11/12/2017</div></td>
                  <td><div class="tbldata-dotdot-vsm">20,000.00</div></td>
				<td><div class="tbldata-dotdot-vsm"><a href="javascript:void(0)" class="butLink"><i class="fa fa-plus"></i>Gan. Invoice</a></div></td>
                  </tr>
                <tr>
				  <td><div class="tbldata-dotdot-sm">0925</div></td>
                  <td><div class="tbldata-dotdot-me">XYZ</div></td>
                  <td><div class="tbldata-dotdot-sm">Project1</div></td>
                  <td><div class="tbldata-dotdot-vsm">11/12/2017</div></td>
                  <td><div class="tbldata-dotdot-vsm">20,000.00</div></td>
				<td><div class="tbldata-dotdot-vsm"><a href="javascript:void(0)" class="butLink"><i class="fa fa-plus"></i>Gan. Invoice</a></div></td>
                  </tr>
                <tr>
				  <td><div class="tbldata-dotdot-sm">0925</div></td>
                  <td><div class="tbldata-dotdot-me">XYZ</div></td>
                  <td><div class="tbldata-dotdot-sm">Project1</div></td>
                  <td><div class="tbldata-dotdot-vsm">11/12/2017</div></td>
                  <td><div class="tbldata-dotdot-vsm">20,000.00</div></td>
				<td><div class="tbldata-dotdot-vsm"><a href="javascript:void(0)" class="butLink"><i class="fa fa-plus"></i>Gan. Invoice</a></div></td>
                  </tr>                                                                        
              </tbody>
            </table>
            <!-- Pagenation -->
            <nav class="clearfix">
              <ul id="paging" class="pagination pull-right">
                <li><a href="#">Previous</a></li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li data-page="last"><a href="#">Next</a></li>
              </ul>
            </nav>
          </div>
          
        </div>
        <!-- Pagenation Ends --> 
      </div>
      
  </section>
  <!-- Content Area Ends--> 

<jsp:include page="../masterFooter.jsp" />