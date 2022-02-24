package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 番剧查询 dto
 *
 * @author ELiaNaCc
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "番剧查询dto")
public class ComicSelectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String comicName;

    /**
     * 状态- 0：已完结，1：周一更新...，8：更新中
     */
    @ApiModelProperty(value = "状态- 0：已完结，1：周一更新...，8：更新中", example = "0")
    private Integer comicStatus;

    /**
     * 上架状态- 1：上架，0：下架
     */
    @ApiModelProperty(value = "上架状态- 1：上架，0：下架", example = "1")
    private Integer comicShelfStatus;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private List<String> comicTag;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer pageNum;

    /**
     * 页记录数
     */
    @ApiModelProperty(value = "页记录数", example = "10")
    private Integer pageSize;

}
