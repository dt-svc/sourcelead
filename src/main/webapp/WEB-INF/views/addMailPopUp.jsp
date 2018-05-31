<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- -email popup statrs -->

<form:form id="email_from_form" name="email_from_form"  method="post" >
<div id="emailMailFrom">
	<div class="modal-dialog">
		<div class="modal-content clearfix">
			<div class="modal-header">
				<a href="javascript:void(0)" class="close" onClick="closeEmailNotifications()">&times;</a>
				<h4 class="modal-title">Add from Email</h4>
			</div>
			<div class="modal-body">
			
			<c:if test="${ empty emailFromMail.email}">
			<div class="col-md-12 minHeight">
                <label>Email<span class="text-danger">&nbsp;*</span></label>
                <input class="form-control" value="" name="email" id="fromMail"  field-name="fromMail" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter EMail" type="text"> 
                </div>
			</c:if>
			<c:if test="${! empty emailFromMail.email}">
				<div class="col-md-12 minHeight">
                <label>Email<span class="text-danger">&nbsp;*</span></label>
                <input class="form-control" value="${emailFromMail.email}" name="email" id="fromMail"  field-name="fromMail" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter EMail" type="text"> 
                </div>
			</c:if>
			
			
            <div class="col-md-12 minHeight">
             <label>Module Names<span class="text-danger">&nbsp;*</span></label>
           <select multiple class="form-control" name="emailModules" data-placeholder="Select Module Options" id="emailModules" field-name="Module name" data-validation="required">
                
			<c:forEach var="education" items="${modulesList}">
               		<c:set var = "NUM" value = "${0}"/>
               		<c:forEach var="existedEducation" items="${emailFromMail.emailModules}">
					    <c:if test="${existedEducation eq education.tabName}">
					    	<c:set var = "NUM" value = "${1}"/>
					       <option  selected value="${education.id}">${education.tabName}</option>
					    </c:if>
					</c:forEach>
					<c:if test="${NUM == 0}">
						<option  value="${education.id}">${education.tabName}</option>
					</c:if>
                </c:forEach>
			
			
          </select>
        </div>
		        <div class="clearfix"></div>
			</div>
			<div class="modal-footer clearfix">
				<div>
				<input type="hidden" name="id" id=id value="${emailFromMail.id}"/>
					<button type="button" id="save_title" class="btn btn-primary btnsStyles" onClick="saveConfFromMail();">Save</button>
					<button type="button" class="btn btn-default ml" onClick="closeEmailNotifications();">Cancel</button>
				</div>
			</div>
		</div>
	</div>
</div>
 <div id="deleteFromMailDiv" class="datatable_filter"> </div>

</form:form>
<script>
$(function(){
    var config = {
		  '.chosen-select'           : {},
		  '.chosen-select-deselect'  : {allow_single_deselect:true},
		  '.chosen-select-no-single' : {disable_search_threshold:10},
		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		  '.chosen-select-width'     : {width:"95%"}
		}
    $("#emailModules").trigger("chosen:updated");
    $("#emailModules").chosen();
    $("#emailModules").trigger("chosen:updated");
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});

function closeEmailNotifications(){
	$(".sideMask").fadeOut();
	$(".popOut").fadeOut();
}
</script>