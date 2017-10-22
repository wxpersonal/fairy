package me.weix.fairy.pojo.vo;

/**
 * @Author: WeiX
 * @Date: 2017/10/21
 * @description :
 */
public class AjaxResult {

    private boolean success;
    private String message;
    private Object data;

    public AjaxResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
