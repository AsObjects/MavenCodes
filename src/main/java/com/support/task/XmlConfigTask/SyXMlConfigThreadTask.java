package com.support.task.XmlConfigTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.Service.serviceCommon.quartz.AbstrctTask;
import com.constants.IXmlConfig;
import com.constants.Properties.SystemParam;
import com.mccutil.StringUtils;

@Component("syXMlConfigThreadTask")
public class SyXMlConfigThreadTask extends AbstrctTask{

	private static Logger logger = LoggerFactory.getLogger(SyXMlConfigThreadTask.class);
	
	@Resource
	private IXmlConfig conf;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try{
			if(!conf.loadConf()){
				//return ; //TODO 修改时才刷新
			}

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("[SyXMlConfigThreadTask] error to run",e);
		}
	}
	
	
	@PostConstruct
	public void inin() throws Exception{
		String jobName = this.getClass().getSimpleName();
		String jobGroup = this.getClass().getPackage().getName();
		String cron=SystemParam.getProperty("cron_xml_config");
		if(StringUtils.isBlank(cron)){
			cron="0/10 * * * * ?";
		}
		super.quartz.addScheduleJob(jobName, jobGroup, this, "0/10 * * * * ?");
		logger.info("the timer [SyXMlConfigThreadTask] start success");
	}
	
	@PreDestroy
	public void destroy() throws Exception{
		String jobName = this.getClass().getSimpleName();
		String jobGroup = this.getClass().getPackage().getName();
		this.quartz.removeScheduleJob(jobName, jobGroup);
	}

}
