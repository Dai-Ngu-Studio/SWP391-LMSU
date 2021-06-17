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
                                                    style="height: 400px;
                                                    width: auto;"
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
                                                        for="customFileAdd">Choose
                                                    Image </label>
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
                                            Author ID
                                        </label>
                                        <div class="col-sm-9">
                                            <input type="text"
                                                    class="form-control"
                                                    value=""
                                                    name="txtAuthorID"
                                            >
                                        </div>
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
                                                Publisher
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
                    if (!$('#rowQuantity').length){
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
        xhttp.open("GET", 'GetBookByISBNServlet?txtISBNTen='+inputTen.value+'&txtISBNThirteen='+inputThirteen.value, true);
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
