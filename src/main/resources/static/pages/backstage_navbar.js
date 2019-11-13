const navbarVm = new Vue({
    el: "#navbar",
    methods: {
        logoutManager: function () {
            bootbox.confirm({
                size: "small",
                title: "<i class='fa fa-comment'></i>&nbsp;&nbsp;提示",
                message: "确定要注销吗？",
                buttons: {
                    confirm: {
                        label: '确定',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '取消',
                        className: 'btn-danger'
                    }
                },
                backdrop: true,
                closeButton: false,
                callback: function (result) {
                    if (result == true) {
                        $.ajax({
                            url: "/manager/logout",
                            data: null,
                            type: "post",
                            success: function (res) {
                                if (res.code == 200) {
                                    location.reload();
                                } else {
                                    alert(res.data.errorMsg);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
});