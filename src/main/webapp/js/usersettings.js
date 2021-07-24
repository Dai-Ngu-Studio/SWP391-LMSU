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
    $('.btnModalCnclOrder').each(function () {
        let $btnModalCnclOrder = $(this);
        $btnModalCnclOrder.each(function () {
            let $button = $(this);
            let $role = $(this).attr('role');
            if ($role === 'cancelOrder') {
                $button.on('click', cancelOrder);
            }
        });
    });

    /**
     * Get Order ID from button
     * Send Order ID to Servlet
     * Servlet returns result
     * Load order status
     * Load item status
     * Remove cancel buttons
     */
    function cancelOrder() {
        let $buttonCancel = $(this);
        let orderID = $buttonCancel.attr('orderid');
        $.ajax({
            method: 'POST',
            url: 'CancelOrderServlet',
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
                    if ((orderStat === ORDER_CANCELLED)) {
                        $('.contModalCancelOrder').filter(`[orderid='${orderID}']`).remove();
                        let $lbCancelOrder = $('.lbCancelOrder').filter(`[orderid='${orderID}']`).remove();
                    } else if (orderStat === ORDER_PENDING) {
                        alert(`Order was not processed successfully. Please try again.`);
                    } else if (orderStat === ORDER_APPROVED) {
                        alert(`Order has already been approved.`);
                    } else if (orderStat === ORDER_REJECTED) {
                        alert(`Order has already been rejected.`);
                    }
                    // Toggle modal off
                    $(`#btnDismissCnclOrder${orderID}`).click();
                } else {
                    alert(`Error processing. Please refresh the page.`);
                }
            }
        });
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
            // console.log('Order Item Status Mutation detected: ', this);
            let $lbItemStat = $(this['target']);
            let lendStatus = $lbItemStat.get(0).attributes['lendStatus']['value'];
            // Temporarily stop observing to ignore rendering changes
            stopObserver();
            orderItemStatColoring($lbItemStat, lendStatus);
            startObserver();
        });
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

// Renewal Approval Interface
$(document).ready(function () {
    const RENEWAL_CANCELLED = '-1';
    const RENEWAL_PENDING = '0';
    const RENEWAL_APPROVED = '1';
    const RENEWAL_REJECTED = '2';

    // Creating listener for approve/ reject button (the ones inside confirm modal)
    $('.btnModalCnclRenew').each(function () {
        let $btnModalCnclRenew = $(this);
        $btnModalCnclRenew.each(function () {
            let $button = $(this);
            let $role = $(this).attr('role');
            if ($role === 'cancelRenewal') {
                $button.on('click', cancelRenewal);
            }
        });
    });

    function cancelRenewal() {
        let $buttonCancel = $(this);
        let renewalID = $buttonCancel.attr('renewalid');
        $.ajax({
            method: 'POST',
            url: 'CancelRenewalServlet',
            data: {
                txtRenewalID: renewalID
            },
            datatype: 'json',
            success: function (renewalInfo) {
                if (renewalInfo !== null) {
                    // Load renewal status
                    $('.lbRenewalStat')
                        .filter(`[renewalid='${renewalID}']`)
                        .attr('renewalStatus', renewalInfo.approvalStatus.toString());
                    $('.inpRenewalStat')
                        .filter(`[renewalid='${renewalID}']`)
                        .attr('renewalStatus', renewalInfo.approvalStatus.toString());

                    // Remove approval buttons
                    let renewalStat = renewalInfo.approvalStatus.toString();
                    if ((renewalStat === RENEWAL_CANCELLED)) {
                        $('.contModalCancelRenew').filter(`[renewalid='${renewalID}']`).remove();
                        let $lbCancelRenewal = $('.lbCancelRenewal').filter(`[renewalid='${renewalID}']`).remove();
                    } else if (renewalStat === RENEWAL_PENDING) {
                        alert(`Request was not processed successfully. Please try again.`);
                    } else if (renewalStat === RENEWAL_APPROVED) {
                        alert(`Request has already been approved.`);
                    } else if (renewalStat === RENEWAL_REJECTED) {
                        alert(`Request has already been rejected.`);
                    }
                    // Toggle modal off
                    $(`#btnDismissCnclRenew${renewalID}`).click();
                }
            }
        });
    }
});

// Mutation Observer for Renewal Status
$(document).ready(function () {
    const RENEWAL_CANCELLED = '-1';
    const RENEWAL_PENDING = '0';
    const RENEWAL_APPROVED = '1';
    const RENEWAL_REJECTED = '2';

    const lbRenewalStat = $('.lbRenewalStat');
    const observerConfig = {attributes: true, subtree: true};

    const renewalStatColoring = function ($lbRenewalStat, renewalStat) {
        let baseClass = 'badge lbRenewalStat';
        $lbRenewalStat.removeClass();
        $lbRenewalStat.addClass(baseClass);
        switch (renewalStat) {
            case RENEWAL_CANCELLED:
                $lbRenewalStat.addClass('bg-secondary text-white');
                $lbRenewalStat.text('Cancelled');
                break;
            case RENEWAL_PENDING:
                $lbRenewalStat.addClass('bg-warning text-dark');
                $lbRenewalStat.text('Pending');
                break;
            case RENEWAL_APPROVED:
                $lbRenewalStat.addClass('bg-success text-white');
                $lbRenewalStat.text('Approved');
                break;
            case RENEWAL_REJECTED:
                $lbRenewalStat.addClass('bg-dark text-white');
                $lbRenewalStat.text('Rejected');
                break;
        }
    }

    const renderRenewalStatData = function (mutationsList, observer) {
        $(mutationsList).each(function () {
            // console.log('Renewal Status Mutation detected: ', this);
            let $lbRenewalStat = $(this['target']);
            let renewalStat = $lbRenewalStat.get(0).attributes['renewalStatus']['value'];
            // Temporarily stop observing to ignore rendering changes
            stopObserver();
            renewalStatColoring($lbRenewalStat, renewalStat);
            startObserver();
        });
    };

    const renewalStatObs = new MutationObserver(renderRenewalStatData);
    const startObserver = function () {
        lbRenewalStat.each(function () {
            renewalStatObs.observe(this, observerConfig);
        });
    };
    const stopObserver = function () {
        renewalStatObs.disconnect();
    };

    // Initial Rendering when page finished loading
    lbRenewalStat.each(function () {
        let $lbRenewalStat = $(this);
        let renewalStat = $lbRenewalStat.get(0).attributes['renewalStatus']['value'];
        renewalStatColoring($lbRenewalStat, renewalStat);
    });

    // Start observing changes
    startObserver();
});

// Mutation Observer for Renewal Status in Details
$(document).ready(function () {
    const RENEWAL_CANCELLED = '-1';
    const RENEWAL_PENDING = '0';
    const RENEWAL_APPROVED = '1';
    const RENEWAL_REJECTED = '2';

    const inpRenewalStat = $('.inpRenewalStat');
    const observerConfig = {attributes: true, subtree: true};

    const renewalStatColoring = function ($inpRenewalStat, renewalStat) {
        let baseClass = 'form-control inpRenewalStat';
        $inpRenewalStat.removeClass();
        $inpRenewalStat.addClass(baseClass);
        switch (renewalStat) {
            case RENEWAL_CANCELLED:
                $inpRenewalStat.addClass('bg-secondary text-white');
                $inpRenewalStat.val('Cancelled');
                break;
            case RENEWAL_PENDING:
                $inpRenewalStat.addClass('bg-warning text-dark');
                $inpRenewalStat.val('Pending');
                break;
            case RENEWAL_APPROVED:
                $inpRenewalStat.addClass('bg-success text-white');
                $inpRenewalStat.val('Approved');
                break;
            case RENEWAL_REJECTED:
                $inpRenewalStat.addClass('bg-dark text-white');
                $inpRenewalStat.val('Rejected');
                break;
        }
    }

    const renderRenewalStatData = function (mutationsList, observer) {
        $(mutationsList).each(function () {
            // console.log('Renewal Status Details Mutation detected: ', this);
            let $inpRenewalStat = $(this['target']);
            let renewalStat = $inpRenewalStat.get(0).attributes['renewalStatus']['value'];
            // Temporarily stop observing to ignore rendering changes
            stopObserver();
            renewalStatColoring($inpRenewalStat, renewalStat);
            startObserver();
        });
    };

    const renewalStatObs = new MutationObserver(renderRenewalStatData);
    const startObserver = function () {
        inpRenewalStat.each(function () {
            renewalStatObs.observe(this, observerConfig);
        });
    };
    const stopObserver = function () {
        renewalStatObs.disconnect();
    };

    // Initial Rendering when page finished loading
    inpRenewalStat.each(function () {
        let $inpRenewalStat = $(this);
        let renewalStat = $inpRenewalStat.get(0).attributes['renewalStatus']['value'];
        renewalStatColoring($inpRenewalStat, renewalStat);
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