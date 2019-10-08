package com.self.framework.http;

import com.self.framework.base.BaseReult;
import com.self.framework.cenum.HttpResultEnum;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @des http请求返回结果
 * @author qiuhang
 * @version v.1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult<T> extends BaseReult {

    private T result;

    public static HttpResult<Map> okResult(){
        HttpResult<Map> httpResult = new HttpResult<>();
        httpResult.setCode(HttpResultEnum.SUCCESS.getCode());
        httpResult.setDescribe(HttpResultEnum.SUCCESS.getMessage());
        httpResult.setResult(new HashMap<>());
        return httpResult;
    }

    public static <V> HttpResult<V> okOtherDataResult(V obj){
        HttpResult<V> httpResult = new HttpResult<>();
        httpResult.setCode(HttpResultEnum.SUCCESS.getCode());
        httpResult.setDescribe(HttpResultEnum.SUCCESS.getMessage());
        httpResult.setResult(obj);
        return httpResult;
    }

    public static HttpResult<Map> errorResult(){
        return errorOtherResult(HttpResultEnum.FAILURE);
    }

    public static <V> HttpResult<V> errorOtherObjResult(V obj){
        HttpResult<V> httpResult = new HttpResult<>();
        httpResult.setCode(HttpResultEnum.FAILURE.getCode());
        httpResult.setDescribe(HttpResultEnum.FAILURE.getMessage());
        httpResult.setResult(obj);
        return httpResult;
    }


    public static HttpResult<Map> errorOtherResult(HttpResultEnum httpResultEnum){
        HttpResult<Map> httpResult = new HttpResult<>();
        httpResult.setCode(httpResultEnum.getCode());
        httpResult.setDescribe(httpResultEnum.getMessage());
        httpResult.setResult(new HashMap<>());
        return httpResult;
    }

    public static <V> HttpResult<V> aOtherResult(HttpResultEnum httpResultEnum, V obj){
        HttpResult<V> httpResult = new HttpResult<>();
        httpResult.setCode(httpResultEnum.getCode());
        httpResult.setDescribe(httpResultEnum.getMessage());
        httpResult.setResult(obj);
        return httpResult;
    }
}
