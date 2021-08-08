<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Policy</title>
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
        <div class="top">
            <h2 class="mb-5"><b>Policy</b></h2>
        </div>
        <div class="introduction">
            <p>
                - PRESENT YOUR VALID CARD TO THE LIBRARIAN TO BORROW OR RETURN BOOKS AT THE LIBRARY
            </p>
        </div>
        <div class="descriptionTitle">
            <p>
                <b>a - BOOK RENTAL</b>
            </p>
        </div>
        <div class="descriptionTxt">
            <ul>
                <li>
                    <p>
                        Members can borrow up to 10 items.
                    </p>
                </li>
                <li>
                    <p>
                        Book rental orders must be approved before you can receive your books.
                        Approval or rejection of your order will be shown on your profile and notified through email.
                    </p>
                </li>
                <li>
                    <p>
                        Items can be renewed at most 4 times and up to 1 week if you have acceptable reasons.
                        To renew books through the web, navigate to your Book Borrowing tab inside your profile and
                        request a renewal.
                    </p>
                </li>
                <li>
                    <p>
                        Renewal requests must be approved for the deadline of your item to be extended.
                        Approval of your request will be shown on your profile.
                    </p>
                </li>
                <li>
                    <p>
                        The library will send you an email if any book you have in your wishlist is now available in
                        stock.
                    </p>
                </li>
            </ul>
        </div>

        <div class="descriptionTitle">
            <p>
                <b>b - BOOK COLLECTION</b>
            </p>
        </div>

        <div class="descriptionTxt">
            <ul>
                <li>
                    <p>
                        Please keep the book in a well condition while you are borrowing it.
                        You are responsible for any visible damage to the book while it is being loaned to you.
                    </p>
                </li>
                <li>
                    <p>
                        Books can only be renewed if they are not overdue.
                        Please keep track of your book rental status on your profile.
                        The library will send you an email when it is about 10 days before the deadline of your item.
                    </p>
                </li>
                <li>
                    <p>
                        You can keep track of your penalties on your profile.
                    </p>
                </li>
            </ul>
        </div>

        <div class="descriptionTitle">
            <p>
                <b>c - FINES & PENALTY</b>
            </p>
        </div>

        <div class="descriptionTxt">
            <ul>
                <li>
                    <p>
                        Overdue books will be penalized at a rate of 5,000 VND/item/day.
                    </p>
                </li>
                <li>
                    <p>
                        Books that have been overdue for more than 13 days will be marked as lost.
                        If such is the case, you are instead to pay the full price of the book for the penalty.
                    </p>
                </li>
                <li>
                    <p>
                        Your lost book would still be counted as a currently borrowed item unless you paid for the
                        penalty.
                        Meaning, your total number of books which you can borrow will be limited.
                        Please settle your penalty as soon as your earliest possible date for your convenience.
                    </p>
                </li>
                <li>
                    <p>
                        If the penalty is higher than the book price, the maximum penalty will be the cost of the book.
                        Meaning, you would pay an amount as much as the price of the book.
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="footer.html"></jsp:include>
</body>
</html>
