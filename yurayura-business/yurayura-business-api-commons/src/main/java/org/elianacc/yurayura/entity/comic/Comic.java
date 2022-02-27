package org.elianacc.yurayura.entity.comic;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 番剧 entity
 *
 * @author ELiaNaCc
 * @since 2022-02-26
 */
@Data
@TableName("yurayura_comic")
@ApiModel(value = "Comic对象", description = "番剧")
public class Comic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 名称
     */
    @TableField("comic_name")
    @ApiModelProperty(value = "名称")
    private String comicName;

    /**
     * 评分
     */
    @TableField("comic_score")
    @ApiModelProperty(value = "评分")
    private Double comicScore;

    /**
     * 简介
     */
    @TableField("comic_content")
    @ApiModelProperty(value = "简介")
    private String comicContent;

    /**
     * 放送时间
     */
    @TableField("comic_time")
    @ApiModelProperty(value = "放送时间")
    private LocalDate comicTime;

    /**
     * 图片地址
     */
    @TableField("comic_image_url")
    @ApiModelProperty(value = "图片地址")
    private String comicImageUrl;

    /**
     * 链接
     */
    @TableField("comic_link")
    @ApiModelProperty(value = "链接")
    private String comicLink;

    /**
     * 标签
     */
    @TableField("comic_label")
    @ApiModelProperty(value = "标签")
    private String comicLabel;

    /**
     * 状态- 0：已完结，1：周一更新...，8：更新中
     */
    @TableField("comic_status")
    @ApiModelProperty(value = "状态- 0：已完结，1：周一更新...，8：更新中")
    private Integer comicStatus;

    /**
     * 当前话数
     */
    @TableField("comic_current_episodes")
    @ApiModelProperty(value = "当前话数")
    private Integer comicCurrentEpisodes;

    /**
     * 上架状态- 1：上架，0：下架
     */
    @TableField("comic_shelf_status")
    @ApiModelProperty(value = "上架状态- 1：上架，0：下架")
    private Integer comicShelfStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("comic_create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime comicCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("comic_update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime comicUpdateTime;


}
