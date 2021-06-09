<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Checkout - LMSU</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="../../../../LMSU-FE/LMSU-FE/css/orderForm.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="../../../../LMSU-FE/LMSU-FE/images/images/favicon.png"/>
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<!--Actual Body-->
<div class="row mt-5">
    <div class="col-lg-2"></div>
    <div class="col-lg-2">
        <div class="list-group" id="list-tab" role="tablist">
            <a class="list-group-item list-group-item-action active" id="list-home-list" data-toggle="list"
               href="#list-home" role="tab" aria-controls="home" style="border-radius: 0">
                Receive directly at the library
            </a>
            <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list"
               href="#list-profile" role="tab" aria-controls="profile" style="border-radius: 0">
                Order a delivery
            </a>
        </div>
    </div>
    <div class="col-lg-6">
        <div class="card">
            <div class="card-body">
                <div class="tab-content" id="nav-tabContent" style="border: none">
                    <div class="tab-pane fade show active" id="list-home" role="tabpanel"
                         aria-labelledby="list-home-list">
                        <div class="card-title">Direct Receipt</div>
                        <div class="card-text">
                            <form action="CheckoutServlet" class="my-0">
                                <div class="form-group">
                                    <label for="receiptDate">Date of receipt</label>
                                    <input type="date" id="receiptDate" name="receiptDate"
                                           style="border-radius: 0.3rem"/>
                                </div>
                                <div class="form-group">
                                    <label for="receiptTime">Time of receipt</label>
                                    <select id="receiptTime" class="form-control">
                                        <option selected>Choose...</option>
                                        <option>...</option>
                                    </select>
                                </div>
                                <small class="text-info">
                                    Please present your ID card to the librarian when you are to receive your orders.
                                </small>
                                <button type="submit" class="btn btn-primary mt-3"
                                        name="btAction" value="DirectBorrow">
                                    Schedule
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">
                        <div class="card-title">Delivery</div>
                        <div class="card-text">
                            <form action="CheckoutServlet" class="my-0">
                                <div class="form-group">
                                    <label for="inputReceiverName">Receiver Name</label>
                                    <input type="text" class="form-control" id="inputReceiverName"
                                           placeholder="Sinh Vi En"/>
                                </div>
                                <div class="form-group">
                                    <label for="inputPhoneNumber">Phone Number</label>
                                    <input type="text" class="form-control" id="inputPhoneNumber"
                                           placeholder="09xxxxxxxxxx"/>
                                </div>
                                <div class="form-group">
                                    <label for="inputAddress">Address</label>
                                    <input type="text" class="form-control" id="inputAddress"
                                           placeholder="1234 D1 Street"/>
                                </div>
                                <div class="form-group">
                                    <label for="inputAddress2">Address 2</label>
                                    <input type="text" class="form-control" id="inputAddress2"
                                           placeholder="Apartment, or floor"/>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-4">
                                        <label for="inputCity">City</label>
                                        <select id="inputCity" class="form-control">
                                            <option selected>Choose...</option>
                                            <option>...</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="inputDistrict">District</label>
                                        <select id="inputDistrict" class="form-control">
                                            <option selected>Choose...</option>
                                            <option>...</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="inputWard">Ward</label>
                                        <select id="inputWard" class="form-control">
                                            <option selected>Choose...</option>
                                            <option>...</option>
                                        </select>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary"
                                        name="btAction" value="DeliveryOrder">
                                    Checkout
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-2"></div>
</div>
<!--Actual Body-->
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>