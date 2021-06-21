<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  Date: 6/2/2021
  Time: 8:29 PM
--%>
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
    <!-- endinject -->
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
    <script src="js/bookmanagement.js"></script>
    <script src="js/iconpro.js"></script>
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
        <!-- partial:../../partials/_sidebar.html -->
        <nav class="sidebar sidebar-offcanvas" id="sidebar">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" href="dashboard.jsp">
                        <i class="icon-grid menu-icon"></i>
                        <span class="menu-title">Dashboard</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">
                        <i class="icon-paper menu-icon"></i>
                        <span class="menu-title">Library</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="collapse" href="#user-management" aria-expanded="false"
                       aria-controls="ui-basic">
                        <i class="icon-head menu-icon"></i>
                        <span class="menu-title">Users</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="collapse" id="user-management">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"><a class="nav-link" href="ShowMemberServlet">Member</a></li>
                            <li class="nav-item"><a class="nav-link" href="ShowStaffServlet">Staff</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ShowBookServlet">
                        <i class="icon-book menu-icon"></i>
                        <span class="menu-title">Books</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ShowAuthorServlet">
                        <i class="fal fa-user-edit menu-icon"></i>
                        <span class="menu-title">Authors</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="BookRentalManagement.jsp">
                        <i class="icon-archive menu-icon"></i>
                        <span class="menu-title">Book Rental</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="log.jsp">
                        <i class="icon-file-subtract menu-icon"></i>
                        <span class="menu-title">Import Log</span>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Data table</h4>
                        <div class="row">
                            <div class="col-12">
                                <div class="table-responsive">
                                    <div id="order-listing_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
                                        <div class="row">
                                            <div class="col-sm-12 col-md-4">
                                                <div class="dataTables_length" id="order-listing_length"><label>Show
                                                    <select name="order-listing_length"
                                                            aria-controls="order-listing"
                                                            class="custom-select custom-select-sm form-control">
                                                        <option value="5">5</option>
                                                        <option value="10">10</option>
                                                        <option value="15">15</option>
                                                        <option value="-1">All</option>
                                                    </select> entries</label></div>
                                            </div>
                                            <div class="col-sm-12 col-md-8">

                                                <div id="order-listing_filter" class="dataTables_filter">
                                                    <!--Start: Add Book Form-->
                                                    <form action="DispatchServlet" enctype="multipart/form-data"
                                                          method="POST">
                                                        <input type="search" class="form-control"
                                                               placeholder="Search"
                                                               name="txtSearchValue" value="${param.txtSearchValue}"
                                                               aria-controls="order-listing"
                                                               id="searchBox">
                                                        <button class="btn btn-primary" type="submit"
                                                                name="btAction" value="SearchBook"
                                                                style="border-radius: 5px"><i class="fa fa-search"></i>
                                                        </button>

                                                        <button class="btn btn-primary" type="button"
                                                                style="border-radius: 5px"
                                                                data-toggle="modal"
                                                                data-target="#AddBookModal"
                                                                title="Add a book">
                                                            <i class="fa fa-plus"></i></button>

                                                        <div class="modal fade" id="AddBookModal"
                                                             tabindex="-1"
                                                             role="dialog"
                                                             aria-labelledby="AddBookModalLongTitle"
                                                             aria-hidden="true">
                                                            <div class="modal-dialog" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title"
                                                                            id="AddBookModalLongTitle">
                                                                            Add book
                                                                        </h5>
                                                                        <button type="button"
                                                                                class="close"
                                                                                data-dismiss="modal"
                                                                                aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>
                                                                    <div class="modal-body" id="addModalBody">
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                ISBN 10 Digits
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       id="txtISBNTen"
                                                                                       class="form-control"
                                                                                       value=""
                                                                                       name="txtISBNTen"
                                                                                       oninput="checkISBN();"
                                                                                >
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                ISBN 13 Digits
                                                                            </label>
                                                                            <div class="col-sm-9">
                                                                                <input type="text"
                                                                                       id="txtISBNThirteen"
                                                                                       class="form-control"
                                                                                       value=""
                                                                                       name="txtISBNThirteen"
                                                                                       oninput="checkISBN();"
                                                                                >
                                                                            </div>
                                                                        </div>
<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">Book--%>
<%--                                                                                Cover--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <img class="rounded float-right"--%>
<%--                                                                                     style="height: 400px;--%>
<%--                                                                                     width: auto;"--%>
<%--                                                                                     src="images/NotAvailable.jpg"--%>
<%--                                                                                     id="coverPicture"--%>
<%--                                                                                     alt="Book cover"--%>
<%--                                                                                >--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>

<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <div class="col-sm-3">--%>
<%--                                                                            </div>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <div class="custom-file">--%>
<%--                                                                                    <input type="file"--%>
<%--                                                                                           class="custom-file-input"--%>
<%--                                                                                           id="customFileAdd"--%>
<%--                                                                                           name="coverPicture"--%>
<%--                                                                                           onchange="readURL(this, 'coverPicture');"--%>
<%--                                                                                    >--%>
<%--                                                                                    <label class="custom-file-label"--%>
<%--                                                                                           for="customFileAdd">Choose--%>
<%--                                                                                        Image </label>--%>
<%--                                                                                </div>--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>
<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <div class="col-sm-3">--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>
<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">--%>
<%--                                                                                Title--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <input type="text"--%>
<%--                                                                                       class="form-control"--%>
<%--                                                                                       value=""--%>
<%--                                                                                       name="txtTitle"--%>
<%--                                                                                >--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>
<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">--%>
<%--                                                                                Author ID--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <input type="text"--%>
<%--                                                                                       class="form-control"--%>
<%--                                                                                       value=""--%>
<%--                                                                                       name="txtAuthorID"--%>
<%--                                                                                >--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>
<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">--%>
<%--                                                                                Subject ID--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <input type="text"--%>
<%--                                                                                       class="form-control"--%>
<%--                                                                                       value=""--%>
<%--                                                                                       name="txtSubjectID"--%>
<%--                                                                                >--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>
<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">--%>
<%--                                                                                Publisher--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <input type="text"--%>
<%--                                                                                       class="form-control"--%>
<%--                                                                                       value=""--%>
<%--                                                                                       name="txtPublisher"--%>
<%--                                                                                >--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>
<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">--%>
<%--                                                                                Publish Date--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <input class="form-control"--%>
<%--                                                                                       type="date"--%>
<%--                                                                                       value="2021-06-03"--%>
<%--                                                                                       name="txtPublishDate"--%>
<%--                                                                                >--%>
<%--                                                                            </div>--%>

<%--                                                                        </div>--%>

<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">--%>
<%--                                                                                Price--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <input type="number"--%>
<%--                                                                                       class="form-control"--%>
<%--                                                                                       value=""--%>
<%--                                                                                       name="txtPrice"--%>
<%--                                                                                >--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>
<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">--%>
<%--                                                                                Quantity--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <input type="number"--%>
<%--                                                                                       class="form-control"--%>
<%--                                                                                       value=""--%>
<%--                                                                                       name="txtQuantity"--%>
<%--                                                                                >--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>

<%--                                                                        <div class="form-group row">--%>
<%--                                                                            <label class="col-sm-3 col-form-label">Description--%>
<%--                                                                            </label>--%>
<%--                                                                            <div class="col-sm-9">--%>
<%--                                                                                <textarea class="form-control"--%>
<%--                                                                                          rows="5"--%>
<%--                                                                                          name="txtDescription"> </textarea>--%>
<%--                                                                            </div>--%>
<%--                                                                        </div>--%>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="submit"
                                                                                class="btn btn-primary"
                                                                                name="btAction"
                                                                                value="AddBook"
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
                                                    </form>
                                                    <!--End: Add Book Form-->
                                                    <%--Start: BUTTON AND ADD FILE MODAL--%>
                                                    <form action="DispatchServlet" enctype="multipart/form-data"
                                                          method="POST">
                                                        <input type="hidden" name="btAction" value="AddBook">
                                                        <input type="hidden" name="isAddFile" value="True">
                                                        <label class="btn btn-primary" style="border-radius: 5px">
                                                            <input type="file"
                                                                   hidden
                                                                   name="fileAdd"
                                                                   onchange="form.submit();"
                                                            >
                                                            <i class="fas fa-file-plus"></i>
                                                        </label>
                                                    </form>
                                                    <%--End: BUTTON AND ADD FILE MODAL--%>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <table id="order-listing" class="table dataTable no-footer my-2"
                                                       role="grid"
                                                       aria-describedby="order-listing_info">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="sorting_asc" tabindex="0"
                                                            aria-controls="order-listing" rowspan="1" colspan="1"
                                                            aria-sort="ascending"
                                                            aria-label="Order #: activate to sort column descending"
                                                            style="width: 0px; text-align: center">#
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="order-listing"
                                                            rowspan="1" colspan="1"
                                                            aria-label="BOOK NAME: activate to sort column ascending"
                                                            style="width: 96px; text-align: left">NAME
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="order-listing"
                                                            rowspan="1" colspan="1"
                                                            aria-label="QUANTITY: activate to sort column ascending"
                                                            style="width: 96px; text-align: right">QUANTITY
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="order-listing"
                                                            rowspan="1" colspan="1"
                                                            aria-label="STATUS: activate to sort column ascending"
                                                            style="width: 67px; text-align: center">STATUS
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="order-listing"
                                                            rowspan="1" colspan="1"
                                                            aria-label="Actions: activate to sort column ascending"
                                                            style="width: 64px; text-align: center">Actions
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:set var="bookList" value="${requestScope.BOOK_LIST}"/>

                                                    <c:forEach var="book" items="${bookList}"
                                                               varStatus="counter">
                                                        <tr class="odd">
                                                            <form action="DispatchServlet">
                                                                <!--Start: Book Item Form-->
                                                                <td class="sorting_1"
                                                                    style="text-align: center">${counter.count}</td>
                                                                <td style="text-align: left">
                                                                        ${book.title}
                                                                </td>
                                                                <td style="text-align: right">${book.quantity}</td>
                                                                <td style="text-align: center">
                                                                    <c:if test="${book.quantity > 0}">
                                                                        <label class="badge badge-success">Available</label>
                                                                    </c:if>
                                                                    <c:if test="${book.quantity == 0}">
                                                                        <label class="badge badge-danger">Unavailable</label>
                                                                    </c:if>
                                                                </td>
                                                            </form>
                                                            <!--End: Book Item Form-->
                                                            <!--Start: Delete Book Form-->
                                                            <form action="DispatchServlet">
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
                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal"
                                                                                data-target="#updateModal${book.bookID}"
                                                                                title="Update"
                                                                                data-original-title="Edit">
                                                                            <i class="fa fa-pencil text-primary"></i>
                                                                        </button>

                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal"
                                                                                data-target="#deleteModal${book.bookID}"
                                                                                title="Delete"
                                                                                data-original-title="Remove">
                                                                            <i class="fa fa-times text-primary"></i>
                                                                        </button>

                                                                    </div>
                                                                </td>
                                                            </form>

                                                            <form action="DispatchServlet" enctype="multipart/form-data"
                                                                  method="POST">
                                                                <input type="hidden" value="${book.bookID}"
                                                                       name="pk">
                                                                <input type="hidden" name="txtSearchValue"
                                                                       value="${param.txtSearchValue}"/>
                                                                <div class="modal fade" id="deleteModal${book.bookID}"
                                                                     tabindex="-1"
                                                                     role="dialog"
                                                                     aria-labelledby="ariaDeleteModal${book.bookID}"
                                                                     aria-hidden="true">
                                                                    <div class="modal-dialog" role="document">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title"
                                                                                    id="exampleModalLongTitle2">
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
                                                            </form>
                                                            <!--End: Delete Book Form-->
                                                            <!--Start: Update Book Form-->
                                                            <form action="DispatchServlet" enctype="multipart/form-data"
                                                                  method="POST">
                                                                <input type="hidden" value="${book.bookID}"
                                                                       name="pk">
                                                                <input type="hidden" name="txtSearchValue"
                                                                       value="${param.txtSearchValue}"/>
                                                                <div class="modal fade" id="updateModal${book.bookID}"
                                                                     tabindex="-1"
                                                                     role="dialog"
                                                                     aria-labelledby="ariaUpdateModal${book.bookID}"
                                                                     aria-hidden="true">
                                                                    <div class="modal-dialog" role="document">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title"
                                                                                    id="exampleModalLongTitle">
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
                                                                                            var
                                                                                            abc="'coverPictureUpdate${book.bookID}'";
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
                                                                                        Author ID
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control"
                                                                                               name="txtUpdateAuthorID"
                                                                                               value="${book.authorID}">
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
                                                                                                    <textarea
                                                                                                            class="form-control"
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
                                                            </form>
                                                            <!--End: Update Book Form-->
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-12 col-md-5">
                                                <div class="dataTables_info" id="order-listing_info" role="status"
                                                     aria-live="polite">Showing 1 to 5 of 10 entries
                                                </div>
                                            </div>
                                            <div class="col-sm-12 col-md-7">
                                                <div class="dataTables_paginate paging_simple_numbers"
                                                     id="order-listing_paginate">
                                                    <ul class="pagination">
                                                        <li class="paginate_button page-item previous disabled"
                                                            id="order-listing_previous"><a href="#"
                                                                                           aria-controls="order-listing"
                                                                                           data-dt-idx="0" tabindex="0"
                                                                                           class="page-link">Previous</a>
                                                        </li>
                                                        <li class="paginate_button page-item active"><a href="#"
                                                                                                        aria-controls="order-listing"
                                                                                                        data-dt-idx="1"
                                                                                                        tabindex="0"
                                                                                                        class="page-link">1</a>
                                                        </li>
                                                        <li class="paginate_button page-item "><a href="#"
                                                                                                  aria-controls="order-listing"
                                                                                                  data-dt-idx="2"
                                                                                                  tabindex="0"
                                                                                                  class="page-link">2</a>
                                                        </li>
                                                        <li class="paginate_button page-item next"
                                                            id="order-listing_next"><a href="#"
                                                                                       aria-controls="order-listing"
                                                                                       data-dt-idx="3" tabindex="0"
                                                                                       class="page-link">Next</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
<!-- End plugin js for this page -->
<!-- inject:js -->
<script src="js/off-canvas.js"></script>
<script src="js/hoverable-collapse.js"></script>
<script src="js/template.js"></script>
<script src="js/settings.js"></script>
<script src="js/todolist.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->

<!-- End custom js for this page-->
</body>
</html>
