package com.self.framework.base;

import org.springframework.beans.BeanUtils;

/**
 * @author qiuhang
 * @desc business层规范定义基类(实现)
 * @date 2019/9/25/025
 */
public class BaseBusinessImpl<F extends BaseFrom, V extends BaseVo, B extends BaseBean> implements BaseBusiness<F, V, B> {

    @Override
    public void exchangeFromToBean(F from, B bean) {
        BeanUtils.copyProperties(from, bean);
    }

    @Override
    public void exchangeBeanToVo(V vo, B bean) {
        BeanUtils.copyProperties(bean, vo);
    }
}
