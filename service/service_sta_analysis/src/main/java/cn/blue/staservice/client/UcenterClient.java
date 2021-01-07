package cn.blue.staservice.client;

import cn.blue.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    @GetMapping("/userCenter/member/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day);
}
