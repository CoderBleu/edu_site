package cn.blue.staservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-14
 */
@RestController
@RequestMapping("/eduStatistic")
public class StatisticsDailyController {

    @Resource
    private StatisticsDailyService staService;

    @ApiOperation("统计某一天注册人数,生成统计数据")
    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable String day) {
        staService.registerCount(day);
        return Result.success();
    }

    @ApiOperation("图表显示，返回两部分数据，日期json数组，数量json数组")
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type,@PathVariable String begin,
                      @PathVariable String end) {
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return Result.success().data(map);
    }
}

