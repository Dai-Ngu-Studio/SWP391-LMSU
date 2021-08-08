<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:directive.page pageEncoding="UTF-8"/>
<jsp:directive.page contentType="text/html; charset=UTF-8"/>
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
    <script src="js/returnform.js"></script>
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<!--Actual Body-->
<div class="bg-light py-4">
    <c:set var="session" value="${sessionScope}"/>
    <c:if test="${not empty session}">
        <div class="row mt-5">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="card">
                    <div class="card-body">
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
                                    Please choose a time slot to return your books.
                                </small>
                            </div>
                            <div class="form-group">
                                <small class="text-info">
                                    Please present your ID card to the librarian when you are to return
                                    your books.
                                </small>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary" id="btnReturnOrder"
                                        name="btAction" value="ReturnOrder" disabled>
                                    Schedule your pick-up
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-sm-3"></div>
        </div>
    </c:if>
</div>
<!--Actual Body-->
<jsp:include page="footer.html"></jsp:include>
</body>

</html>