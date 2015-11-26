package Service.ServiceUtil;

import java.io.IOException;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TieBaRequestService {
	
	private String firstRequest="http://www.baidu.com";
	
	private String sencendRequest="https://passport.baidu.com/v2/api/?getapi&class=login&tpl=pp&tangram=true";
	
	private String ThreadRequestLogin="https://passport.baidu.com/v2/api/?login";
	
	private String fourRequestJava="http://tieba.baidu.com/f?kw=%BE%B5%D6%D0%BB%A8_%CB%AE%D6%D0%D4%C2";
	
	private CloseableHttpClient httpClient=HttpClients.createDefault();
	
	private CloseableHttpResponse response=null;
	
	private HttpEntity entity=null;	
	
	private String tbs="";
	
	private String str="";
	/*
	 * 向百度发送请求
	 */
	public void loginBaiDu(){
		
		try{
			this.getMessage(firstRequest);
			String token=this.getMessage(sencendRequest);
			token=token.substring(token.indexOf("='")+2,
					token.indexOf("';"));
			System.out.println("token:   "+token);
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
			this.postMessage(ThreadRequestLogin, paramMap);
			str=this.getMessage(fourRequestJava);
			tbs=str.substring(str.indexOf("PageData.tbs = \"")+16,str.indexOf("\";PageData.forum"));
			System.out.println(" tbs:   "+tbs);
			Document doc=Jsoup.parse(str);
			Elements element=doc.getElementsByClass("t_con");
			for(int i=0 ;i<2;i++){
				int num=Integer.parseInt(element.get(i).
						getElementsByClass("threadlist_li_left").get(0).
						getElementsByClass("threadlist_rep_num").get(0).
						text());
				if(num ==0){
					System.out.println("帖子名："+element.get(i).getElementsByClass("threadlist_li_right").get(0).
							                                  getElementsByClass("threadlist_lz").get(0).
							                                  getElementsByTag("a").get(0).attr("title"));
					//这里是获取零回复帖子的链接，这个是相对定位的链接   /p/4175153432
					String href=element.get(i).getElementsByClass("threadlist_li_right").get(0).
							                   getElementsByClass("threadlist_lz").get(0).
							                   getElementsByTag("a").get(0).attr("href");
					String uid=element.get(i).getElementsByClass("threadlist_li_right").get(0).
												getElementsByClass("threadlist_author").get(0).
												getElementsByTag("span").get(0).attr("data-field");
					String author=element.get(i).getElementsByClass("threadlist_li_right").get(0).
			                   					getElementsByClass("threadlist_author").get(0).
		                   						getElementsByTag("span").get(0).attr("title");
					System.out.println("href:"+href+",uid:"+uid+",author:"+author);
					//执行get就相当于进帖子
					String content=this.getMessage("http://tieba.baidu.com"+href);
					String tid=href.substring(href.lastIndexOf("/")+1);
					//post发帖
					Map<String,String> paramMaps=new HashMap<String, String>();
					paramMaps.put("kw", "java");
					paramMaps.put("ie", "utf-8");
					paramMaps.put("fid", "693735");
					paramMaps.put("tid", tid);
					paramMaps.put("floor_num", "1");
					paramMaps.put("rich_text", "1");
					paramMaps.put("content", "吊炸天");
					paramMaps.put("_type_", "reply");
					paramMaps.put("tbs", tbs);
					this.postMessage("http://tieba.baidu.com/f/commit/post/add", paramMaps);
					//str=this.getMessage(fourRequestJava);
					//tbs=str.substring(str.indexOf("PageData.tbs = \"")+16,str.indexOf("\";PageData.forum"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				httpClient.close();
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getMessage(String url) throws ClientProtocolException, IOException{
		HttpGet get=new HttpGet(url);
		RequestConfig rc=RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
		get.setConfig(rc);
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
		return EntityUtils.toString(entity);
	}
	
	public void postMessage(String url,Map<String,String>map) throws Exception{
		 HttpPost post = new HttpPost(url);
		  RequestConfig config = RequestConfig.custom()
		    .setConnectionRequestTimeout(10000).setConnectTimeout(10000)
		    .setSocketTimeout(10000).build();
		  CloseableHttpResponse response = null;
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
		   String html=EntityUtils.toString(entity);
		   if(html.contains("\"no\":0,\"err_code\":0")){
	           System.out.println("在java吧成功抢到一个二楼");
	        }else{
	            System.out.println("回帖失败了,错误码信息："+html);
	        }
		   System.out.println("消息头 : [ "+response.getStatusLine()+" ]");
			Header [] header=response.getAllHeaders();
			System.out.println("==================开始解析================");
			for(int i=0;header.length>i;i++){
				System.out.println(header[i]);
			}
			System.out.println("==================解析结束================");
	}
	
	public static void main(String[] args) {
		new TieBaRequestService().loginBaiDu();
	}
}
