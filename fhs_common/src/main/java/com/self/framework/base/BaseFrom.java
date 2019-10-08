package com.self.framework.base;

import com.alibaba.fastjson.JSON;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * @author qiuhang
 * @version v1.0
 * @des 基础前段from交互
 */
public class BaseFrom implements Serializable {
    /** 范围条件 */
    private Map<String, String> between;

    /** 分页条件[页数量] */
    private Integer rows;

    /** 分页条件[第几页] */
    private Integer page;

    /** 排序字段 */
    private String sortFiled;
    
    /**
     * @description json转换方法
     * @author qiuhang
     * @date 2019/9/29/029
     */
    public String asJson() {
        return JSON.toJSONString(this);
    }

    public Map<String, String> getBetween() {
        return between;
    }

    public void setBetween(Map<String, String> between) {
        this.between = between;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSortFiled() {
        return sortFiled;
    }

    public void setSortFiled(String sortFiled) {
        this.sortFiled = sortFiled;
    }

    public BaseFrom() {
    }

    public BaseFrom(String accessTolen, Map<String, String> between, Integer rows, Integer page, String sortFiled) {
        this.between = between;
        this.rows = rows;
        this.page = page;
        this.sortFiled = sortFiled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        BaseFrom baseFrom = (BaseFrom) o;

        if (between != null ? !between.equals(baseFrom.between) : baseFrom.between != null) {return false;}
        if (rows != null ? !rows.equals(baseFrom.rows) : baseFrom.rows != null) {return false;}
        if (page != null ? !page.equals(baseFrom.page) : baseFrom.page != null) {return false;}
        return sortFiled != null ? sortFiled.equals(baseFrom.sortFiled) : baseFrom.sortFiled == null;
    }

    @Override
    public int hashCode() {
        int result = between != null ? between.hashCode() : 0;
        result = 31 * result + (rows != null ? rows.hashCode() : 0);
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (sortFiled != null ? sortFiled.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseFrom{" +
                "between=" + between +
                ", rows=" + rows +
                ", page=" + page +
                ", sortFiled='" + sortFiled + '\'' +
                '}';
    }
}
