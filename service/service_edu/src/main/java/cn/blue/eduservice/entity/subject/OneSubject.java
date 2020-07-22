package cn.blue.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
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
     * 分类排序
     */
    private Integer sort;

    /**
     * 分类添加时间
     */
    private Date gmtCreate;

    /**
     * 分类修改时间
     */
    private Date gmtModified;

    /**
     * 分类的状态
     */
    private Integer status;

    /**
     * 一个一级分类有多个二级分类
     */
    private List<TwoSubject> children = new ArrayList<>();
}
