<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  Date: 6/2/2021
  Time: 4:27 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Home</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%--Main css--%>
    <link rel="stylesheet" href="css/index.css">
    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <%--jquery CDN--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <%--FAVICON--%>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<div class="box">
    <div class="top">
        <div class="quotation">
            <p>
                <b>
                    In the nonstop tsunami<br>
                    of global information,<br>
                    librarians provide us with<br>
                    floaties and teach us to swim.
                </b>
            </p>
            <p>
                <b>
                    – Linton Weeks –
                </b>
            </p>
        </div>
        <div class="illustration">
            <img src="images/index-illustration.svg">
        </div>
    </div>
</div>
<div class="section">
    <div class="sectionTitle">
        <p><b>Books Category</b></p>
    </div>
    <div class="section-child col-lg-12">
        <a href="#">
            <div>
                <img src="images/computer-science.png">
                <p><b>Computer Science</b></p>
            </div>
        </a>
        <a href="#">
            <div>
                <img src="images/graphic-design.png">
                <p><b>Graphic Design</b></p>
            </div>
        </a>
        <a href="#">
            <div>
                <img src="images/business-administration.png">
                <p><b>Business Administration</b></p>
            </div>
        </a>
        <a href="#">
            <div>
                <img src="images/language-studies.png">
                <p><b>Language Studies</b></p>
            </div>
        </a>
    </div>
    <div class="section-child col-lg-12 moreCategory" style="display: none;">
        <a href="#">
            <div>
                <img src="images/computer-science.png">
                <p><b>Computer Science</b></p>
            </div>
        </a>
        <a href="#">
            <div>
                <img src="images/graphic-design.png">
                <p><b>Graphic Design</b></p>
            </div>
        </a>
        <a href="#">
            <div>
                <img src="images/business-administration.png">
                <p><b>Business Administration</b></p>
            </div>
        </a>
        <a href="#">
            <div>
                <img src="images/language-studies.png">
                <p><b>Language Studies</b></p>
            </div>
        </a>
    </div>
    <div style="display: flex; justify-content: center;">
        <button class="btn seeMoreBtn seeMoreCategory">
            <b>See more</b>
        </button>
    </div>
</div>
<div class="anotherSection">
    <div class="smallSection">
        <div class="title">
            <img src="images/book-icon.png">
            <p style="width: max-content;">
                <b>Most Favorite Books</b>
            </p>
        </div>
        <div class="section-child">
            <c:forEach var="mostFavoriteBook" items="${requestScope.MOST_FAVORITE_BOOKS_LIST}">
                <c:url var="viewBookDetails" value="ViewBookDetailsServlet">
                    <c:param name="bookPk" value="${mostFavoriteBook.bookID}"/>
                </c:url>
                <a href="${viewBookDetails}">
                    <div class="book-cover"
                         style="background-image: url(${pageContext.request.contextPath}/image/${mostFavoriteBook.coverPath}),
                                 url(images/NotAvailable.jpg);">
                    </div>
                    <div class="book-info">
                        <p><b>${mostFavoriteBook.title}</b></p>
                        <p>${mostFavoriteBook.author.authorName}</p>
                    </div>
                    <div class="more-info">
                        <p>More info&nbsp;</p>
                        <i class="fa fa-arrow-circle-right"></i>
                    </div>
                </a>
            </c:forEach>
        </div>
        <div>
            <button class="btn seeMoreBtn">
                <a href="#" style="color: #fff;">
                    <b>See more</b>
                </a>
            </button>
        </div>
    </div>
    <div class="smallSection">
        <div class="title">
            <img src="images/book-icon.png">
            <p><b>New Arrival</b></p>
        </div>
        <div class="section-child">
            <c:forEach var="newArrivalBook" items="${requestScope.NEW_ARRIVAL_BOOKS_LIST}">
                <c:url var="viewBookDetails" value="ViewBookDetailsServlet">
                    <c:param name="bookPk" value="${newArrivalBook.bookID}"/>
                </c:url>
                <a href="${viewBookDetails}">
                    <div class="book-cover"
                         style="background-image: url(${pageContext.request.contextPath}/image/${newArrivalBook.coverPath}),
                                 url(images/NotAvailable.jpg);"></div>
                    <div class="book-info">
                        <p><b>${newArrivalBook.title}</b></p>

                        <p>${newArrivalBook.author.authorName}</p>
                    </div>
                    <div class="more-info">
                        <p>More info&nbsp;</p>
                        <i class="fa fa-arrow-circle-right"></i>
                    </div>
                </a>
            </c:forEach>
        </div>
        <div>
            <button class="btn seeMoreBtn">
                <a href="#" style="color: #fff;">
                    <b>See more</b>
                </a>
            </button>
        </div>
    </div>
</div>
<div class="section" style="position: relative; margin-top: -20px;">
    <div class="sectionTitle">
        <p><b>Popular Author</b></p>
    </div>
    <div class="section-child">
        <c:forEach var="popularAuthor" items="${requestScope.POPULAR_AUTHORS_LIST}">
            <c:url var="author" value="ShowAuthorBookServlet">
                <c:param name="authorPk" value="${popularAuthor.authorID}"/>
            </c:url>
            <a href="${author}">
                <div>
                    <div class="author-image"
                         style="background-image: url(${pageContext.request.contextPath}/image/${popularAuthor.coverPath}),
                                 url(images/default-user-icon.png);"></div>
                    <p class="author-name my-2"><b>${popularAuthor.authorName}</b></p>
                </div>
            </a>
        </c:forEach>
    </div>
    <div style="display: flex; justify-content: center;">
        <button class="btn seeMoreBtn">
            <a href="#"><b>See more</b></a>
        </button>
    </div>
</div>
<div class="anotherSection">
    <div class="smallSection">
        <div class="title">
            <img src="images/book-icon.png">
            <p><b>E-Book</b></p>
        </div>
        <div class="section-child">
            <a href="#">
                <div class="book-cover"></div>
                <div class="book-info">
                    <p>
                        <b>
                            Software Engineering 10th Edition
                        </b>
                    </p>
                    <p>Ian Sommerville</p>
                </div>
                <div class="more-info">
                    <p>More info&nbsp;</p>
                    <i class="fa fa-arrow-circle-right"></i>
                </div>
            </a>
            <a href="#">
                <div class="book-cover"></div>
                <div class="book-info">
                    <p>
                        <b>
                            Software Requirements 3rd Edition
                        </b>
                    </p>
                    <p>Karl Wiegers & Joy Beatty </p>
                </div>
                <div class="more-info">
                    <p>More info&nbsp;</p>
                    <i class="fa fa-arrow-circle-right"></i>
                </div>
            </a>
            <a href="#">
                <div class="book-cover"></div>
                <div class="book-info">
                    <p>
                        <b>
                            Foundation of Software Testing - 3rd Edition
                        </b>
                    </p>
                    <p>Rex Black Erik Van Veenendaal & Dorothy Graham</p>
                </div>
                <div class="more-info">
                    <p>More info&nbsp;</p>
                    <i class="fa fa-arrow-circle-right"></i>
                </div>
            </a>
            <a href="#">
                <div class="book-cover"></div>
                <div class="book-info">
                    <p>
                        <b>
                            Adaptive Code via C#
                        </b>
                    </p>
                    <p>Gary McLean Hall</p>
                </div>
                <div class="more-info">
                    <p>More info&nbsp;</p>
                    <i class="fa fa-arrow-circle-right"></i>
                </div>
            </a>
        </div>
        <div>
            <button class="btn seeMoreBtn" style="margin-bottom: 3rem;">
                <a href="#" style="color: #fff;">
                    <b>See more</b>
                </a>
            </button>
        </div>
    </div>
</div>

<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
<script>
    $(document).ready(function () {
        $(".seeMoreCategory").click(function () {
            $(".moreCategory").fadeToggle();
        });
    });
</script>
</body>
</html>
