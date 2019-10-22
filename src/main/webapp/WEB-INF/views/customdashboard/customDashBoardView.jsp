<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery.mousewheel.js"></script> 
<script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery.jscrollpane.min.js"></script> 
<script>

function prepareDynamicTableGrid(subTabName,dataSet){
console.log(dataSet);
		var my_columns = [];
		dataSet = JSON.parse(dataSet);
		$.each( dataSet[0], function( key, value ) {
			console.log("key"+key);
			console.log("value"+value);
			var my_item = {};
			my_item.data = key;
			my_item.title = key;
			my_columns.push(my_item);
		});
console.log(my_columns);
		$('#'+subTabName).DataTable({
			data: dataSet,
			"columns": my_columns
	   });
}
</script>

<section class="content-wraper">

    <section class="content-header clearfix">
      <div class="pull-left"><h2>Dashboard View 
          <span class="pull-right addPop"><a class="newAdBtn ml5" href="javascript:void(0)" title="Add">+ Add</a></span>
        </h2></div>
      <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/settings">Settings</a> &raquo; Dashboard list</div>
    </section>
    
<div class="row clearfix" id="configItemsList">

     	<c:forEach var="configureDashBoard" items="${dashBoardConfData}" >
    			 <div class="col-md-6 mb">
				        <div class="newDashBg mb10">
				            <div class="newDashhead clearfix">
				                <a href="javascript:void(0)" class="pull-left">${configureDashBoard.displayTabName }</a>
				                <span class="pull-right">
				                  <i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/edit.svg"></i>
				                  <i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/trash.svg">
				                  </i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/drag.svg"></i>
				                </span>
				            </div>
				             <table id="${configureDashBoard.subTabName}"></table>
				               <script>
				                    prepareDynamicTableGrid('${configureDashBoard.subTabName}', '${configureDashBoard.jsonStringData}');
				                    		
				                    		
				                    				                    		
				                     </script>
				        </div>
    			</div>
		</c:forEach> 
    <%-- <div class="col-md-6 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
                <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 2</a>
                <span class="pull-right">
                  <i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/edit.svg"></i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/trash.svg"></i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/drag.svg"></i>
                </span>
            </div>
              <p>*********************Text for Test************************* </p>
        </div>
    </div>
    <div class="col-md-12 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
                <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 3</a>
                <span class="pull-right">
                  <i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/edit.svg"></i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/trash.svg"></i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/drag.svg"></i>
                </span>
               
            </div>
             <p>***********************Text for Test*************** </p>
        </div>
    </div>    
    <div class="col-md-6 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
               <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 4</a>
                <span class="pull-right">
                  <i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/edit.svg"></i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/trash.svg"></i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/drag.svg"></i>
                </span>
            </div>
              <p>***********************Text for Test*************************** </p>
        </div>
    </div>
    <div class="col-md-6 mb">
        <div class="newDashBg mb10">
            <div class="newDashhead clearfix">
                <a href="javascript:void(0)" class="pull-left">Dashboard heading text... 5</a>
                <span class="pull-right">
                  <i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/edit.svg"></i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/trash.svg"></i><i><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/drag.svg"></i>
                </span>
            </div>
              <p>****************Text for Test*************************</p>
        </div>
    </div>    --%>
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


<!--    Dashboard Draging Function    -->
<script>
$(function() {
  $("#configItemsList").sortable();
  $("#configItemsList").on("sortdeactivate", function( event, ui ) {
  } );
  $("#configItemsList").disableSelection();
  });
</script>
<jsp:include page="../masterFooter.jsp" />
  