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

    // Initial Rendering when page finished loading
    orderStatContainers.each(function () {
        let $lbOrderStat = $(this.firstElementChild);
        let activeStatus = $lbOrderStat.get(0).attributes['activeStatus']['value'];
        orderStatColoring($lbOrderStat, activeStatus);
    });
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

    const lbItemStat = $('.lbItemStat');

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

    // Initial Rendering when page finished loading
    lbItemStat.each(function () {
        let $lbItemStat = $(this);
        let lendStatus = $lbItemStat.get(0).attributes['lendStatus']['value'];
        orderItemStatColoring($lbItemStat, lendStatus);
    });
});