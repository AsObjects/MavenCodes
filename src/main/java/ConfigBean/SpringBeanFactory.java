package ConfigBean;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @TODO:oauth2kuangjia jicheng 在某些地方（filter，jsp等）不能使用spring的注入来获取bean实例，通过
 * ServiceBeanFactory的getServiceBean方法，可以获取相应的bean。
 * ServiceBeanFactory实现了单例模式，因此能通过静态方法getInstance获取其唯一实例；
 * 同时ServiceBeanFactory通过实现spring的ApplicationContextAware接口获取applicationContext。
 * 建议：尽量使用spring的注入机制来获取bean对象，只有在无法获取的时候使用。
 * @author Administrator `
 */
public class SpringBeanFactory implements ApplicationContextAware{
	
    private static Logger logger =
            LoggerFactory.getLogger(SpringBeanFactory.class);
    
	 // 唯一实例，用于单例模式
    private static SpringBeanFactory instance;

    // spring的applicationContext，用于获取bean
    private ApplicationContext applicationContext;

    // 用于单元测试
    Map<Class<?>, Object> serviceBeansForTest = new HashMap<Class<?>, Object>();

    // clazz的名称与实际定义beanid对应mapping
    Map<String, String> beanNameMapping = new HashMap<String, String>();

    /**
     * 在spring初始化bean时，初始化静态唯一实例。
     */
    @PostConstruct
    public void init() {
    	System.out.println("........................................");
        instance = this;
        logger.info("........................................");
    }

    /**
     * 获取工厂唯一实例
     * @return
     */
    public static SpringBeanFactory getInstance() {
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setTestBean(Class<?> requiredType, Object bean) {
        this.serviceBeansForTest.put(requiredType, bean);
    }

    /**
     * 根据bean实现接口的class从spring获取对应的bean
     * @param requiredType
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getServiceBean(Class<T> requiredType) {
        // long time=System.currentTimeMillis();
        if (this.serviceBeansForTest.containsKey(requiredType)) {
            return (T) this.serviceBeansForTest.get(requiredType);
        }

        String simpleName = requiredType.getSimpleName();
        String fullName = requiredType.getName();
        int code = fullName.hashCode();

        String beanName = beanNameMapping.get(simpleName + code);

        if (beanName==null||beanName=="") {
            //
            String[] beanNames =
                    this.applicationContext.getBeanNamesForType(requiredType);
            if (beanNames.length == 0 || beanNames[0]==null||beanNames[0]=="") {
                logger.error("the bean not define  ,type:{}", fullName);
                //throw new ServiceException(ErrorCodes.INTERNAL_ERROR);
            }
            if (beanNames.length > 1) {
               /* logger.error("the bean define  duplicate,type:{},beanNames:{}",
                        fullName, ArrayUtils.toString(beanNames));*/
               // throw new ServiceException(ErrorCodes.INTERNAL_ERROR);
            }
            beanName = beanNames[0];
            beanNameMapping.put(simpleName + code, beanName);
            // System.out.println("cache bean name mapping: "+fullName);
        }

        // try{
        return this.applicationContext.getBean(beanName, requiredType);
        // }finally{
        // System.out.println(simpleName+"
        // time:"+(System.currentTimeMillis()-time));
        // }

        // return this.applicationContext.getBean(requiredType);
    }

   /* public static void main(String[] args) {
        Class requiredType = ICacheService.class;
        String simpleName = requiredType.getSimpleName();
        String fullName =
                requiredType.getPackage().getName() + "." + simpleName;
        int code = fullName.hashCode();

        System.out.println(simpleName);
        System.out.println(fullName);
        System.out.println(code);
    }*/

    /**
     * 根据名称获取相应的bean
     * @param name
     * @return
     */
    public Object getServiceBean(String name) {
        try {
            return this.applicationContext.getBean(name);
        } catch (Exception e) {
            throw new RuntimeException("can't find class of " + name, e);
        }
    }

    /**
     * 根据bean的接口类型和名称获取
     * @param name 查找bean的名称
     * @param requiredType bean实现的接口类型
     * @return
     */
    public <T> T getServiceBean(String name, Class<T> requiredType) {
        return this.applicationContext.getBean(name, requiredType);
    }

    /**
     * 获取spring的ApplicationContext对象，通过该对象可以获取bean实例。
     * @return
     */
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

}
