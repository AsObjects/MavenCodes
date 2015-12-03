package Service.ServiceImpl;

import java.util.Map;

import javax.annotation.PostConstruct;

public class XmlConfig {

	static Map<String, Object> configMap;
	
	@PostConstruct
	public boolean loadConf(){//TODO:监听器检测是否配置有更新操
		return false;
	}
	
	public Map<String, Object> getConfig() {
		return configMap;
	}
	
	public Map<String, Object> getConfig(String key) {
		return (Map<String, Object>)getConfig().get(key);
	}
}
