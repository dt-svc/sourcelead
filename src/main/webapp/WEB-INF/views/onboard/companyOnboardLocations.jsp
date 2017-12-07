<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="clearfix compOnBorLoca">
   <div id="registeredOrgs"></div> 
</div>



<script>
function loadRegisteredOrgs(){
	var result = "";
	result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-building'></i><h3>Orgs List is Empty</h3></div>";
	$('#registeredOrgs').append(result);
	$("#loading").removeAttr("style");
}
</script>