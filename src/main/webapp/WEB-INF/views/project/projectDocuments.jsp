<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png"
	href="resources/images/fav.png" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- this script is for Project Details functionality at Projects screen -->
<script>
	function projectDocuments(projectId) {

		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "GET",
					contentType : "application/json; charset=utf-8",
					url : sourceleadUrl
							+ "/projectDocuments/getProjectDocumentsById?${_csrf.parameterName}=${_csrf.token}&projectId="
							+ projectId,
					success : function(data) {
						var result = "<table>";
						for (var i = 0; i < data.length; i++) {
							var doc = data[i];
							result+= "<TR><TD>"+doc.fileName+"</TD></TR>";
						}
						$("#tab-1").empty();
						$("#tab-2").empty();
						$("#tab-3").empty();
						$("#tab-4").append(result);
						$("#tab-4").show();

						$(".project-tabs-menu a").click(
								function(event) {
									event.preventDefault();
									$(this).parent().addClass("current");
									$(this).parent().siblings().removeClass(
											"current");
									var tab = $(this).attr("href");
									$(".project_tabs_inn").not(tab).css(
											"display", "none");
									$(tab).fadeIn();
								});

					}
				});
	}

	
</script>








