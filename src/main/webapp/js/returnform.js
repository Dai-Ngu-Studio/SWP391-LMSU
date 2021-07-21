// Validation for Direct Method
$(document).ready(function () {
    $('.directInput').on("change", function () {
        $('.directError').removeClass('text-danger').addClass('text-muted');
        $('.directInput').removeClass('is-invalid').addClass('is-valid');
        $('#errorPickupDate')
            .text("Your pick-up must be within 7 days from now.");
        $('#errorPickupTime')
            .text("Please choose a time slot to return your books.");
        $.ajax({
            method: 'POST',
            url: 'CheckoutDirectValidationServlet',
            data: {
                txtPickupDate: $('#txtPickupDate').val(),
                txtPickupTime: $('#txtPickupTime').val()
            },
            datatype: 'json',
            success: function (responseJson) {
                $.each(responseJson, function (tagError, inputMessage) {
                    $('#' + tagError)
                        .removeClass('text-muted')
                        .addClass('text-danger');
                    console.log("inputMsg", inputMessage);
                    $.each(inputMessage, function (tagInput, errorMsg) {
                        $('#' + inputMessage['key'])
                            .removeClass('is-valid')
                            .addClass('is-invalid');
                        $('#' + tagError)
                            .text(errorMsg);
                    });
                });

                if ($.isEmptyObject(responseJson)) {
                    $('#btnReturnOrder')
                        .removeAttr('disabled')
                        .removeClass('btn-secondary')
                        .addClass('btn-primary');
                } else {
                    $('#btnReturnOrder')
                        .attr('disabled', '')
                        .removeClass('btn-primary')
                        .addClass('btn-secondary');
                }
            }
        });
    });
});