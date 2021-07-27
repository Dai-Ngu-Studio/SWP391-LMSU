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
    <!-- inject:css -->
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <script src="js/iconpro.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<c:set var="role" value="${sessionScope.LOGIN_USER.roleID}"/>
<c:if test="${role eq '4' or role eq 'null'}">
    <c:redirect url="IndexServlet"></c:redirect>
</c:if>

<c:if test="${role eq '1' or role eq '2' or role eq '3'}">
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
                            <div class="row">
                                <div class="table-responsive">
                                    <div class="col-12">
                                        <table id="order-listing"
                                               class="table table-hover dataTable no-footer"
                                               role="grid"
                                               aria-describedby="order-listing_info">
                                            <thead>
                                            <tr>
                                                <th style="width: 54px; text-align: center">#</th>
                                                <th style="width: 72px; text-align: left">Full Name</th>
                                                <th style="width: 110px; text-align: left">Email</th>
                                                <th style="width: 110px; text-align: right">Phone</th>
                                                <th style="width: 110px; text-align: center">Status</th>
                                                <th style="width: 64px; text-align: center">Actions</th>
                                            </tr>

                                            </thead>
                                            <tbody>
                                            <c:set var="feedbackList" value="${requestScope.FEEDBACK_LIST}"/>
                                            <c:forEach var="feedback" items="${feedbackList}"
                                                       varStatus="counter">
                                                <tr>
                                                        <%--<form action="DispatchServlet"
                                                              enctype="multipart/form-data"
                                                              method="POST">--%>

                                                    <td style="text-align: center">${counter.count}</td>
                                                    <td style="text-align: left">${feedback.fullName}</td>
                                                    <td style="text-align: left">${feedback.email}</td>
                                                    <td style="text-align: right">${feedback.phone}</td>
                                                    <td style="text-align: center">
                                                        <c:choose>
                                                            <c:when test="${feedback.resolveStatus == false}">
                                                                <label class="badge badge-secondary">Unresolved</label>
                                                            </c:when>
                                                            <c:when test="${feedback.resolveStatus == true}">
                                                                <label class="badge badge-primary">Resolved</label>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>

                                                    <td style="text-align: center">
                                                        <button class="btn btn-outline-primary"
                                                                data-toggle="modal"
                                                                data-target="#feedbackModal${counter.count}">View
                                                            Details
                                                        </button>
                                                        <div class="modal fade"
                                                             id="feedbackModal${counter.count}"
                                                             tabindex="-1"
                                                             role="dialog"
                                                             aria-labelledby="exampleModalLongTitle"
                                                             aria-hidden="true">
                                                            <div class="modal-dialog modal-xl" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title"
                                                                            id="modalTitle${counter.count}">
                                                                            Feedback details</h5>
                                                                        <button type="button" class="close"
                                                                                data-dismiss="modal"
                                                                                aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>
                                                                    <form action="DispatchServlet"
                                                                          enctype="multipart/form-data"
                                                                          method="POST">
                                                                        <input type="hidden" name="feedbackID"
                                                                               value="${feedback.feedbackID}">
                                                                        <div class="modal-body">
                                                                            <fieldset disabled>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Attachment
                                                                                    </label>

                                                                                    <div class="col-sm-9">
                                                                                        <img class="rounded float-right"
                                                                                             style="height: 280px; width: auto;"
                                                                                             src="${pageContext.request.contextPath}/image/${feedback.attachment}"
                                                                                             id="coverPictureUpdate${feedback.feedbackID}"
                                                                                             alt="Feedback attachment"
                                                                                             onerror="this.onerror=null; this.src='images/imagenotfound.jpg';"
                                                                                        />
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">Full
                                                                                        Name</label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control"
                                                                                               value="${feedback.fullName}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">Email
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control"
                                                                                               value="${feedback.email}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">Phone
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control"
                                                                                               value="${feedback.phone}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">Feedback
                                                                                        Type
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control"
                                                                                               value="${feedback.feedbackType}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">Feedback
                                                                                        Message
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                    <textarea
                                                                                            class="form-control"
                                                                                            rows="5">
                                                                                            ${feedback.feedbackMessage}
                                                                                    </textarea>
                                                                                    </div>
                                                                                </div>
                                                                            </fieldset>
                                                                        </div>
                                                                        <c:choose>
                                                                            <c:when test="${feedback.resolveStatus == false}">
                                                                                <div class="modal-footer">
                                                                                    <button type="submit"
                                                                                            class="btn btn-primary"
                                                                                            name="btAction"
                                                                                            value="Resolve Feedback">
                                                                                        Resolved
                                                                                    </button>
                                                                                    <button type="button"
                                                                                            class="btn btn-primary"
                                                                                            data-dismiss="modal">Close
                                                                                    </button>
                                                                                </div>
                                                                            </c:when>
                                                                            <c:when test="${feedback.resolveStatus == true}">
                                                                                <div class="modal-footer">
                                                                                    <button type="button"
                                                                                            class="btn btn-primary"
                                                                                            data-dismiss="modal">Close
                                                                                    </button>
                                                                                </div>
                                                                            </c:when>
                                                                        </c:choose>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                        <%--</form>--%>
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
    <%--    Datatable--%>
    <link rel="stylesheet" type="text/css"
          href="https://cdn.datatables.net/v/bs4/jqc-1.12.4/dt-1.10.25/datatables.min.css"/>

    <script type="text/javascript"
            src="https://cdn.datatables.net/v/bs4/jqc-1.12.4/dt-1.10.25/datatables.min.js"></script>
    <script type="text/javascript" src="js/feedbackmanagement.js"></script>
    <%--    End inject dataTable--%>
    <!-- inject:js -->
    <script src="js/off-canvas.js"></script>
    <script src="js/hoverable-collapse.js"></script>
    <script src="js/template.js"></script>
    <script src="js/settings.js"></script>
    <script src="js/todolist.js"></script>
    <!-- endinject -->
    <!-- Custom js for this page-->

    <!-- End custom js for this page-->
</c:if>
</body>
</html>
