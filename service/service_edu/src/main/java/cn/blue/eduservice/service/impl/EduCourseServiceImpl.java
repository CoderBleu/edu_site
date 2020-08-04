package cn.blue.eduservice.service.impl;

import cn.blue.eduservice.entity.EduChapter;
import cn.blue.eduservice.entity.EduCourse;
import cn.blue.eduservice.entity.EduCourseDescription;
import cn.blue.eduservice.entity.EduVideo;
import cn.blue.eduservice.entity.frontvo.CourseFrontVo;
import cn.blue.eduservice.entity.frontvo.CourseWebVo;
import cn.blue.eduservice.entity.vo.CourseInfoVo;
import cn.blue.eduservice.mapper.EduChapterMapper;
import cn.blue.eduservice.mapper.EduCourseDescriptionMapper;
import cn.blue.eduservice.mapper.EduCourseMapper;
import cn.blue.eduservice.mapper.EduVideoMapper;
import cn.blue.eduservice.service.EduCourseService;
import cn.blue.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //1 条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            //价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        //下一页
        boolean hasNext = pageParam.hasNext();
        //上一页
        boolean hasPrevious = pageParam.hasPrevious();

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
