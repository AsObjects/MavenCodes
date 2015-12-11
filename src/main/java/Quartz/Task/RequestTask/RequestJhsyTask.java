package Quartz.Task.RequestTask;

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

import ParamClass.BaiDuRequestUrl;
import Quartz.AbstrctTask;
import Quartz.TaskUtil;
import SystemParam.Properties.SystemParam;
import Util.HttpKit;
@Component("requestJhsyTask")
public class RequestJhsyTask extends AbstrctTask {

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
			str=HttpKit.getRequest(BaiDuRequestUrl.LOGIN_JHSY, httpClient);
			tbs=str.substring(str.indexOf("'tbs'")+8,str.indexOf("PageData.page")-15);
			Document doc=Jsoup.parse(str);
			Elements element=doc.getElementsByClass("threadlist_title");
			for(int i=0;i<15;i++){
				//这里是获取零回复帖子的链接，这个是相对定位的链接   /p/4175153432
				String href=element.get(0).getElementsByTag("a").attr("href");
				//执行get就相当于进帖子
				HttpKit.getRequest(BaiDuRequestUrl.LOGIN_SPECIFIC_NOTE+href, httpClient);
				String tid=href.substring(href.lastIndexOf("/")+1);
				//post发帖
				String html=HttpKit.postRequest(BaiDuRequestUrl.SEND_NOTE, 
						TaskUtil.getSendMessageParam("镜中花_水中月", "5048102", tid, tbs, TaskUtil.getContent()),
						httpClient);
				if(html.contains("\"no\":0,\"err_code\":0")){
					//System.out.println("镜花水月吧水贴成功");
				}else{
					if(html.indexOf("<!DOCTYPE")<0){
						//System.out.println("回帖失败了,错误码信息："+html);	        		
					}
				}
				str=HttpKit.getRequest(BaiDuRequestUrl.LOGIN_JHSY, httpClient);
				tbs=str.substring(str.indexOf("'tbs'")+8,str.indexOf("PageData.page")-15);		
				Thread.sleep(2000);
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
		SystemParam.getProperty("cron_java");
        super.quartz.addScheduleJob(jobName, jobGroup, this, "0 0 13 * * ?");
	}
	
	@PreDestroy
	public void destroy() throws Exception{
		String jobName = this.getClass().getSimpleName();
		String jobGroup = this.getClass().getPackage().getName();
		this.quartz.removeScheduleJob(jobName, jobGroup);
	}
}