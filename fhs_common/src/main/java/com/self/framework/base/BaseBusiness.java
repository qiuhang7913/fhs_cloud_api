package com.self.framework.base;

/**
 * @author qiuhang
 * @desc business层规范定义基类(接口)
 * @date 2019/9/25/025
 */
public interface BaseBusiness<F extends BaseFrom, V extends BaseVo, B extends BaseBean> {
    
    /**
     * @description from -> bean
     * @author qiuhang
     * @date 2019/9/25/025
     */
    void exchangeFromToBean(F from, B bean);

    /**
     * @description bean -> vo
     * @author qiuhang
     * @date 2019/9/25/025
     */
    void exchangeBeanToVo(V vo, B bean);
}
