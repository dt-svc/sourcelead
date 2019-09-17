<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Product List</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Product List</div>
    </section>
    <div class="project_wrap clearfix backgroundWhite">
      
      <div class="clearfix mt">
          <ul class="project-tabs-menu projecttabs-custom clearfix productListtab">
            <li class="current" id="self">
              <a href="javascript:void(0)">Self</a>
            </li>
            <li id="public" >
                <a href="javascript:void(0)">Public</a>
            </li>
            <li >
                <a class="newAdBtn mr5" href="addproducts.html" title="Add">+ Add Items</a>
                </li>
                <li > 
                  <a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a>
              </li>
                <li >
                    <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                    <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
                </li>
            </ul>
          
      </div>
      
        <div class="clearfix">
            <div id="selfDiv">


                <div class="productList clearfix">
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product3.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product3.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
            </div>



            </div>
            <div id="publicDiv" style="display: none">


                <div class="productList clearfix">
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product3.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product3.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
                <a href="product-view.html"><div class="productListItem">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    <div class="productText">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        <a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div></a>
            </div>


            </div>
        </div>

    </div>
  </section>

<div class="advancedsearch-mask" onclick="closeAdsrch()" style="display: none;"></div>

<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small">
    <h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a> </h5>
    <div class="addInner">
      <div class="row">
        <div class="col-md-4 mb positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myManagersInput" onkeyup="myManagersFunction()" placeholder="Location" title="Type in a managers" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL">
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="1883" type="checkbox">
                <label>Hyderabad</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Pune</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Mumbai</label>
              </li>
             <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Banglore</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Hyderabad</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Visakhapatnam</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Noida</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Delhi NCR</label>
              </li>

            </ul>
          </div>
        </div>
        <div class="col-md-4 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myRolesInput" onkeyup="myRolesFunction()" placeholder="By Date" title="Type in a roles" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="">
              <li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="checkbox">
                <label>This Week</label>
              </li>              <li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="checkbox">
                <label>Month</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>Year</label>
              </li>
                        </ul>
          </div>
        </div>

        <div class="col-md-4 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myRolesInput" onkeyup="myRolesFunction()" placeholder="By Price" title="Type in a roles" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="">
              <li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="checkbox">
                <label>500 > </label>
              </li>              <li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="checkbox">
                <label>500 > 5000</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>5000 > 10000</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>10000 > </label>
              </li>
                        </ul>
          </div>
        </div>
        
        <div class="clearfix"></div>
      </div>
    </div>
	
	<div class="clearfix">
		<div class="col-md-12 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
          <h5>Custom Filter by Date <span class="text-danger paddingLeft">*</span></h5>
		  </div>
          <div class="clearfix">
            <ul class="list-unstyled positionRelative filterSearch" id="">
              <li class="positionRelative mb">
                <div class="row">
                <div class="col-md-6">
                	<input type="text" value="" class="form-control calendar" placeholder="From Date" id="frmDate">
                </div>
                <div class="col-md-6">
                	<input type="text" value="" class="form-control calendar" placeholder="To Date" id="toDate">
				</div>
             </div>
              </li>
            </ul>
          </div>
        </div>
	</div>
	</div>

<script type="text/javascript">
    // Close Advanced Search
	function closePop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").hide();
	}
	function openPop(){
	$(".advancedsearch-mask").show();
	$("div.cand-advsrch-popup").toggle();
	}
//Date Picker
$( function(){
	$("#frmDate").datepicker();
	$("#toDate").datepicker();
 });
</script>
<script type="text/javascript">
  $(".productListtab li#self").click(function() {
    $("#publicDiv").hide();
    $(this).addClass("current");
    $("#public").removeClass("current");
    $("#selfDiv").show();
  })
  $(".productListtab li#public").click(function() {
    $("#self").removeClass("current");
    $(this).addClass("current");
    $("#publicDiv").show();
    $("#selfDiv").hide();
  })
</script>





 <jsp:include page="../masterFooter.jsp" />	