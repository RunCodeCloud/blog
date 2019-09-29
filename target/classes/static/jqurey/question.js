$(function () {

    $(".iconboss1").click(function(){

        var commentId = $(this).attr("var");
        var status = $(this).attr("status");

        if(status=="true"){
            $.ajax({
                type:"POST",
                url:"http://localhost:8080/comment/secondLevel",
                data:{
                    "commentId":commentId,
                    "like":1
                },
                success:function(data){
                    if(data.status=="success"){

                        //改变点赞数
                        var str = "#like"+commentId;
                        $(str).text(data.like);

                        //不能踩
                        var str1 = "#dislike1"+commentId;
                        $(str1).css("pointer-events","none");

                        //改变status
                        var str2 = "#like1"+commentId;
                        $(str2).attr("status","false");
                        //改变css
                        var str3 = "#icon1"+commentId;
                        $(str3).css("backgroundColor","#499ef3");
                        $(str3).css("color","white");

                        alert("点赞成功");
                    }else {
                        alert(data.message);
                    }
                },
                error:function(data){
                    alert("操作失败,原因为"+data.responseText);
                }
            });
            return false;
        }else {
            $.ajax({
                type:"POST",
                url:"http://localhost:8080/comment/secondLevel",
                data:{
                    "commentId":commentId,
                    "like":-1
                },
                success:function(data){
                    if(data.status=="success"){

                        //改变点赞数
                        var str = "#like"+commentId;
                        $(str).text(data.like);

                        //能踩
                        var str1 = "#dislike1"+commentId;
                        $(str1).css("pointer-events","auto");

                        //改变status
                        var str2 = "#like1"+commentId;
                        $(str2).attr("status","true");
                        //改变css
                        var str3 = "#icon1"+commentId;
                        $(str3).css("backgroundColor","white");
                        $(str3).css("color","#999999");
                        alert("取消点赞");
                    }else {
                        alert(data.message);
                    }
                },
                error:function(data){
                    alert("操作失败,原因为"+data.responseText);
                }
            });
            return false;
        }
    });

    $(".iconboss2").click(function(){
        var commentId = $(this).attr("var");
        var status = $(this).attr("status");

        if(status=="true"){
            $.ajax({
                type:"POST",
                url:"http://localhost:8080/comment/secondLevel",
                data:{
                    "commentId":commentId,
                    "dislike":1
                },
                success:function(data){
                    if(data.status=="success"){
                        //改变踩值
                        var str = "#dislike"+commentId;
                        $(str).text(data.dislike);
                        //不能点赞
                        var str1 = "#like1"+commentId;
                        $(str1).css("pointer-events","none");
                        //改变status
                        var str2 = "#dislike1"+commentId;
                        $(str2).attr("status","false");

                        //改变css
                        var str3 = "#icon2"+commentId;
                        $(str3).css("backgroundColor","#499ef3");
                        $(str3).css("color","white");
                        alert("点踩成功");
                    }else {
                        alert(data.message);
                    }
                },
                error:function(data){
                    alert("操作失败,原因为"+data.responseText);
                }
            });
            return false;
        }else {
            $.ajax({
                type:"POST",
                url:"http://localhost:8080/comment/secondLevel",
                data:{
                    "commentId":commentId,
                    "dislike":-1
                },
                success:function(data){
                    if(data.status=="success"){
                        //改变踩值
                        var str = "#dislike"+commentId;
                        $(str).text(data.dislike);
                        //能点赞
                        var str1 = "#like1"+commentId;
                        $(str1).css("pointer-events","auto");
                        //改变status
                        var str2 = "#dislike1"+commentId;
                        $(str2).attr("status","true");

                        //改变css
                        var str3 = "#icon2"+commentId;
                        $(str3).css("backgroundColor","white");
                        $(str3).css("color","#999999");
                        alert("取消点踩");

                    }else {
                        alert(data.message);
                    }
                },
                error:function(data){
                    alert("操作失败,原因为"+data.responseText);
                }
            });
            return false;
        }
    });

    $(".iconboss3").click(function(){
        var commentId = $(this).attr("var");
        var status = $(this).attr("status");

        //弹出二级回复框
        if(status=="true"){

        }

        $.ajax({
            type:"POST",
            url:"http://localhost:8080/comment/level",
            data:{
                "commentId":commentId,
                "dislike":1
            },
            success:function(data){
                if(data.status=="success"){
                    //改变踩值
                    var str = "#dislike"+commentId;
                    $(str).text(data.dislike);
                    //不能点赞
                    var str1 = "#like1"+commentId;
                    $(str1).css("pointer-events","none");
                    //改变status
                    var str2 = "#dislike1"+commentId;
                    $(str2).attr("status","false");

                    //改变css
                    var str3 = "#icon2"+commentId;
                    $(str3).css("backgroundColor","#499ef3");
                    $(str3).css("color","white");
                    alert("点踩成功");
                }else {
                    alert(data.message);
                }
            },
            error:function(data){
                alert("操作失败,原因为"+data.responseText);
            }
        });
        return false;
    });


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