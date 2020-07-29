package cn.blue.eduservice.service;

import cn.blue.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 判断小节是否唯一
     * @param eduVideo 小节信息
     * @return 是否唯一
     */
    boolean checkIsUnique(EduVideo eduVideo);
}
