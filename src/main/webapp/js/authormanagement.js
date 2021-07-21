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
$(document).ready(function (){
    $('#author-datatable').DataTable({bFilter: false});
    let searchAddBar = `<div class="row float-right" style="padding-right: 50px;"> 
                            <form action="DispatchServlet"
                                    enctype="multipart/form-data"
                                    method="POST">
                                <input type="search" class="form-control"
                                        placeholder="Search"
                                        style="float: left; width: 250px; border-radius: 5px 0 0 5px;"
                                        name="txtSearchValue" value=""
                                        aria-controls="order-listing"
                                        id="searchBox">
                                <button class="btn btn-primary" type="submit"
                                        name="btAction" value="SearchAuthor"
                                        style="border-radius: 0 5px 5px 0;"><i class="fa fa-search"></i>
                                </button>
                                <button class="btn btn-primary" type="button"
                                        style="border-radius: 5px"
                                        data-toggle="modal"
                                        data-target="#AddAuthorModal"
                                        title="Add a author">
                                    <i class="fa fa-plus"></i></button>
                                <div class="modal fade" id="AddAuthorModal"
                                        tabindex="-1"
                                        role="dialog"
                                        aria-labelledby="AddAuthorModalLongTitle"
                                        aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title"
                                                    id="AddAuthorModalLongTitle">
                                                    Add author
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
                                                    <label class="col-sm-3 col-form-label">Author
                                                        Cover
                                                    </label>
                                                    <div class="col-sm-9">
                                                        <img class="rounded float-right"
                                                                style="height: 280px;
                                                                            width: auto;"
                                                                src="images/imagenotfound.jpg"
                                                                id="coverPicture"
                                                                alt="Author cover"
                                                        >
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <div class="col-sm-3">
                                                    </div>
                                                    <div class="col-sm-9">
                                                        <div class="custom-file">
                                                            <input type="file"
                                                                    class="custom-file-input"
                                                                    id="customFileAdd"
                                                                    name="coverPicture"
                                                                    onchange="readURL(this, 'coverPicture');"
                                                            >
                                                            <label class="custom-file-label"
                                                                    for="customFileAdd">Choose
                                                                Image </label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <div class="col-sm-3">
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label">
                                                        Name
                                                    </label>
                                                    <div class="col-sm-9">
                                                        <input type="text"
                                                                class="form-control"
                                                                value=""
                                                                name="txtAuthorName"
                                                        >
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label">
                                                        Bio
                                                    </label>
                                                    <div class="col-sm-9">
                                                        <input type="text"
                                                                class="form-control"
                                                                value=""
                                                                name="txtBio"
                                                        >
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit"
                                                        class="btn btn-primary"
                                                        name="btAction"
                                                        value="AddAuthor"
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
});