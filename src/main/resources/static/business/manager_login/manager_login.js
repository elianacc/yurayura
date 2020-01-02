var managerLoginThat = this;

const containerVm = new Vue({
    el: "#container",
    data: {
        isContainerShow: true,
        managerName: "",
        managerPassword: "",
        verifyCode: "",
        isInValid: [
            false,
            false,
            false
        ],
        isInValidText: [
            "用户名不能为空",
            "密码不能为空"
        ]
    },
    methods: {
        validataForm: function () {
            if (this.managerName == "") {
                Vue.set(this.isInValid, 0, true);
                return false;
            } else if (this.managerPassword == "") {
                Vue.set(this.isInValid, 1, true);
                return false;
            } else if (this.verifyCode == "") {
                Vue.set(this.isInValid, 2, true);
                return false;
            } else {
                return true;
            }
        },
        loginBusiness: function () {
            if (this.validataForm()) {
                $("#managerLoginForm").ajaxSubmit({
                    url: "/sys/manager/login",
                    data: {managerPassword: Base64.encode(this.managerPassword)},
                    type: "post",
                    success: function (res) {
                        if (res.code == 100) {
                            managerLoginThat.loadVerifyImage();
                            toastr.error(res.msg);
                        } else if (res.code == 101) {
                            console.log("请不要重复提交");
                        } else {
                            containerVm.isContainerShow = false;
                            spinnerVm.isSpinnerShow = true;
                            setTimeout(function () {
                                location.replace("/business/comic_info");
                            }, 3000);
                        }
                    }
                });
            }
        }
    },
    watch: {
        managerName: function () {
            Vue.set(this.isInValid, 0, false);
        },
        managerPassword: function () {
            Vue.set(this.isInValid, 1, false);
        },
        verifyCode: function () {
            Vue.set(this.isInValid, 2, false);
        }
    },
    mounted: function () {
        managerLoginThat.initToastr();
    }
});

const spinnerVm = new Vue({
    el: "#spinner",
    data: {
        isSpinnerShow: false
    }
});

function loadVerifyImage() {
    document.getElementById("verifyImage").src = "/sys/manager/getVerifyCode?randomId=" + Math.random();
}