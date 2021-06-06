<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title></title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="../../../../LMSU-FE/LMSU-FE/images/images/favicon.png"/>
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<!--Actual Body-->
<div class="bg-light">
    <div class="row pt-5">
        <div class="col-lg-2"></div>
        <div class="col-lg-5">
            <h4>Your Cart<small class="text-muted"> (2 books)</small></h4>
        </div>
        <div class="col-lg-3"></div>
        <div class="col-lg-2"></div>
    </div>
    <div class="row pb-5">
        <div class="col-lg-2"></div>
        <div class="col-lg-5">
            <div class="card mt-2">
                <div class="card-body">
                    <div class="card-title">
                        <a href="../../../../LMSU-FE/LMSU-FE/bookDetails.html" class="link text-info">
                            Software Engineering 10th Edition</a>
                        <a href="../../../../LMSU-FE/LMSU-FE/bookDetails.html" class="link"><i
                                class="fa fa-arrow-circle-right text-info"
                                aria-hidden="true"></i></a>
                    </div>
                    <div class="card-text">
                        <div class="row">
                            <div class="col-lg-3">
                                <img src="../images/software-engineering.jpg" class="rounded img-fluid img-thumbnail"
                                     alt="..."/>
                            </div>
                            <div class="col-lg-5">
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget sapien et neque
                                auctor facilisis ut id felis. Donec consequat rutrum elit, at mollis metus maximus
                                vitae...
                            </div>
                            <div class="col-lg-4">
                                <button type="button" class="btn btn-danger btn-block">
                                    <i class="fa fa-minus-circle" aria-hidden="true"></i> Remove
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card mt-2">
                <div class="card-body">
                    <div class="card-title">
                        <a href="../../../../LMSU-FE/LMSU-FE/bookDetails.html" class="link text-info">
                            Adaptive Code via C#</a>
                        <a href="../../../../LMSU-FE/LMSU-FE/bookDetails.html" class="link"><i
                                class="fa fa-arrow-circle-right text-info"
                                aria-hidden="true"></i></a>
                    </div>
                    <div class="card-text">
                        <div class="row">
                            <div class="col-lg-3">
                                <img src="../images/adaptive-code-via-csharp.jpg"
                                     class="rounded img-fluid img-thumbnail" alt="..."/>
                            </div>
                            <div class="col-lg-5">
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget sapien et neque
                                auctor facilisis ut id felis. Donec consequat rutrum elit, at mollis metus maximus
                                vitae...
                            </div>
                            <div class="col-lg-4">
                                <button type="button" class="btn btn-danger btn-block">
                                    <i class="fa fa-minus-circle" aria-hidden="true"></i> Remove
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3">
            <div class="card mt-2">
                <div class="card-body text-center">
                    <div class="card-text">
                        Logged in as <strong>SinhVNSE151413</strong>
                    </div>
                    <a href="../../../../LMSU-FE/LMSU-FE/orderForm.html" type="button"
                       class="btn btn-success link mt-2">
                        Proceed to borrow
                    </a>
                </div>
            </div>
        </div>
        <div class="col-lg-2"></div>
    </div>
</div>
<!--Actual Body-->
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>