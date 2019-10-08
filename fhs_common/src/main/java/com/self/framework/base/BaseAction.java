package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import com.self.framework.annotation.SysLog;
import com.self.framework.cenum.HttpResultEnum;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.exception.BusinessException;
import com.self.framework.http.HttpResult;
import com.self.framework.http.PageResult;
import com.self.framework.utils.ObjectCheckUtil;
import com.self.framework.utils.StrTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @des 公共 crud action
 * @author qiuhang
 * @version v1.0
 */

public class BaseAction<T extends BaseBean> extends SuperAction {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** service 操作 */
    @Autowired
    private BaseService<T> baseService;

    /** hibernate校验器 */
    @Autowired
    private Validator validator;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping(value = "addOrUpdate", method = {RequestMethod.POST})
    @SysLog
    @ResponseBody
    public HttpResult<Map> addOrUpdate(@RequestBody String obj){
        if (!isPermission(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_FLAG)){
            logger.debug("当前用户没有添加/修改权限!");
            throw new BusinessException(HttpResultEnum.USER_NO_ACCESS);
        }
        T t = this.transformationRequestParam(obj, true);
        Integer addCode = baseService.addOrUpdata(t);
        if (addCode.equals(BusinessCommonConstamt.ZERO_CODE)){
            return HttpResult.errorResult();
        }
        return HttpResult.okResult();
    }

    @RequestMapping(value = "obtainOne/{id}", method = {RequestMethod.GET})
    @SysLog(logOptType = BusinessCommonConstamt.TOW_STRING_CODE)
    @ResponseBody
    public HttpResult<T> obtainOne(@PathVariable(value = "id" ) String id){
        if (!isPermission(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_FIND_FLAG)){
            logger.debug("当前用户没有查看权限!");
            throw new BusinessException(HttpResultEnum.USER_NO_ACCESS);
        }
        T one = baseService.findOneById(id);
        if (!ObjectCheckUtil.checkIsNullOrEmpty(one)){
            return HttpResult.okOtherDataResult(one);
        }
        return HttpResult.errorOtherObjResult(one);
    }

    /**
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "del", method = {RequestMethod.DELETE})
    @SysLog(logOptType = BusinessCommonConstamt.THREE_STRING_CODE)
    @ResponseBody
    public HttpResult<Map> delete(@RequestBody List<String> ids){
        if (!isPermission(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_FLAG)){
            logger.debug("当前用户没有删除权限!");
            throw new BusinessException(HttpResultEnum.USER_NO_ACCESS);
        }

        try {
            baseService.delete(ids);
        }catch (Exception e){
            logger.debug(e.getMessage());
            throw new BusinessException(HttpResultEnum.FAILURE);
        }
        return HttpResult.okResult();
    }

    @RequestMapping(value = "list", method = {RequestMethod.POST})
    @SysLog(logOptType = BusinessCommonConstamt.TOW_STRING_CODE)
    @ResponseBody
    public PageResult<T> list(@RequestBody String obj , HttpServletRequest request){
        if (!isPermission(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_FIND_FLAG)){
            logger.debug("当前用户没有查询权限!");
            throw new BusinessException(HttpResultEnum.USER_NO_ACCESS);
        }

        //手动序列化当前类
        T t = this.transformationRequestParam(obj, true);

        try {
            Page<T> pageData = baseService.queryListHasPagingAndSort(t, t.getPage()-1, t.getRows(), t.getSortOrder(), t.getSortFiled());

            if (!ObjectCheckUtil.checkIsNullOrEmpty(pageData)){
                return PageResult.pageResult(HttpResultEnum.SUCCESS, pageData.getContent(), pageData.getTotalElements());
            }
        }catch (Exception e){
            logger.error("异常!",e);
            throw new BusinessException(HttpResultEnum.FAILURE, e.getMessage());
        }
        return PageResult.pageResult(HttpResultEnum.DATA_FIND_RRROR,null,null);
    }
    /**
     * bean手动转化于校验
     * @param requestParam
     * @param needViolation
     * @return
     */
    private T transformationRequestParam(String requestParam, boolean needViolation){
        ParameterizedType ptClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        //获取当前泛型类型
        Type actualTypeArgument = ptClass.getActualTypeArguments()[0];
        //手动序列化当前类
        T t = JSON.parseObject(requestParam, actualTypeArgument);

        Set<ConstraintViolation<T>> violationSet = validator.validate(t);
        if (needViolation){
            //校验不通过
            if (violationSet.size() > BusinessCommonConstamt.ZERO_CODE){
                List<String> errorValidMsg = new ArrayList<>();
                for (ConstraintViolation<T> item : violationSet) {
                    errorValidMsg.add(item.getPropertyPath() + "字段" + item.getMessage());
                }
                throw new BusinessException(HttpResultEnum.REQUEST_PARAM_RRROR,    StrTool.join(errorValidMsg,","));
            }
        }
        return t;
    }

    private boolean isPermission(String action){
        RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
        String nameSpace = requestMapping.value()[0];
        // UserDetails sessionUserInfo = this.getSessionUserInfo();
        //
        // SimpleGrantedAuthority superAuth = new SimpleGrantedAuthority("super");
        // if (sessionUserInfo.getAuthorities().contains(superAuth)){
        //     return true;
        // }
        // return sessionUserInfo.getAuthorities().contains(new SimpleGrantedAuthority(nameSpace + ":" + action));
        return true;
    }


    protected boolean tokenIsRefresh(){
        String authHeaderReq = request.getHeader("Authorization");
        String authHeaderRes = response.getHeader("Authorization");
        if (!authHeaderReq.equals(authHeaderRes)){
            return true;
        }
        return false;
    }

    protected String obtainAuthToken(){
        String authHeaderRes = response.getHeader("Authorization");
        return authHeaderRes.substring(BusinessCommonConstamt.AUTH_TOKEN_PERFIX.length());
    }
}
