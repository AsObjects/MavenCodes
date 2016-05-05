package com.constants.XmlConfig;

import java.io.File;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.constants.IXmlConfig;
import com.constants.SystemParamMapping;
import com.constants.Properties.SystemParam;
import com.mccutil.SaxExtendXmlParser;
import com.model.MccDefauleException;




public class XmlConfigImpl implements IXmlConfig{
	
	
	private static Logger logger = LoggerFactory.getLogger(XmlConfigImpl.class);
	
	/*
	 * 文件名注入
	 */
	private String fileName;
	/*
	 * 静态缓存
	 */
	public static Map<String, Object> wxConfigMap;
	/*
	 * 最后修改的时间
	 */
	private static Long modify;
	
	@PostConstruct
	public boolean loadConf(){//TODO:监听器检测是否配置有更新操作
		String file=SystemParam.getProperty(SystemParamMapping.WAR_HOME_PATH)+fileName;
		File f =new File(file);
		if(!f.exists()){
			logger.error("wechat conf file not exists");
			throw new MccDefauleException();
		}
		Long mod = f.lastModified();
		if (modify == null || !mod.equals(modify)) {
			modify = mod;
			Map<String, Object> map=null;
			try {
				map = SaxExtendXmlParser.parseXmlFromFile(f);
			} catch (Exception e) {

				logger.error("error to  load wechat_conf.xml");
				throw new MccDefauleException();
			}
			if(map==null||map.isEmpty()){
				logger.error("error to  load wechat_conf.xml,is empty");
				throw new MccDefauleException();
			}else{
				wxConfigMap = map;
				return true;
			}
		}
		return false;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Map<String, Object> getConfig() {
		return wxConfigMap;
	}



	@Override
	public Map<String, Object> getConfig(XML_CONF_FIRST_NODE key) {
		return (Map<String, Object>)getConfig().get(key.name());
	}
/*

	@Override
	public Map<String, Object> getConfig(String key) {
		return (Map<String, Object>)getConfig().get(key);
	}


	@Override
	public Object getConfStatItem(String key) {
		return getConfig(WC_CONF_ROOT_KEYS.ConfStatItems).get(key);
	}


	@Override
	public Object getConfReplaceStr(String key) {
		return getConfig(WC_CONF_ROOT_KEYS.ConfReplaceStrs).get(key);
	}


	@Override
	public Object getConfFlag(String key) {
		return getConfig(WC_CONF_ROOT_KEYS.ConfFlags).get(key);
	}


	@Override
	public Object getConfWechatResponse(String key) {
		return getConfig(WC_CONF_ROOT_KEYS.ConfWechatResponse).get(key);
	}


	@Override
	public Object getConfSendRedPacket(String key) {
		return getConfig(WC_CONF_ROOT_KEYS.sendRedPacket).get(key);
	}*/
}
