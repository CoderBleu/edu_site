package cn.blue.eduservice.service;

import cn.blue.eduservice.entity.EduSubject;
import cn.blue.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file excel文件
     * @param eduSubjectService 为了查数据库
     * @return 添加课程分类
     */
    boolean saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 获取课程分类树形结构
     * @return 返回分类数据
     */
    List<OneSubject> getSubjectTree();

    /**
     * 获取课程分类列表
     * @param eduSubject 查询条件
     * @return 返回分类数据
     */
    List<EduSubject> getAllSubject(EduSubject eduSubject);

    /**
     * 根据条件获取课程分类列表
     * @return 返回分类数据
     */
    List<OneSubject> getSubjectListCondition();

    /**
     * 下载excel
     * @return 是否成功
     */
    boolean uploadSubject();
}
