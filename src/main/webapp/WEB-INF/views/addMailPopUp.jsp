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
			
				<div class="col-md-12 minHeight">
                <label>Email555<span class="text-danger">&nbsp;*</span></label>
                <input class="form-control" value="" name="email" id="fromMail"  field-name="fromMail" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter EMail" type="text"> 
                 
            </div>
            
            <div class="col-md-12 minHeight">
            
<!--             
          <select class="chosen-select form-control" multiple data-placeholder="Select Module" name="emailModules" id="emailTabs">
           -->
           <select multiple class="form-control" name="education" data-placeholder="Select Education Options" id="education" data-validation-optional="true">
                
           <c:forEach var="option" items="${modulesList}">
				<option value="${option.id}">${option.tabName}</option>
			</c:forEach>
          </select>
        </div>
		        <div class="clearfix"></div>
			</div>
			<div class="modal-footer clearfix">
				<div>
					<button type="button" class="btn btn-success btnSuccess mr" onClick="closeEmailNotifications();saveConfFromMail();">Save</button>
					<button type="button" class="btn btn-Default">Cancel</button>
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
    $("#education").trigger("chosen:updated");
    $("#education").chosen();
    $("#education").trigger("chosen:updated");
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