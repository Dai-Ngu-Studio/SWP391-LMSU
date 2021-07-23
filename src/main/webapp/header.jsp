<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body {
            font-size: 15px;
        }

        .header {
            background-color: #fff;
            display: flex;
            justify-content: space-around;
            padding-top: .5rem;
            padding-bottom: .5rem;
            width: 100%;
            height: 65px;
            align-items: center;
            position: fixed;
            box-shadow: 0px 0px 18px rgba(0, 0, 0, 0.2);
            z-index: 999;
        }

        .logo {
            width: 10%;
        }

        .logo img {
            width: 100%;
        }

        .search {
            display: flex;
            /* height: 70%; */
            /* margin-left: 35%; */
            transform: translateX(100px);
        }

        .searchInput {
            padding-top: 4px !important;
            padding-bottom: 4px !important;
            width: 26vw !important;
            border: 1px solid #748DDB !important;
            font-size: 15px !important;
            padding-left: 10px !important;
        }

        .search button {
            padding: .2rem .65rem;
            border-radius: 0px 6px 6px 0px;
        }

        .search .btn-primary {
            background-color: #748DDB;
            border-color: #748DDB;
        }

        .search .btn-primary i {
            color: #fff !important;
        }

        @media (max-width: 991px) {
            .navbar .navbar-menu-wrapper .search {
                margin-left: 0%;
            }
        }

        .left {
            display: flex;
            align-items: center;
        }

        .profile a {
            font-size: 1.2vw;
            color: #748DDB;
            text-decoration: none !important;
        }

        .logout {
            margin-left: 10px;
        }

        .logout button {
            padding: .2rem .5rem;
        }

        .btn-filter {
            color: #748DDB !important;
            border-color: #748DDB !important;
            border-radius: 6px 0 0 6px !important;
            padding: 4.5px 10px !important;
        }

        .navbar-menu-wrapper li {
            margin: 0;
        }

        .navbar {
            box-shadow: 0px 0px 18px rgba(0, 0, 0, 0.2) !important;
        }

        @media (max-width: 991px) {
            .navbar .navbar-menu-wrapper .navbar-nav .nav-item.dropdown {
                position: static;
            }

            .navbar .navbar-menu-wrapper .navbar-nav .nav-item.dropdown .navbar-dropdown {
                left: unset !important;
                right: 20px !important;
                top: 60px;
                width: 0 !important;
            }
        }

        .dropdown-menu {
            position: absolute !important;
        }

        .dropdown .dropdown-menu {
            box-shadow: 0px 0px 18px rgba(0, 0, 0, 0.2) !important;
        }

        .navbar-nav .nav-link {
            color: #738CD9 !important;
        }

        #notificationDropdown p,
        #notificationDropdown i {
            color: #748DDB;
        }

        .btn {
            height: auto !important;
        }

        .login-btn a {
            color: #748DDB;
            text-decoration: none !important;
        }
    </style>
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
</head>
<body>
<c:set var="user" value="${sessionScope.LOGIN_USER}"/>
<nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
    <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
        <a class="navbar-brand brand-logo mr-5" href="IndexServlet"><img src="images/LIBRARY LOGO.svg" class="mr-2"
                                                                         alt="logo" style="margin-left: 36px;"/></a>
        <a class="navbar-brand brand-logo-mini" href="IndexServlet"><img src="images/LIBRARY LOGO MINI.svg"
                                                                         alt="logo"/></a>
    </div>
    <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end"
         style="justify-content: inherit !important">
        <ul class="navbar-nav mr-lg-2">
            <!--Start: Search Form-->
            <form action="DispatchServlet" class="my-0">
                <div class="search">
                    <div class="dropdown">
                        <button type="button" class="btn btn-filter dropdown-toggle" id="dropdownMenuIconButton3"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="ti-filter"></i>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuIconButton3">
                            <h6 class="dropdown-header"><b>SEARCH FOR</b></h6>
                            <label class="dropdown-item">
                                <input type="radio" name="itemFilterOptions" id="itemFilterOption1" value="Books"
                                       checked> Books
                            </label>
                            <label class="dropdown-item">
                                <input type="radio" name="itemFilterOptions" id="itemFilterOption2" value="Authors">
                                Authors
                            </label>
                        </div>
                    </div>
                    <input type="text" placeholder="Search..." class="searchInput" name="txtBookCatalogSearchValue">
                    <button class="btn btn-primary" type="submit" name="btAction" value="SearchBookCatalog">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </form>
            <!--End: Search Form-->
        </ul>
        <ul class="navbar-nav navbar-nav-right">
            <li class="nav-item dropdown">
                <a class="nav-link count-indicator dropdown-toggle d-flex" href="ShowCartServlet">
                    <c:set var="memberTotalActiveBorrows" value="${0}"/>
                    <c:if test="${not empty sessionScope.MEMBER_TOTAL_ACTIVE_BORROWS}">
                        <c:set var="memberTotalActiveBorrows" value="${sessionScope.MEMBER_TOTAL_ACTIVE_BORROWS}"/>
                    </c:if>
                    <c:if test="${user.roleID eq '4'}">
                        <p class="mb-0 px-2">View Cart
                            <c:if test="${not empty sessionScope.MEMBER_CART}">
                                <c:out value=" (${sessionScope.MEMBER_CART.cartQuantity}/${10-memberTotalActiveBorrows})"/>
                                <c:set var="numberOfReserved" value="${sessionScope.MEMBER_CART.cartReserved}"/>
                                <c:if test="${numberOfReserved > 0}">
                                    <c:out value="+${numberOfReserved}"/>
                                </c:if>
                            </c:if>
                        </p>
                        <i class="ti-shopping-cart"></i>
                    </c:if>
                </a>
            </li>
            <c:if test="${not empty user}">
                <li class="nav-item nav-profile dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" id="profileDropdown">
                        <img src="${user.profilePicturePath}" alt="profile picture"
                             onerror="this.onerror=null; this.src='images/imagenotfound.jpg';"
                             style="border-radius: 50%; cursor: pointer;"/>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
                        <c:if test="${user.roleID ne '4'}">
                            <a class="dropdown-item" href="dashboard.jsp">
                                <i class="icon-grid text-primary"></i> Dashboard
                            </a>
                        </c:if>
                        <a class="dropdown-item" href="ShowProfileServlet">
                            <i class="ti-settings text-primary"></i> Profile
                        </a>
                        <form action="DispatchServlet" class="my-0">
                            <button class="dropdown-item" name="btAction" value="Logout">
                                <i class="ti-power-off text-primary"></i> Logout
                            </button>
                        </form>
                    </div>
                </li>
            </c:if>
            <c:if test="${empty user}">
                <div class="d-flex login-btn align-items-center">
                    <a href="login.jsp">Login</a>
                </div>
            </c:if>
        </ul>
    </div>
</nav>

<!-- plugins:js -->
<script src="vendors/js/vendor.bundle.base.js"></script>
<!-- endinject -->

</body>
</html>
