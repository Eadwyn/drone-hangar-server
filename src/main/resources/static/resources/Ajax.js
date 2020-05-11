var Ajax = {
    post: function (url, data, callback) {
        $.ajax({
            url: "http://localhost:8071" + url,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (response) {
                if (callback) {
                    callback(response);
                } else {
                    if (response.code == "0") {} else {
                        alert(response.message);
                    }
                }
            },
            error: function (ajaxobj) {
                if (ajaxobj.responseText != '') {
                    console.log(ajaxobj.responseText);
                }
            }
        });
    }
}