	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Background details</title>
   <section class="content-wraper">
      <section class="content-header clearfix">
        <div class="pull-left">
          <h2>Background Verification</h2>
        </div>
        <div class="pull-right brud-crum">Home » Payhead List » BGC</div>
      </section>
      <div class="contentareaInner-styles clearfix">
        <div class="timesheet_out" id="payrollRegister" style="display: block;">
          <div class="clearfix">
            <div class="positionRelative reportHead clearfix mb5">
                <ul class="project-tabs-menu projecttabs-custom clearfix productListtab pull-left ml bgonProgressTab">
                    <li class="current" id="self">
                      <a href="#bgc-inProgress" class="pull-left">In progress</a>
                      <span class="label label-warning mt10 pull-left">30%</span>
                    </li>
                    <li id="public">
                        <a href="#bgc-Completed" class="pull-left">Completed</a>
                        <span class="label label-success mt10 pull-left">100%</span>
                    </li>
                  </ul>
                </div>

              <div id="bgc-inProgress" class="clearfix bgcTemplateInformation-inner" style="display: block;">
              <!-- <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span> -->
              <ul class="pull-right list-unstyled list-inline">
                <li><a class="newAdBtn mr5" href="bgc-initiate.html" title="Add">Initiate</a></li>
                <!-- <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
                <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a> -->
                  <div class="newActPop" style=""> <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div>
                
                <li class="positionRelative">
                  <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                  <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
              </ul>
            
            <div class="require_wrap_table tableFirstSecond clearfix">
              <table>
                <thead>
                  <tr>
                    <th>Candidate Id</th>
                    <th>Name</th>
                    <th>Education / Academic <br> credentials</th>
                    <th>Prior Employment</th>
                    <th>Personal Information</th>
                    <th>Helath</th>
                    <th>Criminal</th>
                    <th>Credit</th>
                    <th>Progress</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td><div class="tblminwidth-75">CAN001</div></td>
                    <td><div class="tblminwidth-75"><a href="/vms/bgc/onProgressView">Rajkumar Santhoshi</a></div></td>
                    <td><div class="tbldata-dotdot-vsm">OK</div></td>
                    <td><div class="tbldata-dotdot-vsm">OK</div></td>
                    <td><div class="tblminwidth-50">OK</div></td>
                    <td><div class="tblminwidth-50">OK</div></td>
                    <td><div class="tblminwidth-50">Not verified</div></td>
                    <td><div class="tblminwidth-50">Not verified</div></td>
                    <td><div class="tblminwidth-50 text-primary">On Progress</div></td>
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

          <div class="bgcTemplateInformation-inner clearfix" id="bgc-Completed" style="display:none">
            <div class="col-md-12">  
            <h4>Completed</h4>
          </div>
          </div>
        </div>
        </div>
        
        <!-- Content Area Ends--> 
      </div>
    </section>
  <!-- Content Area -->
<jsp:include page="../masterFooter.jsp" />
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
$("#bgc").click(function(){
	$(this).find('ul').slideToggle();
});

$(document).ready(function() {
$("#bgc-inProgress").show();
  $(".bgonProgressTab a").click(function() {
    $(this).parent().addClass("current").siblings().removeClass();
    var bgcTab = $(this).attr("href");
    $(".bgcTemplateInformation-inner").not(bgcTab).css("display","none");
    $(bgcTab).fadeIn();
  })
})
</script>


