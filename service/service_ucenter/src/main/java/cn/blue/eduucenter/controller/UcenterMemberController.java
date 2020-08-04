package cn.blue.eduucenter.controller;


import cn.blue.commonutils.JwtUtils;
import cn.blue.commonutils.Result;
import cn.blue.commonutils.ordervo.UcenterMemberOrder;
import cn.blue.eduucenter.entity.UcenterMember;
import cn.blue.eduucenter.entity.vo.RegisterVo;
import cn.blue.eduucenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 * 用户中心
 *
 * @author testjava
 * @since 2020-07-30
 */
@RestController
@CrossOrigin
@RequestMapping("/userCenter/member")
public class UcenterMemberController {

    @Resource
    private UcenterMemberService memberService;

    @ApiOperation("登录")
    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember member) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return Result.success().data("token", token);
    }

    @ApiOperation("注册")
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo) {
        if (memberService.register(registerVo)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return Result.success().data("userInfo", member);
    }

    @ApiOperation("根据用户id获取用户信息")
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
}

