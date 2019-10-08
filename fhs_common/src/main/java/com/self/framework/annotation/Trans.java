package com.self.framework.annotation;

import org.springframework.context.annotation.ComponentScans;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Trans {

    /**
     * redis  存储key
     * @return
     */
    String transKey() default "";
    
    /**
     * @description：关联字典表名
     * @author qiuhang
     * @date 2019/9/23
     */
    String refDICTName() default "";
    
    /**
     * @description：关联字段表条件字段
     * @author qiuhang
     * @date 2019/9/23/023
     */
    String refDICTCondition() default "FLD_ID";
    
    /**
     * @description：关联字段表结果字段
     * @author qiuhang
     * @date 2019/9/23/023
     */
    String refDICTQuery() default "FLD_NAME";
}
