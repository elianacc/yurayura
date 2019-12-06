package org.cny.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 番剧插入与更新 dto
 *
 * @author CNY
 * @date 2019年11月21日 20:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "番剧插入与更新dto")
public class ComicInstAndUpdtDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "名称", required = true)
    private String comicName;

    @ApiModelProperty(value = "简介")
    private String comicContent;

    @ApiModelProperty(value = "放送时间")
    private String comicTime;

    @ApiModelProperty(value = "链接")
    private String comicLink;

    @ApiModelProperty(value = "标签")
    private String comicLabel;

    @ApiModelProperty(value = "上架状态  1：上架  0：下架", required = true)
    private Integer comicShelfStatus;

    @ApiModelProperty(value = "状态 0：已完结 8：更新中", required = true)
    private Integer comicStatus;

    @ApiModelProperty(value = "更新时间 1：周一更新 ...")
    private Integer comicUdTime;

}
