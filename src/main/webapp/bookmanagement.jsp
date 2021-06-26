<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>LMSU Dashboard</title>
    <!-- plugins:css -->
    <link rel="stylesheet" href="vendors/feather/feather.css">
    <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
    <!-- endinject -->
    <!-- Plugin css for this page -->
    <link rel="stylesheet" href="vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <!-- endinject -->
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <!-- Jquery JS -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- Jquery UI -->
    <script class="jsbin" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
    <!-- icon -->
    <script src="js/iconpro.js"></script>
    <!-- CSS Autocomplete -->
    <link rel="stylesheet" href="css/autocomplete.css">
</head>
<body>
<div class="container-scroller">
    <!-- partial:../../partials/_navbar.html -->
    <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            <a class="navbar-brand brand-logo mr-5" href="dashboard.jsp"><img src="images/LMSU LOGO DASHBOARD.svg"
                                                                              class="mr-2" alt="logo"
                                                                              style="margin-left: 36px;"/></a>
            <a class="navbar-brand brand-logo-mini" href="dashboard.jsp"><img src="images/LMSU LOGO MINI DASHBOARD.svg"
                                                                              alt="logo"/></a>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
            <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
                <span class="icon-menu"></span>
            </button>
            <ul class="navbar-nav mr-lg-2">
                <li class="nav-item nav-search d-none d-lg-block">
                    <div class="input-group">
                        <div class="input-group-prepend hover-cursor" id="navbar-search-icon">
                                <span class="input-group-text" id="search">
                                    <i class="icon-search"></i>
                                </span>
                        </div>
                        <input type="text" class="form-control" id="navbar-search-input" placeholder="Search now"
                               aria-label="search" aria-describedby="search">
                    </div>
                </li>
            </ul>
            <ul class="navbar-nav navbar-nav-right">
                <li class="nav-item dropdown">
                    <a class="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#"
                       data-toggle="dropdown">
                        <i class="icon-bell mx-0"></i>
                        <span class="count"></span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
                         aria-labelledby="notificationDropdown">
                        <p class="mb-0 font-weight-normal float-left dropdown-header">Notifications</p>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-success">
                                    <i class="ti-info-alt mx-0"></i>
                                </div>
                            </div>
                            <div class="preview-item-content">
                                <h6 class="preview-subject font-weight-normal">Application Error</h6>
                                <p class="font-weight-light small-text mb-0 text-muted">
                                    Just now
                                </p>
                            </div>
                        </a>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-warning">
                                    <i class="ti-settings mx-0"></i>
                                </div>
                            </div>
                            <div class="preview-item-content">
                                <h6 class="preview-subject font-weight-normal">Settings</h6>
                                <p class="font-weight-light small-text mb-0 text-muted">
                                    Private message
                                </p>
                            </div>
                        </a>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-info">
                                    <i class="ti-user mx-0"></i>
                                </div>
                            </div>
                            <div class="preview-item-content">
                                <h6 class="preview-subject font-weight-normal">New user registration</h6>
                                <p class="font-weight-light small-text mb-0 text-muted">
                                    2 days ago
                                </p>
                            </div>
                        </a>
                    </div>
                </li>
                <li class="nav-item nav-profile dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" id="profileDropdown">
                        <img src="images/images/faces/fn2.png" alt="profile" style="border-radius: 50%;"/>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
                        <a class="dropdown-item" href="ShowProfileServlet">
                            <i class="ti-settings text-primary"></i> Profile
                        </a>
                        <a class="dropdown-item" href="LogoutServlet">
                            <i class="ti-power-off text-primary"></i> Logout
                        </a>
                    </div>
                </li>
            </ul>
            <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
                    data-toggle="offcanvas">
                <span class="icon-menu"></span>
            </button>
        </div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial -->
        <jsp:include page="sidebar.jsp"/>
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Books Management</h4>
                        <c:set var="invalidRowList" value="${requestScope.INVALID_ROW_LIST}"/>
                        <c:if test="${not empty invalidRowList}">
                            <c:forEach var="indexRow" items="${invalidRowList}">
                                <div class="alert alert-danger">
                                    <strong>Error!</strong> There are invalid information at row number ${indexRow}
                                </div>
                            </c:forEach>
                        </c:if>

                        <div class="row">
                            <div class="table-responsive">
                                <table id="book-datatable"
                                       class="table table-hover dataTable no-footer my-2"
                                       role="grid"
                                       aria-describedby="order-listing_info">
                                    <thead>
                                    <tr>
                                        <th style="width: 0px; text-align: center">#</th>
                                        <th style="width: 96px; text-align: left">NAME</th>
                                        <th style="width: 96px; text-align: right">QUANTITY</th>
                                        <th style="width: 67px; text-align: center">STATUS</th>
                                        <th style="width: 64px; text-align: center">Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:set var="bookList" value="${requestScope.BOOK_LIST}"/>
                                    <c:forEach var="book" items="${bookList}" varStatus="counter">
                                        <!--Start: Book Item Row-->
                                        <tr>
                                            <form action="DispatchServlet"
                                                  enctype="multipart/form-data"
                                                  method="POST">
                                                <td style="text-align: center">${counter.count}</td>
                                                <td style="text-align: left">${book.title}</td>
                                                <td style="text-align: right">${book.quantity}</td>
                                                <td style="text-align: center">
                                                    <c:if test="${book.quantity > 0}">
                                                        <label class="badge badge-success">Available</label>
                                                    </c:if>
                                                    <c:if test="${book.quantity == 0}">
                                                        <label class="badge badge-danger">Unavailable</label>
                                                    </c:if>
                                                </td>
                                                <input type="hidden" value="${book.bookID}"
                                                       name="pk">
                                                <input type="hidden" value="${book.bookID}"
                                                       name="bookPk">
                                                <input type="hidden" name="txtSearchValue"
                                                       value="${param.txtSearchValue}"/>
                                                <td style="text-align: center">
                                                    <div class="btn-group">
                                                        <button type="submit" class="btn btn-light"
                                                                name="btAction" value="View Details">
                                                            <i class="fa fa-eye text-primary"></i>
                                                        </button>
                                                            <%--Button and update modal--%>
                                                        <button type="button" class="btn btn-light"
                                                                data-toggle="modal"
                                                                data-target="#updateModal${book.bookID}"
                                                                title="Update"
                                                                data-original-title="Edit">
                                                            <i class="fa fa-pencil text-primary"></i>
                                                        </button>
                                                        <div class="modal fade" id="updateModal${book.bookID}"
                                                             tabindex="-1"
                                                             role="dialog"
                                                             aria-labelledby="ariaUpdateModal${book.bookID}"
                                                             aria-hidden="true">
                                                            <div class="modal-dialog" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title">
                                                                            Edit Book Details
                                                                        </h5>
                                                                        <button type="button"
                                                                                class="close"
                                                                                data-dismiss="modal"
                                                                                aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">Book
                                                                                Cover
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <img class="rounded float-right"
                                                                                     style="height: 400px; width: auto;"
                                                                                     src="${pageContext.request.contextPath}/image/${book.coverPath}"
                                                                                     id="coverPictureUpdate${book.bookID}"
                                                                                     alt="Book cover"
                                                                                     onerror="this.onerror=null; this.src='images/NotAvailable.jpg';"
                                                                                />
                                                                                <input type="hidden"
                                                                                       name="txtCoverFile"
                                                                                       value="${book.coverPath}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <div class="col-sm-3">
                                                                            </div>
                                                                            <div class="col-sm-9">
                                                                                <div class="custom-file">
                                                                                    <input type="file"
                                                                                           class="custom-file-input"
                                                                                           id="customFileUpdate${book.bookID}"
                                                                                           name="coverPicture"
                                                                                           onchange="readURL(this, 'coverPictureUpdate${book.bookID}');"
                                                                                    >
                                                                                    <label class="custom-file-label"
                                                                                           for="customFileUpdate${book.bookID}">Choose
                                                                                        Image
                                                                                    </label>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">Title</label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       name="txtUpdateTitle"
                                                                                       value="${book.title}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                Subject ID
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       name="txtUpdateSubjectID"
                                                                                       value="${book.subjectID}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                Publisher
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       name="txtUpdatePublisher"
                                                                                       value="${book.publisher}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                Publication date
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       name="txtUpdatePubliDate"
                                                                                       value="${book.publicationDate}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                Price
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       name="txtUpdatePrice"
                                                                                       value="${book.price}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                Quantity
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       name="txtUpdateQuantity"
                                                                                       value="${book.quantity}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                ISBN Ten
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       name="txtUpdateISBNTen"
                                                                                       value="${book.isbnTen}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                ISBN Thirteen
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       name="txtUpdateISBNThirteen"
                                                                                       value="${book.isbnThirteen}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">Description
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                        <textarea class="form-control"
                                                                                  id="Textarea${book.bookID}"
                                                                                  name="txtUpdateDescription"
                                                                                  rows="5">${book.description}
                                                                        </textarea>
                                                                            </div>
                                                                        </div>

                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="submit"
                                                                                name="btAction"
                                                                                value="Update Book"
                                                                                class="btn btn-primary"
                                                                        >
                                                                            Save
                                                                        </button>
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary"
                                                                                data-dismiss="modal">
                                                                            Close
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                            <%--End button and update modal--%>

                                                            <%--Button and Delete modal--%>
                                                        <button type="button" class="btn btn-light"
                                                                data-toggle="modal"
                                                                data-target="#deleteModal${book.bookID}"
                                                                title="Delete"
                                                                data-original-title="Remove">
                                                            <i class="fa fa-times text-primary"></i>
                                                        </button>
                                                        <div class="modal fade" id="deleteModal${book.bookID}"
                                                             tabindex="-1"
                                                             role="dialog"
                                                             aria-labelledby="ariaDeleteModal${book.bookID}"
                                                             aria-hidden="true">
                                                            <div class="modal-dialog" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title">
                                                                            WARNING
                                                                        </h5>
                                                                        <button type="button"
                                                                                class="close"
                                                                                data-dismiss="modal"
                                                                                aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        Do you want to delete this book?
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="submit"
                                                                                name="btAction"
                                                                                value="Delete Book"
                                                                                class="btn btn-primary"
                                                                        >
                                                                            Yes
                                                                        </button>
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary"
                                                                                data-dismiss="modal">
                                                                            Cancel
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                            <%--End button and Delete modal--%>
                                                    </div>
                                                </td>
                                            </form>
                                        </tr>
                                        <!--End: Book Item Row-->
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- content-wrapper ends -->
            <!-- partial:../../partials/_footer.html -->
            <footer class="footer">
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
                        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Copyright © 2021.
                            Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin
                                template</a> from BootstrapDash. All rights reserved.</span>
                    <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Hand-crafted & made
                            with <i class="ti-heart text-danger ml-1"></i></span>
                </div>
            </footer>
            <!-- partial -->
        </div>
        <!-- main-panel ends -->
    </div>
    <!-- page-body-wrapper ends -->
</div>
<!-- container-scroller -->

<!-- plugins:js -->
<script src="vendors/js/vendor.bundle.base.js"></script>
<!-- endinject -->
<!-- Plugin js for this page -->
<script src="vendors/datatables.net/jquery.dataTables.js"></script>
<script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<!-- JS -->
<script src="js/bookmanagement.js"></script>
<!-- End plugin js for this page -->
<!-- inject:js -->
<script src="js/off-canvas.js"></script>
<script src="js/hoverable-collapse.js"></script>
<script src="js/template.js"></script>
<script src="js/settings.js"></script>
<script src="js/todolist.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->
<script type="text/javascript" src="src/jquery.autocomplete.js"></script>
<!-- End custom js for this page-->
</body>
</html>
