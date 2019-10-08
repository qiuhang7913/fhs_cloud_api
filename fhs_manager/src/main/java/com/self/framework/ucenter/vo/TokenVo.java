package com.self.framework.ucenter.vo;

import com.self.framework.base.BaseVo;
import com.self.framework.ucenter.bean.SysRole;

import java.util.List;
import java.util.Map;

public class TokenVo extends BaseVo {
    private String loginName;

    private List<Map> sysRes;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<Map> getSysRes() {
        return sysRes;
    }

    public void setSysRes(List<Map> sysRes) {
        this.sysRes = sysRes;
    }
}
