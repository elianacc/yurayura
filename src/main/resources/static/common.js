/**
 * common.js
 * 通用jQuery插件init方法,jQuery插件封装方法，字典获取方法
 * @author EliaNaCc
 * @since 2019-11-06
 */
const init = {
    // toast初始化方法
    initToastr() {
        toastr.options = {
            closeButton: false, // 是否显示关闭按钮
            debug: false,
            progressBar: false, // 是否显示进度条
            positionClass: 'toast-top-center', // 位置
            showDuration: '300', // 显示的动画时间
            hideDuration: '1000', // 消失的动画时间
            timeOut: '4000', // 展现时间
            extendedTimeOut: '0', // 加长展示时间
            showEasing: 'swing', // 显示时的动画缓冲方式
            hideEasing: 'linear', // 消失时的动画缓冲方式
            showMethod: 'fadeIn', // 显示时的动画方式
            hideMethod: 'fadeOut' // 消失时的动画方式
        };
    },
    // BootSideMenu初始化方法
    initSideMenu(obj) {
        $(obj).BootSideMenu({
            side: 'left',
            autoClose: false
        });
    },
    // metisMenu初始化方法
    initMetisMenu(obj) {
        $(obj).metisMenu({toggle: false});
    },
    // bootstrap-datepicker初始化方法
    initDatePicker(obj) {
        $(obj).datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'zh-CN',
            todayBtn: 'linked',
            clearBtn: true,
            weekStart: 1,
            minViewMode: 'month',
            todayHighlight: true
        });
    },
    // jquery scrollUp初始化方法
    initScrollUp() {
        $.scrollUp({
            scrollName: 'scrollUp', // 对象 ID
            scrollDistance: '300', // 距离顶部多远时显示对象 (px)
            scrollSpeed: 300, // 返回顶部速度 (ms)
            animation: 'fade', // 按钮出现、隐藏的方式 Fade, slide, none 动画效果
            animationSpeed: 200, // 动画速度 (ms)
            scrollText: '', // 对象文本
            activeOverlay: false //是否显示参考线
        });
    },
    // jquery图片懒加载初始化方法
    initImgLazyLoad() {
        $('img').unveil(100);
    }
};

const bootBox = {
    // bootbox alert封装方法(无返回事件)
    alert(msg) {
        bootbox.alert({
            size: 'small',
            title: "<span class='font-size-16'><i class='fa fa-info-circle fa-lg'></i>&nbsp;&nbsp;提示</span>",
            message: msg,
            closeButton: false,
            buttons: {
                ok: {
                    label: '确定',
                }
            }
        });
        return false;
    },
    // bootbox alert封装方法(自定义返回事件)
    alertWithCall(msg, callbackFunction) {
        bootbox.alert({
            size: 'small',
            title: "<span class='font-size-16'><i class='fa fa-info-circle fa-lg'></i>&nbsp;&nbsp;提示</span>",
            message: msg,
            closeButton: false,
            buttons: {
                ok: {
                    label: '确定',
                }
            },
            callback() {
                callbackFunction();
            }
        });
    },
    // bootbox confirm封装方法
    confirm(msg, callbackFunction) {
        bootbox.confirm({
            size: 'small',
            title: "<span class='font-size-16'><i class='fa fa-info-circle fa-lg'></i>&nbsp;&nbsp;提示</span>",
            message: msg,
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
            callback(result) {
                if (result == true) {
                    callbackFunction();
                }
            }
        });
    }
};

const datePicker = {
    // 设置date-picker选中日期封装方法
    setDate(obj, val) {
        $(obj).datepicker('setDate', val);
    },
    // 清空date-picker选中日期封装方法
    clearDate(obj) {
        $(obj).datepicker('clearDates');
    }
};

const sysDict = {
    // 字典获取方法
    get(dictCode) {
        let dictData = '';
        $.ajax({
            url: '/sys/dict/getByDictCode',
            data: {
                dictCode: dictCode
            },
            type: 'post',
            dataType: 'json',
            async: false,
            success(res) {
                if (res.code == 200) {
                    dictData = res.data;
                } else if (res.code == 100) {
                    console.log(res.msg);
                } else if (res.code == 500) {
                    console.log('字典编码：' + dictCode + '对应系统数据字典获取异常');
                }
            }
        });
        return dictData;
    }
};

const imgVerificat = {
    // 常见图片合法性验证
    confirm(imgFile, imgLimit) {
        if (!/.(gif|jpg|jpeg|png|GIF|JPG|JPEG|PNG)$/.test(imgFile.name)) {
            toastr.warning('图片格式必须是.gif,jpeg,jpg,png中的一种');
            return false;
        } else if ((imgFile.size).toFixed(2) >= imgLimit) {
            toastr.warning('图片不能超过' + imgLimit / 1024 + 'KB');
            return false;
        }
        return true;
    }
};

export {init, bootBox, datePicker, sysDict, imgVerificat};