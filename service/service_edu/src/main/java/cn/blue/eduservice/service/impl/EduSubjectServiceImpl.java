package cn.blue.eduservice.service.impl;

import cn.blue.eduservice.entity.EduSubject;
import cn.blue.eduservice.entity.excel.SubjectData;
import cn.blue.eduservice.entity.subject.OneSubject;
import cn.blue.eduservice.entity.subject.TwoSubject;
import cn.blue.eduservice.listener.SubjectExcelListener;
import cn.blue.eduservice.mapper.EduSubjectMapper;
import cn.blue.eduservice.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private EduSubjectMapper eduSubjectMapper;

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

    @Override
    public List<OneSubject> getSubjectTree() {
        //1 查询所有一级分类  parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有二级分类  parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        // 3 封装一级分类
        // 查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        // 封装到要求的list集合里面 List<OneSubject> finalSubjectList
        // 遍历oneSubjectList集合
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //得到oneSubjectList每个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);
            //把eduSubject里面值获取出来，放到OneSubject对象里面
            OneSubject oneSubject = new OneSubject();
            // oneSubject.setId(eduSubject.getId());
            // oneSubject.setTitle(eduSubject.getTitle());
            // eduSubject值复制到对应oneSubject对象里面
            BeanUtils.copyProperties(eduSubject, oneSubject);
            // 多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            // 在一级分类循环遍历查询所有的二级分类
            // 创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            // 遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                // 获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(m);
                // 判断二级分类parentid和一级分类id是否一样
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            // 把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        // 根据排序升序排
        finalSubjectList.sort((a,b) -> a.getSort().compareTo(b.getSort()));
        return finalSubjectList;
    }

    @Override
    public List<EduSubject> getAllSubject(EduSubject eduSubject) {
        return eduSubjectMapper.getAllSubject(eduSubject);
    }

    @Override
    public boolean uploadSubject() {
        String date = new DateTime().toString("yyyy/MM/dd").replace("/", "");
        String path = "D://Temp//";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        } /*else {
            file.delete();
        }*/
        String fileName = path + "课程分类-" + date + ".xlsx";
        try {
            List<EduSubject> list = eduSubjectMapper.selectList(null);
            EasyExcel.write(fileName, EduSubject.class).sheet("课程分类").doWrite(list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OneSubject> getSubjectListCondition() {
        return null;
    }
}
