package cn.blue.eduservice.mapper;

import cn.blue.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@Mapper
public interface EduSubjectMapper extends BaseMapper<EduSubject> {

    /**
     * 获取课程分类列表
     * @param eduSubject 查询条件
     * @return 返回分类数据
     */
    List<EduSubject> getAllSubject(EduSubject eduSubject);
}
