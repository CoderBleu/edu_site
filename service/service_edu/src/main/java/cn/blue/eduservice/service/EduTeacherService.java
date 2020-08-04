package cn.blue.eduservice.service;

import cn.blue.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-30
 */
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     * 分页查询
     * @param pageTeacher 查询参数
     * @return 分页信息
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
