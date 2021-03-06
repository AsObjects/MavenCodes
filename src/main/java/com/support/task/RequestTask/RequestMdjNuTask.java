package com.support.task.RequestTask;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.Service.serviceCommon.quartz.AbstrctTask;
import com.Service.serviceCommon.quartz.TaskUtil;
import com.constants.BaiDuRequestUrl;
import com.mccutil.HttpKit;


@Component("RequestMdjNuTask")
public class RequestMdjNuTask extends AbstrctTask {

	private String str=null;
	
	private String tbs=null;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CloseableHttpClient httpClient=null;
		try{
			httpClient=HttpClients.createDefault();
			HttpKit.getRequest(BaiDuRequestUrl.GET_COOKIES, httpClient);
			String token=HttpKit.getRequest(BaiDuRequestUrl.GET_TOKEN, httpClient);
			token=token.substring(token.indexOf("='")+2,
					token.indexOf("';"));
			HttpKit.postRequest(BaiDuRequestUrl.LOGIN_BAIDU, TaskUtil.getLoginParam(token), httpClient);
			str=HttpKit.getRequest(BaiDuRequestUrl.LOGIN_MDJNU, httpClient);
			tbs=str.substring(str.indexOf("PageData.tbs = \"")+16,str.indexOf("\";PageData.forum"));
			Document doc=Jsoup.parse(str);
			Elements element=doc.getElementsByClass("t_con");
			for(int i=0 ;i<element.size();i++){
				int num=0;
				try{
					num=Integer.parseInt(element.get(i).
							getElementsByClass("threadlist_li_left").get(0).
							getElementsByClass("threadlist_rep_num").get(0).
							text());
				}catch(Exception e){
					continue;
				}
				
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
					//System.out.println("href:"+href+",uid:"+uid+",author:"+author);
					//执行get就相当于进帖子
					HttpKit.getRequest(BaiDuRequestUrl.LOGIN_SPECIFIC_NOTE+href, httpClient);
					String tid=href.substring(href.lastIndexOf("/")+1);
					//post发帖
					String html=HttpKit.postRequest(BaiDuRequestUrl.SEND_NOTE, 
							             TaskUtil.getSendMessageParam("牡丹江师范学院", "156696", tid, tbs, TaskUtil.getContent()),
							             httpClient);
					if(html.contains("\"no\":0,\"err_code\":0")){
				          // System.out.println("在牡丹江师范学院吧成功抢到一个二楼");
				        }else{
				        	if(html.indexOf("<!DOCTYPE")<0){
				        		//System.out.println("回帖失败了,错误码信息："+html);	        		
				        	}
				        }
					str=HttpKit.getRequest(BaiDuRequestUrl.LOGIN_MDJNU, httpClient);
					tbs=str.substring(str.indexOf("PageData.tbs = \"")+16,str.indexOf("\";PageData.forum"));
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

	@PostConstruct
	public void inin() throws Exception{
		String jobName = this.getClass().getSimpleName();
		String jobGroup = this.getClass().getPackage().getName();
		super.quartz.addScheduleJob(jobName, jobGroup, this, "0 0/20 * * * ?");
	}
	
	@PreDestroy
	public void destroy() throws Exception{
		String jobName = this.getClass().getSimpleName();
		String jobGroup = this.getClass().getPackage().getName();
		this.quartz.removeScheduleJob(jobName, jobGroup);
	}
}
