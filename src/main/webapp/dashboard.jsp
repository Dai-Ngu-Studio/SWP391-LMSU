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
    <script src="js/iconpro.js"></script>
    <link rel="stylesheet" href="css/dashboard.css">
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.4.1/chart.min.js"
            integrity="sha512-5vwN8yor2fFT9pgPS9p9R7AszYaNn0LkQElTXIsZFCL7ucT8zDCAqlQXDdaqgA1mZP47hdvztBMsIoFxq/FyyQ=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
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
                <div class="row" id="statisticGroup">
                    <div class="col-md-3 col-sm-6" id="tBook">
                        <a href="ShowBookServlet" style="text-decoration: none;">
                            <div class="rectangle">
                                <p class="totalNumber">${TOTAL_BOOKS}</p>
                                <img src="images/v3_8.png">
                                <p class="totalClass">Total books</p>
                                <div class="blackBar">
                                    <p>More info <i class="fas fa-arrow-circle-right"></i></p>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-3 col-sm-6" id="tStudent">
                        <a href="ShowMemberServlet" style="text-decoration: none;">
                            <div class="rectangle">
                                <p class="totalNumber">${TOTAL_ACTIVE_USER}</p>
                                <img src="images/v3_72.png">
                                <p class="totalClass">Total active users</p>
                                <div class="blackBar">
                                    <p>More info <i class="fas fa-arrow-circle-right"></i></p>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-3 col-sm-6" id="tReturned">
                        <a href="ShowReturnOrdersServlet" style="text-decoration: none;">
                            <div class="rectangle">
                                <c:if test="${empty TODAY_RETURNED}">
                                    <p class="totalNumber" style="font-size: 30px">No data</p>
                                </c:if>
                                <c:if test="${not empty TODAY_RETURNED}">
                                    <p class="totalNumber">${TODAY_RETURNED}</p>
                                </c:if>

                                <img src="images/v3_75.png" style="-webkit-filter: invert(0);">
                                <p class="totalClass">Returned today</p>
                                <div class="blackBar">
                                    <p>More info <i class="fas fa-arrow-circle-right"></i></p>
                                </div>
                            </div>
                        </a>

                    </div>
                    <div class="col-md-3 col-sm-6" id="tBorrowed">
                        <a href="ShowOrdersServlet" style="text-decoration: none;">
                            <div class="rectangle">
                                <c:if test="${empty TODAY_BORROWED}">
                                    <p class="totalNumber" style="font-size: 30px">No data</p>
                                </c:if>
                                <c:if test="${not empty TODAY_BORROWED}">
                                    <p class="totalNumber">${TODAY_BORROWED}</p>
                                </c:if>

                                <img src="images/v3_76.png" style="-webkit-filter: invert(0);">
                                <p class="totalClass">Borrowed today</p>
                                <div class="blackBar">
                                    <p>More info <i class="fas fa-arrow-circle-right"></i></p>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-11">

                        <canvas id="myChart" height="100" style="padding-left: 50px"></canvas>
                        <c:set var="mapBorrowed" value="${requestScope.MAP_BORROWED}"/>
                        <c:set var="mapReturned" value="${requestScope.MAP_RETURNED}"/>
                        <script>
                            var ctx = document.getElementById('myChart');
                            var myChart = new Chart(ctx, {
                                type: 'bar',
                                data: {
                                    labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
                                    datasets: [{
                                        label: 'Borrowed',
                                        data: [${mapBorrowed.get("1")},
                                            ${mapBorrowed.get("2")},
                                            ${mapBorrowed.get("3")},
                                            ${mapBorrowed.get("4")},
                                            ${mapBorrowed.get("5")},
                                            ${mapBorrowed.get("6")},
                                            ${mapBorrowed.get("7")},
                                            ${mapBorrowed.get("8")},
                                            ${mapBorrowed.get("9")},
                                            ${mapBorrowed.get("10")},
                                            ${mapBorrowed.get("11")},
                                            ${mapBorrowed.get("12")},
                                        ],
                                        backgroundColor: ['#C4C4C4']
                                    },{
                                        label: 'Returned ',
                                        data: [${mapReturned.get("1")},
                                            ${mapReturned.get("2")},
                                            ${mapReturned.get("3")},
                                            ${mapReturned.get("4")},
                                            ${mapReturned.get("5")},
                                            ${mapReturned.get("6")},
                                            ${mapReturned.get("7")},
                                            ${mapReturned.get("8")},
                                            ${mapReturned.get("9")},
                                            ${mapReturned.get("10")},
                                            ${mapReturned.get("11")},
                                            ${mapReturned.get("12")},
                                        ],
                                        backgroundColor: ['#00A559']
                                    }]
                                },
                                options: {
                                    scales: {
                                        y: {
                                            beginAtZero: true
                                        }
                                    }
                                }
                            });
                        </script>
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
<%--<script src="vendors/js/vendor.bundle.base.js"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<!-- endinject -->
<!-- Plugin js for this page -->
<script src="vendors/chart.js/Chart.min.js"></script>
<!-- End plugin js for this page -->
<!-- inject:js -->
<script src="js/off-canvas.js"></script>
<script src="js/hoverable-collapse.js"></script>
<script src="js/template.js"></script>
<script src="js/settings.js"></script>
<script src="js/todolist.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->
<script src="js/chart.js"></script>
<!-- End custom js for this page-->
</body>
</html>
