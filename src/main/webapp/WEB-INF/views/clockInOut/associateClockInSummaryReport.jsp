<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


 <!-- Side Nav Bar --> 
  
  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Associate Clocked</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Associate Clocked</div>
    </section>
    <div class="contentareaInner-styles clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <span class="pull-left refreIcon noPad mt5">
              <a href="javascript:void(0)" class="refeIcon mt2" title="Refresh" onclick="resetFilter();"></a>
            </span>
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
                <div class="newActPop"> <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div>
              </li>
              <li class="noMar">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table tableFirstSecond clearfix dropdownTableDesig">
            <table>
              <thead>
                <tr>
                  <th>Employee Name</th>
                  <th>
                      Monday <br>
                      <div class="fontSmall clearfix mt2">03/01/2019</div>
                  </th>
                  <th>Tuesday
                      <div class="fontSmall clearfix mt2">03/02/2019</div>
                  </th>
                  <th>Wednesday
                      <div class="fontSmall clearfix mt2">03/03/2019</div>
                  </th>
                  <th>Thursday
                      <div class="fontSmall clearfix mt2">03/04/2019</div>
                  </th>
                  <th>Friday
                      <div class="fontSmall clearfix mt2">03/05/2019</div>
                  </th>
                  <th>Saturday
                      <div class="fontSmall clearfix mt2">03/06/2019</div>
                  </th>
                  <th>
                      Monday <br>
                      <div class="fontSmall clearfix mt2">03/01/2019</div>
                  </th>
                  <th>Tuesday
                      <div class="fontSmall clearfix mt2">03/02/2019</div>
                  </th>
                  <th>Wednesday
                      <div class="fontSmall clearfix mt2">03/03/2019</div>
                  </th>
                  <th>Thursday
                      <div class="fontSmall clearfix mt2">03/04/2019</div>
                  </th>
                  <th>Friday
                      <div class="fontSmall clearfix mt2">03/05/2019</div>
                  </th>
                  <th>Saturday
                      <div class="fontSmall clearfix mt2">03/06/2019</div>
                  </th>
                  <th>
                      Monday <br>
                      <div class="fontSmall clearfix mt2">03/01/2019</div>
                  </th>
                  <th>Tuesday
                      <div class="fontSmall clearfix mt2">03/02/2019</div>
                  </th>
                  <th>Wednesday
                      <div class="fontSmall clearfix mt2">03/03/2019</div>
                  </th>
                  <th>Thursday
                      <div class="fontSmall clearfix mt2">03/04/2019</div>
                  </th>
                  <th>Friday
                      <div class="fontSmall clearfix mt2">03/05/2019</div>
                  </th>
                  <th>Saturday
                      <div class="fontSmall clearfix mt2">03/06/2019</div>
                  </th>
                  <th>
                      Monday <br>
                      <div class="fontSmall clearfix mt2">03/01/2019</div>
                  </th>
                  <th>Tuesday
                      <div class="fontSmall clearfix mt2">03/02/2019</div>
                  </th>
                  <th>Wednesday
                      <div class="fontSmall clearfix mt2">03/03/2019</div>
                  </th>
                  <th>Thursday
                      <div class="fontSmall clearfix mt2">03/04/2019</div>
                  </th>
                  <th>Friday
                      <div class="fontSmall clearfix mt2">03/05/2019</div>
                  </th>
                  <th>Saturday
                      <div class="fontSmall clearfix mt2">03/06/2019</div>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tbldata-dotdot inlineBlock">Rajkumar Santoshi Josh</div></td>
                  <td><div class="tblminwidth-50 selectedTd">8hrs</div></td>
                  <td><div class="tblminwidth-50">9hrs</div></td>
                  <td><div class="tblminwidth-50">10hrs</div></td>
                  <td><div class="tblminwidth-50">11hrs</div></td>
                  <td><div class="tblminwidth-50">8hrs</div></td>
                  <td><div class="tblminwidth-50">6hrs</div></td>
                  <td><div class="tblminwidth-50 selectedTd">8hrs</div></td>
                  <td><div class="tblminwidth-50">9hrs</div></td>
                  <td><div class="tblminwidth-50">10hrs</div></td>
                  <td><div class="tblminwidth-50">11hrs</div></td>
                  <td><div class="tblminwidth-50">8hrs</div></td>
                  <td><div class="tblminwidth-50">6hrs</div></td>
                  <td><div class="tblminwidth-50 selectedTd">8hrs</div></td>
                  <td><div class="tblminwidth-50">9hrs</div></td>
                  <td><div class="tblminwidth-50">10hrs</div></td>
                  <td><div class="tblminwidth-50">11hrs</div></td>
                  <td><div class="tblminwidth-50">8hrs</div></td>
                  <td><div class="tblminwidth-50">6hrs</div></td>
                  <td><div class="tblminwidth-50 selectedTd">8hrs</div></td>
                  <td><div class="tblminwidth-50">9hrs</div></td>
                  <td><div class="tblminwidth-50">10hrs</div></td>
                  <td><div class="tblminwidth-50">11hrs</div></td>
                  <td><div class="tblminwidth-50">8hrs</div></td>
                  <td><div class="tblminwidth-50">6hrs</div></td>
                </tr>

                <tr>
                    <td colspan="7">
                        <div class="clearfix subTable">
                            <table class="">
                                  <thead>
                                      <tr>
                                          <th>Clock In</th>
                                          <th>No. of hours</th>
                                          <th>Task</th>
                                          <th>Comment</th>
                                          <th>Clock Out</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                      <tr>
                                          <td><div class="tblminwidth-50">9.30pm</div></td>
                                          <td><div class="tblminwidth-50">7.30pm</div></td>
                                          <td><div class="tbldata-dotdot-big">HTML Screens</div></td>
                                          <td><div class="tbldata-dotdot-verybig">Working on clock in clock out Screens</div></td>
                                          <td><div class="tblminwidth-75">6.30pm</div></td>
                                        </tr>
                                  </tbody>
                            </table>
                        </div>
                    </td>
                </tr>

              </tbody>
            </table>
          </div>
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
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>
  <!-- Footer Area -->
  
  
  <script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});
function runPayrollregister() {
	$("#payrollRegister").toggle();
}
	// Close Advanced Search
	function closePop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").hide();
	}
	function openPop(){
	$(".advancedsearch-mask").show();
	$("div.cand-advsrch-popup").toggle();
	}
//Date Picker
$( function(){
	$("#frmDate").datepicker();
	$("#toDate").datepicker();
 });
 $(".dropdownTableDesig table tr td").click(function() {
	  $(this).find('a').parent().toggleClass("selectedTd");
	  $(this).parents().next('tr').toggle();
	});
</script>
  <jsp:include page="../masterFooter.jsp" />
  
  
  