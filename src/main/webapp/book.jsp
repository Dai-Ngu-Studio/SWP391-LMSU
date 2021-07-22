<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Books</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<%--Actual Body--%>
<div class="row mt-5">
    <div class="col-lg-2"></div>
    <div class="col-lg-8 text-center">
        <h3>Book Catalog</h3>
    </div>
    <div class="col-lg-2"></div>

</div>
<%--<div class="row mt-2">--%>
<%--    <div class="col-lg-2"></div>--%>
<%--    <div class="col-lg-4 text-center h5">Page 1 of 20</div>--%>
<%--    <div class="col-lg-4 text-center h5">--%>
<%--        < 1 2 3 4 5 6 7 ...>--%>
<%--    </div>--%>
<%--    <div class="col-lg-2"></div>--%>
<%--</div>--%>

<c:set var="bookList" value="${requestScope.BOOK_LIST}"/>
<c:set var="indexCount" value="${0}"/>
<c:set var="bookTotal" value="${0}"/>
<script>
    $(document).ready(function () {
        $('#checkoutSuccessModal').modal('toggle');
    });
</script>

<c:forEach var="book" items="${bookList}"
           varStatus="counter">
    <%--Book Row--%>
    <c:if test="${indexCount eq 0}">
        <div class="row mt-3"> <%--Start: div class="row mt-2" --%>
    </c:if>
    <%--Start: Empty Column--%>
    <c:if test="${indexCount eq 0}">
        <div class="col-lg-2"></div>
    </c:if>
    <%--End: Empty Column--%>
    <%--Start: Book Item--%>

    <div class="col-lg-4 text-center">
        <div class="card h-100 mh-100" style="max-width: 540px">
            <div class="row">
                <div class="col-lg-4">
                    <img src="${pageContext.request.contextPath}/image/${book.coverPath}" alt="Book cover"
                         class="img-fluid"
                         onerror="this.onerror=null; this.src='images/NotAvailable.jpg';"/>
                </div>
                <div class="col-lg-8">
                    <div class="card-body">
                        <h5 class="card-title text-center">${book.title}</h5>
                        <div class="card-text">
                            <div class="row mb-3 text-left">
                                <c:set var="shortDesc" value="${fn:substring(book.description, 0, 170)}"/>
                                    ${shortDesc}...
                            </div>
                            <div class="row">
                                <div class="col-lg-2"></div>
                                <div class="col-lg-8">
                                    <form action="ViewBookDetailsServlet" class="mb-0">
                                        <input type="hidden" name="bookPk" value="${book.bookID}">
                                        <button type="submit" class="btn btn-info"><i class="fa fa-info-circle"
                                                                                      aria-hidden="true"></i> More Info
                                        </button>
                                    </form>
                                </div>
                                <div class="col-lg-2"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--End: Book Item--%>
    <%--Start: Empty Column--%>
    <c:if test="${indexCount eq 1}">
        <div class="col-lg-2"></div>
    </c:if>
    <%--End: Empty Column--%>
    <c:if test="${indexCount eq 1}">
        </div> <%--End: div class="row mt-2" --%>
    </c:if>
    <c:choose>
        <c:when test="${indexCount eq 0}">
            <c:set var="indexCount" value="${1}"/>
        </c:when>
        <c:otherwise>
            <c:set var="indexCount" value="${0}"/>
        </c:otherwise>
    </c:choose>
    <%--End of Book Row--%>
    <c:set var="bookTotal" value="${counter.count}"/>
</c:forEach>
<c:if test="${(bookTotal mod 2) ne 0}">
    <%--Closing div in case odd number of books--%>
    </div> <%--End: div class="row mt-2" --%>
</c:if>
<%--Actual Body--%>
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>