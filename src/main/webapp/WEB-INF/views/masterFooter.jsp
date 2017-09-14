<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</div>
<!-- Footer Area -->
  <footer class="footer-area clearfix">
    <div class="copyright">@ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div>
  </footer>
  <div class="alert alert-danger" role="alert" style='display:none;'>
	<div>
		<strong>Alert</strong>
		<div class="close" data-type="2"></div>
	</div>
	<span></span>
  </div>
  <div class="alert alert-success" role="alert" style='display:none;'>
	<div>
		<strong>Message</strong>
		<div class="close" data-type="1"></div>
	</div>
	<span></span>
  </div>
  <!-- Footer Area --> 
  <script type="text/javascript" src='resources/js/slead-common.js'></script>
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
<!-- <script>
$(document).tooltip({
position: {
my: "center bottom",
at: "center top-10",
collision: "flip",
using: function( position, feedback ) {
$( this ).addClass( feedback.vertical )
.css( position );
}
}
});
</script> -->
<c:if test='${viewPage == "orgChart"}'>
	<script src="resources/js/jquery.orgchart.js"></script>
	<link href="resources/css/jquery.orgchart.css" type='text/css' rel='stylesheet'/>
</c:if>
<script src="resources/js/cropper.min.js"></script>
<link rel="stylesheet" href="resources/css/cropper.min.css" type="text/css" />
<div id='imagecrop' class='post_popup'>
	
<div class="post_popup_img">
    <div class="post_popup_img_main">
        <div class="img-container">
            <img src="" alt="Picture">
        </div>
    </div>
</div>
<div class="post_popup_comments" id="style-4">
    <div class="homeless_wrap_posts homeless_wrap_posts_pop">
        <div class="homeless_posts_head">
            <div class="homeless_posts_head_top homeless_posts_head_top1">
                <div class='jcrop-preview1' id='previewparent'>
                    <img src="" id="preview" />
                </div>
            </div>
            <div class="homeless_posts_like_bar">
                <ul>
                    <li id="applyImage">Apply</li>
                    <li id="cancelImage">Cancel</li>
                    <div class="clear"></div>
                </ul>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>