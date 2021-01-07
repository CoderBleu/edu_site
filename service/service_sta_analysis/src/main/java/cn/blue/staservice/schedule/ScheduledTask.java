package cn.blue.staservice.schedule;

import cn.blue.staservice.service.StatisticsDailyService;
import cn.blue.staservice.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Blue
 * 定时任务
 */
@Component
public class ScheduledTask {

    @Resource
    private StatisticsDailyService staService;

//    /**
//     * 0/5 * * * * ?表示每隔5分钟执行一次这个方法
//     */
//    @Scheduled(cron = "0/* 5 * * * ?")
//    public void task1() {
//        System.out.println("定时任务ScheduledTask执行了..");
//    }

    /**
     * 在每天凌晨1点，把前一天数据进行数据查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
