<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:directive.page pageEncoding="UTF-8"/>
<jsp:directive.page contentType="text/html; charset=UTF-8" language="java"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>LMSU Dashboard</title>
    <link rel="stylesheet" href="vendors/feather/feather.css">
    <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/searchpanes/1.3.0/css/searchPanes.dataTables.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/select/1.3.3/css/select.dataTables.min.css">
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
        <jsp:include page="adminheader.jsp"/>
        <div class="container-fluid page-body-wrapper">
            <jsp:include page="sidebar.jsp"></jsp:include>
            <div class="main-panel">
                <div class="content-wrapper">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title">Penalty</h4>
                            <div class="row">
                                <div class="table-responsive">
                                    <div id="order-listing_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
                                        <div class="row">
                                            <div class="table-responsive">
                                                <div class="col-sm-12">
                                                    <table id="rental-datatable" class="table dataTable no-footer"
                                                           role="grid"
                                                           aria-describedby="order-listing_info">
                                                        <thead>
                                                        <tr>
                                                            <th class="text-right">#</th>
                                                            <th class="text-left">BOOK</th>
                                                            <th class="text-left">BORROWER</th>
                                                            <th class="text-center">STATUS</th>
                                                            <th class="text-center">ACTIONS</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:set var="penaltyList" value="${requestScope.PENALTY_LIST}"/>
                                                        <c:forEach var="penalty" items="${penaltyList}"
                                                                   varStatus="counter">
                                                            <tr>
                                                                <td class="text-right">${counter.count}</td>
                                                                <td class="text-left">
                                                                        ${penalty.book.title}
                                                                </td>
                                                                <td class="text-left">${penalty.order.member.name}
                                                                    (${penalty.order.member.id})
                                                                </td>
                                                                <td class="text-center">
                                                                    <label class="badge lbPenaltyStat"
                                                                           id="lbPenaltyStat${penalty.id}"
                                                                           orderitemid="${penalty.id}"
                                                                           penaltyStatus="${penalty.penaltyStatus}"
                                                                           value="${penalty.penaltyStatus}"></label>
                                                                </td>
                                                                <td class="text-center">
                                                                    <div class="btn-group">
                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal"
                                                                                data-target="#penaltyModal${penalty.id}">
                                                                            <i class="fa fa-eye text-primary"></i>
                                                                        </button>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>
                                                    <c:forEach var="penalty" items="${penaltyList}">
                                                        <div class="modal fade"
                                                             id="penaltyModal${penalty.id}"
                                                             tabindex="-1">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title">
                                                                            Penalty Details
                                                                        </h5>
                                                                        <button type="button"
                                                                                class="close"
                                                                                data-dismiss="modal">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <div class="form-group row">
                                                                            <label class="col-12 col-form-label">
                                                                                Order ID
                                                                            </label>
                                                                            <div class="col-12">
                                                                                <input type="text"
                                                                                       id="txtOrderID${penalty.orderID}"
                                                                                       class="form-control"
                                                                                       value="${penalty.orderID}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-12 col-form-label">
                                                                                Member ID
                                                                            </label>
                                                                            <div class="col-12">
                                                                                <input type="text"
                                                                                       id="txtMemberID${penalty.order.member.id}"
                                                                                       class="form-control"
                                                                                       value="${penalty.order.member.id}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-12 col-form-label">
                                                                                Member Name
                                                                            </label>
                                                                            <div class="col-12">
                                                                                <input type="text"
                                                                                       id="txtMemberName${penalty.order.member.name}"
                                                                                       class="form-control"
                                                                                       value="${penalty.order.member.name}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-12 col-form-label">
                                                                                Book
                                                                            </label>
                                                                            <div class="col-12">
                                                                                <input type="text"
                                                                                       id="txtBookID${penalty.bookID}"
                                                                                       class="form-control"
                                                                                       value="${penalty.book.title}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-12 col-form-label">
                                                                                Return Deadline
                                                                            </label>
                                                                            <div class="col-12">
                                                                                <input type="text"
                                                                                       id="txtDeadline${penalty.bookID}"
                                                                                       class="form-control"
                                                                                       value="${penalty.returnDeadline}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-12 col-form-label">
                                                                                Penalty Amount
                                                                            </label>
                                                                            <div class="col-12">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${penalty.penaltyAmount}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row"
                                                                             orderitemid="${penalty.id}">
                                                                            <label class="col-12 col-form-label">
                                                                                Penalty Status
                                                                            </label>
                                                                            <div class="col-12">
                                                                                <input type="text"
                                                                                       class="form-control inpPenaltyStat"
                                                                                       id="inpPenaltyStat${penalty.id}"
                                                                                       orderitemid="${penalty.id}"
                                                                                       penaltyStatus="${penalty.penaltyStatus}"
                                                                                       value="${penalty.penaltyStatus}"
                                                                                       disabled/>
                                                                                <select class="form-control custom-select slPenaltyStat"
                                                                                        id="slPenaltyStat${penalty.id}"
                                                                                        orderitemid="${penalty.id}"
                                                                                        style="display: none">
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="row">
                                                                            <div class="col-12 text-center">
                                                                                <div class="btn-group groupBtnEdit"
                                                                                     orderitemid="${penalty.id}">
                                                                                    <button type="button"
                                                                                            class="btn btn-light"
                                                                                            orderitemid="${penalty.id}">
                                                                                        <h4 class="fa fa-pencil text-primary"></h4>
                                                                                    </button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="storageBtn"
                                                                         id="hiddenBtnStorage${penalty.id}"
                                                                         orderitemid="${penalty.id}"
                                                                         hidden>
                                                                        <button type="button" class="btn btn-light"
                                                                                orderitemid="${penalty.id}"
                                                                                role="confirmEdit">
                                                                            <h4 class="fa fa-check text-success"></h4>
                                                                        </button>
                                                                        <button type="button" class="btn btn-light"
                                                                                orderitemid="${penalty.id}"
                                                                                role="cancelEdit">
                                                                            <h4 class="fa fa-times text-danger"></h4>
                                                                        </button>
                                                                    </div>
                                                                    <div class="storageSl"
                                                                         id="hiddenSlStorage${penalty.id}"
                                                                         orderitemid="${penalty.id}"
                                                                         hidden>

                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary"
                                                                                data-dismiss="modal">
                                                                            Close
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <footer class="footer">
                    <div class="d-sm-flex justify-content-center justify-content-sm-between">
                        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Copyright © 2021.
                            Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin
                                template</a> from BootstrapDash. All rights reserved.</span>
                        <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Hand-crafted & made
                            with <i class="ti-heart text-danger ml-1"></i></span>
                    </div>
                </footer>
            </div>
        </div>
    </div>
    <script src="vendors/js/vendor.bundle.base.js"></script>
    <script src="vendors/datatables.net/jquery.dataTables.js"></script>
    <script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
    <script src="https://cdn.datatables.net/searchpanes/1.3.0/js/dataTables.searchPanes.min.js"></script>
    <script src="https://cdn.datatables.net/select/1.3.3/js/dataTables.select.min.js"></script>
    <script src="js/off-canvas.js"></script>
    <script src="js/hoverable-collapse.js"></script>
    <script src="js/template.js"></script>
    <script src="js/settings.js"></script>
    <script src="js/todolist.js"></script>
    <script src="js/penaltymanagement.js"></script>
</c:if>
</body>
</html>
