package com.etc.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author gaogang
 * @ClassName: SpringContextUtil
 * @Description: 静态获取Bean
 * @date 2016年7月12日 下午4:21:26
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * 当前IOC上下文环境
     */
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param name bean name,infact it's name in spring IOC
     * @return bean实例
     * @Description 从spring容器获取bean实例</br>
     * <font color='red'>注意，这里beanId是spring ioc中bean实例的名字,如果是注解扫描方式，没有特意指定名称，一般为类名，首字母小写(java遵从驼峰命名规则)，如果有指定名称，则为指定的bean名称</font>
     * @author wjggwm
     * @data 2016年8月25日 下午2:22:16
     */
    public static Object getBean(String name) throws BeansException {
        return getApplicationContext().getBean(name);
    }

    /**
     * @param name         bean name,infact it's name in spring IOC
     * @param requiredType bean class类型
     * @return bean实例
     * @throws BeansException
     * @Description 从spring容器获取bean实例</br>
     * <font color='red'>注意，这里beanId是spring ioc中bean实例的名字,如果是注解扫描方式，没有特意指定名称，一般为类名，首字母小写(java遵从驼峰命名规则)，如果有指定名称，则为指定的bean名称</font>
     * @author wjggwm
     * @data 2017年1月5日 下午5:58:27
     */
    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getApplicationContext().getBean(name, requiredType);
    }

    /**
     * 功能描述: 根据类型返回
     *
     * @return : T
     * @param: requiredType
     * @author : Tiandaguang
     * @date : 2019/11/6 9:48
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }


}