package com.self.framework.base;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @description spring data jpa 持久层dao 公共基类(接口)
 *      当前所有库表主键类型都为string
 * @param <T>
 * @author qiuhang
 */
@NoRepositoryBean
public interface BaseDao<T>
        extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
    /**
     * @description 定义sql文查询 map返回
     * @author qiuhang
     * @date 2019/10/8/008
     */
    List<Map<String, Object>> findOther(String sql, Map<String,Object> param);

    /**
     * @description 定义sql文查询 bean返回
     * @author qiuhang
     * @date 2019/10/8/008
     */
    <Z> List<Z> findOther(String sql, Map<String,Object> param, Class<Z> zClass);

    boolean insertOther(BaseBean baseBean);
}
