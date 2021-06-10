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
<div class="bg-light py-5">
    <div class="row">
        <div class="col-lg-2"></div>
        <div class="col-lg-8 text-center">
            <h3>Author Catalog</h3>
        </div>
        <div class="col-lg-2"></div>
    </div>
    <div class="row mt-2">
        <div class="col-lg-2"></div>
        <div class="col-lg-4 text-center h5">Page 1 of 20</div>
        <div class="col-lg-4 text-center h5">
            < 1 2 3 4 5 6 7 ...>
        </div>
        <div class="col-lg-2"></div>
    </div>

    <c:set var="authorList" value="${requestScope.AUTHOR_LIST}"/>
    <c:set var="authorTotal" value="${0}"/>

    <c:forEach var="author" items="${authorList}"
               varStatus="counter">
        <div class="row my-3">
                <%--Start: Empty Column--%>
            <div class="col-lg-2"></div>
                <%--End: Empty Column--%>
                <%--Start: Author Item--%>
            <div class="col-lg-8 text-center">
                <div class="card h-100 mh-100">
                    <div class="row">
                        <div class="col-lg-4">
                            <img src="${pageContext.request.contextPath}/image/${author.coverPath}"
                                 alt="Author Portrait"
                                 class="img-fluid img-thumbnail w-50 my-2"
                                 onerror="this.onerror=null; this.src='images/default-user-icon.png';"/>
                        </div>
                        <div class="col-lg-8">
                            <div class="card-body">
                                <h5 class="card-title text-center">${author.authorName}</h5>
                                <div class="card-text">
                                    <div class="row mb-3 text-left">
                                            ${author.authorBio}
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-2"></div>
                                        <div class="col-lg-8">
                                            <form action="ShowAuthorBookServlet" class="mb-0">
                                                <input type="hidden" name="authorPk" value="${author.authorID}">
                                                <button type="submit" class="btn btn-info">
                                                    <i class="fa fa-book" aria-hidden="true"></i>
                                                    See More Books of this Author
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
                <%--Start: Empty Column--%>
            <div class="col-lg-2"></div>
                <%--End: Empty Column--%>
        </div>
    </c:forEach>
</div>
<%--Actual Body--%>
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>