package de.weichert.multitenancy.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

//@Component
public class TenantScopedBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

        for (String beanName : factory.getBeanDefinitionNames()) {

            BeanDefinition beanDef = factory.getBeanDefinition(beanName);

            String explicitScope = beanDef.getScope();

            if ("".equals(explicitScope)) {

                beanDef.setScope(TenantScope.SCOPE_NAME);
            }
        }
    }
}
