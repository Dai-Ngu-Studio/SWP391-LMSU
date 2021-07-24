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
    <!-- endinject -->
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
    <script src="js/subjectmanagement.js"></script>
    <script src="js/iconpro.js"></script>
</head>
<body>
<div class="container-scroller">
    <!-- partial:../../partials/_navbar.html -->
    <jsp:include page="adminheader.jsp"/>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial -->
        <jsp:include page="sidebar.jsp"/>
        <!-- partial:../../partials/_sidebar.html -->
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Data table</h4>
                        <div class="table-responsive">
                            <div id="order-listing_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
                                <div class="row">
                                    <div class="table-responsive">
                                        <div class="col-sm-12">
                                            <table id="author-datatable" class="table dataTable no-footer my-2"
                                                   role="grid"
                                                   aria-describedby="order-listing_info">
                                                <thead>
                                                <tr>
                                                    <th style="width: 0px; text-align: center">#</th>
                                                    <th style="width: 96px; text-align: left">NAME</th>
                                                    <th style="width: 96px; text-align: right">SEMESTER NO</th>
                                                    <%--<th style="width: 67px; text-align: left"></th>--%>
                                                    <th style="width: 64px; text-align: center">Actions</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>
                                                <c:set var="subjectMap" value="${requestScope.SUBJECT_MAP}"/>
                                                <c:set var="countSubject" value="${requestScope.COUNT_SUBJECT}"/>
                                                <c:forEach var="subject" items="${subjectList}"
                                                           varStatus="counter">
                                                    <tr>
                                                        <form action="DispatchServlet"
                                                              enctype="multipart/form-data"
                                                              method="POST">
                                                            <!--Start: Author Item Form-->
                                                            <td class="sorting_1"
                                                                style="text-align: center">${counter.count}</td>
                                                            <td style="text-align: left">
                                                                    ${subject.name}
                                                            </td>
                                                            <td style="text-align: right">${subject.semester_no}</td>

                                                            <input type="hidden" value="${subject.id}"
                                                                   name="pk">
                                                            <input type="hidden" value="${subject.id}"
                                                                   name="subjectPK">
                                                            <input type="hidden" name="txtSearchValue"
                                                                   value="${param.txtSearchValue}"/>
                                                            <td style="text-align: center;">
                                                                <div class="btn-group">
                                                                    <button type="button" class="btn btn-light"
                                                                            data-toggle="modal"
                                                                            data-target="#viewModal${counter.count}"
                                                                            title="View"
                                                                            data-original-title="View">
                                                                        <i class="fa fa-eye text-primary"></i>
                                                                    </button>
                                                                    <button type="button" class="btn btn-light"
                                                                            data-toggle="modal"
                                                                            data-target="#updateModal${subject.id}"
                                                                            title="Update"
                                                                            data-original-title="Edit">
                                                                        <i class="fa fa-pencil text-primary"></i>
                                                                    </button>
                                                                    <button type="button" class="btn btn-light"
                                                                            data-toggle="modal"
                                                                            data-target="#deleteModal${subject.id}"
                                                                            title="Delete"
                                                                            data-original-title="Remove">
                                                                        <i class="fa fa-times text-primary"></i>
                                                                    </button>
                                                                    <!--Start: View Subject Modal-->
                                                                    <div class="modal fade"
                                                                         id="viewModal${counter.count}"
                                                                         tabindex="-1"
                                                                         role="dialog"
                                                                         aria-labelledby="exampleModalLongTitle"
                                                                         aria-hidden="true">
                                                                        <div class="modal-dialog modal-xl"
                                                                             role="document">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h5 class="modal-title"
                                                                                        id="modalTitle${counter.count}">
                                                                                        Subject Log</h5>
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
                                                                                            <label class="col-sm-1 col-form-label">Subject
                                                                                                Name</label>
                                                                                            <div class="col-sm-10">
                                                                                                <input type="text"
                                                                                                       class="form-control"
                                                                                                       name="txtUpdateSubjectName"
                                                                                                       value="${subject.name}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-1 col-form-label">Semester
                                                                                                No</label>
                                                                                            <div class="col-sm-10">
                                                                                                <input type="text"
                                                                                                       class="form-control"
                                                                                                       name="txtUpdateSemester"
                                                                                                       value="${subject.semester_no}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <div class="table-responsive">
                                                                                                <table class="table table-hover">
                                                                                                    <thead>
                                                                                                    <tr>
                                                                                                        <th scope="col">
                                                                                                            Book ID
                                                                                                        </th>
                                                                                                        <th scope="col">
                                                                                                            Book Title
                                                                                                        </th>
                                                                                                        <th scope="col">
                                                                                                            Publisher
                                                                                                        </th>
                                                                                                        <th scope="col">
                                                                                                            Publish Date
                                                                                                        </th>
                                                                                                        <th scope="col">
                                                                                                            Price
                                                                                                        </th>
                                                                                                        <th scope="col">
                                                                                                            Quantity
                                                                                                        </th>
                                                                                                        <th scope="col">
                                                                                                            Rating
                                                                                                        </th>
                                                                                                    </tr>
                                                                                                    </thead>
                                                                                                    <tbody>
                                                                                                        <%--<c:set var="authorMap" value="${requestScope.AUTHOR_MAP}"/>
                                                                                                        <c:set var="keyAuthorID" value="${authorMap.keySet()}"/> --%>
                                                                                                    <c:set var="subjectBook"
                                                                                                           value="${subjectMap.get(subject.id)}"/>
                                                                                                    <c:forEach
                                                                                                            var="bookOfSubject"
                                                                                                            items="${subjectBook}">
                                                                                                        <tr>
                                                                                                            <td>${bookOfSubject.bookID} </td>
                                                                                                            <td>${bookOfSubject.title} </td>
                                                                                                            <td>${bookOfSubject.publisher} </td>
                                                                                                            <td>${bookOfSubject.publicationDate} </td>
                                                                                                            <td>${bookOfSubject.price} </td>
                                                                                                            <td>${bookOfSubject.quantity} </td>
                                                                                                            <td>${bookOfSubject.avgRating} </td>
                                                                                                        </tr>
                                                                                                    </c:forEach>
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
                                                                    <!--End: View Subject Modal-->

                                                                    <!--Start: Update Subject Modal-->
                                                                    <div class="modal fade"
                                                                         id="updateModal${subject.id}"
                                                                         tabindex="-1"
                                                                         role="dialog"
                                                                         aria-labelledby="ariaUpdateModal${subject.id}"
                                                                         aria-hidden="true">
                                                                        <div class="modal-dialog" role="document">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h5 class="modal-title"
                                                                                        id="exampleModalLongTitle3">
                                                                                        Edit Subject Details
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
                                                                                        <label class="col-sm-3 col-form-label">Subject
                                                                                            Name</label>
                                                                                        <div class="col-sm-9">
                                                                                            <input type="text"
                                                                                                   class="form-control"
                                                                                                   name="txtUpdateSubjectName"
                                                                                                   value="${subject.name}">
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="form-group row">
                                                                                        <label class="col-sm-3 col-form-label">Semester
                                                                                            No</label>
                                                                                        <div class="col-sm-9">
                                                                                            <input type="text"
                                                                                                   class="form-control"
                                                                                                   name="txtUpdateSemester"
                                                                                                   value="${subject.semester_no}">
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="submit"
                                                                                            name="btAction"
                                                                                            value="Update Subject"
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
                                                                    <!--End: Update Subject Modal-->

                                                                    <!--Start: Delete Subject Modal-->
                                                                    <div class="modal fade"
                                                                         id="deleteModal${subject.id}"
                                                                         tabindex="-1"
                                                                         role="dialog"
                                                                         aria-labelledby="ariaDeleteModal${subject.id}"
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
                                                                                <c:if test="${countSubject!=null}">
                                                                                    <c:choose>
                                                                                        <c:when test="${countSubject
                                                                                    .get(subject.id) > 0}">
                                                                                            <div class="modal-body">
                                                                                                <div class="row">
                                                                                                    <div class="col-12 text-center">
                                                                                                        You haven't
                                                                                                        deleted
                                                                                                        all
                                                                                                        the
                                                                                                        books from
                                                                                                        this subject
                                                                                                    </div>

                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="modal-footer">
                                                                                                <button type="button"
                                                                                                        class="btn btn-outline-primary"
                                                                                                        data-dismiss="modal">
                                                                                                    Close
                                                                                                </button>
                                                                                            </div>
                                                                                        </c:when>
                                                                                        <c:when test="${countSubject
                                                                                    .get(subject.id) eq null}">
                                                                                            <div class="modal-body">
                                                                                                Do you want to delete
                                                                                                this
                                                                                                subject?
                                                                                            </div>
                                                                                            <div class="modal-footer">
                                                                                                <button type="submit"
                                                                                                        name="btAction"
                                                                                                        value="Delete Subject"
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
                                                                                        </c:when>
                                                                                    </c:choose>
                                                                                </c:if>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <!--End: Delete Author Modal-->
                                                                </div>
                                                            </td>
                                                        </form>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
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

