<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 
 
 
 <section class="content-wraper content-wraperNew">
   
<section class="content-header clearfix cusFieldHead cusFieldHeadFixed">
      <div class="pull-left positionRelative"><h2>Custom Fields</h2>
		  <select class="form-control selectpicker"id="actionId"  onChange="getFormFieldSections();">
			  <c:forEach var="entity" items="${Sourcelead_Entities}">
			  	  <c:if test="${entity.actionName eq 'Add Job' or entity.actionName eq 'Add Candidate' or entity.actionName eq 'Add Associate' or entity.actionName eq'Add Client' or entity.actionName eq'Add Vendor'or entity.actionName eq'Add Project'or entity.actionName eq'Add Project Tasks'or entity.actionName eq'Add Asset'}"> 
			  			<option value="${entity.id}">${entity.actionName}</option>
			  	  </c:if> 
			  </c:forEach>
		  </select>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/app/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Custom Fields</div>
	<div class="clearfix"></div> 
</section>
   
   
 <section class="immigraTabsWrap immigraTabsWrapFixed">
	 
	  <div id="formFieldSectionData">
        <jsp:include page="customFormSections.jsp"></jsp:include>	  
	  </div>
</section>
</section>
  
  
   
  
<script>
$(document).ready(function() {
	getFormFieldSections();
});
</script>
  
  <script>
  function getFormFieldSections(){
	  var actionId =  $('#actionId').val();
	  $.ajax({
		type : "POST" , 
			 url: sourceleadUrl+"/getFormSectionsByActionId?actionId="+actionId,
			 success: function(data) {
				$("#formFieldSectionData").empty();
				$("#formFieldSectionData").append(data);
			 }
	  })
	  
  }
  </script>
  
  <script>
  function addFieldPopUp(sectionId){
			$(".mask").fadeIn(200);
			$(".newCustomFieldPopup").fadeIn(100);
			$('#minDate').datepicker({changeMonth : true,changeYear : true}); 
			$('#maxDate').datepicker({changeMonth : true,changeYear : true}); 
		$(".popClose").click(function(){
			$(".mask").fadeOut(200);
			$(".newCustomFieldPopup").fadeOut(100);
			flushPopupData();
		});
  }
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
       /*  $(document).ready(function(){
        $("#cd").click(function(){
        $(".clientDetails").toggleClass('hidden', 500);
        });

        }); */
    </script> 
  
  
  