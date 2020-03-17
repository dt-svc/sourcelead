	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<script src="${SourceLeadWeb_vmsUrl}/resources/js/bgcCandidate.js?staticTime=${staticTime}"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Background details</title>
   <section class="content-wraper">
      <section class="content-header clearfix">
        <div class="pull-left">
          <h2>Background Verification</h2>
        </div>
        <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo;  
         Background Verification</div>
      </section>
      <div id="emptyPopup"></div>
      <div class="contentareaInner-styles clearfix">
        <div class="timesheet_out" id="payrollRegister" style="display: block;">
          <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
          <div class="pull-left ml mt5">
             <h4>Candidates List</h4>
          </div>
          <ul class="pull-right list-unstyled list-inline">
                <!-- <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
                <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a> -->
                
                <!-- 17-jan-2020 
                bgc export pdf/excel
                -->
                <li class="positionRelative">
                <a href="javascript:void(0)" class="overIcon" title="Actions" onclick="bgc.newActions();"></a>
                <div class="newActPop" style=""> 
                <span>
                <a href="javascript:void(0)" onclick="bgc.exportBgcToPdf(null);" title="PDF">Export PDF</a> 
                <a href="javascript:void(0)" onclick="bgc.exportBgcToExcel(null);" title="Excel">Export Excel</a>
                 </span> 
                 </div>
              	</li>
                <!-- xx -->
                  <!-- <div class="newActPop" style=""> <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div> -->
                
                <li class="positionRelative">
                  <input class="repSearchBar" placeholder="Status/ID/Name" id="bgcListSearch" type="text">
                  <span class="repSearchIcon" onclick="bgc.getDynamicBgcList(null);"></span></li>
              </ul>
          </div>
              <div id="bgc-inProgress" class="clearfix bgcTemplateInformation-inner" style="display: block;">
              <!-- <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span> -->
              
            
                 <div id="dynamicBgcListViewDiv" class="project_wrap_table datatable_filter"></div>

        </div>
        </div>
        
        <!-- Content Area Ends--> 
        <div id="popUp"></div>
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
var vmsUrl = '${SourceLeadWeb_vmsUrl}';

$(document).ready(function() {
	  bgc.getDynamicBgcList(null);
})

$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	
</script>


