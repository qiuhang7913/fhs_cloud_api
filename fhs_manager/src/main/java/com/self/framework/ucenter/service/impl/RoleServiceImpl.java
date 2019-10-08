package com.self.framework.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.self.framework.base.BaseServiceImpl;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysRole;
import com.self.framework.ucenter.dao.RoleDao;
import com.self.framework.ucenter.service.RoleService;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends BaseServiceImpl<SysRole> implements RoleService {

    @Autowired
    public RoleDao roleDao;

    public HttpResult<Map> obtainCurrRoleAllRes(){
        return HttpResult.okResult();
    }

    @Override
    public List<String> findSysRoleSysMenuFuncIds(String roleId, String menuId) {
        String querySql = "SELECT RM.FUNC_IDS FROM ROLE_MENU RM WHERE RM.ROLE_ID = :roleId AND RM.MENU_ID = :menuId ";
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("roleId", roleId);
        mapParam.put("menuId", menuId);
        List<Map<String, Object>> dbQueryRvData = roleDao.findOther(querySql,mapParam);

        if (!ObjectCheckUtil.checkIsNullOrEmpty(dbQueryRvData)){
            String funcIds = ConvertDataUtil.convertStr(dbQueryRvData.get(0).get("FUNC_IDS"));
            List<Map> fundIdMap = JSON.parseArray(funcIds, Map.class);
            List<String> findIdStrs = new ArrayList<>();
            fundIdMap.forEach(map -> {
                findIdStrs.add(ConvertDataUtil.convertStr (map.get("id")));
            });
            return findIdStrs;
        }

        return null;
    }
}
