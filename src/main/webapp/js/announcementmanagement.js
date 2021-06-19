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
});