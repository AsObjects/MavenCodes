package com.mccutil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapToSimpleXmlHelper {


    /**
     * 
     */
    public MapToSimpleXmlHelper() {
    }

    
    public static String convertToCustomXml(Map<String, Object> map,String rootNodeName,boolean addHead) {
        return new MapToSimpleXmlHelper().convertToSimpleXml(map, rootNodeName, addHead);
    }
    
    public  String convertToSimpleXml(Map<String, Object> map, String msgType,
            boolean addHead) {
        StringBuilder strBuilder = new StringBuilder();
        if(addHead){
        strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        }
        //
        strBuilder.append("<" + msgType + ">");

        // 解析body中其它string节点，list节点，object对象，map节点

        //
        buildXmlBodyMap(map, strBuilder);
        
        //
        strBuilder.append("</" + msgType + ">");

        return strBuilder.toString();
    }


    private void buildXmlBodyMap(Map<String, Object> map,
            StringBuilder strBuilder) {
        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value==null) {
                //System.err.println("the value is null,key:"+ key);
                continue ;
            }
            if (value instanceof String) {// 子节点为string节点
                appendStringNode(strBuilder, key, (String) value);
            } else if (value instanceof List) {// 子节点为List节点
                //strBuilder.append("<list name=\"" + key + "\">");
            	strBuilder.append("<"+key+">");
                buildXmlBodyList((List) value, strBuilder);
                //strBuilder.append("</list>");
            	strBuilder.append("</"+key+">");
            } else if (value instanceof Map) {// 子节点为Map节点
                //strBuilder.append("<object name=\"" + key + "\">");
            	strBuilder.append("<"+key+">");
                buildXmlBodyMap((Map) value, strBuilder);
                //strBuilder.append("</object>");
            	strBuilder.append("</"+key+">");
            } else if (value instanceof Object[]) {// 子节点为 数组 节点
                throw new RuntimeException("error map defined,can not support the array!");
                /*
                strBuilder.append("<arrays name=\"" + key + "\">");
                buildXmlArrayNode((Object[]) value, strBuilder);
                strBuilder.append("</arrays>");*/
            }else {
                appendStringNode(strBuilder, key, value.toString());
//                System.err.println("can not parse the data,the map's value classType is:"
//                        + value.getClass().getName());
            }
        }
    }
    
    private void buildXmlArrayNode(Object[] arrays,
            StringBuilder strBuilder) {
        for (Object obj : arrays) {
            if (obj instanceof String) {
                appendStringNode(strBuilder, "array", (String) obj);

            } else if (obj instanceof Map) {
                strBuilder.append("<array>");
                buildXmlBodyMap((Map) obj, strBuilder);
                strBuilder.append("</array>");
            } else {
                System.err.println("can not parse the data from the list, item classType is:"
                        + obj.getClass().getName());
            }
        }
    }

    private void buildXmlBodyList(List list,
            StringBuilder strBuilder) {
        for (Object obj : list) {
            if(obj==null) {
                break;
            }
            if (obj instanceof String) {
                appendStringNode(strBuilder, "string", (String) obj);

            } else if (obj instanceof Map) {
                //strBuilder.append("<object>");
                strBuilder.append("<item>");
                buildXmlBodyMap((Map) obj, strBuilder);
                //strBuilder.append("</object>");
                strBuilder.append("</item>");
            } else {
                System.err.println("can not parse the data from the list, item classType is:"
                        + obj.getClass().getName());
            }
        }
    }

    private void appendStringNode(StringBuilder strBuilder, String key,
            String value) {
        //strBuilder.append("<" + key + ">" + value + "</" + key + ">");
        //strBuilder.append("<" + key + "><![CDATA[" + value + "]]></" + key + ">");
        strBuilder.append("<" + key + "><![CDATA[" + value + "]]></" + key + ">");
    }

    
    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("key", new String[] {"11231","123123"});
        System.out.println(convertToCustomXml(map,"xml",true));
    }
}
