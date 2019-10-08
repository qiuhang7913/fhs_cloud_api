package com.self.framework.ucenter.service;

import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.bean.SysMenuResourceFunc;
import com.self.framework.ucenter.bean.SysRole;
import com.self.framework.ucenter.bean.SysUser;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @des: spring security 核心认证授权
 * @author qiuhang
 * @version v1.0
 */
@Component
public class CustomUserDetailsService implements UserDetailsService  {

    @Autowired
    private UserSevice userSevice;

    @Autowired
    private  RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {

        //--------------------认证账号
        SysUser sysUser = userSevice.loadUserByLoginName(loginName);
        if (sysUser == null) {
            throw new UsernameNotFoundException("账号不存在");
        }

        //-------------------开始授权
        // 可用性 :true:可用 false:不可用
        boolean enabled = true;
        // 过期性 :true:没过期 false:过期
        boolean accountNonExpired = true;
        // 有效性 :true:凭证有效 false:凭证无效
        boolean credentialsNonExpired = true;
        // 锁定性 :true:未锁定 false:已锁定
        boolean accountNonLocked = true;

        User user = new User(sysUser.getLoginName(), sysUser.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, obtainUserAuths(sysUser));
        return user;
    }

    /**
     * 赋值权限
     * @param sysUser
     */
    private List<GrantedAuthority> obtainUserAuths(SysUser sysUser){
        List<GrantedAuthority> auths = new ArrayList<>();
        Set<String> authResources = new HashSet<>();
        List<SysRole> userRoleBeans = sysUser.getUserRoles();

        if (sysUser.getType().equals(BusinessCommonConstamt.TOW_CODE)){
            authResources.add("super");
        }else{
            List<SysRole> currUserRoles = sysUser.getUserRoles();
            currUserRoles.forEach(sysRole -> {
                sysRole.getSysMenuResources().forEach(sysMenuResource -> {
                    List<String> currSysRoleSysMenuFuncIds = roleService.findSysRoleSysMenuFuncIds(sysRole.getId(), sysMenuResource.getId());
                    List<SysMenuResourceFunc> sysMenuResourceFuncs = sysMenuResource.getSysMenuResourceFuncs();
                    String name = sysMenuResource.getNameSpace();
                    sysMenuResourceFuncs.forEach(sysMenuResourceFunc -> {
                        if(currSysRoleSysMenuFuncIds.contains(sysMenuResourceFunc.getId())){
                        String authFlag = name + ":";
                        Integer funcType = sysMenuResourceFunc.getFuncType();
                        if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_CODE.intValue()){
                            authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_FLAG;
                        }else if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_UPDATE_CODE.intValue()){
                            authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_UPDATE_FLAG;
                        }else if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_CODE.intValue()){
                            authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_FLAG;
                        }else{
                            authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_FIND_FLAG;
                        }
                        authResources.add(authFlag);
                        }
                    });
                });
            });

        }
        authResources.forEach( authResource -> {
            auths.add(new SimpleGrantedAuthority(authResource));
        });
        return auths;
    }
}
