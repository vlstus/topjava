var mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("profile/meals/", updateTableByData);
}


$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return date.substring(0, 10);
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (data.excess) {
                    $(row).attr("data-mealExcess", true);
                } else {
                    $(row).attr("data-mealExcess", false);
                }
            }
        }),
        updateTable: updateFilteredTable
    });
});