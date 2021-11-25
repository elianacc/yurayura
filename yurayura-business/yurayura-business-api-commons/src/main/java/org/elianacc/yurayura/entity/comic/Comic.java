package org.elianacc.yurayura.entity.comic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 番剧 entity
 *
 * @author ELiaNaCc
 * @since 2020-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
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
    @ApiModelProperty(value = "名称")
    private String comicName;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private Double comicScore;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String comicContent;

    /**
     * 放送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "放送时间")
    private LocalDate comicTime;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String comicImageUrl;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    private String comicLink;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String comicLabel;

    /**
     * 状态- 0：已完结，1：周一更新...，8：更新中
     */
    @ApiModelProperty(value = "状态- 0：已完结，1：周一更新...，8：更新中")
    private Integer comicStatus;

    /**
     * 当前话数
     */
    @ApiModelProperty(value = "当前话数")
    private Integer comicCurrentEpisodes;

    /**
     * 上架状态- 1：上架，0：下架
     */
    @ApiModelProperty(value = "上架状态- 1：上架，0：下架")
    private Integer comicShelfStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime comicCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime comicUpdateTime;


}
