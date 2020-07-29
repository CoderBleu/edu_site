package cn.blue.servicevod.controller;

import cn.blue.commonutils.Result;
import cn.blue.servicevod.service.VodService;
import cn.blue.servicevod.servicebase.exceptionhandler.GuliException;
import cn.blue.servicevod.utils.ConstantVodUtils;
import cn.blue.servicevod.utils.InitVodClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Blue
 */
@RestController
@RequestMapping("/eduVod/video")
@CrossOrigin
public class VodController {

    @Resource
    private VodService vodService;

    @ApiOperation("上传视频到阿里云")
    @PostMapping("uploadAlyVideo")
    public Result uploadAlyVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return Result.success().data("videoId", videoId);
    }

    @ApiOperation("根据视频id删除阿里云视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public Result removeAlyVideo(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return Result.success().message("删除视频成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    @ApiOperation("删除多个阿里云视频的方法")
    @DeleteMapping("deleteBatch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        return vodService.removeMoreAlyVideo(videoIdList) ?
                Result.success().message("批量删除视频成功") : Result.error().message("批量删除视频失败");
    }
}
