package cn.blue.eduservice.controller;

import cn.blue.commonutils.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author Blue
 * CrossOrigin  解决跨域
 */
@RestController
@RequestMapping("/eduService/user")
// @CrossOrigin
public class EduLoginController {

    /**
     * login
     */
    @PostMapping("login")
    public Result login() {
        return Result.success().data("token", "admin");
    }

    /**
     * info
     */
    @GetMapping("info")
    public Result info() {
        return Result.success().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
