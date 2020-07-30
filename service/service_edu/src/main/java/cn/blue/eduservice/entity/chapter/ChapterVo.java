package cn.blue.eduservice.entity.chapter;

import lombok.Data;

import java.util.List;

/**
 * @author Blue
 * 课程章节
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    private Integer sort;

    /**
     * 表示小节
     */
    private List<VideoVo> children;
}
