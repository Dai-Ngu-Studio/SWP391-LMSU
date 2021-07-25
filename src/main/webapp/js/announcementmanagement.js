$("#ViewDraft").on("click", function () {
    $("#semesterDraft").html($("#semester").val());
    $("#yearDraft").html($("#year").val());
    $("#defaultDayFromDraft").html($("#defaultDayFrom").val());
    $("#defaultDateFromDraft").html($("#defaultDateFrom").val());
    $("#defaultDayToDraft").html($("#defaultDayTo").val());
    $("#defaultDateToDraft").html($("#defaultDateTo").val());
    $("#subjectDraft").html($("#subject").val());
    $("#dayDraft").html($("#day").val());
    $("#dateFromDraft").html($("#dateFrom").val());
    $("#dateToDraft").html($("#dateTo").val());
    $("#returnDateDraft").html($("#returnDate").val());
});
$(document).ready(function () {
    //validation form
    $.validator.setDefaults({
        onkeyup: false
    });
    $.validator.addClassRules({
        textField: {
            required: {
                depends: function () {
                    $(this).val($.trim($(this).val()));
                    return true;
                }
            },
            minlength: 1,
            maxlength: 20,
        },
        yearField: {
            required: true,
            number: true,
            min: 2021
        }
    });
    $('form').each(function () {
        $(this).validate();
    });
});