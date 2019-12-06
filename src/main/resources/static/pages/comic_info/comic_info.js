var comicInfoThat = this;

const containerVm = new Vue({
    el: "#container",
    data: {
        searchWord: "",
        ids: [],
        nowPageNum: 1,
        pageInfo: "",
        modalTitle: "",
        isInsertModal: true,
        isDetailModal: true,
        isUdTimeShow: false,
        id: 0,
        comicName: "",
        comicTime: "",
        comicContent: "",
        comicStatus: 0,
        comicUdTime: "",
        comicTag: [],
        freeLabelA: "",
        freeLabelB: "",
        freeLabelC: "",
        freeLabelD: "",
        cmImgFileLb: "示例图片.jpg",
        cmImgFilePv: "/images/tpjxz.jpg",
        comicLink: "",
        comicShelfStatus: 1
    },
    methods: {
        buildPageComicManage: function () {
            $.ajax({
                url: "/comic/getPageToAll",
                data: {
                    pageNum: this.nowPageNum
                },
                type: "post",
                dataType: 'json',
                success: function (res) {
                    if (res.code == 200) {
                        containerVm.pageInfo = res.data;
                    } else {
                        toastr.error(res.msg);
                    }
                }
            });
        },
        toFirstPage: function () {
            this.nowPageNum = 1;
            this.buildPageComicManage();
        },
        toPrePage: function () {
            this.nowPageNum = this.nowPageNum - 1;
            this.buildPageComicManage();
        },
        toNextPage: function () {
            this.nowPageNum = this.nowPageNum + 1;
            this.buildPageComicManage();
        },
        toLastPage: function (lastPage) {
            this.nowPageNum = lastPage;
            this.buildPageComicManage();
        },
        toClickPage: function (clickPage) {
            this.nowPageNum = clickPage;
            this.buildPageComicManage();
        },
        insertModalOpen: function () {
            this.modalTitle = "『添加窗口』";
            this.isInsertModal = true;
            $("#comicModal").modal();
        },
        insertContent: function () {
            if (this.comicName == "") {
                toastr.error("番剧名不能为空");
            } else if (this.comicContent.length >= 200) {
                toastr.error("内容不能超过200个字符");
            } else {
                $("#comicForm").ajaxSubmit({
                    url: "/comic/insert",
                    type: "post",
                    success: function (res) {
                        $("#comicModal").modal('hide');
                        if (res.code == 200) {
                            toastr.success(res.msg);
                            containerVm.toFirstPage();
                        } else if (res.code == 101) {
                            console.log("请不要重复提交");
                        } else {
                            toastr.error(res.msg);
                        }
                    }
                });
            }
        },
        deleteBetch: function () {
            bootbox.confirm({
                size: "small",
                title: "<i class='fa fa-comment'></i>&nbsp;&nbsp;提示",
                message: "确定要删除选中项吗？",
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
                        if (containerVm.ids.length == 0) {
                            toastr.error("请至少选择一项删除");
                        } else {
                            $.ajax({
                                url: "/comic/deleteBatchByIds",
                                data: {
                                    ids: containerVm.ids.toString()
                                },
                                type: "post",
                                success: function (res) {
                                    if (res.code == 200) {
                                        toastr.success(res.msg);
                                        containerVm.toClickPage(containerVm.nowPageNum);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        },
        searchContent: function () {
            this.nowPageNum = 1;
            $.ajax({
                url: "/comic/getPageByName",
                data: {
                    pageNum: this.nowPageNum,
                    comicName: this.searchWord
                },
                type: "post",
                success: function (res) {
                    if (res.code == 200) {
                        containerVm.pageInfo = res.data;
                    } else {
                        toastr.error(res.msg);
                    }
                }
            });
        },
        detailModalOpen: function (id) {
            this.isDetailModal = true;
            this.modalTitle = "『详情窗口』";
            this.isInsertModal = false;
            comicInfoThat.detlAndUpdtModalGetVal(id);
        },
        updateModalOpen: function (id) {
            this.isDetailModal = false;
            this.modalTitle = "『修改窗口』";
            this.isInsertModal = false;
            comicInfoThat.detlAndUpdtModalGetVal(id);
        },
        updateContent: function () {
            if (this.comicName == "") {
                toastr.error("番剧名不能为空");
            } else if (this.comicContent.length >= 200) {
                toastr.error("内容不能超过200个字符");
            } else {
                $("#comicForm").ajaxSubmit({
                    url: "/comic/update",
                    type: "post",
                    success: function (res) {
                        $('#comicModal').modal('hide');
                        if (res.code == 200) {
                            toastr.success(res.msg);
                            containerVm.toClickPage(containerVm.nowPageNum);
                        } else if (res.code == 101) {
                            console.log("请不要重复提交");
                        } else {
                            toastr.error(res.msg);
                        }
                    }
                });
            }
        },
        deleteContent: function (id) {
            bootbox.confirm({
                size: "small",
                title: "<i class='fa fa-comment'></i>&nbsp;&nbsp;提示",
                message: "确定要删除吗？",
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
                            url: "/comic/deleteById",
                            data: {
                                id: id
                            },
                            type: "post",
                            success: function (res) {
                                if (res.code == 200) {
                                    toastr.success(res.msg);
                                    containerVm.toClickPage(containerVm.nowPageNum);
                                }
                            }
                        });
                    }
                }
            });
        },
        datePickerBind: function () {
            $(".datepicker").datepicker().on("hide", function () {
                if ($("#comicTime").val() == "") {
                    $("#comicTime").val(containerVm.comicTime);
                }
                containerVm.comicTime = $("#comicTime").val();
            });
        },
        cmImgFileChange: function (e) {
            let file = e.target.files[0];
            let fileName = file.name;
            if (!/.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(fileName)) {
                toastr.error("图片格式必须是.gif,jpeg,jpg,png中的一种");
                this.cmImgFileLb = "示例图片.jpg";
                this.cmImgFilePv = "/images/tpjxz.jpg";
                $("#cmImgFile").val("");
            } else if ((file.size).toFixed(2) >= 102400) {
                toastr.error("图片不能超过100KB");
                this.cmImgFileLb = "示例图片.jpg";
                this.cmImgFilePv = "/images/tpjxz.jpg";
                $("#cmImgFile").val("");
            } else {
                this.cmImgFileLb = fileName;
                let reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function (e) {
                    containerVm.cmImgFilePv = e.target.result;
                }
            }
        },
        modalReset: function () {
            $('#comicModal').on('hidden.bs.modal', function () {
                containerVm.id = 0;
                containerVm.comicName = "";
                containerVm.comicTime = "";
                containerVm.comicContent = "";
                containerVm.comicStatus = 0;
                containerVm.comicUdTime = "";
                containerVm.comicTag = [];
                containerVm.freeLabelA = "";
                containerVm.freeLabelB = "";
                containerVm.freeLabelC = "";
                containerVm.freeLabelD = "";
                containerVm.cmImgFileLb = "示例图片.jpg";
                containerVm.cmImgFilePv = "/images/tpjxz.jpg";
                containerVm.comicLink = "";
                containerVm.comicShelfStatus = 1;
                $(".datepicker").datepicker('clearDates');
                $("#cmImgFile").val("");
            });
        }
    },
    watch: {
        comicStatus: function () {
            this.isUdTimeShow = this.comicStatus == 8;
            if (this.comicStatus == 0) {
                this.comicUdTime = "";
            }
        }
    },
    computed: {
        freeLabelArr: {
            get: function () {
                let tempFreeLabel = [];
                tempFreeLabel.push(this.freeLabelA);
                tempFreeLabel.push(this.freeLabelB);
                tempFreeLabel.push(this.freeLabelC);
                tempFreeLabel.push(this.freeLabelD);
                return tempFreeLabel;
            },
            set: function (value) {
                this.freeLabelA = value[0];
                this.freeLabelB = value[1];
                this.freeLabelC = value[2];
                this.freeLabelD = value[3];
            }
        },
        comicLabel: {
            get: function () {
                return this.comicTag.concat(this.freeLabelArr);
            },
            set: function (value) {
                let labelArr = value.split(",");
                this.comicTag = labelArr.slice(0, labelArr.length - 4);
                let tempFreeLabel = [];
                tempFreeLabel.push(labelArr[labelArr.length - 4]);
                tempFreeLabel.push(labelArr[labelArr.length - 3]);
                tempFreeLabel.push(labelArr[labelArr.length - 2]);
                tempFreeLabel.push(labelArr[labelArr.length - 1]);
                this.freeLabelArr = tempFreeLabel;
            }
        }
    },
    mounted: function () {
        comicInfoThat.initToastr();
        comicInfoThat.initDatePicker();
        comicInfoThat.initScrollUp();
        comicInfoThat.initImgLazyLoad();
        this.datePickerBind();
        this.modalReset();
        this.buildPageComicManage();
    }
});

function detlAndUpdtModalGetVal(id) {
    $.ajax({
        url: "/comic/getOneById",
        data: {
            id: id
        },
        type: "post",
        dataType: 'json',
        success: function (res) {
            if (res.code == 200) {
                containerVm.id = res.data.id;
                containerVm.comicName = res.data.comicName;
                containerVm.comicTime = res.data.comicTime;
                containerVm.comicContent = res.data.comicContent;
                if (res.data.comicStatus == 0) {
                    containerVm.comicStatus = 0;
                } else {
                    containerVm.comicStatus = 8;
                    containerVm.comicUdTime = res.data.comicStatus;
                }
                containerVm.comicLabel = res.data.comicLabel;
                let fileName = res.data.comicImageUrl;
                let fileNewNameStart = fileName.lastIndexOf("/");
                containerVm.cmImgFileLb = fileName.substring(fileNewNameStart + 1);
                containerVm.cmImgFilePv = "/" + res.data.comicImageUrl;
                containerVm.comicLink = res.data.comicLink;
                containerVm.comicShelfStatus = res.data.comicShelfStatus;
                $('#comicModal').modal();
            }
        }
    });
}

Vue.filter("cmStatusFilter", function (value) {
    let arrCmStatus = ['已完结', '周一更新', '周二更新', '周三更新', '周四更新', '周五更新', '周六更新', '周日更新', '更新中'];
    for (let i = 0; i < arrCmStatus.length; i++) {
        if (value == i) {
            return arrCmStatus[i];
        }
    }
});

Vue.filter("cmLabelFilter", function (value) {
    let arrCmLabel = (value).split(",");
    let arrNewCmLabel = [];
    for (let i = 0; i < arrCmLabel.length; i++) {
        if (arrCmLabel[i] != "") {
            arrNewCmLabel.push(arrCmLabel[i]);
        }
    }
    return arrNewCmLabel.toString();
});