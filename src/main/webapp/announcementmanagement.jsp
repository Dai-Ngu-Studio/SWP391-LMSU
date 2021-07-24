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

    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <link rel="stylesheet" href="css/announcementmanagement.css">
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
        <c:set var="announcement_list" value="${requestScope.ANNOUNCEMENT_MANA_LIST}"/>

        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <form action="DispatchServlet">
                            <div class="d-flex flex-column align-items-center mb-5">
                                <h6><b>INFORMATION AND LIBRARY CENTER</b></h6>
                                <h3><b>ANNOUNCEMENT</b></h3>
                            </div>
                            <div>
                                <p>Dear Library Users,</p>
                                <p>
                                    Delivering textbook schedule for <%--<input type="text" placeholder="Enter semester"
                                                                            id="semester" name="semester" value=""
                                                                            required>--%>
                                    <select id="semester" name="semester" required>
                                        <option>Spring</option>
                                        <option>Summer</option>
                                        <option>Fall</option>
                                    </select>
                                    <input type="text" placeholder="Enter year" id="year" required
                                           name="year" value=""> semester at FPT University Library.
                                    Library users are required to follow below schedule:
                                </p>
                                <p class="text-danger">
                                    <b>
                                        Start: <%--<input type="text" placeholder="Enter day from" id="defaultDayFrom"
                                                      name="defaultDayFrom" value="" required>--%> <input type="date"
                                                                                                      id="defaultDateFrom"
                                                                                                      name="defaultDateFrom"
                                                                                                      value="" required>
                                        to
                                        <%--<input type="text" placeholder="Enter day to" id="defaultDayTo" required
                                               name="defaultDayTo" value="">--%> <input type="date" id="defaultDateTo"
                                                                                    name="defaultDateTo" value=""
                                                                                    required> at room no LB102.
                                    </b>
                                </p>
                                <p>
                                    Textbook for subjects: <input type="text" placeholder="Enter subject" id="subject"
                                                                  name="subject" value="" required>
                                    <b class="text-danger">from <%--<input type="text" placeholder="Enter day" id="day"
                                                                       name="day" value="" required>--%>
                                        <input type="date" id="dateFrom" name="dateFrom" value="" required>
                                        to <input type="date" id="dateTo" name="dateTo" value="" required> at room no
                                        LB101</b> .
                                </p>
                                <h3><b>Time:</b></h3>
                                <p>
                                    - Morning: From 08:30 to 11:30 <br> - Afternoon: From 13:30 to 16:30
                                </p>
                                <h3><b>Notes:</b></h3>
                                <p>
                                    - Students must return old textbooks before borrow new textbooks; <br> -
                                    Students who don't borrow and get textbooks as schedule must take responsibility
                                    of having no textbooks; <br> - Students can view information of textbooks at
                                    here; <br> - Students have to return their books before <b class="text-danger">
                                    <input type="date" placeholder="Enter return date" id="returnDate"
                                           name="returnDate" value="" required> </b>
                                </p>
                                <h3><b>Should you have any inquiry, please contact us via:</b></h3>
                                <p>
                                    <b>Phone No:</b> 024-6680 5912 <br>
                                    <b>Email:</b> lmsu@gmail.com <br> <b>Fanpage:</b> https://www.facebook.com/lmsu
                                </p>
                                <div class="d-flex flex-row justify-content-center">
                                    <button class="btn btn-secondary m-3" type="button" data-toggle="modal"
                                            id="ViewDraft" data-target="#ViewDraftModal" title="View Draft">View draft
                                    </button>
                                    <c:if test="${empty announcement_list}">
                                        <button class="btn btn-primary m-3" type="submit"
                                                name="btAction" value="Add Announcement">Save
                                        </button>
                                    </c:if>
                                    <c:if test="${not empty announcement_list}">
                                        <c:forEach var="announcement" items="${announcement_list}">
                                            <input type="hidden" value="${announcement.id}" name="txtAnnouncementID">
                                            <button class="btn btn-primary m-3" type="submit"
                                                    name="btAction" value="Update Announcement">Update
                                            </button>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="ViewDraftModal" tabindex="-1" role="dialog"
                 aria-labelledby="ariaViewDraftModal" style="display: none" aria-modal="true">
                <div class="modal-dialog mt-4" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">View Draft</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="d-flex flex-column align-items-center mb-5">
                                <h6><b>INFORMATION AND LIBRARY CENTER</b></h6>
                                <h3><b>ANNOUNCEMENT</b></h3>
                            </div>
                            <div>
                                <p>Dear Library Users,</p>
                                <p>
                                    Delivering textbook schedule for <span class="draft" id="semesterDraft"></span>
                                    <span class="draft"
                                          id="yearDraft"></span>
                                    semester at FPT University Library. Library users are required to follow below
                                    schedule:
                                </p>
                                <p class="text-danger">
                                    <b>
                                        Start: <span class="draft" id="defaultDayFromDraft"></span> <span class="draft"
                                                                                                          id="defaultDateFromDraft"></span>
                                        to
                                        <span class="draft" id="defaultDayToDraft"></span> <span class="draft"
                                                                                                 id="defaultDateToDraft"></span>
                                        at
                                        room no LB102.
                                    </b>
                                </p>
                                <p>
                                    Textbook for subjects: <span class="draft" id="subjectDraft"></span> <b
                                        class="text-danger">
                                    from <span class="draft" id="dayDraft"></span> <span class="draft"
                                                                                         id="dateFromDraft"></span> to
                                    <span class="draft"
                                          id="dateToDraft"></span> at room no LB101</b> .
                                </p>
                                <h3><b>Time:</b></h3>
                                <p>
                                    - Morning: From 08:30 to 11:30 <br> - Afternoon: From 13:30 to 16:30
                                </p>
                                <h3><b>Notes:</b></h3>
                                <p>
                                    - Students must return old textbooks before borrow new textbooks; <br> -
                                    Students who don't borrow and get textbooks as schedule must take responsibility
                                    of having no textbooks; <br> - Students can view information of textbooks at
                                    here; <br> - Students have to return their books before <b class="text-danger">
                                    <span class="draft" id="returnDateDraft"></span> </b>
                                </p>
                                <h3><b>Should you have any inquiry, please contact us via:</b></h3>
                                <p>
                                    <b>Phone No:</b> 024-6680 5912 <br>
                                    <b>Email:</b> lmsu@gmail.com <br> <b>Fanpage:</b> https://www.facebook.com/lmsu
                                </p>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <%--<button type="submit" name="btAction" value="Update Announcement" class="btn btn-primary">
                                Save
                            </button>--%>
                            <button type="button" class="btn btn-outline-primary" data-dismiss="modal">
                                Close
                            </button>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
        integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="js/announcementmanagement.js"></script>
<!-- container-scroller -->
<!-- plugins:js -->
<script src="vendors/js/vendor.bundle.base.js"></script>
<!-- endinject -->
<!-- Plugin js for this page -->

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
