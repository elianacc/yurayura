package org.cny.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 番剧查询 dto
 *
 * @author CNY
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "番剧查询dto")
public class ComicSelectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String selectComicName;

    /**
     * 状态- 0：已完结，1：周一更新...，8：更新中
     */
    @ApiModelProperty(value = "状态")
    private Integer selectComicStatus;

}
