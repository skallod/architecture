package ru.galuzin.notification.sidecar.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.List;

import static java.util.Arrays.asList;

public class DynamicBeanFactoryProcessor  implements BeanFactoryPostProcessor, InitializingBean {

    List<String> topics;// = Collections.emptyList();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        final BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;

        topics.forEach(t -> beanDefinitionRegistry.registerBeanDefinition("topicBean-" + t, BeanDefinitionBuilder
                        .rootBeanDefinition(NewTopic.class)
                        //.setFactoryMethodOnBean("createInstance", "dynamicBeanFactoryProcessor")
                        .addConstructorArgValue(t)
                        .addConstructorArgValue(1)
                        .addConstructorArgValue((short)1)
                        .getBeanDefinition()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.topics = asList(PropertiesLoaderUtils
                .loadProperties(new ClassPathResource("/application.properties"))
                .getProperty("listen.topic.name", "")
                .split(","));
    }
}
