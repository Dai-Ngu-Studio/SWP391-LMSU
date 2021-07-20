<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Checkout - LMSU</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="js/revieworder.js" defer="defer"></script>
</head>
<body>
<jsp:directive.page pageEncoding="UTF-8"/>
<jsp:directive.page contentType="text/html; charset=UTF-8"/>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<!--Actual Body-->
<div class="bg-light py-4">
    <c:set var="session" value="${sessionScope}"/>
    <c:if test="${not empty session}">
        <div class="row mt-5">
            <div class="col-2"></div>
            <div class="col-8">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title text-dark">Review your Order</div>
                        <c:if test="${requestScope.CHECKOUT_RESERVE}">
                            <form action="ReserveServlet" method="POST">
                                <div class="card-text">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label" for="cartTable">Your Items</label>
                                        <div class="col-8 px-0">
                                            <c:set var="books" value="${sessionScope.MEMBER_CART.items}"/>
                                            <table class="table table-striped table-bordered"
                                                   id="cartTable" name="cartTable">
                                                <thead>
                                                <tr>
                                                    <th class="text-right">#</th>
                                                    <th>Book Title</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="book" items="${books}" varStatus="bookCounter">
                                                    <tr>
                                                        <td class="text-right">
                                                                ${bookCounter.count}
                                                        </td>
                                                        <td>
                                                                ${book.value.title}
                                                            <c:if test="${book.value.quantity eq 0}">
                                                            <span class="badge badge-pill badge-secondary font-weight-light">
                                                                Reserve
                                                            </span>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-1"></div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-3">
                                            <div class="row">
                                                <a type="button"
                                                   class="col-4 btn btn-outline-secondary btn-block col-form-label mx-3"
                                                   href="ShowCartServlet" for="btnReserveOrder">
                                                    <i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
                                                    Back
                                                </a>
                                                <div class="col-8"></div>
                                            </div>
                                        </div>
                                        <div class="col-8">
                                            <div class="row">
                                                <div class="col-3"></div>
                                                <button type="submit" class="col-6 btn btn-success btn-block"
                                                        id="btnReserveOrder"
                                                        name="btAction" value="ConfirmDeliveryOrder">
                                                    <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                                                    Place Order
                                                </button>
                                                <div class="col-3"></div>
                                            </div>
                                        </div>
                                        <div class="col-1"></div>
                                    </div>
                                </div>
                            </form>
                        </c:if>
                        <c:if test="${not requestScope.CHECKOUT_RESERVE}">
                            <%--Start: Direct Method--%>
                            <c:if test="${not sessionScope.CHECKOUT_METHOD}">
                                <form action="CheckoutDirectServlet" method="POST">
                                    <div class="card-text">
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtPickupDate">Date of
                                                Pick-up</label>
                                            <input class="text-black-50 col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_PICKUP_DATE}"
                                                   id="txtPickupDate" name="txtPickupDate" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtPickupTime">Time of
                                                Pick-up</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_PICKUP_TIME}"
                                                   id="txtPickupTime" name="txtPickupDate" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="cartTable">Your Items</label>
                                            <div class="col-8 px-0">
                                                <c:set var="books" value="${sessionScope.MEMBER_CART.items}"/>
                                                <table class="table table-striped table-bordered"
                                                       id="cartTable" name="cartTable">
                                                    <thead>
                                                    <tr>
                                                        <th class="text-right">#</th>
                                                        <th>Book Title</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="book" items="${books}" varStatus="bookCounter">
                                                        <tr>
                                                            <td class="text-right">
                                                                    ${bookCounter.count}
                                                            </td>
                                                            <td>
                                                                    ${book.value.title}
                                                                <c:if test="${book.value.quantity eq 0}">
                                                            <span class="badge badge-pill badge-secondary font-weight-light">
                                                                Reserve
                                                            </span>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-3">
                                                <div class="row">
                                                    <a type="button"
                                                       class="col-4 btn btn-outline-secondary btn-block col-form-label mx-3"
                                                       href="OrderFormServlet" for="btnDirectOrder">
                                                        <i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
                                                        Edit
                                                    </a>
                                                    <div class="col-8"></div>
                                                </div>
                                            </div>
                                            <div class="col-8">
                                                <div class="row">
                                                    <div class="col-3"></div>
                                                    <button type="submit" class="col-6 btn btn-success btn-block"
                                                            id="btnDirectOrder"
                                                            name="btAction" value="ConfirmDirectOrder">
                                                        <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                                                        Place Order
                                                    </button>
                                                    <div class="col-3"></div>
                                                </div>
                                            </div>
                                            <div class="col-1"></div>
                                        </div>
                                    </div>
                                </form>
                            </c:if>
                            <%--End: Direct Method--%>
                            <%--Start: Delivery Method--%>
                            <c:if test="${sessionScope.CHECKOUT_METHOD}">
                                <form action="CheckoutDeliveryServlet" method="POST">
                                    <div class="card-text">
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtReceiverName">Receiver
                                                Name</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_RECEIVERNAME}"
                                                   id="txtReceiverName" name="txtReceiverName" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtPhoneNumber">Phone
                                                Number</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_PHONENUMBER}"
                                                   id="txtPhoneNumber" name="txtPhoneNumber" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtAddressOne">Street
                                                Address</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_ADDRESSONE}"
                                                   id="txtAddressOne" name="txtAddressOne" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtAddressTwo">Residence
                                                Address</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_ADDRESSTWO}"
                                                   id="txtAddressTwo" name="txtAddressTwo" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtCity">City</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_CITY}"
                                                   id="txtCity" name="txtCity" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtDistrict">District</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_DISTRICT}"
                                                   id="txtDistrict" name="txtDistrict" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtWard">Ward</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_WARD}"
                                                   id="txtWard" name="txtWard" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtWard">Estimated Delivery Date</label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_EXPECTEDTIME}"
                                                   id="txtDeliveryTime" name="txtDeliveryTime" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="txtWard">
                                                Estimated Fee (Service Fee included)
                                            </label>
                                            <input class="text-black col-8" type="text"
                                                   value="${sessionScope.CHECKOUT_FEE}"
                                                   id="txtFee" name="txtFee" disabled
                                            />
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label" for="cartTable">Your Items</label>
                                            <div class="col-8 px-0">
                                                <c:set var="books" value="${sessionScope.MEMBER_CART.items}"/>
                                                <table class="table table-striped table-bordered"
                                                       id="cartTable" name="cartTable">
                                                    <thead>
                                                    <tr>
                                                        <th class="text-right">#</th>
                                                        <th>Book Title</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="book" items="${books}" varStatus="bookCounter">
                                                        <tr>
                                                            <td class="text-right">
                                                                    ${bookCounter.count}
                                                            </td>
                                                            <td>
                                                                    ${book.value.title}
                                                                <c:if test="${book.value.quantity eq 0}">
                                                            <span class="badge badge-pill badge-secondary font-weight-light">
                                                                Reserve
                                                            </span>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-1"></div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-3">
                                                <div class="row">
                                                    <a type="button"
                                                       class="col-4 btn btn-outline-secondary btn-block col-form-label mx-3"
                                                       href="OrderFormServlet" for="btnDeliveryOrder">
                                                        <i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
                                                        Edit
                                                    </a>
                                                    <div class="col-8"></div>
                                                </div>
                                            </div>
                                            <div class="col-8">
                                                <div class="row">
                                                    <div class="col-3"></div>
                                                    <button type="submit" class="col-6 btn btn-success btn-block"
                                                            id="btnDeliveryOrder"
                                                            name="btAction" value="ConfirmDeliveryOrder">
                                                        <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                                                        Place Order
                                                    </button>
                                                    <div class="col-3"></div>
                                                </div>
                                            </div>
                                            <div class="col-1"></div>
                                        </div>
                                    </div>
                                </form>
                            </c:if>
                            <%--End: Delivery Method--%>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
        </div>
    </c:if>
</div>
<!--Actual Body-->
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>
</html>
