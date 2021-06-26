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

<div class="bg-light py-4">
    <c:set var="session" value="${sessionScope}"/>

    <c:if test="${not empty session}">
        <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
        <c:if test="${not empty user}">
            <c:set var="memberTotalActiveBorrows" value="${0}"/>
            <c:if test="${not empty sessionScope.MEMBER_TOTAL_ACTIVE_BORROWS}">
                <c:set var="memberTotalActiveBorrows" value="${sessionScope.MEMBER_TOTAL_ACTIVE_BORROWS.size()}"/>
            </c:if>
            <c:set var="cart" value="${sessionScope.MEMBER_CART}"/>
            <%--When cart has items, it is not null--%>
            <c:if test="${not empty cart}">
                <%--Map<String bookID, BookObj>--%>
                <c:set var="books" value="${cart.items}"/>
                <c:if test="${not empty books}">
                    <div class="row pt-5">
                        <div class="col-2"></div>
                        <div class="col-5">
                            <h4>Your Cart<small class="text-muted">
                                <c:out value=" (${cart.cartQuantity}/${10-memberTotalActiveBorrows})"/>
                                <c:set var="numberOfReserved" value="${cart.cartReserved}"/>
                                <c:if test="${numberOfReserved > 0}">
                                    <c:out value=" + ${numberOfReserved} reserved books"/>
                                </c:if>
                            </small>
                            </h4>
                        </div>
                        <div class="col-3"></div>
                        <div class="col-2"></div>
                    </div>
                    <div class="row pb-5">
                        <div class="col-2"></div>
                            <%--Start: Cart Items Column--%>
                        <div class="col-5">
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
                                                <c:if test="${book.value.quantity eq 0}">
                                                    <label class="badge badge-secondary text-white
                                                    float-right font-weight-light">
                                                        <i class="fa fa-bookmark" aria-hidden="true"></i>
                                                        Reserve
                                                    </label>
                                                </c:if>
                                            </div>
                                            <div class="card-text">
                                                <div class="row">
                                                    <div class="col-3">
                                                        <img src="${pageContext.request.contextPath}/image/${book.value.coverPath}"
                                                             class="rounded img-fluid img-thumbnail"
                                                             alt="..."
                                                             onerror="this.onerror=null; this.src='images/NotAvailable.jpg';"
                                                        />
                                                    </div>
                                                    <div class="col-7">

                                                    </div>
                                                    <div class="col-2">
                                                        <div class="row">
                                                            <div class="col-12">
                                                                <input type="hidden" name="bookPk" value="${book.key}">
                                                                <button type="submit" name="btAction"
                                                                        value="RemoveFromCart"
                                                                        class="btn btn-outline-danger btn-block">
                                                                    <i class="fa fa-minus-circle"
                                                                       aria-hidden="true"></i> Remove
                                                                </button>
                                                            </div>
                                                        </div>


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
                        <div class="col-3">
                            <div class="card mt-2">
                                <div class="card-body text-center">
                                    <div class="card-text">
                                        Logged in as <strong>${user.name}</strong>
                                    </div>
                                    <c:if test="${not empty user}">
                                        <a href="OrderFormServlet" type="button"
                                           class="btn btn-success link mt-2">
                                            <i class="fa fa-chevron-circle-right" aria-hidden="true"></i>
                                            Proceed to borrow
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                            <%--End: Checkout Column--%>
                        <div class="col-2"></div>
                    </div>
                </c:if>
                <c:if test="${empty books}">
                    <div class="row my-4">
                        <div class="col-4"></div>
                        <div class="col-4">
                            <div class="card mt-2">
                                <div class="card-body text-center">
                                    <div class="card-text">
                                        No books have been added to cart yet.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-4"></div>
                    </div>
                </c:if>
            </c:if>
            <%--When cart has no items, it is null--%>
            <c:if test="${empty cart}">
                <div class="row my-4">
                    <div class="col-4"></div>
                    <div class="col-4">
                        <div class="card mt-2">
                            <div class="card-body text-center">
                                <div class="card-text">
                                    No books have been added to cart yet.
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-4"></div>
                </div>
            </c:if>
        </c:if>
    </c:if>
    <c:if test="${(empty session) or (empty user)}">
        <div class="row my-4">
            <div class="col-4"></div>
            <div class="col-4">
                <div class="card mt-2">
                    <div class="card-body text-center">
                        <div class="card-text">
                            <div class="row">
                                <div class="col-2"></div>
                                <div class="col-8">
                                    You are currently not logged in.<br>
                                </div>
                                <div class="col-2"></div>
                            </div>
                            <div class="row">
                                <div class="col-2"></div>
                                <div class="col-8">
                                    <a href="StartupServlet" type="button"
                                       class="btn btn-primary link mt-2">
                                        Login now
                                    </a>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-4"></div>
        </div>
    </c:if>
</div>
<%--Actual Body--%>
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>