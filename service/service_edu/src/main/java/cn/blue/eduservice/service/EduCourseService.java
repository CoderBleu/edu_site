package cn.blue.eduservice.service;

import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param courseInfoVo 课程信息
     * @return 是否成功
     */
    boolean saveCourseInfo(CourseInfoVo courseInfoVo) throws Exception;
}
