package cn.blue.eduservice.service;

import cn.blue.eduservice.entity.EduChapter;
import cn.blue.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
public interface EduChapterService extends IService<EduChapter> {
    /**
     * 课程大纲列表,根据课程id进行查询
     * @param courseId 课程id
     * @return 课程大纲列表
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除章节的方法
     * @param chapterId 章节的id
     * @return 是否成功
     */
    boolean deleteChapter(String chapterId);

    /**
     * 检查此章节的序号是否唯一
     * @param eduChapter 序号
     * @return 是否唯一
     */
    boolean checkIsUnique(EduChapter eduChapter);
}
