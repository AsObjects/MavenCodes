package com.mccutil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtil {
	/*
	 * ����ȡֵ��ʽ
	 */
	  private Map<String, String> map = new HashMap<String, String>();
	  
	  public void init(){
		  map.put("1", "value1");
		  map.put("2", "value2");
		  map.put("3", "value3");
	  }
	  
	  
	  public void maps(String one){
		//��һ�֣��ձ�ʹ�ã�����ȡֵ
		System.out.println("ͨ��Map.keySet����key��value��");
		for (String key : map.keySet()) {
			System.out.println("the one  key= "+ key + " and value= " + map.get(key));
		}
	  }
	  
	  public void maps(int two){
		  System.out.println("ͨ��Map.entrySetʹ��iterator����key��value��");
		  Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		  while (it.hasNext()) {
		   Map.Entry<String, String> entry = it.next();
		   System.out.println("the two  key= " + entry.getKey() + " and value= " + entry.getValue());
		  }
	  }
	  
	  public void maps(Object three){
		//�����֣��Ƽ�����������ʱ
		  System.out.println("ͨ��Map.entrySet����key��value");
		  for (Map.Entry<String, String> entry : map.entrySet()) {
		    System.out.println("the three  key= " + entry.getKey() + " and value= " + entry.getValue());
		  }
	  }
	  
	  public void maps(){
		//������
		  System.out.println("ͨ��Map.values()�������е�value�������ܱ���key");
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
