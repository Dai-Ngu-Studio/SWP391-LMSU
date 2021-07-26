<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>LMSU Dashboard</title>
    <!--materializeCss -->
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="css/chip.css">
    <!--end: materializeCss -->
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
    <!-- icon -->
    <script src="js/iconpro.js"></script>
    <!-- CSS Autocomplete -->
    <link rel="stylesheet" href="css/autocomplete.css">
    <script src="js/jquery.validate.min.js"></script>
</head>
<body>
<div class="container-scroller">
    <!-- partial:../../partials/_navbar.html -->
    <jsp:include page="adminheader.jsp"/>
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
                            <div class="alert alert-danger">
                                <strong>Error!</strong> There are invalid information at row number
                                <c:forEach var="indexRow" items="${invalidRowList}">
                                    ${indexRow}
                                </c:forEach>

                            </div>

                        </c:if>

                        <c:set var="bookAuthorMap" value="${requestScope.BOOK_AUTHOR_MAP}"/>
                        <c:set var="bookList" value="${requestScope.BOOK_LIST}"/>

                        <c:set var="existedInvalidBook" value="false"/>
                        <c:if test="${not empty bookList}">
                            <c:forEach var="book" items="${bookList}">
                                <c:if test="${empty bookAuthorMap.get(book.bookID)}">
                                    <c:set var="existedInvalidBook" value="true"/>
                                </c:if>
                            </c:forEach>
                        </c:if>

                        <input type="hidden" id="existedInvalidBook" value="${existedInvalidBook}">

                        <div class="row">
                            <div class="table-responsive">
                                <div class="col-12">
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
                                        <c:if test="${not empty bookList}">
                                            <c:forEach var="book" items="${bookList}" varStatus="counter">
                                                <!--Start: Book Item Row-->
                                                <tr>

                                                    <td style="text-align: center">${counter.count}</td>
                                                    <td style="text-align: left">${book.title}</td>
                                                    <td style="text-align: right">${book.quantity}</td>
                                                    <td style="text-align: center">
                                                        <c:if test="${not empty bookAuthorMap.get(book.bookID)}">
                                                            <c:if test="${book.quantity > 0}">
                                                                <label class="badge badge-success">Available</label>
                                                            </c:if>
                                                            <c:if test="${book.quantity == 0}">
                                                                <label class="badge badge-warning">Unavailable</label>
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${empty bookAuthorMap.get(book.bookID)}">
                                                            <label class="badge badge-danger">Invalid author</label>
                                                        </c:if>
                                                    </td>
                                                    <td style="text-align: center">
                                                        <form action="DispatchServlet"
                                                              enctype="multipart/form-data"
                                                              method="POST">
                                                            <input type="hidden" value="${book.bookID}"
                                                                   name="pk">
                                                            <input type="hidden" value="${book.bookID}"
                                                                   name="bookPk">
                                                            <c:if test="${not empty requestScope.SHOWING_INVALID}">
                                                                <input type="hidden" name="showInvalidList"
                                                                       value="True">
                                                            </c:if>
                                                            <div class="btn-group">
                                                                    <%--<button type="submit" class="btn btn-light"
                                                                            name="btAction" value="View Details">
                                                                        <i class="fa fa-eye text-primary"></i>
                                                                    </button>--%>
                                                                    <%--Button and view modal--%>
                                                                <button type="button" class="btn btn-light"
                                                                        data-toggle="modal"
                                                                        data-target="#viewModal${counter.count}"
                                                                        title="View"
                                                                        data-original-title="View">
                                                                    <i class="fa fa-eye text-primary"></i>
                                                                </button>
                                                                <div class="modal fade"
                                                                     id="viewModal${counter.count}"
                                                                     tabindex="-1"
                                                                     role="dialog"
                                                                     aria-labelledby="exampleModalLongTitle"
                                                                     aria-hidden="true">
                                                                    <div class="modal-dialog" role="document">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title"
                                                                                    id="modalTitle${counter.count}">
                                                                                    Book Log</h5>
                                                                                <button type="button" class="close"
                                                                                        data-dismiss="modal"
                                                                                        aria-label="Close">
                                                                                        <span
                                                                                                aria-hidden="true">&times;</span>
                                                                                </button>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <fieldset disabled>
                                                                                    <div class="form-group row">
                                                                                        <label class="col-sm-3 col-form-label">Book
                                                                                            Cover
                                                                                        </label>

                                                                                        <div class="col-sm-9">
                                                                                            <img class="rounded float-right"
                                                                                                 style="height: 280px; width: auto;"
                                                                                                 src="${pageContext.request.contextPath}/image/${book.coverPath}"
                                                                                                 id="coverPictureUpdate2${book.bookID}"
                                                                                                 alt="Book cover"
                                                                                                 onerror="this.onerror=null; this.src='images/imagenotfound.jpg';"
                                                                                            />
                                                                                            <input type="hidden"
                                                                                                   name="txtCoverFile"
                                                                                                   class="form-control"
                                                                                                   value="${book.coverPath}">
                                                                                        </div>
                                                                                    </div>

                                                                                    <div class="form-group row">
                                                                                        <label class="col-sm-3 col-form-label">Title</label>
                                                                                        <div class="col-sm-9">
                                                                                            <input type="text"
                                                                                                   class="form-control"
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
                                                                                                   value="${book.isbnThirteen}">
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="form-group row">
                                                                                        <label class="col-sm-3 col-form-label">Description
                                                                                        </label>
                                                                                        <div class="col-sm-9">
                                                                        <textarea class="form-control"
                                                                                  rows="5">${book.description}
                                                                        </textarea>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="form-group row">
                                                                                        <div class="table-responsive">
                                                                                            <table class="table table-hover">
                                                                                                <thead>
                                                                                                <tr>
                                                                                                    <th scope="col">
                                                                                                        Author ID
                                                                                                    </th>
                                                                                                    <th scope="col">
                                                                                                        Author Name
                                                                                                    </th>

                                                                                                </tr>
                                                                                                </thead>
                                                                                                <tbody>
                                                                                                    <%--<c:set var="authorMap" value="${requestScope.AUTHOR_MAP}"/>
                                                                                                    <c:set var="keyAuthorID" value="${authorMap.keySet()}"/> --%>
                                                                                                <c:set var="authorBook"
                                                                                                       value="${bookAuthorMap.get(book.bookID)}"/>
                                                                                                <c:if test="${not empty authorBook}">
                                                                                                    <c:forEach
                                                                                                            var="bookOfAuthor"
                                                                                                            items="${authorBook}">
                                                                                                        <tr>
                                                                                                            <td>${bookOfAuthor.authorID} </td>
                                                                                                            <td>${bookOfAuthor.authorName} </td>
                                                                                                        </tr>
                                                                                                    </c:forEach>
                                                                                                </c:if>

                                                                                                </tbody>
                                                                                            </table>
                                                                                        </div>

                                                                                    </div>
                                                                                </fieldset>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="button"
                                                                                        class="btn btn-primary"
                                                                                        data-dismiss="modal">Close
                                                                                </button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                    <%--End button and view modal--%>

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
                                                                     aria-hidden="true"
                                                                >
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
                                                                                               name="txtUpdateTitle"
                                                                                               class="form-control textField"
                                                                                               value="${book.title}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Author Name
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               id="authorAutoComplete${book.bookID}"
                                                                                               class="form-control"
                                                                                               onfocus="inputForAutoComplete(this);"

                                                                                        >
                                                                                        <c:set var="authors"
                                                                                               value="${bookAuthorMap.get(book.bookID)}"/>
                                                                                        <c:if test="${not empty authors}">
                                                                                            <c:forEach var="author"
                                                                                                       items="${authors}">
                                                                                                <div class="chip">
                                                                                                        ${author.authorName}
                                                                                                    <i class="close fal fa-times"></i>
                                                                                                    <input type="hidden"
                                                                                                           name="txtAuthorID"
                                                                                                           value="${author.authorID}">
                                                                                                </div>
                                                                                            </c:forEach>
                                                                                        </c:if>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Subject
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control"
                                                                                               id="subjectAutoComplete${book.bookID}"
                                                                                               value="${book.subject.name}"
                                                                                               onfocus="inputForAutoCompleteSubject(this);"
                                                                                        >
                                                                                        <input type="hidden"
                                                                                               name="txtUpdateSubjectID"
                                                                                               value=""/>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Publisher
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control textField"
                                                                                               name="txtUpdatePublisher"
                                                                                               value="${book.publisher}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Publication date
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="date"
                                                                                               class="form-control"
                                                                                               name="txtUpdatePubliDate"
                                                                                               value="${book.publicationDate}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Price in VNĐ
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="number"
                                                                                               class="form-control priceField"
                                                                                               name="txtUpdatePrice"
                                                                                               value="${book.price}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Quantity
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="number"
                                                                                               class="form-control quantityField"
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
                                                                                               class="form-control isbnTen"
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
                                                                                               class="form-control isbnThirteen"
                                                                                               name="txtUpdateISBNThirteen"
                                                                                               value="${book.isbnThirteen}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Description
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <textarea
                                                                                                class="form-control descriptionField"
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
                                                        </form>
                                                        <script>
                                                            $("#updateForm").validate({
                                                                rules: {
                                                                    field${book.bookID}: {
                                                                        required: true,
                                                                        minlength: 3
                                                                    }
                                                                }
                                                            });
                                                        </script>
                                                    </td>
                                                </tr>
                                                <!--End: Book Item Row-->
                                            </c:forEach>
                                        </c:if>
                                        </tbody>
                                    </table>
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
<!--materializeJS -->
<!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<!--end: materializeJS -->
<!-- plugins:js -->
<%--<script src="vendors/js/vendor.bundle.base.js"></script>--%>
<!-- endinject -->
<!-- Plugin js for this page -->
<script src="vendors/datatables.net/jquery.dataTables.js"></script>
<script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>

<!-- inject:js -->
<script src="js/off-canvas.js"></script>
<script src="js/hoverable-collapse.js"></script>
<script src="js/template.js"></script>
<script src="js/settings.js"></script>
<script src="js/todolist.js"></script>
<!-- endinject -->
<script type="text/javascript" src="src/jquery.autocomplete.subject.js"></script>
<script type="text/javascript" src="src/jquery.autocomplete.js"></script>

</body>
<script src="js/bookmanagement.js"></script>
</html>
