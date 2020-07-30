package cn.blue.eduservice.service.impl;

import cn.blue.eduservice.entity.EduChapter;
import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.EduCourseDescription;
import cn.blue.eduservice.entity.EduVideo;
import cn.blue.eduservice.entity.vo.CourseInfoVo;
import cn.blue.eduservice.mapper.EduChapterMapper;
import cn.blue.eduservice.mapper.EduCourseDescriptionMapper;
import cn.blue.eduservice.mapper.EduCourseMapper;
import cn.blue.eduservice.service.EduCourseService;
import cn.blue.servicevod.servicebase.exceptionhandler.GuliException;
import cn.blue.eduservice.mapper.EduVideoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Resource
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;
    @Resource
    private EduVideoMapper videoMapper;
    @Resource
    private EduChapterMapper chapterMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveCourseInfo(CourseInfoVo courseInfoVo) throws Exception {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int rows = baseMapper.insert(eduCourse);
        if (rows > 0) {
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            eduCourseDescription.setDescription(courseInfoVo.getDescription());
            // 在 insert()后，会返回生成的 主键id
            eduCourseDescription.setId(eduCourse.getId());
            rows = eduCourseDescriptionMapper.insert(eduCourseDescription);
            return eduCourse.getId();
        }
        return null;
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVo 课程信息
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionMapper.updateById(description);
    }

    /**
     * 根据课程id查询课程确认信息
     *
     * @param id 课程id
     * @return 课程信息
     */
    @Override
    public CourseInfoVo publishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeCourse(String courseId) {
        //1 根据课程id删除小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        videoMapper.delete(videoWrapper);
        //2 根据课程id删除章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        chapterMapper.delete(chapterWrapper);
        //3 根据课程id删除描述
        QueryWrapper<EduCourseDescription> descWrapper = new QueryWrapper<>();
        descWrapper.eq("id", courseId);
        eduCourseDescriptionMapper.delete(descWrapper);
        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        return result > 0;
    }
}
