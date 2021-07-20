// Renewal Approval Interface
$(document).ready(function () {
    const RENEWAL_CANCELLED = '-1';
    const RENEWAL_PENDING = '0';
    const RENEWAL_APPROVED = '1';
    const RENEWAL_REJECTED = '2';

    // Creating listener for approve/ reject button (the ones inside confirm modal)
    $('.btnModalAppr').each(function () {
        let $btnModalAppr = $(this);
        $btnModalAppr.each(function () {
            let $button = $(this);
            let $role = $(this).attr('role');
            if ($role === 'approveRenewal') {
                $button.on('click', approveRenewal);
            } else if ($role === 'rejectRenewal') {
                $button.on('click', rejectRenewal);
            }
        });
    });

    function approveRenewal() {
        let $buttonApprove = $(this);
        let renewalID = $buttonApprove.attr('renewalid');
        $.ajax({
            method: 'POST',
            url: 'ApproveRenewalServlet',
            data: {
                txtRenewalID: renewalID
            },
            datatype: 'json',
            success: function (renewalInfo) {
                if (renewalInfo !== null) {
                    console.log(renewalInfo);
                    // Load renewal status
                    $('.lbRenewalStat')
                        .filter(`[renewalid='${renewalID}']`)
                        .attr('renewalStatus', renewalInfo.approvalStatus.toString());
                    $('.inpRenewalStat')
                        .filter(`[renewalid='${renewalID}']`)
                        .attr('renewalStatus', renewalInfo.approvalStatus.toString());
                    // Load staff
                    $('.frmStaffID')
                        .filter(`[renewalid='${renewalID}'`)
                        .val(renewalInfo.librarian.id);
                    $('.frmStaffName')
                        .filter(`[renewalid='${renewalID}'`)
                        .val(renewalInfo.librarian.name);

                    // Remove approval buttons
                    let renewalStat = renewalInfo.approvalStatus.toString();
                    if ((renewalStat !== RENEWAL_PENDING)
                        && (renewalStat !== RENEWAL_CANCELLED)) {
                        $('.contModalApprove').filter(`[renewalid='${renewalID}']`).remove();
                        $('.contModalReject').filter(`[renewalid='${renewalID}']`).remove();
                        let $frmRenewalStat = $('.frmRenewalStat').filter(`[renewalid='${renewalID}']`);
                        let $contStat = $('<div>').addClass('col-lg-5 col-12').appendTo($frmRenewalStat);
                        let $statRenewal = $('<div>')
                            .addClass('btn btn-block btn-outline-success btn-sm bg-white')
                            .attr('disabled', 'disabled')
                            .appendTo($contStat);
                        let $btnAppr = $('<h3>').appendTo($statRenewal);
                        if (renewalStat === RENEWAL_APPROVED) {
                            $btnAppr.addClass('fa fa-check-circle text-success')
                        } else if (renewalStat === RENEWAL_REJECTED) {
                            $btnAppr.addClass('fa fa-times-circle text-danger')
                            alert(`Request was rejected by another librarian.`);
                        }
                    } else if (renewalStat === RENEWAL_PENDING) {
                        alert(`Request was not processed successfully. Please try again.`);
                    } else if (renewalStat === RENEWAL_CANCELLED) {
                        alert(`Request had already been cancelled by the borrower.`);
                    }
                    // Toggle modal off
                    $(`#btnDismissAppr${renewalID}`).click();
                }
            }
        });
    }

    function rejectRenewal() {
        let $buttonApprove = $(this);
        let renewalID = $buttonApprove.attr('renewalid');
        $.ajax({
            method: 'POST',
            url: 'RejectRenewalServlet',
            data: {
                txtRenewalID: renewalID
            },
            datatype: 'json',
            success: function (renewalInfo) {
                if (renewalInfo !== null) {
                    console.log(renewalInfo);
                    // Load renewal status
                    $('.lbRenewalStat')
                        .filter(`[renewalid='${renewalID}']`)
                        .attr('renewalStatus', renewalInfo.approvalStatus.toString());
                    $('.inpRenewalStat')
                        .filter(`[renewalid='${renewalID}']`)
                        .attr('renewalStatus', renewalInfo.approvalStatus.toString());
                    // Load staff
                    $('.frmStaffID')
                        .filter(`[renewalid='${renewalID}'`)
                        .val(renewalInfo.librarian.id);
                    $('.frmStaffName')
                        .filter(`[renewalid='${renewalID}'`)
                        .val(renewalInfo.librarian.name);

                    // Remove approval buttons
                    let renewalStat = renewalInfo.approvalStatus.toString();
                    if ((renewalStat !== RENEWAL_PENDING)
                        && (renewalStat !== RENEWAL_CANCELLED)) {
                        $('.contModalApprove').filter(`[renewalid='${renewalID}']`).remove();
                        $('.contModalReject').filter(`[renewalid='${renewalID}']`).remove();
                        let $frmRenewalStat = $('.frmRenewalStat').filter(`[renewalid='${renewalID}']`);
                        let $contStat = $('<div>').addClass('col-lg-5 col-12').appendTo($frmRenewalStat);
                        let $statRenewal = $('<div>')
                            .addClass('btn btn-block btn-outline-danger btn-sm bg-white')
                            .attr('disabled', 'disabled')
                            .appendTo($contStat);
                        let $btnAppr = $('<h3>').appendTo($statRenewal);
                        if (renewalStat === RENEWAL_APPROVED) {
                            $btnAppr.addClass('fa fa-check-circle text-success')
                            alert(`Request was approved by another librarian.`);
                        } else if (renewalStat === RENEWAL_REJECTED) {
                            $btnAppr.addClass('fa fa-times-circle text-danger')
                        }
                    } else if (renewalStat === RENEWAL_PENDING) {
                        alert(`Request was not processed successfully. Please try again.`);
                    } else if (renewalStat === RENEWAL_CANCELLED) {
                        alert(`Request had already been cancelled by the borrower.`);
                    }
                    // Toggle modal off
                    $(`#btnDismissReject${renewalID}`).click();
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
            console.log('Renewal Status Mutation detected: ', this);
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
            console.log('Renewal Status Details Mutation detected: ', this);
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