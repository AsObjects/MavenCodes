package com.constants.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 从配置文件读取参数
 */

public class GetSystemParam{
	
	private static Logger log = LoggerFactory.getLogger(GetSystemParam.class);
	//缓存
	protected static Map<String,String> properties = new HashMap<String,String>();
		//配置文件
		String fileName;
		//配置文件上次更新时间
		long lastModifyTime;



		@PostConstruct
		public void init(){
			this.load();
		}
		
		/**
		 * 读取配置文件
		 * @return
		 */
		private File getConfigFile(){
			// 初始化平台的setting.property配置文件		
			if (this.getClass().getResource(this.fileName) == null) {
				//log.error("can't find {}",fileName);
				return null;
			} else {
				File configFile = new File(this.getClass()
						.getResource(this.fileName)
						.getFile());
				return configFile;
			}	
		}
		
		/**
		 * 从配置文件读取配置，放入domain中。
		 * 假设属性名为： a.b.c 那么取a.b为domain，c为key
		 * 加入属性名为:c，那么domain为"",c为key
		 * @param propertiesFile 配置文件路径
		 */
		public void load(){
			FileInputStream fin = null;
			File configFile = this.getConfigFile();
			try {
				fin = new FileInputStream(configFile);
				Properties pro = new Properties();
				pro.load(fin);	
				this.load(pro);
			} catch (Exception e) {
				log.error("read properties from {} failed",configFile.getPath(),e);
			}finally{
				IOUtils.closeQuietly(fin);
			}			
			this.lastModifyTime = configFile.lastModified();
			/*if (log.isDebugEnabled()) {
				log.debug("load config datas from {},total is {}",configFile,this.properties.size());
			}*/
		}
		
		/**
		 * 从配置文件读取配置，放入domain中。
		 * 假设属性名为： a.b.c 那么取a.b为domain，c为key
		 * 加入属性名为:c，那么domain为"",c为key
		 * 
		 * 为了兼容，同时更新portal全局参数
		 * @param pro
		 */
		private void load(Properties pro){
			Enumeration<?> enume = pro.propertyNames();
			while(enume.hasMoreElements()){
				
				String propertyName = ((String)enume.nextElement()).trim();
				String value = pro.getProperty(propertyName,"").trim();
				
				if (propertyName.length() == 0){
					continue;
				}
				
				//更新portal全局参数
				this.addConfig(propertyName, value);
				
				//String[] resolvedDatas = this.resolvePropertyName(propertyName);
				/*if (resolvedDatas != null){
					this.addConfig(resolvedDatas[0],resolvedDatas[1], value);
				}*/
			}
		}
		
		/**
		 * 将propertyName解析成namespace+key。
		 * 1)支持 namespace|key的情况
		 * 2)支持 xx.xx.xx ,其中最后一个点前为namespace，后为key
		 * @param propertyName
		 * @return
		 */
		/*String[] resolvePropertyName(String propertyName){
			//处理namespace|key
			if (propertyName.indexOf("|") != -1){
				String[] temp = propertyName.split("\\|");
				if (temp.length != 2){
					//格式校验
					//log.warn("{} not format: [namespace]|[key]",propertyName);
					return null;
				}else{
					return new String[]{temp[0],temp[1]};
				}
			}
			
			//处理xx.xx.xx
			int posOfLastDot = propertyName.lastIndexOf(".");
			if (posOfLastDot == -1){
				return new String[]{"",propertyName};
			}else{
				String namespace = propertyName.substring(0,posOfLastDot).trim();
				String key = propertyName.substring(posOfLastDot + 1).trim();
				return new String[]{namespace,key};
			}
		}*/
		
		/**
		 * 检查文件是否发生了改变
		 */
		/*public boolean checkConfigChange() {
			File configFile = this.getConfigFile();
			long nowTime = configFile.lastModified();
			if (nowTime != this.lastModifyTime){
				this.load();
				return true;
			}
			return false;
		}*/

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
		public String getFileName() {
			return fileName;
		}
		
		/*public String getValue(String namespace, String key){

			String value=null;
			String defaultNamespace = this.getSystemValue(PropertyConsts.TYPE_DEF_CONF_LIST);
			String[] nsList=null;
			if(!StringUtils.isBlank(defaultNamespace)){
				nsList=defaultNamespace.split(",");

				for(String ns:nsList){
					value=super.getValue(ns,key);
					if(value!=null){
						return value;
					}
				}
			}
			
			value=super.getValue(namespace, key);
			if(value!=null){
				return value;
			}
			
			value=super.getSystemValue(key);
			
			return value;
		}*/

		public void addConfig(String key,String value){
			this.properties.put(key, value);
		}
		
		public String getSystemValue(String key) {
			return this.properties.get(key);
		}
}
