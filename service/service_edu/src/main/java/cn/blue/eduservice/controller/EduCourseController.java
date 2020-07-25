package cn.blue.eduservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.vo.CourseInfoVo;
import cn.blue.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
@CrossOrigin
public class EduCourseController {

    @Resource
    private EduCourseService eduCourseService;

    @PostMapping("saveCourseInfo")
    @ApiOperation(value = "添加课程详细信息")
    public Result saveCourseInfo(CourseInfoVo courseInfo) throws Exception {
        if(eduCourseService.saveCourseInfo(courseInfo)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}

