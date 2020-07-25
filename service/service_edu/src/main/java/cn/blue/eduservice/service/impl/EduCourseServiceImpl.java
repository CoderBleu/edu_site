package cn.blue.eduservice.service.impl;

import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.EduCourseDescription;
import cn.blue.eduservice.entity.vo.CourseInfoVo;
import cn.blue.eduservice.mapper.EduCourseDescriptionMapper;
import cn.blue.eduservice.mapper.EduCourseMapper;
import cn.blue.eduservice.service.EduCourseService;
import cn.blue.servicebase.exceptionhandler.GuliException;
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
    
    @Override
    @Transactional(rollbackFor= Exception.class)
    public boolean saveCourseInfo(CourseInfoVo courseInfoVo) throws Exception{
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int rows = baseMapper.insert(eduCourse);
        if(rows > 0) {
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            eduCourseDescription.setDescription(courseInfoVo.getDescription());
            // 在 insert()后，会返回生成的 主键id
            eduCourseDescription.setId(eduCourse.getId());
            rows = eduCourseDescriptionMapper.insert(eduCourseDescription);
            return rows > 0;
        }
        return false;
    }
}
