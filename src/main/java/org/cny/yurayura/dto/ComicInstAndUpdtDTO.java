package org.cny.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 番剧插入与更新 dto
 *
 * @author CNY
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "番剧插入与更新dto")
public class ComicInstAndUpdtDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    private String comicName;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String comicContent;

    /**
     * 放送时间
     */
    @ApiModelProperty(value = "放送时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate comicTime;

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
     * 当前话数
     */
    @ApiModelProperty(value = "当前话数")
    private Integer comicCurrentEpisodes;

    /**
     * 上架状态 1：上架  0：下架
     */
    @ApiModelProperty(value = "上架状态 1：上架  0：下架", required = true)
    private Integer comicShelfStatus;

    /**
     * 状态 0：已完结  8：更新中
     */
    @ApiModelProperty(value = "状态 0：已完结  8：更新中", required = true)
    private Integer comicStatus;

    /**
     * 更新时间 1：周一更新...
     */
    @ApiModelProperty(value = "更新时间 1：周一更新...")
    private Integer comicUdTime;

}
