$(document).ready(function () {
    $('#rental-datatable').DataTable({
        searchPanes: {
            viewTotal: true,
            columns: [3]
        },
        dom: 'Plfrtip',
        columnDefs: [
            {
                searchPanes: {
                    show: true,
                    options: [
                        {
                            label: 'Unpaid',
                            value: function (rowData, rowIdx) {
                                return $(rowData[3]).attr('penaltyStatus') === '1';
                            }
                        },
                        {
                            label: 'Paid',
                            value: function (rowData, rowIdx) {
                                return $(rowData[3]).attr('penaltyStatus') === '2';
                            }
                        },
                    ]
                },
                targets: [3]
            }
        ],
        order: [[1, 'asc']]
    });

    $('.dtsp-panesContainer').addClass('col-3 mx-auto');
    $('.dataTables_scrollBody').css('height', '80px');
    $('#rental-datatable_wrapper').css('max-width', '81vw');
});

// For loading item status select options
function loadPenaltyStatOptions(penaltyStatusDirection, baseStats) {
    $('.lbPenaltyStat').each(function () {
        let $lbPenaltyStat = $(this);
        let orderItemID = $lbPenaltyStat.attr('orderitemid');
        let penaltyStatus = $lbPenaltyStat.attr('penaltyStatus');
        let $slPenaltyStat = $(`#slPenaltyStat${orderItemID}`);
        $slPenaltyStat.find('option').remove();
        let $optBase = $('<option>')
            .val(penaltyStatus)
            .attr('orderitemid', orderItemID)
            .appendTo($slPenaltyStat);
        for (const baseStat of baseStats) {
            if (baseStat === penaltyStatus) {
                $('<option>')
                    .val(penaltyStatusDirection.get(penaltyStatus))
                    .attr('orderitemid', orderItemID)
                    .appendTo($slPenaltyStat);
                break;
            }
        }
    });
}

// For loading/ hiding editing buttons
function loadButtons() {
    const PENALTY_NONE = '0';
    const PENALTY_UNPAID = '1';
    const PENALTY_PAID = '2';
    $('.lbPenaltyStat').each(function () {
        let $lbPenaltyStat = $(this);
        let orderItemID = $lbPenaltyStat.attr('orderitemid');
        let penaltyStatus = $lbPenaltyStat.attr('penaltyStatus');
        if ((penaltyStatus === PENALTY_NONE)
            || (penaltyStatus === PENALTY_PAID)) {
            $('.groupBtnEdit').filter(`[orderitemid='${orderItemID}']`).hide();
        } else {
            $('.groupBtnEdit').filter(`[orderitemid='${orderItemID}']`).show();
        }
    });
}


// Penalty Edit Interface
$(document).ready(function () {
    const PENALTY_NONE = '0';
    const PENALTY_UNPAID = '1';
    const PENALTY_PAID = '2';

    // Penalty Status Direction
    const PENALTY_STATUS_DIRECTION = new Map();
    PENALTY_STATUS_DIRECTION.set(PENALTY_UNPAID, PENALTY_PAID);
    let baseStats = [...PENALTY_STATUS_DIRECTION.keys()];

    // Load buttons for editing
    loadButtons();

    // Loading options for editing
    loadPenaltyStatOptions(PENALTY_STATUS_DIRECTION, baseStats);

    // Creating listener for coloring penalty status select
    $('.slPenaltyStat').each(function () {
        let $slPenaltyStat = $(this);
        $slPenaltyStat.on('click', function () {
            $slPenaltyStat.removeClass();
            $slPenaltyStat.addClass('custom-select slPenaltyStat');
            $slPenaltyStat.addClass($slPenaltyStat.find(':selected').attr('class'));
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
     * Get orderItemID from button
     * Get item statuses of order ID
     * Create list of item statuses
     * Send list to servlet
     * Servlet returns result
     * Load item status
     * Hide edit interface
     */
    function submitEdit() {
        let $buttonSubmit = $(this);
        let orderItemID = $buttonSubmit.attr('orderitemid');
        let penaltyStatus = $('.slPenaltyStat')
            .filter(`[orderitemid='${orderItemID}']`)
            .find('option:selected').val();
        $.ajax({
            method: 'POST',
            url: 'UpdatePenaltyServlet',
            data: {
                txtOrderItemID: orderItemID,
                txtPenaltyStatus: penaltyStatus
            },
            datatype: 'json',
            success: function (penalty) {
                let orderItemID = $(penalty).attr('id');
                let penaltyStatus = $(penalty).attr('penaltyStatus');
                $(`#lbPenaltyStat${orderItemID}`).attr('penaltyStatus', penaltyStatus);
                $(`#inpPenaltyStat${orderItemID}`).attr('penaltyStatus', penaltyStatus);

                let $groupBtn = $('.groupBtnEdit').filter(`[orderitemid='${orderItemID}']`);
                let $storageBtn = $('.storageBtn').filter(`[orderitemid='${orderItemID}']`);
                let $btnsEdit = $groupBtn.find(`[orderitemid='${orderItemID}']`);
                let $btnLoad = $storageBtn.find(`[orderitemid='${orderItemID}']`);
                $btnsEdit.appendTo($storageBtn);
                $btnLoad.appendTo($groupBtn);

                let $slPenaltyStat = $('.slPenaltyStat').filter(`[orderitemid='${orderItemID}']`);
                let $inpPenaltyStat = $('.inpPenaltyStat').filter(`[orderitemid='${orderItemID}']`);
                $slPenaltyStat.hide();
                $inpPenaltyStat.show();

                // Check if buttons should be hidden
                loadButtons();
            }
        });
    }

    /**
     * Cancelling edit|
     * Get the orderItemID from the clicked button
     * Get container of edit buttons (groupBtn), get container of load interface button (storageBtn)
     * Get the buttons inside each container
     * Swap the buttons
     * ---
     * Hide edit interface
     * Show details interface
     */
    function cancelEditInterface() {
        let $buttonCancel = $(this);
        let orderItemID = $buttonCancel.attr('orderitemid');
        let $groupBtn = $('.groupBtnEdit').filter(`[orderitemid='${orderItemID}']`);
        let $storageBtn = $('.storageBtn').filter(`[orderitemid='${orderItemID}']`);
        let $btnsEdit = $groupBtn.find(`[orderitemid='${orderItemID}']`);
        let $btnLoad = $storageBtn.find(`[orderitemid='${orderItemID}']`);
        $btnsEdit.appendTo($storageBtn);
        $btnLoad.appendTo($groupBtn);

        let $slPenaltyStat = $('.slPenaltyStat').filter(`[orderitemid='${orderItemID}']`);
        let $inpPenaltyStat = $('.inpPenaltyStat').filter(`[orderitemid='${orderItemID}']`);
        $slPenaltyStat.hide();
        $inpPenaltyStat.show();
    }

    /**
     * Start editing|
     * Get the orderItemID from the clicked button
     * Get container of load interface button (groupBtn), get container of edit buttons (storageBtn)
     * Get the buttons inside each container
     * Swap the buttons
     * ---
     * Hide details interface
     * Show edit interface
     */
    function loadEditInterface() {
        let $button = $(this);
        let orderItemID = $button.attr('orderitemid');
        let $groupBtn = $('.groupBtnEdit').filter(`[orderitemid='${orderItemID}']`);
        let $storageBtn = $('.storageBtn').filter(`[orderitemid='${orderItemID}']`);
        let $btnsEdit = $storageBtn.find(`[orderitemid='${orderItemID}']`);
        $btnsEdit.appendTo($groupBtn);
        $button.appendTo($storageBtn);

        let $slPenaltyStat = $('.slPenaltyStat').filter(`[orderitemid='${orderItemID}']`);
        let $inpPenaltyStat = $('.inpPenaltyStat').filter(`[orderitemid='${orderItemID}']`);
        $inpPenaltyStat.hide();
        $slPenaltyStat.show();
    }
});

// Mutation Observer for Penalty Status
$(document).ready(function () {
    const PENALTY_NONE = '0';
    const PENALTY_UNPAID = '1';
    const PENALTY_PAID = '2';

    // Penalty Status Direction
    const PENALTY_STATUS_DIRECTION = new Map();
    PENALTY_STATUS_DIRECTION.set(PENALTY_UNPAID, PENALTY_PAID);
    let baseStats = [...PENALTY_STATUS_DIRECTION.keys()];

    const lbPenaltyStat = $('#rental-datatable').DataTable().rows().nodes().to$();
    const observerConfig = {attributes: true, subtree: true};

    const penaltyStatColoring = function ($lbPenaltyStat, penaltyStat) {
        let baseClass = 'badge lbPenaltyStat';
        $lbPenaltyStat.removeClass();
        $lbPenaltyStat.addClass(baseClass);
        switch (penaltyStat) {
            case PENALTY_UNPAID:
                $lbPenaltyStat.addClass('bg-danger text-white');
                $lbPenaltyStat.text('Unpaid');
                break;
            case PENALTY_PAID:
                $lbPenaltyStat.addClass('bg-success text-white');
                $lbPenaltyStat.text('Paid');
                break;
        }
    }

    const renderPenaltyStatData = function (mutationsList, observer) {
        $(mutationsList).each(function () {
            // console.log('Penalty Status Mutation detected: ', this);
            let $lbPenaltyStat = $(this['target']);
            let penaltyStat = $lbPenaltyStat.get(0).attributes['penaltyStatus']['value'];
            // Temporarily stop observing to ignore rendering changes
            stopObserver();
            penaltyStatColoring($lbPenaltyStat, penaltyStat);
            startObserver();
        });
        // Load penalty status options after updating penaltyStatus
        loadPenaltyStatOptions(PENALTY_STATUS_DIRECTION, baseStats);
    };

    const penaltyStatObs = new MutationObserver(renderPenaltyStatData);
    const startObserver = function () {
        lbPenaltyStat.each(function () {
            penaltyStatObs.observe($(this.cells[3]).find('label').get(0), observerConfig);
        });
    };
    const stopObserver = function () {
        penaltyStatObs.disconnect();
    };

    // Initial Rendering when page finished loading
    lbPenaltyStat.each(function () {
        let $lbPenaltyStat = $(this.cells[3]).find('label');
        let penaltyStat = $lbPenaltyStat.get(0).attributes['penaltyStatus']['value'];
        penaltyStatColoring($lbPenaltyStat, penaltyStat);
    });

    // Start observing changes
    startObserver();

});

// Mutation Observer for Penalty Status in Details
$(document).ready(function () {
    const PENALTY_NONE = '0';
    const PENALTY_UNPAID = '1';
    const PENALTY_PAID = '2';

    // Penalty Status Direction
    const PENALTY_STATUS_DIRECTION = new Map();
    PENALTY_STATUS_DIRECTION.set(PENALTY_UNPAID, PENALTY_PAID);
    let baseStats = [...PENALTY_STATUS_DIRECTION.keys()];

    const inpPenaltyStat = $('.inpPenaltyStat');
    const observerConfig = {attributes: true, subtree: true};

    const penaltyStatColoring = function ($inpPenaltyStat, penaltyStat) {
        let baseClass = 'form-control inpPenaltyStat';
        $inpPenaltyStat.removeClass();
        $inpPenaltyStat.addClass(baseClass);
        switch (penaltyStat) {
            case PENALTY_UNPAID:
                $inpPenaltyStat.addClass('bg-danger text-white');
                $inpPenaltyStat.val('Unpaid');
                break;
            case PENALTY_PAID:
                $inpPenaltyStat.addClass('bg-success text-white');
                $inpPenaltyStat.val('Paid');
                break;
        }
    }

    const renderPenaltyStatData = function (mutationsList, observer) {
        $(mutationsList).each(function () {
            // console.log('Penalty Status Details Mutation detected: ', this);
            let $inpPenaltyStat = $(this['target']);
            let penaltyStat = $inpPenaltyStat.get(0).attributes['penaltyStatus']['value'];
            // Temporarily stop observing to ignore rendering changes
            stopObserver();
            penaltyStatColoring($inpPenaltyStat, penaltyStat);
            startObserver();
        });
        // Load penalty status options after updating penaltyStatus
        loadPenaltyStatOptions(PENALTY_STATUS_DIRECTION, baseStats);
    };

    const penaltyStatObs = new MutationObserver(renderPenaltyStatData);
    const startObserver = function () {
        inpPenaltyStat.each(function () {
            penaltyStatObs.observe(this, observerConfig);
        });
    };
    const stopObserver = function () {
        penaltyStatObs.disconnect();
    };

    // Initial Rendering when page finished loading
    inpPenaltyStat.each(function () {
        let $inpPenaltyStat = $(this);
        let penaltyStat = $inpPenaltyStat.get(0).attributes['penaltyStatus']['value'];
        penaltyStatColoring($inpPenaltyStat, penaltyStat);
    });

    // Start observing changes
    startObserver();

});

// Mutation Observer for Penalty Status Select when Editing
$(document).ready(function () {
    const PENALTY_NONE = '0';
    const PENALTY_UNPAID = '1';
    const PENALTY_PAID = '2';

    const slPenaltyStat = $('.slPenaltyStat');
    const observerConfig = {childList: true};

    const penaltyStatColoring = function ($inpPenaltyStat, penaltyStat) {
        $inpPenaltyStat.removeClass();
        switch (penaltyStat) {
            case PENALTY_UNPAID:
                $inpPenaltyStat.addClass('bg-danger text-white');
                $inpPenaltyStat.text('Unpaid');
                break;
            case PENALTY_PAID:
                $inpPenaltyStat.addClass('bg-success text-white');
                $inpPenaltyStat.text('Paid');
                break;
        }
    }

    const renderPenaltyStatData = function (mutationsList, observer) {
        $(mutationsList).each(function () {
            // console.log('Penalty Status Select Mutation detected: ', this);
            let $slPenaltyStat = $(this['target']);
            let $optPenaltyStat = $slPenaltyStat.find('option');
            stopObserver();
            $optPenaltyStat.each(function () {
                let penaltyStatus = $(this).val();
                penaltyStatColoring($(this), penaltyStatus);
            });
            startObserver();
        });
    };

    const penaltyStatObs = new MutationObserver(renderPenaltyStatData);
    const startObserver = function () {
        slPenaltyStat.each(function () {
            penaltyStatObs.observe(this, observerConfig);
        });
    };
    const stopObserver = function () {
        penaltyStatObs.disconnect();
    };

    // Initial Rendering when page finished loading
    slPenaltyStat.each(function () {
        let $optPenaltyStat = $(this).find('option');
        $optPenaltyStat.each(function () {
            let penaltyStatus = $(this).val();
            penaltyStatColoring($(this), penaltyStatus);
        });
    });

    // Start observing changes
    startObserver();
});