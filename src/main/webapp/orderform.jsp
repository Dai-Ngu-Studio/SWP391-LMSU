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
    <script src="js/orderform.js" defer="defer"></script>
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
            <div class="col-sm-2"></div>
            <div class="col-sm-2">
                <div class="btn-group-vertical list-group">
                    <a class="btn btn-primary rounded-top methodSelect text-white active" data-toggle="list"
                       href="#list-direct">
                        Receive directly at the library
                    </a>
                    <a class="btn btn-primary rounded-bottom methodSelect text-white" data-toggle="list"
                       href="#list-delivery">
                        Order a delivery
                    </a>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <div class="tab-content" id="nav-tabContent" style="border: none">
                            <div class="tab-pane fade show active" id="list-direct">
                                <div class="card-title text-dark">Direct Pick-up</div>
                                <div class="card-text">
                                        <%--Start: Direct Borrow Form--%>
                                    <form action="ReviewOrderServlet" method="POST" class="my-0">
                                        <div class="form-group">
                                            <div class="form-group mb-0">
                                                <label for="txtPickupDate">Date of Pick-up</label>
                                            </div>
                                            <div class="form-group">
                                                <input class="text-black-50 directInput" type="date" id="txtPickupDate"
                                                       name="txtPickupDate" value="${sessionScope.CHECKOUT_PICKUP_DATE}"
                                                       style="border-radius: 0.3rem"/>
                                                <small id="errorPickupDate"
                                                       class="form-text text-muted directError">
                                                    Your pick-up must be within 7 days from now.
                                                </small>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="txtPickupTime">Time of Pick-up</label>
                                            <select id="txtPickupTime" name="txtPickupTime"
                                                    class="form-control directInput">
                                                <option disabled selected hidden>Choose...</option>
                                                <option value="09:00">09:00</option>
                                                <option value="10:00">10:00</option>
                                                <option value="11:00">11:00</option>
                                                <option value="14:00">14:00</option>
                                                <option value="15:00">15:00</option>
                                                <option value="16:00">16:00</option>
                                            </select>
                                            <small id="errorPickupTime"
                                                   class="form-text text-muted directError">
                                                Please choose a time slot to pick-up your books.
                                            </small>
                                        </div>
                                        <div class="form-group">
                                            <small class="text-info">
                                                Please present your ID card to the librarian when you are to receive
                                                your books.
                                            </small>
                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary" id="btnDirectOrder"
                                                    name="btAction" value="DirectOrder" disabled>
                                                Schedule your pick-up
                                            </button>
                                        </div>
                                    </form>
                                        <%--End: Direct Borrow Form--%>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="list-delivery">
                                <div class="card-title text-dark">Delivery</div>
                                <div class="card-text">
                                        <%--Start: Delivery Order Form--%>
                                    <form action="ReviewOrderServlet" method="POST" class="my-0">
                                        <div class="form-group my-10">
                                            <label for="inputReceiverName">Receiver Name</label>
                                            <input type="text" class="form-control deliverInput" id="inputReceiverName"
                                                   name="txtReceiverName" placeholder="Sinh Vi En"
                                                   value="${sessionScope.CHECKOUT_RECEIVERNAME}"
                                            />
                                            <small id="errorReceiverName"
                                                   class="form-text text-muted deliverError">
                                                Name of receiver must be 2-60 characters long, and must only contain
                                                letters.
                                            </small>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputPhoneNumber">Phone Number</label>
                                            <input type="text" class="form-control deliverInput" id="inputPhoneNumber"
                                                   name="txtPhoneNumber" placeholder="090000000000"
                                                   value="${sessionScope.CHECKOUT_PHONENUMBER}"
                                            />
                                            <small id="errorPhoneNumber"
                                                   class="form-text text-muted deliverError">
                                                Phone number must be 10 characters long, and must only contain
                                                numbers.
                                            </small>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputAddressOne">Street Address</label>
                                            <input type="text" class="form-control deliverInput" id="inputAddressOne"
                                                   name="txtAddressOne" placeholder="1234 D1 Street"
                                                   value="${sessionScope.CHECKOUT_ADDRESSONE}"
                                            />
                                            <small id="errorAddressOne"
                                                   class="form-text text-muted deliverError">
                                                Street address must be 5-50 characters long, can contain letters,
                                                numbers and special characters.
                                            </small>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputAddressTwo">Residence Address</label>
                                            <input type="text" class="form-control deliverInput" id="inputAddressTwo"
                                                   name="txtAddressTwo" placeholder="Apartment, or floor"
                                                   value="${sessionScope.CHECKOUT_ADDRESSTWO}"
                                            />
                                            <small id="errorAddressTwo"
                                                   class="form-text text-muted deliverError">
                                                Residence address is optional, must be 0-50 characters long, can contain
                                                letters, numbers and special characters.
                                            </small>
                                        </div>
                                        <div class="form-row">
                                            <div class="form-group col-md-4">
                                                <label for="inputCity">City</label>
                                                <select id="inputCity" name="txtCity"
                                                        class="custom-select deliverInput">
                                                    <option selected disabled>Select a city..</option>
                                                </select>
                                                <small id="errorCity"
                                                       class="form-text text-muted deliverError">
                                                    Please select a city.
                                                </small>
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label for="inputDistrict">District</label>
                                                <select id="inputDistrict" name="txtDistrict"
                                                        class="custom-select deliverInput">
                                                    <option selected disabled>Select a city first...</option>
                                                </select>
                                                <small id="errorDistrict"
                                                       class="form-text text-muted deliverError">
                                                    Please select a district.
                                                </small>
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label for="inputWard">Ward</label>
                                                <select id="inputWard" name="txtWard"
                                                        class="custom-select deliverInput">
                                                    <option selected disabled>Select a district first...</option>
                                                </select>
                                                <small id="errorWard"
                                                       class="form-text text-muted deliverError">
                                                    Please select a ward.
                                                </small>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary"
                                                    id="btnDeliveryOrder"
                                                    name="btAction" value="DeliveryOrder"
                                                    disabled>
                                                Checkout
                                            </button>
                                        </div>
                                    </form>
                                        <%--End: Delivery Order Form--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2"></div>
        </div>
    </c:if>
</div>
<!--Actual Body-->
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>