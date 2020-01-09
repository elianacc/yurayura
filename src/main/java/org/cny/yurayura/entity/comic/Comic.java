package org.cny.yurayura.entity.comic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 番剧
 * </p>
 *
 * @author CNY
 * @since 2019-11-18
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
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate comicTime;

    /**
     * 图片地址
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
     * 状态- 0：已完结，1：周一更新...，8：更新中
     */
    private Integer comicStatus;

    /**
     * 当前话数
     */
    private Integer comicCurrentEpisodes;

    /**
     * 上架状态- 1：上架，0：下架
     */
    private Integer comicShelfStatus;

    /**
     * 最近编辑时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime comicCurrentEditTime;


}
