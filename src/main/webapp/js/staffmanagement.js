$(document).ready(function () {
    $('#staff-datatable').DataTable({bFilter: false});

    let fileSearchAdd = `
                                                    <form action="DispatchServlet">
                                                        <input type="search" class="form-control"
                                                               id="searchBox" placeholder="Search"
                                                               name="txtSearchValue" value="${param.txtSearchValue}"
                                                               aria-controls="order-listing"
                                                        >
                                                        <button class="btn btn-primary" type="submit"
                                                                name="btAction" value="Search Staff"
                                                                style="border-radius: 5px"><i class="fa fa-search"></i>
                                                        </button>
                                                    </form>
    `;
});