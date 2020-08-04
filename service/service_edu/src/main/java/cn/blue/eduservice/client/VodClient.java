package cn.blue.eduservice.client;

import cn.blue.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 调用的服务名称
 *
 * @author Blue
 */
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    /**
     * 定义调用的方法路径
     * 根据视频id删除阿里云视频
     * PathVariable注解一定要指定参数名称，否则出错
     *
     * @param id 视频id
     */
    @DeleteMapping("/eduVod/video/removeAlyVideo/{id}")
    public Result removeAlyVideo(@PathVariable("id") String id);

    /**
     * 定义调用删除多个视频的方法
     * 删除多个阿里云视频的方法
     * 参数多个视频id  List videoIdList
     *
     * @param videoIdList 多个视频id
     */
    @DeleteMapping("/eduVod/video/deleteBatch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
