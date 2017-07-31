<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Footer Area -->
  <footer class="footer-area clearfix">
  
    <div class="copyright">@ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div>
  </footer>
  <!-- Footer Area --> 
  <script type="text/javascript">
       //window.scroll for Side Nav
               $(function() {
   var sideBar = $(".side-bar");
   $(window).scroll(function() {
       var scroll = $(window).scrollTop();
       if (scroll >= 20) {
           sideBar.addClass("side-nav-fixed");
       } else {
           sideBar.removeClass("side-nav-fixed");
       }
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
				});
</script>
<c:if test='${viewPage == "orgChart"}'>
	<script src="resources/js/jquery.orgchart.js"></script>
	<link href="resources/css/jquery.orgchart.css" type='text/css' rel='stylesheet'/>
</c:if>
</body>
</html>