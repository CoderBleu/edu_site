package cn.blue.eduservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.eduservice.client.VodClient;
import cn.blue.eduservice.entity.chapter.ChapterVo;
import cn.blue.eduservice.service.EduChapterService;
import cn.blue.eduservice.entity.EduChapter;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/eduService/eduChapter")
@CrossOrigin
public class EduChapterController {

    @Resource
    private EduChapterService chapterService;

    @ApiOperation("课程大纲列表,根据课程id进行查询")
    @GetMapping("getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return Result.success().data("allChapterVideo", list);
    }

    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter) {
        boolean b = chapterService.checkIsUnique(eduChapter);
        if (!b) {
            return Result.error().data("msg", "章节序号不是唯一！");
        }
        if (chapterService.save(eduChapter)) {
            return Result.success().data("msg", "添加成功");
        } else {
            return Result.error().message("修改失败");
        }
    }

    @ApiOperation("根据章节id查询")
    @GetMapping("getChapterInfo/{chapterId}")
    public Result getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Result.success().data("chapter", eduChapter);
    }

    @ApiOperation("修改章节")
    @PutMapping("updateChapter/{id}")
    public Result updateChapter(@PathVariable String id,@RequestBody EduChapter eduChapter) {
        if (chapterService.updateById(eduChapter)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ApiOperation("删除章节")
    @DeleteMapping("{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId) {
        if (chapterService.deleteChapter(chapterId)) {
            return Result.success().message("删除成功");
        } else {
            return Result.error().message("删除失败");
        }
    }
}

