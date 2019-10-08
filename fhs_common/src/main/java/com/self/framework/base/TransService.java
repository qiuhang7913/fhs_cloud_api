package com.self.framework.base;

import java.util.List;

/**
 * @des 翻译service
 */
public interface TransService<T extends BaseBean>{
    /**
     * 列表翻译
     * @return
     */
    void transMore(List<T> listData);

    /**
     * 单个翻译
     * @return
     */
    void transOne(T data);
    
    /**
     * @description dao传入
     * @author qiuhang
     * @date 2019/9/25/025
     */
    void setDao(BaseDao<T> baseDao);
}
