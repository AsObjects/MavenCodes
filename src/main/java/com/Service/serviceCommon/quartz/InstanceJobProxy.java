package com.Service.serviceCommon.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 一般增加job传入的是job类名，在执行时根据类名实例化job对象，
 * 但是某些情况下，传入的是job实例，为了匹配quarz,增加一个代理处理这种情况。
 * 代理类从context参数中获取真正的job对象
 * @author Administrator
 *
 */

public class InstanceJobProxy implements Job{

	public static final String JOB_INSTANCE_KEY = "job_instance_key";
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		Job realJob = (Job)context.getJobDetail().getJobDataMap().get(JOB_INSTANCE_KEY);
		realJob.execute(context);	
	}

}
