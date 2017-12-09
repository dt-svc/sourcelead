<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 
 
 
 <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix cusFieldHead">
      <div class="pull-left positionRelative"><h2>Custom Fields</h2>
		  <select class="form-control selectpicker"id="SectionId"  onChange="setSectionFieldsValues();">
			  <c:forEach var="sectionFieldNames" items="${sectionFeildName}">
			  <option value="${sectionFieldNames.id}">${sectionFieldNames.sectionName}</option>
			  </c:forEach>
		  </select>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Custom Fields</div>
	<div class="clearfix"></div>
    </section>
    
	<section class="immigraTabsWrap">
	  <div class="cusFielInn">
		<div class="cusFielInnHead clearfix">
	  		<h2>Job Details</h2>
		  	<a href="javascript:void(0)" id="addField"><i class="glyphicon glyphicon-plus"></i> Add Field</a> 
		</div>
	  	<div class="cusFielTable" id="cusFielTable">
			<jsp:include page="customSectionFields.jsp"></jsp:include> 
		</div>
		</div>   
	</section>
</section>
  
   <script>
$(document).ready(function() {
	setSectionFieldsValues();
	alert("hi");
});
</script>
  
  <script>
  function setSectionFieldsValues(){
	  var sectionFieldId =  $('#SectionId').val();
	  alert(sectionFieldId);
	  alert(sourceleadUrl);
	  $.ajax({
		type : "POST" , 
			 url: sourceleadUrl+"/getFormFieldsBysectionId?&sectionFieldId="+sectionFieldId,
			 success: function(data) {
				$("#cusFielTable").empty();
				$("#cusFielTable").append(data);
			 }
	  })
	  
  }
  
  
  </script>
  
  
  