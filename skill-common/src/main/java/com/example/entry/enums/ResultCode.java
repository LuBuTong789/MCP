package com.example.entry.enums;

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限"),
    ERROR(500, "服务器异常");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    // get/set
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}