package cn.blue.eduservice.service.impl;

import cn.blue.eduservice.entity.EduVideo;
import cn.blue.eduservice.mapper.EduVideoMapper;
import cn.blue.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public boolean checkIsUnique(EduVideo eduVideo) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("sort", eduVideo.getSort());
        wrapper.eq("chapter_id", eduVideo.getChapterId());
        return baseMapper.selectList(wrapper).size() > 0;
    }
}
