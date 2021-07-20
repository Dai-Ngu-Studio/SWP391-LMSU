<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function readURL(input, idImage) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#' + idImage)
                    .attr('src', e.target.result)
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    $(document).ready(function () {
        $('#staff-datatable').DataTable({bFilter: false});

        let SearchAdd = `
        <div class="d-flex justify-content-end">
        <form action="DispatchServlet" class="d-flex">
            <input type="search" class="form-control"
                   id="searchBox" placeholder="Search"
                   name="txtSearchValue" value="${param.txtSearchValue}"
                   aria-controls="order-listing"
                   style="border-radius: 5px 0 0 5px"
            >
            <button class="btn btn-primary mr-2" type="submit"
                    name="btAction" value="Search Staff"
                    style="border-radius: 0 5px 5px 0">
                <i class="fa fa-search"></i>
            </button>
        </form>

        <form action="DispatchServlet" method="POST">
            <button class="btn btn-primary" type="button"
                    style="border-radius: 5px"
                    data-toggle="modal"
                    data-target="#AddUserModal"
                    title="Add user">
                <i class="fa fa-plus"></i>
            </button>
            <div class="modal fade" id="AddUserModal"
                 tabindex="-1"
                 role="dialog"
                 aria-labelledby="AddBookModalLongTitle"
                 aria-hidden="true">
                <div class="modal-dialog"
                     style="margin-top: 30px" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title"
                                id="AddBookModalLongTitle">
                                Add user
                            </h5>
                            <button type="button"
                                    class="close"
                                    data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" id="addModalBody">
                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">
                                    User ID
                                </label>
                                <div class="col-sm-9">
                                    <input type="text"
                                           class="form-control" required
                                           name="txtUserID" value=""
                                    >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">
                                    User Name
                                </label>
                                <div class="col-sm-9">
                                    <input type="text"
                                           class="form-control" required
                                           name="txtUserName" value=""
                                    >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">
                                    Password
                                </label>
                                <div class="col-sm-9">
                                    <input type="password"
                                           class="form-control" required
                                           name="txtPassword" value=""
                                    >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">
                                    Email
                                </label>
                                <div class="col-sm-9">
                                    <input type="email"
                                           class="form-control" required
                                           name="txtEmail" value=""
                                    >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">
                                    Phone number
                                </label>
                                <div class="col-sm-9">
                                    <input type="number"
                                           minlength="10" maxlength="12"
                                           class="form-control" required
                                           name="txtPhoneNumber" value=""
                                    >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">
                                    Role
                                </label>
                                <div class="col-sm-9">
                                    <select name="txtRoleID" class="pl-3">
                                        <option value="3">
                                            Librarian
                                        </option>
                                        <option value="4">
                                            Manager
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary"
                                    name="btAction" value="Add Staff"
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
    </div>
        `;
        $('#staff-datatable_wrapper').children().eq(0).children().eq(1).append(SearchAdd);
    });
</script>
<style>
    .staff-datatable_wrapper .row {
        width: 100% !important;
    }
</style>