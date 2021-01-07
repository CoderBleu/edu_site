package cn.blue.eduservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.eduservice.entity.EduTeacher;
import cn.blue.eduservice.entity.vo.TeacherQuery;
import cn.blue.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/eduService/eduTeacher")
@Api(value = "讲师管理")
// @CrossOrigin
public class EduTeacherController {
    @Resource
    private EduTeacherService eduTeacherService;

    @GetMapping("findAll")
    @ApiOperation(value = "所有讲师列表")
    public Result findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.success().data("list", list);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public Result removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        try {
            if (eduTeacherService.removeById(id)) {
                return Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error();
    }

    /**
     * @param current 当前页
     * @param limit   每页数据量
     * @return 分页数据
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation(value = "分页讲师列表")
    public Result pageTeacherList(@PathVariable long current,
                                  @PathVariable long limit) {
        // 创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        // 调用方法实现分页，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(page, null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        // 相当于创建一个HashMap,然后依次map.put进去
        return Result.success().data("records", records).data("total", total);
    }

    /**
     * @param current      当前页
     * @param limit        每页数据量
     * @param teacherQuery vo对象
     * @return 带条件的分页查询
     * RequestBody: 需要使用post提交方式
     * ResponseBody: 返回数据，返回json数据
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ApiOperation(value = "带条件的分页讲师列表")
    public Result pageTeacherCondition(@PathVariable long current,
                                       @PathVariable long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        // 创建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合,类似于mybatis的动态sql
        String name = teacherQuery.getName();
        if (!StringUtils.isEmpty(name)) {
            name = name.trim();
        }
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 判断值是否为空，如果不为空就拼接条件
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        eduTeacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return Result.success().data("rows", records).data("total", total);
    }

    @ApiOperation(value = "添加讲师接口的方法")
    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ApiOperation(value = "根据讲师id进行查询")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = null;
        try {
            eduTeacher = eduTeacherService.getById(id);
            return Result.success().data("teacher", eduTeacher);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }

    }

    @ApiOperation(value = "讲师修改功能")
    @PutMapping("{id}")
    public Result updateTeacher(EduTeacher eduTeacher, @PathVariable String id) {
        eduTeacher.setId(id);
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}

