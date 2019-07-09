<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="${SourceLeadWeb_vmsUrl}/resources/js/immigration.js?staticTime=${staticTime}"></script>
<title>Insert title here</title>
<c:set var ="myLcaPage" value ="${myLcaPage}" scope="session"/>

 <section class="content-wraper content-wraperNew">
	        <div class="success_pop" id="selector" >
	       <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
			</div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2></h2>
      </div>
     
	   <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_vmsUrl}/immigration/viewLCAList">LCA List</a> &raquo; LCA Summary</div>
      
        <input type="hidden" id ="lcaId" value ="${lcaId}">
    </section>
    
	<section class="immigraTabsWrap">
	
  	<div class="immHeadWrap">
  		
		<div class="immigraHead">
			<ul class="immigraTabs pull-left">
              <li class="current"><a href="#lcaDetailsTab"><i class="fa fa-check-circle-o"></i>Details</a></li>
              <li><a href="#employeeTab"><i class="fa fa-check-circle-o"></i>Employee</a></li>
             
              <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
		</div>
		
		</div>

  
	  <div id="lcaDetailsTab" class="immigraTabs_inn">
	  <div id="lcaLoadingDetailsTab"></div>
		 	<jsp:include page="lcaViewDetailsTab.jsp"/>	  		  		  
	  </div>
	  
	  <div id="employeeTab" class="immigraTabs_inn">
	  <div id=""></div>
		<jsp:include page="employeeDetailsTab.jsp"/>	
		
	  </div>

	</section>
    
  </section>

<script>
// project side bar tabs
$(document).ready(function() {
	$("#lcaDetailsTab").show()
	$("#remove_immigration_pop").remove();
	$(".project_inn_mask").remove();
    $(".immigraTabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".immigraTabs_inn").not(tab).css("display", "none");
        if(tab=='#lcaDetailsTab'){
        	//showLoading("lcaLoadingDetailsTab");
      		///immigration.displayPetitionDetails(immigrationId);
        }else if(tab=='#employeeTab'){
        //	showLoading("lcaLoadingEmpTab");
      //  alert("AAAAA");
        	getEmployeeList(lcaId)//immigration.displayFamilyDetails(candidateId);
        }
       
        $(tab).fadeIn();
    });
	
	
});	
	</script>

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
	
</script> 
    <!-- Script for Linked Invoice pop -->

<script type="text/javascript">
function getEmployeeList(lcaId){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var lcaId = $("#lcaId").val();
	$.ajax({
        url: vmsUrl+"/immigration/getEmployeeByLcaId?lcaId="+lcaId,
        type: "GET",
        success: function (result) {
            $("#employeeTab").html(result);
        }
    });
}
</script>

<jsp:include page="../masterFooter.jsp" />
 
