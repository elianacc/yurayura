package org.cny.yurayura.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value="Comic对象", description="番剧")
public class Comic implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String comicName;

    @ApiModelProperty(value = "简介")
    private String comicContent;

    @ApiModelProperty(value = "放送时间")
    private String comicTime;

    @ApiModelProperty(value = "图片地址")
    private String comicImageUrl;

    @ApiModelProperty(value = "链接")
    private String comicLink;

    @ApiModelProperty(value = "标签")
    private String comicLabel;

    @ApiModelProperty(value = "状态 0：已完结  1：周一更新 ...   8：更新中")
    private Integer comicStatus;

    @ApiModelProperty(value = "上架状态  1：上架  0：下架")
    private Integer comicShelfStatus;


}
