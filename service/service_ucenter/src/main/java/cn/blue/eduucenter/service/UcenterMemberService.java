package cn.blue.eduucenter.service;

import cn.blue.eduucenter.entity.UcenterMember;
import cn.blue.eduucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-30
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    /**
     * 注册
     * @param registerVo 注册提交的信息
     * @return 是否成功
     */
    boolean register(RegisterVo registerVo);

    /**
     * 登录
     * @param member 登录信息
     * @return token
     */
    String login(UcenterMember member);

    /**
     * 微信登录
     * @param openid
     * @return 用户信息
     */
    UcenterMember getOpenIdMember(String openid);
}
