package cn.blue.eduservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.vo.CourseInfoVo;
import cn.blue.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/eduService/eduCourse")
// @CrossOrigin
public class EduCourseController {

    @Resource
    private EduCourseService eduCourseService;

    @PostMapping("saveCourseInfo")
    @ApiOperation(value = "添加课程详细信息")
    public Result saveCourseInfo(CourseInfoVo courseInfo) throws Exception {
        String id = eduCourseService.saveCourseInfo(courseInfo);
        if (!StringUtils.isEmpty(id)) {
            return Result.success().data("courseId", id);
        } else {
            return Result.error();
        }
    }

    @ApiOperation("修改课程信息")
    @PutMapping("updateCourseInfo/{id}")
    public Result updateCourseInfo(@PathVariable String id, @RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.success();
    }

    @ApiOperation("根据课程id查询课程确认后的信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id) {
        CourseInfoVo courseInfoVo = eduCourseService.publishCourseInfo(id);
        return Result.success().data("publishCourse", courseInfoVo);
    }

    @ApiOperation("课程列表")
    @PostMapping("findList/{current}/{limit}")
    public Result getCourseList(@PathVariable long current,
                                @PathVariable long limit,
                                @RequestBody(required = false) EduCourse eduCourse) {
        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(eduCourse.getTitle())) {
            wrapper.eq("title", eduCourse.getTitle().trim());
        }
        if (!StringUtils.isEmpty(eduCourse.getStatus())) {
            wrapper.eq("status", eduCourse.getStatus());
        }
        wrapper.orderByDesc("gmt_create");
        eduCourseService.page(page, wrapper);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return Result.success().data("list", records).data("total", total);
    }

    @ApiOperation("课程最终发布-修改课程状态")
    @PostMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        //设置课程发布状态
        eduCourse.setStatus("Normal");
        if (eduCourseService.updateById(eduCourse)) {
            return Result.success();
        } else {
            return Result.error().message("课程发布失败");
        }
    }

    @ApiOperation("删除课程")
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@PathVariable String courseId) {
        if (eduCourseService.removeCourse(courseId)) {
            return Result.success().message("删除成功");
        } else {
            return Result.error().message("删除失败");
        }
    }
}

