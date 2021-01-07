package cn.blue.eduservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.eduservice.entity.EduSubject;
import cn.blue.eduservice.entity.subject.OneSubject;
import cn.blue.eduservice.entity.vo.SubjectQuery;
import cn.blue.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
// @CrossOrigin
public class EduSubjectController {
    @Resource
    private EduSubjectService eduSubjectService;

    /**
     * 读取上传过来的文件，添加课程分类
     *
     * @param file 上传过来的excel文件
     */
    @PostMapping("addSubject")
    @ApiOperation("读取上传过来的文件，添加课程分类")
    public Result addSubject(MultipartFile file) {
        if (eduSubjectService.saveSubject(file, eduSubjectService)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 读取表格数据，下载成excel表格
     */
    @ApiOperation("读取表格数据，下载成excel表格")
    @GetMapping("uploadSubject")
    public Result uploadSubject() {
        if (eduSubjectService.uploadSubject()) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 获取课程分类树型列表
     *
     * @return 课程分类树型列表
     */
    @GetMapping("getSubjectTree")
    @ApiOperation(value = "获取课程分类树型列表")
    public Result getSubjectTree() {
        List<OneSubject> list = eduSubjectService.getSubjectTree();
        return Result.success().data("list", list);
    }


    /**
     * 获取课程分类列表(根据条件查询)
     *
     * @param eduSubject 查询条件
     * @return 课程分类列表
     */
    @ApiOperation(value = "获取课程分类列表(根据条件查询)")
    @PostMapping("getSubjectListCondition/{current}/{limit}")
    public Result getSubjectListCondition(@PathVariable Integer current,
                                          @PathVariable Integer limit,
                                          @RequestBody(required = false) EduSubject eduSubject) {
        String title = eduSubject.getTitle();
        if (!StringUtils.isEmpty(title)) {
            title = eduSubject.getTitle().trim();
        }
        PageHelper.startPage(current, limit);
        List<OneSubject> list = eduSubjectService.getAllSubject(eduSubject);
        PageInfo<OneSubject> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        return Result.success().data("list", list).data("total", total);
    }
}

