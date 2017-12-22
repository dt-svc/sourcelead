<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript">
$(document).ready(function(){
	
});
</script>


 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>A/P Aging List</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; A/P Agings List</div>
    </section>
    <div class="clearfix"></div>
    <div id="loading"></div>
    <div class="ReqRepHead clearfix">
      <div class="col-md-3 pull-left reqListBtn">
          <a href='${appPath}/apAgingGraphReport' title="Graph View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href='${appPath}/apAgingReport' title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
     </div>
      <div class="col-md-2 dropdown-select positionRelative col-md-offset-4 hidden">
        <select class="form-control" id="custom-dates">
          <option>Select Date</option>
          <option selected>Last 7 days</option>
          <option>Last 15 days</option>
          <option>1 month</option>
          <option>3 months</option>
          <option>last year</option>
          <option value="custom">Custom</option>
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
      <div class="col-md-1 col-md-offset-8">
        <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
          <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
          <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
          <ul class="dropdown-menu clearfix  dropdown-menu-right">
            <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            <!--
						<li role="Print" class="mt5 cursorPointer"><i class="fa fa-print paddingRight"></i>Print</li>
						<li role="Download" class="mt5 cursorPointer"><i class="fa fa-download paddingRight"></i>Download</li>
						<li role="Share" class="mt5 mb5 cursorPointer"><i class="fa fa-share-alt paddingRight"></i>Share</li>
-->
          </ul>
        </div>
      </div>
    </div>
    <div class="clearfix"></div>
      <div class="vendor_wrap mt">
        <div class="tableStyles">
         <div class="clearfix table_head">
        	 
        	 <div class="col-md-offset-6 select-fild col-md-2 mt5">
                    <select id="apagingType" name="apagingType" onchange="vendorapAging();">
                          <option value="byvendorbill">By Bill</option>
                          <option value="Payables">By Vendor</option>
                    </select>
             </div>
             <div class="col-md-2 dropdown-select positionRelatives mt5" style="display: none" id="customDatesDropdown">
        <select class="form-control" id="custom-datess">
          <option>Select Date</option>
          <option selected>Last 7 days</option>
          <option>Last 15 days</option>
          <option>1 month</option>
          <option>3 months</option>
          <option>last year</option>
          <option value="custom">Custom</option>
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
        	 
         	 <p class="col-md-2 form-control-static">
         	 	<label class="mt5 text-muted">Total:</label><span class="ml mt5"><strong>250</strong></span>
         	 </p>
         </div>
         <div id="byCustomer">
          <table class="table">
            <thead>
              <tr>
               <th>Vendor</th>
                <th>
                  10 Days
                </th>
                <th>
                  15 Days
                </th>
                <th>
                  30 Days
                </th>
                <th>
                  60 Days
                </th>
                <th>
                  90 Days
                </th>
                 <th>
                  180 Days
                </th>
                 <th>
                  365 Days
                </th>
                 <th>
                  &gt; 365 Days
                </th>
                <th>Total</th>
              </tr>
              </thead>
            <tbody>
              <tr>
              <td>
                  <div class="tbldata-dotdot">Client Name</div>
                </td>
				<td>
                 <div class="tblminwidth-50">2598742365</div>
                </td>
                <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                 <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                 <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                 <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                <td><div class="tblminwidth-50">2598742365</div></td>              </tr>
            </tbody>
          </table>
        <nav>
          <ul id="paging" class="pagination">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
        </div>
        
        <div class="clearfix"></div>
      <div id="byInvoice" style="display: none">
          <table class="table">
            <thead>
              <tr>
              <th>Refer Id</th>
               <th>Client</th>
                <th>
                  Bill Date
                </th>
                <th>
                  Due Date
                </th>
                <th>
                 Due Amounts
                </th>
                <th>
                  Total Amount
                </th>
              </tr>
              </thead>
            <tbody>
              <tr>
              <td>
                 <div class="tblminwidth-50">00150</div>
                </td>
              <td>
                  <div class="tbldata-dotdot">Client Name</div>
                </td>
				<td>
                 <div class="tblminwidth-50">2598742365</div>
                </td>
                <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
                <td>
                  <div class="tblminwidth-50">2598742365</div>
                </td>
               </tr>
            </tbody>
          </table>
        <nav>
          <ul id="pagin2" class="pagination">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
        </div>
        </div>
        </div>
  </section>
		
		
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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 </script> 
 
 
 <script>
$(function() {
        $('#custom-dates').change(function(){
			var optval = $(this).val();
			if(optval === "custom") {
				$("#custom-select").show();
			}else{
				$("#custom-select").hide();
			}
        });
    });
</script>
<script>
$(function() {
        $('#araginglistSel').change(function(){
			var optval = $(this).val();
			if(optval === "byInvoice") {
				$("#byInvoice").show();
				$("#byCustomer").hide();
				$("#customDatesDropdown").show();
			}else if(optval === "byCustomer"){
				$("#byCustomer").show();
				$("#byInvoice").hide();
	$("#customDatesDropdown").hide();
			}
        });
    });
</script> 

<script>
function vendorapAging(){
		var apagingType = $("#apagingType").val();
	    $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/vendorapAging?"+tokenParamName+"="+tokenValue+"&apagingType="+apagingType, function(data) {
         if(data !=null){
          alert(data);	
          }
		}); 
}
</script>


<jsp:include page="../masterFooter.jsp" />