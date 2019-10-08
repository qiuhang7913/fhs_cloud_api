package com.self.framework.spring.extend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @des 获取spring容器bean
 * @author qh
 * @version v1.0
 */
@Component
public class ObtainSpringBean implements ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ObtainSpringBean.applicationContext == null) {
            ObtainSpringBean.applicationContext = applicationContext;
        }
        logger.debug("---------------------------------------------------------------------");

        logger.debug("---------------------------------------------------------------------");

        logger.debug("---------com.self.framework.spring.extend.obtainSpringBean-----------");

        logger.debug("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+ ObtainSpringBean.applicationContext+"========");

        logger.debug("---------------------------------------------------------------------");
    }

    /**
     * @description 获取applicationContext
     * @author qiuhang
     * @date 2019/9/24/024
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @description 通过name获取 Bean.
     * @author qiuhang
     * @date 2019/9/24/024
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * @description 通过class获取Bean.
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @description 通过name,以及Clazz返回指定的Bean
     * @author qiuhang
     * @date 2019/9/24/024
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
