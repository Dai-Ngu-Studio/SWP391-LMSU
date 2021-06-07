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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="images/images/favicon.png" />
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
                        <div class="tab-pane fade show active" id="list-information" role="tabpanel"
                             aria-labelledby="list-information-list">
                            <div class="card-body">
                                <h4 class="card-title">Account Information</h4>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="basic-addon1">ID</span>
                                    </div>
                                    <input type="text" class="form-control" placeholder="SinhVNSE151413"
                                           aria-label="User ID" aria-describedby="basic-addon1"/>
                                </div>
                                <div class="input-group mb-3">
                                    <input type="text" class="form-control" placeholder="SinhVNSE151413"
                                           aria-label="Email Address" aria-describedby="basic-addon2"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text" id="basic-addon2">@fpt.edu.vn</span>
                                    </div>
                                </div>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Phone Number</span>
                                    </div>
                                    <input type="text" class="form-control" aria-label="Phone Number"
                                           placeholder="09******11"/>
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-secondary" type="button">
                                            Edit <i class="ti ti-pencil"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--Security tab-->
                        <div class="tab-pane fade" id="list-security" role="tabpanel"
                             aria-labelledby="list-security-list">
                            <div class="card-body">
                                <h4 class="card-title">Security</h4>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Password</span>
                                    </div>
                                    <input type="password" class="form-control" aria-label="Password"
                                           placeholder="****************"/>
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-secondary" type="button" data-toggle="modal"
                                                data-target="#passwordChangeModal" style="padding: 0 1.5rem;">
                                            Change <i class="ti ti-pencil"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="passwordChangeModal" tabindex="-1" role="dialog"
                             aria-labelledby="passwordChangeModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="passwordChangeModalLabel">
                                            Change Password
                                        </h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row mb-1">
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"
                                                          id="label-current-password">Current Password</span>
                                                </div>
                                                <input type="password" class="form-control"
                                                       placeholder="Current Password" aria-label="Current Password"
                                                       aria-describedby="basic-addon1"/>
                                            </div>
                                        </div>
                                        <div class="row mb-1">
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                        <span class="input-group-text" id="label-new-password">New
                                                        Password</span>
                                                </div>
                                                <input type="password" class="form-control" placeholder="New Password"
                                                       aria-label="New Password" aria-describedby="basic-addon1"/>
                                            </div>
                                        </div>
                                        <div class="row mb-1">
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"
                                                          id="label-confirm-password">Confirm Password</span>
                                                </div>
                                                <input type="password" class="form-control"
                                                       placeholder="Confirm Password" aria-label="Confirm Password"
                                                       aria-describedby="basic-addon1"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            Close
                                        </button>
                                        <button type="button" class="btn btn-primary">
                                            Save changes
                                        </button>
                                    </div>
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
                                                   autocomplete="off" checked/>
                                            Off
                                        </label>
                                        <label class="btn btn-secondary">
                                            <input type="radio" name="options" id="onHighestRated"
                                                   autocomplete="off"/>
                                            On
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
                                            <th class="sorting_asc" tabindex="0" aria-controls="order-listing"
                                                rowspan="1" colspan="1" aria-sort="ascending"
                                                aria-label="Order #: activate to sort column descending"
                                                style="width: 54px;">Order #
                                            </th>
                                            <th class="sorting" tabindex="0" aria-controls="order-listing" rowspan="1"
                                                colspan="1" aria-label="NAME: activate to sort column ascending"
                                                style="width: 96px;">NAME
                                            </th>
                                            <th tabindex="0" aria-controls="order-listing" rowspan="1" colspan="1"
                                                aria-label="Actions: activate to sort column ascending"
                                                style="width: 64px;">Actions
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr class="odd">
                                            <td class="sorting_1">1</td>
                                            <td>Watership Down</td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="bookDetails.html" class="btn btn-light" role="button">
                                                        <i class="fa fa-eye text-primary"></i>
                                                    </a>
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
                                                                            <p class="ml-3">
                                                                                Do you want send a request to renew
                                                                                Watership Down
                                                                            </p>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-primary"
                                                                            data-dismiss="modal">Yes
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
                                        </tr>
                                        <tr class="even">
                                            <td class="sorting_1">2</td>
                                            <td>Titanic</td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="bookDetails.html" class="btn btn-light" role="button">
                                                        <i class="fa fa-eye text-primary"></i>
                                                    </a>
                                                    <button type="button" class="btn btn-light" data-toggle="modal"
                                                            data-target="#logModal1" title="Update"
                                                            data-original-title="Edit">
                                                        <i class="fa fa-refresh text-primary" aria-hidden="true"></i>
                                                    </button>
                                                    <button type="button" class="btn btn-light" data-toggle="tooltip"
                                                            title="Delete" data-original-title="Delete">
                                                        <i class="fa fa-reply text-primary" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="odd">
                                            <td class="sorting_1">3</td>
                                            <td>Introduction to Software Engineering</td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="bookDetails.html" class="btn btn-light" role="button">
                                                        <i class="fa fa-eye text-primary"></i>
                                                    </a>
                                                    <button type="button" class="btn btn-light" data-toggle="modal"
                                                            data-target="#logModal1" title="Update"
                                                            data-original-title="Edit">
                                                        <i class="fa fa-refresh text-primary" aria-hidden="true"></i>
                                                    </button>
                                                    <button type="button" class="btn btn-light" data-toggle="tooltip"
                                                            title="Delete" data-original-title="Delete">
                                                        <i class="fa fa-reply text-primary" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="even">
                                            <td class="sorting_1">4</td>
                                            <td>Clean code</td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="bookDetails.html" class="btn btn-light" role="button">
                                                        <i class="fa fa-eye text-primary"></i>
                                                    </a>
                                                    <button type="button" class="btn btn-light" data-toggle="modal"
                                                            data-target="#logModal1" title="Update"
                                                            data-original-title="Edit">
                                                        <i class="fa fa-refresh text-primary" aria-hidden="true"></i>
                                                    </button>
                                                    <button type="button" class="btn btn-light" data-toggle="tooltip"
                                                            title="Delete" data-original-title="Delete">
                                                        <i class="fa fa-reply text-primary" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </td>
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
