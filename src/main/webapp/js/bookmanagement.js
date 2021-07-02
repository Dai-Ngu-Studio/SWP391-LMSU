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

function checkISBN() {
    let inputTen = document.getElementById("txtISBNTen");
    let inputThirteen = document.getElementById("txtISBNThirteen");
    if (inputTen.value.length == 10 && inputThirteen.value.length == 13) {
        var xhttp;
        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                if (this.responseText.localeCompare('true') != 0) {
                    let htmlForNewBook = `<div class="form-group row" id="rowCover">
                                        <label class="col-sm-3 col-form-label">Book
                                            Cover
                                        </label>
                                        <div class="col-sm-9">
                                            <img class="rounded float-right"
                                                    style="height: 400px; width: auto;"
                                                    src="images/NotAvailable.jpg"
                                                    id="coverPicture"
                                                    alt="Book cover"
                                            >
                                        </div>
                                    </div>

                                    <div class="form-group row" id="rowInputCover">
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
                                                        for="customFileAdd">
                                                        Choose Image
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row" id="rowTitle">
                                        <label class="col-sm-3 col-form-label">
                                            Title
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text"
                                                    class="form-control"
                                                    value=""
                                                    name="txtTitle"
                                            >
                                        </div>
                                    </div>
                                    <div class="form-group row" id="rowAuthor">
                                        <label class="col-sm-3 col-form-label">
                                            Author Name
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text" id="authorAutoComplete"
                                                    class="form-control"
                                            >
                                            <input type="hidden" id="authorIDAutocomplete"
                                                    name="txtAuthorID"
                                                    value=""
                                            >
                                        </div>
                                        <script>
                                            $("#authorAutoComplete").autocomplete({
                                            serviceUrl: 'AutoSuggestAuthorServlet', //tell the script where to send requests
                                            width: "auto", //set width
                                            //add value to input field
                                            onSelect: function (suggestion) {
                                                let chipTune='<div class="chip">'+suggestion.authorName+'<i class="close fal fa-times"></i><input type="hidden" value="'+suggestion.authorID+'"></div>';
                                                // $('#authorAutoComplete').val(suggestion.authorName);
                                                $('input[id=authorIDAutocomplete]').parent().append(chipTune);
                                            },
                                            showNoSuggestionNotice: true,
                                            noSuggestionNotice: 'Sorry, no matching results',
                                            });
                                        </script>
                                    </div>
                                    <div class="form-group row" id="rowSubject">
                                        <label class="col-sm-3 col-form-label">
                                            Subject ID
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text"
                                                    class="form-control"
                                                    value=""
                                                    name="txtSubjectID"
                                            >
                                        </div>
                                    </div>
                                    <div class="form-group row" id="rowPublisher">
                                        <label class="col-sm-3 col-form-label">
                                            Publisher
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text"
                                                    class="form-control"
                                                    value=""
                                                    name="txtPublisher"
                                            >
                                        </div>
                                    </div>
                                    <div class="form-group row" id="rowSupplier">
                                        <label class="col-sm-3 col-form-label">
                                            Supplier
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text"
                                                    class="form-control"
                                                    value=""
                                                    name="txtSupplier"
                                            >
                                        </div>
                                    </div>
                                    <div class="form-group row" id="rowPublishDate">
                                        <label class="col-sm-3 col-form-label">
                                            Publish Date
                                        </label>
                                        <div class="col-sm-9">
                                            <input class="form-control"
                                                    type="date"
                                                    value="2021-06-03"
                                                    name="txtPublishDate"
                                            >
                                        </div>

                                    </div>

                                    <div class="form-group row" id="rowPrice">
                                        <label class="col-sm-3 col-form-label">
                                            Price
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="number"
                                                    class="form-control"
                                                    value=""
                                                    name="txtPrice"
                                            >
                                        </div>
                                    </div>
                                    <div class="form-group row" id="rowQuantity">
                                        <label class="col-sm-3 col-form-label">
                                            Quantity
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="number"
                                                    class="form-control"
                                                    value=""
                                                    name="txtQuantity"
                                            >
                                        </div>
                                    </div>

                                    <div class="form-group row" id="rowDescription">
                                        <label class="col-sm-3 col-form-label">Description
                                        </label>
                                        <div class="col-sm-9">
                                            <textarea class="form-control"
                                                        rows="5"
                                                        name="txtDescription"> </textarea>
                                        </div>
                                    </div>`;
                    $('#addModalBody').append(htmlForNewBook);
                } else {
                    $('#rowCover').remove();
                    $('#rowInputCover').remove();
                    $('#rowTitle').remove();
                    $('#rowAuthor').remove();
                    $('#rowSubject').remove();
                    $('#rowPublisher').remove();
                    $('#rowPublishDate').remove();
                    // $('#rowQuantity').remove();
                    $('#rowPrice').remove();
                    $('#rowDescription').remove();
                    // $('#rowSupplier').remove();
                    if (!$('#rowSupplier').length) {
                        let rowHTMLSupplier = `<div class="form-group row" id="rowSupplier">
                                                <label class="col-sm-3 col-form-label">
                                                Supplier
                                                </label>
                                                <div class="col-sm-9">
                                                    <input type="text"
                                                        class="form-control"
                                                        value=""
                                                        name="txtSupplier"
                                                    >
                                                </div>
                                            </div>`;
                        $('#addModalBody').append(rowHTMLSupplier);
                    }
                    if (!$('#rowQuantity').length) {
                        let rowHTMLQuantity = `<div class="form-group row" id="rowQuantity">
                                                    <label class="col-sm-3 col-form-label">
                                                    Quantity
                                                    </label>
                                                    <div class="col-sm-9">
                                                        <input type="number"
                                                            class="form-control"
                                                            value=""
                                                            name="txtQuantity"
                                                        >
                                                    </div>
                                                </div>`;
                        $('#addModalBody').append(rowHTMLQuantity);
                    }
                }
            }
        };
        xhttp.open("GET", 'GetBookByISBNServlet?txtISBNTen=' + inputTen.value + '&txtISBNThirteen=' + inputThirteen.value, true);
        xhttp.send();
    } else {
        $('#rowCover').remove();
        $('#rowInputCover').remove();
        $('#rowTitle').remove();
        $('#rowAuthor').remove();
        $('#rowSubject').remove();
        $('#rowPublisher').remove();
        $('#rowPublishDate').remove();
        $('#rowQuantity').remove();
        $('#rowPrice').remove();
        $('#rowDescription').remove();
        $('#rowSupplier').remove();
    }
}

$(document).ready(function () {
    $('#book-datatable').DataTable({bFilter: false});
    let fileSearchAdd = `<div class="row float-right" style="padding-right: 50px;">
                            <!--Start: Add Book Form-->
                            <form action="DispatchServlet" enctype="multipart/form-data"
                                method="POST">
                                <input type="search" class="form-control"
                                    style="float: left; width: 250px; border-radius: 0px"
                                    placeholder="Search"
                                    name="txtSearchValue" value=""
                                    aria-controls="order-listing"
                                    id="searchBox">
                                <button class="btn btn-primary" type="submit"
                                        name="btAction" value="SearchBook"
                                        style="border-radius: 0px"><i class="fa fa-search"></i>
                                </button>

                                <button class="btn btn-primary" type="button"
                                        style="border-radius: 5px; 
                                               display: inline-block;"
                                        data-toggle="modal"
                                        data-target="#AddBookModal"
                                        title="Add a book">
                                    <i class="fa fa-plus"></i>
                                </button>

                                <div class="modal fade" id="AddBookModal"
                                    tabindex="-1"
                                    role="dialog"
                                    aria-labelledby="AddBookModalLongTitle"
                                    aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title"
                                                    id="AddBookModalLongTitle">
                                                    Add book
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
                                                        ISBN 10 Digits
                                                    </label>
                                                    <div class="col-sm-9">
                                                        <input type="text"
                                                            id="txtISBNTen"
                                                            class="form-control"
                                                            value=""
                                                            name="txtISBNTen"
                                                            oninput="checkISBN();"
                                                        >
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label">
                                                        ISBN 13 Digits
                                                    </label>
                                                    <div class="col-sm-9">
                                                        <input type="text"
                                                            id="txtISBNThirteen"
                                                            class="form-control"
                                                            value=""
                                                            name="txtISBNThirteen"
                                                            oninput="checkISBN();"
                                                        >
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit"
                                                        class="btn btn-primary"
                                                        name="btAction"
                                                        value="AddBook"
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
                            <!--End: Add Book Form-->
                            <!-- Start: BUTTON AND ADD FILE-->
                            <form action="DispatchServlet" enctype="multipart/form-data"
                                method="POST">
                                <input type="hidden" name="btAction" value="AddBook">
                                <input type="hidden" name="isAddFile" value="True">
                                <label class="btn btn-primary" style="border-radius: 5px">
                                    <input type="file"
                                        hidden
                                        name="fileAdd"
                                        onchange="form.submit();"
                                    >
                                    <i class="fas fa-file-plus"></i>
                                </label>
                            </form>
                            <!--End: BUTTON AND ADD FILE-->
                        </div>`;
    $('#book-datatable_wrapper').children().eq(0).children().eq(1).append(fileSearchAdd);
});