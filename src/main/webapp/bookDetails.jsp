<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Book Details</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="images/images/favicon.png" />
</head>

<body>
    <script>
        $(function() {
            $('#includedHeader').load('header.html');
        });
    </script>
    <script>
        $(function() {
            $('#includedNavbar').load('navbar.html');
        });
    </script>
    <script>
        $(function() {
            $('#includedScrollToTop').load('scrolltotop.html');
        });
    </script>
    <script>
        $(function() {
            $('#includedFooter').load('footer.html');
        });
    </script>
    <div id="includedHeader"></div>
    <div id="includedNavbar"></div>
    <div id="includedScrollToTop"></div>
    <!--Actual Body-->

    <c:set var="bookObj" value="${requestScope.BOOK_OBJECT}"/>
    <div class="bg-light">
        <!--Start: Book Section-->
        <div class="row pt-5">
            <!--Start: Empty Column-->
            <div class="col-lg-2"></div>
            <!--End: Empty Column-->
            <!--Start: Left Column-->
            <div class="col-lg-3">
                <!--Start: Book Cover Image-->
                <div class="text-center">
                    <img src="images/software-engineering.jpg" class="rounded img-fluid img-thumbnail" alt="..." />
                </div>
                <!--End: Book Cover Image-->
                <!--Start: Book Metadata-->
                <div class="card mt-2">
                    <div class="card-body">
                        <div class="card-title">Book Information</div>
                        <ul class="list-group">
                            <li class="list-group-item mt-0 mb-0">
                                <div class="row">
                                    <!--Not yet implemented: Physical or E-Book-->
                                    <div class="col-lg-6"><strong>Format</strong></div>
                                    <div class="col-lg-6">Physical</div>
                                    <!--Not yet implemented: Physical or E-Book-->
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
                <!--End: Book Metadata-->
            </div>
            <!--End: Left Column-->
            <!--Start: Right Column-->
            <div class="col-lg-5">
                <div class="card">
                    <div class="card-body">
                        <span class="card-title mb-0 pr-2">${bookObj.title}</span>
                        <span class="card-text text-primary">
                            <i class="fa fa-star" aria-hidden="true"></i>
                            <i class="fa fa-star" aria-hidden="true"></i>
                            <i class="fa fa-star" aria-hidden="true"></i>
                            <i class="fa fa-star" aria-hidden="true"></i>
                            <i class="fa fa-star-half-o" aria-hidden="true"></i> (52 ratings)
                        </span>
                        <div class="card-text mt-2">
                            By <a href="#" class="badge badge-secondary">${bookObj.authorName}</a>
                        </div>
                        <div class="card-title mt-2 mb-0">Description</div>
                        <div class="card-text">
                            ${bookObj.description}
                        </div>
                    </div>
                </div>
                <div class="card mt-3">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#addToCartModal">
                                    <i class="fa fa-cart-plus" aria-hidden="true"></i> Add to Cart
                                </button>
                            </div>
                        </div>
                    </div>
                    <!--Start: Add to Cart-->
                    <div class="modal fade" id="addToCartModal" tabindex="-1" role="dialog" aria-labelledby="addToCartModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <div class="row h6 text-danger">This book is currently out of stock.</div>
                                    <div class="row h5 mt-2">
                                        Would you like to add it to your wishlist instead?
                                    </div>
                                    <div class="row mt-4">
                                        <div class="col-lg-6">
                                            <button type="button" class="btn btn-danger btn-block" data-dismiss="modal">
                                                <i class="fa fa-ban" aria-hidden="true"></i> No
                                            </button>
                                        </div>
                                        <div class="col-lg-6">
                                            <button type="button" class="btn btn-success btn-block">
                                                <i class="fa fa-heart" aria-hidden="true"></i> Yes
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--End: Add to Cart-->
                </div>
                <div class="card mt-3">
                    <div class="card-body">
                        <div class="card-title">Comments (2)</div>
                        <div class="row mt-3">
                            <div class="col-lg-2">
                                <img src="images/images/faces/fn2.png" class="rounded-circle img-fluid" alt="..." />
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
                                    <a class="nav-link count-indicator" id="commentOptionDropdown" href="#" data-toggle="dropdown">
                                        <i class="ti-more"></i>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="commentOptionDropdown">
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
                                <img src="images/images/faces/face25.jpg" class="rounded-circle img-fluid" alt="..." />
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
                                    <a class="nav-link count-indicator" id="notificationDropdown" href="#" data-toggle="dropdown">
                                        <i class="ti-more"></i>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="notificationDropdown">
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
                                <img src="images/images/faces/face9.jpg" class="rounded-circle img-fluid" alt="..." />
                            </div>
                            <div class="col-lg-8">
                                <div class="card">
                                    <div class="card-body pt-1">
                                        <div class="card-title mt-1 mb-1">
                                            Quí Bừu Lọt (K15 HCM)
                                        </div>
                                        <div class="form-group">
                                            <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
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
            </div>
            <!--End: Right Column-->
            <!--Start: Empty Column-->
            <div class="col-lg-2"></div>
            <!--End: Empty Column-->
        </div>
        <!--End: Book Section-->
        <!--Start: Related Books Section-->
        <!--Section Title-->
        <div class="row mt-5">
            <div class="col-lg-2"></div>
            <div class="col-lg-8">
                <div class="text-center h2">You might also like...</div>
            </div>
            <div class="col-lg-2"></div>
        </div>
        <!--Related Books-->
        <div class="row mt-5 pb-5">
            <div class="col-lg-2"></div>
            <div class="col-lg-2">
                <div class="card">
                    <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg" alt="software-engineering" />
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
                    <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg" alt="software-engineering" />
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
                    <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg" alt="software-engineering" />
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
                    <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg" alt="software-engineering" />
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
        <!--End: Related Books Section-->
    </div>
    <!--Actual Body-->
    <div id="includedFooter"></div>
</body>

</html>