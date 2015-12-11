package Quartz;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ConfigBean.SpringBeanGetter;
import SystemParam.IXmlConfig;
import SystemParam.IXmlConfig.XML_CONF_FIRST_NODE;

/**
 * 任务工具，专门针对任务的工具类
 */
public class TaskUtil {
	
	/*
	 * 抽象出登录贴吧需要的参数封装
	 */
	public static Map getLoginParam(String token){
		Map<String,String> paramMap=new HashMap<String, String>();
		paramMap.put("charset", "utf-8");
		paramMap.put("tpl", "pp");
		paramMap.put("apiver", "v3");
		paramMap.put("tt", "1390751409263");
		paramMap.put("saleflg", "0");
		paramMap.put("isPhone", "false");
		paramMap.put("quick_user", "0");
		paramMap.put("logintype", "basicLogin");
		paramMap.put("token", token);
		paramMap.put("username", "镜花8水月90");
		paramMap.put("password", "lajidhao809294");
		paramMap.put("mem_pass", "on");
		paramMap.put("verifycode", "");
		paramMap.put("callback", "parent.bd_pcbs_ajnsn");
		return paramMap;
	}
	/*
	 * 封装发帖子的参数
	 */
	public static Map getSendMessageParam(String tiebaName,String fid,String tid,String tbs,String content){
		Map<String,String> paramMaps=new HashMap<String, String>();
		paramMaps.put("kw", tiebaName);
		paramMaps.put("ie", "utf-8");
		paramMaps.put("fid", fid);
		paramMaps.put("tid", tid);
		paramMaps.put("floor_num", "1");
		paramMaps.put("rich_text", "1");
		paramMaps.put("content", content);
		paramMaps.put("_type_", "reply");
		paramMaps.put("tbs", tbs);
		return paramMaps;
	}
	
	/*
	 * 随机产生不同内容的水贴方法
	 */
	public static String getContent(){
		IXmlConfig conf=SpringBeanGetter.getBeanByClass(IXmlConfig.class);
		Map str=(Map) conf.getConfig().get(XML_CONF_FIRST_NODE.TieBaLiuYan.name());
		String []randomStr=new String[str.size()];
		int i=0;
		for(Object s:str.keySet()){
			randomStr[i]=(String) str.get(s);
			i++;
		}
		Random r=new Random();
		int num=r.nextInt(randomStr.length);
		//System.out.println(random[num]+"                   test:"+num);
		return randomStr[num];
	}
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			getContent();
		}
	}
}
