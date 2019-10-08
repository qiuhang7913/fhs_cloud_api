package com.self.framework.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * @des 基础db从持久层 bean
 * @author qiuhang
 * @version v1.0
 */
public class BaseBean implements Serializable {


    // @Column(name = "create_time")
    // private String createTime;
    //
    // @Column(name = "create_user")
    // private String createUser;
    //
    // @Column(name = "update_time")
    // private String updateTime;
    //
    // @Column(name = "update_user")
    // private String updateUser;

    private Map<String,String> between;

    private Integer rows;

    private Integer page;

    private String sortFiled;

    private Integer sortOrder;

    private Map<String,Object> transMap;

    /**
     * 主键值
     */
    private String pkFileValue;

    // public String getCreateTime() {
    //     return createTime;
    // }
    //
    // public void setCreateTime(String createTime) {
    //     this.createTime = createTime;
    // }
    //
    // public String getCreateUser() {
    //     return createUser;
    // }
    //
    // public void setCreateUser(String createUser) {
    //     this.createUser = createUser;
    // }
    //
    // public String getUpdateTime() {
    //     return updateTime;
    // }
    //
    // public void setUpdateTime(String updateTime) {
    //     this.updateTime = updateTime;
    // }
    //
    // public String getUpdateUser() {
    //     return updateUser;
    // }
    //
    // public void setUpdateUser(String updateUser) {
    //     this.updateUser = updateUser;
    // }

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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortFiled() {
        return sortFiled;
    }

    public void setSortFiled(String sortFiled) {
        this.sortFiled = sortFiled;
    }

    public Map<String, Object> getTransMap() {
        return transMap;
    }

    public void setTransMap(Map<String, Object> transMap) {
        this.transMap = transMap;
    }

    public String getPkFileValue() {
        return pkFileValue;
    }

    public void setPkFileValue(String pkFileValue) {
        this.pkFileValue = pkFileValue;
    }

    public BaseBean() {
    }

    public BaseBean(Map<String, String> between, Integer rows, Integer page, String sortFiled, Integer sortOrder, Map<String, Object> transMap, String pkFileValue) {
        this.between = between;
        this.rows = rows;
        this.page = page;
        this.sortFiled = sortFiled;
        this.sortOrder = sortOrder;
        this.transMap = transMap;
        this.pkFileValue = pkFileValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        BaseBean baseBean = (BaseBean) o;

        if (between != null ? !between.equals(baseBean.between) : baseBean.between != null) {return false;}
        if (rows != null ? !rows.equals(baseBean.rows) : baseBean.rows != null) {return false;}
        if (page != null ? !page.equals(baseBean.page) : baseBean.page != null) {return false;}
        if (sortFiled != null ? !sortFiled.equals(baseBean.sortFiled) : baseBean.sortFiled != null) {return false;}
        if (sortOrder != null ? !sortOrder.equals(baseBean.sortOrder) : baseBean.sortOrder != null) {return false;}
        if (transMap != null ? !transMap.equals(baseBean.transMap) : baseBean.transMap != null) {return false;}
        return pkFileValue != null ? pkFileValue.equals(baseBean.pkFileValue) : baseBean.pkFileValue == null;
    }

    @Override
    public int hashCode() {
        int result = between != null ? between.hashCode() : 0;
        result = 31 * result + (rows != null ? rows.hashCode() : 0);
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (sortFiled != null ? sortFiled.hashCode() : 0);
        result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
        result = 31 * result + (transMap != null ? transMap.hashCode() : 0);
        result = 31 * result + (pkFileValue != null ? pkFileValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "between=" + between +
                ", rows=" + rows +
                ", page=" + page +
                ", sortFiled='" + sortFiled + '\'' +
                ", sortOrder=" + sortOrder +
                ", transMap=" + transMap +
                ", pkFileValue='" + pkFileValue + '\'' +
                '}';
    }

}
