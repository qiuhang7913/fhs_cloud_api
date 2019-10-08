package com.self.framework.cenum;

import lombok.Getter;

/**
 * @description 统一的http返回枚举类
 * @author qiuhang
 * @date 2019/9/25/025
 */
@Getter
public enum HttpResultEnum {
    
    /** */
    SUCCESS(200,"成功"),

    /** */
    FAILURE(400,"失败"),

    /** */
    USER_NEED_AUTHORITIES(201,"用户未登录"),

    /** */
    USER_LOGIN_FAILED(202,"用户账号或密码错误"),

    /** */
    USER_ACCESS_TOKEN_FAILED(203,"获取access_token失败!"),

    /** */
    USER_NO_ACCESS(204,"用户无权访问"),

    /** */
    USER_LOGOUT_SUCCESS(205,"用户登出成功"),

    /** */
    TOKEN_IS_BLACKLIST(206,"此token为黑名单"),

    /** */
    LOGIN_IS_OVERDUE(207,"登录已失效"),

    /** */
    ERROR_CALL(208,"错误的调用"),

    /** */
    SYSTEM_RRROR(500,"内部系统报错"),

    /** */
    REQUEST_PARAM_RRROR(501,"请求参数校验失败"),

    /** */
    REQUEST_PARAM_CONVERT_RRROR(502,"请求参数类型转换失败"),

    /** */
    REQUEST_METHOD_TYPE_RRROR(503,"请求方法类型错误"),

    /** */
    DATA_FIND_RRROR(1001,"数据查询错误,可能数据不存在"),

    /** */
    DATA_FILE_DOWNLOAD_RRROR(1002,"数据文件导出下载异常"),
    ;

    private Integer code;

    private String message;

    HttpResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @description 通过code返回枚举
     * @author qiuhang
     * @date 2019/9/25/025
     */
    public static HttpResultEnum parse(int code){
        HttpResultEnum[] values = values();
        for (HttpResultEnum value : values) {
            if(value.getCode() == code){
                return value;
            }
        }
        throw  new RuntimeException("Unknown code of ResultEnum");
    }
}
