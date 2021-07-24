$(document).ready(function () {
    $('#author-datatable').DataTable({bFilter: false});
    let searchAddBar = `<div class="row float-right" style="padding-right: 50px;"> 
                            <form action="DispatchServlet"
                                    enctype="multipart/form-data"
                                    method="POST">
                                <input type="search" class="form-control"
                                        placeholder="Search"
                                        style="float: left; width: 250px;"
                                        name="txtSearchValue" value=""
                                        aria-controls="order-listing"
                                        id="searchBox">
                                <button class="btn btn-primary" type="submit"
                                        name="btAction" value="SearchSubject"
                                        style="border-radius: 5px"><i class="fa fa-search"></i>
                                </button>
                                <button class="btn btn-primary" type="button"
                                        style="border-radius: 5px"
                                        data-toggle="modal"
                                        data-target="#AddSubjectModal"
                                        title="Add a author">
                                    <i class="fa fa-plus"></i></button>
                                <div class="modal fade" id="AddSubjectModal"
                                        tabindex="-1"
                                        role="dialog"
                                        aria-labelledby="AddSubjectModalLongTitle"
                                        aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title"
                                                    id="AddSubjectModalLongTitle">
                                                    Add subject
                                                </h5>
                                                <button type="button"
                                                        class="close"
                                                        data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                
                                                <div class="form-group row">
                                                    <div class="col-sm-3">
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label">
                                                        Subject Name
                                                    </label>
                                                    <div class="col-sm-9">
                                                        <input type="text"
                                                                class="form-control textField"
                                                                value=""
                                                                name="txtSubjectName"
                                                        >
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label">
                                                        Semester No
                                                    </label>
                                                    <div class="col-sm-9">
                                                        <input type="text"
                                                                class="form-control semesterField"
                                                                value=""
                                                                name="txtSubjectSemester"
                                                        >
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit"
                                                        class="btn btn-primary"
                                                        name="btAction"
                                                        value="AddSubject"
                                                >
                                                    Save
                                                </button>
                                                <button type="button"
                                                        class="btn btn-outline-primary"
                                                        data-dismiss="modal">
                                                    Close
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>`;
    $('#author-datatable_wrapper').children().eq(0).children().eq(1).append(searchAddBar);

    //Validate form
    $.validator.setDefaults({
        onkeyup: false,
    });
    $.validator.addClassRules({
        textField: {
            required: {
                depends: function () {
                    $(this).val($.trim($(this).val()));
                    return true;
                }
            },
            minlength: 3,
            maxlength: 80,
        },
        semesterField: {
            required: {
                depends: function () {
                    $(this).val($.trim($(this).val()));
                    return true;
                }
            },
            number: true,
            range: [1, 9]
        }
    });
    $('form').each(function () {
        $(this).validate();
    });
});