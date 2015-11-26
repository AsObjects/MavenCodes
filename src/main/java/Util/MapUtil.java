package Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtil {
	/*
	 * 四中取值方式
	 */
	  private Map<String, String> map = new HashMap<String, String>();
	  
	  public void init(){
		  map.put("1", "value1");
		  map.put("2", "value2");
		  map.put("3", "value3");
	  }
	  
	  
	  public void maps(String one){
		//第一种：普遍使用，二次取值
		System.out.println("通过Map.keySet遍历key和value：");
		for (String key : map.keySet()) {
			System.out.println("the one  key= "+ key + " and value= " + map.get(key));
		}
	  }
	  
	  public void maps(int two){
		  System.out.println("通过Map.entrySet使用iterator遍历key和value：");
		  Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		  while (it.hasNext()) {
		   Map.Entry<String, String> entry = it.next();
		   System.out.println("the two  key= " + entry.getKey() + " and value= " + entry.getValue());
		  }
	  }
	  
	  public void maps(Object three){
		//第三种：推荐，尤其是容量大时
		  System.out.println("通过Map.entrySet遍历key和value");
		  for (Map.Entry<String, String> entry : map.entrySet()) {
		    System.out.println("the three  key= " + entry.getKey() + " and value= " + entry.getValue());
		  }
	  }
	  
	  public void maps(){
		//第四种
		  System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
		  for (String v : map.values()) {
		   System.out.println("the four value= " + v);
		  }
	  }
	  public static void main(String[] args) {
		MapUtil mapu=new MapUtil();
		mapu.init();
		System.out.println("==========one========");
		mapu.maps("");
		System.out.println("==========two========");
		mapu.maps(1);
		System.out.println("==========three========");
		mapu.maps(new Object());
		System.out.println("==========four========");
		mapu.maps();
	  }

}
