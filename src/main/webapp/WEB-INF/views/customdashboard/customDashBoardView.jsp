<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left"><h2>Dashboard View 
          <span class="pull-right addPop"><a class="newAdBtn ml5" href="javascript:void(0)" title="Add">+ Add</a></span>
        </h2></div>
      <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/settings">Settings</a> &raquo; Dashboard list</div>
    </section>
    
<div class="row clearfix" id="configItemsList">
    <div class="col-md-6 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
                <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 1</a>
                <span class="pull-right">
                  <i><img src="../images/edit.svg"></i><i><img src="../images/trash.svg"></i><i><img src="../images/drag.svg"></i>
                </span>
            </div>
        </div>
    </div>
    <div class="col-md-6 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
                <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 2</a>
                <span class="pull-right">
                  <i><img src="../images/edit.svg"></i><i><img src="../images/trash.svg"></i><i><img src="../images/drag.svg"></i>
                </span>
            </div>
        </div>
    </div>
    <div class="col-md-12 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
                <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 3</a>
                <span class="pull-right">
                  <i><img src="../images/edit.svg"></i><i><img src="../images/trash.svg"></i><i><img src="../images/drag.svg"></i>
                </span>
            </div>
        </div>
    </div>    
    <div class="col-md-6 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
               <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 4</a>
                <span class="pull-right">
                  <i><img src="../images/edit.svg"></i><i><img src="../images/trash.svg"></i><i><img src="../images/drag.svg"></i>
                </span>
            </div>
        </div>
    </div>
    <div class="col-md-6 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
                <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 5</a>
                <span class="pull-right">
                  <i><img src="../images/edit.svg"></i><i><img src="../images/trash.svg"></i><i><img src="../images/drag.svg"></i>
                </span>
            </div>
        </div>
    </div>  
</div>
	
  </section>
  
  <script>
$(document).ready(function(){
    $('#tab-content div').hide();
    $('#tab-content div:first').show();

    $('#nav li').click(function() {
        $('#nav li a').removeClass("active");
        $(this).find('a').addClass("active");
        $('#tab-content div').hide();

        var indexer = $(this).index(); 
        $('#tab-content div:eq(' + indexer + ')').fadeIn(); 
    });  
});

</script>
<jsp:include page="../masterFooter.jsp" />
  