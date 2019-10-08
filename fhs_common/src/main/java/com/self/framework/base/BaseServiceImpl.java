package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.spring.extend.jpa.SpecificationQueryExtend;
import com.self.framework.utils.ObjectCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @des 基础service实现
 * @author qiuhang
 * @version v1.0
 * @param <T>
 */
public class BaseServiceImpl<T extends BaseBean> implements BaseService<T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BaseDao<T> baseDao;

    @Autowired
    private SpecificationQueryExtend<T> querySqlBuild;

    @Autowired
    private TransService<T> transService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdata(T v) {
        return ObjectCheckUtil.checkIsNullOrEmpty(saveCurrData(v)) ? 0 : 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T addOrUpdataReturn(T v) {
        return saveCurrData(v);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> ids) {
        try {
            List<T> allById = baseDao.findAllById(ids);
            baseDao.deleteInBatch(allById);
        } catch (Exception e){
            logger.debug("删除异常！参数{}，异常信息{}\n",JSON.toJSONString(ids), e.getMessage());
        }
    }

    @Override
    public Page<T> queryListHasPagingAndSort(T v, Integer pageStart, Integer pageEnd, Integer sortType, String ... sortFiled) {
        Sort sort = new Sort(sortType.equals(BusinessCommonConstamt.ZERO_CODE) ? Sort.Direction.DESC : Sort.Direction.ASC, sortFiled);
        PageRequest of = PageRequest.of(pageStart, pageEnd, sort);

        try {
            Page<T> all = baseDao.findAll(querySqlBuild.getWhereClause(v), of);
            transService.setDao(baseDao);
            transService.transMore(all.getContent());
            return all;
        }catch (Exception e){
            logger.debug("查询异常!参数{}，异常信息{}\n",JSON.toJSONString(v), e.getMessage());
            return null;
        }
    }

    @Override
    public Page<T> queryListHasPaging(T v, Integer pageStart, Integer pageEnd) {
        return this.queryListHasPagingAndSort(v, pageStart, pageEnd, DEFAULT_SORT_TYPE, DEFAULT_SORT_FILE);
    }

    @Override
    public List<T> queryList(T v) {
        if (ObjectCheckUtil.checkIsNullOrEmpty(v)){
            return new ArrayList<>();
        }
        try {
            List<T> rvDataList = baseDao.findAll(querySqlBuild.getWhereClause(v));
            transService.setDao(baseDao);
            transService.transMore(rvDataList);
            return rvDataList;
        }catch (Exception e){
            logger.debug("查询异常！参数{}，异常信息{}\n",JSON.toJSONString(v), e.getMessage());
            return null;
        }

    }

    @Override
    public T findOne(T v) {
        Example<T> of = Example.of(v);
        Optional<T> one = baseDao.findOne(of);
        if (one.isPresent()){
            transService.setDao(baseDao);
            transService.transOne(one.get());
            return one.get();
        }
        logger.debug("未找到该数据,查询参数为{}\n",JSON.toJSONString(v));
        return null;
    }

    @Override
    public T findOneById(String id) {
        Optional<T> byId = baseDao.findById(id);
        if (byId.isPresent()){
            transService.setDao(baseDao);
            transService.transOne(byId.get());
            return byId.get();
        }
        logger.debug("未找到该数据,查询参数为{}\n",id);
        return null;
    }

    /**
     *
     * @return T
     */
    private T saveCurrData(T v){
        T one = findOne(v);
        String userName = "";
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if (!ObjectCheckUtil.checkIsNullOrEmpty(authentication)){
        //     UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //     userName = userDetails.getUsername();
        // }

        // if (ObjectCheckUtil.checkIsNullOrEmpty(one)){
        //     v.setCreateTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3));
        //     v.setCreateUser(userName);
        // }else{
        //     v.setCreateTime(one.getCreateTime());
        //     v.setCreateUser(one.getCreateUser());
        //     v.setUpdateTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3));
        //     v.setUpdateUser(userName);
        // }
        return baseDao.saveAndFlush(v);
    }
}
