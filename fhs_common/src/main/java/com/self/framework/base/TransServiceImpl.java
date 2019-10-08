package com.self.framework.base;

import com.self.framework.annotation.Trans;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.nosql.redis.StringRedisService;
import com.self.framework.spring.extend.ObtainSpringBean;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import com.self.framework.utils.ReflectUtil;
import com.self.framework.utils.StrTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransServiceImpl<T> implements TransService {

    @Autowired
    private StringRedisService redisService;

    private BaseDao<T> baseDao = null;

    @Override
    public void transMore(List listData) {
        listData.forEach(data ->{
            transOne((BaseBean) data);
        });
    }

    @Override
    public void transOne(BaseBean data) {
        boolean isBeTrans = data.getClass().isAnnotationPresent(Trans.class);
        if (isBeTrans){
            Field[] fields = data.getClass().getDeclaredFields();
            for (Field field:fields) {
                Trans trans = field.getAnnotation(Trans.class);
                if (ObjectCheckUtil.checkIsNullOrEmpty(trans)){
                    continue;
                }
                Object filedValue = ReflectUtil.reflectObjObtainFileValue(data, field.getName());
                String redisQueryKey = trans.transKey() + filedValue;
                String value = "";
                if (redisService.isExist(redisQueryKey)){
                    value = redisService.getObj(redisQueryKey);
                } else {
                    String dictQuerySql = "SELECT * FROM " + trans.refDICTName() + " DAC WHERE DAC." + trans.refDICTCondition() + "= :condition ";
                    value = findTransValueFromDB(dictQuerySql, trans.refDICTQuery(), filedValue);
                    if (StrTool.isEmpty(value)) {
                        //防击穿
                        redisService.insertObj(redisQueryKey, 60L, value);
                    } else {
                        redisService.insertObj(redisQueryKey, value);
                    }
                }
                if (null == data.getTransMap()){
                    data.setTransMap(new HashMap<>());
                }
                data.getTransMap().put(field.getName() + "ToTransValue", value);
            }
        }
    }

    @Override
    public void setDao(BaseDao baseDao) {
        if (null == this.baseDao){
            this.baseDao = baseDao;
        }
    }

    /**
     * @description db查询
     * @author qiuhang
     * @date 2019/9/25/025
     */
    private String findTransValueFromDB(String dictQuerySql, String refDICTQuery, Object filedValue){
        if (ObjectCheckUtil.checkIsNullOrEmpty(baseDao)){
            return null;
        }

        try {
            List<Map<String, Object>> fldData = baseDao.findOther(dictQuerySql, new HashMap<String, Object>() {{
                put("condition", filedValue);
            }});
            if (!ObjectCheckUtil.checkIsNullOrEmpty(fldData) && fldData.size() > 0){
                return ConvertDataUtil.convertStr(fldData.get(0).get(refDICTQuery));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
