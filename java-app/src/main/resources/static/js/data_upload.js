// 主処理部
$(function(){

    // アップロードボタンを押下した
    $("#data_upload_form").submit(function(event){
        // 要素規定の動作をキャンセルする
        event.preventDefault();

        var ajaxUrl = "/upload";

        if(window.FormData){
            var formData = new FormData($(this)[0]);

            $.ajax({
                type : "POST",                  // HTTP通信の種類
                url  : ajaxUrl,                 // リクエストを送信する先のURL
                dataType : "text",              // サーバーから返されるデータの型
                data : formData,                // サーバーに送信するデータ
                processData : false,
                contentType: false,
            }).done(function(data) {        // Ajax通信が成功した時の処理
                alert("アップロードが完了しました。");
            }).fail(function(XMLHttpRequest, textStatus, errorThrown) { // Ajax通信が失敗した時の処理
                alert("アップロードが失敗しました。");
            });
        }else{
            alert("アップロードに対応できていないブラウザです。");
        }
    });

    $("#data_uploadstream_form").submit(function(event){
        event.preventDefault();

        var ajaxUrl = "/uploadstream";

        if(window.FormData){
            var formData = new FormData($(this)[0]);

            $.ajax({
                type : "POST",
                url  : ajaxUrl,
                dataType : "text",
                data : formData,
                processData : false,
                contentType: false,
            }).done(function(data) {
                alert("アップロードが完了しました。");
            }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
                alert("アップロードが失敗しました。");
            });
        }else{
            alert("アップロードに対応できていないブラウザです。");
        }
    });

    $("#data_uploadstream_form").submit(function(event){
        event.preventDefault();

        var ajaxUrl = "/uploadschedule";

        if(window.FormData){
            var formData = new FormData($(this)[0]);

            $.ajax({
                type : "POST",
                url  : ajaxUrl,
                dataType : "text",
                data : formData,
                processData : false,
                contentType: false,
            }).done(function(data) {
                alert("アップロードが完了しました。");
            }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
                alert("アップロードが失敗しました。");
            });
        }else{
            alert("アップロードに対応できていないブラウザです。");
        }
    });

    $("#data_uploadsas_form").submit(function(event){

        event.preventDefault();

        var url = await fetch("http://localhost:8080/sas", {
            method: "GET"
        });
        var ajaxUrl = url;
        console.log(ajaxUrl);

        if(window.FormData){
            var formData = new FormData($(this)[0]);

            $.ajax({
                type : "POST",
                url  : ajaxUrl,
                dataType : "text",
                data : formData,
                processData : false,
                contentType: false,
            }).done(function(data) {
                alert("アップロードが完了しました。");
            }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
                alert("アップロードが失敗しました。");
            });
        }else{
            alert("アップロードに対応できていないブラウザです。");
        }
    });
    
});