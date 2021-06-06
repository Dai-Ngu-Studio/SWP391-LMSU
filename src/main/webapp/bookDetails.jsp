<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Book Details</title>
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

<c:set var="bookObj" value="${requestScope.BOOK_OBJECT}"/>
<div class="bg-light">
    <%--Start: Book Section--%>
    <div class="row pt-5">
        <%--Start: Empty Column--%>
        <div class="col-lg-2"></div>
        <%--End: Empty Column--%>
        <%--Start: Left Column--%>
        <div class="col-lg-3">
            <%--Start: Book Cover Image--%>
            <div class="text-center">
                <img src="images/software-engineering.jpg" class="rounded img-fluid img-thumbnail" alt="..."/>
            </div>
            <%--End: Book Cover Image--%>
            <%--Start: Book Metadata--%>
            <div class="card mt-2">
                <div class="card-body">
                    <div class="card-title">Book Information</div>
                    <ul class="list-group">
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <%--Not yet implemented: Physical or E-Book--%>
                                <div class="col-lg-6"><strong>Format</strong></div>
                                <div class="col-lg-6">Physical</div>
                                <%--Not yet implemented: Physical or E-Book--%>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>Author</strong></div>
                                <div class="col-lg-6">${bookObj.authorName}</div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>Publisher</strong></div>
                                <div class="col-lg-6">${bookObj.publisher}</div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>Publication Date</strong></div>
                                <div class="col-lg-6">${bookObj.publishDate}</div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>ISBN-13</strong></div>
                                <div class="col-lg-6">${bookObj.isbnThirteen}</div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>ISBN-10</strong></div>
                                <div class="col-lg-6">${bookObj.isbnTen}</div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <%--End: Book Metadata--%>
        </div>
        <%--End: Left Column--%>
        <%--Start: Right Column--%>
        <div class="col-lg-5">
            <div class="card">
                <div class="card-body">
                    <span class="card-title mb-0 pr-2">${bookObj.title}</span>
                    <%--Start: Book Rating--%>
                    <span class="card-text text-primary">
                        <c:set var="bookAvgRating" value="${bookObj.avgRating}"/>
                        <%--Get numbers before and after decimal plate--%>
                        <c:set var="integralRating" value="${fn:substringBefore(bookAvgRating,'.')}"/>
                        <c:set var="fractionalRating" value="${bookAvgRating mod 1}"/>
                        <%--If rating is not 0, print stars--%>
                        <c:if test="${bookAvgRating ne 0}">
                            <%--Print stars until reach integral number of stars--%>
                            <c:forEach begin="${1}" end="${integralRating}">
                                <i class="fa fa-star" aria-hidden="true"></i>
                            </c:forEach>
                            <%--If there are numbers after decimal plate and rating is not 5--%>
                            <c:if test="${(fractionalRating ne 0) and (integralRating ne 5)}">
                                <%--ge: greater or equal--%>
                                <%--lt: less than--%>
                                <%--ne: not equal--%>
                                <%--Print half star if fraction greater than or equal to 0.5--%>
                                <c:if test="${(fractionalRating ge 0.5)}">
                                    <i class="fa fa-star-half-o" aria-hidden="true"></i>
                                </c:if>
                                <%--Print empty star if fraction less than 0.5--%>
                                <c:if test="${(fractionalRating lt 0.5)}">
                                    <i class="fa fa-star-o" aria-hidden="true"></i>
                                </c:if>
                            </c:if>
                            <%--If there are no numbers after decimal plate and rating is not 5--%>
                            <c:if test="${(fractionalRating eq 0) and (integralRating ne 5)}">
                                <%--Print remaining empty stars--%>
                                <c:forEach begin="${integralRating}" end="${4}">
                                    <i class="fa fa-star-o" aria-hidden="true"></i>
                                </c:forEach>
                            </c:if>
                        </c:if>
                        <%--If rating is 0, print five empty stars--%>
                        <c:if test="${bookAvgRating eq 0}">
                            <c:forEach begin="${1}" end="${5}">
                                <i class="fa fa-star-o" aria-hidden="true"></i>
                            </c:forEach>
                        </c:if>
                        </span>
                    <%--End: Book Rating--%>
                    <div class="card-text mt-2">
                        By <a href="#" class="badge badge-secondary">${bookObj.authorName}</a>
                    </div>
                    <div class="card-title mt-2 mb-0">Description</div>
                    <div class="card-text">
                        ${bookObj.description}
                    </div>
                </div>
            </div>
            <%--Start: Book Options Section--%>
            <div class="card mt-3">
                <div class="card-body">
                    <div class="row text-center">
                        <div class="col-lg-4"></div>
                        <div class="col-lg-4">
                            <%--gt: greater than--%>
                            <c:if test="${bookObj.quantity gt 0}">
                                <form action="AddBookToCartServlet" class="row my-lg-0">
                                    <input type="hidden" name="bookPk" value="${bookObj.id}">
                                    <button type="submit" class="btn btn-primary btn-block"
                                            name="btAction" value="AddToCart">
                                        <i class="fa fa-cart-plus" aria-hidden="true"></i> Add to Cart
                                    </button>
                                </form>
                            </c:if>
                            <%--eq: equal--%>
                            <c:if test="${bookObj.quantity eq 0}">
                                <div class="row">This book is currently out of stock.</div>
                                <div class="row">
                                    <button type="button" class="btn btn-success btn-block">
                                        <i class="fa fa-heart" aria-hidden="true"></i> Add to Wishlist
                                    </button>
                                </div>
                            </c:if>
                        </div>
                        <div class="col-lg-4"></div>
                    </div>
                </div>
            </div>
            <%--End: Book Options Section--%>
            <%--Start: Book Comment Section--%>
            <div class="card mt-3">
                <div class="card-body">
                    <div class="card-title">Comments (2)</div>
                    <div class="row mt-3">
                        <div class="col-lg-2">
                            <img src="images/images/faces/fn2.png" class="rounded-circle img-fluid" alt="..."/>
                        </div>
                        <div class="col-lg-8">
                            <div class="card">
                                <div class="card-body pt-1">
                                    <div class="card-title mt-1 mb-1">
                                        Nguyễn Dũng (K15 HCM)
                                    </div>
                                    <div class="card-text">khok.</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-2" style="list-style-type: none;">
                            <li class="nav-item dropdown">
                                <a class="nav-link count-indicator" id="commentOptionDropdown" href="#"
                                   data-toggle="dropdown">
                                    <i class="ti-more"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
                                     aria-labelledby="commentOptionDropdown">
                                    <a class="dropdown-item preview-item d-flex align-items-center">
                                        <div class="preview-thumbnail">
                                            <i class="fa fa-edit"></i>
                                        </div>
                                        <div class="preview-item-content">
                                            <h6 class="preview-subject font-weight-normal mb-0">Edit Comment</h6>
                                        </div>
                                    </a>
                                    <a class="dropdown-item preview-item d-flex align-items-center">
                                        <div class="preview-thumbnail">
                                            <i class="fa fa-trash-o"></i>
                                        </div>
                                        <div class="preview-item-content">
                                            <h6 class="preview-subject font-weight-normal mb-0">Delete Comment</h6>
                                        </div>
                                    </a>
                                </div>
                            </li>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-lg-2">
                            <img src="images/images/faces/face25.jpg" class="rounded-circle img-fluid" alt="..."/>
                        </div>
                        <div class="col-lg-8">
                            <div class="card">
                                <div class="card-body pt-1">
                                    <div class="card-title mt-1 mb-1">Ni Gà (K15 HCM)</div>
                                    <div class="card-text">
                                        Yooooo... duude, this stuff takes you to frickin' heaven maan...
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-2" style="list-style-type: none;">
                            <li class="nav-item dropdown">
                                <a class="nav-link count-indicator" id="notificationDropdown" href="#"
                                   data-toggle="dropdown">
                                    <i class="ti-more"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
                                     aria-labelledby="notificationDropdown">
                                    <a class="dropdown-item preview-item d-flex align-items-center">
                                        <div class="preview-thumbnail">
                                            <i class="fa fa-edit"></i>
                                        </div>
                                        <div class="preview-item-content">
                                            <h6 class="preview-subject font-weight-normal mb-0">Edit Comment
                                            </h6>
                                        </div>
                                    </a>
                                    <a class="dropdown-item preview-item d-flex align-items-center">
                                        <div class="preview-thumbnail">
                                            <i class="fa fa-trash-o"></i>
                                        </div>
                                        <div class="preview-item-content">
                                            <h6 class="preview-subject font-weight-normal mb-0">Delete Comment
                                            </h6>
                                        </div>
                                    </a>
                                </div>
                            </li>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-lg-2">
                            <img src="images/images/faces/face9.jpg" class="rounded-circle img-fluid" alt="..."/>
                        </div>
                        <div class="col-lg-8">
                            <div class="card">
                                <div class="card-body pt-1">
                                    <div class="card-title mt-1 mb-1">
                                        Quí Bừu Lọt (K15 HCM)
                                    </div>
                                    <div class="form-group">
                                        <textarea class="form-control" id="exampleFormControlTextarea1"
                                                  rows="3"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-2">
                            <button type="button" class="btn btn-primary btn-block">
                                <i class="fa fa-paper-plane" aria-hidden="true"></i> Submit
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <%--End: Book Comment Section--%>
        </div>
        <%--End: Right Column--%>
        <%--Start: Empty Column--%>
        <div class="col-lg-2"></div>
        <%--End: Empty Column--%>
    </div>
    <%--End: Book Section--%>
    <%--Start: Related Books Section--%>
    <%--Section Title--%>
    <div class="row mt-5">
        <div class="col-lg-2"></div>
        <div class="col-lg-8">
            <div class="text-center h2">You might also like...</div>
        </div>
        <div class="col-lg-2"></div>
    </div>
    <%--Related Books--%>
    <div class="row mt-5 pb-5">
        <div class="col-lg-2"></div>
        <div class="col-lg-2">
            <div class="card">
                <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg"
                     alt="software-engineering"/>
                <div class="card-body">
                    <h5 class="card-title text-center">
                        Software Engineering 10th Edition
                    </h5>
                    <div class="text-center">
                        <a href="#" class="btn btn-info"><i class="fa fa-info-circle" aria-hidden="true"></i> More
                            Info</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-2">
            <div class="card">
                <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg"
                     alt="software-engineering"/>
                <div class="card-body">
                    <h5 class="card-title text-center">
                        Software Engineering 10th Edition
                    </h5>
                    <div class="text-center">
                        <a href="#" class="btn btn-info"><i class="fa fa-info-circle" aria-hidden="true"></i> More
                            Info</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-2">
            <div class="card">
                <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg"
                     alt="software-engineering"/>
                <div class="card-body">
                    <h5 class="card-title text-center">
                        Software Engineering 10th Edition
                    </h5>
                    <div class="text-center">
                        <a href="#" class="btn btn-info"><i class="fa fa-info-circle" aria-hidden="true"></i> More
                            Info</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-2">
            <div class="card">
                <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg"
                     alt="software-engineering"/>
                <div class="card-body">
                    <h5 class="card-title text-center">
                        Software Engineering 10th Edition
                    </h5>
                    <div class="text-center">
                        <a href="#" class="btn btn-info"><i class="fa fa-info-circle" aria-hidden="true"></i> More
                            Info</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-2"></div>
    </div>
    <%--End: Related Books Section--%>
</div>
<%--Actual Body--%>
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>