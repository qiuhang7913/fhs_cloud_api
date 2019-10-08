package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description spring data jpa 持久层dao 公共基类(实现)
 * @author qiuhang
 * @date 2019/10/8/008
 */
public class BaseDaoImpl<T>
        extends SimpleJpaRepository<T, String> implements BaseDao<T>  {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Class<T> domainClass;
    private EntityManager entityManager;

    public BaseDaoImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }

    @Override
    public List<Map<String, Object>> findOther(String sql, Map<String,Object> param) {
        Query query = entityManager.createNativeQuery(sql);
        param.entrySet().forEach(entry -> {
            query.setParameter(entry.getKey(),entry.getValue());
        });

        // Query 接口是 spring-data-jpa 的接口，而 SQLQuery 接口是 hibenate 的接口，这里的做法就是先转成 hibenate 的查询接口对象，然后设置结果转换器
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    @Override
    public <Z> List<Z> findOther(String sql, Map<String,Object> param, Class<Z> zClass) {
        List<Map<String, Object>> other = findOther(sql, param);
        List<Z> resultList = new ArrayList<>();
        other.forEach(obj -> {
            resultList.add(JSON.parseObject(JSON.toJSONString(obj), zClass));
        });
        return resultList;
    }

    @Override
    public boolean insertOther(BaseBean baseBean) {
        try {
            entityManager.persist(baseBean);
            return true;
        }catch (Exception e){
            logger.error("数据入库异常!",e);
        }
        return false;
    }
}
