package cn.blue.eduservice.client;

import cn.blue.commonutils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Blue
 * 熔断器:出错就使用,比如 宕机
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    /**
     * 出错之后会执行
     */
    @Override
    public Result removeAlyVideo(String id) {
        return Result.error().message("删除视频失败");
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        return Result.error().message("批量删除视频失败");
    }
}
