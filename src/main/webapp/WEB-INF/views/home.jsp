 
<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
    </section>
    
  </section>
  <!-- Content Area Ends-->  
<jsp:include page="masterFooter.jsp" />
<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>
<script type="text/javascript">
        $('#project_form')
           .submit(function(e) {
        	    e.preventDefault(); 
        	    var frm = $('#project_form').serializeFormJSON();
        		var con = JSON.stringify(frm);
                $.ajax({
            	      type: "GET",
                  	  url: "http://localhost:8080/sourcelead/saveProject?json="+con,
                  	  data:con
                });
            
            });
</script>

