package cn.blue.servicevod.controller;

import cn.blue.commonutils.Result;
import cn.blue.servicebase.exceptionhandler.GuliException;
import cn.blue.servicevod.service.VodService;
import cn.blue.servicevod.utils.ConstantVodUtils;
import cn.blue.servicevod.utils.InitVodClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
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
            throw new GuliException(500, "删除视频失败");
        }
    }

    @ApiOperation("删除多个阿里云视频的方法")
    @DeleteMapping("deleteBatch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        return vodService.removeMoreAlyVideo(videoIdList) ?
                Result.success().message("批量删除视频成功") : Result.error().message("批量删除视频失败");
    }

    @ApiOperation("根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.success().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new GuliException(500,"获取凭证失败");
        }
    }
}
