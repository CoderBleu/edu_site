package cn.blue.servicevod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Blue
 */
public interface VodService {
    /**
     * 上传到阿里云
     *
     * @param file 视频
     * @return 返回视频 id
     */
    String uploadVideoAly(MultipartFile file);

    /**
     * 删除多个视频
     *
     * @param videoIdList id列表
     * @return 是否成功
     */
    boolean removeMoreAlyVideo(List<String> videoIdList);
}
