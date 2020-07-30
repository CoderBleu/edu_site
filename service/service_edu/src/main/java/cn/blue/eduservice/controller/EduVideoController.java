package cn.blue.eduservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.eduservice.client.VodClient;
import cn.blue.eduservice.service.EduVideoService;
import cn.blue.eduservice.entity.EduVideo;
import cn.blue.servicevod.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/eduService/eduVideo")
@CrossOrigin
public class EduVideoController {
    @Resource
    private EduVideoService videoService;
    @Resource
    private VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo) {
        // 判断小节序号是否唯一
        if (videoService.checkIsUnique(eduVideo)) {
            return Result.error().message("小节序号是唯一的！");
        }
        if (videoService.save(eduVideo)) {
            return Result.success().message("添加成功");
        } else {
            return Result.error().message("添加失败");
        }
    }


    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            Result result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 500) {
                throw new GuliException(500, "熔断器执行...");
            }
        }
        if (videoService.removeById(id)) {
            return Result.success().message("删除成功");
        } else {
            return Result.error().message("删除失败");
        }
    }

    @ApiOperation("根据id查询小节信息")
    @GetMapping("findVideoInfoById/{id}")
    public Result findVideoInfoById(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        if (!StringUtils.isEmpty(video)) {
            return Result.success().data("video", video);
        } else {
            return Result.error();
        }
    }

    @ApiOperation("修改小节")
    @PutMapping("updateVideo/{id}")
    public Result updateVideo(@PathVariable String id, @RequestBody EduVideo eduVideo) {
        if (videoService.updateById(eduVideo)) {
            return Result.success().message("修改成功");
        } else {
            return Result.error().message("修改失败");
        }
    }
}

