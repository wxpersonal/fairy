package me.weix.fairy.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.weix.fairy.config.quartz.QuartzJobFactory;
import me.weix.fairy.config.quartz.ScheduleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
@Api(value = "QuartzRest")
@Path("quartz")
public class QuartzRest {

	/**
	 * 计划任务map
	 */
	private static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();

	@Autowired
	@Qualifier("scheduler")
	Scheduler scheduler;

	@ApiOperation(value = "添加一个任务")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(value = "add")
	public String add() {
		System.out.println("----------------");
		ScheduleJob job = new ScheduleJob();
		job.setJobId("10001");
		job.setJobName("data_import");
		job.setJobGroup("dataWork");
		job.setJobStatus("1");
		job.setCronExpression("0/5 * * * * ?");
		job.setDesc("数据导入任务");

		JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
		jobDetail.getJobDataMap().put("scheduleJob", job);

		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "is ok";
	}

}
