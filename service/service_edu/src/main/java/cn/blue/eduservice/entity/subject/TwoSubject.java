package cn.blue.eduservice.entity.subject;

import lombok.Data;

import java.util.Date;

/**
 * 二级分类
 *
 * @author Blue
 */
@Data
public class TwoSubject {
    /**
     * 二级分类 ID
     */
    private String id;

    /**
     * 二级分类标题
     */
    private String title;

    /**
     * 分类排序
     */
    private Integer sort;

    /**
     * 分类的状态
     */
    private Integer status;

    /**
     * 分类添加时间
     */
    private Date gmtCreate;

    /**
     * 分类修改时间
     */
    private Date gmtModified;
}
