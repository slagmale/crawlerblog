$('#spider').on('click',function () {
    $(".spinner").show();
    $.ajax({
        url:"/crawler/start.action",
        type:"post",
        data:{
            userName:$("#userName").val()
        },
        datatype:"JSON",
        success:function (res) {
            $(".spinner").hide();
        },
        error:function () {
            alert("没进入后台")
        }
    })
})

$('#blogNews').on('click',function () {
    $(".spinner").show();
    $.ajax({
        url:"/news/start.action",
        type:"post",
        datatype:"JSON",
        success:function (res) {
            $(".spinner").hide();
        },
        error:function () {
            alert("没进入后台")
        }
    })
})