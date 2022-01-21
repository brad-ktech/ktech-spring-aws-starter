package com.ktech.starter.configurations;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware {


    private static ApplicationContext CONTEXT;

    public void init() {


    }

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {

        CONTEXT = context;

    }


    /**
     * Wire up any given POJO to work with Spring's @Autowired annotation
     *
     * @param someBeanNotCreatedBySpring Object not created or managed by Spring
     */
    public static void inject(Object someBeanNotCreatedBySpring) {


        if (CONTEXT != null) {
            CONTEXT.getAutowireCapableBeanFactory().autowireBeanProperties(someBeanNotCreatedBySpring, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
        }


    }

    /**
     * Retrieve an instance of a Spring bean by name
     *
     * @param beanName JNDI name of Spring bean
     * @return Spring bean
     */
    public static Object getBean(String beanName) {

        Object o = null;

        if (CONTEXT != null) {
            o = CONTEXT.getBean(beanName);
        }

        return o;
    }
}
