package com.self.framework.exception;

import com.self.framework.cenum.HttpResultEnum;

/**
 * @description 自定义全局业务错误返回
 * @author qiuhang
 * @date 2019/9/25/025
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpResultEnum httpResultEnum;

    private Object otherObjResult;

    public BusinessException(HttpResultEnum httpResultEnum) {
        super(httpResultEnum.getMessage());
        this.httpResultEnum = httpResultEnum;
    }

    public BusinessException(HttpResultEnum httpResultEnum, Object otherObjResult) {
        super(httpResultEnum.getMessage());
        this.httpResultEnum = httpResultEnum;
        this.otherObjResult = otherObjResult;
    }

    public HttpResultEnum getHttpResultEnum() {
        return httpResultEnum;
    }

    public void setHttpResultEnum(HttpResultEnum httpResultEnum) {
        this.httpResultEnum = httpResultEnum;
    }

    public Object getOtherObjResult() {
        return otherObjResult;
    }

    public void setOtherObjResult(Object otherObjResult) {
        this.otherObjResult = otherObjResult;
    }
}
