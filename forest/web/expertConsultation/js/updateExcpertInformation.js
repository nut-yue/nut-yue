$(function () {
    var url = location.search;
    var specialistId = "";
    if(url.indexOf('?')!=-1){
        specialistId = url.substr(1);
        console.log(specialistId)
    }
    $.ajax({
        type:"post",
        url:"../../servlet/getSpecialistInformation",
        data:{"specialistId":specialistId},
       dataType:"json",
        success:function (datas) {
            console.log(datas)
            $("#specialistName").val(datas.specialistName);
            $("#speciality").val(datas.speciality);
            $("#telphone").val(datas.telphone);
            $("#address").val(datas.address);
            $("#duty").val(datas.duty);
            $("#workunit").val(datas.workunit);
            $("#email").val(datas.email);
            $("#date1").val(datas.birthday);
            $("#sex").val(datas.sex);
            $("#demo2").attr("src","../../down.jpg/"+datas.picture);
        }
    })
    $(".update").click(function () {
     $.ajax({
         type:"post",
         url:"../../servlet/updateSpecialistInformation",
         data:{"specialistId":specialistId,"telphone":$("#telphone").val(),"duty":$("#duty").val(),"workunit":$("#workunit").val(),"email":$("#email").val()},
         dataType:"json",
         success:function (datas) {
             console.log(datas)
             if(datas==1){
                 window.location.href="expert.html"
             }
         }
     })
    });
    $(".back").click(function () {
        window.location.href="expert.html";
    });
});