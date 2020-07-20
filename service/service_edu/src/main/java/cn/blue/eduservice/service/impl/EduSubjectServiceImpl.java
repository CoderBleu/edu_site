package cn.blue.eduservice.service.impl;

import cn.blue.eduservice.entity.EduSubject;
import cn.blue.eduservice.entity.excel.SubjectData;
import cn.blue.eduservice.listener.SubjectExcelListener;
import cn.blue.eduservice.mapper.EduSubjectMapper;
import cn.blue.eduservice.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public boolean saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
            String fileName = "D://Temp//demo.xlsx";
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(fileName, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
