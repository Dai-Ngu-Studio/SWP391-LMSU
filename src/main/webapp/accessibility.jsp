<%--
  Created by IntelliJ IDEA.
  User: NDungx
  Date: 6/3/2021
  Time: 7:30 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Accessibility</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <%--Bootstrap CDN--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <%--jquery CDN--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <%--Main css--%>
    <link rel="stylesheet" href="css/policy.css"/>
    <%--FAVICON--%>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>

<div class="box">
    <div class="policy">
        <div class="top mb-5">
            <h2 class="pageTitle"><b>Accessibility</b></h2>
        </div>
        <p>
            - Should you experience any difficulty in accessing Library, please contact us. If you're an institutional
            administrator who would like a copy of our Voluntary Product Accessibility Template (VPAT), please contact
            your account manager.
        </p>
        <div class="descriptionTitle">
            <p class="mb-0 mt-2"><b>Text Resizing</b></p>
        </div>

        <div class="descriptionTxt">
            - The site text can be resized to help readability for individual users. In order to resize the text please
            follow the instructions below:
        </div>

        <div class="descriptionTitle">
            <p class="mb-0 mt-2"><b>PC / all browsers:</b></p>
        </div>

        <div class="descriptionTxt">
            <p>- Increase text size: Hold down the CTRL key and press +</p>
            <p>- Decrease text size: Hold down the CTRL key and press –</p>
        </div>

        <div class="descriptionTitle">
            <p class="mb-0 mt-2"><b>Mac / all browsers:</b></p>
        </div>

        <div class="descriptionTxt">
            <p>- Increase text size: Hold down the Command key and press +</p>
            <p>- Decrease text size: Hold down the Command key and press –</p>
        </div>

        <div class="descriptionTitle">
            <p class="mb-0 mt-2"><b>Browser Support</b></p>
        </div>

        <div class="descriptionTxt">
            <p>- LMSU has been built using code compliant with W3C standards for HTML and CSS. The site displays
                correctly in the latest stable versions of Google Chrome, Microsoft Edge, and Mozilla
                Firefox. For older browsers, visual design may be compromised but all content will be readable, and
                features will function as intended. The site can also be accessed on tablet, and a selection of pages
                are optimized for those devices.</p>
        </div>
    </div>
</div>
<jsp:include page="footer.html"></jsp:include>
</body>
</html>
