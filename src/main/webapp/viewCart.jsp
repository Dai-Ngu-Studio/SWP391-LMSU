<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Your Cart - LMSU</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
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
<%--Actual Body--%>

<div class="bg-light">
    <c:set var="session" value="${sessionScope}"/>

    <c:if test="${not empty session}">
        1
        <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
        <%--CartObj--%>
        <c:set var="cart" value="${sessionScope.MEMBER_CART}"/>
        <c:if test="${not empty cart}">
            2
            <%--Map<String, BookObj>--%>
            <c:set var="books" value="${cart.items}"/>
            <c:if test="${not empty books}">
                3
                <div class="row pt-5">
                    <div class="col-lg-2"></div>
                        <%--Start: Cart Quantity--%>
                    <div class="col-lg-5">
                        <h4>Your Cart<small class="text-muted"> (${cart.cartQuantity} books)</small></h4>
                    </div>
                        <%--End: Cart Quantity--%>
                    <div class="col-lg-3"></div>
                    <div class="col-lg-2"></div>
                </div>
                <div class="row pb-5">
                        <%--Start: Empty Column--%>
                    <div class="col-lg-2"></div>
                        <%--End: Empty Column--%>
                        <%--Start: Cart Items Column--%>
                    <div class="col-lg-5">
                        <c:forEach var="book" items="${books}" varStatus="bookCounter">
                            <form action="RemoveFromCartServlet" class="my-0 mx-0">
                                <div class="card mt-2">
                                    <div class="card-body">
                                        <div class="card-title">
                                            <a href="<c:url value='ViewBookDetailsServlet?bookPk=${book.key}'/>"
                                               class="link text-info">
                                                    ${book.value.title}</a>
                                            <a href="<c:url value='ViewBookDetailsServlet?bookPk=${book.key}'/>"
                                               class="link"><i
                                                    class="fa fa-arrow-circle-right text-info"
                                                    aria-hidden="true"></i></a>
                                        </div>
                                        <div class="card-text">
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <img src="images/software-engineering.jpg"
                                                         class="rounded img-fluid img-thumbnail"
                                                         alt="..."/>
                                                </div>
                                                <div class="col-lg-5">
                                                        ${book.value.description}
                                                </div>
                                                <div class="col-lg-4">
                                                    <input type="hidden" name="bookPk" value="${book.key}">
                                                    <button type="submit" name="btAction"
                                                            value="RemoveFromCart" class="btn btn-danger btn-block">
                                                        <i class="fa fa-minus-circle" aria-hidden="true"></i> Remove
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </c:forEach>
                    </div>
                        <%--End: Cart Items Column--%>
                        <%--Start: Checkout Column--%>
                    <div class="col-lg-3">
                        <div class="card mt-2">
                            <div class="card-body text-center">
                                <div class="card-text">
                                    Logged in as <strong>${user.name}</strong>
                                </div>
                                <a href="../../../../LMSU-FE/LMSU-FE/orderForm.html" type="button"
                                   class="btn btn-success link mt-2">
                                    Proceed to borrow
                                </a>
                            </div>
                        </div>
                    </div>
                        <%--End: Checkout Column--%>
                        <%--Start: Empty Column--%>
                    <div class="col-lg-2"></div>
                        <%--End: Empty Column--%>
                </div>
            </c:if>
            <c:if test="${empty books}">
                No books have been added to cart yet.
            </c:if>
        </c:if>
    </c:if>
</div>
<%--Actual Body--%>
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>