package com.self.framework.ucenter.service;

import com.self.framework.base.BaseService;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.bean.SysRole;

import java.util.List;

public interface RoleService extends BaseService<SysRole> {

    /**
     * 查看当前角色下的菜单信息
     * @description：
     * @author 李阳
     * @date 2019/9/11/011
     */

    List<String> findSysRoleSysMenuFuncIds(String roleId, String menuId);

}
