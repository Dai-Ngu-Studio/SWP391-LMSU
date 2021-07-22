// DataTable rendering
$(document).ready(function () {
    $('#rental-datatable').DataTable({
        searchPanes: {
            viewTotal: true,
            columns: [1, 4]
        },
        dom: 'Plfrtip',
        columnDefs: [
            {
                className: 'lbOrderStatDt',
                searchPanes: {
                    show: true,
                    options: [
                        {
                            label: 'Pending',
                            value: function (rowData, rowIdx) {
                                return $(rowData[4]).attr('activeStatus') === '0';
                            }
                        },
                        {
                            label: 'Approved',
                            value: function (rowData, rowIdx) {
                                return $(rowData[4]).attr('activeStatus') === '1';
                            }
                        },
                        {
                            label: 'Received',
                            value: function (rowData, rowIdx) {
                                return $(rowData[4]).attr('activeStatus') === '2';
                            }
                        },
                        {
                            label: 'Closed',
                            value: function (rowData, rowIdx) {
                                return $(rowData[4]).attr('activeStatus') === '3';
                            }
                        },
                        {
                            label: 'Overdue',
                            value: function (rowData, rowIdx) {
                                return $(rowData[4]).attr('activeStatus') === '4';
                            }
                        },
                        {
                            label: 'Rejected',
                            value: function (rowData, rowIdx) {
                                return $(rowData[4]).attr('activeStatus') === '5';
                            }
                        },
                        {
                            label: 'Reserve',
                            value: function (rowData, rowIdx) {
                                return $(rowData[4]).attr('activeStatus') === '6';
                            }
                        },
                        {
                            label: 'Cancelled',
                            value: function (rowData, rowIdx) {
                                return $(rowData[4]).attr('activeStatus') === '-1';
                            }
                        }
                    ]
                },
                targets: [4]
            },
            {
                searchPanes: {
                    show: true
                },
                targets: [1]
            }
        ],
        order: [[1, 'asc']]
    });

    $('.dtsp-panesContainer').addClass('col-8 mx-auto');
    $('.dataTables_scrollBody').css('height', '100px');
    $('#rental-datatable_wrapper').css('max-width', '81vw');
});

// For loading item status select options
function loadItemStatOptions(itemStatusDirection, baseStats) {
    $('.lbItemStat').each(function () {
        let $lbStat = $(this);
        let orderID = $lbStat.attr('orderid');
        let orderItemID = $lbStat.attr('orderitemid');
        let lendStatus = $lbStat.attr('lendstatus');
        let $slItemStat = $(`#slItemStat${orderItemID}`);
        $slItemStat.find('option').remove();
        let $optBase = $('<option>')
            .val(lendStatus)
            .attr('orderitemid', orderItemID)
            .attr('orderid', orderID)
            .appendTo($slItemStat);
        for (const baseStat of baseStats) {
            if (baseStat === lendStatus) {
                $('<option>')
                    .val(itemStatusDirection.get(lendStatus))
                    .attr('orderitemid', orderItemID)
                    .attr('orderid', orderID)
                    .appendTo($slItemStat);
                break;
            }
        }
    });
}

// For loading order status after updating item status
function checkDirectOrderStatus(orderID) {
    $.ajax({
        method: 'GET',
        url: 'CheckOrderStatusServlet',
        data: {
            txtOrderID: orderID
        },
        datatype: 'json',
        success: function (orderInformation) {
            if (orderInformation !== null) {
                // Load order status
                $('.lbOrderStat')
                    .filter(`[orderid='${orderID}']`)
                    .find('p, label')
                    .attr('activeStatus', orderInformation.key['activeStatus']);
                // Load librarian ID
                if ($('.frmStaffID').filter(`[orderid='${orderID}']`)
                    .find('input').val() === 'N/A') {
                    $('.frmStaffID')
                        .filter(`[orderid='${orderID}']`)
                        .find('input')
                        .val(orderInformation.value['id']);
                    $('.frmStaffName')
                        .filter(`[orderid='${orderID}']`)
                        .find('input')
                        .val(orderInformation.value['name']);
                }
                // Load edit buttons
                loadButtons();
            } else {
                alert(`Error processing. Please refresh the page.`);
            }
        }
    });
}

// For loading/ hiding editing buttons
function loadButtons() {
    const ORDER_CANCELLED = '-1';
    const ORDER_PENDING = '0';
    const ORDER_APPROVED = '1';
    const ORDER_RECEIVED = '2';
    const ORDER_RETURNED = '3';
    const ORDER_OVERDUE = '4';
    const ORDER_REJECTED = '5';
    const ORDER_RESERVE_ONLY = '6';
    const ORDER_RETURN_SCHEDULED = '7';
    const ORDER_RETURN_RETURNED = '8';
    $('.lbOrderStat').find('p').each(function () {
        let $pOrderStat = $(this);
        let orderID = $pOrderStat.attr('orderid');
        let activeStatus = $pOrderStat.attr('activeStatus');
        if ((activeStatus === ORDER_PENDING)
            || (activeStatus === ORDER_CANCELLED)
            || (activeStatus === ORDER_REJECTED)
            || (activeStatus === ORDER_RESERVE_ONLY)
            || (activeStatus === ORDER_RETURNED)) {
            $('.groupBtnEdit').filter(`[orderid='${orderID}']`).hide();
        } else {
            $('.groupBtnEdit').filter(`[orderid='${orderID}']`).show();
        }
    });
}

// Order Approval Interface
$(document).ready(function () {
    const ORDER_CANCELLED = '-1';
    const ORDER_PENDING = '0';
    const ORDER_APPROVED = '1';
    const ORDER_RECEIVED = '2';
    const ORDER_RETURNED = '3';
    const ORDER_OVERDUE = '4';
    const ORDER_REJECTED = '5';
    const ORDER_RESERVE_ONLY = '6';
    const ORDER_RETURN_SCHEDULED = '7';
    const ORDER_RETURN_RETURNED = '8';

    // Creating listener for approve/ reject button (the ones inside confirm modal)
    $('.btnModalAppr').each(function () {
        let $btnModalAppr = $(this);
        $btnModalAppr.each(function () {
            let $button = $(this);
            let $role = $(this).attr('role');
            if ($role === 'approveOrder') {
                $button.on('click', approveOrder);
            } else if ($role === 'rejectOrder') {
                $button.on('click', rejectOrder);
            }
        });
    });

    /**
     * Get Order ID from button
     * Send Order ID to Servlet
     * Servlet returns result
     * Load order status
     * Load item status
     * Remove approval buttons
     * Notify if order had been cancelled or rejected by someone
     */
    function approveOrder() {
        let $buttonApprove = $(this);
        let orderID = $buttonApprove.attr('orderid');
        $.ajax({
            method: 'POST',
            url: 'ApproveOrderServlet',
            data: {
                txtOrderID: orderID
            },
            datatype: 'json',
            success: function (orderInformation) {
                if (orderInformation !== null) {
                    // Load order status
                    $('.lbOrderStat')
                        .filter(`[orderid='${orderID}']`)
                        .find('p, label')
                        .attr('activeStatus', orderInformation.key['activeStatus']);
                    // Load item status
                    $(orderInformation.value).each(function () {
                        let orderItemID = this['id'];
                        $(`#lbItemStat${orderItemID}`).attr('lendStatus', this['lendStatus']);
                    });
                    // Remove approval buttons
                    let orderStat = orderInformation.key['activeStatus'].toString();
                    if ((orderStat !== ORDER_PENDING)
                        && (orderStat !== ORDER_CANCELLED)) {
                        $('.contModalApprove').filter(`[orderid='${orderID}']`).remove();
                        $('.contModalReject').filter(`[orderid='${orderID}']`).remove();
                        let $frmOrderStat = $('.frmOrderStat').filter(`[orderid='${orderID}']`);
                        let $contStat = $('<div>').addClass('col-lg-5 col-12').appendTo($frmOrderStat);
                        let $statOrder = $('<div>')
                            .addClass('btn btn-block btn-outline-success btn-sm bg-white')
                            .attr('disabled', 'disabled')
                            .appendTo($contStat);
                        let $btnAppr = $('<h3>').appendTo($statOrder);
                        if (orderStat === ORDER_APPROVED) {
                            $btnAppr.addClass('fa fa-check-circle text-success')
                        } else if (orderStat === ORDER_REJECTED) {
                            $btnAppr.addClass('fa fa-times-circle text-danger')
                            alert(`Order was rejected by another librarian.`);
                        }
                    } else if (orderStat === ORDER_PENDING) {
                        alert(`Order was not processed successfully. Please try again.`);
                    } else if (orderStat === ORDER_CANCELLED) {
                        alert(`Order had already been cancelled by the borrower.`);
                    }
                    // Toggle modal off
                    $(`#btnDismissAppr${orderID}`).click();
                    // Load edit buttons
                    if (orderStat === ORDER_APPROVED) {
                        loadButtons();
                    }
                } else {
                    alert(`Error processing. Please refresh the page.`);
                }
            }
        });
    }

    // Reject should add Librarian ID
    function rejectOrder() {
        let $buttonReject = $(this);
        let orderID = $buttonReject.attr('orderid');
        $.ajax({
            method: 'POST',
            url: 'RejectOrderServlet',
            data: {
                txtOrderID: orderID
            },
            datatype: 'json',
            success: function (orderInformation) {
                if (orderInformation !== null) {
                    // Load order status
                    $('.lbOrderStat')
                        .filter(`[orderid='${orderID}']`)
                        .find('p, label')
                        .attr('activeStatus', orderInformation.key['activeStatus']);
                    // Load item status
                    $(orderInformation.value).each(function () {
                        let orderItemID = this['id'];
                        $(`#lbItemStat${orderItemID}`).attr('lendStatus', this['lendStatus']);
                    });
                    // Remove approval buttons
                    let orderStat = orderInformation.key['activeStatus'].toString();
                    if ((orderStat !== ORDER_PENDING)
                        && (orderStat !== ORDER_CANCELLED)) {
                        $('.contModalApprove').filter(`[orderid='${orderID}']`).remove();
                        $('.contModalReject').filter(`[orderid='${orderID}']`).remove();
                        let $frmOrderStat = $('.frmOrderStat').filter(`[orderid='${orderID}']`);
                        let $contStat = $('<div>').addClass('col-lg-5 col-12').appendTo($frmOrderStat);
                        let $statOrder = $('<div>')
                            .addClass('btn btn-block btn-outline-danger btn-sm bg-white')
                            .attr('disabled', 'disabled')
                            .appendTo($contStat);
                        let $btnAppr = $('<h3>').appendTo($statOrder);
                        if (orderStat === ORDER_APPROVED) {
                            $btnAppr.addClass('fa fa-check-circle text-success')
                            alert(`Order was approved by another librarian.`);
                        } else if (orderStat === ORDER_REJECTED) {
                            $btnAppr.addClass('fa fa-times-circle text-danger')
                        }
                    } else if (orderStat === ORDER_PENDING) {
                        alert(`Order was not processed successfully. Please try again.`);
                    } else if (orderStat === ORDER_CANCELLED) {
                        alert(`Order had already been cancelled by the borrower.`);
                    }
                    // Toggle modal off
                    $(`#btnDismissReject${orderID}`).click();
                    // Load edit buttons
                    if (orderStat === ORDER_APPROVED) {
                        loadButtons();
                    }
                } else {
                    alert(`Error processing. Please refresh the page.`);
                }
            }
        });
    }

});

// Order Item Edit Interface
$(document).ready(function () {
    const ITEM_CANCELLED = '-1';
    const ITEM_PENDING = '0';
    const ITEM_APPROVED = '1';
    const ITEM_RECEIVED = '2';
    const ITEM_RETURN_SCHEDULED = '3';
    const ITEM_RETURNED = '4';
    const ITEM_OVERDUE = '5';
    const ITEM_OVERDUE_RETURN_SCHEDULED = '6';
    const ITEM_OVERDUE_RETURNED = '7';
    const ITEM_REJECTED = '8';
    const ITEM_LOST = '9';
    const ITEM_RESERVED = '10';
    const ITEM_RESERVED_INACTIVE = '11';

    // Item Status Direction
    const ITEM_STATUS_DIRECTION = new Map();
    ITEM_STATUS_DIRECTION.set(ITEM_APPROVED, ITEM_RECEIVED);
    ITEM_STATUS_DIRECTION.set(ITEM_RECEIVED, ITEM_RETURNED);
    ITEM_STATUS_DIRECTION.set(ITEM_OVERDUE, ITEM_OVERDUE_RETURNED);
    ITEM_STATUS_DIRECTION.set(ITEM_LOST, ITEM_OVERDUE_RETURNED);
    ITEM_STATUS_DIRECTION.set(ITEM_RETURN_SCHEDULED, ITEM_RETURNED);
    ITEM_STATUS_DIRECTION.set(ITEM_OVERDUE_RETURN_SCHEDULED, ITEM_OVERDUE_RETURNED);
    let baseStats = [...ITEM_STATUS_DIRECTION.keys()];

    // Load buttons for editing
    loadButtons();

    // Loading options for editing
    loadItemStatOptions(ITEM_STATUS_DIRECTION, baseStats);

    // Creating listener for coloring item status select
    $('.slItemStat').each(function () {
        let $slItemStat = $(this);
        $slItemStat.on('click', function () {
            $slItemStat.removeClass();
            $slItemStat.addClass('custom-select slItemStat');
            $slItemStat.addClass($slItemStat.find(':selected').attr('class'));
        });
    });

    // Creating listener for loading edit interface
    $('.groupBtnEdit').each(function () {
        $(this).find('button').on('click', loadEditInterface);
    });

    // Creating listener for edit confirm, edit cancel
    $('.storageBtn').each(function () {
        var $btns = $(this).find('button');
        $btns.each(function () {
            let $button = $(this);
            let $role = $(this).attr('role');
            if ($role === 'confirmEdit') {
                $button.on('click', submitEdit);
            } else if ($role === 'cancelEdit') {
                $button.on('click', cancelEditInterface);
            }
        });
    });

    /**
     * Submitting edit|
     * Get orderID from button
     * Get item statuses of order ID
     * Create list of item statuses
     * Send list to servlet
     * Servlet returns result
     * Load item status
     * Hide edit interface
     */
    function submitEdit() {
        let $buttonSubmit = $(this);
        let orderID = $buttonSubmit.attr('orderid');
        let $optsSelected = $('.slItemStat')
            .filter(`[orderid='${orderID}']`)
            .find('option:selected');
        itemStats = [];
        $optsSelected.each(function () {
            let orderItem = {
                orderItemID: $(this).attr('orderItemID'),
                lendStatus: $(this).attr('value'),
                orderID: $(this).attr('orderID'),
                lendDate: $(`#dateLend${$(this).attr('orderItemID')}`).attr('datevalue'),
                returnDate: $(`#dateReturn${$(this).attr('orderItemID')}`).attr('datevalue')
            }
            itemStats.push(orderItem);
        });
        $.ajax({
            method: 'POST',
            url: 'UpdateOrderServlet',
            data: {
                txtOrderID: orderID,
                jsonItemStats: JSON.stringify(itemStats)
            },
            datatype: 'json',
            success: function (orderItems) {
                if (orderItems !== null) {
                    for (const item of orderItems) {
                        // console.log($(item));
                        let orderItemID = $(item).attr('id');
                        let lendStatus = $(item).attr('lendStatus');
                        let lendDate = $(item).attr('lendDate');
                        let returnDate = $(item).attr('returnDate');
                        $(`#lbItemStat${orderItemID}`).attr('lendStatus', lendStatus);
                        if (lendDate) {
                            $(`#dateLend${orderItemID}`).text(lendDate);
                        }
                        if (returnDate) {
                            $(`#dateReturn${orderItemID}`).text(returnDate);
                        }
                    }
                    let $groupBtn = $('.groupBtnEdit').filter(`[orderid='${orderID}']`);
                    let $storageBtn = $('.storageBtn').filter(`[orderid='${orderID}']`);
                    let $btnsEdit = $groupBtn.find(`[orderid='${orderID}']`);
                    let $btnLoad = $storageBtn.find(`[orderid='${orderID}']`);
                    $btnsEdit.appendTo($storageBtn);
                    $btnLoad.appendTo($groupBtn);

                    let $inputsStat = $('.contSlItemStat').filter(`[orderid='${orderID}']`);
                    let $lbsStat = $('.contItemStat').filter(`[orderid='${orderID}']`);
                    $inputsStat.hide();
                    $lbsStat.show();

                    // Check order status after updating lendStatus
                    checkDirectOrderStatus(orderID);
                    // Check if buttons should be hidden
                    loadButtons();
                } else {
                    alert(`Error processing. Please refresh the page.`);
                }
            }
        });
    }

    /**
     * Cancelling edit|
     * Get the orderID from the clicked button.
     * Get container of edit buttons (groupBtn), get container of load interface button (storageBtn)
     * Get the buttons inside each container
     * Swap the buttons
     * ---
     * Hide edit interface
     * Show details interface
     */
    function cancelEditInterface() {
        let $buttonCancel = $(this);
        let orderID = $buttonCancel.attr('orderid');
        let $groupBtn = $('.groupBtnEdit').filter(`[orderid='${orderID}']`);
        let $storageBtn = $('.storageBtn').filter(`[orderid='${orderID}']`);
        let $btnsEdit = $groupBtn.find(`[orderid='${orderID}']`);
        let $btnLoad = $storageBtn.find(`[orderid='${orderID}']`);
        $btnsEdit.appendTo($storageBtn);
        $btnLoad.appendTo($groupBtn);

        let $inputsStat = $('.contSlItemStat').filter(`[orderid='${orderID}']`);
        let $lbsStat = $('.contItemStat').filter(`[orderid='${orderID}']`);
        $inputsStat.hide();
        $lbsStat.show();
    }

    /**
     * Start editing|
     * Get the orderID from the clicked button.
     * Get container of load interface button (groupBtn), get container of edit buttons (storageBtn)
     * Get the buttons inside each container
     * Swap the buttons
     * ---
     * Hide details interface
     * Show edit interface
     */
    function loadEditInterface() {
        let $button = $(this);
        let orderID = $button.attr('orderid');
        let $groupBtn = $('.groupBtnEdit').filter(`[orderid='${orderID}']`);
        let $storageBtn = $('.storageBtn').filter(`[orderid='${orderID}']`);
        let $btnsEdit = $storageBtn.find(`[orderid='${orderID}']`);
        $btnsEdit.appendTo($groupBtn);
        $button.appendTo($storageBtn);

        let $inputsStat = $('.contSlItemStat').filter(`[orderid='${orderID}']`);
        let $lbsStat = $('.contItemStat').filter(`[orderid='${orderID}']`);
        $lbsStat.hide();
        $inputsStat.show();
    }
});

// Mutation Observer for Order Status
$(document).ready(function () {
    const ORDER_CANCELLED = '-1';
    const ORDER_PENDING = '0';
    const ORDER_APPROVED = '1';
    const ORDER_RECEIVED = '2';
    const ORDER_RETURNED = '3';
    const ORDER_OVERDUE = '4';
    const ORDER_REJECTED = '5';
    const ORDER_RESERVE_ONLY = '6';
    const ORDER_RETURN_SCHEDULED = '7';
    const ORDER_RETURN_RETURNED = '8';

    const orderStatContainers = $('.lbOrderStat');
    const observerConfig = {attributes: true, subtree: true};

    const orderStatColoring = function ($lbOrderStat, activeStatus) {
        let baseClass = 'btn';
        if ($lbOrderStat.hasClass('form-control')) {
            baseClass = 'form-control';
        } else if ($lbOrderStat.hasClass('badge')) {
            baseClass = 'badge';
        }
        $lbOrderStat.removeClass();
        $lbOrderStat.addClass(baseClass);
        switch (activeStatus) {
            case ORDER_CANCELLED:
                $lbOrderStat.addClass('bg-secondary text-white');
                $lbOrderStat.text('Cancelled');
                break;
            case ORDER_PENDING:
                $lbOrderStat.addClass('bg-warning text-dark');
                $lbOrderStat.text('Pending');
                break;
            case ORDER_APPROVED:
                $lbOrderStat.addClass('bg-info text-white');
                $lbOrderStat.text('Approved');
                break;
            case ORDER_RECEIVED:
                $lbOrderStat.addClass('bg-primary text-white');
                $lbOrderStat.text('Received');
                break;
            case ORDER_RETURNED:
                $lbOrderStat.addClass('bg-success text-white');
                $lbOrderStat.text('Closed');
                break;
            case ORDER_OVERDUE:
                $lbOrderStat.addClass('bg-danger text-white');
                $lbOrderStat.text('Overdue');
                break;
            case ORDER_REJECTED:
                $lbOrderStat.addClass('bg-dark text-white');
                $lbOrderStat.text('Rejected');
                break;
            case ORDER_RESERVE_ONLY:
                $lbOrderStat.addClass('bg-secondary text-white');
                $lbOrderStat.text('Reserve');
                break;
            case ORDER_RETURN_SCHEDULED:
                $lbOrderStat.addClass('bg-primary text-white');
                $lbOrderStat.text('Scheduled');
                break;
            case ORDER_RETURN_RETURNED:
                $lbOrderStat.addClass('bg-success text-white');
                $lbOrderStat.text('Closed');
                break;
        }
    };

    const renderStatData = function (mutationsList, observer) {
        $(mutationsList).each(function () {
            let $pOrderStat = $(this['target']);
            let activeStatus = $pOrderStat.get(0).attributes['activeStatus']['value'];
            // Temporarily stop observing to ignore rendering changes
            stopObserver();
            orderStatColoring($pOrderStat, activeStatus);
            startObserver();
        });
    };

    const orderStatObs = new MutationObserver(renderStatData);
    const startObserver = function () {
        orderStatContainers.each(function () {
            orderStatObs.observe(this, observerConfig);
        });
    };
    const stopObserver = function () {
        orderStatObs.disconnect();
    };

    // Initial Rendering when page finished loading
    orderStatContainers.each(function () {
        let $lbOrderStat = $(this.firstElementChild);
        let activeStatus = $lbOrderStat.get(0).attributes['activeStatus']['value'];
        orderStatColoring($lbOrderStat, activeStatus);
    });

    // Start observing changes
    startObserver();
});

// Mutation Observer for Order Item Status
$(document).ready(function () {
    const ITEM_CANCELLED = '-1';
    const ITEM_PENDING = '0';
    const ITEM_APPROVED = '1';
    const ITEM_RECEIVED = '2';
    const ITEM_RETURN_SCHEDULED = '3';
    const ITEM_RETURNED = '4';
    const ITEM_OVERDUE = '5';
    const ITEM_OVERDUE_RETURN_SCHEDULED = '6';
    const ITEM_OVERDUE_RETURNED = '7';
    const ITEM_REJECTED = '8';
    const ITEM_LOST = '9';
    const ITEM_RESERVED = '10';
    const ITEM_RESERVED_INACTIVE = '11';

    // Item Status Direction
    const ITEM_STATUS_DIRECTION = new Map();
    ITEM_STATUS_DIRECTION.set(ITEM_APPROVED, ITEM_RECEIVED);
    ITEM_STATUS_DIRECTION.set(ITEM_RECEIVED, ITEM_RETURNED);
    ITEM_STATUS_DIRECTION.set(ITEM_OVERDUE, ITEM_OVERDUE_RETURNED);
    ITEM_STATUS_DIRECTION.set(ITEM_LOST, ITEM_OVERDUE_RETURNED);
    ITEM_STATUS_DIRECTION.set(ITEM_RETURN_SCHEDULED, ITEM_RETURNED);
    ITEM_STATUS_DIRECTION.set(ITEM_OVERDUE_RETURN_SCHEDULED, ITEM_OVERDUE_RETURNED);
    let baseStats = [...ITEM_STATUS_DIRECTION.keys()];

    const lbItemStat = $('.lbItemStat');
    const observerConfig = {attributes: true, subtree: true};

    const orderItemStatColoring = function ($lbItemStat, lendStatus) {
        let baseClass = 'badge lbItemStat';
        $lbItemStat.removeClass();
        $lbItemStat.addClass(baseClass);
        switch (lendStatus) {
            case ITEM_CANCELLED:
                $lbItemStat.addClass('bg-secondary text-white');
                $lbItemStat.text('Cancelled');
                break;
            case ITEM_PENDING:
                $lbItemStat.addClass('bg-warning text-dark');
                $lbItemStat.text('Pending');
                break;
            case ITEM_APPROVED:
                $lbItemStat.addClass('bg-info text-white');
                $lbItemStat.text('Approved');
                break;
            case ITEM_RECEIVED:
                $lbItemStat.addClass('bg-primary text-white');
                $lbItemStat.text('Received');
                break;
            case ITEM_RETURN_SCHEDULED:
                $lbItemStat.addClass('bg-primary text-white');
                $lbItemStat.text('Scheduled');
                break;
            case ITEM_RETURNED:
                $lbItemStat.addClass('bg-success text-white');
                $lbItemStat.text('Returned');
                break;
            case ITEM_OVERDUE:
                $lbItemStat.addClass('bg-danger text-white');
                $lbItemStat.text('Overdue');
                break;
            case ITEM_OVERDUE_RETURN_SCHEDULED:
                $lbItemStat.addClass('bg-primary text-white');
                $lbItemStat.text('Scheduled');
                break;
            case ITEM_OVERDUE_RETURNED:
                $lbItemStat.addClass('bg-success text-white');
                $lbItemStat.text('Returned');
                break;
            case ITEM_REJECTED:
                $lbItemStat.addClass('bg-dark text-white');
                $lbItemStat.text('Rejected');
                break;
            case ITEM_LOST:
                $lbItemStat.addClass('bg-danger text-white');
                $lbItemStat.text('Missing');
                break;
            case ITEM_RESERVED:
                $lbItemStat.addClass('bg-secondary text-white');
                $lbItemStat.text('Reserved');
                break;
            case ITEM_RESERVED_INACTIVE:
                $lbItemStat.addClass('bg-secondary text-white');
                $lbItemStat.text('Inactive');
                break;
        }
    };

    const renderItemStatData = function (mutationsList, observer) {
        $(mutationsList).each(function () {
            console.log('Order Item Status Mutation detected: ', this);
            let $lbItemStat = $(this['target']);
            let lendStatus = $lbItemStat.get(0).attributes['lendStatus']['value'];
            // Temporarily stop observing to ignore rendering changes
            stopObserver();
            orderItemStatColoring($lbItemStat, lendStatus);
            startObserver();
        });
        // Load item status options after updating lendStatus
        loadItemStatOptions(ITEM_STATUS_DIRECTION, baseStats);

    };

    const orderItemStatObs = new MutationObserver(renderItemStatData);
    const startObserver = function () {
        lbItemStat.each(function () {
            orderItemStatObs.observe(this, observerConfig);
        });
    };
    const stopObserver = function () {
        orderItemStatObs.disconnect();
    };

    // Initial Rendering when page finished loading
    lbItemStat.each(function () {
        let $lbItemStat = $(this);
        let lendStatus = $lbItemStat.get(0).attributes['lendStatus']['value'];
        orderItemStatColoring($lbItemStat, lendStatus);
    });

    // Start observing changes
    startObserver();
});

// Mutation Observer for Order Item Status Select when Editing
$(document).ready(function () {
    const ITEM_CANCELLED = '-1';
    const ITEM_PENDING = '0';
    const ITEM_APPROVED = '1';
    const ITEM_RECEIVED = '2';
    const ITEM_RETURN_SCHEDULED = '3';
    const ITEM_RETURNED = '4';
    const ITEM_OVERDUE = '5';
    const ITEM_OVERDUE_RETURN_SCHEDULED = '6';
    const ITEM_OVERDUE_RETURNED = '7';
    const ITEM_REJECTED = '8';
    const ITEM_LOST = '9';
    const ITEM_RESERVED = '10';
    const ITEM_RESERVED_INACTIVE = '11';

    const slItemStat = $('.slItemStat');
    const observerConfig = {childList: true};

    const orderItemStatColoring = function ($optItemStat, lendStatus) {
        $optItemStat.removeClass();
        switch (lendStatus) {
            case ITEM_CANCELLED:
                $optItemStat.addClass('bg-secondary text-white');
                $optItemStat.text('Cancelled');
                break;
            case ITEM_PENDING:
                $optItemStat.addClass('bg-warning text-dark');
                $optItemStat.text('Pending');
                break;
            case ITEM_APPROVED:
                $optItemStat.addClass('bg-info text-white');
                $optItemStat.text('Approved');
                break;
            case ITEM_RECEIVED:
                $optItemStat.addClass('bg-primary text-white');
                $optItemStat.text('Received');
                break;
            case ITEM_RETURN_SCHEDULED:
                $optItemStat.addClass('bg-primary text-white');
                $optItemStat.text('Scheduled');
                break;
            case ITEM_RETURNED:
                $optItemStat.addClass('bg-success text-white');
                $optItemStat.text('Returned');
                break;
            case ITEM_OVERDUE:
                $optItemStat.addClass('bg-danger text-white');
                $optItemStat.text('Overdue');
                break;
            case ITEM_OVERDUE_RETURN_SCHEDULED:
                $optItemStat.addClass('bg-primary text-white');
                $optItemStat.text('Scheduled');
                break;
            case ITEM_OVERDUE_RETURNED:
                $optItemStat.addClass('bg-success text-white');
                $optItemStat.text('Returned');
                break;
            case ITEM_REJECTED:
                $optItemStat.addClass('bg-dark text-white');
                $optItemStat.text('Rejected');
                break;
            case ITEM_LOST:
                $optItemStat.addClass('bg-danger text-white');
                $optItemStat.text('Missing');
                break;
            case ITEM_RESERVED:
                $optItemStat.addClass('bg-secondary text-white');
                $optItemStat.text('Reserved');
                break;
            case ITEM_RESERVED_INACTIVE:
                $optItemStat.addClass('bg-secondary text-white');
                $optItemStat.text('Inactive');
                break;
        }
    };

    const renderItemStatData = function (mutationsList, observer) {
        $(mutationsList).each(function () {
            console.log('Order Item Status Select Mutation detected: ', this);
            let $slItemStat = $(this['target']);
            let $optItemStat = $slItemStat.find('option');
            stopObserver();
            $optItemStat.each(function () {
                let lendStatus = $(this).val();
                orderItemStatColoring($(this), lendStatus);
            });
            startObserver();
        });
    };

    const orderItemStatObs = new MutationObserver(renderItemStatData);
    const startObserver = function () {
        slItemStat.each(function () {
            orderItemStatObs.observe(this, observerConfig);
        });
    };
    const stopObserver = function () {
        orderItemStatObs.disconnect();
    };

    // Initial Rendering when page finished loading
    slItemStat.each(function () {
        let $optItemStat = $(this).find('option');
        $optItemStat.each(function () {
            let lendStatus = $(this).val();
            orderItemStatColoring($(this), lendStatus);
        });
    });

    // Start observing changes
    startObserver();
});

// Bootstrap Multiple Modal Overlay Fix
// https://stackoverflow.com/questions/19305821/multiple-modals-overlay
$(document).on('show.bs.modal', '.modal', function () {
    let zIndex = 1040 + (10 * $('.modal:visible').not(this).length);
    $(this).css('z-index', zIndex);
    setTimeout(function () {
        $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
    }, 0);
});