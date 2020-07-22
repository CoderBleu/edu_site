package cn.blue.eduservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.eduservice.entity.EduSubject;
import cn.blue.eduservice.entity.subject.OneSubject;
import cn.blue.eduservice.entity.vo.SubjectQuery;
import cn.blue.eduservice.entity.vo.TeacherQuery;
import cn.blue.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
public class EduSubjectController {
    @Resource
    private EduSubjectService eduSubjectService;

    /**
     * 读取上传过来的文件，添加课程分类
     * @param file 上传过来的excel文件
     */
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file) {
        if(eduSubjectService.saveSubject(file, eduSubjectService)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 读取表格数据，下载成excel表格
     */
    @GetMapping("uploadSubject")
    public Result uploadSubject() {
        if(eduSubjectService.uploadSubject()) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 获取课程分类树型列表
     * @return 课程分类树型列表
     */
    @GetMapping("getSubjectTree")
    public Result getSubjectTree() {
        List<OneSubject> list  = eduSubjectService.getSubjectTree();
        return Result.success().data("list", list);
    }

    /**
     * 获取课程分类列表(根据条件查询)
     * @param eduSubject 查询条件
     * @return 课程分类列表
     * TODO 需要根据条件来查一二级分类，如果还需要分页应该需要引入pageHelper
     */
    @GetMapping("getSubjectListCondition")
    public Result getSubjectListCondition(@RequestBody(required = false) SubjectQuery subjectQuery) {
        Page<EduSubject> pageSubject = new Page<>();
        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        String title = subjectQuery.getTitle();
        String begin = subjectQuery.getBegin();
        String end = subjectQuery.getEnd();
        if(!StringUtils.isEmpty(title)) {
            title = title.trim();
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        List<OneSubject> list  = eduSubjectService.getSubjectListCondition();
        return Result.success().data("list", list);
    }

    /**
     * 获取课程分类列表(根据条件查询)
     * @param eduSubject 查询条件
     * @return 课程分类列表
     * TODO 增加children字段后select报错，然后使用mybatis的resultmap找不到方法，就算放在resource下。
     */
    @PostMapping("getAllSubject")
    public Result getAllSubject(EduSubject eduSubject) {
        List<EduSubject> list  = eduSubjectService.getAllSubject(eduSubject);
        return Result.success().data("list", list);
    }
}

