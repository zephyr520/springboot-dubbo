package com.zephyr.common.web;

/**
 * Created by Administrator on 2018/8/16.
 * @author Administrator
 */
public enum ApiResultCode {

    NO_LOGIN(101, "没有登录或会话超时"),
    NO_AUTH(102, "没有访问权限"),
    TIMEOUT_ACCESS(103, "访问超时"),
    TOKEN_INVALID(104, "非法Token"),
    TOKEN_EXPIRE(105, "Token过期"),
    ILLEGAL_ACCESS(106, "非法访问"),


    SUCCESS(200, "success"),
    NO_DATA(201, "未查找到数据"),
    PWD_ERROR(202, "用户名或密码错误"),
    MOBILE_ERROR(203, "手机号格式错误"),
    NO_ROLE(204, "用户未获得系统角色"),
    EXIST_DATA(205, "数据已存在"),
    OLD_PWD_ERROR(207, "原始密码错误"),

    PARAM_ERROR(300, "请求参数错误"),
    PARAM_EMPTY(301, "请求参数不能为空"),
    
    DELETE_FORBID(401, "系统初始数据禁止删除"),

    SERVICE_ERROR(500, "哎呀，系统出错了"),

    CUSTOMIZE_ERROR(700,"自定义错误"),
    ;

    private final int code;
    private String message;

    ApiResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
