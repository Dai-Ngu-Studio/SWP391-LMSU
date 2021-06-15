<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: NDungx
  Date: 6/7/2021
  Time: 12:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Setting</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/userAccountSetting.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <!-- plugins:css -->
    <link rel="stylesheet" href="vendors/feather/feather.css">
    <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
    <!-- endinject -->
    <!-- Plugin css for this page -->

    <!-- End plugin css for this page -->
    <!-- inject:css -->

    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <%--jquery CDN--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <!-- endinject -->
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>

<div class="p-5 bg-light">
    <div class="row">
        <div class="col-lg-3 grid-margin">
            <div class="card pb-3">
                <div class="card-body">
                    <h4 class="card-title">Settings</h4>
                    <div class="col-12">
                        <div class="list-group" id="list-tab" role="tablist">
                            <a class="list-group-item list-group-item-action active" id="list-information-list"
                               data-toggle="list" href="#list-information" role="tab" aria-controls="information"><i
                                    class="ti-user"></i> Account
                                Information</a>
                            <a class="list-group-item list-group-item-action" id="list-security-list" data-toggle="list"
                               href="#list-security" role="tab" aria-controls="security"><i class="ti-lock"></i>
                                Security</a>
                            <a class="list-group-item list-group-item-action" id="list-notifications-list"
                               data-toggle="list" href="#list-notifications" role="tab" aria-controls="notifications"><i
                                    class="ti-announcement"></i> Notifications</a>
                            <a class="list-group-item list-group-item-action" id="list-miscellaneous-list"
                               data-toggle="list" href="#list-miscellaneous" role="tab" aria-controls="miscellaneous"><i
                                    class="ti-agenda"></i> Book Borrowing</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-9 grid-margin stretch-card">
            <div class="card">
                <div class="col-12">
                    <div class="tab-content" id="nav-tabContent" style="border:none">
                        <!--Account Information tab-->
                        <c:set var="profile" value="${sessionScope.LOGIN_USER}"/>
                        <c:set var="email_split" value="${fn:split(profile.email, '@')}"/>
                        <div class="tab-pane fade show active" id="list-information" role="tabpanel"
                             aria-labelledby="list-information-list">
                            <div class="card-body">
                                <h4 class="card-title">Account Information</h4>

                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" style="padding: 0 1.7rem;"
                                              id="basic-addon1">Member ID</span>
                                    </div>
                                    <input type="text" class="form-control" value="${profile.id}"
                                           aria-label="User ID" aria-describedby="basic-addon1"
                                           style="background-color: #fff" readonly/>
                                </div>
                                <c:if test="${not empty email_split}">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text px-5">Email</span>
                                        </div>
                                        <input type="text" class="form-control" value="${email_split[0]}"
                                               aria-label="Email Address" aria-describedby="basic-addon2"
                                               style="background-color: #fff" readonly/>
                                        <div class="input-group-append">
                                            <span class="input-group-text" style="padding: 0 1.3rem;"
                                                  id="basic-addon2">@fpt.edu.vn</span>
                                        </div>
                                    </div>
                                </c:if>

                                <form action="DispatchServlet">
                                    <input type="hidden" value="${profile.id}" name="pk">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Phone Number</span>
                                        </div>
                                        <input type="text" class="form-control" aria-label="Phone Number"
                                               value="${profile.phoneNumber}" pattern="^[0-9]{1,10}$"
                                               name="txtPhone"/>
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-secondary" type="submit" name="btAction"
                                                    value="Change Phone Number">
                                                Edit <i class="ti ti-pencil"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>

                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" style="padding: 0 1.3rem;">Semester No</span>
                                    </div>
                                    <input type="text" class="form-control" aria-label="Phone Number"
                                           value="${profile.semester}" readonly style="background-color: #fff"/>
                                </div>
                            </div>
                        </div>
                        <!--Security tab-->
                        <div class="tab-pane fade" id="list-security" role="tabpanel"
                             aria-labelledby="list-security-list">
                            <div class="card-body">
                                <h4 class="card-title">Security</h4>
                                <div class="input-group mb-3">
                                    <form action="DispatchServlet" class="w-100">
                                        <input type="hidden" value="${profile.id}" name="pk">
                                        <div class="px-3">
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                    <span class="input-group-text" style="padding: 0 .8rem"
                                                          id="label-current-password">Current Password</span>
                                                    </div>
                                                    <input type="password" class="form-control"
                                                           placeholder="Current Password"
                                                           aria-label="Current Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtCurrentPassword" value=""/>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text px-4"
                                                              id="label-new-password">New Password</span>
                                                    </div>
                                                    <input type="password" class="form-control"
                                                           placeholder="New Password"
                                                           aria-label="New Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtNewPassword" value=""/>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                    <span class="input-group-text"
                                                          id="label-confirm-password">Confirm Password</span>
                                                    </div>
                                                    <input type="password" class="form-control"
                                                           placeholder="Confirm Password"
                                                           aria-label="Confirm Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtConfirmPassword" value=""/>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="submit" class="btn btn-primary" name="btAction"
                                                value="Change Password">
                                            Save changes
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!--Notifications tab-->
                        <div class="tab-pane fade" id="list-notifications" role="tabpanel"
                             aria-labelledby="list-notifications-list" style="color: black !important;">
                            <div class="row mb-1 align-items-center">
                                <div class="col-lg-5">
                                    Notify me about newest arrivals
                                </div>
                                <div class="col-lg-5">
                                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                        <label class="btn btn-secondary active">
                                            <input type="radio" name="options" id="offNewArrival"
                                                   autocomplete="off" checked/>
                                            Off
                                        </label>
                                        <label class="btn btn-secondary">
                                            <input type="radio" name="options" id="onNewArrival"
                                                   autocomplete="off"/>
                                            On
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-1 align-items-center">
                                <div class="col-lg-5">
                                    Notify me about highest rated book of the week
                                </div>
                                <div class="col-lg-5">
                                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                        <label class="btn btn-secondary active">
                                            <input type="radio" name="options" id="offHighestRated"
                                                   autocomplete="off" checked/>Off
                                        </label>
                                        <label class="btn btn-secondary">
                                            <input type="radio" name="options" id="onHighestRated"
                                                   autocomplete="off"/>On
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="list-miscellaneous" role="tabpanel"
                             aria-labelledby="list-miscellaneous-list">
                            <div class="row">
                                <div class="col-sm-12">

                                    <table id="order-listing" class="table dataTable no-footer" role="grid"
                                           aria-describedby="order-listing_info">
                                        <thead>
                                        <tr role="row">
                                            <th style="width: 100px;">#</th>
                                            <th style="width: 73%;">NAME</th>
                                            <th style="width: 64px;">Actions</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="order_items" value="${requestScope.ORDER_ITEMS}"/>
                                        <c:forEach var="order" items="${order_items}" varStatus="counter">
                                            <tr class="odd">
                                                <form action="DispatchServlet">
                                                    <!--Start: Renew Book Item Form-->
                                                    <td class="sorting_1"
                                                        style="text-align: center">${counter.count}</td>
                                                    <td style="text-align: left">
                                                        ${order.title}
                                                    </td>
                                                </form>

                                                <form action="DispatchServlet">
                                                    <td>
                                                        <div class="btn-group">
                                                            <input type="hidden" name="bookPk" value="${order.bookID}">
                                                            <input type="hidden" name="orderItemsPk" value="${order.id}">

                                                            <button type="submit" class="btn btn-light"
                                                                    name="btAction" value="View Details">
                                                                <i class="fa fa-eye text-primary"></i>
                                                            </button>
                                                            <button type="button" class="btn btn-light" data-toggle="modal"
                                                                    data-target="#renewModal" title="Renew"
                                                                    data-original-title="Renew">
                                                                <i class="fa fa-refresh text-primary" aria-hidden="true"></i>
                                                            </button>
                                                            <div class="modal fade" id="renewModal" tabindex="-1" role="dialog"
                                                                 aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                                                <div class="modal-dialog" role="document">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h5 class="modal-title" id="exampleModalLongTitle">
                                                                                Request renew book
                                                                            </h5>
                                                                            <button type="button" class="close"
                                                                                    data-dismiss="modal" aria-label="Close">
                                                                                <span
                                                                                        aria-hidden="true">&times;
                                                                                </span>
                                                                            </button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <form>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Reason
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control"
                                                                                               name="txtReason"
                                                                                               value="">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row" id="rowPublishDate">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Extend Date
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <input class="form-control"
                                                                                               type="date"
                                                                                               value="2021-06-03"
                                                                                               name="txtExtendDate"
                                                                                        >
                                                                                    </div>
                                                                                </div>
                                                                            </form>
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="submit" class="btn btn-primary"
                                                                                    data-dismiss="modal" name="btAction" value="Renew Book">
                                                                                Yes
                                                                            </button>
                                                                            <button type="button"
                                                                                    class="btn btn-outline-primary"
                                                                                    data-dismiss="modal">Cancel
                                                                            </button>
                                                                        </div>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <button type="button" class="btn btn-light" data-toggle="tooltip"
                                                                    title="Delete" data-original-title="Delete">
                                                                <i class="fa fa-reply text-primary" aria-hidden="true"></i>
                                                            </button>
                                                        </div>
                                                    </td>
                                                </form>
                                        </c:forEach>
                                        </tr>
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
</div>

<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>
</html>
