<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
</div>
<!-- Footer Area -->
<footer class="footer-area clearfix">
    <div class="col-md-6 text-left">
    	&copy; 2017 SourceLead<sup class="paddingRight paddingLeft">&reg;</sup>, All Rights Reserved
    </div>
            <div class='col-md-6 text-right'>
                  <a href="/terms-of-use.html" target="blank" title="Terms of Service">Terms of Service</a> <small class="text-muted">|</small> <a href="/privacy-policy.html" target="blank" title="Privacy Policy">Privacy Policy</a>
            </div>
</footer>
  
  <div class='file_mask'>
  </div>
  <!-- <div class="alert alert-danger" role="alert" style='display:none;'>
	<div>
		<strong>Alert</strong>
		<div class="close" data-type="2"></div>
	</div>
	<span></span>
  </div>-->
  <div class="alert alert-success" role="alert" style='display:none;'>
	<div>
		<strong>Message</strong>
		<div class="close" data-type="1"></div>
	</div>
	<span></span>
  </div> 
  <style type='text/css'>
  	.file_mask {
  		display: none;
  		position: fixed;
  		top: 0;
  		left: 0;
  		right: 0;
  		bottom: 0;
  		background-color: black;
  		opacity: .4;
  		background-image: url('/resources/images/loading.gif');
  		background-position: center;
  		background-size: 50px;
  		background-repeat: no-repeat;
  		z-index: 100000;
  	}
  </style>
  <!-- Footer Area --> 
  <script type="text/javascript" src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>
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
	<script src="/resources/js/jquery.orgchart.js"></script>
	<link href="/resources/css/jquery.orgchart.css" type='text/css' rel='stylesheet'/>
</c:if>

<script src="/resources/js/cropper.min.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/cropper.min.css" type="text/css" />
<div id='imagecrop' class='post_popup'></div>
<script type="text/javascript" src="https://cdn.ywxi.net/js/1.js" async></script>
</body>
</html>