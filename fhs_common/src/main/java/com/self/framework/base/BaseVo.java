package com.self.framework.base;

import java.io.Serializable;
import java.util.Map;

/**
 * @description BaseVo 基础rest 接口返回
 * @author qiuhang
 * @date 2019/9/29/029
 */
public class BaseVo implements Serializable {

    /** 鉴权token */
    private String accessToken;

    private String refreshToken;

    /** 鉴权token失效时间 */
    private String accessTokenExpireTime;

    /** 内部错误描述 */
    private String errorMsg;

    /** 内部错误码 */
    private Integer errorCode;

    /** 字典翻译值 */
    private Map<String,Object> transMap;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    public Map<String, Object> getTransMap() {
        return transMap;
    }

    public void setTransMap(Map<String, Object> transMap) {
        this.transMap = transMap;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseVo vo = (BaseVo) o;

        if (accessToken != null ? !accessToken.equals(vo.accessToken) : vo.accessToken != null) return false;
        if (refreshToken != null ? !refreshToken.equals(vo.refreshToken) : vo.refreshToken != null) return false;
        if (accessTokenExpireTime != null ? !accessTokenExpireTime.equals(vo.accessTokenExpireTime) : vo.accessTokenExpireTime != null)
            return false;
        if (errorMsg != null ? !errorMsg.equals(vo.errorMsg) : vo.errorMsg != null) return false;
        if (errorCode != null ? !errorCode.equals(vo.errorCode) : vo.errorCode != null) return false;
        return transMap != null ? transMap.equals(vo.transMap) : vo.transMap == null;
    }

    @Override
    public int hashCode() {
        int result = accessToken != null ? accessToken.hashCode() : 0;
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        result = 31 * result + (accessTokenExpireTime != null ? accessTokenExpireTime.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (transMap != null ? transMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseVo{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessTokenExpireTime='" + accessTokenExpireTime + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
                ", transMap=" + transMap +
                '}';
    }
}
