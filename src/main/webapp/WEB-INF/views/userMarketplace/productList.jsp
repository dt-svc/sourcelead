<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Products Self and Public</h2>
      </div>
       <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/OrgMarketplace'/>">Associate Marektplace </a>&raquo;Product List</div>     
    </section>
    <div class="project_wrap clearfix backgroundWhite">
      
      <div class="positionRelative reportHead clearfix">
          <ul class="project-tabs-menu projecttabs-custom clearfix productListtab pull-left">
            <li class="current" id="self">
              <a href="javascript:void(0)">Self</a>
            </li>
            <li id="public">
                <a href="javascript:void(0)">Public</a>
            </li>
          </ul>

          <ul class="pull-right list-unstyled list-inline" id="selfOrgHeader">
          <li><a class="newAdBtn mr5" href="${SourceLeadWeb_atsUrl}/addProduct" onclick="addProduct()" title="Add">+ Add Items</a></li>
                <li>
                  <a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a>
              </li>
              <li>
                  <div class="width100px">
                    <select class="sel form-control">
                      <option selected="">View By</option>
                      <option>By Org</option>
                      <option>By User</option>
                    </select>
                </div>
              </li>
                <li class="">
                    <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                    <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
                </li>
            </ul>

            <ul class="pull-right list-unstyled list-inline" id="publicOrgHeader" style="display: none">
                    <li>
                      <a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a>
                  </li>
                    <li class="">
                        <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                        <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
                    </li>
                </ul>
      </div>
        <div class="clearfix">
            <div id="selfDiv">
                <div class="productList clearfix">
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">
                            <div class="clearfix">
                            <div class="text-left pull-left">Contact Seller</div>
                            <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                        </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product3.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product3.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                            <h3>Product Name here..</h3>
                            <p>Product short description</p>
                            <span><b>Rs:</b>50000.00</span>
                            </a><a href="javascript:void(0)">
                                <div class="clearfix">
                                <div class="text-left pull-left">Contact Seller</div>
                                <div class="text-left pull-right text-muted small mt2">Posted: 30/10/2019</div>
                            </div>
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
                </div>
            </div>



            </div>
            <div id="publicDiv" style="display: none">


                <div class="productList clearfix">
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product3.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
                            <div class="contSellPop">
                                <div class="contSellPopArr clearfix">
                                    <img src="../images/23.jpg">
                                    <span>
                                        <h4>Seller Name</h4>
                                        <p>+91 9000 000 000</p>
                                        <p>name@domain.com</p>
                                    </span>
                                </div>
                            </div>&gt;
                        </a>
                    </div>
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product3.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product4.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
                <a href="product-view.html"></a><div class="productListItem"><a href="product-view.html">
                    <div class="productImg"><img src="../images/product2.jpg"></div>
                    </a><div class="productText"><a href="product-view.html">
                        <h3>Product Name here..</h3>
                        <p>Product short description</p>
                        <span><b>Rs:</b>50000.00</span>
                        </a><a href="javascript:void(0)">Contact Seller
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
                </div>
            </div>
            </div>
        </div>

    </div>
  </section>
  <script type="text/javascript">
  $(".productListtab li#self").click(function() {
    $("#publicDiv").hide();
    $(this).addClass("current");
    $("#public").removeClass("current");
    $("#selfDiv, #selfOrgHeader").show();
    $("#publicOrgHeader").hide();
    
  })
  $(".productListtab li#public").click(function() {
    $("#self").removeClass("current");
    $(this).addClass("current");
    $("#publicDiv, #publicOrgHeader").show();
    $("#selfDiv, #selfOrgHeader").hide();
  })
  
  function addProduct(){
				var atsUrl = "${SourceLeadWeb_atsUrl}";
				  location.replace(atsUrl+"/addProduct");
				 
				}
</script>
}
 <jsp:include page="../masterFooter.jsp" />	