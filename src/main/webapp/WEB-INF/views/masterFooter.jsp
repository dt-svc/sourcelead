<!-- Footer Area -->
  <footer class="footer-area clearfix">
  
  <!-- <div class="addthis_inline_share_toolbox" data-url='http://www.google.com' data-title='google' data-desc='google'></div>
<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-596e031897dcfb4a"></script> -->
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
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
</body>
</html>