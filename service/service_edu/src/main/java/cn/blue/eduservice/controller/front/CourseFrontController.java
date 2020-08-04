package cn.blue.eduservice.controller.front;

import cn.blue.commonutils.Result;
import cn.blue.commonutils.ordervo.CourseWebVoOrder;
import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.chapter.ChapterVo;
import cn.blue.eduservice.entity.frontvo.CourseFrontVo;
import cn.blue.eduservice.entity.frontvo.CourseWebVo;
import cn.blue.eduservice.service.EduChapterService;
import cn.blue.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Blue
 */
@RestController
@RequestMapping("/eduService/courseFront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("条件查询带分页查询课程")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return Result.success().data(map);
    }

    @ApiOperation("课程详情的方法")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        return Result.success().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    @ApiOperation("根据课程id查询课程信息")
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}












