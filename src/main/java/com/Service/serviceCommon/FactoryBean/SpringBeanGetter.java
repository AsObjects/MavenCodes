package com.Service.serviceCommon.FactoryBean;


/**
 * 获取业务bean的公共方法
 * SpringBeanFactory.getInstance().getServiceBean(.class)
 *
 */
public class SpringBeanGetter {

	 /**
	  * TODO:根据class获取实例，
	  * @return
	  */
	public static <T>T getBeanByClass(Class<T> entry){
		return SpringBeanFactory.getInstance().getServiceBean(entry);
	}
}
