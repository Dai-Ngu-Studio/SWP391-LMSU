<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
    <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
        <a class="navbar-brand brand-logo mr-5" href="dashboard.jsp">
            <img src="images/LMSU LOGO DASHBOARD.svg" class="mr-2" alt="logo" style="margin-left: 40px;"/>
        </a>
        <a class="navbar-brand brand-logo-mini" href="dashboard.jsp">
            <img src="images/LMSU LOGO MINI DASHBOARD.svg" alt="logo"/>
        </a>
    </div>
    <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
        <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
            <span class="icon-menu"></span>
        </button>

        <ul class="navbar-nav navbar-nav-right">
            <li class="nav-item nav-profile dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" id="profileDropdown">
                    <img src="${sessionScope.LOGIN_USER.profilePicturePath}" alt="profile picture"
                         style="border-radius: 50%;"
                         onerror="this.onerror=null; this.src='images/default-user-icon.png';"/>
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