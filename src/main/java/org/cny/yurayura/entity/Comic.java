package org.cny.yurayura.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_comic")
public class Comic implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String comicName;

    /**
     * 简介
     */
    private String comicContent;

    /**
     * 放送时间
     */
    private String comicTime;

    /**
     * 图片
     */
    private String comicImageUrl;

    /**
     * 链接
     */
    private String comicLink;

    /**
     * 标签
     */
    private String comicLabel;

    /**
     * 状态 0：已完结  1：周一更新 ...   8：更新中
     */
    private Integer comicStatus;


}
