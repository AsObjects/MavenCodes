package Quartz;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;


@Component("test")
public class Test extends AbstrctTask{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//System.out.println("aaaaa");
	}
	@PostConstruct
	public void init() throws Exception{
		String jobName = this.getClass().getSimpleName();
		String jobGroup = this.getClass().getPackage().getName();
		super.quartz.addScheduleJob(jobName, jobGroup, this, "0/30 * * * * ?");
		
	}
	@PreDestroy
	public void desply() throws Exception{
		// 注销定时任务
		String jobName = this.getClass().getSimpleName();
		String jobGroup = this.getClass().getPackage().getName();
		this.quartz.removeScheduleJob(jobName, jobGroup);
	}
}
