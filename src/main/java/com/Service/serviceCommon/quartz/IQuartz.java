package com.Service.serviceCommon.quartz;

import java.util.concurrent.TimeUnit;

import org.quartz.Job;


/**
 * 
 * 提供统一的后台任务，包括定时任务、异步任务。
 * 1)定时任务使用quartz框架。
 *   直接使用quartz框架的例子：
 *   //定义一个job类
 *   class MyJob implements Job{
 *       public void execute(org.quartz.JobExecutionContext context) throws org.quartz.JobExecutionException{
 *           System.out.println("hello,world");
 *       }
 *   }
 *   
 *   //加入到quartz框架
 *   String jobName = "first job";
 *   String jobGroup = "default";
 *   String jobClassName = "<packageName>.MyJob";
 *   String cron = "0 0/30 9-17 * * ?";
 *   bgsSrvice.addScheduleJob(jobName,jobGroup,jobClassName,cron);
 *   
 * 2)异步任务。
 * BgsService封装了一个线程池，执行异步任务时从线程池获取线程执行。
 * 
 * a.线程池配置
 * 线程池的配置参数在系统配置文件(settings.properties)中配置：
 * type.portal.threadPool.corePoolSize :线程池维护线程的最少数量
 * type.portal.threadPool.maximumPoolSize :线程池维护线程所允许的空闲时间 
 * type.portal.threadPool.keepAliveTime :线程池维护线程所允许的空闲时间的单位,单位为秒
 * type.portal.threadPool.queueSize :线程池等待队列的最大长度
 * 
 * b.异步任务需要实现AsynTask接口。
 * class MyAsynTask implements AsynTask{
 *     public String getTaskName(){
 *         return "first asyn task";
 *     }
 *     
 *     public void run(){
 *        System.out.println("haha");
 *     }
 * }
 * 
 * @author Administrator
 *
 */
public interface IQuartz {
	/**
	 * 增加一个定时任务
	 * @param jobName 定时任务名称
	 * @param jobGroup 定时任务类型
	 * @param jobClass  定时任务job类，实现Job接口
	 * @param cronExpression cron表达式
	 * @throws Exception
	 */
	void addScheduleJob(String jobName,String jobGroup,String jobClassName,String cronExpression) throws Exception;	

	/**
	 * 增加一个定时任务
	 * @param jobName 定时任务名称
	 * @param jobGroup 定时任务类型
	 * @param jobInstance  定时任务job类的实例，实现Job接口
	 * @param cronExpression cron表达式
	 * @throws Exception
	 */
	void addScheduleJob(String jobName,String jobGroup,Job jobInstance,String cronExpression) throws Exception;	

	/**
	 * 增加一个定时任务，定时任务每隔sInterval秒执行一次
	 * @param jobName 定时任务名称
	 * @param jobGroup 定时任务类型
	 * @param jobClass  定时任务job类，实现Job接口
	 * @param timeUit 间隔时间的时间单位,只支持小时、分、秒
	 * @param interval 执行间隔，单位由timeUnit指定
	 * @throws Exception
	 */
	void addScheduleJob(String jobName,String jobGroup,String jobClassName,TimeUnit timeUit,int interval) throws Exception;
	
	/**
	 * 增加一个定时任务，定时任务每隔sInterval秒执行一次
	 * @param jobName 定时任务名称
	 * @param jobGroup 定时任务类型
	 * @param jobInstance  定时任务job类的实例，实现Job接口
	 * @param timeUit 间隔时间的时间单位,只支持小时、分、秒
	 * @param interval 执行间隔，单位由timeUnit指定
	 * @throws Exception
	 */
	void addScheduleJob(String jobName,String jobGroup,Job jobInstance,TimeUnit timeUit,int interval) throws Exception;
	
	/**
	 * 删除一个定时任务
	 * @param jobName 定时任务名称
	 * @param jobGroup 定时任务类型
	 */	
	void removeScheduleJob(String jobName,String jobGroup) throws Exception;
	
	/**
	 * 立即执行后台任务
	 * @param jobName
	 * @param jobGroup
	 * @throws Exception
	 */
	void executeScheduleJobNow(String jobName,String jobGroup) throws Exception;
}
