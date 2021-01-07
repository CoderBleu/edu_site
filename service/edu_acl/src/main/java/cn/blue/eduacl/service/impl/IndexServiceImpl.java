package cn.blue.eduacl.service.impl;

import cn.blue.eduacl.entity.Role;
import cn.blue.eduacl.entity.User;
import cn.blue.eduacl.service.IndexService;
import cn.blue.eduacl.service.PermissionService;
import cn.blue.eduacl.service.RoleService;
import cn.blue.eduacl.service.UserService;
import cn.blue.servicebase.exceptionhandler.GuliException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUsername(username);
        if (StringUtils.isEmpty(user)) {
            throw new GuliException(500, "用户或者密码错误");
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if (roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getUsername());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     *
     * @param username
     * @return
     */
    @Override
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);
        if (StringUtils.isEmpty(user)) {
            throw new GuliException(500, "用户账号或密码错误");
        }
        // 管理员
//        if (Objects.equals(user.getUsername(), "admin")) {
//            return permissionService.selectAdminPermissionByUserId(user.getId());
//            //根据用户id获取用户菜单权限
//        } else {
//            return permissionService.selectPermissionByUserId(user.getId());
//        }
        return permissionService.selectAdminPermissionByUserId(user.getId());
    }


}
