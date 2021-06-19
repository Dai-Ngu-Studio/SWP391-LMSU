<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Contact Us</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/contact.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <%--Bootstrap CDN--%>
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">--%>
    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <%--FAVICON--%>
    <link rel="shortcut icon" href="images/images/favicon.png" />
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>

<div class="box">
    <div class="top">
        <h2><b>Contact Us</b></h2>
        <p>Any question or report a bug? Just write us a message.</p>
    </div>
    <div class="contact">
        <div class="left-contact">
            <img src="images/contact-illustration.svg">
            <h5><b>Contact Infomation</b></h5>
            <p style="margin-bottom: 1rem; text-align: center;">
                Fill up a form to help us improve quality.
            </p>
            <a>
                <i class="fa fa-envelope"></i>
                <p>&nbsp;lmsu@gmail.com</p>
            </a>
            <a style="margin-bottom: 5rem;">
                <i class="fa fa-phone"></i>
                <p>&nbsp;+842873005588</p>
            </a>
            <div class="social-page">
                <i class="fa fa-facebook-square"></i>
                <i class="fa fa-google-plus-square"></i>
                <i class="fa fa-twitter-square"></i>
            </div>
        </div>
        <div class="right-contact">
            <form>
                <div class="form-group">
                    <label for="inputName"><b>Full Name <span class="required-field">*</span></b></label>
                    <input type="text" class="form-control" id="inputName" placeholder="Enter full name">
                </div>
                <div class="form-group">
                    <label for="inputEmail"><b>Email <span class="required-field">*</span></b></label>
                    <input type="email" class="form-control" id="inputEmail" placeholder="Enter email">
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                        else.</small>
                </div>
                <div class="form-group">
                    <label for="inputPhoneNumber"><b>Phone Number <span class="required-field">*</span></b></label>
                    <input type="tel" class="form-control" id="inputPhoneNumber" placeholder="Enter phone number">
                </div>
                <div class="form-group">
                    <div>
                        <label>
                            <b>Type of Feedback <span class="required-field">*</span></b>
                        </label>
                    </div>
                    <div>
                        <table>
                            <tr>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="question" id="radio1" name="typeOfFeedback">
                                        <label class="form-check-label" for="radio1">
                                            Question
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="compliment" id="radio2" name="typeOfFeedback">
                                        <label class="form-check-label" for="radio2">
                                            Compliment
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="bug" id="radio3" name="typeOfFeedback">
                                        <label class="form-check-label" for="radio3">
                                            Bug
                                        </label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="content" id="radio4" name="typeOfFeedback">
                                        <label class="form-check-label" for="radio4">
                                            Content
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="suggestion" id="radio5" name="typeOfFeedback">
                                        <label class="form-check-label" for="radio5">
                                            Suggestion
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" value="other" id="radio6" name="typeOfFeedback">
                                        <label class="form-check-label" for="radio6">
                                            Other
                                        </label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="form-group">
                    <script>
                        $('document').ready(function() {
                            var $file = $('#file-input'),
                                $label = $file.next('label'),
                                $labelText = $label.find('span'),
                                $labelRemove = $('i.remove'),
                                labelDefault = $labelText.text();

                            // on file change
                            $file.on('change', function(event) {
                                var fileName = $file.val().split('\\').pop();
                                if (fileName) {
                                    console.log($file)
                                    $labelText.text(fileName);
                                    $labelRemove.show();
                                } else {
                                    $labelText.text(labelDefault);
                                    $labelRemove.hide();
                                }
                            });

                            // Remove file
                            $labelRemove.on('click', function(event) {
                                $file.val("");
                                $labelText.text(labelDefault);
                                $labelRemove.hide();
                                console.log($file)
                            });
                        })
                    </script>
                    <div class="wrapper">
                        <input type="file" id="file-input">
                        <label for="file-input" style="font-size: 1.2rem; color: rgb(110, 80, 195);">
                            <i class="fa fa-paperclip">
                                <b style="font-family: Arial, Helvetica, sans-serif;">
                                    &nbsp;Upload your attachment
                                </b>
                            </i>
                            <!-- <p style="width: 100%;"></p> -->
                            <span style="color: #000; font-weight: 500;"></span>
                        </label>
                        <i class="fa fa-times-circle remove"></i>
                    </div>
                </div>
                <script>
                    $(function() {
                        $("#message").keypress(function(e) {
                            var code = (e.keyCode ? e.keyCode : e.which);
                            if (code == 13) {
                                $("#form1").submit();
                            }
                        });
                    });
                </script>
                <div class="form-group">
                    <label for="inputMessage"><b>Message <span class="required-field">*</span></b></label>
                    <textarea type="text" class="form-control" id="inputMessage" placeholder="Enter message"></textarea>
                </div>
                <div style="display: flex; justify-content: flex-end;">
                    <button type="submit" class="btn btn-send-message">Send Message</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>