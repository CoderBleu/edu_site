package cn.blue.servicemsm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Blue
 * 阿里云短信配置类
 */
@Component
public class ConstantMsmUtil implements InitializingBean {

    @Value("${aliyun.msm.file.keyid}")
    private String keyid;

    @Value("${aliyun.msm.file.keysecret}")
    private String keysecret;

    public static String ACCESS_KEY_SECRET;
    public static String ACCESS_KEY_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }
}
