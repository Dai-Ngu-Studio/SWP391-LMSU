<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Author</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <link rel="stylesheet" href="css/author.css"/>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
</head>

<body>
<script>
    $(function () {
        $("#includedHeader").load("header.html");
    });
</script>
<script>
    $(function () {
        $("#includedNavbar").load("navbar.html");
    });
</script>
<script>
    $(function () {
        $("#includedScrollToTop").load("scrolltotop.html");
    });
</script>
<script>
    $(function () {
        $("#includedFooter").load("footer.html");
    });
</script>
<div id="includedHeader"></div>
<div id="includedNavbar"></div>
<div id="includedScrollToTop"></div>

<div class="box bg-light">
    <div class="top">
        <h2 class="pageTitle"><b>Author</b></h2>
    </div>

    <c:set var="authorList" value="${requestScope.AUTHOR_LIST}"/>
    <c:forEach var="author" items="${authorList}">
        <div class="author">


            <div class="authorItem d-flex">
                <div class="d-flex flex-column">
                    <div class="authorIMG w-100"></div>
                    <button class="btn btn-light w-100 mt-3">See More Books of This Author</button>
                </div>

                <div class="descriptionTxt px-4">
                    <ul>
                        <li>
                            <p>
                                    ${author.authorName}
                            </p>
                        </li>
                        <li>
                            <p>
                                    ${author.authorBio}
                            </p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </c:forEach>

    <div class="d-flex justify-content-center">
        <div class="dataTables_paginate paging_simple_numbers" id="order-listing_paginate">
            <ul class="pagination">
                <li class="paginate_button page-item previous disabled" id="order-listing_previous">
                    <a href="#"
                       aria-controls="order-listing"
                       data-dt-idx="0"
                       tabindex="0"
                       class="page-link">Previous</a>
                </li>
                <li class="paginate_button page-item active">
                    <a href="#" aria-controls="order-listing" data-dt-idx="1"
                       tabindex="0" class="page-link">1</a>
                </li>
                <li class="paginate_button page-item ">
                    <a href="#" aria-controls="order-listing" data-dt-idx="2"
                       tabindex="0" class="page-link">2</a>
                </li>
                <li class="paginate_button page-item next" id="order-listing_next">
                    <a href="#"
                       aria-controls="order-listing"
                       data-dt-idx="3" tabindex="0"
                       class="page-link">Next</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div id="includedFooter"></div>
</body>

</html>
