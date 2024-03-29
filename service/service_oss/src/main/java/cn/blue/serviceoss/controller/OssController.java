package cn.blue.serviceoss.controller;

import cn.blue.commonutils.Result;
import cn.blue.serviceoss.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Blue
 */
@RestController
@RequestMapping("/eduOss/fileOss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像的方法
     *
     * @param file 头像
     */
    @PostMapping
    @ApiOperation(value = "上传Excel文件")
    public Result uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return Result.success().data("url", url);
    }
}
