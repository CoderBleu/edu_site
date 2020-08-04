package cn.blue.eduucenter.service.impl;

import cn.blue.commonutils.JwtUtils;
import cn.blue.eduucenter.entity.UcenterMember;
import cn.blue.eduucenter.entity.vo.RegisterVo;
import cn.blue.eduucenter.mapper.UcenterMemberMapper;
import cn.blue.eduucenter.service.UcenterMemberService;
import cn.blue.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-30
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录的方法
     *
     * @param member 登录信息
     * @return 是否可以生成jwt令牌
     */
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if (mobileMember == null) {
            throw new GuliException(500, "手机号不存在");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if (!mobileMember.getPassword().equals(DigestUtils.md5DigestAsHex((mobile + password).getBytes(StandardCharsets.UTF_8)))) {
            throw new GuliException(500, "密码错误");
        }

        //判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(500, "此用户失效");
        }
        // 将id，昵称信息添加进jwt令牌
        return JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 注册的方法
     *
     * @param registerVo 注册提交的信息
     * @return 是否注册成功
     */
    @Override
    public boolean register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(500, "验证码错误");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(500, "手机号已存在");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        //密码需要加密的
        password = DigestUtils.md5DigestAsHex((mobile + password).getBytes(StandardCharsets.UTF_8));
        member.setPassword(password);
        //用户不禁用
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        return baseMapper.insert(member) > 0;
    }
}
