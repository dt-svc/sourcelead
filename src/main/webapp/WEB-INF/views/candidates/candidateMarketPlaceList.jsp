	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script> -->
<!-- <script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script> -->
<%-- <c:set var ="myImmigrationPage" value = "Y" scope="session"/> --%>
<title>Welcome to Source Lead :: Marketplace List</title>
    <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Marketplace List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/candidatesList'/>">Candidates</a> &raquo; Marketplace</div>
    </section>
    <div class="timesheet_out">
      <div class="tablelist-wrap">
        
        <div class="timesheet_head minHeight40">
          <div class="clearfix"></div>
          <div class="col-md-6 mt10">
          
          <div class="row">
          		<div class="col-md-3">
					<button type="button" class="btn btn-primary btn-block" onClick="setPrice();">
						<i class="fa fa-dollar paddingRight"></i>Set Price
					</button>
          		</div>
          		<div class="col-md-9 customSetprice">
          				<div class="form-inline">
          					<div class="form-group">
          						<input type="text" class="form-control" value="" placeholder="Enter">
          					</div>
          					<div class="form-group dropdown-select bgPosition">
          						<select>
								<option selected>INR</option>
								<option>AUD</option>
								<option>USD</option>
								<option>GBP</option>
								<option>SGD</option>
								<option>EUR</option>
								</select>
          					</div>
          					<div class="form-group">
          						<button type="button" class="btn btn-primary">Set</button>
          					</div>
          				</div>			
          		</div>
          </div>
          
          </div>
          <div class="remove_search">
            <input type="text" placeholder="Search"/>
          </div>
        </div>
        <div class="clearfix mt"></div>
         <div class="styleTables" id="marketPlaceTableDiv">
          <!-- <table>
            <thead>
              <tr>
                <th>All</th>
                <th>Id</th>
                <th>Name</th>
                <th>Email</th>
                <th>Primary Skills</th>
                <th>Experience</th>
                <th>Current Rate</th>
                <th>Expected Rate</th>
                <th>Price</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>
                  <div class="tblminwidth-30">
                  	<div class="checkbox-new pull-left ml">
                      <input id="price" class="checkbox-custom" name="price" type="checkbox">
                      <label for="price" class="checkbox-custom-label"></label>
                    </div>
                  </div>
                </td>
                <td>
                  <div class="tblminwidth-30">0013005</div>
                </td>
                <td>
                  <div class="tbldata-dotdot-100">Rajakumar Santoshi</div>
                </td>
                <td>
                  <div class="tbldata-dotdot-100">vannam@domain.com</div>
                </td>
                <td>
                  <div class="tbldata-dotdot-100">Java</div>
                </td>
                <td>
                  <div class="tblminwidth-50">3 years</div>
                </td>
                <td>
                 <div class="tblminwidth-30">100</div>
                </td>
                <td>
                 <div class="tblminwidth-30">200</div>
                </td>
                <td>
                 <div class="tblminwidth-75 clearfix">
                 	<div class="dropdown-inputbox">
                 			<input type="text" value="" placeholder="Enter" class="form-control input-sm">
                 		<select class="form-control input-sm sel">
							<option selected>INR</option>
							<option>AUD</option>
							<option>USD</option>
							<option>GBP</option>
							<option>SGD</option>
							<option>EUR</option>
						</select>
                 	</div>
                 </div>
                </td>

              </tr>
            </tbody>
          </table> -->
        </div>
        <!-- <div class="clearfix">
         <nav class="pull-right mr">
          <ul id="paging" class="pagination mb0 mt">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
        </div> -->
        <div class="footerstyelsNew mt clearfix">
        <div class="col-lg-12">
        <div class="pull-right">
        <input type="button" class="btn btn-success btnSuccess ml" value="Share">
        <input type="button" class="btn btn-default ml" value="Cancel">
        </div>
        </div>
        </div>
          
      </div>
    </div>
  </section>
<script type="text/javascript">
/*function funPrice(event){
	var price = event.target.text || event.srcElement.text;
	$(".selectPrice").text(price);
	
}*/

$(document).ready(function() {
	loadMarketPlaceDraftData();
});

function loadMarketPlaceDraftData(){
	
	$("#marketPlaceTableDiv").empty();
	var result = "";
	result = result + "<table id='marketPlaceTable'><thead>";
	result = result + "<tr><th>All</th>";
	result = result + "<th>Id</th>";
	result = result + "<th>Name</th>";
	result = result + "<th>Email</th>";
	result = result + "<th>Primary Skills</th>";
	result = result + "<th>Experience</th>";
	result = result + "<th>Current Rate</th>";
	result = result + "<th>Expected Rate</th>";
	result = result + "<th>Price</th>";
	result = result + "</tr>";
	result = result + "</thead>";
	result = result +"<tbody>"; 
    result = result +" </tbody>";
    result = result +"</table>";
    $("#marketPlaceTableDiv").append(result);
    marketPlacePagination();
	/* $.ajax({
		type : "GET",
		url : sourceleadUrl + "/getMarketPlaceDraftList",
		success : function(result) {
			
		}
	}); */
}

function marketPlacePagination(){
	alert("${movedCandidateIds}");
	var colIndex = 0;
	/*$.fn.dataTable.moment( 'DD-MM-YYYY' );*/
    $('#marketPlaceTable').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "searchHighlight": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: sourceleadUrl + "/getMarketPlaceCandidateMovedList",
            //data: "candidateIds = ${movedCandidateIds}",
            method: 'POST',
            pages: 10
        }),
        "columns": [
        	{ "data": "id" },
            { "data": "generatedId"},
            { "data": "firstName"},
            { "data": "email" },
            { "data": "primarySkill" },
            { "data": "experienceMonths"},
            { "data": "currentRate" },
            { "data": "expectedRate" },
            { "data": "price"}
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"sortable": false,
    	  	"data": "id",
    	  	"render": function(data, type, full, meta) {
    	  		alert("full.id :"+full.id);
    	  		 var result = "";
	        		result = "<input type='hidden' id='candidateId' value='"+full.id+"'/>";
    	  		 return result; 
    	  	}
        	},  {
	    	  	"targets": colIndex++,
	    	  	//"className": "tbldata-dotdot_50",
	    	  	"data": "generatedId",
	    	  	"sortable": false,
	    	  	"render": function(data, type, full, meta) {
	    	  		var result = "";
	    	  		if(full.generatedId != null && full.generatedId != '' && full.generatedId != "undefined"){
	            	  		result = result +"<div class='tblminwidth-30'><span title='"+full.generatedId+"'>"+full.generatedId+"</span></div>";
	              	}else{
	            	  		result = result +"<div>--</div>";
	             	}
		              return  result;
	    	  	}
	      }  ,{
            "targets": colIndex++,
            "data": "firstName",
            "render": function ( data, type, full, meta ) {
            		var result = "";
            		var lastName = "";
            		var middleName = "";
            		if(full.lastName != null && full.lastName != "" && full.lastName != 'undefined'){
	            		lastName = full.lastName;
	            	}else{
	            		lastName = "";
	            	}
            		if(full.middleName != null && full.middleName != "" && full.middleName != 'undefined'){
	            		middleName = full.middleName;
	            	}else{
	            		middleName = "";
	            	}
	               result = result +"  <td><div class='tbldata-dotdot-100' onclick='#'><span title='"+full.firstName+" "+middleName+" "+lastName+"'>"+full.firstName+" "+middleName+" "+lastName+"</span></div></td>";
	              return result;
            }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "email",
	    	  	"render": function(data, type, opt, meta) {
	    	  		return "<div ><span title='"+opt.email+"'>"+opt.email+"</span></div>";
	    	  	}
	    	  },{
              "targets": colIndex++,
              "data": "primarySkill",
              "render": function ( data, type, full, meta ) {
              		var result = "";
              		if(full.primarySkill != null && full.primarySkill != '' && full.primarySkill != "undefined"){
  	            	  		result = result +"<div><span title='"+full.primarySkill+"'>"+full.primarySkill+"</span></div>";
  	              	}else{
  	            	  		result = result +"<div>--</div>";
  	             	}
  	              return result;
              }
            }, {
                "targets": colIndex++,
                "data": "experience",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		var experienceYears='';
                		if(opt.experienceYears == null || opt.experienceYears == "null" || opt.experienceYears == '' || opt.experienceYears == "undefined" || opt.experienceYears == 0){
                			experienceYears = 0
   	             	 	}else{
   	             	 		experienceYears = opt.experienceYears
   	             	 	}
                		var experienceMonths='';
                		alert(opt.experienceYears+" : "+opt.experienceMonths);
                		if(opt.experienceMonths == null || opt.experienceMonths == "null" || opt.experienceMonths == '' || opt.experienceMonths == "undefined" || opt.experienceMonths == 0){
                			experienceMonths = 0
   	             	 	}else{
   	             	 		experienceMonths = opt.experienceMonths
   	             	 	}
                		var experience = experienceYears+" Years, "+experienceMonths+" Months";
                		result = result + "<td><div class='tblminwidth-50' onclick='#'><span title='"+experience+"'>"+experience+"</span></div></td>";
   	            	  
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "currentRate",
	    	  	"sortable": false,
	    	  	"render": function(data, type, full, meta) {
	    	  		var currentRate = "";
	    	  		if(full.currentRate != null && full.currentRate != 'undefined' && full.currentRate != ""){
	    	  			currentRate = full.currentRate;
		            }else{
		            	currentRate = "--";
		            }
	    	  		return "<td><div class='tblminwidth-30' onclick='#'><span title='"+currentRate+"'>"+currentRate+"</span></div></td>";
	    	  	}
	    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "expectedRate",
		    	  	"sortable": false,
		    	  	"render": function(data, type, full, meta) {
		    	  		var expectedRate = "";
		    	  		if(full.expectedRate != null && full.expectedRate != 'undefined' && full.expectedRate != ""){
		    	  			expectedRate = full.expectedRate;
			            }else{
			            	expectedRate = "--";
			            }
		    	  		return "<td><div class='tblminwidth-30' onclick='#'><span title='"+expectedRate+"'>"+expectedRate+"</span></div></td>";
		    	  		
		    	  		
		    	  	}
		    	  }, {
	    	    	  	"targets": colIndex++,
	    	    	  	"data": "price",
	    	    		"sortable": false,
	    	    	  	"render": function(data, type, opt, meta) {
	    	    	  		var result = "";
	    	    	  		result = result + '<td><div class="tblminwidth-75 clearfix"><div class="dropdown-inputbox"><input type="text" value="" placeholder="Enter" class="form-control input-sm"><select class="form-control input-sm sel">';
	    	    	  		result = result + '<option selected>INR</option><option>AUD</option><option>USD</option><option>GBP</option><option>SGD</option><option>EUR</option></select></div></div></td>';
	    	    	  		return result;
	    	    	  	}
    	    	  } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	//"aaSorting": [[9,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
	     /* drawCallback: function(){
   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
   	    	candidateSearchHighlight();
	         }); 
	   } */
    } );
    // $("#candidatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    //$("input[type=search]").hide();
    //$("#dataTableSearch").keyup(function(e) {
    	//   if(e.which === 13) {
		//candidateList();
		//candidateSearchHighlight();
    	 //  }
    	//}); 
}

$(".funPrice").on("click", "li", function(){
	var x = $(this).text();
	$(this).parents(".selectPriceli").find(".selectPrice").text(x);
});
function setPrice(){
	$(".customSetprice").toggle();
}
</script>	
