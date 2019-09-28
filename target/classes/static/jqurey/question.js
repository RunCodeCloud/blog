$(function () {

    $('.closed').css('padding-top','0');

    $("#post").on("click",function(){
       var parent_id = $("#parent_id").val();
       var question_id = $("#question_id").val();
       var type = $("#type").val();
       var content = $("#content").val();
        $.ajax({
            type:"POST",
            url:"http://localhost:8080/comment",
            data:{
                "parent_id":parent_id,
                "question_id":question_id,
                "type":type,
                "content":content
            },
            /*配合后端设置,使session共享*/
            success: function(data){
                if(data.status=="success"){
                    window.location.href=data.url;
                }else{
                    if(data.status=="fail"&&data.message=="用户未登录"){
                        var isAccepted = confirm(data.message);
                        if(isAccepted){
                            window.open("https://github.com/login/oauth/authorize?client_id=64e70d915680d856dec0&redirect_uri=http://localhost:8080/callback&scope=user&state=1 ");
                            window.localStorage.setItem("close","true");
                       }
                    }else {
                        alert("回复失败,原因为"+data.message);
                    }
                }
            },
            error: function(data){
                alert("回复失败,原因为"+data.responseText);
            }
        });
        return false;
    });
});