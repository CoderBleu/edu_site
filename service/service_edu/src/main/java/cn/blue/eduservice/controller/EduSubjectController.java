package cn.blue.eduservice.controller;


import cn.blue.commonutils.Result;
import cn.blue.eduservice.service.EduSubjectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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

}

