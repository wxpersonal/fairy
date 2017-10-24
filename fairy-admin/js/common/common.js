var common = {
    /**通用ajax表单提交方法
     * @param urles 链接
     * @param formnames 表单名称
     * @param async 是否异步
     * @param localdata 回传数据
     */
    commonAjaxFormSubmit: function (url, formName, functionName, isasync, localdata) {
        $.ajax({
            url: url,
            type: "post",
            cache: false,
            async: isasync,
            beforeSend: function (XMLHttpRequest) {
                XMLHttpRequest.setRequestHeader("formtype", "jqueryajax"); // 调用本次AJAX请求时传递的options参数,解决中文乱码问题
            },
            contentType: "application/json",
            processData: true,
            dataType: "json",
            data: $(formName).serialize(),
            localdata: localdata,
            callback_function: functionName,
            success: common.commonAjaxSuccess,
            error: common.commonAjaxError
        });
    },

    /**
     * 通用ajax方法提交
     * @param url 链接
     * @param data 传入的数据，为json格式
     * @param method 提交方法，get或者post
     * @param isasync 是否异步
     * @param localdata 回传数据
     */
    commonAjaxSubmit: function (url, data, method, functionName, isasync, localdata) {
        $.ajax({
            url: url,
            type: method,
            cache: false,
            timeout: 600000,
            async: isasync,
            beforeSend: function (XMLHttpRequest) {
                XMLHttpRequest.setRequestHeader("formtype", "jqueryajax"); // 调用本次AJAX请求时传递的options参数,解决中文乱码问题
            },
            contentType: "application/json",
            processData: true,
            dataType: "json",
            data: JSON.stringify(data),
            localdata: localdata,
            callback_function: functionName,
            success: common.commonAjaxSuccess,
            error: common.commonAjaxError
        });
    },

    /**
     * ajax 请求正确相应的后续调用
     */
    commonAjaxSuccess: function (data) {
        // data 可能是 xmlDoc, jsonObj, html, text, 等等...
        if (data.s) {
            if ("function" == eval("typeof(success_" + this.callback_function + ")"))
                if (typeof(this.localdata) == "undefined") {
                    eval("success_" + this.callback_function + "(data);");
                } else {
                    var localdata = this.localdata
                    eval("success_" + this.callback_function + "(data,localdata);");
                }
            else {
                //默认刷新
                if (null != data.u)
                    self.location.href = data.u;
                else {
                    window.location.reload();
                }
            }
        }
        else {
            errorMessage(data.m);
            if ("function" == eval("typeof(fail_" + this.bpjasmine_function + ")"))
                eval("fail_" + this.bpjasmine_function + "(data);");
        }
    },


    /**
     * ajax 请求相应出错的处理
     */
    commonAjaxError: function (XMLHttpRequest, textStatus, errorThrown) {
        if ("timeout" == textStatus)
            errorMessage("服务器正忙，响应超时，请联系系统管理员或重试！");
        else if ("error" == textStatus)
            errorMessage("连接服务器出错，请检查网络或者服务器可能已经停止服务，请联系系统管理员或重试！");
        else
            errorMessage("未知原因(" + textStatus + ")，请联系系统管理员或重试！");
    },

    /**
     * checkbox全选操作
     * @param obj 全选按钮对象
     * @param chbName 单向选择框的name
     */
    allChecked: function (obj, chbName) {
        var flag = obj.checked;
        $("input[type='checkbox'][name='" + chbName + "']").prop("checked", flag);
    },

    /**
     * 校验手机号
     * @param phone
     * @returns {Boolean}
     */
    checkMobile: function (phone) {
        var flag = true;
        var re = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if (!re.test(phone)) {
            errorMessage("请输入正确的手机号！");
            flag = false;
        }
        return flag;
    },

    /**
     * 校验身份证号
     * @param data
     * @returns {Boolean}
     */
    checkIdentityID: function (data) {
        var flag = true;
        var re = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (!re.test(data)) {
            errorMessage("请输入正确的身份证号！");
            flag = false;
        }
        return flag;
    },
    trimall: function (strValue) {
        var spacei = 0;
        var intLen;
        var frontSpaceNum = 0;
        var endSpaceNum = 0;
        if (strValue.length == 0) return "";
        else {
            intLen = strValue.length;
            for (spacei = 0; spacei < intLen; spacei++) {        // 去除前面的空格、回车、换行符
                if (strValue.charAt(spacei) == " " || strValue.charAt(spacei) == "\n" || strValue.charAt(spacei) == "\r") {
                    frontSpaceNum++;
                }
                else
                    break;
            }

            for (spacei = 1; spacei < intLen; spacei++) {        // 去除后面的空格、回车、换行符
                if (strValue.charAt(intLen - spacei) == " " || strValue.charAt(intLen - spacei) == "\n" || strValue.charAt(intLen - spacei) == "\r") {
                    endSpaceNum++;
                }
                else
                    break;
            }
            if (frontSpaceNum == intLen) {
                return "";
            }
            else {
                strValue = strValue.substring(frontSpaceNum, strValue.length - endSpaceNum);
                return strValue;
            }
        }

    }
}




