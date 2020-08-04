package cn.blue.servicemsm.controller;

import cn.blue.commonutils.Result;
import cn.blue.servicemsm.service.MsmService;
import cn.blue.servicemsm.utils.RandomUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Blue
 */
@RestController
@RequestMapping("/eduMsm/msm")
@CrossOrigin
public class MsmController {

    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("发送短信的方法")
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone) {
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.success();
        }
        //2 如果redis获取 不到，进行阿里云发送
        // 生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信的方法
//        boolean isSend = msmService.send(param, phone);
        if (true) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间 15分钟 -> 5TimeUnit.MINUTES
            redisTemplate.opsForValue().set(phone, code, 15, TimeUnit.MINUTES);
            return Result.success();
        } else {
            return Result.error().message("短信发送失败");
        }
    }
}
