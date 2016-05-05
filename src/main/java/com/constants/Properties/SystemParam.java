package com.constants.Properties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.Service.serviceCommon.FactoryBean.SpringBeanGetter;

public class SystemParam {


    
    private  static Map<String,String> map=new ConcurrentHashMap<String, String>();
    
            
    public static void setProperty(String key,String value) {
        map.put(key, value);
    }
    
    public static void setProperty(String key,String value,String defaultVal) {
        if(value==null||value=="") {
            value=defaultVal;
        }
        map.put(key, value);
    }

    public static String getProperty(String key) {
    	if (map.containsKey(key)){
    		return map.get(key);
    	}else{
    		return getPropertyFromConfigService(key);
    	}
    }

    public static String getProperty(String key,String defaultVal) {
        String value= getProperty(key);
        if(value==null||value=="") {
            return defaultVal;
        }
        return value;
    }
    
    /**
     * 从配置服务的本地缓存读取配置参数
     *现在只从配置文件里读即可
     * @param key
     * @return
     */
    private static String getPropertyFromConfigService(String key){
    	/*if(ServiceBeanFactory.getInstance()==null){
    		return null;
    	}
    	IConfigService configService = ServiceBeanFactory.getInstance().getServiceBean(IConfigService.class);
    	if (configService == null){
    		System.out.println("config service is null");
    		return null;
    	}
    	
    	
    	String value = null;
    	//从系统参数表读取    	
    	String defaultNamespace = configService.getSystemValue(PropertyConsts.TYPE_DEF_CONF_LIST);
    	String[] nsList=null;
		if(!.isBlank(defaultNamespace)){
			nsList=defaultNamespace.split(",");
    		for(String ns:nsList){
    			value=configService.getValue(ns,key);
    			if(value!=null){
    				return value;
    			}
    		}
		}*/
    	GetSystemParam getSystemParam=(GetSystemParam)SpringBeanGetter.getBeanByClass(GetSystemParam.class);
    	//从本地读取系统参数
    	String value = getSystemParam.getSystemValue(key);
    	if (value != null){
    		return value;
    	}
    	
    	    	
    	return null; 
    }
    
    
}
