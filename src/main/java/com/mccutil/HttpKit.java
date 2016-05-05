package com.mccutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.sun.tools.internal.ws.processor.model.Response;

//4.5.1�� httpClient
/**
 * @author 
 * 1 创建HttpClient对象
 * 2 创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。
 * 3 如果需要发送请求参数，可调用HttpGet、HttpPost共同的setParams(HetpParams params)方法来添加请求参数；
 * 对于HttpPost对象而言，也可调用setEntity(HttpEntity entity)方法来设置请求参数。
 * 4 调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。
 * 5 调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；
 * 调用HttpResponse的getEntity()方法可获取HttpEntity对象，
 * 该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。
 * 6 释放连接。无论执行方法是否成功，都必须释放连接
 */
public class HttpKit {
	
	public static String sendHttpByGetRequest(String []urls , String head){
		CloseableHttpClient httpClient=HttpClients.createDefault();
		CloseableHttpResponse response=null;
		HttpEntity entity=null;		
		try{
			StringBuffer sb=null;
			for(String url:urls){
				sb=new StringBuffer();
				HttpGet get=new HttpGet(url);
				RequestConfig rc=RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
				get.setConfig(rc);
				/*
				 * getReq.addHeader("Accept", "application/json, text/javascript, ; q=0.01");
				 * getReq.addHeader("Accept-Encoding", "gzip,deflate,sdch");
				 * getReq.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
				 * getReq.addHeader("Content-Type", "text/html; charset=UTF-8");
				 * getReq.addHeader("Host", HOST);
				 * getReq.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.76 Safari/537.36");
				 */
				get.setHeader("Content-Type","charset=UTF-8");
				
				response=httpClient.execute(get);
				entity = response.getEntity();
				System.out.println("消息头 : [ "+response.getStatusLine()+" ]");
				Header [] header=response.getAllHeaders();
				System.out.println("==================解析开始================");
				for(int i=0;header.length>i;i++){
					System.out.println(header[i]);
				}
				System.out.println("==================解析结束================");
				InputStream is=entity.getContent();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
				BufferedReader br = new BufferedReader(isr);
				/*
				 * java.io.IOException: Attempted read from closed stream.
				 */
				sb.append(EntityUtils.toString(entity,"UTF-8"));
			}
            return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				System.out.println("the heepclient or response colosed is fail . . . ");
			}
		}
		
		return null;
	}
	/*
	 *http,post
	 */
	
	@SuppressWarnings("rawtypes")
	public static String sendHttpByPostRequest(String url,Map<String,String>map){
		  CloseableHttpClient httpClient = HttpClients.createDefault();
		  HttpPost post = new HttpPost(url);
		  RequestConfig config = RequestConfig.custom()
		    .setConnectionRequestTimeout(10000).setConnectTimeout(10000)
		    .setSocketTimeout(10000).build();
		  CloseableHttpResponse response = null;
		  try {
		   List formparams = new ArrayList();
		   BasicNameValuePair bnv=null;
		   for(String key:map.keySet()){
			   bnv=new BasicNameValuePair(key, map.get(key));
			   formparams.add(bnv);
		   }
		   post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
		   //post.setEntity(new StringEntity("<xml><userName>a</userName></xml>"));
		   post.setConfig(config);
		   response = httpClient.execute(post);
		   HttpEntity entity = response.getEntity();
		   
		   System.out.println("消息头 : [ "+response.getStatusLine()+" ]");
			Header [] header=response.getAllHeaders();
			System.out.println("==================开始解析================");
			for(int i=0;header.length>i;i++){
				System.out.println(header[i]);
			}
			System.out.println("==================解析结束================");
			InputStream is=entity.getContent();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
			BufferedReader br = new BufferedReader(isr);
			
			
		   return "返回"+EntityUtils.toString(entity);
		   
		  } catch (Exception e) {
			  System.out.println("the post request is fail . . .");
		  } finally{
			  try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				System.out.println("close response is fail . . .");
			}
		  }
		  return null;
	}
	
	/*
	 * 没关闭httpclient的get请求
	 */
	public static String getRequest(String url,CloseableHttpClient httpClient) 
			                                     throws ClientProtocolException, IOException{
		HttpGet get=new HttpGet(url);
		RequestConfig rc=RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
		get.setConfig(rc);
		get.setHeader("Content-Type","charset=UTF-8");
		CloseableHttpResponse response = null;
		/*Header [] header=response.getAllHeaders();
		System.out.println("==================解析开始================");
		for(int i=0;header.length>i;i++){
			System.out.println(header[i]);
		}
		System.out.println("==================解析结束================");*/
	   String html=null;
	   try{
		   response=httpClient.execute(get);
		   HttpEntity entity = response.getEntity();
		   html=EntityUtils.toString(entity);			   
		   //System.out.println("消息头 : [ "+response.getStatusLine()+" ];请求地址:["+url+"]");
	   }finally{
		   response.close();
	   }
		return html;
	}

	/*
	 * 没关闭httpclient的post请求
	 */
	public static String postRequest(String url,Map<String,String>map,CloseableHttpClient httpClient)
			                                             throws ClientProtocolException, IOException{
		 HttpPost post = new HttpPost(url);
		  RequestConfig config = RequestConfig.custom()
		    .setConnectionRequestTimeout(10000).setConnectTimeout(10000)
		    .setSocketTimeout(10000).build();
		   List formparams = new ArrayList();
		   BasicNameValuePair bnv=null;
		   for(String key:map.keySet()){
			   bnv=new BasicNameValuePair(key, map.get(key));
			   formparams.add(bnv);
		   }
		   post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
		   //post.setEntity(new StringEntity("<xml><userName>a</userName></xml>"));
		   post.setConfig(config);
		   String html=null;
		   CloseableHttpResponse response=null;
		   try{
			   response= httpClient.execute(post);
			   HttpEntity entity = response.getEntity();
			   html=EntityUtils.toString(entity);			   
			   //System.out.println("消息头 : [ "+response.getStatusLine()+" ];请求地址:["+url+"]");
		   }finally{
			   response.close();
		   }
		   /*if(html.contains("\"no\":0,\"err_code\":0")){
	           System.out.println("在java吧成功抢到一个二楼");
	        }else{
	        	if(html.indexOf("<!DOCTYPE")<0){
	        		System.out.println("回帖失败了,错误码信息："+html);	        		
	        	}
	        }*/
			/*Header [] header=response.getAllHeaders();
			System.out.println("==================开始解析================");
			for(int i=0;header.length>i;i++){
				System.out.println(header[i]);
			}
			System.out.println("==================解析结束================");*/
		   
		   return html;
	}
}
