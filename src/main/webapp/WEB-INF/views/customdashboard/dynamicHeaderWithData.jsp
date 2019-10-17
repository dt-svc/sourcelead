<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<!-- Content Area -->
<section class="content-wraper">

	<section class="content-header clearfix">
		<table id="example" class="display" cellspacing="0" width="100%"></table>
	</section>



</section>


<script>
var dataSet = [{
	  "First Name": "Rajashekar",
	  "Last Name": "Chelpuri",
	  "Full Name": "Raja Chelpuri",
	  "email": "rajasekhar@gmail.com"
	}, {
		"First Name": "subbu",
		"Last Name": "idapalapati",
		"Full Name": "subbu idapalapati",
		"email": "subb@gmail.com"
	}, {
	  "First Name": "Mahesh",
	  "Last Name": "Vannam",
	  "Full Name": "Vannam Mahesh",
	  "email": "mahesh@gmail.com",
	}, {
		"First Name": "Manoj",
		"Last Name": "Mahaveer",
		"Full Name": "Mahaveer Manoj",
		"email": "mahaveer@gmail.com"
	}];
	
	var my_columns = [];
	
	$.each( dataSet[0], function( key, value ) {
			var my_item = {};
			my_item.data = key;
			my_item.title = key;
			my_columns.push(my_item);
	});
	
	$(document).ready(function() {
	  $('#example').DataTable({
	    data: dataSet,
	    "columns": my_columns
	  });
	});



</script>

<jsp:include page="../masterFooter.jsp" />
