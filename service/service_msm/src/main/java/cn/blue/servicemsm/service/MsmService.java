package cn.blue.servicemsm.service;

import java.util.Map;

public interface MsmService {
    /**
     * 发送短信的方法
     * @param param 验证码
     * @param phone 手机号
     * @return 是否验证成功
     */
    boolean send(Map<String, Object> param, String phone);
}
