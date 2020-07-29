package cn.blue.eduservice.mapper;

import cn.blue.eduservice.entity.EduSubject;
import cn.blue.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 这边好像是resultMap封装的是OneSubject的对象，然后我传的是eduSubject对象，直接获取不到值
     * 但是加了别名后，别名.属性就可以。还有 date类型不需要 != ''
     */
    List<OneSubject> getAllSubject(@Param("eduSubject") EduSubject eduSubject);
}
