package cn.blue.eduservice.controller.front;

import cn.blue.commonutils.Result;
import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.EduTeacher;
import cn.blue.eduservice.service.EduCourseService;
import cn.blue.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Blue
 */
@RestController
@RequestMapping("/eduService/indexFront")
// @CrossOrigin
public class IndexFrontController {

    @Resource
    private EduCourseService courseService;

    @Resource
    private EduTeacherService teacherService;

    @Cacheable(value = "course", key = "'selectIndexCourse'")
    @ApiOperation("查询前4条名师")
    @GetMapping("indexCourse")
    public Result indexCourse() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);
        return Result.success().data("eduList",eduList);
    }

    @Cacheable(value = "teacher", key = "'selectIndexTeacher'")
    @ApiOperation("查询前4条名师")
    @GetMapping("indexTeacher")
    public Result indexTeacher() {
        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);
        return Result.success().data("teacherList",teacherList);
    }

}
