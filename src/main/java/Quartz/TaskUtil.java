package Quartz;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
		Random r=new Random();
		String []random={
				"伟大的度娘啊，以吾之精血，遵循古老的盟约，" +
				"倾听吾之耳语，打破时空的界限。" +
				"吾以契约呼唤以血脉请求以吾身为器以吾灵魂为贡献籍远古精灵之语言，" +
				"诵念汝之真名：亚马逊听吾之命，为吾而战！" +
				"楼下的亚马逊，告诉他们海外代购最高返多少？",
				"以前不懂，看帖总是不回。一直没升级和增加经验，" +
				"现在我明白了。反正回帖15字就可以升级，还可以赚经验。" +
				"升级又需要经验，于是我把这句话复制下来。遇贴就回，捞完就闪",
				"是谁把光阴剪成了烟花，一瞬间，看尽繁华。一树繁花，只一眼，便是天涯。",
				"前排围观！！",
				"火前留名！！"
				};
		int num=r.nextInt(random.length);
		System.out.println(random[num]+"                   test:"+num);
		return random[num];
	}
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			getContent();
		}
	}
}
