package com.constants;

import java.util.Map;

public interface IXmlConfig {
	
	/**
	 * 加载xml文件
	 */
	public boolean loadConf();
	
	/**
	 * xml配置文件的一级节点配置
	 * @author james
	 */
	public static enum XML_CONF_FIRST_NODE{
		TieBaLiuYan
	}
	
	/**
	 * xml配置文件的二级节点配置
	 * @author james
	 */
	public static enum XML_CONF_SECOND_NODE{
		
	}
	
	/**
	 * 获取缓存map的入口
	 */
	public Map<String, Object> getConfig();
	
	/**
	 * 通过一级节点获取数据
	 */
	public Map<String,Object> getConfig(XML_CONF_FIRST_NODE key);
}
