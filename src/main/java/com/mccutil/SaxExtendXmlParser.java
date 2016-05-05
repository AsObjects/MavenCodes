package com.mccutil;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 把xml解析成Map<String,Object>对象  ,扩展原有sax解析，更通用
 */
public class SaxExtendXmlParser extends DefaultHandler {

    public SaxExtendXmlParser() {
        super();
        //setRunInGoogleJvm(true);//TODO:启动代码，解析程序运行在手机终端上
        //setPrintAble(true);//TODO:启动代码，打印出xml内容
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseXml(InputStream io) throws Exception {
        return parseXmlFromInputSource(new InputSource(new InputStreamReader(
                io, DEFAULT_ENCODING)));
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseXmlFromIO(InputStream io)
            throws Exception {
        //long lasting = System.currentTimeMillis();
        SaxExtendXmlParser reader = new SaxExtendXmlParser();
        SAXParserFactory sf = SAXParserFactory.newInstance();
        SAXParser sp = sf.newSAXParser();
        sp.parse(new InputSource(new InputStreamReader(io, DEFAULT_ENCODING)),
                reader);
        //reader.printlnXmlContent("\r\n----parse xml use time： "+ (System.currentTimeMillis() - lasting) + " ms");
        return (Map<String, Object>) reader.getXmlNodeStack().peek().getValue();

    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseXmlFromFile(File file)
            throws Exception {
        //long lasting = System.currentTimeMillis();
        SaxExtendXmlParser reader = new SaxExtendXmlParser();
        SAXParserFactory sf = SAXParserFactory.newInstance();
        SAXParser sp = sf.newSAXParser();
        sp.parse(file, reader);
        //reader.printlnXmlContent("\r\n----parse xml use time： "+ (System.currentTimeMillis() - lasting) + " ms");
        return (Map<String, Object>) reader.getXmlNodeStack().peek().getValue();

    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseXmlFromFile(String filePath)
            throws Exception {
        //long lasting = System.currentTimeMillis();
        SaxExtendXmlParser reader = new SaxExtendXmlParser();
        SAXParserFactory sf = SAXParserFactory.newInstance();
        SAXParser sp = sf.newSAXParser();
        sp.parse(new File(filePath), reader);
        //reader.printlnXmlContent("parse xml to map use time："+ (System.currentTimeMillis() - lasting) + "ms");
        return (Map<String, Object>) reader.getXmlNodeStack().peek().getValue();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseXmlFromInputSource(InputSource is)
            throws Exception {
        //long lasting = System.currentTimeMillis();
        SaxExtendXmlParser reader = new SaxExtendXmlParser();
        SAXParserFactory sf = SAXParserFactory.newInstance();
        SAXParser sp = sf.newSAXParser();
        sp.parse(is, reader);
        //reader.printlnXmlContent("parse xml to map use time："+ (System.currentTimeMillis() - lasting) + "ms");
        return (Map<String, Object>) reader.getXmlNodeStack().peek().getValue();
    }

    /**
     * 可以从 http://xxxx.xxx/xxx.xxx中 url中读取xml文件或者响应消息中取xml内容进行解析
     * @param uri
     * @return map对象
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseXmlFromUri(String uri)
            throws Exception {
        //long lasting = System.currentTimeMillis();
        SaxExtendXmlParser reader = new SaxExtendXmlParser();
        SAXParserFactory sf = SAXParserFactory.newInstance();
        SAXParser sp = sf.newSAXParser();
        sp.parse(uri, reader);
        //reader.printlnXmlContent("parse xml to map use time："+ (System.currentTimeMillis() - lasting) + "ms");
        return (Map<String, Object>) reader.getXmlNodeStack().peek().getValue();
    }

    /**
     * 读取到<tag>标签开始
     * @tagName 标签名
     * @attrs 标签属性
     */
    @Override
    public void startElement(String uri, String localName, String tagName,
            Attributes attrs) {
        if (isRunInAndroidJvm) {
            tagName = localName;
        }
        // 把读取的前一个标签text缓存内容清空
        setTagValue("");

        if (attrs.getLength() == 0) {
            // 初始化stack值堆栈
            initStack(tagName, null);

            // 打印开始标签,
            if (isPrintAble()) {
                printXmlContent("\r\n"
                        + getWhitSpaceByLevel(xmlNodeStack.size())
                        + String.format("<%s>", tagName));
            }
        } else {
            // 初始化stack值堆栈
            initStack(tagName, attrs.getValue(0));

            //打印开始标签与其属性
            if (isPrintAble()) {
                printXmlContent("\r\n"
                        + getWhitSpaceByLevel(xmlNodeStack.size())
                        + String.format("<%s name=\"%s\">", tagName,
                                attrs.getValue(0)));
            }
        }

    }

    /**
     * 解析<tag>xxx</tag> <tag>yyy</tag>中<tag>xxx</tag>之中值，解析</tag>
     * <tag>之间内容
     */
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        // 标签内容
        tagValue += new String(ch, start, length);
        //tagValue=recoverXmlString(tagValue);
    }
    
    
    
    /**
     * 非系统约定特殊节点类型，返回true,并且需设置节点值
     * @param type
     * @return
     */
    public static boolean checkUnSysNodeType(String type){
    	if (!(type.equalsIgnoreCase(ClientConstants.TAG_MSG_HEAD)
                || type.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY)
                || type.equalsIgnoreCase(ClientConstants.TAG_REQUEST)
                || type.equalsIgnoreCase(ClientConstants.TAG_RESPONSE)
                || type.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY_OBJECT) 
                || type.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY_LIST)
                || type.equalsIgnoreCase("xml")
                || type.equalsIgnoreCase("item")
                || type.endsWith("s")
    			)
                  ) {
    		return true;
    	}
    	return false;
    }

    /**
     * 读取到</tag>标签结束
     * @tagName 标签名
     */
    @Override
    public void endElement(String uri, String localName, String tagName)
            throws SAXException {
        if (isRunInAndroidJvm) {
            tagName = localName;
        }
        XmlNodeInfo topNode = xmlNodeStack.peek();
        String type = topNode.getType();

        if(checkUnSysNodeType(type)){//非系统约定特殊节点类型，返回true,并且需设置节点值
            if (isPrintAble()) {
                printXmlContent(String.format("%s</%s>", tagValue, tagName));
            }
            // 把标签中text值，存放在xmlNodeStack中
            topNode.setValue(tagValue);
        } else {
            // 打印结束标签
            if (isPrintAble()) {
                printXmlContent("\r\n"
                        + getWhitSpaceByLevel(xmlNodeStack.size())
                        + String.format("</%s>", tagName));
            }
        }

        // 把堆栈中顶部节点内容放入到上一级节点存贮
        pushStack();

    }
    
    /**
     * 通过tagName判断节点是否为二级子节点
     * @param tagName
     * @return
     */
    public boolean checkSecondNodeByName(String tagName){
    	if (tagName.equalsIgnoreCase(ClientConstants.TAG_MSG_HEAD)
                || tagName.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY)) {
    		return true;
    	}
    	return false;
    }

    /**
     * 在开始读取到标签名时，根据标签名创建相应节点数据对应的存贮对象，并且放入堆栈中<br>
     * 1. xml消息格式中二级节点head,body不处理<br>
     * 2. 一级节点request,response，以及object节点下的数据，以map对象存贮<br>
     * 3. list节点下数据以List对象存贮<br>
     * 4. string节点，或是普通string属性节点，以String对象存贮<br>
     * @param tagName 标签名
     */
    public void initStack(String tagName, String attrName) {
    	if(checkSecondNodeByName(tagName)){
            // 1. 二级节点不处理
            return;
        }
        XmlNodeInfo topNode = new XmlNodeInfo();
        //
        topNode.setType(tagName);
        //
        if (isBlack(attrName)) {
            topNode.setKey(tagName);
        } else {
            topNode.setKey(attrName);
        }
        //
        if (checkNodeSaveMapByTagName(tagName)) {
            // 2. 一级节点或是object，创建map
            topNode.setValue(new LinkedHashMap<String, Object>());
        } else if (checkNodeSaveListByTagName(tagName)) {
            // 3. list节点  
            if (isBlack(attrName)) {//TODO:如果为list时，attrName不空，需要报警          

            }
            topNode.setValue(new ArrayList<Object>());
        } else {
            // 4.1 string节点，
            // 4.2 其它string类型节点
            // 4.3 TODO:属性名为约束之外的字符串时，下面存放map数据时，也提供支持
            // topNode.setValue(new String(""));
            //topNode.setValue(new Object());
        }
        xmlNodeStack.push(topNode);
    }
    
    /**
     * 通过tagName名称判断，那些节点需要通过Map进行保存数据
     * @param tagName
     * @return
     */
    public static boolean checkNodeSaveMapByTagName(String tagName){
    	if (tagName.equalsIgnoreCase(ClientConstants.TAG_REQUEST)
                || tagName.equalsIgnoreCase(ClientConstants.TAG_RESPONSE)
                || tagName.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY_OBJECT)
                || tagName.equalsIgnoreCase("xml")
                || tagName.equalsIgnoreCase("item")
    			) {
    		return true;
    	}
    	return false;
    }
    /**
     * 通过tagName名称判断，那些节点需要通过List进行保存数据
     * @param tagName
     * @return
     */
    public static boolean checkNodeSaveListByTagName(String tagName){
    	if (tagName.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY_LIST)) {
    		return true;
    	}
    	if (tagName.endsWith("s")) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * 顶级节点与二级节点request,response,head,body什么都不做
     * @param topkey
     * @return
     */
    public static boolean checkNodeSkipWhenPushByTagName(String topkey){
    	if (topkey.equalsIgnoreCase(ClientConstants.TAG_REQUEST)
                || topkey.equalsIgnoreCase(ClientConstants.TAG_RESPONSE)
                || topkey.equalsIgnoreCase(ClientConstants.TAG_MSG_HEAD)
                || topkey.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY)
                || topkey.equalsIgnoreCase("xml")
    			) {
    		return true;
    	}
    	return false;
    			
    }
    

    public static boolean checkSaveObjByTagName(String topkey){
    	if(topkey.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY_OBJECT)
        || topkey.equalsIgnoreCase(ClientConstants.TAG_MSG_BODY_STRING)
        || topkey.equalsIgnoreCase("item")
    	){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 对值堆栈中的最后两个对象进行处理，顶层的值放到上一值栈中存放<br>
     * 1.对于遍历到request,response,head,body等节点时，不进行处理<br>
     * 2.遍历到object,string节点，把它们放到上级节点list对象中<br>
     * 3.遍历到list节点，或是普通string节点时，把它们放到上级的map对象中去<br>
     */
    @SuppressWarnings("unchecked")
    public void pushStack() {
        XmlNodeInfo topNode = xmlNodeStack.pop();
        String topkey = topNode.getKey();
        if (checkNodeSkipWhenPushByTagName(topkey)) {
            // 1. 顶级节点与二级节点request,response,head,body什么都不做
            xmlNodeStack.push(topNode);
            return;
        }

        //
        XmlNodeInfo lastNode = xmlNodeStack.peek();

        if (checkSaveObjByTagName(topkey)) {// 2.
            // 当前节点为object或string

            if (lastNode.getType().equalsIgnoreCase(ClientConstants.TAG_MSG_BODY_LIST)
            	||lastNode.getKey().endsWith("s")
            		) {// 2.1
                // 上级节点list节点
                ((List<Object>) lastNode.getValue()).add(topNode.getValue());// 把当前节点加入到上级list中存放
            } else {// 2.2
                // 上级节点为object节点
                ((Map<String, Object>) lastNode.getValue()).put(topkey,
                        topNode.getValue());
            }
        } else {
            // 3.当前节点为list，上级一定是map<String,Object>，或其它节点都当map中key值处理
            if (lastNode.getValue() == null) {// 上一节点为值为null时表示为map对象，
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put(topkey, topNode.getValue());
                lastNode.setValue(map);
                lastNode.setType(ClientConstants.TAG_MSG_BODY_OBJECT);
            } else {
                ((Map<String, Object>) lastNode.getValue()).put(topkey,
                        topNode.getValue());// 把当前节点放入上级map中存放
            }
        }
    }

    public static boolean isBlack(String content) {
        if (content == null) {
            return true;
        }
//        if (content.equals("") || content.trim().equals("")) {
//            return true;
//        }
        return false;
    }

    public String getWhitSpaceByLevel(int level) {
        String white = "";
        if (isPrintAble()) {
            for (int i = 0; i < level - 1; i++) {
                white += "    ";
            }
        }
        return white;
    }

    public void printlnXmlContent(String content) {
        System.out.println(content);
    }

    public void printXmlContent(String content) {
        System.out.print(content);
    }
    

    public static String recoverXmlString(String value) {
        return value.replaceAll("&amp;", "&");
    }

    public static class XmlNodeInfo {

        /**
         * 节点类型:list,object,string三种
         */
        private String type;

        /**
         * xml中普通节点时，key为标签tagName<br>
         * 如果为list节点时，key值为的name属性value值<br>
         */
        private String key;

        private Object value;

        public XmlNodeInfo() {
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return this.value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    

    // 标签开始
    public final static int STATUS_READ_TAG_START = 0;

    // 标签结束
    public final static int STATUS_READ_TAG_END = 1;

    public final static String DEFAULT_ENCODING = "utf-8";

    /**
     * xml中标签对应子节点内容存放堆栈
     */
    private Stack<XmlNodeInfo> xmlNodeStack = new Stack<XmlNodeInfo>();

    /**
     * 存放标签值
     */
    private String tagValue = "";

    /**
     * 标签读取位置，标记符：标识标签开始，结束
     */
    private int tagReadStatus;

    /**
     * 是否需要在解析时打印出解析的xml
     */
    private boolean printAble = false;

    /**
     * xml解析工具是否运行在android系统中，true表示是，默认为false表示不是
     */
    private boolean isRunInAndroidJvm = false;

    public String getTagValue() {
        return this.tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public int getTagReadStatus() {
        return this.tagReadStatus;
    }

    public void setTagReadStatus(int tagReadStatus) {
        this.tagReadStatus = tagReadStatus;
    }

    public Stack<XmlNodeInfo> getXmlNodeStack() {
        return this.xmlNodeStack;
    }

    public void setXmlNodeStack(Stack<XmlNodeInfo> xmlNodeStack) {
        this.xmlNodeStack = xmlNodeStack;
    }

    public boolean isPrintAble() {
        return this.printAble;
    }

    public void setPrintAble(boolean printAble) {
        this.printAble = printAble;
    }

    public boolean isRunInGoogleJvm() {
        return this.isRunInAndroidJvm;
    }

    public void setRunInGoogleJvm(boolean isRunInGoogleJvm) {
        this.isRunInAndroidJvm = isRunInGoogleJvm;
    }

    public static void main(String args[]) throws Exception {
        long start = System.currentTimeMillis();
        //把xml解析成map对象
        String filePath ="D:/workplace/portal1.0.1/src/main/webapp/WEB-INF/tplrepo/run/gd/weixin/wxconfig.xml";
                //"testxml/t.xml";    //request.xml";//csp_signPackage.xml";
        Map<String, Object> map = null;
        //map=SaxXmlParser.parseXmlFromUri("http://192.168.0.161:8080/request.xml");//通过uri读取xml
        map = SaxExtendXmlParser.parseXmlFromFile(filePath);//从文件中读xml
        System.out.println("");
        System.out.println(map.toString());
        System.out.println(System.currentTimeMillis() - start + "ms");

        //把map对象转换成xml字符串
        //start = System.currentTimeMillis();
        //System.out.println(MapToXmlHelper.converToResponseXml(map));
        
       System.out.println(MapToSimpleXmlHelper.convertToCustomXml(map, "xml",true));
    }

}
