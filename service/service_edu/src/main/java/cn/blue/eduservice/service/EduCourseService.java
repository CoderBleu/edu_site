package cn.blue.eduservice.service;

import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.frontvo.CourseFrontVo;
import cn.blue.eduservice.entity.frontvo.CourseWebVo;
import cn.blue.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 报错课程信息
     *
     * @param courseInfoVo 课程信息
     * @return 是否成功
     * @throws Exception 异常
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo) throws Exception;

    /**
     * 修改课程信息
     *
     * @param courseInfoVo 课程信息
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     *
     * @param id 课程id
     * @return 课程信息
     */
    CourseInfoVo publishCourseInfo(String id);

    /**
     * 删除课程
     *
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean removeCourse(String courseId);

    /**
     * 得到课程的基本信息
     * @param courseId 课程ID
     * @return 课程的基本信息
     */
    CourseWebVo getBaseCourseInfo(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);
}
