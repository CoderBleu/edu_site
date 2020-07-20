package cn.blue.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 * @author Blue
 */
@Data
public class OneSubject {
    /**
     * 一级分类 ID
     */
    private String id;

    /**
     * 分类标题
     */
    private String title;

    /**
     * 一个一级分类有多个二级分类
     */
    private List<TwoSubject> children = new ArrayList<>();
}
