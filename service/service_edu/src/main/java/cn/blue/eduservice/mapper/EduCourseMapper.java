package cn.blue.eduservice.mapper;

import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.frontvo.CourseWebVo;
import cn.blue.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * 根据课程id查询课程确认信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    CourseInfoVo getPublishCourseInfo(String courseId);

    /**
     * 查询课程基本信息
     * @param courseId 课程id
     * @return 课程基本信息
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
