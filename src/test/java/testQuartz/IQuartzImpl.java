package testQuartz;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

@Service
public class IQuartzImpl implements IQuartz {

	//定时任务：Quartz
	Scheduler scheduler;
	
	/**
	 * 初始化定时任务框架quartz
	 * @throws Exception
	 */
	private void initQuartz() throws Exception{
		//定时任务
		this.scheduler = StdSchedulerFactory.getDefaultScheduler();
		this.scheduler.start();
	}
	
	@PostConstruct
	public void init(){
		try {
			this.initQuartz();		
		} catch (Throwable e) {
			
		}
	}
	
	@PreDestroy
	public void destroy() {
		try {
			//关闭quartz
			scheduler.shutdown();
		} catch (SchedulerException e) {
			
		}

	}
	
	
	/**
	 * 传入trigger,增加一个定时任务，
	 * @param jobName
	 * @param jobGroup
	 * @param jobClassName
	 * @param trigger
	 * @throws Exception
	 */
	void addScheduleJob(String jobName, String jobGroup,
			String jobClassName,Object jobInstance,Trigger trigger) throws Exception{
		//trigger的name和group和job一致
		trigger.setName(jobName);
		trigger.setGroup(jobGroup);
		
		//根据job类名，获取class对象
		Class<?> jobClass = jobInstance == null ? Class.forName(jobClassName) : InstanceJobProxy.class;
		
		//构造定时任务对象
		JobDetail jobDetail =
                new JobDetail(jobName, jobGroup,jobClass);
		
		//构造传递给job的参数
		JobDataMap jobDataMap = new JobDataMap();
        jobDetail.setJobDataMap(jobDataMap);
		
        //特殊处理传入job实例，将job实例加入到参数中传给代理
        if (jobInstance != null){
			jobDataMap.put(InstanceJobProxy.JOB_INSTANCE_KEY,jobInstance);
		}
		
		//启动定时任务
		this.scheduler.scheduleJob(jobDetail,trigger);	
		
	}

	
	@Override
	public void addScheduleJob(String jobName, String jobGroup,
			String jobClassName, String cronExpression) throws Exception {
		
		//构造定时任务触发器
		Trigger trigger =  new CronTrigger(jobName ,jobGroup,cronExpression);
		
		addScheduleJob(jobName, jobGroup, jobClassName, null, trigger);

	}

	@Override
	public void addScheduleJob(String jobName, String jobGroup,
			Job jobInstance, String cronExpression) throws Exception {
		//构造定时任务触发器
		Trigger trigger =  new CronTrigger(jobName ,jobGroup,cronExpression);
		
		//启动定时任务
		this.addScheduleJob(jobName,jobGroup,null,jobInstance,trigger);	

	}

	@Override
	public void addScheduleJob(String jobName, String jobGroup,
			String jobClassName, TimeUnit timeUit, int interval)
			throws Exception {
		Trigger trigger = null;
		switch(timeUit){
		case HOURS:
			trigger = TriggerUtils.makeHourlyTrigger(interval);
			break;
		case MINUTES:
			trigger = TriggerUtils.makeMinutelyTrigger(interval);
			break;
		case SECONDS:
			trigger = TriggerUtils.makeSecondlyTrigger(interval);
			break;
		default:
				throw new RuntimeException("can't support time unit:" + timeUit.name());
		}

		//启动定时任务
		this.addScheduleJob(jobName,jobGroup,jobClassName,null,trigger);
	}

	@Override
	public void addScheduleJob(String jobName, String jobGroup,
			Job jobInstance, TimeUnit timeUit, int interval) throws Exception {
		Trigger trigger = null;
		switch(timeUit){
		case HOURS:
			trigger = TriggerUtils.makeHourlyTrigger(interval);
			break;
		case MINUTES:
			trigger = TriggerUtils.makeMinutelyTrigger(interval);
			break;
		case SECONDS:
			trigger = TriggerUtils.makeSecondlyTrigger(interval);
			break;
		default:
				throw new RuntimeException("can't support time unit:" + timeUit.name());
		}

		//启动定时任务
		this.addScheduleJob(jobName,jobGroup,null,jobInstance,trigger);	

	}

	@Override
	public void removeScheduleJob(String jobName, String jobGroup)
			throws Exception {
		JobDetail jobDetail = this.scheduler.getJobDetail(jobName,jobGroup);
		if (jobDetail != null){
			this.scheduler.deleteJob(jobName, jobGroup);
		}
	}

	@Override
	public void executeScheduleJobNow(String jobName, String jobGroup)
			throws Exception {
		  // 首先查找该task
        JobDetail jobDetail = this.scheduler.getJobDetail(jobName,jobGroup);
        if (jobDetail != null) {
            // 设置trigger
            this.scheduler.triggerJob(jobName, jobGroup);
        }
	}

}
